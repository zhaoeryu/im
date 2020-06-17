package cn.wecloud.layim.security;

import cn.wecloud.layim.constants.BaseConstants;
import cn.wecloud.layim.mvc.domain.entity.UserInfo;
import cn.wecloud.layim.mvc.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Desc :
 * @Create : zhaoey ~ 2019/12/12
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, username).last("limit 1"));
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return new LayimUser(user.getId(),user.getUsername(),passwordEncoder.encode(BaseConstants.DEFAULT_PASSWORD),user.getAvatar());
    }

}
