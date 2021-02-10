package cn.study.im.mvc.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc : layim用户群
 * @Create : zhaoey ~ 2020/06/13
 */
@Data
public class LayimGroupVo implements Serializable {

    /** 组ID */
    private String id;
    /** 组名 */
    private String groupname;
    /** 头像 */
    private String avatar;
}
