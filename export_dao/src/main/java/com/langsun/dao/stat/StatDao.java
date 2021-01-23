package com.langsun.dao.stat;

import java.util.List;
import java.util.Map;

/**
 * @author slang
 * @date 2020-08-27 21:32
 * @Param $
 * @return $
 **/
public interface StatDao {


    List<Map> findFactory(String companyId);

    List<Map> findSell(String companyId);

    List<Map> findOnline(String companyId);
}
