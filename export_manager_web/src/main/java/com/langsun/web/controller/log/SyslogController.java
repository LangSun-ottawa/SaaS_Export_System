package com.langsun.web.controller.log;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Dept;
import com.langsun.service.log.SyslogService;
import com.langsun.service.system.DeptService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/log")
public class SyslogController extends BaseController {

    @Autowired
    private SyslogService syslogService;


    @RequestMapping(value = "/list", name="根据公司Id的日志分页查询")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {

        PageInfo pageInfo = syslogService.findByPage(page, size,super.companyId);
        request.setAttribute("page", pageInfo);
        return "system/log/log-list";
    }

}
