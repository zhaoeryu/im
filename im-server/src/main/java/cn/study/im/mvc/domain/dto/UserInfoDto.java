package cn.study.im.mvc.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class UserInfoDto implements Serializable {

    private String id;

    /** 用户名 */
    private String username;

    /** 在线状态 */
    private String status;

    /** 签名 */
    private String sign;

    /** 用户头像 */
    private String avatar;
}
