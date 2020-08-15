package cn.wecloud.layim.mvc.service;

import cn.study.common.base.IBaseService;
import cn.wecloud.layim.mvc.domain.dto.UserInfoDto;
import cn.wecloud.layim.mvc.domain.entity.UserInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
public interface UserInfoService extends IBaseService<UserInfo>{

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<UserInfoDto> all, HttpServletResponse response) throws IOException;
}
