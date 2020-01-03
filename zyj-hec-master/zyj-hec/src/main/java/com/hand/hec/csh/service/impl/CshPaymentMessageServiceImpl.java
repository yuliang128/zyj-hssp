package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentMessage;
import com.hand.hec.csh.dto.CshPaymentMethod;
import com.hand.hec.csh.mapper.CshPaymentMethodMapper;
import com.hand.hec.csh.service.ICshPaymentMessageService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentMessageServiceImpl extends BaseServiceImpl<CshPaymentMessage>
                implements ICshPaymentMessageService {

    @Autowired
    private CshPaymentMethodMapper cshPaymentMethodMapper;

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
    @Override
    public void createPaymentMessage(IRequest iRequest, Long accEntityId, Long companyId, String currencyCode,
                    String payeeCategory, Long payeeId, Date payeeDate, BigDecimal amount, String description,
                    Long paymentMethodId, String accountName, String accountNumber, String documentType,
                    Long documentId, String documentNumber, Long documentLineId) {
        String ebankingFlag = null, offerFlag = null;
        // 获取支付方式
        CshPaymentMethod cshPaymentMethod = cshPaymentMethodMapper.selectByPrimaryKey(paymentMethodId);
        if (CshPaymentMethod.PAY_CODE_METHOD_10.equals(cshPaymentMethod.getPayMethodCode())) {
            ebankingFlag = BaseConstants.YES;
            offerFlag = BaseConstants.NO;
        } else if (CshPaymentMethod.PAY_CODE_METHOD_20.equals(cshPaymentMethod.getPayMethodCode())) {
            ebankingFlag = BaseConstants.NO;
            offerFlag = BaseConstants.YES;
        } else if (CshPaymentMethod.PAY_CODE_METHOD_30.equals(cshPaymentMethod.getPayMethodCode())) {
            ebankingFlag = BaseConstants.NO;
            offerFlag = BaseConstants.NO;
        }

        CshPaymentMessage cshPaymentMessage = new CshPaymentMessage();
        cshPaymentMessage.setAccEntityId(accEntityId);
        cshPaymentMessage.setCompanyId(companyId);
        cshPaymentMessage.setCurrencyCode(currencyCode);
        cshPaymentMessage.setPayeeCategory(payeeCategory);
        cshPaymentMessage.setPayeeId(payeeId);
        cshPaymentMessage.setPayeeDate(payeeDate);
        cshPaymentMessage.setAmount(amount);
        cshPaymentMessage.setDescription(description);
        cshPaymentMessage.setPaymentMethodId(paymentMethodId);
        cshPaymentMessage.setAccountName(accountName);
        cshPaymentMessage.setAccountNumber(accountNumber);
        cshPaymentMessage.setDocumentType(documentType);
        cshPaymentMessage.setDocumentId(documentId);
        cshPaymentMessage.setDocumentNumber(documentNumber);
        cshPaymentMessage.setDocumentLineId(documentLineId);
        cshPaymentMessage.setEbankingFlag(ebankingFlag);
        cshPaymentMessage.setOfferFlag(offerFlag);

        self().insertSelective(iRequest, cshPaymentMessage);

    }
}
