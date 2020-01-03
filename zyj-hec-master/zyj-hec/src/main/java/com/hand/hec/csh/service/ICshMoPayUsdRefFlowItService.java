package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPayUsdRefFlowIt;

import java.util.List;
/**
 * <p>
 * 付款用途联系现金流量项Service接口
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
public interface ICshMoPayUsdRefFlowItService extends IBaseService<CshMoPayUsdRefFlowIt>, ProxySelf<ICshMoPayUsdRefFlowItService>{
    /**
     * 查询符合条件的付款用途联系现金流量项（三表）
     * @param request
     * @param cshMoPayUsdRefFlowIt
     * @param pageNum
     * @param pageSize
     * @return 符合条件的付款用途联系现金流量项
     */
    List<CshMoPayUsdRefFlowIt> queryIncludeSetOfBooks(IRequest request,CshMoPayUsdRefFlowIt cshMoPayUsdRefFlowIt,int pageNum, int pageSize);


    /**
     * <p>获取现金流量项(对应exp_util_pkg.get_cash_flow_item_id)<p/>
     *
     * @param paymentUsedeId 付款用途ID
     * @param accEntityId 核算主体ID
     * @return 现金流量项ID
     * @author yang.duan 2019/3/13 18:38
     */
    Long getCashFlowItemId(Long paymentUsedeId, Long accEntityId);
}