package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshRepaymentRegisterDist;

public interface ICshRepaymentRegisterDistService extends IBaseService<CshRepaymentRegisterDist>, ProxySelf<ICshRepaymentRegisterDistService> {

    /**
     * 还款申请单出纳-分配预付款-预付款查询
     *
     * @param request
     * @param registerLnId 还款申请单行 ID
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterDist>
     * @author jialin.xing@hand-china.com 2019-05-14 10:44
     */
    List<CshRepaymentRegisterDist> baseSelect(IRequest request, Long registerLnId);

    List<Map> queryTrxById(Long reqLnId, String paymentNum, String transactionNum);
}