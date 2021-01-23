package com.langsun.service.finance;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.dao.cargo.ContractDao;
import com.langsun.dao.finance.FinanceDao;
import com.langsun.domain.cargo.Contract;
import com.langsun.domain.finance.Finance;
import com.langsun.domain.finance.FinanceExample;
import com.langsun.service.packing.PackingListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author slang
 * @date 2020-09-10 0:40
 * @Param $
 * @return $
 **/
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceDao financeDao;
    @Autowired
    private ContractDao contractDao;

    @Override
    public Finance findById(String id) {
        return financeDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Finance finance) {
        finance.setFinanceId(UUID.randomUUID().toString());
        String contractIds = finance.getContractIds();
        Double invoicePrice = 0d;
        if (contractIds.contains(",")) {
            String[] split = contractIds.split(",");
            for (String s : split) {
                Contract contract = contractDao.selectByPrimaryKey(s);
                invoicePrice += contract.getTotalAmount();
            }
        } else {
            Contract contract = contractDao.selectByPrimaryKey(contractIds);
            invoicePrice += contract.getTotalAmount();
        }
        finance.setInvoiceValue(invoicePrice);
        finance.setState(0);
        financeDao.insertSelective(finance);
    }

    @Override
    public void update(Finance finance) {
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Autowired
    private PackingListService packingListService;

    @Override
    public void delete(String id) {
        Finance finance = financeDao.selectByPrimaryKey(id);
        String packingListId = finance.getPackingListId();
        packingListService.delete(packingListId);
        financeDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(FinanceExample financeExample, int page, int size) {
        PageHelper.startPage(page, size);
        List<Finance> finances = financeDao.selectByExample(financeExample);
        return new PageInfo(finances);
    }

    @Override
    public List<Finance> findAll(FinanceExample financeExample) {
        return financeDao.selectByExample(financeExample);
    }
}
