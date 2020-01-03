package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPaymentUsed;

import java.util.List;

/**
 * <p>
 * 付款用途Service接口
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
public interface ICshMoPaymentUsedService extends IBaseService<CshMoPaymentUsed>, ProxySelf<ICshMoPaymentUsedService> {
    /**
     * 查询符合条件的付款用途
     * 
     * @param request
     * @param cshMoPaymentUsed
     * @param pageNum
     * @param pageSize
     * @return 符合条件的付款用途
     */
    List<CshMoPaymentUsed> queryAll(IRequest request, CshMoPaymentUsed cshMoPaymentUsed, int pageNum, int pageSize);

    /**
     * @Description 获取公司（管理组织）下启用的付款用途
     *
     * @Author Tagin
     * @Date 2019-03-04 20:50
     * @param iRequest
     * @Return java.util.List<com.hand.hec.csh.dto.CshMoPaymentUsed>
     * @Version 1.0
     **/
    List<CshMoPaymentUsed> queryPaymentUsed(IRequest iRequest);

}
