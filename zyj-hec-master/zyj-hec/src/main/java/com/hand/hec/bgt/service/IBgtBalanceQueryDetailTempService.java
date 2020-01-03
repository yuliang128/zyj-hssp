package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryDetailTemp;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;

public interface IBgtBalanceQueryDetailTempService
                extends IBaseService<BgtBalanceQueryDetailTemp>, ProxySelf<IBgtBalanceQueryDetailTempService> {

    /**
     * 生成预算余额的最明细数据
     *
     * @param request
     * @param group
     * @param filterField
     * @param periodStrategy
     * @author mouse 2019-04-23 16:10
     * @return void
     */
    void generateBalanceDetail(IRequest request, BgtBalanceQueryGroup group, String filterField, String periodStrategy);

    /**
     * 生成预算占用额的最明细数据
     *
     * @param request
     * @param group
     * @param filterField
     * @param periodStrategy
     * @author mouse 2019-04-23 16:10
     * @return void
     */
    void generateReserveDetail(IRequest request, BgtBalanceQueryGroup group, String filterField, String periodStrategy);

    /**
     * 清除明细数据
     *
     * @param request
     * @author mouse 2019-04-25 14:34
     * @return void
     */
    void cleanDetailTemp(IRequest request);

}
