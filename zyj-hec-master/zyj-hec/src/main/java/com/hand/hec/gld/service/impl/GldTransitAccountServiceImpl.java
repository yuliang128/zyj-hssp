package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldTransitAccount;
import com.hand.hec.gld.mapper.GldTransitAccountMapper;
import com.hand.hec.gld.service.IGldTransitAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldTransitAccountServiceImpl extends BaseServiceImpl<GldTransitAccount> implements IGldTransitAccountService{
    @Autowired
    private GldTransitAccountMapper gldTransitAccountMapper;
    @Override
    public GldTransitAccount getTransitAccount(GldTransitAccount gldTransitAccount){
        GldTransitAccount gldTransitAccount1 = new GldTransitAccount();
        gldTransitAccount1 = gldTransitAccountMapper.getTransitAccount(gldTransitAccount);
        return gldTransitAccount1;
    }
}