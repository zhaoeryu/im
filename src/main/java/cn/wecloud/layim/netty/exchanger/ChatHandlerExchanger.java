package cn.wecloud.layim.netty.exchanger;

import cn.hutool.core.lang.Assert;
import cn.study.common.utils.SpringContextHolder;
import cn.wecloud.layim.enums.LayimMessageTypeEnum;
import cn.wecloud.layim.mvc.domain.entity.MessageLog;
import cn.wecloud.layim.mvc.domain.message.TxtMessage;
import cn.wecloud.layim.mvc.service.MessageLogService;
import cn.wecloud.layim.netty.event.UnReadMessageEvent;
import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import cn.wecloud.layim.netty.protocol.packet.WsMessageRequestPacket;
import cn.wecloud.layim.netty.protocol.request.ChatMessage;
import cn.wecloud.layim.netty.protocol.response.ChatResponseMessage;
import cn.wecloud.layim.netty.utils.ObjectMapperUtils;
import cn.wecloud.layim.netty.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;

/**
 * @Desc : 收发单聊消息
 * @Create : zhaoey ~ 2020/06/14
 */
@Component
@Slf4j
public class ChatHandlerExchanger implements HandlerExchanger {

    @Resource
    private MessageLogService messageLogService;

    @Override
    public boolean support(byte cmd) {
        return MsgCommand.CHAT.getCmd() == cmd;
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        log.info("exchanger chat ...");
        log.info(packet.getMessage());

        // TODO 收发消息
        ChatMessage messageBody = ObjectMapperUtils.readValue(packet.getMessage(), ChatMessage.class);
        Assert.notNull(messageBody);
        // 1.聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]

        // 2.给自己发送成功消息
        Channel fromChannel = SessionUtil.getChannel(messageBody.getMine().getId());
        ChatResponseMessage responseMessage = new ChatResponseMessage();
        responseMessage.setCmd(packet.getCmd());
        responseMessage.setId(messageBody.getMine().getId());
        responseMessage.setUsername(messageBody.getMine().getUsername());
        responseMessage.setAvatar(messageBody.getMine().getAvatar());
        responseMessage.setContent(messageBody.getMine().getContent());
        responseMessage.setType(LayimMessageTypeEnum.FRIEND.getValue());
        responseMessage.setCid(null);
        responseMessage.setMine(true);
        responseMessage.setFromid(messageBody.getMine().getId());
        responseMessage.setTimestamp(Instant.now().toEpochMilli());
        sendMessage(fromChannel,ObjectMapperUtils.toJsonString(responseMessage));

        // 3.发送消息 从全局用户Channel关系中获取接受方的channel
        boolean isRead = false;
        Channel toChannel = SessionUtil.getChannel(messageBody.getTo().getId());
        if (null == toChannel){
            // TODO channel为null,代表用户不在线,推送消息
            log.info("用户不在线");
            SpringContextHolder.getApplicationContext().publishEvent(new UnReadMessageEvent(packet));
        }else{
            // 给接收者发送消息
            isRead = true;
            responseMessage.setMine(false);
            sendMessage(toChannel,ObjectMapperUtils.toJsonString(responseMessage));
        }

        // 4.保存聊天记录
//        超链接格式：a(地址)[文本]       如：a(http://www.layui.com)[layui]
//        图片格式：img[地址]            如：img[http://cdn.layui.com/xxx/a.jpg]
//        文件格式：file(地址)[文本]      如：file(http://cdn.layui.com/download/layim.zip)[layim.zip]
//        音频格式：audio[地址]          如：audio[http://cdn.layui.com/xxx/a.mp3]
//        视频格式：video[地址]          如：video[http://cdn.layui.com/xxx/a.avi]

        TxtMessage txtMessage = new TxtMessage();
        txtMessage.setMsg(messageBody.getMine().getContent());

        MessageLog messageLog = new MessageLog();
        messageLog.setFromId(messageBody.getMine().getId());
        messageLog.setToId(messageBody.getTo().getId());
        messageLog.setChatType(LayimMessageTypeEnum.FRIEND.getValue());
        messageLog.setContent(ObjectMapperUtils.toJsonString(txtMessage));
        messageLog.setSendTime(new Date(responseMessage.getTimestamp()));
        messageLog.setReadFlag(isRead);
        messageLogService.save(messageLog);
    }
}
