package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshWriteOffAccount;
import com.hand.hec.csh.mapper.CshWriteOffAccountMapper;
import com.hand.hec.csh.service.ICshWriteOffAccountService;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.dto.ExpReportDist;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.exception.ExpReportAccountException;
import com.hand.hec.expm.mapper.*;
import com.hand.hec.expm.service.IExpReportAccountService;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.dto.GldSegment;
import com.hand.hec.gld.dto.GldTransitAccount;
import com.hand.hec.gld.service.IGldMappingConditionService;
import com.hand.hec.gld.service.IGldSobSegmentService;
import com.hand.hec.gld.service.IGldTransitAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ExpReportAccountServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportAccountServiceImpl extends BaseServiceImpl<ExpReportAccount> implements IExpReportAccountService {
    @Autowired
    private ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    private ExpReportDistMapper expReportDistMapper;

    @Autowired
    private ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    @Autowired
    private GldPeriodMapper gldPeriodMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private IGldExchangeRateService gldExchangeRateService;

    @Autowired
    private IGldMappingConditionService gldMappingConditionService;

    @Autowired
    private ExpAmortizationAccountMapper expAmortizationAccountMapper;

    @Autowired
    private IGldTransitAccountService gldTransitAccountService;

    @Autowired
    private IExpReportAccountService service;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @Autowired
    private ICshWriteOffAccountService cshWriteOffAccountService;

    @Autowired
    private IExpReportHeaderService expReportHeaderService;

    @Autowired
    private CshWriteOffAccountMapper cshWriteOffAccountMapper;

    @Autowired
    private ExpReportAccountMapper expReportAccountMapper;

    /**
     * 获取报销单本币金额
     *
     * @param currencyCode           原币币种
     * @param functionalCurrencyCode 本位币币种
     * @param periodName             期间
     * @param journalDate            凭证日期
     * @param amount                 金额
     * @param expReportAccount       凭证信息
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 10:34
     * @Version 1.0
     **/
    public BigDecimal getFunctionAmount(String currencyCode, String functionalCurrencyCode, Long accEntityId,
                                        String periodName, Date journalDate, BigDecimal amount, ExpReportAccount expReportAccount) {
        BigDecimal functionAmount = new BigDecimal(0);
        BigDecimal exchangeRate = new BigDecimal(0);
        String exchangeRateType = sysParameterService.queryParamValueByCode("DEFAULT_EXCHANGE_RATE_TYPE", null, null,
                null, accEntityId, null, null, null);

        if (currencyCode.equals(functionalCurrencyCode)) {
            exchangeRate = new BigDecimal(1);
            expReportAccount.setExchangeRate(new BigDecimal(1));
        } else {
            if (!exchangeRateType.isEmpty()) {
                // 获取对应的汇率
                exchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, currencyCode, functionalCurrencyCode,
                        exchangeRateType, journalDate, periodName, "N");
                expReportAccount.setExchangeRateType(exchangeRateType);
                expReportAccount.setExchangeRate(exchangeRate);
            }
        }
        // 获取小数精度
        int precision = gldCurrencyMapper.getPrecision(functionalCurrencyCode);
        functionAmount = functionAmount
                .add(amount.multiply(exchangeRate).setScale(precision, BigDecimal.ROUND_HALF_UP));
        return functionAmount;
    }

    /**
     * 创建费用及中转科目凭证
     *
     * @param expReportHeader   报销单头信息
     * @param expReportDist     报销单分配行信息
     * @param periodName        期间信息
     * @param journalDate       凭证日期
     * @param expReportAccounts 凭证信息
     * @param userId            创建人
     * @return String
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 18:52
     * @Version 1.0
     **/
    public String createExpExpenseAccount(ExpReportDist expReportDist, ExpReportHeader expReportHeader,
                                          String periodName, Date journalDate, Long userId, List<ExpReportAccount> expReportAccounts,
                                          String amortizationFlag) {
        ExpReportAccount expReportAccount = new ExpReportAccount();
        String periodComparison = "";
        Long distPeriodNum = gldPeriodMapper.getInternalPeriodNum(expReportDist.getAccEntityId(),
                expReportDist.getPeriodName());
        Long operationPeriodNum = gldPeriodMapper.getInternalPeriodNum(expReportHeader.getAccEntityId(), periodName);
        // 分摊凭证创建的期间比较依据
        if (distPeriodNum.longValue() > operationPeriodNum.longValue()) {
            periodComparison = "EARLIER";
        } else if (distPeriodNum.longValue() == operationPeriodNum.longValue()) {
            periodComparison = "IN";
        } else if (distPeriodNum.longValue() < operationPeriodNum.longValue()) {
            periodComparison = "LATER";
        }
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        // 设置预置匹配组预置值
        gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExp(
                expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                expReportDist.getCompanyId() == null ? null : expReportDist.getCompanyId().toString(),
                expReportDist.getPositionId() == null ? null : expReportDist.getPositionId().toString(),
                expReportDist.getAccEntityId() == null ? null : expReportDist.getAccEntityId().toString(),
                expReportDist.getRespCenterId() == null ? null : expReportDist.getRespCenterId().toString(),
                expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                expReportDist.getPaymentCurrencyCode(),
                expReportDist.getMoExpenseItemId() == null ? null : expReportDist.getMoExpenseItemId().toString(),
                expReportDist.getMoExpenseTypeId() == null ? null : expReportDist.getMoExpenseTypeId().toString(),
                periodComparison,
                expReportHeader.getEmployeeTypeId() == null ? null : expReportHeader.getEmployeeTypeId().toString(),
                expReportDist.getUnitId() == null ? null : expReportDist.getUnitId().toString(),
                expReportDist.getUnitTypeId() == null ? null : expReportDist.getUnitTypeId().toString());
        // 通过匹配组获取借方科目
        Long accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE",
                expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
        if(accountId == null){
            return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR;
        }
        if (accountId.longValue() == 0) {
            return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR;
        } else {
            expReportAccount.setAccountId(accountId);
        }
        if (expReportDist.getRespCenterId() == null) {
            return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
        } else {
            expReportAccount.setRespCenterId(expReportDist.getRespCenterId());
        }
        /*
         * 1.0.3 根据发票入账方式获取凭证费用金额 1、发票入账方式：全税额入抵扣 则 费用金额为单据金额 - 发票税额； 2、发票入账方式：报销额入抵扣，单独转出 / 报销额入抵扣，不单独转出 则
         * 费用金额为单据金额 - 报销税额
         */
        String accountMethod = sysParameterService.queryParamValueByCode("VAT_INVOICE_ACCOUNT_METHOD", null, null,
                expReportDist.getCompanyId(), expReportDist.getAccEntityId(), expReportDist.getSetOfBooksId(),
                null, null);
        if ("Y".equals(accountMethod) || "N".equals(accountMethod) || accountMethod.isEmpty()) {
            expReportAccount.setEnteredAmountDr(
                    expReportDist.getPaymentAmount().subtract(expReportDist.getPaymentSpTaxAmount() == null ? BigDecimal.ZERO : expReportDist.getPaymentSpTaxAmount()));
        } else if ("S".equals(accountMethod) || "D".equals(accountMethod)) {
            expReportAccount.setEnteredAmountDr(
                    expReportDist.getPaymentAmount().subtract(expReportDist.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDist.getPaymentRpTaxAmount()));
        }
        expReportAccount.setFunctionalAmountDr(getFunctionAmount(expReportDist.getPaymentCurrencyCode(),
                expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(), periodName,
                journalDate, expReportAccount.getEnteredAmountDr(), expReportAccount));
        expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
        expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
        expReportAccount.setExpReportDistId(expReportDist.getExpReportDistId());
        expReportAccount.setDescription(expReportDist.getDescription());
        expReportAccount.setCurrencyCode(expReportDist.getPaymentCurrencyCode());
        expReportAccount.setJournalDate(journalDate);
        expReportAccount.setPeriodName(periodName);
        expReportAccount.setUsageCode("EMPLOYEE_EXPENSE");
        expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
        expReportAccount.setGldInterfaceFlag("N");
        expReportAccount.setCreatedBy(userId);
        expReportAccount.setCreationDate(new Date());
        expReportAccount.setLastUpdatedBy(userId);
        expReportAccount.setLastUpdateDate(new Date());
        expReportAccounts.add(expReportAccount);

        // 判断费用是否需要分摊
        if ("Y".equals(amortizationFlag)) {
            // 判断对应的是否是分摊科目
            int count = 0;
            count = expAmortizationAccountMapper.checkAccountExist(accountId, expReportHeader.getSetOfBooksId());
            if (count > 0) {
                // 获取创建费用凭证期间的第一天做为摊销凭证的凭证日期
                Date firstDate = new Date();
                firstDate = gldPeriodMapper.getFirstDate(periodName, expReportDist.getAccEntityId());
                // 创建摊销借方凭证
                ExpReportAccount expReportAmrDrAccount = new ExpReportAccount();
                expReportAmrDrAccount = expReportAccount;
                // 设置预置匹配组预置值
                gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExp(
                        expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                        expReportDist.getCompanyId() == null ? null : expReportDist.getCompanyId().toString(),
                        expReportDist.getPositionId() == null ? null : expReportDist.getPositionId().toString(),
                        expReportDist.getAccEntityId() == null ? null : expReportDist.getAccEntityId().toString(),
                        expReportDist.getRespCenterId() == null ? null : expReportDist.getRespCenterId().toString(),
                        expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                        expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                        expReportDist.getPaymentCurrencyCode(),
                        expReportDist.getMoExpenseItemId() == null ? null : expReportDist.getMoExpenseItemId().toString(),
                        expReportDist.getMoExpenseTypeId() == null ? null : expReportDist.getMoExpenseTypeId().toString(),
                        "IN",
                        expReportHeader.getEmployeeTypeId() == null ? null : expReportHeader.getEmployeeTypeId().toString(),
                        expReportDist.getUnitId() == null ? null : expReportDist.getUnitId().toString(),
                        expReportDist.getUnitTypeId() == null ? null : expReportDist.getUnitTypeId().toString());
                // 通过匹配组获取借方科目
                Long amrAccountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE",
                        expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                if(amrAccountId == null){
                    return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR;
                }
                if (amrAccountId.longValue() == 0) {
                    return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR;
                } else {
                    expReportAmrDrAccount.setAccountId(amrAccountId);
                }
                expReportAmrDrAccount.setJournalDate(firstDate);
                expReportAmrDrAccount.setPeriodName(expReportDist.getPeriodName());
                expReportAccounts.add(expReportAmrDrAccount);
                // 创建摊销贷方凭证
                ExpReportAccount expReportAmrCrAccount = new ExpReportAccount();
                expReportAmrCrAccount = expReportAccount;
                expReportAmrCrAccount.setJournalDate(firstDate);
                expReportAmrCrAccount.setPeriodName(expReportDist.getPeriodName());
                expReportAccounts.add(expReportAmrCrAccount);
            }
        }

        // 获取中转科目
        GldTransitAccount gldTransitAccount = new GldTransitAccount();
        gldTransitAccount.setMagOrgId(expReportHeader.getMagOrgId());
        gldTransitAccount.setSetOfBooksId(expReportHeader.getSetOfBooksId());
        gldTransitAccount.setMoExpenseItemId(expReportDist.getMoExpenseItemId());
        gldTransitAccount.setMoExpReportTypeId(expReportHeader.getMoExpReportTypeId());
        gldTransitAccount.setAccEntityId(expReportDist.getAccEntityId());
        GldTransitAccount gldTransitAccount1 = gldTransitAccountService.getTransitAccount(gldTransitAccount);
        if(gldTransitAccount1 != null) {
            if (gldTransitAccount1.getCrAccountId() != null && gldTransitAccount1.getDrAccountId() != null) {
                // 中转科目-借方
                ExpReportAccount expReportTnsDrAccount = new ExpReportAccount();
                expReportTnsDrAccount = expReportAccount;
                expReportTnsDrAccount.setAccountId(gldTransitAccount1.getDrAccountId());
                expReportAccounts.add(expReportTnsDrAccount);
                // 中转科目-贷方
                ExpReportAccount expReportTnsCrAccount = new ExpReportAccount();
                expReportTnsCrAccount = expReportAccount;
                expReportTnsCrAccount.setAccountId(gldTransitAccount1.getCrAccountId());
                expReportTnsCrAccount.setUsageCode("EMPLOYEE_EXPENSE_CLEARING");
                expReportTnsCrAccount.setEnteredAmountCr(expReportAccount.getEnteredAmountDr());
                expReportTnsCrAccount.setEnteredAmountDr(null);
                expReportTnsCrAccount.setFunctionalAmountCr(expReportAccount.getFunctionalAmountDr());
                expReportTnsCrAccount.setFunctionalAmountDr(null);
                expReportAccounts.add(expReportTnsCrAccount);
            }
        }
        return "";
    }

    /**
     * 创建报销单借方凭证
     *
     * @param expReportHeader 报销单头信息
     * @param expReportDists  报销单分配行信息
     * @param periodName      凭证期间
     * @param expReportHeader 报销单头信息
     * @return String
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/14 14:48
     * @Version 1.0
     **/
    public String createExpReportDrAccount(ExpReportHeader expReportHeader, List<ExpReportDist> expReportDists,
                                           String periodName, Date journalDate, List<ExpReportAccount> expReportAccounts, Long userId,
                                           String amortizationFlag) {
        if (expReportDists.size() != 0 && expReportDists != null) {
            for (ExpReportDist expReportDist : expReportDists) {
                String errorCode = "";
                // 生成凭证借方【含往来/中转的贷方（若存在）】
                if (expReportDist.getAccEntityId().longValue() == expReportHeader.getAccEntityId().longValue()) {
                    // 单据头核算主体/分配行核算主体相等
                    // 生成借方凭证 【费用凭证】
                    errorCode = createExpExpenseAccount(expReportDist, expReportHeader, periodName, journalDate, userId,
                            expReportAccounts, amortizationFlag);
                    if (!errorCode.isEmpty()) {
                        return errorCode;
                    }
                } else {
                    // 单据头核算主体/分配行核算主体不相等
                    // 生成借方凭证 【费用凭证】
                    errorCode = createExpExpenseAccount(expReportDist, expReportHeader, periodName, journalDate, userId,
                            expReportAccounts, amortizationFlag);
                    if (!errorCode.isEmpty()) {
                        return errorCode;
                    }
                    // 生成借方凭证 【往来凭证-单据头核算主体_记内部往来-应收】
                    ExpReportAccount expReportAccount = new ExpReportAccount();
                    List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
                    gldMappingConditions = gldMappingConditionService.accBuilderEmpCompanyAr(
                            expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                            expReportDist.getCompanyId() == null ? null : expReportDist.getCompanyId().toString(),
                            expReportDist.getPositionId() == null ? null : expReportDist.getPositionId().toString(),
                            expReportDist.getAccEntityId() == null ? null : expReportDist.getAccEntityId().toString(),
                            expReportDist.getRespCenterId() == null ? null : expReportDist.getRespCenterId().toString(),
                            expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDist.getPaymentCurrencyCode(),
                            expReportDist.getMoExpenseItemId() == null ? null : expReportDist.getMoExpenseItemId().toString(),
                            expReportDist.getMoExpenseTypeId() == null ? null : expReportDist.getMoExpenseTypeId().toString(),
                            expReportHeader.getAccEntityId() == null ? null : expReportHeader.getAccEntityId().toString());
                    // 通过匹配组获取借方科目
                    Long accountId = gldMappingConditionService.getAccount(gldMappingConditions, "INTERCOMPANY_AR",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.EXP5140_GET_INTERCOMPANY_AR_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_INTERCOMPANY_AR_ACC_ERROR;
                    } else {
                        expReportAccount.setAccountId(accountId);
                    }
                    if (expReportDist.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportAccount.setRespCenterId(expReportDist.getRespCenterId());
                    }
                    /*
                     * 2.0.8 根据发票入账方式获取凭证费用金额 1、发票入账方式：全税额入抵扣 则 费用金额为单据金额 - 发票税额； 2、发票入账方式：报销额入抵扣，单独转出 / 报销额入抵扣，不单独转出 则
                     * 费用金额为单据金额 - 报销税额
                     */
                    String accountMethod = sysParameterService.queryParamValueByCode("VAT_INVOICE_ACCOUNT_METHOD", null,
                            null, expReportDist.getCompanyId(), expReportDist.getAccEntityId(),
                            expReportDist.getSetOfBooksId(), null, null);
                    if ("Y".equals(accountMethod) || "N".equals(accountMethod) || accountMethod.isEmpty()) {
                        expReportAccount.setEnteredAmountDr(expReportDist.getPaymentAmount()
                                .subtract(expReportDist.getPaymentSpTaxAmount() == null ? BigDecimal.ZERO : expReportDist.getPaymentSpTaxAmount()));
                    } else if ("S".equals(accountMethod) || "D".equals(accountMethod)) {
                        expReportAccount.setEnteredAmountDr(expReportDist.getPaymentAmount()
                                .subtract(expReportDist.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDist.getPaymentRpTaxAmount()));
                    }
                    expReportAccount.setFunctionalAmountDr(getFunctionAmount(expReportDist.getPaymentCurrencyCode(),
                            expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                            periodName, journalDate, expReportAccount.getEnteredAmountDr(), expReportAccount));
                    expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportAccount.setExpReportDistId(expReportDist.getExpReportDistId());
                    expReportAccount.setDescription(expReportDist.getDescription());
                    expReportAccount.setCurrencyCode(expReportDist.getPaymentCurrencyCode());
                    expReportAccount.setJournalDate(journalDate);
                    expReportAccount.setPeriodName(periodName);
                    expReportAccount.setUsageCode("INTERCOMPANY_AR");
                    expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
                    expReportAccount.setGldInterfaceFlag("N");
                    expReportAccount.setCreatedBy(userId);
                    expReportAccount.setCreationDate(new Date());
                    expReportAccount.setLastUpdatedBy(userId);
                    expReportAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportAccount);
                    // 生成贷方凭证 【往来凭证-单据行核算主体_记内部往来-应付】
                    ExpReportAccount expReportAccount1 = new ExpReportAccount();
                    BeanUtils.copyProperties(expReportAccount,expReportAccount1);
                    List<GldMappingCondition> gldMappingConditions1 = new ArrayList<>();
                    gldMappingConditions1 = gldMappingConditionService.accBuilderEmpCompanyAp(
                            expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                            expReportDist.getCompanyId() == null ? null : expReportDist.getCompanyId().toString(),
                            expReportDist.getPositionId() == null ? null : expReportDist.getPositionId().toString(),
                            expReportDist.getAccEntityId() == null ? null : expReportDist.getAccEntityId().toString(),
                            expReportDist.getRespCenterId() == null ? null : expReportDist.getRespCenterId().toString(),
                            expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDist.getPaymentCurrencyCode(),
                            expReportDist.getMoExpenseItemId() == null ? null : expReportDist.getMoExpenseItemId().toString(),
                            expReportDist.getMoExpenseTypeId() == null ? null : expReportDist.getMoExpenseTypeId().toString(),
                            expReportHeader.getAccEntityId() == null ? null : expReportHeader.getAccEntityId().toString());
                    // 通过匹配组获取借方科目
                    Long accountId1 = gldMappingConditionService.getAccount(gldMappingConditions1, "INTERCOMPANY_AP",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId1 == null){
                        return ExpReportAccountException.EXP5140_GET_INTERCOMPANY_AP_ACC_ERROR;
                    }
                    if (accountId1.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_INTERCOMPANY_AP_ACC_ERROR;
                    } else {
                        expReportAccount1.setAccountId(accountId1);
                    }
                    expReportAccount1.setEnteredAmountCr(expReportAccount.getEnteredAmountDr());
                    expReportAccount1.setFunctionalAmountCr(expReportAccount.getFunctionalAmountDr());
                    expReportAccount1.setEnteredAmountDr(null);
                    expReportAccount1.setFunctionalAmountDr(null);
                    expReportAccount1.setUsageCode("INTERCOMPANY_AP");
                    expReportAccounts.add(expReportAccount1);
                }
            }
        }
        return "";
    }

    /**
     * 创建报销单借方税金凭证
     *
     * @param expReportHeader   报销单头信息
     * @param expReportDistsTax 报销单税务行信息
     * @param periodName        期间信息
     * @param journalDate       凭证日期
     * @param expReportAccounts 凭证信息
     * @param userId            创建人
     * @return String
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/14 14:48
     * @Version 1.0
     **/
    public String createExpReportDrTaxAccount(ExpReportHeader expReportHeader, List<ExpReportDist> expReportDistsTax,
                                              String periodName, Date journalDate, List<ExpReportAccount> expReportAccounts, Long userId) {
        // 1.0.1 判断进项税、进项税待抵扣凭证生成条件
        if (!expReportDistsTax.isEmpty()) {
            for (ExpReportDist expReportDistTax : expReportDistsTax) {
                // 生成借方凭证 【进项税凭证-EMPLOYEE_EXPENSE_TAX】
                ExpReportAccount expReportAccount = new ExpReportAccount();
                String ipttaxMethod = "";
                ipttaxMethod = sysParameterService.queryParamValueByCode("VAT_INPUT_TAX_ACCOUNT_METHOD", null, null,
                        expReportDistTax.getCompanyId(), expReportDistTax.getAccEntityId(),
                        expReportDistTax.getSetOfBooksId(), null, null);
                String accountMethod = "";
                accountMethod = sysParameterService.queryParamValueByCode("VAT_INVOICE_ACCOUNT_METHOD", null, null,
                        expReportDistTax.getCompanyId(), expReportDistTax.getAccEntityId(),
                        expReportDistTax.getSetOfBooksId(), null, null);
                List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
                if ("D".equals(ipttaxMethod)) {
                    // 1.0.4 生成进项税 1 或 未配置参数的凭证
                    gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExpTax(
                            expReportDistTax.getCompanyId() == null ? null : expReportDistTax.getCompanyId().toString(),
                            expReportDistTax.getPositionId() == null ? null : expReportDistTax.getPositionId().toString(),
                            expReportDistTax.getAccEntityId() == null ? null : expReportDistTax.getAccEntityId().toString(),
                            expReportHeader.getRespCenterId() == null ? null : expReportHeader.getRespCenterId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistTax.getTaxTypeId() == null ? null : expReportDistTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    Long accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE_TAX",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    } else {
                        expReportAccount.setAccountId(accountId);
                    }
                    if (expReportHeader.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportAccount.setRespCenterId(expReportHeader.getRespCenterId());
                    }
                    if ("S".equals(accountMethod)) {
                        expReportAccount.setEnteredAmountDr(expReportDistTax.getPaymentRpTaxAmount());
                    } else {
                        expReportAccount.setEnteredAmountDr(expReportDistTax.getPaymentSpTaxAmount());
                    }
                    expReportAccount.setFunctionalAmountDr(getFunctionAmount(expReportDistTax.getPaymentCurrencyCode(),
                            expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                            periodName, journalDate, expReportAccount.getEnteredAmountDr(), expReportAccount));
                    expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportAccount.setExpReportDistId(expReportDistTax.getExpReportDistId());
                    expReportAccount.setDescription(expReportDistTax.getDescription());
                    expReportAccount.setCurrencyCode(expReportDistTax.getPaymentCurrencyCode());
                    expReportAccount.setJournalDate(journalDate);
                    expReportAccount.setPeriodName(periodName);
                    expReportAccount.setUsageCode("EMPLOYEE_EXPENSE_TAX");
                    expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
                    expReportAccount.setGldInterfaceFlag("N");
                    expReportAccount.setCreatedBy(userId);
                    expReportAccount.setCreationDate(new Date());
                    expReportAccount.setLastUpdatedBy(userId);
                    expReportAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportAccount);
                    // 1.0.5 生成进项税 2 的凭证
                    if ("S".equals(accountMethod)) {
                        BigDecimal enteredAmountDr = expReportDistTax.getPaymentSpTaxAmount()
                                .subtract(expReportDistTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistTax.getPaymentRpTaxAmount());
                        if (enteredAmountDr.compareTo(new BigDecimal(0)) > 0) {
                            expReportAccount.setEnteredAmountDr(enteredAmountDr);
                            expReportAccount.setFunctionalAmountDr(
                                    getFunctionAmount(expReportDistTax.getPaymentCurrencyCode(),
                                            expReportHeader.getFunctionalCurrencyCode(),
                                            expReportHeader.getAccEntityId(), periodName, journalDate,
                                            expReportAccount.getEnteredAmountDr(), expReportAccount));
                            expReportAccounts.add(expReportAccount);
                        }
                    }
                } else if ("I".equals(ipttaxMethod)) {
                    /*
                     * Note：进项税待抵扣（先进待抵扣进项税） 报销额入抵扣，单独转出 两行进项税待抵扣凭证 进项税待抵扣 1：报销税额 进项税待抵扣 2：发票税额 - 报销税额
                     */
                    // 1.0.6 生成进项税 1 或 未配置参数的凭证
                    gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExpTax(
                            expReportDistTax.getCompanyId() == null ? null : expReportDistTax.getCompanyId().toString(),
                            expReportDistTax.getPositionId() == null ? null : expReportDistTax.getPositionId().toString(),
                            expReportDistTax.getAccEntityId() == null ? null : expReportDistTax.getAccEntityId().toString(),
                            expReportHeader.getRespCenterId() == null ? null : expReportHeader.getRespCenterId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistTax.getTaxTypeId() == null ? null : expReportDistTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    Long accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE_TAX",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    } else {
                        expReportAccount.setAccountId(accountId);
                    }
                    if (expReportHeader.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportAccount.setRespCenterId(expReportHeader.getRespCenterId());
                    }
                    if ("S".equals(accountMethod)) {
                        expReportAccount.setEnteredAmountDr(expReportDistTax.getPaymentRpTaxAmount());
                    } else if ("Y".equals(accountMethod) || "D".equals(accountMethod)) {
                        expReportAccount.setEnteredAmountDr(expReportDistTax.getPaymentSpTaxAmount());
                    }
                    expReportAccount.setFunctionalAmountDr(getFunctionAmount(expReportDistTax.getPaymentCurrencyCode(),
                            expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                            periodName, journalDate, expReportAccount.getEnteredAmountDr(), expReportAccount));
                    expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportAccount.setExpReportDistId(expReportDistTax.getExpReportDistId());
                    expReportAccount.setDescription(expReportDistTax.getDescription());
                    expReportAccount.setCurrencyCode(expReportDistTax.getPaymentCurrencyCode());
                    expReportAccount.setJournalDate(journalDate);
                    expReportAccount.setPeriodName(periodName);
                    expReportAccount.setUsageCode("EMPLOYEE_EXPENSE_TAX");
                    expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
                    expReportAccount.setGldInterfaceFlag("N");
                    expReportAccount.setCreatedBy(userId);
                    expReportAccount.setCreationDate(new Date());
                    expReportAccount.setLastUpdatedBy(userId);
                    expReportAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportAccount);
                    // 1.0.7 生成进项税 2 的凭证
                    if ("S".equals(accountMethod)) {
                        BigDecimal enteredAmountDr = expReportDistTax.getPaymentSpTaxAmount()
                                .subtract(expReportDistTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistTax.getPaymentRpTaxAmount());
                        if (enteredAmountDr.compareTo(new BigDecimal(0)) > 0) {
                            expReportAccount.setEnteredAmountDr(enteredAmountDr);
                            expReportAccount.setFunctionalAmountDr(
                                    getFunctionAmount(expReportDistTax.getPaymentCurrencyCode(),
                                            expReportHeader.getFunctionalCurrencyCode(),
                                            expReportHeader.getAccEntityId(), periodName, journalDate,
                                            expReportAccount.getEnteredAmountDr(), expReportAccount));
                            expReportAccounts.add(expReportAccount);
                        }
                    }
                } else {
                    // 未配置进项税待抵扣参数 正常生成税额凭证
                    gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExpTax(
                            expReportDistTax.getCompanyId() == null ? null : expReportDistTax.getCompanyId().toString(),
                            expReportDistTax.getPositionId() == null ? null : expReportDistTax.getPositionId().toString(),
                            expReportDistTax.getAccEntityId() == null ? null : expReportDistTax.getAccEntityId().toString(),
                            expReportHeader.getRespCenterId() == null ? null : expReportHeader.getRespCenterId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistTax.getTaxTypeId() == null ? null : expReportDistTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    Long accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE_TAX",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    } else {
                        expReportAccount.setAccountId(accountId);
                    }
                    if (expReportHeader.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportAccount.setRespCenterId(expReportHeader.getRespCenterId());
                    }
                    expReportAccount.setEnteredAmountDr(expReportDistTax.getPaymentSpTaxAmount());
                    expReportAccount.setFunctionalAmountDr(getFunctionAmount(expReportDistTax.getPaymentCurrencyCode(),
                            expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                            periodName, journalDate, expReportAccount.getEnteredAmountDr(), expReportAccount));
                    expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportAccount.setExpReportDistId(expReportDistTax.getExpReportDistId());
                    expReportAccount.setDescription(expReportDistTax.getDescription());
                    expReportAccount.setCurrencyCode(expReportDistTax.getPaymentCurrencyCode());
                    expReportAccount.setJournalDate(journalDate);
                    expReportAccount.setPeriodName(periodName);
                    expReportAccount.setUsageCode("EMPLOYEE_EXPENSE_TAX");
                    expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
                    expReportAccount.setGldInterfaceFlag("N");
                    expReportAccount.setCreatedBy(userId);
                    expReportAccount.setCreationDate(new Date());
                    expReportAccount.setLastUpdatedBy(userId);
                    expReportAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportAccount);
                }
                // 生成贷方凭证 【进项税转出凭证-TAX_TRANSFER_OUT】
                if ("S".equals(accountMethod) || "D".equals(accountMethod)) {
                    ExpReportAccount expReportAccount1 = new ExpReportAccount();
                    gldMappingConditions = gldMappingConditionService.accBuilderTaxTransferOut(
                            expReportDistTax.getInvoiceItemId() == null ? null : expReportDistTax.getInvoiceItemId().toString(),
                            expReportDistTax.getInvoiceUsedeId() == null ? null : expReportDistTax.getInvoiceUsedeId().toString(),
                            expReportDistTax.getTaxTypeId() == null ? null : expReportDistTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    Long accountId = gldMappingConditionService.getAccount(gldMappingConditions, "TAX_TRANSFER_OUT",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.EXP5140_GET_TAX_TRANSFER_OUT_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_TAX_TRANSFER_OUT_ACC_ERROR;
                    } else {
                        expReportAccount1.setAccountId(accountId);
                    }
                    if (expReportHeader.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportAccount1.setRespCenterId(expReportHeader.getRespCenterId());
                    }
                    expReportAccount1.setEnteredAmountDr(expReportDistTax.getPaymentSpTaxAmount());
                    expReportAccount1.setFunctionalAmountDr(getFunctionAmount(expReportDistTax.getPaymentCurrencyCode(),
                            expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                            periodName, journalDate, expReportAccount.getEnteredAmountDr(), expReportAccount));
                    expReportAccount1.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportAccount1.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportAccount1.setExpReportDistId(expReportDistTax.getExpReportDistId());
                    expReportAccount1.setDescription(expReportDistTax.getDescription());
                    expReportAccount1.setCurrencyCode(expReportDistTax.getPaymentCurrencyCode());
                    expReportAccount1.setJournalDate(journalDate);
                    expReportAccount1.setPeriodName(periodName);
                    expReportAccount1.setUsageCode("TAX_TRANSFER_OUT");
                    expReportAccount1.setSourceCode("EXPENSE_REPORT_AUDIT");
                    expReportAccount1.setGldInterfaceFlag("N");
                    expReportAccount1.setCreatedBy(userId);
                    expReportAccount1.setCreationDate(new Date());
                    expReportAccount1.setLastUpdatedBy(userId);
                    expReportAccount1.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportAccount1);
                }
            }
        }
        return "";
    }

    /**
     * @param expReportHeader       报销单头信息
     * @param expReportPmtSchedules 报销单计划付款行信息
     * @param periodName            期间信息
     * @param journalDate           凭证日期
     * @param expReportAccounts     凭证信息
     * @param userId                创建人
     * @return String
     * @Description 创建报销单贷方凭证
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/14 14:48
     * @Version 1.0
     **/
    public String createExpReportCrAccount(ExpReportHeader expReportHeader,
                                           List<ExpReportPmtSchedule> expReportPmtSchedules, String periodName, Date journalDate,
                                           List<ExpReportAccount> expReportAccounts, Long userId) {
        if (!expReportPmtSchedules.isEmpty()) {
            for (ExpReportPmtSchedule expReportPmtSchedule : expReportPmtSchedules) {
                ExpReportAccount expReportAccount = new ExpReportAccount();
                List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
                // 设置预置匹配组预置值
                gldMappingConditions = gldMappingConditionService.accBuilderExpClearing(
                        expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                        expReportHeader.getCompanyId() == null ? null : expReportHeader.getCompanyId().toString(),
                        expReportHeader.getPositionId() == null ? null : expReportHeader.getPositionId().toString(),
                        expReportHeader.getAccEntityId() == null ? null : expReportHeader.getAccEntityId().toString(),
                        expReportHeader.getRespCenterId() == null ? null : expReportHeader.getRespCenterId().toString(),
                        expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                        expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                        expReportPmtSchedule.getPaymentCurrencyCode(),
                        expReportHeader.getEmployeeTypeId() == null ? null : expReportHeader.getEmployeeTypeId().toString(),
                        expReportPmtSchedule.getPayeeCategory(),
                        expReportPmtSchedule.getPayeeTypeId() == null ? null : expReportPmtSchedule.getPayeeTypeId().toString(),
                        expReportPmtSchedule.getPayeeId() == null ? null : expReportPmtSchedule.getPayeeId().toString(),
                        expReportPmtSchedule.getPaymentUsedeId() == null ? null : expReportPmtSchedule.getPaymentUsedeId().toString());
                // 通过匹配组获取贷方科目
                Long accountId = gldMappingConditionService.getAccount(gldMappingConditions,
                        "EMPLOYEE_EXPENSE_CLEARING", expReportHeader.getMagOrgId(),
                        expReportHeader.getSetOfBooksId());
                if (accountId == null) {
                    return ExpReportAccountException.EXP5140_GET_EXP_CLE_ACC_ERROR;
                }
                if (accountId.longValue() == 0) {
                    return ExpReportAccountException.EXP5140_GET_EXP_CLE_ACC_ERROR;
                } else {
                    expReportAccount.setAccountId(accountId);
                }
                if (expReportHeader.getRespCenterId() == null) {
                    return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                } else {
                    expReportAccount.setRespCenterId(expReportHeader.getRespCenterId());
                }
                /*
                 * 1.0.3 根据计划付款行是否存在税额获取贷方金额 1、存在税额：计划付款行金额 - 税额 2、不存在税额：计划付款行金额
                 */
                expReportAccount.setEnteredAmountCr(
                        expReportPmtSchedule.getDueAmount().subtract(expReportPmtSchedule.getTaxAmount() == null ? BigDecimal.ZERO : expReportPmtSchedule.getTaxAmount()));
                expReportAccount.setFunctionalAmountCr(getFunctionAmount(expReportPmtSchedule.getPaymentCurrencyCode(),
                        expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                        periodName, journalDate, expReportPmtSchedule.getDueAmount(), expReportAccount));
                expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
                expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                expReportAccount.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
                expReportAccount.setDescription(expReportPmtSchedule.getDescription());
                expReportAccount.setCurrencyCode(expReportPmtSchedule.getPaymentCurrencyCode());
                expReportAccount.setJournalDate(journalDate);
                expReportAccount.setPeriodName(periodName);
                expReportAccount.setUsageCode("EMPLOYEE_EXPENSE_CLEARING");
                expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
                expReportAccount.setGldInterfaceFlag("N");
                expReportAccount.setCreatedBy(userId);
                expReportAccount.setCreationDate(new Date());
                expReportAccount.setLastUpdatedBy(userId);
                expReportAccount.setLastUpdateDate(new Date());
                expReportAccounts.add(expReportAccount);
            }
        }
        return "";
    }

    /**
     * @param expReportHeader          报销单头信息
     * @param expReportPmtSchedulesTax 报销单计划付款税务行信息
     * @param periodName               期间信息
     * @param journalDate              凭证日期
     * @param expReportAccounts        凭证信息
     * @param userId                   创建人
     * @return String
     * @Description 创建报销单贷方税金凭证
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/14 14:48
     * @Version 1.0
     **/
    public String createExpReportCrTaxAccount(ExpReportHeader expReportHeader,
                                              List<ExpReportPmtSchedule> expReportPmtSchedulesTax, String periodName, Date journalDate,
                                              List<ExpReportAccount> expReportAccounts, Long userId) {
        if (!expReportPmtSchedulesTax.isEmpty()) {
            for (ExpReportPmtSchedule expReportPmtSchedule : expReportPmtSchedulesTax) {
                ExpReportAccount expReportAccount = new ExpReportAccount();
                List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
                // 设置预置匹配组预置值
                gldMappingConditions = gldMappingConditionService.accBuilderExpClearTax(
                        expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                        expReportHeader.getCompanyId() == null ? null : expReportHeader.getCompanyId().toString(),
                        expReportHeader.getPositionId() == null ? null : expReportHeader.getPositionId().toString(),
                        expReportHeader.getAccEntityId() == null ? null : expReportHeader.getAccEntityId().toString(),
                        expReportHeader.getRespCenterId() == null ? null : expReportHeader.getRespCenterId().toString(),
                        expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                        expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                        expReportPmtSchedule.getPaymentCurrencyCode(),
                        expReportHeader.getEmployeeTypeId() == null ? null : expReportHeader.getEmployeeTypeId().toString(),
                        expReportPmtSchedule.getPayeeCategory(),
                        expReportPmtSchedule.getPayeeTypeId() == null ? null : expReportPmtSchedule.getPayeeTypeId().toString(),
                        expReportPmtSchedule.getPayeeId() == null ? null : expReportPmtSchedule.getPayeeId().toString(),
                        expReportPmtSchedule.getPaymentUsedeId() == null ? null : expReportPmtSchedule.getPaymentUsedeId().toString(),
                        expReportPmtSchedule.getTaxTypeId() == null ? null : expReportPmtSchedule.getTaxTypeId().toString());
                // 通过匹配组获取贷方科目
                Long accountId = gldMappingConditionService.getAccount(gldMappingConditions,
                        "EMPLOYEE_EXPENSE_CLEARING_TAX", expReportHeader.getMagOrgId(),
                        expReportHeader.getSetOfBooksId());
                if(accountId == null){
                    return ExpReportAccountException.EXP5140_GET_EXP_CLE_TAX_ACC_ERROR;
                }
                if (accountId.longValue() == 0) {
                    return ExpReportAccountException.EXP5140_GET_EXP_CLE_TAX_ACC_ERROR;
                } else {
                    expReportAccount.setAccountId(accountId);
                }
                if (expReportHeader.getRespCenterId() == null) {
                    return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                } else {
                    expReportAccount.setRespCenterId(expReportHeader.getRespCenterId());
                }
                /*
                 * 1.0.3 根据计划付款行是否存在税额获取贷方金额 1、存在税额：计划付款行金额 - 税额 2、不存在税额：计划付款行金额
                 */
                expReportAccount.setEnteredAmountCr(expReportPmtSchedule.getTaxAmount());
                expReportAccount.setFunctionalAmountCr(getFunctionAmount(expReportPmtSchedule.getPaymentCurrencyCode(),
                        expReportHeader.getFunctionalCurrencyCode(), expReportHeader.getAccEntityId(),
                        periodName, journalDate, expReportPmtSchedule.getDueAmount(), expReportAccount));
                expReportAccount.setAccEntityId(expReportHeader.getAccEntityId());
                expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                expReportAccount.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
                expReportAccount.setDescription(expReportPmtSchedule.getDescription());
                expReportAccount.setCurrencyCode(expReportPmtSchedule.getPaymentCurrencyCode());
                expReportAccount.setJournalDate(journalDate);
                expReportAccount.setPeriodName(periodName);
                expReportAccount.setUsageCode("EMPLOYEE_EXPENSE_CLEARING_TAX");
                expReportAccount.setSourceCode("EXPENSE_REPORT_AUDIT");
                expReportAccount.setGldInterfaceFlag("N");
                expReportAccount.setCreatedBy(userId);
                expReportAccount.setCreationDate(new Date());
                expReportAccount.setLastUpdatedBy(userId);
                expReportAccount.setLastUpdateDate(new Date());
                expReportAccounts.add(expReportAccount);
            }
        }
        return "";
    }

    /**
     * 创建发票认证凭证
     *
     * @param expReportHeader        报销单头数据
     * @param expReportDistsCertTaxs 发票认证数据
     * @param expReportAccounts      凭证数据
     * @param userId                 创建人
     * @return String
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/22 14:36
     * @Version 1.0
     **/
    public String createInvoiceGenerateCertAccount(ExpReportHeader expReportHeader,
                                                   List<ExpReportDist> expReportDistsCertTaxs, List<ExpReportAccount> expReportAccounts, Long userId) {
        for (ExpReportDist expReportDistsCertTax : expReportDistsCertTaxs) {
            List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
            Long accountId = 0L;
            if ("Y".equals(expReportDistsCertTax.getAuthenticationStatus())) {
                // 判断发票是否需要认证，若无需认证则不生成认证凭证
                if ("N".equals(expReportDistsCertTax.getAuthenticationFlag())) {
                    continue;
                }
                // 获取凭证期间
                String periodName = "";
                periodName = gldPeriodMapper.getPeriodName(new Date(), expReportDistsCertTax.getAccEntityId(), "O");
                if (periodName.isEmpty()) {
                    throw new RuntimeException("");
                }
                // 发票入账方式
                String accountMethod = "";
                accountMethod = sysParameterService.queryParamValueByCode("VAT_INVOICE_ACCOUNT_METHOD", null, null,
                        expReportDistsCertTax.getCompanyId(), expReportDistsCertTax.getAccEntityId(),
                        expReportDistsCertTax.getSetOfBooksId(), null, null);
                String inputTaxAccountMethod = "";
                inputTaxAccountMethod = sysParameterService.queryParamValueByCode("VAT_INPUT_TAX_ACCOUNT_METHOD", null,
                        null, expReportDistsCertTax.getCompanyId(), expReportDistsCertTax.getAccEntityId(),
                        expReportDistsCertTax.getSetOfBooksId(), null, null);
                // 若进项税入账方式为直接进进项税，认证通过时不生成认证凭证
                if ("I".equals(inputTaxAccountMethod) || inputTaxAccountMethod.isEmpty()) {
                    // 生成贷方凭证 【进项税/进项税抵扣-EMPLOYEE_EXPENSE_TAX】
                    ExpReportAccount expReportCrAccount = new ExpReportAccount();
                    /*
                     * 未创建过费用凭证【Tips：此处逻辑为先认证后审核（即：没有费用凭证前）。
                     * 由于逻辑调整，此业务场景已变更为审核时候费用/认证凭证一起出具，因此产品中已不存在此业务场景，但保留逻辑便于后续项目存在此情况】 Note：进项税待抵扣（先进待抵扣进项税）
                     * 报销额入抵扣，单独转出 两行进项税待抵扣凭证 进项税待抵扣 1：报销税额 进项税待抵扣 2：发票税额 - 报销税额 报销额入抵扣，不单独转出 / 全税额入抵扣 进项税待抵扣 1：发票税额
                     */
                    gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExpTax(
                            expReportDistsCertTax.getCompanyId() == null ? null : expReportDistsCertTax.getCompanyId().toString(),
                            expReportDistsCertTax.getPositionId() == null ? null : expReportDistsCertTax.getPositionId().toString(),
                            expReportDistsCertTax.getAccEntityId() == null ? null : expReportDistsCertTax.getAccEntityId().toString(),
                            expReportDistsCertTax.getRespCenterId() == null ? null : expReportDistsCertTax.getRespCenterId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistsCertTax.getTaxTypeId() == null ? null : expReportDistsCertTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE_TAX",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                    } else {
                        expReportCrAccount.setAccountId(accountId);
                    }
                    if (expReportDistsCertTax.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportCrAccount.setRespCenterId(expReportDistsCertTax.getRespCenterId());
                    }
                    // 生成进项税 1 或 未配置参数的凭证
                    if ("S".equals(accountMethod)) {
                        expReportCrAccount.setEnteredAmountCr(expReportDistsCertTax.getPaymentRpTaxAmount());
                        expReportCrAccount.setFunctionalAmountCr(expReportDistsCertTax.getFunctionalRpTaxAmount());
                    } else {
                        expReportCrAccount.setEnteredAmountCr(expReportDistsCertTax.getPaymentSpTaxAmount());
                        expReportCrAccount.setFunctionalAmountCr(expReportDistsCertTax.getFunctionalSpTaxAmount());
                    }
                    expReportCrAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportCrAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportCrAccount.setExpReportDistId(expReportDistsCertTax.getExpReportDistId());
                    expReportCrAccount.setDescription(expReportDistsCertTax.getDescription());
                    expReportCrAccount.setCurrencyCode(expReportDistsCertTax.getPaymentCurrencyCode());
                    expReportCrAccount.setExchangeRate(expReportDistsCertTax.getPay2funExchangeRate());
                    expReportCrAccount.setExchangeRateType(expReportDistsCertTax.getPay2funExchangeType());
                    expReportCrAccount.setJournalDate(new Date());
                    expReportCrAccount.setPeriodName(periodName);
                    expReportCrAccount.setUsageCode("EMPLOYEE_EXPENSE_TAX");
                    expReportCrAccount.setSourceCode("INVOICE_CERTIFICATION");
                    expReportCrAccount.setGldInterfaceFlag("N");
                    expReportCrAccount.setCreatedBy(userId);
                    expReportCrAccount.setCreationDate(new Date());
                    expReportCrAccount.setLastUpdatedBy(userId);
                    expReportCrAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportCrAccount);
                    // 生成进项税 2 的凭证
                    if ("S".equals(accountMethod)) {
                        ExpReportAccount expReportCrAccount1 = new ExpReportAccount();
                        BeanUtils.copyProperties(expReportCrAccount,expReportCrAccount1);
                        expReportCrAccount1.setEnteredAmountCr(expReportDistsCertTax.getPaymentSpTaxAmount()
                                .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                        expReportCrAccount1.setFunctionalAmountCr(expReportDistsCertTax.getFunctionalSpTaxAmount()
                                .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                        if ((new BigDecimal(0)).compareTo(expReportCrAccount1.getEnteredAmountCr()) < 0
                                && (new BigDecimal(0))
                                .compareTo(expReportCrAccount1.getFunctionalAmountCr()) < 0) {
                            expReportAccounts.add(expReportCrAccount1);
                        }
                    }
                    // 生成借方凭证 【增值税认证税务处理-EXPENSE_ADDVALUE_DEDUCTED】
                    ExpReportAccount expReportDrAccount = new ExpReportAccount();
                    /*
                     * 生成借方凭证 Note：进项税（先进待抵扣进项税） 报销额入抵扣，单独转出 两行进项税凭证 进项税 1：报销税额 进项税 2：发票税额 - 报销税额 报销额入抵扣，不单独转出 / 全税额入抵扣
                     * 进项税 1：发票税额
                     */
                    gldMappingConditions = gldMappingConditionService.accBuilderInvoiceTax(
                            expReportDistsCertTax.getCompanyId() == null ? null : expReportDistsCertTax.getCompanyId().toString(),
                            expReportDistsCertTax.getPositionId() == null ? null : expReportDistsCertTax.getPositionId().toString(),
                            expReportDistsCertTax.getRespCenterId() == null ? null : expReportDistsCertTax.getRespCenterId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistsCertTax.getTaxTypeId() == null ? null : expReportDistsCertTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EXPENSE_ADDVALUE_DEDUCTED",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.VAT1028_GET_VAT_INVOICE_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.VAT1028_GET_VAT_INVOICE_ACC_ERROR;
                    } else {
                        expReportDrAccount.setAccountId(accountId);
                    }
                    if (expReportHeader.getRespCenterId() == null) {
                        return ExpReportAccountException.EXP5140_RESP_CENTER_ERROR;
                    } else {
                        expReportDrAccount.setRespCenterId(expReportHeader.getRespCenterId());
                    }
                    // 生成进项税 1 或 未配置参数的凭证
                    if ("S".equals(accountMethod)) {
                        expReportDrAccount.setEnteredAmountDr(expReportDistsCertTax.getPaymentRpTaxAmount());
                        expReportDrAccount.setFunctionalAmountDr(expReportDistsCertTax.getFunctionalRpTaxAmount());
                    } else {
                        expReportDrAccount.setEnteredAmountDr(expReportDistsCertTax.getPaymentSpTaxAmount());
                        expReportDrAccount.setFunctionalAmountDr(expReportDistsCertTax.getFunctionalSpTaxAmount());
                    }
                    expReportDrAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportDrAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportDrAccount.setExpReportDistId(expReportDistsCertTax.getExpReportDistId());
                    expReportDrAccount.setDescription(expReportDistsCertTax.getDescription());
                    expReportDrAccount.setCurrencyCode(expReportDistsCertTax.getPaymentCurrencyCode());
                    expReportDrAccount.setExchangeRate(expReportDistsCertTax.getPay2funExchangeRate());
                    expReportDrAccount.setExchangeRateType(expReportDistsCertTax.getPay2funExchangeType());
                    expReportDrAccount.setJournalDate(new Date());
                    expReportDrAccount.setPeriodName(periodName);
                    expReportDrAccount.setUsageCode("EXPENSE_ADDVALUE_DEDUCTED");
                    expReportDrAccount.setSourceCode("INVOICE_CERTIFICATION");
                    expReportDrAccount.setGldInterfaceFlag("N");
                    expReportDrAccount.setCreatedBy(userId);
                    expReportDrAccount.setCreationDate(new Date());
                    expReportDrAccount.setLastUpdatedBy(userId);
                    expReportDrAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportDrAccount);
                    // 生成进项税 2 的凭证
                    if ("S".equals(accountMethod)) {
                        ExpReportAccount expReportDrAccount1 = new ExpReportAccount();
                        BeanUtils.copyProperties(expReportDrAccount,expReportDrAccount1);
                        expReportDrAccount1.setEnteredAmountDr(expReportDistsCertTax.getPaymentSpTaxAmount()
                                .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                        expReportDrAccount1.setFunctionalAmountDr(expReportDistsCertTax.getFunctionalSpTaxAmount()
                                .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                        if ((new BigDecimal(0)).compareTo(expReportDrAccount1.getEnteredAmountDr()) < 0
                                && (new BigDecimal(0))
                                .compareTo(expReportDrAccount1.getFunctionalAmountDr()) < 0) {
                            expReportAccounts.add(expReportDrAccount1);
                        }
                    }

                }
            } else if ("F".equals(expReportDistsCertTax.getAuthenticationStatus())) {
                // 增值税专票认证失败，生成认证失败的凭证信息
                // 获取凭证期间
                String periodName = "";
                periodName = gldPeriodMapper.getPeriodName(new Date(), expReportDistsCertTax.getAccEntityId(), "O");
                if (periodName.isEmpty()) {
                    throw new RuntimeException("");
                }
                // 发票入账方式
                String accountMethod = "";
                accountMethod = sysParameterService.queryParamValueByCode("VAT_INVOICE_ACCOUNT_METHOD", null, null,
                        expReportDistsCertTax.getCompanyId(), expReportDistsCertTax.getAccEntityId(),
                        expReportDistsCertTax.getSetOfBooksId(), null, null);
                // 进项税入账方式【Tips："先进待抵扣进项税" 时入增值税认证税务处理，"直接进进项税" 时入报销单费用】
                String inputTaxAccountMethod = "";
                inputTaxAccountMethod = sysParameterService.queryParamValueByCode("VAT_INPUT_TAX_ACCOUNT_METHOD", null,
                        null, expReportDistsCertTax.getCompanyId(), expReportDistsCertTax.getAccEntityId(),
                        expReportDistsCertTax.getSetOfBooksId(), null, null);
                // 生成贷方凭证 【报销单费用/增值税认证税务处理-EMPLOYEE_EXPENSE/EXPENSE_ADDVALUE_DEDUCTED】
                ExpReportAccount expReportCrAccount = new ExpReportAccount();
                if ("I".equals(inputTaxAccountMethod) || inputTaxAccountMethod.isEmpty()) {
                    gldMappingConditions = gldMappingConditionService.accBuilderInvoiceTax(
                            expReportDistsCertTax.getCompanyId() == null ? null : expReportDistsCertTax.getCompanyId().toString(),
                            expReportDistsCertTax.getPositionId() == null ? null : expReportDistsCertTax.getPositionId().toString(),
                            expReportDistsCertTax.getRespCenterId() == null ? null : expReportDistsCertTax.getRespCenterId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistsCertTax.getTaxTypeId() == null ? null : expReportDistsCertTax.getTaxTypeId().toString());
                    // 通过匹配组获取借方科目
                    accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EXPENSE_ADDVALUE_DEDUCTED",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.VAT1028_GET_VAT_INVOICE_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.VAT1028_GET_VAT_INVOICE_ACC_ERROR;
                    } else {
                        expReportCrAccount.setAccountId(accountId);
                    }
                    expReportCrAccount.setUsageCode("EXPENSE_ADDVALUE_DEDUCTED");
                } else {
                    String periodComparison = "";
                    Long distPeriodNum = gldPeriodMapper.getInternalPeriodNum(expReportDistsCertTax.getAccEntityId(),
                            expReportDistsCertTax.getPeriodName());
                    Long operationPeriodNum =
                            gldPeriodMapper.getInternalPeriodNum(expReportHeader.getAccEntityId(), periodName);
                    // 分摊凭证创建的期间比较依据
                    if (distPeriodNum.longValue() > operationPeriodNum.longValue()) {
                        periodComparison = "EARLIER";
                    } else if (distPeriodNum.longValue() == operationPeriodNum.longValue()) {
                        periodComparison = "IN";
                    } else if (distPeriodNum.longValue() < operationPeriodNum.longValue()) {
                        periodComparison = "LATER";
                    }
                    // 设置预置匹配组预置值
                    gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExp(
                            expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                            expReportDistsCertTax.getCompanyId() == null ? null : expReportDistsCertTax.getCompanyId().toString(),
                            expReportDistsCertTax.getPositionId() == null ? null : expReportDistsCertTax.getPositionId().toString(),
                            expReportDistsCertTax.getAccEntityId() == null ? null : expReportDistsCertTax.getAccEntityId().toString(),
                            expReportDistsCertTax.getRespCenterId() == null ? null : expReportDistsCertTax.getRespCenterId().toString(),
                            expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportDistsCertTax.getPaymentCurrencyCode(),
                            expReportDistsCertTax.getMoExpenseItemId() == null ? null : expReportDistsCertTax.getMoExpenseItemId().toString(),
                            expReportDistsCertTax.getMoExpenseTypeId() == null ? null : expReportDistsCertTax.getMoExpenseTypeId().toString(),
                            periodComparison,
                            expReportHeader.getEmployeeTypeId() == null ? null : expReportHeader.getEmployeeTypeId().toString(),
                            expReportDistsCertTax.getUnitId() == null ? null : expReportDistsCertTax.getUnitId().toString(),
                            expReportDistsCertTax.getUnitTypeId() == null ? null : expReportDistsCertTax.getUnitTypeId().toString());
                    // 通过匹配组获取借方科目
                    accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE",
                            expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                    if(accountId == null){
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR;
                    }
                    if (accountId.longValue() == 0) {
                        return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR;
                    } else {
                        expReportCrAccount.setAccountId(accountId);
                    }
                    expReportCrAccount.setUsageCode("EMPLOYEE_EXPENSE");
                }
                // 获取凭证金额
                if ("S".equals(accountMethod)) {
                    expReportCrAccount.setEnteredAmountCr(expReportDistsCertTax.getPaymentSpTaxAmount()
                            .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                    expReportCrAccount.setFunctionalAmountCr(expReportDistsCertTax.getFunctionalSpTaxAmount()
                            .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                } else {
                    expReportCrAccount.setEnteredAmountCr(expReportDistsCertTax.getPaymentSpTaxAmount());
                    expReportCrAccount.setFunctionalAmountCr(expReportDistsCertTax.getFunctionalSpTaxAmount());
                }
                if ((new BigDecimal(0)).compareTo(expReportCrAccount.getEnteredAmountCr()) < 0
                        && (new BigDecimal(0)).compareTo(expReportCrAccount.getFunctionalAmountCr()) < 0) {
                    expReportCrAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportCrAccount.setRespCenterId(expReportDistsCertTax.getRespCenterId());
                    expReportCrAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportCrAccount.setExpReportDistId(expReportDistsCertTax.getExpReportDistId());
                    expReportCrAccount.setDescription(expReportDistsCertTax.getDescription());
                    expReportCrAccount.setCurrencyCode(expReportDistsCertTax.getPaymentCurrencyCode());
                    expReportCrAccount.setExchangeRate(expReportDistsCertTax.getPay2funExchangeRate());
                    expReportCrAccount.setExchangeRateType(expReportDistsCertTax.getPay2funExchangeType());
                    expReportCrAccount.setJournalDate(new Date());
                    expReportCrAccount.setPeriodName(periodName);
                    expReportCrAccount.setSourceCode("INVOICE_CERTIFICATION");
                    expReportCrAccount.setGldInterfaceFlag("N");
                    expReportCrAccount.setCreatedBy(userId);
                    expReportCrAccount.setCreationDate(new Date());
                    expReportCrAccount.setLastUpdatedBy(userId);
                    expReportCrAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportCrAccount);
                }
                // 生成借方凭证 【进项税/进项税抵扣-EMPLOYEE_EXPENSE_TAX】
                ExpReportAccount expReportDrAccount = new ExpReportAccount();
                gldMappingConditions = gldMappingConditionService.accBuilderEmployeeExpTax(
                        expReportDistsCertTax.getCompanyId() == null ? null : expReportDistsCertTax.getCompanyId().toString(),
                        expReportDistsCertTax.getPositionId() == null ? null : expReportDistsCertTax.getPositionId().toString(),
                        expReportDistsCertTax.getAccEntityId() == null ? null : expReportDistsCertTax.getAccEntityId().toString(),
                        expReportDistsCertTax.getRespCenterId() == null ? null : expReportDistsCertTax.getRespCenterId().toString(),
                        expReportHeader.getMoExpReportTypeId() == null ? null : expReportHeader.getMoExpReportTypeId().toString(),
                        expReportDistsCertTax.getTaxTypeId() == null ? null : expReportDistsCertTax.getTaxTypeId().toString());
                // 通过匹配组获取借方科目
                accountId = gldMappingConditionService.getAccount(gldMappingConditions, "EMPLOYEE_EXPENSE_TAX",
                        expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                if(accountId == null){
                    return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                }
                if (accountId.longValue() == 0) {
                    return ExpReportAccountException.EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR;
                } else {
                    expReportDrAccount.setAccountId(accountId);
                }
                // 获取凭证金额
                if ("S".equals(accountMethod)) {
                    expReportDrAccount.setEnteredAmountDr(expReportDistsCertTax.getPaymentSpTaxAmount()
                            .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                    expReportDrAccount.setFunctionalAmountDr(expReportDistsCertTax.getFunctionalSpTaxAmount()
                            .subtract(expReportDistsCertTax.getPaymentRpTaxAmount() == null ? BigDecimal.ZERO : expReportDistsCertTax.getPaymentRpTaxAmount()));
                } else {
                    expReportDrAccount.setEnteredAmountDr(expReportDistsCertTax.getPaymentSpTaxAmount());
                    expReportDrAccount.setFunctionalAmountDr(expReportDistsCertTax.getFunctionalSpTaxAmount());
                }
                if ((new BigDecimal(0)).compareTo(expReportDrAccount.getEnteredAmountDr()) < 0
                        && (new BigDecimal(0)).compareTo(expReportDrAccount.getFunctionalAmountDr()) < 0) {
                    expReportDrAccount.setAccEntityId(expReportHeader.getAccEntityId());
                    expReportDrAccount.setRespCenterId(expReportDistsCertTax.getRespCenterId());
                    expReportDrAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                    expReportDrAccount.setExpReportDistId(expReportDistsCertTax.getExpReportDistId());
                    expReportDrAccount.setDescription(expReportDistsCertTax.getDescription());
                    expReportDrAccount.setCurrencyCode(expReportDistsCertTax.getPaymentCurrencyCode());
                    expReportDrAccount.setExchangeRate(expReportDistsCertTax.getPay2funExchangeRate());
                    expReportDrAccount.setExchangeRateType(expReportDistsCertTax.getPay2funExchangeType());
                    expReportDrAccount.setJournalDate(new Date());
                    expReportDrAccount.setPeriodName(periodName);
                    expReportDrAccount.setUsageCode("EMPLOYEE_EXPENSE_TAX");
                    expReportDrAccount.setSourceCode("INVOICE_CERTIFICATION");
                    expReportDrAccount.setGldInterfaceFlag("N");
                    expReportDrAccount.setCreatedBy(userId);
                    expReportDrAccount.setCreationDate(new Date());
                    expReportDrAccount.setLastUpdatedBy(userId);
                    expReportDrAccount.setLastUpdateDate(new Date());
                    expReportAccounts.add(expReportDrAccount);
                }
            }
        }
        return "";
    }

    @Override
    public void createExpReportAccounts(IRequest iRequest, Long expReportHeaderId, Date journalDate, String periodName,
                                        String amortizationFlag) {
        //删除所有凭证数据
        ExpReportAccount account = new ExpReportAccount();
        account.setExpReportHeaderId(expReportHeaderId);
        List<ExpReportAccount> accounts = self().select(iRequest, account, 0, 0);
        if (accounts != null) {
            if (!accounts.isEmpty()) {
                self().batchDelete(accounts);
            }
        }
        // 获取错误代码
        String errorCode = "";
        // 获取报销单分配行数据生成凭证借方
        List<ExpReportDist> expReportDists = expReportDistMapper.getExpReportDists(expReportHeaderId);
        // 获取报销单计划付款行数据生成凭证贷方
        List<ExpReportPmtSchedule> expReportPmtSchedules =
                expReportPmtScheduleMapper.getExpReportPmtSchedules(expReportHeaderId);
        // 获取计划付款行税务信息
        List<ExpReportPmtSchedule> expReportPmtSchedulesTax =
                expReportPmtScheduleMapper.getExpReportPmtSchedulesTax(expReportHeaderId);
        // 获取税务信息
        List<ExpReportDist> expReportDistsTax = expReportDistMapper.getExpReportDistsTax(expReportHeaderId);
        // 获取认证发票信息
        List<ExpReportDist> expReportDistsCertTax = expReportDistMapper.getExpReportDistsCertTax(expReportHeaderId);
        // 获取报销单头信息
        ExpReportHeader expReportHeader = expReportHeaderMapper.getExpReportHeader(expReportHeaderId);
        // 生成的凭证行的LIST
        List<ExpReportAccount> expReportAccounts = new ArrayList<>();
        // 需要更新的核销数据
        List<CshWriteOffAccount> cshWriteOffAccounts = new ArrayList<>();
        // 需要更新的总账分录表数据
        // List<GlAccountEntry>
//        try {
            // 创建借方凭证
            errorCode = createExpReportDrAccount(expReportHeader, expReportDists, periodName, journalDate,
                    expReportAccounts, iRequest.getUserId(), amortizationFlag);
            if (!errorCode.isEmpty()) {
                throw new ExpReportAccountException(errorCode, errorCode, null);
            }
            // 创建贷方凭证
            errorCode = createExpReportCrAccount(expReportHeader, expReportPmtSchedules, periodName, journalDate,
                    expReportAccounts, iRequest.getUserId());
            if (!errorCode.isEmpty()) {
                throw new ExpReportAccountException(errorCode, errorCode, null);
            }
            // 创建贷方税金凭证
            errorCode = createExpReportCrTaxAccount(expReportHeader, expReportPmtSchedulesTax, periodName, journalDate,
                    expReportAccounts, iRequest.getUserId());
            if (!errorCode.isEmpty()) {
                throw new ExpReportAccountException(errorCode, errorCode, null);
            }
            // 创建借方税金凭证
            errorCode = createExpReportDrTaxAccount(expReportHeader, expReportDistsTax, periodName, journalDate,
                    expReportAccounts, iRequest.getUserId());
            if (!errorCode.isEmpty()) {
                throw new ExpReportAccountException(errorCode, errorCode, null);
            }
            // 创建发票认证凭证
            errorCode = createInvoiceGenerateCertAccount(expReportHeader, expReportDistsCertTax, expReportAccounts,
                    iRequest.getUserId());
            if (!errorCode.isEmpty()) {
                throw new ExpReportAccountException(errorCode, errorCode, null);
            }
            // 插入凭证表
            for (ExpReportAccount expReportAccount : expReportAccounts) {
                // 获取行Id
                Long expLineId = 0L;
                expReportAccount = service.insertSelective(iRequest, expReportAccount);
                String drCrFlag = "";
                if (expReportAccount.getEnteredAmountDr() == null) {
                    drCrFlag = "CR";
                } else if (expReportAccount.getEnteredAmountCr() == null) {
                    drCrFlag = "DR";
                }
                // 匹配段值
                GldSegment gldSegment = new GldSegment();
                gldSegment = gldSobSegmentService.mappingSegmentValue(expReportAccount.getAccEntityId(),
                        expReportHeader.getSetOfBooksId(), expReportAccount.getAccountId(), "EXP_REPORT",
                        expReportAccount.getExpReportJeLineId(), drCrFlag, expReportHeaderId, expLineId,
                        expReportAccount.getExpReportDistId(), expReportAccount.getPaymentScheduleLineId());
                if (gldSegment == null) {
                    throw new RuntimeException("未获取到对应科目段!");
                }
                expReportAccount.setAccountSegment1(gldSegment.getSegment1());
                expReportAccount.setAccountSegment2(gldSegment.getSegment2());
                expReportAccount.setAccountSegment3(gldSegment.getSegment3());
                expReportAccount.setAccountSegment4(gldSegment.getSegment4());
                expReportAccount.setAccountSegment5(gldSegment.getSegment5());
                expReportAccount.setAccountSegment6(gldSegment.getSegment6());
                expReportAccount.setAccountSegment7(gldSegment.getSegment7());
                expReportAccount.setAccountSegment8(gldSegment.getSegment8());
                expReportAccount.setAccountSegment9(gldSegment.getSegment9());
                expReportAccount.setAccountSegment10(gldSegment.getSegment10());
                expReportAccount.setAccountSegment11(gldSegment.getSegment11());
                expReportAccount.setAccountSegment12(gldSegment.getSegment12());
                expReportAccount.setAccountSegment13(gldSegment.getSegment13());
                expReportAccount.setAccountSegment14(gldSegment.getSegment14());
                expReportAccount.setAccountSegment15(gldSegment.getSegment15());
                expReportAccount.setAccountSegment16(gldSegment.getSegment16());
                expReportAccount.setAccountSegment17(gldSegment.getSegment17());
                expReportAccount.setAccountSegment18(gldSegment.getSegment18());
                expReportAccount.setAccountSegment19(gldSegment.getSegment19());
                expReportAccount.setAccountSegment20(gldSegment.getSegment20());
                service.updateByPrimaryKeySelective(iRequest, expReportAccount);
                // 如果是清算科目则更新核销行凭证信息
                if ("EMPLOYEE_EXPENSE_CLEARING".equals(expReportAccount.getSourceCode())) {
                    // 查询出该计划付款行对应的核销数据
                    List<CshWriteOffAccount> cshWriteOffPmtAccounts = new ArrayList<>();
                    cshWriteOffPmtAccounts = cshWriteOffAccountMapper
                            .getWriteOffAccounts(expReportAccount.getPaymentScheduleLineId());
                    for (CshWriteOffAccount cshWriteOffAccount : cshWriteOffPmtAccounts) {
                        BeanUtils.copyProperties(expReportAccount, cshWriteOffAccount);
                        cshWriteOffAccounts.add(cshWriteOffAccount);
                    }
                }
            }
            // 批量更新对应的核销凭证数据
            cshWriteOffAccountService.batchUpdate(iRequest, cshWriteOffAccounts);
            // 更新单据头凭证创建状态
            expReportHeader.setJeCreationStatus("SUCCESS");
            expReportHeaderService.updateByPrimaryKeySelective(iRequest, expReportHeader);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }

    }

    @Override
    public List<ExpReportAccount> accountUpdate(IRequest iRequest, List<ExpReportAccount> expReportAccounts) {
        if (!expReportAccounts.isEmpty()) {
            for (ExpReportAccount expReportAccount : expReportAccounts) {
                ExpReportAccount expReportAccount1 = new ExpReportAccount();
                String drCrFlag = "";
                expReportAccount1.setExpReportJeLineId(expReportAccount.getExpReportJeLineId());
                expReportAccount1 = service.selectByPrimaryKey(iRequest, expReportAccount1);
                if (expReportAccount.getAccountId().longValue() != expReportAccount1.getAccountId().longValue()) {
                    if (expReportAccount.getEnteredAmountDr() == null) {
                        drCrFlag = "CR";
                    } else if (expReportAccount.getEnteredAmountCr() == null) {
                        drCrFlag = "DR";
                    }
                    Long setOfBooksId = expReportAccountMapper.selectSetOfBooksId(expReportAccount.getAccEntityId());
                    // 匹配段值
                    GldSegment gldSegment = new GldSegment();
                    gldSegment = gldSobSegmentService.mappingSegmentValue(expReportAccount.getAccEntityId(),
                            setOfBooksId, expReportAccount.getAccountId(), "EXP_REPORT",
                            expReportAccount.getExpReportJeLineId(), drCrFlag,
                            expReportAccount.getExpReportHeaderId(), expReportAccount.getExpLineId(),
                            expReportAccount.getExpReportDistId(), expReportAccount.getPaymentScheduleLineId());
                    if (gldSegment == null) {
                        throw new RuntimeException("未获取到对应科目段!");
                    }
                    expReportAccount.setAccountSegment1(gldSegment.getSegment1());
                    expReportAccount.setAccountSegment2(gldSegment.getSegment2());
                    expReportAccount.setAccountSegment3(gldSegment.getSegment3());
                    expReportAccount.setAccountSegment4(gldSegment.getSegment4());
                    expReportAccount.setAccountSegment5(gldSegment.getSegment5());
                    expReportAccount.setAccountSegment6(gldSegment.getSegment6());
                    expReportAccount.setAccountSegment7(gldSegment.getSegment7());
                    expReportAccount.setAccountSegment8(gldSegment.getSegment8());
                    expReportAccount.setAccountSegment9(gldSegment.getSegment9());
                    expReportAccount.setAccountSegment10(gldSegment.getSegment10());
                    expReportAccount.setAccountSegment11(gldSegment.getSegment11());
                    expReportAccount.setAccountSegment12(gldSegment.getSegment12());
                    expReportAccount.setAccountSegment13(gldSegment.getSegment13());
                    expReportAccount.setAccountSegment14(gldSegment.getSegment14());
                    expReportAccount.setAccountSegment15(gldSegment.getSegment15());
                    expReportAccount.setAccountSegment16(gldSegment.getSegment16());
                    expReportAccount.setAccountSegment17(gldSegment.getSegment17());
                    expReportAccount.setAccountSegment18(gldSegment.getSegment18());
                    expReportAccount.setAccountSegment19(gldSegment.getSegment19());
                    expReportAccount.setAccountSegment20(gldSegment.getSegment20());
                }
                service.updateByPrimaryKeySelective(iRequest, expReportAccount);
                // 如果是清算科目则更新核销行凭证信息
                if ("EMPLOYEE_EXPENSE_CLEARING".equals(expReportAccount.getSourceCode())) {
                    // 查询出该计划付款行对应的核销数据
                    List<CshWriteOffAccount> cshWriteOffPmtAccounts = new ArrayList<>();
                    List<CshWriteOffAccount> cshWriteOffAccounts = new ArrayList<>();
                    cshWriteOffPmtAccounts = cshWriteOffAccountMapper
                            .getWriteOffAccounts(expReportAccount.getPaymentScheduleLineId());
                    for (CshWriteOffAccount cshWriteOffAccount : cshWriteOffPmtAccounts) {
                        BeanUtils.copyProperties(expReportAccount, cshWriteOffAccount);
                        cshWriteOffAccounts.add(cshWriteOffAccount);
                    }
                    cshWriteOffAccountService.batchUpdate(iRequest, cshWriteOffAccounts);
                }
            }
        }
        return expReportAccounts;
    }

    @Override
    public void updateAccountExchangeRate(IRequest iRequest, Long expReportHeaderId, BigDecimal exchangeRate, String functionCurrencyCode) {
        List<ExpReportAccount> expReportAccounts = new ArrayList<>();
        expReportAccounts = expReportAccountMapper.getExpReportAccountInfo(expReportHeaderId);
        if (!expReportAccounts.isEmpty()) {
            for (ExpReportAccount expReportAccount : expReportAccounts) {
                BigDecimal functionAmount = new BigDecimal(0);
                expReportAccount.setExchangeRate(exchangeRate);
                int precision = gldCurrencyMapper.getPrecision(functionCurrencyCode);
                if (expReportAccount.getEnteredAmountDr() != null) {
                    functionAmount = functionAmount
                            .add(expReportAccount.getEnteredAmountDr().multiply(exchangeRate).setScale(precision, BigDecimal.ROUND_HALF_UP));
                    expReportAccount.setFunctionalAmountDr(functionAmount);
                } else if (expReportAccount.getEnteredAmountCr() != null) {
                    functionAmount = functionAmount
                            .add(expReportAccount.getEnteredAmountCr().multiply(exchangeRate).setScale(precision, BigDecimal.ROUND_HALF_UP));
                    expReportAccount.setFunctionalAmountCr(functionAmount);
                }
                service.updateByPrimaryKeySelective(iRequest, expReportAccount);
            }
        }
    }

    @Override
    public List<ExpReportAccount> auditAccountQuery(IRequest requestContext, ExpReportAccount dto, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return expReportAccountMapper.auditAccountQuery(dto);
    }
}
