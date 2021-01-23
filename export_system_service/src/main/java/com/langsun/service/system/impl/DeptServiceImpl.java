package com.langsun.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.system.DeptDao;
import com.langsun.domain.system.Dept;
import com.langsun.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    @Override
    public PageInfo findByPage(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page, size);
        List<Dept> list = deptDao.findAll(companyId);
//        System.out.println(list);
        return new PageInfo(list);
    }

    @Override
    public void save(Dept dept) {
        String s = UUID.randomUUID().toString();
        dept.setId(s);
        deptDao.save(dept);
    }

    @Override
    public void edit(Dept dept) {
        deptDao.edit(dept);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public void delete(String id) {
        deptDao.delete(id);
    }
}
