package cn.wecloud.layim.netty.protocol.request;

import lombok.Data;

/**
 * @Desc : 消息签收体
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class SignedMessage extends BaseMessage {

    /**
     * 多个消息ID 用逗号分隔
     */
    private String msgids;

}
