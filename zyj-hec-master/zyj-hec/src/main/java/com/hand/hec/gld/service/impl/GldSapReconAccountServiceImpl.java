package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSapReconAccount;
import com.hand.hec.gld.service.IGldSapReconAccountService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSapReconAccountServiceImpl extends BaseServiceImpl<GldSapReconAccount> implements IGldSapReconAccountService{

}