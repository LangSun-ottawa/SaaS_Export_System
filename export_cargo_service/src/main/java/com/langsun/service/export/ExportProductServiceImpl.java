package com.langsun.service.export;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.export.ExportProductDao;
import com.langsun.domain.export.ExportProduct;
import com.langsun.domain.export.ExportProductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author slang
 * @date 2020-08-20 6:39
 * @Param $
 * @return $
 **/
@Service
public class ExportProductServiceImpl implements ExportProductService {
    @Autowired
    private ExportProductDao exportProductDao;

    @Override
    public ExportProduct findById(String id) {
        return null;
    }

    @Override
    public void save(ExportProduct exportProduct) {

    }

    @Override
    public void update(ExportProduct exportProduct) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        return null;
    }

    @Override
    public List<ExportProduct> findByExample(ExportProductExample exportProductExample) {
        return exportProductDao.selectByExample(exportProductExample);
    }

}
