package cn.wecloud.layim.mvc.service;

import cn.study.common.base.IBaseService;
import cn.wecloud.layim.layui.model.LayuiResult;
import cn.wecloud.layim.mvc.domain.dto.ApplyAuditDto;
import cn.wecloud.layim.mvc.domain.dto.ApplyAuditQueryCriteria;
import cn.wecloud.layim.mvc.domain.entity.ApplyAudit;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface ApplyAuditService extends IBaseService<ApplyAudit>{

    LayuiResult getMsgList(Pageable pageable);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ApplyAuditQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<ApplyAuditDto>
    */
    List<ApplyAudit> queryAll(ApplyAuditQueryCriteria criteria);


    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<ApplyAuditDto> all, HttpServletResponse response) throws IOException;
}
