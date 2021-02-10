package cn.study.im.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/16
 */
public class SecurityHelper {

    public static LayimUser getUser(){
        return (LayimUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserId(){
        return getUser().getId();
    }

    public static String getUsername(){
        return getUser().getUsername();
    }

}
