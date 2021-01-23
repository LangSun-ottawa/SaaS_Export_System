package com.langsun.service.system;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.User;

import java.util.List;

public interface UserService {
    //用户的分页
    PageInfo findByPage(Integer page, Integer size, String companyId);

    //查询所有的用户
    List<User> findAll(String companyId);

    //保存方法
    void save(User user);

    //修改方法
    void update(User user);

    //根据id查询对象信息
    User findById(String id);

    //根据id删除数据
    void delete(String id);

    void changeUserRole(String userid, String roleIds);

    User findByEmail(String email);
}
