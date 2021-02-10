package cn.study.im.enums;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public enum LayimApplyStatusEnum {
    // 已申请
    APPLY(0)
    // 已同意
    ,AGREE(1)
    // 已拒绝
    ,DENY(2)
    ;

    private final int value;

    LayimApplyStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
