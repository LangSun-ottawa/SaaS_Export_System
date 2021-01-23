package com.langsun.service.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll(String companyId);

    PageInfo findByPage(Integer page, Integer size, String companyId);

    void save(Dept dept);

    void edit(Dept dept);

    Dept findById(String id);

    void delete(String id);
}
