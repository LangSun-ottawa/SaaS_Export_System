package com.langsun.web.controller.print;

import com.alibaba.dubbo.config.annotation.Reference;
import com.langsun.domain.vo.ContractProductVo;
import com.langsun.service.cargo.ContractService;
import com.langsun.utils.DownloadUtil;
import com.langsun.web.controller.base.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author slang
 * @date 2020-08-17 22:59
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/contract")
public class ContractPrintController extends BaseController {
    @Reference
    private ContractService contractService;

    /**
     * 跳转出货表页面
     * @return
     */
    @RequestMapping("/print")
    public String print(){
        return "cargo/print/contract-print";
    }

/**
 * 出货表打印
 * 1.inputDate 船期 - 条件
 * 2.根据船期 查询数据 -> List<临时对象 , 含有八个字段两个表中的> ->List<ContractProductVo>
 * 3.将List转换excel数据
 * 4.下载即可
 * @return
 */
//    @RequestMapping("/printExcel")
//    public void printExcel(String inputDate) throws Exception {//下载 不需要跳转页面
//        //获取数据
//        List<ContractProductVo> contarctProductList = contractService.findByShipTime(inputDate, super.companyId);
//        System.out.println(contarctProductList);
//        //2.1 工作薄
//        Workbook wb = new XSSFWorkbook();
//        //2.2 创建表
//        Sheet sheet = wb.createSheet();
//        //2.3 创建excel的大标题
//        Row row = sheet.createRow(0);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
//        Cell cell = row.createCell(1);
//        //传入的是2015-01
//        inputDate = inputDate.replaceAll("-" , "year"); //将-替换成年
//        cell.setCellValue(inputDate + "Monthly Shipment Table");
//        CellStyle cellStyle = bigTitle(wb);//样式
//        cell.setCellStyle( cellStyle ); //设置样式
//        sheet.setDefaultRowHeightInPoints( 30 );//设置所有的行高
//        //2.4 创建excel的小标题
//        //2.4.1 准备标题的文字
//        String [] headerNames = { "","CustomerName","ContractId","ProductNo","Cnumber","FactoryName","DeliveryPeriod","ShippingTime","TradeTerms"};
//        //{ "","客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
//        //小标题
//        row = sheet.createRow(1);
//        //设置cell的宽度
//        sheet.setColumnWidth(1 , 26 * 256);
//        sheet.setColumnWidth(2 , 15 * 256);
//        sheet.setColumnWidth(3 , 30 * 256);
//        sheet.setColumnWidth(4 , 12 * 256);
//        sheet.setColumnWidth(5 , 17 * 256);
//        sheet.setColumnWidth(6 , 17 * 256);
//        sheet.setColumnWidth(7 , 17 * 256);
//        sheet.setColumnWidth(8 , 17 * 256);
//
//        //循环创建小标题
//        for(int i = 1 ; i <headerNames.length ; i ++){
//            cell =  row.createCell(i);//创建每一个单元格
//            cell.setCellValue(headerNames[i]); //设置值
//            cell.setCellStyle( title(wb) );//设置样式
//        }
//
//        //2.5 构建数据  一行数据一个对象
//        int index = 2;
//        for (ContractProductVo contractProductVo : contarctProductList) {
//            row = sheet.createRow(index);//创建每一行
//            //"客户"
//            cell = row.createCell(1);
//            cell.setCellValue(contractProductVo.getCustomName());
//            cell.setCellStyle( text(wb) );
//            //"订单号"
//            cell = row.createCell(2);
//            cell.setCellValue(contractProductVo.getContractNo());
//            cell.setCellStyle( text(wb) );
//            // "货号"
//            cell = row.createCell(3);
//            cell.setCellValue(contractProductVo.getProductNo());
//            cell.setCellStyle( text(wb) );
//            // "数量"
//            cell = row.createCell(4);
//            cell.setCellValue(contractProductVo.getCnumber());
//            cell.setCellStyle( text(wb) );
//            // "工厂"
//            cell = row.createCell(5);
//            cell.setCellValue(contractProductVo.getFactoryName());
//            cell.setCellStyle( text(wb) );
//            // "工厂交期"
//            cell = row.createCell(6);
//            cell.setCellValue(contractProductVo.getDeliveryPeriod());
//            cell.setCellStyle( text(wb) );
//            // "船期"
//            cell = row.createCell(7);
//            cell.setCellValue(contractProductVo.getShipTime());
//            cell.setCellStyle( text(wb) );
//            // "贸易条款"
//            cell = row.createCell(8);
//            cell.setCellValue(contractProductVo.getTradeTerms());
//            cell.setCellStyle( text(wb) );
//            index++; //模拟i
//        }
//
//
//        //创建输出流
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
//        wb.write(byteArrayOutputStream);
//        DownloadUtil.download(byteArrayOutputStream, response, "export.xlsx");
//    }



