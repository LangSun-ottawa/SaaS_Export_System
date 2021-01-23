package com.langsun.service.log;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.log.SysLog;

/**
 * @author slang
 * @date 2020-08-05 15:37
 * @Param $
 * @return $
 **/
public interface SyslogService {


    PageInfo findByPage(Integer page, Integer size, String companyId);

    void save(SysLog sysLog);

}
