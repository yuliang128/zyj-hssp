package com.hand.hec.csh.service;

import java.math.BigDecimal;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentRequisitionRef;
import org.apache.ibatis.annotations.Param;

public interface ICshPaymentRequisitionRefService
                extends IBaseService<CshPaymentRequisitionRef>, ProxySelf<ICshPaymentRequisitionRefService> {

    /**
     * 已引用金额合计
     *
     * @param paymentReqLineId 借款申请行ID
     * @author ngls.luhui 2019-03-11 18:50
     * @return 已引用金额合计
     */
    BigDecimal querySumAmount(Long paymentReqLineId);

    /**
     * 获取某个借款单行的已支付合计金额
     *
     * @param paymentReqLineId
     * @author mouse 2019-05-07 19:50
     * @return java.math.BigDecimal
     */
    BigDecimal queryPaidAmount(Long paymentReqLineId);
}
