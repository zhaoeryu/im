package cn.wecloud.layim.netty.exchanger;

import cn.wecloud.layim.enums.LayimOnlineStatusEnum;
import cn.wecloud.layim.mvc.domain.entity.RelUserFriendGroup;
import cn.wecloud.layim.mvc.service.RelUserFriendGroupService;
import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import cn.wecloud.layim.netty.protocol.packet.WsMessageRequestPacket;
import cn.wecloud.layim.netty.protocol.response.OnlineStatusChangeResponseMessage;
import cn.wecloud.layim.netty.session.Session;
import cn.wecloud.layim.netty.utils.ObjectMapperUtils;
import cn.wecloud.layim.netty.utils.SessionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc : ws连接成功
 * @Create : zhaoey ~ 2020/06/14
 */
@Component
@Slf4j
public class ConnectHandlerExchanger implements HandlerExchanger {

    @Resource
    private RelUserFriendGroupService relUserFriendGroupService;

    @Override
    public boolean support(byte cmd) {
        return MsgCommand.CONNECT.getCmd() == cmd;
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        log.info("exchanger connect ...");
        log.info(packet.getMessage());

        // TODO 打开WS连接,保存Session
        // 当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
        Session session = Objects.requireNonNull(ObjectMapperUtils.readValue(packet.getMessage(), Session.class));
        SessionUtil.bindSession(ctx.channel(), session);

        final AtomicInteger count = new AtomicInteger(0);
        OnlineStatusChangeResponseMessage responseMessage = new OnlineStatusChangeResponseMessage();
        responseMessage.setCmd(MsgCommand.ONLINE_STATUS.getCmd());
        responseMessage.setFriendId(session.getId());
        responseMessage.setOnlineStatus(LayimOnlineStatusEnum.HIDE.getStatus().equals(session.getStatus()) ? LayimOnlineStatusEnum.OFFLINE.getStatus() : LayimOnlineStatusEnum.ONLINE.getStatus());
        String respMsgStr = ObjectMapperUtils.toJsonString(responseMessage);
        relUserFriendGroupService.list(new LambdaQueryWrapper<RelUserFriendGroup>()
                .eq(RelUserFriendGroup::getUserId,session.getId()))
                .forEach(item -> {
                    Channel channel = SessionUtil.getChannel(item.getFriendId());
                    if (null == channel){
                        return;
                    }
                    channel.writeAndFlush(new TextWebSocketFrame(respMsgStr).retain());
                    count.incrementAndGet();
                });
        log.info("{} 上线成功，通知给 {} 位好友",session.getUsername(),count.get());
    }
}
