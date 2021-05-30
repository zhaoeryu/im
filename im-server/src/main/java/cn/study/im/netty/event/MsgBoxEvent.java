package cn.study.im.netty.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhaoey
 * @since 2021/5/30
 */
public class MsgBoxEvent extends ApplicationEvent {
    public MsgBoxEvent(Object source) {
        super(source);
    }
}
