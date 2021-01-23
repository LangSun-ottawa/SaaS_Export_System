package com.langsun.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.*;
import com.langsun.service.cargo.ContractService;
import com.langsun.service.cargo.ExtCproductService;
import com.langsun.service.cargo.FactoryService;
import com.langsun.web.controller.base.BaseController;
import com.langsun.web.controller.util.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author slang
 * @date 2020-08-16 0:42
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @RequestMapping("/list")
    public String list(String contractId , String contractProductId, @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10") Integer size) {

        //* 分页查询附件
        //* 1.根据货物id查询
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCriteria = extCproductExample.createCriteria();
        extCriteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo = extCproductService.findAll(extCproductExample, page, size);
        request.setAttribute("page", pageInfo);
        System.out.println(pageInfo);
        //2.查询工厂列表信息
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("附件");
        factoryExampleCriteria.andStateEqualTo("1");
        factoryExampleCriteria.andCompanyIdEqualTo(super.companyId);
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);
        //3.存入货物id
        request.setAttribute("contractId" ,contractId );
        //存入合同id
        request.setAttribute("contractProductId" ,contractProductId );
        return "cargo/extc/extc-list";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id , String contractId , String contractProductId ){
        //1.附件的对象
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct" , extCproduct);

        //2.生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("附件");
        factoryExampleCriteria.andStateEqualTo("1");
        factoryExampleCriteria.andCompanyIdEqualTo(super.companyId);

        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);
        //赋值合同对象

        request.setAttribute("contractId" , contractId);
        request.setAttribute("contractProductId" , contractProductId);

        return "cargo/extc/extc-update";
    }

    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct, MultipartFile productPhoto) throws Exception {
        extCproduct.setCompanyId(super.companyId);
        extCproduct.setCompanyName(super.companyName);
        if (!productPhoto.isEmpty()) {
            String imgPath = fileUploadUtil.upload(productPhoto);
            System.out.println(imgPath);
            extCproduct.setProductImage(imgPath);
        }
        String id = extCproduct.getId();
        if (StringUtils.isBlank(id)) {
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?contractId="+extCproduct.getContractId()+"&contractProductId="+extCproduct.getContractProductId();
    }

    @RequestMapping("/delete")
    public String delete(String id , String contractId , String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }



}
