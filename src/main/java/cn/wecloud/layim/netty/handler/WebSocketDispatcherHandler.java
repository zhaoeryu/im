package cn.wecloud.layim.netty.handler;

import cn.study.common.utils.SpringContextHolder;
import cn.wecloud.layim.layui.enums.LayimOnlineStatusEnum;
import cn.wecloud.layim.mvc.domain.entity.RelUserFriendGroup;
import cn.wecloud.layim.mvc.service.RelUserFriendGroupService;
import cn.wecloud.layim.netty.dispatcher.HandlerDispatcher;
import cn.wecloud.layim.netty.protocol.command.MsgCommand;
import cn.wecloud.layim.netty.protocol.packet.WsMessageRequestPacket;
import cn.wecloud.layim.netty.protocol.response.OnlineStatusChangeResponseMessage;
import cn.wecloud.layim.netty.session.Session;
import cn.wecloud.layim.netty.utils.ObjectMapperUtils;
import cn.wecloud.layim.netty.utils.SessionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@Slf4j
public class WebSocketDispatcherHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame text) throws Exception {
        log.info("ws handler ...");
        WsMessageRequestPacket wsMessage = objectMapper.readValue(text.text(), WsMessageRequestPacket.class);
        text.retain();
        SpringContextHolder.getBean(HandlerDispatcher.class).dispatcher(ctx,wsMessage);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("netty 获得连接 ...");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        log.info("客户端被移除，channelId为：" + channelId);
        // 移除Session
        Session session = SessionUtil.getSession(ctx.channel());
        SessionUtil.unbindSession(ctx.channel());
        notifyOffline(session);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(" netty 异常了...... ");
        cause.printStackTrace();
        // 移除Session
        Session session = SessionUtil.getSession(ctx.channel());
        SessionUtil.unbindSession(ctx.channel());
        // 发生异常之后关闭连接（关闭channel）
        ctx.channel().close();
        notifyOffline(session);
    }

    private void notifyOffline(Session session){
        RelUserFriendGroupService relUserFriendGroupService = SpringContextHolder.getBean(RelUserFriendGroupService.class);
        final AtomicInteger count = new AtomicInteger(0);
        OnlineStatusChangeResponseMessage responseMessage = new OnlineStatusChangeResponseMessage();
        responseMessage.setCmd(MsgCommand.ONLINE_STATUS.getCmd());
        responseMessage.setFriendId(session.getId());
        responseMessage.setOnlineStatus(LayimOnlineStatusEnum.OFFLINE.getStatus());
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
        log.info("{} 离线，通知给 {} 位好友",session.getUsername(),count.get());
    }

}
