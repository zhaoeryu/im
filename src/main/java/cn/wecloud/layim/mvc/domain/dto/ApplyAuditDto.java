package cn.wecloud.layim.mvc.domain.dto;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
public class ApplyAuditDto implements Serializable {

    private Integer id;

    /** 申请类型：friend:添加好友申请，group:添加群申请 */
    private String type;

    /** 申请者ID */
    private String applyUserId;

    /** 审核者ID */
    private String auditUserId;

    /** 申请类型为friend，则值为分组ID；申请类型为group，则值为群ID */
    private String groupId;

    /** 申请备注 */
    private String remark;

    /** 申请时间 */
    private Date createTime;

    private Integer status;

}
