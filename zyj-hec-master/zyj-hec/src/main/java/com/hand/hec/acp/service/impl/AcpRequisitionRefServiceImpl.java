package com.hand.hec.acp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpRequisitionRef;
import com.hand.hec.acp.mapper.AcpRequisitionRefMapper;
import com.hand.hec.acp.service.IAcpRequisitionRefService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpRequisitionRefServiceImpl extends BaseServiceImpl<AcpRequisitionRef>
                implements IAcpRequisitionRefService {

    @Autowired
    private AcpRequisitionRefMapper acpRequisitionRefMapper;

    /**
     * 获取付款申请单行已付金额
     *
     * @author Tagin
     * @date 2019-04-30 10:09
     * @param requisitionLnsId 付款申请单行 ID
     * @return java.math.BigDecimal
     * @version 1.0
     **/
    @Override
    public BigDecimal getPaidAmount(Long requisitionLnsId) {
        return acpRequisitionRefMapper.getPaidAmount(requisitionLnsId);
    }

}
