package com.hand.hec.csh.service;

import java.math.BigDecimal;
import java.util.Date;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentMessage;

public interface ICshPaymentMessageService
                extends IBaseService<CshPaymentMessage>, ProxySelf<ICshPaymentMessageService> {

    /**
     * @Description 创建单据支付信息
     *
     * @Author Tagin
     * @Date 2019-03-21 15:32
     * @param iRequest 请求
     * @param accEntityId 核算主体ID
     * @param companyId 公司ID
     * @param currencyCode 币种
     * @param payeeCategory 收款方类型
     * @param payeeId 收款方
     * @param payeeDate 支付日期
     * @param amount 金额
     * @param description 描述
     * @param paymentMethodId 付款方式ID
     * @param accountName 收款方户名
     * @param accountNumber 收款方账户
     * @param documentType 单据类型
     * @param documentId 单据头ID
     * @param documentNumber 单据编号
     * @param documentLineId 单据行ID
     * @Return void
     * @Version 1.0
     **/
    void createPaymentMessage(IRequest iRequest, Long accEntityId, Long companyId, String currencyCode,
                    String payeeCategory, Long payeeId, Date payeeDate, BigDecimal amount, String description,
                    Long paymentMethodId, String accountName, String accountNumber, String documentType,
                    Long documentId, String documentNumber, Long documentLineId);

}
