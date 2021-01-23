package com.langsun.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Dept;
import com.langsun.domain.system.Role;
import com.langsun.domain.system.User;
import com.langsun.service.system.DeptService;
import com.langsun.service.system.RoleService;
import com.langsun.service.system.UserService;
import com.langsun.web.controller.base.BaseController;
import com.langsun.web.controller.producer.MQProducer;
import com.langsun.web.controller.util.FileUploadUtil;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    /**
     * 用户查询
     * @return
     */
    @RequestMapping(value = "/list" , name = "用户查询")
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "5") Integer size){
        //作业:用户查询必须根据企业查询用户
        //分页的代码
        PageInfo pageInfo =  userService.findByPage(page , size , super.companyId );
        //存入request对象
        request.setAttribute("page" , pageInfo);
        return "system/user/user-list";
    }

    @Autowired
    private DeptService deptService;
    /**
     * 跳转用户添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd" ,name = "跳转添加用户页面")
    public String toAdd(){
        //查询部门
        List<Dept> deptList = deptService.findAll(super.companyId);
        request.setAttribute("deptList" , deptList);
        return "system/user/user-add";
    }

    @Autowired
    private MQProducer mqProducer;
    /**
     * 添加或者修改
     * @return
     */
    @RequestMapping("/edit")
    public String edit(User user){
        //user需要 企业id 企业名称  从当前登录的用户得到
        //判断id
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if(StringUtils.isBlank(user.getId())){//没有就是保存
            String to = user.getEmail();
            String subject = "Congratulations, welcome to join Export_SAAS platform ";
            String content = "Your password is: " + user.getPassword();
            Map map = new HashMap();
            map.put("to", to);
            map.put("subject", subject);
            map.put("content", content);
            mqProducer.sendDataToQueue("mail.send", map);
            userService.save(user);
        }else{//有就是修改
            String to = user.getEmail();
            String subject = "Export_SAAS platform, You have changed your password";
            String content = "Your new password is: " + user.getPassword();
            Map map = new HashMap();
            map.put("to", to);
            map.put("subject", subject);
            map.put("content", content);
            mqProducer.sendDataToQueue("mail.send", map);
            userService.update(user);
        }
        //修改完后在查询一次
        return "redirect:/system/user/list.do";
    }


    /**
     * 跳转修改的页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" ,name = "跳转修改的页面")
    public String toUpdate(String id){
        //1.根据id查询 当前修改的用户信息数据
        User user = userService.findById(id);
        request.setAttribute("user" , user);

        //2.构造下拉列表的数据
        //查询部门
        List<Dept> deptList = deptService.findAll(super.companyId);
        request.setAttribute("deptList" , deptList);
        return "/system/user/user-update";
    }

    /**
     * 根据id删除用户数据
     * @return
     */
    @RequestMapping(value = "/delete" ,name = "根据id删除用户数据")
    public String delete(String id ){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    /**
     * 用户角色分配跳转页面
     *
     */
    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "roleList", name = "用户角色分配跳转页面")
    public String roleList(String id) {
        String userRoleStr = "";
        User user = userService.findById(id);
        request.setAttribute("user", user);

        List<Role> roleList = roleService.findAll(super.companyId);
        request.setAttribute("roleList", roleList);
        List<Role> userRole = roleService.findByUid(id);
        for (Role role : userRole) {
            userRoleStr += role.getId() + ",";
        }
//        userRoleStr = userRoleStr.substring(0, userRoleStr.length() - 1);
//        System.out.println(userRoleStr);
        request.setAttribute("userRoleStr", userRoleStr);
        return "/system/user/user-role";
    }

    /**
     * 更新用户角色
     * userid: 002108e2-9a10-4510-9683-8d8fd1d374ef
     * roleIds: 4028a1c34ec2e5c8014ec2ebf8430001
     * roleIds: 4028a1c34ec2e5c8014ec2ec38cc0002
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0000
     */
    @RequestMapping(value = "changeRole", name = "更新用户角色")
    public String changeRole(String userid, String roleIds) {
//        System.out.println(userid);
//        System.out.println(roleIds);
        userService.changeUserRole(userid, roleIds);
        return "redirect:list.do";
    }

    @RequestMapping(value = "personal", name = "personal information")
    public String personal() {
        String id = super.loginUser.getId();
        User user = userService.findById(id);
        request.setAttribute("personal", user);
        return "personInfo/personInfo";
    }


    @Autowired
    private FileUploadUtil fileUploadUtil;

    @RequestMapping(value = "personalUpdate", name = "personal information update")
    public String personalUpdate(User user, MultipartFile photo) throws Exception {
        if (!photo.isEmpty()) {
            String imgPath = fileUploadUtil.upload(photo);
            System.out.println(imgPath);
            user.setImg(imgPath);
        }
        userService.update(user);

        return "forward:/login.jsp";
    }

}
