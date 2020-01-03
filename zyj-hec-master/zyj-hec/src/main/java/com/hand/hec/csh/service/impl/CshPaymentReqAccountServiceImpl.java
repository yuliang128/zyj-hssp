package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hand.hap.fnd.mapper.FndCompanyRefAccEntityMapper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.csh.exception.CshPaymentReqAccountException;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.dto.ExpRequisitionDist;
import com.hand.hec.exp.dto.ExpRequisitionHeader;
import com.hand.hec.exp.dto.ExpRequisitionLine;
import com.hand.hec.exp.mapper.ExpRequisitionDistMapper;
import com.hand.hec.exp.mapper.ExpRequisitionLineMapper;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.exp.service.IExpRequisitionDistService;
import com.hand.hec.exp.service.IExpRequisitionHeaderService;
import com.hand.hec.exp.service.IExpRequisitionLineService;
import com.hand.hec.fnd.dto.GldPeriod;

import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hap.gld.mapper.GldCurrencyMapper;

import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.gld.service.IGldMappingConditionService;
import com.hand.hap.sys.service.ISysParameterService;

import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.hand.hec.csh.mapper.CshPaymentReqAccountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hec.csh.dto.CshPaymentReqAccount;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import com.hand.hec.csh.dto.CshPaymentRequisitionLn;
import com.hand.hec.csh.mapper.CshPaymentRequisitionLnMapper;
import com.hand.hec.csh.service.ICshPaymentReqAccountService;
import com.hand.hec.csh.service.ICshPaymentRequisitionHdService;
import com.hand.hec.gld.dto.GldSegment;
import com.hand.hec.gld.service.IGldSobSegmentService;

@Service
@Transactional(rollbackFor = Exception.class)

/**
 * <p>
 * 借款申请单凭证表Impl
 * </p>
 *
 * @author Tagin 2019/02/21 22:36
 */
