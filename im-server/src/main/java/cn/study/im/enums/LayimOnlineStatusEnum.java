package cn.study.im.enums;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
public enum  LayimOnlineStatusEnum {
    // 在线
    ONLINE("online")
    // 不在线
    ,OFFLINE("offline")
    // 隐身
    ,HIDE("hide")
    ;

    private String status;

    LayimOnlineStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
