package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.service.IAcpRequisitionHdService;
import com.hand.hec.csh.exception.CshDocPayAccEntityException;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.service.IExpReportHeaderService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionDtl;
import com.hand.hec.acp.service.IAcpRequisitionDtlService;
import com.hand.hec.acp.service.IAcpRequisitionRefService;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.mapper.*;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.ExpEmployeeAccount;
import com.hand.hec.exp.mapper.ExpEmployeeAccountMapper;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.dto.OrdCustomerAccount;
import com.hand.hec.fnd.mapper.OrdCustomerAccountMapper;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.service.IGlAccountEntryService;
import com.hand.hec.pur.dto.PurVenderAccount;
import com.hand.hec.pur.mapper.PurVenderAccountMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshDocPayAccEntityServiceImpl extends BaseServiceImpl<CshDocPayAccEntity>
                implements ICshDocPayAccEntityService {

    @Autowired
    private CshDocPayAccEntityMapper cshDocPayAccEntityMapper;

    @Autowired
    private ICshPaymentMethodService cshPaymentMethodService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private CshPaymentEntityRuleMapper cshPaymentEntityRuleMapper;

    @Autowired
    private ICshPaymentTrxRuleService cshPaymentTrxRuleService;

    @Autowired
    private ICshPaymentTrxRuleDtlService cshPaymentTrxRuleDtlService;

    @Autowired
    private IFndCompanyService fndCompanyService;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;

    @Autowired
    private ICshTransactionLineService cshTransactionLineService;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private ICshPaymentMessageService cshPaymentMessageService;

    @Autowired
    private CshTransactionHeaderMapper cshTransactionHeaderMapper;

    @Autowired
    private CshTransactionAccountMapper cshTransactionAccountMapper;

    @Autowired
    private ICshPaymentBatchRuleService cshPaymentBatchRuleService;

    @Autowired
    private ICshPaymentBatchTpDtlService cshPaymentBatchTpDtlService;

    @Autowired
    private CshBankAccountMapper cshBankAccountMapper;

    @Autowired
    private ICshPaymentBatchHeaderService cshPaymentBatchHeaderService;

    @Autowired
    private ICshPaymentBatchLineService cshPaymentBatchLineService;

    @Autowired
    private CshPaymentBatchTypeMapper cshPaymentBatchTypeMapper;

    @Autowired
    private ExpEmployeeAccountMapper expEmployeeAccountMapper;

    @Autowired
    private PurVenderAccountMapper purVenderAccountMapper;

    @Autowired
    private OrdCustomerAccountMapper ordCustomerAccountMapper;

    @Autowired
    private IAcpRequisitionRefService acpRequisitionRefService;

    @Autowired
    private CshMoTransactionClassMapper cshMoTransactionClassMapper;

    @Autowired
    private IAcpRequisitionDtlService acpRequisitionDtlService;

    @Autowired
    private ICshWriteOffService cshWriteOffService;

    @Autowired
    private DatabaseLockProvider databaseLockProvider;

    @Autowired
    private IGlAccountEntryService glAccountEntryService;

    @Autowired
    private IExpReportHeaderService expReportHeaderService;

    @Autowired
    private IExpDocumentHistoryService historyService;

    @Autowired
    private ICshPaymentRequisitionHdService cshPaymentRequisitionHdService;

    @Autowired
    private IAcpRequisitionHdService acpRequisitionHdService;

    /**
     * <p>
     * 单据支付锁表
     * </p>
     *
     * @param iRequest 请求
     * @param docCategory 单据类别
     * @param cshDocPayAccEntities 支付信息集合
     * @return void
     * @author Tagin
     * @date 2019-05-27 11:15
     * @version 1.0
     **/
    public void lockTable(IRequest iRequest, String docCategory, List<CshDocPayAccEntity> cshDocPayAccEntities) {
        switch (docCategory) {
            case CshDocPayAccEntity.DOC_EXP_REPORT:
                for (CshDocPayAccEntity dto : cshDocPayAccEntities) {
                    StringBuilder whereCondition = new StringBuilder();
                    String tableName = "exp_report_header erh, exp_report_pmt_schedule erps";
                    whereCondition.append(" erps.payment_schedule_line_id = ").append(dto.getDocLineId())
                                    .append(" and erps.exp_report_header_id = erh.exp_report_header_id ");
                    databaseLockProvider.lock(tableName, whereCondition.toString(), null);
                }
                break;
            case CshDocPayAccEntity.DOC_PAYMENT_REQUISITION:
                for (CshDocPayAccEntity dto : cshDocPayAccEntities) {
                    StringBuilder whereCondition = new StringBuilder();
                    String tableName = "csh_payment_requisition_hd cprh, csh_payment_requisition_ln cprl";
                    whereCondition.append(" cprl.payment_requisition_line_id = ").append(dto.getDocLineId()).append(
                                    " and cprl.payment_requisition_header_id = cprh.payment_requisition_header_id ");
                    databaseLockProvider.lock(tableName, whereCondition.toString(), null);
                }
                break;
            case CshDocPayAccEntity.DOC_ACP_REQUISITION:
                for (CshDocPayAccEntity dto : cshDocPayAccEntities) {
                    StringBuilder whereCondition = new StringBuilder();
                    String tableName = "acp_requisition_hd arh, acp_requisition_ln arl";
                    whereCondition.append(" arl.requisition_lns_id = ").append(dto.getDocLineId())
                                    .append(" and arl.requisition_hds_id = arh.requisition_hds_id ");
                    databaseLockProvider.lock(tableName, whereCondition.toString(), null);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 创建支付核算主体信息
     *
     * @Author Tagin
     * @Date 2019-03-14 20:20
     * @param iRequest 请求
     * @param docCategory 单据类别
     * @param docCompanyId 单据公司ID
     * @param docTypeId 单据类型ID
     * @param docId 单据头ID
     * @param docLineId 单据行ID Tips: 报销单计划付款行、借款单行、付款单行
     * @param defaultAccEntityId 默认核算主体
     * @param paymentMethodId 付款方式ID
     * @param payeeCategory 收款方类别
     * @param paymentFlag 支付标志
     * @param frozenFlag 冻结标志
     * @Return void
     * @Version 1.0
     **/
    @Override
    public void createPayAccEntity(IRequest iRequest, String docCategory, Long docCompanyId, Long docTypeId, Long docId,
                    Long docLineId, Long defaultAccEntityId, Long paymentMethodId, String payeeCategory,
                    String paymentFlag, String frozenFlag) {
        String paymentStatus = null;
        // 管理组织
        FndCompany fndCompany = new FndCompany();
        fndCompany.setCompanyId(docCompanyId);
        fndCompany = fndCompanyService.selectByPrimaryKey(iRequest, fndCompany);
        Long magOrgId = fndCompany.getMagOrgId();

        // 付款核算主体配置中配置的核算主体
        Long accEntityId = cshPaymentEntityRuleMapper.selectForAccEntityId(magOrgId, docCategory, docCompanyId,
                        docTypeId, paymentMethodId, payeeCategory);
        accEntityId = accEntityId == null ? defaultAccEntityId : accEntityId;
        // 是否需支付确认参数
        String pendingFlag =
                        sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DOCUMENT_PENDING_CONFIRM,
                                        null, null, null, accEntityId, null, null, null);
        // 冻结优先级最高
        if (BaseConstants.YES.equals(frozenFlag)) {
            paymentStatus = CshDocPayAccEntity.STATUS_FROZEN;
        } else if (BaseConstants.NO.equals(paymentFlag)) {
            paymentStatus = CshDocPayAccEntity.STATUS_NEVER;
        } else {
            if (BaseConstants.YES.equals(pendingFlag)) {
                paymentStatus = CshDocPayAccEntity.STATUS_PENDING;
            } else {
                paymentStatus = CshDocPayAccEntity.STATUS_ALLOWED;
            }
        }
        // 录入值
        CshDocPayAccEntity cshDocPayAccEntity = new CshDocPayAccEntity(docCategory, docCompanyId, docTypeId, docId,
                        docLineId, paymentMethodId, payeeCategory, accEntityId, paymentStatus);
        self().insertSelective(iRequest, cshDocPayAccEntity);
    }

    /**
     * 删除支付核算主体信息
     *
     * @Author Tagin
     * @Date 2019-03-14 19:01
     * @param docCategory 单据类别
     * @param docId 单据头ID
     * @param docLineId 单据行ID Tips: 报销单计划付款行、借款单行、付款单行
     * @Return void
     * @Version 1.0
     **/
    @Override
    public void deletePayAccEntity(IRequest iRequest, String docCategory, Long docId, Long docLineId) {
        cshDocPayAccEntityMapper.deletePayAccEntity(docCategory, docId, docLineId);
    }

    /**
     * 单据支付-支付数据查询
     *
     * @Author Tagin
     * @Date 2019-03-05 19:35
     * @param request 请求
     * @param accEntityId 核算主体ID
     * @param docCategory 单据类别
     * @param docNumber 单据编号
     * @param payeeCategory 收款方对象
     * @param payeeId 收款方
     * @param requisitionDateFrom 申请日期从
     * @param requisitionDateTo 申请日期到
     * @param paymentMethodId 付款方式ID
     * @param paymentUsedeId 付款用途ID
     * @param schedulePaymentDateFrom 计划付款日期从
     * @param schedulePaymentDateTo 计划付款日期到
     * @param currencyCode 币种
     * @param page 页数
     * @param pageSize 每页大小
     * @Return java.util.List<com.hand.hec.csh.dto.CshDocPayAccEntity>
     * @Version 1.0
     **/
    @Override
    public List<CshDocPayAccEntity> queryPayment(IRequest request, Long accEntityId, String docCategory,
                    String docNumber, String payeeCategory, Long payeeId, Date requisitionDateFrom,
                    Date requisitionDateTo, Long paymentMethodId, Long paymentUsedeId, Date schedulePaymentDateFrom,
                    Date schedulePaymentDateTo, String currencyCode, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return cshDocPayAccEntityMapper.queryPayment(accEntityId, docCategory, docNumber, payeeCategory, payeeId,
                        requisitionDateFrom, requisitionDateTo, paymentMethodId, paymentUsedeId,
                        schedulePaymentDateFrom, schedulePaymentDateTo, currencyCode);
    }

    /**
     * 获取现金事务动态分组后的支付集合
     *
     * @Author Tagin
     * @Date 2019-03-19 21:21
     * @param iRequest 请求
     * @param paymentList 付款集合
     * @param mergeFlag 合并标识
     * @return java.util.Map<java.lang.Object,java.util.List<com.hand.hec.csh.dto.CshDocPayAccEntity>>
     * @Version 1.0
     **/
    private Map<Object, List<CshDocPayAccEntity>> trxGroup(IRequest iRequest, List<CshDocPayAccEntity> paymentList,
                    String mergeFlag) {
        Map<Object, List<CshDocPayAccEntity>> map = new HashMap<>();
        if (BaseConstants.YES.equals(mergeFlag)) {
            // 1.0 默认合并支付 Tips：按收款方类型、收款方、银行户名、账户、单据编号、单据核算主体、单据支付币种、现金流量项进行合并
            map = paymentList.stream().collect(Collectors.groupingBy(x -> Arrays.asList(x.getPayeeCategory(),
                            x.getPayeeId(), x.getAccountName(), x.getAccountNumber(), x.getDocNumber(),
                            x.getAccEntityId(), x.getCurrencyCode(), x.getCashFlowItemId(), x.getMoCshTrxClassId())));
        } else if (BaseConstants.NO.equals(mergeFlag)) {
            // 2.0 不合并
            map.put(new Random().nextDouble(), paymentList);
        } else {
            // 3.0 按付款事务生成规则进行合并
            // 获取付款事务规则ID
            for (CshDocPayAccEntity dto : paymentList) {
                Long ruleId = cshPaymentTrxRuleService.getTrxRuleId(iRequest.getMagOrgId(), dto.getAccEntityId(),
                                dto.getDocCategory(), dto.getDocTypeCode(), dto.getPayeeCategory(), dto.getPayeeName(),
                                dto.getAccountNumber(), dto.getCurrencyCode(), dto.getPaymentMethodCode(),
                                dto.getPaymentUsedeCode());
                dto.setRuleId(ruleId);
            }
            // 未获取到付款事务生成规则
            List<CshDocPayAccEntity> unRuleList = paymentList.stream()
                            .filter(x -> (x.getRuleId() == null || x.getRuleId().toString().isEmpty()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unRuleList)) {
                map.put(new Random().nextDouble(), unRuleList);
            }
            // 按照事务生成规则进行分组
            List<CshDocPayAccEntity> ruleList = paymentList.stream()
                            .filter(x -> (x.getRuleId() != null && !x.getRuleId().toString().isEmpty()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(ruleList)) {
                Map<Object, List<CshDocPayAccEntity>> ruleMap = ruleList.stream()
                                .collect(Collectors.groupingBy((Function<CshDocPayAccEntity, List<Object>>) x -> {
                                    List<Object> objectList = new ArrayList<>();
                                    objectList.add(x.getRuleId());
                                    List<CshPaymentTrxRuleDtl> cshPaymentTrxRuleDtls =
                                                    cshPaymentTrxRuleDtlService.queryCondition(iRequest, x.getRuleId());
                                    for (CshPaymentTrxRuleDtl e : cshPaymentTrxRuleDtls) {
                                        Object object = new Object();
                                        if (CshPaymentTrxRuleDtl.GROUP_CONDITION_PAYEE_CATEGORY
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getPayeeCategory();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_PAYEE_ID
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getPayeeId();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_ACCOUNT_NAME
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getAccountName();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_ACCOUNT_NUMBER
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getAccountNumber();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_DOCUMENT_NUMBER
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getDocNumber();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_DOCUMENT_TYPE
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getDocTypeId();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_ACC_ENTITY_ID
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getAccEntityId();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_PAYMENT_METHOD_ID
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getPaymentMethodId();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_PAYMENT_USEDE_ID
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getPaymentUsedeId();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_CURRENCY_CODE
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getCurrencyCode();
                                        } else if (CshPaymentTrxRuleDtl.GROUP_CONDITION_CASH_FLOW_ITEM_ID
                                                        .equals(e.getGroupCondition())) {
                                            object = x.getCashFlowItemId();
                                        }
                                        if (Objects.nonNull(object)) {
                                            objectList.add(object);
                                        }
                                    }
                                    return objectList;
                                }));
                // 获取事务生成规则的分组条件
                for (Map.Entry<Object, List<CshDocPayAccEntity>> m : ruleMap.entrySet()) {
                    map.put(m.getKey(), m.getValue());
                }
            }
        }
        return map;
    }

    /**
     * 生成付款批
     *
     * @author Tagin
     * @date 2019-04-08 15:46
     * @param iRequest 请求
     * @param cshTransactionLines 现金事务集合
     * @return void
     * @version 1.0
     **/
    private void generatePaymentBatch(IRequest iRequest, CshDocPayment cshDocPayment,
                    List<CshTransactionLine> cshTransactionLines) {
        // 1.0 获取付款批分组集合
        Map<Object, List<CshTransactionLine>> map = cshTransactionLines.stream()
                        .collect(Collectors.groupingBy((Function<CshTransactionLine, List<Object>>) x -> {
                            List<Object> objectList = new ArrayList<>();
                            objectList.add(x.getTypeId());
                            List<CshPaymentBatchTpDtl> cshPaymentBatchTpDtls =
                                            cshPaymentBatchTpDtlService.queryCondition(iRequest, x.getTypeId());
                            for (CshPaymentBatchTpDtl e : cshPaymentBatchTpDtls) {
                                Object object = new Object();
                                if (CshPaymentBatchTpDtl.GROUP_CONDITION_DOCUMENT_CATEGORY
                                                .equals(e.getGroupCondition())) {
                                    object = x.getDocumentCategory();
                                } else if (CshPaymentBatchTpDtl.GROUP_CONDITION_DOCUMENT_TYPE
                                                .equals(e.getGroupCondition())) {
                                    object = x.getDocumentTypeId();
                                } else if (CshPaymentBatchTpDtl.GROUP_CONDITION_PAYEE_CATEGORY
                                                .equals(e.getGroupCondition())) {
                                    object = x.getPayeeCategory();
                                } else if (CshPaymentBatchTpDtl.GROUP_CONDITION_PAYEE_ID
                                                .equals(e.getGroupCondition())) {
                                    object = x.getPayeeId();
                                } else if (CshPaymentBatchTpDtl.GROUP_CONDITION_PAYMENT_METHOD_ID
                                                .equals(e.getGroupCondition())) {
                                    object = x.getPaymentMethodId();
                                } else if (CshPaymentBatchTpDtl.GROUP_CONDITION_PAYMENT_USEDE_ID
                                                .equals(e.getGroupCondition())) {
                                    object = x.getPaymentUsedeId();
                                }
                                if (Objects.nonNull(object)) {
                                    objectList.add(object);
                                }
                            }
                            return objectList;
                        }));
        // 2.0 获取
        for (Map.Entry<Object, List<CshTransactionLine>> ctxMap : map.entrySet()) {
            String status;
            // 获取付款方账户
            Map<String, Object> bankMap =
                            cshBankAccountMapper.queryAccountAndBank(ctxMap.getValue().get(0).getBankAccountId());
            if (MapUtils.isEmpty(bankMap)) {
                throw new CshTransactionException(CshTransactionException.PAYMENT_ACCOUNT_NOT_FOUND,
                                CshTransactionException.PAYMENT_ACCOUNT_NOT_FOUND, null);
            }
            // 自审核标志
            CshPaymentBatchType cshPaymentBatchType =
                            cshPaymentBatchTypeMapper.selectByPrimaryKey(ctxMap.getValue().get(0).getTypeId());
            if (cshPaymentBatchType.getAuditMethod() != null && CshPaymentBatchType.AUDIT_METHOD_AUTO_AUDIT
                            .equals(cshPaymentBatchType.getAuditMethod())) {
                status = CshPaymentBatchHeader.STATUS_AUDITED;
            } else {
                status = CshPaymentBatchHeader.STATUS_GENERATE;
            }
            // 生成付款批头
            CshPaymentBatchHeader batchHeader = cshPaymentBatchHeaderService.insertBatchHeader(iRequest,
                            cshDocPayment.getAccEntityId(), iRequest.getCompanyId(),
                            ctxMap.getValue().get(0).getTypeId(), new Date(),
                            ctxMap.getValue().get(0).getPaymentMethodId(), ctxMap.getValue().get(0).getCurrencyCode(),
                            ctxMap.getValue().get(0).getBankAccountId(),
                            String.valueOf(bankMap.get(CshBank.FIELD_BANK_CODE)),
                            String.valueOf(bankMap.get(CshBank.FIELD_BANK_NAME)), null, null, null, null, null, null,
                            String.valueOf(bankMap.get(CshBankAccount.FIELD_BANK_ACCOUNT_NUM)),
                            String.valueOf(bankMap.get(CshBankAccount.FIELD_BANK_ACCOUNT_NAME)),
                            Long.valueOf(ctxMap.getValue().size()),
                            ctxMap.getValue().stream().map(CshTransactionLine::getTransactionAmount)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add),
                            0L, BigDecimal.valueOf(0), 0L, null, status, null, BaseConstants.NO);

            // 生成付款批行
            for (CshTransactionLine dto : ctxMap.getValue()) {
                String payeeBankCode, payeeBankName, payeeBankLocationCode, payeeBankLocation, payeeProvinceCode,
                                payeeProvinceName, payeeCityCode, payeeCityName, payeeAccountNumber, payeeAccountName;
                // 获取收方账户信息
                if (CshTransactionLine.PAYEE_CATEGORY_EMPLOYEE.equals(dto.getPayeeCategory())) {
                    List<ExpEmployeeAccount> expEmployeeAccounts = expEmployeeAccountMapper
                                    .getEmployeeAccount(dto.getPayeeId(), dto.getAccountName(), dto.getAccountNumber());
                    if (CollectionUtils.isEmpty(expEmployeeAccounts)) {
                        throw new CshTransactionException(CshTransactionException.PAYEE_ACCOUNT_NOT_FOUND,
                                        CshTransactionException.PAYEE_ACCOUNT_NOT_FOUND, null);
                    } else {
                        payeeBankCode = expEmployeeAccounts.get(0).getBankCode();
                        payeeBankName = expEmployeeAccounts.get(0).getBankName();
                        payeeBankLocationCode = expEmployeeAccounts.get(0).getBankLocationCode();
                        payeeBankLocation = expEmployeeAccounts.get(0).getBankLocation();
                        payeeProvinceCode = expEmployeeAccounts.get(0).getProvinceCode();
                        payeeProvinceName = expEmployeeAccounts.get(0).getProvinceName();
                        payeeCityCode = expEmployeeAccounts.get(0).getCityCode();
                        payeeCityName = expEmployeeAccounts.get(0).getCityName();
                        payeeAccountNumber = expEmployeeAccounts.get(0).getAccountNumber();
                        payeeAccountName = expEmployeeAccounts.get(0).getAccountName();
                    }
                } else if (CshTransactionLine.PAYEE_CATEGORY_VENDER.equals(dto.getPayeeCategory())) {
                    List<PurVenderAccount> purVenderAccounts = purVenderAccountMapper.queryByVenderId(dto.getPayeeId());
                    if (CollectionUtils.isEmpty(purVenderAccounts)) {
                        throw new CshTransactionException(CshTransactionException.PAYEE_ACCOUNT_NOT_FOUND,
                                        CshTransactionException.PAYEE_ACCOUNT_NOT_FOUND, null);
                    } else {
                        payeeBankCode = purVenderAccounts.get(0).getBankCode();
                        payeeBankName = purVenderAccounts.get(0).getBankName();
                        payeeBankLocationCode = purVenderAccounts.get(0).getCnapsCode();
                        payeeBankLocation = purVenderAccounts.get(0).getCnapsName();
                        payeeProvinceCode = purVenderAccounts.get(0).getProvinceCode();
                        payeeProvinceName = purVenderAccounts.get(0).getProvinceName();
                        payeeCityCode = purVenderAccounts.get(0).getCityCode();
                        payeeCityName = purVenderAccounts.get(0).getCityName();
                        payeeAccountNumber = purVenderAccounts.get(0).getAccountNumber();
                        payeeAccountName = purVenderAccounts.get(0).getAccountName();
                    }
                } else {
                    OrdCustomerAccount ordCustomerAccount = new OrdCustomerAccount();
                    ordCustomerAccount.setCustomerId(dto.getPayeeId());
                    List<OrdCustomerAccount> ordCustomerAccounts =
                                    ordCustomerAccountMapper.queryBySysCustomer(ordCustomerAccount);
                    if (CollectionUtils.isEmpty(ordCustomerAccounts)) {
                        throw new CshTransactionException(CshTransactionException.PAYEE_ACCOUNT_NOT_FOUND,
                                        CshTransactionException.PAYEE_ACCOUNT_NOT_FOUND, null);
                    } else {
                        payeeBankCode = ordCustomerAccounts.get(0).getBankCode();
                        payeeBankName = ordCustomerAccounts.get(0).getBankName();
                        payeeBankLocationCode = ordCustomerAccounts.get(0).getCnapsCode();
                        payeeBankLocation = ordCustomerAccounts.get(0).getCnapsName();
                        payeeProvinceCode = ordCustomerAccounts.get(0).getProvinceCode();
                        payeeProvinceName = ordCustomerAccounts.get(0).getProvinceName();
                        payeeCityCode = ordCustomerAccounts.get(0).getCityCode();
                        payeeCityName = ordCustomerAccounts.get(0).getCityName();
                        payeeAccountNumber = ordCustomerAccounts.get(0).getAccountNumber();
                        payeeAccountName = ordCustomerAccounts.get(0).getAccountName();
                    }
                }
                CshPaymentBatchLine batchLine = cshPaymentBatchLineService.insertBatchLine(iRequest,
                                batchHeader.getBatchHeaderId(), dto.getAccEntityId(), dto.getCompanyId(),
                                dto.getTransactionLineId(), dto.getPayeeCategory(), dto.getPayeeId(), payeeBankCode,
                                payeeBankName, payeeBankLocationCode, payeeBankLocation, payeeProvinceCode,
                                payeeProvinceName, payeeCityCode, payeeCityName, payeeAccountNumber, payeeAccountName,
                                dto.getTransactionAmount(), CshPaymentBatchLine.PAYMENT_STATUS_UNPAID, BaseConstants.NO,
                                null, BaseConstants.NO, null);
            }
        }
    }

    /**
     * 费用报销单支付
     *
     * @Author Tagin
     * @Date 2019-03-14 14:15
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据
     * @param reportList 报销单支付集合
     * @param periodName 期间
     * @param eBankFlag 网银标志
     * @param mergeFlag 合并标志
     * @param batchFlag 付款批标志
     * @return java.util.List<com.hand.hec.csh.dto.CshTransactionLine>
     * @Version 1.0
     **/
    public List<CshTransactionLine> reportPayment(IRequest iRequest, CshDocPayment cshDocPayment,
                    List<CshDocPayAccEntity> reportList, String periodName, String eBankFlag, String mergeFlag,
                    String batchFlag) {
        List<CshTransactionLine> cshTransactionLines = new ArrayList<>();
        // 1.0 获取需合并的数据集合
        Map<Object, List<CshDocPayAccEntity>> map = this.trxGroup(iRequest, reportList, mergeFlag);

        // 2.0 处理过账
        for (Map.Entry<Object, List<CshDocPayAccEntity>> ctxMap : map.entrySet()) {
            // 2.0.1 生成现金事务头
            CshTransactionHeader cshTransactionHeader = cshTransactionHeaderService.insertTrxHeader(iRequest,
                            CshTransactionHeader.TRX_TYPE_PAYMENT, null, iRequest.getCompanyId(),
                            cshDocPayment.getAccEntityId(), iRequest.getEmployeeId(), cshDocPayment.getDescription(),
                            BaseConstants.YES, cshDocPayment.getPaymentDate(), periodName,
                            cshDocPayment.getPaymentMethodId(), CshTransactionHeader.TRX_CATEGORY_BUSINESS,
                            BaseConstants.NO, BaseConstants.NO, null, BaseConstants.NO, BaseConstants.NO, null, null,
                            BaseConstants.YES.equals(eBankFlag) ? CshTransactionHeader.INTERFACE_FLAG_H
                                            : CshTransactionHeader.INTERFACE_FLAG_P,
                            null, eBankFlag, CshDocPayAccEntity.DOC_EXP_REPORT, ctxMap.getValue().get(0).getDocTypeId(),
                            ctxMap.getValue().get(0).getPaymentUsedeId(), ctxMap.getValue().get(0).getPayeeCategory(),
                            ctxMap.getValue().get(0).getPayeeId(), ctxMap.getValue().get(0).getCurrencyCode(),
                            cshDocPayment.getBankAccountId());

            // 2.0.2 生成现金事务行
            CshTransactionLine cshTransactionLine = cshTransactionLineService.insertTrxLine(iRequest,
                            ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getExchangeRateType(),
                            cshDocPayment.getExchangeRate(), cshDocPayment.getBankAccountId(),
                            ctxMap.getValue().get(0).getDocNumber(), ctxMap.getValue().get(0).getPayeeCategory(),
                            ctxMap.getValue().get(0).getPayeeId(), null, cshDocPayment.getDescription(), null, null,
                            iRequest.getEmployeeId(), null, CshDocPayAccEntity.DOC_EXP_REPORT,
                            ctxMap.getValue().get(0).getDocTypeId(), ctxMap.getValue().get(0).getPaymentUsedeId(),
                            cshDocPayment.getPaymentMethodId(), cshTransactionHeader.getTransactionHeaderId(),
                            iRequest.getCompanyId(), cshDocPayment.getAccEntityId(),
                            ctxMap.getValue().stream().map(CshDocPayAccEntity::getPaymentAmount).reduce(BigDecimal.ZERO,
                                            BigDecimal::add));

            // 2.0.3 获取付款批类型
            if (BaseConstants.YES.equals(batchFlag)) {
                Long typeId = cshPaymentBatchRuleService.getTypeId(iRequest.getMagOrgId(),
                                cshDocPayment.getAccEntityId(), CshDocPayAccEntity.DOC_EXP_REPORT,
                                ctxMap.getValue().get(0).getDocTypeCode(), ctxMap.getValue().get(0).getPayeeCategory(),
                                ctxMap.getValue().get(0).getPayeeName(), ctxMap.getValue().get(0).getAccountNumber(),
                                ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getPaymentMethodCode(),
                                ctxMap.getValue().get(0).getPaymentUsedeCode());
                if (typeId == null || StringUtils.isEmpty(typeId.toString())) {
                    throw new CshTransactionException(CshTransactionException.PAYMENT_BATCH_NOT_FOUND,
                                    CshTransactionException.PAYMENT_BATCH_NOT_FOUND, null);
                } else {
                    cshTransactionLine.setTypeId(typeId);
                }
                cshTransactionLine.setAccountName(ctxMap.getValue().get(0).getAccountName());
                cshTransactionLine.setAccountNumber(ctxMap.getValue().get(0).getAccountNumber());
            }

            // 2.0.4 写入支付信息【银企直连】/ 写入后续核销记录
            List<CshWriteOff> cshWriteOffList = new ArrayList<>();
            for (CshDocPayAccEntity dto : ctxMap.getValue()) {
                // 支付信息
                if (BaseConstants.YES.equals(eBankFlag)) {
                    cshPaymentMessageService.createPaymentMessage(iRequest, dto.getAccEntityId(), dto.getDocCompanyId(),
                                    dto.getCurrencyCode(), dto.getPayeeCategory(), dto.getPayeeId(), new Date(),
                                    dto.getPayedAmount(), dto.getAccountName(), dto.getPaymentMethodId(),
                                    dto.getAccountName(), dto.getAccountNumber(), dto.getDocCategory(), dto.getDocId(),
                                    dto.getDocNumber(), dto.getDocLineId());
                }

                // 核销记录
                CshWriteOff cshWriteOff = new CshWriteOff();
                cshWriteOff.setWriteOffType(CshWriteOff.PAYMENT_EXPENSE_REPORT);
                cshWriteOff.setTransactionHeaderId(cshTransactionHeader.getTransactionHeaderId());
                cshWriteOff.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
                cshWriteOff.setCshWriteOffAmount(dto.getPaymentAmount());
                cshWriteOff.setDocumentSource(CshWriteOff.DOC_SOURCE_EXPENSE_REPORT);
                cshWriteOff.setDocumentHeaderId(dto.getDocId());
                cshWriteOff.setDocumentLineId(dto.getDocLineId());
                cshWriteOff.setDocumentWriteOffAmount(dto.getPaymentAmount());
                cshWriteOff.setWriteOffDate(cshDocPayment.getPaymentDate());
                cshWriteOff.setPeriodName(periodName);
                cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                cshWriteOffList.add(cshWriteOff);
            }

            // 2.0.5 调用支付逻辑
            cshTransactionHeaderService.postTransaction(iRequest, cshTransactionHeader.getTransactionHeaderId(),
                            cshDocPayment, cshWriteOffList);

            // 2.0.6 处理状态
            if (BaseConstants.YES.equals(eBankFlag)) {
                // 更新现金事务头过账标识为暂挂
                cshTransactionHeaderMapper.updateTrxHeaderFlag(CshTransactionHeader.POSTED_FLAG_H, null, null, null,
                                cshTransactionHeader.getTransactionHeaderId(), null, null);
                // 更新现金事务凭证表总账标识为暂挂
                cshTransactionAccountMapper.updateInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_H,
                                cshTransactionLine.getTransactionLineId());
            }

            // 2.0.7 回写现金事务集合
            cshTransactionLines.add(cshTransactionLine);
        }
        // 3.0 返回现金事务集合
        return cshTransactionLines;
    }

    /**
     * 借款申请单支付
     *
     * @author Tagin
     * @date 2019-03-29 11:37
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据
     * @param loanList 借款单支付集合
     * @param periodName 期间
     * @param eBankFlag 网银标志
     * @param mergeFlag 合并标志
     * @param batchFlag 付款批标志
     * @return java.util.List<com.hand.hec.csh.dto.CshTransactionLine>
     * @version 1.0
     **/
    public List<CshTransactionLine> loanPayment(IRequest iRequest, CshDocPayment cshDocPayment,
                    List<CshDocPayAccEntity> loanList, String periodName, String eBankFlag, String mergeFlag,
                    String batchFlag) {
        List<CshTransactionLine> cshTransactionLines = new ArrayList<>();
        // 1.0 获取需合并的数据集合
        Map<Object, List<CshDocPayAccEntity>> map = this.trxGroup(iRequest, loanList, mergeFlag);

        // 2.0 处理过账
        for (Map.Entry<Object, List<CshDocPayAccEntity>> ctxMap : map.entrySet()) {
            // 2.0.1 生成现金事务头
            CshTransactionHeader cshTransactionHeader = cshTransactionHeaderService.insertTrxHeader(iRequest,
                            CshTransactionHeader.TRX_TYPE_PAYMENT, ctxMap.getValue().get(0).getMoCshTrxClassId(),
                            iRequest.getCompanyId(), cshDocPayment.getAccEntityId(), iRequest.getEmployeeId(),
                            cshDocPayment.getDescription(), BaseConstants.YES, cshDocPayment.getPaymentDate(),
                            periodName, cshDocPayment.getPaymentMethodId(), CshTransactionHeader.TRX_CATEGORY_BUSINESS,
                            BaseConstants.NO, BaseConstants.NO, null, BaseConstants.NO, BaseConstants.NO, null, null,
                            BaseConstants.YES.equals(eBankFlag) ? CshTransactionHeader.INTERFACE_FLAG_H
                                            : CshTransactionHeader.INTERFACE_FLAG_P,
                            null, eBankFlag, CshDocPayAccEntity.DOC_PAYMENT_REQUISITION,
                            ctxMap.getValue().get(0).getDocTypeId(), ctxMap.getValue().get(0).getPaymentUsedeId(),
                            ctxMap.getValue().get(0).getPayeeCategory(), ctxMap.getValue().get(0).getPayeeId(),
                            ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getBankAccountId());

            // 2.0.2 生成现金事务行
            CshTransactionLine cshTransactionLine = cshTransactionLineService.insertTrxLine(iRequest,
                            ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getExchangeRateType(),
                            cshDocPayment.getExchangeRate(), cshDocPayment.getBankAccountId(),
                            ctxMap.getValue().get(0).getDocNumber(), ctxMap.getValue().get(0).getPayeeCategory(),
                            ctxMap.getValue().get(0).getPayeeId(), null, cshDocPayment.getDescription(), null, null,
                            iRequest.getEmployeeId(), null, CshDocPayAccEntity.DOC_PAYMENT_REQUISITION,
                            ctxMap.getValue().get(0).getDocTypeId(), ctxMap.getValue().get(0).getPaymentUsedeId(),
                            cshDocPayment.getPaymentMethodId(), cshTransactionHeader.getTransactionHeaderId(),
                            iRequest.getCompanyId(), cshDocPayment.getAccEntityId(),
                            ctxMap.getValue().stream().map(CshDocPayAccEntity::getPaymentAmount).reduce(BigDecimal.ZERO,
                                            BigDecimal::add));

            // 2.0.3 获取付款批类型
            if (BaseConstants.YES.equals(batchFlag)) {
                Long typeId = cshPaymentBatchRuleService.getTypeId(iRequest.getMagOrgId(),
                                cshDocPayment.getAccEntityId(), CshDocPayAccEntity.DOC_PAYMENT_REQUISITION,
                                ctxMap.getValue().get(0).getDocTypeCode(), ctxMap.getValue().get(0).getPayeeCategory(),
                                ctxMap.getValue().get(0).getPayeeName(), ctxMap.getValue().get(0).getAccountNumber(),
                                ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getPaymentMethodCode(),
                                ctxMap.getValue().get(0).getPaymentUsedeCode());
                if (typeId == null || StringUtils.isEmpty(typeId.toString())) {
                    throw new CshTransactionException(CshTransactionException.PAYMENT_BATCH_NOT_FOUND,
                                    CshTransactionException.PAYMENT_BATCH_NOT_FOUND, null);
                } else {
                    cshTransactionLine.setTypeId(typeId);
                }
                cshTransactionLine.setAccountName(ctxMap.getValue().get(0).getAccountName());
                cshTransactionLine.setAccountNumber(ctxMap.getValue().get(0).getAccountNumber());
            }

            // 2.0.4 写入支付信息【银企直连】/ 写入后续核销记录
            List<CshWriteOff> cshWriteOffList = new ArrayList<>();
            for (CshDocPayAccEntity dto : ctxMap.getValue()) {
                // 支付信息
                if (BaseConstants.YES.equals(eBankFlag)) {
                    cshPaymentMessageService.createPaymentMessage(iRequest, dto.getAccEntityId(), dto.getDocCompanyId(),
                                    dto.getCurrencyCode(), dto.getPayeeCategory(), dto.getPayeeId(), new Date(),
                                    dto.getPayedAmount(), dto.getAccountName(), dto.getPaymentMethodId(),
                                    dto.getAccountName(), dto.getAccountNumber(), dto.getDocCategory(), dto.getDocId(),
                                    dto.getDocNumber(), dto.getDocLineId());
                }

                // 核销记录
                CshWriteOff cshWriteOff = new CshWriteOff();
                cshWriteOff.setWriteOffType(CshWriteOff.PAYMENT_PREPAYMENT);
                cshWriteOff.setMoCshTrxClassId(dto.getMoCshTrxClassId());
                cshWriteOff.setTransactionHeaderId(cshTransactionHeader.getTransactionHeaderId());
                cshWriteOff.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
                cshWriteOff.setCshWriteOffAmount(dto.getPaymentAmount());
                cshWriteOff.setDocumentSource(CshWriteOff.DOC_SOURCE_PAYMENT_REQUISITION);
                cshWriteOff.setDocumentHeaderId(dto.getDocId());
                cshWriteOff.setDocumentLineId(dto.getDocLineId());
                cshWriteOff.setDocumentWriteOffAmount(dto.getPaymentAmount());
                cshWriteOff.setWriteOffDate(cshDocPayment.getPaymentDate());
                cshWriteOff.setPeriodName(periodName);
                cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                cshWriteOffList.add(cshWriteOff);
            }

            // 2.0.5 调用支付逻辑
            cshTransactionHeaderService.postTransaction(iRequest, cshTransactionHeader.getTransactionHeaderId(),
                            cshDocPayment, cshWriteOffList);

            // 2.0.6 处理状态
            if (BaseConstants.YES.equals(eBankFlag)) {
                // 更新现金事务头过账标识为暂挂
                cshTransactionHeaderMapper.updateTrxHeaderFlag(CshTransactionHeader.POSTED_FLAG_H, null, null, null,
                                cshTransactionHeader.getTransactionHeaderId(), null, null);

                // 更新预付款现金事务头过账标识为暂挂
                cshTransactionHeaderMapper.updateTrxHeaderFlag(CshTransactionHeader.POSTED_FLAG_H, null, null, null,
                                null, CshTransactionHeader.TRX_TYPE_PREPAYMENT,
                                cshTransactionHeader.getTransactionHeaderId());
                // 更新现金事务凭证表总账标识为暂挂
                cshTransactionAccountMapper.updateInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_H,
                                cshTransactionLine.getTransactionLineId());
            }

            // 2.0.7 回写现金事务集合
            cshTransactionLines.add(cshTransactionLine);
        }
        // 3.0 现金事务集合
        return cshTransactionLines;
    }

    /**
     * 付款申请单支付
     *
     * @author Tagin
     * @date 2019-04-09 11:37
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据
     * @param acpList 付款单支付集合
     * @param periodName 期间
     * @param eBankFlag 网银标志
     * @param mergeFlag 合并标志
     * @param batchFlag 付款批标志
     * @return java.util.List<com.hand.hec.csh.dto.CshTransactionLine>
     * @version 1.0
     **/
    public List<CshTransactionLine> acpPayment(IRequest iRequest, CshDocPayment cshDocPayment,
                    List<CshDocPayAccEntity> acpList, String periodName, String eBankFlag, String mergeFlag,
                    String batchFlag) {
        BigDecimal singleAmount;
        String writeTypeCode, documentSource;
        List<CshTransactionLine> cshTransactionLines = new ArrayList<>();
        List<CshWriteOff> cshWriteOffList = new ArrayList<>();
        // 1.0 获取需合并的数据集合
        Map<Object, List<CshDocPayAccEntity>> map = this.trxGroup(iRequest, acpList, mergeFlag);

        // 2.0 处理过账
        for (Map.Entry<Object, List<CshDocPayAccEntity>> ctxMap : map.entrySet()) {
            // 2.0.1 生成现金事务头
            CshTransactionHeader cshTransactionHeader = cshTransactionHeaderService.insertTrxHeader(iRequest,
                            CshTransactionHeader.TRX_TYPE_PAYMENT, ctxMap.getValue().get(0).getMoCshTrxClassId(),
                            iRequest.getCompanyId(), cshDocPayment.getAccEntityId(), iRequest.getEmployeeId(),
                            cshDocPayment.getDescription(), BaseConstants.YES, cshDocPayment.getPaymentDate(),
                            periodName, cshDocPayment.getPaymentMethodId(), CshTransactionHeader.TRX_CATEGORY_BUSINESS,
                            BaseConstants.NO, BaseConstants.NO, null, BaseConstants.NO, BaseConstants.NO, null, null,
                            BaseConstants.YES.equals(eBankFlag) ? CshTransactionHeader.INTERFACE_FLAG_H
                                            : CshTransactionHeader.INTERFACE_FLAG_P,
                            null, eBankFlag, CshDocPayAccEntity.DOC_ACP_REQUISITION,
                            ctxMap.getValue().get(0).getDocTypeId(), ctxMap.getValue().get(0).getPaymentUsedeId(),
                            ctxMap.getValue().get(0).getPayeeCategory(), ctxMap.getValue().get(0).getPayeeId(),
                            ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getBankAccountId());

            // 2.0.2 生成现金事务行
            CshTransactionLine cshTransactionLine = cshTransactionLineService.insertTrxLine(iRequest,
                            ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getExchangeRateType(),
                            cshDocPayment.getExchangeRate(), cshDocPayment.getBankAccountId(),
                            ctxMap.getValue().get(0).getDocNumber(), ctxMap.getValue().get(0).getPayeeCategory(),
                            ctxMap.getValue().get(0).getPayeeId(), null, cshDocPayment.getDescription(), null, null,
                            iRequest.getEmployeeId(), null, CshDocPayAccEntity.DOC_ACP_REQUISITION,
                            ctxMap.getValue().get(0).getDocTypeId(), ctxMap.getValue().get(0).getPaymentUsedeId(),
                            cshDocPayment.getPaymentMethodId(), cshTransactionHeader.getTransactionHeaderId(),
                            iRequest.getCompanyId(), cshDocPayment.getAccEntityId(),
                            ctxMap.getValue().stream().map(CshDocPayAccEntity::getPaymentAmount).reduce(BigDecimal.ZERO,
                                            BigDecimal::add));

            // 2.0.3 获取付款批类型
            if (BaseConstants.YES.equals(batchFlag)) {
                Long typeId = cshPaymentBatchRuleService.getTypeId(iRequest.getMagOrgId(),
                                cshDocPayment.getAccEntityId(), CshDocPayAccEntity.DOC_ACP_REQUISITION,
                                ctxMap.getValue().get(0).getDocTypeCode(), ctxMap.getValue().get(0).getPayeeCategory(),
                                ctxMap.getValue().get(0).getPayeeName(), ctxMap.getValue().get(0).getAccountNumber(),
                                ctxMap.getValue().get(0).getCurrencyCode(), cshDocPayment.getPaymentMethodCode(),
                                ctxMap.getValue().get(0).getPaymentUsedeCode());
                if (typeId == null || StringUtils.isEmpty(typeId.toString())) {
                    throw new CshTransactionException(CshTransactionException.PAYMENT_BATCH_NOT_FOUND,
                                    CshTransactionException.PAYMENT_BATCH_NOT_FOUND, null);
                } else {
                    cshTransactionLine.setTypeId(typeId);
                }
                cshTransactionLine.setAccountName(ctxMap.getValue().get(0).getAccountName());
                cshTransactionLine.setAccountNumber(ctxMap.getValue().get(0).getAccountNumber());
            }

            // 2.0.4 写入支付信息【银企直连】/ 写入后续核销记录
            for (CshDocPayAccEntity dto : ctxMap.getValue()) {
                // 支付信息
                if (BaseConstants.YES.equals(eBankFlag)) {
                    cshPaymentMessageService.createPaymentMessage(iRequest, dto.getAccEntityId(), dto.getDocCompanyId(),
                                    dto.getCurrencyCode(), dto.getPayeeCategory(), dto.getPayeeId(), new Date(),
                                    dto.getPayedAmount(), dto.getAccountName(), dto.getPaymentMethodId(),
                                    dto.getAccountName(), dto.getAccountNumber(), dto.getDocCategory(), dto.getDocId(),
                                    dto.getDocNumber(), dto.getDocLineId());
                }
                // 付款现金事务分类
                CshMoTransactionClass cshMoTransactionClass =
                                cshMoTransactionClassMapper.selectByPrimaryKey(dto.getMoCshTrxClassId());
                CshWriteOff cshWriteOff = new CshWriteOff();
                if (cshMoTransactionClass != null && CshTransactionHeader.TRX_TYPE_PAYMENT
                                .equals(cshMoTransactionClass.getCshTransactionTypeCode())) {
                    List<AcpRequisitionDtl> acpRequisitionDtls =
                                    acpRequisitionDtlService.queryAllByLnsId(dto.getDocLineId());
                    if (CollectionUtils.isEmpty(acpRequisitionDtls)) {
                        // 1.0 不存在明细行
                        // 录入核销记录 Tips：未未关联报销、合同结算的付款申请单
                        cshWriteOff.setWriteOffType(CshWriteOff.ACP_PAYMENT);
                        cshWriteOff.setMoCshTrxClassId(dto.getMoCshTrxClassId());
                        cshWriteOff.setTransactionHeaderId(cshTransactionHeader.getTransactionHeaderId());
                        cshWriteOff.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
                        cshWriteOff.setCshWriteOffAmount(dto.getPaymentAmount());
                        cshWriteOff.setDocumentSource(CshWriteOff.DOC_SOURCE_ACP_REQUISITION);
                        cshWriteOff.setDocumentHeaderId(dto.getDocId());
                        cshWriteOff.setDocumentLineId(dto.getDocLineId());
                        cshWriteOff.setDocumentWriteOffAmount(dto.getPaymentAmount());
                        cshWriteOff.setWriteOffDate(cshDocPayment.getPaymentDate());
                        cshWriteOff.setPeriodName(periodName);
                        cshWriteOff.setDocumentId(dto.getDocLineId());
                        cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                        cshWriteOffList.add(cshWriteOff);
                    } else {
                        // 2.0 存在明细行记录
                        for (AcpRequisitionDtl acpRequisitionDtl : acpRequisitionDtls) {
                            // 付款申请单行已付金额
                            BigDecimal paidAmount = acpRequisitionRefService.getPaidAmount(dto.getDocLineId());
                            if (dto.getNeedPaymentAmount().compareTo(dto.getPaymentAmount().add(paidAmount)) == 0) {
                                // 完全支付 Tips：此处计算付款明细行 对应的本次支付应支付金额
                                singleAmount = acpRequisitionDtl.getAmount()
                                                .subtract(acpRequisitionDtl.getPayedAmount());
                            } else {
                                // 未完全支付 [本次核销金额 = 支付金额 * 权重（付款明细行金额 / 付款申请单行金额）]
                                singleAmount = dto.getPaymentAmount().multiply(
                                                acpRequisitionDtl.getAmount().divide(dto.getNeedPaymentAmount(), 6));
                            }
                            // 录入核销记录
                            cshWriteOff.setWriteOffType(CshWriteOff.ACP_PAYMENT);
                            cshWriteOff.setMoCshTrxClassId(dto.getMoCshTrxClassId());
                            cshWriteOff.setTransactionHeaderId(cshTransactionHeader.getTransactionHeaderId());
                            cshWriteOff.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
                            cshWriteOff.setCshWriteOffAmount(singleAmount);
                            if (AcpRequisitionDtl.DOC_TYPE_REPORT.equals(acpRequisitionDtl.getRefDocumentType())) {
                                documentSource = CshWriteOff.DOC_SOURCE_EXPENSE_REPORT;
                            } else {
                                documentSource = CshWriteOff.DOC_SOURCE_CON_CONTRACT;
                            }
                            cshWriteOff.setDocumentSource(documentSource);
                            cshWriteOff.setDocumentHeaderId(acpRequisitionDtl.getRefDocumentId());
                            cshWriteOff.setDocumentLineId(acpRequisitionDtl.getRefDocumentLineId());
                            cshWriteOff.setDocumentWriteOffAmount(singleAmount);
                            cshWriteOff.setWriteOffDate(cshDocPayment.getPaymentDate());
                            cshWriteOff.setPeriodName(periodName);
                            cshWriteOff.setDocumentId(dto.getDocLineId());
                            cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                            cshWriteOffList.add(cshWriteOff);
                        }
                    }
                } else {
                    // 录入核销记录 Tips：付款申请行为预付款
                    cshWriteOff.setWriteOffType(CshWriteOff.ACP_PREPAYMENT);
                    cshWriteOff.setMoCshTrxClassId(dto.getMoCshTrxClassId());
                    cshWriteOff.setTransactionHeaderId(cshTransactionHeader.getTransactionHeaderId());
                    cshWriteOff.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
                    cshWriteOff.setCshWriteOffAmount(dto.getPaymentAmount());
                    cshWriteOff.setDocumentSource(CshWriteOff.DOC_SOURCE_ACP_REQUISITION);
                    cshWriteOff.setDocumentHeaderId(dto.getDocId());
                    cshWriteOff.setDocumentLineId(dto.getDocLineId());
                    cshWriteOff.setDocumentWriteOffAmount(dto.getPaymentAmount());
                    cshWriteOff.setWriteOffDate(cshDocPayment.getPaymentDate());
                    cshWriteOff.setPeriodName(periodName);
                    cshWriteOff.setDocumentId(dto.getDocLineId());
                    cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                    cshWriteOffList.add(cshWriteOff);
                }
            }

            // 2.0.5 调用支付逻辑
            cshTransactionHeaderService.postTransaction(iRequest, cshTransactionHeader.getTransactionHeaderId(),
                            cshDocPayment, cshWriteOffList);

            // 2.0.6 处理预付款核销-报销单、合同
            for (CshDocPayAccEntity dto : ctxMap.getValue()) {
                // 付款现金事务分类
                CshMoTransactionClass cshMoTransactionClass =
                                cshMoTransactionClassMapper.selectByPrimaryKey(dto.getMoCshTrxClassId());
                if (CshTransactionHeader.TRX_TYPE_PREPAYMENT
                                .equals(cshMoTransactionClass.getCshTransactionTypeCode())) {
                    List<AcpRequisitionDtl> acpRequisitionDtls =
                                    acpRequisitionDtlService.queryAllByLnsId(dto.getDocLineId());
                    if (CollectionUtils.isNotEmpty(acpRequisitionDtls)) {
                        // 存在明细行记录
                        for (AcpRequisitionDtl acpRequisitionDtl : acpRequisitionDtls) {
                            // 付款申请单行已付金额
                            BigDecimal paidAmount = acpRequisitionRefService.getPaidAmount(dto.getDocLineId());
                            if (dto.getNeedPaymentAmount().compareTo(dto.getPaymentAmount().add(paidAmount)) == 0) {
                                // 完全支付 Tips：此处计算付款明细行 对应的本次支付应支付金额
                                singleAmount = acpRequisitionDtl.getAmount()
                                                .subtract(acpRequisitionDtl.getPayedAmount());
                            } else {
                                // 未完全支付 [本次核销金额 = 支付金额 * 权重（付款明细行金额 / 付款申请单行金额）]
                                singleAmount = dto.getPaymentAmount().multiply(
                                                acpRequisitionDtl.getAmount().divide(dto.getNeedPaymentAmount(), 6));
                            }
                            // 录入核销记录
                            CshWriteOff preWriteOff = cshWriteOffService.getWriteOffByAcpRef(dto.getDocLineId(),
                                            cshTransactionLine.getTransactionLineId());
                            CshWriteOff cshWriteOff = new CshWriteOff();
                            cshWriteOff.setWriteOffType(CshWriteOff.PREPAYMENT_ACP_REQUISITION);
                            cshWriteOff.setMoCshTrxClassId(dto.getMoCshTrxClassId());
                            cshWriteOff.setTransactionHeaderId(preWriteOff.getTransactionHeaderId());
                            cshWriteOff.setCshTransactionLineId(preWriteOff.getSourceCshTrxLineId());
                            cshWriteOff.setCshWriteOffAmount(singleAmount);
                            if (AcpRequisitionDtl.DOC_TYPE_REPORT.equals(acpRequisitionDtl.getRefDocumentType())) {
                                documentSource = CshWriteOff.DOC_SOURCE_EXPENSE_REPORT;
                            } else {
                                documentSource = CshWriteOff.DOC_SOURCE_CON_CONTRACT;
                            }
                            cshWriteOff.setDocumentSource(documentSource);
                            cshWriteOff.setDocumentHeaderId(acpRequisitionDtl.getRefDocumentId());
                            cshWriteOff.setDocumentLineId(acpRequisitionDtl.getRefDocumentLineId());
                            cshWriteOff.setDocumentWriteOffAmount(singleAmount);
                            cshWriteOff.setWriteOffDate(cshDocPayment.getPaymentDate());
                            cshWriteOff.setPeriodName(periodName);
                            cshWriteOff.setDocumentId(dto.getDocLineId());
                            cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                            cshWriteOffList.add(cshWriteOff);
                        }
                    }
                }
            }

            // 2.0.7 调用核销逻辑
            List<CshWriteOff> cshWriteOffs = cshWriteOffList.stream()
                            .filter(x -> CshWriteOff.PREPAYMENT_ACP_REQUISITION.equals(x.getWriteOffType()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(cshWriteOffs)) {
                cshWriteOffService.executePayment(iRequest, cshDocPayment, cshWriteOffs, cshTransactionHeader,
                                cshTransactionLine);
            }

            // 2.0.8 处理状态
            if (BaseConstants.YES.equals(eBankFlag)) {
                // 更新现金事务头过账标识为暂挂
                cshTransactionHeaderMapper.updateTrxHeaderFlag(CshTransactionHeader.POSTED_FLAG_H, null, null, null,
                                cshTransactionHeader.getTransactionHeaderId(), null, null);
                // 更新预付款现金事务头过账标识为暂挂
                cshTransactionHeaderMapper.updateTrxHeaderFlag(CshTransactionHeader.POSTED_FLAG_H, null, null, null,
                                null, CshTransactionHeader.TRX_TYPE_PREPAYMENT,
                                cshTransactionHeader.getTransactionHeaderId());
                // 更新现金事务凭证表总账标识为暂挂
                cshTransactionAccountMapper.updateInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_H,
                                cshTransactionLine.getTransactionLineId());
            }

            // 2.0.9 回写现金事务集合
            cshTransactionLines.add(cshTransactionLine);
        }
        return cshTransactionLines;
    }

    /**
     * 单据支付
     *
     * @Author Tagin
     * @Date 2019-03-12 21:47
     * @param iRequest 请求
     * @param cshDocPayment 单据支付基础信息对象
     * @param cshDocPayAccEntities 支付集合
     * @Return void
     * @Version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void executePayment(IRequest iRequest, CshDocPayment cshDocPayment,
                    List<CshDocPayAccEntity> cshDocPayAccEntities) {
        List<CshTransactionLine> cshTransactionLines = new ArrayList<>();
        // 期间
        String periodName = gldPeriodService.getPeriodName(iRequest, cshDocPayment.getPaymentDate(),
                        cshDocPayment.getAccEntityId(), GldPeriod.STATUS_CODE_O);
        // 网银标志
        String eBankFlag = cshPaymentMethodService.getEBankFlag(cshDocPayment.getPaymentMethodId());
        // 控制参数 [Y 按照缺省规则合并（原分组合并条件）、N 不合并、R 按照事务分组合并]
        String mergeFlag = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_CSH_PAYMENT_MERGE, null,
                        null, null, cshDocPayment.getAccEntityId(), null, iRequest.getMagOrgId(), null);
        // 付款批参数 [Y 启用付款批、N 不启用付款批]
        String batchFlag = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_CSH_PAYMENT_BATCHES, null,
                        null, null, cshDocPayment.getAccEntityId(), null, iRequest.getMagOrgId(), null);
        // 1.0 报销单支付
        List<CshDocPayAccEntity> reportList = cshDocPayAccEntities.stream()
                        .filter(x -> CshDocPayAccEntity.DOC_EXP_REPORT.equals(x.getDocCategory()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(reportList)) {
            // Lock Table
            lockTable(iRequest, CshDocPayAccEntity.DOC_EXP_REPORT, reportList);
            cshTransactionLines.addAll(reportPayment(iRequest, cshDocPayment, reportList, periodName, eBankFlag,
                            mergeFlag, batchFlag));
        }
        // 2.0 借款单支付
        List<CshDocPayAccEntity> loanList = cshDocPayAccEntities.stream()
                        .filter(x -> CshDocPayAccEntity.DOC_PAYMENT_REQUISITION.equals(x.getDocCategory()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(loanList)) {
            // Lock Table
            lockTable(iRequest, CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, reportList);
            cshTransactionLines.addAll(loanPayment(iRequest, cshDocPayment, loanList, periodName, eBankFlag, mergeFlag,
                            batchFlag));
        }
        // 3.0 付款申请单支付
        List<CshDocPayAccEntity> acpList = cshDocPayAccEntities.stream()
                        .filter(x -> CshDocPayAccEntity.DOC_ACP_REQUISITION.equals(x.getDocCategory()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(acpList)) {
            // Lock Table
            lockTable(iRequest, CshDocPayAccEntity.DOC_ACP_REQUISITION, reportList);
            cshTransactionLines.addAll(
                            acpPayment(iRequest, cshDocPayment, acpList, periodName, eBankFlag, mergeFlag, batchFlag));
        }
        // 4.0 生成付款批
        if (CollectionUtils.isNotEmpty(cshTransactionLines) && BaseConstants.YES.equals(batchFlag)) {
            generatePaymentBatch(iRequest, cshDocPayment, cshTransactionLines);
        }
        // 5.0 将生成的现金事务凭证写入分录
        if (BaseConstants.NO.equals(batchFlag) && CollectionUtils.isNotEmpty(cshTransactionLines)) {
            for (CshTransactionLine dto : cshTransactionLines) {
                glAccountEntryService.headerGlAccountEntry(iRequest, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                                dto.getTransactionHeaderId());
            }
        }
    }

    /**
     * 更新对应单据付款状态
     *
     * @param docId 单据头ID
     * @param docLineId 单据行ID【Tips：报销单计划付款行、借款申请单行、付款申请单行】
     * @param paymentStatus 支付状态
     * @param docCategory 单据类别
     * @param iRequest 请求
     * @author ngls.luhui 2019-03-11 19:30
     * @return
     */
    @Override
    public void updatePaymentStatus(Long docId, Long docLineId, String paymentStatus, String docCategory,
                    IRequest iRequest) {
        cshDocPayAccEntityMapper.updatePaymentStatus(docId, docLineId, paymentStatus, docCategory);
    }

    @Override
    public void payBack(IRequest iRequest, List<CshDocPayAccEntity> dto) {
        String opinion = cshDocPayAccEntityMapper.queryDocBackDesc(CshPaymentRequisitionHd.STATUS_PAY_BACK);
        // 报销单
        List<CshDocPayAccEntity> reportList =
                        dto.stream().filter(x -> CshDocPayAccEntity.DOC_EXP_REPORT.equals(x.getDocCategory()))
                                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(reportList)) {
            expPayReturnDoc(iRequest, reportList, opinion);
        }
        // 借款单
        List<CshDocPayAccEntity> loanList =
                        dto.stream().filter(x -> CshDocPayAccEntity.DOC_PAYMENT_REQUISITION.equals(x.getDocCategory()))
                                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(loanList)) {
            cshReqReturnDoc(iRequest, loanList, opinion);
        }
        // 付款单
        List<CshDocPayAccEntity> acpList =
                        dto.stream().filter(x -> CshDocPayAccEntity.DOC_ACP_REQUISITION.equals(x.getDocCategory()))
                                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(acpList)) {
            acpReqReturnDoc(iRequest, acpList, opinion);
        }

    }

    /**
     * 报销单支付出纳退回
     *
     * @param request
     * @param reportList 报销单
     * @author guiyuting 2019-06-17 11:11
     * @return
     */
    private void expPayReturnDoc(IRequest request, List<CshDocPayAccEntity> reportList, String opinion) {
        for (CshDocPayAccEntity cshDocPayAccEntity : reportList) {
            int num = cshDocPayAccEntityMapper.countBackForExpReport(cshDocPayAccEntity.getDocId());
            if (num != 1) {
                throw new CshDocPayAccEntityException(CshDocPayAccEntityException.DOC_STATUS_ERROR, null);
            }
            ExpReportHeader expReportHeader = ExpReportHeader.builder().expReportHeaderId(cshDocPayAccEntity.getDocId())
                            .docStatus(CshPaymentRequisitionHd.STATUS_PAY_BACK).build();
            expReportHeaderService.updateByPrimaryKeySelective(request, expReportHeader);
            historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
                            expReportHeader.getExpReportHeaderId(), ExpDocumentHistory.STATUS_PAY_BACK,
                            request.getUserId(), opinion);
        }
    }

    /**
     * 借款单支付出纳退回
     *
     * @param loanList
     * @author guiyuting 2019-06-17 15:02
     * @return
     */
    private void cshReqReturnDoc(IRequest request, List<CshDocPayAccEntity> loanList, String opinion) {
        for (CshDocPayAccEntity cshDocPayAccEntity : loanList) {
            int num = cshDocPayAccEntityMapper.countBackForPaymentReq(cshDocPayAccEntity.getDocId());
            if (num != 1) {
                throw new CshDocPayAccEntityException(CshDocPayAccEntityException.DOC_STATUS_ERROR, null);
            }
            CshPaymentRequisitionHd cshPaymentRequisitionHd =
                            CshPaymentRequisitionHd.builder().paymentRequisitionHeaderId(cshDocPayAccEntity.getDocId())
                                            .docStatus(CshPaymentRequisitionHd.STATUS_PAY_BACK).build();
            cshPaymentRequisitionHdService.updateByPrimaryKeySelective(request, cshPaymentRequisitionHd);
            historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_PAYMENT_REQUISITION,
                            cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_PAY_BACK,
                            request.getUserId(), opinion);
        }
    }

    /**
     * 付款申请单支付出纳退回
     *
     * @param acpList
     * @author guiyuting 2019-06-17 15:01
     * @return
     */
    private void acpReqReturnDoc(IRequest request, List<CshDocPayAccEntity> acpList, String opinion) {
        for (CshDocPayAccEntity cshDocPayAccEntity : acpList) {
            int num = cshDocPayAccEntityMapper.countBackForAcpReq(cshDocPayAccEntity.getDocId());
            if (num != 1) {
                throw new CshDocPayAccEntityException(CshDocPayAccEntityException.DOC_STATUS_ERROR, null);
            }
            AcpRequisitionHd acpRequisitionHd =
                            AcpRequisitionHd.builder().requisitionHdsId(cshDocPayAccEntity.getDocId())
                                            .docStatus(CshPaymentRequisitionHd.STATUS_PAY_BACK).build();
            acpRequisitionHdService.updateByPrimaryKeySelective(request, acpRequisitionHd);
            historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_ACP_REQUISITION,
                            acpRequisitionHd.getRequisitionHdsId(), ExpDocumentHistory.STATUS_PAY_BACK,
                            request.getUserId(), opinion);
        }
    }
}
