package com.langsun.dao.cargo;

import com.langsun.domain.cargo.Contract;
import com.langsun.domain.cargo.ContractExample;

import java.util.List;
import java.util.Map;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    List<Map<String, Object>> findByShipTime(String inputDate, String companyId);
}