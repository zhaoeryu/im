package cn.study.im.netty.protocol.packet;

import lombok.Data;

/**
 * @Desc : 消息传输体
 * @Create : zhaoey ~ 2020/06/13
 */
@Data
public class WsMessageRequestPacket {

    /** 命令 */
    private Byte cmd;
    /** 消息体 */
    private String message;

}
