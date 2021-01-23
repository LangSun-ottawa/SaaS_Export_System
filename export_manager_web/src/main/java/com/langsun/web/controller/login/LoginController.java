package com.langsun.web.controller.login;


import com.langsun.domain.system.Module;
import com.langsun.domain.system.User;
import com.langsun.service.system.ModuleService;
import com.langsun.service.system.UserService;
import com.langsun.utils.Encrypt;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

//    @Autowired
//    private UserService userService;
    @Autowired
    private ModuleService moduleService;

//	@RequestMapping("/login")
//	public String login(String email,String password) {
//        System.out.println("登录模块");
//        //1.校验用户名密码必须是非空状态
//        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
//            return "redirect:/login.jsp";
//        }
//        //2.校验数据库是否含有该用户的数据
//        //2.1 user 不一定有数据
//        //此处不能使用结果视图(逻辑视图)
//        User user = userService.findByEmail(email);
////        System.out.println(user);
//        if (user == null||!user.getEmail().equals(email)) {
//            request.setAttribute("error", "用户名错误");
//            return "forward:login.jsp";
//        }
//
//        String md5Password = Encrypt.md5(password, email);
//
//        if (!user.getPassword().equals(md5Password)) {
//            request.setAttribute("error", "密码错误");
//            return "forward:login.jsp";
//        } else if (user.getEmail().equals(email)&&user.getPassword().equals(md5Password)) {
//            session.setAttribute("loginUser", user);
//
//            List<Module> userModules = moduleService.findByUser(user);
//            session.setAttribute("modules", userModules);
//            return "home/main";
//        }
//            return null;
//    }

    @RequestMapping("/login")
    public String login(String email,String password) {
//        System.out.println("到达login控制器");
        //1.获得用户名密码
        try {
            //2.拿到subject对象
            Subject subject = SecurityUtils.getSubject();
            //3.使用subject进行登录
            //需要构造upToken传入
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email, password);
            //login 自动调用认证方法 , 如果认证通过 自动调用密码比较器方法
            subject.login(usernamePasswordToken);
            //如果认证成功 操作继续 但是 如果认证不成功 底层直接抛出异常 所以需要try catch

            //4.手动获得shiro中的登录数据
            User user = (User) subject.getPrincipal();
            //5.将数据放入session中
            session.setAttribute("loginUser", user);
            //6.操作模块数据(左侧菜单栏)
            List<Module> moduleList = moduleService.findByUser(user);
//            System.out.println(">>>>>>当前菜单列表"+moduleList);
            session.setAttribute("modules", moduleList);
            //跳转主页
            return "home/main";
        } catch (Exception e) {
            //登录失败
            request.setAttribute("error", "wrong username or password");
            return "forward:/login.jsp";
        }
    }

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
//        SecurityUtils.getSubject().logout();   //登出
        return "forward:/login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
