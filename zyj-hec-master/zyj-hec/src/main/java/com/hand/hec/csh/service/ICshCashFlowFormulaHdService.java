package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshCashFlowFormulaHd;

public interface ICshCashFlowFormulaHdService extends IBaseService<CshCashFlowFormulaHd>, ProxySelf<ICshCashFlowFormulaHdService>{
    /**
     *
     *
     * @param request  请求上下文
     * @param cashFlowItemId  现金流量表Id
     * @author dingwei.ma@hand-china.com 2019-02-21 16:58
     * @return CshCashFlowFormulaHd
     */
    CshCashFlowFormulaHd queryFormulaByItemId(IRequest request,Long cashFlowItemId);

    /**
     *
     * @param request  请求上下文
     * @param cashFlowItemId  现金流量表Id
     * @param setOfBooksId  账套Id
     * @author dingwei.ma@hand-china.com 2019-02-21 16:58
     * @return CshCashFlowFormulaHd
     */
    List<Map> queryCashFlowLineNumber(IRequest request,Long cashFlowItemId, Long setOfBooksId);
}