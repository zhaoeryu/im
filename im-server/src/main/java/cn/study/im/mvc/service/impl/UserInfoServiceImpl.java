package cn.study.im.mvc.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.study.common.base.BaseServiceImpl;
import cn.study.im.constants.BaseConstants;
import cn.study.im.enums.LayimOnlineStatusEnum;
import cn.study.im.mvc.domain.entity.UserFriendGroup;
import cn.study.im.mvc.domain.entity.UserInfo;
import cn.study.im.mvc.mapper.UserInfoMapper;
import cn.study.im.mvc.service.UserFriendGroupService;
import cn.study.im.mvc.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    UserFriendGroupService userFriendGroupService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void register(UserInfo userInfo) {
        List<UserInfo> list = list(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, userInfo.getUsername()));
        if (CollectionUtil.isNotEmpty(list)) {
            throw new RuntimeException("该用户名已被使用");
        }
        // 注册用户
        userInfo.setStatus(LayimOnlineStatusEnum.ONLINE.getStatus());
        userInfo.setSign(BaseConstants.DEFAULT_SIGN);
        userInfo.setAvatar(BaseConstants.DEFAULT_AVATAR);
        save(userInfo);

        // 给用户绑定默认的分组
        UserFriendGroup userFriendGroup1 = new UserFriendGroup();
        userFriendGroup1.setGroupname(BaseConstants.DEFAULT_BIND_GROUP_1);
        userFriendGroup1.setUserId(userInfo.getId());
        UserFriendGroup userFriendGroup2 = new UserFriendGroup();
        userFriendGroup2.setGroupname(BaseConstants.DEFAULT_BIND_GROUP_2);
        userFriendGroup2.setUserId(userInfo.getId());
        userFriendGroupService.saveBatch(CollUtil.newArrayList(userFriendGroup1, userFriendGroup2));
    }
}
