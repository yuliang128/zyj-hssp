package com.hand.hec.acp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.mapper.AcpMoPayReqTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpMoPayReqType;
import com.hand.hec.acp.service.IAcpMoPayReqTypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpMoPayReqTypeServiceImpl extends BaseServiceImpl<AcpMoPayReqType> implements IAcpMoPayReqTypeService{
    @Autowired
    private AcpMoPayReqTypeMapper moPayReqTypeMapper;

    @Override
    public List<AcpMoPayReqType> queryByCompany(IRequest request, Long companyId) {
        return moPayReqTypeMapper.queryByCompany(companyId);
    }

    @Override
    public List<AcpMoPayReqType> getAcpMoPayReqTypeList(IRequest request, Long employeeId, Long companyId) {
        return moPayReqTypeMapper.getAcpMoPayReqTypeList(employeeId, companyId);
    }
}