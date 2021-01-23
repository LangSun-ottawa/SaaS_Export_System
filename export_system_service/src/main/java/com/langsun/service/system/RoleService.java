package com.langsun.service.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Role;

import java.util.List;

public interface RoleService {
    //角色的分页
    PageInfo findByPage(Integer page, Integer size, String companyId);

    //查询所有的角色
    List<Role> findAll(String companyId);

    //保存方法
    void save(Role role);

    //修改方法
    void update(Role role);

    //根据id查询对象信息
    Role findById(String id);

    //根据id删除数据
    void delete(String id);

    List<Role> findByUid(String id);
}
