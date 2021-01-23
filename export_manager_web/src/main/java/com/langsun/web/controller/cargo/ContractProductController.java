package com.langsun.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.*;
import com.langsun.service.cargo.ContractProductService;
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
 * @date 2020-08-12 18:25
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(ContractProduct contractProduct, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
//        System.out.println(">>>>>ContractId: "+contractProduct.getContractId());
        //1.查询购销合同下的货物数据 contractId 合同id 需要分页
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria productExampleCriteria = contractProductExample.createCriteria();
        productExampleCriteria.andContractIdEqualTo(contractProduct.getContractId());
        PageInfo pageInfo = contractProductService.findAll(contractProductExample, page, size);
        request.setAttribute("page",pageInfo);

        //2.查询添加列表中 生产厂家的 下拉列表数据 但是 注意:只能查询生产货物的厂家 不需要分页
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("货物");
        factoryExampleCriteria.andStateEqualTo("1");
        factoryExampleCriteria.andCompanyIdEqualTo(super.companyId);
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);

        //3.存入购销合同id  页面的保存功能 和其他功能需要使用
        request.setAttribute("contractId",contractProduct.getContractId());

        return "cargo/product/product-list";
    }



    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //查询货物回显
        ContractProduct contractProductListById = contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProductListById);
        //2.查询添加列表中 生产厂家的 下拉列表数据 但是 注意:只能查询生产货物的厂家,不查生产配件的厂家 不需要分页
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("货物");
        factoryExampleCriteria.andStateEqualTo("1");
        factoryExampleCriteria.andCompanyIdEqualTo(super.companyId);
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);
        return "cargo/product/product-update";
    }

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        System.out.println(productPhoto);
        contractProduct.setCompanyId(super.companyId);
        contractProduct.setCompanyName(super.companyName);
        if (!productPhoto.isEmpty()) {
            String imgPath = fileUploadUtil.upload(productPhoto);
            System.out.println(imgPath);
            contractProduct.setProductImage(imgPath);
        }
        if (StringUtils.isBlank(contractProduct.getId())) {
            contractProductService.save(contractProduct);
        } else {
//            System.out.println("controller的update环节");
            contractProductService.update(contractProduct);
        }

        return "redirect:list.do?contractId=" + contractProduct.getContractId();
    }

    @RequestMapping("/delete")
    public String delete(String id,String contractId) {
        contractProductService.delete(id);
        return "redirect:list.do?contractId=" + contractId;
    }


}
