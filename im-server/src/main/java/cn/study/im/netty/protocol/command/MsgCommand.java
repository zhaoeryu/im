package cn.study.im.netty.protocol.command;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/14
 */
public enum MsgCommand {
    /** 第一次(或重连)初始化连接 */
    CONNECT((byte)1)
    /** 聊天消息 */
    ,CHAT((byte)2)
    /** 消息签收 */
    ,SIGNED((byte)3)
    /** 客户端保持心跳 */
    ,KEEPALIVE((byte)4)
    /** 群消息 */
    ,GROUP_MSG((byte)5)
    /** 在线状态 */
    ,ONLINE_STATUS((byte)6)
    /** 消息盒子 */
    ,MSG_BOX((byte) 7)
    ;

    private final byte cmd;

    MsgCommand(byte cmd) {
        this.cmd = cmd;
    }

    public byte getCmd(){
        return cmd;
    }

}
