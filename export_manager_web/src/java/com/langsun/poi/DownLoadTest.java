package com.langsun.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author slang
 * @date 2020-08-17 11:51
 * @Param $
 * @return $
 **/
public class DownLoadTest {
    public static void main(String[] args) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        cell.setCellValue("langsun_test");

        FileOutputStream fileOutputStream = new FileOutputStream("D://poi//downTest.xlsx");
        workbook.write(fileOutputStream);
    }
}
