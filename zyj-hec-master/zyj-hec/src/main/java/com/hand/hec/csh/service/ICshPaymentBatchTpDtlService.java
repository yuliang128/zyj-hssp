package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatchTpDtl;

public interface ICshPaymentBatchTpDtlService
                extends IBaseService<CshPaymentBatchTpDtl>, ProxySelf<ICshPaymentBatchTpDtlService> {

    /**
     * 获取付款批类型中的组批规则
     *
     * @Author Tagin
     * @Date 2019-03-19 12:35
     * @param iRequest 请求
     * @param typeId 付款批类型ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshPaymentBatchTpDtl>
     * @Version 1.0
     **/
    List<CshPaymentBatchTpDtl> queryCondition(IRequest iRequest, Long typeId);

}
