package cn.wecloud.layim.mvc.domain.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Data
@TableName("rel_user_friend_group")
public class RelUserFriendGroup implements Serializable {

    /** 用户ID */
    @NotBlank
    private String userId;


    /** 分组ID */
    @NotBlank
    private String groupId;


    /** 处于分组中的好友ID */
    @NotBlank
    private String friendId;


    public void copy(RelUserFriendGroup source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
