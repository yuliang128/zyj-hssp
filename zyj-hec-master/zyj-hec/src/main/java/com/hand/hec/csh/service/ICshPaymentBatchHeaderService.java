package com.hand.hec.csh.service;

import java.math.BigDecimal;
import java.util.Date;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatchHeader;

public interface ICshPaymentBatchHeaderService
                extends IBaseService<CshPaymentBatchHeader>, ProxySelf<ICshPaymentBatchHeaderService> {

    /**
     * 付款批头录入
     *
     * @author Tagin
     * @date 2019-04-08 22:38
     * @param iRequest 请求
     * @param accEntityId 核算主体ID
     * @param companyId 公司ID
     * @param batchTypeId 付款批类型ID
     * @param batchDate 日期
     * @param paymentMethodId 付款方式ID
     * @param paymentCurrencyCode 支付币种代码
     * @param bankAccountId 银行账户ID
     * @param draweeBankCode 付款方银行代码
     * @param draweeBankName 付款方银行名称
     * @param draweeBankLocationCode 付款方分行代码
     * @param draweeBankLocation 付款方分行地址
     * @param draweeProvinceCode 付款方分行省代码
     * @param draweeProvinceName 付款方分行省名称
     * @param draweeCityCode 付款方城市代码
     * @param draweeCityName 付款方城市名称
     * @param draweeAccountNumber 付款方银行账号
     * @param draweeAccountName 付款方户名
     * @param totalNumber 总笔数
     * @param amount 金额
     * @param paymentTotalNumber 支付总笔数
     * @param paymentAmount 支付金额
     * @param exportNumber 导出次数
     * @param exportUserId 导出用户
     * @param status 状态
     * @param paymentCompletedDate 完全支付日期
     * @param reversedFlag 反冲标志
     * @return com.hand.hec.csh.dto.CshPaymentBatchHeader
     * @version 1.0
     **/
    CshPaymentBatchHeader insertBatchHeader(IRequest iRequest, Long accEntityId, Long companyId, Long batchTypeId,
                    Date batchDate, Long paymentMethodId, String paymentCurrencyCode, Long bankAccountId,
                    String draweeBankCode, String draweeBankName, String draweeBankLocationCode,
                    String draweeBankLocation, String draweeProvinceCode, String draweeProvinceName,
                    String draweeCityCode, String draweeCityName, String draweeAccountNumber, String draweeAccountName,
                    Long totalNumber, BigDecimal amount, Long paymentTotalNumber, BigDecimal paymentAmount,
                    Long exportNumber, Long exportUserId, String status, Date paymentCompletedDate,
                    String reversedFlag);

}
