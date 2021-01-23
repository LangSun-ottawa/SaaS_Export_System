package com.langsun.web.controller.base;

import com.langsun.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public HttpSession session;

    @Autowired
    public HttpServletResponse response; //报错不管 代码可以运行 idea的解析问题

    //企业id 和企业名称 以后从登录的用户中获得
    public String companyId ;
    public String companyName;

    public User loginUser;
    protected String deptName;      //部门名称

    /**
     * @ModelAttribute 在执行所有方法之前执行一次
     */
    @ModelAttribute(name = "baseController_before")
    public void before(){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser != null) {
            companyId=loginUser.getCompanyId();
            companyName = loginUser.getCompanyName();
            deptName = loginUser.getDeptName();
            this.loginUser = loginUser;
        }
    }


}
