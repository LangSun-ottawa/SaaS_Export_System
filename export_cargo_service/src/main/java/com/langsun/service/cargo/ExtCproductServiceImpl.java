package com.langsun.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.cargo.ContractDao;
import com.langsun.dao.cargo.ExtCproductDao;
import com.langsun.domain.cargo.Contract;
import com.langsun.domain.cargo.ExtCproduct;
import com.langsun.domain.cargo.ExtCproductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author slang
 * @date 2020-08-11 18:49
 * @Param $
 * @return $
 **/
@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    @Override
    public void save(ExtCproduct extCproduct) {
        Double amount = 0d;

        if(extCproduct.getPrice() != null && extCproduct.getCnumber()!=null){
            amount=extCproduct.getPrice() * extCproduct.getCnumber();
        }
        extCproduct.setAmount(amount);
        extCproduct.setId(UUID.randomUUID().toString());
        extCproductDao.insertSelective(extCproduct);

        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        contract.setExtNum(contract.getExtNum() + 1);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        ExtCproduct oldExtCproduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());

        Double amount = 0d;
        if(extCproduct.getPrice() != null && extCproduct.getCnumber()!=null){
            amount=extCproduct.getPrice() * extCproduct.getCnumber();
        }
        extCproduct.setAmount(amount);
        extCproductDao.updateByPrimaryKeySelective(extCproduct);


        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        contract.setTotalAmount(contract.getTotalAmount() - oldExtCproduct.getAmount() + amount);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //1.附件表
        //1.1 先获得附件的对象
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        //1.2 删除附件即可
        extCproductDao.deleteByPrimaryKey(id);
        //2.合同表
        //2.1 根据已经查询附件对象 查询合同对象
        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //2.2 计算总金额
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        //2.3 附件的种类数量
        contract.setExtNum(contract.getExtNum() - 1);
        //2.4 修改合同表
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        return extCproduct;
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(example);
        return new PageInfo(extCproducts);
    }
}
