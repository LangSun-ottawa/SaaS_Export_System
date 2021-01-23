package com.langsun.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.cargo.ContractDao;
import com.langsun.dao.cargo.ContractProductDao;
import com.langsun.dao.cargo.ExtCproductDao;
import com.langsun.domain.cargo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author slang
 * @date 2020-08-11 18:48
 * @Param $
 * @return $
 **/
@Service
public class ContractProductServiceImpl implements ContractProductService {
    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;
    /**
     * 保存方法
     * 一: 货物表
     *      1.计算货物的总金额
     *      2.赋值给货物对象
     *      3.给货物id 保存货物
     * 二: 合同表
     *      4.修改总金额
     *      5.修改货物的数量种类
     *      6.保存到数据库
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {
        //一:货物表
        Double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getPrice() * contractProduct.getCnumber();
        }
        contractProduct.setAmount(amount);

        contractProduct.setId(UUID.randomUUID().toString());

        contractProductDao.insertSelective(contractProduct);

        //二: 合同表
        //查询到合同的数据
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //     4.修改总金额 一个货物可以 10  5
        //合同总金额 = 所有货物的总金额  + 所有附件的总金额
        //合同总金额 = 所有货物的总金额 货物1的总金额 + 货物2的总金额...
        //合同总金额  = 原来的合同总金额 + 新增的货物金额
        contract.setTotalAmount( contract.getTotalAmount() + amount );
        //     5.修改货物的数量种类 + 1
        contract.setProNum( contract.getProNum() + 1  );
        //     6.保存到数据库
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //查询旧的amount金额
        ContractProduct oldContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //1.货物总价更新
        Double amount = 0d;
        if (contractProduct.getPrice() != null && contractProduct.getCnumber() != null) {
            amount = contractProduct.getPrice() * contractProduct.getCnumber();
        }
        contractProduct.setAmount(amount);
        //存入数据库
        contractProductDao.updateByPrimaryKeySelective(contractProduct);

        //2.合同表总价更新
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + (amount - oldContractProduct.getAmount()));
        //存入数据库
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Autowired
    private ExtCproductDao extCproductDao;
    /**
     * 删除货物
     *  1.货物表
     *  1.1 先获得货物对象
     *  1.2 删除货物对象
     *  2.附件表
     *  2.1 先当前货物下所有的附件对象
     *  2.2 删除附件对象
     *  3.合同表
     *  3.1 获得合同对象
     *  3.2 计算合同的金额
     *  3.3 计算货物数量 和 附件数量
     *  3.4 保存到数据库
     * @param id
     */
    @Override
    public void delete(String id) {
        Double amount = 0d;
//         1.货物表
//          *  1.1 先获得货物对象
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
//          *  1.2 删除货物对象
        amount = contractProduct.getAmount();
        contractProductDao.deleteByPrimaryKey(id);

//        *  2.附件表
//        *  2.1 先当前货物下所有的附件对象
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCproductExampleCriteria = extCproductExample.createCriteria();
        extCproductExampleCriteria.andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        //*  2.2 删除附件对象

        if (!extCproducts.isEmpty()) {
            for (ExtCproduct extCproduct : extCproducts) {
                amount += extCproduct.getAmount();
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }
//        *  3.合同表
//        *  3.1 获得合同对象
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
//        *  3.2 计算合同的金额
        contract.setTotalAmount(contract.getTotalAmount() - amount);
//        *  3.3 计算货物数量 和 附件数量
        contract.setProNum(contract.getProNum() - 1);
        contract.setExtNum(contract.getExtNum() - extCproducts.size());
//        *  3.4 保存到数据库
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {

        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(example);
        return new PageInfo(contractProducts);
    }

    @Override
    public List<ContractProduct> findAll(ContractProductExample example) {
        return contractProductDao.selectByExample(example);
    }


    @Override
    public void saveList(List<ContractProduct> list) {
        for (ContractProduct object : list) {
            save(object);
        }
    }

}
