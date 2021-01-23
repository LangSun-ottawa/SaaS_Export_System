package com.langsun.web.controller.print;

import com.alibaba.dubbo.config.annotation.Reference;
import com.langsun.domain.cargo.ContractProduct;
import com.langsun.service.cargo.ContractProductService;
import com.langsun.web.controller.base.BaseController;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author slang
 * @date 2020-08-17 23:00
 * @Param $
 * @return $
 **/
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductImportController extends BaseController {

    @Reference
    private ContractProductService contractProductService;
    /**
     * 跳转上传货物的页面
     * @return
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId){
        request.setAttribute("contractId" , contractId);
        return "cargo/product/product-import";
    }

    /**
     * 上传货物
     * 1.上传的数据
     * 2.解析excel
     * 3.批量保存货物   save(List<contractProduct>)
     * 3.1 将excel的数据转换成 List<contractProduct>
     * 3.2 批量保存 list数据即可
     * @return
     */

    @RequestMapping("/import")
    public String importProduct(String contractId , MultipartFile file ) throws Exception {
        List<ContractProduct> list = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        //看看能不能遍历所有sheet
        int numberOfSheets = workbook.getNumberOfSheets();
//        System.out.println(numberOfSheets);  result=1 可以遍历
        for (int k = 0; k < numberOfSheets; k++) {
            System.out.println(">>>"+k);
            XSSFSheet sheet = workbook.getSheetAt(k);
            int lastRowNum = sheet.getLastRowNum();
            //row的0行是 序号, 姓名, 年龄, 家庭住址, 出生日期
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                Object[] params = new Object[10];

                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    if (cell != null) {
                        params[j] = checkValue(cell);
                    }
                }
                System.out.println(Arrays.toString(params));
                ContractProduct contractProduct = new ContractProduct(params, super.companyId, super.companyName, contractId);
                list.add(contractProduct);
            }
        }
        contractProductService.saveList(list);
        return "redirect:/cargo/contract/list.do";
    }

    public static Object checkValue(Cell cell) {
        Object o = null;
        if (cell.getCellType().equals(CellType.STRING)) {
            o = cell.getStringCellValue();
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {//在excel中 数字类型 和 日期类型 类型一致 , 在java中不一致
            if (DateUtil.isCellDateFormatted(cell)) {//判断当前单元格是不是日期类型
                o = cell.getDateCellValue();
            } else {
                o = cell.getNumericCellValue();
            }
        } else if (cell.getCellType().equals(CellType.BOOLEAN)) {
            o = cell.getBooleanCellValue();
        }

        return o;
    }



}
