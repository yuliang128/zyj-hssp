package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshCashFlowFormulaLn;

public interface ICshCashFlowFormulaLnService extends IBaseService<CshCashFlowFormulaLn>, ProxySelf<ICshCashFlowFormulaLnService>{

    /**
     * 现金流量表行查询
     *
     * @param request 请求上下文
     * @param cashFlowItemId 现金流量表Id
     * @param pageNum 页码
     * @param pageSize 页数
     * @author user 2019-02-21 18:40
     * @return
     */

    List<Map> queryByItemId(IRequest request, Long cashFlowItemId, int pageNum, int pageSize);

}