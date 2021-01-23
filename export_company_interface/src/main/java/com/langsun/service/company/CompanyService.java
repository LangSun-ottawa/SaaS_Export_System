package com.langsun.service.company;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.company.Company;
import com.langsun.entity.PageResult;

public interface CompanyService {

    public PageInfo findAll();

    void save(Company company);

    Company findById(String id);

    void edit(Company company);

    void deleteById(String id);

    PageResult findPage(Integer page, Integer size);

    PageInfo findByPage(Integer page, Integer size);
}
