package com.langsun.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.system.RoleDao;
import com.langsun.domain.system.Role;
import com.langsun.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * 角色分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByPage(Integer page, Integer size ,String companyId) {
        //1.调用分页插件
        PageHelper.startPage( page , size);
        //2.查询数据
        List<Role> list = roleDao.findAll(companyId);
        //3.返回pageInfo对象
        return new PageInfo(list);
    }

    /**
     * 查询本公司所有的角色信息
     * @return
     */
    @Override
    public List<Role> findAll(String companyId) {
        List<Role> list = roleDao.findAll(companyId);
        return list;
    }

    /**
     * 保存
     * @param role
     */
    @Override
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    /**
     * 修改
     * @param role
     */
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    /**
     * 根据id查询角色数据
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    /**
     * 根据id删除角色数据
     * @param id
     */
    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public List<Role> findByUid(String id) {

        return roleDao.findByUid(id);
    }
}
