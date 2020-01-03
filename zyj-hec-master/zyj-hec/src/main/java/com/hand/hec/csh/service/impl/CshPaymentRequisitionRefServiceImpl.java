package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentRequisitionRef;
import com.hand.hec.csh.mapper.CshPaymentRequisitionRefMapper;
import com.hand.hec.csh.service.ICshPaymentRequisitionRefService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentRequisitionRefServiceImpl extends BaseServiceImpl<CshPaymentRequisitionRef>
                implements ICshPaymentRequisitionRefService {

    @Autowired
    private CshPaymentRequisitionRefMapper cshPaymentRequisitionRefMapper;

    /**
     * 已引用金额合计
     *
     * @param paymentReqLineId 借款申请行ID
     * @author ngls.luhui 2019-03-11 18:50
     * @return 已引用金额合计
     */
    @Override
    public BigDecimal querySumAmount(Long paymentReqLineId) {
        return cshPaymentRequisitionRefMapper.querySumAmount(paymentReqLineId);
    }

    @Override
    public BigDecimal queryPaidAmount(Long paymentReqLineId) {
        return cshPaymentRequisitionRefMapper.getPaidAmount(paymentReqLineId);
    }

}
