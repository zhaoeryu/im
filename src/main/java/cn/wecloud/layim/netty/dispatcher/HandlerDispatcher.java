package cn.wecloud.layim.netty.dispatcher;

import cn.hutool.core.collection.CollectionUtil;
import cn.wecloud.layim.netty.exchanger.HandlerExchanger;
import cn.wecloud.layim.netty.protocol.packet.WsMessageRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/14
 */
@Slf4j
@Component
public class HandlerDispatcher implements ApplicationContextAware {

    private Collection<HandlerExchanger> exchangers;

    public void dispatcher(ChannelHandlerContext ctx, WsMessageRequestPacket packet){
        if (CollectionUtil.isEmpty(exchangers)){
            return;
        }
        exchangers.forEach(item -> {
            if (item.support(packet.getCmd())) {
                item.exchange(ctx,packet);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        exchangers = applicationContext.getBeansOfType(HandlerExchanger.class).values();
    }
}
