package com.hand.hec.acp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.*;
import com.hand.hec.acp.exception.AcpRequisitionException;
import com.hand.hec.acp.mapper.AcpRequisitionAccountMapper;
import com.hand.hec.acp.mapper.AcpRequisitionHdMapper;
import com.hand.hec.acp.service.*;
import com.hand.hec.csh.mapper.CshPaymentRequisitionHdMapper;
import com.hand.hec.csh.service.ICshDocPayAccEntityService;
import com.hand.hec.csh.service.ICshWriteOffService;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.expm.service.IExpReportPmtScheduleService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.mapper.GldAePeriodStatusMapper;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpRequisitionHdServiceImpl extends BaseServiceImpl<AcpRequisitionHd> implements IAcpRequisitionHdService {

    @Autowired
    private AcpRequisitionHdMapper acpRequisitionHdMapper;

    @Autowired
    private IFndCodingRuleObjectService codingRuleObjectService;

    @Autowired
    private IFndCompanyService companyService;

    @Autowired
    private IAcpMoPayReqTypeService acpMoPayReqTypeService;

    @Autowired
    private CshPaymentRequisitionHdMapper paymentRequisitionHdMapper;

    @Autowired
    private IExpDocumentHistoryService iExpDocumentHistoryService;

    @Autowired
    private IAcpRequisitionLnService acpRequisitionLnService;

    @Autowired
    private IAcpRequisitionDtlService acpRequisitionDtlService;

    @Autowired
    private IExpReportPmtScheduleService expReportPmtScheduleService;

    @Autowired
    private ICshWriteOffService cshWriteOffService;

    @Autowired
    private ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Autowired
    private ISysParameterService iSysParameterService;

    @Autowired
    private AcpRequisitionAccountMapper acpRequisitionAccountMapper;

    @Autowired
    private GldAccountingEntityMapper gldAccountingEntityMapper;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private GldAePeriodStatusMapper gldAePeriodStatusMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IGldExchangeRateService gldExchangeRateService;

    @Autowired
    private IAcpRequisitionAccountService acpRequisitionAccountService;

    private List<AcpRequisitionAccountTemp> accountTempList = new ArrayList<>();

    private List<AcpReqCurrencyTemp> currencyTempList = new ArrayList<>();

    @Override
    public List<AcpRequisitionHd> queryMain(IRequest request, AcpRequisitionHd acpRequisitionHd, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);

        List<AcpRequisitionHd> list = acpRequisitionHdMapper.queryMain(acpRequisitionHd);
        // 根据状态筛选 如可维护 :GENERATE;TAKEN_BACK;REJECTED
        if (acpRequisitionHd.getStatus() != null) {
            String status = acpRequisitionHd.getStatus();
            list = list.parallelStream().filter(header -> {
                return status.indexOf(header.getStatus()) != -1;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public AcpRequisitionHd selectByHeaderId(IRequest request, AcpRequisitionHd acpRequisitionHd) {
        return acpRequisitionHdMapper.selectByHeaderId(acpRequisitionHd);
    }

    @Override
    public List<AcpRequisitionHd> batchUpdate(IRequest request, List<AcpRequisitionHd> list) {
        for (AcpRequisitionHd acpRequisitionHd : list) {
            if (acpRequisitionHd.getRequisitionHdsId() == null) {
                acpRequisitionHd = this.insertAcpRequisitionHds(request, acpRequisitionHd);
            } else {
                acpRequisitionHd = this.updateAcpRequisitionHds(request, acpRequisitionHd);
            }
            List<AcpRequisitionLn> lines = acpRequisitionHd.getLines();
            if (lines != null) {
                acpRequisitionLnService.batchUpdate(request, lines, acpRequisitionHd);
            }
        }
        return list;
    }

    /**
     * 获取付款申请单头记录-根据付款申请单头 ID
     *
     * @author Tagin
     * @date 2019-05-07 21:12
     * @param iRequest 请求
     * @param requisitionHdsId 付款申请单头 ID
     * @return com.hand.hec.acp.dto.AcpRequisitionHd
     * @version 1.0
     **/
    @Override
    public AcpRequisitionHd getAcpRequisitionHeader(IRequest iRequest, Long requisitionHdsId) {
        return acpRequisitionHdMapper.getAcpRequisitionHeader(requisitionHdsId);
    }

    private AcpRequisitionHd insertAcpRequisitionHds(IRequest request, AcpRequisitionHd acpRequisitionHd) {
        // 1.0 获取值（ID及单据编号）
        FndCompany company = companyService.getCompany(request, acpRequisitionHd.getCompanyId());
        String requisitionNumber = codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_ACP_PAY_REQ,
                        acpRequisitionHd.getMoPayReqTypeId().toString(), company.getMagOrgId(),
                        acpRequisitionHd.getCompanyId(), acpRequisitionHd.getAccEntityId());
        if (requisitionNumber == null) {
            throw new AcpRequisitionException(AcpRequisitionException.DOC_NUMBER_ERROR, null);
        }
        acpRequisitionHd.setRequisitionNumber(requisitionNumber);

        String payReqTypeBusiness = "";
        if (this.getBusinessFlag(request, acpRequisitionHd).equals(BaseConstants.YES)) {
            payReqTypeBusiness = AcpRequisitionHd.PAY_REQ_TYPE_BUSINESS;
        } else {
            payReqTypeBusiness = AcpRequisitionHd.PAY_REQ_TYPE_MISCELLANEOUS;
        }

        // 2.0 录入
        // 获取时间戳
        Long requisitionDateL = DateUtils.getTimeStamp(acpRequisitionHd.getRequisitionDate());
        Timestamp reqTimeStamp = new Timestamp(requisitionDateL);
        acpRequisitionHd.setRequisitionDateTz(reqTimeStamp);
        acpRequisitionHd.setRequisitionDateLtz(reqTimeStamp);

        acpRequisitionHd = self().insertSelective(request, acpRequisitionHd);

        // 3.0 写入历史
        iExpDocumentHistoryService.insertDocumentHistory(request, AcpRequisitionHd.ACP_REQUISITION,
                        acpRequisitionHd.getRequisitionHdsId(), ExpDocumentHistory.STATUS_GENERATE,
                        acpRequisitionHd.getEmployeeId(), acpRequisitionHd.getDescription());
        return acpRequisitionHd;
    }

    private AcpRequisitionHd updateAcpRequisitionHds(IRequest request, AcpRequisitionHd acpRequisitionHd) {
        if (this.updateStatusCheck(request, acpRequisitionHd.getRequisitionHdsId()).equals(BaseConstants.YES)) {
            // 获取时间戳
            Long requisitionDateL = DateUtils.getTimeStamp(acpRequisitionHd.getRequisitionDate());
            Timestamp reqTimeStamp = new Timestamp(requisitionDateL);
            acpRequisitionHd.setRequisitionDateTz(reqTimeStamp);
            acpRequisitionHd.setRequisitionDateLtz(reqTimeStamp);

            if (acpRequisitionHd.getApprovalDate() != null) {
                Long approveDateL = DateUtils.getTimeStamp(acpRequisitionHd.getApprovalDate());
                acpRequisitionHd.setApprovalDateTz(new Timestamp(approveDateL));
                acpRequisitionHd.setApprovalDateLtz(acpRequisitionHd.getApprovalDateLtz());
            }


            if (acpRequisitionHd.getClosedDate() != null) {
                Long closeDateL = DateUtils.getTimeStamp(acpRequisitionHd.getClosedDate());
                acpRequisitionHd.setClosedDateTz(new Timestamp(closeDateL));
                acpRequisitionHd.setClosedDateLtz(acpRequisitionHd.getClosedDateLtz());
            }


            acpRequisitionHd = self().updateByPrimaryKey(request, acpRequisitionHd);

        }
        return acpRequisitionHd;
    }

    /**
     * 根据付款申请单类型ID获取是否经营类
     *
     * @param acpRequisitionHd
     * @author guiyuting 2019-05-05 16:03
     * @return
     */
    private String getBusinessFlag(IRequest request, AcpRequisitionHd acpRequisitionHd) {
        String vBusinessFlag = "";
        AcpMoPayReqType acpMoPayReqType = acpMoPayReqTypeService.selectByPrimaryKey(request,
                        AcpMoPayReqType.builder().moPayReqTypeId(acpRequisitionHd.getMoPayReqTypeId()).build());
        return vBusinessFlag;
    }

    /**
     * 检验付款申请单能否更新
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-05 16:02
     * @return
     */
    @Override
    public String updateStatusCheck(IRequest request, Long requisitionHdsId) {
        List<String> statusList = Arrays.asList("GENERATE", "REJECTED", "TAKEN_BACK");
        List<AcpRequisitionHd> list = self().select(request,
                        AcpRequisitionHd.builder().requisitionHdsId(requisitionHdsId).build(), 0, 0);
        list = list.parallelStream().filter(record -> statusList.contains(record.getDocStatus()))
                        .collect(Collectors.toList());
        return list.size() == 0 ? BaseConstants.YES : BaseConstants.NO;
    }


    @Override
    public boolean submitAcpRequisition(IRequest request, Long requisitionHdsId) {
        if (this.updateStatusCheck(request, requisitionHdsId).equals(BaseConstants.YES)) {
            AcpRequisitionHd header = self().selectByPrimaryKey(request,
                            AcpRequisitionHd.builder().requisitionHdsId(requisitionHdsId).build());
            List<AcpRequisitionLn> lines = acpRequisitionLnService.select(request,
                            AcpRequisitionLn.builder().requisitionHdsId(requisitionHdsId).build(), 0, 0);
            if (lines.isEmpty()) {
                throw new AcpRequisitionException(AcpRequisitionException.UNEXISTS_LINES_ERROR, null);
            }
            AcpMoPayReqType acpMoPayReqType = acpMoPayReqTypeService.selectByPrimaryKey(request,
                            AcpMoPayReqType.builder().moPayReqTypeId(header.getMoPayReqTypeId()).build());

            // 1.0 资金计划校验【还未完成】/金额校验
            this.checkAmount(request, requisitionHdsId);
            // 2.0 自审批
            if (acpMoPayReqType.getAutoApproveFlag().equals(BaseConstants.YES)) {
                // 2.0.1 更新单据头
                this.updateHeaderSelective(request, header, "Y");
                // 2.0.2 更新单据行
                this.updateLineStatus(request, requisitionHdsId);
                // 自审批历史
                iExpDocumentHistoryService.insertDocumentHistory(request, AcpRequisitionHd.ACP_REQUISITION,
                                header.getRequisitionHdsId(), ExpDocumentHistory.STATUS_APPROVE, header.getEmployeeId(),
                                "付款申请单[" + header.getRequisitionNumber() + "]自审批完成");
                // 触发付款申请单审批通过事件
            } else {
                // 2.0.1 更新单据头
                this.updateHeaderSelective(request, header, "N");
                // 2.0.2 更新单据行
                this.updateLineStatus(request, requisitionHdsId);
                // 提交历史
                iExpDocumentHistoryService.insertDocumentHistory(request, AcpRequisitionHd.ACP_REQUISITION,
                                header.getRequisitionHdsId(), ExpDocumentHistory.STATUS_SUBMIT, header.getEmployeeId(),
                                "付款申请单[" + header.getRequisitionNumber() + "]提交完成");
                // 触发付款申请单审批通过事件
                /**
                 * TODO:ZYJ不需要，未实现 this.prepareAcpRequistion();
                 */

            }

        }
        return true;
    }

    @Override
    public boolean deleteAcpRequisition(IRequest request, Long requisitionHdsId) {
        if (this.updateStatusCheck(request, requisitionHdsId).equals(BaseConstants.YES)) {
            // 删除对应工作流实例
            // 1.0 资金计划
            // 2.0 付款信息
            List<AcpRequisitionLn> lines = acpRequisitionLnService.select(request,
                            AcpRequisitionLn.builder().requisitionHdsId(requisitionHdsId).build(), 0, 0);
            for (AcpRequisitionLn line : lines) {
                cshDocPayAccEntityService.deletePayAccEntity(request, AcpRequisitionHd.ACP_REQUISITION,
                                line.getRequisitionHdsId(), line.getRequisitionLnsId());
            }
            // 3.0 付款明细行
            List<AcpRequisitionDtl> dtls = acpRequisitionDtlService.queryByHeaderId(requisitionHdsId);
            dtls.forEach(acpRequisitionDtl -> {
                acpRequisitionDtlService.deleteByPrimaryKey(acpRequisitionDtl);
            });

            // 4.0 付款申请行
            acpRequisitionLnService.deleteByHeaderId(requisitionHdsId);
            // 5.0 付款申请头
            self().deleteByPrimaryKey(AcpRequisitionHd.builder().requisitionHdsId(requisitionHdsId).build());
            // 6.0 单据历史
            iExpDocumentHistoryService.delteDocumentHistory(request, AcpRequisitionHd.ACP_REQUISITION,
                            requisitionHdsId);

        }
        return true;
    }

    /**
     * 保存/提交时校验总金额
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-06 14:35
     * @return
     */
    private void checkAmount(IRequest request, Long requisitionHdsId) {
        // 1.0 头信息
        AcpRequisitionHd header = self().selectByPrimaryKey(request,
                        AcpRequisitionHd.builder().requisitionHdsId(requisitionHdsId).build());

        // 2.0 行金额
        BigDecimal totalMount = acpRequisitionLnService.getTotalAmount(requisitionHdsId);

        // 3.0 校验头金额与行金额汇总
        if (header.getAmount().compareTo(totalMount) != 0) {
            throw new AcpRequisitionException(AcpRequisitionException.LNS_AMOUNT_ERROR, null);
        }

        // 4.0 若为报销结算/合同结算，校验行与明细行汇总
        AcpMoPayReqType acpMoPayReqType = acpMoPayReqTypeService.selectByPrimaryKey(request,
                        AcpMoPayReqType.builder().moPayReqTypeId(header.getMoPayReqTypeId()).build());
        if (acpMoPayReqType.getPaymentTypeCode().equals(AcpRequisitionHd.PAYMENT_TYPE_REPORT)
                        || acpMoPayReqType.getPaymentTypeCode().equals(AcpRequisitionHd.PAYMENT_TYPE_CONTRACT)) {
            List<AcpRequisitionLn> lines = acpRequisitionLnService.select(request,
                            AcpRequisitionLn.builder().requisitionHdsId(requisitionHdsId).build(), 0, 0);
            for (AcpRequisitionLn line : lines) {
                BigDecimal dtlTotalAmount = acpRequisitionDtlService.getTotalAmount(line.getRequisitionLnsId());
                if (line.getAmount().compareTo(dtlTotalAmount) != 0) {
                    throw new AcpRequisitionException(AcpRequisitionException.DTL_AMOUNT_ERROR, null);
                }
            }
            // 4.0.1 校验明细行可用
            List<AcpRequisitionDtl> dtls = acpRequisitionDtlService.queryByHeaderId(requisitionHdsId);
            for (AcpRequisitionDtl dtl : dtls) {
                if (this.checkDtlAmount(request, dtl).equals(BaseConstants.NO)) {
                    throw new AcpRequisitionException(AcpRequisitionException.USED_AMOUNT_ERROR, null);
                }
            }

        }
    }

    /**
     * 校验明细行金额
     *
     * @param acpRequisitionDtl
     * @author guiyuting 2019-05-06 16:39
     * @return
     */
    private String checkDtlAmount(IRequest request, AcpRequisitionDtl acpRequisitionDtl) {
        String flag = BaseConstants.NO;
        if (acpRequisitionDtl.getRefDocumentType().equals(AcpRequisitionLn.REF_DOCUMENT_TYPE_REPORT)) {
            // 1.0.1 总金额[计划付款行]
            BigDecimal totalAmount =
                            expReportPmtScheduleService.getTotalAmountByLine(acpRequisitionDtl.getRefDocumentLineId());
            // 1.0.2 已付金额
            BigDecimal paidAmount = cshWriteOffService.getPaidAmountByAcpReq(acpRequisitionDtl.getRefDocumentLineId());
            // 1.0.3 其他金额
            BigDecimal otherAmount = acpRequisitionDtlService.getOtherAmount(acpRequisitionDtl);

            BigDecimal result = totalAmount.subtract(paidAmount).subtract(otherAmount)
                            .subtract(acpRequisitionDtl.getAmount());
            if (result.compareTo(new BigDecimal(0)) >= 0) {
                flag = BaseConstants.YES;
            }
        } else if (acpRequisitionDtl.getRefDocumentType().equals(AcpRequisitionLn.REF_DOCUMENT_TYPE_CONTRACT)) {
            flag = BaseConstants.YES;
        }
        return flag;

    }

    private void updateHeaderSelective(IRequest request, AcpRequisitionHd acpRequisitionHd, String autoApproveFlag) {
        AcpRequisitionHd header = new AcpRequisitionHd();
        header.setRequisitionHdsId(acpRequisitionHd.getRequisitionHdsId());
        if (autoApproveFlag.equals(BaseConstants.YES)) {
            header.setStatus(AcpRequisitionHd.STATUS_APPROVED);
            // 获取时间戳
            Date approveDate = DateUtils.getCurrentDate();
            Long approveDateL = DateUtils.getTimeStamp(approveDate);
            header.setApprovalDate(approveDate);
            header.setApprovalDateTz(new Timestamp(approveDateL));
            header.setApprovalDateLtz(new Timestamp(approveDateL));
            header.setApprovedBy(request.getUserId());
        } else {
            header.setStatus(AcpRequisitionHd.STATUS_SUBMITTED);
        }
        self().updateByPrimaryKeySelective(request, header);
    }

    private void updateLineStatus(IRequest request, Long requisitionHdsId) {
        AcpRequisitionLn line = new AcpRequisitionLn();
        line.setRequisitionHdsId(requisitionHdsId);
        line.setPaymentStatus(AcpRequisitionLn.PAYMENT_STATUS_NEVER);
        acpRequisitionLnService.updateStatusByHeaderId(request, line);
    }

    @Override
    public List<AcpRequisitionHd> queryAuditResult(IRequest request, AcpRequisitionHd dto, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return acpRequisitionHdMapper.queryAuditResult(dto);
    }

    @Override
    public boolean createAcpRequisitionAccount(IRequest request, Long accEntityId, Date journalDate,
                    List<AcpRequisitionHd> details) {
        this.dealWithTemp(request, accEntityId, journalDate, details);

        // 开始创建付款申请单凭证
        acpRequisitionAccountService.createAcpRequisitionAccount(request, accEntityId, journalDate,
                        this.accountTempList, this.currencyTempList);
        return true;
    }



    /**
     * 对用4.0 acp_requistion_audit_create_currency_tmp.svc
     *
     * @param accEntityId 核算主体ID
     * @param journalDate 币种汇率的期间
     * @author guiyuting 2019-05-08 10:45
     * @return
     */
    private boolean dealWithTemp(IRequest request, Long accEntityId, Date journalDate, List<AcpRequisitionHd> details) {
        this.accountTempList = new ArrayList<>();

        details.forEach(acpRequisitionHd -> {
            this.insAcpReqAccountsTmp(acpRequisitionHd.getRequisitionHdsId(), request);
        });

        this.createCurrencyCodeTmp(request, accEntityId, journalDate);
        return true;
    }

    /**
     * 创建选择单据临时数据
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-08 10:27
     * @return
     */
    private boolean insAcpReqAccountsTmp(Long requisitionHdsId, IRequest request) {
        AcpRequisitionAccountTemp temp = AcpRequisitionAccountTemp.builder().requisitionHdsId(requisitionHdsId)
                        .creationDate(new Date()).build();
        this.accountTempList.add(temp);
        return true;
    }

    private boolean createCurrencyCodeTmp(IRequest request, Long accEntityId, Date journalDate) {
        String companyCurrencyCode = "";
        String defaultExchangeRateType = "";
        String periodName = null;
        BigDecimal exchangeRate = null;
        Date lastJournalDate = DateUtils.getCurrentDate();
        List<Long> headerIds = this.accountTempList.parallelStream().map(AcpRequisitionAccountTemp::getRequisitionHdsId)
                        .distinct().collect(Collectors.toList());
        // this.currencyTempList = new ArrayList<>();
        for (Long headerId : headerIds) {
            AcpRequisitionHd header = self().selectByPrimaryKey(request,
                            AcpRequisitionHd.builder().requisitionHdsId(headerId).build());
            companyCurrencyCode = gldAccountingEntityMapper.queryFuncCurrencyByEntity(accEntityId);
            periodName = gldPeriodService.getPeriodName(request, journalDate, accEntityId, "O");
            // 当sysdate的期间取不到时,取打开期间的最后一天
            if (periodName == null) {
                lastJournalDate = gldAePeriodStatusMapper.getMaxDateOfPeriod(accEntityId);
                periodName = gldPeriodService.getPeriodName(request, lastJournalDate, accEntityId, "O");
            }

            if (!header.getCurrencyCode().equals(companyCurrencyCode)) {
                if (periodName != null) {
                    defaultExchangeRateType = sysParameterService.queryParamValueByCode(
                                    com.hand.hap.sys.constants.ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE,
                                    null, null, null, accEntityId, null, null, null);

                    if (defaultExchangeRateType != null) {
                        exchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, header.getCurrencyCode(),
                                        companyCurrencyCode, defaultExchangeRateType, lastJournalDate, periodName,
                                        BaseConstants.YES);
                    }
                    AcpReqCurrencyTemp acpReqCurrencyTemp = AcpReqCurrencyTemp.builder()
                                    .requisitionHdsId(header.getRequisitionHdsId())
                                    .currencyCode(header.getCurrencyCode()).exchangeRateType(defaultExchangeRateType)
                                    .exchangeRate(exchangeRate).creationDate(DateUtils.getCurrentDate()).build();
                    this.currencyTempList.add(acpReqCurrencyTemp);
                } else {
                    AcpReqCurrencyTemp acpReqCurrencyTemp =
                                    AcpReqCurrencyTemp.builder().requisitionHdsId(header.getRequisitionHdsId())
                                                    .currencyCode(header.getCurrencyCode())
                                                    .creationDate(DateUtils.getCurrentDate()).build();
                    this.currencyTempList.add(acpReqCurrencyTemp);
                }

            } else {
                AcpReqCurrencyTemp acpReqCurrencyTemp = AcpReqCurrencyTemp.builder()
                                .requisitionHdsId(header.getRequisitionHdsId()).currencyCode(header.getCurrencyCode())
                                .exchangeRate(new BigDecimal(1)).creationDate(DateUtils.getCurrentDate()).build();
                this.currencyTempList.add(acpReqCurrencyTemp);
            }

        }
        return true;

    }

    /**
     * 付款申请单准备入池
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-05-09 15:53
     * @return
     */
    private void prepareAcpRequistion(IRequest request, Long requisitionHdsId) {
        AcpRequisitionHd header = self().getAcpRequisitionHeader(request, requisitionHdsId);
        Long companyId = header.getCompanyId();
        Long magOrgId = header.getMagOrgId();
        // 判断WBC_WORKBENCH_ENABLED参数
        /*
         * String enable = sysParameterService.queryParamValueByCode(
         * com.hand.hap.sys.constants.ParameterConstants.WBC_WORKBENCH_ENABLED, null, null, companyId, null,
         * null, magOrgId, null); if(BaseConstants.YES.equals(enable)){
         * 
         * }
         */

    }

    @Override
    public  List<AcpRequisitionHd> queryRequisition(IRequest request, AcpRequisitionHd dto,String allCompanyFlag, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        List<AcpRequisitionHd> list = acpRequisitionHdMapper.queryRequisition(dto,allCompanyFlag);
        // 根据状态筛选
        if (dto.getStatus() != null) {
            String status = dto.getStatus();
            list = list.parallelStream().filter(header -> {
                return status.indexOf(header.getStatus()) != -1;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public  List<AcpRequisitionHd> queryComRequisition(IRequest request, AcpRequisitionHd dto, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        List<AcpRequisitionHd> list = acpRequisitionHdMapper.queryComRequisition(dto);
        // 根据状态筛选
        if (dto.getStatus() != null) {
            String status = dto.getStatus();
            list = list.parallelStream().filter(header -> {
                return status.indexOf(header.getStatus()) != -1;
            }).collect(Collectors.toList());
        }
        return list;
    }
}
