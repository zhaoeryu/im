package cn.wecloud.layim.security.logout;


import cn.hutool.core.util.StrUtil;
import cn.study.common.model.ResultBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

    private String signOutUrl;
    private ObjectMapper objectMapper = new ObjectMapper();

    public BrowserLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        log.debug("退出成功");

        // 根据用户是否配置退出页面，来判断退出后进行跳转还是发送JSON
        if (StrUtil.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResultBody.ok().msg("退出成功")));
        } else {
            response.sendRedirect(signOutUrl);
        }
    }
}
