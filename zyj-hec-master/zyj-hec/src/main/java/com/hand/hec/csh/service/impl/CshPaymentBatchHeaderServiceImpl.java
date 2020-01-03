package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentBatchHeader;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.service.ICshPaymentBatchHeaderService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchHeaderServiceImpl extends BaseServiceImpl<CshPaymentBatchHeader>
                implements ICshPaymentBatchHeaderService {

    @Autowired
    private IFndCompanyService fndCompanyService;

    @Autowired
    private IFndCodingRuleObjectService fndCodingRuleObjectService;

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
    @Override
    public CshPaymentBatchHeader insertBatchHeader(IRequest iRequest, Long accEntityId, Long companyId,
                    Long batchTypeId, Date batchDate, Long paymentMethodId, String paymentCurrencyCode,
                    Long bankAccountId, String draweeBankCode, String draweeBankName, String draweeBankLocationCode,
                    String draweeBankLocation, String draweeProvinceCode, String draweeProvinceName,
                    String draweeCityCode, String draweeCityName, String draweeAccountNumber, String draweeAccountName,
                    Long totalNumber, BigDecimal amount, Long paymentTotalNumber, BigDecimal paymentAmount,
                    Long exportNumber, Long exportUserId, String status, Date paymentCompletedDate,
                    String reversedFlag) {
        CshPaymentBatchHeader cshPaymentBatchHeader = new CshPaymentBatchHeader();
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, companyId);
        String batchNumber = fndCodingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_PAYMENT_BATCH, null,
                        fndCompany.getMagOrgId(), companyId, accEntityId);
        if (StringUtils.isEmpty(batchNumber)) {
            throw new CshTransactionException(CshTransactionException.TRX_CODING_RULE_ERROR,
                            CshTransactionException.TRX_CODING_RULE_ERROR, null);
        }
        cshPaymentBatchHeader.setBatchNumber(batchNumber);
        cshPaymentBatchHeader.setAccEntityId(accEntityId);
        cshPaymentBatchHeader.setCompanyId(companyId);
        cshPaymentBatchHeader.setBatchTypeId(batchTypeId);
        cshPaymentBatchHeader.setBatchDate(batchDate);
        cshPaymentBatchHeader.setPaymentMethodId(paymentMethodId);
        cshPaymentBatchHeader.setPaymentCurrencyCode(paymentCurrencyCode);
        cshPaymentBatchHeader.setBankAccountId(bankAccountId);
        cshPaymentBatchHeader.setDraweeBankCode(draweeBankCode);
        cshPaymentBatchHeader.setDraweeBankName(draweeBankName);
        cshPaymentBatchHeader.setDraweeBankLocationCode(draweeBankLocationCode);
        cshPaymentBatchHeader.setDraweeBankLocation(draweeBankLocation);
        cshPaymentBatchHeader.setDraweeProvinceCode(draweeProvinceCode);
        cshPaymentBatchHeader.setDraweeProvinceName(draweeProvinceName);
        cshPaymentBatchHeader.setDraweeCityCode(draweeCityCode);
        cshPaymentBatchHeader.setDraweeCityName(draweeCityName);
        cshPaymentBatchHeader.setDraweeAccountNumber(draweeAccountNumber);
        cshPaymentBatchHeader.setDraweeAccountName(draweeAccountName);
        cshPaymentBatchHeader.setTotalNumber(totalNumber);
        cshPaymentBatchHeader.setAmount(amount);
        cshPaymentBatchHeader.setPaymentTotalNumber(paymentTotalNumber);
        cshPaymentBatchHeader.setPaymentAmount(paymentAmount);
        cshPaymentBatchHeader.setExportNumber(exportNumber);
        cshPaymentBatchHeader.setExportUserId(exportUserId);
        cshPaymentBatchHeader.setStatus(status);
        cshPaymentBatchHeader.setPaymentCompletedDate(paymentCompletedDate);
        cshPaymentBatchHeader.setPaymentCompletedDateTz(paymentCompletedDate);
        cshPaymentBatchHeader.setPaymentCompletedDateLtz(paymentCompletedDate);
        cshPaymentBatchHeader.setReversedFlag(reversedFlag);
        CshPaymentBatchHeader dto = self().insertSelective(iRequest, cshPaymentBatchHeader);
        return dto;
    }
}
