package com.langsun.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Dept;
import com.langsun.service.system.DeptService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;


    @RequestMapping(value = "/list", name="根据公司Id的分页查询")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {

        PageInfo pageInfo = deptService.findByPage(page, size,super.companyId);
        request.setAttribute("page", pageInfo);
        return "system/dept/dept-list";
    }

    @RequestMapping(value = "/toAdd", name = "跳转添加用户界面")
    public String toAdd() {
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList", list);
        return "system/dept/dept-add";
    }

    @RequestMapping(value = "/edit", name = "添加和编辑部门信息")
    public String edit(Dept dept) {

        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if (StringUtils.isBlank(dept.getId())) {
            deptService.save(dept);
        } else {
            deptService.edit(dept);
        }

        return "redirect:list.do";
    }

    @RequestMapping(value = "/toUpdate", name = "编辑部门的跳转页面")
    public String toUpdate(String id) {
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);
        Dept dept = deptService.findById(id);
        request.setAttribute("dept", dept);
        return "system/dept/dept-update";
    }

    @RequestMapping(value = "/delete", name = "删除选中部门数据")
    public String delete(String id) {
        deptService.delete(id);
        return "redirect:list.do";
    }


}
