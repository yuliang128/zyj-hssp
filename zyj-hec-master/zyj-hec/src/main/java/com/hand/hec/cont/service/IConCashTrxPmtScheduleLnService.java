package com.hand.hec.cont.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.cont.dto.ConCashTrxPmtScheduleLn;

public interface IConCashTrxPmtScheduleLnService
                extends IBaseService<ConCashTrxPmtScheduleLn>, ProxySelf<IConCashTrxPmtScheduleLnService> {

    /**
     * 校验合同资金计划
     *
     * @author Tagin
     * @date 2019-04-03 19:13
     * @param transactionLineId 现金事务行ID
     * @return java.lang.String
     * @version 1.0
     **/
    String validateConSchedule(Long transactionLineId);

}
