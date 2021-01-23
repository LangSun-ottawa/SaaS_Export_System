package com.langsun.dao.system;


import com.github.pagehelper.PageInfo;
import com.langsun.domain.system.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //根据角色id 查询模块的方法
    List<Module> findByRoleId(String roleid);

    //查询全部
    List<Module> findAll();

    //根据角色id 删除中间表数据
    void deleteRoleModule(String roleid);

    //添加中间表数据
    void saveRoleModule(@Param("roleid") String roleid, @Param("moduleId") String moduleId);

    List<Module> findByBelong(String s);

    List<Module> findByUserBelong(@Param("belong")String s,@Param("companyId")String companyId);

    List<Module> findByUserId(String id);

    List<Module> findAllExcptSaas(String companyId);
}