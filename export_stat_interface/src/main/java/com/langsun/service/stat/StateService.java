package com.langsun.service.stat;

import java.util.List;
import java.util.Map;

/**
 * @author slang
 * @date 2020-08-27 18:51
 * @Param $
 * @return $
 **/
public interface StateService {
    List<Map> findFactory(String companyId);

    List<Map> findSell(String companyId);

    List<Map> findOnline(String companyId);
}
