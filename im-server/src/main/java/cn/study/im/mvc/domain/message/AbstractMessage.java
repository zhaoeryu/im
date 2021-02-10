package cn.study.im.mvc.domain.message;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/17
 */
public abstract class AbstractMessage implements Serializable {
    /** 消息类型 */
    private Integer type;

    public AbstractMessage(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
