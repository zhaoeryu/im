package cn.wecloud.layim.mvc.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc : layim用户
 * @Create : zhaoey ~ 2020/06/13
 */
@Data
public class LayimUserVo implements Serializable {

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

}
