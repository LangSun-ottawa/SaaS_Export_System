package com.langsun.dao.cargo;

import com.langsun.domain.cargo.ExtCproduct;
import com.langsun.domain.cargo.ExtCproductExample;

import java.util.List;

public interface ExtCproductDao {

	//删除
	int deleteByPrimaryKey(String id);
	//根据合同外键删除
	int deleteByContractId(String cid);

	//保存
	int insertSelective(ExtCproduct record);

	//条件查询
	List<ExtCproduct> selectByExample(ExtCproductExample example);

	//id查询
	ExtCproduct selectByPrimaryKey(String id);

	//更新
	int updateByPrimaryKeySelective(ExtCproduct record);

}