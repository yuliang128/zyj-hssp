package com.hand.hec.csh.service;

import java.math.BigDecimal;
import java.util.Date;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatchLine;

public interface ICshPaymentBatchLineService
                extends IBaseService<CshPaymentBatchLine>, ProxySelf<ICshPaymentBatchLineService> {

    /**
     * 付款批行录入
     *
     * @author Tagin
     * @date 2019-04-09 10:24
     * @param iRequest 请求
     * @param batchHeaderId 付款批头ID
     * @param accEntityId 核算主体ID
     * @param companyId 公司ID
     * @param transactionLineId 现金事务行ID
     * @param payeeCategory 收款方类型
     * @param payeeId 收款方ID
     * @param payeeBankCode 收款方银行代码
     * @param payeeBankName 收款方银行名称
     * @param payeeBankLocationCode 收款方分行代码
     * @param payeeBankLocation 收款方分行名称
     * @param payeeProvinceCode 收款方省代码
     * @param payeeProvinceName 收款方省名称
     * @param payeeCityCode 收款方市代码
     * @param payeeCityName 收款方市名称
     * @param payeeAccountNumber 收款方银行账号
     * @param payeeAccountName 收款方户名
     * @param amount 金额
     * @param paymentStatus 支付状态
     * @param reversedFlag 反冲标志
     * @param reversedDate 反冲日期
     * @param returnedFlag 退款标志
     * @param sourceLineId 来源行ID
     * @return com.hand.hec.csh.dto.CshPaymentBatchLine
     * @version 1.0
     **/
    CshPaymentBatchLine insertBatchLine(IRequest iRequest, Long batchHeaderId, Long accEntityId, Long companyId,
                    Long transactionLineId, String payeeCategory, Long payeeId, String payeeBankCode,
                    String payeeBankName, String payeeBankLocationCode, String payeeBankLocation,
                    String payeeProvinceCode, String payeeProvinceName, String payeeCityCode, String payeeCityName,
                    String payeeAccountNumber, String payeeAccountName, BigDecimal amount, String paymentStatus,
                    String reversedFlag, Date reversedDate, String returnedFlag, Long sourceLineId);

}
