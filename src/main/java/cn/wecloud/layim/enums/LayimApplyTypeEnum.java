package cn.wecloud.layim.enums;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public enum LayimApplyTypeEnum {
    // 添加好友申请
    FRIEND("friend")
    // 添加群申请
    ,GROUP("group")
    ;

    private String value;

    LayimApplyTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
