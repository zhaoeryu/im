package cn.wecloud.layim.mvc.service;

import cn.study.common.base.IBaseService;
import cn.wecloud.layim.layui.domain.vo.LayimGroupVo;
import cn.wecloud.layim.layui.domain.vo.LayimUserVo;
import cn.wecloud.layim.mvc.domain.dto.UserGroupQueryCriteria;
import cn.wecloud.layim.mvc.domain.entity.UserGroup;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface UserGroupService extends IBaseService<UserGroup>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserGroupQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<UserGroupDto>
    */
    List<UserGroup> queryAll(UserGroupQueryCriteria criteria);

    /**
     * 获取用户所有的群
     */
    List<LayimGroupVo> getGroupsByUserId(String userId);

    /**
     * 获取群成员
     */
    List<LayimUserVo> getUsersByGroupId(String groupId);
}
