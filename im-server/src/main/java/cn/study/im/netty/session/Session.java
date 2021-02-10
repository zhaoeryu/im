package cn.study.im.netty.session;

import lombok.Data;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@Data
public class Session {

    /** 用户ID */
    private String id;
    /** 用户名 */
    private String username;
    /** 在线状态 {online,offline} */
    private String status;
    /** 签名 */
    private String sign;
    /** 头像 */
    private String avatar;

    public Session() {
    }

    public Session(String id, String username, String status, String sign, String avatar) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.sign = sign;
        this.avatar = avatar;
    }
}
