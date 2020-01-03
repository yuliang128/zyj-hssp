package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.mapper.AcpMoPayReqTypeMapper;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.exception.CshWriteOffException;
import com.hand.hec.csh.mapper.CshBankAccountMapper;
import com.hand.hec.csh.mapper.CshMoPaymentReqTypeMapper;
import com.hand.hec.csh.mapper.CshTransactionAccountMapper;
import com.hand.hec.csh.service.ICshTransactionAccountService;
import com.hand.hec.csh.service.ICshTransactionHeaderService;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.mapper.ExpReportPmtScheduleMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hec.gld.constants.UsageCodeConstants;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.dto.GldSegment;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.service.IGldSetOfBookService;
import com.hand.hec.gld.service.IGldSobSegmentService;

/**
 * <p>
 * 现金事务凭证ServiceImpl
 * </p>
 *
 * @author Tagin 2019/01/22 20:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshTransactionAccountServiceImpl extends BaseServiceImpl<CshTransactionAccount>
                implements ICshTransactionAccountService {

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @Autowired
    private IGldExchangeRateService gldExchangeRateService;

    @Autowired
    private CshBankAccountMapper cshBankAccountMapper;

    @Autowired
    private IGldSetOfBookService gldSetOfBookService;

    @Autowired
    private CshMoPaymentReqTypeMapper cshMoPaymentReqTypeMapper;

    @Autowired
    private CshTransactionAccountMapper cshTransactionAccountMapper;

    @Autowired
    private AcpMoPayReqTypeMapper acpMoPayReqTypeMapper;

    @Autowired
    private ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    /**
     * 获取现金事务凭证
     *
     * @author Tagin
     * @date 2019-04-01 15:17
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @param writeOffId 核销 ID
     * @return List<CshTransactionAccount>
     * @version 1.0
     **/
    @Override
    public CshTransactionAccount queryAccount(Long transactionLineId, String usageCode, String drFlag, String crFlag,
                    Long writeOffId) {
        List<CshTransactionAccount> accounts = cshTransactionAccountMapper.queryAccount(transactionLineId, usageCode,
                        drFlag, crFlag, writeOffId);
        return CollectionUtils.isNotEmpty(accounts) ? accounts.get(0) : null;
    }

    @Override
    public void updateAccountSegment(IRequest request, CshTransactionAccount account, String crDrFlag,
                    Long setOfBooksId, CshTransactionLine line, String docCategory, Long docHeaderId, Long docLineId,
                    Long docDistId, Long docPmtLineId) {
        // 调用生成段值逻辑
        GldSegment gldSegment = gldSobSegmentService.mappingSegmentValue(line.getAccEntityId(), setOfBooksId,
                        account.getAccountId(), docCategory, account.getTransactionJeLineId(), crDrFlag, docHeaderId,
                        docLineId, docDistId, docPmtLineId);
        account.setAccountSegment1(gldSegment.getSegment1());
        account.setAccountSegment2(gldSegment.getSegment2());
        account.setAccountSegment3(gldSegment.getSegment3());
        account.setAccountSegment4(gldSegment.getSegment4());
        account.setAccountSegment5(gldSegment.getSegment5());
        account.setAccountSegment6(gldSegment.getSegment6());
        account.setAccountSegment7(gldSegment.getSegment7());
        account.setAccountSegment8(gldSegment.getSegment8());
        account.setAccountSegment9(gldSegment.getSegment9());
        account.setAccountSegment10(gldSegment.getSegment10());
        account.setAccountSegment11(gldSegment.getSegment11());
        account.setAccountSegment12(gldSegment.getSegment12());
        account.setAccountSegment13(gldSegment.getSegment13());
        account.setAccountSegment14(gldSegment.getSegment14());
        account.setAccountSegment15(gldSegment.getSegment15());
        account.setAccountSegment16(gldSegment.getSegment16());
        account.setAccountSegment17(gldSegment.getSegment17());
        account.setAccountSegment18(gldSegment.getSegment18());
        account.setAccountSegment19(gldSegment.getSegment19());
        account.setAccountSegment20(gldSegment.getSegment20());
        self().updateByPrimaryKey(request, account);
    }

    @Override
    public List<CshTransactionAccount> queryByLineId(IRequest request, Long transactionLineId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return cshTransactionAccountMapper.queryByLineId(transactionLineId);
    }

    /**
     * 报销单、借款单、付款单支付-生成贷方凭证
     *
     * @author Tagin
     * @date 2019-03-27 17:19
     * @param iRequest 请求
     * @param docCategory 单据类别（报销单、借款单、付款单）
     * @param cshDocPayment 支付基础信息对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param payPrecision 支付币种精度
     * @param funPrecision 本位币精度
     * @return void
     * @version 1.0
     **/
    public void createCrAccount(IRequest iRequest, String docCategory, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, int payPrecision, int funPrecision) {
        // 1.0 生成贷方凭证
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        GldSetOfBook gldSetOfBook =
                        gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
        if (gldSetOfBook == null || StringUtils.isEmpty(gldSetOfBook.getSetOfBooksId().toString())) {
            throw new CshTransactionException(CshTransactionException.DFT_SET_OF_BOOKS_NOT_FOUND,
                            CshTransactionException.DFT_SET_OF_BOOKS_NOT_FOUND, null);
        }
        // 银行账户配置的责任中心及银行科目
        Map<String, Object> map = cshBankAccountMapper.queryAccountAndResp(cshDocPayment.getBankAccountId(),
                        gldSetOfBook.getSetOfBooksId());
        if (MapUtils.isEmpty(map) || StringUtils.isEmpty(String.valueOf(map.get("accountId")))
                        || StringUtils.isEmpty(String.valueOf(map.get("respCenterId")))) {
            throw new CshTransactionException(CshTransactionException.BANK_ACCOUNT_NOT_FUND,
                            CshTransactionException.BANK_ACCOUNT_NOT_FUND, null);
        }
        // 创建凭证
        cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
        cshTransactionAccount.setWriteOffId(null);
        cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
        cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
        cshTransactionAccount.setCompanyId(cshTransactionLine.getCompanyId());
        cshTransactionAccount.setAccEntityId(cshTransactionLine.getAccEntityId());
        cshTransactionAccount.setRespCenterId((Long) map.get("respCenterId"));
        cshTransactionAccount.setAccountId((Long) map.get("accountId"));
        cshTransactionAccount.setEnteredAmountDr(null);
        cshTransactionAccount.setEnteredAmountCr(
                        cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
        cshTransactionAccount.setFunctionalAmountDr(null);
        cshTransactionAccount.setFunctionalAmountCr(
                        cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                        .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
        cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
        cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
        cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
        cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
        cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
        cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
        cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
        cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_CASH_ACCOUNT);
        CshTransactionAccount dto = self().insertSelective(iRequest, cshTransactionAccount);
        // 调用生成段值逻辑
        GldSegment gldSegment = gldSobSegmentService.mappingSegmentValue(cshTransactionLine.getAccEntityId(),
                        gldSetOfBook.getSetOfBooksId(), (Long) map.get("accountId"),
                        GlAccountEntry.RULE_TYPE_CSH_TRANSACTION, dto.getTransactionJeLineId(), "Cr",
                        cshTransactionHeader.getTransactionHeaderId(), cshTransactionLine.getTransactionLineId(), null,
                        null);
        cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
        cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
        cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
        cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
        cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
        cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
        cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
        cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
        cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
        cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
        cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
        cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
        cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
        cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
        cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
        cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
        cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
        cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
        cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
        cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
        self().updateByPrimaryKey(iRequest, cshTransactionAccount);
    }

    /**
     * 报销单、借款单、付款单支付-生成往来凭证
     *
     * @author Tagin
     * @date 2019-03-27 15:34
     * @param iRequest 请求
     * @param docCategory 单据类别（报销单、借款单、付款单）
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param expReportPmtSchedule 报销单计划付款行对象
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @param acpRequisitionLn 付款申请单行对象
     * @param cshWriteOff 核销对象
     * @param payPrecision 支付币种精度
     * @param funPrecision 本位币精度
     * @param funCurrency 本位币
     * @return void
     * @version 1.0
     **/
    public void createInterAccount(IRequest iRequest, String docCategory, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, ExpReportPmtSchedule expReportPmtSchedule,
                    CshPaymentRequisitionLn cshPaymentRequisitionLn, AcpRequisitionLn acpRequisitionLn,
                    CshWriteOff cshWriteOff, int payPrecision, int funPrecision, String funCurrency) {
        // 变量
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        GldSegment gldSegment = new GldSegment();
        FndCompany fndCompany = new FndCompany();
        GldSetOfBook gldSetOfBook = new GldSetOfBook();
        CshTransactionAccount dto = new CshTransactionAccount();
        Long respCenterId, accountId, docCompanyId = 0L, docAccEntityId = 0L;
        String respCenterParam, exchangeRateType;
        BigDecimal exchangeRate;
        // 单据字段
        if (CshDocPayAccEntity.DOC_EXP_REPORT.equals(docCategory)) {
            docCompanyId = expReportPmtSchedule.getCompanyId();
            docAccEntityId = expReportPmtSchedule.getAccEntityId();
        } else if (CshDocPayAccEntity.DOC_PAYMENT_REQUISITION.equals(docCategory)) {
            docCompanyId = cshPaymentRequisitionLn.getCompanyId();
            docAccEntityId = cshPaymentRequisitionLn.getAccEntityId();
        } else if (CshDocPayAccEntity.DOC_ACP_REQUISITION.equals(docCategory)) {
            docCompanyId = acpRequisitionLn.getCompanyId();
            docAccEntityId = acpRequisitionLn.getAccEntityId();
        }
        if (cshTransactionLine.getAccEntityId().compareTo(docAccEntityId) != 0) {
            // 成本中心
            respCenterParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            cshTransactionLine.getAccEntityId(), null, null, null);
            if (BaseConstants.NO.equals(respCenterParam)) {
                throw new CshWriteOffException(CshWriteOffException.DEFAULT_RESP_NOT_FOUND,
                                CshWriteOffException.DEFAULT_RESP_NOT_FOUND, null);
            } else {
                respCenterId = Long.valueOf(respCenterParam);
            }
            // 核算主体默认账套
            gldSetOfBook = gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
            // 支付核算主体内部往来应收科目
            accountId = cshTransactionHeaderService.getCshIntercompanyArAccount(iRequest,
                            cshTransactionLine.getCompanyId(), cshTransactionLine.getAccEntityId(), docAccEntityId,
                            respCenterId, null, cshTransactionLine.getCurrencyCode(), gldSetOfBook.getSetOfBooksId());
            if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
                throw new CshTransactionException(CshTransactionException.CSH_INTERCOMPANY_AR_NOT_FOUND,
                                CshTransactionException.CSH_INTERCOMPANY_AR_NOT_FOUND, null);
            }
            // 1.0 生成借方凭证【往来凭证-AR.支付核算主体】
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(cshTransactionLine.getCompanyId());
            cshTransactionAccount.setAccEntityId(cshTransactionLine.getAccEntityId());
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_CSH_INTERCOMPANY_AR);
            dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 调用生成段值逻辑
            gldSegment = gldSobSegmentService.mappingSegmentValue(cshTransactionLine.getAccEntityId(),
                            gldSetOfBook.getSetOfBooksId(), accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                            dto.getTransactionJeLineId(), "Dr", cshTransactionHeader.getTransactionHeaderId(),
                            cshTransactionLine.getTransactionLineId(), null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);

            // 成本中心
            respCenterParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null, docAccEntityId,
                            null, null, null);
            if (BaseConstants.NO.equals(respCenterParam)) {
                throw new CshWriteOffException(CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND,
                                CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND, null);
            } else {
                respCenterId = Long.valueOf(respCenterParam);
            }
            // 核算主体默认账套
            gldSetOfBook = gldSetOfBookService.queryDftSetOffBookByAe(iRequest, docAccEntityId);
            // 支付核算主体内部往来应付科目
            accountId = cshTransactionHeaderService.getCshIntercompanyApAccount(iRequest, docCompanyId, docAccEntityId,
                            cshTransactionLine.getAccEntityId(), respCenterId, null,
                            cshTransactionLine.getCurrencyCode(), gldSetOfBook.getSetOfBooksId());
            if (accountId == null || accountId == 0 || StringUtils.isEmpty(accountId.toString())) {
                throw new CshTransactionException(CshTransactionException.CSH_INTERCOMPANY_AP_NOT_FOUND,
                                CshTransactionException.CSH_INTERCOMPANY_AP_NOT_FOUND, null);
            }
            // 汇率、汇率类型
            if (cshTransactionLine.getCurrencyCode().equals(funCurrency)) {
                exchangeRateType = null;
                exchangeRate = BigDecimal.valueOf(1);
            } else {
                exchangeRateType = sysParameterService.queryParamValueByCode(
                                ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, null, docAccEntityId,
                                null, null, null);
                if (StringUtils.isEmpty(exchangeRateType)) {
                    throw new CshTransactionException(CshTransactionException.DFT_TO_EXCHANGE_RATE_TYPE_NOT_FOUND,
                                    CshTransactionException.DFT_TO_EXCHANGE_RATE_TYPE_NOT_FOUND, null);
                }
                exchangeRate = gldExchangeRateService.getExchangeRate(docAccEntityId, funCurrency,
                                cshTransactionLine.getCurrencyCode(), exchangeRateType, cshWriteOff.getWriteOffDate(),
                                cshWriteOff.getPeriodName(), null);
                if (exchangeRate == null || StringUtils.isEmpty(exchangeRate.toString())) {
                    throw new CshTransactionException(CshTransactionException.DFT_TO_EXCHANGE_RATE_NOT_FOUND,
                                    CshTransactionException.DFT_TO_EXCHANGE_RATE_NOT_FOUND, null);
                }
            }
            // 2.0 生成贷方凭证【往来凭证-AP.计划付款行核算主体】
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(docCompanyId);
            cshTransactionAccount.setAccEntityId(docAccEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(null);
            cshTransactionAccount.setEnteredAmountCr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountDr(null);
            cshTransactionAccount.setFunctionalAmountCr(cshWriteOff.getCshWriteOffAmount().multiply(exchangeRate)
                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setExchangeRateType(exchangeRateType);
            cshTransactionAccount.setExchangeRate(exchangeRate);
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_CSH_INTERCOMPANY_AP);
            dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 调用生成段值逻辑
            gldSegment = gldSobSegmentService.mappingSegmentValue(docAccEntityId, gldSetOfBook.getSetOfBooksId(),
                            accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION, dto.getTransactionJeLineId(), "Cr",
                            cshTransactionHeader.getTransactionHeaderId(), cshTransactionLine.getTransactionLineId(),
                            null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        }
    }

    /**
     * 报销单支付-生成借方凭证
     *
     * @author Tagin
     * @date 2019-03-27 15:39
     * @param iRequest 请求
     * @param expReportPmtSchedule 计划付款行队形
     * @param cshTransactionHeader 现金事务行对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param payPrecision 支付币种精度
     * @param funPrecision 本位币精度
     * @return java.math.BigDecimal
     * @version 1.0
     **/
    public BigDecimal createReportDrAccount(IRequest iRequest, ExpReportPmtSchedule expReportPmtSchedule,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, int payPrecision, int funPrecision) {
        // 1.0 生成 借方凭证
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        Long respCenterId, accEntityId, companyId;
        String respCenterParam, respCenterException;
        ExpReportAccount expReportAccount = cshTransactionHeaderService.getReportCrAccount(expReportPmtSchedule);
        if (expReportAccount == null || expReportAccount.getAccountId() == null
                        || expReportAccount.getAccountId() == 0) {
            throw new CshWriteOffException(CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND,
                            CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND, null);
        }
        // 获取成本中心
        if (cshTransactionLine.getAccEntityId().compareTo(expReportPmtSchedule.getAccEntityId()) == 0) {
            companyId = cshTransactionLine.getCompanyId();
            accEntityId = cshTransactionLine.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_RESP_NOT_FOUND;
        } else {
            companyId = expReportPmtSchedule.getCompanyId();
            accEntityId = expReportPmtSchedule.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND;
        }
        respCenterParam = sysParameterService.queryParamValueByCode(
                        ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null, accEntityId, null,
                        null, null);
        if (BaseConstants.NO.equals(respCenterParam)) {
            throw new CshWriteOffException(respCenterException, respCenterException, null);
        } else {
            respCenterId = Long.valueOf(respCenterParam);
        }
        // 若存在自审核【Tips：不生成费用凭证则汇率同现金事务的汇率】
        if (expReportAccount.getExpReportJeLineId() == null
                        || StringUtils.isEmpty(expReportAccount.getExpReportJeLineId().toString())) {
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(expReportAccount.getAccountId());
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_WRITE_OFF);
            CshTransactionAccount dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 调用生成段值逻辑
            GldSetOfBook gldSetOfBook =
                            gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
            GldSegment gldSegment = gldSobSegmentService.mappingSegmentValue(cshTransactionLine.getAccEntityId(),
                            gldSetOfBook.getSetOfBooksId(), expReportAccount.getAccountId(),
                            GlAccountEntry.RULE_TYPE_CSH_TRANSACTION, dto.getTransactionJeLineId(), "Dr",
                            cshTransactionHeader.getTransactionHeaderId(), cshTransactionLine.getTransactionLineId(),
                            null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        } else {
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(expReportAccount.getRespCenterId());
            cshTransactionAccount.setAccountId(expReportAccount.getAccountId());
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(expReportAccount.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_WRITE_OFF);
            cshTransactionAccount.setAccountSegment1(expReportAccount.getAccountSegment1());
            cshTransactionAccount.setAccountSegment2(expReportAccount.getAccountSegment2());
            cshTransactionAccount.setAccountSegment3(expReportAccount.getAccountSegment3());
            cshTransactionAccount.setAccountSegment4(expReportAccount.getAccountSegment4());
            cshTransactionAccount.setAccountSegment5(expReportAccount.getAccountSegment5());
            cshTransactionAccount.setAccountSegment6(expReportAccount.getAccountSegment6());
            cshTransactionAccount.setAccountSegment7(expReportAccount.getAccountSegment7());
            cshTransactionAccount.setAccountSegment8(expReportAccount.getAccountSegment8());
            cshTransactionAccount.setAccountSegment9(expReportAccount.getAccountSegment9());
            cshTransactionAccount.setAccountSegment10(expReportAccount.getAccountSegment10());
            cshTransactionAccount.setAccountSegment11(expReportAccount.getAccountSegment11());
            cshTransactionAccount.setAccountSegment12(expReportAccount.getAccountSegment12());
            cshTransactionAccount.setAccountSegment13(expReportAccount.getAccountSegment13());
            cshTransactionAccount.setAccountSegment14(expReportAccount.getAccountSegment14());
            cshTransactionAccount.setAccountSegment15(expReportAccount.getAccountSegment15());
            cshTransactionAccount.setAccountSegment16(expReportAccount.getAccountSegment16());
            cshTransactionAccount.setAccountSegment17(expReportAccount.getAccountSegment17());
            cshTransactionAccount.setAccountSegment18(expReportAccount.getAccountSegment18());
            cshTransactionAccount.setAccountSegment19(expReportAccount.getAccountSegment19());
            cshTransactionAccount.setAccountSegment20(expReportAccount.getAccountSegment20());
            self().insertSelective(iRequest, cshTransactionAccount);
        }
        BigDecimal exchangeRate = expReportAccount.getExchangeRate() == null ? cshTransactionLine.getExchangeRate()
                        : expReportAccount.getExchangeRate();
        return cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                        .subtract(cshWriteOff.getCshWriteOffAmount().multiply(exchangeRate));
    }

    /**
     * 报销单支付-生成汇率差异凭证
     *
     * @author Tagin
     * @date 2019-03-27 20:03
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param expReportPmtSchedule 报销单计划付款行对象
     * @param cshWriteOff 核销对象
     * @param revaluationAmount 汇率差
     * @param funPrecision 本位币精度
     * @return void
     * @version 1.0
     **/
    public void createReportRevaluationAccount(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, ExpReportPmtSchedule expReportPmtSchedule,
                    CshWriteOff cshWriteOff, BigDecimal revaluationAmount, int funPrecision) {
        // 变量
        Long companyId, accEntityId, respCenterId;
        String respCenterParam, respCenterException;
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        CshTransactionAccount dto = new CshTransactionAccount();
        GldSegment gldSegment = new GldSegment();
        // 获取成本中心
        if (cshTransactionLine.getAccEntityId().toString().equals(expReportPmtSchedule.getAccEntityId().toString())) {
            companyId = cshTransactionLine.getCompanyId();
            accEntityId = cshTransactionLine.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_RESP_NOT_FOUND;
        } else {
            companyId = expReportPmtSchedule.getCompanyId();
            accEntityId = expReportPmtSchedule.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND;
        }
        respCenterParam = sysParameterService.queryParamValueByCode(
                        ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null, accEntityId, null,
                        null, null);
        if (BaseConstants.NO.equals(respCenterParam)) {
            throw new CshWriteOffException(respCenterException, respCenterException, null);
        } else {
            respCenterId = Long.valueOf(respCenterParam);
        }
        // 核算主体默认账套
        GldSetOfBook gldSetOfBook = gldSetOfBookService.queryDftSetOffBookByAe(iRequest, accEntityId);
        // 汇率差异用途代码定义的科目
        Long accountId = cshTransactionHeaderService.getRevaluationAccount(iRequest, companyId, accEntityId,
                        respCenterId, null, gldSetOfBook.getSetOfBooksId(), cshTransactionLine.getCurrencyCode());
        if (accountId == null || accountId == 0 || StringUtils.isEmpty(accountId.toString())) {
            throw new CshWriteOffException(CshWriteOffException.REVALUATION_ACCOUNT_NOT_FOUND,
                            CshWriteOffException.REVALUATION_ACCOUNT_NOT_FOUND, null);
        }
        // 判断值大小
        if (revaluationAmount.compareTo(BigDecimal.ZERO) > 0) {
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(BigDecimal.valueOf(0));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount
                            .setFunctionalAmountDr(revaluationAmount.setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_REVALUATION);
            dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 调用生成段值逻辑
            gldSegment = gldSobSegmentService.mappingSegmentValue(accEntityId, gldSetOfBook.getSetOfBooksId(),
                            accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION, dto.getTransactionJeLineId(), "Dr",
                            cshTransactionHeader.getTransactionHeaderId(), cshTransactionLine.getTransactionLineId(),
                            null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        } else if (revaluationAmount.compareTo(BigDecimal.ZERO) < 0) {
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(null);
            cshTransactionAccount.setEnteredAmountCr(BigDecimal.valueOf(0));
            cshTransactionAccount.setFunctionalAmountDr(null);
            cshTransactionAccount.setFunctionalAmountCr(BigDecimal.valueOf(-1).multiply(revaluationAmount)
                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_REVALUATION);
            dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 调用生成段值逻辑
            gldSegment = gldSobSegmentService.mappingSegmentValue(accEntityId, gldSetOfBook.getSetOfBooksId(),
                            accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION, dto.getTransactionJeLineId(), "Cr",
                            cshTransactionHeader.getTransactionHeaderId(), cshTransactionLine.getTransactionLineId(),
                            null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        }
    }

    /**
     * 费用报销单支付-生成支付凭证
     *
     * @author Tagin
     * @date 2019-03-22 15:26
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param expReportPmtSchedule 计划付款行对象
     * @return void
     * @version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generateReportAccount(IRequest iRequest, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, ExpReportPmtSchedule expReportPmtSchedule) {
        // 获取核算主体默认本币
        String funCurrency = gldCurrencyMapper.getAccEntityFunCurrCode(cshTransactionLine.getAccEntityId());
        // 支付币种、本位币精度
        int payPrecision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        payPrecision = payPrecision == 0 ? 2 : payPrecision;
        int funPrecision = gldCurrencyMapper.getPrecision(funCurrency);
        funPrecision = funPrecision == 0 ? 2 : funPrecision;
        // 1.0 生成借方凭证
        BigDecimal revaluationAmount = this.createReportDrAccount(iRequest, expReportPmtSchedule, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, payPrecision, funPrecision);
        // 2.0 生成贷方凭证
        this.createCrAccount(iRequest, CshDocPayAccEntity.DOC_EXP_REPORT, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, payPrecision, funPrecision);
        // 3.0 生成往来凭证
        this.createInterAccount(iRequest, CshDocPayAccEntity.DOC_EXP_REPORT, cshTransactionHeader, cshTransactionLine,
                        expReportPmtSchedule, null, null, cshWriteOff, payPrecision, funPrecision, funCurrency);
        // 4.0 生成汇率差异凭证
        if (revaluationAmount.compareTo(BigDecimal.ZERO) != 0) {
            this.createReportRevaluationAccount(iRequest, cshTransactionHeader, cshTransactionLine,
                            expReportPmtSchedule, cshWriteOff, revaluationAmount, funPrecision);
        }
    }

    /**
     * 借款单支付-生成借方凭证
     *
     * @author Tagin
     * @date 2019-03-29 17:21
     * @param iRequest 请求
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @param cshTransactionHeader 现金事务行头对象
     * @param cshTransactionLine 现金事务行行对象
     * @param cshWriteOff 核销对象
     * @param payPrecision 支付币种财务精度
     * @param funPrecision 本位币财务精度
     * @return void
     * @version 1.0
     **/
    public void createLoanDrAccount(IRequest iRequest, CshPaymentRequisitionLn cshPaymentRequisitionLn,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, int payPrecision, int funPrecision) {
        // 1.0 生成借方凭证
        Long companyId, accEntityId, respCenterId;
        String respCenterException;
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        // 获取参数
        String interfaceParam = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_SYS_INTERFACE_TYPE,
                        null, null, null, null, null, null, null);
        String auitFlag = cshMoPaymentReqTypeMapper.getAuitFlag(null,
                        cshPaymentRequisitionLn.getPaymentRequisitionHeaderId());
        if (cshTransactionLine.getAccEntityId().compareTo(cshPaymentRequisitionLn.getAccEntityId()) == 0) {
            companyId = cshTransactionLine.getCompanyId();
            accEntityId = cshTransactionLine.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_RESP_NOT_FOUND;
        } else {
            companyId = cshPaymentRequisitionLn.getCompanyId();
            accEntityId = cshPaymentRequisitionLn.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND;
        }
        if (CshTransactionHeader.SYS_INTERFACE_EBS_AP.equals(interfaceParam) && !BaseConstants.YES.equals(auitFlag)) {
            CshPaymentReqAccount cshPaymentReqAccount =
                            cshTransactionHeaderService.getPaymentCrAccount(cshTransactionLine);
            if (cshPaymentReqAccount == null || cshPaymentReqAccount.getAccountId() == null
                            || cshPaymentReqAccount.getAccountId().toString().isEmpty()) {
                throw new CshTransactionException(CshTransactionException.CSH_AUDIT_ACCOUNT_NOT_FOUND,
                                CshTransactionException.CSH_AUDIT_ACCOUNT_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(cshPaymentReqAccount.getRespCenterId());
            cshTransactionAccount.setAccountId(cshPaymentReqAccount.getAccountId());
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT);
            cshTransactionAccount.setAccountSegment1(cshPaymentReqAccount.getAccountSegment1());
            cshTransactionAccount.setAccountSegment2(cshPaymentReqAccount.getAccountSegment2());
            cshTransactionAccount.setAccountSegment3(cshPaymentReqAccount.getAccountSegment3());
            cshTransactionAccount.setAccountSegment4(cshPaymentReqAccount.getAccountSegment4());
            cshTransactionAccount.setAccountSegment5(cshPaymentReqAccount.getAccountSegment5());
            cshTransactionAccount.setAccountSegment6(cshPaymentReqAccount.getAccountSegment6());
            cshTransactionAccount.setAccountSegment7(cshPaymentReqAccount.getAccountSegment7());
            cshTransactionAccount.setAccountSegment8(cshPaymentReqAccount.getAccountSegment8());
            cshTransactionAccount.setAccountSegment9(cshPaymentReqAccount.getAccountSegment9());
            cshTransactionAccount.setAccountSegment10(cshPaymentReqAccount.getAccountSegment10());
            cshTransactionAccount.setAccountSegment11(cshPaymentReqAccount.getAccountSegment11());
            cshTransactionAccount.setAccountSegment12(cshPaymentReqAccount.getAccountSegment12());
            cshTransactionAccount.setAccountSegment13(cshPaymentReqAccount.getAccountSegment13());
            cshTransactionAccount.setAccountSegment14(cshPaymentReqAccount.getAccountSegment14());
            cshTransactionAccount.setAccountSegment15(cshPaymentReqAccount.getAccountSegment15());
            cshTransactionAccount.setAccountSegment16(cshPaymentReqAccount.getAccountSegment16());
            cshTransactionAccount.setAccountSegment17(cshPaymentReqAccount.getAccountSegment17());
            cshTransactionAccount.setAccountSegment18(cshPaymentReqAccount.getAccountSegment18());
            cshTransactionAccount.setAccountSegment19(cshPaymentReqAccount.getAccountSegment19());
            cshTransactionAccount.setAccountSegment20(cshPaymentReqAccount.getAccountSegment20());
            self().insertSelective(iRequest, cshTransactionAccount);
        } else {
            // 获取成本中心
            String respCenterParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null, accEntityId, null,
                            null, null);
            if (BaseConstants.NO.equals(respCenterParam)) {
                throw new CshWriteOffException(respCenterException, respCenterException, null);
            } else {
                respCenterId = Long.valueOf(respCenterParam);
            }
            Long accountId = cshTransactionHeaderService.getPrepaymentAccount(iRequest,
                            cshTransactionLine.getTransactionLineId());
            if (accountId == null || accountId == 0 || StringUtils.isEmpty(accountId.toString())) {
                throw new CshTransactionException(CshTransactionException.PREPAYMENT_NOT_FOUND,
                                CshTransactionException.PREPAYMENT_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT);
            CshTransactionAccount dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 默认账套
            GldSetOfBook gldSetOfBook =
                            gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
            // 调用生成段值逻辑
            GldSegment gldSegment = gldSobSegmentService.mappingSegmentValue(cshTransactionLine.getAccEntityId(),
                            gldSetOfBook.getSetOfBooksId(), accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                            dto.getTransactionJeLineId(), "Dr", cshTransactionHeader.getTransactionHeaderId(),
                            cshTransactionLine.getTransactionLineId(), null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        }
    }

    /**
     * 借款单支付-生成汇率差异凭证
     *
     * @author Tagin
     * @date 2019-03-29 17:29
     * @param iRequest 请求
     * @param cshTransactionLine 现金事务行对象
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @param cshWriteOff 核销对象
     * @param revaluationAmount 汇率差
     * @param funPrecision 本位币财务精度
     * @return void
     * @version 1.0
     **/
    public void createLoanRevaluationAccount(IRequest iRequest, CshTransactionLine cshTransactionLine,
                    CshPaymentRequisitionLn cshPaymentRequisitionLn, CshWriteOff cshWriteOff,
                    BigDecimal revaluationAmount, int funPrecision) {
        // Tips：借款申请单支付预留的汇率差异凭证方法，待有需求时可补充逻辑
    }

    /**
     * 借款申请单支付-生成支付凭证
     *
     * @author Tagin
     * @date 2019-03-29 17:14
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @return void
     * @version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generateLoanAccount(IRequest iRequest, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, CshPaymentRequisitionLn cshPaymentRequisitionLn) {
        // 获取核算主体默认本币
        String funCurrency = gldCurrencyMapper.getAccEntityFunCurrCode(cshTransactionLine.getAccEntityId());
        // 支付币种、本位币精度
        int payPrecision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        payPrecision = payPrecision == 0 ? 2 : payPrecision;
        int funPrecision = gldCurrencyMapper.getPrecision(funCurrency);
        funPrecision = funPrecision == 0 ? 2 : funPrecision;
        // 1.0 生成借方凭证
        this.createLoanDrAccount(iRequest, cshPaymentRequisitionLn, cshTransactionHeader, cshTransactionLine,
                        cshWriteOff, payPrecision, funPrecision);
        // 2.0 生成贷方凭证
        this.createCrAccount(iRequest, CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, payPrecision, funPrecision);
        // 3.0 生成往来凭证
        this.createInterAccount(iRequest, CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, cshTransactionHeader,
                        cshTransactionLine, null, cshPaymentRequisitionLn, null, cshWriteOff, payPrecision,
                        funPrecision, funCurrency);
        // 4.0 生成汇率差异凭证
        // Tips：借款申请单支付预留调用汇率差异凭证
    }

    /**
     * 付款申请单支付（零星付款）-生成借方凭证
     *
     * @author Tagin
     * @date 2019-04-30 14:28
     * @param iRequest 请求
     * @param acpRequisitionLn 付款申请单行对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 相近事务行对象
     * @param cshWriteOff 核销对象
     * @param payPrecision 支付币种精度
     * @param funPrecision 本位币精度
     * @return void
     * @version 1.0
     **/
    public void createAcpDrAccount(IRequest iRequest, AcpRequisitionLn acpRequisitionLn,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, int payPrecision, int funPrecision) {
        // 1.0 生成借方凭证
        Long companyId, accEntityId, respCenterId;
        String respCenterException;
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        // 获取参数
        String interfaceParam = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_SYS_INTERFACE_TYPE,
                        null, null, null, null, null, null, null);
        String auitFlag = acpMoPayReqTypeMapper.getAuitFlag(null, acpRequisitionLn.getRequisitionHdsId());
        if (cshTransactionLine.getAccEntityId().compareTo(acpRequisitionLn.getAccEntityId()) == 0) {
            companyId = cshTransactionLine.getCompanyId();
            accEntityId = cshTransactionLine.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_RESP_NOT_FOUND;
        } else {
            companyId = acpRequisitionLn.getCompanyId();
            accEntityId = acpRequisitionLn.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND;
        }
        if (CshWriteOff.DOC_SOURCE_EXPENSE_REPORT.equals(cshWriteOff.getDocumentSource())) {
            ExpReportAccount expReportAccount = cshTransactionHeaderService.getReportCrAccount(
                            expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId()));
            if (expReportAccount == null || expReportAccount.getAccountId() == null
                            || expReportAccount.getAccountId() == 0) {
                throw new CshWriteOffException(CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND,
                                CshWriteOffException.REPORT_ACCOUNT_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(expReportAccount.getRespCenterId());
            cshTransactionAccount.setAccountId(expReportAccount.getAccountId());
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(expReportAccount.getUsageCode());
            cshTransactionAccount.setAccountSegment1(expReportAccount.getAccountSegment1());
            cshTransactionAccount.setAccountSegment2(expReportAccount.getAccountSegment2());
            cshTransactionAccount.setAccountSegment3(expReportAccount.getAccountSegment3());
            cshTransactionAccount.setAccountSegment4(expReportAccount.getAccountSegment4());
            cshTransactionAccount.setAccountSegment5(expReportAccount.getAccountSegment5());
            cshTransactionAccount.setAccountSegment6(expReportAccount.getAccountSegment6());
            cshTransactionAccount.setAccountSegment7(expReportAccount.getAccountSegment7());
            cshTransactionAccount.setAccountSegment8(expReportAccount.getAccountSegment8());
            cshTransactionAccount.setAccountSegment9(expReportAccount.getAccountSegment9());
            cshTransactionAccount.setAccountSegment10(expReportAccount.getAccountSegment10());
            cshTransactionAccount.setAccountSegment11(expReportAccount.getAccountSegment11());
            cshTransactionAccount.setAccountSegment12(expReportAccount.getAccountSegment12());
            cshTransactionAccount.setAccountSegment13(expReportAccount.getAccountSegment13());
            cshTransactionAccount.setAccountSegment14(expReportAccount.getAccountSegment14());
            cshTransactionAccount.setAccountSegment15(expReportAccount.getAccountSegment15());
            cshTransactionAccount.setAccountSegment16(expReportAccount.getAccountSegment16());
            cshTransactionAccount.setAccountSegment17(expReportAccount.getAccountSegment17());
            cshTransactionAccount.setAccountSegment18(expReportAccount.getAccountSegment18());
            cshTransactionAccount.setAccountSegment19(expReportAccount.getAccountSegment19());
            cshTransactionAccount.setAccountSegment20(expReportAccount.getAccountSegment20());
            self().insertSelective(iRequest, cshTransactionAccount);
        } else if (CshTransactionHeader.SYS_INTERFACE_EBS_AP.equals(interfaceParam)
                        && !BaseConstants.YES.equals(auitFlag)) {
            AcpRequisitionAccount acpRequisitionAccount =
                            cshTransactionHeaderService.getAcpRequisitionDrAccount(acpRequisitionLn);
            if (acpRequisitionAccount == null || acpRequisitionAccount.getAccountId() == null) {
                throw new CshTransactionException(CshTransactionException.ACP_AUDIT_ACCOUNT_NOT_FOUND,
                                CshTransactionException.ACP_AUDIT_ACCOUNT_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(acpRequisitionAccount.getRespCenterId());
            cshTransactionAccount.setAccountId(acpRequisitionAccount.getAccountId());
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(acpRequisitionAccount.getUsageCode());
            cshTransactionAccount.setAccountSegment1(acpRequisitionAccount.getAccountSegment1());
            cshTransactionAccount.setAccountSegment2(acpRequisitionAccount.getAccountSegment2());
            cshTransactionAccount.setAccountSegment3(acpRequisitionAccount.getAccountSegment3());
            cshTransactionAccount.setAccountSegment4(acpRequisitionAccount.getAccountSegment4());
            cshTransactionAccount.setAccountSegment5(acpRequisitionAccount.getAccountSegment5());
            cshTransactionAccount.setAccountSegment6(acpRequisitionAccount.getAccountSegment6());
            cshTransactionAccount.setAccountSegment7(acpRequisitionAccount.getAccountSegment7());
            cshTransactionAccount.setAccountSegment8(acpRequisitionAccount.getAccountSegment8());
            cshTransactionAccount.setAccountSegment9(acpRequisitionAccount.getAccountSegment9());
            cshTransactionAccount.setAccountSegment10(acpRequisitionAccount.getAccountSegment10());
            cshTransactionAccount.setAccountSegment11(acpRequisitionAccount.getAccountSegment11());
            cshTransactionAccount.setAccountSegment12(acpRequisitionAccount.getAccountSegment12());
            cshTransactionAccount.setAccountSegment13(acpRequisitionAccount.getAccountSegment13());
            cshTransactionAccount.setAccountSegment14(acpRequisitionAccount.getAccountSegment14());
            cshTransactionAccount.setAccountSegment15(acpRequisitionAccount.getAccountSegment15());
            cshTransactionAccount.setAccountSegment16(acpRequisitionAccount.getAccountSegment16());
            cshTransactionAccount.setAccountSegment17(acpRequisitionAccount.getAccountSegment17());
            cshTransactionAccount.setAccountSegment18(acpRequisitionAccount.getAccountSegment18());
            cshTransactionAccount.setAccountSegment19(acpRequisitionAccount.getAccountSegment19());
            cshTransactionAccount.setAccountSegment20(acpRequisitionAccount.getAccountSegment20());
            self().insertSelective(iRequest, cshTransactionAccount);
        } else {
            // 获取成本中心
            String respCenterParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null, accEntityId, null,
                            null, null);
            if (BaseConstants.NO.equals(respCenterParam)) {
                throw new CshWriteOffException(respCenterException, respCenterException, null);
            } else {
                respCenterId = Long.valueOf(respCenterParam);
            }
            Long accountId = cshTransactionHeaderService.getPayRequisitionAccount(iRequest,
                            cshTransactionLine.getTransactionLineId());
            if (accountId == null || accountId == 0 || StringUtils.isEmpty(accountId.toString())) {
                throw new CshTransactionException(CshTransactionException.PAY_REQUISITION_NOT_FOUND,
                                CshTransactionException.PAY_REQUISITION_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_PAY_REQUISITION);
            CshTransactionAccount dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 默认账套
            GldSetOfBook gldSetOfBook =
                            gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
            // 调用生成段值逻辑
            GldSegment gldSegment = gldSobSegmentService.mappingSegmentValue(cshTransactionLine.getAccEntityId(),
                            gldSetOfBook.getSetOfBooksId(), accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                            dto.getTransactionJeLineId(), "Dr", cshTransactionHeader.getTransactionHeaderId(),
                            cshTransactionLine.getTransactionLineId(), null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        }
    }

    /**
     * 付款申请单支付（零星付款）-生成支付凭证
     *
     * @author Tagin
     * @date 2019-05-05 10:43
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generateAcpAccount(IRequest iRequest, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, AcpRequisitionLn acpRequisitionLn) {
        // 获取核算主体默认本币
        String funCurrency = gldCurrencyMapper.getAccEntityFunCurrCode(cshTransactionLine.getAccEntityId());
        // 支付币种、本位币精度
        int payPrecision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        payPrecision = payPrecision == 0 ? 2 : payPrecision;
        int funPrecision = gldCurrencyMapper.getPrecision(funCurrency);
        funPrecision = funPrecision == 0 ? 2 : funPrecision;
        // 1.0 生成借方凭证
        this.createAcpDrAccount(iRequest, acpRequisitionLn, cshTransactionHeader, cshTransactionLine, cshWriteOff,
                        payPrecision, funPrecision);
        // 2.0 生成贷方凭证
        this.createCrAccount(iRequest, CshDocPayAccEntity.DOC_ACP_REQUISITION, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, payPrecision, funPrecision);
        // 3.0 生成往来凭证
        this.createInterAccount(iRequest, CshDocPayAccEntity.DOC_ACP_REQUISITION, cshTransactionHeader,
                        cshTransactionLine, null, null, acpRequisitionLn, cshWriteOff, payPrecision, funPrecision,
                        funCurrency);
        // 4.0 生成汇率差异凭证
        // Tips：付款申请单单支付预留调用汇率差异凭证
    }


    /**
     * 付款申请单支付（预付款）-生成借方凭证
     *
     * @author Tagin
     * @date 2019-04-30 14:28
     * @param iRequest 请求
     * @param acpRequisitionLn 付款申请单行对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 相近事务行对象
     * @param cshWriteOff 核销对象
     * @param payPrecision 支付币种精度
     * @param funPrecision 本位币精度
     * @return void
     * @version 1.0
     **/
    public void createAcpPreDrAccount(IRequest iRequest, AcpRequisitionLn acpRequisitionLn,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, int payPrecision, int funPrecision) {
        // 1.0 生成借方凭证
        Long companyId, accEntityId, respCenterId;
        String respCenterException;
        CshTransactionAccount cshTransactionAccount = new CshTransactionAccount();
        // 获取参数
        String interfaceParam = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_SYS_INTERFACE_TYPE,
                        null, null, null, null, null, null, null);
        String auitFlag = acpMoPayReqTypeMapper.getAuitFlag(null, acpRequisitionLn.getRequisitionHdsId());
        if (cshTransactionLine.getAccEntityId().compareTo(acpRequisitionLn.getAccEntityId()) == 0) {
            companyId = cshTransactionLine.getCompanyId();
            accEntityId = cshTransactionLine.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_RESP_NOT_FOUND;
        } else {
            companyId = acpRequisitionLn.getCompanyId();
            accEntityId = acpRequisitionLn.getAccEntityId();
            respCenterException = CshWriteOffException.DEFAULT_TO_RESP_NOT_FOUND;
        }
        if (CshTransactionHeader.SYS_INTERFACE_EBS_AP.equals(interfaceParam) && !BaseConstants.YES.equals(auitFlag)) {
            AcpRequisitionAccount acpRequisitionAccount =
                            cshTransactionHeaderService.getAcpRequisitionDrAccount(acpRequisitionLn);
            if (acpRequisitionAccount == null || acpRequisitionAccount.getAccountId() == null) {
                throw new CshTransactionException(CshTransactionException.ACP_AUDIT_ACCOUNT_NOT_FOUND,
                                CshTransactionException.ACP_AUDIT_ACCOUNT_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(acpRequisitionAccount.getRespCenterId());
            cshTransactionAccount.setAccountId(acpRequisitionAccount.getAccountId());
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT);
            cshTransactionAccount.setAccountSegment1(acpRequisitionAccount.getAccountSegment1());
            cshTransactionAccount.setAccountSegment2(acpRequisitionAccount.getAccountSegment2());
            cshTransactionAccount.setAccountSegment3(acpRequisitionAccount.getAccountSegment3());
            cshTransactionAccount.setAccountSegment4(acpRequisitionAccount.getAccountSegment4());
            cshTransactionAccount.setAccountSegment5(acpRequisitionAccount.getAccountSegment5());
            cshTransactionAccount.setAccountSegment6(acpRequisitionAccount.getAccountSegment6());
            cshTransactionAccount.setAccountSegment7(acpRequisitionAccount.getAccountSegment7());
            cshTransactionAccount.setAccountSegment8(acpRequisitionAccount.getAccountSegment8());
            cshTransactionAccount.setAccountSegment9(acpRequisitionAccount.getAccountSegment9());
            cshTransactionAccount.setAccountSegment10(acpRequisitionAccount.getAccountSegment10());
            cshTransactionAccount.setAccountSegment11(acpRequisitionAccount.getAccountSegment11());
            cshTransactionAccount.setAccountSegment12(acpRequisitionAccount.getAccountSegment12());
            cshTransactionAccount.setAccountSegment13(acpRequisitionAccount.getAccountSegment13());
            cshTransactionAccount.setAccountSegment14(acpRequisitionAccount.getAccountSegment14());
            cshTransactionAccount.setAccountSegment15(acpRequisitionAccount.getAccountSegment15());
            cshTransactionAccount.setAccountSegment16(acpRequisitionAccount.getAccountSegment16());
            cshTransactionAccount.setAccountSegment17(acpRequisitionAccount.getAccountSegment17());
            cshTransactionAccount.setAccountSegment18(acpRequisitionAccount.getAccountSegment18());
            cshTransactionAccount.setAccountSegment19(acpRequisitionAccount.getAccountSegment19());
            cshTransactionAccount.setAccountSegment20(acpRequisitionAccount.getAccountSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        } else {
            // 获取成本中心
            String respCenterParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null, accEntityId, null,
                            null, null);
            if (BaseConstants.NO.equals(respCenterParam)) {
                throw new CshWriteOffException(respCenterException, respCenterException, null);
            } else {
                respCenterId = Long.valueOf(respCenterParam);
            }
            Long accountId = cshTransactionHeaderService.getPrepaymentAccount(iRequest,
                            cshTransactionLine.getTransactionLineId());
            if (accountId == null || accountId == 0 || StringUtils.isEmpty(accountId.toString())) {
                throw new CshTransactionException(CshTransactionException.PREPAYMENT_NOT_FOUND,
                                CshTransactionException.PREPAYMENT_NOT_FOUND, null);
            }
            // 创建凭证
            cshTransactionAccount.setTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshTransactionAccount.setWriteOffId(cshWriteOff.getWriteOffId());
            cshTransactionAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            cshTransactionAccount.setDescription(cshTransactionLine.getDescription());
            cshTransactionAccount.setPeriodName(cshWriteOff.getPeriodName());
            cshTransactionAccount.setCompanyId(companyId);
            cshTransactionAccount.setAccEntityId(accEntityId);
            cshTransactionAccount.setRespCenterId(respCenterId);
            cshTransactionAccount.setAccountId(accountId);
            cshTransactionAccount.setEnteredAmountDr(
                            cshWriteOff.getCshWriteOffAmount().setScale(payPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setEnteredAmountCr(null);
            cshTransactionAccount.setFunctionalAmountDr(
                            cshWriteOff.getCshWriteOffAmount().multiply(cshTransactionLine.getExchangeRate())
                                            .setScale(funPrecision, BigDecimal.ROUND_HALF_UP));
            cshTransactionAccount.setFunctionalAmountCr(null);
            cshTransactionAccount.setExchangeRateType(cshTransactionLine.getExchangeRateType());
            cshTransactionAccount.setExchangeRate(cshTransactionLine.getExchangeRate());
            cshTransactionAccount.setCurrencyCode(cshTransactionLine.getCurrencyCode());
            cshTransactionAccount.setJournalDate(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateTz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setJournalDateLtz(cshWriteOff.getWriteOffDate());
            cshTransactionAccount.setGldInterfaceFlag(cshTransactionHeader.getGldInterfaceFlag());
            cshTransactionAccount.setUsageCode(UsageCodeConstants.USAGE_PREPAYMENT);
            CshTransactionAccount dto = self().insertSelective(iRequest, cshTransactionAccount);
            // 默认账套
            GldSetOfBook gldSetOfBook =
                            gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
            // 调用生成段值逻辑
            GldSegment gldSegment = gldSobSegmentService.mappingSegmentValue(cshTransactionLine.getAccEntityId(),
                            gldSetOfBook.getSetOfBooksId(), accountId, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                            dto.getTransactionJeLineId(), "Dr", cshTransactionHeader.getTransactionHeaderId(),
                            cshTransactionLine.getTransactionLineId(), null, null);
            cshTransactionAccount.setAccountSegment1(gldSegment.getSegment1());
            cshTransactionAccount.setAccountSegment2(gldSegment.getSegment2());
            cshTransactionAccount.setAccountSegment3(gldSegment.getSegment3());
            cshTransactionAccount.setAccountSegment4(gldSegment.getSegment4());
            cshTransactionAccount.setAccountSegment5(gldSegment.getSegment5());
            cshTransactionAccount.setAccountSegment6(gldSegment.getSegment6());
            cshTransactionAccount.setAccountSegment7(gldSegment.getSegment7());
            cshTransactionAccount.setAccountSegment8(gldSegment.getSegment8());
            cshTransactionAccount.setAccountSegment9(gldSegment.getSegment9());
            cshTransactionAccount.setAccountSegment10(gldSegment.getSegment10());
            cshTransactionAccount.setAccountSegment11(gldSegment.getSegment11());
            cshTransactionAccount.setAccountSegment12(gldSegment.getSegment12());
            cshTransactionAccount.setAccountSegment13(gldSegment.getSegment13());
            cshTransactionAccount.setAccountSegment14(gldSegment.getSegment14());
            cshTransactionAccount.setAccountSegment15(gldSegment.getSegment15());
            cshTransactionAccount.setAccountSegment16(gldSegment.getSegment16());
            cshTransactionAccount.setAccountSegment17(gldSegment.getSegment17());
            cshTransactionAccount.setAccountSegment18(gldSegment.getSegment18());
            cshTransactionAccount.setAccountSegment19(gldSegment.getSegment19());
            cshTransactionAccount.setAccountSegment20(gldSegment.getSegment20());
            self().updateByPrimaryKey(iRequest, cshTransactionAccount);
        }
    }


    /**
     * 付款申请单支付（预付付款）-生成支付凭证
     *
     * @author Tagin
     * @date 2019-05-05 10:43
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generateAcpPreAccount(IRequest iRequest, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, AcpRequisitionLn acpRequisitionLn) {
        // 获取核算主体默认本币
        String funCurrency = gldCurrencyMapper.getAccEntityFunCurrCode(cshTransactionLine.getAccEntityId());
        // 支付币种、本位币精度
        int payPrecision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        payPrecision = payPrecision == 0 ? 2 : payPrecision;
        int funPrecision = gldCurrencyMapper.getPrecision(funCurrency);
        funPrecision = funPrecision == 0 ? 2 : funPrecision;
        // 1.0 生成借方凭证
        this.createAcpPreDrAccount(iRequest, acpRequisitionLn, cshTransactionHeader, cshTransactionLine, cshWriteOff,
                        payPrecision, funPrecision);
        // 2.0 生成贷方凭证
        this.createCrAccount(iRequest, CshDocPayAccEntity.DOC_ACP_REQUISITION, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, payPrecision, funPrecision);
        // 3.0 生成往来凭证
        this.createInterAccount(iRequest, CshDocPayAccEntity.DOC_ACP_REQUISITION, cshTransactionHeader,
                        cshTransactionLine, null, null, acpRequisitionLn, cshWriteOff, payPrecision, funPrecision,
                        funCurrency);
        // 4.0 生成汇率差异凭证
        // Tips：付款申请单单支付预留调用汇率差异凭证
    }

    @Override
    public List<CshTransactionAccount> queryAccountForFinance(IRequest request, Long registerHdsId, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return cshTransactionAccountMapper.queryAccountForFinance(registerHdsId);
    }

    @Override
    public List<CshTransactionAccount> paymentFinanceInforQuery(IRequest request, Long transactionHeaderId, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return cshTransactionAccountMapper.queryByHeaderId(request, transactionHeaderId);
    }
}
