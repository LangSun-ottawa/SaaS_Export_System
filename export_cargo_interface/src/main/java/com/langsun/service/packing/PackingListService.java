package com.langsun.service.packing;

import com.langsun.domain.export.Export;
import com.langsun.domain.packing.PackingList;
import com.langsun.domain.packing.PackingListExample;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface PackingListService {

	//根据id查询
    PackingList findById(String id);

    //保存
    void save(PackingList packing);

    //更新
    void update(PackingList packingList);

    //删除
    void delete(String id);

    //分页查询
    PageInfo findAll(PackingListExample packingListExample, int page, int size);


    List<PackingList> findAll(PackingListExample packingListExample);

    void update(PackingList packingList, String exportIds);

    List<Export> findExports(String exportIds);

    void updateFinance(PackingList packingList);
}
