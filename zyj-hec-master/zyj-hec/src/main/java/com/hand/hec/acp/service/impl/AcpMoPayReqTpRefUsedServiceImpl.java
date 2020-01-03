package com.hand.hec.acp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefUsed;
import com.hand.hec.acp.service.IAcpMoPayReqTpRefUsedService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpMoPayReqTpRefUsedServiceImpl extends BaseServiceImpl<AcpMoPayReqTpRefUsed> implements IAcpMoPayReqTpRefUsedService{

}