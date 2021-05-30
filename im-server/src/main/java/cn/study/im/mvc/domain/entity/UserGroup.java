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
* @Create : zhaoey ~ 2020-06-14
*/
@Data
@TableName("user_group")
public class UserGroup implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
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

    private Date createTime;
    private Date updateTime;
    public void copy(UserGroup source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
