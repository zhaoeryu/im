package cn.wecloud.layim.netty.exchanger;

import cn.hutool.core.collection.CollectionUtil;
import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import cn.wecloud.layim.netty.protocol.request.SignedMessage;
import cn.wecloud.layim.netty.utils.ObjectMapperUtils;
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
    public void exchange(ChannelHandlerContext ctx, String message, Byte cmd) {
        log.info("exchanger signed ...");
        log.info(message);

        // TODO 消息签收
        SignedMessage signedMessage = ObjectMapperUtils.readValue(message, SignedMessage.class);
        Assert.notNull(signedMessage);

        String[] msgIds = StringUtils.delimitedListToStringArray(signedMessage.getMsgids(), ",");
        List<String> mids = Arrays.asList(msgIds);
        if (CollectionUtil.isEmpty(mids)){

        }
        // ...
    }
}
