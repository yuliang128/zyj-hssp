package com.hand.hec.gld.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldTransitAccount;

public interface IGldTransitAccountService extends IBaseService<GldTransitAccount>, ProxySelf<IGldTransitAccountService>{
    /**
     *@Description 获取中转科目
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/13 14:40
     *@Param
     *@Version 1.0
     **/
    GldTransitAccount getTransitAccount(GldTransitAccount gldTransitAccounts);
}