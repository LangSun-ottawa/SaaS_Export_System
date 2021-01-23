package com.langsun.web.controller.company;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.company.Company;
import com.langsun.entity.PageResult;
import com.langsun.service.company.CompanyService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;


    //查询公司数据,展示
    @RequiresPermissions("企业管理")
    @RequestMapping(value = "/list", name = "查询公司数据")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
//        System.out.println("company_controller");
//        int i = 1 / 0;
        //
//        System.out.println(date);
//        List<Company> all = companyService.findAll();
        PageInfo pageInfo = companyService.findByPage(page, size);
        request.setAttribute("page", pageInfo);
//        System.err.println(all);
//        System.out.println(pageInfo);
        return "company/company-list";
    }

    //跳转添加页面
    @RequestMapping(value = "/toAdd",name = "跳转添加页面")
    public String toAdd() {
        return "company/company-add";
    }

//企业添加代码
    @RequestMapping(value = "/edit",name = "企业添加")
    public String edit(Company company) {
        if (StringUtils.isBlank(company.getId())) {
            companyService.save(company);
        } else {
            companyService.edit(company);
        }
        return "redirect:/company/list.do";
    }

    //跳转企业修改页面
    @RequestMapping(value = "/toUpdate", name = "跳转企业修改页面")
    public String toUpdate(String id) {

        Company company = companyService.findById(id);
        request.setAttribute("company",company);
        return "company/company-update";
    }

    //删除选中数据
    @RequestMapping(value = "delete", name = "删除选中数据")
    public String delete(String id) {

        companyService.deleteById(id);

        return "redirect:/company/list.do";
    }

}
