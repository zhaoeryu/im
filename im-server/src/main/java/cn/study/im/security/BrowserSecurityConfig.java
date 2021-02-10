package cn.study.im.security;


import cn.study.common.utils.SpringContextHolder;
import cn.study.im.security.annotation.AnonymousAccess;
import cn.study.im.security.logout.BrowserLogoutSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    /**
     * 退出成功处理器
     */
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 设置项目启动的时候创建保存记住我的那张表
//         tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置和用户名密码登录相关的配置
        http.formLogin()
                .loginPage("/login")
                .successForwardUrl("/index");
        http

                // 记住我的配置
                .rememberMe()
                .rememberMeParameter("rememberMe")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(30 * 24 * 60 * 60)
                // 指定获取UserDetails的类
                .userDetailsService(userDetailsService)

                // 退出登录相关的配置
                .and()
                .logout()
                // 配置处理退出登录的URL
                .logoutUrl("/logout")
                // 下面两个logoutSuccessUrl和logoutSuccessHandler是互斥的，配置一个后另一个就会失效
                // 配置退出 登录后跳转的URL
                // .logoutSuccessUrl("/logout.html")
                .logoutSuccessHandler(logoutSuccessHandler)
                // 删除浏览器中Cookie
                .deleteCookies("JSESSIONID")

                // 允许iframe嵌套
                .and().headers().frameOptions().disable()

                .and()
                // 关闭跨域防护伪造
                .csrf().disable();

        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = SpringContextHolder.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();

        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            RequestMappingInfo requestMappingInfo = infoEntry.getKey();

            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                Set<String> strings = infoEntry.getKey()
                        .getPatternsCondition().getPatterns();
                if(strings.size() == 1){
                    String[] arr = strings.toArray((new String[0]));
                    if(requestMappingInfo.getMethodsCondition().getMethods().contains(RequestMethod.GET)){
                        String newUrl = arr[0]+"/**";
                        List<String> list = Collections.singletonList(newUrl);
                        Set<String> sSet = new HashSet<>(list);
                        anonymousUrls.addAll(sSet);
                        continue;
                    }
                }
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }

        // 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
        http.authorizeRequests()
                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                .antMatchers(
                        "/**/*.ico",
                        "/css/**",
                        "/js/**",
                        "/json/**",
                        "/layui/**",
                        "/layui.min/**",

                        // 代码生成
                        "/generator/**"
                ).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 退出登录成功处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new BrowserLogoutSuccessHandler("/login");
    }
}
