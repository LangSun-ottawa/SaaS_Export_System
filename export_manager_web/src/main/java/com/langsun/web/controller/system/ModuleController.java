package com.langsun.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Module;
import com.langsun.service.system.ModuleService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;
    /**
     * 用户查询
     * @return
     */
    @RequestMapping(value = "/list" , name = "用户查询")
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "5") Integer size){

        //分页的代码
        PageInfo pageInfo =  moduleService.findAll(page , size);
        //存入request对象
        request.setAttribute("page" , pageInfo);
        return "system/module/module-list";
    }

    /**
     * 跳转用户添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd" ,name = "跳转添加用户页面")
    public String toAdd(){
        //查询上级模块的信息
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus" , moduleList);
        return "system/module/module-add";
    }

    /**
     * 添加或者修改
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Module module){
        //module需要 企业id 企业名称  从当前登录的用户得到
        //判断id
        if(StringUtils.isBlank(module.getId())){//没有就是保存
            moduleService.save(module);
        }else{//有就是修改
            moduleService.update(module);
        }
        //修改完后在查询一次
        return "redirect:/system/module/list.do";
    }


    /**
     * 跳转修改的页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" ,name = "跳转修改的页面")
    public String toUpdate(String id){
        //1.根据id查询 当前修改的用户信息数据
        Module module = moduleService.findById(id);
        request.setAttribute("module" , module);

        //2.构造下拉列表的数据
        //查询部门
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus" , moduleList);
        return "/system/module/module-update";
    }

    /**
     * 根据id删除用户数据
     * @return
     */
    @RequestMapping(value = "/delete" ,name = "根据id删除用户数据")
    public String delete(String id ){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }


}
