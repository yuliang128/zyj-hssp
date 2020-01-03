package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.ExpMoRepEleRefExpTpMapper;
import com.hand.hec.exp.service.IExpMoExpTypeRefExpItService;
import com.hand.hec.exp.service.IExpMoExpenseItemService;
import com.hand.hec.exp.service.IExpMoRepEleRefExpItService;
import com.hand.hec.exp.service.IExpMoRepEleRefExpTpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ExpMoRepEleRefExpTpServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepEleRefExpTpServiceImpl extends BaseServiceImpl<ExpMoRepEleRefExpTp>
                implements IExpMoRepEleRefExpTpService {


    @Autowired
    ExpMoRepEleRefExpTpMapper eleRefExpTpMapper;

    @Autowired
    IExpMoExpTypeRefExpItService expTypeRefExpItService;

    @Autowired
    IExpMoRepEleRefExpItService eleRefExpItService;


    @Autowired
    IExpMoExpenseItemService expenseItemService;

    /**
     * <p>
     * 报销单类型定义-页面元素-报销类型批量提交
     * <p/>
     * 
     * @param List<ExpMoRepEleRefExpTp> refExpTps
     * @param IRequest request
     * @return List<ExpMoRepEleRefExpTp>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 13:46
     */
    @Override
    public List<ExpMoRepEleRefExpTp> batchSubmit(IRequest request, List<ExpMoRepEleRefExpTp> refExpTps)
                    throws RuntimeException {
        ExpMoExpTypeRefExpIt expTypeRefExpIt = new ExpMoExpTypeRefExpIt();
        ExpMoRepEleRefExpIt moRepEleRefExpIt = new ExpMoRepEleRefExpIt();
        if (!refExpTps.isEmpty()) {
            for (ExpMoRepEleRefExpTp expTp : refExpTps) {
                if (expTp.get__status().equals(DTOStatus.DELETE)) {
                    // 级联删除费用项目
                    moRepEleRefExpIt.setExpTypeRefId(expTp.getExpTypeRefId());
                    batchDeleteRefIt(request, moRepEleRefExpIt);
                    this.deleteByPrimaryKey(expTp);
                }
                expTp.setLastUpdateDate(new Date());
                expTp.setLastUpdatedBy(request.getUserId());
                if (expTp.getApponitItemFlag().equals("Y") && expTp.get__status().equals(DTOStatus.ADD)) {
                    expTp.setCreationDate(new Date());
                    expTp.setCreatedBy(request.getUserId());
                    this.insertSelective(request, expTp);
                }
                // 如果'指定明细项目标志'为N且状态为ADD,在exp_mo_req_ele_ref_req_its表中需插入当前报销类型下的所有费用项目
                if (expTp.getApponitItemFlag().equals("N") && expTp.get__status().equals(DTOStatus.ADD)) {
                    expTp.setCreationDate(new Date());
                    expTp.setCreatedBy(request.getUserId());
                    expTypeRefExpIt.setMoExpenseTypeId(this.insertSelective(request, expTp).getMoExpenseTypeId());
                    sumbitItem(request, expTypeRefExpIt, expTp.getExpTypeRefId());
                }
                // 如果'指定明细项目标志'为N且状态为UPDATE,先删除所有费用项目,再重新关联报销类型下所有费用项目
                if (expTp.getApponitItemFlag().equals("N") && expTp.get__status().equals(DTOStatus.UPDATE)) {
                    moRepEleRefExpIt.setExpTypeRefId(
                                    this.updateByPrimaryKeySelective(request, expTp).getExpTypeRefId());
                    // 级联删除费用项目
                    batchDeleteRefIt(request, moRepEleRefExpIt);
                    // 重新关联报销类型下所有费用项目
                    expTypeRefExpIt.setMoExpenseTypeId(expTp.getMoExpenseTypeId());
                    sumbitItem(request, expTypeRefExpIt, expTp.getExpTypeRefId());
                }
                if (expTp.getApponitItemFlag().equals("Y") && expTp.get__status().equals(DTOStatus.UPDATE)) {
                    ExpMoRepEleRefExpTp oldValue = mapper.selectByPrimaryKey(expTp.getExpTypeRefId());
                    this.updateByPrimaryKeySelective(request, expTp);
                    // 如果'指定明细项目标志'由N变为Y,则级联删除费用项目
                    if (oldValue.getApponitItemFlag().equals("N")) {
                        moRepEleRefExpIt.setExpTypeRefId(expTp.getExpTypeRefId());
                        batchDeleteRefIt(request, moRepEleRefExpIt);
                    }
                }
            }
        }
        return refExpTps;
    }

    /**
     * <p>
     * 关联报销类型下所有的费用项目
     * <p/>
     * 
     * @param IRequest request
     * @param ExpMoExpTypeRefExpIt typeRefExpIt
     * @param Long expTypeRefId
     * @return List<ExpMoRepEleRefExpIt>
     * @author yang.duan 2019/2/28 15:48
     */
    private List<ExpMoRepEleRefExpIt> sumbitItem(IRequest request, ExpMoExpTypeRefExpIt typeRefExpIt,
                    Long expTypeRefId) {
        List<ExpMoRepEleRefExpIt> eleRefExpIts = new ArrayList<ExpMoRepEleRefExpIt>();
        Criteria expItCri = new Criteria(typeRefExpIt);
        expItCri.where(new WhereField(ExpMoExpTypeRefExpIt.FIELD_MO_EXPENSE_TYPE_ID, Comparison.EQUAL));
        List<ExpMoExpTypeRefExpIt> expItList = expTypeRefExpItService.selectOptions(request, typeRefExpIt, expItCri);
        if (expItList != null && !expItList.isEmpty()) {
            for (ExpMoExpTypeRefExpIt it : expItList) {
                ExpMoExpenseItem item = new ExpMoExpenseItem();
                item.setMoExpenseItemId(it.getMoExpenseItemId());
                ExpMoExpenseItem expenseItem = expenseItemService.selectByPrimaryKey(request, item);
                if (expenseItem != null && expenseItem.getEnabledFlag().equals("Y")) {
                    ExpMoRepEleRefExpIt eit = new ExpMoRepEleRefExpIt();
                    eit.setExpTypeRefId(expTypeRefId);
                    eit.setMoExpenseItemId(it.getMoExpenseItemId());
                    eit.setEnabledFlag("Y");
                    eit.set__status(DTOStatus.ADD);
                    eleRefExpIts.add(eit);
                }
            }
        }
        return eleRefExpItService.batchUpdate(request, eleRefExpIts);
    }

    /**
     * <p>
     * 级联删除费用项目
     * <p/>
     * 
     * @param IRequest request
     * @param ExpMoRepEleRefExpIt expIt
     * @return int
     * @author yang.duan 2019/2/28 15:49
     */
    private int batchDeleteRefIt(IRequest request, ExpMoRepEleRefExpIt expIt) {
        Criteria refItCri = new Criteria(expIt);
        refItCri.where(new WhereField(ExpMoRepEleRefExpIt.FIELD_EXP_TYPE_REF_ID, Comparison.EQUAL));
        List<ExpMoRepEleRefExpIt> itList = eleRefExpItService.selectOptions(request, expIt, refItCri);
        if (itList != null && !itList.isEmpty()) {
            return eleRefExpItService.batchDelete(itList);
        }
        return 0;
    }
}
