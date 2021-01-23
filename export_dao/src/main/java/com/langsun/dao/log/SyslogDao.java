package com.langsun.dao.log;

import com.langsun.domain.log.SysLog;

import java.util.List;

/**
 * @author slang
 * @date 2020-08-05 16:46
 * @Param $
 * @return $
 **/
public interface SyslogDao {

    List<SysLog> findAll(String companyId);

    void save(SysLog sysLog);
}