public class CshPaymentReqAccountServiceImpl extends BaseServiceImpl<CshPaymentReqAccount>
                implements ICshPaymentReqAccountService {
    @Autowired
    private CshPaymentRequisitionLnMapper cshPaymentRequisitionLnMapper;

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private ICshPaymentReqAccountService service;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @Autowired
    private ICshPaymentRequisitionHdService cshPaymentRequisitionHdService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IGldMappingConditionService gldMappingConditionService;

    @Autowired
    private CshPaymentReqAccountMapper cshPaymentReqAccountMapper;

    @Autowired
    private GldAccountingEntityMapper gldAccountingEntityMapper;

    @Autowired
    private ISysParameterService iSysParameterService;

    @Autowired
    private IExpDocumentHistoryService iExpDocumentHistoryService;

    @Autowired
    private BgtBudgetReserveMapper bgtBudgetReserveMapper;

    @Autowired
    private IExpRequisitionHeaderService iExpRequisitionHeaderService;

    @Autowired
    private IExpRequisitionLineService iExpRequisitionLineService;

    @Autowired
    private IExpRequisitionDistService iExpRequisitionDistService;

    @Autowired
    private IGldExchangeRateService gldExchangeRateService;

    @Autowired
    private ExpRequisitionLineMapper expRequisitionLineMapper;

    @Autowired
    private ExpRequisitionDistMapper expRequisitionDistMapper;

    @Autowired
    private FndCompanyRefAccEntityMapper fndCompanyRefAccEntityMapper;

    @Autowired
    private GldPeriodMapper gldPeriodMapper;


    /**
     * 获取报销单本币金额
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 10:34
     * @param currencyCode 原币币种
     * @param functionalCurrencyCode 本位币币种
     * @param periodName 期间
     * @param journalDate 凭证日期
     * @param amount 金额
     * @param cshPaymentReqDrAccount 凭证信息
     * @Version 1.0
     **/
    public BigDecimal getFunctionAmount(String currencyCode, String functionalCurrencyCode, Long accEntityId,
                    String periodName, Date journalDate, BigDecimal amount, CshPaymentReqAccount cshPaymentReqAccount) {
        BigDecimal functionAmount = new BigDecimal(0);
        BigDecimal exchangeRate = new BigDecimal(0);
        String exchangeRateType = sysParameterService.queryParamValueByCode("DEFAULT_EXCHANGE_RATE_TYPE", null, null,
                        null, accEntityId, null, null, null);

        if (currencyCode.equals(functionalCurrencyCode)) {
            exchangeRate = new BigDecimal(1);
            cshPaymentReqAccount.setExchangeRate(new BigDecimal(1));
        } else {
            if (!exchangeRateType.isEmpty()) {
                // 获取对应的汇率
                exchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, currencyCode, functionalCurrencyCode,
                                exchangeRateType, journalDate, periodName, "N");
                cshPaymentReqAccount.setExchangeRateType(exchangeRateType);
                cshPaymentReqAccount.setExchangeRate(exchangeRate);
            }
        }
        // 获取小数精度
        int precision = gldCurrencyMapper.getPrecision(functionalCurrencyCode);
        functionAmount = functionAmount
                        .add(amount.multiply(exchangeRate).setScale(precision, BigDecimal.ROUND_HALF_UP));
        return functionAmount;
    }

    /**
     * 借款单借方凭证创建
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/26 13:54
     * @param cshPaymentRequisitionLns 借款单行信息
     * @param cshPaymentReqAccounts 凭证信息
     * @param journalDate 审核日期
     * @param periodName 期间
     * @param userId 创建人
     * @return
     * @Version 1.0
     **/
    public void createCshPaymentReqDrAccount(IRequest iRequest,List<CshPaymentRequisitionLn> cshPaymentRequisitionLns,
                    List<CshPaymentReqAccount> cshPaymentReqAccounts, Date journalDate, String periodName,
                    Long userId) {
        if (!cshPaymentRequisitionLns.isEmpty()) {
            for (CshPaymentRequisitionLn cshPaymentRequisitionLn : cshPaymentRequisitionLns) {
                //创建凭证前先删除凭证信息
                CshPaymentReqAccount account = new CshPaymentReqAccount();
                account.setPaymentRequisitionLineId(cshPaymentRequisitionLn.getPaymentRequisitionLineId());
                List<CshPaymentReqAccount> accounts = new ArrayList<>();
                accounts = self().select(iRequest,account,0,0);
                if(!accounts.isEmpty() && accounts != null){
                    self().batchDelete(accounts);
                }
                //再创建凭证
                CshPaymentReqAccount cshPaymentReqDrAccount = new CshPaymentReqAccount();
                List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
                Long accountId = 0L;
                // 1.0.2 根据用途代码【PAYMENT_REQUISITION】获取科目
                gldMappingConditions = gldMappingConditionService.accBuildPaymentReq(
                                cshPaymentRequisitionLn.getMagOrgId().toString(),
                                cshPaymentRequisitionLn.getCompanyId().toString(),
                                cshPaymentRequisitionLn.getPositionId().toString(),
                                cshPaymentRequisitionLn.getAccEntityId().toString(),
                                cshPaymentRequisitionLn.getRespCenterId().toString(),
                                cshPaymentRequisitionLn.getPayeeCategory(),
                                cshPaymentRequisitionLn.getPayeeTypeId().toString(),
                                cshPaymentRequisitionLn.getPayeeId().toString(),
                                cshPaymentRequisitionLn.getMoCshTrxClassId().toString());
                // 通过匹配组获取借方科目
                accountId = gldMappingConditionService.getAccount(gldMappingConditions, "PAYMENT_REQUISITION",
                                cshPaymentRequisitionLn.getMagOrgId(), cshPaymentRequisitionLn.getSetOfBooksId());
                if (accountId.longValue() == 0) {
                    throw new RuntimeException("未能获取用途代码为[EMPLOYEE_EXPENSE]的科目，请检查科目配置！");
                } else {
                    cshPaymentReqDrAccount.setAccountId(accountId);
                }
                if (cshPaymentRequisitionLn.getRespCenterId() == null) {
                    throw new RuntimeException("未取到公司缺省成本中心！");
                } else {
                    cshPaymentReqDrAccount.setRespCenterId(cshPaymentRequisitionLn.getRespCenterId());
                }
                cshPaymentReqDrAccount.setEnteredAmountDr(cshPaymentRequisitionLn.getAmount());
                cshPaymentReqDrAccount.setFunctionalAmountDr(getFunctionAmount(
                                cshPaymentRequisitionLn.getCurrencyCode(), cshPaymentRequisitionLn.getCurrencyCode(),
                                cshPaymentRequisitionLn.getAccEntityId(), periodName, journalDate,
                                cshPaymentRequisitionLn.getAmount(), cshPaymentReqDrAccount));
                cshPaymentReqDrAccount.setAccEntityId(cshPaymentRequisitionLn.getAccEntityId());
                cshPaymentReqDrAccount
                                .setPaymentRequisitionLineId(cshPaymentRequisitionLn.getPaymentRequisitionLineId());
                cshPaymentReqDrAccount.setDescription(cshPaymentRequisitionLn.getHeaderDescription());
                cshPaymentReqDrAccount.setCurrencyCode(cshPaymentRequisitionLn.getCurrencyCode());
                cshPaymentReqDrAccount.setJournalDate(journalDate);
                cshPaymentReqDrAccount.setPeriodName(periodName);
                cshPaymentReqDrAccount.setUsageCode("PAYMENT_REQUISITION");
                cshPaymentReqDrAccount.setGldInterfaceFlag("N");
                cshPaymentReqDrAccount.setCreatedBy(userId);
                cshPaymentReqDrAccount.setCreationDate(new Date());
                cshPaymentReqDrAccount.setLastUpdatedBy(userId);
                cshPaymentReqDrAccount.setLastUpdateDate(new Date());
                cshPaymentReqAccounts.add(cshPaymentReqDrAccount);
            }
        }
    }

    /**
     * 借款单贷方凭证创建
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/26 13:54
     * @param cshPaymentRequisitionLns 借款单行信息
     * @param cshPaymentReqAccounts 凭证信息
     * @param journalDate 审核日期
     * @param periodName 期间
     * @param userId 创建人
     * @return
     * @Version 1.0
     **/
    public void createCshPaymentReqCrAccount(List<CshPaymentRequisitionLn> cshPaymentRequisitionLns,
                    List<CshPaymentReqAccount> cshPaymentReqAccounts, Date journalDate, String periodName,
                    Long userId) {
        if (!cshPaymentRequisitionLns.isEmpty()) {
            for (CshPaymentRequisitionLn cshPaymentRequisitionLn : cshPaymentRequisitionLns) {
                CshPaymentReqAccount cshPaymentReqDrAccount = new CshPaymentReqAccount();
                List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
                Long accountId = 0L;
                // 1.0.2 根据用途代码【PAYMENT_REQUISITION】获取科目
                gldMappingConditions = gldMappingConditionService.accBuildPaymentReq(
                                cshPaymentRequisitionLn.getMagOrgId().toString(),
                                cshPaymentRequisitionLn.getCompanyId().toString(),
                                cshPaymentRequisitionLn.getPositionId().toString(),
                                cshPaymentRequisitionLn.getAccEntityId().toString(),
                                cshPaymentRequisitionLn.getRespCenterId().toString(),
                                cshPaymentRequisitionLn.getPayeeCategory(),
                                cshPaymentRequisitionLn.getPayeeTypeId().toString(),
                                cshPaymentRequisitionLn.getPayeeId().toString(),
                                cshPaymentRequisitionLn.getMoCshTrxClassId().toString());
                // 通过匹配组获取借方科目
                accountId = gldMappingConditionService.getAccount(gldMappingConditions, "PAYMENT_REQUISITION_CLEARING",
                                cshPaymentRequisitionLn.getMagOrgId(), cshPaymentRequisitionLn.getSetOfBooksId());
                if (accountId.longValue() == 0) {
                    throw new RuntimeException("未能获取用途代码为[EMPLOYEE_EXPENSE]的科目，请检查科目配置！");
                } else {
                    cshPaymentReqDrAccount.setAccountId(accountId);
                }
                if (cshPaymentRequisitionLn.getRespCenterId() == null) {
                    throw new RuntimeException("未取到公司缺省成本中心！");
                } else {
                    cshPaymentReqDrAccount.setRespCenterId(cshPaymentRequisitionLn.getRespCenterId());
                }
                cshPaymentReqDrAccount.setEnteredAmountDr(cshPaymentRequisitionLn.getAmount());
                cshPaymentReqDrAccount.setFunctionalAmountDr(getFunctionAmount(
                                cshPaymentRequisitionLn.getCurrencyCode(), cshPaymentRequisitionLn.getCurrencyCode(),
                                cshPaymentRequisitionLn.getAccEntityId(), periodName, journalDate,
                                cshPaymentRequisitionLn.getAmount(), cshPaymentReqDrAccount));
                cshPaymentReqDrAccount.setAccEntityId(cshPaymentRequisitionLn.getAccEntityId());
                cshPaymentReqDrAccount
                                .setPaymentRequisitionLineId(cshPaymentRequisitionLn.getPaymentRequisitionLineId());
                cshPaymentReqDrAccount.setDescription(cshPaymentRequisitionLn.getHeaderDescription());
                cshPaymentReqDrAccount.setCurrencyCode(cshPaymentRequisitionLn.getCurrencyCode());
                cshPaymentReqDrAccount.setJournalDate(journalDate);
                cshPaymentReqDrAccount.setPeriodName(periodName);
                cshPaymentReqDrAccount.setUsageCode("PAYMENT_REQUISITION_CLEARING");
                cshPaymentReqDrAccount.setGldInterfaceFlag("N");
                cshPaymentReqDrAccount.setCreatedBy(userId);
                cshPaymentReqDrAccount.setCreationDate(new Date());
                cshPaymentReqDrAccount.setLastUpdatedBy(userId);
                cshPaymentReqDrAccount.setLastUpdateDate(new Date());
                cshPaymentReqAccounts.add(cshPaymentReqDrAccount);
            }
        }
    }


    @Override
    public void createCshPaymentReqAccount(IRequest iRequest, Long paymentRequisitionHeaderId, Date journalDate,
                    String periodName) {
        // 获取借款单行信息
        List<CshPaymentRequisitionLn> cshPaymentRequisitionLns = new ArrayList<>();
        cshPaymentRequisitionLns =
                        cshPaymentRequisitionLnMapper.getCshPaymentRequisitionLns(paymentRequisitionHeaderId);
        // 凭证对应的List
        List<CshPaymentReqAccount> cshPaymentReqAccounts = new ArrayList<>();
        // 创建借方凭证
        createCshPaymentReqDrAccount(iRequest,cshPaymentRequisitionLns, cshPaymentReqAccounts, journalDate, periodName,
                        iRequest.getUserId());
        // 创建贷方凭证
        createCshPaymentReqCrAccount(cshPaymentRequisitionLns, cshPaymentReqAccounts, journalDate, periodName,
                        iRequest.getUserId());
        // 保存凭证信息并跟新科目段
        for (CshPaymentReqAccount cshPaymentReqAccount : cshPaymentReqAccounts) {
            Long setOfBooksId = cshPaymentReqAccount.getSetOfBooksId();
            cshPaymentReqAccount = service.insertSelective(iRequest, cshPaymentReqAccount);
            String drCrFlag = "";
            if (cshPaymentReqAccount.getEnteredAmountDr() == null) {
                drCrFlag = "CR";
            } else if (cshPaymentReqAccount.getEnteredAmountCr() == null) {
                drCrFlag = "DR";
            }
            GldSegment gldSegment = new GldSegment();
            gldSobSegmentService.mappingSegmentValue(cshPaymentReqAccount.getAccEntityId(), setOfBooksId,
                            cshPaymentReqAccount.getAccountId(), "CSH_PMT_REQ",
                            cshPaymentReqAccount.getPaymentReqJeLineId(), drCrFlag, paymentRequisitionHeaderId,
                            cshPaymentReqAccount.getPaymentRequisitionLineId(), null, null);
            if (gldSegment == null) {
                throw new RuntimeException("未获取到对应科目段!");
            }
            cshPaymentReqAccount.setAccountSegment1(gldSegment.getSegment1());
            cshPaymentReqAccount.setAccountSegment2(gldSegment.getSegment2());
            cshPaymentReqAccount.setAccountSegment3(gldSegment.getSegment3());
            cshPaymentReqAccount.setAccountSegment4(gldSegment.getSegment4());
            cshPaymentReqAccount.setAccountSegment5(gldSegment.getSegment5());
            cshPaymentReqAccount.setAccountSegment6(gldSegment.getSegment6());
            cshPaymentReqAccount.setAccountSegment7(gldSegment.getSegment7());
            cshPaymentReqAccount.setAccountSegment8(gldSegment.getSegment8());
            cshPaymentReqAccount.setAccountSegment9(gldSegment.getSegment9());
            cshPaymentReqAccount.setAccountSegment10(gldSegment.getSegment10());
            cshPaymentReqAccount.setAccountSegment11(gldSegment.getSegment11());
            cshPaymentReqAccount.setAccountSegment12(gldSegment.getSegment12());
            cshPaymentReqAccount.setAccountSegment13(gldSegment.getSegment13());
            cshPaymentReqAccount.setAccountSegment14(gldSegment.getSegment14());
            cshPaymentReqAccount.setAccountSegment15(gldSegment.getSegment15());
            cshPaymentReqAccount.setAccountSegment16(gldSegment.getSegment16());
            cshPaymentReqAccount.setAccountSegment17(gldSegment.getSegment17());
            cshPaymentReqAccount.setAccountSegment18(gldSegment.getSegment18());
            cshPaymentReqAccount.setAccountSegment19(gldSegment.getSegment19());
            cshPaymentReqAccount.setAccountSegment20(gldSegment.getSegment20());
            service.updateByPrimaryKey(iRequest, cshPaymentReqAccount);
            // 跟新头凭证创建状态
            CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
            cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
            cshPaymentRequisitionHd.setJeCreationStatus("SUCCESS");
            cshPaymentRequisitionHd.setLastUpdateDate(new Date());
            cshPaymentRequisitionHd.setLastUpdatedBy(iRequest.getUserId());
            cshPaymentRequisitionHdService.updateByPrimaryKeySelective(iRequest, cshPaymentRequisitionHd);
        }
    }

    @Override
    public List<Map> queryByHeaderId(IRequest request, Long paymentRequisitionHeaderId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cshPaymentReqAccountMapper.queryByHeaderId(paymentRequisitionHeaderId);
    }

    @Override
    public List<Map> queryByCompanyId(IRequest request) {
        return gldAccountingEntityMapper.queryForDftAccEntity();
    }

    @Override
    public List<Map> queryCurrentTime(IRequest request) {
        return cshPaymentReqAccountMapper.queryCurrentTime();
    }

    @Override
    @Transactional
    public List<CshPaymentRequisitionHd> createAccount(IRequest request, List<CshPaymentRequisitionHd> list,
                    String journalDate, String periodName) throws CshPaymentReqAccountException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        Date journalD;
        try {
            journalD = simpleDateFormat.parse(journalDate);
        } catch (ParseException e) {
            throw new CshPaymentReqAccountException(CshPaymentReqAccountException.DATE_FORMAT_CONVERSION_FAIL,
                            CshPaymentReqAccountException.DATE_FORMAT_CONVERSION_FAIL, null);
        }
        // 根据头Id循环生成凭证
        for (CshPaymentRequisitionHd record : list) {
            self().createCshPaymentReqAccount(request, record.getPaymentRequisitionHeaderId(), journalD, periodName);
        }
        return list;
    }

    @Override
    @Transactional
    public List<CshPaymentRequisitionHd> auditPayReq(IRequest request, List<CshPaymentRequisitionHd> list)
                    throws CshPaymentReqAccountException {
        String recheckFlag = iSysParameterService.queryParamValueByCode("CSH_PAYMENT_REQUISITION_RECHECK", null, null,
                        null, null, null, null, null);
        if (recheckFlag.isEmpty()) {
            recheckFlag = "N";
        }
        for (CshPaymentRequisitionHd record : list) {
            CshPaymentRequisitionHd cshPaymentRequisitionHd =
                            cshPaymentRequisitionHdService.selectByPrimaryKey(request, record);
            switch (cshPaymentRequisitionHd.getJeCreationStatus()) {
                case "SUCCESS":
                    break;
                default:
                    throw new CshPaymentReqAccountException(CshPaymentReqAccountException.CREATE_ACCOUNT_FIRST,
                                    CshPaymentReqAccountException.CREATE_ACCOUNT_FIRST, null);
            }
            // 借贷平衡校验
            accountBalanceCheck(request, record);
            Criteria criteria = new Criteria(cshPaymentRequisitionHd);
            criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_FLAG);
            criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_DATE);
            switch (recheckFlag) {
                case "N":
                    record.setAuditFlag("Y");
                    record.setAuditDate(new Date());
                    cshPaymentRequisitionHdService.updateByPrimaryKeyOptions(request, record, criteria);

                    // 变更凭证为P
                    cshPaymentReqAccountMapper.updateByLineId(record.getPaymentRequisitionHeaderId(), "P");
                    // 报销单审核事件 暂无

                    break;
                case "Y":
                    record.setAuditFlag("R");
                    record.setAuditDate(new Date());

                    cshPaymentRequisitionHdService.updateByPrimaryKeyOptions(request, record, criteria);
                    break;
                default:
                    break;
            }
            // 单据历史
            iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                            cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_AUDIT,
                            request.getEmployeeId(), null);
        }
        return list;
    }

    @Override
    @Transactional
    public List<CshPaymentRequisitionHd> auditrecheckPayReq(IRequest request, List<CshPaymentRequisitionHd> list)
                    throws CshPaymentReqAccountException {
        String recheckFlag = iSysParameterService.queryParamValueByCode("CSH_PAYMENT_REQUISITION_RECHECK", null, null,
                        null, null, null, null, null);
        switch (recheckFlag) {
            case "Y":
                break;
            default:
                throw new CshPaymentReqAccountException(
                                CshPaymentReqAccountException.PAYMENT_REQ_AUDIT_RECHECK_NOT_NEED,
                                CshPaymentReqAccountException.PAYMENT_REQ_AUDIT_RECHECK_NOT_NEED, null);
        }

        for (CshPaymentRequisitionHd record : list) {
            CshPaymentRequisitionHd cshPaymentRequisitionHd =
                            cshPaymentRequisitionHdService.selectByPrimaryKey(request, record);
            switch (cshPaymentRequisitionHd.getJeCreationStatus()) {
                case "SUCCESS":
                    break;
                default:
                    throw new CshPaymentReqAccountException(CshPaymentReqAccountException.CREATE_ACCOUNT_FIRST,
                                    CshPaymentReqAccountException.CREATE_ACCOUNT_FIRST, null);
            }

            switch (cshPaymentRequisitionHd.getAuditFlag()) {
                case "R":
                    break;
                default:
                    throw new CshPaymentReqAccountException(CshPaymentReqAccountException.PAYMENT_REQ_CONFIRM_STATUS,
                                    CshPaymentReqAccountException.PAYMENT_REQ_CONFIRM_STATUS, null);
            }

            // 借贷平衡校验
            accountBalanceCheck(request, record);

            // 变更单据状态
            Criteria criteria = new Criteria(cshPaymentRequisitionHd);
            criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_FLAG);
            criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_DATE);
            record.setAuditFlag("Y");
            record.setAuditDate(new Date());
            cshPaymentRequisitionHdService.updateByPrimaryKeyOptions(request, record, criteria);

            // 变更凭证为P
            cshPaymentReqAccountMapper.updateByLineId(record.getPaymentRequisitionHeaderId(), "P");
            // 报销单审核事件 暂无

            // 单据历史
            iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                            cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_AUDIT,
                            request.getEmployeeId(), null);
        }
        return list;
    }

    /**
     * 借款申请单借贷平衡校验
     *
     * @param record 借款单头
     * @author dingwei.ma@hand-china.com 2019-03-04 15:32
     * @return void
     */
    private void accountBalanceCheck(IRequest request, CshPaymentRequisitionHd record)
                    throws CshPaymentReqAccountException {
        List<Map> mapList = cshPaymentReqAccountMapper.selectGroupByLine(record.getPaymentRequisitionHeaderId());
        for (Map map : mapList) {
            // 查询借贷方相减的差额
            List<Map> amountList = cshPaymentReqAccountMapper.selectForEnterAmount(
                            (Long) map.get("paymentRequisitionLineId"), (Long) map.get("accEntityId"));
            BigDecimal enteredAmountBal = (BigDecimal) amountList.get(0).get("enteredAmountBal");
            BigDecimal functionalAmountBal = (BigDecimal) amountList.get(0).get("functionalAmountBal");
            if (enteredAmountBal.equals(BigDecimal.ZERO) || functionalAmountBal.equals(BigDecimal.ZERO)) {
                throw new CshPaymentReqAccountException(CshPaymentReqAccountException.LOAN_VOUCHER_NOT_BALANCE,
                                CshPaymentReqAccountException.LOAN_VOUCHER_NOT_BALANCE, null);
            }
        }
    }

    @Override
    @Transactional
    public List<CshPaymentRequisitionHd> rejectPayReq(IRequest request, List<CshPaymentRequisitionHd> list,
                    String description) {
        for (CshPaymentRequisitionHd record : list) {
            CshPaymentRequisitionHd cshPaymentRequisitionHd =
                            cshPaymentRequisitionHdService.selectByPrimaryKey(request, record);
            switch (cshPaymentRequisitionHd.getAuditFlag()) {
                case "R":
                    // 变更单据状态
                    Criteria criteria = new Criteria(cshPaymentRequisitionHd);
                    criteria.update(CshPaymentRequisitionHd.FIELD_JE_CREATION_STATUS);
                    criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_FLAG);
                    criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_DATE);
                    record.setJeCreationStatus("SUCCESS");
                    record.setAuditFlag("N");
                    record.setAuditDate(new Date());
                    cshPaymentRequisitionHdService.updateByPrimaryKeyOptions(request, record, criteria);
                    break;
                default:
                    try {
                        rejectPayReqCheck(request, cshPaymentRequisitionHd, description);
                    } catch (CshPaymentReqAccountException e) {
                    }

                    break;
            }
        }
        return list;
    }

    private void rejectPayReqCheck(IRequest request, CshPaymentRequisitionHd record, String description)
                    throws CshPaymentReqAccountException {
        String submitFlag = iSysParameterService.queryParamValueByCode("PAYMENT_REQUISITION_APPROVED_WITH_REQ", null,
                        null, request.getCompanyId(), null, null, null, null);
        if (submitFlag.isEmpty()) {
            submitFlag = "N";
        }
        switch (record.getSourceType()) {
            case "EXP_REQUISITION":
                switch (submitFlag) {
                    case "Y":
                        CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
                        cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(record.getPaymentRequisitionHeaderId());
                        List<CshPaymentRequisitionLn> cshPaymentRequisitionLnList =
                                        cshPaymentRequisitionLnMapper.select(cshPaymentRequisitionLn);
                        String existFlag = "";
                        for (CshPaymentRequisitionLn ln : cshPaymentRequisitionLnList) {
                            existFlag = cshPaymentReqAccountMapper.selectExpReqExist(ln.getRefDocumentId());
                            switch (existFlag) {
                                case "Y":
                                    throw new CshPaymentReqAccountException(
                                                    CshPaymentReqAccountException.PAYMENT_REQ_ASSO_EXP_REQ_EXPENSE,
                                                    CshPaymentReqAccountException.PAYMENT_REQ_ASSO_EXP_REQ_EXPENSE,
                                                    null);
                                default:
                                    existFlag = cshPaymentReqAccountMapper
                                                    .selectExpReqCloseExist(ln.getRefDocumentId());
                                    switch (existFlag) {
                                        case "Y":
                                            throw new CshPaymentReqAccountException(
                                                            CshPaymentReqAccountException.EXP_REQUISITION_EXISTS_CLOSED_LINE,
                                                            CshPaymentReqAccountException.EXP_REQUISITION_EXISTS_CLOSED_LINE,
                                                            null);
                                        default:
                                            // 拒绝申请单
                                            rejectExpRequisition(request, ln.getRefDocumentId());
                                            break;
                                    }
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        // 拒绝借款申请单
        rejectPaymentRequisition(request, record, description);
        // 删除借款申请单凭证
        cshPaymentReqAccountMapper.deleteByPayReqLineId(record.getPaymentRequisitionHeaderId());
    }

    private void rejectExpRequisition(IRequest request, Long expRequisitionHeaderId) {
        // 变更申请单预算表的状态
        bgtBudgetReserveMapper.updateForStatus("EXP_REQUISITION", expRequisitionHeaderId, "N");
        // 变更申请单状态
        Criteria criteria = null;
        ExpRequisitionHeader expRequisitionHeader = new ExpRequisitionHeader();
        expRequisitionHeader.setExpRequisitionHeaderId(expRequisitionHeaderId);
        iExpRequisitionHeaderService.selectByPrimaryKey(request, expRequisitionHeader);

        ExpRequisitionLine expRequisitionLine = new ExpRequisitionLine();
        expRequisitionLine.setExpRequisitionHeaderId(expRequisitionHeaderId);
        List<ExpRequisitionLine> expRequisitionLineList = expRequisitionLineMapper.select(expRequisitionLine);
        for (ExpRequisitionLine line : expRequisitionLineList) {
            ExpRequisitionDist expRequisitionDist = new ExpRequisitionDist();
            expRequisitionDist.setExpRequisitionLineId(line.getExpRequisitionLineId());
            List<ExpRequisitionDist> expRequisitionDistList = expRequisitionDistMapper.select(expRequisitionDist);
            for (ExpRequisitionDist dist : expRequisitionDistList) {
                dist.setRequisitionStatus(ExpRequisitionHeader.FIELD_STATUS_REJECTED);
                criteria = new Criteria(dist);
                criteria.update(ExpRequisitionDist.FIELD_REQUISITION_STATUS);
                iExpRequisitionDistService.updateByPrimaryKeyOptions(request, dist, criteria);
            }

            line.setRequisitionStatus(ExpRequisitionHeader.FIELD_STATUS_REJECTED);
            criteria = new Criteria(line);
            criteria.update(ExpRequisitionLine.FIELD_REQUISITION_STATUS);
            iExpRequisitionLineService.updateByPrimaryKeyOptions(request, line, criteria);
        }
        expRequisitionHeader.setRequisitionStatus(ExpRequisitionHeader.FIELD_STATUS_REJECTED);
        criteria = new Criteria(expRequisitionHeader);
        criteria.update(ExpRequisitionHeader.FIELD_REQUISITION_STATUS);
        iExpRequisitionHeaderService.updateByPrimaryKeyOptions(request, expRequisitionHeader, criteria);

    }

    private void rejectPaymentRequisition(IRequest request, CshPaymentRequisitionHd record, String description) {
        record.setStatus(CshPaymentRequisitionHd.STATUS_REJECTED);
        record.setJeCreationStatus("N");
        Criteria criteria = new Criteria(record);
        criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_JE_CREATION_STATUS);
        cshPaymentRequisitionHdService.updateByPrimaryKeyOptions(request, record, criteria);

        iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                        record.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_AUDIT_REJECT,
                        request.getEmployeeId(), description);
    }

    @Override
    public List<GldPeriod> queryPeriodName(IRequest request, Date journalDate, String status) {
        // 查询默认核算主体
        Long accEntityId = fndCompanyRefAccEntityMapper.queryDftAccByComId();
        String periodName = gldPeriodMapper.getPeriodName(journalDate, accEntityId, status);
        GldPeriod period = new GldPeriod();
        period.setPeriodName(periodName);
        List<GldPeriod> periodList = new ArrayList<>();
        periodList.add(period);
        return periodList;
    }

    @Override
    public List<CshPaymentReqAccount> updateAccount(IRequest request, List<CshPaymentReqAccount> list)
                    throws CshPaymentReqAccountException {
        for (CshPaymentReqAccount account : list) {
            if (account.getEnteredAmountCr().toString().isEmpty()
                            && account.getEnteredAmountDr().toString().isEmpty()) {
                throw new CshPaymentReqAccountException(CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_BE_NULL,
                                CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_BE_NULL, null);
            }
            if (account.getFunctionalAmountCr().toString().isEmpty()
                            && account.getFunctionalAmountDr().toString().isEmpty()) {
                throw new CshPaymentReqAccountException(CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_BE_NULL,
                                CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_BE_NULL, null);
            }
            if (!account.getEnteredAmountCr().toString().isEmpty()
                            && !account.getEnteredAmountDr().toString().isEmpty()) {
                throw new CshPaymentReqAccountException(CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_ALL_INPUT,
                                CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_ALL_INPUT, null);
            }
            if (!account.getFunctionalAmountCr().toString().isEmpty()
                            && !account.getFunctionalAmountDr().toString().isEmpty()) {
                throw new CshPaymentReqAccountException(CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_ALL_INPUT,
                                CshPaymentReqAccountException.DEBIT_CREDIT_CANNOT_ALL_INPUT, null);
            }
            Criteria criteria = new Criteria(account);
            criteria.update(CshPaymentReqAccount.FIELD_ACC_ENTITY_ID, CshPaymentReqAccount.FIELD_RESP_CENTER_ID,
                            CshPaymentReqAccount.FIELD_ACCOUNT_ID, CshPaymentReqAccount.FIELD_ENTERED_AMOUNT_DR,
                            CshPaymentReqAccount.FIELD_ENTERED_AMOUNT_CR,
                            CshPaymentReqAccount.FIELD_FUNCTIONAL_AMOUNT_DR,
                            CshPaymentReqAccount.FIELD_FUNCTIONAL_AMOUNT_CR, CshPaymentReqAccount.FIELD_DESCRIPTION);
            self().updateByPrimaryKeyOptions(request, account, criteria);
        }
        return list;
    }

    /**
     * 获取借款审核凭证
     *
     * @author Tagin
     * @date 2019-04-01 14:51
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param paymentRequisitionLineId 借款申请单行ID
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @return java.util.List<com.hand.hec.csh.dto.CshPaymentReqAccount>
     * @version 1.0
     **/
    @Override
    public CshPaymentReqAccount queryAccount(Long transactionLineId, String usageCode, Long paymentRequisitionLineId,
                    String drFlag, String crFlag) {
        return cshPaymentReqAccountMapper
                        .queryAccount(transactionLineId, usageCode, paymentRequisitionLineId, drFlag, crFlag).get(0);
    }
}
