package com.langsun.service.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Module;
import com.langsun.domain.system.User;

import java.util.List;

public interface ModuleService {
    //用户的分页
    PageInfo findAll(Integer page, Integer size);

    //查询所有的用户 42 77 需要companyid
    List<Module> findAll();

    //查询除SAAS以外的所有的用户  rid没用到,需要companyid
    List<Module> findAll(String rId,String companyId);

    //保存方法
    void save(Module module);

    //修改方法
    void update(Module module);

    //根据id查询对象信息
    Module findById(String id);

    //根据id删除数据
    void delete(String id);

    List<Module> findRoleModule(String roleid);

    void updateRoleModule(String roleid, String moduleIds);

    List<Module> findByUser(User user);
}
