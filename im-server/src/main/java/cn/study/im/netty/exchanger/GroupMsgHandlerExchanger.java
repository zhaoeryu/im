package cn.study.im.netty.exchanger;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.study.im.enums.LayimMessageTypeEnum;
import cn.study.im.mvc.domain.entity.MessageLog;
import cn.study.im.mvc.domain.entity.RelUserGroup;
import cn.study.im.mvc.domain.message.TxtMessage;
import cn.study.im.mvc.service.MessageLogService;
import cn.study.im.mvc.service.RelUserGroupService;
import cn.study.im.netty.protocol.command.MsgCommand;
import cn.study.im.netty.protocol.packet.WsMessageRequestPacket;
import cn.study.im.netty.protocol.request.ChatMessage;
import cn.study.im.netty.protocol.response.ChatResponseMessage;
import cn.study.im.netty.utils.ObjectMapperUtils;
import cn.study.im.netty.utils.SessionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc : 收发群聊消息
 * @Create : zhaoey ~ 2020/06/14
 */
@Component
@Slf4j
public class GroupMsgHandlerExchanger implements HandlerExchanger {

    @Resource
    private MessageLogService messageLogService;
    @Resource
    private RelUserGroupService relUserGroupService;

    @Override
    public boolean support(byte cmd) {
        return MsgCommand.GROUP_MSG.getCmd() == cmd;
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        // TODO 群消息发送
        log.info("exchanger group_msg ...");
        log.info(packet.getMessage());

        ChatMessage messageBody = ObjectMapperUtils.readValue(packet.getMessage(), ChatMessage.class);
        Assert.notNull(messageBody);
        // 1.聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]

        // 2.给自己发送成功消息
        Channel fromChannel = SessionUtil.getChannel(messageBody.getMine().getId());
        ChatResponseMessage responseMessage = new ChatResponseMessage();
        responseMessage.setCmd(packet.getCmd());
        responseMessage.setId(messageBody.getTo().getId());
        responseMessage.setUsername(messageBody.getMine().getUsername());
        responseMessage.setAvatar(messageBody.getMine().getAvatar());
        responseMessage.setContent(messageBody.getMine().getContent());
        responseMessage.setType(LayimMessageTypeEnum.GROUP.getValue());
        responseMessage.setCid(null);
        responseMessage.setMine(true);
        responseMessage.setFromid(messageBody.getMine().getId());
        responseMessage.setTimestamp(Instant.now().toEpochMilli());
        sendMessage(fromChannel,ObjectMapperUtils.toJsonString(responseMessage));

        // 1.获取群组
//        ChannelGroup channelGroup = SessionUtil.getChannelGroup(messageBody.getTo().getId());
        List<RelUserGroup> groupUsers = relUserGroupService.list(new LambdaQueryWrapper<RelUserGroup>().eq(RelUserGroup::getGroupId, messageBody.getTo().getId()));
//        // 添加伪数据--记得替换 1.聊天记录，2.群成员
//        channelGroup = SessionUtil.initChannelGroup(messageBody.getTo().getId(),new DefaultChannelGroup(ctx.executor()));;

        // 2.给群组广播消息,排除自己
        responseMessage.setMine(false);
        String respMsg = ObjectMapperUtils.toJsonString(responseMessage);
        final AtomicInteger count = new AtomicInteger(0);
        groupUsers.forEach(item -> {
            Channel channel = SessionUtil.getChannel(item.getUserId());
            if (!StrUtil.equals(messageBody.getMine().getId(),item.getUserId()) && null != channel){
                count.incrementAndGet();
                channel.writeAndFlush(new TextWebSocketFrame(respMsg).retain());
            }
        });
        log.info("群消息发送成功,群ID：{},给{}个成员发送了消息,在线：{}人，离线：{}人",messageBody.getTo().getId(),count.get(),count.get()+1,groupUsers.size()-1 - count.get());

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
        messageLog.setChatType(LayimMessageTypeEnum.GROUP.getValue());
        messageLog.setContent(ObjectMapperUtils.toJsonString(txtMessage));
        messageLog.setSendTime(new Date(responseMessage.getTimestamp()));
        messageLogService.save(messageLog);
    }
}
