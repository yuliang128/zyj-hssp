package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.mapper.AcpMoPayReqTypeMapper;
import com.hand.hec.acp.service.IAcpRequisitionHdService;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.exception.CshWriteOffException;
import com.hand.hec.csh.mapper.CshMoPaymentReqTypeMapper;
import com.hand.hec.csh.mapper.CshTransactionLineMapper;
import com.hand.hec.csh.mapper.CshWriteOffAccountMapper;
import com.hand.hec.csh.service.ICshTransactionAccountService;
import com.hand.hec.csh.service.ICshTransactionHeaderService;
import com.hand.hec.csh.service.ICshWriteOffAccountService;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.mapper.ExpReportHeaderMapper;
import com.hand.hec.expm.mapper.ExpReportPmtScheduleMapper;
import com.hand.hec.gld.constants.UsageCodeConstants;
import com.hand.hec.gld.dto.GldSegment;
import com.hand.hec.gld.service.IGldSobSegmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核销凭证表ServiceImpl
 * </p>
 *
 * @author Tagin 2019/01/22 10:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshWriteOffAccountServiceImpl extends BaseServiceImpl<CshWriteOffAccount>
                implements ICshWriteOffAccountService {
    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;

    @Autowired
    private CshTransactionLineMapper cshTransactionLineMapper;

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    private ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    @Autowired
    private CshMoPaymentReqTypeMapper cshMoPaymentReqTypeMapper;

    @Autowired
    private AcpMoPayReqTypeMapper acpMoPayReqTypeMapper;

    @Autowired
    private ICshTransactionAccountService cshTransactionAccountService;

    @Autowired
    private IAcpRequisitionHdService acpRequisitionHdService;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @Autowired
    private CshWriteOffAccountMapper cshWriteOffAccountMapper;

    /**
     * 生成核销凭证-借方凭证
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @param expReportHeader 报销单头对象
     * @param expReportPmtSchedule 计划付款行对象
     * @param cshTransactionLine 现金事务行对象
     * @param funPrecision 本位币财务精度
     * @param respCenterId 成本中心ID
     * @Author Tagin
     * @Date 2019-02-21 19:29
     * @Return java.math.BigDecimal
     * @Version 1.0
     **/
    public BigDecimal createDrAccount(IRequest iRequest, CshWriteOff cshWriteOff, ExpReportHeader expReportHeader,
                    ExpReportPmtSchedule expReportPmtSchedule, CshTransactionLine cshTransactionLine, int funPrecision,
                    Long respCenterId) {
        // 1.0 核销凭证对象
        CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
        BigDecimal functionalAmount;
        // 2.0 获取报销单借方凭证【Tips：若报销单未审核则没费用凭证，直接获取报销单费用凭证贷方（清算科目用途代码）】
        ExpReportAccount expReportAccount = cshTransactionHeaderService.getReportCrAccount(expReportPmtSchedule);
        if (expReportAccount == null || expReportAccount.getAccountId() == null
                        || expReportAccount.getAccountId() == 0) {
            throw new CshWriteOffException(CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND,
                            CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND, null);
        }
        // 3.0 判断
        if (expReportAccount.getExpReportJeLineId() != null) {
            // 存在报销单凭证数据数据
            cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
            cshWriteOffAccount.setCompanyId(expReportHeader.getCompanyId());
            cshWriteOffAccount.setAccEntityId(expReportHeader.getAccEntityId());
            cshWriteOffAccount.setRespCenterId(expReportAccount.getRespCenterId());
            cshWriteOffAccount.setAccountId(expReportAccount.getAccountId());
            cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshWriteOffAccount.setExchangeRateType(expReportAccount.getExchangeRateType());
            cshWriteOffAccount.setExchangeRate(expReportAccount.getExchangeRate());
            cshWriteOffAccount.setEnteredAmountDr(cshWriteOff.getCshWriteOffAmount());
            cshWriteOffAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(expReportAccount.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
            cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshWriteOffAccount.setGldInterfaceFlag(cshWriteOff.getGldInterfaceFlag());
            cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_WRITE_OFF);
            cshWriteOffAccount.setAccountSegment1(expReportAccount.getAccountSegment1());
            cshWriteOffAccount.setAccountSegment2(expReportAccount.getAccountSegment2());
            cshWriteOffAccount.setAccountSegment3(expReportAccount.getAccountSegment3());
            cshWriteOffAccount.setAccountSegment4(expReportAccount.getAccountSegment4());
            cshWriteOffAccount.setAccountSegment5(expReportAccount.getAccountSegment5());
            cshWriteOffAccount.setAccountSegment6(expReportAccount.getAccountSegment6());
            cshWriteOffAccount.setAccountSegment7(expReportAccount.getAccountSegment7());
            cshWriteOffAccount.setAccountSegment8(expReportAccount.getAccountSegment8());
            cshWriteOffAccount.setAccountSegment9(expReportAccount.getAccountSegment9());
            cshWriteOffAccount.setAccountSegment10(expReportAccount.getAccountSegment10());
            cshWriteOffAccount.setAccountSegment11(expReportAccount.getAccountSegment11());
            cshWriteOffAccount.setAccountSegment12(expReportAccount.getAccountSegment12());
            cshWriteOffAccount.setAccountSegment13(expReportAccount.getAccountSegment13());
            cshWriteOffAccount.setAccountSegment14(expReportAccount.getAccountSegment14());
            cshWriteOffAccount.setAccountSegment15(expReportAccount.getAccountSegment15());
            cshWriteOffAccount.setAccountSegment16(expReportAccount.getAccountSegment16());
            cshWriteOffAccount.setAccountSegment17(expReportAccount.getAccountSegment17());
            cshWriteOffAccount.setAccountSegment18(expReportAccount.getAccountSegment18());
            cshWriteOffAccount.setAccountSegment19(expReportAccount.getAccountSegment19());
            cshWriteOffAccount.setAccountSegment20(expReportAccount.getAccountSegment20());
            self().insertSelective(iRequest, cshWriteOffAccount);
            functionalAmount = cshWriteOff.getCshWriteOffAmount().multiply(expReportAccount.getExchangeRate())
                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP);
        } else {
            // 不存在报销单凭证数据
            cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
            cshWriteOffAccount.setCompanyId(expReportHeader.getCompanyId());
            cshWriteOffAccount.setAccEntityId(expReportHeader.getAccEntityId());
            cshWriteOffAccount.setRespCenterId(respCenterId);
            cshWriteOffAccount.setAccountId(expReportAccount.getAccountId());
            cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshWriteOffAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshWriteOffAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshWriteOffAccount.setEnteredAmountDr(cshWriteOff.getCshWriteOffAmount());
            cshWriteOffAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
            cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshWriteOffAccount.setGldInterfaceFlag(cshWriteOff.getGldInterfaceFlag());
            cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_WRITE_OFF);
            // Tips：此时报销单未生成审核凭证因此段值不更新。费用报销单后续审核时会回写核销凭证借方段值
            self().insertSelective(iRequest, cshWriteOffAccount);
            functionalAmount = cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP);
        }
        return functionalAmount;
    }

    /**
     * 生成核销凭证-贷方凭证
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @param expReportHeader 报销单头对象
     * @param cshTransactionLine 预付款现金事务行对象
     * @param funPrecision 本位币财务精度
     * @Author Tagin
     * @Date 2019-02-22 20:13
     * @Return java.math.BigDecimal
     * @Version 1.0
     **/
    public BigDecimal createCrAccount(IRequest iRequest, CshWriteOff cshWriteOff, ExpReportHeader expReportHeader,
                    CshTransactionLine cshTransactionLine, int funPrecision) {
        // 1.0 核销凭证对象
        CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
        BigDecimal functionalAmount;
        // 2.0 获取借款申请单审核/支付凭证【Tips：核销凭证的贷方取借款审核凭证/借款支付凭证的借方】
        CshPaymentReqAccount cshPaymentReqAccount = cshTransactionHeaderService.getPaymentDrAccount(cshTransactionLine,
                        cshMoPaymentReqTypeMapper.getAuitFlag(null, cshWriteOff.getRequisitionHeaderId()));
        if (cshPaymentReqAccount == null || cshPaymentReqAccount.getAccountId() == null) {
            throw new CshWriteOffException(CshWriteOffException.PAYMENT_REQUISITION_ACCOUNT_NOT_FOUND,
                            CshWriteOffException.PAYMENT_REQUISITION_ACCOUNT_NOT_FOUND, null);
        }
        // 3.0 生成凭证
        cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
        cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
        cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
        cshWriteOffAccount.setCompanyId(expReportHeader.getCompanyId());
        cshWriteOffAccount.setAccEntityId(expReportHeader.getAccEntityId());
        cshWriteOffAccount.setRespCenterId(cshPaymentReqAccount.getRespCenterId());
        cshWriteOffAccount.setAccountId(cshPaymentReqAccount.getAccountId());
        cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
        cshWriteOffAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
        cshWriteOffAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
        cshWriteOffAccount.setEnteredAmountCr(cshWriteOff.getCshWriteOffAmount());
        cshWriteOffAccount.setFunctionalAmountCr(
                        cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                        .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
        cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
        cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
        cshWriteOffAccount.setGldInterfaceFlag(cshWriteOff.getGldInterfaceFlag());
        cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT_WRITE_OFF);
        cshWriteOffAccount.setAccountSegment1(cshPaymentReqAccount.getAccountSegment1());
        cshWriteOffAccount.setAccountSegment2(cshPaymentReqAccount.getAccountSegment2());
        cshWriteOffAccount.setAccountSegment3(cshPaymentReqAccount.getAccountSegment3());
        cshWriteOffAccount.setAccountSegment4(cshPaymentReqAccount.getAccountSegment4());
        cshWriteOffAccount.setAccountSegment5(cshPaymentReqAccount.getAccountSegment5());
        cshWriteOffAccount.setAccountSegment6(cshPaymentReqAccount.getAccountSegment6());
        cshWriteOffAccount.setAccountSegment7(cshPaymentReqAccount.getAccountSegment7());
        cshWriteOffAccount.setAccountSegment8(cshPaymentReqAccount.getAccountSegment8());
        cshWriteOffAccount.setAccountSegment9(cshPaymentReqAccount.getAccountSegment9());
        cshWriteOffAccount.setAccountSegment10(cshPaymentReqAccount.getAccountSegment10());
        cshWriteOffAccount.setAccountSegment11(cshPaymentReqAccount.getAccountSegment11());
        cshWriteOffAccount.setAccountSegment12(cshPaymentReqAccount.getAccountSegment12());
        cshWriteOffAccount.setAccountSegment13(cshPaymentReqAccount.getAccountSegment13());
        cshWriteOffAccount.setAccountSegment14(cshPaymentReqAccount.getAccountSegment14());
        cshWriteOffAccount.setAccountSegment15(cshPaymentReqAccount.getAccountSegment15());
        cshWriteOffAccount.setAccountSegment16(cshPaymentReqAccount.getAccountSegment16());
        cshWriteOffAccount.setAccountSegment17(cshPaymentReqAccount.getAccountSegment17());
        cshWriteOffAccount.setAccountSegment18(cshPaymentReqAccount.getAccountSegment18());
        cshWriteOffAccount.setAccountSegment19(cshPaymentReqAccount.getAccountSegment19());
        cshWriteOffAccount.setAccountSegment20(cshPaymentReqAccount.getAccountSegment20());
        self().insertSelective(iRequest, cshWriteOffAccount);
        functionalAmount = cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                        .setScale(funPrecision, BigDecimal.ROUND_HALF_UP);
        return functionalAmount;
    }

    /**
     * 生成汇率差异凭证
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @param cshTransactionLine 现金事务行对象
     * @param revaluationAmount 汇率差异金额
     * @param funPrecision 本位币财务精度
     * @Author Tagin
     * @Date 2019-02-25 13:45
     * @Return void
     * @Version 1.0
     **/
    public void createRevaluationAccount(IRequest iRequest, String docCategory, CshWriteOff cshWriteOff,
                    CshTransactionLine cshTransactionLine, BigDecimal revaluationAmount, int funPrecision) {
        // 1.0 核销凭证对象
        CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
        GldSegment gldSegment = new GldSegment();
        Long accountId = null, companyId = null, positionId = null, accEntityId = null, respCenterId = null,
                        setOfBooksId = null;
        if (CshWriteOff.DOC_SOURCE_EXPENSE_REPORT.equals(docCategory)) {
            // 2.0 报销单头信息
            ExpReportHeader expReportHeader =
                            expReportHeaderMapper.getExpReportHeader(cshWriteOff.getDocumentHeaderId());
            companyId = expReportHeader.getCompanyId();
            positionId = expReportHeader.getPositionId();
            accEntityId = expReportHeader.getAccEntityId();
            respCenterId = expReportHeader.getRespCenterId();
            setOfBooksId = expReportHeader.getSetOfBooksId();
        } else if (CshWriteOff.PREPAYMENT_ACP_REQUISITION.equals(docCategory)) {
            // 3.0 付款单头信息
            AcpRequisitionHd acpRequisitionHd = acpRequisitionHdService.getAcpRequisitionHeader(iRequest,
                            cshWriteOff.getDocumentHeaderId());
            companyId = acpRequisitionHd.getCompanyId();
            positionId = acpRequisitionHd.getPositionId();
            accEntityId = acpRequisitionHd.getAccEntityId();
            respCenterId = acpRequisitionHd.getRespCenterId();
            setOfBooksId = acpRequisitionHd.getSetOfBooksId();
        }
        accountId = cshTransactionHeaderService.getRevaluationAccount(iRequest, companyId, accEntityId, respCenterId,
                        positionId, setOfBooksId, cshTransactionLine.getCurrencyCode());
        if (accountId == null || accountId == 0 || StringUtils.isEmpty(accountId.toString())) {
            throw new CshWriteOffException(CshWriteOffException.REVALUATION_ACCOUNT_NOT_FOUND,
                            CshWriteOffException.REVALUATION_ACCOUNT_NOT_FOUND, null);
        }
        // 4.0 判断值大小
        if (revaluationAmount.compareTo(BigDecimal.ZERO) > 0) {
            cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
            cshWriteOffAccount.setCompanyId(companyId);
            cshWriteOffAccount.setAccountId(accEntityId);
            cshWriteOffAccount.setRespCenterId(respCenterId);
            cshWriteOffAccount.setAccountId(accountId);
            cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshWriteOffAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshWriteOffAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshWriteOffAccount.setEnteredAmountDr(BigDecimal.valueOf(0));
            cshWriteOffAccount.setEnteredAmountCr(null);
            cshWriteOffAccount
                            .setFunctionalAmountDr(revaluationAmount.setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshWriteOffAccount.setFunctionalAmountCr(null);
            cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
            cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshWriteOffAccount.setGldInterfaceFlag(cshWriteOff.getGldInterfaceFlag());
            cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_REVALUATION);
            CshWriteOffAccount dto = self().insertSelective(iRequest, cshWriteOffAccount);
            gldSegment = gldSobSegmentService.mappingSegmentValue(accEntityId, setOfBooksId, accountId,
                            CshDocPayAccEntity.DOC_EXP_REPORT, dto.getWriteOffJeLineId(), "Dr",
                            cshWriteOff.getDocumentHeaderId(), cshWriteOff.getDocumentLineId(), null,
                            cshWriteOff.getDocumentLineId());
            cshWriteOffAccount.setAccountSegment1(gldSegment.getSegment1());
            cshWriteOffAccount.setAccountSegment2(gldSegment.getSegment2());
            cshWriteOffAccount.setAccountSegment3(gldSegment.getSegment3());
            cshWriteOffAccount.setAccountSegment4(gldSegment.getSegment4());
            cshWriteOffAccount.setAccountSegment5(gldSegment.getSegment5());
            cshWriteOffAccount.setAccountSegment6(gldSegment.getSegment6());
            cshWriteOffAccount.setAccountSegment7(gldSegment.getSegment7());
            cshWriteOffAccount.setAccountSegment8(gldSegment.getSegment8());
            cshWriteOffAccount.setAccountSegment9(gldSegment.getSegment9());
            cshWriteOffAccount.setAccountSegment10(gldSegment.getSegment10());
            cshWriteOffAccount.setAccountSegment11(gldSegment.getSegment11());
            cshWriteOffAccount.setAccountSegment12(gldSegment.getSegment12());
            cshWriteOffAccount.setAccountSegment13(gldSegment.getSegment13());
            cshWriteOffAccount.setAccountSegment14(gldSegment.getSegment14());
            cshWriteOffAccount.setAccountSegment15(gldSegment.getSegment15());
            cshWriteOffAccount.setAccountSegment16(gldSegment.getSegment16());
            cshWriteOffAccount.setAccountSegment17(gldSegment.getSegment17());
            cshWriteOffAccount.setAccountSegment18(gldSegment.getSegment18());
            cshWriteOffAccount.setAccountSegment19(gldSegment.getSegment19());
            cshWriteOffAccount.setAccountSegment20(gldSegment.getSegment20());
        } else if (revaluationAmount.compareTo(BigDecimal.ZERO) < 0) {
            cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
            cshWriteOffAccount.setCompanyId(accEntityId);
            cshWriteOffAccount.setAccountId(accEntityId);
            cshWriteOffAccount.setRespCenterId(respCenterId);
            cshWriteOffAccount.setAccountId(accountId);
            cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshWriteOffAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshWriteOffAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshWriteOffAccount.setEnteredAmountDr(null);
            cshWriteOffAccount.setEnteredAmountCr(BigDecimal.valueOf(0));
            cshWriteOffAccount.setFunctionalAmountDr(null);
            cshWriteOffAccount.setFunctionalAmountCr(revaluationAmount.multiply(BigDecimal.valueOf(-1))
                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
            cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshWriteOffAccount.setGldInterfaceFlag(cshWriteOff.getGldInterfaceFlag());
            cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_REVALUATION);
            CshWriteOffAccount dto = self().insertSelective(iRequest, cshWriteOffAccount);
            gldSegment = gldSobSegmentService.mappingSegmentValue(accEntityId, setOfBooksId, accountId,
                            CshDocPayAccEntity.DOC_EXP_REPORT, dto.getWriteOffJeLineId(), "Dr",
                            cshWriteOff.getDocumentHeaderId(), cshWriteOff.getDocumentLineId(), null,
                            cshWriteOff.getDocumentLineId());
            cshWriteOffAccount.setAccountSegment1(gldSegment.getSegment1());
            cshWriteOffAccount.setAccountSegment2(gldSegment.getSegment2());
            cshWriteOffAccount.setAccountSegment3(gldSegment.getSegment3());
            cshWriteOffAccount.setAccountSegment4(gldSegment.getSegment4());
            cshWriteOffAccount.setAccountSegment5(gldSegment.getSegment5());
            cshWriteOffAccount.setAccountSegment6(gldSegment.getSegment6());
            cshWriteOffAccount.setAccountSegment7(gldSegment.getSegment7());
            cshWriteOffAccount.setAccountSegment8(gldSegment.getSegment8());
            cshWriteOffAccount.setAccountSegment9(gldSegment.getSegment9());
            cshWriteOffAccount.setAccountSegment10(gldSegment.getSegment10());
            cshWriteOffAccount.setAccountSegment11(gldSegment.getSegment11());
            cshWriteOffAccount.setAccountSegment12(gldSegment.getSegment12());
            cshWriteOffAccount.setAccountSegment13(gldSegment.getSegment13());
            cshWriteOffAccount.setAccountSegment14(gldSegment.getSegment14());
            cshWriteOffAccount.setAccountSegment15(gldSegment.getSegment15());
            cshWriteOffAccount.setAccountSegment16(gldSegment.getSegment16());
            cshWriteOffAccount.setAccountSegment17(gldSegment.getSegment17());
            cshWriteOffAccount.setAccountSegment18(gldSegment.getSegment18());
            cshWriteOffAccount.setAccountSegment19(gldSegment.getSegment19());
            cshWriteOffAccount.setAccountSegment20(gldSegment.getSegment20());
        }
    }

    /**
     * 生成核销凭证-报销单核销借款
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @Author Tagin
     * @Date 2019-02-21 13:51
     * @Return void
     * @Version 1.0
     **/
    @Override
    public void generateAccount(IRequest iRequest, CshWriteOff cshWriteOff) {
        // 1.0 报销单头信息
        ExpReportHeader expReportHeader = expReportHeaderMapper.getExpReportHeader(cshWriteOff.getDocumentHeaderId());
        // 2.0 计划付款行信息
        ExpReportPmtSchedule expReportPmtSchedule =
                        expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
        // 3.0 现金事务行信息
        CshTransactionLine cshTransactionLine =
                        cshTransactionLineMapper.selectByPrimaryKey(cshWriteOff.getCshTransactionLineId());
        // 4.0 获取默认责任中心
        Long respCenterId;
        String respCenterParam = sysParameterService.queryParamValueByCode(
                        ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                        cshTransactionLine.getAccEntityId(), null, null, null);
        if (BaseConstants.NO.equals(respCenterParam)) {
            throw new CshWriteOffException(CshWriteOffException.DEFAULT_RESP_NOT_FOUND,
                            CshWriteOffException.DEFAULT_RESP_NOT_FOUND, null);
        } else {
            respCenterId = Long.valueOf(respCenterParam);
        }
        // 5.0 本币财务精度
        int funPrecision = gldCurrencyMapper.getPrecision(expReportHeader.getFunctionalCurrencyCode());
        funPrecision = funPrecision == 0 ? 2 : funPrecision;
        // 6.0 生成借方凭证
        BigDecimal drFunctionalAmount = this.createDrAccount(iRequest, cshWriteOff, expReportHeader,
                        expReportPmtSchedule, cshTransactionLine, funPrecision, respCenterId);
        // 7.0 生成贷方凭证
        BigDecimal crFunctionalAmount =
                        this.createCrAccount(iRequest, cshWriteOff, expReportHeader, cshTransactionLine, funPrecision);
        // 8.0 生成汇率差异凭证
        if (drFunctionalAmount.compareTo(crFunctionalAmount) != 0) {
            this.createRevaluationAccount(iRequest, CshWriteOff.DOC_SOURCE_EXPENSE_REPORT, cshWriteOff,
                            cshTransactionLine, drFunctionalAmount.subtract(crFunctionalAmount), funPrecision);
        }
    }


    /**
     * 付款申请单（预付款）核销报销单、合同-生成借方凭证
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @param expReportHeader 报销单头对象
     * @param expReportPmtSchedule 计划付款行对象
     * @param funPrecision 本位币精度
     * @return java.math.BigDecimal
     * @author Tagin
     * @date 2019-05-07 19:12
     * @version 1.0
     **/
    public BigDecimal createAcpWriteDrAccount(IRequest iRequest, CshWriteOff cshWriteOff,
                    ExpReportHeader expReportHeader, ExpReportPmtSchedule expReportPmtSchedule, int funPrecision) {
        CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
        BigDecimal functionalAmount;
        if (CshWriteOff.DOC_SOURCE_EXPENSE_REPORT.equals(cshWriteOff.getDocumentSource())) {
            // 付款申请单-关联报销单信息
            // 获取报销单审核凭证借方
            ExpReportAccount expReportAccount = cshTransactionHeaderService.getReportCrAccount(expReportPmtSchedule);
            if (expReportAccount == null || expReportAccount.getAccountId() == null
                            || expReportAccount.getAccountId() == 0) {
                throw new CshWriteOffException(CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND,
                                CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND, null);
            }
            cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
            cshWriteOffAccount.setCompanyId(expReportPmtSchedule.getCompanyId());
            cshWriteOffAccount.setAccEntityId(expReportPmtSchedule.getAccEntityId());
            cshWriteOffAccount.setRespCenterId(expReportAccount.getRespCenterId());
            cshWriteOffAccount.setAccountId(expReportAccount.getAccountId());
            cshWriteOffAccount.setCurrencyCode(expReportAccount.getCurrencyCode());
            cshWriteOffAccount.setExchangeRateType(expReportAccount.getExchangeRateType());
            cshWriteOffAccount.setExchangeRate(expReportAccount.getExchangeRate());
            cshWriteOffAccount.setEnteredAmountCr(cshWriteOff.getCshWriteOffAmount());
            cshWriteOffAccount.setFunctionalAmountCr(
                            cshWriteOff.getCshWriteOffAmount().multiply(expReportAccount.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
            cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            if (BaseConstants.YES.equals(expReportHeader.getAuditFlag())) {
                cshWriteOffAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
            } else {
                cshWriteOffAccount.setGldInterfaceFlag(BaseConstants.NO);
            }
            cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_WRITE_OFF);
            cshWriteOffAccount.setAccountSegment1(expReportAccount.getAccountSegment1());
            cshWriteOffAccount.setAccountSegment2(expReportAccount.getAccountSegment2());
            cshWriteOffAccount.setAccountSegment3(expReportAccount.getAccountSegment3());
            cshWriteOffAccount.setAccountSegment4(expReportAccount.getAccountSegment4());
            cshWriteOffAccount.setAccountSegment5(expReportAccount.getAccountSegment5());
            cshWriteOffAccount.setAccountSegment6(expReportAccount.getAccountSegment6());
            cshWriteOffAccount.setAccountSegment7(expReportAccount.getAccountSegment7());
            cshWriteOffAccount.setAccountSegment8(expReportAccount.getAccountSegment8());
            cshWriteOffAccount.setAccountSegment9(expReportAccount.getAccountSegment9());
            cshWriteOffAccount.setAccountSegment10(expReportAccount.getAccountSegment10());
            cshWriteOffAccount.setAccountSegment11(expReportAccount.getAccountSegment11());
            cshWriteOffAccount.setAccountSegment12(expReportAccount.getAccountSegment12());
            cshWriteOffAccount.setAccountSegment13(expReportAccount.getAccountSegment13());
            cshWriteOffAccount.setAccountSegment14(expReportAccount.getAccountSegment14());
            cshWriteOffAccount.setAccountSegment15(expReportAccount.getAccountSegment15());
            cshWriteOffAccount.setAccountSegment16(expReportAccount.getAccountSegment16());
            cshWriteOffAccount.setAccountSegment17(expReportAccount.getAccountSegment17());
            cshWriteOffAccount.setAccountSegment18(expReportAccount.getAccountSegment18());
            cshWriteOffAccount.setAccountSegment19(expReportAccount.getAccountSegment19());
            cshWriteOffAccount.setAccountSegment20(expReportAccount.getAccountSegment20());
            self().insertSelective(iRequest, cshWriteOffAccount);
            functionalAmount = cshWriteOff.getCshWriteOffAmount().multiply(expReportAccount.getExchangeRate())
                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP);
        } else {
            // 付款申请单-关联合同信息
            // Tips：实际业务不存在借款申请预付关联合同支付，用预付款核销合同的情况
            functionalAmount = BigDecimal.ZERO;
        }
        return functionalAmount;
    }

    /**
     * 付款申请单（预付款）核销报销单、合同-生成贷方凭证
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @param acpRequisitionLn 付款申请单行对象
     * @param expReportHeader 报销单头
     * @param cshTransactionLine 现金事务行对象
     * @param funPrecision 本位币精度
     * @return java.math.BigDecimal
     * @author Tagin
     * @date 2019-05-07 19:39
     * @version 1.0
     **/
    public BigDecimal createAcpWriteCrAccount(IRequest iRequest, CshWriteOff cshWriteOff,
                    AcpRequisitionLn acpRequisitionLn, ExpReportHeader expReportHeader,
                    CshTransactionLine cshTransactionLine, int funPrecision) {
        CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
        BigDecimal functionalAmount;
        if (CshWriteOff.DOC_SOURCE_EXPENSE_REPORT.equals(cshWriteOff.getDocumentSource())) {
            // 付款申请单-关联报销单信息
            // 获取参数
            String interfaceParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_SYS_INTERFACE_TYPE, null, null, null, null, null, null, null);
            String auitFlag = acpMoPayReqTypeMapper.getAuitFlag(null, cshWriteOff.getDocumentHeaderId());
            if (CshTransactionHeader.SYS_INTERFACE_EBS_AP.equals(interfaceParam)
                            && !BaseConstants.YES.equals(auitFlag)) {
                AcpRequisitionAccount acpRequisitionAccount =
                                cshTransactionHeaderService.getAcpRequisitionDrAccount(acpRequisitionLn);
                if (acpRequisitionAccount == null || acpRequisitionAccount.getAccountId() == null) {
                    throw new CshTransactionException(CshTransactionException.ACP_AUDIT_ACCOUNT_NOT_FOUND,
                                    CshTransactionException.ACP_AUDIT_ACCOUNT_NOT_FOUND, null);
                }
                // 生成凭证
                cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
                cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
                cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
                cshWriteOffAccount.setCompanyId(expReportHeader.getCompanyId());
                cshWriteOffAccount.setAccEntityId(expReportHeader.getAccEntityId());
                cshWriteOffAccount.setRespCenterId(acpRequisitionAccount.getRespCenterId());
                cshWriteOffAccount.setAccountId(acpRequisitionAccount.getAccountId());
                cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
                cshWriteOffAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
                cshWriteOffAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
                cshWriteOffAccount.setEnteredAmountCr(cshWriteOff.getCshWriteOffAmount());
                cshWriteOffAccount.setFunctionalAmountCr(
                                cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                                .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
                cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
                cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
                if (BaseConstants.YES.equals(expReportHeader.getAuditFlag())) {
                    cshWriteOffAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                } else {
                    cshWriteOffAccount.setGldInterfaceFlag(BaseConstants.NO);
                }
                cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT_WRITE_OFF);
                cshWriteOffAccount.setAccountSegment1(acpRequisitionAccount.getAccountSegment1());
                cshWriteOffAccount.setAccountSegment2(acpRequisitionAccount.getAccountSegment2());
                cshWriteOffAccount.setAccountSegment3(acpRequisitionAccount.getAccountSegment3());
                cshWriteOffAccount.setAccountSegment4(acpRequisitionAccount.getAccountSegment4());
                cshWriteOffAccount.setAccountSegment5(acpRequisitionAccount.getAccountSegment5());
                cshWriteOffAccount.setAccountSegment6(acpRequisitionAccount.getAccountSegment6());
                cshWriteOffAccount.setAccountSegment7(acpRequisitionAccount.getAccountSegment7());
                cshWriteOffAccount.setAccountSegment8(acpRequisitionAccount.getAccountSegment8());
                cshWriteOffAccount.setAccountSegment9(acpRequisitionAccount.getAccountSegment9());
                cshWriteOffAccount.setAccountSegment10(acpRequisitionAccount.getAccountSegment10());
                cshWriteOffAccount.setAccountSegment11(acpRequisitionAccount.getAccountSegment11());
                cshWriteOffAccount.setAccountSegment12(acpRequisitionAccount.getAccountSegment12());
                cshWriteOffAccount.setAccountSegment13(acpRequisitionAccount.getAccountSegment13());
                cshWriteOffAccount.setAccountSegment14(acpRequisitionAccount.getAccountSegment14());
                cshWriteOffAccount.setAccountSegment15(acpRequisitionAccount.getAccountSegment15());
                cshWriteOffAccount.setAccountSegment16(acpRequisitionAccount.getAccountSegment16());
                cshWriteOffAccount.setAccountSegment17(acpRequisitionAccount.getAccountSegment17());
                cshWriteOffAccount.setAccountSegment18(acpRequisitionAccount.getAccountSegment18());
                cshWriteOffAccount.setAccountSegment19(acpRequisitionAccount.getAccountSegment19());
                cshWriteOffAccount.setAccountSegment20(acpRequisitionAccount.getAccountSegment20());
                self().insertSelective(iRequest, cshWriteOffAccount);
                functionalAmount = cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                .setScale(funPrecision, BigDecimal.ROUND_HALF_UP);
            } else {
                // 获取付款申请单支付凭证借方
                CshTransactionAccount cshTransactionAccount =
                                cshTransactionAccountService.queryAccount(cshTransactionLine.getTransactionLineId(),
                                                UsageCodeConstants.USAGE_PREPAYMENT, BaseConstants.YES, null, null);
                if (cshTransactionAccount == null || cshTransactionAccount.getAccountId() == null) {
                    throw new CshTransactionException(CshTransactionException.ACP_PAYMENT_ACCOUNT_NOT_FOUND,
                                    CshTransactionException.ACP_PAYMENT_ACCOUNT_NOT_FOUND, null);
                }
                // 生成凭证
                cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
                cshWriteOffAccount.setPeriodName(cshWriteOff.getPeriodName());
                cshWriteOffAccount.setSourceCode(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
                cshWriteOffAccount.setCompanyId(expReportHeader.getCompanyId());
                cshWriteOffAccount.setAccEntityId(expReportHeader.getAccEntityId());
                cshWriteOffAccount.setRespCenterId(cshTransactionAccount.getRespCenterId());
                cshWriteOffAccount.setAccountId(cshTransactionAccount.getAccountId());
                cshWriteOffAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
                cshWriteOffAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
                cshWriteOffAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
                cshWriteOffAccount.setEnteredAmountCr(cshWriteOff.getCshWriteOffAmount());
                cshWriteOffAccount.setFunctionalAmountCr(
                                cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                                .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
                cshWriteOffAccount.setCashClearingFlag(BaseConstants.NO);
                cshWriteOffAccount.setJournalDate(cshWriteOff.getWriteOffDate());
                if (BaseConstants.YES.equals(expReportHeader.getAuditFlag())) {
                    cshWriteOffAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                } else {
                    cshWriteOffAccount.setGldInterfaceFlag(BaseConstants.NO);
                }
                cshWriteOffAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT_WRITE_OFF);
                cshWriteOffAccount.setAccountSegment1(cshTransactionAccount.getAccountSegment1());
                cshWriteOffAccount.setAccountSegment2(cshTransactionAccount.getAccountSegment2());
                cshWriteOffAccount.setAccountSegment3(cshTransactionAccount.getAccountSegment3());
                cshWriteOffAccount.setAccountSegment4(cshTransactionAccount.getAccountSegment4());
                cshWriteOffAccount.setAccountSegment5(cshTransactionAccount.getAccountSegment5());
                cshWriteOffAccount.setAccountSegment6(cshTransactionAccount.getAccountSegment6());
                cshWriteOffAccount.setAccountSegment7(cshTransactionAccount.getAccountSegment7());
                cshWriteOffAccount.setAccountSegment8(cshTransactionAccount.getAccountSegment8());
                cshWriteOffAccount.setAccountSegment9(cshTransactionAccount.getAccountSegment9());
                cshWriteOffAccount.setAccountSegment10(cshTransactionAccount.getAccountSegment10());
                cshWriteOffAccount.setAccountSegment11(cshTransactionAccount.getAccountSegment11());
                cshWriteOffAccount.setAccountSegment12(cshTransactionAccount.getAccountSegment12());
                cshWriteOffAccount.setAccountSegment13(cshTransactionAccount.getAccountSegment13());
                cshWriteOffAccount.setAccountSegment14(cshTransactionAccount.getAccountSegment14());
                cshWriteOffAccount.setAccountSegment15(cshTransactionAccount.getAccountSegment15());
                cshWriteOffAccount.setAccountSegment16(cshTransactionAccount.getAccountSegment16());
                cshWriteOffAccount.setAccountSegment17(cshTransactionAccount.getAccountSegment17());
                cshWriteOffAccount.setAccountSegment18(cshTransactionAccount.getAccountSegment18());
                cshWriteOffAccount.setAccountSegment19(cshTransactionAccount.getAccountSegment19());
                cshWriteOffAccount.setAccountSegment20(cshTransactionAccount.getAccountSegment20());
                self().insertSelective(iRequest, cshWriteOffAccount);
                functionalAmount = cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                .setScale(funPrecision, BigDecimal.ROUND_HALF_UP);
            }
        } else {
            // 付款申请单-关联合同信息
            // Tips：实际业务不存在借款申请预付关联合同支付，用预付款核销合同的情况
            functionalAmount = BigDecimal.ZERO;
        }
        return functionalAmount;
    }

    /**
     * 生成核销凭证-付款申请单核销报销、合同
     *
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @author Tagin
     * @date 2019-05-08 10:18
     * @version 1.0
     **/
    @Override
    public void generateAcpWriteAccount(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff, AcpRequisitionLn acpRequisitionLn) {
        // 1.0 报销单头信息
        ExpReportHeader expReportHeader = expReportHeaderMapper.getExpReportHeader(cshWriteOff.getDocumentHeaderId());
        // 2.0 计划付款行信息
        ExpReportPmtSchedule expReportPmtSchedule =
                        expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
        // 3.0 获取核算主体默认本币
        String funCurrency = gldCurrencyMapper.getAccEntityFunCurrCode(cshTransactionLine.getAccEntityId());
        // 4.0 获取本位币精度
        int funPrecision = gldCurrencyMapper.getPrecision(funCurrency);
        funPrecision = funPrecision == 0 ? 2 : funPrecision;
        // 5.0 生成借方凭证
        BigDecimal drFunctionalAmount = this.createAcpWriteDrAccount(iRequest, cshWriteOff, expReportHeader,
                        expReportPmtSchedule, funPrecision);
        // 6.0 生成贷方凭证
        BigDecimal crFunctionalAmount = this.createAcpWriteCrAccount(iRequest, cshWriteOff, acpRequisitionLn,
                        expReportHeader, cshTransactionLine, funPrecision);
        // 7.0 生成汇率差异凭证
        if (drFunctionalAmount.compareTo(crFunctionalAmount) != 0) {
            this.createRevaluationAccount(iRequest, CshWriteOff.DOC_SOURCE_ACP_REQUISITION, cshWriteOff,
                            cshTransactionLine, drFunctionalAmount.subtract(crFunctionalAmount), funPrecision);
        }
    }


    /**
     * <p>
     * 付款反冲查询凭证信息
     * </p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @param pageNum
     * @param pageSize
     * @return List<Map>
     * @author yang.duan 2019/5/24 11:37
     **/
    @Override
    public List<Map> cshPaymentFinanceInfo(IRequest request, Long transactionHeaderId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cshWriteOffAccountMapper.cshPaymentFinanceInfo(transactionHeaderId);
    }

    @Override
	public List<Map> cshPrepaymentFinanceInfo(IRequest request, Long writeOffId,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return cshWriteOffAccountMapper.cshPrepaymentFinanceInfo(writeOffId);
	}

    /**
     * 查询核销凭证信息
     *
     * @author Tagin
     * @date 2019-06-05 19:04
     * @param writeOffId 核销 ID
     * @param usageCode 用途代码
     * @param drFlag 借方标志
     * @param crFlag 贷方标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    @Override
    public List<CshWriteOffAccount> queryAccount(Long writeOffId, String usageCode, String drFlag, String crFlag) {
        return cshWriteOffAccountMapper.queryAccount(writeOffId, usageCode, drFlag, crFlag);
    }
}
