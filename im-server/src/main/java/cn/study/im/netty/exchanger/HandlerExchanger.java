package cn.study.im.netty.exchanger;

import cn.study.im.netty.protocol.packet.WsMessageRequestPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @Desc : ws消息处理交换器
 * @Create : zhaoey ~ 2020/06/14
 */
public interface HandlerExchanger {

    boolean support(byte cmd);

    void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet);

    default void sendMessage(Channel channel, String message){
        channel.writeAndFlush(new TextWebSocketFrame(message).retain());
    }
}
