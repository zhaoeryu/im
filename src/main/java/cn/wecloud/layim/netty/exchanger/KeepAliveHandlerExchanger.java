package cn.wecloud.layim.netty.exchanger;

import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import cn.wecloud.layim.netty.protocol.packet.WsMessageRequestPacket;
import cn.wecloud.layim.netty.protocol.request.Mine;
import cn.wecloud.layim.netty.session.Session;
import cn.wecloud.layim.netty.utils.ObjectMapperUtils;
import cn.wecloud.layim.netty.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        log.info("exchanger KEEPALIVE ...");
        log.info(packet.getMessage());
        // TODO 心跳消息
        log.info("收到来自channel为[" + ctx.channel() + "]的心跳包...");
        Mine mine = ObjectMapperUtils.readValue(packet.getMessage(), Mine.class);
        if(mine == null){
            log.info("心跳包不包含用户信息");
            return;
        }
        Channel fromChannel = SessionUtil.getChannel(mine.getId());
        if (null == fromChannel){
            log.info("短线重连导致获取到channel，重新绑定");
            SessionUtil.bindSession(ctx.channel(), Objects.requireNonNull(ObjectMapperUtils.readValue(packet.getMessage(), Session.class)));
        }
        log.info("当前channel个数为：{}", SessionUtil.getOnlineCount());
        log.info("当前Groupchannel个数为：{}", SessionUtil.getOnlineGroupCount());
    }
}
