package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetReserveExtend;
/**
 * <p>
 * 预算占用币种扩展Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:55
 */
public interface IBgtBudgetReserveExtendService extends IBaseService<BgtBudgetReserveExtend>, ProxySelf<IBgtBudgetReserveExtendService>{
    /**
     * 归档预算占用扩展表
     *
     * @param budgetReserveLineId 预算保留余额行ID
     * @author guiyuting 2019-05-17 14:17
     * @return
     */
    void archiveReserveExtend(IRequest request, Long budgetReserveLineId);
}