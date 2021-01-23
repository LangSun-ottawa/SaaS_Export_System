package com.langsun.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author slang
 * @date 2020-08-11 19:01
 * @Param $
 * @return $
 **/
public class StatTest {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        ac.start();
        System.out.println("stat_provider启动完毕");
        System.in.read();
    }

}
