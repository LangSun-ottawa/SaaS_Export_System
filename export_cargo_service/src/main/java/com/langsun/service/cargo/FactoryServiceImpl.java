package com.langsun.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.cargo.FactoryDao;
import com.langsun.domain.cargo.Factory;
import com.langsun.domain.cargo.FactoryExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author slang
 * @date 2020-08-11 18:49
 * @Param $
 * @return $
 **/
@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;

    @Override
    public void save(Factory factory) {
        factory.setId(UUID.randomUUID().toString());
        factoryDao.insertSelective(factory);
    }

    @Override
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    @Override
    public void delete(String id) {
        Factory factory = factoryDao.selectByPrimaryKey(id);
        factory.setState(0);
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);

    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        return factoryDao.selectByExample(example);
    }

    @Override
    public PageInfo findAll(FactoryExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Factory> factories = factoryDao.selectByExample(example);
        return new PageInfo(factories);
    }

}
