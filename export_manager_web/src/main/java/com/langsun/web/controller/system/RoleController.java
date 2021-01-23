package com.langsun.web.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Dept;
import com.langsun.domain.system.Module;
import com.langsun.domain.system.Role;
import com.langsun.service.system.DeptService;
import com.langsun.service.system.ModuleService;
import com.langsun.service.system.RoleService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    /**
     * 角色查询
     * @return
     */
    @RequestMapping(value = "/list" , name = "角色查询")
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "5") Integer size){

        PageInfo pageInfo = roleService.findByPage(page, size, super.companyId);
        request.setAttribute("page",pageInfo);
        return "system/role/role-list";
    }



    /**
     * 跳转角色添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd" ,name = "跳转添加角色页面")
    public String toAdd(){

        return "system/role/role-add";
    }

    /**
     * 添加或者修改
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Role role){

        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        //判断id
        if (StringUtils.isBlank(role.getId())) {
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        //修改完后在查询一次
        return "redirect:/system/role/list.do";
    }


    /**
     * 跳转修改的页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" ,name = "跳转修改的页面")
    public String toUpdate(String id){
        //1.根据id查询 当前修改的角色信息数据
        Role role = roleService.findById(id);
        request.setAttribute("role" , role);

        return "/system/role/role-update";
    }

    /**
     * 根据id删除角色数据
     * @return
     */
    @RequestMapping(value = "/delete" ,name = "根据id删除角色数据")
    public String delete(String id ){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    @RequestMapping(value = "/roleModule", name = "跳转分配角色权限页面")
    public String roleModule(String roleid) {
        Role role = roleService.findById(roleid);
        request.setAttribute("role" , role);
        return "system/role/role-module";
    }

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/initTree", name = "返回树形结构")
    @ResponseBody
    public List<Map> initTree(String roleid) throws JsonProcessingException {
        List<Map> treeList =  new ArrayList<>();
//        ObjectMapper objectMapper = new ObjectMapper();
        List<Module> modules = moduleService.findAll(roleid,loginUser.getCompanyId());
//        System.out.println(objectMapper.writeValueAsString(modules));
        List<Module> roleModules = moduleService.findRoleModule(roleid);
//        System.out.println(objectMapper.writeValueAsString(roleModules));
        for (Module module : modules) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", module.getId());
            map.put("name", module.getName());
            map.put("pId", module.getParentId());
            for (Module roleModule : roleModules) {
                if (roleModule.getId().equals(module.getId())) {
                    map.put("checked", true);
                }
            }
            treeList.add(map);
        }
//        System.out.println(objectMapper.writeValueAsString(treeList));

        return treeList;
    }

    @RequestMapping(value = "/updateRoleModule", name = "保存/修改选中 到中间表")
    public String updateRoleModule(String roleid, String moduleIds) {
        moduleService.updateRoleModule(roleid,moduleIds);
        return "redirect:list.do";
    }

}
