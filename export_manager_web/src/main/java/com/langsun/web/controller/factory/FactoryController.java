package com.langsun.web.controller.factory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.Factory;
import com.langsun.domain.cargo.FactoryExample;
import com.langsun.service.cargo.FactoryService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author slang
 * @date 2020-09-12 0:34
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/baseinfo/factory")
public class FactoryController extends BaseController {

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria = factoryExample.createCriteria();
        criteria.andStateEqualTo("1");
        criteria.andCompanyIdEqualTo(super.companyId);

        PageInfo pageInfo = factoryService.findAll(factoryExample, page, size);
        request.setAttribute("page", pageInfo);
        return "factory/factory-list";
    }


    @RequestMapping("/toAdd")
    public String toAdd() {

        return "factory/factory-add";
    }

    @RequestMapping("/edit")
    public String edit(Factory factory) {
        factory.setCompanyId(super.companyId);
        if (StringUtils.isBlank(factory.getId())) {
            factoryService.save(factory);
        } else {
            factoryService.update(factory);
        }
        return "redirect:/baseinfo/factory/list.do";
    }


    //跳转到添加页面
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //根据id查询回显
        Factory factory = factoryService.findById(id);
        request.setAttribute("coFactory", factory);
        return "factory/factory-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        factoryService.delete(id);
        return "redirect:/baseinfo/factory/list.do";
    }
}
