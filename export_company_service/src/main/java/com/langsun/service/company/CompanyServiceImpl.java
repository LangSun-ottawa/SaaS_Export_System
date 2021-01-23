package com.langsun.service.company;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.company.CompanyDao;
import com.langsun.domain.company.Company;
import com.langsun.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;


    @Override
    public PageInfo findByPage(Integer page, Integer size) {

        PageHelper.startPage(page, size);
        List<Company> list = companyDao.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo findAll() {
        return null;
    }

    @Override
    public void save(Company company) {

        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);

    }

    @Override
    public Company findById(String id) {
        Company company = companyDao.findById(id);
        return company;
    }

    @Override
    public void edit(Company company) {
        companyDao.edit(company);
    }

    @Override
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    @Override
    public PageResult findPage(Integer page, Integer size) {
        System.out.println("service");
        long total = companyDao.findTotal();
        System.out.println(total);
        List rows = companyDao.findRows((page - 1) * size, size);
        System.out.println(rows);
        PageResult pageResult = new PageResult(total,rows,page,size);

        return pageResult;
    }
}
