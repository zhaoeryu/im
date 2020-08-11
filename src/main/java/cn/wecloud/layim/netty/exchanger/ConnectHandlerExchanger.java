package cn.wecloud.layim.netty.exchanger;

import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import cn.wecloud.layim.netty.session.Session;
import cn.wecloud.layim.netty.utils.ObjectMapperUtils;
import cn.wecloud.layim.netty.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Desc : ws连接成功
 * @Create : zhaoey ~ 2020/06/14
 */
@Component
@Slf4j
public class ConnectHandlerExchanger implements HandlerExchanger {

    @Override
    public boolean support(byte cmd) {
        return MsgCommand.CONNECT.getCmd() == cmd;
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, String message) {
        log.info("exchanger connect ...");
        log.info(message);

        // TODO 打开WS连接,保存Session
        // 当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
        SessionUtil.bindSession(ctx.channel(), Objects.requireNonNull(ObjectMapperUtils.readValue(message, Session.class)));
    }
}
