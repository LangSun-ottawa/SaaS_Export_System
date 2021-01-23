package com.langsun.service.packing;

import com.alibaba.dubbo.config.annotation.Service;
import com.langsun.dao.cargo.ContractDao;
import com.langsun.dao.export.ExportDao;
import com.langsun.dao.packing.PackingListDao;
import com.langsun.domain.export.Export;
import com.langsun.domain.finance.Finance;
import com.langsun.domain.packing.PackingList;
import com.langsun.domain.packing.PackingListExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langsun.service.finance.FinanceService;
import com.langsun.utils.BeanMapUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PackingListServiceImpl implements PackingListService {
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private PackingListDao packingListDao;
    @Autowired
    private ContractDao contractDao;
    @Override
    public PackingList findById(String id) {
        return packingListDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(PackingList packingList) {
        packingList.setId(UUID.randomUUID().toString());
        packingList.setState(0L);
        packingList.setCreateTime(new Date());
        //设置装箱单的购销合同id
        String packingContractIds = "";
        //添加装箱单的同时,修改报运单的冗余字段
        String exportIds = packingList.getExportIds();
        System.out.println("exportIds"+exportIds);
        String[] exportIdsSplit = exportIds.split(",");
        for (String exportId : exportIdsSplit) {
            //修改报运单的状态 0 草稿 1已上报 2 已报运 3已装箱 4委托 5发票 6财务
            Export export = exportDao.selectByPrimaryKey(exportId);
            export.setState(3);
            //修改购销合同的状态
            //获取该报运单下的所有购销合同
            String contractIds = export.getContractIds();
            //循环添加购销合同id 到装箱单中
            packingContractIds += contractIds+",";
            //判断是否包含多个购销合同
            /*if (contractIds.contains(",")) {
                String[] splitContractIds = exportIds.split(",");
                //遍历数组修改购销合同的状态为 已装箱  state = 3
                for (String contractId : splitContractIds) {
                    Contract contract = new Contract();
                    contract.setId(contractId);
                    contract.setState(3);
                    contractDao.updateByPrimaryKeySelective(contract);
                }
            } else {//不包含
                Contract contract = new Contract();
                contract.setId(contractIds);
                contract.setState(3);
                contractDao.updateByPrimaryKeySelective(contract);
            }*/
            //添加装箱单id
//            System.err.println(packingList.getId());
            export.setPackingListId(packingList.getId());
            exportDao.updateByPrimaryKeySelective(export);
        }
        packingList.setContractIds(packingContractIds);
        System.out.println("contractId>>>>"+packingContractIds);
        System.out.println("contractId>>>>"+packingContractIds.length());
        System.out.println("packlist"+packingList);
        packingListDao.insertSelective(packingList);
    }

    @Override
    public void update(PackingList packingList) {
        packingListDao.updateByPrimaryKeySelective(packingList);
    }

    //根据id删除装箱单
    @Override
    public void delete(String id) {
        //根据packingListId查找装箱单对象
        PackingList packingList = packingListDao.selectByPrimaryKey(id);
        //通过装箱单查找此装箱单中的报运单
        String exportIds = packingList.getExportIds();
        //修改报运的单状态为2
        //判断exportIds中是否包含 ","
        boolean b = exportIds.contains(",");
        if (b) { //如果包含多个id 就遍历修改状态
            String[] exportIdsSplit = exportIds.split(",");
            for (String exportId : exportIdsSplit) {
                Export export = new Export();
                export.setId(exportId);
                export.setState(2);
              /*  //获取此报运单下的所有合同id
                String contractIds = export.getContractIds();
                if (contractIds.contains(",")) {//如果包含多个id 就遍历修改状态
                    String[] splitContractIds = contractIds.split(",");
                    for (String contractId : splitContractIds) {
                        Contract contract = new Contract();
                        contract.setId(contractId);
                        contract.setState(2);
                        contractDao.updateByPrimaryKeySelective(contract);
                    }
                } else {//不包含就直接修改单个id
                    Contract contract = new Contract();
                    contract.setId(contractIds);
                    contract.setState(2);
                    contractDao.updateByPrimaryKeySelective(contract);
                }*/
                //修改报运单中的packingListId 为null
                export.setPackingListId(null);
                exportDao.updateByPrimaryKeySelective(export);
            }
        } else {
            //不包含就直接修改单个id
            Export export = new Export();
            export.setId(exportIds);
            export.setState(2);
            //修改报运单中的packingListId 为null
            export.setPackingListId(null);
            exportDao.updateByPrimaryKeySelective(export);
        }

        packingListDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(PackingListExample packingListExample, int page, int size) {
        PageHelper.startPage(page, size);
        List<PackingList> list = packingListDao.selectByExample(packingListExample);
        return new PageInfo(list);
    }

    @Override
    public List<PackingList> findAll(PackingListExample packingListExample) {
        return packingListDao.selectByExample(packingListExample);
    }

    @Override
    public void update(PackingList packingList, String afterExportIds) {
        //先清除之前装箱单中的报运单
        String exportIds1 = packingList.getExportIds();
        if (exportIds1.contains(",")) {
            String[] split1 = exportIds1.split(",");
            for (String exportId : split1) {
                System.out.println("之前的========"+exportId);
                Export export = new Export();
                export.setId(exportId);
                export.setState(2);
                export.setPackingListId("null");
                exportDao.updateByPrimaryKeySelective(export);

            }
        } else {
            Export export = new Export();
            export.setId(exportIds1);
            export.setState(2);
            exportDao.updateByPrimaryKeySelective(export);
        }

        //修改现在包含的报运单
        if (afterExportIds.contains(",")) {
            String[] split2 = afterExportIds.split(",");

            for (String exportId : split2) {
                System.out.println("之后的========"+exportId);
                Export export = new Export();
                export.setId(exportId);
                export.setState(3);
                export.setPackingListId(packingList.getId());
                exportDao.updateByPrimaryKeySelective(export);
            }
        } else {
            Export export = new Export();
            export.setId(afterExportIds);
            export.setState(3);
            exportDao.updateByPrimaryKeySelective(export);
        }
        packingList.setExportIds(afterExportIds);

        packingListDao.updateByPrimaryKeySelective(packingList);
    }

    @Override
    public List<Export> findExports(String exportIds) {
        List<Export> exportList = new ArrayList<>();

        if (exportIds.contains(",")) {
            String[] split = exportIds.split(",");

            for (String s : split) {
                Export export = exportDao.selectByPrimaryKey(s);
                exportList.add(export);
            }

        } else {
            Export export = exportDao.selectByPrimaryKey(exportIds);
            exportList.add(export);
        }
        return exportList;
    }

    @Autowired
    private FinanceService financeService;

    @Override
    public void updateFinance(PackingList packingList) {

        String invoiceNo = UUID.randomUUID().toString();
//        System.out.println(invoiceNo);
        packingList.setInvoiceNo(invoiceNo);
        packingListDao.updateByPrimaryKeySelective(packingList);
        PackingList packingListNew = packingListDao.selectByPrimaryKey(packingList.getId());
//        save finance
        Finance finance = new Finance();
        finance.setCreateDept(packingListNew.getCreateDept());
        finance.setCompanyId(packingListNew.getCompanyId());
        finance.setCompanyName(packingListNew.getCompanyName());
        finance.setContractIds(packingListNew.getContractIds());
        finance.setCreateBy(packingListNew.getCreateBy());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        simpleDateFormat.format(date);
        finance.setCreateTime(date);
        finance.setPackingListId(packingListNew.getId());
        finance.setInvoiceNo(packingListNew.getInvoiceNo());
        financeService.save(finance);

        String exportIds = packingListNew.getExportIds();
        if (exportIds.contains(",")) {
            String[] split = exportIds.split(",");
            for (String s : split) {
                Export export = new Export();
                export.setId(s);
                export.setState(4);
                export.setPackingListId(packingListNew.getId());
                exportDao.updateByPrimaryKeySelective(export);
            }
        }
    }
}

