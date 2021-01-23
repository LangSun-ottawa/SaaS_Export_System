package com.langsun.web.controller.packing;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.langsun.domain.export.Export;
import com.langsun.domain.export.ExportExample;
import com.langsun.domain.export.ExportProduct;
import com.langsun.domain.export.ExportProductExample;
import com.langsun.domain.packing.PackingList;
import com.langsun.domain.packing.PackingListExample;
import com.langsun.service.export.ExportProductService;
import com.langsun.service.export.ExportService;
import com.langsun.service.packing.PackingListService;
import com.langsun.utils.BeanMapUtils;
import com.langsun.utils.DownloadUtil;
import com.langsun.web.controller.base.BaseController;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/cargo/packing")
public class PackingController extends BaseController {
    @Reference
    private PackingListService packingListService;
    @Reference
    private ExportService exportService;
    /**
     * 查看装箱列表
     * @return
     */
    @RequestMapping(value = "/list",name = "查看装箱列表")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")  Integer size){
        PackingListExample packingListExample = new PackingListExample();
        PackingListExample.Criteria criteria = packingListExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        PageInfo pageInfo = packingListService.findAll(packingListExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/packing/packing-list";
    }

    /**
     * 跳转到新增装箱单界面
     * @return
     */
    @RequestMapping(value = "/toAdd",name = "跳转到新增装箱单界面")
    public String toAdd(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")  Integer size){
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        //只能查看到已报运的报运单状态
        criteria.andStateEqualTo(2L);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/packing/packing-add";
    }

    /**
     * 保存或修改装箱
     * @param packingList
     * @param afterExportIds 报运货物id
     * @return
     */
    @RequestMapping(value = "/edit",name = "packing list edit")
    public String edit(PackingList packingList, String afterExportIds) {
        System.out.println("之前的报运单id"+packingList.getExportIds());
        System.out.println("之后的报运单id"+afterExportIds);
        if (StringUtils.isBlank(packingList.getId())) {
            packingList.setCompanyId(super.companyId);
            packingList.setCompanyName(super.companyName);
            packingList.setCreateDept(super.deptName);
            packingList.setCreateBy(super.loginUser.getUserName());
            packingListService.save(packingList);
        } else {
            //修改之前,先把
            packingListService.update(packingList, afterExportIds);
        }
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 跳转到修改装箱单界面
     * @return
     */
    @RequestMapping(value = "/toUpdate",name = "跳转到修改装箱单界面")
    public String toUpdate(String id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5")  Integer size){
        //根据id查找装箱单
        PackingList packingList = packingListService.findById(id);
        request.setAttribute("packingList", packingList);
        //查询当前装箱单下的所有报运单
        String exportIds = packingList.getExportIds();
        List<Export> exports = packingListService.findExports(exportIds);
        request.setAttribute("exports", exports);
        //所有已经装箱的,包括当前单子下的
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andStateBetween(2L,4L);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);

        return "cargo/packing/packing-update";
    }

    @Reference
    private ExportProductService exportProductService;

    @RequestMapping(value = "/download",name = "下载装箱单")
    public void download(String id) throws Exception {
        PackingList packingList = packingListService.findById(id);
//        HashMap<String, Object> map = new HashMap<>();
        Map<String, Object> map = BeanMapUtils.beanToMap(packingList);

        map.put("seller", packingList.getSeller());
        map.put("buyer", packingList.getBuyer());
        map.put("invoiceNo", packingList.getInvoiceNo());
        map.put("invoiceDate", packingList.getInvoiceDate());
        map.put("marks", packingList.getMarks());
        map.put("descriptions", packingList.getDescriptions());

        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();


        List<ExportProduct> exportProducts = new ArrayList<>();
        List<Export> exports = packingListService.findExports(packingList.getExportIds());
        for (Export export : exports) {
            String exportId = export.getId();
            criteria.andExportIdEqualTo(exportId);
            List<ExportProduct> exportProductList = exportProductService.findByExample(exportProductExample);
            exportProducts.addAll(exportProductList);
        }

        //查询当前装箱单下的所有报运单
        InputStream is = session.getServletContext().getResourceAsStream("/jasper/export.jasper");
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(exportProducts);
        //填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, jrBeanCollectionDataSource);
        //下载导出
      // JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
        //打印数据,下载pdf
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,byteArrayOutputStream);
//        response.setContentType("application/octet-stream;charset=utf-8");
//        response.addHeader("Content-Disposition", "attachment;filename=" + new Date().toString() + ".pdf");
        DownloadUtil.download(byteArrayOutputStream,response,"PackingList.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    /**
     * 根据id查找装箱单
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById",name = "根据id查找装箱单")
    @ResponseBody
    public PackingList findByPackingListId(String id) {
        return packingListService.findById(id);
    }

    /**
     * 提交装箱单状态
     * @param id
     * @return
     */
    @RequestMapping(value = "/submit",name = "提交装箱单状态")
    public String submit(String id){
        PackingList packingList = new PackingList();
        packingList.setId(id);
        packingList.setState(1L);
        packingListService.update(packingList);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 提交finance
     * @param id
     * @return
     */
    @RequestMapping(value = "/finance",name = "提交finance状态")
    public String finance(String id){
        PackingList packingList = new PackingList();
        packingList.setId(id);
        packingList.setState(2L);
        packingListService.updateFinance(packingList);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 取消装箱单状态
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancel", name = "取消装箱单状态")
    public String cancel(String id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5")  Integer size){
        PackingList packingList = new PackingList();
        packingList.setId(id);
        packingList.setState(0L);
        packingListService.update(packingList);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 删除装箱单
     * @return
     */
    @RequestMapping(value = "/delete",name = "删除装箱单")
    public String delete(String id){
        packingListService.delete(id);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 跳转到查看装箱单界面
     *
     * @return
     */
    @RequestMapping(value = "/toView", name = "跳转到查看装箱单界面")
    public String toView(String id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5")  Integer size) {
        PackingList packingList = packingListService.findById(id);
        request.setAttribute("packingList",packingList);

        return "/cargo/packing/packing-view";
    }
}
