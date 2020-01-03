package com.hand.hec.csh.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshDefaultCashFlowItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshDefaultCashFlowItem;
import com.hand.hec.csh.service.ICshDefaultCashFlowItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshDefaultCashFlowItemServiceImpl extends BaseServiceImpl<CshDefaultCashFlowItem> implements ICshDefaultCashFlowItemService {
    @Autowired
    CshDefaultCashFlowItemMapper mapper;

    @Override
    public List<Map> queryByItemId(IRequest request, CshDefaultCashFlowItem condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.queryByItemId(condition);
    }

    @Override
    public List<Map> queryByAccount(IRequest request, Long setOfBooksId, String accountCodeFrom, String accountCodeTo) {
        return mapper.queryByAccount(setOfBooksId,accountCodeFrom,accountCodeTo);
    }

    @Override
    public List<CshDefaultCashFlowItem> batchSave(IRequest request, List<CshDefaultCashFlowItem> list) {
        for (CshDefaultCashFlowItem record : list){
            Criteria criteria = new Criteria(record);
            criteria.where(CshDefaultCashFlowItem.FIELD_CASH_FLOW_ITEM_ID
                          ,CshDefaultCashFlowItem.FIELD_ACCOUNT_ID);
            List<CshDefaultCashFlowItem> cashFlowItemList = self().selectOptions(request,record,criteria);
            if (cashFlowItemList.isEmpty()){
                self().insertSelective(request,record);
            }
        }
        return list;
    }
}