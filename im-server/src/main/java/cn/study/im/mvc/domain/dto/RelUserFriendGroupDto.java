package cn.study.im.mvc.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class RelUserFriendGroupDto implements Serializable {

    private String id;

    /** 群ID */
    private String groupId;

    /** 用户ID */
    private String userId;

    /**
     * 好友ID
     */
    private String friendId;
}
