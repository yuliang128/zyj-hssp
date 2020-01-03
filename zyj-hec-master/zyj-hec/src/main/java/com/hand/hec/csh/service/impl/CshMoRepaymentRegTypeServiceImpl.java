package com.hand.hec.csh.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoRepaymentRegType;
import com.hand.hec.csh.mapper.CshMoRepaymentRegTypeMapper;
import com.hand.hec.csh.service.ICshMoRepaymentRegTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoRepaymentRegTypeServiceImpl extends BaseServiceImpl<CshMoRepaymentRegType> implements ICshMoRepaymentRegTypeService{

    @Autowired
    private CshMoRepaymentRegTypeMapper mapper;

    @Override
    public List<CshMoRepaymentRegType> queryByCompanyId(IRequest request) {
        return mapper.queryByCompanyId();
    }
}