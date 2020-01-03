package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachmentRefRecord;
import com.hand.hap.aurora.attachment.service.IFndAtmAttachmentRefRecordService;
import com.hand.hap.aurora.attachment.service.IFndAtmAttachmentService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.mapper.ExpEmployeeAssignMapper;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.fnd.dto.FndCompanyRefAccBe;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.mapper.FndCompanyRefBgtEntityMapper;
import com.hand.hap.fnd.service.IFndCompanyRefAccBeService;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.IPromptService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.bgt.service.*;
import com.hand.hec.csh.dto.CshCnaps;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.dto.CshWriteOff;
import com.hand.hec.csh.dto.CshWriteOffAccount;
import com.hand.hec.csh.mapper.CshWriteOffMapper;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.ExpOrgUnitRefAccOrgMapper;
import com.hand.hec.exp.service.*;
import com.hand.hec.expm.dto.*;
import com.hand.hec.expm.exception.ExpReportAccountException;
import com.hand.hec.expm.exception.ExpReportException;
import com.hand.hec.expm.mapper.*;
import com.hand.hec.expm.service.*;
import com.hand.hec.fnd.dto.*;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.*;
import com.hand.hec.gld.dto.*;
import com.hand.hec.gld.service.*;
import com.hand.hec.pur.dto.PurSystemVender;
import com.hand.hec.pur.dto.PurVenderAccount;
import com.hand.hec.pur.service.IPurSystemVenderService;
import com.hand.hec.pur.service.IPurVenderAccountService;
import com.hand.hec.vat.dto.VatInvoice;
import com.hand.hec.vat.service.IVatInvoiceRelationLnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * ExpReportHeaderServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportHeaderServiceImpl extends BaseServiceImpl<ExpReportHeader> implements IExpReportHeaderService {
    /*
     * COMPLETELY_APPROVED 审批通过 AUDIT 审核通过 PAID 部分支付 COMPLETELY_PAID 完全支付 REVERSE 反冲
     */
    public static final String PROGRESS_STATUS_COMPLETELY_APPROVED = "COMPLETELY_APPROVED";
    public static final String PROGRESS_STATUS_AUDIT = "AUDIT";
    public static final String PROGRESS_STATUS_PAID = "PAID";
    public static final String PROGRESS_STATUS_COMPLETELY_PAID = "COMPLETELY_PAID";
    public static final String PROGRESS_STATUS_REVERSE = "REVERSE";
    public static final String PROGRESS_STATUS_SUBMITTED = "PROGRESS_STATUS";

    @Autowired
    private ExpReportHeaderMapper reportHeaderMapper;


    @Autowired
    private ExpReportLineMapper expReportLineMapper;

    @Autowired
    private GldCurrencyMapper gldCurrencyMapper;


    // 费用对象Mapper
    @Autowired
    private ExpReportObjectMapper expReportObjectMapper;

    // 费用对象Service
    @Autowired
    private IExpReportObjectService expReportObjectService;

    // 计划付款行mapper
    @Autowired
    private ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    // 税金行mapper
    @Autowired
    private ExpReportPmtSchTaxLineMapper expReportPmtSchTaxLineMapper;

    // 核销mapper
    @Autowired
    private CshWriteOffMapper cshWriteOffMapper;

    // 系统代码
    @Autowired
    private ICodeService codeService;

    // 单据历史
    @Autowired
    private IExpDocumentHistoryService expDocumentHistoryService;

    // 报销单类型
    @Autowired
    private IExpMoReportTypeService reportTypeService;

    // 报销单标准行
    @Autowired
    private IExpReportLineService lineService;

    // 报销单差旅行
    @Autowired
    private IExpReportTravelLineService travelLineService;


    // 报销单计划付款行
    @Autowired
    private IExpReportPmtScheduleService pmtScheduleService;


    @Autowired
    private IGldExchangeRateService gldExchangeRateService;


    @Autowired
    private IGldExchangerateTypeService gldExchangerateTypeService;

    @Autowired
    private ExpEmployeeMapper expEmployeeMapper;

    @Autowired
    private IExpEmployeeAccountService expEmployeeAccountService;

    @Autowired
    private IPurVenderAccountService purVenderAccountService;

    @Autowired
    private IOrdCustomerAccountService ordCustomerAccountService;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    @Autowired
    private ICshCnapsService cshCnapsService;

    @Autowired
    private IBgtPeriodService bgtPeriodService;


    @Autowired
    private IExpEmployeeAssignService expEmployeeAssignService;

    @Autowired
    private ExpEmployeeAssignMapper expEmployeeAssignMapper;

    @Autowired
    private IExpOrgPositionService expOrgPositionService;

    @Autowired
    private IExpOrgUnitService expOrgUnitService;

    @Autowired
    private IGldResponsibilityCenterService gldResponsibilityCenterService;

    @Autowired
    private ExpOrgUnitRefAccOrgMapper expOrgUnitRefAccOrgMapper;

    @Autowired
    private IBgtEntityService bgtEntityService;

    @Autowired
    private IFndCompanyRefAccBeService fndCompanyRefAccBeService;

    @Autowired
    private IFndCompanyRefBgtEntityService fndCompanyRefBgtEntityService;

    @Autowired
    private FndCompanyRefBgtEntityMapper fndCompanyRefBgtEntityMapper;

    @Autowired
    private IBgtCenterService bgtCenterService;


    @Autowired
    private IExpOrgUnitRefAccOrgService expOrgUnitRefAccOrgService;

    @Autowired
    private IGldRespCenterRefBcService gldRespCenterRefBcService;

    @Autowired
    private IExpOrgUnitRefBgtOrgService expOrgUnitRefBgtOrgService;

    @Autowired
    private IExpMoRepTypeRefHdDimService expMoRepTypeRefHdDimService;

    @Autowired
    private IExpEmployeeService employeeService;

    @Autowired
    private IPurSystemVenderService purSystemVenderService;

    @Autowired
    private IOrdSystemCustomerService ordSystemCustomerService;

    @Autowired
    private IFndCodingRuleObjectService codingRuleObjectService;

    @Autowired
    private ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Autowired
    private IExpRequisitionLineService requisitionLineService;

    @Autowired
    private IBgtCheckService bgtCheckService;

    @Autowired
    private BgtBudgetReserveMapper budgetReserveMapper;

    @Autowired
    private IExpRequisitionReleaseService requisitionReleaseService;

    @Autowired
    private IGlAccountEntryService glAccountEntryService;


    @Autowired
    private ExpReportHeaderMapper mapper;

    @Autowired
    private IExpReportHeaderService service;

    @Autowired
    private IExpReportAccountService accountService;

    @Autowired
    private GldPeriodMapper gldPeriodMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ExpReportAccountMapper expReportAccountMapper;


    @Autowired
    private ExpReportDistMapper expReportDistMapper;


    @Autowired
    private ICshWriteOffService cshWriteOffService;

    @Autowired
    private ICshWriteOffAccountService cshWriteOffAccountService;

    @Autowired
    private ICshTransactionHeaderService cshTransactionHeaderService;


    @Autowired
    private IExpReportAccountService expReportAccountService;

    @Autowired
    private IGldMappingConditionService gldMappingConditionService;

    @Autowired
    private IExpReportLineService expReportLineService;

    @Autowired
    private IExpReportDistService expReportDistService;

    @Autowired
    private IBgtBudgetReserveService bgtBudgetReserveService;

    @Autowired
    private IExpRequisitionReleaseService expRequisitionReleaseService;

    @Autowired
    private IExpRequisitionDistService expRequisitionDistService;

    @Autowired
    private IExpReportPmtScheduleService expReportPmtScheduleService;


    @Autowired
    private IGldAccountingEntityService accountingEntityService;

    @Autowired
    private IExpOrgUnitRefAccOrgService refAccOrgService;

    @Autowired
    private IExpOrgUnitRefBgtOrgService refBgtOrgService;


    @Autowired
    private IPromptService promptService;


    @Autowired
    private IBgtCheckLogService bgtCheckLogService;

    @Autowired
    private IFndAtmAttachmentService fndAtmAttachmentService;

    @Autowired
    private IFndAtmAttachmentRefRecordService fndAtmAttachmentRefRecordService;


    @Autowired
    private DatabaseLockProvider databaseLockProvider;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @Autowired
    private IVatInvoiceRelationLnService vatInvoiceRelationLnService;

    /**
     * <p>
     * 我的费用报销主查询
     * <p/>
     *
     * @param dto     查询DTO
     * @param request
     * @return 报销单主页面信息list
     * @author yang.duan 2019/3/12 14:04
     */
    @Override
    public List<ExpReportHeader> queryExpReportMain(ExpReportHeader dto, IRequest request, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        dto.setCompanyId(request.getCompanyId());
        dto.setCreatedBy(request.getUserId());
        List<ExpReportHeader> headerList = reportHeaderMapper.queryExpReportMain(dto);
        if (!headerList.isEmpty()) {
            headerList = this.exchangeProgressStatus(request, headerList, dto);
        }
        return headerList;
    }

    // 转换单据进度状态字段
    private List<ExpReportHeader> exchangeProgressStatus(IRequest request, List<ExpReportHeader> dtoList, ExpReportHeader dto) {
        BigDecimal writeOffAmount = new BigDecimal(0);
        BigDecimal scheduleAmount = new BigDecimal(0);
        BigDecimal taxAmount = new BigDecimal(0);
        for (ExpReportHeader h : dtoList) {
            if (!h.getReportStatus().equals(PROGRESS_STATUS_COMPLETELY_APPROVED)) {
                h.setProgressStatus(h.getReportStatus());
            } else {
                if ((h.getAuditFlag() != null) && ("N".equals(h.getAuditFlag()))) {
                    h.setProgressStatus(PROGRESS_STATUS_COMPLETELY_APPROVED);
                } else {
                    // 获取报销单核销金额
                    writeOffAmount = cshWriteOffMapper.totalDocAmount(h.getExpReportHeaderId(), "EXPENSE_REPORT");
                    if (writeOffAmount.compareTo(BigDecimal.ZERO) == 0) {
                        if ("W".equals(h.getReversedFlag())) {
                            h.setProgressStatus(PROGRESS_STATUS_REVERSE);
                        } else {
                            h.setProgressStatus(PROGRESS_STATUS_AUDIT);
                        }
                    } else {
                        // 获取计划付款行金额
                        scheduleAmount = expReportPmtScheduleMapper.totalAmount(h.getExpReportHeaderId());
                        // 获取税额
                        taxAmount = expReportPmtSchTaxLineMapper.getTaxAmountByHeaderId(h.getExpReportHeaderId());
                        if (scheduleAmount.compareTo((writeOffAmount.add(taxAmount))) != 0) {
                            h.setProgressStatus(PROGRESS_STATUS_PAID);
                        } else {
                            h.setProgressStatus(PROGRESS_STATUS_COMPLETELY_PAID);
                        }
                    }
                }
            }
            if ((dto.getStatus() != null) && (!dto.getStatus().contains(h.getProgressStatus()))) {
                dtoList.remove(h);
                if (dtoList.isEmpty()) {
                    break;
                }
            } else {
                h.setProgressStatusName(codeService.getCodeMeaningByValue(request, "DOC_PROGRESS_STATUS", h.getProgressStatus()));
                h.setProgressCount(this.getProgressCount(h.getProgressStatus()));
            }
        }
        return dtoList;
    }

    // 获取单据进度对应百分比
    private Long getProgressCount(String expProgressStatus) {
        switch (expProgressStatus) {
            case PROGRESS_STATUS_SUBMITTED:
                return Long.valueOf(30);
            case PROGRESS_STATUS_COMPLETELY_APPROVED:
                return Long.valueOf(45);
            case PROGRESS_STATUS_AUDIT:
                return Long.valueOf(60);
            case PROGRESS_STATUS_REVERSE:
                return Long.valueOf(75);
            case PROGRESS_STATUS_PAID:
                return Long.valueOf(90);
            case PROGRESS_STATUS_COMPLETELY_PAID:
                return Long.valueOf(100);
            default:
                return Long.valueOf(15);
        }
    }

    /**
     * <p>
     * 报销单头保存(页面保存)
     * <p/>
     *
     * @param request
     * @param headers 头信息
     * @return 返回保存后的头信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 14:38
     */
    public List<ExpReportHeader> saveReportHeader(IRequest request, List<ExpReportHeader> headers) throws RuntimeException {
        if (headers != null && !headers.isEmpty()) {
            for (ExpReportHeader header : headers) {
                ExpMoReportType reportType = new ExpMoReportType();
                reportType.setMoExpReportTypeId(header.getMoExpReportTypeId());
                // 获取单据类型
                reportType = reportTypeService.selectByPrimaryKey(request, reportType);
                // 头保存
                header = saveHeader(request, header, reportType);

                // 标准行保存
                List<ExpReportLine> standardLines = header.getStandardLines();
                if (standardLines != null && !standardLines.isEmpty()) {
                    standardLines = saveStandardLines(request, header, reportType, standardLines);
                }


                // 精简行保存
                List<ExpReportLine> streamlinedLines = header.getStreamlinedLines();
                if (streamlinedLines != null && !streamlinedLines.isEmpty()) {
                    streamlinedLines = saveStandardLines(request, header, reportType, streamlinedLines);
                }

                // 机票统报行
                List<ExpReportTravelLine> ticketUnifiedLines = header.getTicketUnifiedLines();
                if (ticketUnifiedLines != null && !ticketUnifiedLines.isEmpty()) {
                    ticketUnifiedLines = saveTravelLines(request, header, reportType, ticketUnifiedLines);
                }

                // 差旅报销行程行
                List<ExpReportTravelLine> travelLines = header.getTravelLines();
                if (travelLines != null && !travelLines.isEmpty()) {
                    travelLines = saveTravelLines(request, header, reportType, travelLines);
                }
                // 差旅报销住宿行
                List<ExpReportTravelLine> travelStayLines = header.getTravelStayLines();
                if (travelStayLines != null && !travelStayLines.isEmpty()) {
                    travelStayLines = saveTravelLines(request, header, reportType, travelStayLines);
                }

                // 住宿统报行
                List<ExpReportTravelLine> travelStayUnified = header.getTravelStayUnified();
                if (travelStayUnified != null && !travelStayUnified.isEmpty()) {
                    travelStayUnified = saveTravelLines(request, header, reportType, travelStayUnified);
                }
                // 差旅报销补贴行
                List<ExpReportTravelLine> travelSubsidyLines = header.getTravelSubsidyLines();
                if (travelSubsidyLines != null && !travelSubsidyLines.isEmpty()) {
                    travelSubsidyLines = saveTravelLines(request, header, reportType, travelSubsidyLines);
                }
                // 计划付款行
                List<ExpReportPmtSchedule> pmtSchedules = header.getPmtSchedules();
                if (pmtSchedules != null && !pmtSchedules.isEmpty()) {
                    savePmtSchedules(request, header, reportType);
                }
                // 自动生成计划付款行
                pmtScheduleService.createExpRepPmtSchedule(request, header, standardLines, reportType);
                // 自动调整增值税拆分税额
            }
        }
        return headers;
    }


    /**
     * <p>
     * 头保存(包含头费用对象保存)
     * <p/>
     *
     * @param request
     * @param header
     * @return ExpReportHeader
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/5 11:17
     */
    private ExpReportHeader saveHeader(IRequest request, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException {
        ExpReportHeader oldHeader = new ExpReportHeader();
        if (header != null && header.getRespCenterId() == null) {
            throw new ExpReportException("EXP", ExpReportException.EXP5110_DFT_RESP_CENTER_IS_NULL_ERROR, null);
        }
        if (header.getExpReportHeaderId() != null) {
            oldHeader = self().selectByPrimaryKey(request, header);
            if (statusCheck(request, header.getExpReportHeaderId())) {
                if ("Y".equals(checkDim(header, oldHeader))) {
                    // 头维度如果发生变化,则更新行/分配行维度
                    lineService.updateLineDim(request, header);
                }
                header.setObjectVersionNumber(oldHeader.getObjectVersionNumber());
                // 更新报销单头
                header = self().updateByPrimaryKeySelective(request, header);
            }
        } else {
            // 设置单据编号
            String expReportNumber = codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_EXP_REPORT, header.getMoExpReportTypeId().toString(), header.getMagOrgId(), header.getCompanyId(), null);
            if (expReportNumber == null || "".equals(expReportNumber)) {
                throw new ExpReportException("EXP", ExpReportException.EXP5110_GET_DOCUMENT_NUMBER_ERROR, null);
            }
            header.setExpReportNumber(expReportNumber);
            // 设置摊销标志
            header.setAmortizationFlag(reportType.getAmortizationFlag());
            if ("Y".equals(reportType.getAutoAuditFlag())) {
                // header.setJeCreationStatus("NEVER");
                header.setAuditFlag("Y");
                header.setAuditDate(new Date());
            } else {
                // header.setJeCreationStatus("N");
                header.setAuditFlag("N");
                header.setAuditDate(null);
            }
            // 插入数据
            header.setJeCreationStatus("N");
            header.setGldInterfaceFlag("N");
            header.setWriteOffStatus("N");
            header.setReversedFlag("N");
            header.setReportStatus(ExpReportHeader.GENERATE);
            header.setReportDateTz(header.getReportDate());
            header.setReportDateLtz(header.getReportDate());
            header.setAuditDateTz(header.getAuditDate());
            header.setAuditDateLtz(header.getAuditDate());


            header.setCreatedBy(request.getUserId());
            header.setCreationDate(new Date());
            header.setLastUpdatedBy(request.getUserId());
            header.setLastUpdateDate(new Date());
            header = self().insertSelective(request, header);
            // 插入单据历史
            expDocumentHistoryService.insertDocumentHistory(request, ExpReportHeader.EXP_REPORT, header.getExpReportHeaderId(), ExpDocumentHistory.STATUS_GENERATE, request.getEmployeeId(), header.getDescription());
            // 创建报销单历史--自审核
            if ("Y".equals(header.getAuditFlag())) {
                expDocumentHistoryService.insertDocumentHistory(request, ExpReportHeader.EXP_REPORT, header.getExpReportHeaderId(), ExpDocumentHistory.STATUS_AUDIT, request.getEmployeeId(), "");
            }
        }
        // 保存头费用对象
        saveHeaderObject(request, header);
        // 保存报销单与通用单据头的关系(未)
        return header;
    }


    // 校验维度是否发生改变
    private String checkDim(ExpReportHeader header, ExpReportHeader oldHeader) {
        List<Long> dimList = new ArrayList<>();
        List<Long> oldDimList = new ArrayList<>();
        String flag = "N";
        dimList.add(header.getDimension1Id() != null ? header.getDimension1Id() : Long.valueOf(0));
        dimList.add(header.getDimension2Id() != null ? header.getDimension2Id() : Long.valueOf(0));
        dimList.add(header.getDimension3Id() != null ? header.getDimension3Id() : Long.valueOf(0));
        dimList.add(header.getDimension4Id() != null ? header.getDimension4Id() : Long.valueOf(0));
        dimList.add(header.getDimension5Id() != null ? header.getDimension5Id() : Long.valueOf(0));
        dimList.add(header.getDimension6Id() != null ? header.getDimension6Id() : Long.valueOf(0));
        dimList.add(header.getDimension7Id() != null ? header.getDimension7Id() : Long.valueOf(0));
        dimList.add(header.getDimension8Id() != null ? header.getDimension8Id() : Long.valueOf(0));
        dimList.add(header.getDimension9Id() != null ? header.getDimension9Id() : Long.valueOf(0));
        dimList.add(header.getDimension10Id() != null ? header.getDimension10Id() : Long.valueOf(0));
        dimList.add(header.getDimension11Id() != null ? header.getDimension11Id() : Long.valueOf(0));
        dimList.add(header.getDimension12Id() != null ? header.getDimension12Id() : Long.valueOf(0));
        dimList.add(header.getDimension13Id() != null ? header.getDimension13Id() : Long.valueOf(0));
        dimList.add(header.getDimension14Id() != null ? header.getDimension14Id() : Long.valueOf(0));
        dimList.add(header.getDimension15Id() != null ? header.getDimension15Id() : Long.valueOf(0));
        dimList.add(header.getDimension16Id() != null ? header.getDimension16Id() : Long.valueOf(0));
        dimList.add(header.getDimension17Id() != null ? header.getDimension17Id() : Long.valueOf(0));
        dimList.add(header.getDimension18Id() != null ? header.getDimension18Id() : Long.valueOf(0));
        dimList.add(header.getDimension19Id() != null ? header.getDimension19Id() : Long.valueOf(0));
        dimList.add(header.getDimension20Id() != null ? header.getDimension20Id() : Long.valueOf(0));


        oldDimList.add(oldHeader.getDimension1Id() != null ? oldHeader.getDimension1Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension2Id() != null ? oldHeader.getDimension2Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension3Id() != null ? oldHeader.getDimension3Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension4Id() != null ? oldHeader.getDimension4Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension5Id() != null ? oldHeader.getDimension5Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension6Id() != null ? oldHeader.getDimension6Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension7Id() != null ? oldHeader.getDimension7Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension8Id() != null ? oldHeader.getDimension8Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension9Id() != null ? oldHeader.getDimension9Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension10Id() != null ? oldHeader.getDimension10Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension11Id() != null ? oldHeader.getDimension11Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension12Id() != null ? oldHeader.getDimension12Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension13Id() != null ? oldHeader.getDimension13Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension14Id() != null ? oldHeader.getDimension14Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension15Id() != null ? oldHeader.getDimension15Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension16Id() != null ? oldHeader.getDimension16Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension17Id() != null ? oldHeader.getDimension17Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension18Id() != null ? oldHeader.getDimension18Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension19Id() != null ? oldHeader.getDimension19Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension20Id() != null ? oldHeader.getDimension20Id() : Long.valueOf(0));

        for (int i = 0; i < dimList.size(); i++) {
            if (dimList.get(i).longValue() != oldDimList.get(i).longValue()) {
                flag = "Y";
                break;
            }
        }
        return flag;
    }

    /**
     * <p>
     * 头费用对象保存
     * <p/>
     *
     * @param request 请求
     * @param header  报销单头信息
     * @return 保存后的头费用对象list
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/6 10:08
     */
    private List<ExpReportObject> saveHeaderObject(IRequest request, ExpReportHeader header) throws RuntimeException {
        List<ExpReportObject> reportObjects = new ArrayList<>();
        List<HashMap<String, Object>> objectList = (List<HashMap<String, Object>>) header.getInnerMap().get("expenseObjectLines");
        if (objectList != null && !objectList.isEmpty()) {
            for (HashMap<String, Object> map : objectList) {
                ExpReportObject reportObject = new ExpReportObject();
                reportObject.setExpReportHeaderId(header.getExpReportHeaderId());
                reportObject.setMoExpObjTypeId(Long.valueOf((String) map.get("moExpObjTypeId")));

                // 查询头费用对象是否存在
                int oldObjectCount = expReportObjectMapper.selectCount(reportObject);

                reportObject.setMoExpenseObjectId(Long.valueOf((Integer) map.get("moExpenseObjectId")));
                reportObject.setMoExpenseObjectName((String) map.get("moExpenseObjectName"));
                // 存在更新
                if (oldObjectCount != 0) {
                    reportObject.setLastUpdatedBy(request.getUserId());
                    reportObject.setLastUpdateDate(new Date());
                    reportObject = expReportObjectService.updateExpReportObject(reportObject);

                } else {
                    reportObject.setCreatedBy(request.getUserId());
                    reportObject.setCreationDate(new Date());
                    reportObject.setLastUpdatedBy(request.getUserId());
                    reportObject.setLastUpdateDate(new Date());
                    reportObject = expReportObjectService.insertSelective(request, reportObject);
                }
                reportObjects.add(reportObject);
            }
        }

        return reportObjects;
    }

    /**
     * <p>
     * 标准行保存(包括费用对象保存)
     * <p/>
     *
     * @param request           请求
     * @param header            报销单头信息
     * @param reportType        报销单类型
     * @param expReportLineList
     * @return 保存后的行list
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/4 20:17
     */
    private List<ExpReportLine> saveStandardLines(IRequest request, ExpReportHeader header, ExpMoReportType reportType, List<ExpReportLine> expReportLineList) throws RuntimeException {
        for (ExpReportLine line : expReportLineList) {
            // 设置行维度
            setDimFromHeaderToLine(header, line);
            if (DTOStatus.ADD.equals(line.get__status())) {
                lineService.insertStandardLine(request, line, header, reportType);
                // 报销单行与申请单行关联创建(未)

                // 报销单行与费用池关联创建(未)

                // 报销单行和增值税发票行关联
                List<VatInvoice> invoiceList = line.getInvoiceLines();
                if(invoiceList != null && !invoiceList.isEmpty()){
                    invoiceList.forEach(invoice ->
                    {
                        vatInvoiceRelationLnService.saveInvoiceRelationLns(request,invoice,
                                header.getExpReportHeaderId(),line.getExpReportLineId());
                    });
                }
            }
            if (DTOStatus.UPDATE.equals(line.get__status())) {
                lineService.updateStandardLine(request, line, header, reportType);
                // 报销单行和增值税发票行关联(未)
            }
            // 行费用对象信息保存
            lineService.saveLineObject(request, line);
            // 保存单据行和通用单据图片间的关系(未)
        }
        return expReportLineList;
    }

    /**
     * <p>差旅行保存</p>
     *
     * @param request
     * @param header         报销单头
     * @param reportType     报销单类型
     * @param travelLineList
     * @return travelLineList
     * @author yang.duan 2019/5/22 15:40
     **/
    private List<ExpReportTravelLine> saveTravelLines(IRequest request, ExpReportHeader header, ExpMoReportType reportType, List<ExpReportTravelLine> travelLineList) throws RuntimeException {
        if (travelLineList != null && !travelLineList.isEmpty()) {
            for (ExpReportTravelLine travelLine : travelLineList) {
                ExpReportLine line = travelLine.getStandrdLine();
                List<ExpReportLine> lineList = new ArrayList<>();
                if (DTOStatus.ADD.equals(travelLine.get__status())) {
                    //日常费用行保存
                    line.set__status(DTOStatus.ADD);
                    lineList.add(line);
                    lineList = this.saveStandardLines(request, header, reportType, lineList);
                    line = lineList.get(0);
                    travelLine = travelLineService.insertTravelLine(request, header, line, reportType, travelLine);
                }
                if (DTOStatus.UPDATE.equals(travelLine.get__status())) {
                    line.set__status(DTOStatus.UPDATE);
                    lineList.add(line);
                    lineList = this.saveStandardLines(request, header, reportType, lineList);
                    line = lineList.get(0);
                    travelLine = travelLineService.updateTravelLine(request, header, line, reportType, travelLine);
                }
            }
        }
        return travelLineList;
    }

    /**
     * <p>
     * 计划付款行保存
     * <p/>
     *
     * @param request
     * @param header     报销单头信息
     * @param reportType 报销单类型
     * @return 保存后的计划付款行list
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/14 11:44
     */
    private List<ExpReportPmtSchedule> savePmtSchedules(IRequest request, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException {
        List<ExpReportPmtSchedule> pmtScheduleList = new ArrayList<>();
        for (ExpReportPmtSchedule pmtSchedule : header.getPmtSchedules()) {
            if (DTOStatus.ADD.equals(pmtSchedule.get__status())) {
                pmtSchedule.setExpReportHeaderId(header.getExpReportHeaderId());
                pmtScheduleService.insertExpRepPmtSchedule(request, pmtSchedule, header, reportType);
            }
            if (DTOStatus.UPDATE.equals(pmtSchedule.get__status())) {
                pmtScheduleService.updateExpRepPmtSchedule(request, pmtSchedule, header, reportType);
            }
            pmtScheduleList.add(pmtSchedule);
        }
        return pmtScheduleList;
    }

    /**
     * <p>
     * 根据头维度设置行维度
     * <p/>
     *
     * @param header 报销单头信息
     * @param line   报销单行信息
     * @author yang.duan 2019/3/11 17:02
     */
    public ExpReportLine setDimFromHeaderToLine(ExpReportHeader header, ExpReportLine line) {
        line.setDimension1Id(header.getDimension1Id() != null ? header.getDimension1Id() : line.getDimension1Id());
        line.setDimension2Id(header.getDimension2Id() != null ? header.getDimension2Id() : line.getDimension2Id());
        line.setDimension3Id(header.getDimension3Id() != null ? header.getDimension3Id() : line.getDimension3Id());
        line.setDimension4Id(header.getDimension4Id() != null ? header.getDimension4Id() : line.getDimension4Id());
        line.setDimension5Id(header.getDimension5Id() != null ? header.getDimension5Id() : line.getDimension5Id());
        line.setDimension6Id(header.getDimension6Id() != null ? header.getDimension6Id() : line.getDimension6Id());
        line.setDimension7Id(header.getDimension7Id() != null ? header.getDimension7Id() : line.getDimension7Id());
        line.setDimension8Id(header.getDimension8Id() != null ? header.getDimension8Id() : line.getDimension8Id());
        line.setDimension9Id(header.getDimension9Id() != null ? header.getDimension9Id() : line.getDimension9Id());
        line.setDimension10Id(header.getDimension10Id() != null ? header.getDimension10Id() : line.getDimension10Id());
        line.setDimension11Id(header.getDimension11Id() != null ? header.getDimension11Id() : line.getDimension11Id());
        line.setDimension12Id(header.getDimension12Id() != null ? header.getDimension12Id() : line.getDimension12Id());
        line.setDimension13Id(header.getDimension13Id() != null ? header.getDimension13Id() : line.getDimension13Id());
        line.setDimension14Id(header.getDimension14Id() != null ? header.getDimension14Id() : line.getDimension14Id());
        line.setDimension15Id(header.getDimension15Id() != null ? header.getDimension15Id() : line.getDimension15Id());
        line.setDimension16Id(header.getDimension16Id() != null ? header.getDimension16Id() : line.getDimension16Id());
        line.setDimension17Id(header.getDimension17Id() != null ? header.getDimension17Id() : line.getDimension17Id());
        line.setDimension18Id(header.getDimension18Id() != null ? header.getDimension18Id() : line.getDimension18Id());
        line.setDimension19Id(header.getDimension19Id() != null ? header.getDimension19Id() : line.getDimension19Id());
        line.setDimension20Id(header.getDimension20Id() != null ? header.getDimension20Id() : line.getDimension20Id());
        return line;
    }

    /**
     * <p>
     * 报销单整单删除
     * <p/>
     *
     * @param request
     * @param header
     * @return
     * @author yang.duan 2019/3/29 10:30
     */
    @Override
    public int deleteHeader(IRequest request, ExpReportHeader header) {
        // 查询报销单头数据
        header = this.selectByPrimaryKey(request, header);
        int deleteflag = -1;
        if (header != null) {
            if (statusCheck(request, header.getExpReportHeaderId())) {
                // 删除对应工作流(未)
                // 删除核销记录
                ExpReportPmtSchedule pmtSchedule = new ExpReportPmtSchedule();
                pmtSchedule.setExpReportHeaderId(header.getExpReportHeaderId());
                expReportPmtScheduleService.deleteExpRepPmtSchedule(request, pmtSchedule);

                // 删除行
                ExpReportLine line = new ExpReportLine();
                line.setExpReportHeaderId(header.getExpReportHeaderId());
                expReportLineService.deleteExpReportLine(request, line);

                // 删除报销单凭证
                ExpReportAccount expReportAccount = new ExpReportAccount();
                expReportAccount.setExpReportHeaderId(header.getExpReportHeaderId());
                List<ExpReportAccount> accountList = accountService.select(request, expReportAccount, 1, 0);
                accountService.batchDelete(accountList);

                // 删除费用对象
                expReportObjectService.deleteExpObject(header.getExpReportHeaderId(), null);
                // 删除报销单头
                deleteflag = self().deleteByPrimaryKey(header);
                // 删除计划付款行
                ExpReportPmtSchedule schedule = new ExpReportPmtSchedule();
                schedule.setExpReportHeaderId(header.getExpReportHeaderId());
                List<ExpReportPmtSchedule> scheduleList = expReportPmtScheduleService.select(request, schedule, 1, 0);
                expReportPmtScheduleService.batchDelete(scheduleList);
                // 删除预算保留行
                BgtBudgetReserve reserve = new BgtBudgetReserve();
                reserve.setDocumentId(header.getExpReportHeaderId());
                List<BgtBudgetReserve> reserveList = bgtBudgetReserveService.select(request, reserve, 1, 0);
                bgtBudgetReserveService.batchDelete(reserveList);


                // 触发删除事件
                // --删除事件
                // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
                // p_source_module => c_exp_report,
                // p_event_type => 'EXP_EXPENSE_POLICY',
                // p_user_id => p_user_id);


                // 删除支付核算主体
                cshDocPayAccEntityService.deletePayAccEntity(request, ExpReportHeader.EXP_REPORT, header.getExpReportHeaderId(), null);
                // 删除附件关联记录
                FndAtmAttachmentRefRecord atmAttachmentRefRecord = new FndAtmAttachmentRefRecord();
                atmAttachmentRefRecord.setTableName("EXP_REPORT_HEADER");
                atmAttachmentRefRecord.setTablePkValue(header.getExpReportHeaderId());
                List<FndAtmAttachmentRefRecord> attachmentRefRecordList = fndAtmAttachmentRefRecordService.select(request, atmAttachmentRefRecord, 1, 0);
                if (attachmentRefRecordList != null && !attachmentRefRecordList.isEmpty()) {
                    fndAtmAttachmentRefRecordService.batchDelete(attachmentRefRecordList);
                }

                //删除附件
                FndAtmAttachment fndAtmAttachment = new FndAtmAttachment();
                fndAtmAttachment.setTableName("EXP_REPORT_HEADER");
                fndAtmAttachment.setTablePkValue(header.getExpReportHeaderId());
                List<FndAtmAttachment> atmAttachmentList = fndAtmAttachmentService.select(request, fndAtmAttachment, 1, 0);
                if (atmAttachmentList != null && !atmAttachmentList.isEmpty()) {
                    fndAtmAttachmentService.batchDelete(atmAttachmentList);
                }
            }
        }
        return deleteflag;
    }

    @Override
    public void createAccount(IRequest iRequest, List<ExpReportHeader> expReportHeaders, Date journalDate) {
        if (expReportHeaders != null) {
            if (!expReportHeaders.isEmpty()) {
                for (ExpReportHeader expReportHeader : expReportHeaders) {
                    String periodName = gldPeriodMapper.getPeriodName(journalDate, expReportHeader.getAccEntityId(), "O");
                    if (periodName == null || "".equals(periodName)) {
                        throw new RuntimeException("所选凭证日期不在打开的期间内,请联系系统管理员!");
                    }
                    accountService.createExpReportAccounts(iRequest, expReportHeader.getExpReportHeaderId(), journalDate, periodName, expReportHeader.getAmortizationFlag());
                }
            }
        }
    }

    /**
     * 借贷平衡校验
     *
     * @param
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 10:54
     * @Version 1.0
     **/
    public boolean expReportBalanceCheck(Long expReportHeaderId) {
        ExpReportAccount expReportAccount = new ExpReportAccount();
        expReportAccount = expReportAccountMapper.getDrCrAmountDiff(expReportHeaderId);
        if ((new BigDecimal(0)).compareTo(expReportAccount.getEnteredAmount()) == 0 && (new BigDecimal(0)).compareTo(expReportAccount.getEnteredAmount()) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取汇率差异科目Id
     *
     * @param
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 19:39
     * @Version 1.0
     **/
    Long getRevalutionAccountId(String currencyCode, Long magOrgId, Long setOfBookId) {
        Long accountId = 0L;
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        gldMappingConditions = gldMappingConditionService.accBuildCshRevaluation(currencyCode);
        accountId = gldMappingConditionService.getAccount(gldMappingConditions, "REVALUATION", magOrgId, setOfBookId);
        return accountId;
    }

    /**
     * 更新报销单对应的核销数据
     *
     * @param expReportHeader 报销单头
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 15:58
     * @Version 1.0
     **/
    public void updateCshWriteOff(IRequest iRequest, ExpReportHeader expReportHeader, Date journalDate, Timestamp journalDateTime, String periodName) {
        List<CshWriteOff> cshWriteOffs = new ArrayList<>();
        cshWriteOffs = cshWriteOffMapper.getCshWriteOffInfo(expReportHeader.getExpReportHeaderId());
        if (cshWriteOffs != null) {
            if (!cshWriteOffs.isEmpty()) {
                for (CshWriteOff cshWriteOff : cshWriteOffs) {
                    cshWriteOff.setWriteOffDate(journalDate);
                    cshWriteOff.setPeriodName(periodName);
                    cshWriteOff.setLastUpdateDate(new Date());
                    cshWriteOff.setLastUpdatedBy(iRequest.getUserId());
                    cshWriteOffService.updateByPrimaryKey(iRequest, cshWriteOff);
                    CshTransactionHeader cshTransactionHeader = new CshTransactionHeader();
                    cshTransactionHeader.setTransactionHeaderId(cshWriteOff.getTransactionHeaderId());
                    cshTransactionHeader.setWriteOffFlag("C");
                    cshTransactionHeader = cshTransactionHeaderService.selectByPrimaryKey(iRequest, cshTransactionHeader);
                    if (cshTransactionHeader != null) {
                        cshTransactionHeader.setWriteOffCompletedDate(journalDate);
                        cshTransactionHeader.setWriteOffCompletedDateTz(journalDateTime);
                        cshTransactionHeader.setWriteOffCompletedDateLtz(journalDateTime);
                        cshTransactionHeaderService.updateByPrimaryKeySelective(iRequest, cshTransactionHeader);
                    }
                    CshWriteOffAccount cshWriteOffAccount = new CshWriteOffAccount();
                    cshWriteOffAccount.setWriteOffId(cshWriteOff.getWriteOffId());
                    List<CshWriteOffAccount> cshWriteOffAccounts = cshWriteOffAccountService.select(iRequest, cshWriteOffAccount, 1, 0);
                    String functionalCurrencyCode = "CNY";
                    BigDecimal exchangeRate = new BigDecimal(0);
                    String exchangeRateType = "";
                    BigDecimal revaluation = new BigDecimal(0);
                    String crDrFlag = "";
                    if (!cshWriteOffAccounts.isEmpty()) {
                        for (CshWriteOffAccount cshWriteOffAccount1 : cshWriteOffAccounts) {
                            BigDecimal functionAmount = new BigDecimal(0);
                            if (functionalCurrencyCode.equals(cshWriteOffAccount1.getCurrencyCode())) {
                                ExpReportAccount expReportAccount = new ExpReportAccount();
                                expReportAccount.setExpReportHeaderId(expReportHeader.getExpReportHeaderId());
                                List<ExpReportAccount> expReportAccounts = expReportAccountService.select(iRequest, expReportAccount, 1, 0);
                                if (expReportAccounts == null) {
                                    exchangeRate = new BigDecimal(1);
                                } else {
                                    if (expReportAccounts.isEmpty()) {
                                        exchangeRate = new BigDecimal(1);
                                    } else {
                                        exchangeRate = expReportAccounts.get(0).getExchangeRate();
                                        exchangeRateType = expReportAccounts.get(0).getExchangeRateType();
                                    }
                                }
                            }
                            // 获取本位币精度
                            int precision = gldCurrencyMapper.getPrecision(functionalCurrencyCode);
                            if ("PREPAYMENT_WRITE_OFF".equals(cshWriteOffAccount1.getUsageCode())) {
                                functionAmount = functionAmount.add(cshWriteOffAccount1.getEnteredAmountCr().multiply(exchangeRate == null ? BigDecimal.ZERO : exchangeRate).setScale(precision, BigDecimal.ROUND_HALF_UP));
                                if ((new BigDecimal(0)).compareTo(revaluation) == 0) {
                                    revaluation = revaluation.add(functionAmount == null ? BigDecimal.ZERO : functionAmount);
                                } else {
                                    revaluation = revaluation.subtract(functionAmount == null ? BigDecimal.ZERO : functionAmount);
                                }
                                crDrFlag = "Cr";
                            } else if ("EMPLOYEE_EXPENSE_WRITE_OFF".equals(cshWriteOffAccount1.getUsageCode())) {
                                functionAmount = functionAmount.add(cshWriteOffAccount1.getEnteredAmountDr().multiply(exchangeRate == null ? BigDecimal.ZERO : exchangeRate).setScale(precision, BigDecimal.ROUND_HALF_UP));
                                if ((new BigDecimal(0)).compareTo(revaluation) == 0) {
                                    revaluation = revaluation.add(functionAmount == null ? BigDecimal.ZERO : functionAmount);
                                } else {
                                    revaluation = revaluation.subtract(functionAmount == null ? BigDecimal.ZERO : functionAmount);
                                }
                                crDrFlag = "Dr";
                            }
                            cshWriteOffAccount1.setExchangeRate(exchangeRate);
                            cshWriteOffAccount1.setExchangeRateType(exchangeRateType);
                            cshWriteOffAccount1.setJournalDate(journalDate);
                            cshWriteOffAccount1.setPeriodName(periodName);
                            cshWriteOffAccount1.setGldInterfaceFlag("P");
                            cshWriteOffAccountService.updateByPrimaryKeySelective(iRequest, cshWriteOffAccount1);
                        }
                    }
                    // 创建汇率差异凭证
                    if ("Cr".equals(crDrFlag)) {
                        if ((new BigDecimal(0)).compareTo(revaluation) < 0) {
                            Long accountId = 0L;
                            try {
                                accountId = getRevalutionAccountId(cshWriteOffAccounts.get(0).getCurrencyCode(), expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                                if (accountId.longValue() == 0) {
                                    throw new ExpReportAccountException(ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, null);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                            CshWriteOffAccount cshWriteOffAccount1 = new CshWriteOffAccount();
                            cshWriteOffAccount1.setAccEntityId(cshWriteOffAccounts.get(0).getAccEntityId());
                            cshWriteOffAccount1.setCompanyId(cshWriteOffAccounts.get(0).getCompanyId());
                            cshWriteOffAccount1.setCurrencyCode(cshWriteOffAccounts.get(0).getCurrencyCode());
                            cshWriteOffAccount1.setRespCenterId(cshWriteOffAccounts.get(0).getRespCenterId());
                            cshWriteOffAccount1.setSourceCode("PREPAYMENT_EXPENSE_REPORT");
                            cshWriteOffAccount1.setAccountId(accountId);
                            cshWriteOffAccount1.setCashClearingFlag("N");
                            cshWriteOffAccount1.setWriteOffId(cshWriteOffAccounts.get(0).getWriteOffId());
                            cshWriteOffAccount1.setEnteredAmountCr(new BigDecimal(0));
                            cshWriteOffAccount1.setFunctionalAmountCr(revaluation);
                            cshWriteOffAccount1.setExchangeRate(exchangeRate);
                            cshWriteOffAccount1.setExchangeRateType(exchangeRateType);
                            cshWriteOffAccount1.setJournalDate(journalDate);
                            cshWriteOffAccount1.setPeriodName(periodName);
                            cshWriteOffAccount1.setGldInterfaceFlag("P");
                            cshWriteOffAccountService.insertSelective(iRequest, cshWriteOffAccount1);
                        } else if ((new BigDecimal(0)).compareTo(revaluation) > 0) {
                            Long accountId = 0L;
                            try {
                                accountId = getRevalutionAccountId(cshWriteOffAccounts.get(0).getCurrencyCode(), expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                                if (accountId.longValue() == 0) {
                                    throw new ExpReportAccountException(ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, null);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                            CshWriteOffAccount cshWriteOffAccount1 = new CshWriteOffAccount();
                            cshWriteOffAccount1.setAccEntityId(cshWriteOffAccounts.get(0).getAccEntityId());
                            cshWriteOffAccount1.setCompanyId(cshWriteOffAccounts.get(0).getCompanyId());
                            cshWriteOffAccount1.setCurrencyCode(cshWriteOffAccounts.get(0).getCurrencyCode());
                            cshWriteOffAccount1.setRespCenterId(cshWriteOffAccounts.get(0).getRespCenterId());
                            cshWriteOffAccount1.setSourceCode("PREPAYMENT_EXPENSE_REPORT");
                            cshWriteOffAccount1.setAccountId(accountId);
                            cshWriteOffAccount1.setCashClearingFlag("N");
                            cshWriteOffAccount1.setWriteOffId(cshWriteOffAccounts.get(0).getWriteOffId());
                            cshWriteOffAccount1.setEnteredAmountDr(new BigDecimal(0));
                            cshWriteOffAccount1.setFunctionalAmountDr(revaluation);
                            cshWriteOffAccount1.setExchangeRate(exchangeRate);
                            cshWriteOffAccount1.setExchangeRateType(exchangeRateType);
                            cshWriteOffAccount1.setJournalDate(journalDate);
                            cshWriteOffAccount1.setPeriodName(periodName);
                            cshWriteOffAccount1.setGldInterfaceFlag("P");
                            cshWriteOffAccountService.insertSelective(iRequest, cshWriteOffAccount1);
                        }
                    } else if ("Dr".equals(crDrFlag)) {
                        if ((new BigDecimal(0)).compareTo(revaluation) < 0) {
                            Long accountId = 0L;
                            try {
                                accountId = getRevalutionAccountId(cshWriteOffAccounts.get(0).getCurrencyCode(), expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                                if (accountId.longValue() == 0) {
                                    throw new ExpReportAccountException(ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, null);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                            CshWriteOffAccount cshWriteOffAccount1 = new CshWriteOffAccount();
                            cshWriteOffAccount1.setAccEntityId(cshWriteOffAccounts.get(0).getAccEntityId());
                            cshWriteOffAccount1.setCompanyId(cshWriteOffAccounts.get(0).getCompanyId());
                            cshWriteOffAccount1.setCurrencyCode(cshWriteOffAccounts.get(0).getCurrencyCode());
                            cshWriteOffAccount1.setRespCenterId(cshWriteOffAccounts.get(0).getRespCenterId());
                            cshWriteOffAccount1.setSourceCode("PREPAYMENT_EXPENSE_REPORT");
                            cshWriteOffAccount1.setAccountId(accountId);
                            cshWriteOffAccount1.setCashClearingFlag("N");
                            cshWriteOffAccount1.setWriteOffId(cshWriteOffAccounts.get(0).getWriteOffId());
                            cshWriteOffAccount1.setEnteredAmountDr(new BigDecimal(0));
                            cshWriteOffAccount1.setFunctionalAmountDr(revaluation);
                            cshWriteOffAccount1.setExchangeRate(exchangeRate);
                            cshWriteOffAccount1.setExchangeRateType(exchangeRateType);
                            cshWriteOffAccount1.setJournalDate(journalDate);
                            cshWriteOffAccount1.setPeriodName(periodName);
                            cshWriteOffAccount1.setGldInterfaceFlag("P");
                            cshWriteOffAccountService.insertSelective(iRequest, cshWriteOffAccount1);
                        } else if ((new BigDecimal(0)).compareTo(revaluation) > 0) {
                            Long accountId = 0L;
                            try {
                                accountId = getRevalutionAccountId(cshWriteOffAccounts.get(0).getCurrencyCode(), expReportHeader.getMagOrgId(), expReportHeader.getSetOfBooksId());
                                if (accountId.longValue() == 0) {
                                    throw new ExpReportAccountException(ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, ExpReportAccountException.EXP5140_RESP_CENTER_ERROR, null);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                            CshWriteOffAccount cshWriteOffAccount1 = new CshWriteOffAccount();
                            cshWriteOffAccount1.setAccEntityId(cshWriteOffAccounts.get(0).getAccEntityId());
                            cshWriteOffAccount1.setCompanyId(cshWriteOffAccounts.get(0).getCompanyId());
                            cshWriteOffAccount1.setCurrencyCode(cshWriteOffAccounts.get(0).getCurrencyCode());
                            cshWriteOffAccount1.setRespCenterId(cshWriteOffAccounts.get(0).getRespCenterId());
                            cshWriteOffAccount1.setSourceCode("PREPAYMENT_EXPENSE_REPORT");
                            cshWriteOffAccount1.setAccountId(accountId);
                            cshWriteOffAccount1.setCashClearingFlag("N");
                            cshWriteOffAccount1.setWriteOffId(cshWriteOffAccounts.get(0).getWriteOffId());
                            cshWriteOffAccount1.setEnteredAmountCr(new BigDecimal(0));
                            cshWriteOffAccount1.setFunctionalAmountCr(revaluation);
                            cshWriteOffAccount1.setExchangeRate(exchangeRate);
                            cshWriteOffAccount1.setExchangeRateType(exchangeRateType);
                            cshWriteOffAccount1.setJournalDate(journalDate);
                            cshWriteOffAccount1.setPeriodName(periodName);
                            cshWriteOffAccount1.setGldInterfaceFlag("P");
                            cshWriteOffAccountService.insertSelective(iRequest, cshWriteOffAccount1);
                        }
                    }
                    //核销凭证录入分录表
                    glAccountEntryService.headerGlAccountEntry(iRequest, GlAccountEntry.RULE_TYPE_CSH_WRITE_OFF, cshWriteOff.getWriteOffId());
                }
            }
        }
    }

    @Override
    public void audit(IRequest iRequest, List<ExpReportHeader> expReportHeaders) {
        if (!expReportHeaders.isEmpty()) {
            // 复核标识
            String recheckFlag = sysParameterService.queryParamValueByCode("EXP_REPORT_RECHECK", null, null, null, null, null, null, null);
            // 营改增模块,是否先认证再传凭证,支付
            String authFirstFlag = sysParameterService.queryParamValueByCode("VAT_CERT_SEQUENCE", null, null, null, null, null, null, null);
            try {
                for (ExpReportHeader expReportHeader : expReportHeaders) {
                    if ("SUCCESS".equals(expReportHeader.getJeCreationStatus())) {
                        // 获取凭证的创建日期
                        Date journalDate = expReportAccountMapper.getJournalDate(expReportHeader.getExpReportHeaderId());
                        Timestamp journalDateTime = new Timestamp(journalDate.getTime());
                        String periodName = gldPeriodMapper.getPeriodName(journalDate, expReportHeader.getAccEntityId(), "O");
                        if (periodName == null || "".equals(periodName)) {
                            throw new ExpReportAccountException(ExpReportAccountException.EXP5140_PERIOD_NOT_OPEN, ExpReportAccountException.EXP5140_PERIOD_NOT_OPEN, null);
                        } else {
                            // 借贷平衡校验
                            boolean checkFlag = expReportBalanceCheck(expReportHeader.getExpReportHeaderId());
                            if (checkFlag) {
                                // 插入单据历史
                                expDocumentHistoryService.insertDocumentHistory(iRequest, ExpReportHeader.EXP_REPORT, expReportHeader.getExpReportHeaderId(), ExpDocumentHistory.STATUS_AUDIT, iRequest.getEmployeeId(), "");
                                if ("N".equals(recheckFlag)) {
                                    // 更新报销单头表
                                    expReportHeader.setAuditFlag("Y");
                                    expReportHeader.setAuditDate(journalDate);
                                    expReportHeader.setAuditDateTz(journalDateTime);
                                    expReportHeader.setAuditDateLtz(journalDateTime);
                                    if ("C".equals(expReportHeader.getWriteOffStatus())) {
                                        expReportHeader.setWriteOffCompletedDate(journalDate);
                                        expReportHeader.setWriteOffCompletedDateTz(journalDateTime);
                                        expReportHeader.setWriteOffCompletedDateLtz(journalDateTime);
                                    }
                                    expReportHeader.setLastUpdateDate(new Date());
                                    expReportHeader.setLastUpdatedBy(iRequest.getUserId());
                                    service.updateByPrimaryKeySelective(iRequest, expReportHeader);
                                    // 批量更新报销单行
                                    expReportLineMapper.batchUpdate(expReportHeader.getExpReportHeaderId(), journalDate, journalDateTime, "Y");
                                    // 批量更新报销单分配行
                                    expReportDistMapper.batchUpdate(expReportHeader.getExpReportHeaderId(), journalDate, journalDateTime, "Y");
                                    // 批量更新报销单凭证行
                                    expReportAccountMapper.batchUpdate(expReportHeader.getExpReportHeaderId());
                                    // 传送报销单凭证信息进分录表
                                    glAccountEntryService.headerGlAccountEntry(iRequest, GlAccountEntry.RULE_TYPE_EXP_REPORT, expReportHeader.getExpReportHeaderId());
                                } else if ("Y".equals(recheckFlag)) {
                                    // 更新报销单头表
                                    expReportHeader.setAuditFlag("R");
                                    expReportHeader.setAuditDate(journalDate);
                                    expReportHeader.setAuditDateTz(journalDateTime);
                                    expReportHeader.setAuditDateLtz(journalDateTime);
                                    if ("C".equals(expReportHeader.getWriteOffStatus())) {
                                        expReportHeader.setWriteOffCompletedDate(journalDate);
                                        expReportHeader.setWriteOffCompletedDateTz(journalDateTime);
                                        expReportHeader.setWriteOffCompletedDateLtz(journalDateTime);
                                    }
                                    expReportHeader.setLastUpdateDate(new Date());
                                    expReportHeader.setLastUpdatedBy(iRequest.getUserId());
                                    service.updateByPrimaryKeySelective(iRequest, expReportHeader);
                                    // 批量更新报销单行
                                    expReportLineMapper.batchUpdate(expReportHeader.getExpReportHeaderId(), journalDate, journalDateTime, "R");
                                    // 批量更新报销单分配行
                                    expReportDistMapper.batchUpdate(expReportHeader.getExpReportHeaderId(), journalDate, journalDateTime, "R");
                                }
                            } else {
                                throw new ExpReportAccountException(ExpReportAccountException.EXP_REP_ACCOUNT_AMOUNT_BALANCE_ERROR, ExpReportAccountException.EXP_REP_ACCOUNT_AMOUNT_BALANCE_ERROR, null);
                            }
                        }
                        // 如果存在核销数据则更新报销单核销数据
                        updateCshWriteOff(iRequest, expReportHeader, journalDate, journalDateTime, periodName);
                    } else {
                        throw new ExpReportAccountException(ExpReportAccountException.EXP5140_JE_CREATION_FIRST, ExpReportAccountException.EXP5140_JE_CREATION_FIRST, null);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public void auditReject(IRequest iRequest, List<ExpReportHeader> expReportHeaders) {
        if (!expReportHeaders.isEmpty()) {
            for (ExpReportHeader expReportHeader : expReportHeaders) {
                // 更新报销单头状态
                expReportHeader.setReportStatus(ExpReportHeader.REJECTED);
                expReportHeader.setJeCreationStatus("N");
                service.updateByPrimaryKeySelective(iRequest, expReportHeader);
                // 更新报销单行
                expReportLineMapper.auditRejectBatchUpdate(expReportHeader.getExpReportHeaderId(), ExpReportHeader.REJECTED, "N");
                // 更新报销单分配行
                expReportDistMapper.auditRejectBatchUpdate(expReportHeader.getExpReportHeaderId(), ExpReportHeader.REJECTED, "N");
                // 批量删除报销单凭证
                List<ExpReportAccount> expReportAccounts = new ArrayList<>();
                expReportAccounts = expReportAccountMapper.getExpReportAccountInfo(expReportHeader.getExpReportHeaderId());
                if (!expReportAccounts.isEmpty()) {
                    expReportAccountService.batchDelete(expReportAccounts);
                }
                // 更新预算状态
                // 打开申请单
                String description = expReportHeader.getInnerMap().get("rejectReason").toString();
                // 创建单据历史
                expDocumentHistoryService.insertDocumentHistory(iRequest, ExpReportHeader.EXP_REPORT, expReportHeader.getExpReportHeaderId(), ExpDocumentHistory.STATUS_AUDIT_REJECT, iRequest.getEmployeeId(), description);
            }
        }
    }

    @Override
    public List<ExpReportHeader> auditQuery(IRequest iRequest, ExpReportHeader expReportHeader, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ExpReportHeader> expReportHeaders = new ArrayList<>();
        expReportHeaders = mapper.auditQuery(expReportHeader);
        return expReportHeaders;
    }

    @Override
    public List<ExpReportHeader> reverseQuery(IRequest iRequest, ExpReportHeader expReportHeader, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ExpReportHeader> expReportHeaders = new ArrayList<>();
        expReportHeaders = mapper.reverseQuery(expReportHeader);
        return expReportHeaders;
    }

    @Override
    public void updateAccount(IRequest iRequest, List<HashMap<String, Object>> list) {
        if (!list.isEmpty()) {
            for (HashMap<String, Object> map : list) {
                Integer expReportHeaderId = (Integer) map.get("expReportHeaderId");
                BigDecimal exchangeRate = new BigDecimal((Double) map.get("exchangeRate"));
                String functionCurrencyCode = (String) map.get("functionalCurrencyCode");
                expReportAccountService.updateAccountExchangeRate(iRequest, expReportHeaderId.longValue(), exchangeRate, functionCurrencyCode);
            }
        }
    }

    /**
     * 反冲报销单头
     *
     * @param iRequest        请求上下文
     * @param expReportHeader 需反冲的报销单头数据
     * @param journalDate     反冲日期
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/13 16:45
     * @Version 1.0
     **/
    public Long reverseExpHeader(IRequest iRequest, ExpReportHeader expReportHeader, Date journalDate) {
        expReportHeader.setReversedFlag("W");
        service.updateByPrimaryKeySelective(iRequest, expReportHeader);
        // 插报销单历史
        expDocumentHistoryService.insertDocumentHistory(iRequest, ExpReportHeader.EXP_REPORT, expReportHeader.getExpReportHeaderId(), ExpDocumentHistory.STATUS_REVERSE, iRequest.getEmployeeId(), "");
        ExpReportHeader expReportHeader1 = new ExpReportHeader();
        BeanUtils.copyProperties(expReportHeader, expReportHeader1);
        expReportHeader1.setReportDate(journalDate);
        expReportHeader1.setReportDateTz(journalDate);
        expReportHeader1.setReportDateLtz(journalDate);
        expReportHeader1.setAuditDate(new Date());
        expReportHeader1.setAuditDateTz(new Date());
        expReportHeader1.setAuditDateLtz(new Date());
        expReportHeader1.setGldInterfaceFlag("P");
        expReportHeader1.setReversedFlag("R");
        FndManagingOrganization fndManagingOrganization = new FndManagingOrganization();
        fndManagingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(iRequest, expReportHeader1.getCompanyId());
        String expReportNumber = codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_EXP_REPORT, expReportHeader1.getMoExpReportTypeId().toString(), fndManagingOrganization.getMagOrgId(), expReportHeader1.getCompanyId(), null);
        expReportHeader1.setExpReportNumber(expReportNumber);
        expReportHeader1.setSourceExpRepHeaderId(expReportHeader.getExpReportHeaderId());
        expReportHeader1.set__status("add");
        expReportHeader1 = service.insertSelective(iRequest, expReportHeader1);
        // 插入报销单历史
        expDocumentHistoryService.insertDocumentHistory(iRequest, ExpReportHeader.EXP_REPORT, expReportHeader1.getExpReportHeaderId(), ExpDocumentHistory.STATUS_GENERATE, iRequest.getEmployeeId(), "");
        expDocumentHistoryService.insertDocumentHistory(iRequest, ExpReportHeader.EXP_REPORT, expReportHeader1.getExpReportHeaderId(), ExpDocumentHistory.STATUS_AUDIT, iRequest.getEmployeeId(), "");
        return expReportHeader1.getExpReportHeaderId();
    }

    /**
     * 反冲报销单预算占用数据
     *
     * @param iRequest             请求上下文
     * @param bgtBudgetReserves    需反冲的预算占用数据
     * @param periodName           期间
     * @param newExpReportDistId   新生成的报销单分配行Id
     * @param newExpReportHeaderId 新生成的报销单头Id
     * @param companyId            公司Id
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/13 16:45
     * @Version 1.0
     **/
    public void reverseBgtBudgetReserve(IRequest iRequest, List<BgtBudgetReserve> bgtBudgetReserves, String periodName, Long newExpReportDistId, Long newExpReportHeaderId, Long companyId) {
        if (!bgtBudgetReserves.isEmpty()) {
            String periodFlag = sysParameterService.queryParamValueByCode("BGT_REVERSE_ORIGINAL_PERIOD", null, null, companyId, null, null, null, null);
            for (BgtBudgetReserve bgtBudgetReserve : bgtBudgetReserves) {
                BgtBudgetReserve bgtBudgetReserve1 = new BgtBudgetReserve();
                BeanUtils.copyProperties(bgtBudgetReserve, bgtBudgetReserve1);
                if (!"Y".equals(periodFlag)) {
                    bgtBudgetReserve1.setPeriodName(periodName);
                }
                if (bgtBudgetReserve.getQuantity() != null) {
                    bgtBudgetReserve1.setQuantity((new BigDecimal(-1)).multiply(bgtBudgetReserve.getQuantity()));
                }
                if (bgtBudgetReserve.getAmount() != null) {
                    bgtBudgetReserve1.setAmount((new BigDecimal(-1)).multiply(bgtBudgetReserve.getAmount()));
                }
                if (bgtBudgetReserve.getFunctionalAmount() != null) {
                    bgtBudgetReserve1.setFunctionalAmount((new BigDecimal(-1)).multiply(bgtBudgetReserve.getFunctionalAmount()));
                }
                bgtBudgetReserve1.setDocumentId(newExpReportHeaderId);
                bgtBudgetReserve1.setDocumentLineId(newExpReportDistId);
                bgtBudgetReserve1.set__status("add");
                bgtBudgetReserveService.insertSelective(iRequest, bgtBudgetReserve1);
            }
        }
    }

    /**
     * 反冲报销单分配行凭证
     *
     * @param iRequest             请求上下文
     * @param expReportAccounts    需反冲的报销单分配行相关凭证
     * @param newExpReportDistId   报销单分配行Id
     * @param newExpReportHeaderId 报销单头Id
     * @param journalDate          反冲日期
     * @param periodName           期间
     * @param accEntityId          核算主体Id
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/14 9:47
     * @Version 1.0
     **/
    public void reverseExpReportAccount(IRequest iRequest, List<ExpReportAccount> expReportAccounts, Long newExpReportDistId, Long newExpReportHeaderId, Date journalDate, String periodName, Long accEntityId) {
        if (!expReportAccounts.isEmpty()) {
            for (ExpReportAccount expReportAccount : expReportAccounts) {
                ExpReportAccount expReportAccount1 = new ExpReportAccount();
                BeanUtils.copyProperties(expReportAccount, expReportAccount1);
                // 比较所选反冲期间与原凭证期间大小
                Long internalPeriodNum = 0L;
                internalPeriodNum = gldPeriodMapper.getInternalPeriodNum(accEntityId, expReportAccount.getPeriodName());
                Long reverseInternalPeriodNum = 0L;
                reverseInternalPeriodNum = gldPeriodMapper.getInternalPeriodNum(accEntityId, periodName);
                if (reverseInternalPeriodNum.longValue() >= internalPeriodNum.longValue()) {
                    expReportAccount1.setPeriodName(periodName);
                    expReportAccount1.setJournalDate(journalDate);
                }
                expReportAccount1.setExpReportDistId(newExpReportDistId);
                expReportAccount1.setExpReportHeaderId(newExpReportHeaderId);
                if (expReportAccount.getEnteredAmountDr() != null) {
                    expReportAccount1.setEnteredAmountDr((new BigDecimal(-1)).multiply(expReportAccount.getEnteredAmountDr()));
                }
                if (expReportAccount.getEnteredAmountCr() != null) {
                    expReportAccount1.setEnteredAmountCr((new BigDecimal(-1)).multiply(expReportAccount.getEnteredAmountCr()));
                }
                if (expReportAccount.getFunctionalAmountDr() != null) {
                    expReportAccount1.setFunctionalAmountDr((new BigDecimal(-1)).multiply(expReportAccount.getFunctionalAmountDr()));
                }
                if (expReportAccount.getFunctionalAmountCr() != null) {
                    expReportAccount1.setFunctionalAmountCr((new BigDecimal(-1)).multiply(expReportAccount.getFunctionalAmountCr()));
                }
                expReportAccount1.setGldInterfaceFlag("P");
                expReportAccount1.set__status("add");
                expReportAccountService.insertSelective(iRequest, expReportAccount1);
            }
        }
    }

    /**
     * 反冲关联的申请单
     *
     * @param iRequest             请求上下文
     * @param oldExpReportHeaderId 原报销单头Id
     * @param newExpReportHeaderId 新生成的报销单头Id
     * @param oldExpReportLineId   原报销单行Id
     * @param newExpReportLineId   新生成的报销单行Id
     * @param oldExpReportDistId   原报销单分配行Id
     * @param newExpReportDistId   新生成的报销分配行Id
     * @param periodName           期间
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/14 11:08
     * @Version 1.0
     **/
    public void reverseExpRequisitionReleases(IRequest iRequest, Long oldExpReportHeaderId, Long newExpReportHeaderId, Long oldExpReportLineId, Long newExpReportLineId, Long oldExpReportDistId, Long newExpReportDistId, String periodName) {
        List<ExpRequisitionRelease> expRequisitionReleases = new ArrayList<>();
        expRequisitionReleases = expRequisitionReleaseService.selectExpRequisitionReleaseInfo(oldExpReportHeaderId, oldExpReportLineId, oldExpReportDistId);
        if (!expRequisitionReleases.isEmpty()) {
            for (ExpRequisitionRelease expRequisitionRelease : expRequisitionReleases) {
                ExpRequisitionRelease expRequisitionRelease1 = new ExpRequisitionRelease();
                expRequisitionRelease1.setDocumentId(newExpReportHeaderId);
                expRequisitionRelease1.setDocumentLineId(newExpReportLineId);
                expRequisitionRelease1.setDocumentDistId(newExpReportDistId);
                if (expRequisitionRelease.getReqReleaseAmount() != null) {
                    expRequisitionRelease1.setReqReleaseAmount((new BigDecimal(-1)).multiply(expRequisitionRelease.getReqReleaseAmount()));
                }
                if (expRequisitionRelease.getReqReleaseQuantity() != null) {
                    expRequisitionRelease1.setReqReleaseQuantity((new BigDecimal(-1)).multiply(expRequisitionRelease.getReqReleaseQuantity()));
                }
                if (expRequisitionRelease.getDocReleaseAmount() != null) {
                    expRequisitionRelease1.setDocReleaseAmount((new BigDecimal(-1)).multiply(expRequisitionRelease.getDocReleaseAmount()));
                }
                if (expRequisitionRelease.getDocReleaseQuantity() != null) {
                    expRequisitionRelease1.setDocReleaseQuantity((new BigDecimal(-1)).multiply(expRequisitionRelease.getDocReleaseQuantity()));
                }
                expRequisitionReleaseService.insertSelective(iRequest, expRequisitionRelease1);
                // 更新申请单分配行的已报销金额、已报销数量、报销状态
                ExpRequisitionDist expRequisitionDist = new ExpRequisitionDist();
                expRequisitionDist.setExpRequisitionDistId(expRequisitionRelease.getExpRequisitionDistId());
                expRequisitionDist = expRequisitionDistService.selectByPrimaryKey(iRequest, expRequisitionDist);
                // 根据分配行ID获取对应的已报销金额、已报销数量
                ExpRequisitionRelease expRequisitionRelease2 = new ExpRequisitionRelease();
                String releasedStatus = null;
                expRequisitionRelease2 = expRequisitionReleaseService.getRequisitionDistsRelease(expRequisitionRelease.getExpRequisitionDistId());
                if ((new BigDecimal(0)).compareTo(expRequisitionRelease2.getDistReleaseAmount()) == 0) {
                    releasedStatus = "N";
                } else if (expRequisitionDist.getPaymentAmount().compareTo(expRequisitionRelease2.getDistReleaseAmount()) > 0) {
                    releasedStatus = "Y";
                } else {
                    releasedStatus = "C";
                }
                expRequisitionDist.setReleasedStatus(releasedStatus);
                expRequisitionDist.setReleasedAmount(expRequisitionRelease2.getDistReleaseAmount());
                expRequisitionDist.setReleasedQuantity(expRequisitionRelease2.getDistReleaseQuantity());
                expRequisitionDistService.updateByPrimaryKeySelective(iRequest, expRequisitionDist);
                // 反冲对应的申请单预算数据
                BgtBudgetReserve bgtBudgetReserve = new BgtBudgetReserve();
                bgtBudgetReserve.setBusinessType("EXP_REQUISITION");
                bgtBudgetReserve.setDocumentLineId(expRequisitionDist.getExpRequisitionDistId());
                bgtBudgetReserve.setReleaseId(expRequisitionRelease.getReleaseId());
                List<BgtBudgetReserve> bgtBudgetReserves = new ArrayList<>();
                bgtBudgetReserves = bgtBudgetReserveService.select(iRequest, bgtBudgetReserve, 1, 0);
                if (!bgtBudgetReserves.isEmpty() && bgtBudgetReserves.size() == 1) {
                    String periodFlag = sysParameterService.queryParamValueByCode("BGT_REVERSE_ORIGINAL_PERIOD", null, null, expRequisitionRelease.getCompanyId(), null, null, null, null);
                    for (BgtBudgetReserve bgtBudgetReserve1 : bgtBudgetReserves) {
                        BgtBudgetReserve bgtBudgetReserve2 = new BgtBudgetReserve();
                        BeanUtils.copyProperties(bgtBudgetReserve1, bgtBudgetReserve2);
                        if (!"Y".equals(periodFlag)) {
                            bgtBudgetReserve2.setPeriodName(periodName);
                        }
                        if (bgtBudgetReserve1.getQuantity() != null) {
                            bgtBudgetReserve2.setQuantity((new BigDecimal(-1)).multiply(bgtBudgetReserve1.getQuantity()));
                        }
                        if (bgtBudgetReserve1.getAmount() != null) {
                            bgtBudgetReserve2.setAmount((new BigDecimal(-1)).multiply(bgtBudgetReserve1.getAmount()));
                        }
                        if (bgtBudgetReserve1.getFunctionalAmount() != null) {
                            bgtBudgetReserve2.setFunctionalAmount((new BigDecimal(-1)).multiply(bgtBudgetReserve1.getFunctionalAmount()));
                        }
                        bgtBudgetReserve2.set__status("add");
                        bgtBudgetReserveService.insertSelective(iRequest, bgtBudgetReserve2);
                    }
                }
            }
        }
    }

    /**
     * 反冲报销单分配行
     *
     * @param iRequest             请求上下文
     * @param expReportDists       需反冲的分配行Id
     * @param journalDate          反冲日期
     * @param periodName           期间
     * @param newExpReportLineId   新生成的报销单行Id
     * @param newExpReportHeaderId 新生成的报销单头Id
     * @param oldExpReportHeaderId 原报销单头Id
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/13 16:45
     * @Version 1.0
     **/
    public void reverseExpDist(IRequest iRequest, List<ExpReportDist> expReportDists, Date journalDate, String periodName, Long newExpReportLineId, Long newExpReportHeaderId, Long oldExpReportHeaderId) {
        if (!expReportDists.isEmpty()) {
            for (ExpReportDist expReportDist : expReportDists) {
                ExpReportDist expReportDist1 = new ExpReportDist();
                BeanUtils.copyProperties(expReportDist, expReportDist1);
                expReportDist1.setExpReportLineId(newExpReportLineId);
                if (expReportDist.getBusinessPrice() != null) {
                    expReportDist1.setBusinessPrice((new BigDecimal(-1)).multiply(expReportDist.getBusinessPrice()));
                }
                if (expReportDist.getBusinessAmount() != null) {
                    expReportDist1.setBusinessAmount((new BigDecimal(-1)).multiply(expReportDist.getBusinessAmount()));
                }
                if (expReportDist.getPrimaryQuantity() != null) {
                    expReportDist1.setPrimaryQuantity((new BigDecimal(-1)).multiply(expReportDist.getPrimaryQuantity()));
                }
                if (expReportDist.getSecondaryQuantity() != null) {
                    expReportDist1.setSecondaryQuantity((new BigDecimal(-1)).multiply(expReportDist.getSecondaryQuantity()));
                }
                if (expReportDist.getManagementAmount() != null) {
                    expReportDist1.setManagementAmount((new BigDecimal(-1)).multiply(expReportDist.getManagementAmount()));
                }
                if (expReportDist.getPaymentAmount() != null) {
                    expReportDist1.setPaymentAmount((new BigDecimal(-1)).multiply(expReportDist.getPaymentAmount()));
                }
                if (expReportDist.getReportFunctionalAmount() != null) {
                    expReportDist1.setReportFunctionalAmount((new BigDecimal(-1)).multiply(expReportDist.getReportFunctionalAmount()));
                }
                expReportDist1.setAuditDate(new Date());
                expReportDist1.setAuditDateTz(new Date());
                expReportDist1.setAuditDateLtz(new Date());
                expReportDist1.set__status("add");
                expReportDist1 = expReportDistService.insertSelective(iRequest, expReportDist1);
                // 反冲对应的预算数据
                BgtBudgetReserve bgtBudgetReserve = new BgtBudgetReserve();
                bgtBudgetReserve.setBusinessType(ExpReportHeader.EXP_REPORT);
                bgtBudgetReserve.setDocumentLineId(expReportDist1.getExpReportDistId());
                List<BgtBudgetReserve> bgtBudgetReserves = new ArrayList<>();
                bgtBudgetReserves = bgtBudgetReserveService.select(iRequest, bgtBudgetReserve, 1, 0);
                reverseBgtBudgetReserve(iRequest, bgtBudgetReserves, periodName, expReportDist1.getExpReportDistId(), newExpReportHeaderId, expReportDist1.getCompanyId());
                // 反冲对应的凭证数据
                ExpReportAccount expReportAccount = new ExpReportAccount();
                expReportAccount.setExpReportHeaderId(oldExpReportHeaderId);
                expReportAccount.setExpReportDistId(expReportDist.getExpReportDistId());
                List<ExpReportAccount> expReportAccounts = expReportAccountService.select(iRequest, expReportAccount, 1, 0);
                reverseExpReportAccount(iRequest, expReportAccounts, expReportDist1.getExpReportDistId(), newExpReportHeaderId, journalDate, periodName, expReportDist.getAccEntityId());
                // 反冲拆分税金预算占用
                List<BgtBudgetReserve> bgtBudgetReserves1 = new ArrayList<>();
                bgtBudgetReserves1 = bgtBudgetReserveService.selectTaxAmountReserveInfo(oldExpReportHeaderId, expReportDist.getExpReportLineId(), expReportDist.getExpReportDistId());
                if (!bgtBudgetReserves1.isEmpty()) {
                    for (BgtBudgetReserve bgtBudgetReserve1 : bgtBudgetReserves1) {
                        BgtBudgetReserve bgtBudgetReserve2 = new BgtBudgetReserve();
                        BeanUtils.copyProperties(bgtBudgetReserve1, bgtBudgetReserve2);
                        if (bgtBudgetReserve1.getAmount() != null) {
                            bgtBudgetReserve2.setAmount((new BigDecimal(-1)).multiply(bgtBudgetReserve1.getAmount()));
                        }
                        if (bgtBudgetReserve1.getFunctionalAmount() != null) {
                            bgtBudgetReserve2.setFunctionalAmount((new BigDecimal(-1)).multiply(bgtBudgetReserve1.getFunctionalAmount()));
                        }
                        if (bgtBudgetReserve1.getQuantity() != null) {
                            bgtBudgetReserve2.setQuantity((new BigDecimal(-1)).multiply(bgtBudgetReserve1.getQuantity()));
                        }
                        bgtBudgetReserve2.set__status("add");
                        bgtBudgetReserveService.insertSelective(iRequest, bgtBudgetReserve2);
                    }
                }
                // 反冲申请单
                reverseExpRequisitionReleases(iRequest, oldExpReportHeaderId, newExpReportHeaderId, expReportDist.getExpReportLineId(), newExpReportLineId, expReportDist.getExpReportDistId(), expReportDist1.getExpReportDistId(), periodName);
            }
        }
    }

    /**
     * 反冲报销单行
     *
     * @param iRequest             请求上下文
     * @param expReportLines       需反冲的行信息
     * @param journalDate          反冲日期
     * @param periodName           期间
     * @param newExpReportHeaderId 新生成的报销单头Id
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/13 16:45
     * @Version 1.0
     **/
    public void reverseExpLine(IRequest iRequest, List<ExpReportLine> expReportLines, Date journalDate, String periodName, Long newExpReportHeaderId) {
        if (!expReportLines.isEmpty()) {
            for (ExpReportLine expReportLine : expReportLines) {
                ExpReportLine expReportLine1 = new ExpReportLine();
                BeanUtils.copyProperties(expReportLine, expReportLine1);
                expReportLine1.setExpReportHeaderId(newExpReportHeaderId);
                if (expReportLine.getBusinessPrice() != null) {
                    expReportLine1.setBusinessPrice((new BigDecimal(-1)).multiply(expReportLine.getBusinessPrice()));
                }
                if (expReportLine.getBusinessAmount() != null) {
                    expReportLine1.setBusinessAmount((new BigDecimal(-1)).multiply(expReportLine.getBusinessAmount()));
                }
                if (expReportLine.getPrimaryQuantity() != null) {
                    expReportLine1.setPrimaryQuantity((new BigDecimal(-1)).multiply(expReportLine.getPrimaryQuantity()));
                }
                if (expReportLine.getSecondaryQuantity() != null) {
                    expReportLine1.setSecondaryQuantity((new BigDecimal(-1)).multiply(expReportLine.getSecondaryQuantity()));
                }
                if (expReportLine.getManagementAmount() != null) {
                    expReportLine1.setManagementAmount((new BigDecimal(-1)).multiply(expReportLine.getManagementAmount()));
                }
                if (expReportLine.getPaymentAmount() != null) {
                    expReportLine1.setPaymentAmount((new BigDecimal(-1)).multiply(expReportLine.getPaymentAmount()));
                }
                if (expReportLine.getReportFunctionalAmount() != null) {
                    expReportLine1.setReportFunctionalAmount((new BigDecimal(-1)).multiply(expReportLine.getReportFunctionalAmount()));
                }
                expReportLine1.setAuditDate(new Date());
                expReportLine1.setAuditDateTz(new Date());
                expReportLine1.setAuditDateLtz(new Date());
                expReportLine1.set__status("add");
                expReportLine1 = expReportLineService.insertSelective(iRequest, expReportLine1);
                // 反冲报销单分配行
                ExpReportDist expReportDist = new ExpReportDist();
                expReportDist.setExpReportLineId(expReportLine.getExpReportLineId());
                List<ExpReportDist> expReportDists = expReportDistService.select(iRequest, expReportDist, 1, 0);
                reverseExpDist(iRequest, expReportDists, journalDate, periodName, expReportLine1.getExpReportLineId(), newExpReportHeaderId, expReportLine.getExpReportHeaderId());
            }
        }
    }

    /**
     * 反冲计划付款行相关凭证信息
     *
     * @param iRequest                 请求上下文
     * @param oldExpReportHeaderId     原报销单头Id
     * @param newExpReportHeaderId     新生成的报销单头Id
     * @param oldPaymentScheduleLineId 原报销单计划付款行Id
     * @param newPaymentScheduleLineId 新生成的报销单计划付款行Id
     * @param journalDate              反冲日期
     * @param periodName               期间
     * @param accEntityId              核算主体Id
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/18 14:06
     * @Version 1.0
     **/
    public void reverseExpReportPmtAccount(IRequest iRequest, Long oldExpReportHeaderId, Long newExpReportHeaderId, Long oldPaymentScheduleLineId, Long newPaymentScheduleLineId, Date journalDate, String periodName, Long accEntityId) {
        List<ExpReportAccount> expReportAccounts = new ArrayList<>();
        ExpReportAccount expReportAccount = new ExpReportAccount();
        expReportAccount.setExpReportHeaderId(oldExpReportHeaderId);
        expReportAccount.setPaymentScheduleLineId(oldPaymentScheduleLineId);
        expReportAccounts = expReportAccountService.select(iRequest, expReportAccount, 1, 0);
        if (!expReportAccounts.isEmpty()) {
            for (ExpReportAccount expReportAccount1 : expReportAccounts) {
                ExpReportAccount expReportAccount2 = new ExpReportAccount();
                BeanUtils.copyProperties(expReportAccount1, expReportAccount2);
                // 比较所选反冲期间与原凭证期间大小
                Long internalPeriodNum = 0L;
                internalPeriodNum = gldPeriodMapper.getInternalPeriodNum(accEntityId, expReportAccount1.getPeriodName());
                Long reverseInternalPeriodNum = 0L;
                reverseInternalPeriodNum = gldPeriodMapper.getInternalPeriodNum(accEntityId, periodName);
                if (reverseInternalPeriodNum.longValue() >= internalPeriodNum.longValue()) {
                    expReportAccount2.setPeriodName(periodName);
                    expReportAccount2.setJournalDate(journalDate);
                }
                expReportAccount2.setPaymentScheduleLineId(newPaymentScheduleLineId);
                expReportAccount2.setExpReportHeaderId(newExpReportHeaderId);
                if (expReportAccount1.getEnteredAmountDr() != null) {
                    expReportAccount2.setEnteredAmountDr((new BigDecimal(-1)).multiply(expReportAccount1.getEnteredAmountDr()));
                }
                if (expReportAccount1.getEnteredAmountCr() != null) {
                    expReportAccount2.setEnteredAmountCr((new BigDecimal(-1)).multiply(expReportAccount1.getEnteredAmountCr()));
                }
                if (expReportAccount1.getFunctionalAmountDr() != null) {
                    expReportAccount2.setFunctionalAmountDr((new BigDecimal(-1)).multiply(expReportAccount1.getFunctionalAmountDr()));
                }
                if (expReportAccount1.getFunctionalAmountCr() != null) {
                    expReportAccount2.setFunctionalAmountCr((new BigDecimal(-1)).multiply(expReportAccount1.getFunctionalAmountCr()));
                }
                expReportAccount2.setGldInterfaceFlag("P");
                expReportAccount2.set__status("add");
                expReportAccountService.insertSelective(iRequest, expReportAccount2);
            }
        }
    }


    /**
     * 反冲计划付款行
     *
     * @param iRequest              请求上下文
     * @param expReportPmtSchedules 需反冲的计划付款行信息
     * @param journalDate           反冲日期
     * @param periodName            期间
     * @param newExpReportHeaderId  新生成的报销单头Id
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/18 13:42
     * @Version 1.0
     **/
    public void reverseExpPmtSchedule(IRequest iRequest, List<ExpReportPmtSchedule> expReportPmtSchedules, Date journalDate, String periodName, Long newExpReportHeaderId) {
        if (!expReportPmtSchedules.isEmpty()) {
            for (ExpReportPmtSchedule expReportPmtSchedule : expReportPmtSchedules) {
                ExpReportPmtSchedule expReportPmtSchedule1 = new ExpReportPmtSchedule();
                BeanUtils.copyProperties(expReportPmtSchedule, expReportPmtSchedule1);
                if (expReportPmtSchedule.getDueAmount() != null) {
                    expReportPmtSchedule1.setDueAmount((new BigDecimal(-1)).multiply(expReportPmtSchedule.getDueAmount()));
                }
                ;
                expReportPmtSchedule1.setExpReportHeaderId(newExpReportHeaderId);
                expReportPmtSchedule1.setPaymentScheduleLineId(null);
                expReportPmtSchedule1 = expReportPmtScheduleService.insertSelective(iRequest, expReportPmtSchedule1);
                // 查询计划付款行是否关联合同
                // 反冲计划付款行相关凭证信息
                reverseExpReportPmtAccount(iRequest, expReportPmtSchedule.getExpReportHeaderId(), newExpReportHeaderId, expReportPmtSchedule.getPaymentScheduleLineId(), expReportPmtSchedule1.getPaymentScheduleLineId(), journalDate, periodName, expReportPmtSchedule.getAccEntityId());
            }
        }
    }

    @Override
    public void reverse(IRequest iRequest, Date journalDate, String periodName, List<ExpReportHeader> expReportHeaders) {
        if (!expReportHeaders.isEmpty()) {
            for (ExpReportHeader expReportHeader : expReportHeaders) {
                // 需要反冲的单据头信息
                ExpReportHeader expReportHeader1 = new ExpReportHeader();
                Long expReportHeaderId = expReportHeader.getExpReportHeaderId();
                expReportHeader1.setExpReportHeaderId(expReportHeaderId);
                expReportHeader1 = service.selectByPrimaryKey(iRequest, expReportHeader);
                // 需要反冲的单据行信息
                ExpReportLine expReportLine = new ExpReportLine();
                expReportLine.setExpReportHeaderId(expReportHeaderId);
                List<ExpReportLine> expReportLines = new ArrayList<>();
                expReportLines = expReportLineService.select(iRequest, expReportLine, 1, 0);
                // 需要反冲的计划付款行
                List<ExpReportPmtSchedule> expReportPmtSchedules = new ArrayList<>();
                expReportPmtSchedules = expReportPmtScheduleMapper.getExpReportPmtSchedules(expReportHeaderId);
                Long newExpReportHeaderId = reverseExpHeader(iRequest, expReportHeader1, journalDate);
                reverseExpLine(iRequest, expReportLines, journalDate, periodName, newExpReportHeaderId);
                reverseExpPmtSchedule(iRequest, expReportPmtSchedules, journalDate, periodName, newExpReportHeaderId);
                // 传送报销单凭证信息进分录表
                glAccountEntryService.headerGlAccountEntry(iRequest, GlAccountEntry.RULE_TYPE_EXP_REPORT, newExpReportHeaderId);
            }
        }
    }

    /**
     * <p>
     * 报销单头查询
     * <p/>
     *
     * @param request
     * @param expReportHeaderId   报销单头ID(新建时可为NULL)
     * @param moExpReportTypeId   报销单类型ID
     * @param accEntityId         核算主体ID
     * @param employeeId          报销人ID
     * @param paymentCurrencyCode 付款币种
     * @return 报销单头list
     * @author yang.duan 2019/3/20 15:09
     */
    @Override
    public List<ExpReportHeader> expReportHeaderQuery(IRequest request, Long expReportHeaderId, Long moExpReportTypeId, Long accEntityId, Long employeeId, String paymentCurrencyCode) {
        List<ExpReportHeader> reportHeaders = reportHeaderMapper.expReportHeaderQuery(expReportHeaderId, moExpReportTypeId, accEntityId, employeeId, paymentCurrencyCode);
        // 进行对应赋值操作
        if (reportHeaders != null && !reportHeaders.isEmpty()) {
            for (ExpReportHeader header : reportHeaders) {
                if (header.getExpReportHeaderId() != null) {
                    // 获取核算主体对应本位币对应信息
                    String funCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(header.getAccEntityId());
                    GldCurrency funCurrency = new GldCurrency();
                    funCurrency.setCurrencyCode(funCurrencyCode);
                    List<GldCurrency> funCurrencyList = gldCurrencyService.select(request, funCurrency, 1, 0);
                    if (funCurrencyList != null && !funCurrencyList.isEmpty()) {
                        funCurrency = funCurrencyList.get(0);
                    }
                    if (funCurrency != null) {
                        header.setFunctionalCurrencyCode(funCurrencyCode);
                        header.setFunctionalCurrencyName(funCurrency.getCurrencyName());
                        header.setFunctionalCurrencyPrecision(funCurrency.getFinancePrecision());
                    }
                    // 获取支付币种->本位币汇率
                    BigDecimal pay2funRate = gldExchangeRateService.getExchangeRate(header.getAccEntityId(), header.getPaymentCurrencyCode(), funCurrencyCode, header.getPay2funExchangeType(), header.getReportDate(), header.getPeriodName(), "N");
                    header.setPay2funExchangeRate(pay2funRate);
                    // 获取管理币种信息
                    GldCurrency magCurrency = new GldCurrency();
                    magCurrency.setCurrencyCode(header.getManagementCurrencyCode());
                    List<GldCurrency> magCurrencyList = gldCurrencyService.select(request, magCurrency, 1, 0);
                    if (magCurrencyList != null && !magCurrencyList.isEmpty()) {
                        magCurrency = magCurrencyList.get(0);
                    }
                    if (magCurrency != null) {
                        header.setManagementCurrencyName(magCurrency.getCurrencyName());
                        header.setManagementCurrencyPrecision(magCurrency.getFinancePrecision());
                    }
                    // 获取支付币种->管理币种汇率类型及描述
                    String pay2MagRateType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_MAG_EXCH_RATE_TYPE, null, null, header.getCompanyId(), null, null, null, null);
                    GldExchangerateType exchangerateType = new GldExchangerateType();
                    exchangerateType.setTypeCode(pay2MagRateType);
                    List<GldExchangerateType> exchangerateTypeList = gldExchangerateTypeService.select(request, exchangerateType, 1, 0);
                    if (exchangerateTypeList != null && !exchangerateTypeList.isEmpty()) {
                        exchangerateType = exchangerateTypeList.get(0);
                    }
                    if (exchangerateType != null && !BaseConstants.NO.equals(exchangerateType.getTypeCode())) {
                        header.setPay2magExchangeType(pay2MagRateType);
                        header.setPay2magExchangeTypeName(exchangerateType.getDescription());
                    }
                    // 获取支付币种->管理币种汇率
                    BigDecimal pay2MagRate = gldExchangeRateService.getExchangeRate(header.getAccEntityId(), header.getPaymentCurrencyCode(), header.getManagementCurrencyCode(), pay2MagRateType, header.getReportDate(), header.getPeriodName(), "N");
                    header.setPay2magExchangeRate(pay2MagRate);

                } else {
                    // 获取报销日期与预算期间
                    // 获取公司默认预算实体
                    FndCompanyRefBgtEntity fndCompanyRefBgtEntity = fndCompanyRefBgtEntityMapper.getDftBgtEntity(request.getCompanyId());
                    header.setReportDate(DateUtils.getCurrentDate());
                    if (fndCompanyRefBgtEntity != null && fndCompanyRefBgtEntity.getEntityId() != null) {
                        String bgtPeriodName = bgtPeriodService.getBgtPeriodName(DateUtils.getCurrentDate(), fndCompanyRefBgtEntity.getEntityId(), request.getCompanyId(), null);
                        header.setPeriodName(bgtPeriodName);
                    }
                    // 获取核算主体对应本位币对应信息
                    String funCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(accEntityId);
                    GldCurrency funCurrency = new GldCurrency();
                    funCurrency.setCurrencyCode(funCurrencyCode);
                    funCurrency = gldCurrencyMapper.selectOne(funCurrency);
                    if (funCurrency != null) {
                        header.setFunctionalCurrencyCode(funCurrencyCode);
                        header.setFunctionalCurrencyName(funCurrency.getCurrencyName());
                        header.setFunctionalCurrencyPrecision(funCurrency.getFinancePrecision());
                    }
                    // 获取支付币种->本位币汇率类型及描述
                    String pay2funRateType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, null, accEntityId, null, null, null);
                    if (pay2funRateType != null && !pay2funRateType.equals(BaseConstants.NO)) {
                        GldExchangerateType gldPay2funRateType = new GldExchangerateType();
                        gldPay2funRateType.setTypeCode(pay2funRateType);
                        List<GldExchangerateType> gldPay2funRateTypeList = gldExchangerateTypeService.select(request, gldPay2funRateType, 1, 0);

                        if (gldPay2funRateTypeList != null && !gldPay2funRateTypeList.isEmpty()) {
                            gldPay2funRateType = gldPay2funRateTypeList.get(0);
                        }
                        if (pay2funRateType != null) {
                            header.setPay2funExchangeType(pay2funRateType);
                            header.setPay2funExchangeTypeName(gldPay2funRateType.getDescription());
                        }
                    } else {
                        header.setPay2funExchangeType(null);
                        header.setPay2funExchangeTypeName(null);
                    }
                    // 获取支付币种->本位币汇率
                    BigDecimal pay2funRate = gldExchangeRateService.getExchangeRate(header.getAccEntityId(), header.getPaymentCurrencyCode(), funCurrencyCode, pay2funRateType, header.getReportDate(), header.getPeriodName(), "N");
                    header.setPay2funExchangeRate(pay2funRate);
                    // 获取管理币种信息
                    GldCurrency magCurrency = new GldCurrency();
                    magCurrency.setCurrencyCode(header.getManagementCurrencyCode());
                    magCurrency = gldCurrencyMapper.selectOne(magCurrency);
                    if (magCurrency != null) {
                        header.setManagementCurrencyName(magCurrency.getCurrencyName());
                        header.setManagementCurrencyPrecision(magCurrency.getFinancePrecision());
                    }
                    // 获取支付币种->管理币种汇率类型及描述
                    String pay2MagRateType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_MAG_EXCH_RATE_TYPE, null, null, request.getCompanyId(), null, null, null, null);
                    if (pay2MagRateType != null && !pay2MagRateType.equals(BaseConstants.NO)) {
                        GldExchangerateType pay2magRateType = new GldExchangerateType();
                        pay2magRateType.setTypeCode(pay2MagRateType);
                        List<GldExchangerateType> pay2magRateTypeList = gldExchangerateTypeService.select(request, pay2magRateType, 1, 0);
                        if (pay2magRateTypeList != null && !pay2magRateTypeList.isEmpty()) {
                            pay2magRateType = pay2magRateTypeList.get(0);
                        }
                        if (pay2magRateType != null) {
                            header.setPay2magExchangeType(pay2MagRateType);
                            header.setPay2magExchangeTypeName(pay2magRateType.getDescription());
                        }
                    } else {
                        header.setPay2magExchangeType(null);
                        header.setPay2magExchangeTypeName(null);
                    }
                    // 获取支付币种->管理币种汇率
                    BigDecimal pay2MagRate = gldExchangeRateService.getExchangeRate(header.getAccEntityId(), header.getPaymentCurrencyCode(), header.getManagementCurrencyCode(), pay2MagRateType, header.getReportDate(), header.getPeriodName(), "N");
                    header.setPay2magExchangeRate(pay2MagRate);

                    // 获取收款方类别描述
                    String payeeCategoryName = codeService.getCodeMeaningByValue(request, "PAYMENT_OBJECT", header.getPayeeCategory());
                    header.setPayeeCategoryName(payeeCategoryName);
                    // 获取收款方ID
                    if ("EMPLOYEE".equals(header.getPayeeCategory())) {
                        header.setPayeeId(employeeId);
                        header.setPayeeName(expEmployeeMapper.selectByPrimaryKey(employeeId).getName());
                    } else {
                        header.setPayeeId(null);
                        header.setPayeeName(null);
                    }

                    // 获取组织架构相关参数
                    header = setGldAndBgtOrg(request, header);
                    // 获取默认维值相关
                    header = setDimensionAndValue(request, header);

                }
                // 获取收款方信息
                switch (header.getPayeeCategory()) {
                    case "EMPLOYEE":
                        header = setEmployeeInfo(request, header);
                        break;
                    case "VENDER":
                        header = setVenderInfo(request, header);
                        break;
                    case "CUSTOMER":
                        header = setCustomerInfo(request, header);
                        break;
                    default:
                        break;
                }

            }
        }
        return reportHeaders;
    }

    private ExpReportHeader setEmployeeInfo(IRequest request, ExpReportHeader header) {
        if (header.getPayeeId() != null) {
            ExpEmployee expEmployee = new ExpEmployee();
            expEmployee.setEmployeeId(header.getPayeeId());
            expEmployee = employeeService.selectByPrimaryKey(request, expEmployee);
            if (expEmployee != null) {
                header.setPayeeName(expEmployee.getName());
            }
            ExpEmployeeAccount employeeAccount = new ExpEmployeeAccount();
            employeeAccount.setEmployeeId(header.getPayeeId());
            List<ExpEmployeeAccount> employeeAccounts = expEmployeeAccountService.select(request, employeeAccount, 1, 0);
            if (employeeAccounts != null && !employeeAccounts.isEmpty()) {
                employeeAccount = employeeAccounts.get(0);
            }
            if (employeeAccount != null) {
                header.setAccountName(employeeAccount.getAccountName());
                header.setAccountNumber(employeeAccount.getAccountNumber());
                header.setBackName(employeeAccount.getBankName());
                header.setBackCode(employeeAccount.getBankCode());
                header.setBankLocationCode(employeeAccount.getBankLocationCode());
                header.setBankLocationName(employeeAccount.getBankLocation());
                header.setProvinceCode(employeeAccount.getProvinceCode());
                header.setProvinceName(employeeAccount.getProvinceName());
                header.setCityCode(employeeAccount.getCityCode());
                header.setCityName(employeeAccount.getCityName());
            }
        }
        return header;
    }

    private ExpReportHeader setVenderInfo(IRequest request, ExpReportHeader header) {
        if (header.getPayeeId() != null) {
            PurSystemVender purSystemVender = new PurSystemVender();
            purSystemVender.setVenderId(header.getPayeeId());
            purSystemVender = purSystemVenderService.selectByPrimaryKey(request, purSystemVender);
            if (purSystemVender != null) {
                header.setPayeeName(purSystemVender.getDescription());
            }
            PurVenderAccount purVenderAccount = new PurVenderAccount();
            purVenderAccount.setVenderId(header.getPayeeId());
            List<PurVenderAccount> purVenderAccounts = purVenderAccountService.select(request, purVenderAccount, 1, 0);
            if (purVenderAccounts != null && !purVenderAccounts.isEmpty()) {
                purVenderAccount = purVenderAccounts.get(0);
            }
            if (purVenderAccount != null) {
                CshCnaps cshCnaps = new CshCnaps();
                cshCnaps.setBankLocationCode(purVenderAccount.getCnapsCode());
                List<CshCnaps> cnapsList = cshCnapsService.select(request, cshCnaps, 1, 0);
                if (cnapsList != null && !cnapsList.isEmpty()) {
                    cshCnaps = cnapsList.get(0);
                }
                if (cshCnaps != null) {
                    header.setBankLocationCode(cshCnaps.getBankLocationCode());
                    header.setBankLocationName(cshCnaps.getBankLocationName());
                }
                header.setAccountName(purVenderAccount.getAccountName());
                header.setAccountNumber(purVenderAccount.getAccountNumber());
                header.setBackName(purVenderAccount.getBankName());
                header.setBackCode(purVenderAccount.getBankCode());
                header.setProvinceCode(purVenderAccount.getProvinceCode());
                header.setProvinceName(purVenderAccount.getProvinceName());
                header.setCityCode(purVenderAccount.getCityCode());
                header.setCityName(purVenderAccount.getCityName());
            }
        }
        return header;
    }

    private ExpReportHeader setCustomerInfo(IRequest request, ExpReportHeader header) {
        if (header.getPayeeId() != null) {
            OrdSystemCustomer ordSystemCustomer = new OrdSystemCustomer();
            ordSystemCustomer.setCustomerId(header.getPayeeId());
            ordSystemCustomer = ordSystemCustomerService.selectByPrimaryKey(request, ordSystemCustomer);
            if (ordSystemCustomer != null) {
                header.setPayeeName(ordSystemCustomer.getDescription());
            }
            OrdCustomerAccount ordCustomerAccount = new OrdCustomerAccount();
            ordCustomerAccount.setCustomerId(header.getPayeeId());
            List<OrdCustomerAccount> ordCustomerAccounts = ordCustomerAccountService.select(request, ordCustomerAccount, 1, 0);
            if (ordCustomerAccounts != null && !ordCustomerAccounts.isEmpty()) {
                ordCustomerAccount = ordCustomerAccounts.get(0);
            }
            if (ordCustomerAccount != null) {
                CshCnaps cshCnaps = new CshCnaps();
                cshCnaps.setBankLocationCode(ordCustomerAccount.getCnapsCode());
                List<CshCnaps> cnapsList = cshCnapsService.select(request, cshCnaps, 1, 0);
                if (cnapsList != null && !cnapsList.isEmpty()) {
                    cshCnaps = cnapsList.get(0);
                }
                if (cshCnaps != null) {
                    header.setBankLocationCode(cshCnaps.getBankLocationCode());
                    header.setBankLocationName(cshCnaps.getBankLocationName());
                }
                header.setAccountName(ordCustomerAccount.getAccountName());
                header.setAccountNumber(ordCustomerAccount.getAccountNumber());
                header.setBackName(ordCustomerAccount.getBankName());
                header.setBackCode(ordCustomerAccount.getBankCode());
                header.setProvinceCode(ordCustomerAccount.getProvinceCode());
                header.setProvinceName(ordCustomerAccount.getProvinceName());
                header.setCityCode(ordCustomerAccount.getCityCode());
                header.setCityName(ordCustomerAccount.getCityName());
            }
        }
        return header;
    }

    private ExpReportHeader setGldAndBgtOrg(IRequest request, ExpReportHeader header) {
        ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(request, header.getCompanyId(), header.getEmployeeId());
        // 设置默认职务
        if (employeeAssign != null && employeeAssign.getPositionId() != null) {
            header.setPositionId(employeeAssign.getPositionId());
            header.setPositionName(employeeAssign.getPositionName());

            // 设置默认岗位
            ExpOrgPosition expOrgPosition = new ExpOrgPosition();
            expOrgPosition.setPositionId(employeeAssign.getPositionId());
            expOrgPosition = expOrgPositionService.selectByPrimaryKey(request, expOrgPosition);

            if (expOrgPosition != null && expOrgPosition.getUnitId() != null) {
                ExpOrgUnit expOrgUnit = new ExpOrgUnit();
                expOrgUnit.setUnitId(expOrgPosition.getUnitId());
                expOrgUnit = expOrgUnitService.selectByPrimaryKey(request, expOrgUnit);
                if (expOrgUnit != null) {
                    header.setUnitId(expOrgUnit.getUnitId());
                    header.setUnitName(expOrgUnit.getDescription());
                }
            }
        }
        // 设置默认成本中心
        ExpEmployeeAssign respCenterAssDto = new ExpEmployeeAssign();
        GldResponsibilityCenter responsibilityCenter = new GldResponsibilityCenter();
        respCenterAssDto.setCompanyId(header.getCompanyId());
        respCenterAssDto.setEmployeeId(header.getEmployeeId());
        respCenterAssDto.setPositionId(header.getPositionId());
        respCenterAssDto.setAccEntityId(header.getAccEntityId());
        respCenterAssDto.setEnabledFlag("Y");
        respCenterAssDto = expEmployeeAssignMapper.selectOne(respCenterAssDto);
        if (respCenterAssDto != null && respCenterAssDto.getResponsibilityCenterId() != null) {
            responsibilityCenter.setResponsibilityCenterId(respCenterAssDto.getResponsibilityCenterId());
            responsibilityCenter = gldResponsibilityCenterService.selectByPrimaryKey(request, responsibilityCenter);
            if (responsibilityCenter != null) {
                header.setRespCenterId(respCenterAssDto.getResponsibilityCenterId());
                header.setRespCenterName(responsibilityCenter.getResponsibilityCenterName());
            }

        } else {
            // 获取部门对应默认成本中心
            ExpOrgUnitRefAccOrg orgUnitRefAccOrg = new ExpOrgUnitRefAccOrg();
            orgUnitRefAccOrg.setAccEntityId(header.getAccEntityId());
            orgUnitRefAccOrg.setUnitId(header.getUnitId());
            orgUnitRefAccOrg.setEnabledFlag("Y");
            orgUnitRefAccOrg.setDefaultFlag("Y");
            orgUnitRefAccOrg = expOrgUnitRefAccOrgMapper.selectOne(orgUnitRefAccOrg);
            if (orgUnitRefAccOrg != null && orgUnitRefAccOrg.getRespCenterId() != null) {
                responsibilityCenter.setResponsibilityCenterId(orgUnitRefAccOrg.getRespCenterId());
                responsibilityCenter = gldResponsibilityCenterService.selectByPrimaryKey(request, responsibilityCenter);
                if (responsibilityCenter != null) {
                    header.setRespCenterId(orgUnitRefAccOrg.getRespCenterId());
                    header.setRespCenterName(responsibilityCenter.getResponsibilityCenterName());
                }
            }
        }
        // 设置默认预算实体
        // 获取员工分配的预算实体
        ExpEmployeeAssign bgtEntityAssDto = new ExpEmployeeAssign();
        BgtEntity bgtEntity = new BgtEntity();
        bgtEntityAssDto.setEmployeeId(header.getEmployeeId());
        bgtEntityAssDto.setCompanyId(header.getCompanyId());
        bgtEntityAssDto.setPositionId(header.getPositionId());
        bgtEntityAssDto.setEnabledFlag("Y");
        bgtEntityAssDto = expEmployeeAssignMapper.selectOne(bgtEntityAssDto);
        if (bgtEntityAssDto != null && bgtEntityAssDto.getEntityId() != null) {
            bgtEntity.setEntityId(bgtEntityAssDto.getEntityId());
            bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
            if (bgtEntity != null) {
                header.setBgtEntityId(bgtEntityAssDto.getEntityId());
                header.setBgtEntityName(bgtEntity.getDescription());
            }
        } else {
            // 核算主体+公司获取预算实体
            FndCompanyRefAccBe companyRefAccBe = fndCompanyRefAccBeService.getBgtEntityByComAndAcc(request, header.getCompanyId(), header.getAccEntityId());
            if (companyRefAccBe != null && companyRefAccBe.getBgtEntityId() != null) {
                header.setBgtEntityId(companyRefAccBe.getBgtEntityId());
                header.setBgtEntityName(companyRefAccBe.getBgtEntityName());
            } else {
                // 获取公司默认的预算实体
                FndCompanyRefBgtEntity companyRefBgtEntity = fndCompanyRefBgtEntityService.getDftBgtEntity(request, header.getCompanyId());
                if (companyRefBgtEntity != null && companyRefBgtEntity.getEntityId() != null) {
                    header.setBgtEntityId(companyRefBgtEntity.getEntityId());
                    header.setBgtEntityName(companyRefBgtEntity.getBgtEntityName());
                }
            }
        }
        // 设置默认预算中心
        // 获取员工分配的预算中心
        ExpEmployeeAssign bgtCenterAssDto = new ExpEmployeeAssign();
        BgtCenter bgtCenter = new BgtCenter();
        bgtCenterAssDto.setEmployeeId(header.getEmployeeId());
        bgtCenterAssDto.setCompanyId(header.getCompanyId());
        bgtCenterAssDto.setPositionId(header.getPositionId());
        bgtCenterAssDto.setEntityId(header.getBgtEntityId());
        bgtCenterAssDto.setEnabledFlag("Y");
        bgtCenterAssDto = expEmployeeAssignMapper.selectOne(bgtCenterAssDto);
        if (bgtCenterAssDto != null && bgtCenterAssDto.getCenterId() != null) {
            bgtCenter.setCenterId(bgtCenterAssDto.getCenterId());
            bgtCenter = bgtCenterService.selectByPrimaryKey(request, bgtCenter);
            if (bgtCenter != null) {
                header.setBgtCenterId(bgtCenterAssDto.getCenterId());
                header.setBgtCenterName(bgtCenter.getDescription());
            }
        } else {
            // 部门ID+核算主体ID+成本中心ID+预算实体ID
            ExpOrgUnitRefAccOrg unitRefAccOrg = new ExpOrgUnitRefAccOrg();
            unitRefAccOrg.setUnitId(header.getUnitId());
            unitRefAccOrg.setAccEntityId(header.getAccEntityId());
            unitRefAccOrg.setRespCenterId(header.getRespCenterId());
            unitRefAccOrg.setBgtEntityId(header.getBgtEntityId());
            unitRefAccOrg.setEnabledFlag("Y");
            unitRefAccOrg.setDefaultFlag("Y");
            List<ExpOrgUnitRefAccOrg> unitRefAccOrgList = expOrgUnitRefAccOrgService.select(request, unitRefAccOrg, 1, 0);
            if (unitRefAccOrgList != null && !unitRefAccOrgList.isEmpty()) {
                unitRefAccOrg = unitRefAccOrgList.get(0);
            }
            if (unitRefAccOrg != null && unitRefAccOrg.getBgtCenterId() != null) {
                header.setBgtCenterId(unitRefAccOrg.getBgtCenterId());
                header.setBgtCenterName(unitRefAccOrg.getBgtCenterName());
            } else {
                // 核算主体ID+预算实体ID
                GldRespCenterRefBc gldRespCenterRefBc = new GldRespCenterRefBc();
                gldRespCenterRefBc.setRespCenterId(header.getRespCenterId());
                gldRespCenterRefBc.setBgtEntityId(header.getBgtEntityId());
                gldRespCenterRefBc.setEnabledFlag("Y");
                gldRespCenterRefBc.setDefaultFlag("Y");
                List<GldRespCenterRefBc> gldRespCenterRefBcList = gldRespCenterRefBcService.select(request, gldRespCenterRefBc, 1, 0);
                if (gldRespCenterRefBcList != null && !gldRespCenterRefBcList.isEmpty()) {
                    gldRespCenterRefBc = gldRespCenterRefBcList.get(0);
                }
                if (gldRespCenterRefBc != null && gldRespCenterRefBc.getBgtCenterId() != null) {
                    header.setBgtCenterId(gldRespCenterRefBc.getBgtCenterId());
                    header.setBgtCenterName(gldRespCenterRefBc.getBgtCenterName());
                } else {
                    // 部门ID+预算实体ID
                    ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                    expOrgUnitRefBgtOrg.setUnitId(header.getUnitId());
                    expOrgUnitRefBgtOrg.setBgtEntityId(header.getBgtEntityId());
                    expOrgUnitRefBgtOrg.setEnabledFlag("Y");
                    expOrgUnitRefBgtOrg.setDefaultFlag("Y");
                    List<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgList = expOrgUnitRefBgtOrgService.select(request, expOrgUnitRefBgtOrg, 1, 0);
                    if (expOrgUnitRefBgtOrgList != null && !expOrgUnitRefBgtOrgList.isEmpty()) {
                        expOrgUnitRefBgtOrg = expOrgUnitRefBgtOrgList.get(0);
                    }
                    if (expOrgUnitRefBgtOrg != null && expOrgUnitRefBgtOrg.getBgtCenterId() != null) {
                        header.setBgtCenterId(expOrgUnitRefBgtOrg.getBgtCenterId());
                        header.setBgtCenterName(expOrgUnitRefBgtOrg.getBgtCenterName());
                    }
                }
            }
        }
        return header;
    }


    private ExpReportHeader setDimensionAndValue(IRequest request, ExpReportHeader header) {
        // 获取默认维值ID
        FndDimensionValue dimensionValue1 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(1));
        header.setDimension1Id(dimensionValue1 != null ? dimensionValue1.getDimensionValueId() : null);
        header.setDimension1Name(dimensionValue1 != null ? dimensionValue1.getDescription() : null);

        FndDimensionValue dimensionValue2 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(2));
        header.setDimension2Id(dimensionValue2 != null ? dimensionValue2.getDimensionValueId() : null);
        header.setDimension2Name(dimensionValue2 != null ? dimensionValue2.getDescription() : null);

        FndDimensionValue dimensionValue3 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(3));
        header.setDimension3Id(dimensionValue3 != null ? dimensionValue3.getDimensionValueId() : null);
        header.setDimension3Name(dimensionValue3 != null ? dimensionValue3.getDescription() : null);

        FndDimensionValue dimensionValue4 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(4));
        header.setDimension4Id(dimensionValue4 != null ? dimensionValue4.getDimensionValueId() : null);
        header.setDimension4Name(dimensionValue4 != null ? dimensionValue4.getDescription() : null);

        FndDimensionValue dimensionValue5 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(5));
        header.setDimension5Id(dimensionValue5 != null ? dimensionValue5.getDimensionValueId() : null);
        header.setDimension5Name(dimensionValue5 != null ? dimensionValue5.getDescription() : null);

        FndDimensionValue dimensionValue6 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(6));
        header.setDimension6Id(dimensionValue6 != null ? dimensionValue6.getDimensionValueId() : null);
        header.setDimension6Name(dimensionValue6 != null ? dimensionValue6.getDescription() : null);

        FndDimensionValue dimensionValue7 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(7));
        header.setDimension7Id(dimensionValue7 != null ? dimensionValue7.getDimensionValueId() : null);
        header.setDimension7Name(dimensionValue7 != null ? dimensionValue7.getDescription() : null);

        FndDimensionValue dimensionValue8 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(8));
        header.setDimension8Id(dimensionValue8 != null ? dimensionValue8.getDimensionValueId() : null);
        header.setDimension8Name(dimensionValue8 != null ? dimensionValue8.getDescription() : null);

        FndDimensionValue dimensionValue9 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(9));
        header.setDimension9Id(dimensionValue9 != null ? dimensionValue9.getDimensionValueId() : null);
        header.setDimension9Name(dimensionValue9 != null ? dimensionValue9.getDescription() : null);

        FndDimensionValue dimensionValue10 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(10));
        header.setDimension10Id(dimensionValue10 != null ? dimensionValue10.getDimensionValueId() : null);
        header.setDimension10Name(dimensionValue10 != null ? dimensionValue10.getDescription() : null);

        FndDimensionValue dimensionValue11 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(11));
        header.setDimension11Id(dimensionValue11 != null ? dimensionValue11.getDimensionValueId() : null);
        header.setDimension11Name(dimensionValue11 != null ? dimensionValue11.getDescription() : null);

        FndDimensionValue dimensionValue12 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(12));
        header.setDimension12Id(dimensionValue12 != null ? dimensionValue12.getDimensionValueId() : null);
        header.setDimension12Name(dimensionValue12 != null ? dimensionValue12.getDescription() : null);

        FndDimensionValue dimensionValue13 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(13));
        header.setDimension13Id(dimensionValue13 != null ? dimensionValue13.getDimensionValueId() : null);
        header.setDimension13Name(dimensionValue13 != null ? dimensionValue13.getDescription() : null);

        FndDimensionValue dimensionValue14 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(14));
        header.setDimension14Id(dimensionValue14 != null ? dimensionValue14.getDimensionValueId() : null);
        header.setDimension14Name(dimensionValue14 != null ? dimensionValue14.getDescription() : null);

        FndDimensionValue dimensionValue15 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(15));
        header.setDimension15Id(dimensionValue15 != null ? dimensionValue15.getDimensionValueId() : null);
        header.setDimension15Name(dimensionValue15 != null ? dimensionValue15.getDescription() : null);

        FndDimensionValue dimensionValue16 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(16));
        header.setDimension16Id(dimensionValue16 != null ? dimensionValue16.getDimensionValueId() : null);
        header.setDimension16Name(dimensionValue16 != null ? dimensionValue16.getDescription() : null);

        FndDimensionValue dimensionValue17 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(17));
        header.setDimension17Id(dimensionValue17 != null ? dimensionValue17.getDimensionValueId() : null);
        header.setDimension17Name(dimensionValue17 != null ? dimensionValue17.getDescription() : null);

        FndDimensionValue dimensionValue18 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(18));
        header.setDimension18Id(dimensionValue18 != null ? dimensionValue18.getDimensionValueId() : null);
        header.setDimension18Name(dimensionValue18 != null ? dimensionValue18.getDescription() : null);

        FndDimensionValue dimensionValue19 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(19));
        header.setDimension19Id(dimensionValue19 != null ? dimensionValue19.getDimensionValueId() : null);
        header.setDimension19Name(dimensionValue19 != null ? dimensionValue19.getDescription() : null);

        FndDimensionValue dimensionValue20 = expMoRepTypeRefHdDimService.getDftDimensionValue(request, header.getMoExpReportTypeId(), Long.valueOf(20));
        header.setDimension20Id(dimensionValue20 != null ? dimensionValue20.getDimensionValueId() : null);
        header.setDimension20Name(dimensionValue20 != null ? dimensionValue20.getDescription() : null);

        return header;
    }

    /**
     * <p>
     * 报销单提交
     * <p/>
     *
     * @param request
     * @param expReportHeaderId      报销单头ID
     * @param bgtIgnoreWarningFlag
     * @param vatIgnoreAccEntityFlag
     * @param vatIgnoreDateFlag
     * @return map
     * @author yang.duan 2019/4/2 13:41
     */
    @Override
    public Map submitExpReport(IRequest request, Long expReportHeaderId, String bgtIgnoreWarningFlag, String vatIgnoreAccEntityFlag, String vatIgnoreDateFlag) {
        Map resultMap = new HashMap();
        // 查询报销单头数据
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(expReportHeaderId);
        header = self().selectByPrimaryKey(request, header);
        if (statusCheck(request, header.getExpReportHeaderId())) {
            // 获取报销单类型
            ExpMoReportType reportType = new ExpMoReportType();
            reportType.setMoExpReportTypeId(header.getMoExpReportTypeId());
            reportType = reportTypeService.selectByPrimaryKey(request, reportType);

            // 头关联容限控制
            expReqHeadToleranceCheck(request, expReportHeaderId);

            // 增值税预算数据提交(未)
            // 增值税发票检查(未)
            // 如果当前单据类型启用预算检查,则先进行预算检查
            if (reportType != null && "Y".equals(reportType.getBudgetControlEnabled())) {
                // 调用预算检查逻辑
                BgtCheckResult checkResult = expReportBgtCheck(request, expReportHeaderId, bgtIgnoreWarningFlag, "N", "N");
                if (checkResult != null && ("ALLOWED".equals(checkResult.getResultType()) || "BLOCK".equals(checkResult.getResultType()))) {
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

                    //中断程序,将错误消息返回前台
                    resultMap.put("message", promptService.getPromptDescription(request.getLocale(), checkResult.getMessage()));
                    resultMap.put("resultType", checkResult.getResultType());
                    return resultMap;
                }
            }
            // 单据状态检查
            submitDataValidCheck(request, expReportHeaderId);
            // 计划付款行核销状态检查[报销单是否存在未核销的借款]
            submitRepPmtSchedulesCheck(request, expReportHeaderId, header.getCompanyId());

            // 更新费用池引用状态(未)


            // --提交时，插入预置事件
            // exp_evt_pkg.fire_workflow_event(p_event_name => 'EXP_REPORT_SUBMIT',
            // p_document_id => p_exp_report_header_id,
            // p_document_line_id => null,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_REPORT_SUBMIT',
            // p_user_id => p_user_id);

            // 插历史记录
            expDocumentHistoryService.insertDocumentHistory(request, ExpReportHeader.EXP_REPORT, expReportHeaderId, ExpDocumentHistory.STATUS_SUBMIT, request.getEmployeeId(), null);

            //获取单据总支付金额
            BigDecimal paymentAmount = BigDecimal.ZERO;
            BigDecimal businessAmount = BigDecimal.ZERO;
            ExpReportLine lineDto = new ExpReportLine();
            lineDto.setExpReportHeaderId(expReportHeaderId);
            List<ExpReportLine> lineList = lineService.select(request, lineDto, 1, 0);
            if (lineList != null && !lineList.isEmpty()) {
                for (ExpReportLine line : lineList) {
                    paymentAmount = paymentAmount.add(line.getPaymentAmount());
                    businessAmount = businessAmount.add(line.getBusinessAmount());
                }
            }
            // 自审批标志判断
            if (reportType != null && "Y".equals(reportType.getAutoApproveFlag())) {
                // 更新头状态
                ExpReportHeader reportHeader = new ExpReportHeader();
                reportHeader.setExpReportHeaderId(expReportHeaderId);
                reportHeader.setReportStatus(ExpReportHeader.COMPLETELY_APPROVED);
                reportHeader.setPaymentAmount(paymentAmount);
                reportHeader.setBusinessAmount(businessAmount);
                self().updateByPrimaryKeySelective(request, reportHeader);
                // 更新行状态
                expReportLineMapper.updateStatus(ExpReportHeader.COMPLETELY_APPROVED, request.getUserId(), expReportHeaderId);
                // 更新分配行状态
                expReportDistMapper.updateStatus(ExpReportHeader.COMPLETELY_APPROVED, request.getUserId(), expReportHeaderId);
                // 插入历史记录
                expDocumentHistoryService.insertDocumentHistory(request, ExpReportHeader.EXP_REPORT, expReportHeaderId, ExpDocumentHistory.STATUS_APPROVE, request.getEmployeeId(), null);
                // --触发报销单审批通过事件（EXP_REPORT_COMPLETELY_APPROVED）
                // exp_evt_pkg.fire_workflow_event(p_event_name => 'EXP_REPORT_COMPLETELY_APPROVED',
                // p_document_id => p_exp_report_header_id,
                // p_document_line_id => null,
                // p_source_module => 'exp_report_successfully_approved',
                // p_event_type => 'put_document_into_pool',
                // p_user_id => p_user_id);
            } else {
                // 提交状态，启动工作流
                // 更新头状态
                ExpReportHeader reportHeader = new ExpReportHeader();
                reportHeader.setExpReportHeaderId(expReportHeaderId);
                reportHeader.setPaymentAmount(paymentAmount);
                reportHeader.setBusinessAmount(businessAmount);
                reportHeader.setReportStatus(ExpReportHeader.SUBMITTED);
                self().updateByPrimaryKeySelective(request, reportHeader);
                // 更新行状态
                expReportLineMapper.updateStatus(ExpReportHeader.SUBMITTED, request.getUserId(), expReportHeaderId);
                // 更新分配行状态
                expReportDistMapper.updateStatus(ExpReportHeader.SUBMITTED, request.getUserId(), expReportHeaderId);
                // 更新库存事务状态(未)

                // 调用工作流流程(未)

            }
            // 调整预算状态
            // 关联的申请单
            budgetReserveMapper.updateRequisitionRelease("P", request.getUserId(), expReportHeaderId);
            // 报销单
            budgetReserveMapper.updateForStatus(ExpReportHeader.EXP_REPORT, expReportHeaderId, "P");

            // 报销单关联发票预算调整(未)

            // 重置申请单预算
            resetReqBgtReserve(request, expReportHeaderId);
        }
        return resultMap;
    }


    private void expReqHeadToleranceCheck(IRequest request, Long expReportHeaderId) {
        BigDecimal reportAmount = BigDecimal.ZERO;
        BigDecimal reqAmount = BigDecimal.ZERO;
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(expReportHeaderId);
        header = self().selectByPrimaryKey(request, header);
        if (header.getExpRequisitionHeaderId() != null) {
            // 获取报销单整单金额
            ExpReportLine line = new ExpReportLine();
            line.setExpReportHeaderId(expReportHeaderId);
            List<ExpReportLine> lineList = expReportLineService.select(request, line, 1, 0);
            if (lineList != null && !lineList.isEmpty()) {
                for (ExpReportLine l : lineList) {
                    reportAmount.add(l.getPaymentAmount());
                }
            }
            // 获取申请单整单金额
            ExpRequisitionLine requisitionLine = new ExpRequisitionLine();
            requisitionLine.setExpRequisitionHeaderId(header.getExpRequisitionHeaderId());
            List<ExpRequisitionLine> requisitionLineList = requisitionLineService.select(request, requisitionLine, 1, 0);
            if (requisitionLineList != null && !requisitionLineList.isEmpty()) {
                for (ExpRequisitionLine rl : requisitionLineList) {
                    reqAmount.add(rl.getPaymentAmount());
                }
            }
        }
        if (reportAmount.compareTo(reqAmount) == 1) {
            throw new RuntimeException("报销单整单金额" + reportAmount + "超出申请单金额" + reqAmount + "!");
        }
    }

    private void submitDataValidCheck(IRequest request, Long expReportHeaderId) {
        BigDecimal reportLineAmount = BigDecimal.ZERO;
        BigDecimal distLineAmount = BigDecimal.ZERO;
        BigDecimal pmtSchedultAmount = BigDecimal.ZERO;
        // 获取报销单类型
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(expReportHeaderId);
        header = self().selectByPrimaryKey(request, header);
        ExpMoReportType reportType = new ExpMoReportType();
        reportType.setMoExpReportTypeId(header.getMoExpReportTypeId());
        // 获取分配行个数
        Long distCount = reportHeaderMapper.queryDistCount(expReportHeaderId);
        if (distCount == Long.valueOf(0)) {
            throw new ExpReportException("EXP", ExpReportException.EXP5110_EXP_REP_DISTS_NULL_ERROR, null);
        }
        // 查询整单金额(数量为正)
        ExpReportLine reportLine = new ExpReportLine();
        reportLine.setExpReportHeaderId(expReportHeaderId);
        List<ExpReportLine> reportLineList = expReportLineService.select(request, reportLine, 1, 0);
        if (reportLineList != null && !reportLineList.isEmpty()) {
            for (ExpReportLine line : reportLineList) {
                if (BigDecimal.ZERO.compareTo(line.getPrimaryQuantity()) == -1) {
                    reportLineAmount = reportLineAmount.add(line.getPaymentAmount());
                }
            }
        }
        // 查询分配行总金额
        distLineAmount = expReportDistMapper.queryPaymentAmountByHeaderId(expReportHeaderId);

        // 查询计划付款行总金额
        pmtSchedultAmount = expReportPmtScheduleMapper.totalAmount(expReportHeaderId);

        if ("Y".equals(reportType.getAdjustmentFlag()) && (BigDecimal.ZERO.compareTo(reportLineAmount) != 0 || BigDecimal.ZERO.compareTo(distLineAmount) != 0)) {
            throw new ExpReportException("EXP", ExpReportException.EXP5110_EXP_REP_ADJUSTMENT_ZERO_ERROR, null);
        }
        Long count = expReportLineMapper.checkDisAmount(expReportHeaderId);
        if (count > Long.valueOf(0)) {
            throw new ExpReportException("EXP", ExpReportException.EXP5110_EXP_REP_LINES_REPORT_AMOUNT_ERROR, null);
        }
        if (reportLineAmount.compareTo(pmtSchedultAmount) != 0) {
            throw new ExpReportException("EXP", ExpReportException.EXP5110_EXP_REP_PMT_CONTRACT_TOTAL_AMOUNT_ERR, null);
        }
    }

    private void submitRepPmtSchedulesCheck(IRequest request, Long expReportHeaderId, Long companyId) {
        // 获取相关系统参数
        String sysPara = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_EXP_REPORT_COMPLETELY_WRITE_OFF, null, null, companyId, null, null, null, null);
        if ("Y".equals(sysPara)) {
            // 获取计划付款行总金额
            BigDecimal pmtScheAmount = expReportPmtScheduleMapper.totalAmount(expReportHeaderId);
            // 获取核销总金额
            BigDecimal writeOffAmount = cshWriteOffMapper.totalDocAmount(expReportHeaderId, "EXPENSE_REPORT");

            if (BigDecimal.ZERO.compareTo(pmtScheAmount.subtract(writeOffAmount)) == -1) {
                // 找到当前计划付款行上的收款方的预付款总额
                // 减去已核销报销单且未被反冲
                // 减去当前预付款已被退款或反冲，且反向事物未被反冲的金额
                BigDecimal cshUnwriteOffAmt = cshWriteOffMapper.getCshUnWriteOffAmt(expReportHeaderId);
                if (BigDecimal.ZERO.compareTo(cshUnwriteOffAmt) == -1) {
                    throw new ExpReportException("EXP", ExpReportException.EXP5110_EXP_REP_CSH_WRITE_OFF_ASK_ERROR, null);
                }
            }
        }
        // 已核销借款金额是否>计划付款行到期金额
        // 获取计划付款行
        String error_flag = "N";
        int count = 0;
        Long lineNum = Long.valueOf(0);
        ExpReportPmtSchedule schedule = new ExpReportPmtSchedule();
        schedule.setExpReportHeaderId(expReportHeaderId);
        List<ExpReportPmtSchedule> scheduleList = expReportPmtScheduleMapper.select(schedule);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            for (ExpReportPmtSchedule pmtSchedule : scheduleList) {
                if (pmtSchedule.getAccountName() == null || pmtSchedule.getAccountNumber() == null) {
                    count++;
                }
                BigDecimal totWriteOffAmt = cshWriteOffMapper.getRepPmtTotWriteOffAmt(pmtSchedule.getExpReportHeaderId(), pmtSchedule.getPaymentScheduleLineId());

                if (pmtSchedule.getDueAmount().compareTo(totWriteOffAmt) == -1) {
                    error_flag = "Y";
                    lineNum = pmtSchedule.getScheduleLineNumber();
                    break;
                }
            }
        }
        if ("Y".equals(error_flag)) {
            String error_message = "报销单" + lineNum + "行中，存在计划付款行核销借款金额大于到期金额！";
            throw new RuntimeException(error_message);
        }
        // 校验计划付款行没有收款方账号不允许提交
        if (count > 0) {
            throw new ExpReportException("EXP", ExpReportException.EXP5110_EXP_REP_PMT_ACCOUNT_NULL_ERROR, null);
        }
    }

    private void resetReqBgtReserve(IRequest request, Long expReportHeaderId) {
        // 行关联，需要重置申请单对应的预算保留
        ExpRequisitionRelease requisitionRelease = new ExpRequisitionRelease();
        requisitionRelease.setDocumentType(ExpReportHeader.EXP_REPORT);
        requisitionRelease.setDocumentId(expReportHeaderId);
        List<ExpRequisitionRelease> requisitionReleases = requisitionReleaseService.select(request, requisitionRelease, 1, 0);
        if (requisitionReleases != null && !requisitionReleases.isEmpty()) {
            for (ExpRequisitionRelease release : requisitionReleases) {
                requisitionReleaseService.releasedBgtReserveBalance(request, release.getReleaseId());
            }
        }
        // 头关联，且当前单据状态为：收回、拒绝时，需打开关联的申请单
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(expReportHeaderId);
        header = self().selectByPrimaryKey(request, header);
        if (header != null && header.getExpRequisitionHeaderId() != null && ExpReportHeader.TAKEN_BACK.equals(header.getReportStatus()) && ExpReportHeader.REJECTED.equals(header.getReportStatus())) {
            requisitionReleaseService.openOneOffExpRequisition(request, header.getExpRequisitionHeaderId());
        }
    }

    /**
     * <p>根据状态条件锁表</p>
     *
     * @param request
     * @param expReportHeaderId 报销单头ID
     * @return
     * @author yang.duan 2019/5/8 10:10
     **/
    public boolean statusCheck(IRequest request, Long expReportHeaderId) {
        //锁表
        StringBuilder condition = new StringBuilder();
        condition.append("exp_report_header_id = ").append(expReportHeaderId);
        condition.append(" and report_status in ");
        condition.append("(" + "'" + ExpReportHeader.GENERATE + "'" + ",");
        condition.append("'" + ExpReportHeader.TAKEN_BACK + "'" + ",");
        condition.append("'" + ExpReportHeader.REJECTED + "'" + ")");
        databaseLockProvider.lock(new ExpReportHeader(), condition.toString(), null);
        return true;
    }


    /**
     * <p>单据行员工选择lov</p>
     *
     * @param request
     * @param companyId           公司ID
     * @param positionId          岗位ID
     * @param primaryPositionFlag 主岗标志
     * @param employeeCode        员工代码
     * @param employeeName        员工名称
     * @return List<Map>
     * @author yang.duan 2019/5/16 16:28
     **/
    @Override
    public List<Map> getEmployeeByCompanyId(IRequest request, Long companyId, Long positionId, String primaryPositionFlag, String employeeCode, String employeeName) {
        List<Map> employeeMapList = new ArrayList<>();
        List<ExpEmployeeAssign> employeeList = reportHeaderMapper.getEmployeeByCompanyId(companyId, positionId, primaryPositionFlag, employeeCode, employeeName);
        if (employeeList != null && !employeeList.isEmpty()) {
            for (ExpEmployeeAssign employeeAssign : employeeList) {
                setGldOrg(request, companyId, employeeAssign);
                setBgtOrg(request, companyId, employeeAssign);
                employeeMapList.add(BeanUtil.convert2Map(employeeAssign));
            }
        }
        return employeeMapList;
    }

    private void setGldOrg(IRequest request, Long companyId, ExpEmployeeAssign employeeAssign) {
        if (employeeAssign != null) {
            if (employeeAssign.getAccEntityId() == null) {
                //获取公司默认的核算主体
                GldAccountingEntity accountingEntity = accountingEntityService.queryDefaultAccEntity(request, companyId);
                if (accountingEntity != null) {
                    employeeAssign.setAccEntityId(accountingEntity.getAccEntityId());
                    employeeAssign.setAccEntityName(accountingEntity.getAccEntityName());
                }
            }
            //获取成本中心
            ExpEmployeeAssign respCenterDto = new ExpEmployeeAssign();
            respCenterDto.setEmployeeId(employeeAssign.getEmployeeId());
            respCenterDto.setCompanyId(companyId);
            respCenterDto.setPositionId(employeeAssign.getPositionId());
            respCenterDto.setAccEntityId(employeeAssign.getAccEntityId());
            respCenterDto.setEnabledFlag("Y");
            List<ExpEmployeeAssign> respCenterDtoList = expEmployeeAssignService.select(request, respCenterDto, 1, 0);
            if (respCenterDtoList != null && !respCenterDtoList.isEmpty()) {
                respCenterDto = respCenterDtoList.get(0);
            }
            if (respCenterDto != null && respCenterDto.getResponsibilityCenterId() != null) {
                GldResponsibilityCenter responsibilityCenter = new GldResponsibilityCenter();
                responsibilityCenter.setResponsibilityCenterId(respCenterDto.getResponsibilityCenterId());
                responsibilityCenter = gldResponsibilityCenterService.selectByPrimaryKey(request, responsibilityCenter);
                employeeAssign.setResponsibilityCenterId(responsibilityCenter.getResponsibilityCenterId());
                employeeAssign.setResponsibilityCenterName(responsibilityCenter.getResponsibilityCenterName());
            } else {
                //获取部门默认的成本中心
                GldResponsibilityCenter responsibilityCenter = gldResponsibilityCenterService.getDefaultRespCenter(employeeAssign.getUnitId(), employeeAssign.getAccEntityId());
                if (responsibilityCenter != null) {
                    employeeAssign.setResponsibilityCenterId(responsibilityCenter.getParentRespCenterId());
                    employeeAssign.setResponsibilityCenterName(responsibilityCenter.getResponsibilityCenterName());
                }
            }
        }
    }

    private void setBgtOrg(IRequest request, Long companyId, ExpEmployeeAssign employeeAssign) {
        if (employeeAssign != null) {
            //获取员工分配的预算实体
            ExpEmployeeAssign BgtEntityDto = new ExpEmployeeAssign();
            BgtEntityDto.setEmployeeId(employeeAssign.getEmployeeId());
            BgtEntityDto.setCompanyId(companyId);
            BgtEntityDto.setPositionId(employeeAssign.getPositionId());
            BgtEntityDto.setEnabledFlag("Y");
            Criteria empAssCri = new Criteria(BgtEntityDto);
            empAssCri.where(new WhereField(ExpEmployeeAssign.FIELD_EMPLOYEE_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_COMPANY_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_POSITION_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_ENABLED_FLAG, Comparison.EQUAL));
            List<ExpEmployeeAssign> employeeAssignList = expEmployeeAssignService.selectOptions(request, BgtEntityDto, empAssCri);
            if (employeeAssignList != null && !employeeAssignList.isEmpty()) {
                BgtEntityDto = employeeAssignList.get(0);
            }
            if (BgtEntityDto.getEntityId() != null) {
                employeeAssign.setEntityId(BgtEntityDto.getEntityId());
            } else {
                // 核算主体+公司获取预算实体
                FndCompanyRefAccBe companyRefAccBe = fndCompanyRefAccBeService.getBgtEntityByComAndAcc(request, companyId, employeeAssign.getAccEntityId());
                if (companyRefAccBe != null && companyRefAccBe.getBgtEntityId() != null) {
                    employeeAssign.setEntityId(companyRefAccBe.getBgtEntityId());
                } else {
                    // 获取公司默认的预算实体
                    FndCompanyRefBgtEntity companyRefBgtEntity = fndCompanyRefBgtEntityService.getDftBgtEntity(request, companyId);
                    if (companyRefBgtEntity != null && companyRefBgtEntity.getEntityId() != null) {
                        employeeAssign.setEntityId(companyRefBgtEntity.getEntityId());
                    }
                }
            }

            // 设置默认预算中心
            // 获取员工分配的预算中心
            ExpEmployeeAssign bgtCenterAssDto = new ExpEmployeeAssign();
            BgtCenter bgtCenter = new BgtCenter();
            bgtCenterAssDto.setEmployeeId(employeeAssign.getEmployeeId());
            bgtCenterAssDto.setCompanyId(companyId);
            bgtCenterAssDto.setPositionId(employeeAssign.getPositionId());
            bgtCenterAssDto.setEntityId(employeeAssign.getEntityId());
            bgtCenterAssDto.setEnabledFlag("Y");
            bgtCenterAssDto = expEmployeeAssignMapper.selectOne(bgtCenterAssDto);
            if (bgtCenterAssDto != null && bgtCenterAssDto.getCenterId() != null) {
                if (bgtCenter != null) {
                    employeeAssign.setCenterId(bgtCenterAssDto.getCenterId());
                }
            } else {
                // 部门ID+核算主体ID+成本中心ID+预算实体ID
                ExpOrgUnitRefAccOrg unitRefAccOrg = new ExpOrgUnitRefAccOrg();
                unitRefAccOrg.setUnitId(employeeAssign.getUnitId());
                unitRefAccOrg.setAccEntityId(employeeAssign.getAccEntityId());
                unitRefAccOrg.setRespCenterId(employeeAssign.getResponsibilityCenterId());
                unitRefAccOrg.setBgtEntityId(employeeAssign.getEntityId());
                unitRefAccOrg.setEnabledFlag("Y");
                unitRefAccOrg.setDefaultFlag("Y");
                List<ExpOrgUnitRefAccOrg> unitRefAccOrgList = refAccOrgService.select(request, unitRefAccOrg, 1, 0);
                if (unitRefAccOrgList != null && !unitRefAccOrgList.isEmpty()) {
                    unitRefAccOrg = unitRefAccOrgList.get(0);
                }
                if (unitRefAccOrg != null && unitRefAccOrg.getBgtCenterId() != null) {
                    employeeAssign.setCenterId(unitRefAccOrg.getBgtCenterId());
                } else {
                    // 核算主体ID+预算实体ID
                    GldRespCenterRefBc gldRespCenterRefBc = new GldRespCenterRefBc();
                    gldRespCenterRefBc.setRespCenterId(employeeAssign.getResponsibilityCenterId());
                    gldRespCenterRefBc.setBgtEntityId(employeeAssign.getEntityId());
                    gldRespCenterRefBc.setEnabledFlag("Y");
                    gldRespCenterRefBc.setDefaultFlag("Y");
                    List<GldRespCenterRefBc> gldRespCenterRefBcList = gldRespCenterRefBcService.select(request, gldRespCenterRefBc, 1, 0);
                    if (gldRespCenterRefBcList != null && !gldRespCenterRefBcList.isEmpty()) {
                        gldRespCenterRefBc = gldRespCenterRefBcList.get(0);
                    }
                    if (gldRespCenterRefBc != null && gldRespCenterRefBc.getBgtCenterId() != null) {
                        employeeAssign.setCenterId(gldRespCenterRefBc.getBgtCenterId());
                    } else {
                        // 部门ID+预算实体ID
                        ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                        expOrgUnitRefBgtOrg.setUnitId(employeeAssign.getUnitId());
                        expOrgUnitRefBgtOrg.setBgtEntityId(employeeAssign.getEntityId());
                        expOrgUnitRefBgtOrg.setEnabledFlag("Y");
                        expOrgUnitRefBgtOrg.setDefaultFlag("Y");
                        List<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgList = refBgtOrgService.select(request, expOrgUnitRefBgtOrg, 1, 0);
                        if (expOrgUnitRefBgtOrgList != null && !expOrgUnitRefBgtOrgList.isEmpty()) {
                            expOrgUnitRefBgtOrg = expOrgUnitRefBgtOrgList.get(0);
                        }
                        if (expOrgUnitRefBgtOrg != null && expOrgUnitRefBgtOrg.getBgtCenterId() != null) {
                            employeeAssign.setCenterId(expOrgUnitRefBgtOrg.getBgtCenterId());
                        }
                    }
                }
            }
        }
    }

    private BgtCheckResult expReportBgtCheck(IRequest request, Long expReportHeaderId, String bgtIgnoreWarningFlag, String oppositeOperation, String noCheckChannel) {
        BgtCheckResult bgtCheckResult = new BgtCheckResult();
        BigDecimal numberFactor = BigDecimal.ONE;
        //获取是否启用缓存预算计算
        String isUseCache = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_BGT_CHECK_WITH_CACHE, null, null, null, null, null, null, null);
        if (!"Y".equals(isUseCache)) {
            if (("Y".equals(oppositeOperation)) || ("Y".equals(noCheckChannel))) {
                return null;
            }
        }
        //报销单类型定义中增加模板类标志,模板类报销单相关逻辑修改
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(expReportHeaderId);
        header = self().selectByPrimaryKey(request, header);
        ExpMoReportType reportType = new ExpMoReportType();
        reportType.setMoExpReportTypeId(header.getMoExpReportTypeId());
        reportType = reportTypeService.selectByPrimaryKey(request, reportType);

        if (reportType != null && "Y".equals(reportType.getTemplateFlag())) {
            return null;
        }
        //更新费用申请对应预算占用状态(未)

        if ("Y".equals(oppositeOperation)) {
            numberFactor = BigDecimal.ONE.negate();
        }

        //查询报销单头(锁表)
        StringBuilder headerCondition = new StringBuilder();
        headerCondition.append("exp_report_header_id = ").append(expReportHeaderId);
        databaseLockProvider.lock(new ExpReportHeader(), headerCondition.toString(), null);
        ExpReportHeader reportHeader = new ExpReportHeader();
        reportHeader.setExpReportHeaderId(expReportHeaderId);
        reportHeader = self().selectByPrimaryKey(request, header);

        if (reportHeader != null) {
            //新增运行参数(未)

            //删除预算检查日志
            BgtCheckLog checkLog = new BgtCheckLog();
            checkLog.setDocumentId(expReportHeaderId);
            checkLog.setDocumentType(ExpReportHeader.EXP_REPORT);
            List<BgtCheckLog> checkLogList = bgtCheckLogService.select(request, checkLog, 1, 0);
            if (checkLogList != null && !checkLogList.isEmpty()) {
                bgtCheckLogService.batchDelete(checkLogList);
            }

            //删除事件记录表(未)

            //获取系统参数(BGT_CONTROL_ENABLED)
            String bgtControlEnabled = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_BGT_CONTROL_ENABLED, null, null, reportHeader.getCompanyId(), null, null, null, null);
            if (bgtControlEnabled == null || "N".equals(bgtControlEnabled)) {
                return null;
            }
            //获取报销单行(锁表)
            StringBuilder lineCondition = new StringBuilder();
            headerCondition.append("exp_report_header_id = ").append(expReportHeaderId);
            databaseLockProvider.lock(new ExpReportLine(), lineCondition.toString(), null);
            ExpReportLine reportLine = new ExpReportLine();
            reportLine.setExpReportHeaderId(expReportHeaderId);
            List<ExpReportLine> reportLineList = lineService.select(request, reportLine, 1, 0);

            if (reportLineList != null && !reportLineList.isEmpty()) {
                for (ExpReportLine line : reportLineList) {
                    //获取预算保留行(锁表)
                    String tableName = "exp_report_dist t,bgt_budget_reserve r";
                    StringBuilder distCondition = new StringBuilder();
                    distCondition.append(" t.exp_report_line_id = ").append(line.getExpReportLineId()).append(" and r.document_line_id = t.exp_report_dist_id").append(" and r.business_type = ").append("'" + ExpReportHeader.EXP_REPORT + "'").append(" and r.status = ").append("'" + "N" + "'");
                    databaseLockProvider.lock(tableName, distCondition.toString(), null);
                    List<BgtBudgetReserve> budgetReserveList = budgetReserveMapper.getBgtReserveByLineId(line.getExpReportLineId(), "N");
                    if (budgetReserveList != null && !budgetReserveList.isEmpty()) {
                        for (BgtBudgetReserve reserve : budgetReserveList) {
                            //调用预算校验核心逻辑
                            bgtCheckResult = bgtCheckService.check(reserve, bgtIgnoreWarningFlag, request);
                            if (!bgtCheckResult.getIsPassed()) {
                                break;
                            } else {
                                if ("Y".equals(oppositeOperation)) {
                                    reserve.setStatus("N");
                                } else {
                                    reserve.setStatus("P");
                                }
                                bgtBudgetReserveService.updateByPrimaryKeySelective(request, reserve);
                            }
                        }
                    }
                }
            }

        }
        //删除运行参数(未)

        return bgtCheckResult;
    }
}
