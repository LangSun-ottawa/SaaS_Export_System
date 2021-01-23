package com.langsun.service.log.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.log.SyslogDao;
import com.langsun.domain.log.SysLog;
import com.langsun.service.log.SyslogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author slang
 * @date 2020-08-05 15:38
 * @Param $
 * @return $
 **/
@Service
public class SyslogServiceImpl implements SyslogService {

    @Autowired
    private SyslogDao syslogDao;

    @Override
    public PageInfo findByPage(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page, size);
        List<SysLog> sysLogList = syslogDao.findAll(companyId);
        PageInfo pageInfo = new PageInfo(sysLogList);
        return pageInfo;
    }

    @Override
    public void save(SysLog sysLog) {
        sysLog.setId(UUID.randomUUID().toString());
        syslogDao.save(sysLog);
    }
}
