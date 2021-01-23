package com.langsun.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.Contract;
import com.langsun.domain.cargo.ContractExample;
import com.langsun.domain.system.User;
import com.langsun.service.cargo.ContractProductService;
import com.langsun.service.cargo.ContractService;
import com.langsun.service.cargo.ExtCproductService;
import com.langsun.web.controller.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author slang
 * @date 2020-08-11 21:38
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {

        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        //基础的标准,除了companyid什么都不加的是总公司企业管理员
        criteria.andCompanyIdEqualTo(super.companyId);
        contractExample.setOrderByClause("create_time desc");
        //根据权限登记查询
//        System.out.println(loginUser);
        if (loginUser.getDegree() == 4) {//普通员工 只能看自己的数据,根据自己的id查询数据库
            criteria.andCreateByEqualTo(loginUser.getId());
        } else if (loginUser.getDegree() == 3) {//部门经理 查看本部门下所有的数据
            criteria.andCreateDeptEqualTo(loginUser.getDeptId());
        } else if (loginUser.getDegree() == 2) {
            criteria.andCreateDeptLike(loginUser.getDeptId() + "%");
        }

        PageInfo pageInfo = contractService.findAll(contractExample, page, size);
        request.setAttribute("page", pageInfo);

        return "cargo/contract/contract-list";
    }


    @RequestMapping("/toAdd")
    public String toAdd() {

        return "cargo/contract/contract-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);

        return "cargo/contract/contract-update";
    }

    @RequestMapping("/edit")
    public String edit(Contract contract) {

        contract.setCompanyId(super.companyId);
        contract.setCompanyName(super.companyName);

        if (StringUtils.isBlank(contract.getId())) {
            contract.setCreateBy(loginUser.getId()); //创建的人
            contract.setCreateDept(loginUser.getDeptId()); //创建的部门
            //时间在service里创建了
            contractService.save(contract);
        } else {
            contract.setUpdateBy(loginUser.getId()); //创建的人
            //contract.setCreateDept(loginUser.getDeptId()); //创建的部门 好像不用,因为如果总公司改完数据后,意味着区域看不到了
            contract.setUpdateTime(new Date());
            contractService.update(contract);
        }

        return "redirect:list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);
        contractService.update(contract);
        return "redirect:list.do";
    }



    @RequestMapping("/delete")
    public String delete(String id) {

        contractService.delete(id);

        return "redirect:list.do";
    }

    @RequestMapping("/toView")
    public String toView(String id) {
        Contract contract = contractService.findById(id);
        System.out.println(contract);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }


}
