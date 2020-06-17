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
@TableName("user_group")
public class UserGroup implements Serializable {

    @TableId(type = IdType.ID_WORKER)
    private String id;


    /** 群名 */
    @NotBlank
    private String groupname;


    /** 群头像 */
    @NotBlank
    private String avatar;

    /**
     * 群主
     */
    @NotBlank
    private String userId;


    public void copy(UserGroup source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
