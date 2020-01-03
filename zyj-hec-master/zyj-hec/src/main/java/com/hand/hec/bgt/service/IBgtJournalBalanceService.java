package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtJournalBalance;

/**
 * <p>
 * 预算余额Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:56
 */
public interface IBgtJournalBalanceService
                extends IBaseService<BgtJournalBalance>, ProxySelf<IBgtJournalBalanceService> {
    /**
     * 创建预算BALANCE
     *
     * @param journalHeaderId 预算日记账ID
     * @author guiyuting 2019-03-27 19:33
     * @return
     */
    BgtJournalBalance createJournalBalance(IRequest request, Long journalHeaderId);



}
