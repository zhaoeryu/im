package cn.wecloud.layim.mvc.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class RelUserFriendGroupDto implements Serializable {

    /** 群ID */
    private String groupId;

    /** 用户ID */
    private String userId;

    /**
     * 好友ID
     */
    private String friendId;
}
