package com.langsun;


import com.langsun.dao.company.CompanyDao;
import com.langsun.domain.company.Company;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestDemo {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
        CompanyDao bean = classPathXmlApplicationContext.getBean(CompanyDao.class);
        List<Company> all = bean.findAll();
        System.out.println(all);
    }

}
