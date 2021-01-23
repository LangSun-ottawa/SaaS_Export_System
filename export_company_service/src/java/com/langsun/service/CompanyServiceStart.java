package com.langsun.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author slang
 * @date 2020-08-08 20:12
 * @Param $
 * @return $
 **/
public class CompanyServiceStart {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*");
        ac.start();

        System.in.read();
    }

}
