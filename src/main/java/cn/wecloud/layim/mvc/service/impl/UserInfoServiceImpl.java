package cn.wecloud.layim.mvc.service.impl;


import cn.study.common.base.BaseServiceImpl;
import cn.study.common.utils.FileUtil;
import cn.wecloud.layim.mvc.domain.dto.UserInfoDto;
import cn.wecloud.layim.mvc.domain.entity.UserInfo;
import cn.wecloud.layim.mvc.mapper.UserInfoMapper;
import cn.wecloud.layim.mvc.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @Desc :
* @Create : zhaoey ~ 2020-06-14
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public void download(List<UserInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserInfoDto userinfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户名", userinfo.getUsername());
            map.put("在线状态", userinfo.getStatus());
            map.put("签名", userinfo.getSign());
            map.put("用户头像", userinfo.getAvatar());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
