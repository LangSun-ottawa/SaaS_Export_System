package com.langsun.service.cargo;


import com.github.pagehelper.PageInfo;
import com.langsun.domain.cargo.Factory;
import com.langsun.domain.cargo.FactoryExample;

import java.util.List;

/**
 */
public interface FactoryService {

	/**
	 * 保存
	 */
	void save(Factory factory);

	/**
	 * 更新
	 */
	void update(Factory factory);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	Factory findById(String id);

	//查询所有
	public List<Factory> findAll(FactoryExample example);

	public PageInfo findAll(FactoryExample example, int page, int size);
}
