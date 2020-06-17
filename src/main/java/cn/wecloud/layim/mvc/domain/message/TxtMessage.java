package cn.wecloud.layim.mvc.domain.message;

import cn.wecloud.layim.constants.MessageTypeConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * @Desc : 文本消息体
 * @Create : zhaoey ~ 2020/06/17
 */
public class TxtMessage extends AbstractMessage {

    public TxtMessage(){
        super(MessageTypeConstants.TXT);
    }

    /** 消息体 */
    @Getter
    @Setter
    private String msg;


    private static final long serialVersionUID = -4148852496062989834L;
}
