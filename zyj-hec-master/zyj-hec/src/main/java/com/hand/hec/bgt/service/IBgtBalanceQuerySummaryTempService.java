package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBalanceQuerySummaryTemp;

public interface IBgtBalanceQuerySummaryTempService
                extends IBaseService<BgtBalanceQuerySummaryTemp>, ProxySelf<IBgtBalanceQuerySummaryTempService> {

    /**
     * 生成汇总查询数据
     *
     * @param request
     * @param group
     * @param filterField
     * @author mouse 2019-04-23 17:13
     * @return void
     */
    void generateSummary(IRequest request, BgtBalanceQueryGroup group, String filterField);

    /**
     * 清除汇总临时数据
     *
     * @param request
     * @author mouse 2019-04-25 14:46
     * @return void
     */
    void cleanSummaryTemp(IRequest request);
}
