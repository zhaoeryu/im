package cn.wecloud.layim.mvc.service.impl;


import cn.study.common.base.BaseServiceImpl;
import cn.wecloud.layim.mvc.domain.entity.UserInfo;
import cn.wecloud.layim.mvc.mapper.UserInfoMapper;
import cn.wecloud.layim.mvc.service.UserInfoService;
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
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
