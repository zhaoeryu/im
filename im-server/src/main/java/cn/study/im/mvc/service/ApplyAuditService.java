package cn.study.im.mvc.service;

import cn.study.common.base.IBaseService;
import cn.study.im.model.LayuiResult;
import cn.study.im.mvc.domain.entity.ApplyAudit;
import org.springframework.data.domain.Pageable;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface ApplyAuditService extends IBaseService<ApplyAudit>{

    LayuiResult getMsgList(Pageable pageable);
}
