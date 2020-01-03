package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondH;

/**
 * <p>
 * 预算余额查询方案头service
 * </p>
 *
 * @author YHL 2019/03/13 18:19
 */
public interface IBgtBalanceQueryCondHService
        extends IBaseService<BgtBalanceQueryCondH>, ProxySelf<IBgtBalanceQueryCondHService> {

    /**
     * 根据预算组织ID查询预算余额查询方案
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-18 10:43
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondH> 预算余额查询方案
     */
    List<BgtBalanceQueryCondH> getBalanceQueryCondH(IRequest request, Long bgtOrgId);

}