    @RequestMapping("/getExcelTemplate")
    public void getExcelTemplate() throws IOException {
        InputStream inputStream = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        Workbook wb = new XSSFWorkbook(inputStream);
        //3.下载 - response
        //创建输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
        //将工作薄转换成字节流
        wb.write(byteArrayOutputStream);
        DownloadUtil.download( byteArrayOutputStream , response , "export-product-template.xlsx");
    }

    /**
     * 模版打印: 模版是提前预定好的
     * 1.读取已经有的模版
     * 2.大标题
     * 3.小标题
     * 4.数据
     * 5.数据的样式-> 没有样式的数据 复制第三行的样式
     * @param inputDate
     * @throws Exception
     */
    //将create换成了get模板中的
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {//下载 不需要跳转页面
        //1.查询数据
        List<ContractProductVo> contarctProductList = contractService.findByShipTime(inputDate, super.companyId);
        //2.转换数据打印
        //读取文件
        //类加载器只能加载src下的内容
        //servletContext对象 , 项目的全文对象 加载webapp下的资源
        InputStream is = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        //2.1 工作薄
        Workbook wb = new XSSFWorkbook(is);

        //2.2 表
        Sheet sheet = wb.getSheetAt(0);
        //2.3 excel的大标题
        Row row = sheet.getRow(0);

        //获得第一个单元格即可
        Cell cell = row.getCell(1);
        //传入的是2015-01
        inputDate = inputDate.replaceAll("-" , " Month "); //将-替换成年
        cell.setCellValue("Year "+inputDate + " Export Table");

        //2.4 创建excel的小标题
        //2.4.1 准备标题的文字
//        String [] headerNames = { "","客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        String [] headerNames = { "CustomerName","ContractId","ProductNo","Cnumber","FactoryName","DeliveryPeriod","ShippingTime","TradeTerms"};

        //小标题
        row = sheet.getRow(1);
        //循环创建小标题
        for(int i = 0 ; i <headerNames.length ; i ++){
            cell =  row.getCell(i+1);//创建每一个单元格
            cell.setCellValue(headerNames[i]); //设置值
        }


        row = sheet.getRow(2);// 获得第三行的模版对象
        short lastCellNum = row.getLastCellNum();
        //构建数据之前 必须得先将表格中的样式复制出来
        CellStyle [] cellStyles = new CellStyle[lastCellNum];//提前设置样式的长度  [ "" , 样式, 样式, 样式, 样式, 样式, 样式, 样式]
        //循环获得样式
        for(int i = 1 ; i < lastCellNum ; i ++ ){
            // i = 0  第一列的样式 第一列没有样式
            //将每一个单元格的样式存入 数组
            cellStyles[i] = row.getCell(i).getCellStyle();
        }


        //2.5 构建数据  一行数据一个对象
        int index = 2 ;
        for (ContractProductVo contractProductVo : contarctProductList) {

            row = sheet.createRow(index);//创建每一行
            //"客户"
            cell = row.createCell(1);
            cell.setCellValue(contractProductVo.getCustomName());
            cell.setCellStyle( cellStyles[1] );//提前设置样式的长度  [ "" , 样式, 样式, 样式, 样式, 样式, 样式, 样式]
            //"订单号"
            cell = row.createCell(2);
            cell.setCellValue(contractProductVo.getContractNo());
            cell.setCellStyle( cellStyles[2] );
            // "货号"
            cell = row.createCell(3);
            cell.setCellValue(contractProductVo.getProductNo());
            cell.setCellStyle( cellStyles[3]  );
            // "数量"
            cell = row.createCell(4);
            cell.setCellValue(contractProductVo.getCnumber());
            cell.setCellStyle( cellStyles[4]  );
            // "工厂"
            cell = row.createCell(5);
            cell.setCellValue(contractProductVo.getFactoryName());
            cell.setCellStyle( cellStyles[5] );
            // "工厂交期"
            cell = row.createCell(6);
            cell.setCellValue(contractProductVo.getDeliveryPeriod());
            cell.setCellStyle( cellStyles[6] );
            // "船期"
            cell = row.createCell(7);
            cell.setCellValue(contractProductVo.getShipTime());
            cell.setCellStyle( cellStyles[7] );
            // "贸易条款"
            cell = row.createCell(8);
            cell.setCellValue(contractProductVo.getTradeTerms());
            cell.setCellStyle( cellStyles[8] );
            index++; //模拟i
        }


        //3.下载 - response
        //创建输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
        //将工作薄转换成字节流
        wb.write(byteArrayOutputStream);
        DownloadUtil.download( byteArrayOutputStream , response , "export-product.xlsx");
    }

/*
* /**
 * 百万数据
 * @param inputDate
 * @throws Exception
 */
/*
@RequestMapping("/printExcel")
public void printExcel(String inputDate) throws Exception {//下载 不需要跳转页面
    //1.查询数据
    List<ContractProductVo> ContractProductVoList = contractService.findByShipTime(inputDate ,super.companyId);
    //2.转换数据打印
    //System.out.println(ContractProductVoList);
    //2.1 工作薄
    Workbook wb = new SXSSFWorkbook();
    //2.2 创建表
    Sheet sheet = wb.createSheet();
    //2.3 创建excel的大标题
    Row row = sheet.createRow(0);
    //单元格合并Merge 合并   Region 区域范围
    //CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol) {
    sheet.addMergedRegion(new CellRangeAddress(0 , 0 ,1 , 8));
    Cell cell = row.createCell(1);
    //传入的是2015-01
    inputDate = inputDate.replaceAll("-" , "年"); //将-替换成年
    cell.setCellValue(inputDate + "月份出货表");

    CellStyle cellStyle = bigTitle(wb);//样式
    cell.setCellStyle( cellStyle ); //设置样式
    sheet.setDefaultRowHeightInPoints( 30 );//设置所有的行高

    //2.4 创建excel的小标题
    //2.4.1 准备标题的文字
    String [] headerNames = { "","客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};

    //小标题
    row = sheet.createRow(1);
    //设置cell的宽度
    sheet.setColumnWidth(1 , 26 * 256);
    sheet.setColumnWidth(2 , 12 * 256);
    sheet.setColumnWidth(3 , 30 * 256);
    sheet.setColumnWidth(4 , 12 * 256);
    sheet.setColumnWidth(5 , 15 * 256);
    sheet.setColumnWidth(6 , 12 * 256);
    sheet.setColumnWidth(7 , 12 * 256);
    sheet.setColumnWidth(8 , 12 * 256);

    //循环创建小标题
    for(int i = 1 ; i <headerNames.length ; i ++){
        cell =  row.createCell(i);//创建每一个单元格
        cell.setCellValue(headerNames[i]); //设置值
        cell.setCellStyle( title(wb) );//设置样式
    }

    //2.5 构建数据  一行数据一个对象
    int index = 2 ;
    for (ContractProductVo contractProductVo : ContractProductVoList) {
        for(int i = 0 ; i < 5000 ; i ++){
            row = sheet.createRow(index);//创建每一行
            //"客户"
            cell = row.createCell(1);
            cell.setCellValue(contractProductVo.getCustomName());
            //cell.setCellStyle( text(wb) );
            //"订单号"
            cell = row.createCell(2);
            cell.setCellValue(contractProductVo.getContractNo());
            //cell.setCellStyle( text(wb) );
            // "货号"
            cell = row.createCell(3);
            cell.setCellValue(contractProductVo.getProductNo());
            //cell.setCellStyle( text(wb) );
            // "数量"
            cell = row.createCell(4);
            cell.setCellValue(contractProductVo.getCnumber());
            //cell.setCellStyle( text(wb) );
            // "工厂"
            cell = row.createCell(5);
            cell.setCellValue(contractProductVo.getFactoryName());
            //cell.setCellStyle( text(wb) );
            // "工厂交期"
            cell = row.createCell(6);
            cell.setCellValue(contractProductVo.getDeliveryPeriod());
            //cell.setCellStyle( text(wb) );
            // "船期"
            cell = row.createCell(7);
            cell.setCellValue(contractProductVo.getShipTime());
            //cell.setCellStyle( text(wb) );
            // "贸易条款"
            cell = row.createCell(8);
            cell.setCellValue(contractProductVo.getTradeTerms());
            //cell.setCellStyle( text(wb) );
            index++; //模拟i
        }
    }


    //3.下载 - response
    DownloadUtil downloadUtil = new DownloadUtil();
    //创建输出流
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
    //将工作薄转换成字节流
    wb.write(byteArrayOutputStream);
    downloadUtil.download( byteArrayOutputStream , response , "出货表.xlsx");
}
**/


    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
