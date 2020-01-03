package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.dto.CshTransactionLine;
import com.hand.hec.csh.mapper.CshTransactionLineMapper;
import com.hand.hec.csh.service.ICshTransactionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 现金事务行ServiceImpl
 * </p>
 * 
 * @author Tagin 2019/01/22 20:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshTransactionLineServiceImpl extends BaseServiceImpl<CshTransactionLine>
                implements ICshTransactionLineService {
    @Autowired
    private CshTransactionLineMapper cshTransactionLineMapper;

    /**
     * @Description 现金事务行录入
     *
     * @Author Tagin
     * @Date 2019-03-19 20:32
     * @param iRequest 请求
     * @param currencyCode 币种
     * @param exchangeRateType 汇率类型
     * @param exchangeRate 汇率
     * @param bankAccountId 付款方银行账户
     * @param documentNum 单据编号
     * @param payeeCategory 收款方类别
     * @param payeeId 收款方ID
     * @param payeeBankAccountId 收款方银行账户
     * @param description 描述
     * @param handlingCharge 手续费
     * @param interest 利息
     * @param agentEmployeeId 经办人
     * @param transInOutType 转入转出标志
     * @param documentCategory 单据类别
     * @param documentTypeId 单据类型ID
     * @param paymentUsedeId 付款用途ID
     * @param paymentMethodId 付款方式ID
     * @param transactionHeaderId 现金事务头ID
     * @param companyId 公司
     * @param accEntityId 核算主体
     * @param transactionAmount 交易金额
     * @Return com.hand.hec.csh.dto.CshTransactionLine
     * @Version 1.0
     **/
    @Override
    public CshTransactionLine insertTrxLine(IRequest iRequest, String currencyCode, String exchangeRateType,
                    BigDecimal exchangeRate, Long bankAccountId, String documentNum, String payeeCategory, Long payeeId,
                    Long payeeBankAccountId, String description, BigDecimal handlingCharge, BigDecimal interest,
                    Long agentEmployeeId, String transInOutType, String documentCategory, Long documentTypeId,
                    Long paymentUsedeId, Long paymentMethodId, Long transactionHeaderId, Long companyId,
                    Long accEntityId, BigDecimal transactionAmount) {
        CshTransactionLine cshTransactionLine = new CshTransactionLine();
        cshTransactionLine.setCurrencyCode(currencyCode);
        cshTransactionLine.setExchangeRateType(exchangeRateType);
        cshTransactionLine.setExchangeRate(exchangeRate);
        cshTransactionLine.setBankAccountId(bankAccountId);
        cshTransactionLine.setDocumentNum(documentNum);
        cshTransactionLine.setPayeeCategory(payeeCategory);
        cshTransactionLine.setPayeeId(payeeId);
        cshTransactionLine.setPayeeBankAccountId(payeeBankAccountId);
        cshTransactionLine.setDescription(description);
        cshTransactionLine.setHandlingCharge(handlingCharge);
        cshTransactionLine.setInterest(interest);
        cshTransactionLine.setAgentEmployeeId(agentEmployeeId);
        cshTransactionLine.setTransInOutType(transInOutType);
        cshTransactionLine.setDocumentCategory(documentCategory);
        cshTransactionLine.setDocumentTypeId(documentTypeId);
        cshTransactionLine.setPaymentUsedeId(paymentUsedeId);
        cshTransactionLine.setPaymentMethodId(paymentMethodId);
        cshTransactionLine.setTransactionHeaderId(transactionHeaderId);
        cshTransactionLine.setCompanyId(companyId);
        cshTransactionLine.setAccEntityId(accEntityId);
        cshTransactionLine.setTransactionAmount(transactionAmount);
        return self().insertSelective(iRequest, cshTransactionLine);
    }

    /**
     * 根据现金事务头ID获取现金事务行
     *
     * @author Tagin
     * @date 2019-03-21 19:32
     * @param transactionHeaderId 现金事务头ID
     * @return List<CshTransactionLine>
     * @version 1.0
     **/
    @Override
    public List<CshTransactionLine> queryTrxLine(IRequest iRequest, Long transactionHeaderId) {
        return cshTransactionLineMapper.queryTrxLine(transactionHeaderId);
    }

    /**
     * 获取现金事务预付款现金事务核销记录
     *
     * @author Tagin
     * @date 2019-05-28 11:07
     * @param transactionHeaderId 现金事务头 ID
     * @return java.lang.Long
     * @version 1.0
     **/
    @Override
    public Long totalPreCount(Long transactionHeaderId) {
        return cshTransactionLineMapper.totalPreCount(transactionHeaderId);
    }

    @Override
    public List<CshTransactionHeader>queryByReqHdsId(IRequest request, Long requisitionHdsId, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        return cshTransactionLineMapper.queryByReqHdsId(requisitionHdsId);
    }
}
