package cn.wecloud.layim.netty.protocol.command;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/14
 */
public enum MsgCommand {
    /** 1. 第一次(或重连)初始化连接 */
    CONNECT((byte)1)
    /** 2. 聊天消息 */
    ,CHAT((byte)2)
    /** 3. 消息签收 */
    ,SIGNED((byte)3)
    /** 4. 客户端保持心跳 */
    ,KEEPALIVE((byte)4)
    /** 5. 群消息 */
    ,GROUP_MSG((byte)5)
    ;

    private final byte cmd;

    MsgCommand(byte cmd) {
        this.cmd = cmd;
    }

    public byte getCmd(){
        return cmd;
    }

}
