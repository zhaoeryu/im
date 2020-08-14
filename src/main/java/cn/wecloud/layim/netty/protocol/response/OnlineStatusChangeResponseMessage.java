package cn.wecloud.layim.netty.protocol.response;

import cn.wecloud.layim.netty.protocol.request.BaseMessage;
import lombok.Data;

/**
 * @Desc : 单聊响应给对方的消息体
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class OnlineStatusChangeResponseMessage extends BaseMessage {

    /** 好友ID */
    private String friendId;
    /** 在线状态 */
    private String onlineStatus;

}
