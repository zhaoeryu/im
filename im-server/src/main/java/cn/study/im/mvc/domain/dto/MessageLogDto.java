package cn.study.im.mvc.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-16
*/
@Data
public class MessageLogDto implements Serializable {

    private String id;

    /** 消息发送者ID */
    private String fromId;

    /** 消息接收者ID，如果为群聊消息，则为群ID */
    private String toId;

    /** 消息类型，单聊chat/群聊groupchat */
    private String chatType;

    /** 消息内容，json格式，不同消息类型，json格式不一样 */
    private String content;

    /** 消息发送时间 */
    private Date sendTime;
}
