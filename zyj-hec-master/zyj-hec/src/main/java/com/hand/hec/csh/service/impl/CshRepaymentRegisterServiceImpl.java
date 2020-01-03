package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshRepaymentRegisterException;
import com.hand.hec.csh.mapper.CshRepaymentRegisterLnMapper;
import com.hand.hec.csh.mapper.CshRepaymentRegisterMapper;
import com.hand.hec.csh.mapper.CshTransactionLineMapper;
import com.hand.hec.csh.mapper.CshWriteOffMapper;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.service.IGlAccountEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/04/30 11:00
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CshRepaymentRegisterServiceImpl implements ICshRepaymentRegisterService {

    @Autowired
    private CshRepaymentRegisterLnMapper lineMapper;

    @Autowired
    private ICshRepaymentRegisterHdService hdService;

    @Autowired
    private ICshRepaymentRegisterLnService lnService;

    @Autowired
    private CshRepaymentRegisterMapper registerMapper;

    @Autowired
    private ICshRepaymentRegisterDistService distService;

    @Autowired
    private IExpDocumentHistoryService historyService;

    @Autowired
    private DatabaseLockProvider databaseLockProvider;

    @Autowired
    private ISysParameterService parameterService;

    @Autowired
    private IGldPeriodService periodService;

    @Autowired
    private CshTransactionLineMapper trxLineMapper;

    @Autowired
    private ICshTransactionLineService transactionLineService;

    @Autowired
    private ICshTransactionHeaderService transactionHeaderService;

    @Autowired
    private CshWriteOffMapper writeOffMapper;

    @Autowired
    private ICshWriteOffService writeOffService;

    @Autowired
    private ICshTransactionService transactionService;

    @Autowired
    private ICshPaymentRequisitionHdService paymentRequisitionHdService;

    @Autowired
    private IExpDocumentHistoryService expDocumentHistoryService;

    @Autowired
    private IGlAccountEntryService glAccountEntryService;

    @Override
    public void lockRegister(Long registerHdsId) {
        // 锁表
        databaseLockProvider.lock(new CshRepaymentRegisterHd(), "register_hds_id=" + registerHdsId, null);
    }

    @Override
    public void amountCheck(IRequest request, CshRepaymentRegisterHd registerHd) {

        List<CshRepaymentRegisterLn> lns = lineMapper.select(CshRepaymentRegisterLn.builder().registerHdsId(registerHd.getRegisterHdsId()).build());

        if (lns.isEmpty()) {
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_LINE_NOT_EXISTS, null);
        }

        //行金额汇总
        BigDecimal amount = lns.parallelStream().map(CshRepaymentRegisterLn::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 如果头金额和行金额汇总不一致，抛出异常
        if (amount.compareTo(registerHd.getAmount()) != 0) {
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_DOC_AMOUNT_ERROR,
                    null);
        }

        // 如果币种不一致，抛出异常
        int result = registerMapper.validateCurrency(registerHd);
        if (result != 0) {
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_CURRENCY_ERROR,
                    null);
        }

        //
        // 遍历所有行，检查借款单付款的余额是否进行本次退款
        // ------------------------------------------------------------------------------
        for (CshRepaymentRegisterLn ln : lns) {
            BigDecimal leftPaymentAmount = self().sumLeftPaymentAmount(request, ln);
            BigDecimal lnAmount = registerMapper.sumRepaymentAmount(ln);
            if (lnAmount.compareTo(leftPaymentAmount) > 0) {
                // 如果本次还款金额大于支付剩余金额，抛出异常
                throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_CURRENCY_ERROR,
                        null);
            }

            //
            // 如果当前状态为提交或者出纳确认
            // 1.校验分配行金额合计是否等于行金额
            // 2.校验分配行金额是否小于分配行关联的付款剩余可退款金额
            // ------------------------------------------------------------------------------

            if (CshRepaymentRegisterHd.STATUS_SUBMITTED.equals(registerHd.getRepaymentStatus())
                    || CshRepaymentRegisterHd.STATUS_CASHIER_CONFIRM.equals(registerHd.getRepaymentStatus())) {
                CshRepaymentRegisterDist queryDist = new CshRepaymentRegisterDist();
                queryDist.setRegisterLnsId(ln.getRegisterLnsId());
                List<CshRepaymentRegisterDist> dists = distService.select(request, queryDist, 0, 0);
                BigDecimal totalDistAmount = new BigDecimal(0);
                for (CshRepaymentRegisterDist dist : dists) {
                    totalDistAmount = totalDistAmount.add(dist.getAmount());
                }

                if (totalDistAmount.compareTo(ln.getAmount()) != 0) {
                    // 如果分配行金额之和不等于行金额，抛出异常
                    throw new CshRepaymentRegisterException("CSH",
                            CshRepaymentRegisterException.CHECK_DIST_AMOUNT_ERROR, null);
                }
                List<Map> trxGroupList = self().selectRepayTrxByLn(request, ln);
                for (Map trxGroup : trxGroupList) {
                    BigDecimal trxLeftAmount =
                            self().sumLeftTrxAmount(request, (Long) trxGroup.get("repay_trx_line_id"));
                    BigDecimal sumDistAmount = new BigDecimal(trxGroup.get("sum_amount").toString());

                    if (sumDistAmount.compareTo(trxLeftAmount) > 0) {
                        // 如果分配行的针对某个现金事物的还款金额大于这个现金事物的可还款金额，抛出异常
                        throw new CshRepaymentRegisterException("CSH",
                                CshRepaymentRegisterException.CHECK_DIST_AMOUNT_EXCEED, null);
                    }
                }
            }
        }
    }

    /**
     * 还款登记单提交检查
     *
     * @return void
     * @author mouse 2019-04-30 11:05
     */
    @Override
    public void submitCheck(IRequest request, CshRepaymentRegisterHd registerHd) {
        if (!(CshRepaymentRegisterHd.STATUS_GENERATE.equals(registerHd.getRepaymentStatus())
                || CshRepaymentRegisterHd.STATUS_TAKEN_BACK.equals(registerHd.getRepaymentStatus())
                || CshRepaymentRegisterHd.STATUS_CASHIER_REJECT.equals(registerHd.getRepaymentStatus()))) {
            // 如果单据状态不为新建、收回、出纳拒绝，抛出异常
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_STATUS_ERROR, null);
        }
        self().amountCheck(request, registerHd);
    }

    @Override
    public void cashierConfirmCheck(IRequest request, CshRepaymentRegisterHd registerHd) {
        if (!(CshRepaymentRegisterHd.STATUS_SUBMITTED.equals(registerHd.getRepaymentStatus())
                || CshRepaymentRegisterHd.STATUS_ACCOUNTING_REJECT.equals(registerHd.getRepaymentStatus()))) {
            // 如果单据状态不为新建、收回、出纳拒绝，抛出异常
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_STATUS_ERROR, null);
        }
        self().amountCheck(request, registerHd);
    }

    @Override
    public void cashierRejectCheck(IRequest request, CshRepaymentRegisterHd registerHd) {
        if (!(CshRepaymentRegisterHd.STATUS_SUBMITTED.equals(registerHd.getRepaymentStatus())
                || CshRepaymentRegisterHd.STATUS_ACCOUNTING_REJECT.equals(registerHd.getRepaymentStatus()))) {
            // 如果单据状态不为新建、收回、出纳拒绝，抛出异常
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_STATUS_ERROR, null);
        }
    }

    @Override
    public void accountingConfirmCheck(IRequest request, CshRepaymentRegisterHd registerHd) {
        if (!CshRepaymentRegisterHd.STATUS_CASHIER_CONFIRM.equals(registerHd.getRepaymentStatus())) {
            // 如果单据状态不为出纳同意，抛出异常
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_STATUS_ERROR, null);
        }
        self().amountCheck(request, registerHd);
    }

    @Override
    public void accountingRejectCheck(IRequest request, CshRepaymentRegisterHd registerHd) {
        if (!CshRepaymentRegisterHd.STATUS_CASHIER_CONFIRM.equals(registerHd.getRepaymentStatus())) {
            // 如果单据状态不为出纳同意，抛出异常
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.CHECK_STATUS_ERROR, null);
        }
    }

    @Override
    public BigDecimal sumLeftPaymentAmount(IRequest request, CshRepaymentRegisterLn ln) {
        return registerMapper.sumLeftPaymentAmount(ln);
    }


    @Override
    public List<Map> selectRepayTrxByLn(IRequest request, CshRepaymentRegisterLn ln) {
        return registerMapper.selectRepayTrxByLn(ln);
    }

    @Override
    public BigDecimal sumLeftTrxAmount(IRequest request, Long repayTrxLineId) {
        return registerMapper.sumLeftTrxAmount(repayTrxLineId);
    }

    @Override
    public void cashierConfirm(IRequest request, CshRepaymentRegisterHd registerHd) {
        lockRegister(registerHd.getRegisterHdsId());

        cashierConfirmCheck(request, registerHd);
        registerHd.setRepaymentStatus(CshRepaymentRegisterHd.STATUS_CASHIER_CONFIRM);
        registerHd.setCashierConfirmDate(new Date());
        registerHd.setCashierUserId(request.getUserId());
        hdService.updateByPrimaryKeyOptions(request, registerHd, null);
        historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_CSH_REPAYMENT_REGISTER,
                registerHd.getRegisterHdsId(), ExpDocumentHistory.STATUS_CASHIER_CONFIRM, request.getUserId(), null);

        completelyConfirm(request, registerHd);

    }

    @Override
    public void cashierReject(IRequest request, CshRepaymentRegisterHd registerHd) {
        self().cashierRejectCheck(request, registerHd);

        registerHd.setRepaymentStatus(CshRepaymentRegisterHd.STATUS_CASHIER_REJECT);
        registerHd.setCashierConfirmDate(null);
        hdService.updateByPrimaryKey(request, registerHd);

        //
        // 删除分配行信息
        // ------------------------------------------------------------------------------
        CshRepaymentRegisterLn queryLn = new CshRepaymentRegisterLn();
        List<CshRepaymentRegisterLn> lns = lnService.select(request, queryLn, 0, 0);
        for (CshRepaymentRegisterLn ln : lns) {
            CshRepaymentRegisterDist queryDist = new CshRepaymentRegisterDist();
            queryDist.setRegisterLnsId(ln.getRegisterLnsId());
            List<CshRepaymentRegisterDist> dists = distService.select(request, queryDist, 0, 0);
            for (CshRepaymentRegisterDist dist : dists) {
                distService.deleteByPrimaryKey(dist);
            }
        }

        //
        // 插入单据历史
        // ------------------------------------------------------------------------------
        expDocumentHistoryService.insertDocumentHistory(request,
                ExpDocumentHistory.DOCUMENT_TYPE_CSH_REPAYMENT_REGISTER, registerHd.getRegisterHdsId(),
                ExpDocumentHistory.STATUS_CASHIER_REJECTED, request.getUserId(), "");
    }

    @Override
    public void accountingConfirm(IRequest request, CshRepaymentRegisterHd registerHd) {
        lockRegister(registerHd.getRegisterHdsId());
        accountingConfirmCheck(request, registerHd);

        registerHd.setRepaymentStatus(CshRepaymentRegisterHd.STATUS_ACCOUNTING_CONFIRM);
        registerHd.setAccountingConfirmDate(new Date());
        registerHd.setAccountingUserId(request.getUserId());
        hdService.updateByPrimaryKey(request, registerHd);

        //
        // 插入单据历史
        // ------------------------------------------------------------------------------
        expDocumentHistoryService.insertDocumentHistory(request,
                ExpDocumentHistory.DOCUMENT_TYPE_CSH_REPAYMENT_REGISTER, registerHd.getRegisterHdsId(),
                ExpDocumentHistory.STATUS_ACCOUNTING_CONFIRM, request.getEmployeeId(), "");

    }

    @Override
    public void accountingReject(IRequest request, CshRepaymentRegisterHd registerHd) {
        self().accountingRejectCheck(request, registerHd);

        registerHd.setRepaymentStatus(CshRepaymentRegisterHd.STATUS_ACCOUNTING_REJECT);
        registerHd.setAccountingConfirmDate(null);
        hdService.updateByPrimaryKey(request, registerHd);

        //
        // 插入单据历史
        // ------------------------------------------------------------------------------
        expDocumentHistoryService.insertDocumentHistory(request,
                ExpDocumentHistory.DOCUMENT_TYPE_CSH_REPAYMENT_REGISTER, registerHd.getRegisterHdsId(),
                ExpDocumentHistory.STATUS_ACCOUNTING_REJECTED, request.getEmployeeId(), "");
    }

    /**
     * 还款申请单完全确认【生成现金事务及凭证/调用付款退款逻辑】
     *
     * @param request
     * @param registerHd
     * @return void
     * @author mouse 2019-04-30 16:46
     */
    private void completelyConfirm(IRequest request, CshRepaymentRegisterHd registerHd) {
        String periodName = periodService.getPeriodName(request, new Date(), registerHd.getAccEntityId(), "O");

        // 期间不存在
        if (periodName == null) {
            throw new CshRepaymentRegisterException("CSH", CshRepaymentRegisterException.PERIOD_NOT_EXISTS, null);
        }

        List<Map> csrDists = selectRepaymentDistForAcc(request, registerHd);

        for (Map csrDist : csrDists) {
            //
            // 获取本次还款对应的现金事物行
            // ------------------------------------------------------------------------------
            CshTransactionLine sourceTrxLine = new CshTransactionLine();
            sourceTrxLine.setTransactionLineId(Long.parseLong(csrDist.get("pay_trx_line_id").toString()));
            sourceTrxLine = transactionLineService.selectByPrimaryKey(request, sourceTrxLine);

            CshTransactionHeader trxHeader = transactionHeaderService.insertTrxHeader(request,
                    CshTransactionHeader.TRX_TYPE_PAYMENT, null, registerHd.getCompanyId(),
                    registerHd.getAccEntityId(), registerHd.getEmployeeId(),
                    "还款申请单 [" + registerHd.getRegisterNumber() + "] 完全确认", "N", new Date(), periodName,
                    registerHd.getPaymentMethodId(), "BUSINESS", "N", "N", null, "R", "N", null,
                    Long.parseLong(csrDist.get("pay_trx_header_id").toString()), "N", null, "N", null, null,
                    request.getUserId(), null, null, null, null);

            CshTransactionLine trxLine = transactionLineService.insertTrxLine(request, sourceTrxLine.getCurrencyCode(),
                    sourceTrxLine.getExchangeRateType(), sourceTrxLine.getExchangeRate(),
                    registerHd.getBankAccountId(), sourceTrxLine.getDocumentNum(),
                    sourceTrxLine.getPayeeCategory(), sourceTrxLine.getPayeeId(),
                    sourceTrxLine.getPayeeBankAccountId(),
                    "还款申请单 [" + registerHd.getRegisterNumber() + "] 完全确认", sourceTrxLine.getHandlingCharge(),
                    sourceTrxLine.getInterest(), sourceTrxLine.getAgentEmployeeId(),
                    sourceTrxLine.getTransInOutType(), null, null, null, null,
                    trxHeader.getTransactionHeaderId(), Long.parseLong(csrDist.get("company_id").toString()),
                    Long.parseLong(csrDist.get("acc_entity_id").toString()),
                    new BigDecimal(csrDist.get("amount").toString()));

            Long payTrxLineId = Long.parseLong(csrDist.get("pay_trx_line_id").toString());
            Long prepayTrxLineId = Long.parseLong(csrDist.get("repay_trx_line_id").toString());

            //
            // 查询原付款核销预付款的核销记录
            // ------------------------------------------------------------------------------
            CshWriteOff sourceWriteOff = new CshWriteOff();
            sourceWriteOff.setCshTransactionLineId(payTrxLineId);
            sourceWriteOff.setSourceCshTrxLineId(prepayTrxLineId);
            sourceWriteOff = writeOffMapper.selectOne(sourceWriteOff);

            List<CshTransactionReturn> trxRtnList = new ArrayList<>();
            CshTransactionReturn trxReturn = new CshTransactionReturn();
            trxReturn.setReturnAmount(new BigDecimal(csrDist.get("amount").toString()));
            trxReturn.setSourceType("CSH_WRITE_OFF");
            trxReturn.setSourceId(sourceWriteOff.getWriteOffId());
            trxRtnList.add(trxReturn);

            //
            // 执行退款过账操作
            // ------------------------------------------------------------------------------
            transactionService.postReturnTransaction(request, trxHeader, trxLine,
                    Long.parseLong(csrDist.get("cash_flow_item_id").toString()), trxRtnList);

            BigDecimal repayableAmount = registerMapper.getRepayableAmount(prepayTrxLineId);

            //
            // 关闭借款申请单
            // ------------------------------------------------------------------------------
            paymentRequisitionHdService.refundClosePaymentRequisition(request,
                    Long.parseLong(csrDist.get("payment_requisition_header_id").toString()), repayableAmount,
                    new BigDecimal(csrDist.get("amount").toString()), "REPAYMENT", new Date());

            //
            // 获取退款预付款事物,并更新还款事物ID及还款预付款事物ID
            // ------------------------------------------------------------------------------
            CshTransactionLine rtnPrepayTrxLn = new CshTransactionLine();
            rtnPrepayTrxLn.setTransactionHeaderId(trxHeader.getTransactionHeaderId());
            rtnPrepayTrxLn = trxLineMapper.selectOne(CshTransactionLine.builder().transactionHeaderId(trxHeader.getTransactionHeaderId()).build());

            distService.updateByPrimaryKeySelective(request, CshRepaymentRegisterDist.builder()
                    .registerDistId(Long.parseLong(csrDist.get("register_dist_id").toString()))
                    .repaymentPayTrxLineId(trxLine.getTransactionLineId())
                    .repaymentPayTrxLineId(rtnPrepayTrxLn.getTransactionLineId())
                    .build());
            //录入分录表
            glAccountEntryService.headerGlAccountEntry(request, GlAccountEntry.RULE_TYPE_CSH_TRANSACTION,
                    trxHeader.getTransactionHeaderId());
        }

        //
        // 更新还款登记单状态
        // ------------------------------------------------------------------------------
        registerHd.setRepaymentStatus(CshRepaymentRegisterHd.STATUS_CASHIER_CONFIRM);
        hdService.updateByPrimaryKey(request, registerHd);


    }

    @Override
    public List<Map> selectRepaymentDistForAcc(IRequest request, CshRepaymentRegisterHd hd) {
        return registerMapper.selectRepaymentDistForAcc(hd);
    }

}
