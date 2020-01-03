package com.hand.hec.csh.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshCashFlowFormulaLnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshCashFlowFormulaLn;
import com.hand.hec.csh.service.ICshCashFlowFormulaLnService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshCashFlowFormulaLnServiceImpl extends BaseServiceImpl<CshCashFlowFormulaLn> implements ICshCashFlowFormulaLnService {
    @Autowired
    CshCashFlowFormulaLnMapper mapper;

    @Override
    public List<Map> queryByItemId(IRequest request, Long cashFlowItemId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.queryByItemId(cashFlowItemId);
    }
}