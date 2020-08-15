package cn.wecloud.layim.mvc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.study.common.base.BaseServiceImpl;
import cn.study.common.dozer.service.IGenerator;
import cn.wecloud.layim.enums.LayimOnlineStatusEnum;
import cn.wecloud.layim.mvc.domain.entity.RelUserFriendGroup;
import cn.wecloud.layim.mvc.domain.entity.UserFriendGroup;
import cn.wecloud.layim.mvc.domain.entity.UserInfo;
import cn.wecloud.layim.mvc.domain.vo.LayimUserGroupVo;
import cn.wecloud.layim.mvc.domain.vo.LayimUserVo;
import cn.wecloud.layim.mvc.mapper.UserFriendGroupMapper;
import cn.wecloud.layim.mvc.service.RelUserFriendGroupService;
import cn.wecloud.layim.mvc.service.UserFriendGroupService;
import cn.wecloud.layim.mvc.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserFriendGroupServiceImpl extends BaseServiceImpl<UserFriendGroupMapper, UserFriendGroup> implements UserFriendGroupService {

    @Resource
    private IGenerator generator;
    @Resource
    private RelUserFriendGroupService relUserFriendGroupService;
    @Resource
    private UserInfoService userInfoService;

    @Override
    public List<LayimUserGroupVo> getUserFriendGroupListByUserId(String userId) {
        List<LayimUserGroupVo> result = new ArrayList<>();

        // 查询用户都有哪些分组
        List<RelUserFriendGroup> list = relUserFriendGroupService.list(new LambdaQueryWrapper<RelUserFriendGroup>().eq(RelUserFriendGroup::getUserId, userId));

        // 查询分组信息
        List<String> groupIds = list.stream().map(RelUserFriendGroup::getGroupId).distinct().collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(groupIds)){
            List<UserFriendGroup> friendGroups = list(new LambdaQueryWrapper<UserFriendGroup>().in(UserFriendGroup::getId, groupIds));
            result = friendGroups.stream().map(item -> {
                LayimUserGroupVo groupVo = new LayimUserGroupVo();
                groupVo.setId(item.getId());
                groupVo.setGroupname(item.getGroupname());
                groupVo.setOnline(0);
                groupVo.setList(new ArrayList<>());
                return groupVo;
            }).collect(Collectors.toList());
        }

        // 查询成员信息
        List<String> friendIds = list.stream().map(RelUserFriendGroup::getFriendId).distinct().collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(friendIds)){
            List<UserInfo> friends = userInfoService.list(new LambdaQueryWrapper<UserInfo>().in(UserInfo::getId, friendIds));
            Map<String,UserInfo> friendMap = new HashMap<>();
            friends.forEach(item -> friendMap.put(item.getId(),item));

            Map<String, List<RelUserFriendGroup>> groupMap = list.stream().collect(Collectors.groupingBy(RelUserFriendGroup::getGroupId));
            result.forEach(item -> {
                List<LayimUserVo> friendInfos = new ArrayList<>();
                List<RelUserFriendGroup> relUserFriendGroups = groupMap.get(item.getId());
                List<String> fids = relUserFriendGroups.stream().map(RelUserFriendGroup::getFriendId).collect(Collectors.toList());
                fids.forEach(fid -> {
                    LayimUserVo userVo = generator.convert(friendMap.get(fid), LayimUserVo.class);
                    if (LayimOnlineStatusEnum.HIDE.getStatus().equals(userVo.getStatus())){
                        userVo.setStatus(LayimOnlineStatusEnum.OFFLINE.getStatus());
                    }
                    friendInfos.add(userVo);
                });
                item.setOnline(relUserFriendGroups.size());
                item.setList(friendInfos);
            });
        }

        return result;
    }
}
