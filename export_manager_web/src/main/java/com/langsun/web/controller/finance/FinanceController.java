package com.langsun.web.controller.finance;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.ContractProduct;
import com.langsun.domain.cargo.ContractProductExample;
import com.langsun.domain.cargo.Factory;
import com.langsun.domain.cargo.FactoryExample;
import com.langsun.domain.finance.Finance;
import com.langsun.domain.finance.FinanceExample;
import com.langsun.service.cargo.ContractProductService;
import com.langsun.service.cargo.FactoryService;
import com.langsun.service.finance.FinanceService;
import com.langsun.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author slang
 * @date 2020-09-10 1:51
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {
    @Reference
    private FinanceService financeService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size) {
        FinanceExample financeExample = new FinanceExample();
        FinanceExample.Criteria criteria = financeExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        PageInfo pageInfo = financeService.findAll(financeExample, page, size);
        request.setAttribute("page", pageInfo);

        return "finance/finance-list";
    }

    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;

    @RequestMapping("/factoryList")
    public String factoryList(String financeId) {
        Finance finance = financeService.findById(financeId);
//        System.out.println(finance);
        request.setAttribute("finance",finance);

        String contractIds = finance.getContractIds();
        List<ContractProduct> list = new ArrayList<>();
        ArrayList<Factory> factories = new ArrayList<>();


//        System.out.println("contractIds>>>>"+contractIds);
        if (contractIds.contains(",")) {
            String[] split = contractIds.split(",");
            request.setAttribute("contractId",split);
            for (String contractId : split) {
                ContractProductExample contractProductExample = new ContractProductExample();
                ContractProductExample.Criteria criteria = contractProductExample.createCriteria();
                criteria.andContractIdEqualTo(contractId);
                List<ContractProduct> contractProducts = contractProductService.findAll(contractProductExample);
                list.addAll(contractProducts);
            }
//            System.out.println(">>>>>>>list<<<<<<<");
//            System.out.println(list);
            for (ContractProduct contractProduct : list) {
                String factoryName = contractProduct.getFactoryName();
                FactoryExample factoryExample = new FactoryExample();
                factoryExample.setDistinct(true);
                FactoryExample.Criteria criteria = factoryExample.createCriteria();
                criteria.andFactoryNameEqualTo(factoryName);
                criteria.andStateEqualTo("1");
                criteria.andCompanyIdEqualTo(super.companyId);
                List<Factory> factoriesList = factoryService.findAll(factoryExample);
                factories.addAll(factoriesList);
                //需要构建一个对应的contract - factory对应类
            }
        } else {
            ContractProductExample contractProductExample = new ContractProductExample();
            ContractProductExample.Criteria criteria = contractProductExample.createCriteria();
            criteria.andContractIdEqualTo(contractIds);
            List<ContractProduct> contractProducts = contractProductService.findAll(contractProductExample);
            for (ContractProduct contractProduct : contractProducts) {
                String factoryName = contractProduct.getFactoryName();
                FactoryExample factoryExample1 = new FactoryExample();
                factoryExample1.setDistinct(true);
                FactoryExample.Criteria criteria1 = factoryExample1.createCriteria();
                criteria1.andStateEqualTo("1");
                criteria1.andCompanyIdEqualTo(super.companyId);
                criteria1.andFactoryNameEqualTo(factoryName);
                List<Factory> factories1 = factoryService.findAll(factoryExample1);
                factories.addAll(factories1);
            }
        }
//        System.out.println(">>>>>factories"+factories);
        request.setAttribute("factories", factories);

        return "finance/financeFactory";
    }


}
