package cn.wecloud.layim.layui.enums;

/**
 * @Desc : 消息类型
 * @Create : zhaoey ~ 2020/06/13
 */
public enum LayimMessageTypeEnum {
    FRIEND("friend")
    ,GROUP("group")
    ;

    private String value;

    LayimMessageTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
