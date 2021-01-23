package com.langsun.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.langsun.domain.export.Export;
import com.langsun.domain.export.ExportProduct;
import com.langsun.domain.export.ExportProductExample;
import com.langsun.service.export.ExportProductService;
import com.langsun.service.export.ExportService;
import com.langsun.utils.BeanMapUtils;
import com.langsun.utils.DownloadUtil;
import com.langsun.web.controller.base.BaseController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author slang
 * @date 2020-08-28 22:33
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/export")
public class ExportPdfController extends BaseController {

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    @RequestMapping("/exportPdf")
    public void exportPdf(String id) throws JRException, IOException {
        //1.查找报运单信息
        Export export = exportService.findById(id);
        //2.查找报运单货物信息
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);

        List<ExportProduct> exportProducts = exportProductService.findByExample(exportProductExample);
        Map<String, Object> map = BeanMapUtils.beanToMap(export);


        InputStream resourceAsStream = session.getServletContext().getResourceAsStream("/jasper/export.jasper");
        //填充数据
        //参数一 文件流
        //参数二 map
        //参数三 list数据

        JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, map, new JRBeanCollectionDataSource(exportProducts));
        //下载文件

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        DownloadUtil.download(byteArrayOutputStream, response, "report.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    }

}
