package cn.study.im.mvc.service;

import cn.study.common.base.IBaseService;
import cn.study.im.mvc.domain.entity.UserFriendGroup;
import cn.study.im.mvc.domain.vo.LayimUserGroupVo;

import java.util.List;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface UserFriendGroupService extends IBaseService<UserFriendGroup>{

    /**
     * 获取用户的分组,以及分组下的好友列表
     */
    List<LayimUserGroupVo> getUserFriendGroupListByUserId(String userId);
}
