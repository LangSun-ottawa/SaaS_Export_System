package com.langsun.web.controller.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyStringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            if (source.indexOf("-") > 0) {
                date = simpleDateFormat.parse(source);
            } else if((source.indexOf("/") > 0))  {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(source);
            }
        } catch (ParseException e) {
            System.out.println("转换日期失败");
            e.printStackTrace();
        }

        return date;
    }
}
