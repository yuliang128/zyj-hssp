package com.hand.hec.csh.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.mapper.AcpRequisitionHdMapper;
import com.hand.hec.acp.mapper.AcpRequisitionLnMapper;
import com.hand.hec.acp.service.IAcpRequisitionAccountService;
import com.hand.hec.cont.service.IConCashTrxPmtScheduleLnService;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.mapper.*;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.exp.service.IExpOrgUnitService;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.mapper.ExpReportAccountMapper;
import com.hand.hec.expm.mapper.ExpReportHeaderMapper;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.mapper.GldAePeriodStatusMapper;
import com.hand.hec.fnd.mapper.OrdSystemCustomerMapper;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;
import com.hand.hec.gld.constants.UsageCodeConstants;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.service.IGldMappingConditionService;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;
import com.hand.hec.gld.service.IGldSetOfBookService;
import com.hand.hec.pur.mapper.PurSystemVenderMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 现金事务头表ServiceImpl
 * </p>
 *
 * @author Tagin 2019/01/22 10:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshTransactionHeaderServiceImpl extends BaseServiceImpl<CshTransactionHeader>
                implements ICshTransactionHeaderService {

    @Autowired
    private ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    private ExpReportAccountMapper expReportAccountMapper;

    @Autowired
    private ExpEmployeeMapper expEmployeeMapper;

    @Autowired
    private PurSystemVenderMapper purSystemVenderMapper;

    @Autowired
    private OrdSystemCustomerMapper ordSystemCustomerMapper;

    @Autowired
    private IGldMappingConditionService gldMappingConditionService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ICshPaymentReqAccountService cshPaymentReqAccountService;

    @Autowired
    private ICshTransactionAccountService cshTransactionAccountService;

    @Autowired
    private CshTransactionLineMapper cshTransactionLineMapper;

    @Autowired
    private CshTransactionHeaderMapper cshTransactionHeaderMapper;

    @Autowired
    private GldAePeriodStatusMapper gldAePeriodStatusMapper;

    @Autowired
    private ICshWriteOffService cshWriteOffService;

    @Autowired
    private IConCashTrxPmtScheduleLnService conCashTrxPmtScheduleLnService;

    @Autowired
    private IFndCodingRuleObjectService fndCodingRuleObjectService;

    @Autowired
    private IFndCompanyService fndCompanyService;

    @Autowired
    private IAcpRequisitionAccountService acpRequisitionAccountService;

    @Autowired
    private IExpOrgUnitService expOrgUnitService;

    @Autowired
    private IGldResponsibilityCenterService gldResponsibilityCenterService;

    @Autowired
    private IExpOrgPositionService expOrgPositionService;

    @Autowired
    private IGldSetOfBookService gldSetOfBookService;

    @Autowired
    private CshWriteOffMapper cshWriteOffMapper;

    @Autowired
    private CshPaymentRequisitionHdMapper cshPaymentRequisitionHdMapper;

    @Autowired
    private AcpRequisitionHdMapper acpRequisitionHdMapper;

    @Autowired
    private CshPaymentRequisitionLnMapper cshPaymentRequisitionLnMapper;

    @Autowired
    private AcpRequisitionLnMapper acpRequisitionLnMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private ICshTransactionService cshTransactionService;

    @Autowired
    private ICshTransactionLineService cshTransactionLineService;


    /**
     * 获取报销单费用凭证-贷方凭证
     *
     * @param expReportPmtSchedule 费用报销单计划付款行对象
     * @return com.hand.hec.expm.dto.ExpReportAccount
     * @author Tagin
     * @date 2019-02-21 20:09
     * @version 1.0
     **/
    @Override
    public ExpReportAccount getReportCrAccount(ExpReportPmtSchedule expReportPmtSchedule) {
        // 获取费用凭证对象
        ExpReportAccount dto = new ExpReportAccount();
        dto.setUsageCode(UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_CLEARING);
        dto.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpReportAccount.FIELD_USAGE_CODE, Comparison.EQUAL),
                        new WhereField(ExpReportAccount.FIELD_PAYMENT_SCHEDULE_LINE_ID, Comparison.EQUAL));
        List<ExpReportAccount> expReportAccounts = new ArrayList<>();
        expReportAccounts = expReportAccountMapper.selectOptions(dto, criteria);
        if (expReportAccounts.size() > 0) {
            return expReportAccounts.get(0);
        } else {
            Long payeeTypeId = null;
            // 获取报销单对象
            ExpReportHeader expReportHeader = new ExpReportHeader();
            expReportHeader = expReportHeaderMapper.getExpReportHeader(expReportPmtSchedule.getExpReportHeaderId());
            // 收款方类型
            if (CshTransactionLine.PAYEE_CATEGORY_EMPLOYEE.equals(expReportPmtSchedule.getPayeeCategory())) {
                payeeTypeId = expEmployeeMapper.selectByPrimaryKey(expReportPmtSchedule.getPayeeId())
                                .getEmployeeTypeId();
            } else if (CshTransactionLine.PAYEE_CATEGORY_VENDER.equals(expReportPmtSchedule.getPayeeCategory())) {
                payeeTypeId = purSystemVenderMapper.selectByPrimaryKey(expReportPmtSchedule.getPayeeId())
                                .getVenderTypeId();
            } else if (CshTransactionLine.PAYEE_CATEGORY_CUSTOMER.equals(expReportPmtSchedule.getPayeeCategory())) {
                payeeTypeId = ordSystemCustomerMapper.selectByPrimaryKey(expReportPmtSchedule.getPayeeId())
                                .getCustomerTypeId();
            }
            // 调用用途代码逻辑
            List<GldMappingCondition> mappingConditions = new ArrayList<>();
            mappingConditions = gldMappingConditionService.accBuilderExpClearing(
                            expReportHeader.getMagOrgId() == null ? null : expReportHeader.getMagOrgId().toString(),
                            expReportHeader.getCompanyId() == null ? null : expReportHeader.getCompanyId().toString(),
                            null,
                            expReportPmtSchedule.getAccEntityId() == null ? null
                                            : expReportPmtSchedule.getAccEntityId().toString(),
                            null,
                            expReportHeader.getEmployeeId() == null ? null : expReportHeader.getEmployeeId().toString(),
                            expReportHeader.getMoExpReportTypeId() == null ? null
                                            : expReportHeader.getMoExpReportTypeId().toString(),
                            expReportPmtSchedule.getPaymentCurrencyCode(),
                            expReportHeader.getEmployeeTypeId() == null ? null
                                            : expReportHeader.getEmployeeTypeId().toString(),
                            expReportPmtSchedule.getPayeeCategory(),
                            payeeTypeId == null ? null : payeeTypeId.toString(),
                            expReportPmtSchedule.getPayeeId() == null ? null
                                            : expReportPmtSchedule.getPayeeId().toString(),
                            expReportPmtSchedule.getPaymentUsedeId() == null ? null
                                            : expReportPmtSchedule.getPaymentUsedeId().toString());
            // 获取科目
            Long accountId = (Long) gldMappingConditionService.getAccount(mappingConditions,
                            UsageCodeConstants.USAGE_EMPLOYEE_EXPENSE_CLEARING, expReportHeader.getMagOrgId(),
                            expReportHeader.getSetOfBooksId());
            ExpReportAccount expReportAccount = new ExpReportAccount();
            expReportAccount.setAccountId(accountId);
            return expReportAccount;
        }
    }

    /**
     * 获取借款申请单凭证【审核凭证/支付凭证】- 借方凭证
     *
     * @param cshTransactionLine 预付款现金事务行对象
     * @param auitFlag 自审核标志
     * @author Tagin
     * @Date 2019-02-22 14:12
     * @Return com.hand.hec.csh.dto.CshPaymentReqAccount
     * @Version 1.0
     **/
    @Override
    public CshPaymentReqAccount getPaymentDrAccount(CshTransactionLine cshTransactionLine, String auitFlag) {
        // 获取现金事务及借款单行ID
        CshWriteOff cshWriteOff = cshWriteOffMapper.getWriteByType(CshWriteOff.PAYMENT_PREPAYMENT, null,
                        CshWriteOff.DOC_SOURCE_PAYMENT_REQUISITION, cshTransactionLine.getTransactionLineId()).get(0);
        String interfaceType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_SYS_INTERFACE_TYPE,
                        null, null, null, null, null, null, null);
        if (CshTransactionHeader.SYS_INTERFACE_EBS_AP.equals(interfaceType) && !BaseConstants.YES.equals(auitFlag)) {
            // 借款申请单需要审核，则取审核凭证
            CshPaymentReqAccount cshPaymentReqAccount =
                            cshPaymentReqAccountService.queryAccount(null, UsageCodeConstants.USAGE_PAYMENT_REQUISITION,
                                            cshWriteOff.getDocumentLineId(), BaseConstants.YES, null);
            if (cshPaymentReqAccount != null) {
                return cshPaymentReqAccount;
            }
        } else {
            // 借款申请单不需审核，则取支付凭证
            CshTransactionAccount cshTransactionAccount = cshTransactionAccountService.queryAccount(
                            cshWriteOff.getCshTransactionLineId(), UsageCodeConstants.USAGE_PREPAYMENT,
                            BaseConstants.YES, null, cshWriteOff.getWriteOffId());
            if (cshTransactionAccount != null) {
                CshPaymentReqAccount cshPaymentReqAccount = new CshPaymentReqAccount();
                cshPaymentReqAccount.setRespCenterId(cshTransactionAccount.getRespCenterId());
                cshPaymentReqAccount.setAccountId(cshTransactionAccount.getAccountId());
                cshPaymentReqAccount.setAccountSegment1(cshTransactionAccount.getAccountSegment1());
                cshPaymentReqAccount.setAccountSegment2(cshTransactionAccount.getAccountSegment2());
                cshPaymentReqAccount.setAccountSegment3(cshTransactionAccount.getAccountSegment3());
                cshPaymentReqAccount.setAccountSegment4(cshTransactionAccount.getAccountSegment4());
                cshPaymentReqAccount.setAccountSegment5(cshTransactionAccount.getAccountSegment5());
                cshPaymentReqAccount.setAccountSegment6(cshTransactionAccount.getAccountSegment6());
                cshPaymentReqAccount.setAccountSegment7(cshTransactionAccount.getAccountSegment7());
                cshPaymentReqAccount.setAccountSegment8(cshTransactionAccount.getAccountSegment8());
                cshPaymentReqAccount.setAccountSegment9(cshTransactionAccount.getAccountSegment9());
                cshPaymentReqAccount.setAccountSegment10(cshTransactionAccount.getAccountSegment10());
                cshPaymentReqAccount.setAccountSegment11(cshTransactionAccount.getAccountSegment11());
                cshPaymentReqAccount.setAccountSegment12(cshTransactionAccount.getAccountSegment12());
                cshPaymentReqAccount.setAccountSegment13(cshTransactionAccount.getAccountSegment13());
                cshPaymentReqAccount.setAccountSegment14(cshTransactionAccount.getAccountSegment14());
                cshPaymentReqAccount.setAccountSegment15(cshTransactionAccount.getAccountSegment15());
                cshPaymentReqAccount.setAccountSegment16(cshTransactionAccount.getAccountSegment16());
                cshPaymentReqAccount.setAccountSegment17(cshTransactionAccount.getAccountSegment17());
                cshPaymentReqAccount.setAccountSegment18(cshTransactionAccount.getAccountSegment18());
                cshPaymentReqAccount.setAccountSegment19(cshTransactionAccount.getAccountSegment19());
                cshPaymentReqAccount.setAccountSegment20(cshTransactionAccount.getAccountSegment20());
                return cshPaymentReqAccount;
            }
        }
        return null;
    }

    /**
     * 获取用途代码付款单科目 [PAY_REQUISITION]
     *
     * @param iRequest 请求
     * @param transactionLineId 现金事务行 ID
     * @return java.lang.Long
     * @author Tagin
     * @date 2019-05-06 20:51
     * @version 1.0
     **/
    @Override
    public Long getPayRequisitionAccount(IRequest iRequest, Long transactionLineId) {
        // 现金事务行
        CshTransactionLine cshTransactionLine = cshTransactionLineMapper.selectByPrimaryKey(transactionLineId);
        // 现金事务头
        CshTransactionHeader cshTransactionHeader =
                        cshTransactionHeaderMapper.selectByPrimaryKey(cshTransactionLine.getTransactionHeaderId());
        // 默认部门
        ExpOrgUnit dftUnit = expOrgUnitService.getDefaultUnit(iRequest, cshTransactionHeader.getEmployeeId(),
                        cshTransactionLine.getCompanyId());
        // 默认成本中心
        GldResponsibilityCenter dftRespCenter = gldResponsibilityCenterService.getDefaultRespCenter(
                        dftUnit == null ? null : dftUnit.getUnitId(), cshTransactionLine.getAccEntityId());
        // 默认岗位
        ExpOrgPosition dftPosition = expOrgPositionService.getDefaultPosition(iRequest, iRequest.getEmployeeId(),
                        cshTransactionLine.getCompanyId());
        // 默认账套
        GldSetOfBook gldSetOfBook =
                        gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
        // 公司
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, cshTransactionLine.getCompanyId());

        List<GldMappingCondition> gldMappingConditions = gldMappingConditionService.accBuilderPayRequisition(
                        cshTransactionLine.getCompanyId().toString(), cshTransactionLine.getAccEntityId().toString(),
                        dftRespCenter == null ? null : dftRespCenter.getResponsibilityCenterId().toString(),
                        dftPosition == null ? null : dftPosition.getPositionId().toString(),
                        cshTransactionLine.getPayeeCategory(), null, null,
                        cshTransactionHeader.getMoCshTrxClassId() == null ? null
                                        : cshTransactionHeader.getMoCshTrxClassId().toString());
        // 获取科目
        return gldMappingConditionService.getAccount(gldMappingConditions, UsageCodeConstants.USAGE_PAY_REQUISITION,
                        fndCompany.getMagOrgId(), gldSetOfBook.getSetOfBooksId());
    }


    /**
     * 获取用途代码预付款科目 [PREPAYMENT]
     *
     * @param iRequest 请求
     * @param transactionLineId 现金事务行 ID
     * @return java.lang.Long
     * @author Tagin
     * @date 2019-05-06 20:51
     * @version 1.0
     **/
    @Override
    public Long getPrepaymentAccount(IRequest iRequest, Long transactionLineId) {
        // 现金事务行
        CshTransactionLine cshTransactionLine = cshTransactionLineMapper.selectByPrimaryKey(transactionLineId);
        // 现金事务头
        CshTransactionHeader cshTransactionHeader =
                        cshTransactionHeaderMapper.selectByPrimaryKey(cshTransactionLine.getTransactionHeaderId());
        // 默认部门
        ExpOrgUnit dftUnit = expOrgUnitService.getDefaultUnit(iRequest, cshTransactionHeader.getEmployeeId(),
                        cshTransactionLine.getCompanyId());
        // 默认成本中心
        GldResponsibilityCenter dftRespCenter = gldResponsibilityCenterService.getDefaultRespCenter(
                        dftUnit == null ? null : dftUnit.getUnitId(), cshTransactionLine.getAccEntityId());
        // 默认岗位
        ExpOrgPosition dftPosition = expOrgPositionService.getDefaultPosition(iRequest, iRequest.getEmployeeId(),
                        cshTransactionLine.getCompanyId());
        // 默认账套
        GldSetOfBook gldSetOfBook =
                        gldSetOfBookService.queryDftSetOffBookByAe(iRequest, cshTransactionLine.getAccEntityId());
        // 公司
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, cshTransactionLine.getCompanyId());

        List<GldMappingCondition> gldMappingConditions =
                        gldMappingConditionService.accBuilderPrepayment(cshTransactionLine.getCompanyId().toString(),
                                        cshTransactionLine.getAccEntityId().toString(),
                                        dftRespCenter == null ? null
                                                        : dftRespCenter.getResponsibilityCenterId().toString(),
                                        cshTransactionLine.getCurrencyCode(),
                                        dftPosition == null ? null : dftPosition.getPositionId().toString(),
                                        cshTransactionHeader.getEmployeeId().toString(),
                                        cshTransactionLine.getPayeeCategory(),
                                        cshTransactionHeader.getTransactionType(), null, null, null,
                                        cshTransactionHeader.getMoCshTrxClassId() == null ? null
                                                        : cshTransactionHeader.getMoCshTrxClassId().toString(),
                                        null, null);
        // 获取科目
        return gldMappingConditionService.getAccount(gldMappingConditions, UsageCodeConstants.USAGE_PREPAYMENT,
                        fndCompany.getMagOrgId(), gldSetOfBook.getSetOfBooksId());
    }


    /**
     * 获取用途代码现金内部往来应收科目 [CSH_INTERCOMPANY_AR]
     *
     * @param iRequest 请求
     * @param companyId 公司 ID
     * @param accEntityId 核算主体 ID
     * @param oppositeAccEntityId 对方核算主体 ID
     * @param respCenterId 成本中心 ID
     * @param positionId 岗位 ID
     * @param currencyCode 币种
     * @param setOfBooksId 账套 ID
     * @return java.lang.Long
     * @author Tagin
     * @date 2019-05-07 15:18
     * @version 1.0
     **/
    @Override
    public Long getCshIntercompanyArAccount(IRequest iRequest, Long companyId, Long accEntityId,
                    Long oppositeAccEntityId, Long respCenterId, Long positionId, String currencyCode,
                    Long setOfBooksId) {

        // 支付核算主体内部往来应收科目
        List<GldMappingCondition> gldMappingConditions = gldMappingConditionService.accBuilderCshInCompanyAr(
                        companyId.toString(), accEntityId.toString(), oppositeAccEntityId.toString(),
                        respCenterId.toString(), currencyCode, positionId.toString());
        // 公司
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, companyId);
        // 获取科目
        return gldMappingConditionService.getAccount(gldMappingConditions, UsageCodeConstants.USAGE_CSH_INTERCOMPANY_AR,
                        fndCompany.getMagOrgId(), setOfBooksId);
    }

    /**
     * 获取用途代码现金内部往来应收科目 [CSH_INTERCOMPANY_AP]
     *
     * @param iRequest 请求
     * @param companyId 公司 ID
     * @param accEntityId 核算主体 ID
     * @param oppositeAccEntityId 对方核算主体 ID
     * @param respCenterId 成本中心 ID
     * @param positionId 岗位 ID
     * @param currencyCode 币种
     * @param setOfBooksId 账套 ID
     * @return java.lang.Long
     * @author Tagin
     * @date 2019-05-07 15:18
     * @version 1.0
     **/
    @Override
    public Long getCshIntercompanyApAccount(IRequest iRequest, Long companyId, Long accEntityId,
                    Long oppositeAccEntityId, Long respCenterId, Long positionId, String currencyCode,
                    Long setOfBooksId) {

        // 支付核算主体内部往来应付科目
        List<GldMappingCondition> gldMappingConditions = gldMappingConditionService.accBuilderCshInCompanyAp(
                        companyId.toString(), accEntityId.toString(), oppositeAccEntityId.toString(),
                        respCenterId.toString(), currencyCode, positionId.toString());
        // 公司
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, companyId);
        // 获取科目
        return gldMappingConditionService.getAccount(gldMappingConditions, UsageCodeConstants.USAGE_CSH_INTERCOMPANY_AP,
                        fndCompany.getMagOrgId(), setOfBooksId);
    }

    /**
     * 获取用途代码汇兑损益科目 [REVALUATION]
     *
     * @param iRequest 请求
     * @param companyId 公司 ID
     * @param accEntityId 核算主体 ID
     * @param respCenterId 成本中心 ID
     * @param positionId 岗位 ID
     * @param setOfBooksId 账套 ID
     * @param currencyCode 币种
     * @return java.lang.Long
     * @author Tagin
     * @date 2019-05-07 15:37
     * @version 1.0
     **/
    @Override
    public Long getRevaluationAccount(IRequest iRequest, Long companyId, Long accEntityId, Long respCenterId,
                    Long positionId, Long setOfBooksId, String currencyCode) {

        // 汇兑损益科目
        List<GldMappingCondition> gldMappingConditions =
                        gldMappingConditionService.accBuilderRevaluation(companyId.toString(), positionId.toString(),
                                        accEntityId.toString(), respCenterId.toString(), currencyCode);
        // 公司
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, companyId);
        // 获取科目
        return gldMappingConditionService.getAccount(gldMappingConditions, UsageCodeConstants.USAGE_REVALUATION,
                        fndCompany.getMagOrgId(), setOfBooksId);
    }

    /**
     * 获取借款申请单凭证【审核凭证/支付凭证】- 贷方凭证
     *
     * @param cshTransactionLine 现金事务行对象
     * @return com.hand.hec.csh.dto.CshPaymentReqAccount
     * @author Tagin
     * @date 2019-04-01 11:20
     * @version 1.0
     **/
    @Override
    public CshPaymentReqAccount getPaymentCrAccount(CshTransactionLine cshTransactionLine) {
        return cshPaymentReqAccountService.queryAccount(cshTransactionLine.getTransactionLineId(), null, null, null,
                        BaseConstants.YES);
    }

    /**
     * 获取付款申请单审核凭证-借方凭证
     *
     * @param acpRequisitionLn 付款申请单行对象
     * @return com.hand.hec.acp.dto.AcpRequisitionAccount
     * @author Tagin
     * @date 2019-04-30 15:34
     * @version 1.0
     **/
    @Override
    public AcpRequisitionAccount getAcpRequisitionDrAccount(AcpRequisitionLn acpRequisitionLn) {
        return acpRequisitionAccountService.queryAccount(null, null, acpRequisitionLn.getRequisitionLnsId(),
                        BaseConstants.YES, null);
    }

    @Override
    public List<Map> queryByPayReqHeaderId(IRequest request, Long headerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cshTransactionHeaderMapper.queryByPayReqHeaderId(headerId);
    }

    @Override
    public List<Map> queryWriteOffByPaYReqHeaderId(IRequest request, Long headerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cshTransactionHeaderMapper.queryWriteOffByPaYReqHeaderId(headerId);
    }

    @Override
    public List<Map> queryPrePay(IRequest request, int pageNum, int pageSize, Long accEntityId, String payeeCategory,
                    Long payeeId, String currencyCode, String transactionNum, String transactionDateFrom,
                    String transactionDateTo, Long paymentMethodId, String userName, Long agentEmployeeId,
                    String contactNumber, String amountFrom, String amountTo, String requisitionNumber,
                    Long bankAccountId) {
        PageHelper.startPage(pageNum, pageSize);
        return cshTransactionHeaderMapper.queryPrePay(accEntityId, payeeCategory, payeeId, currencyCode, transactionNum,
                        transactionDateFrom, transactionDateTo, paymentMethodId, userName, agentEmployeeId,
                        contactNumber, amountFrom, amountTo, requisitionNumber, bankAccountId);
    }

    @Override
    public List<Map> queryPrePayWtfByHId(IRequest request, Long transactionHeaderId) {
        return cshTransactionHeaderMapper.queryPrePayWtfByHId(transactionHeaderId);
    }

    /**
     * 现金事务过账
     *
     * @param iRequest 请求
     * @param transactionHeaderId 现金事务头ID
     * @param cshDocPayment 支付信息基础对象【Tips：此对象在预付款核销时为空】
     * @param cshWriteOffList 核销集合
     * @return void
     * @author ngls.luhui 2019-03-08 16:25
     * @version 1.0
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void postTransaction(IRequest iRequest, Long transactionHeaderId, CshDocPayment cshDocPayment,
                    List<CshWriteOff> cshWriteOffList) {
        // 1.0 前置校验
        CshTransactionHeader cshTransactionHeader = cshTransactionHeaderMapper
                        .select(CshTransactionHeader.builder().transactionHeaderId(transactionHeaderId).build()).get(0);
        CshTransactionLine cshTransactionLine = cshTransactionLineMapper.queryTrxLine(transactionHeaderId).get(0);
        // 已完全核销
        if (CshWriteOff.WRITE_OFF_FLAG_C.equals(cshTransactionHeader.getWriteOffFlag())) {
            throw new CshTransactionException(CshTransactionException.WRITE_OFF_COMPLETED_ERROR,
                            CshTransactionException.WRITE_OFF_COMPLETED_ERROR, null);
        }
        // 已完全退款
        if (CshTransactionHeader.RETURNED_FLAG_C.equals(cshTransactionHeader.getReturnedFlag())) {
            throw new CshTransactionException(CshTransactionException.RETURNED_COMPLETED_ERROR,
                            CshTransactionException.RETURNED_COMPLETED_ERROR, null);
        }
        // 已反冲
        if (CshTransactionHeader.REVERSED_FLAG_R.equals(cshTransactionHeader.getReversedFlag())) {
            throw new CshTransactionException(CshTransactionException.REVERSED_ERROR,
                            CshTransactionException.REVERSED_ERROR, null);
        }
        // 检验是否已过账
        if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                        && CshTransactionHeader.POSTED_FLAG_Y.equals(cshTransactionHeader.getPostedFlag())) {
            throw new CshTransactionException(CshTransactionException.TRX_POSTED_ERROR,
                            CshTransactionException.TRX_POSTED_ERROR, null);
        }
        // 判断期间是否打开
        if (!GldPeriod.STATUS_CODE_O.equals(gldAePeriodStatusMapper.checkPeriodOpen(
                        cshTransactionHeader.getPeriodName(), cshTransactionHeader.getAccEntityId()))) {
            throw new CshTransactionException(CshTransactionException.PERIOD_NOT_OPEN_ERROR,
                            CshTransactionException.PERIOD_NOT_OPEN_ERROR, null);
        }

        // 2.0 特例处理
        if (CshTransactionHeader.TRX_CATEGORY_BUSINESS.equals(cshTransactionHeader.getTransactionCategory())) {
            // 经营类 调用核销核心逻辑
            cshWriteOffService.executePayment(iRequest, cshDocPayment, cshWriteOffList, cshTransactionHeader,
                            cshTransactionLine);
        } else if (CshTransactionHeader.TRX_CATEGORY_MISCELLANEOUS
                        .equals(cshTransactionHeader.getTransactionCategory())) {
            // 杂项类需要校验分配行借贷平衡
            // TODO
            // 更新凭证gld_interface_flag
            // TODO
        }

        // 3.0 创建现金流量记录
        // TODO

        // 4.0 校验凭证借贷平衡
        // TODO

        // 6.0 校验合同资金计划行
        String validateMsg =
                        conCashTrxPmtScheduleLnService.validateConSchedule(cshTransactionLine.getTransactionLineId());
        if (StringUtils.isNotEmpty(validateMsg)) {
            throw new CshTransactionException(validateMsg, validateMsg, null);
        }
    }

    /**
     * 现金事务头录入
     *
     * @param iRequest 请求
     * @param transactionType 现金事物类型(PAYMENT,PREPAYMENT)
     * @param moCshTrxClassId 现金事物分类ID
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @param employeeId 经办人，源单据头上员工
     * @param description 描述
     * @param enabledWriteOffFlag 是否启用核销
     * @param transactionDate 交易日期
     * @param periodName 期间
     * @param paymentMethodId 付款方式ID
     * @param transactionCategory 现金事物类别(BUSINESS,MISCELLANEOUS)
     * @param postedFlag 过账标志
     * @param reversedFlag 反冲标志
     * @param reversedDate 反冲日期
     * @param returnedFlag 退款标志(R退款事物\Y部分退款\C完全退款\N未退款)
     * @param writeOffFlag 核销标志（C完全核销\Y部分核销\N未核销）
     * @param writeOffCompletedDate 核销日期
     * @param sourceHeaderId 反冲、退款的来源现金事物头ID
     * @param gldInterfaceFlag 总账标志（N未入总账\P可入总账）
     * @param sourcePaymentHeaderId 来源付款现金事物ID
     * @param ebankingFlag 网银标志
     * @param documentCategory 单据类别
     * @param documentTypeId 单据类型ID
     * @param paymentUsedeId 付款用途ID
     * @param payeeCategory 收款方类型
     * @param payeeId 收款方
     * @param currencyCode 币种
     * @param bankAccountId 付款方银行账户
     * @Author Tagin
     * @Date 2019-03-19 19:53
     * @Return com.hand.hec.csh.dto.CshTransactionHeader
     * @Version 1.0
     **/
    @Override
    public CshTransactionHeader insertTrxHeader(IRequest iRequest, String transactionType, Long moCshTrxClassId,
                    Long companyId, Long accEntityId, Long employeeId, String description, String enabledWriteOffFlag,
                    Date transactionDate, String periodName, Long paymentMethodId, String transactionCategory,
                    String postedFlag, String reversedFlag, Date reversedDate, String returnedFlag, String writeOffFlag,
                    Date writeOffCompletedDate, Long sourceHeaderId, String gldInterfaceFlag,
                    Long sourcePaymentHeaderId, String ebankingFlag, String documentCategory, Long documentTypeId,
                    Long paymentUsedeId, String payeeCategory, Long payeeId, String currencyCode, Long bankAccountId) {
        CshTransactionHeader cshTransactionHeader = new CshTransactionHeader();
        FndCompany fndCompany = fndCompanyService.getCompany(iRequest, companyId);
        String trxNumber = fndCodingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_CSH_TRANSACTION, null,
                        fndCompany.getMagOrgId(), companyId, accEntityId);
        if (StringUtils.isEmpty(trxNumber)) {
            throw new CshTransactionException(CshTransactionException.TRX_CODING_RULE_ERROR,
                            CshTransactionException.TRX_CODING_RULE_ERROR, null);
        }
        cshTransactionHeader.setTransactionNum(trxNumber);
        cshTransactionHeader.setTransactionType(transactionType);
        cshTransactionHeader.setMoCshTrxClassId(moCshTrxClassId);
        cshTransactionHeader.setCompanyId(companyId);
        cshTransactionHeader.setAccEntityId(accEntityId);
        cshTransactionHeader.setEmployeeId(employeeId);
        cshTransactionHeader.setDescription(description);
        cshTransactionHeader.setEnabledWriteOffFlag(enabledWriteOffFlag);
        cshTransactionHeader.setTransactionDate(transactionDate);
        cshTransactionHeader.setTransactionDateTz(transactionDate);
        cshTransactionHeader.setTransactionDateLtz(transactionDate);
        cshTransactionHeader.setPeriodName(periodName);
        cshTransactionHeader.setPaymentMethodId(paymentMethodId);
        cshTransactionHeader.setTransactionCategory(transactionCategory);
        cshTransactionHeader.setPostedFlag(postedFlag);
        cshTransactionHeader.setReversedFlag(reversedFlag);
        cshTransactionHeader.setReversedDate(reversedDate);
        cshTransactionHeader.setReversedDateTz(
                        reversedDate == null ? null : new Timestamp(DateUtils.getTimeStamp(reversedDate)));
        cshTransactionHeader.setReversedDateLtz(
                        reversedDate == null ? null : new Timestamp(DateUtils.getTimeStamp(reversedDate)));
        cshTransactionHeader.setReturnedFlag(returnedFlag);
        cshTransactionHeader.setWriteOffFlag(writeOffFlag);
        cshTransactionHeader.setWriteOffCompletedDate(writeOffCompletedDate);
        cshTransactionHeader.setWriteOffCompletedDateTz(writeOffCompletedDate == null ? null
                        : new Timestamp(DateUtils.getTimeStamp(writeOffCompletedDate)));
        cshTransactionHeader.setWriteOffCompletedDateLtz(writeOffCompletedDate == null ? null
                        : new Timestamp(DateUtils.getTimeStamp(writeOffCompletedDate)));
        cshTransactionHeader.setSourceHeaderId(sourceHeaderId);
        cshTransactionHeader.setGldInterfaceFlag(gldInterfaceFlag);
        cshTransactionHeader.setSourcePaymentHeaderId(sourcePaymentHeaderId);
        cshTransactionHeader.setEbankingFlag(ebankingFlag);
        cshTransactionHeader.setDocumentCategory(documentCategory);
        cshTransactionHeader.setDocumentTypeId(documentTypeId);
        cshTransactionHeader.setPaymentUsedeId(paymentUsedeId);
        cshTransactionHeader.setPayeeCategory(payeeCategory);
        cshTransactionHeader.setPayeeId(payeeId);
        cshTransactionHeader.setCurrencyCode(currencyCode);
        cshTransactionHeader.setBankAccountId(bankAccountId);
        return self().insertSelective(iRequest, cshTransactionHeader);
    }

    @Override
    public BigDecimal getPrepaymentReturnedAmount(IRequest request, Long sourceHeaderId) {
        return cshTransactionHeaderMapper.getPrepaymentReturnedAmount(sourceHeaderId);
    }


    /**
     * <p>
     * 付款反冲主页查询
     * </p>
     *
     * @param request
     * @param pageNum
     * @param pageSize
     * @param docNumber 单据编号
     * @param accEntityId 核算主体ID
     * @param payeeCategory 收款方类别
     * @param payeeId 收款方ID
     * @param currencyCode 币种
     * @param transactionNum 现金事务编号
     * @param transactionDateFrom 现金事务日期从
     * @param transactionDateTo 现金事务日期到
     * @param paymentMethodId 付款方式ID
     * @param paymentEmployeeName 支付员工名称
     * @param agentEmployeeName 代理员工名称
     * @param transactionAmountFrom 付款金额从
     * @param transactionAmountTo 付款金额到
     * @return List<Map>
     * @author yang.duan 2019/5/23 10:41
     **/
    @Override
    public List<Map> queryPaymentReverse(IRequest request, int pageNum, int pageSize, String docNumber,
                    Long accEntityId, String payeeCategory, Long payeeId, String currencyCode, String transactionNum,
                    String transactionDateFrom, String transactionDateTo, Long paymentMethodId,
                    String paymentEmployeeName, String agentEmployeeName, String transactionAmountFrom,
                    String transactionAmountTo) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> reverseList = cshTransactionHeaderMapper.queryPaymentReverse(accEntityId, payeeCategory, payeeId,
                        currencyCode, transactionNum, transactionDateFrom, transactionDateTo, paymentMethodId,
                        paymentEmployeeName, agentEmployeeName, transactionAmountFrom, transactionAmountTo);
        List<Map> list = new ArrayList<>();
        if (reverseList != null && !reverseList.isEmpty()) {
            for (Map map : reverseList) {
                // 获取单据类别
                String docCategory = getDocCategory(request, (Long) map.get("transactionHeaderId"));
                map.put("docCategory", docCategory);

                // 获取单据类别描述
                String docCategoryName =
                                codeService.getCodeMeaningByValue(request, "WFL_WORKFLOW_CATEGORY", docCategory);
                map.put("docCategoryName", docCategoryName);

                // 获取单据ID
                Long docId = getDocId(request, (Long) map.get("transactionHeaderId"));
                if (docId != null) {
                    map.put("docId", docId);
                }
                // 获取单据编号
                String number = getDocNumber(request, (Long) map.get("transactionHeaderId"));
                map.put("docNumber", number);
                if (docNumber != null) {
                    if (docNumber.contains(number)) {
                        list.add(map);
                    }
                } else {
                    list.add(map);
                }
            }
            reverseList.retainAll(list);
        }
        return reverseList;
    }

    @Override
    public String getDocNumber(IRequest request, Long transactionHeaderId) {
        String docNumber = "";
        CshTransactionHeader transactionHeader = new CshTransactionHeader();
        transactionHeader.setTransactionHeaderId(transactionHeaderId);
        transactionHeader = self().selectByPrimaryKey(request, transactionHeader);
        if ("PAYMENT".equals(transactionHeader.getTransactionType())) {
            if (("R".equals(transactionHeader.getReturnedFlag()))
                            || ("R".equals(transactionHeader.getReversedFlag()))) {
                docNumber = getDocNumber(request, transactionHeader.getSourceHeaderId());
            } else {
                List<CshWriteOff> cshWriteOffList = cshWriteOffMapper.getDocumentHeaderId(transactionHeaderId);
                if (cshWriteOffList != null && !cshWriteOffList.isEmpty()) {
                    for (CshWriteOff writeOff : cshWriteOffList) {
                        if ("PAYMENT_EXPENSE_REPORT".equals(writeOff.getWriteOffType())) {
                            ExpReportHeader reportHeader =
                                            expReportHeaderMapper.selectByPrimaryKey(writeOff.getDocumentHeaderId());
                            if (reportHeader != null) {
                                docNumber = docNumber + reportHeader.getExpReportNumber() + ",";
                            }
                        } else if ("PAYMENT_PREPAYMENT".equals(writeOff.getWriteOffType())) {
                            CshPaymentRequisitionHd cshPaymentRequisitionHd = cshPaymentRequisitionHdMapper
                                            .selectByPrimaryKey(writeOff.getDocumentHeaderId());
                            if (cshPaymentRequisitionHd != null) {
                                docNumber = docNumber + cshPaymentRequisitionHd.getRequisitionNumber() + ",";
                            }
                        } else if ("ACP_PAYMENT".equals(writeOff.getWriteOffType())
                                        || "ACP_PREPAYMENT".equals(writeOff.getWriteOffType())) {
                            docNumber = docNumber + acpRequisitionHdMapper.getDocNumber(transactionHeaderId) + ",";
                        }
                    }
                }
            }
        }else if("PREPAYMENT".equals(transactionHeader.getTransactionType())){
            docNumber = getDocNumber(request,transactionHeader.getSourcePaymentHeaderId());
        }
        if (!docNumber.isEmpty() && docNumber.endsWith(",")) {
            docNumber = docNumber.substring(0, docNumber.length() - 1);
        }
        return docNumber;
    }

    @Override
    public Long getDocId(IRequest request, Long transactionHeaderId) {
        Long docId = null;
        CshTransactionHeader transactionHeader = new CshTransactionHeader();
        transactionHeader.setTransactionHeaderId(transactionHeaderId);
        transactionHeader = self().selectByPrimaryKey(request, transactionHeader);
        if ("PAYMENT".equals(transactionHeader.getTransactionType())) {
            if (("R".equals(transactionHeader.getReturnedFlag()))
                            || ("R".equals(transactionHeader.getReversedFlag()))) {
                docId = getDocId(request, transactionHeader.getSourceHeaderId());
            } else {
                CshWriteOff writeOff = cshWriteOffMapper.getCshWriteOffByTransaction(transactionHeaderId);
                if (writeOff != null) {
                    if ("PAYMENT_EXPENSE_REPORT".equals(writeOff.getWriteOffType())) {
                        Long reportId = cshWriteOffMapper.selectByPrimaryKey(writeOff.getWriteOffId())
                                        .getDocumentHeaderId();
                        ExpReportHeader reportHeader = expReportHeaderMapper.selectByPrimaryKey(reportId);
                        if (reportHeader != null) {
                            docId = reportHeader.getExpReportHeaderId();
                        }

                    } else if ("PAYMENT_PREPAYMENT".equals(writeOff.getWriteOffType())) {

                        docId = cshPaymentRequisitionLnMapper.getDocId(transactionHeaderId);

                    } else if ("ACP_PAYMENT".equals(writeOff.getWriteOffType())
                                    || "ACP_PREPAYMENT".equals(writeOff.getWriteOffType())) {

                        docId = acpRequisitionLnMapper.getDocId(transactionHeaderId);
                    }
                }
            }
        } else {
            if ("PREPAYMENT".equals(transactionHeader.getTransactionType())) {
                docId = getDocId(request, transactionHeader.getSourcePaymentHeaderId());
            }
        }
        return docId;
    }

    private String getDocCategory(IRequest request, Long transactionHeaderId) {
        String docCategory = "";
        CshTransactionHeader transactionHeader = new CshTransactionHeader();
        transactionHeader.setTransactionHeaderId(transactionHeaderId);
        transactionHeader = self().selectByPrimaryKey(request, transactionHeader);
        if ("PAYMENT".equals(transactionHeader.getTransactionType())) {
            if (("R".equals(transactionHeader.getReturnedFlag()))
                            || ("R".equals(transactionHeader.getReversedFlag()))) {
                docCategory = getDocCategory(request, transactionHeader.getSourceHeaderId());
            } else {
                CshWriteOff writeOff = cshWriteOffMapper.getCshWriteOffByTransaction(transactionHeaderId);
                if (writeOff != null) {
                    if ("PAYMENT_EXPENSE_REPORT".equals(writeOff.getWriteOffType())) {
                        docCategory = ExpReportHeader.EXP_REPORT;

                    } else if ("PAYMENT_PREPAYMENT".equals(writeOff.getWriteOffType())) {
                        docCategory = "PAYMENT_REQUISITION";

                    } else if ("ACP_PAYMENT".equals(writeOff.getWriteOffType())
                                    || "ACP_PREPAYMENT".equals(writeOff.getWriteOffType())) {
                        docCategory = "ACP_REQUISITION";
                    }
                }
            }
        } else {
            if ("PREPAYMENT".equals(transactionHeader.getTransactionType())) {
                docCategory = getDocCategory(request, transactionHeader.getSourcePaymentHeaderId());
            }
        }
        return docCategory;
    }


    /**
     * <p>
     * 付款反冲明细页面付款信息查询
     * </p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map>
     * @author yang.duan 2019/5/24 10:35
     **/
    @Override
    public List<Map> cshTransactionInfoQuery(IRequest request, Long transactionHeaderId) {
        return cshTransactionHeaderMapper.cshTransactionInfoQuery(request, transactionHeaderId);
    }


    /**
     * <p>
     * 付款查询页面信息
     * </p>
     *
     * @param request
     * @param accEntityId 核算主体ID
     * @param agentEmployeeName 代理员工名称
     * @param bankAccountId 银行账户ID
     * @param contactNumber 合同编号
     * @param currencyCode 币种
     * @param docNumber 单据编号
     * @param docCategory 单据类别CODE
     * @param transactionAmountFrom 交易金额从
     * @param transactionAmountTo 交易金额到
     * @param payeeCategory 收款方类别
     * @param payeeId 收款方ID
     * @param paymentEmployeeName 付款员工
     * @param paymentMethodId 付款方式ID
     * @param sourceTransactionNum 来源现金事务编号
     * @param transactionDateFrom 交易日期从
     * @param transactionDateTo 交易日期到
     * @param transactionNum 现金事务编号
     * @param transactionType 现金事务类型
     * @param pageNum
     * @param pageSize
     * @return List<Map>
     * @author yang.duan 2019/5/29 16:19
     **/
    @Override
    public List<Map> cshTransactionQuery(IRequest request, Long accEntityId, String transactionDateFrom,
                    String transactionDateTo, String payeeCategory, Long payeeId, String currencyCode,
                    String transactionNum, Long bankAccountId, Long paymentMethodId, String paymentEmployeeName,
                    String agentEmployeeName, String contactNumber, String transactionType,
                    String transactionAmountFrom, String transactionAmountTo, String docNumber, String docCategory,
                    String sourceTransactionNum, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> mapList = cshTransactionHeaderMapper.cshTransactionQuery(accEntityId, transactionDateFrom,
                        transactionDateTo, payeeCategory, payeeId, currencyCode, transactionNum, bankAccountId,
                        paymentMethodId, paymentEmployeeName, agentEmployeeName, contactNumber, transactionType,
                        transactionAmountFrom, transactionAmountTo, sourceTransactionNum);
        List<Map> list = new ArrayList<>();
        if (mapList != null && !mapList.isEmpty()) {
            for (Map TransactionMap : mapList) {
                // 获取单据类别
                String docTypeCode = getDocCategory(request, (Long) TransactionMap.get("transactionHeaderId"));
                TransactionMap.put("docCategory", docTypeCode);

                // 获取单据类别描述
                String docCategoryName =
                                codeService.getCodeMeaningByValue(request, "WFL_WORKFLOW_CATEGORY", docCategory);
                TransactionMap.put("docCategoryName", docCategoryName);

                // 获取单据ID
                Long docId = getDocId(request, (Long) TransactionMap.get("transactionHeaderId"));
                if (docId != null) {
                    TransactionMap.put("docId", docId);
                }
                // 获取单据编号
                String number = getDocNumber(request, (Long) TransactionMap.get("transactionHeaderId"));
                TransactionMap.put("docNumber", number);
                if ((docCategory == null || docCategory.isEmpty()) && (docNumber == null || docNumber.isEmpty())) {
                    list.add(TransactionMap);
                } else if (docCategory != null && !docCategory.isEmpty() && docNumber != null && !docNumber.isEmpty()) {
                    if (docCategory.equals(docTypeCode) && docNumber.equals(number)) {
                        list.add(TransactionMap);
                    }
                } else {
                    if (docCategory != null && !docCategory.isEmpty() && (docNumber == null || docNumber.isEmpty())) {
                        if (docCategory.equals(docTypeCode)) {
                            list.add(TransactionMap);
                        }
                    } else {
                        if (docNumber.equals(number)) {
                            list.add(TransactionMap);
                        }
                    }
                }
            }
            mapList.retainAll(list);
        }
        return mapList;
    }

    /**
     * <p>
     * 付款反冲
     * </p>
     *
     * @param request
     * @param dto
     * @return void
     * @author yang.duan 2019/5/30 18:02
     **/
    @Override
    public void postReverseTransaction(IRequest request, List<CshTransactionHeader> dto) {
        if (dto != null && !dto.isEmpty()) {
            Date reversedDate = DateUtils.str2Date((String) dto.get(0).getInnerMap().get("reverseDate"), null);
            for (CshTransactionHeader header : dto) {
                header = self().selectByPrimaryKey(request, header);
                CshTransactionLine line = new CshTransactionLine();
                line.setTransactionHeaderId(header.getTransactionHeaderId());
                List<CshTransactionLine> lineList = cshTransactionLineService.select(request, line, 1, 0);
                if (lineList != null && !lineList.isEmpty()) {
                    line = lineList.get(0);
                }
                cshTransactionService.postReverseTransaction(request, header, line, reversedDate);
            }
        }
    }

    @Override
	public List<Map> queryPrePayForReverse(IRequest request,String payeeCategory,Long payeeId,
			String currencyCode,String transactionNum,String transactionDateFrom,
			String transactionDateTo,String requisitionNumber, String contractNumber,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
    	return cshTransactionHeaderMapper.queryPrePayForReverse(payeeCategory, payeeId, currencyCode, transactionNum, transactionDateFrom, transactionDateTo, requisitionNumber, contractNumber);
	}

	@Override
	public List<Map> cshTransInfoQuery(IRequest request, Long transactionHeaderId) {
		return cshTransactionHeaderMapper.cshTransInfoQuery(transactionHeaderId);
	}
}
