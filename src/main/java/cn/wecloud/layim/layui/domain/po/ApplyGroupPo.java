package cn.wecloud.layim.layui.domain.po;

import lombok.Data;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class ApplyGroupPo {

    /** 我的ID，暂时测试用 */
    private String mineId;
    /**
     * 要添加的群ID
     */
    private String groupId;
    /**
     * 申请备注
     */
    private String remark;

}
