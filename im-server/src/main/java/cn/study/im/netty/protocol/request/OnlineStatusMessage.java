package cn.study.im.netty.protocol.request;

import lombok.Data;

/**
 * @Desc : 单聊消息体
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class OnlineStatusMessage extends BaseMessage {

    private String status;

}
