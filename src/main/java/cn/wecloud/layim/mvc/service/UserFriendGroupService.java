package cn.wecloud.layim.mvc.service;

import cn.study.common.base.IBaseService;
import cn.wecloud.layim.layui.domain.vo.LayimUserGroupVo;
import cn.wecloud.layim.mvc.domain.dto.UserFriendGroupQueryCriteria;
import cn.wecloud.layim.mvc.domain.entity.UserFriendGroup;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface UserFriendGroupService extends IBaseService<UserFriendGroup>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserFriendGroupQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<UserFriendGroupDto>
    */
    List<UserFriendGroup> queryAll(UserFriendGroupQueryCriteria criteria);

    /**
     * 获取用户的分组,以及分组下的好友列表
     */
    List<LayimUserGroupVo> getUserFriendGroupListByUserId(String userId);
}
