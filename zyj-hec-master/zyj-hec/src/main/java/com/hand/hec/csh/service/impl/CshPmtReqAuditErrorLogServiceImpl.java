package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPmtReqAuditErrorLog;
import com.hand.hec.csh.service.ICshPmtReqAuditErrorLogService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPmtReqAuditErrorLogServiceImpl extends BaseServiceImpl<CshPmtReqAuditErrorLog> implements ICshPmtReqAuditErrorLogService{

}