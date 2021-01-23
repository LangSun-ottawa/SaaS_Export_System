package com.langsun.dao.cargo;

import com.langsun.domain.cargo.Factory;
import com.langsun.domain.cargo.FactoryExample;

import java.util.List;

public interface FactoryDao {

    int deleteByPrimaryKey(String id);

    String selectKeyReturnName(String id);

    int insertSelective(Factory record);


    List<Factory> selectByExample(FactoryExample example);


    Factory selectByPrimaryKey(String id);


    int updateByPrimaryKeySelective(Factory record);


    int insert(Factory record);


    int updateByPrimaryKey(Factory record);
}