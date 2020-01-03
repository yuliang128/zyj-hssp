package com.hand.hec.bgt.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetItem;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnCom;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnMo;
import com.hand.hec.bgt.mapper.BgtBudgetItemMapper;
import com.hand.hec.bgt.service.IBgtBudgetItemAsgnComService;
import com.hand.hec.bgt.service.IBgtBudgetItemAsgnMoService;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.service.IExpMoExpenseItemService;
import com.hand.hec.exp.service.IExpMoReqItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 预算项目ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 14:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetItemServiceImpl extends BaseServiceImpl<BgtBudgetItem> implements IBgtBudgetItemService {

    @Autowired
    BgtBudgetItemMapper itemMapper;

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IBgtBudgetItemAsgnMoService budgetItemAsgnMoService;

    @Autowired
    IBgtBudgetItemAsgnComService budgetItemAsgnComService;

    @Autowired
    IExpMoExpenseItemService expenseItemService;

    @Autowired
    IExpMoReqItemService expMoReqItemService;


    @Override
    public List<BgtBudgetItem> checkBgtItem(String controlType, Long bgtOrgId, String filtrateMethod, Long itemId,
                    String controlItemCodeFrom, String controlItemCodeTo) {
        return itemMapper.checkBgtItem(controlType, bgtOrgId, filtrateMethod, itemId, controlItemCodeFrom,
                        controlItemCodeTo);

    }

    /**
     * <p>
     * 根据费用项目获取预算项目(未获取到则取申请项目关联的预算项目)
     * <p/>
     *
     * @param request
     * @param expenseItemId
     * @return 预算项目ID(找不到返回-1)
     * @author yang.duan 2019/3/6 17:04
     */
    @Override
    public Long getExpneseBgtItemId(IRequest request, Long expenseItemId) {
        Long expBgtItemId = Long.valueOf(-1);
        ExpMoExpenseItem expenseItem = new ExpMoExpenseItem();
        expenseItem.setMoExpenseItemId(expenseItemId);
        expenseItem = expenseItemService.selectByPrimaryKey(request, expenseItem);
        if ("Y".equals(expenseItem.getEnabledFlag()) && expenseItem.getBudgetItemId() != null) {
            expBgtItemId = expenseItem.getBudgetItemId();
        }
        if (expenseItem.getBudgetItemId() == null && expenseItem.getMoReqItemId() != null) {
            ExpMoReqItem expMoReqItem = new ExpMoReqItem();
            expMoReqItem.setMoReqItemId(expenseItem.getMoReqItemId());
            expMoReqItem = expMoReqItemService.selectByPrimaryKey(request, expMoReqItem);
            expBgtItemId = expMoReqItem.getBudgetItemId();
        }
        return expBgtItemId;
    }

    @Override
    public List<BgtBudgetItem> queryHierarchyByBgtOrgId(IRequest request, BgtBudgetItem bgtBudgetItem, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return itemMapper.queryHierarchyByBgtOrgId(bgtBudgetItem);
    }

    @Override
    public List<BgtBudgetItem> batchAssignMagOrg(IRequest request, List<BgtBudgetItem> bgtBudgetItemList) {
        for (BgtBudgetItem bgtBudgetItem : bgtBudgetItemList) {
            List<BgtBudgetItemAsgnMo> budgetItemAsgnMoList = bgtBudgetItem.getMagOrgList();
            for (BgtBudgetItemAsgnMo budgetItemAsgnMo : budgetItemAsgnMoList) {
                budgetItemAsgnMo.setBudgetItemId(bgtBudgetItem.getBudgetItemId());
                this.batchAssign(request, budgetItemAsgnMo);
            }
        }
        return bgtBudgetItemList;
    }

    @Override
    public List<BgtBudgetItem> queryNoSummaryByBgtOrg(IRequest request, BgtBudgetItem bgtBudgetItem) {
        return itemMapper.queryNoSummaryByBgtOrg(bgtBudgetItem);
    }

    @Override
    public List<BgtBudgetItem> bgtJournalBatchAssign(IRequest request, BgtBudgetItem bgtBudgetItem) {
        return itemMapper.bgtJournalBatchAssign(bgtBudgetItem);
    }

    @Override
    public List<BgtBudgetItem> getTreeChildItems(IRequest request, Long bgtItemId) {
        List<BgtBudgetItem> list = new ArrayList<>();
        BgtBudgetItem bgtBudgetItem =
                        self().selectByPrimaryKey(request, BgtBudgetItem.builder().budgetItemId(bgtItemId).build());
        if (bgtBudgetItem != null) {
            list.add(bgtBudgetItem);
        }
        this.getChildren(request, bgtItemId, list);
        return list;
    }

    private List<BgtBudgetItem> getChildren(IRequest request, Long parentBgtItemId, List<BgtBudgetItem> list) {
        BgtBudgetItem bgtBudgetItem = BgtBudgetItem.builder().parentBudgetItemId(parentBgtItemId)
                        .summaryFlag(BaseConstants.NO).build();
        int num = itemMapper.selectCount(bgtBudgetItem);
        if (num > 0) {
            List<BgtBudgetItem> subParents = itemMapper.select(bgtBudgetItem);
            list.addAll(subParents);
            subParents.forEach(budgetItem -> {
                getChildren(request, budgetItem.getBudgetItemId(), list);
            });
        }
        return list;
    }

    private void batchAssign(IRequest request, BgtBudgetItemAsgnMo budgetItemAsgnMo) {
        // 查询管理组织下所有有效的管理公司
        List<FndCompany> companies = companyService.select(request,
                        FndCompany.builder().magOrgId(budgetItemAsgnMo.getMagOrgId()).build(), 0, 0);
        BgtBudgetItemAsgnMo resultBudgetItemAsgnMo =
                        budgetItemAsgnMoService.submitBgtBudgetItemAsgnMo(request, budgetItemAsgnMo);
        for (FndCompany company : companies) {
            if (company.isActive()) {
                BgtBudgetItemAsgnCom budgetItemAsgnCom = new BgtBudgetItemAsgnCom();
                budgetItemAsgnCom.setAssignMoId(resultBudgetItemAsgnMo.getAssignMoId());
                budgetItemAsgnCom.setCompanyId(company.getCompanyId());
                budgetItemAsgnComService.submitBgtBudgetItemAsgnCom(request, budgetItemAsgnCom);
            }
        }
    }

}
