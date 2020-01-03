package com.hand.hec.acp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hap.core.IRequest;
import com.hand.hec.acp.mapper.AcpReqAuditErrorLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpReqAuditErrorLog;
import com.hand.hec.acp.service.IAcpReqAuditErrorLogService;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
public class AcpReqAuditErrorLogServiceImpl extends BaseServiceImpl<AcpReqAuditErrorLog> implements IAcpReqAuditErrorLogService{

    @Autowired
    AcpReqAuditErrorLogMapper mapper;
    @Override
    public List<AcpReqAuditErrorLog> queryBySessionId(IRequest request,AcpReqAuditErrorLog dto,int page,int pageSize){
        return mapper.queryBySessionId();
    }
}