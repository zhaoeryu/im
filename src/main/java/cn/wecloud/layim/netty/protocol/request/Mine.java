package cn.wecloud.layim.netty.protocol.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class Mine implements Serializable {
    private String avatar;
    private String id;
    private String username;
    //是否我发送的消息
    private boolean mine;
    private String content;
}
