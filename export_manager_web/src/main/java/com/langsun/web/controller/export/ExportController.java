package com.langsun.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.Contract;
import com.langsun.domain.cargo.ContractExample;
import com.langsun.domain.export.Export;
import com.langsun.domain.export.ExportExample;
import com.langsun.domain.export.ExportProduct;
import com.langsun.domain.export.ExportProductExample;
import com.langsun.domain.vo.ExportProductVo;
import com.langsun.domain.vo.ExportResult;
import com.langsun.domain.vo.ExportVo;
import com.langsun.service.cargo.ContractService;
import com.langsun.service.export.ExportProductService;
import com.langsun.service.export.ExportService;
import com.langsun.web.controller.base.BaseController;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slang
 * @date 2020-08-20 19:17
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping(value = "/cargo/export")
public class ExportController extends BaseController{

    @Reference
    private ContractService contractService;

    /**
     * 查询已经上报的购销合同数据  state=1
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/contractList" )
    public String contractList(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //查询合同为1 的数据  并且根据用户的企业查询
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractExampleCriteria = contractExample.createCriteria();
        contractExampleCriteria.andStateEqualTo(1);
        contractExampleCriteria.andCompanyIdEqualTo( super.companyId );
        PageInfo pageInfo = contractService.findAll(contractExample, page, size);
        request.setAttribute("page" , pageInfo);
        return "cargo/export/export-contractList";
    }



    @Reference
    private ExportService exportService;
    /**
     *  查询出口报运的数据
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list" )
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //查询contract为1 export的状态不一定是1,相对于contract而已 的数据 -- 即已经报运的 -- 并且根据用户的企业查询
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria exampleCriteria = exportExample.createCriteria();
        exampleCriteria.andCompanyIdEqualTo(super.companyId);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page" , pageInfo);
        return "cargo/export/export-list";
    }


    @RequestMapping("/toExport")
    public String toExport(String id) {
        //可以接受多个id的拼接字符串
        request.setAttribute("id", id);

        return "cargo/export/export-toExport";
    }

    @RequestMapping(value = "/edit")
    public String edit(Export export) {
        export.setCompanyId( super.companyId );
        export.setCompanyName( super.companyName );

        if (StringUtils.isBlank(export.getId())) {
            exportService.save(export);
        } else {
            exportService.update(export);
        }

        return "redirect:list.do";
    }

    @Reference
    private ExportProductService exportProductService;

    @RequestMapping(value = "/toUpdate", name = "ExportUpdate")
    public String update(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);

        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findByExample(exportProductExample);
        System.out.println("货物列表信息>>>>>"+exportProductList);
        request.setAttribute("eps",exportProductList);

        return "/cargo/export/export-update";
    }


    @RequestMapping("/delete")
    public String delete(String id) {
        exportService.delete(id);
        return "redirect:/cargo/export/list.do";
    }


    @RequestMapping("/submit")
    public String submit(String id) {
        Export export = new Export();
        export.setId(id);
        export.setState(1);

        exportService.update(export);
        return "redirect:list.do";
    }

    //电子报运
    // /**
    //     * 出口报运: 电子报运
    //     * 将我们的数据转换成海关需要的数据 并且传输
    //     * 1.根据id查询 报运单对象
    //     * 2.根据id查询到 报运单下货物对象
    //     * 3.将我们的报运单对象转换成海关的vo对象
    //     * 4.将我们的报运单下货物对象 转换成海关的vo对象
    //     * 5.将转换完以后的数据(对象) 传输给海关系统
    //     * 6.再次调用海关的查询接口 查询刚才的操作是否成功
    //     * 7.需要算海关的税务金额 , 修改报运单状态
    //     * @return
    //     */
    @RequestMapping("/exportE")
    public String exportE(String id){
        //     * 1.根据id查询 报运单对象
        Export exportById = exportService.findById(id);
        //     * 2.根据id查询到 报运单下货物对象
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria exportProductExampleCriteria = exportProductExample.createCriteria();
        exportProductExampleCriteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findByExample(exportProductExample);
        //     * 3.将我们的报运单对象转换成海关的vo对象
        ExportVo exportVo = new ExportVo();
        BeanUtils.copyProperties(exportById, exportVo);
        exportVo.setExportId(id);


        ArrayList<ExportProductVo> list = new ArrayList<>();
        //     * 4.将我们的报运单下货物对象 转换成海关的vo对象
        for (ExportProduct exportProduct : exportProductList) {
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            exportProductVo.setExportProductId(exportProduct.getId());
            exportProductVo.setExportId(id);
            list.add(exportProductVo);
        }
        exportVo.setProducts(list);

        //     * 5.将转换完以后的数据(对象) 传输给海关系统
        WebClient webClient = WebClient.create("http://localhost:9090/ws/export/user");
        webClient.post(exportVo);//使用post方式请求给海关
        //6.再次调用海关的查询接口 查询刚才的操作是否成功  http://localhost:9096/ws/export/user/{id}
        webClient = WebClient.create("http://localhost:9090/ws/export/user/"+id);
        ExportResult exportResult = webClient.get(ExportResult.class);
        System.out.println(exportResult);
        //     * 7.需要算海关的税务金额 , 修改报运单状态
        exportService.updateExport(exportResult);
        return "redirect:list.do";
    }

    @RequestMapping(value = "/toView", name = "ExportView")
    public String View(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);

        return "/cargo/export/export-view";
    }

    @RequestMapping(value = "/cancel", name = "ExportView")
    public String cancel(String id) {

        Export export = exportService.findById(id);
        export.setState(0);
        exportService.update(export);

        return "redirect:/cargo/export/list.do";
    }


}
