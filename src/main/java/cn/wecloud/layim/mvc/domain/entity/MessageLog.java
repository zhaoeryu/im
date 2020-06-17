package cn.wecloud.layim.mvc.domain.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.validation.constraints.*;
import java.util.Date;
import java.io.Serializable;

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

    public void copy(MessageLog source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
