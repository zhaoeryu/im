package cn.wecloud.layim.mvc.domain.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class UserGroupDto implements Serializable {

    private String id;

    /** 群名 */
    private String groupname;

    /** 群头像 */
    private String avatar;

    /**
     * 群主
     */
    private String userId;

}
