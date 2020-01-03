package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshPaymentRequisitionLnException;
import com.hand.hec.csh.mapper.CshPaymentRequisitionLnMapper;
import com.hand.hec.csh.mapper.CshPaymentRequisitionRefMapper;
import com.hand.hec.csh.mapper.CshTransactionLineMapper;
import com.hand.hec.csh.service.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentRequisitionLnServiceImpl extends BaseServiceImpl<CshPaymentRequisitionLn>
                implements ICshPaymentRequisitionLnService {
    @Autowired
    ICshPaymentRequisitionHdService iPaymentRequisitionHdService;
    @Autowired
    ICshMoPaymentReqTypeService iMoPaymentReqTypeService;
    @Autowired
    CshPaymentRequisitionLnMapper cshPaymentRequisitionLnMapper;
    @Autowired
    ICshPaymentRequisitionLnService cshPaymentRequisitionLnService;
    @Autowired
    CshTransactionLineMapper cshTransactionLineMapper;
    @Autowired
    CshPaymentRequisitionRefMapper cshPaymentRequisitionRefMapper;
    @Autowired
    ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Override
    public List<CshPaymentRequisitionLn> batchSave(IRequest request, List<CshPaymentRequisitionLn> list)
                    throws CshPaymentRequisitionLnException {
        for (CshPaymentRequisitionLn ln : list) {
            switch (ln.get__status()) {
                case DTOStatus.ADD:
                    self().insertPaymentReqLn(request, ln);
                    break;
                case DTOStatus.UPDATE:
                    self().updatePaymentReqLn(request, ln);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(ln);
                    break;
                default:
                    break;

            }
        }
        return list;
    }

    @Override
    public CshPaymentRequisitionLn insertPaymentReqLn(IRequest request, CshPaymentRequisitionLn record)
                    throws CshPaymentRequisitionLnException {
        // 单据头
        CshPaymentRequisitionHd paymentRequisitionHd = new CshPaymentRequisitionHd();
        paymentRequisitionHd.setPaymentRequisitionHeaderId(record.getPaymentRequisitionHeaderId());
        paymentRequisitionHd = iPaymentRequisitionHdService.selectByPrimaryKey(request, paymentRequisitionHd);

        // 单据类型
        CshMoPaymentReqType moPaymentReqType = new CshMoPaymentReqType();
        moPaymentReqType.setMoPaymentReqTypeId(paymentRequisitionHd.getPaymentReqTypeId());
        moPaymentReqType = iMoPaymentReqTypeService.selectByPrimaryKey(request, moPaymentReqType);

        // 默认现金流量项
        Long cashFlowItemId = null;
        if (record.getMoCshTrxClassId().toString() != null || !record.getMoCshTrxClassId().toString().isEmpty()) {
            cashFlowItemId = cshPaymentRequisitionLnMapper.selectForFlowId(record.getMoCshTrxClassId(),
                            record.getAccEntityId(), moPaymentReqType.getMagOrgId());
        }
        // 判断账号
        if (record.getAccountNumber() == null || record.getAccountNumber().isEmpty()) {
            throw new CshPaymentRequisitionLnException(CshPaymentRequisitionLnException.ACCOUNT_NUMBER_NULL,
                            CshPaymentRequisitionLnException.ACCOUNT_NUMBER_NULL, null);
        }
        record.setCashFlowItemId(cashFlowItemId);
        //默认支付状态和完成支付日期
        record.setPaymentStatus("NEVER");
        record.setPaymentCompletedDate(null);
        self().insertSelective(request, record);

        // 获取核算主体
        Long payAccEntityId = cshPaymentRequisitionLnMapper.selectForAccEntityId(moPaymentReqType.getMagOrgId(),
                        record.getCompanyId(), CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                        paymentRequisitionHd.getPaymentReqTypeId(), record.getPaymentMethodId(),
                        record.getPayeeCategory());
        if (payAccEntityId == null || payAccEntityId.toString().isEmpty()) {
            payAccEntityId = record.getAccEntityId();
        }
        // 保存付款主体信息
        cshDocPayAccEntityService.createPayAccEntity(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                        paymentRequisitionHd.getCompanyId(), paymentRequisitionHd.getPaymentReqTypeId(),
                        paymentRequisitionHd.getPaymentRequisitionHeaderId(), record.getPaymentRequisitionLineId(),
                        payAccEntityId, paymentRequisitionHd.getPaymentMethodId(),
                        paymentRequisitionHd.getPayeeCategory(), BaseConstants.YES, BaseConstants.NO);
        // 保存费用对象
        // 保存维度

        return record;
    }

    @Override
    public CshPaymentRequisitionLn updatePaymentReqLn(IRequest request, CshPaymentRequisitionLn record)
                    throws CshPaymentRequisitionLnException {
        // 单据头
        CshPaymentRequisitionHd paymentRequisitionHd = new CshPaymentRequisitionHd();
        paymentRequisitionHd.setPaymentRequisitionHeaderId(record.getPaymentRequisitionHeaderId());
        paymentRequisitionHd = iPaymentRequisitionHdService.selectByPrimaryKey(request, paymentRequisitionHd);

        // 单据类型
        CshMoPaymentReqType moPaymentReqType = new CshMoPaymentReqType();
        moPaymentReqType.setMoPaymentReqTypeId(paymentRequisitionHd.getPaymentReqTypeId());
        moPaymentReqType = iMoPaymentReqTypeService.selectByPrimaryKey(request, moPaymentReqType);

        // 默认现金流量项
        Long cashFlowItemId = null;
        if (record.getMoCshTrxClassId().toString() != null || !record.getMoCshTrxClassId().toString().isEmpty()) {
            cashFlowItemId = cshPaymentRequisitionLnMapper.selectForFlowId2(record.getMoCshTrxClassId(),
                            moPaymentReqType.getMagOrgId());
        }
        record.setCashFlowItemId(cashFlowItemId);
        //默认支付状态和完成支付日期
        record.setPaymentStatus("NEVER");
        record.setPaymentCompletedDate(null);
        self().updateByPrimaryKey(request, record);

        // 获取核算主体
        Long payAccEntityId = cshPaymentRequisitionLnMapper.selectForAccEntityId(moPaymentReqType.getMagOrgId(),
                        record.getCompanyId(), CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                        paymentRequisitionHd.getPaymentReqTypeId(), record.getPaymentMethodId(),
                        record.getPayeeCategory());
        if (payAccEntityId == null || payAccEntityId.toString().isEmpty()) {
            payAccEntityId = record.getAccEntityId();
        }

        // 删除付款主体
        cshDocPayAccEntityService.deletePayAccEntity(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                        record.getPaymentRequisitionHeaderId(), record.getPaymentRequisitionLineId());
        // 新增付款主体
        cshDocPayAccEntityService.createPayAccEntity(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
                        paymentRequisitionHd.getCompanyId(), paymentRequisitionHd.getPaymentReqTypeId(),
                        paymentRequisitionHd.getPaymentRequisitionHeaderId(), record.getPaymentRequisitionLineId(),
                        payAccEntityId, paymentRequisitionHd.getPaymentMethodId(),
                        paymentRequisitionHd.getPayeeCategory(), BaseConstants.YES, BaseConstants.NO);
        // 保存费用对象国
        // 保存维度

        return record;
    }

    @Override
    public void deletePaymentReqLn(IRequest request, Long paymentRequisitionHeaderId) {
        CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
        cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
        Criteria criteria = new Criteria(cshPaymentRequisitionLn);
        criteria.where(new WhereField(CshPaymentRequisitionLn.FIELD_PAYMENT_REQUISITION_HEADER_ID), Comparison.EQUAL);
        List<CshPaymentRequisitionLn> lnList = self().selectOptions(request, cshPaymentRequisitionLn, criteria);

        for (CshPaymentRequisitionLn ln : lnList) {
            self().deleteByPrimaryKey(ln);
        }
    }

    @Override
    public List<Map> selectByHeaderId(IRequest request, Long paymentRequisitionHeaderId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return cshPaymentRequisitionLnMapper.selectByHeaderId(paymentRequisitionHeaderId);
    }

    @Override
    public String checkPayReq(Long transactionHeaderId, Long transactionLineId) {
        // 获取付款金额
        List<CshTransactionLine> lineList = cshTransactionLineMapper
                        .select(CshTransactionLine.builder().transactionLineId(transactionLineId).build());
        if (lineList.isEmpty()) {
            // 单据已被其他用户删除
            return "TRX_NOT_FOUND_ERROR";
        }
        BigDecimal transactionAmount = lineList.get(0).getTransactionAmount();

        // 引用借款申请金额合计
        List<CshPaymentRequisitionRef> cshPaymentRequisitionRefsList = cshPaymentRequisitionRefMapper
                        .select(CshPaymentRequisitionRef.builder().cshTransactionLineId(transactionLineId).build());
        if (cshPaymentRequisitionRefsList.isEmpty()) {
            // 未查到资金-现金事务关联行
            return "CASH_PMT_NOT_FOUND_ERROR";
        }
        BigDecimal sumScheduleAmt = BigDecimal.valueOf(0);
        for (CshPaymentRequisitionRef cshPaymentRequisitionRef : cshPaymentRequisitionRefsList) {
            sumScheduleAmt = cshPaymentRequisitionRef.getAmount().add(sumScheduleAmt);
        }
        if (sumScheduleAmt.equals(0)) {
            // 总金额为零,不存在资金计划行
            return "CASH_PMT_ZERO_ERROR";
        }
        if (!sumScheduleAmt.equals(transactionAmount)) {
            // 现金事务与合同资金总金额不相等！
            return "CASH_NOT_EQUAL_PMT_ERROR";
        }

        // 校验资金计划行的付款对象与付款事务的付款对象的一致性
        Long countObj = cshPaymentRequisitionRefMapper.checkTranAndPayObj(transactionLineId);
        if (countObj > 0) {
            // 付款事务收款方已变更，引用借款申请收款方与付款事务收款方不一致，请修改。
            return "REQ_PAYEE_ERROR";
        }

        // 校验资金计划行的币种与付款事务的币种的一致性
        Long countCurrency = cshPaymentRequisitionRefMapper.checkTranAndPayCurrency(transactionLineId);
        if (countCurrency > 0) {
            // 付款币种已变更，资金计划行币种与付款币种不一致，请修改。
            return "REQ_CURRENCY_ERROR";
        }
        return null;
    }

}
