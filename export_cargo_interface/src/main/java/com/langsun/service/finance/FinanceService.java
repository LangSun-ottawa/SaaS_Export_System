package com.langsun.service.finance;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.finance.Finance;
import com.langsun.domain.finance.FinanceExample;

import java.util.List;

/**
 * @author slang
 * @date 2020-09-10 0:40
 * @Param $
 * @return $
 **/
public interface FinanceService {

    Finance findById(String id);

    //保存
    void save(Finance finance);

    //更新
    void update(Finance finance);

    //删除
    void delete(String id);

    //分页查询
    PageInfo findAll(FinanceExample financeExample, int page, int size);


    List<Finance> findAll(FinanceExample financeExample);
    
}
