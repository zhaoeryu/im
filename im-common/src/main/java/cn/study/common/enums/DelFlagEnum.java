package cn.study.common.enums;

/**
 * @Desc : 逻辑删除标记
 * @Create : zhaoey ~ 2020/06/12
 */
public enum DelFlagEnum {
    NORMAL(1,"正常")
    ,DELETE(0,"已删除")
    ;

    int value;
    String label;

    DelFlagEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
