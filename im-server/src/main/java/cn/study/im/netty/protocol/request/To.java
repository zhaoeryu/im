package cn.study.im.netty.protocol.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class To implements Serializable {
    private String avatar;
    private String id;
    private String name;
    //聊天类型，一般分friend和group两种，group即群聊
    private String type;

    // friend
    private String sign;
    private String username;

    // group
    private String groupname;
    private Long historyTime;
}
