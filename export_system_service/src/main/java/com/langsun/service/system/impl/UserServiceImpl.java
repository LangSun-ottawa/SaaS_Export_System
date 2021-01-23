package com.langsun.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.system.UserDao;
import com.langsun.domain.system.User;
import com.langsun.service.system.UserService;
import com.langsun.utils.Encrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByPage(Integer page, Integer size , String companyId) {
        //1.调用分页插件
        PageHelper.startPage( page , size);
        //2.查询数据
        List<User> list = userDao.findAll(companyId);
        //3.返回pageInfo对象
        return new PageInfo(list);
    }

    /**
     * 查询所有的用户信息
     * @return
     */
    @Override
    public List<User> findAll(String companyId) {
        List<User> list = userDao.findAll(companyId);
        return list;
    }

    /**
     * 保存
     * @param user
     */
    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        String password = user.getPassword();
        String userName = user.getEmail();
        String md5Password = Encrypt.md5(password, userName);
        user.setPassword(md5Password);
        userDao.insertSelective(user);
    }

    /**
     * 修改
     * @param user
     */
    @Override
    public void update(User user) {
        String password = user.getPassword();
        System.out.println(password);
//        System.out.println(password==null);
        String userName = user.getEmail();
        String md5Password = Encrypt.md5(password, userName);
        if (StringUtils.isBlank(user.getPassword())) {
            User userById = userDao.findById(user.getId());
            md5Password = userById.getPassword();
        }
        System.out.println(md5Password);
        user.setPassword(md5Password);
        userDao.updateByPrimaryKeySelective(user);
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    /**
     * 根据id删除用户数据
     * @param id
     */
    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public void changeUserRole(String userid, String roleIds) {
        userDao.deleteUserRole(userid);
        String[] splitRoleIds = roleIds.split(",");
        for (String splitRoleId : splitRoleIds) {
            userDao.saveUserRoleId(userid, splitRoleId);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = userDao.findByEmail(email);
        return user;
    }
}
