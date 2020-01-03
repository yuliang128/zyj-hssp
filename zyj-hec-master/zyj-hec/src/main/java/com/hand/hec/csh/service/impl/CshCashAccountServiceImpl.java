package com.hand.hec.csh.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshCashAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshCashAccount;
import com.hand.hec.csh.service.ICshCashAccountService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshCashAccountServiceImpl extends BaseServiceImpl<CshCashAccount> implements ICshCashAccountService {
    @Autowired
    CshCashAccountMapper mapper;

    @Override
    public List<Map> queryByBooksId(IRequest request, Long setOfBooksId) {
        return mapper.queryByBooksId(setOfBooksId);
    }

    @Override
    public List<CshCashAccount> queryCashAccount(IRequest request, CshCashAccount record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.queryCashAccount(record);
    }
}