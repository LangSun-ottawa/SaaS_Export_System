package com.langsun.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.system.ModuleDao;
import com.langsun.domain.system.Module;
import com.langsun.domain.system.User;
import com.langsun.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    /**
     * 用户分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findAll(Integer page, Integer size) {
        //1.调用分页插件
        PageHelper.startPage(page, size);
        //2.查询数据
        List<Module> list = moduleDao.findAll();
        //3.返回pageInfo对象
        return new PageInfo(list);
    }

    /**
     * 查询除SAAS以外的用户信息
     * @return
     */
    @Override
    public List<Module> findAll(String rid,String companyId) {
        List<Module> list = moduleDao.findAllExcptSaas(companyId);
        return list;
    }

    /**
     * 查询所有的用户信息
     * @return
     */
    @Override
    public List<Module> findAll() {
        List<Module> list = moduleDao.findAll();
        return list;
    }

    /**
     * 保存
     * @param module
     */
    @Override
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    /**
     * 修改
     * @param module
     */
    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return
     */
    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    /**
     * 根据id删除用户数据
     * @param id
     */
    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public List<Module> findRoleModule(String roleid) {
        return moduleDao.findByRoleId(roleid);
    }

    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        moduleDao.deleteRoleModule(roleid);

        if (moduleIds.length() > 0) {
            String[] splitModuleIds = moduleIds.split(",");
            for (String splitModuleId : splitModuleIds) {
                moduleDao.saveRoleModule(roleid, splitModuleId);
            }
        }

    }


    /**
     * 根据用户的信息得到 当前用户的模块信息
     * /**
     *      用户数据
     *      private Integer degree;
     *      * 0作为内部控制，租户企业不能使用
     *      *      0-saas管理员
     *      *      1-企业管理员
     *      *      2-管理所有下属部门和人员
     *      *      3-管理本部门
     *      *      4-普通员工
     *
     *      模块表的数据
     *      private String belong;
     *      * 从属关系
     *      *  0：sass系统内部菜单
     *      *  1：租用企业菜单
     *
     *
     * @param user
     * @return
     */
    @Override
    public List<Module> findByUser(User user) {
        List<Module> moduleList = new ArrayList<>();
        if (user.getDegree() == 0) {
            moduleList = moduleDao.findByBelong("0");
        } else if (user.getDegree() == 1) {
            moduleList = moduleDao.findByUserBelong("1",user.getCompanyId());
        } else {
            moduleList=moduleDao.findByUserId(user.getId());
        }
        for(Module m : moduleList){
            System.out.println(m.getName());
        }
        return moduleList;
    }
}
