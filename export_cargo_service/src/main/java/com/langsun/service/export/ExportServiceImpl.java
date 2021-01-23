package com.langsun.service.export;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.cargo.ContractDao;
import com.langsun.dao.cargo.ContractProductDao;
import com.langsun.dao.cargo.ExtCproductDao;
import com.langsun.dao.cargo.FactoryDao;
import com.langsun.dao.export.ExportDao;
import com.langsun.dao.export.ExportProductDao;
import com.langsun.dao.export.ExtEproductDao;
import com.langsun.domain.cargo.*;
import com.langsun.domain.export.Export;
import com.langsun.domain.export.ExportExample;
import com.langsun.domain.export.ExportProduct;
import com.langsun.domain.export.ExtEproduct;
import com.langsun.domain.vo.ExportProductResult;
import com.langsun.domain.vo.ExportResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sun.dc.pr.PRError;

import java.util.*;

/**
 * @author slang
 * @date 2020-08-20 6:38
 * @Param $
 * @return $
 **/
@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ExportDao exportDao;

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ExportProductDao exportProductDao;
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ExtEproductDao extEproductDao;

    /**
     * 新增出口报运单
     * 1.将合同数据转换成出口报运单数据
     * 2.将货物数据转换成出口货物数据
     * 3.将附件数据转换出口附件的数据
     * @param export
     */
    @Override
    public void save(Export export) {
        //1.将合同数据转换成出口报运单数据
        //1.1 id
        export.setId(UUID.randomUUID().toString());
        //1.2 制单日期
        export.setInputDate(new Date());
        //1.3 拼接一个合同确认书号 有多个合同
        String customerContract = "";

        //1.4 先查询合同对象 -> 获得合同id 查询合同对象
        String contractIds = export.getContractIds();
        String[] splitContractIds = contractIds.split(",");

        //select * from 表 where id = 1   select * from 表 where id in (1,2,3)
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andIdIn(Arrays.asList(splitContractIds));
        List<Contract> contracts = contractDao.selectByExample(contractExample);
        //1.5 遍历合同数据
        for (Contract contract : contracts) {
            customerContract += contract.getContractNo()+ " " ;//拼接合同的号
            //需要修改合同的状态值 将state修改为2
            contract.setState(2);
            //修改合同状态
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //1.6 新增的出口报运单 状态
        export.setState(0);//草稿状态
        export.setCustomerContract(customerContract);


        //2.将货物数据转换成出口货物数据
        //2.1 查询每个合同下的货物数据 根据合同id的数组直接查询
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria contractProductExampleCriteria = contractProductExample.createCriteria();
        contractProductExampleCriteria.andContractIdIn( Arrays.asList(splitContractIds) );//根据数组查询到所有的货物
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);
        HashMap<String, String> idMap = new HashMap<>();
        //2.2.转换成出口报运货物数据
        for (ContractProduct contractProduct : contractProducts) {
            //出口报运的货物对象
            ExportProduct exportProduct = new ExportProduct();
            //对象转换 参数1 源对象 参数2 : 目标对象  将源对象的数据复制到目标对象中 前提是字段名称一致 字段名称不一致 不动
            BeanUtils.copyProperties(contractProduct, exportProduct);
            //赋值id
            exportProduct.setId(UUID.randomUUID().toString()); //需要在复制数据完以后添加,不然被copy直接覆盖成contracP的id
            exportProduct.setExportId( export.getId() );//添加报运单id  export对象必须提前设置id

            idMap.put(contractProduct.getId(), exportProduct.getId());
            exportProductDao.insertSelective(exportProduct);
        }

        //3.将附件数据转换出口附件的数据
        //3.1 根据购销合同的id 查询附件的数据(以前有冗余字段)
        ExtCproductExample example  = new ExtCproductExample();
        ExtCproductExample.Criteria exampleCriteria = example.createCriteria();
        exampleCriteria.andContractIdIn( Arrays.asList( splitContractIds ));
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(example); //附件对象

        //3.2 遍历
        for (ExtCproduct extCproduct : extCproducts) {
            //3.3 将附件数据转换成报运附件的数据
            ExtEproduct extEproduct = new ExtEproduct();
            BeanUtils.copyProperties(extCproduct ,extEproduct );
            //重新赋值id
            extEproduct.setId(UUID.randomUUID().toString());
            //3.4 保存数据-> 必须建立联系
            extEproduct.setExportId(export.getId());

            //根据以前的货物id 获得新的报运货物id
            String extEproductId = idMap.get(extCproduct.getContractProductId());
            extEproduct.setId(extEproductId);

            //保存附件对象
            extEproductDao.insertSelective(extEproduct);
        }

        export.setProNum(contractProducts.size());
        export.setExtNum(extCproducts.size());

        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {
        //1.修改报运单
        exportDao.updateByPrimaryKeySelective(export);
        //2.修改报运单下货物数据
        if (export.getExportProducts() != null && !export.getExportProducts().isEmpty()) {
            for (ExportProduct exportProduct : export.getExportProducts()) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    @Override
    public void delete(String id) {
        Export export = exportDao.selectByPrimaryKey(id);
        String contractIds = export.getContractIds();
        String[] splitContractIds = contractIds.split(",");
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andIdIn(Arrays.asList(splitContractIds));
        List<Contract> contracts = contractDao.selectByExample(contractExample);
        //1.5 遍历合同数据
        for (Contract contract : contracts) {
            //需要修改合同的状态值 将state修改为0,不合格退回成草稿
            contract.setState(0);
            //修改合同状态
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //删除不合格的表格 以及export的货物和附件
        extEproductDao.deleteByExportId(id);
        exportProductDao.deleteByExportId(id);
        exportDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateExport(ExportResult exportResult) {
        Export export = new Export();
        export.setId(exportResult.getExportId());
        export.setRemark(exportResult.getRemark());
        export.setState(exportResult.getState());
        exportDao.updateByPrimaryKeySelective(export);

        for (ExportProductResult product : exportResult.getProducts()) {
            ExportProduct exportProduct = new ExportProduct();
            exportProduct.setId(product.getExportProductId());
            exportProduct.setTax(product.getTax());
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }

    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Export> exports = exportDao.selectByExample(example);

        return new PageInfo(exports);
    }

    @Override
    public List<Export> findAll(ExportExample example) {
        return exportDao.selectByExample(example);
    }


}
