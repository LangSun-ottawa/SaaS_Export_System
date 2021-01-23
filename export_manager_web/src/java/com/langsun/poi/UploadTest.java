package com.langsun.poi;

import com.langsun.domain.cargo.Contract;
import com.langsun.domain.cargo.ContractProduct;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;


/**
 * @author slang
 * @date 2020-08-17 12:24
 * @Param $
 * @return $
 **/
public class UploadTest {
    public static void main(String[] args) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook("D://poi//demo.xlsx");

        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();

//        System.out.println(CellType.STRING);
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    Object o = checkValue(cell);
                    System.out.println(o);
                }
            }
        }
    }

    public static Object checkValue(Cell cell) {
        Object o = null;
        if (cell.getCellType().equals(CellType.STRING)) {
            o = cell.getStringCellValue();
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            if (DateUtil.isCellDateFormatted(cell)) {
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
