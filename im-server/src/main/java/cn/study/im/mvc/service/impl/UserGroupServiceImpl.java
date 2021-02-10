package cn.study.im.mvc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.study.common.base.BaseServiceImpl;
import cn.study.common.dozer.service.IGenerator;
import cn.study.im.mvc.domain.entity.RelUserGroup;
import cn.study.im.mvc.domain.entity.UserGroup;
import cn.study.im.mvc.domain.entity.UserInfo;
import cn.study.im.mvc.domain.vo.LayimGroupVo;
import cn.study.im.mvc.domain.vo.LayimUserVo;
import cn.study.im.mvc.mapper.UserGroupMapper;
import cn.study.im.mvc.service.RelUserGroupService;
import cn.study.im.mvc.service.UserGroupService;
import cn.study.im.mvc.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Resource
    private IGenerator generator;
    @Resource
    private RelUserGroupService relUserGroupService;
    @Resource
    private UserInfoService userInfoService;

    @Override
    public List<LayimGroupVo> getGroupsByUserId(String userId) {
        // 获取用户参加的所有群
        List<RelUserGroup> list = relUserGroupService.list(new LambdaQueryWrapper<RelUserGroup>().eq(RelUserGroup::getUserId, userId));
        if (CollectionUtil.isNotEmpty(list)){
            List<String> groupIds = list.stream().map(RelUserGroup::getGroupId).distinct().collect(Collectors.toList());
            List<UserGroup> groups = list(new LambdaQueryWrapper<UserGroup>().in(UserGroup::getId, groupIds));
            return generator.convert(groups, LayimGroupVo.class);
        }
        return new ArrayList<>();
    }

    @Override
    public List<LayimUserVo> getUsersByGroupId(String groupId) {
        List<RelUserGroup> list = relUserGroupService.list(new LambdaQueryWrapper<RelUserGroup>().eq(RelUserGroup::getGroupId, groupId));
        if (CollectionUtil.isNotEmpty(list)){
            List<String> userIds = list.stream().map(RelUserGroup::getUserId).distinct().collect(Collectors.toList());
            List<UserInfo> users = userInfoService.list(new LambdaQueryWrapper<UserInfo>().in(UserInfo::getId, userIds));
            return generator.convert(users,LayimUserVo.class);
        }
        return new ArrayList<>();
    }
}
