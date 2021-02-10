package cn.study.im.netty.event;

import org.springframework.context.ApplicationEvent;

/**
 * 未读消息事件
 */
public class UnReadMessageEvent extends ApplicationEvent {
    public UnReadMessageEvent(Object source) {
        super(source);
    }
}
