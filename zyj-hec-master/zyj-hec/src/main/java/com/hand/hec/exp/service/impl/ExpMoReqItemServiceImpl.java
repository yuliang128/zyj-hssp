package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.exception.ReqItemMultiException;
import com.hand.hec.exp.mapper.ExpMoReqItemMapper;
import com.hand.hec.exp.service.IExpMoReqItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqItemServiceImpl extends BaseServiceImpl<ExpMoReqItem> implements IExpMoReqItemService {
    @Autowired
    ExpMoReqItemMapper mapper;

    /**
     * <p>
     * 申请项目批量提交
     * <p/>
     *
     * @param request
     * @param expMoReqItems
     * @return List<ExpMoReqItem>
     * @throws ReqItemMultiException exception 申请项目代码重复定义异常
     * @author yang.duan 2019/2/20 9:40
     */
    @Override
    public List<ExpMoReqItem> batchSubmit(IRequest request, List<ExpMoReqItem> expMoReqItems) throws ReqItemMultiException {
        List<ExpMoReqItem> expMoReqItemList = new ArrayList<ExpMoReqItem>();
        Set<String> codeSet = new HashSet<String>();
        for (ExpMoReqItem item : expMoReqItems) {
            codeSet.add(item.getMoReqItemCode());
        }
        if ((codeSet.size() != expMoReqItems.size()) || (checkUnique(expMoReqItems))) {
            throw new ReqItemMultiException("EXP", ReqItemMultiException.ERROR_REQ_ITEM_UNIQUE_MULTI);
        }
        return this.batchUpdate(request, expMoReqItems);
    }

    /**
     * <p>
     * 校验唯一性
     * <p/>
     *
     * @param dto
     * @return flag
     * @author yang.duan 2019/2/20 9:36
     */
    private boolean checkUnique(List<ExpMoReqItem> dto) {
        int count = 0;
        boolean flag = false;
        ExpMoReqItem reqItem = new ExpMoReqItem();
        for (ExpMoReqItem ei : dto) {
            if ((ei.get__status().equals(DTOStatus.DELETE)) || (ei.get__status().equals(DTOStatus.UPDATE))) {
                break;
            } else {
                reqItem.setMagOrgId(ei.getMagOrgId());
                reqItem.setMoReqItemCode(ei.getMoReqItemCode());
                count = mapper.selectCount(reqItem);
                if (count > 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * <p>根据报销类型获取费用项目</p>
     *
     * @param request
     * @param moExpenseTypeId
     * @return List<ExpMoReqItem>
     * @author yang.duan 2019/4/29 11:30
     **/
    @Override
    public List<ExpMoReqItem> getReqItemByExpenseType(IRequest request, Long moExpenseTypeId) {
        return mapper.getReqItemByExpenseType(moExpenseTypeId);
    }
}
