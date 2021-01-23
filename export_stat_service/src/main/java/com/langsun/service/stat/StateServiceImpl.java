package com.langsun.service.stat;

import com.alibaba.dubbo.config.annotation.Service;
import com.langsun.dao.stat.StatDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author slang
 * @date 2020-08-27 18:51
 * @Param $
 * @return $
 **/
@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StatDao statDao;

    @Override
    public List<Map> findFactory(String companyId) {
        return statDao.findFactory(companyId);
    }

    @Override
    public List<Map> findSell(String companyId) {
        return statDao.findSell(companyId);
    }

    @Override
    public List<Map> findOnline(String companyId) {
        return statDao.findOnline(companyId);
    }

}
