package cn.study.im.netty.protocol.response;

import cn.study.im.netty.protocol.request.BaseMessage;
import lombok.Data;

import java.time.Instant;

/**
 * @Desc : 单聊响应给对方的消息体
 * @Create : zhaoey ~ 2020/06/14
 */
@Data
public class ChatResponseMessage extends BaseMessage {

    //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
    private String id;
    //消息来源用户名
    private String username;
    //消息来源用户头像
    private String avatar;
    /** 内容 */
    private String content;
    //聊天窗口来源类型，从发送消息传递的to里面获取
    private String type;
    //消息id，可不传。除非你要对消息进行一些操作（如撤回）
    private String cid;
    //是否我发送的消息，如果为true，则会显示在右方
    private boolean mine;
    //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
    private String fromid;
    //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
    private long timestamp = Instant.now().toEpochMilli();

}
