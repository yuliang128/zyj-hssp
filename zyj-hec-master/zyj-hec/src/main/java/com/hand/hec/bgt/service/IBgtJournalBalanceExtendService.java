package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtJournalBalanceExtend;

/**
 * <p>
 * 预算余额币种扩展Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:59
 */
public interface IBgtJournalBalanceExtendService
                extends IBaseService<BgtJournalBalanceExtend>, ProxySelf<IBgtJournalBalanceExtendService> {
    /**
     * 计算预算余额的扩展币种金额
     *
     * @param balanceId
     * @author guiyuting 2019-05-17 14:28
     * @return
     */
    void calculateBalanceExtend(IRequest request, Long balanceId);

}
