package com.hand.hec.csh.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoTransactionClass;
import com.hand.hec.csh.mapper.CshMoTransactionClassMapper;
import com.hand.hec.csh.service.ICshMoTransactionClassService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 现金事物分类定义impl
 * </p>
 *
 * @author LJK 2019/02/19 12:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoTransactionClassServiceImpl extends BaseServiceImpl<CshMoTransactionClass> implements ICshMoTransactionClassService {

    @Autowired
    private CshMoTransactionClassMapper trxClassMapper;

    @Override
    public List<CshMoTransactionClass> queryTrxClass(IRequest request) {
        return trxClassMapper.queryTrxClass();
    }
}