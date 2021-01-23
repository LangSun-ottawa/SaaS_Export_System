package com.langsun.service.export;


import com.github.pagehelper.PageInfo;
import com.langsun.domain.export.ExportProduct;
import com.langsun.domain.export.ExportProductExample;

import java.util.List;


public interface ExportProductService {

	ExportProduct findById(String id);

	void save(ExportProduct exportProduct);

	void update(ExportProduct exportProduct);

	void delete(String id);

	PageInfo findAll(String companyId, int page, int size);

	List<ExportProduct> findByExample(ExportProductExample exportProductExample);
}
