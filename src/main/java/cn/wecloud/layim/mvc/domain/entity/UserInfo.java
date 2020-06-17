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
@TableName("user_info")
public class UserInfo implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;


    /** 用户名 */
    @NotBlank
    private String username;


    /** 在线状态 */
    private String status;


    /** 签名 */
    private String sign;


    /** 用户头像 */
    private String avatar;


    public void copy(UserInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
