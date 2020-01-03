package com.hand.hec.csh.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.dto.AcpRequisitionRef;
import com.hand.hec.acp.mapper.AcpRequisitionLnMapper;
import com.hand.hec.acp.service.IAcpRequisitionLnService;
import com.hand.hec.acp.service.IAcpRequisitionRefService;
import com.hand.hec.base.service.HecUtil;
import com.hand.hec.csh.dto.*;
import com.hand.hec.csh.exception.CshWriteOffException;
import com.hand.hec.csh.mapper.*;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.mapper.ExpReportHeaderMapper;
import com.hand.hec.expm.mapper.ExpReportPmtScheduleMapper;
import com.hand.hec.expm.service.IExpReportAccountService;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.expm.service.IExpReportPmtScheduleService;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import com.hand.hec.gld.service.IGlAccountEntryService;
import com.hand.hec.gld.service.IGldMappingConditionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 核销表ServiceImpl
 * </p>
 *
 * @author Tagin 2019/01/22 09:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshWriteOffServiceImpl extends BaseServiceImpl<CshWriteOff> implements ICshWriteOffService {

    @Autowired
    private CshWriteOffMapper cshWriteOffMapper;

    @Autowired
    private ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    private ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    @Autowired
    private CshTransactionHeaderMapper cshTransactionHeaderMapper;

    @Autowired
    private CshTransactionLineMapper cshTransactionLineMapper;

    @Autowired
    private GldPeriodMapper gldPeriodMapper;

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    private ICshWriteOffAccountService cshWriteOffAccountService;

    @Autowired
    private IExpDocumentHistoryService expDocumentHistoryService;

    @Autowired
    private ICshTransactionAccountService cshTransactionAccountService;

    @Autowired
    private ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Autowired
    private ICshPaymentRequisitionRefService cshPaymentRequisitionRefService;

    @Autowired
    private CshPaymentRequisitionHdMapper cshPaymentRequisitionHdMapper;

    @Autowired
    private ICshPaymentRequisitionLnService cshPaymentRequisitionLnService;

    @Autowired
    private CshPaymentRequisitionLnMapper cshPaymentRequisitionLnMapper;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;

    @Autowired
    private ICshTransactionLineService cshTransactionLineService;

    @Autowired
    private CshMoTransactionClassMapper cshMoTransactionClassMapper;

    @Autowired
    private CshPaymentRequisitionRefMapper cshPaymentRequisitionRefMapper;

    @Autowired
    private IGldCurrencyService currencyService;

    @Autowired
    private DatabaseLockProvider lockProvider;

    @Autowired
    private IExpReportPmtScheduleService pmtScheduleService;

    @Autowired
    private IExpReportHeaderService reportHeaderService;

    @Autowired
    private IExpReportAccountService reportAccountService;

    @Autowired
    private IAcpRequisitionRefService acpRequisitionRefService;

    @Autowired
    private ICshBankAccountService bankAccountService;

    @Autowired
    private ICshBankAccountAsgnAccService bankAccountAsgnAccService;

    @Autowired
    private ISysParameterService parameterService;

    @Autowired
    private GldSetOfBookMapper gldSetOfBookMapper;

    @Autowired
    private IGldMappingConditionService gldMappingConditionService;

    @Autowired
    private FndCompanyMapper companyMapper;

    @Autowired
    private HecUtil hecUtil;

    @Autowired
    private ICshTransactionService transactionService;

    @Autowired
    private ICshPaymentRequisitionRefService requisitionRefService;

    @Autowired
    private IAcpRequisitionLnService acpRequisitionLnService;

    @Autowired
    private AcpRequisitionLnMapper acpRequisitionLnMapper;


    @Autowired
    private IGlAccountEntryService glAccountEntryService;


    /**
     * 报销单核销借款-单据信息-查询
     *
     * @Author Tagin
     * @Date 2019/2/18 10:30
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Version 1.0
     **/
    @Override
    public List<CshWriteOff> docQuery(IRequest iRequest, Long paymentScheduleLineId) {
        // 获取报销单核销借款-单据信息
        return cshWriteOffMapper.docQuery(paymentScheduleLineId);
    }

    /**
     * 报销单核销借款-未核销记录-查询
     *
     * @Author Tagin
     * @Date 2019/1/29 10:30
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Param requisitionNumber 单据编号
     * @Param dateFrom 日期从
     * @Param dateTo 日期到
     * @Version 1.0
     **/
    @Override
    public List<CshWriteOff> unWriteQuery(IRequest iRequest, Long paymentScheduleLineId, String requisitionNumber,
                    String dateFrom, String dateTo) {
        // 获取报销单核销借款-未核销的数据
        return cshWriteOffMapper.unWriteQuery(paymentScheduleLineId, iRequest.getCompanyId(), requisitionNumber,
                        dateFrom, dateTo);
    }

    /**
     * 报销单核销借款-已核销记录-查询
     *
     * @Author Tagin
     * @Date 2019/2/14 17:49
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Version 1.0
     **/
    @Override
    public List<CshWriteOff> writeQuery(IRequest iRequest, Long paymentScheduleLineId) {
        // 获取报销单核销借款-已核销的记录
        return cshWriteOffMapper.writeQuery(paymentScheduleLineId);
    }

    /**
     * 报销单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:22
     * @param transactionLineId 现金事务行对象
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    @Override
    public List<CshWriteOff> queryRptWriteForReverse(Long transactionLineId, String returnFlag) {
        return cshWriteOffMapper.queryRptWriteForReverse(transactionLineId, returnFlag);
    }

    /**
     * 借款申请单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:56
     * @param transactionLineId 现金事务行 ID
     * @param transactionHeaderId 现金事务头 ID
     * @param sourceHeaderId 还款事务头 ID
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    @Override
    public List<CshWriteOff> queryPayWriteForReverse(Long transactionLineId, Long transactionHeaderId,
                    Long sourceHeaderId, String returnFlag) {
        return cshWriteOffMapper.queryPayWriteForReverse(transactionLineId, transactionHeaderId, sourceHeaderId,
                        returnFlag);
    }

    /**
     * 付款申请单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:58
     * @param transactionLineId 现金事务行 ID
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    @Override
    public List<CshWriteOff> queryAcpWriteForReverse(Long transactionLineId, String returnFlag) {
        return cshWriteOffMapper.queryAcpWriteForReverse(transactionLineId, returnFlag);
    }

    /**
     * 报销单/借款单/付款单已付金额校验【Tips：报销单亦可用于核销金额校验】
     *
     * @param docCategory 单据类别
     * @param expReportPmtSchedule 报销单计划付款行对象
     * @param cshPaymentRequisitionLn 付款申请单行对象
     * @param acpRequisitionLn 付款申请单行对象
     * @param paymentAmount 本次 支付/核销金额
     * @Author Tagin
     * @Date 2019-02-20 13:31
     * @Return java.lang.String
     * @Version 1.0
     **/
    public String validatePaymentAmount(String docCategory, ExpReportPmtSchedule expReportPmtSchedule,
                    CshPaymentRequisitionLn cshPaymentRequisitionLn, AcpRequisitionLn acpRequisitionLn,
                    BigDecimal paymentAmount) {
        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return CshWriteOffException.NEGATIVE_AMOUNT_ERROR;
        }
        if (CshDocPayAccEntity.DOC_EXP_REPORT.equals(docCategory)) {
            // 1.0 报销单金额校验
            // 1.0.1 获取报销单已付金额（含报销单核销及支付）
            BigDecimal payedAmount = cshWriteOffMapper.totalWriteAmount(expReportPmtSchedule.getExpReportHeaderId(),
                            expReportPmtSchedule.getPaymentScheduleLineId(), CshWriteOff.DOC_SOURCE_EXPENSE_REPORT,
                            null, null, null);
            if (payedAmount.compareTo(expReportPmtSchedule.getDueAmount()) == 0) {
                return CshWriteOffException.WRITE_OFF_COMPLETE_ERROR;
            } else if (paymentAmount.compareTo(expReportPmtSchedule.getDueAmount().subtract(payedAmount)) > 0) {
                return CshWriteOffException.PAYMENT_AMOUNT_GREATER_ERROR;
            }
        } else if (CshDocPayAccEntity.DOC_PAYMENT_REQUISITION.equals(docCategory)) {
            // 2.0 借款申请单金额校验
            // 2.0.1 获取借款单已付金额
            BigDecimal payedAmount = cshPaymentRequisitionRefService
                            .querySumAmount(cshPaymentRequisitionLn.getPaymentRequisitionLineId());
            if (payedAmount.compareTo(cshPaymentRequisitionLn.getAmount()) == 0) {
                return CshWriteOffException.WRITE_OFF_COMPLETE_ERROR;
            } else if (paymentAmount.compareTo(cshPaymentRequisitionLn.getAmount().subtract(payedAmount)) > 0) {
                return CshWriteOffException.PAYMENT_AMOUNT_GREATER_ERROR;
            }
        } else if (CshDocPayAccEntity.DOC_ACP_REQUISITION.equals(docCategory)) {
            // 3.0 付款申请单金额校验
            // 3.0.1 付款申请单已付金额
            BigDecimal payedAmount = acpRequisitionRefService.getPaidAmount(acpRequisitionLn.getRequisitionLnsId());
            if (payedAmount.compareTo(acpRequisitionLn.getAmount()) == 0) {
                return CshWriteOffException.WRITE_OFF_COMPLETE_ERROR;
            } else if (paymentAmount.compareTo(acpRequisitionLn.getAmount().subtract(payedAmount)) > 0) {
                return CshWriteOffException.PAYMENT_AMOUNT_GREATER_ERROR;
            }
        }
        return null;
    }

    /**
     * 处理报销单核销状态
     *
     * @param iRequest 请求
     * @param expReportHeader 报销单头对象
     * @param expReportPmtSchedule 计划付款行对象
     * @param paymentFlag 是否为支付
     * @Author Tagin
     * @Date 2019-02-21 12:23
     * @Return void
     * @Version 1.0
     **/
    @Override
    public void updateRptWriteStatus(IRequest iRequest, ExpReportHeader expReportHeader,
                    ExpReportPmtSchedule expReportPmtSchedule, String paymentFlag) {
        // 获取报销单核销总金额及报销单计划付款总金额
        BigDecimal writeTotalAmount = cshWriteOffMapper.totalDocAmount(expReportHeader.getExpReportHeaderId(),
                        CshWriteOff.DOC_SOURCE_EXPENSE_REPORT);
        BigDecimal payToalAmount = expReportPmtScheduleMapper.totalAmount(expReportHeader.getExpReportHeaderId());
        if (writeTotalAmount.compareTo(BigDecimal.ZERO) == 0) {
            // 不存在核销记录 则更新报销单头核销状态为 "N"
            expReportHeader.setWriteOffStatus(CshWriteOff.WRITE_OFF_FLAG_N);
            expReportHeader.setWriteOffCompletedDate(null);
            expReportHeader.setWriteOffCompletedDateTz(null);
            expReportHeader.setWriteOffCompletedDateLtz(null);
            expReportHeader.setLastUpdatedBy(iRequest.getUserId());
            expReportHeaderMapper.updateByPrimaryKey(expReportHeader);
        } else {
            // 存在核销记录 则判断是完全核销还是部分核销
            if (payToalAmount.compareTo(writeTotalAmount) <= 0) {
                // 完全核销
                Date maxWriteDate = cshWriteOffMapper.maxWriteDate(expReportHeader.getExpReportHeaderId(),
                                CshWriteOff.DOC_SOURCE_EXPENSE_REPORT, null);
                expReportHeader.setWriteOffStatus(CshWriteOff.WRITE_OFF_FLAG_C);
                expReportHeader.setWriteOffCompletedDate(maxWriteDate);
                expReportHeader.setWriteOffCompletedDateTz(maxWriteDate);
                expReportHeader.setWriteOffCompletedDateLtz(maxWriteDate);
                expReportHeader.setLastUpdatedBy(iRequest.getUserId());
                expReportHeaderMapper.updateByPrimaryKey(expReportHeader);
            } else {
                // 部分核销
                expReportHeader.setWriteOffStatus(CshWriteOff.WRITE_OFF_FLAG_Y);
                expReportHeader.setWriteOffCompletedDate(null);
                expReportHeader.setWriteOffCompletedDateTz(null);
                expReportHeader.setWriteOffCompletedDateLtz(null);
                expReportHeader.setLastUpdatedBy(iRequest.getUserId());
                expReportHeaderMapper.updateByPrimaryKey(expReportHeader);
            }
        }
        // 若为报销单支付则需更新已支付状态
        if (BaseConstants.YES.equals(paymentFlag)) {
            cshDocPayAccEntityService.updatePaymentStatus(expReportPmtSchedule.getExpReportHeaderId(),
                            expReportPmtSchedule.getPaymentScheduleLineId(), CshDocPayAccEntity.STATUS_PAID,
                            CshDocPayAccEntity.DOC_EXP_REPORT, iRequest);
        }
    }

    /**
     * 处理现金事务核销状态
     *
     * @Author Tagin
     * @Date 2019-02-21 12:29
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param transactionLineId 现金事务行ID
     * @param transactionAmount 现金事务行金额
     * @param paymentFlag 是否为支付
     * @Return void
     * @Version 1.0
     **/
    public void updateTrxWriteStatus(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    Long transactionLineId, BigDecimal transactionAmount, String paymentFlag) {
        // 获取现金事务的核销金额
        BigDecimal trxWriteAmount = cshWriteOffMapper.totalWriteAmount(null, null, null,
                        cshTransactionHeader.getTransactionType(), transactionLineId, null);
        if (trxWriteAmount.compareTo(BigDecimal.ZERO) == 0) {
            // 不存在核销记录 则更新现金事务头核销状态为 "N"
            cshTransactionHeader.setWriteOffFlag(CshWriteOff.WRITE_OFF_FLAG_N);
            cshTransactionHeader.setWriteOffCompletedDate(null);
            cshTransactionHeader.setWriteOffCompletedDateTz(null);
            cshTransactionHeader.setWriteOffCompletedDateLtz(null);
            cshTransactionHeader.setLastUpdatedBy(iRequest.getUserId());
            cshTransactionHeaderMapper.updateByPrimaryKey(cshTransactionHeader);
        } else {
            // 存在核销记录 则判断是完全核销还是部分核销
            if (transactionAmount.compareTo(trxWriteAmount) <= 0) {
                // 完全核销
                Date maxWriteDate = cshWriteOffMapper.maxWriteDate(null, null, transactionLineId);
                cshTransactionHeader.setWriteOffFlag(CshWriteOff.WRITE_OFF_FLAG_C);
                cshTransactionHeader.setWriteOffCompletedDate(maxWriteDate);
                cshTransactionHeader.setWriteOffCompletedDateTz(maxWriteDate);
                cshTransactionHeader.setWriteOffCompletedDateLtz(maxWriteDate);
                cshTransactionHeader.setLastUpdatedBy(iRequest.getUserId());
                cshTransactionHeaderMapper.updateByPrimaryKey(cshTransactionHeader);
            } else {
                // 部分核销
                cshTransactionHeader.setWriteOffFlag(CshWriteOff.WRITE_OFF_FLAG_Y);
                cshTransactionHeader.setWriteOffCompletedDate(null);
                cshTransactionHeader.setWriteOffCompletedDateTz(null);
                cshTransactionHeader.setWriteOffCompletedDateLtz(null);
                cshTransactionHeader.setLastUpdatedBy(iRequest.getUserId());
                cshTransactionHeaderMapper.updateByPrimaryKey(cshTransactionHeader);
            }
        }
        if (BaseConstants.YES.equals(paymentFlag)) {
            cshTransactionHeader.setPostedFlag(CshTransactionHeader.POSTED_FLAG_Y);
            // Tips：总账标识在现金事务时已经设置，无需重复更新
            // cshTransactionHeader.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
            cshTransactionHeader.setLastUpdatedBy(iRequest.getUserId());
            cshTransactionHeaderMapper.updateByPrimaryKey(cshTransactionHeader);
        }
    }

    /**
     * 更新借款单支付状态
     *
     * @author Tagin
     * @date 2019-04-01 20:39
     * @param iRequest 请求
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @return void
     * @version 1.0
     **/
    @Override
    public void updatePaymentStatus(IRequest iRequest, CshPaymentRequisitionLn cshPaymentRequisitionLn) {
        // 已付金额合计
        BigDecimal totalAmount = cshPaymentRequisitionRefMapper
                        .getPaidAmount(cshPaymentRequisitionLn.getPaymentRequisitionLineId());
        if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
            // 1.0 单据状态更新-未支付
            cshPaymentRequisitionLn.setPaymentStatus(CshPaymentRequisitionHd.STATUS_NEVER);
            cshPaymentRequisitionLn.setPaymentCompletedDate(null);
            cshPaymentRequisitionLnService.updateByPrimaryKeySelective(iRequest, cshPaymentRequisitionLn);
            // 2.0 支付信息状态更新-未支付
            cshDocPayAccEntityService.updatePaymentStatus(cshPaymentRequisitionLn.getPaymentRequisitionHeaderId(),
                            cshPaymentRequisitionLn.getPaymentRequisitionLineId(), CshDocPayAccEntity.STATUS_ALLOWED,
                            CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, iRequest);
        } else if (totalAmount.compareTo(cshPaymentRequisitionLn.getAmount()) < 0) {
            // 1.0 单据状态更新-部分支付
            cshPaymentRequisitionLn.setPaymentStatus(CshPaymentRequisitionHd.STATUS_PARTIALLY);
            cshPaymentRequisitionLn.setPaymentCompletedDate(null);
            cshPaymentRequisitionLnService.updateByPrimaryKeySelective(iRequest, cshPaymentRequisitionLn);
            // 2.0 支付信息状态更新-部分支付
            cshDocPayAccEntityService.updatePaymentStatus(cshPaymentRequisitionLn.getPaymentRequisitionHeaderId(),
                            cshPaymentRequisitionLn.getPaymentRequisitionLineId(), CshDocPayAccEntity.STATUS_PAID,
                            CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, iRequest);
        } else if (totalAmount.compareTo(cshPaymentRequisitionLn.getAmount()) == 0) {
            // 1.0 单据状态更新-完全支付
            cshPaymentRequisitionLn.setPaymentStatus(CshPaymentRequisitionHd.STATUS_COMPLETELY);
            cshPaymentRequisitionLn.setPaymentCompletedDate(DateUtils.getCurrentDate());
            cshPaymentRequisitionLnService.updateByPrimaryKeySelective(iRequest, cshPaymentRequisitionLn);
            // 2.0 支付信息状态更新-完全支付
            cshDocPayAccEntityService.updatePaymentStatus(cshPaymentRequisitionLn.getPaymentRequisitionHeaderId(),
                            cshPaymentRequisitionLn.getPaymentRequisitionLineId(), CshDocPayAccEntity.STATUS_PAID,
                            CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, iRequest);
        }
    }

    /**
     * 更新付款申请单支付状态
     *
     * @param iRequest 请求
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @author Tagin
     * @date 2019-05-05 11:15
     * @version 1.0
     **/
    @Override
    public void updateAcpPaymentStatus(IRequest iRequest, AcpRequisitionLn acpRequisitionLn) {
        // 已付金额合计
        BigDecimal totalAmount = acpRequisitionRefService.getPaidAmount(acpRequisitionLn.getRequisitionLnsId());
        if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
            // 1.0 单据状态更新-未支付
            acpRequisitionLn.setPaymentStatus(AcpRequisitionLn.PAYMENT_STATUS_NEVER);
            acpRequisitionLn.setPaymentCompletedDate(null);
            acpRequisitionLnService.updateByPrimaryKeySelective(iRequest, acpRequisitionLn);
            // 2.0 支付信息状态更新-未支付
            cshDocPayAccEntityService.updatePaymentStatus(acpRequisitionLn.getRequisitionHdsId(),
                            acpRequisitionLn.getRequisitionLnsId(), CshDocPayAccEntity.STATUS_ALLOWED,
                            CshDocPayAccEntity.DOC_ACP_REQUISITION, iRequest);
        } else if (totalAmount.compareTo(acpRequisitionLn.getAmount()) < 0) {
            // 1.0 单据状态更新-部分支付
            acpRequisitionLn.setPaymentStatus(AcpRequisitionLn.PAYMENT_STATUS_PARTIALLY);
            acpRequisitionLn.setPaymentCompletedDate(null);
            acpRequisitionLnService.updateByPrimaryKeySelective(iRequest, acpRequisitionLn);
            // 2.0 支付信息状态更新-部分支付
            cshDocPayAccEntityService.updatePaymentStatus(acpRequisitionLn.getRequisitionHdsId(),
                            acpRequisitionLn.getRequisitionLnsId(), CshDocPayAccEntity.STATUS_PAID,
                            CshDocPayAccEntity.DOC_ACP_REQUISITION, iRequest);
        } else if (totalAmount.compareTo(acpRequisitionLn.getAmount()) == 0) {
            // 1.0 单据状态更新-完全支付
            acpRequisitionLn.setPaymentStatus(AcpRequisitionLn.PAYMENT_STATUS_COMPLETELY);
            acpRequisitionLn.setPaymentCompletedDate(DateUtils.getCurrentDate());
            acpRequisitionLnService.updateByPrimaryKeySelective(iRequest, acpRequisitionLn);
            // 2.0 支付信息状态更新-完全支付
            cshDocPayAccEntityService.updatePaymentStatus(acpRequisitionLn.getRequisitionHdsId(),
                            acpRequisitionLn.getRequisitionLnsId(), CshDocPayAccEntity.STATUS_PAID,
                            CshDocPayAccEntity.DOC_ACP_REQUISITION, iRequest);
        }
    }

    /**
     * @Description 报销单核销借款-核销记录-创建
     * @Author Tagin
     * @Date 2019/2/19 16:05
     * @Param iRequest 请求对象
     * @Param cshWriteOffList 待核销集合
     * @Version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createWrite(IRequest iRequest, List<CshWriteOff> cshWriteOffList) {
        // 1.0 筛选大于 0 的记录
        List<CshWriteOff> cshWriteOffs = cshWriteOffList.stream()
                        .filter(x -> x.getAmount().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
        // 2.0 校验金额及生成核销信息
        for (CshWriteOff cshWriteOff : cshWriteOffs) {
            // 获取计划付款行信息
            ExpReportPmtSchedule expReportPmtSchedule =
                            expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
            // 获取报销单头信息
            ExpReportHeader expReportHeader =
                            expReportHeaderMapper.selectByPrimaryKey(cshWriteOff.getDocumentHeaderId());
            // 获取现金事务行信息
            CshTransactionLine cshTransactionLine =
                            cshTransactionLineMapper.selectByPrimaryKey(cshWriteOff.getCshTransactionLineId());
            // 获取现金事务头信息
            CshTransactionHeader cshTransactionHeader =
                            cshTransactionHeaderMapper.selectByPrimaryKey(cshTransactionLine.getTransactionHeaderId());
            // 获取已核销金额【Tips：被其他报销单核销的金额合计】
            BigDecimal writeAmount = cshWriteOffMapper.totalWriteAmount(null, null, null, null,
                            cshWriteOff.getCshTransactionLineId(), CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
            // 获取已还款金额【Tips：此处计算未被反冲的还款金额合计】
            BigDecimal repayAmount = cshTransactionLineMapper.totalRepayAmount(cshWriteOff.getCshTransactionLineId());
            // 判断报销单状态 不为提交
            if (!ExpReportHeader.SUBMITTED.equals(expReportHeader.getReportStatus())) {
                // 判断本次核销金额是否大于未核销金额
                if (cshTransactionLine.getTransactionAmount().subtract(writeAmount.add(repayAmount))
                                .compareTo(cshWriteOff.getAmount()) < 0) {
                    throw new CshWriteOffException(CshWriteOffException.WRITE_AMOUNT_OVER_ERROR,
                                    CshWriteOffException.WRITE_AMOUNT_OVER_ERROR, null);
                }
                // 调用生成核销逻辑
                this.reportWrite(iRequest, expReportHeader, expReportPmtSchedule, cshWriteOff);
            }
            // 更新现金事务核销状态
            this.updateTrxWriteStatus(iRequest, cshTransactionHeader, cshTransactionLine.getTransactionLineId(),
                            cshTransactionLine.getTransactionAmount(), BaseConstants.YES);
        }
    }

    /**
     * 删除核销凭证
     *
     * @param iRequest 请求
     * @param cshWriteOffList 待删除的核销集合
     * @Author Tagin
     * @Date 2019/2/14 17:49
     * @Return void
     * @Version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteWrite(IRequest iRequest, List<CshWriteOff> cshWriteOffList) {
        for (CshWriteOff cshWriteOff : cshWriteOffList) {
            ExpReportHeader expReportHeader =
                            expReportHeaderMapper.selectByPrimaryKey(cshWriteOff.getDocumentHeaderId());
            // 判断报销单状态为 新建、拒绝、收回、审批通过（Tips：审批完成后，允许财务人员进行删除）
            if (ExpReportHeader.GENERATE.equals(expReportHeader.getReportStatus())
                            || ExpReportHeader.REJECTED.equals(expReportHeader.getReportStatus())
                            || ExpReportHeader.TAKEN_BACK.equals(expReportHeader.getReportStatus())
                            || ExpReportHeader.COMPLETELY_APPROVED.equals(expReportHeader.getReportStatus())) {
                // 1.0 删除核销凭证数据及核销数据
                List<CshWriteOffAccount> cshWriteOffAccounts =
                                cshWriteOffAccountService.queryAccount(cshWriteOff.getWriteOffId(), null, null, null);
                cshWriteOffAccountService.batchDelete(cshWriteOffAccounts);
                // 2.0 删除核销记录
                cshWriteOffMapper.deleteByPrimaryKey(cshWriteOff);
                // 3.0 处理报销单头核销状态及核销完成日期
                this.updateRptWriteStatus(iRequest, expReportHeader, null, BaseConstants.NO);
                // 4.0 处理现金事务头核销状态及核销完成日期
                CshTransactionHeader cshTransactionHeader =
                                cshTransactionHeaderMapper.selectByPrimaryKey(cshWriteOff.getTransactionHeaderId());
                this.updateTrxWriteStatus(iRequest, cshTransactionHeader, cshWriteOff.getCshTransactionLineId(),
                                cshWriteOff.getTransactionAmount(), BaseConstants.NO);
                // 5.0 写单据历史表
                expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
                                cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_CANCEL_WRITE_OFF,
                                iRequest.getEmployeeId(), "删除核销记录，金额 " + cshWriteOff.getCshWriteOffAmount());
            }
        }
    }

    /**
     * 费用报销单核销借款
     *
     * @param expReportHeader 报销单头对象
     * @param expReportPmtSchedule 计划付款行对象
     * @param cshWriteOff 核销对象
     * @Author Tagin
     * @Date 2019-02-21 12:53
     * @Return java.lang.Boolean
     * @Version 1.0
     **/
    public void reportWrite(IRequest iRequest, ExpReportHeader expReportHeader,
                    ExpReportPmtSchedule expReportPmtSchedule, CshWriteOff cshWriteOff) {
        // 1.0 校验金额
        String validateMsg = this.validatePaymentAmount(CshDocPayAccEntity.DOC_EXP_REPORT, expReportPmtSchedule, null,
                        null, cshWriteOff.getAmount());
        if (StringUtils.isNotEmpty(validateMsg)) {
            throw new CshWriteOffException(validateMsg, validateMsg, null);
        }
        // 2.0 校验期间
        String periodName = gldPeriodMapper.getPeriodName(cshWriteOff.getWriteOffDate(), cshWriteOff.getAccEntityId(),
                        GldPeriod.STATUS_CODE_O);
        if (StringUtils.isEmpty(periodName)) {
            throw new CshWriteOffException(CshWriteOffException.WRITE_PERIOD_NOT_OPEN,
                            CshWriteOffException.WRITE_PERIOD_NOT_OPEN, null);
        }
        // 3.0 获取支付币种的财务精度
        int precision = gldCurrencyMapper.getPrecision(expReportPmtSchedule.getPaymentCurrencyCode());
        precision = precision == 0 ? 2 : precision;
        // 4.0 生成核销记录
        cshWriteOff.setWriteOffType(CshWriteOff.PREPAYMENT_EXPENSE_REPORT);
        cshWriteOff.setCshWriteOffAmount(
                        cshWriteOff.getAmount().setScale(Math.toIntExact(precision), BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setDocumentSource(CshWriteOff.DOC_SOURCE_EXPENSE_REPORT);
        cshWriteOff.setDocumentWriteOffAmount(
                        cshWriteOff.getAmount().setScale(Math.toIntExact(precision), BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setPeriodName(periodName);
        cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_N);
        self().insertSelective(iRequest, cshWriteOff);
        // 5.0 调用生成核销凭证逻辑
        cshWriteOffAccountService.generateAccount(iRequest, cshWriteOff);
        // 6.0 更新报销单核销状态
        this.updateRptWriteStatus(iRequest, expReportHeader, null, BaseConstants.NO);
        // 7.0 写入单据跟踪
        expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
                        cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_WRITE_OFF,
                        iRequest.getEmployeeId(), "付款交易编号 [" + cshWriteOff.getTransactionNum() + "] ，金额 " + cshWriteOff
                                        .getAmount().setScale(Math.toIntExact(precision), BigDecimal.ROUND_HALF_UP));
        //8.0 凭证录入分录表
        glAccountEntryService.headerGlAccountEntry(iRequest, GlAccountEntry.RULE_TYPE_CSH_WRITE_OFF, cshWriteOff.getWriteOffId());
    }

    /**
     * 费用报销单支付
     *
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @Author Tagin
     * @Date 2019-02-21 12:53
     * @Return java.lang.Boolean
     * @Version 1.0
     **/
    public void reportPayment(IRequest iRequest, CshDocPayment cshDocPayment, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff) {
        // 1.0 获取计划付款行信息
        ExpReportPmtSchedule expReportPmtSchedule =
                        expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
        // 2.0 获取报销单头对象
        ExpReportHeader expReportHeader = expReportHeaderMapper.selectByPrimaryKey(cshWriteOff.getDocumentHeaderId());
        // 3.0 校验报销单未付金额
        String validateMsg = this.validatePaymentAmount(CshDocPayAccEntity.DOC_EXP_REPORT, expReportPmtSchedule, null,
                        null, cshWriteOff.getCshWriteOffAmount());
        if (StringUtils.isNotEmpty(validateMsg)) {
            throw new CshWriteOffException(validateMsg, validateMsg, null);
        }
        // 4.0 获取支付币种的财务精度
        int precision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        precision = precision == 0 ? 2 : precision;
        // 5.0 录入核销记录
        cshWriteOff.setCshWriteOffAmount(
                        cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setDocumentWriteOffAmount(
                        cshWriteOff.getDocumentWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        self().insertSelective(iRequest, cshWriteOff);
        // 6.0 关联合同计划
        // TODO
        // 7.0 调用凭证生成逻辑
        cshTransactionAccountService.generateReportAccount(iRequest, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, expReportPmtSchedule);
        // 8.0 调整报销单核销状态【含支付状态】
        this.updateRptWriteStatus(iRequest, expReportHeader, expReportPmtSchedule, BaseConstants.YES);
        // 9.0 写入单据跟踪
        expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
                        cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_PAY, iRequest.getEmployeeId(),
                        "报销单支付，付款交易编号[" + cshTransactionHeader.getTransactionNum() + "]，金额：" + cshWriteOff
                                        .getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 借款申请支付
     *
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @return void
     * @Author Tagin
     * @Date 2019-02-21 12:53
     * @Version 1.0
     **/
    public void loanPayment(IRequest iRequest, CshDocPayment cshDocPayment, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff) {
        // 1.0 获取借款申请行信息
        CshPaymentRequisitionLn cshPaymentRequisitionLn =
                        cshPaymentRequisitionLnMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
        // 2.0 获取借款申请头信息
        CshPaymentRequisitionHd cshPaymentRequisitionHd =
                        cshPaymentRequisitionHdMapper.selectByPrimaryKey(cshWriteOff.getDocumentHeaderId());
        // 3.0 校验报销单未付金额
        String validateMsg = this.validatePaymentAmount(CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, null,
                        cshPaymentRequisitionLn, null, cshWriteOff.getCshWriteOffAmount());
        if (StringUtils.isNotEmpty(validateMsg)) {
            throw new CshWriteOffException(validateMsg, validateMsg, null);
        }
        // 支付币种汇率
        int precision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        precision = precision == 0 ? 2 : precision;
        CshMoTransactionClass cshMoTransactionClass =
                        cshMoTransactionClassMapper.selectByPrimaryKey(cshWriteOff.getMoCshTrxClassId());
        // 4.0 生成现金事务头
        CshTransactionHeader preTransactionHeader = cshTransactionHeaderService.insertTrxHeader(iRequest,
                        CshTransactionHeader.TRX_TYPE_PREPAYMENT, cshWriteOff.getMoCshTrxClassId(),
                        cshTransactionHeader.getCompanyId(), cshTransactionHeader.getAccEntityId(),
                        cshTransactionHeader.getEmployeeId(), cshTransactionHeader.getDescription(),
                        cshMoTransactionClass.getEnabledWriteOffFlag(), cshTransactionHeader.getTransactionDate(),
                        cshTransactionHeader.getPeriodName(), cshTransactionHeader.getPaymentMethodId(),
                        cshTransactionHeader.getTransactionCategory(), BaseConstants.YES, BaseConstants.NO, null,
                        BaseConstants.NO, BaseConstants.NO, null, null, CshTransactionHeader.INTERFACE_FLAG_P,
                        cshTransactionHeader.getTransactionHeaderId(), cshTransactionHeader.getEbankingFlag(),
                        cshTransactionHeader.getDocumentCategory(), cshTransactionHeader.getDocumentTypeId(),
                        cshTransactionHeader.getPaymentUsedeId(), cshTransactionHeader.getPayeeCategory(),
                        cshTransactionHeader.getPayeeId(), cshTransactionHeader.getCurrencyCode(),
                        cshTransactionHeader.getBankAccountId());
        // 5.0 生成现金事务行
        CshTransactionLine preTransactionLine = cshTransactionLineService.insertTrxLine(iRequest,
                        cshTransactionLine.getCurrencyCode(), cshTransactionLine.getExchangeRateType(),
                        cshTransactionLine.getExchangeRate(), cshTransactionLine.getBankAccountId(),
                        cshTransactionLine.getDocumentNum(), cshTransactionLine.getPayeeCategory(),
                        cshTransactionLine.getPayeeId(), cshTransactionLine.getPayeeBankAccountId(),
                        cshTransactionLine.getDescription(), cshTransactionLine.getHandlingCharge(),
                        cshTransactionLine.getInterest(), iRequest.getEmployeeId(),
                        cshTransactionLine.getTransInOutType(), cshTransactionLine.getDocumentCategory(),
                        cshTransactionLine.getDocumentTypeId(), cshTransactionLine.getPaymentUsedeId(),
                        cshTransactionLine.getPaymentMethodId(), preTransactionHeader.getTransactionHeaderId(),
                        cshTransactionHeader.getCompanyId(), cshTransactionLine.getAccEntityId(),
                        cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        // 6.0 录入核销记录
        cshWriteOff.setCshWriteOffAmount(
                        cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setDocumentWriteOffAmount(
                        cshWriteOff.getDocumentWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setSourceCshTrxLineId(preTransactionLine.getTransactionLineId());
        CshWriteOff dto = self().insertSelective(iRequest, cshWriteOff);
        // 7.0 处理关联支付信息
        if (StringUtils.isNotEmpty(cshWriteOff.getDocumentLineId().toString())) {
            CshPaymentRequisitionRef cshPaymentRequisitionRef = new CshPaymentRequisitionRef();
            cshPaymentRequisitionRef.setPaymentRequisitionLineId(cshWriteOff.getDocumentLineId());
            cshPaymentRequisitionRef.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
            cshPaymentRequisitionRef.setWriteOffId(dto.getWriteOffId());
            cshPaymentRequisitionRef.setAmount(
                            cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
            cshPaymentRequisitionRefService.insertSelective(iRequest, cshPaymentRequisitionRef);

            // 写入单据跟踪
            expDocumentHistoryService.insertDocumentHistory(iRequest,
                            ExpDocumentHistory.DOCUMENT_TYPE_PAYMENT_REQUISITION, cshWriteOff.getDocumentHeaderId(),
                            ExpDocumentHistory.STATUS_PAY, iRequest.getEmployeeId(),
                            "借款单据支付，付款交易编号[" + cshTransactionHeader.getTransactionNum() + "]，金额：" + cshWriteOff
                                            .getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        }
        // 8.0 处理合同资金计划
        if (StringUtils.isNotEmpty(cshWriteOff.getContractNumber())) {
            // TODO
        }
        // 9.0 调用凭证生成逻辑
        cshTransactionAccountService.generateLoanAccount(iRequest, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, cshPaymentRequisitionLn);
        // 10.0 更新借款申请单支付状态
        this.updatePaymentStatus(iRequest, cshPaymentRequisitionLn);
    }

    /**
     * 付款申请单-零星付款
     *
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @return void
     * @author Tagin
     * @date 2019-04-29 17:35
     * @version 1.0
     **/
    public void acpPayment(IRequest iRequest, CshDocPayment cshDocPayment, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff) {
        // 1.0 获取借款申请单行对象
        AcpRequisitionLn acpRequisitionLn = acpRequisitionLnMapper.selectByPrimaryKey(cshWriteOff.getDocumentId());
        // 2.0 校验付款单未付金额
        String validateMsg = this.validatePaymentAmount(CshDocPayAccEntity.DOC_ACP_REQUISITION, null, null,
                        acpRequisitionLn, cshWriteOff.getCshWriteOffAmount());
        if (StringUtils.isNotEmpty(validateMsg)) {
            throw new CshWriteOffException(validateMsg, validateMsg, null);
        }
        // 3.0 获取支付币种的财务精度
        int precision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        precision = precision == 0 ? 2 : precision;
        // 4.0 录入核销记录
        cshWriteOff.setCshWriteOffAmount(
                        cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setDocumentWriteOffAmount(
                        cshWriteOff.getDocumentWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        CshWriteOff dto = self().insertSelective(iRequest, cshWriteOff);
        // 5.0 录入付款申请单支付记录
        if (StringUtils.isNotEmpty(cshWriteOff.getDocumentLineId().toString())) {
            AcpRequisitionRef acpRequisitionRef = new AcpRequisitionRef();
            acpRequisitionRef.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
            acpRequisitionRef.setAmount(
                            cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
            acpRequisitionRef.setRequisitionLnsId(acpRequisitionLn.getRequisitionLnsId());
            if (CshWriteOff.DOC_SOURCE_EXPENSE_REPORT.equals(cshWriteOff.getDocumentSource())
                            || CshWriteOff.DOC_SOURCE_CON_CONTRACT.equals(cshWriteOff.getDocumentSource())) {
                acpRequisitionRef.setWriteOffFlag(BaseConstants.YES);
            } else {
                acpRequisitionRef.setWriteOffFlag(BaseConstants.NO);
            }
            acpRequisitionRef.setWriteOffId(dto.getWriteOffId());
            acpRequisitionRefService.insertSelective(iRequest, acpRequisitionRef);

            // 写入单据跟踪
            expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_ACP_REQUISITION,
                            acpRequisitionLn.getRequisitionHdsId(), ExpDocumentHistory.STATUS_PAY,
                            iRequest.getEmployeeId(),
                            "付款单支付，付款交易编号[" + cshTransactionHeader.getTransactionNum() + "]，金额：" + cshWriteOff
                                            .getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        }
        // 8.0 处理合同资金计划
        if (StringUtils.isNotEmpty(cshWriteOff.getContractNumber())) {
            // TODO
        }
        // 9.0 调用凭证生成逻辑
        cshTransactionAccountService.generateAcpAccount(iRequest, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, acpRequisitionLn);
        // 10.0 更新付款申请单支付状态
        this.updateAcpPaymentStatus(iRequest, acpRequisitionLn);
    }

    /**
     * 付款申请单-预付款
     *
     * @param iRequest 请求
     * @param cshDocPayment 支付基础数据对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @return void
     * @Author Tagin
     * @Date 2019-02-21 12:53
     * @Version 1.0
     **/
    public void acpPrePayment(IRequest iRequest, CshDocPayment cshDocPayment, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff) {
        // 1.0 获取借款申请单行对象
        AcpRequisitionLn acpRequisitionLn = acpRequisitionLnMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
        // 2.0 校验付款单未付金额
        String validateMsg = this.validatePaymentAmount(CshDocPayAccEntity.DOC_ACP_REQUISITION, null, null,
                        acpRequisitionLn, cshWriteOff.getCshWriteOffAmount());
        if (StringUtils.isNotEmpty(validateMsg)) {
            throw new CshWriteOffException(validateMsg, validateMsg, null);
        }
        // 3.0 支付币种汇率
        int precision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
        precision = precision == 0 ? 2 : precision;
        CshMoTransactionClass cshMoTransactionClass =
                        cshMoTransactionClassMapper.selectByPrimaryKey(cshWriteOff.getMoCshTrxClassId());
        // 4.0 生成现金事务头
        CshTransactionHeader preTransactionHeader = cshTransactionHeaderService.insertTrxHeader(iRequest,
                        CshTransactionHeader.TRX_TYPE_PREPAYMENT, cshWriteOff.getMoCshTrxClassId(),
                        cshTransactionHeader.getCompanyId(), cshTransactionHeader.getAccEntityId(),
                        cshTransactionHeader.getEmployeeId(), cshTransactionHeader.getDescription(),
                        cshMoTransactionClass.getEnabledWriteOffFlag(), cshTransactionHeader.getTransactionDate(),
                        cshTransactionHeader.getPeriodName(), cshTransactionHeader.getPaymentMethodId(),
                        cshTransactionHeader.getTransactionCategory(), BaseConstants.YES, BaseConstants.NO, null,
                        BaseConstants.NO, BaseConstants.NO, null, null, CshTransactionHeader.INTERFACE_FLAG_P,
                        cshTransactionHeader.getTransactionHeaderId(), cshTransactionHeader.getEbankingFlag(),
                        cshTransactionHeader.getDocumentCategory(), cshTransactionHeader.getDocumentTypeId(),
                        cshTransactionHeader.getPaymentUsedeId(), cshTransactionHeader.getPayeeCategory(),
                        cshTransactionHeader.getPayeeId(), cshTransactionHeader.getCurrencyCode(),
                        cshTransactionHeader.getBankAccountId());
        // 5.0 生成现金事务行
        CshTransactionLine preTransactionLine = cshTransactionLineService.insertTrxLine(iRequest,
                        cshTransactionLine.getCurrencyCode(), cshTransactionLine.getExchangeRateType(),
                        cshTransactionLine.getExchangeRate(), cshTransactionLine.getBankAccountId(),
                        cshTransactionLine.getDocumentNum(), cshTransactionLine.getPayeeCategory(),
                        cshTransactionLine.getPayeeId(), cshTransactionLine.getPayeeBankAccountId(),
                        cshTransactionLine.getDescription(), cshTransactionLine.getHandlingCharge(),
                        cshTransactionLine.getInterest(), iRequest.getEmployeeId(),
                        cshTransactionLine.getTransInOutType(), cshTransactionLine.getDocumentCategory(),
                        cshTransactionLine.getDocumentTypeId(), cshTransactionLine.getPaymentUsedeId(),
                        cshTransactionLine.getPaymentMethodId(), preTransactionHeader.getTransactionHeaderId(),
                        cshTransactionHeader.getCompanyId(), cshTransactionLine.getAccEntityId(),
                        cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        // 6.0 录入核销记录
        cshWriteOff.setCshWriteOffAmount(
                        cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setDocumentWriteOffAmount(
                        cshWriteOff.getDocumentWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        cshWriteOff.setSourceCshTrxLineId(preTransactionLine.getTransactionLineId());
        CshWriteOff dto = self().insertSelective(iRequest, cshWriteOff);
        // 7.0 处理关联支付信息
        if (StringUtils.isNotEmpty(cshWriteOff.getDocumentLineId().toString())) {
            AcpRequisitionRef acpRequisitionRef = new AcpRequisitionRef();
            acpRequisitionRef.setCshTransactionLineId(cshTransactionLine.getTransactionLineId());
            acpRequisitionRef.setAmount(
                            cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
            acpRequisitionRef.setRequisitionLnsId(cshWriteOff.getDocumentLineId());
            acpRequisitionRef.setWriteOffFlag(BaseConstants.YES);
            acpRequisitionRef.setWriteOffId(dto.getWriteOffId());
            acpRequisitionRefService.insertSelective(iRequest, acpRequisitionRef);

            // 写入单据跟踪
            expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_ACP_REQUISITION,
                            cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_PAY, iRequest.getEmployeeId(),
                            "付款单生成预付款，付款交易编号[" + cshTransactionHeader.getTransactionNum() + "]，金额：" + cshWriteOff
                                            .getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        }
        // 8.0 处理合同资金计划
        if (StringUtils.isNotEmpty(cshWriteOff.getContractNumber())) {
            // TODO
        }
        // 9.0 调用凭证生成逻辑
        cshTransactionAccountService.generateAcpPreAccount(iRequest, cshDocPayment, cshTransactionHeader,
                        cshTransactionLine, cshWriteOff, acpRequisitionLn);
        // 10.0 更新借款申请单支付状态
        this.updateAcpPaymentStatus(iRequest, acpRequisitionLn);
    }

    /**
     * 付款申请单核销-费用报销单、合同
     *
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @return void
     * @author Tagin
     * @date 2019-05-08 11:59
     * @version 1.0
     **/
    public void acpWrite(IRequest iRequest, CshWriteOff cshWriteOff, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine) {
        // 1.0 获取借款申请单行对象
        AcpRequisitionLn acpRequisitionLn = acpRequisitionLnMapper.selectByPrimaryKey(cshWriteOff.getDocumentId());
        if (CshWriteOff.DOC_SOURCE_EXPENSE_REPORT.equals(cshWriteOff.getDocumentSource())) {
            // 2.0 付款申请单核销报销单
            ExpReportPmtSchedule expReportPmtSchedule =
                            expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
            ExpReportHeader expReportHeader =
                            expReportHeaderMapper.selectByPrimaryKey(cshWriteOff.getDocumentHeaderId());
            String validateMsg = this.validatePaymentAmount(CshDocPayAccEntity.DOC_EXP_REPORT, expReportPmtSchedule,
                            null, null, cshWriteOff.getCshWriteOffAmount());
            if (StringUtils.isNotEmpty(validateMsg)) {
                throw new CshWriteOffException(validateMsg, validateMsg, null);
            }
            // 支付币种汇率
            int precision = gldCurrencyMapper.getPrecision(cshTransactionLine.getCurrencyCode());
            precision = precision == 0 ? 2 : precision;

            // 录入核销记录
            cshWriteOff.setCshWriteOffAmount(
                            cshWriteOff.getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
            cshWriteOff.setDocumentWriteOffAmount(
                            cshWriteOff.getDocumentWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
            if (BaseConstants.YES.equals(expReportHeader.getAuditFlag())) {
                cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
            } else {
                cshWriteOff.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_N);
            }
            CshWriteOff dto = self().insertSelective(iRequest, cshWriteOff);
            // 更新报销单核销状态
            this.updateRptWriteStatus(iRequest, expReportHeader, expReportPmtSchedule, BaseConstants.NO);

            // 写入单据跟踪
            expDocumentHistoryService.insertDocumentHistory(iRequest, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
                            cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_PAY, iRequest.getEmployeeId(),
                            "付款申请单核销费用报销单，付款交易编号[" + cshTransactionHeader.getTransactionNum() + "]，金额：" + cshWriteOff
                                            .getCshWriteOffAmount().setScale(precision, BigDecimal.ROUND_HALF_UP));
        } else {
            // 3.0 校验合同资金计划未付金额 Tips：实际业务中不存在付款申请单关联合同的预付，因此当前逻辑课不考虑
            // TODO
        }
        // 2.0 调用凭证生成逻辑
        cshWriteOffAccountService.generateAcpWriteAccount(iRequest, cshTransactionHeader, cshTransactionLine,
                        cshWriteOff, acpRequisitionLn);
    }

    /**
     * 单据支付入口【含预付款核销】
     *
     * @param iRequest 请求
     * @param cshDocPayment 单据支付基础信息
     * @param cshWriteOffs 核销集合
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @return void
     * @author Tagin
     * @date 2019-03-28 11:45
     * @version 1.0
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void executePayment(IRequest iRequest, CshDocPayment cshDocPayment, List<CshWriteOff> cshWriteOffs,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine) {
        // 校验现金事务金额同核销集合金额是否一致
        BigDecimal sumWriteAmount = cshWriteOffs.stream().map(CshWriteOff::getCshWriteOffAmount).reduce(BigDecimal.ZERO,
                        BigDecimal::add);
        if (cshTransactionLine.getTransactionAmount().compareTo(sumWriteAmount) != 0) {
            throw new CshWriteOffException(CshWriteOffException.SUM_WRITE_OFF_AMOUNT_ERROR,
                            CshWriteOffException.SUM_WRITE_OFF_AMOUNT_ERROR, null);
        }
        // 调用具体支付逻辑
        for (CshWriteOff cshWriteOff : cshWriteOffs) {
            if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                            && BaseConstants.YES.equals(cshTransactionHeader.getEnabledWriteOffFlag())
                            && CshWriteOff.PAYMENT_EXPENSE_REPORT.equals(cshWriteOff.getWriteOffType())) {
                // 报销单支付
                this.reportPayment(iRequest, cshDocPayment, cshTransactionHeader, cshTransactionLine, cshWriteOff);
            } else if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                            && BaseConstants.YES.equals(cshTransactionHeader.getEnabledWriteOffFlag())
                            && CshWriteOff.PAYMENT_PREPAYMENT.equals(cshWriteOff.getWriteOffType())
                            && !cshWriteOff.getMoCshTrxClassId().toString().isEmpty()) {
                // 借款单支付
                this.loanPayment(iRequest, cshDocPayment, cshTransactionHeader, cshTransactionLine, cshWriteOff);
            } else if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                            && BaseConstants.YES.equals(cshTransactionHeader.getEnabledWriteOffFlag())
                            && CshWriteOff.PREPAYMENT_EXPENSE_REPORT.equals(cshWriteOff.getWriteOffType())) {
                ExpReportPmtSchedule expReportPmtSchedule =
                                expReportPmtScheduleMapper.selectByPrimaryKey(cshWriteOff.getDocumentLineId());
                ExpReportHeader expReportHeader =
                                expReportHeaderMapper.selectByPrimaryKey(cshWriteOff.getDocumentHeaderId());
                // 报销单核销借款
                this.reportWrite(iRequest, expReportHeader, expReportPmtSchedule, cshWriteOff);
            } else if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                            && BaseConstants.YES.equals(cshTransactionHeader.getEnabledWriteOffFlag())
                            && CshWriteOff.ACP_PAYMENT.equals(cshWriteOff.getWriteOffType())) {
                // 付款申请单-零星付款
                this.acpPayment(iRequest, cshDocPayment, cshTransactionHeader, cshTransactionLine, cshWriteOff);
            } else if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                            && BaseConstants.YES.equals(cshTransactionHeader.getEnabledWriteOffFlag())
                            && CshWriteOff.ACP_PREPAYMENT.equals(cshWriteOff.getWriteOffType())) {
                // 付款申请单-预付款
                this.acpPrePayment(iRequest, cshDocPayment, cshTransactionHeader, cshTransactionLine, cshWriteOff);
            } else if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(cshTransactionHeader.getTransactionType())
                            && BaseConstants.YES.equals(cshTransactionHeader.getEnabledWriteOffFlag())
                            && CshWriteOff.PREPAYMENT_ACP_REQUISITION.equals(cshWriteOff.getWriteOffType())) {
                // 付款申请单-关联报销结算、合同结算核销
                this.acpWrite(iRequest, cshWriteOff, cshTransactionHeader, cshTransactionLine);
            }
        }
        // 更新核销事务状态
        this.updateTrxWriteStatus(iRequest, cshTransactionHeader, cshTransactionLine.getTransactionLineId(),
                        cshTransactionLine.getTransactionAmount(), BaseConstants.YES);
    }

    @Override
    public List<CshWriteOff> queryPrePayDetail(Long transactionHeaderId, IRequest request, Integer page,
                    Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return cshWriteOffMapper.queryPrePayDetail(transactionHeaderId);
    }

    @Override
    public List<Map> getWriteOffHistory(Long transactionHeaderId, IRequest request, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return cshWriteOffMapper.getWriteOffHistory(transactionHeaderId);
    }

    @Override
    public List<Map> queryFinance(Long transactionHeaderId, IRequest request, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return cshWriteOffMapper.queryFinance(transactionHeaderId);
    }


    @Override
    public String submitCshWriteOffCheck(IRequest request, String payeeCategory, Long payeeId, Long companyId,
                    Long accEntityId) {
        return cshWriteOffMapper.submitCshWriteOffCheck(payeeCategory, payeeId, companyId, accEntityId);
    }

    @Override
    public void returnCshTrxWriteOff(IRequest request, CshTransactionHeader rtnTrxHd, CshTransactionLine rtnTrxLn,
                    CshTransactionHeader sourceTrxHd, List<CshTransactionReturn> trxRtnList) {
        //
        // 校验退款总金额是否等于核销行退款总金额
        // ------------------------------------------------------------------------------
        BigDecimal totalRtnAmount = new BigDecimal(0);
        for (CshTransactionReturn trxRtn : trxRtnList) {
            totalRtnAmount = totalRtnAmount.add(trxRtn.getReturnAmount());
        }
        if (totalRtnAmount.compareTo(rtnTrxLn.getTransactionAmount()) != 0) {
            throw new CshWriteOffException("CSH", CshWriteOffException.SUM_WRITE_OFF_AMOUNT_NOT_EQUAL_RETURN_AMOUNT,
                            null);
        }

        String functionalCurrencyCode = currencyService.getAccEntityFunCurrCode(rtnTrxHd.getAccEntityId());


        // 核算主体默认账套
        List<GldSetOfBook> gldSetOfBooks = gldSetOfBookMapper.queryDftSetOffBookByAe(rtnTrxHd.getAccEntityId());
        Long setOfBooksId = gldSetOfBooks.get(0).getSetOfBooksId();

        for (CshTransactionReturn trxRtn : trxRtnList) {
            if ("CSH_WRITE_OFF".equals(trxRtn.getSourceType())) {
                CshWriteOff sourceWriteOff =
                                checkReturnWriteOffAmount(request, trxRtn.getSourceId(), trxRtn.getReturnAmount());

                if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(rtnTrxHd.getTransactionType())
                                && CshWriteOff.PAYMENT_EXPENSE_REPORT.equals(sourceWriteOff.getWriteOffType())) {
                    //
                    // 付款核销报销单
                    // ------------------------------------------------------------------------------
                    self().returnPaymentExpReport(request, sourceWriteOff, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                                    rtnTrxLn.getTransactionAmount(), functionalCurrencyCode, setOfBooksId);
                } else if (CshTransactionHeader.TRX_TYPE_PAYMENT.equals(rtnTrxHd.getTransactionType())
                                && CshWriteOff.PAYMENT_PREPAYMENT.equals(sourceWriteOff.getWriteOffType())) {
                    //
                    // 付款核销预付款
                    // ------------------------------------------------------------------------------
                    self().returnPaymentPrepayment(request, sourceWriteOff, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                                    rtnTrxLn.getTransactionAmount(), functionalCurrencyCode, setOfBooksId);
                } else {
                    throw new CshWriteOffException("CSH", CshWriteOffException.UNSUPPORT_WRITE_OFF_TYPE, null);
                }
            }
        }
    }

    @Override
    public void returnPaymentExpReport(IRequest request, CshWriteOff sourceWriteOff, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    String functionalCurrencyCode, Long setOfBooksId) {
        //
        // 锁表
        // ------------------------------------------------------------------------------
        lockProvider.lock(ExpReportPmtSchedule.class, "payment_schedule_line_id=" + sourceWriteOff.getDocumentLineId(),
                        null);

        //
        // 获取对应的报销单计划付款行
        // ------------------------------------------------------------------------------
        ExpReportPmtSchedule pmtSchedule = new ExpReportPmtSchedule();
        pmtSchedule.setPaymentScheduleLineId(sourceWriteOff.getDocumentLineId());
        pmtSchedule = pmtScheduleService.selectByPrimaryKey(request, pmtSchedule);

        //
        // 获取对应的报销单头
        // ------------------------------------------------------------------------------
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(pmtSchedule.getExpReportHeaderId());
        header = reportHeaderService.selectByPrimaryKey(request, header);

        String expExchangeRateType = null;
        BigDecimal expExchangeRate = null;

        //
        // 根据报销单凭证获取汇率类型和汇率
        // ------------------------------------------------------------------------------
        ExpReportAccount queryAccount = new ExpReportAccount();
        queryAccount.setExpReportHeaderId(header.getExpReportHeaderId());
        queryAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        List<ExpReportAccount> accountList = reportAccountService.select(request, queryAccount, 0, 0);
        if (accountList != null && accountList.size() != 0) {
            expExchangeRateType = accountList.get(0).getExchangeRateType();
            expExchangeRate = accountList.get(0).getExchangeRate();
        } else {
            expExchangeRateType = rtnTrxLn.getExchangeRateType();
            expExchangeRate = rtnTrxLn.getExchangeRate();
        }

        returnAmount = currencyService.calcGldAmount(request, returnAmount, rtnTrxLn.getCurrencyCode());

        //
        // 插入本次退款的核销记录
        // ------------------------------------------------------------------------------
        CshWriteOff writeOff = new CshWriteOff();
        writeOff.setWriteOffType(sourceWriteOff.getWriteOffType());
        writeOff.setCshTransactionLineId(sourceWriteOff.getCshTransactionLineId());
        writeOff.setCshWriteOffAmount(returnAmount.multiply(new BigDecimal(-1)));
        writeOff.setDocumentSource(sourceWriteOff.getDocumentSource());
        writeOff.setDocumentHeaderId(sourceWriteOff.getDocumentHeaderId());
        writeOff.setDocumentLineId(sourceWriteOff.getDocumentLineId());
        writeOff.setDocumentWriteOffAmount(returnAmount.multiply(new BigDecimal(-1)));
        writeOff.setWriteOffDate(rtnTrxHd.getTransactionDate());
        writeOff.setPeriodName(rtnTrxHd.getPeriodName());
        writeOff.setSourceCshTrxLineId(rtnTrxLn.getTransactionLineId());
        writeOff.setSourceWriteOffAmount(returnAmount.multiply(new BigDecimal(-1)));
        writeOff.setGldInterfaceFlag(CshWriteOff.GLD_INTERFACE_FLAG_P);
        writeOff = self().insertSelective(request, writeOff);

        //
        // 更新报销单支付状态和报销单核销状态
        // ------------------------------------------------------------------------------
        updateRptWriteStatus(request, header, pmtSchedule, BaseConstants.YES);

        //
        // 如果是来自付款申请单的支付，插入对应的退款记录
        // ------------------------------------------------------------------------------
        AcpRequisitionRef queryRef = new AcpRequisitionRef();
        queryRef.setWriteOffId(sourceWriteOff.getWriteOffId());
        List<AcpRequisitionRef> refList = acpRequisitionRefService.select(request, queryRef, 0, 0);
        if (refList != null && refList.size() != 0) {
            for (AcpRequisitionRef ref : refList) {
                if (ref.getAmount().compareTo(new BigDecimal(0)) > 0) {
                    AcpRequisitionRef returnRef = new AcpRequisitionRef();
                    returnRef.setRequisitionLnsId(ref.getRequisitionLnsId());
                    returnRef.setCshTransactionLineId(ref.getCshTransactionLineId());
                    returnRef.setWriteOffFlag(null);
                    returnRef.setWriteOffId(writeOff.getWriteOffId());
                    returnRef.setAmount(returnAmount.multiply(new BigDecimal(-1)));
                    returnRef.setDescription("付款退款");

                    returnRef = acpRequisitionRefService.insertSelective(request, returnRef);

                    break;
                }
            }
        }

        //
        // 插入跟踪单据信息
        // ------------------------------------------------------------------------------
        expDocumentHistoryService.insertDocumentHistory(request, ExpReportHeader.EXP_REPORT,
                        writeOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_REFUND, request.getEmployeeId(),
                        "退款交易编号[" + rtnTrxHd.getTransactionNum() + "],金额:"
                                        + returnAmount.multiply(new BigDecimal(-1)).doubleValue());

        //
        // 关联合同或合同资金计划行，暂未实现
        // ------------------------------------------------------------------------------

        //
        // 判断当前核算主体借方凭证是否存在（对应银行存款账户的凭证）
        // ------------------------------------------------------------------------------
        CshTransactionAccount queryTrxAccount = new CshTransactionAccount();
        queryTrxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        queryTrxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        List<CshTransactionAccount> trxAccList = cshTransactionAccountService.select(request, queryTrxAccount, 0, 0);
        int drCount = 0;
        for (CshTransactionAccount trxAcc : trxAccList) {
            if (trxAcc.getEnteredAmountDr() == null) {
                drCount += 1;
            }
        }


        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;
        BigDecimal revaluation = null;

        //
        // 当前核算主体借方凭证不存在，生成对应的凭证
        // 银行存款账户的凭证
        // ------------------------------------------------------------------------------
        if (drCount == 0) {
            insertReturnCurEntityDr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, CshDocPayAccEntity.DOC_EXP_REPORT,
                            writeOff, functionalCurrencyCode, setOfBooksId);
        }


        if (rtnTrxHd.getAccEntityId().equals(pmtSchedule.getAccEntityId())) {
            //
            // 非跨核算主体，生成贷方凭证
            // ------------------------------------------------------------------------------
            insertReturnRepNoInterComCr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, returnAmount, header, pmtSchedule,
                            expExchangeRateType, expExchangeRate, writeOff, functionalCurrencyCode, setOfBooksId);

        } else {
            //
            // 跨核算主体
            // ------------------------------------------------------------------------------

            //
            // 当前核算主体贷方凭证
            // ------------------------------------------------------------------------------
            insertReturnRepInterComCurEntityCr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, returnAmount, header,
                            pmtSchedule, expExchangeRateType, expExchangeRate, writeOff, functionalCurrencyCode,
                            setOfBooksId);

            //
            // 对方核算主体借方、贷方凭证
            // ------------------------------------------------------------------------------
            insertReturnRepInterComOppEntityCrDr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, returnAmount, header,
                            pmtSchedule, expExchangeRateType, expExchangeRate, writeOff, functionalCurrencyCode,
                            setOfBooksId);
        }
        transactionService.setBalance(request, rtnTrxLn.getTransactionLineId());
    }

    @Override
    public void returnPaymentPrepayment(IRequest request, CshWriteOff sourceWriteOff, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    String functionalCurrencyCode, Long setOfBooksId) {
        //
        // 获取源预付款事物的头行
        // ------------------------------------------------------------------------------
        CshTransactionLine sourcePrepayTrxLn = new CshTransactionLine();
        sourcePrepayTrxLn.setTransactionLineId(sourceWriteOff.getSourceCshTrxLineId());
        sourcePrepayTrxLn = cshTransactionLineService.selectByPrimaryKey(request, sourcePrepayTrxLn);

        CshTransactionHeader sourcePrepayTrxHd = new CshTransactionHeader();
        sourcePrepayTrxHd.setTransactionHeaderId(sourcePrepayTrxLn.getTransactionHeaderId());
        sourcePrepayTrxHd = cshTransactionHeaderService.selectByPrimaryKey(request, sourcePrepayTrxHd);

        //
        // 锁表
        // ------------------------------------------------------------------------------
        lockProvider.lock(sourcePrepayTrxHd, "transaction_header_id=" + sourcePrepayTrxHd.getTransactionHeaderId(),
                        null);

        Long prepayMagOrgId = hecUtil.getMagOrgId(request, sourcePrepayTrxHd.getCompanyId());
        Long prepaySetOfBooksId = hecUtil.getSetOfBooksId(request, sourcePrepayTrxHd.getAccEntityId());
        String prepayExchangeRateType = sourcePrepayTrxLn.getExchangeRateType();
        BigDecimal prepayExchangeRate = sourcePrepayTrxLn.getExchangeRate();

        returnAmount = currencyService.calcGldAmount(request, returnAmount, functionalCurrencyCode);

        //
        // 创建预付款的还款现金事物头
        // ------------------------------------------------------------------------------
        CshTransactionHeader header = cshTransactionHeaderService.insertTrxHeader(request, "PREPAYMENT",
                        sourcePrepayTrxHd.getMoCshTrxClassId(), sourceTrxHd.getCompanyId(), rtnTrxHd.getAccEntityId(),
                        rtnTrxHd.getEmployeeId(), rtnTrxHd.getDescription(), sourcePrepayTrxHd.getEnabledWriteOffFlag(),
                        rtnTrxHd.getTransactionDate(), rtnTrxHd.getPeriodName(), rtnTrxHd.getPaymentMethodId(),
                        rtnTrxHd.getTransactionCategory(), CshTransactionHeader.POSTED_FLAG_Y,
                        CshTransactionHeader.REVERSED_FLAG_N, null, CshTransactionHeader.RETURNED_FLAG_R, "N", null,
                        sourcePrepayTrxHd.getTransactionHeaderId(), CshTransactionHeader.INTERFACE_FLAG_P,
                        rtnTrxHd.getTransactionHeaderId(), rtnTrxHd.getEbankingFlag(), null, null, null, null, null,
                        null, null);

        //
        // 创建预付款的还款现金事物行
        // ------------------------------------------------------------------------------
        CshTransactionLine line = cshTransactionLineService.insertTrxLine(request, rtnTrxLn.getCurrencyCode(),
                        rtnTrxLn.getExchangeRateType(), rtnTrxLn.getExchangeRate(), rtnTrxLn.getBankAccountId(),
                        rtnTrxLn.getDocumentNum(), rtnTrxLn.getPayeeCategory(), rtnTrxLn.getPayeeId(),
                        rtnTrxLn.getPayeeBankAccountId(), rtnTrxLn.getDescription(), rtnTrxLn.getHandlingCharge(),
                        rtnTrxLn.getInterest(), rtnTrxLn.getAgentEmployeeId(), rtnTrxLn.getTransInOutType(),
                        rtnTrxLn.getDocumentCategory(), rtnTrxLn.getDocumentTypeId(), rtnTrxLn.getPaymentUsedeId(),
                        rtnTrxLn.getPaymentMethodId(), header.getTransactionHeaderId(), rtnTrxLn.getCompanyId(),
                        rtnTrxLn.getAccEntityId(), returnAmount);

        //
        // 创建核销记录
        // ------------------------------------------------------------------------------
        CshWriteOff writeOff = new CshWriteOff();
        writeOff.setWriteOffType(sourceWriteOff.getWriteOffType());
        writeOff.setCshTransactionLineId(sourceWriteOff.getCshTransactionLineId());
        writeOff.setCshWriteOffAmount(returnAmount.multiply(new BigDecimal(-1)));
        writeOff.setWriteOffDate(rtnTrxHd.getTransactionDate());
        writeOff.setPeriodName(rtnTrxHd.getPeriodName());
        writeOff.setSourceWriteOffAmount(returnAmount.multiply(new BigDecimal(-1)));
        writeOff.setGldInterfaceFlag(CshWriteOff.GLD_INTERFACE_FLAG_P);
        writeOff = self().insertSelective(request, writeOff);

        //
        // 更新预付款事物的
        // ------------------------------------------------------------------------------
        transactionService.setTrxReturnStatus(request, sourcePrepayTrxHd);

        //
        // 插入借款申请的refs信息
        // ------------------------------------------------------------------------------
        CshPaymentRequisitionRef ref = null;
        CshPaymentRequisitionRef queryRef = new CshPaymentRequisitionRef();
        queryRef.setWriteOffId(sourceWriteOff.getWriteOffId());
        List<CshPaymentRequisitionRef> refs = requisitionRefService.select(request, queryRef, 0, 0);
        for (CshPaymentRequisitionRef curRef : refs) {
            if (curRef.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                ref = curRef;
                break;
            }
        }

        if (ref != null) {
            //
            // 插入本次退款的Ref
            // ------------------------------------------------------------------------------
            CshPaymentRequisitionRef newRef = new CshPaymentRequisitionRef();
            newRef.setPaymentRequisitionLineId(ref.getPaymentRequisitionLineId());
            newRef.setCshTransactionLineId(ref.getCshTransactionLineId());
            newRef.setWriteOffId(writeOff.getWriteOffId());
            newRef.setAmount(returnAmount.multiply(new BigDecimal(-1)));

            newRef = requisitionRefService.insertSelective(request, newRef);

            //
            // 借款申请单记录本次退款的单据历史
            // ------------------------------------------------------------------------------
            CshPaymentRequisitionLn paymentRequisitionLn = new CshPaymentRequisitionLn();
            paymentRequisitionLn.setPaymentRequisitionLineId(ref.getPaymentRequisitionLineId());
            paymentRequisitionLn = cshPaymentRequisitionLnService.selectByPrimaryKey(request, paymentRequisitionLn);

            expDocumentHistoryService.insertDocumentHistory(request,
                            ExpDocumentHistory.DOCUMENT_TYPE_PAYMENT_REQUISITION,
                            paymentRequisitionLn.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_REFUND,
                            request.getEmployeeId(),
                            "退款事务编号[" + rtnTrxHd.getTransactionNum() + "],金额:"
                                            + returnAmount.multiply(new BigDecimal(-1)) + " 借款申请单行ID:"
                                            + paymentRequisitionLn.getPaymentRequisitionLineId());
        }

        //
        // 关联合同或合同资金计划行,暂未实现
        // ------------------------------------------------------------------------------
        CshTransactionAccount queryAccount = new CshTransactionAccount();
        queryAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        queryAccount.setAccEntityId(rtnTrxHd.getAccEntityId());


        //
        // 如果当前核算主体借方不存在，生成当前核算主体的借方凭证
        List<CshTransactionAccount> accountList = cshTransactionAccountService.select(request, queryAccount, 0, 0);
        int drCount = 0;
        for (CshTransactionAccount account : accountList) {
            if (account.getEnteredAmountDr() == null && account.getFunctionalAmountDr() == null) {
                drCount += 1;
            }
        }
        if (drCount == 0) {
            insertReturnCurEntityDr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                            CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff, functionalCurrencyCode, setOfBooksId);
        }

        //
        // 不跨核算主体，生成贷方凭证
        // ------------------------------------------------------------------------------
        if (sourcePrepayTrxHd.getAccEntityId().equals(rtnTrxHd.getAccEntityId())) {
            insertReturnPrepayNoInterComCr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, header, line, returnAmount,
                            prepayExchangeRateType, prepayExchangeRate, writeOff, functionalCurrencyCode, setOfBooksId);
        } else {
            //
            // 跨核算主体
            // ------------------------------------------------------------------------------

            //
            // 当前核算主体贷方凭证
            // ------------------------------------------------------------------------------
            insertReturnPrepayInterComCurEntityCr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, returnAmount, header, line,
                            prepayExchangeRateType, prepayExchangeRate, writeOff, functionalCurrencyCode, setOfBooksId);

            //
            // 对方核算主体借方、贷方凭证
            // ------------------------------------------------------------------------------
            insertReturnPrepayInterComOppEntityCrDr(request, rtnTrxHd, rtnTrxLn, sourceTrxHd, returnAmount, header,
                            line, prepayExchangeRateType, prepayExchangeRate, writeOff, functionalCurrencyCode,
                            setOfBooksId);
        }
        transactionService.setBalance(request, rtnTrxLn.getTransactionLineId());
    }

    /**
     * 生成退款现金事物的当前核算主体的借方凭证
     *
     * @param request
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param functionalCurrencyCode
     * @param setOfBooksId
     * @return void
     * @author mouse 2019-05-07 13:09
     */
    private void insertReturnCurEntityDr(IRequest request, CshTransactionHeader rtnTrxHd, CshTransactionLine rtnTrxLn,
                    CshTransactionHeader sourceTrxHd, String docCategory, CshWriteOff writeOff,
                    String functionalCurrencyCode, Long setOfBooksId) {
        //
        // 获取银行账户的科目、责任中心
        // ------------------------------------------------------------------------------
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;

        try {
            CshBankAccount bankAccount = new CshBankAccount();
            bankAccount.setBankAccountId(rtnTrxLn.getBankAccountId());
            bankAccount = bankAccountService.selectByPrimaryKey(request, bankAccount);

            CshBankAccountAsgnAcc bankAccountAsgnAcc = new CshBankAccountAsgnAcc();
            bankAccountAsgnAcc.setBankAccountId(bankAccount.getBankAccountId());
            bankAccountAsgnAcc.setSetOfBooksId(setOfBooksId);
            bankAccountAsgnAcc = bankAccountAsgnAccService.select(request, bankAccountAsgnAcc, 0, 0).get(0);

            respCenterId = bankAccount.getResponsibilityCenterId();
            accountId = bankAccountAsgnAcc.getCashAccountId();
        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.BANK_ACCOUNT_ERROR, null);
        }

        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.BANK_ACCOUNT_ERROR, null);
        }

        enteredAmount = rtnTrxLn.getTransactionAmount();
        functionalAmount = enteredAmount.multiply(rtnTrxLn.getExchangeRate());

        functionalAmount = currencyService.calcGldAmount(request, functionalAmount, functionalCurrencyCode);

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountDr(enteredAmount);
        trxAccount.setFunctionalAmountDr(functionalAmount);
        trxAccount.setExchangeRateType(rtnTrxLn.getExchangeRateType());
        trxAccount.setExchangeRate(rtnTrxLn.getExchangeRate());
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setBankAccountFlag("Y");
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("CASH_ACCOUNT");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Dr", setOfBooksId, rtnTrxLn,
                        docCategory, writeOff.getDocumentHeaderId(), writeOff.getDocumentLineId(), null,
                        writeOff.getDocumentLineId());
    }

    /**
     * 生成报销单退款，非跨核算主体的贷方凭证
     *
     * @param request
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param returnAmount
     * @param header
     * @param pmtSchedule
     * @param expExchangeRateType
     * @param expExchangeRate
     * @param writeOff
     * @param functionalCurrencyCode
     * @param setOfBooksId
     * @return void
     * @author mouse 2019-05-07 13:10
     */
    private void insertReturnRepNoInterComCr(IRequest request, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    ExpReportHeader header, ExpReportPmtSchedule pmtSchedule, String expExchangeRateType,
                    BigDecimal expExchangeRate, CshWriteOff writeOff, String functionalCurrencyCode,
                    Long setOfBooksId) {
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;
        BigDecimal revaluation = null;

        //
        // 非跨核算主体
        // 当前核算主体贷方
        // ------------------------------------------------------------------------------

        //
        // 根据核算主体找到默认责任中心
        // ------------------------------------------------------------------------------
        try {
            respCenterId = Long.parseLong(parameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            rtnTrxHd.getAccEntityId(), null, null, null));

        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_RESP_CENTER, null);
        }

        //
        // 获取报销单贷方凭证作为本次借方凭证
        // ------------------------------------------------------------------------------
        ExpReportAccount reportAccount = cshTransactionHeaderService.getReportCrAccount(pmtSchedule);

        accountId = reportAccount.getAccountId();
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.EXPENSE_CLEARING_NOT_EXISTS, null);
        }

        enteredAmount = returnAmount;
        functionalAmount = enteredAmount.multiply(expExchangeRate);
        functionalAmount = currencyService.calcGldAmount(request, functionalAmount, functionalCurrencyCode);

        revaluation = currencyService.calcGldAmount(request, enteredAmount.multiply(rtnTrxLn.getExchangeRate()),
                        functionalCurrencyCode);
        revaluation = revaluation.subtract(functionalAmount);

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountCr(enteredAmount);
        trxAccount.setFunctionalAmountCr(functionalAmount);
        trxAccount.setExchangeRateType(expExchangeRateType);
        trxAccount.setExchangeRate(expExchangeRate);
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("EMPLOYEE_EXPENSE_WRITE_OFF");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Cr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_EXP_REPORT, writeOff.getDocumentHeaderId(), writeOff.getDocumentLineId(),
                        null, writeOff.getDocumentLineId());

        //
        // 创建汇兑损益凭证
        // ------------------------------------------------------------------------------

        //
        // 汇率差异用途代码定义的科目
        // ------------------------------------------------------------------------------
        if (revaluation.compareTo(BigDecimal.ZERO) != 0) {
            insertRevaluationAccount(request, revaluation, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                            CshDocPayAccEntity.DOC_EXP_REPORT, writeOff, respCenterId, setOfBooksId);
        }
    }

    /**
     * 生成报销单退款现金事物的跨核算主体时，当前核算主体[退款核算主体]的贷方凭证
     *
     * @param request
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param returnAmount
     * @param header
     * @param pmtSchedule
     * @param expExchangeRateType
     * @param expExchangeRate
     * @param writeOff
     * @param functionalCurrencyCode
     * @param setOfBooksId
     * @return void
     * @author mouse 2019-05-07 13:10
     */
    private void insertReturnRepInterComCurEntityCr(IRequest request, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    ExpReportHeader header, ExpReportPmtSchedule pmtSchedule, String expExchangeRateType,
                    BigDecimal expExchangeRate, CshWriteOff writeOff, String functionalCurrencyCode,
                    Long setOfBooksId) {
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;

        try {
            respCenterId = Long.parseLong(parameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            rtnTrxHd.getAccEntityId(), null, null, null));
        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_RESP_CENTER, null);
        }


        //
        // 取内部往来应收科目
        // ------------------------------------------------------------------------------
        List<GldMappingCondition> gldMappingConditions =
                        gldMappingConditionService.accBuilderCshInCompanyAr(rtnTrxHd.getCompanyId().toString(),
                                        rtnTrxHd.getAccEntityId().toString(), pmtSchedule.getAccEntityId().toString(),
                                        respCenterId.toString(), rtnTrxHd.getCurrencyCode(), null);
        FndCompany fndCompany = companyMapper.selectByPrimaryKey(rtnTrxHd.getCompanyId());
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, "CSH_INTERCOMPANY_AR",
                        fndCompany.getMagOrgId(), setOfBooksId);
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.INTERCOMPANY_AR_NOT_EXISTS, null);
        }

        enteredAmount = returnAmount;
        functionalAmount = currencyService.calcGldAmount(request, enteredAmount.multiply(rtnTrxLn.getExchangeRate()),
                        functionalCurrencyCode);

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountCr(enteredAmount);
        trxAccount.setFunctionalAmountCr(functionalAmount);
        trxAccount.setExchangeRateType(rtnTrxLn.getExchangeRateType());
        trxAccount.setExchangeRate(rtnTrxLn.getExchangeRate());
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("CSH_INTERCOMPANY_AR");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Cr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_EXP_REPORT, writeOff.getDocumentHeaderId(), writeOff.getDocumentLineId(),
                        null, writeOff.getDocumentLineId());
    }

    /**
     * 报销单退款，跨核算主体，生成对方核算主体[报销单的核算主体]的借贷方凭证
     *
     * @param request
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param returnAmount
     * @param header
     * @param pmtSchedule
     * @param expExchangeRateType
     * @param expExchangeRate
     * @param writeOff
     * @param functionalCurrencyCode
     * @param setOfBooksId
     * @return void
     * @author mouse 2019-05-07 13:01
     */
    private void insertReturnRepInterComOppEntityCrDr(IRequest request, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    ExpReportHeader header, ExpReportPmtSchedule pmtSchedule, String expExchangeRateType,
                    BigDecimal expExchangeRate, CshWriteOff writeOff, String functionalCurrencyCode,
                    Long setOfBooksId) {
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;
        BigDecimal revaluation = null;

        //
        // 对方核算主体借方凭证-AP
        // ------------------------------------------------------------------------------
        try {
            respCenterId = Long.parseLong(parameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            pmtSchedule.getAccEntityId(), null, null, null));
        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_RESP_CENTER, null);
        }

        List<GldMappingCondition> gldMappingConditions =
                        gldMappingConditionService.accBuilderCshInCompanyAp(pmtSchedule.getCompanyId().toString(),
                                        pmtSchedule.getAccEntityId().toString(), rtnTrxLn.getAccEntityId().toString(),
                                        respCenterId.toString(), rtnTrxLn.getCurrencyCode(), null);
        FndCompany fndCompany = companyMapper.selectByPrimaryKey(pmtSchedule.getCompanyId());
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, "CSH_INTERCOMPANY_AP",
                        fndCompany.getMagOrgId(), setOfBooksId);
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.INTERCOMPANY_AP_NOT_EXISTS, null);
        }

        //
        // 取对方核算主体默认汇率
        // ------------------------------------------------------------------------------
        String apExchangeRateType =
                        parameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE,
                                        null, null, null, header.getAccEntityId(), null, null, null);

        if (apExchangeRateType == null) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_EXCHANGE_RATE_TYPE_NOT_EXISTS, null);
        }

        BigDecimal apExchangeRate = hecUtil.getExchangeRate(rtnTrxLn.getAccEntityId(), functionalCurrencyCode,
                        rtnTrxLn.getCurrencyCode(), apExchangeRateType, rtnTrxHd.getTransactionDate(),
                        rtnTrxHd.getPeriodName());

        if (apExchangeRate == null) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_EXCHANGE_RATE_NOT_EXISTS, null);
        }

        enteredAmount = returnAmount;
        functionalAmount = currencyService.calcGldAmount(request, enteredAmount.multiply(apExchangeRate),
                        functionalCurrencyCode);

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountDr(enteredAmount);
        trxAccount.setFunctionalAmountDr(functionalAmount);
        trxAccount.setExchangeRateType(apExchangeRateType);
        trxAccount.setExchangeRate(apExchangeRate);
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("CSH_INTERCOMPANY_AP");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Dr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_EXP_REPORT, writeOff.getDocumentHeaderId(), writeOff.getDocumentLineId(),
                        null, writeOff.getDocumentLineId());

        //
        // 对方核算主体贷方凭证-EMPLOYEE_EXPENSE_WRITE_OFF
        // ------------------------------------------------------------------------------
        enteredAmount = returnAmount;
        functionalAmount = currencyService.calcGldAmount(request, enteredAmount.multiply(expExchangeRate),
                        functionalCurrencyCode);

        //
        // 获取报销单贷方凭证作为本次借方凭证
        // ------------------------------------------------------------------------------
        ExpReportAccount reportAccount = cshTransactionHeaderService.getReportCrAccount(pmtSchedule);

        accountId = reportAccount.getAccountId();
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.EXPENSE_CLEARING_NOT_EXISTS, null);
        }

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountCr(enteredAmount);
        trxAccount.setFunctionalAmountCr(functionalAmount);
        trxAccount.setExchangeRateType(expExchangeRateType);
        trxAccount.setExchangeRate(expExchangeRate);
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("EMPLOYEE_EXPENSE_WRITE_OFF");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Cr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_EXP_REPORT, writeOff.getDocumentHeaderId(), writeOff.getDocumentLineId(),
                        null, writeOff.getDocumentLineId());


        //
        // 汇兑损益金额 = 原币金额*AP默认汇率 - 原币金额*报销汇率
        // ------------------------------------------------------------------------------
        revaluation = currencyService
                        .calcGldAmount(request, enteredAmount.multiply(apExchangeRate), functionalCurrencyCode)
                        .subtract(currencyService.calcGldAmount(request, enteredAmount.multiply(expExchangeRate),
                                        functionalCurrencyCode));

        //
        // 生成汇兑损益科目
        // ------------------------------------------------------------------------------
        if (revaluation.compareTo(BigDecimal.ZERO) != 0) {
            insertRevaluationAccount(request, revaluation, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                            CshDocPayAccEntity.DOC_EXP_REPORT, writeOff, respCenterId, setOfBooksId);
        }
    }

    /**
     * 生成预付款退款，非跨核算主体的贷方凭证
     *
     * @param request
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param returnAmount
     * @param expExchangeRateType
     * @param expExchangeRate
     * @param writeOff
     * @param functionalCurrencyCode
     * @param setOfBooksId
     * @return void
     * @author mouse 2019-05-07 13:10
     */
    private void insertReturnPrepayNoInterComCr(IRequest request, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, CshTransactionHeader rtnPreTrxHd,
                    CshTransactionLine rtnPreTrxLn, BigDecimal returnAmount, String expExchangeRateType,
                    BigDecimal expExchangeRate, CshWriteOff writeOff, String functionalCurrencyCode,
                    Long setOfBooksId) {
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;
        BigDecimal revaluation = null;
        //
        // 非跨核算主体
        // 当前核算主体贷方
        // ------------------------------------------------------------------------------

        //
        // 根据核算主体找到默认责任中心
        // ------------------------------------------------------------------------------
        try {
            respCenterId = Long.parseLong(parameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            rtnTrxHd.getAccEntityId(), null, null, null));
        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_RESP_CENTER, null);
        }
        //
        // 获取报销单贷方凭证作为本次借方凭证
        // ------------------------------------------------------------------------------
        accountId = transactionService.getPrepaymentAccount(request, rtnPreTrxHd, rtnPreTrxLn);

        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.EXPENSE_CLEARING_NOT_EXISTS, null);
        }
        enteredAmount = returnAmount;
        functionalAmount = enteredAmount.multiply(expExchangeRate);
        functionalAmount = currencyService.calcGldAmount(request, functionalAmount, functionalCurrencyCode);

        revaluation = currencyService.calcGldAmount(request, enteredAmount.multiply(rtnTrxLn.getExchangeRate()),
                        functionalCurrencyCode);
        revaluation = revaluation.subtract(functionalAmount);

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountCr(enteredAmount);
        trxAccount.setFunctionalAmountCr(functionalAmount);
        trxAccount.setExchangeRateType(expExchangeRateType);
        trxAccount.setExchangeRate(expExchangeRate);
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("EMPLOYEE_EXPENSE_WRITE_OFF");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Cr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff.getDocumentHeaderId(),
                        writeOff.getDocumentLineId(), null, writeOff.getDocumentLineId());
        //
        // 创建汇兑损益凭证
        // ------------------------------------------------------------------------------
        if (revaluation.compareTo(BigDecimal.ZERO) != 0) {
            insertRevaluationAccount(request, revaluation, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                            CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff, respCenterId, setOfBooksId);
        }
    }

    /**
     * 生成预付款退款，跨核算主体的当前核算主体[还款核算主体]的贷方凭证
     *
     * @param request
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param returnAmount
     * @param rtnPreTrxHd
     * @param rtnPreTrxLn
     * @param prepayExchangeRateType
     * @param prepayExchangeRate
     * @param writeOff
     * @param functionalCurrencyCode
     * @param setOfBooksId
     * @return void
     * @author mouse 2019-05-07 14:33
     */
    private void insertReturnPrepayInterComCurEntityCr(IRequest request, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    CshTransactionHeader rtnPreTrxHd, CshTransactionLine rtnPreTrxLn, String prepayExchangeRateType,
                    BigDecimal prepayExchangeRate, CshWriteOff writeOff, String functionalCurrencyCode,
                    Long setOfBooksId) {
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;
        try {
            respCenterId = Long.parseLong(parameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            rtnTrxHd.getAccEntityId(), null, null, null));
        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_RESP_CENTER, null);
        }
        //
        // 取内部往来应收科目
        // ------------------------------------------------------------------------------
        List<GldMappingCondition> gldMappingConditions =
                        gldMappingConditionService.accBuilderCshInCompanyAr(rtnTrxHd.getCompanyId().toString(),
                                        rtnTrxHd.getAccEntityId().toString(), rtnPreTrxHd.getAccEntityId().toString(),
                                        respCenterId.toString(), rtnTrxHd.getCurrencyCode(), null);
        FndCompany fndCompany = companyMapper.selectByPrimaryKey(rtnTrxHd.getCompanyId());
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, "CSH_INTERCOMPANY_AR",
                        fndCompany.getMagOrgId(), setOfBooksId);
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.INTERCOMPANY_AR_NOT_EXISTS, null);
        }
        enteredAmount = returnAmount;
        functionalAmount = currencyService.calcGldAmount(request, enteredAmount.multiply(rtnTrxLn.getExchangeRate()),
                        functionalCurrencyCode);
        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountCr(enteredAmount);
        trxAccount.setFunctionalAmountCr(functionalAmount);
        trxAccount.setExchangeRateType(rtnTrxLn.getExchangeRateType());
        trxAccount.setExchangeRate(rtnTrxLn.getExchangeRate());
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("CSH_INTERCOMPANY_AR");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Cr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff.getDocumentHeaderId(),
                        writeOff.getDocumentLineId(), null, writeOff.getDocumentLineId());
    }

    private void insertReturnPrepayInterComOppEntityCrDr(IRequest request, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    CshTransactionHeader rtnPreTrxHd, CshTransactionLine rtnPreTrxLn, String prepayExchangeRateType,
                    BigDecimal prepayExchangeRate, CshWriteOff writeOff, String functionalCurrencyCode,
                    Long setOfBooksId) {
        Long respCenterId = null;
        Long accountId = null;
        BigDecimal enteredAmount = null;
        BigDecimal functionalAmount = null;
        CshTransactionAccount trxAccount = null;
        BigDecimal revaluation = null;

        //
        // 对方核算主体借方凭证-AP
        // ------------------------------------------------------------------------------
        try {
            respCenterId = Long.parseLong(parameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_DEFAULT_RESPONSIBILITY_CENTER, null, null, null,
                            rtnPreTrxHd.getAccEntityId(), null, null, null));
        } catch (Exception e) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_RESP_CENTER, null);
        }

        List<GldMappingCondition> gldMappingConditions =
                        gldMappingConditionService.accBuilderCshInCompanyAp(rtnPreTrxHd.getCompanyId().toString(),
                                        rtnPreTrxHd.getAccEntityId().toString(), rtnTrxLn.getAccEntityId().toString(),
                                        respCenterId.toString(), rtnTrxLn.getCurrencyCode(), null);
        FndCompany fndCompany = companyMapper.selectByPrimaryKey(rtnPreTrxHd.getCompanyId());
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, "CSH_INTERCOMPANY_AP",
                        fndCompany.getMagOrgId(), setOfBooksId);
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.INTERCOMPANY_AP_NOT_EXISTS, null);
        }

        //
        // 取对方核算主体默认汇率
        // ------------------------------------------------------------------------------
        String apExchangeRateType =
                        parameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE,
                                        null, null, null, rtnPreTrxHd.getAccEntityId(), null, null, null);

        if (apExchangeRateType == null) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_EXCHANGE_RATE_TYPE_NOT_EXISTS, null);
        }

        BigDecimal apExchangeRate = hecUtil.getExchangeRate(rtnTrxLn.getAccEntityId(), functionalCurrencyCode,
                        rtnTrxLn.getCurrencyCode(), apExchangeRateType, rtnTrxHd.getTransactionDate(),
                        rtnTrxHd.getPeriodName());

        if (apExchangeRate == null) {
            throw new CshWriteOffException("CSH", CshWriteOffException.DEFAULT_EXCHANGE_RATE_NOT_EXISTS, null);
        }

        enteredAmount = returnAmount;
        functionalAmount = currencyService.calcGldAmount(request, enteredAmount.multiply(apExchangeRate),
                        functionalCurrencyCode);

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountDr(enteredAmount);
        trxAccount.setFunctionalAmountDr(functionalAmount);
        trxAccount.setExchangeRateType(apExchangeRateType);
        trxAccount.setExchangeRate(apExchangeRate);
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("CSH_INTERCOMPANY_AP");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Dr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff.getDocumentHeaderId(),
                        writeOff.getDocumentLineId(), null, writeOff.getDocumentLineId());


        //
        // 对方核算主体贷方凭证-EMPLOYEE_EXPENSE_WRITE_OFF
        // ------------------------------------------------------------------------------
        enteredAmount = returnAmount;
        functionalAmount = currencyService.calcGldAmount(request, enteredAmount.multiply(prepayExchangeRate),
                        functionalCurrencyCode);

        //
        // 获取报销单贷方凭证作为本次借方凭证
        // ------------------------------------------------------------------------------
        accountId = transactionService.getPrepaymentAccount(request, rtnPreTrxHd, rtnPreTrxLn);
        if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
            throw new CshWriteOffException("CSH", CshWriteOffException.EXPENSE_CLEARING_NOT_EXISTS, null);
        }

        trxAccount = new CshTransactionAccount();
        trxAccount.setTransactionLineId(rtnTrxLn.getTransactionLineId());
        trxAccount.setWriteOffId(writeOff.getWriteOffId());
        trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
        trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
        trxAccount.setPeriodName(rtnTrxHd.getPeriodName());
        trxAccount.setCompanyId(rtnTrxHd.getCompanyId());
        trxAccount.setAccEntityId(rtnTrxHd.getAccEntityId());
        trxAccount.setRespCenterId(respCenterId);
        trxAccount.setAccountId(accountId);
        trxAccount.setEnteredAmountCr(enteredAmount);
        trxAccount.setFunctionalAmountCr(functionalAmount);
        trxAccount.setExchangeRateType(prepayExchangeRateType);
        trxAccount.setExchangeRate(prepayExchangeRate);
        trxAccount.setCurrencyCode(rtnTrxLn.getCurrencyCode());
        trxAccount.setJournalDate(rtnTrxHd.getTransactionDate());
        trxAccount.setGldInterfaceFlag(CshTransactionAccount.GLD_INTERFACE_FLAG_P);
        trxAccount.setUsageCode("EMPLOYEE_EXPENSE_WRITE_OFF");

        trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
        //
        // 更新科目段
        // ------------------------------------------------------------------------------
        cshTransactionAccountService.updateAccountSegment(request, trxAccount, "Cr", setOfBooksId, rtnTrxLn,
                        CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff.getDocumentHeaderId(),
                        writeOff.getDocumentLineId(), null, writeOff.getDocumentLineId());

        //
        // 汇兑损益金额 = 原币金额*AP默认汇率 - 原币金额*报销汇率
        // ------------------------------------------------------------------------------
        revaluation = currencyService
                        .calcGldAmount(request, enteredAmount.multiply(apExchangeRate), functionalCurrencyCode)
                        .subtract(currencyService.calcGldAmount(request, enteredAmount.multiply(prepayExchangeRate),
                                        functionalCurrencyCode));

        //
        // 生成汇兑损益科目
        // ------------------------------------------------------------------------------
        if (revaluation.compareTo(BigDecimal.ZERO) != 0) {
            insertRevaluationAccount(request, revaluation, rtnTrxHd, rtnTrxLn, sourceTrxHd,
                            CshDocPayAccEntity.DOC_PAYMENT_REQUISITION, writeOff, respCenterId, setOfBooksId);
        }
    }

    private void insertRevaluationAccount(IRequest request, BigDecimal revaluation, CshTransactionHeader trxHd,
                    CshTransactionLine trxLn, CshTransactionHeader sourceTrxHd, String docCategory,
                    CshWriteOff writeOff, Long respCenterId, Long setOfBooksId) {
        Long accountId = null;
        CshTransactionAccount trxAccount = null;
        String drCrFlag = null;
        //
        // 汇率差异用途代码定义的科目
        // ------------------------------------------------------------------------------
        if (revaluation.compareTo(BigDecimal.ZERO) != 0) {
            List<GldMappingCondition> gldMappingConditions = gldMappingConditionService.accBuilderRevaluation(
                            trxHd.getCompanyId().toString(), null, trxHd.getAccEntityId().toString(),
                            respCenterId.toString(), trxHd.getCurrencyCode());
            FndCompany fndCompany = companyMapper.selectByPrimaryKey(trxHd.getCompanyId());
            accountId = gldMappingConditionService.getAccount(gldMappingConditions, "REVALUATION",
                            fndCompany.getMagOrgId(), setOfBooksId);

            if (accountId == null || accountId == 0 || accountId.toString().isEmpty()) {
                throw new CshWriteOffException("CSH", CshWriteOffException.REVALUATION_NOT_EXISTS, null);
            }

            trxAccount = new CshTransactionAccount();
            trxAccount.setTransactionLineId(trxLn.getTransactionLineId());
            trxAccount.setWriteOffId(writeOff.getWriteOffId());
            trxAccount.setSourceCode(CshTransactionAccount.SOURCE_CODE_CSH_PAYMENT);
            trxAccount.setDescription("退款" + sourceTrxHd.getTransactionNum());
            trxAccount.setPeriodName(trxHd.getPeriodName());
            trxAccount.setCompanyId(trxHd.getCompanyId());
            trxAccount.setAccEntityId(trxHd.getAccEntityId());
            trxAccount.setRespCenterId(respCenterId);
            trxAccount.setAccountId(accountId);
            trxAccount.setExchangeRateType(trxLn.getExchangeRateType());
            trxAccount.setExchangeRate(trxLn.getExchangeRate());
            trxAccount.setCurrencyCode(trxLn.getCurrencyCode());
            trxAccount.setJournalDate(trxHd.getTransactionDate());
            trxAccount.setJournalDateTz(trxHd.getTransactionDate());
            trxAccount.setJournalDateLtz(trxHd.getTransactionDate());
            trxAccount.setGldInterfaceFlag(CshTransactionHeader.INTERFACE_FLAG_P);
            trxAccount.setUsageCode("REVALUATION");
            if (revaluation.compareTo(BigDecimal.ZERO) > 0) {
                trxAccount.setEnteredAmountDr(null);
                trxAccount.setEnteredAmountCr(BigDecimal.valueOf(0));
                trxAccount.setFunctionalAmountDr(null);
                trxAccount.setFunctionalAmountCr(revaluation);
                drCrFlag = "Cr";
            } else if (revaluation.compareTo(BigDecimal.ZERO) < 0) {
                trxAccount.setEnteredAmountDr(BigDecimal.valueOf(0));
                trxAccount.setEnteredAmountCr(null);
                trxAccount.setFunctionalAmountDr(revaluation.multiply(new BigDecimal(-1)));
                trxAccount.setFunctionalAmountCr(null);
                drCrFlag = "Dr";
            }

            trxAccount = cshTransactionAccountService.insertSelective(request, trxAccount);
            //
            // 更新科目段
            // ------------------------------------------------------------------------------
            cshTransactionAccountService.updateAccountSegment(request, trxAccount, drCrFlag, setOfBooksId, trxLn,
                            CshDocPayAccEntity.DOC_EXP_REPORT, writeOff.getDocumentHeaderId(),
                            writeOff.getDocumentLineId(), null, writeOff.getDocumentLineId());

        }
    }

    /**
     * 校验最大可退款金额
     *
     * @param request
     * @param writeOffId
     * @param returnAmount
     * @return com.hand.hec.csh.dto.CshWriteOff
     * @author mouse 2019-05-05 14:59
     */
    private CshWriteOff checkReturnWriteOffAmount(IRequest request, Long writeOffId, BigDecimal returnAmount) {
        CshWriteOff writeOff = new CshWriteOff();
        writeOff.setWriteOffId(writeOffId);
        writeOff = selectByPrimaryKey(request, writeOff);

        BigDecimal maxReturnAmount = new BigDecimal(0);

        if (CshWriteOff.PAYMENT_EXPENSE_REPORT.equals(writeOff.getWriteOffType())) {
            //
            // 获取报销单可退款金额
            // ------------------------------------------------------------------------------
            CshWriteOff queryWriteOff = new CshWriteOff();
            queryWriteOff.setCshTransactionLineId(writeOff.getCshTransactionLineId());
            queryWriteOff.setDocumentLineId(writeOff.getDocumentLineId());

            List<CshWriteOff> writeOffList = select(request, queryWriteOff, 0, 0);
            for (CshWriteOff cshWriteOff : writeOffList) {
                maxReturnAmount = maxReturnAmount.add(cshWriteOff.getCshWriteOffAmount());
            }
        } else if (CshWriteOff.PAYMENT_PREPAYMENT.equals(writeOff.getWriteOffType())) {
            //
            // 获取借款单可退金额
            // ------------------------------------------------------------------------------

            //
            // 获取预付款事物
            // ------------------------------------------------------------------------------
            CshTransactionLine prepaymentTrxLine = new CshTransactionLine();
            prepaymentTrxLine.setTransactionLineId(writeOff.getSourceCshTrxLineId());
            prepaymentTrxLine = cshTransactionLineService.selectByPrimaryKey(request, prepaymentTrxLine);

            //
            // 获取预付款已核销部分
            // ------------------------------------------------------------------------------
            CshWriteOff queryWriteOff = new CshWriteOff();
            queryWriteOff.setCshTransactionLineId(writeOff.getSourceCshTrxLineId());
            List<CshWriteOff> writeOffList = select(request, queryWriteOff, 0, 0);

            //
            // 获取预付款已退款金额
            // ------------------------------------------------------------------------------
            BigDecimal returnedAmount = cshTransactionHeaderService.getPrepaymentReturnedAmount(request,
                            prepaymentTrxLine.getTransactionHeaderId());

            //
            // 借款单可退金额等于 = 预付款金额 - 已核销报销金额 - 已退款金额
            // ------------------------------------------------------------------------------
            maxReturnAmount = maxReturnAmount.add(prepaymentTrxLine.getTransactionAmount());
            for (CshWriteOff cshWriteOff : writeOffList) {
                maxReturnAmount = maxReturnAmount.subtract(cshWriteOff.getCshWriteOffAmount());
            }
            maxReturnAmount = maxReturnAmount.subtract(returnedAmount);
        }

        if (maxReturnAmount.compareTo(returnAmount) < 0) {
            throw new CshWriteOffException("CSH", CshWriteOffException.RETURN_AMOUNT_ERROR, null);
        }

        return writeOff;
    }

    @Override
    public BigDecimal getPaidAmountByAcpReq(Long documentLineId) {
        return cshWriteOffMapper.getPaidAmountByAcpReq(documentLineId);
    }

    /**
     * 根据付款申请单行、现金事务获取核销数据
     *
     * @param requisitionLnsId 付款申请单行 ID
     * @param transactionLineId 现金事务行 ID
     * @return com.hand.hec.csh.dto.CshWriteOff
     * @author Tagin
     * @date 2019-05-07 17:56
     * @version 1.0
     **/
    @Override
    public CshWriteOff getWriteOffByAcpRef(Long requisitionLnsId, Long transactionLineId) {
        return cshWriteOffMapper.getWriteOffByAcpRef(requisitionLnsId, transactionLineId).get(0);
    }


    /**
     * <p>
     * 核销信息查询(付款反冲)
     * </p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @param pageNum
     * @param pageSize
     * @return List<Map>
     * @author yang.duan 2019/5/24 14:57
     **/
    @Override
    public List<Map> queryCshWriteOffHistory(IRequest request, Long transactionHeaderId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> cshWriteOffMapList = cshWriteOffMapper.queryCshWriteOffHistory(transactionHeaderId);
        if (cshWriteOffMapList != null && !cshWriteOffMapList.isEmpty()) {
            String docNumber = cshTransactionHeaderService.getDocNumber(request, transactionHeaderId);
            Long docId = cshTransactionHeaderService.getDocId(request, transactionHeaderId);
            for (Map map : cshWriteOffMapList) {
                String writeOffType = (String) map.get("writeOffType");
                if ("PAYMENT_EXPENSE_REPORT".equals(writeOffType)) {
                    map.replace("expReportNumber", docNumber);
                    map.replace("expReportHeaderId", docId);
                } else if ("PAYMENT_PREPAYMENT".equals(writeOffType)) {
                    map.replace("paymentRequisitionNumber", docNumber);
                    map.replace("paymentRequisitionHeaderId", docId);
                } else {
                    map.replace("acpPayRequisitionNumber", docNumber);
                    map.replace("acpPayRequisitionHeaderId", docId);
                }
            }
        }
        return cshWriteOffMapList;
    }

    @Override
	public List<CshWriteOff> queryPrePayWriteForReverse(IRequest request, Long transactionHeaderId, int pageNum, int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return cshWriteOffMapper.queryPrePayWriteForReverse(transactionHeaderId);
	}

	@Override
	public List<CshWriteOff> reversePrePayWrite(IRequest request, List<CshWriteOff> records){
    	for(CshWriteOff cshWriteOff : records){
			CshTransactionHeader cshTransactionHeader = cshTransactionHeaderMapper.selectByPrimaryKey(cshWriteOff.getTransactionHeaderId());
			String periodName = gldPeriodMapper.getPeriodName(cshWriteOff.getReverseDate(), cshTransactionHeader.getAccEntityId(), null);
			if (null == periodName) {
				throw new RuntimeException("反冲日期必须在打开的会计期间内！");
			}
			ExpReportHeader expReportHeader = new ExpReportHeader();
			expReportHeader.setExpReportHeaderId(cshWriteOff.getDocumentHeaderId());
			expReportHeader = reportHeaderService.selectByPrimaryKey(request, expReportHeader);
			if(!"Y".equals(expReportHeader.getAuditFlag())){
				throw new RuntimeException("核销的报销单未审核，不能进行反冲。");
			}
			BigDecimal totalWriteOffAmount = cshWriteOffMapper.totalOthersDocAmount(cshWriteOff.getWriteOffId());
			if(null != totalWriteOffAmount && totalWriteOffAmount.compareTo(BigDecimal.ZERO) < 0){
				throw new RuntimeException("原核销记录已被完全反冲，不能再进行反冲。");
			}

			if (cshWriteOff.getReverseDate().before(cshWriteOff.getWriteOffDate())){
				throw new RuntimeException("反冲日期不能早于核销日期!");
			}

			if (cshWriteOff.getCshWriteOffAmount().compareTo(BigDecimal.ZERO) < 0){
				throw new RuntimeException("原核销记录已被完全反冲，不能再进行反冲。");
			}

			//校验核销记录是否已被反冲
			BigDecimal sumWriteOffAmount = cshWriteOffMapper.totalWriteAmount(null, cshWriteOff.getDocumentLineId(), null, null, cshWriteOff.getCshTransactionLineId(), null);
			if(sumWriteOffAmount.compareTo(BigDecimal.ZERO) == 0){
				throw new RuntimeException("原核销记录已被反冲，不能再进行反冲。");
			}
			Long writeOffId = cshWriteOff.getWriteOffId();
			//生成核销反冲记录
			cshWriteOff.setCshWriteOffAmount(cshWriteOff.getCshWriteOffAmount().multiply(BigDecimal.valueOf(-1)));
			cshWriteOff.setDocumentWriteOffAmount(cshWriteOff.getDocumentWriteOffAmount().multiply(BigDecimal.valueOf(-1)));
			cshWriteOff.setWriteOffDate(cshWriteOff.getReverseDate());
			cshWriteOff.setPeriodName(periodName);
			cshWriteOff.setGldInterfaceFlag("P");
			cshWriteOff.setSourceCshTrxLineId(null);
			cshWriteOff.setSourceWriteOffAmount(null);
			cshWriteOff.setWriteOffId(null);
			cshWriteOff.setCreationDate(null);
			cshWriteOff.setCreatedBy(null);
			CshWriteOff newRecord = self().insertSelective(request, cshWriteOff);
			Long newWriteOffId = newRecord.getWriteOffId();

			//生成反冲凭证
			CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
			cshWriteOffAccount.setWriteOffId(writeOffId);
			Criteria criteria = new Criteria(cshWriteOffAccount);
			criteria.where(new WhereField(CshWriteOffAccount.FIELD_WRITE_OFF_ID, Comparison.EQUAL));
			List<CshWriteOffAccount> writeOffAccounts = cshWriteOffAccountService
					.selectOptions(request, cshWriteOffAccount, criteria);
			for(CshWriteOffAccount writeOffAccount : writeOffAccounts){
				writeOffAccount.setWriteOffId(newWriteOffId);
				writeOffAccount.setDescription("预付款核销反冲");
				writeOffAccount.setPeriodName(periodName);
				writeOffAccount.setWriteOffJeLineId(null);
				writeOffAccount.setCreatedBy(null);
				writeOffAccount.setCreationDate(null);
				writeOffAccount.setJournalDate(cshWriteOff.getReverseDate());
				if(null != writeOffAccount.getEnteredAmountCr()){
					writeOffAccount.setEnteredAmountCr(BigDecimal.valueOf(-1).multiply(writeOffAccount.getEnteredAmountCr()));
				}
				if(null != writeOffAccount.getEnteredAmountDr()){
					writeOffAccount.setEnteredAmountDr(BigDecimal.valueOf(-1).multiply(writeOffAccount.getEnteredAmountDr()));
				}
				if(null != writeOffAccount.getFunctionalAmountCr()){
					writeOffAccount.setFunctionalAmountCr(BigDecimal.valueOf(-1).multiply(writeOffAccount.getFunctionalAmountCr()));
				}
				if(null != writeOffAccount.getFunctionalAmountDr()){
					writeOffAccount.setFunctionalAmountDr(BigDecimal.valueOf(-1).multiply(writeOffAccount.getFunctionalAmountDr()));
				}
				writeOffAccount.setGldInterfaceFlag("P");
				cshWriteOffAccountService.insertSelective(request, writeOffAccount);

				//更新报销单核销状态
				setExpRepWriteOffStatus(request, cshWriteOff.getDocumentHeaderId());

				cshDocPayAccEntityService.updatePaymentStatus(cshWriteOff.getDocumentId(), cshWriteOff.getDocumentLineId(),
						"ALLOWED", CshDocPayAccEntity.DOC_EXP_REPORT, request);
				// 录入跟踪单据
				expDocumentHistoryService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_EXP_REPORT,
						cshWriteOff.getDocumentHeaderId(), ExpDocumentHistory.STATUS_REVERSE,
						request.getEmployeeId(),
						" 核销反冲交易编号 [" + cshTransactionHeader.getTransactionNum() + "]，金额 "
								+ cshWriteOff.getDocumentWriteOffAmount());

				//更新现金事务核销状态
				this.updateTrxWriteStatus(request, cshTransactionHeader, cshWriteOff.getCshTransactionLineId(),
						cshWriteOff.getTransactionAmount(), BaseConstants.NO);
			}
		}
    	return records;
	}

	public void setExpRepWriteOffStatus(IRequest request, Long expReportHeaderId){
		BigDecimal totalDocAmount = cshWriteOffMapper.totalDocAmount(expReportHeaderId, "EXPENSE_REPORT");
		ExpReportHeader expReportHeader = new ExpReportHeader();
		expReportHeader.setExpReportHeaderId(expReportHeaderId);
		if(totalDocAmount.compareTo(BigDecimal.ZERO) == 0){
			expReportHeader.setWriteOffStatus("N");
			expReportHeader.setWriteOffCompletedDate(null);
		}else {
			BigDecimal totalAmount = expReportPmtScheduleMapper.totalAmount(expReportHeaderId);
			if(totalAmount.subtract(totalDocAmount).compareTo(BigDecimal.ZERO) <= 0){
				Date maxWriteDate = cshWriteOffMapper.maxWriteDate(expReportHeaderId, "EXPENSE_REPORT", null);
				expReportHeader.setWriteOffStatus("C");
				expReportHeader.setWriteOffCompletedDate(maxWriteDate);
			}else {
				expReportHeader.setWriteOffStatus("Y");
				expReportHeader.setWriteOffCompletedDate(null);
			}
		}
		reportHeaderService.updateByPrimaryKeySelective(request, expReportHeader);
	}

}
