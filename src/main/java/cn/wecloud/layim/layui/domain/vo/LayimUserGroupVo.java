package cn.wecloud.layim.layui.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc : layim用户分组
 * @Create : zhaoey ~ 2020/06/13
 */
@Data
public class LayimUserGroupVo implements Serializable {

    /** 组ID */
    private String id;
    /** 组名 */
    private String groupname;
    /** 在线人数 */
    private Integer online;
    /** 分组成员 */
    private List<LayimUserVo> list;
}
