package cn.study.im.netty.protocol.response;

import cn.study.im.netty.protocol.request.BaseMessage;
import lombok.Data;

/**
 * @Desc : 单聊响应给对方的消息体
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class MsgBoxResponseMessage extends BaseMessage {

    /** 消息未读数量 */
    private long unreadCount;

}
