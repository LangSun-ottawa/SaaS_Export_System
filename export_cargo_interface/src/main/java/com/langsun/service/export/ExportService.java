package com.langsun.service.export;


import com.github.pagehelper.PageInfo;
import com.langsun.domain.export.Export;
import com.langsun.domain.export.ExportExample;
import com.langsun.domain.vo.ExportResult;

import java.util.List;


public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo findAll(ExportExample example, int page, int size);

    List<Export> findAll(ExportExample example);

    void updateExport(ExportResult exportResult);
}
