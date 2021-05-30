package cn.study.im.mvc.domain.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-16
*/
@Data
@TableName("message_log")
public class MessageLog implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;


    /** 消息发送者ID */
    @NotBlank
    private String fromId;


    /** 消息接收者ID，如果为群聊消息，则为群ID */
    @NotBlank
    private String toId;


    /** 消息类型，单聊friend/群聊group */
    @NotBlank
    private String chatType;


    /** 消息内容，json格式，不同消息类型，json格式不一样 */
    @NotBlank
    private String content;


    /** 消息发送时间 */
    private Date sendTime;

    private Boolean readFlag;

    private Date createTime;
    private Date updateTime;

    public void copy(MessageLog source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
