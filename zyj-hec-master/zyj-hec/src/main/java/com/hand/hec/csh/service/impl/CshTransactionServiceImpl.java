package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.dto.AcpRequisitionRef;
import com.hand.hec.acp.mapper.AcpRequisitionLnMapper;
import com.hand.hec.acp.mapper.AcpRequisitionRefMapper;
import com.hand.hec.acp.service.IAcpRequisitionRefService;
import com.hand.hec.base.service.HecUtil;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshTransactionException;
import com.hand.hec.csh.mapper.*;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.exp.service.IExpOrgUnitService;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.mapper.ExpReportHeaderMapper;
import com.hand.hec.expm.mapper.ExpReportPmtScheduleMapper;
import com.hand.hec.expm.service.IExpReportAccountService;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.constants.UsageCodeConstants;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import com.hand.hec.gld.service.IGlAccountEntryService;
import com.hand.hec.gld.service.IGldMappingConditionService;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;

/**
 * 现金事物处理程序
 *
 * @author mouse 2019/05/06 13:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshTransactionServiceImpl implements ICshTransactionService {

    @Autowired
    IExpReportAccountService reportAccountService;

    @Autowired
    HecUtil hecUtil;

    @Autowired
    ICshTransactionAccountService transactionAccountService;

    @Autowired
    ICshTransactionLineService transactionLineService;

    @Autowired
    ICshTransactionHeaderService transactionHeaderService;

    @Autowired
    CshTransactionMapper transactionMapper;

    @Autowired
    IExpOrgUnitService expOrgUnitService;

    @Autowired
    IGldResponsibilityCenterService gldResponsibilityCenterService;

    @Autowired
    IExpOrgPositionService expOrgPositionService;

    @Autowired
    GldSetOfBookMapper gldSetOfBookMapper;

    @Autowired
    FndCompanyMapper fndCompanyMapper;

    @Autowired
    IGldMappingConditionService gldMappingConditionService;

    @Autowired
    ICshWriteOffService writeOffService;

    @Autowired
    ICshPaymentRequisitionRefService paymentRequisitionRefService;

    @Autowired
    ICshPaymentRequisitionLnService paymentRequisitionLnService;

    @Autowired
    ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private CshWriteOffMapper cshWriteOffMapper;

    @Autowired
    private ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    private ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    @Autowired
    private CshTransactionAccountMapper cshTransactionAccountMapper;

    @Autowired
    private IExpDocumentHistoryService expDocumentHistoryService;

    @Autowired
    private CshTransactionLineMapper cshTransactionLineMapper;

    @Autowired
    private CshTransactionHeaderMapper cshTransactionHeaderMapper;

    @Autowired
    private CshPaymentRequisitionLnMapper cshPaymentRequisitionLnMapper;

    @Autowired
    private AcpRequisitionRefMapper acpRequisitionRefMapper;

    @Autowired
    private IAcpRequisitionRefService acpRequisitionRefService;

    @Autowired
    private AcpRequisitionLnMapper acpRequisitionLnMapper;

    @Autowired
    private ICshWriteOffAccountService cshWriteOffAccountService;

    @Autowired
    private CshWriteOffAccountMapper cshWriteOffAccountMapper;

    @Autowired
    private IGlAccountEntryService glAccountEntryService;

    @Override
    public void setBalance(IRequest request, Long transactionLineId) {
        CshTransactionAccount queryAccount = new CshTransactionAccount();
        queryAccount.setTransactionLineId(transactionLineId);
        List<CshTransactionAccount> accountList = transactionAccountService.select(request, queryAccount, 0, 0);
        BigDecimal crTotal = new BigDecimal(0);
        BigDecimal drTotal = new BigDecimal(0);
        for (CshTransactionAccount account : accountList) {
            if (account.getFunctionalAmountCr() != null) {
                crTotal = crTotal.add(account.getFunctionalAmountCr());
            }

            if (account.getFunctionalAmountDr() != null) {
                drTotal = drTotal.add(account.getFunctionalAmountDr());
            }
        }

        if (crTotal.compareTo(drTotal) != 0) {
            BigDecimal differ = crTotal.subtract(drTotal);

            if (differ.compareTo(BigDecimal.ZERO) > 0) {
                //
                // 贷方大于借方
                // ------------------------------------------------------------------------------
                for (CshTransactionAccount account : accountList) {
                    if (account.getFunctionalAmountDr() != null) {
                        //
                        // 当前凭证行借方金额不为空，将差额计入到当前行
                        // ------------------------------------------------------------------------------
                        account.setFunctionalAmountDr(account.getFunctionalAmountDr().add(differ));
                        transactionAccountService.updateByPrimaryKey(request, account);
                        return;
                    }
                }
            } else {
                //
                // 借方大于贷方
                // ------------------------------------------------------------------------------
                for (CshTransactionAccount account : accountList) {
                    if (account.getFunctionalAmountDr() != null) {
                        //
                        // 当前凭证行贷方金额不为空，将差额计入到当前行
                        // ------------------------------------------------------------------------------
                        account.setFunctionalAmountCr(account.getFunctionalAmountCr().subtract(differ));
                        transactionAccountService.updateByPrimaryKey(request, account);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void setTrxReturnStatus(IRequest request, CshTransactionHeader header) {
        BigDecimal returnedAmount = transactionMapper.getReturnedAmount(header);

        if (returnedAmount.compareTo(BigDecimal.ZERO) == 0) {
            //
            // 如果当前预付款事物已还款金额为0，设置还款状态
            // ------------------------------------------------------------------------------
            header.setReturnedFlag(CshTransactionHeader.RETURNED_FLAG_N);
            transactionHeaderService.updateByPrimaryKey(request, header);
        } else {
            //
            // 判断原预付款金额与当前已还款金额之间的关系，设置还款状态
            // ------------------------------------------------------------------------------
            CshTransactionLine line = new CshTransactionLine();
            line.setTransactionHeaderId(header.getTransactionHeaderId());
            line = transactionLineService.select(request, line, 0, 0).get(0);

            if (line.getTransactionAmount().compareTo(returnedAmount) == 0) {
                //
                // 完全退款
                // ------------------------------------------------------------------------------
                header.setReturnedFlag(CshTransactionHeader.RETURNED_FLAG_C);
                transactionHeaderService.updateByPrimaryKey(request, header);
            } else {
                //
                // 部分退款
                // ------------------------------------------------------------------------------
                header.setReturnedFlag(CshTransactionHeader.RETURNED_FLAG_Y);
                transactionHeaderService.updateByPrimaryKey(request, header);
            }
        }
    }

    public void setPaymentReqStatus(IRequest request, CshTransactionLine line) {
        CshPaymentRequisitionRef queryRef = new CshPaymentRequisitionRef();
        queryRef.setCshTransactionLineId(line.getTransactionLineId());
        List<CshPaymentRequisitionRef> refList = paymentRequisitionRefService.select(request, queryRef, 0, 0);
        HashSet<Long> paymentReqLineIdSet = new HashSet<>();

        for (CshPaymentRequisitionRef ref : refList) {
            if (!paymentReqLineIdSet.contains(ref.getPaymentRequisitionLineId())) {
                paymentReqLineIdSet.add(ref.getPaymentRequisitionLineId());

                CshPaymentRequisitionLn paymentReqLine = new CshPaymentRequisitionLn();
                paymentReqLine.setPaymentRequisitionLineId(ref.getPaymentRequisitionLineId());
                paymentReqLine = paymentRequisitionLnService.selectByPrimaryKey(request, paymentReqLine);

                CshDocPayAccEntity docPayAccEntity = new CshDocPayAccEntity();
                docPayAccEntity.setDocCategory("PAYMENT_REQUISITION");
                docPayAccEntity.setDocId(paymentReqLine.getPaymentRequisitionHeaderId());
                docPayAccEntity.setDocLineId(paymentReqLine.getPaymentRequisitionLineId());
                docPayAccEntity = cshDocPayAccEntityService.select(request, docPayAccEntity, 0, 0).get(0);

                BigDecimal paidAmount = paymentRequisitionRefService.queryPaidAmount(ref.getPaymentRequisitionLineId());

                if (paidAmount.compareTo(BigDecimal.ZERO) == 0) {
                    //
                    // 如果已支付金额为0，更新单据支付状态为未支付
                    // ------------------------------------------------------------------------------
                    paymentReqLine.setPaymentStatus("NEVER");
                    paymentReqLine.setPaymentCompletedDate(null);

                    paymentRequisitionLnService.updateByPrimaryKey(request, paymentReqLine);

                    //
                    // 更新单据支付状态表
                    // ------------------------------------------------------------------------------

                    docPayAccEntity.setPaymentStatus("ALLOWED");
                    cshDocPayAccEntityService.updateByPrimaryKey(request, docPayAccEntity);
                } else {
                    //
                    // 判断当前已支付金额为部分支付还是完全支付，并更新对应的单据状态
                    // ------------------------------------------------------------------------------
                    if (paymentReqLine.getAmount().compareTo(paidAmount) > 0) {
                        //
                        // 单据行金额大于已支付金额，部分支付
                        // ------------------------------------------------------------------------------
                        paymentReqLine.setPaymentStatus("NEVER");
                        paymentReqLine.setPaymentCompletedDate(null);

                        paymentRequisitionLnService.updateByPrimaryKey(request, paymentReqLine);


                        docPayAccEntity.setPaymentStatus("PAID");
                        cshDocPayAccEntityService.updateByPrimaryKey(request, docPayAccEntity);

                    } else if (paymentReqLine.getAmount().compareTo(paidAmount) == 0) {
                        //
                        // 单据行金额等于已支付金额，完全支付
                        // ------------------------------------------------------------------------------
                        paymentReqLine.setPaymentStatus("COMPLETELY");
                        paymentReqLine.setPaymentCompletedDate(null);

                        paymentRequisitionLnService.updateByPrimaryKey(request, paymentReqLine);


                        docPayAccEntity.setPaymentStatus("PAID");
                        cshDocPayAccEntityService.updateByPrimaryKey(request, docPayAccEntity);
                    }
                }
            }
        }
    }

    @Override
    public Long getPrepaymentAccount(IRequest request, CshTransactionHeader header, CshTransactionLine line) {
        // 默认成本中心
        ExpOrgUnit dftUnit = expOrgUnitService.getDefaultUnit(request, header.getEmployeeId(), line.getCompanyId());
        GldResponsibilityCenter dftRespCenter = gldResponsibilityCenterService
                        .getDefaultRespCenter(dftUnit == null ? null : dftUnit.getUnitId(), line.getAccEntityId());
        // 默认岗位
        ExpOrgPosition dftPosition =
                        expOrgPositionService.getDefaultPosition(request, request.getEmployeeId(), line.getCompanyId());
        List<GldSetOfBook> gldSetOfBooks = gldSetOfBookMapper.queryDftSetOffBookByAe(line.getAccEntityId());
        FndCompany fndCompany = fndCompanyMapper.selectByPrimaryKey(line.getCompanyId());
        List<GldMappingCondition> gldMappingConditions = gldMappingConditionService.accBuilderPrepayment(
                        line.getCompanyId().toString(), line.getAccEntityId().toString(),
                        dftRespCenter == null ? null : dftRespCenter.getResponsibilityCenterId().toString(),
                        line.getCurrencyCode(), dftPosition == null ? null : dftPosition.getPositionId().toString(),
                        header.getEmployeeId().toString(), line.getPayeeCategory(), header.getTransactionType(), null,
                        null, null, header.getMoCshTrxClassId() == null ? null : header.getMoCshTrxClassId().toString(),
                        null, null);
        Long accountId = gldMappingConditionService.getAccount(gldMappingConditions,
                        UsageCodeConstants.USAGE_PREPAYMENT, fndCompany.getMagOrgId(),
                        gldSetOfBooks.get(0).getSetOfBooksId());

        return accountId;
    }

    @Override
    public void postReturnTransaction(IRequest request, CshTransactionHeader rtnTrxHd, CshTransactionLine rtnTrxLn,
                    Long cashFlowItemId, List<CshTransactionReturn> trxRtnList) {
        //
        // 获取原付款事物
        // ------------------------------------------------------------------------------
        CshTransactionHeader sourceTrxHd = new CshTransactionHeader();
        sourceTrxHd.setTransactionHeaderId(rtnTrxHd.getSourceHeaderId());
        sourceTrxHd = transactionHeaderService.selectByPrimaryKey(request, sourceTrxHd);

        //
        // 如果完全退款，抛出错误
        // ------------------------------------------------------------------------------
        if (CshTransactionHeader.RETURNED_FLAG_C.equals(sourceTrxHd.getReturnedFlag())) {
            throw new CshTransactionException("CSH", CshTransactionException.RETURN_COMPLETE_ERROR, null);
        }

        //
        // 如果被反冲，抛出错误
        // ------------------------------------------------------------------------------
        if (!(sourceTrxHd.getReversedFlag() == null
                        || CshTransactionHeader.REVERSED_FLAG_N.equals(sourceTrxHd.getReversedFlag()))) {
            throw new CshTransactionException("CSH", CshTransactionException.RETURN_REVERSED_ERROR, null);
        }

        //
        // 校验事物日期
        // ------------------------------------------------------------------------------
        if (rtnTrxHd.getTransactionDate().compareTo(sourceTrxHd.getTransactionDate()) < 0) {
            throw new CshTransactionException("CSH", CshTransactionException.RETURN_DATE_ERROR, null);
        }

        //
        // 校验期间是否打开
        // ------------------------------------------------------------------------------
        if (!hecUtil.isPeriodOpen(request, rtnTrxHd.getPeriodName(), rtnTrxHd.getAccEntityId())) {
            throw new CshTransactionException("CSH", CshTransactionException.PERIOD_NOT_OPEN_ERROR, null);
        }

        //
        // 经营性现金事物，退核销记录
        // ------------------------------------------------------------------------------
        if (CshTransactionHeader.TRX_CATEGORY_BUSINESS.equals(sourceTrxHd.getTransactionCategory())) {
            writeOffService.returnCshTrxWriteOff(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, trxRtnList);
        }

        //
        // 创建现金流量记录
        // 暂未实现
        // ------------------------------------------------------------------------------
        createJournalCashFlow(request, rtnTrxHd.getAccEntityId(), rtnTrxLn, cashFlowItemId);

        //
        // 校验凭证借贷平衡
        // ------------------------------------------------------------------------------
        checkAccountDrCrBalance(request, rtnTrxHd, rtnTrxLn);

        //
        // 更新状态
        // ------------------------------------------------------------------------------
        rtnTrxHd.setPostedFlag(CshTransactionHeader.POSTED_FLAG_Y);
        rtnTrxHd.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
        transactionHeaderService.updateByPrimaryKey(request, rtnTrxHd);

        //
        // 更新原付款事务退款标志
        // ------------------------------------------------------------------------------
        setTrxReturnStatus(request, sourceTrxHd);

        //
        // 更新借款申请行付款状态
        // ------------------------------------------------------------------------------

    }

    /**
     * 费用报销单支付反冲
     *
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param reverseTrxLine 反冲现金事务行对象
     * @param reversedDate 反冲日期
     * @param periodName 期间
     * @return void
     * @author Tagin
     * @date 2019-05-28 16:50
     * @version 1.0
     **/
    private void reverseReport(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshTransactionLine reverseTrxLine, Date reversedDate,
                    String periodName) {
        String status;
        List<CshWriteOff> cshWriteOffs = writeOffService.queryRptWriteForReverse(
                        cshTransactionLine.getTransactionLineId(), cshTransactionHeader.getReturnedFlag());
        for (CshWriteOff cshWriteOff : cshWriteOffs) {
            // 1.0 校验金额
            BigDecimal payedAmount = cshWriteOffMapper.totalWriteAmount(cshWriteOff.getDocumentHeaderId(),
                            cshWriteOff.getDocumentLineId(), CshWriteOff.DOC_SOURCE_EXPENSE_REPORT, null, null, null);
            ExpReportHeader expReportHeader = expReportHeaderMapper.selectOne(
                            ExpReportHeader.builder().expReportHeaderId(cshWriteOff.getDocumentHeaderId()).build());
            ExpReportPmtSchedule expReportPmtSchedule = expReportPmtScheduleMapper.selectOne(ExpReportPmtSchedule
                            .builder().paymentScheduleLineId(cshWriteOff.getDocumentLineId()).build());
            if (payedAmount.subtract(cshWriteOff.getDocumentWriteOffAmount())
                            .compareTo(expReportPmtSchedule.getDueAmount()) > 0) {
                throw new CshTransactionException(CshTransactionException.REVERSED_REPORT_COMPLETE_ERROR,
                                CshTransactionException.REVERSED_REPORT_COMPLETE_ERROR, null);
            }
            // 2.0 生成反冲核销记录
            CshWriteOff dto = new CshWriteOff();
            BeanUtils.copyProperties(cshWriteOff, dto);
            dto.setWriteOffDate(reversedDate);
            dto.setPeriodName(periodName);
            dto.setCshWriteOffAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getCshWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getCshWriteOffAmount()));
            dto.setDocumentWriteOffAmount(new BigDecimal(-1)
                            .multiply(cshWriteOff.getDocumentWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getDocumentWriteOffAmount()));
            dto.setSourceWriteOffAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getSourceWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getSourceWriteOffAmount()));
            dto.setSourceCshTrxLineId(reverseTrxLine.getTransactionLineId());
            dto.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
            dto.setCreatedBy(iRequest.getUserId());
            dto.setCreationDate(DateUtils.getCurrentDate());
            dto.setLastUpdateDate(DateUtils.getCurrentDate());
            dto.setLastUpdatedBy(iRequest.getUserId());
            dto = writeOffService.insertSelective(iRequest, dto);
            // 3.0 关联合同或合同资金计划行
            // TODO Tips：暂时不处理合同资金计划逻辑
            // 4.0 反冲支付凭证
            List<CshTransactionAccount> cshTransactionAccounts = cshTransactionAccountMapper.queryAccount(
                            cshTransactionLine.getTransactionLineId(), null, null, null, cshWriteOff.getWriteOffId());
            for (CshTransactionAccount cshTransactionAccount : cshTransactionAccounts) {
                CshTransactionAccount trxAccount = new CshTransactionAccount();
                BeanUtils.copyProperties(cshTransactionAccount, trxAccount);
                trxAccount.setTransactionLineId(reverseTrxLine.getTransactionLineId());
                trxAccount.setWriteOffId(dto.getWriteOffId());
                trxAccount.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
                trxAccount.setPeriodName(periodName);
                trxAccount.setEnteredAmountDr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getEnteredAmountDr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getEnteredAmountDr()));
                trxAccount.setEnteredAmountCr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getEnteredAmountCr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getEnteredAmountCr()));
                trxAccount.setFunctionalAmountDr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getFunctionalAmountDr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getFunctionalAmountDr()));
                trxAccount.setFunctionalAmountCr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getFunctionalAmountCr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getFunctionalAmountCr()));
                trxAccount.setJournalDate(reversedDate);
                trxAccount.setJournalDateTz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
                trxAccount.setJournalDateLtz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
                trxAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                trxAccount.setCreatedBy(iRequest.getUserId());
                trxAccount.setCreationDate(DateUtils.getCurrentDate());
                trxAccount.setLastUpdatedBy(iRequest.getUserId());
                trxAccount.setLastUpdateDate(DateUtils.getCurrentDate());
                transactionAccountService.insertSelective(iRequest, trxAccount);
                // 创建现金流量记录
                // TODO Tips：暂时不处理现金流量项逻辑
            }
            // 5.0 更新报销单核销状态
            writeOffService.updateRptWriteStatus(iRequest, expReportHeader, expReportPmtSchedule, BaseConstants.NO);
            // 6.0 更新支付状态
            if (CshTransactionHeader.RETURNED_FLAG_R.equals(cshTransactionHeader.getReturnedFlag())) {
                status = CshDocPayAccEntity.STATUS_PAID;
            } else {
                status = CshDocPayAccEntity.STATUS_ALLOWED;
            }
            cshDocPayAccEntityService.updatePaymentStatus(cshWriteOff.getDocumentId(), cshWriteOff.getDocumentLineId(),
                            status, CshDocPayAccEntity.DOC_EXP_REPORT, iRequest);
            // 7.0 录入跟踪单据
            expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
                            cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_REVERSE,
                            iRequest.getEmployeeId(),
                            " 付款反冲，交易编号 [" + cshTransactionHeader.getTransactionNum() + "]，金额 "
                                            + new BigDecimal(-1).multiply(cshWriteOff.getCshWriteOffAmount()));
        }
    }

    /**
     * 借款申请单支付反冲
     *
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param reverseTrxLine 反冲现金事务行对象
     * @param reversedDate 反冲日期
     * @param periodName 期间
     * @return void
     * @author Tagin
     * @date 2019-05-28 20:24
     * @version 1.0
     **/
    public void reversePayment(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshTransactionLine reverseTrxLine, Date reversedDate,
                    String periodName) {
        List<CshWriteOff> cshWriteOffs = writeOffService.queryPayWriteForReverse(
                        cshTransactionLine.getTransactionLineId(), cshTransactionHeader.getTransactionHeaderId(),
                        cshTransactionHeader.getSourceHeaderId(), cshTransactionHeader.getReturnedFlag());
        for (CshWriteOff cshWriteOff : cshWriteOffs) {
            // 1.0 校验金额
            BigDecimal payedAmount = paymentRequisitionRefService.querySumAmount(cshWriteOff.getDocumentLineId());
            CshPaymentRequisitionLn cshPaymentRequisitionLn =
                            cshPaymentRequisitionLnMapper.selectOne(CshPaymentRequisitionLn.builder()
                                            .paymentRequisitionLineId(cshWriteOff.getDocumentLineId()).build());
            if (payedAmount.subtract(cshWriteOff.getCshWriteOffAmount())
                            .compareTo(cshPaymentRequisitionLn.getAmount()) > 0) {
                throw new CshTransactionException(CshTransactionException.REVERSED_PAYMENT_COMPLETE_ERROR,
                                CshTransactionException.REVERSED_PAYMENT_COMPLETE_ERROR, null);
            }
            // 2.0 获取预付款
            CshTransactionLine preTrxLine = cshTransactionLineMapper.selectOne(CshTransactionLine.builder()
                            .transactionLineId(cshWriteOff.getSourceCshTrxLineId()).build());
            CshTransactionHeader preTrxHeader = cshTransactionHeaderMapper.selectOne(CshTransactionHeader.builder()
                            .transactionHeaderId(preTrxLine.getTransactionHeaderId()).build());
            // 3.0 反冲预付款现金事务
            CshTransactionHeader reversePreTrxHeader = transactionHeaderService.insertTrxHeader(iRequest,
                            preTrxHeader.getTransactionType(), preTrxHeader.getMoCshTrxClassId(),
                            preTrxHeader.getCompanyId(), preTrxHeader.getAccEntityId(), preTrxHeader.getEmployeeId(),
                            " 付款反冲 [" + preTrxHeader.getTransactionNum() + "]", preTrxHeader.getEnabledWriteOffFlag(),
                            reversedDate, periodName, preTrxHeader.getPaymentMethodId(),
                            preTrxHeader.getTransactionCategory(), BaseConstants.YES,
                            CshTransactionHeader.REVERSED_FLAG_R, reversedDate, preTrxHeader.getReturnedFlag(),
                            BaseConstants.NO, null, preTrxHeader.getTransactionHeaderId(),
                            CshTransactionHeader.INTERFACE_FLAG_P, reverseTrxLine.getTransactionHeaderId(),
                            preTrxHeader.getEbankingFlag(), preTrxHeader.getDocumentCategory(),
                            preTrxHeader.getDocumentTypeId(), preTrxHeader.getPaymentUsedeId(),
                            preTrxHeader.getPayeeCategory(), preTrxHeader.getPayeeId(), preTrxHeader.getCurrencyCode(),
                            preTrxHeader.getBankAccountId());
            preTrxLine.setTransactionHeaderId(reversePreTrxHeader.getTransactionHeaderId());
            preTrxLine.setTransactionAmount(new BigDecimal(-1).multiply(preTrxLine.getTransactionAmount()));
            preTrxLine.setHandlingCharge(new BigDecimal(-1).multiply(
                            preTrxLine.getHandlingCharge() == null ? BigDecimal.ZERO : preTrxLine.getHandlingCharge()));
            preTrxLine.setInterest(new BigDecimal(-1)
                            .multiply(preTrxLine.getInterest() == null ? BigDecimal.ZERO : preTrxLine.getInterest()));
            CshTransactionLine reversePreTrxLine = transactionLineService.insertSelective(iRequest, preTrxLine);
            // 4.0 反冲核销记录
            CshWriteOff dto = new CshWriteOff();
            BeanUtils.copyProperties(cshWriteOff, dto);
            dto.setCshWriteOffAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getCshWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getCshWriteOffAmount()));
            dto.setDocumentWriteOffAmount(new BigDecimal(-1)
                            .multiply(cshWriteOff.getDocumentWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getDocumentWriteOffAmount()));
            dto.setSourceWriteOffAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getSourceWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getSourceWriteOffAmount()));
            dto.setSourceCshTrxLineId(reversePreTrxLine.getTransactionLineId());
            dto.setPeriodName(periodName);
            dto.setWriteOffDate(reversedDate);
            dto.setCreatedBy(iRequest.getUserId());
            dto.setCreationDate(DateUtils.getCurrentDate());
            dto.setLastUpdatedBy(iRequest.getUserId());
            dto.setLastUpdateDate(DateUtils.getCurrentDate());
            dto = writeOffService.insertSelective(iRequest, dto);
            // 5.0 反冲付款记录
            CshPaymentRequisitionRef cshPaymentRequisitionRef = new CshPaymentRequisitionRef();
            cshPaymentRequisitionRef.setPaymentRequisitionLineId(cshWriteOff.getDocumentLineId());
            cshPaymentRequisitionRef.setCshTransactionLineId(reversePreTrxLine.getTransactionLineId());
            cshPaymentRequisitionRef.setAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getCshWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getCshWriteOffAmount()));
            cshPaymentRequisitionRef.setWriteOffFlag(BaseConstants.NO);
            cshPaymentRequisitionRef.setWriteOffId(dto.getWriteOffId());
            cshPaymentRequisitionRef.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
            cshPaymentRequisitionRef.setCreatedBy(iRequest.getUserId());
            cshPaymentRequisitionRef.setCreationDate(DateUtils.getCurrentDate());
            cshPaymentRequisitionRef.setLastUpdatedBy(iRequest.getUserId());
            cshPaymentRequisitionRef.setLastUpdateDate(DateUtils.getCurrentDate());
            paymentRequisitionRefService.insertSelective(iRequest, cshPaymentRequisitionRef);
            // 6.0 录入跟踪单据
            expDocumentHistoryService.insertDocumentHistory(iRequest,
                            ExpDocumentHistory.DOCUMENT_TYPE_PAYMENT_REQUISITION, cshWriteOff.getDocumentHeaderId(),
                            ExpDocumentHistory.STATUS_REVERSE, iRequest.getEmployeeId(),
                            " 付款反冲，交易编号 [" + cshTransactionHeader.getTransactionNum() + "]，金额 "
                                            + new BigDecimal(-1).multiply(
                                                            cshWriteOff.getCshWriteOffAmount() == null ? BigDecimal.ZERO
                                                                            : cshWriteOff.getCshWriteOffAmount()));
            // 7.0 反冲合同资金计划
            // TODO Tips：暂时不处理合同资金计划逻辑
            // 8.0 反冲支付凭证
            List<CshTransactionAccount> cshTransactionAccounts = cshTransactionAccountMapper.queryAccount(
                            cshTransactionLine.getTransactionLineId(), null, null, null, cshWriteOff.getWriteOffId());
            for (CshTransactionAccount cshTransactionAccount : cshTransactionAccounts) {
                CshTransactionAccount trxAccount = new CshTransactionAccount();
                BeanUtils.copyProperties(cshTransactionAccount, trxAccount);
                trxAccount.setTransactionLineId(reverseTrxLine.getTransactionLineId());
                trxAccount.setWriteOffId(dto.getWriteOffId());
                trxAccount.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
                trxAccount.setPeriodName(periodName);
                trxAccount.setEnteredAmountDr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getEnteredAmountDr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getEnteredAmountDr()));
                trxAccount.setEnteredAmountCr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getEnteredAmountCr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getEnteredAmountCr()));
                trxAccount.setFunctionalAmountDr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getFunctionalAmountDr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getFunctionalAmountDr()));
                trxAccount.setFunctionalAmountCr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getFunctionalAmountCr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getFunctionalAmountCr()));
                trxAccount.setJournalDate(reversedDate);
                trxAccount.setJournalDateTz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
                trxAccount.setJournalDateLtz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
                trxAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                trxAccount.setCreatedBy(iRequest.getUserId());
                trxAccount.setCreationDate(DateUtils.getCurrentDate());
                trxAccount.setLastUpdatedBy(iRequest.getUserId());
                trxAccount.setLastUpdateDate(DateUtils.getCurrentDate());
                transactionAccountService.insertSelective(iRequest, trxAccount);
                // 创建现金流量记录
                // TODO Tips：暂时不处理现金流量项逻辑
            }
            // 9.0 更新预付款现金事务
            cshTransactionHeaderMapper.updateTrxHeaderFlag(null, null, CshTransactionHeader.REVERSED_FLAG_W,
                            reversedDate, preTrxHeader.getTransactionHeaderId(), null, null);
            // 10.0 更新借款还款现金事物对应的原借款单的付款现金事物行还款标志为N【因此时的事物为还款申请的反冲，需要将原单据现金事物还款标志冲回去】
            cshTransactionHeaderMapper.updateTrxHeaderFlag(null, CshTransactionHeader.RETURNED_FLAG_N, null, null,
                            preTrxHeader.getSourceHeaderId(), null, null);
            // 11.0 更新支付状态
            writeOffService.updatePaymentStatus(iRequest, cshPaymentRequisitionLn);
        }
    }

    /**
     * 付款申请单支付反冲
     *
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param reverseTrxLine 反冲现金事务行对象
     * @param reversedDate 反冲日期
     * @param periodName 期间
     * @return void
     * @author Tagin
     * @date 2019-05-29 14:17
     * @version 1.0
     **/
    public void reverseAcp(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshTransactionLine reverseTrxLine, Date reversedDate,
                    String periodName) {
        List<CshWriteOff> cshWriteOffs = writeOffService.queryAcpWriteForReverse(
                        cshTransactionLine.getTransactionLineId(), cshTransactionHeader.getReturnedFlag());
        for (CshWriteOff cshWriteOff : cshWriteOffs) {
            // 1.0 存在预付款则反冲预付款现金事务
            if (CshWriteOff.ACP_PREPAYMENT.equals(cshWriteOff.getWriteOffType())) {
                // 1.0.1 获取预付款
                CshTransactionLine preTrxLine = cshTransactionLineMapper.selectOne(CshTransactionLine.builder()
                                .transactionLineId(cshWriteOff.getSourceCshTrxLineId()).build());
                CshTransactionHeader preTrxHeader = cshTransactionHeaderMapper.selectOne(CshTransactionHeader.builder()
                                .transactionHeaderId(preTrxLine.getTransactionHeaderId()).build());
                // 1.0.2 反冲预付款现金事务
                CshTransactionHeader reversePreTrxHeader = transactionHeaderService.insertTrxHeader(iRequest,
                                preTrxHeader.getTransactionType(), preTrxHeader.getMoCshTrxClassId(),
                                preTrxHeader.getCompanyId(), preTrxHeader.getAccEntityId(),
                                preTrxHeader.getEmployeeId(), " 付款反冲 [" + preTrxHeader.getTransactionNum() + "]",
                                preTrxHeader.getEnabledWriteOffFlag(), reversedDate, periodName,
                                preTrxHeader.getPaymentMethodId(), preTrxHeader.getTransactionCategory(),
                                BaseConstants.YES, CshTransactionHeader.REVERSED_FLAG_R, reversedDate,
                                preTrxHeader.getReturnedFlag(), BaseConstants.NO, null,
                                preTrxHeader.getTransactionHeaderId(), CshTransactionHeader.INTERFACE_FLAG_P,
                                reverseTrxLine.getTransactionHeaderId(), preTrxHeader.getEbankingFlag(),
                                preTrxHeader.getDocumentCategory(), preTrxHeader.getDocumentTypeId(),
                                preTrxHeader.getPaymentUsedeId(), preTrxHeader.getPayeeCategory(),
                                preTrxHeader.getPayeeId(), preTrxHeader.getCurrencyCode(),
                                preTrxHeader.getBankAccountId());
                preTrxLine.setTransactionHeaderId(reversePreTrxHeader.getTransactionHeaderId());
                preTrxLine.setTransactionAmount(
                                new BigDecimal(-1).multiply(preTrxLine.getTransactionAmount() == null ? BigDecimal.ZERO
                                                : preTrxLine.getTransactionAmount()));
                preTrxLine.setHandlingCharge(
                                new BigDecimal(-1).multiply(preTrxLine.getHandlingCharge() == null ? BigDecimal.ZERO
                                                : preTrxLine.getHandlingCharge()));
                preTrxLine.setInterest(new BigDecimal(-1).multiply(
                                preTrxLine.getInterest() == null ? BigDecimal.ZERO : preTrxLine.getInterest()));
                CshTransactionLine reversePreTrxLine = transactionLineService.insertSelective(iRequest, preTrxLine);
                // 1.0.3 反冲付款申请核销报销单、合同的核销记录【此处为 反冲 付款申请单现金事务分配为预付款-用付款申请的付款金额去核销关联报销单计划付款行金额】
                List<CshWriteOff> cshWriteOffList =
                                cshWriteOffMapper.getWriteByType(CshWriteOff.PREPAYMENT_ACP_REQUISITION,
                                                cshWriteOff.getSourceCshTrxLineId(), null, null);
                for (CshWriteOff acpWrite : cshWriteOffList) {
                    CshWriteOff dto = new CshWriteOff();
                    BeanUtils.copyProperties(acpWrite, dto);
                    dto.setCshWriteOffAmount(new BigDecimal(-1)
                                    .multiply(cshWriteOff.getCshWriteOffAmount() == null ? BigDecimal.ZERO
                                                    : cshWriteOff.getCshWriteOffAmount()));
                    dto.setDocumentWriteOffAmount(new BigDecimal(-1)
                                    .multiply(cshWriteOff.getDocumentWriteOffAmount() == null ? BigDecimal.ZERO
                                                    : cshWriteOff.getDocumentWriteOffAmount()));
                    dto.setSourceWriteOffAmount(new BigDecimal(-1)
                                    .multiply(cshWriteOff.getSourceWriteOffAmount() == null ? BigDecimal.ZERO
                                                    : cshWriteOff.getSourceWriteOffAmount()));
                    dto.setSourceCshTrxLineId(reversePreTrxLine.getTransactionLineId());
                    dto.setPeriodName(periodName);
                    dto.setWriteOffDate(reversedDate);
                    dto.setCreatedBy(iRequest.getUserId());
                    dto.setCreationDate(DateUtils.getCurrentDate());
                    dto.setLastUpdateDate(DateUtils.getCurrentDate());
                    dto.setLastUpdatedBy(iRequest.getUserId());
                    writeOffService.insertSelective(iRequest, dto);
                }
                // 1.0.4 更新预付款现金事务 new Timestamp(DateUtils.getTimeStamp(reversedDate))
                cshTransactionHeaderMapper.updateTrxHeaderFlag(null, null, CshTransactionHeader.REVERSED_FLAG_W,
                                reversedDate, preTrxHeader.getTransactionHeaderId(), null, null);
                // 1.0.5
                cshTransactionHeaderMapper.updateTrxHeaderFlag(null, CshTransactionHeader.RETURNED_FLAG_N, null, null,
                                preTrxHeader.getSourceHeaderId(), null, null);
            }
            // 2.0 反冲零星付款及预付款的核销记录
            CshWriteOff dto = new CshWriteOff();
            BeanUtils.copyProperties(cshWriteOff, dto);
            dto.setCshWriteOffAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getCshWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getCshWriteOffAmount()));
            dto.setDocumentWriteOffAmount(new BigDecimal(-1)
                            .multiply(cshWriteOff.getDocumentWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getDocumentWriteOffAmount()));
            dto.setSourceWriteOffAmount(
                            new BigDecimal(-1).multiply(cshWriteOff.getSourceWriteOffAmount() == null ? BigDecimal.ZERO
                                            : cshWriteOff.getSourceWriteOffAmount()));
            dto.setPeriodName(periodName);
            dto.setWriteOffDate(reversedDate);
            dto.setCreatedBy(iRequest.getUserId());
            dto.setCreationDate(DateUtils.getCurrentDate());
            dto.setLastUpdatedBy(iRequest.getUserId());
            dto.setLastUpdateDate(DateUtils.getCurrentDate());
            dto = writeOffService.insertSelective(iRequest, dto);
            // 3.0 反冲付款申请
            List<AcpRequisitionRef> acpRequisitionRefs = acpRequisitionRefMapper
                            .queryRefByWrite(cshWriteOff.getWriteOffId(), cshTransactionLine.getTransactionLineId());
            for (AcpRequisitionRef acpRequisitionRef : acpRequisitionRefs) {
                AcpRequisitionRef ref = new AcpRequisitionRef();
                BeanUtils.copyProperties(acpRequisitionRef, ref);
                ref.setAmount(new BigDecimal(-1).multiply(acpRequisitionRef.getAmount() == null ? BigDecimal.ZERO
                                : acpRequisitionRef.getAmount()));
                ref.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
                ref.setCreatedBy(iRequest.getUserId());
                ref.setCreationDate(DateUtils.getCurrentDate());
                ref.setLastUpdateDate(DateUtils.getCurrentDate());
                ref.setLastUpdatedBy(iRequest.getUserId());
                ref.setCreatedBy(iRequest.getUserId());
                ref.setCreationDate(DateUtils.getCurrentDate());
                ref.setLastUpdatedBy(iRequest.getUserId());
                ref.setLastUpdateDate(DateUtils.getCurrentDate());
                acpRequisitionRefService.insertSelective(iRequest, ref);
                // 3.0.1 写入单据历史
                expDocumentHistoryService.insertDocumentHistory(iRequest,
                                ExpDocumentHistory.DOCUMENT_TYPE_ACP_REQUISITION, cshWriteOff.getDocumentHeaderId(),
                                ExpDocumentHistory.STATUS_REVERSE, iRequest.getEmployeeId(),
                                " 付款反冲，交易编号 [" + cshTransactionHeader.getTransactionNum() + "]，金额 "
                                                + new BigDecimal(-1).multiply(cshWriteOff.getCshWriteOffAmount()));
                // 3.0.2 更新支付状态
                writeOffService.updateAcpPaymentStatus(iRequest, acpRequisitionLnMapper.selectOne(AcpRequisitionLn
                                .builder().requisitionLnsId(acpRequisitionRef.getRequisitionLnsId()).build()));
            }
            // 4.0 反冲合同资金计划
            // TODO Tips：暂时不处理合同资金计划逻辑
            // 5.0 反冲支付凭证
            List<CshTransactionAccount> cshTransactionAccounts = cshTransactionAccountMapper.queryAccount(
                            cshTransactionLine.getTransactionLineId(), null, null, null, cshWriteOff.getWriteOffId());
            for (CshTransactionAccount cshTransactionAccount : cshTransactionAccounts) {
                CshTransactionAccount trxAccount = new CshTransactionAccount();
                BeanUtils.copyProperties(cshTransactionAccount, trxAccount);
                trxAccount.setTransactionLineId(reverseTrxLine.getTransactionLineId());
                trxAccount.setWriteOffId(dto.getWriteOffId());
                trxAccount.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
                trxAccount.setPeriodName(periodName);
                trxAccount.setEnteredAmountDr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getEnteredAmountDr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getEnteredAmountDr()));
                trxAccount.setEnteredAmountCr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getEnteredAmountCr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getEnteredAmountCr()));
                trxAccount.setFunctionalAmountDr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getFunctionalAmountDr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getFunctionalAmountDr()));
                trxAccount.setFunctionalAmountCr(new BigDecimal(-1)
                                .multiply(cshTransactionAccount.getFunctionalAmountCr() == null ? BigDecimal.ZERO
                                                : cshTransactionAccount.getFunctionalAmountCr()));
                trxAccount.setJournalDate(reversedDate);
                trxAccount.setJournalDateTz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
                trxAccount.setJournalDateLtz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
                trxAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                trxAccount.setCreatedBy(iRequest.getUserId());
                trxAccount.setCreationDate(DateUtils.getCurrentDate());
                trxAccount.setLastUpdatedBy(iRequest.getUserId());
                trxAccount.setLastUpdateDate(DateUtils.getCurrentDate());
                transactionAccountService.insertSelective(iRequest, trxAccount);
                // 创建现金流量记录
                // TODO Tips：暂时不处理现金流量项逻辑
            }
            // 6.0 反冲核销凭证
            List<CshWriteOffAccount> cshWriteOffAccounts =
                            cshWriteOffAccountMapper.queryAccount(cshWriteOff.getWriteOffId(), null, null, null);
            for (CshWriteOffAccount cshWriteOffAccount : cshWriteOffAccounts) {
                CshWriteOffAccount writeAccount = new CshWriteOffAccount();
                BeanUtils.copyProperties(cshWriteOffAccount, writeAccount);
                writeAccount.setWriteOffId(dto.getWriteOffId());
                writeAccount.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
                writeAccount.setPeriodName(periodName);
                writeAccount.setEnteredAmountDr(new BigDecimal(-1)
                                .multiply(cshWriteOffAccount.getEnteredAmountDr() == null ? BigDecimal.ZERO
                                                : cshWriteOffAccount.getEnteredAmountDr()));
                writeAccount.setEnteredAmountCr(new BigDecimal(-1)
                                .multiply(cshWriteOffAccount.getEnteredAmountCr() == null ? BigDecimal.ZERO
                                                : cshWriteOffAccount.getEnteredAmountCr()));
                writeAccount.setFunctionalAmountDr(new BigDecimal(-1)
                                .multiply(cshWriteOffAccount.getFunctionalAmountDr() == null ? BigDecimal.ZERO
                                                : cshWriteOffAccount.getFunctionalAmountDr()));
                writeAccount.setFunctionalAmountCr(new BigDecimal(-1)
                                .multiply(cshWriteOffAccount.getFunctionalAmountCr() == null ? BigDecimal.ZERO
                                                : cshWriteOffAccount.getFunctionalAmountCr()));
                writeAccount.setJournalDate(reversedDate);
                writeAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
                writeAccount.setCreatedBy(iRequest.getUserId());
                writeAccount.setCreationDate(DateUtils.getCurrentDate());
                writeAccount.setLastUpdateDate(DateUtils.getCurrentDate());
                writeAccount.setLastUpdatedBy(iRequest.getUserId());
                cshWriteOffAccountService.insertSelective(iRequest, writeAccount);
            }
        }
    }

    /**
     * 付款反冲
     *
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param reversedDate 反冲日期
     * @return void
     * @author Tagin
     * @date 2019-05-28 10:05
     * @version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void postReverseTransaction(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, Date reversedDate) {
        // 1.0 退款校验
        if (CshTransactionHeader.RETURNED_FLAG_Y.equals(cshTransactionHeader.getReturnedFlag())
                        || CshTransactionHeader.RETURNED_FLAG_C.equals(cshTransactionHeader.getReturnedFlag())) {
            throw new CshTransactionException(CshTransactionException.REVERSED_RETURN_ERROR,
                            CshTransactionException.REVERSED_RETURN_ERROR, null);
        }
        // 2.0 校验反冲
        if (!CshTransactionHeader.REVERSED_FLAG_N.equals(cshTransactionHeader.getReversedFlag())) {
            throw new CshTransactionException(CshTransactionException.REVERSED_COMPLETE_ERROR,
                            CshTransactionException.REVERSED_COMPLETE_ERROR, null);
        }
        // 3.0 校验预付款事务是否被核销
        if (transactionLineService.totalPreCount(cshTransactionHeader.getTransactionHeaderId()) != 0) {
            throw new CshTransactionException(CshTransactionException.PREPAYMENT_WRITE_OFF_ERROR,
                            CshTransactionException.PREPAYMENT_WRITE_OFF_ERROR, null);
        }
        // 4.0 校验反冲日期
        reversedDate = DateUtils.str2Date(DateUtils.date2Str(reversedDate, null), null);
        Date transactionDate =
                        DateUtils.str2Date(DateUtils.date2Str(cshTransactionHeader.getTransactionDate(), null), null);
        if (reversedDate.before(transactionDate)) {
            throw new CshTransactionException(CshTransactionException.REVERSED_DATE_ERROR,
                            CshTransactionException.REVERSED_DATE_ERROR, null);
        }
        // 5.0 获取期间
        String periodName = gldPeriodService.getPeriodName(iRequest, reversedDate,
                        cshTransactionHeader.getAccEntityId(), GldPeriod.STATUS_CODE_O);
        if (StringUtils.isEmpty(periodName)) {
            throw new CshTransactionException(CshTransactionException.REVERSED_PERIOD_NOT_OPEN_ERROR,
                            CshTransactionException.REVERSED_PERIOD_NOT_OPEN_ERROR, null);
        }
        // 6.0 生成现金事务
        CshTransactionHeader reverseTrxHeader = transactionHeaderService.insertTrxHeader(iRequest,
                        cshTransactionHeader.getTransactionType(), cshTransactionHeader.getMoCshTrxClassId(),
                        cshTransactionHeader.getCompanyId(), cshTransactionHeader.getAccEntityId(),
                        cshTransactionHeader.getEmployeeId(),
                        " 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]",
                        cshTransactionHeader.getEnabledWriteOffFlag(), reversedDate, periodName,
                        cshTransactionHeader.getPaymentMethodId(), cshTransactionHeader.getTransactionCategory(),
                        CshTransactionHeader.POSTED_FLAG_Y, CshTransactionHeader.REVERSED_FLAG_R, reversedDate,
                        cshTransactionHeader.getReturnedFlag(), CshWriteOff.WRITE_OFF_FLAG_N, null,
                        cshTransactionHeader.getTransactionHeaderId(), CshTransactionHeader.INTERFACE_FLAG_P, null,
                        cshTransactionHeader.getEbankingFlag(), cshTransactionHeader.getDocumentCategory(),
                        cshTransactionHeader.getDocumentTypeId(), cshTransactionHeader.getPaymentUsedeId(),
                        cshTransactionHeader.getPayeeCategory(), cshTransactionHeader.getPayeeId(),
                        cshTransactionHeader.getCurrencyCode(), cshTransactionHeader.getBankAccountId());
        CshTransactionLine reverseTrxLine = new CshTransactionLine();
        BeanUtils.copyProperties(cshTransactionLine, reverseTrxLine);
        reverseTrxLine.setTransactionHeaderId(reverseTrxHeader.getTransactionHeaderId());
        reverseTrxLine.setTransactionAmount(new BigDecimal(-1).multiply(cshTransactionLine.getTransactionAmount()));
        reverseTrxLine.setHandlingCharge(cshTransactionLine.getHandlingCharge() == null ? null
                        : new BigDecimal(-1).multiply(cshTransactionLine.getHandlingCharge()));
        reverseTrxLine.setInterest(cshTransactionLine.getInterest() == null ? null
                        : new BigDecimal(-1).multiply(cshTransactionLine.getInterest()));
        reverseTrxLine = transactionLineService.insertSelective(iRequest, reverseTrxLine);
        // 7.0 反冲
        if (CshTransactionHeader.TRX_CATEGORY_BUSINESS.equals(cshTransactionHeader.getTransactionCategory())) {
            // 7.0.1 反冲银行账户凭证
            CshTransactionAccount cshTransactionAccount =
                            transactionAccountService.queryAccount(cshTransactionLine.getTransactionLineId(),
                                            UsageCodeConstants.USAGE_CASH_ACCOUNT, null, null, null);
            cshTransactionAccount.setTransactionLineId(reverseTrxLine.getTransactionLineId());
            cshTransactionAccount.setDescription(" 付款反冲 [" + cshTransactionHeader.getTransactionNum() + "]");
            cshTransactionAccount.setEnteredAmountDr(new BigDecimal(-1)
                            .multiply(cshTransactionAccount.getEnteredAmountDr() == null ? BigDecimal.ZERO
                                            : cshTransactionAccount.getEnteredAmountDr()));
            cshTransactionAccount.setEnteredAmountCr(new BigDecimal(-1)
                            .multiply(cshTransactionAccount.getEnteredAmountCr() == null ? BigDecimal.ZERO
                                            : cshTransactionAccount.getEnteredAmountCr()));
            cshTransactionAccount.setFunctionalAmountDr(new BigDecimal(-1)
                            .multiply(cshTransactionAccount.getFunctionalAmountDr() == null ? BigDecimal.ZERO
                                            : cshTransactionAccount.getFunctionalAmountDr()));
            cshTransactionAccount.setFunctionalAmountCr(new BigDecimal(-1)
                            .multiply(cshTransactionAccount.getFunctionalAmountCr() == null ? BigDecimal.ZERO
                                            : cshTransactionAccount.getFunctionalAmountCr()));
            cshTransactionAccount.setJournalDate(reversedDate);
            cshTransactionAccount.setJournalDateTz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
            cshTransactionAccount.setJournalDateLtz(new Timestamp(DateUtils.getTimeStamp(reversedDate)));
            cshTransactionAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
            cshTransactionAccount.setCreatedBy(iRequest.getUserId());
            cshTransactionAccount.setCreationDate(DateUtils.getCurrentDate());
            cshTransactionAccount.setLastUpdatedBy(iRequest.getUserId());
            cshTransactionAccount.setLastUpdateDate(DateUtils.getCurrentDate());
            transactionAccountService.insertSelective(iRequest, cshTransactionAccount);

            // 7.0.2 若存在现金流量项录入现金流量项
            // TODO Tips：现金流量项暂未补充

            // 7.0.3 费用报销单支付反冲
            reverseReport(iRequest, cshTransactionHeader, cshTransactionLine, reverseTrxLine, reversedDate, periodName);
            // 7.0.4 借款申请单支付反冲
            reversePayment(iRequest, cshTransactionHeader, cshTransactionLine, reverseTrxLine, reversedDate,
                            periodName);
            // 7.0.5 付款申请单支付反冲
            reverseAcp(iRequest, cshTransactionHeader, cshTransactionLine, reverseTrxLine, reversedDate, periodName);
        }
        // 8.0 更新付款现金事务反冲状态
        cshTransactionHeaderMapper.updateTrxHeaderFlag(null, null, CshTransactionHeader.REVERSED_FLAG_W, reversedDate,
                        cshTransactionHeader.getTransactionHeaderId(), null, null);
        // 9.0 反冲还款
        // TODO Tips：此处应该不涉及还款信息
        // 10.0 反冲凭证入分录表
        glAccountEntryService.headerGlAccountEntry(iRequest, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                        reverseTrxHeader.getTransactionHeaderId());
    }

    @Override
    public void createJournalCashFlow(IRequest request, Long accEntityId, CshTransactionLine line,
                    Long cashFlowItemId) {
        //
        // 暂未实现
        // ------------------------------------------------------------------------------
    }

    public void checkAccountDrCrBalance(IRequest request, CshTransactionHeader header, CshTransactionLine line) {
        CshTransactionAccount queryAccount = new CshTransactionAccount();
        queryAccount.setTransactionLineId(line.getTransactionLineId());
        List<CshTransactionAccount> accountList = transactionAccountService.select(request, queryAccount, 0, 0);
        BigDecimal enteredDrTotal = new BigDecimal(0);
        BigDecimal enteredCrTotal = new BigDecimal(0);
        BigDecimal functionalDrTotal = new BigDecimal(0);
        BigDecimal functionalCrTotal = new BigDecimal(0);

        for (CshTransactionAccount account : accountList) {
            if (account.getEnteredAmountDr() != null) {
                enteredDrTotal = enteredDrTotal.add(account.getEnteredAmountDr());
            }

            if (account.getEnteredAmountCr() != null) {
                enteredCrTotal = enteredCrTotal.add(account.getEnteredAmountCr());
            }

            if (account.getFunctionalAmountDr() != null) {
                functionalDrTotal = functionalDrTotal.add(account.getFunctionalAmountDr());
            }

            if (account.getFunctionalAmountCr() != null) {
                functionalCrTotal = functionalCrTotal.add(account.getFunctionalAmountCr());
            }
        }

        if (enteredDrTotal.compareTo(enteredCrTotal) != 0 || functionalDrTotal.compareTo(functionalCrTotal) != 0) {
            throw new CshTransactionException("CSH", CshTransactionException.DR_CR_AMOUNT_ERROR, null);
        }
    }

}
