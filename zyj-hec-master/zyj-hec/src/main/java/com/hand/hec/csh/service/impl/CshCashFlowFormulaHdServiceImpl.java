package com.hand.hec.csh.service.impl;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshCashFlowFormulaHdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshCashFlowFormulaHd;
import com.hand.hec.csh.service.ICshCashFlowFormulaHdService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshCashFlowFormulaHdServiceImpl extends BaseServiceImpl<CshCashFlowFormulaHd> implements ICshCashFlowFormulaHdService {
    @Autowired
    CshCashFlowFormulaHdMapper mapper;

    @Override
    public CshCashFlowFormulaHd queryFormulaByItemId(IRequest request, Long cashFlowItemId) {
        return mapper.queryFormulaByItemId(cashFlowItemId);
    }

    @Override
    public List<Map> queryCashFlowLineNumber(IRequest request,Long cashFlowItemId, Long setOfBooksId) {
        List<Map> list = mapper.queryCashFlowLineNumber(cashFlowItemId,setOfBooksId);
        return mapper.queryCashFlowLineNumber(cashFlowItemId,setOfBooksId);
    }
}