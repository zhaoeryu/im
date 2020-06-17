package cn.wecloud.layim.mvc.domain.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
@TableName("rel_user_group")
public class RelUserGroup implements Serializable {

    /** 群ID */
    @NotBlank
    private String groupId;


    /** 成员ID */
    @NotBlank
    private String userId;

    /** 最后一次读消息的时间 */
    private Date lastAckTime;


    public void copy(RelUserGroup source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
