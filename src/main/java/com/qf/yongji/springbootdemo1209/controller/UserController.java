package com.qf.yongji.springbootdemo1209.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    /**
     * 测试spring boot工程的搭建
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello world!";
    }

    /**
     *测试Spring boot整合thymeleaf的测试方法
     * @param model
     * @return
     */
    @RequestMapping("/testThymeleaf")
    public String testThymeleaf(Model model){
        model.addAttribute("name", "hello,thymeleaf");
        return "testThymeleaf";
    }

    /**
     * 创建add页面的访问方法
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "add";
    }

    /**
     * 创建update页面的访问方法
     * @return
     */
    @RequestMapping("/update")
    public String update(){
        return "update";
    }

    /**
     * 在没有认证的情况下访问系统，自动跳转到登录界面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 禁止没有权限的访问页面
     * @return
     */
    @RequestMapping("/refuse")
    public String refuse(){
        return "refuse";
    }


    /**
     * 退出用户登录
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "toLogin";
    }

    @RequestMapping("/login")
    public String login(String username, String password, String rememberMe, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try{
            subject.login(token);
            return "redirect:/testThymeleaf";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "用户名或密码不正确！！！");
            return "login";
//        }catch (IncorrectCredentialsException e){
//            e.printStackTrace();
//            model.addAttribute("msg", "密码不正确");
//            return "login";
        }
    }

}
