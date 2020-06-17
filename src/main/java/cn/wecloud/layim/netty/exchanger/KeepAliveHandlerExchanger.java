package cn.wecloud.layim.netty.exchanger;

import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Desc : 心跳
 * @Create : zhaoey ~ 2020/06/14
 */
@Component
@Slf4j
public class KeepAliveHandlerExchanger implements HandlerExchanger {

    @Override
    public boolean support(byte cmd) {
        return MsgCommand.KEEPALIVE.getCmd() == cmd;
    }

    @Override
    public boolean exchange(ChannelHandlerContext ctx, String message) {
        log.info("exchanger KEEPALIVE ...");
        log.info(message);
        // TODO 心跳消息
        log.info("收到来自channel为[" + ctx.channel() + "]的心跳包...");
        return true;
    }
}
