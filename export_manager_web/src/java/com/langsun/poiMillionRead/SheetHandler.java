package com.langsun.poiMillionRead;

import com.langsun.domain.vo.ContractProductVo;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * <Description>
 * SheetContentsHandler 接口在XSSFSheetXMLHandler 类中
 * 事件驱动的解析: 一行一行解析 每次只读取一行数据
 */
public class SheetHandler implements  XSSFSheetXMLHandler.SheetContentsHandler {

    private ContractProductVo contractProductVo = null; //临时对象
    @Override
    public void startRow(int rowNum) {// 解析每一行之前执行的方法  rowNum 解析的索引
        if(rowNum>1){//前两行标题不要
            contractProductVo = new ContractProductVo();//创建对象
        }
    }

    @Override
    public void endRow(int rowNum) {// 解析每一行之后执行的方法
        System.out.println(contractProductVo);//打印对象
    }

    /**
     * 解析每一个单元格的方法   这一行有多少单元格就执行多少次
     * 开始封装对象数据
     * @param cellReference     单元格的指向(列名)  B1 B2 B3 B4  B100... 用来确定每一个数据的位置  B开头的就是 客户 C开头的就是订单号
     * @param formattedValue    单元格中的值部分
     * @param comment            单元格的注释
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if(contractProductVo!=null){//不是前两行
            //确定是哪一列
            String columnName = cellReference.substring(0, 1); //截取字符串 获得列的名称
            //根据已经得到的列 知道每一列的位置 属性
            switch (columnName){
                case "B":
                    contractProductVo.setCustomName( formattedValue );
                    break;
                case "C":
                    contractProductVo.setContractNo( formattedValue );
                    break;
                case "D":
                    break;
                case "E":
                    break;
                case "F":
                    break;
                case "G":
                    break;
                case "H":
                    break;
            }
        }
    }
}
