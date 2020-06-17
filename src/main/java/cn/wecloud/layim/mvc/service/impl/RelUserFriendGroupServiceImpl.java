package cn.wecloud.layim.mvc.service.impl;

import cn.study.common.base.BaseServiceImpl;
import cn.wecloud.layim.mvc.domain.entity.RelUserFriendGroup;
import cn.wecloud.layim.mvc.mapper.RelUserFriendGroupMapper;
import cn.wecloud.layim.mvc.service.RelUserFriendGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RelUserFriendGroupServiceImpl extends BaseServiceImpl<RelUserFriendGroupMapper, RelUserFriendGroup> implements RelUserFriendGroupService {

}
