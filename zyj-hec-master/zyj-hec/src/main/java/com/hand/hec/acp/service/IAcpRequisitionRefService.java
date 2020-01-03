package com.hand.hec.acp.service;

import java.math.BigDecimal;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionRef;

public interface IAcpRequisitionRefService
                extends IBaseService<AcpRequisitionRef>, ProxySelf<IAcpRequisitionRefService> {

    /**
     * 获取付款申请单行已付金额
     *
     * @author Tagin
     * @date 2019-04-30 10:09
     * @param requisitionLnsId 付款申请单行 ID
     * @return java.math.BigDecimal
     * @version 1.0
     **/
    BigDecimal getPaidAmount(Long requisitionLnsId);
}
