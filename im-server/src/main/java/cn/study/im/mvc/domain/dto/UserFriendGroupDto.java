package cn.study.im.mvc.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class UserFriendGroupDto implements Serializable {

    private String id;

    /** 分组名 */
    private String groupname;
}
