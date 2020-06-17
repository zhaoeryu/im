package cn.wecloud.layim.mvc.controller;

import cn.wecloud.layim.security.annotation.AnonymousAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@Controller
public class IndexController {

    @AnonymousAccess
    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView();

        mv.setViewName("register");
        return mv;
    }

    @AnonymousAccess
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();

        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();

        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/mobile")
    public ModelAndView mobile(){
        ModelAndView mv = new ModelAndView();

        mv.setViewName("mobile");
        return mv;
    }
}
