package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentBatchTpAsgnAe;
import com.hand.hec.csh.mapper.CshPaymentBatchTpAsgnAeMapper;
import com.hand.hec.csh.service.ICshPaymentBatchTpAsgnAeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchTpAsgnAeServiceImpl extends BaseServiceImpl<CshPaymentBatchTpAsgnAe> implements ICshPaymentBatchTpAsgnAeService{
    @Autowired
    private CshPaymentBatchTpAsgnAeMapper mapper;

    @Override
    public List<CshPaymentBatchTpAsgnAe> queryCanAsgEntity(IRequest iRequest,Long typeId, Long magOrgId, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        return mapper.queryCanAsgEntity(typeId,magOrgId);
    }
}