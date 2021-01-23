package com.langsun.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.cargo.ContractDao;
import com.langsun.dao.cargo.ContractProductDao;
import com.langsun.dao.cargo.ExtCproductDao;
import com.langsun.domain.cargo.Contract;
import com.langsun.domain.cargo.ContractExample;
import com.langsun.domain.vo.ContractProductVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author slang
 * @date 2020-08-11 18:48
 * @Param $
 * @return $
 **/
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public Contract findById(String id) {
        Contract contractById = contractDao.selectByPrimaryKey(id);
        return contractById;
    }


    @Override
    public void save(Contract contract) {
        //1.页面传入参数 但是不全  需要我们手动补充
        if (contract.getTotalAmount() == null || contract.getTotalAmount() == 0) {
            System.out.println(">>>>contract的总金额数创建时为0<<<<");
            contract.setTotalAmount(0d);//0 double
        }
        contract.setState(0);//草稿的数据可以删除  ,上报 提交给领导了   报运(给海关)
        //2.要保存到数据库 需要有id
        contract.setId(UUID.randomUUID().toString());
        contract.setProNum(0);//货物数量
        contract.setExtNum(0);//附件数量
        contract.setCreateTime( new Date() );//数据创建的日期 - 数据排序
        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //删除附件（根据购销合同id删除附件）
        extCproductDao.deleteByContractId(id);
        //删除货物(根据购销合同id删除货物)
        contractProductDao.deleteByContractId(id);
        //删除购销合同
        contractDao.deleteByPrimaryKey(id);
    }


    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Contract> contractList = contractDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(contractList);
        return pageInfo;
    }

    @Override
    public List<ContractProductVo> findByShipTime(String inputDate, String companyId) {
        List<Map<String, Object>> lists = contractDao.findByShipTime(inputDate, companyId);
        List<ContractProductVo> result = new ArrayList<>();
        try {
            for (Map<String, Object> map : lists) {
                ContractProductVo contractProductVo = new ContractProductVo();
                BeanUtils.populate(contractProductVo,map);
                result.add(contractProductVo);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
