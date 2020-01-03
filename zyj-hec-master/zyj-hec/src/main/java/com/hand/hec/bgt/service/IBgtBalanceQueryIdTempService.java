package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryIdTemp;

public interface IBgtBalanceQueryIdTempService extends IBaseService<BgtBalanceQueryIdTemp>, ProxySelf<IBgtBalanceQueryIdTempService>{

    /**
     * 清除ID的临时数据
     *
     * @param request
     * @author mouse 2019-04-25 14:32
     * @return void
     */
    void cleanIdTemp(IRequest request);
}