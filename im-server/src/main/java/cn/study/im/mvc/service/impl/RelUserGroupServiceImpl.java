package cn.study.im.mvc.service.impl;

import cn.study.common.base.BaseServiceImpl;
import cn.study.im.mvc.domain.entity.RelUserGroup;
import cn.study.im.mvc.mapper.RelUserGroupMapper;
import cn.study.im.mvc.service.RelUserGroupService;
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
public class RelUserGroupServiceImpl extends BaseServiceImpl<RelUserGroupMapper, RelUserGroup> implements RelUserGroupService {

}
