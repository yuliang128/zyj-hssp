package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoRepayRegRefEmpGp;
import com.hand.hec.csh.service.ICshMoRepayRegRefEmpGpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoRepayRegRefEmpGpServiceImpl extends BaseServiceImpl<CshMoRepayRegRefEmpGp> implements ICshMoRepayRegRefEmpGpService{

}