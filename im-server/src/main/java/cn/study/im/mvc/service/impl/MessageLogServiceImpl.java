package cn.study.im.mvc.service.impl;


import cn.study.common.base.BaseServiceImpl;
import cn.study.im.mvc.domain.entity.MessageLog;
import cn.study.im.mvc.mapper.MessageLogMapper;
import cn.study.im.mvc.service.MessageLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-16
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MessageLogServiceImpl extends BaseServiceImpl<MessageLogMapper, MessageLog> implements MessageLogService {


}
