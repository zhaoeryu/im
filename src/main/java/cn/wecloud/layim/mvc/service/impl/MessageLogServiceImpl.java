package cn.wecloud.layim.mvc.service.impl;


import cn.study.common.base.BaseServiceImpl;
import cn.study.common.dozer.service.IGenerator;
import cn.wecloud.layim.mvc.domain.entity.MessageLog;
import cn.wecloud.layim.mvc.mapper.MessageLogMapper;
import cn.wecloud.layim.mvc.service.MessageLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-16
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MessageLogServiceImpl extends BaseServiceImpl<MessageLogMapper, MessageLog> implements MessageLogService {

    @Resource
    private IGenerator generator;


}
