package cn.wecloud.layim.mvc.service;

import cn.study.common.base.IBaseService;
import cn.wecloud.layim.mvc.domain.entity.UserGroup;
import cn.wecloud.layim.mvc.domain.vo.LayimGroupVo;
import cn.wecloud.layim.mvc.domain.vo.LayimUserVo;

import java.util.List;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface UserGroupService extends IBaseService<UserGroup>{

    /**
     * 获取用户所有的群
     */
    List<LayimGroupVo> getGroupsByUserId(String userId);

    /**
     * 获取群成员
     */
    List<LayimUserVo> getUsersByGroupId(String groupId);
}
