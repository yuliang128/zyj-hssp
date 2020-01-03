package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBalanceQueryResult;

import java.util.List;

public interface IBgtBalanceQueryResultService
                extends IBaseService<BgtBalanceQueryResult>, ProxySelf<IBgtBalanceQueryResultService> {

    /**
     * 对结果进行汇总
     *
     * @param request
     * @author mouse 2019-04-24 20:29
     * @return void
     */
    void groupResult(IRequest request, BgtBalanceQueryGroup group, String groupField);

    /**
     *
     * 获取最小的预算期间Num
     *
     * @author mouse 2019-04-25 11:27
     * @return java.lang.Long
     */
    Long getMinPeriodNum(IRequest request);

    /**
     *
     * 获取最大的预算期间Num
     *
     * @author mouse 2019-04-25 11:27
     * @return java.lang.Long
     */
    Long getMaxPeriodNum(IRequest request);

    /**
     * 期间汇总
     *
     * @param request
     * @param group
     * @author mouse 2019-04-25 14:15
     * @return void
     */
    void periodSummary(IRequest request, BgtBalanceQueryGroup group, String periodDesc);

    /**
     * 预算余额查询 - 结果查询
     *
     * @param quantityAmountCode
     * @author guiyuting 2019-05-16 16:52
     * @return 
     */
    List<BgtBalanceQueryResult> getBudgetBalanceResult(IRequest request, BgtBalanceQueryResult balanceQueryResult, int page,
                    int pageSize);

    void deleteBalanceResult(IRequest request);
}
