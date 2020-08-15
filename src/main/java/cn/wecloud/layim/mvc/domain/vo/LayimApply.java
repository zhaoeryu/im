package cn.wecloud.layim.mvc.domain.vo;

import cn.wecloud.layim.mvc.domain.entity.UserGroup;
import lombok.Data;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/16
 */
@Data
public class LayimApply implements Serializable {

    private Integer id;
    private String content;
    private String uid;
    // 对方ID
    private String from;
    // 对方设定的好友分组
    private String fromGroup;
    private String remark;
    private Integer read;
    private String time;
    private LayimUserVo user;
    private UserGroup group;

    private String href;
    private String type;

    private static final long serialVersionUID = -2400760186061514424L;
}
