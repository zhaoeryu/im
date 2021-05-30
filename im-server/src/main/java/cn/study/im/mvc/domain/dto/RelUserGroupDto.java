package cn.study.im.mvc.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class RelUserGroupDto implements Serializable {

    private String id;
    /** 群ID */
    private String groupId;

    /** 成员ID */
    private String userId;

    /** 最后一次读消息的时间 */
    private Date lastAckTime;
}
