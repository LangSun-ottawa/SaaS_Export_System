package com.langsun.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.langsun.service.stat.StateService;
import com.langsun.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author slang
 * @date 2020-08-27 18:18
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Reference
    private StateService stateService;


    @RequestMapping("/toCharts")
    public String toCharts(String chartsType) {

        return "stat/stat-"+chartsType;
    }

    @RequestMapping("/findFactory")
    @ResponseBody
    public List<Map> findFactory() {
        List<Map> statList = stateService.findFactory(super.companyId);

        return statList;
    }

    @RequestMapping("/findSell")
    @ResponseBody
    public List<Map> findSell() {
        List<Map> statList = stateService.findSell(super.companyId);

        return statList;
    }

    @RequestMapping("/findOnline")
    @ResponseBody
    public List<Map> findOnline() {
        List<Map> statList = stateService.findOnline(super.companyId);

        return statList;
    }
}
