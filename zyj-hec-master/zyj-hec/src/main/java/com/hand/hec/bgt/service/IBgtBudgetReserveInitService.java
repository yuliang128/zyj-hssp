package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBudgetReserveInit;
import org.apache.ibatis.annotations.Param;

/**
 * @author mouse
 */
public interface IBgtBudgetReserveInitService
                extends IBaseService<BgtBudgetReserveInit>, ProxySelf<IBgtBudgetReserveInitService> {

    /**
     * 初始化预算占用数据
     *
     * @param group
     * @author mouse 2019-04-18 10:46
     * @return void
     */
    void initReserveData(IRequest request, BgtBalanceQueryGroup group);

    /**
     * 清除预算占用数据
     *
     *
     * @author mouse 2019-04-18 10:47
     * @return void
     */
    void cleanReserveData(IRequest request);
}
