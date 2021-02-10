package cn.study.im.netty.protocol.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc : 基础消息体
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public abstract class BaseMessage implements Serializable {
    /** 命令 */
    private Byte cmd;
}
