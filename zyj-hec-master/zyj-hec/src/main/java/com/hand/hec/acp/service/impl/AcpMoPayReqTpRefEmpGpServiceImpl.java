package com.hand.hec.acp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefEmpGp;
import com.hand.hec.acp.service.IAcpMoPayReqTpRefEmpGpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpMoPayReqTpRefEmpGpServiceImpl extends BaseServiceImpl<AcpMoPayReqTpRefEmpGp> implements IAcpMoPayReqTpRefEmpGpService{

}