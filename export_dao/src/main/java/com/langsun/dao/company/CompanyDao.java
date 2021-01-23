package com.langsun.dao.company;

import com.langsun.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {
    public List<Company> findAll();

    void save(Company company);

    Company findById(String id);

    void edit(Company company);

    void deleteById(String id);

    long findTotal();

    List findRows(Integer i, Integer size);
}
