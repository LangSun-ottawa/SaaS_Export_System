package com.langsun.dao.system;

import com.langsun.domain.system.Dept;

import java.util.List;

public interface DeptDao{

    List<Dept> findAll(String companyId);

//    List<Dept> findByCompanyId(String companyId);

    Dept findById(String id);

    void save(Dept dept);

    void edit(Dept dept);

    void delete(String id);
}
