package cn.study.im.netty.exchanger;

import cn.hutool.core.lang.Assert;
import cn.study.im.netty.protocol.packet.WsMessageRequestPacket;
import cn.study.im.netty.utils.ObjectMapperUtils;
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

    default <T> T readValue(WsMessageRequestPacket packet,Class<T> clazz, boolean checkNull){
        T t = ObjectMapperUtils.readValue(packet.getMessage(), clazz);
        if (checkNull) {
            Assert.notNull(t);
        }
        return t;
    }
    default <T> T readValue(WsMessageRequestPacket packet,Class<T> clazz){
        return readValue(packet, clazz, true);
    }
}
