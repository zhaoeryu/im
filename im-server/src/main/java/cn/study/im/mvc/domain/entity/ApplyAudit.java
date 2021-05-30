package cn.study.im.mvc.domain.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
@TableName("apply_audit")
public class ApplyAudit implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;


    /** 申请类型：friend:添加好友申请，group:添加群申请 */
    @NotBlank
    private String type;


    /** 申请者ID */
    @NotBlank
    private String applyUserId;


    /** 审核者ID */
    @NotBlank
    private String auditUserId;


    /** 申请类型为friend，则值为分组ID；申请类型为group，则值为群ID */
    @NotBlank
    private String groupId;


    /** 申请备注 */
    @NotBlank
    private String remark;


    /** 申请时间 */
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;
    private Date updateTime;

    private Integer status;

    private Integer readFlag;

    public void copy(ApplyAudit source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
