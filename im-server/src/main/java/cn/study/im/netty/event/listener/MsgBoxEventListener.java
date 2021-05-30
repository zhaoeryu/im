package cn.study.im.netty.event.listener;

import cn.study.im.mvc.domain.dto.MsgBoxUser;
import cn.study.im.netty.event.MsgBoxEvent;
import cn.study.im.netty.protocol.command.MsgCommand;
import cn.study.im.netty.protocol.response.MsgBoxResponseMessage;
import cn.study.im.netty.utils.ObjectMapperUtils;
import cn.study.im.netty.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 刷新请求
 * @author zhaoey
 * @since 2021/5/30
 */
@Slf4j
@Component
public class MsgBoxEventListener implements ApplicationListener<MsgBoxEvent> {

    @Override
    public void onApplicationEvent(MsgBoxEvent msgBoxEvent) {
        MsgBoxUser source = (MsgBoxUser)msgBoxEvent.getSource();
        Channel channel = SessionUtil.getChannel(source.getUserId());
        if (channel == null) {
            // 不在线
            return;
        }
        MsgBoxResponseMessage resp = new MsgBoxResponseMessage();
        resp.setCmd(MsgCommand.MSG_BOX.getCmd());
        resp.setUnreadCount(1);
        channel.writeAndFlush(new TextWebSocketFrame(ObjectMapperUtils.toJsonString(resp)).retain());
    }
}
