package cn.study.im.netty.exchanger;

import cn.hutool.core.collection.CollectionUtil;
import cn.study.im.netty.protocol.command.MsgCommand;
import cn.study.im.netty.protocol.packet.WsMessageRequestPacket;
import cn.study.im.netty.protocol.request.SignedMessage;
import cn.study.im.netty.utils.ObjectMapperUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Desc : 消息签收
 * @Create : zhaoey ~ 2020/06/14
 */
@Component
@Slf4j
public class SignedHandlerExchanger implements HandlerExchanger {

    @Override
    public boolean support(byte cmd) {
        return MsgCommand.SIGNED.getCmd() == cmd;
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        log.info("exchanger signed ...");
        log.info(packet.getMessage());

        // TODO 消息签收
        SignedMessage signedMessage = ObjectMapperUtils.readValue(packet.getMessage(), SignedMessage.class);
        Assert.notNull(signedMessage);

        String[] msgIds = StringUtils.delimitedListToStringArray(signedMessage.getMsgids(), ",");
        List<String> mids = Arrays.asList(msgIds);
        if (CollectionUtil.isEmpty(mids)){

        }
        // ...
    }
}
