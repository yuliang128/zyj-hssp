package com.hand.hec.acp.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.IPromptService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.*;
import com.hand.hec.acp.exception.AcpRequisitionAuditException;
import com.hand.hec.acp.exception.AcpRequisitionException;
import com.hand.hec.acp.mapper.AcpRequisitionAccountMapper;
import com.hand.hec.acp.mapper.AcpRequisitionHdMapper;
import com.hand.hec.acp.service.IAcpReqAuditErrorLogService;
import com.hand.hec.acp.service.IAcpRequisitionAccountService;
import com.hand.hec.acp.service.IAcpRequisitionHdService;
import com.hand.hec.acp.service.IAcpRequisitionLnService;
import com.hand.hec.csh.mapper.CshPaymentRequisitionHdMapper;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.dto.GldSegment;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.gld.service.IGlAccountEntryService;
import com.hand.hec.gld.service.IGldMappingConditionService;
import com.hand.hec.gld.service.IGldSobSegmentService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpRequisitionAccountServiceImpl extends BaseServiceImpl<AcpRequisitionAccount>
                implements IAcpRequisitionAccountService {

    @Autowired
    private AcpRequisitionAccountMapper acpRequisitionAccountMapper;

    @Autowired
    private ISysParameterService iSysParameterService;

    @Autowired
    private IAcpRequisitionHdService iAcpRequisitionHdService;

    @Autowired
    CshPaymentRequisitionHdMapper paymentRequisitionHdMapper;

    @Autowired
    private IExpDocumentHistoryService iExpDocumentHistoryService;

    @Autowired
    private GldAccountingEntityMapper gldAccountingEntityMapper;

    @Autowired
    private AcpRequisitionHdMapper acpRequisitionHdMapper;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private IAcpRequisitionLnService acpRequisitionLnService;

    @Autowired
    GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IGldMappingConditionService gldMappingConditionService;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @Autowired
    private IPromptService promptService;

    @Autowired
    private IAcpReqAuditErrorLogService logService;

    @Autowired
    private IGlAccountEntryService glAccountEntryService;


    /**
     * 获取借款审核凭证
     *
     * @author Tagin
     * @date 2019-04-30 15:51
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param requisitionLnsId 付款申请单行ID
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @return java.util.List<com.hand.hec.acp.dto.AcpRequisitionAccount>
     * @version 1.0
     **/
    @Override
    public AcpRequisitionAccount queryAccount(Long transactionLineId, String usageCode, Long requisitionLnsId,
                    String drFlag, String crFlag) {
        List<AcpRequisitionAccount> accounts = acpRequisitionAccountMapper.queryAccount(transactionLineId, usageCode,
                        requisitionLnsId, drFlag, crFlag);
        return CollectionUtils.isNotEmpty(accounts) ? accounts.get(0) : null;
    }

    /**
     * 付款申请单审核借贷平衡校验
     * 
     * @param request
     * @param record
     * @author zhongyu 2019-5-9
     * @return void
     */
    private void accountBalanceCheck(IRequest request, AcpRequisitionHd record) throws AcpRequisitionException {
        List<Map> mapList = acpRequisitionAccountMapper.selectGroupByLine(record.getRequisitionHdsId());
        for (Map map : mapList) {
            // 查询借贷方相减的差额
            List<Map> amountList = acpRequisitionAccountMapper.selectForEnterAmount((Long) map.get("requisitionLineId"),
                            (Long) map.get("accEntityId"));
            BigDecimal enteredAmountBal = (BigDecimal) amountList.get(0).get("enterAmountBal");
            BigDecimal functionalAmountBal = (BigDecimal) amountList.get(0).get("functionalAmountBal");
            if ((!enteredAmountBal.equals(BigDecimal.ZERO)) || (!functionalAmountBal.equals(BigDecimal.ZERO))) {
                throw new AcpRequisitionException(AcpRequisitionException.LOAN_VOUCHER_NOT_BALANCE, null);
            }
        }
    }

    @Override
    @Transactional
    public List<AcpRequisitionHd> auditAcpReq(IRequest request, List<AcpRequisitionHd> list)
                    throws AcpRequisitionException {
        String recheckFlag = iSysParameterService.queryParamValueByCode("ACP_REQUISITION_RECHECK", null, null, null,
                        null, null, null, null);
        if (recheckFlag.isEmpty()) {
            recheckFlag = "N";
        }
        for (AcpRequisitionHd record : list) {
            AcpRequisitionHd acpRequisitionHd = iAcpRequisitionHdService.selectByPrimaryKey(request, record);

            if (!acpRequisitionHd.getJeCreationStatus().equals("SUCCESS")) {
                throw new AcpRequisitionException(AcpRequisitionException.CREATE_ACCOUNT_FIRST, null);
            }
            // switch (acpRequisitionHd.getJeCreationStatus()) {
            // case "SUCCESS":
            // break;
            // default:
            //
            // }

            // 借贷平衡校验
            accountBalanceCheck(request, record);

            // 变更单据状态

            acpRequisitionHd.setAuditDate(new Date());
            acpRequisitionHd.setAuditFlag("Y");
            acpRequisitionHd.setAuditDateLtz(new Timestamp(System.currentTimeMillis()));
            acpRequisitionHd.setAuditDateTz(new Timestamp(System.currentTimeMillis()));

            iAcpRequisitionHdService.updateByPrimaryKey(request, acpRequisitionHd);

            // 变更凭证为P
            acpRequisitionAccountMapper.updateByLineId(acpRequisitionHd.getRequisitionHdsId(), "P");

            // 触发事件，入分录表
            // EvtEventRecord evtEventRecord = new EvtEventRecord();
            //// evtEventRecord.setE
            glAccountEntryService.headerGlAccountEntry(request, GlAccountEntry.RULE_TYPE_ACP_REQ,
                            acpRequisitionHd.getRequisitionHdsId());


            // 单据历史
            iExpDocumentHistoryService.insertDocumentHistory(request, AcpRequisitionHd.ACP_REQUISITION,
                            acpRequisitionHd.getRequisitionHdsId(), ExpDocumentHistory.STATUS_AUDIT,
                            request.getEmployeeId(), null);
        }

        return list;

    }

    @Override
    public void deleteByAcpLineId(Long requisitionLnsId) {
        acpRequisitionAccountMapper.deleteByAcpLineId(requisitionLnsId);
    }

    @Override
    public boolean createAcpRequisitionAccount(IRequest request, Long accEntityId, Date journalDate,
                    List<AcpRequisitionAccountTemp> accountTempList, List<AcpReqCurrencyTemp> currencyTempList) {
        String requisitionNumber = "";
        String periodName = null;
        String errCode = "";
        /**
         * TODO:不确定获取是否正确
         */
        String currencyCode = gldAccountingEntityMapper.queryFuncCurrencyByEntity(accEntityId);
        List<Long> headerIds = accountTempList.parallelStream().map(AcpRequisitionAccountTemp::getRequisitionHdsId)
                        .distinct().collect(Collectors.toList());

        List<AcpReqCurrencyTemp> newList = currencyTempList.parallelStream().filter(record -> {
            return record.getExchangeRate() == null && !record.getCurrencyCode().equals(currencyCode);
        }).collect(Collectors.toList());

        if (!newList.isEmpty()) {
            throw new AcpRequisitionAuditException(AcpRequisitionAuditException.NEED_EXCHANGE_RATE, null);
        }

        for (Long headerId : headerIds) {
            AcpRequisitionHd header = acpRequisitionHdMapper.queryForAudit(headerId);
            requisitionNumber = header.getRequisitionNumber();
            periodName = gldPeriodService.getPeriodName(request, journalDate, accEntityId, "O");
            if (periodName == null) {
                throw new AcpRequisitionAuditException(AcpRequisitionAuditException.PERIOD_NAME, null);
            }
            switch (header.getPaymentType()) {
                case AcpMoPayReqType.PAYMENT_TYPE_SPORADIC:
                    this.executeAcpSporadicAccount(request, header.getRequisitionHdsId(), journalDate, periodName,
                                    requisitionNumber, currencyTempList);
                    break;
                case AcpMoPayReqType.PAYMENT_TYPE_CONTRACT:
                    this.executeAcpSporadicAccount(request, header.getRequisitionHdsId(), journalDate, periodName,
                                    requisitionNumber, currencyTempList);
                    break;
                case AcpMoPayReqType.PAYMENT_TYPE_REPORT:
                    this.executeAcpExpAccount(request, header.getRequisitionHdsId(), journalDate, periodName,
                                    requisitionNumber, currencyTempList);
                    break;
                default:
                    break;
            }
        }
        accountTempList = new ArrayList<>();
        currencyTempList = new ArrayList<>();
        return true;
    }

    /**
     * 零星付款生成审核凭证
     *
     * @param requisitionHdsId 付款申请单头ID
     * @param journalDate 期间
     * @param periodName 期间名
     * @author guiyuting 2019-05-30 10:34
     * @return
     */
    private void executeAcpSporadicAccount(IRequest request, Long requisitionHdsId, Date journalDate, String periodName,
                    String requisitionNumber, List<AcpReqCurrencyTemp> currencyTempList) {
        List<AcpRequisitionLn> lines = acpRequisitionLnService.queryForCreateAccount(requisitionHdsId);
        List<AcpRequisitionAccount> acpRequisitionAccountList = new ArrayList<>();
        for (AcpRequisitionLn line : lines) {
            // 一、删除已生成的凭证
            self().deleteByAcpLineId(line.getRequisitionLnsId());
            // 分配的现金事物为“预付款”
            if ("PREPAYMENT".equals(line.getCshTransactionTypeCode())) {
                // 生成凭证借方
                GldAccountingEntity defaultAccEntity =
                                gldAccountingEntityMapper.queryDefaultAccEntity(line.getCompanyId());
                String standardCurrency =
                                gldAccountingEntityMapper.queryFuncCurrencyByEntity(defaultAccEntity.getAccEntityId());
                this.createAcpReqDrAccount(request, line, requisitionHdsId, journalDate, periodName, requisitionNumber,
                                currencyTempList, acpRequisitionAccountList, defaultAccEntity, standardCurrency,
                                "PAYMENT_REQUISITION");
                // 二、生成凭证贷方
                this.createAcpReqCrAccount(request, line, requisitionHdsId, journalDate, periodName, requisitionNumber,
                                currencyTempList, acpRequisitionAccountList, defaultAccEntity, standardCurrency,
                                "PAYMENT_REQUISITION_CLEARING");
            } else {
                // 分配的现金事物为非预付款
                // 生成凭证借方
                GldAccountingEntity defaultAccEntity =
                                gldAccountingEntityMapper.queryDefaultAccEntity(line.getCompanyId());
                String standardCurrency =
                                gldAccountingEntityMapper.queryFuncCurrencyByEntity(defaultAccEntity.getAccEntityId());
                this.createAcpReqDrAccount(request, line, requisitionHdsId, journalDate, periodName, requisitionNumber,
                                currencyTempList, acpRequisitionAccountList, defaultAccEntity, standardCurrency,
                                "PAY_REQUISITION");
                // 二、生成凭证贷方
                this.createAcpReqCrAccount(request, line, requisitionHdsId, journalDate, periodName, requisitionNumber,
                                currencyTempList, acpRequisitionAccountList, defaultAccEntity, standardCurrency,
                                "PAY_REQUISITION_CLEARING");
            }

            for (AcpRequisitionAccount acpRequisitionAccount : acpRequisitionAccountList) {
                this.insAcpRequisitionAccounts(request, acpRequisitionAccount, requisitionHdsId);
            }

            // 三、更新生成凭证状态
            AcpRequisitionHd acpRequisitionHd = AcpRequisitionHd.builder().requisitionHdsId(requisitionHdsId)
                            .jeCreationStatus("SUCCESS").build();
            acpRequisitionHd.setLastUpdateDate(DateUtils.getCurrentDate());
            acpRequisitionHd.setLastUpdatedBy(request.getUserId());
            acpRequisitionHdMapper.updateJeCreationStatus(acpRequisitionHd);
        }
    }

    /**
     * 报销单结算付款申请单审核凭证
     *
     * @param request
     * @param requisitionHdsId 付款申请单头ID
     * @param journalDate 期间
     * @param periodName 期间名
     * @author guiyuting 2019-05-08 15:51
     * @return
     */
    private void executeAcpExpAccount(IRequest request, Long requisitionHdsId, Date journalDate, String periodName,
                    String requisitionNumber, List<AcpReqCurrencyTemp> currencyTempList) {
        List<AcpRequisitionLn> lines = acpRequisitionLnService.queryForCreateAccount(requisitionHdsId);
        /*
         * 报销单结算： A.分配的现金事物为“预付款" 审核凭证： DR:PAYMENT_REQUISITION-借款单科目 CR:PAYMENT_REQUISITION_CLEARING-借款单清算科目
         * B.分配的现金事物为“付款” 此类付款申请单不需要审核，配置上需要注意，因为这就是3.0的冻结再支付
         */
        List<AcpRequisitionAccount> acpRequisitionAccountList = new ArrayList<>();
        for (AcpRequisitionLn line : lines) {
            // 一、删除已生成的凭证
            self().deleteByAcpLineId(line.getRequisitionLnsId());
            // 分配的现金事物为“预付款”
            if ("PREPAYMENT".equals(line.getCshTransactionTypeCode())) {
                // 生成凭证借方
                GldAccountingEntity defaultAccEntity =
                                gldAccountingEntityMapper.queryDefaultAccEntity(line.getCompanyId());
                String standardCurrency =
                                gldAccountingEntityMapper.queryFuncCurrencyByEntity(defaultAccEntity.getAccEntityId());
                this.createAcpReqDrAccount(request, line, requisitionHdsId, journalDate, periodName, requisitionNumber,
                                currencyTempList, acpRequisitionAccountList, defaultAccEntity, standardCurrency,
                                "PAYMENT_REQUISITION");
                // 二、生成凭证贷方
                this.createAcpReqCrAccount(request, line, requisitionHdsId, journalDate, periodName, requisitionNumber,
                                currencyTempList, acpRequisitionAccountList, defaultAccEntity, standardCurrency,
                                "PAYMENT_REQUISITION_CLEARING");
            } else if ("PAYMENT".equals(line.getCshTransactionTypeCode())) {
                // 分配的现金事物为非预付款
                // 此类付款申请单不需要审核，配置上需要注意，因为这就是3.0的冻结再支付
            }
        }

        for (AcpRequisitionAccount acpRequisitionAccount : acpRequisitionAccountList) {
            this.insAcpRequisitionAccounts(request, acpRequisitionAccount, requisitionHdsId);
        }

        // 三、更新生成凭证状态
        AcpRequisitionHd acpRequisitionHd = AcpRequisitionHd.builder().requisitionHdsId(requisitionHdsId)
                        .jeCreationStatus("SUCCESS").build();
        acpRequisitionHd.setLastUpdateDate(DateUtils.getCurrentDate());
        acpRequisitionHd.setLastUpdatedBy(request.getUserId());
        acpRequisitionHdMapper.updateJeCreationStatus(acpRequisitionHd);

    }

    /**
     * 创建付款申请单借方凭证
     *
     * @param request
     * @param line 付款申请单行信息
     * @param requisitionHdsId 付款申请单头ID
     * @param journalDate 期间
     * @param periodName 期间名
     * @param currencyTempList 币种临时信息
     * @author guiyuting 2019-05-09 10:48
     * @return
     */
    private void createAcpReqDrAccount(IRequest request, AcpRequisitionLn line, Long requisitionHdsId, Date journalDate,
                    String periodName, String requisitionNumber, List<AcpReqCurrencyTemp> currencyTempList,
                    List<AcpRequisitionAccount> acpRequisitionAccountList, GldAccountingEntity defaultAccEntity,
                    String standardCurrency, String usageCode) {
        AcpRequisitionAccount acpRequisitionAccount = new AcpRequisitionAccount();

        BigDecimal enteredAmountDr = line.getAmount();
        Map<String, Object> map = this.getFunctionalAmount(requisitionHdsId, line.getCurrencyCode(), standardCurrency,
                        line.getAmount(), currencyTempList);
        BigDecimal functionalAmountDr = new BigDecimal(map.get("functionalAmount").toString());
        // 1.0.2 根据用途代码【PAYMENT_REQUISITION】获取科目
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        Long accountId = 0L;
        gldMappingConditions = gldMappingConditionService.accBuildPaymentReq(line.getMagOrgId().toString(),
                        line.getCompanyId().toString(), line.getPositionId().toString(),
                        line.getAccEntityId().toString(), line.getRespCenterId().toString(), line.getPayeeCategory(),
                        line.getPayeeTypeId().toString(), line.getPayeeId().toString(),
                        line.getMoCshTrxClassId().toString());
        // 通过匹配组获取借方科目
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, usageCode, line.getMagOrgId(),
                        line.getSetOfBooksId());
        if (accountId == null || accountId.intValue() == 0) {
            // 添加错误日志
            this.errLog(request, requisitionNumber, line, AcpRequisitionAuditException.ACP_GET_ACP_REQUISITION_ERROR);
            throw new AcpRequisitionAuditException(AcpRequisitionAuditException.ACP_GET_ACP_REQUISITION_ERROR, null);
        }

        // 1.0.3 取默认责任中心
        Long respCenterId = Long.parseLong(sysParameterService.queryParamValueByCode(
                        com.hand.hap.sys.constants.ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null,
                        null, defaultAccEntity.getAccEntityId(), null, null, null));
        if (respCenterId == null) {
            this.errLog(request, requisitionNumber, line, AcpRequisitionAuditException.EXP5140_RESP_CENTER_ERROR);
            throw new AcpRequisitionAuditException(AcpRequisitionAuditException.EXP5140_RESP_CENTER_ERROR, null);
        }
        acpRequisitionAccount.setRequisitionLnsId(line.getRequisitionLnsId());
        acpRequisitionAccount.setDescription(line.getHeaderDescription());
        acpRequisitionAccount.setJournalDate(journalDate);
        acpRequisitionAccount.setPeriodName(periodName);
        acpRequisitionAccount.setAccEntityId(line.getAccEntityId());
        acpRequisitionAccount.setRespCenterId(respCenterId);
        acpRequisitionAccount.setAccountId(accountId);
        acpRequisitionAccount.setCurrencyCode(line.getCurrencyCode());
        if (map.get("exchangeRateType") != null) {
            acpRequisitionAccount.setExchangeRateType(map.get("exchangeRateType").toString());
        }
        if (map.get("exchangeRate") != null) {
            acpRequisitionAccount.setExchangeRate(new BigDecimal(map.get("exchangeRate").toString()));
        }
        acpRequisitionAccount.setEnteredAmountDr(enteredAmountDr);
        acpRequisitionAccount.setFunctionalAmountDr(functionalAmountDr);
        acpRequisitionAccount.setUsageCode(usageCode);
        acpRequisitionAccount.setSetOfBooksId(line.getSetOfBooksId());
        acpRequisitionAccountList.add(acpRequisitionAccount);
    }

    /**
     * 创建付款申请单贷方凭证
     *
     * @param request
     * @param line 付款申请单行信息
     * @param requisitionHdsId 付款申请单头ID
     * @param journalDate 期间
     * @param periodName 期间名
     * @param currencyTempList 币种临时信息
     * @author guiyuting 2019-05-09 10:48
     * @return
     */
    private void createAcpReqCrAccount(IRequest request, AcpRequisitionLn line, Long requisitionHdsId, Date journalDate,
                    String periodName, String requisitionNumber, List<AcpReqCurrencyTemp> currencyTempList,
                    List<AcpRequisitionAccount> acpRequisitionAccountList, GldAccountingEntity defaultAccEntity,
                    String standardCurrency, String usageCode) {
        AcpRequisitionAccount acpRequisitionAccount = new AcpRequisitionAccount();

        BigDecimal enteredAmountCr = line.getAmount();
        Map<String, Object> map = this.getFunctionalAmount(requisitionHdsId, line.getCurrencyCode(), standardCurrency,
                        line.getAmount(), currencyTempList);
        BigDecimal functionalAmountCr = new BigDecimal(map.get("functionalAmount").toString());
        // 1.0.2 根据用途代码【PAYMENT_REQUISITION】获取科目
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        Long accountId = 0L;
        gldMappingConditions = gldMappingConditionService.accBuildPaymentReq(line.getMagOrgId().toString(),
                        line.getCompanyId().toString(), line.getPositionId().toString(),
                        line.getAccEntityId().toString(), line.getRespCenterId().toString(), line.getPayeeCategory(),
                        line.getPayeeTypeId().toString(), line.getPayeeId().toString(),
                        line.getMoCshTrxClassId().toString());
        // 通过匹配组获取借方科目
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, usageCode, line.getMagOrgId(),
                        line.getSetOfBooksId());
        if (accountId.intValue() == 0) {
            // 添加错误日志
            this.errLog(request, requisitionNumber, line, AcpRequisitionAuditException.ACP_GET_ACP_REQUISITION_ERROR);
            throw new AcpRequisitionAuditException(AcpRequisitionAuditException.ACP_GET_ACP_REQUISITION_ERROR, null);
        }

        // 1.0.3 取默认责任中心
        Long respCenterId = Long.parseLong(sysParameterService.queryParamValueByCode(
                        com.hand.hap.sys.constants.ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null,
                        null, defaultAccEntity.getAccEntityId(), null, null, null));
        if (respCenterId == null) {
            this.errLog(request, requisitionNumber, line, AcpRequisitionAuditException.EXP5140_RESP_CENTER_ERROR);
            throw new AcpRequisitionAuditException(AcpRequisitionAuditException.EXP5140_RESP_CENTER_ERROR, null);
        }

        acpRequisitionAccount.setRequisitionLnsId(line.getRequisitionLnsId());
        acpRequisitionAccount.setDescription(line.getHeaderDescription());
        acpRequisitionAccount.setJournalDate(journalDate);
        acpRequisitionAccount.setPeriodName(periodName);
        acpRequisitionAccount.setAccEntityId(line.getAccEntityId());
        acpRequisitionAccount.setRespCenterId(respCenterId);
        acpRequisitionAccount.setAccountId(accountId);
        acpRequisitionAccount.setCurrencyCode(line.getCurrencyCode());
        if (map.get("exchangeRateType") != null) {
            acpRequisitionAccount.setExchangeRateType(map.get("exchangeRateType").toString());
        }
        if (map.get("exchangeRate") != null) {
            acpRequisitionAccount.setExchangeRate(new BigDecimal(map.get("exchangeRate").toString()));
        }
        acpRequisitionAccount.setEnteredAmountCr(enteredAmountCr);
        acpRequisitionAccount.setFunctionalAmountCr(functionalAmountCr);
        acpRequisitionAccount.setSetOfBooksId(line.getSetOfBooksId());
        acpRequisitionAccount.setUsageCode(usageCode);
        acpRequisitionAccountList.add(acpRequisitionAccount);

    }

    /**
     * 付款申请凭证-录入
     *
     * @param acpRequisitionAccount
     * @author guiyuting 2019-05-09 10:39
     * @return
     */
    private AcpRequisitionAccount insAcpRequisitionAccounts(IRequest request,
                    AcpRequisitionAccount acpRequisitionAccount, Long requisitionHdsId) {
        acpRequisitionAccount.setGldInterfaceFlag(BaseConstants.NO);
        acpRequisitionAccount = self().insertSelective(request, acpRequisitionAccount);

        // 生成科目段
        Long setOfBooksId = acpRequisitionAccount.getSetOfBooksId();
        String drCrFlag = "";
        if (acpRequisitionAccount.getEnteredAmountDr() == null) {
            drCrFlag = "CR";
        } else if (acpRequisitionAccount.getEnteredAmountCr() == null) {
            drCrFlag = "DR";
        }
        GldSegment gldSegment = new GldSegment();
        gldSobSegmentService.mappingSegmentValue(acpRequisitionAccount.getAccEntityId(), setOfBooksId,
                        acpRequisitionAccount.getAccountId(), "ACP_REQ", acpRequisitionAccount.getAcpReqJeLineId(),
                        drCrFlag, requisitionHdsId, acpRequisitionAccount.getRequisitionLnsId(), null, null);
        if (gldSegment == null) {
            throw new RuntimeException("未获取到对应科目段!");
        }
        acpRequisitionAccount.setAccountSegment1(gldSegment.getSegment1());
        acpRequisitionAccount.setAccountSegment2(gldSegment.getSegment2());
        acpRequisitionAccount.setAccountSegment3(gldSegment.getSegment3());
        acpRequisitionAccount.setAccountSegment4(gldSegment.getSegment4());
        acpRequisitionAccount.setAccountSegment5(gldSegment.getSegment5());
        acpRequisitionAccount.setAccountSegment6(gldSegment.getSegment6());
        acpRequisitionAccount.setAccountSegment7(gldSegment.getSegment7());
        acpRequisitionAccount.setAccountSegment8(gldSegment.getSegment8());
        acpRequisitionAccount.setAccountSegment9(gldSegment.getSegment9());
        acpRequisitionAccount.setAccountSegment10(gldSegment.getSegment10());
        acpRequisitionAccount.setAccountSegment11(gldSegment.getSegment11());
        acpRequisitionAccount.setAccountSegment12(gldSegment.getSegment12());
        acpRequisitionAccount.setAccountSegment13(gldSegment.getSegment13());
        acpRequisitionAccount.setAccountSegment14(gldSegment.getSegment14());
        acpRequisitionAccount.setAccountSegment15(gldSegment.getSegment15());
        acpRequisitionAccount.setAccountSegment16(gldSegment.getSegment16());
        acpRequisitionAccount.setAccountSegment17(gldSegment.getSegment17());
        acpRequisitionAccount.setAccountSegment18(gldSegment.getSegment18());
        acpRequisitionAccount.setAccountSegment19(gldSegment.getSegment19());
        acpRequisitionAccount.setAccountSegment20(gldSegment.getSegment20());
        self().updateByPrimaryKey(request, acpRequisitionAccount);
        return acpRequisitionAccount;

    }

    private Map<String, Object> getFunctionalAmount(Long requisitionHdsId, String currencyCode, String standardCurrency,
                    BigDecimal amount, List<AcpReqCurrencyTemp> currencyTempList) {
        Map<String, Object> resultMap = new HashMap<>();
        BigDecimal res = amount;
        BigDecimal exchangeRate = null;
        String exchangeRateType = null;
        List<AcpReqCurrencyTemp> newCurrencyTemps = currencyTempList.parallelStream().filter(record -> {
            return record.getCurrencyCode().equals(currencyCode)
                            && record.getRequisitionHdsId().intValue() == requisitionHdsId.intValue();
        }).collect(Collectors.toList());

        if (!currencyCode.equals(standardCurrency)) {
            res = amount.multiply(newCurrencyTemps.get(0).getExchangeRate());
            exchangeRate = newCurrencyTemps.get(0).getExchangeRate();
            exchangeRateType = newCurrencyTemps.get(0).getExchangeRateType();
        } else {
            exchangeRate = new BigDecimal(1);
        }
        res = this.calcCurrGldAmount(res, standardCurrency);
        resultMap.put("exchangeRate", exchangeRate);
        resultMap.put("exchangeRateType", exchangeRateType);
        resultMap.put("functionalAmount", res);
        return resultMap;

    }

    /**
     * 添加错误日志
     *
     * @param requisitionNumber 付款申请单编号
     * @param line 付款申请单行信息
     * @param errCode 错误代码
     * @author guiyuting 2019-05-09 14:42
     * @return
     */
    private void errLog(IRequest request, String requisitionNumber, AcpRequisitionLn line, String errCode) {
        AcpReqAuditErrorLog log = new AcpReqAuditErrorLog();
        log.setSessionId(request.getSessionId());
        log.setRequisitionLnsId(line.getRequisitionLnsId());
        log.setMessage(promptService.getPromptDescription(request.getLocale(), errCode));
        log.setCreatedBy(request.getUserId());
        log.setCreationDate(new Date());
        logService.insertSelective(request, log);
    }

    /**
     * <p>
     * 根据精度计算金额(财务精度)
     * <p/>
     *
     * @param amount
     * @return 返回计算金额
     * @author guiyuting 2019-05-08 15:51
     */
    private BigDecimal calcCurrGldAmount(BigDecimal amount, String currencyCode) {
        int precision = gldCurrencyMapper.getPrecision(currencyCode);
        if (precision == 0) {
            return amount.setScale(2, BigDecimal.ROUND_DOWN);
        } else {
            return amount.setScale(precision, BigDecimal.ROUND_DOWN);
        }

    }


    @Override
    @Transactional
    public List<AcpRequisitionHd> rejectAcpReq(IRequest request, List<AcpRequisitionHd> list)
                    throws AcpRequisitionException {
        for (AcpRequisitionHd record : list) {
            AcpRequisitionHd acpRequisitionHd = iAcpRequisitionHdService.selectByPrimaryKey(request, record);

            // 设置付款申请单为拒绝状态
            acpRequisitionHd.setStatus("REJECTED");
            acpRequisitionHd.setJeCreationStatus("N");
            iAcpRequisitionHdService.updateByPrimaryKey(request, acpRequisitionHd);

            // 提交历史
            iExpDocumentHistoryService.insertDocumentHistory(request, AcpRequisitionHd.ACP_REQUISITION,
                            acpRequisitionHd.getRequisitionHdsId(), ExpDocumentHistory.STATUS_AUDIT,
                            request.getEmployeeId(), null);
            // 删除相关凭证数据
            acpRequisitionAccountMapper.rejectAcpDelete(record.getRequisitionHdsId());
        }
        return list;
    }
}
