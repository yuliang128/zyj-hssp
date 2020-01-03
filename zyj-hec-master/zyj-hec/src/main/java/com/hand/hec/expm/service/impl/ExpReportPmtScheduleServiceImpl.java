package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoPayUsdRefFlowIt;
import com.hand.hec.csh.dto.CshWriteOff;
import com.hand.hec.csh.mapper.CshMoPayUsdRefFlowItMapper;
import com.hand.hec.csh.mapper.CshWriteOffMapper;
import com.hand.hec.csh.service.ICshDocPayAccEntityService;
import com.hand.hec.csh.service.ICshWriteOffService;
import com.hand.hec.exp.dto.ExpEmployeeAccount;
import com.hand.hec.exp.dto.ExpMoRepTypeRefPayUd;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.mapper.ExpEmployeeAccountMapper;
import com.hand.hec.exp.mapper.ExpMoRepTypeRefPayUdMapper;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.dto.ExpReportPmtSchTaxLine;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.exception.ExpReportException;
import com.hand.hec.expm.mapper.ExpReportLineMapper;
import com.hand.hec.expm.mapper.ExpReportPmtSchTaxLineMapper;
import com.hand.hec.expm.mapper.ExpReportPmtScheduleMapper;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.expm.service.IExpReportPmtSchTaxLineService;
import com.hand.hec.expm.service.IExpReportPmtScheduleService;
import com.hand.hec.fnd.dto.OrdSystemCustomer;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.fnd.service.IOrdSystemCustomerService;
import com.hand.hec.gld.dto.GldAccEntityRefSob;
import com.hand.hec.gld.mapper.GldAccEntityRefSobMapper;
import com.hand.hec.pur.dto.PurSystemVender;
import com.hand.hec.pur.service.IPurSystemVenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExpReportPmtScheduleServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportPmtScheduleServiceImpl extends BaseServiceImpl<ExpReportPmtSchedule> implements IExpReportPmtScheduleService {

    @Autowired
    ExpReportPmtScheduleMapper expReportPmtScheduleMapper;

    @Autowired
    ExpReportLineMapper expReportLineMapper;

    @Autowired
    CshMoPayUsdRefFlowItMapper cshMoPayUsdRefFlowItMapper;

    @Autowired
    CshWriteOffMapper cshWriteOffMapper;

    @Autowired
    ExpEmployeeAccountMapper expEmployeeAccountMapper;

    @Autowired
    ExpReportPmtSchTaxLineMapper expReportPmtSchTaxLineMapper;

    @Autowired
    ExpMoRepTypeRefPayUdMapper expMoRepTypeRefPayUdMapper;

    @Autowired
    GldAccEntityRefSobMapper gldAccEntityRefSobMapper;


    @Autowired
    IExpReportHeaderService expReportHeaderService;

    @Autowired
    IExpReportPmtSchTaxLineService expReportPmtSchTaxLineService;


    @Autowired
    ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Autowired
    ICshWriteOffService cshWriteOffService;

    @Autowired
    IExpEmployeeService employeeService;

    @Autowired
    IPurSystemVenderService purSystemVenderService;

    @Autowired
    IOrdSystemCustomerService ordSystemCustomerService;

    @Autowired
    private IGldPeriodService gldPeriodService;


    /**
     * <p>
     * 自动创建计划付款行
     * <p/>
     *
     * @param request    请求
     * @param header     报销单头
     * @param lineList   报销单行list
     * @param reportType 报销单类型
     * @return 生成的计划付款行DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 13:54
     */
    @Override
    public ExpReportPmtSchedule createExpRepPmtSchedule(IRequest request, ExpReportHeader header, List<ExpReportLine> lineList, ExpMoReportType reportType) throws RuntimeException {
        ExpReportPmtSchedule reportPmtSchedule = new ExpReportPmtSchedule();
        BigDecimal totalPayAmount = new BigDecimal(0);
        // 获取报销单支付总金额
        ExpReportLine line = new ExpReportLine();
        line.setExpReportHeaderId(header.getExpReportHeaderId());
        List<ExpReportLine> reportLines = new ArrayList<>();
        reportLines = expReportLineMapper.select(line);
        List<Long> idList = new ArrayList<>();
        if (lineList != null && !lineList.isEmpty()) {
            if ("STOCK_LINE".equals(expReportLineMapper.getReportPageElementCode(header.getExpReportHeaderId())) || "STOCK_PS_LINE".equals(expReportLineMapper.getReportPageElementCode(header.getExpReportHeaderId())) || "STOCK_CR_LINE".equals(expReportLineMapper.getReportPageElementCode(header.getExpReportHeaderId()))) {

                for (ExpReportLine stockLine : lineList) {
                    idList.add(stockLine.getExpReportLineId());
                    totalPayAmount = totalPayAmount.add(stockLine.getPaymentAmount().compareTo(BigDecimal.ZERO) == 1 ? stockLine.getPaymentAmount() : BigDecimal.ZERO);
                }
            } else {
                if (lineList != null && !lineList.isEmpty()) {
                    for (ExpReportLine l : lineList) {
                        idList.add(l.getExpReportLineId());
                        totalPayAmount = totalPayAmount.add(l.getPaymentAmount());
                    }
                }
            }
        } else {
            for (ExpReportLine line1 : reportLines) {
                totalPayAmount = totalPayAmount.add(line1.getPaymentAmount());
            }
        }
        if (reportLines != null && !reportLines.isEmpty()) {
            for (ExpReportLine line1 : reportLines) {
                if (!idList.isEmpty() && !idList.contains(line1.getExpReportLineId())) {
                    totalPayAmount = totalPayAmount.add(line1.getPaymentAmount());
                }
            }
        }
        // 获取报销单类型关联的默认付款用途
        ExpMoRepTypeRefPayUd typeRefPayUd = new ExpMoRepTypeRefPayUd();
        typeRefPayUd.setMoExpReportTypeId(header.getMoExpReportTypeId());
        List<ExpMoRepTypeRefPayUd> typeRefPayUdList = expMoRepTypeRefPayUdMapper.select(typeRefPayUd);
        if (typeRefPayUdList != null && !typeRefPayUdList.isEmpty()) {
            for (ExpMoRepTypeRefPayUd ud : typeRefPayUdList) {
                if ("Y".equals(ud.getDefaultFlag()) && "Y".equals(ud.getEnabledFlag())) {
                    typeRefPayUd.setUsedesId(ud.getUsedesId());
                    break;
                }
            }
        }
        // 获取报销单头核算主体对应的默认账套
        GldAccEntityRefSob gldAccEntityRefSob = new GldAccEntityRefSob();
        gldAccEntityRefSob.setAccEntityId(header.getAccEntityId());
        List<GldAccEntityRefSob> gldAccEntityRefSobList = gldAccEntityRefSobMapper.select(gldAccEntityRefSob);
        if (gldAccEntityRefSobList != null && !gldAccEntityRefSobList.isEmpty()) {
            for (GldAccEntityRefSob refSob : gldAccEntityRefSobList) {
                if ("Y".equals(refSob.getDefaultFlag())) {
                    gldAccEntityRefSob.setSetOfBooksId(refSob.getSetOfBooksId());
                    break;
                }
            }
        }
        // 获取现金流量项
        CshMoPayUsdRefFlowIt cshMoPayUsdRefFlowIt = new CshMoPayUsdRefFlowIt();
        cshMoPayUsdRefFlowIt.setPaymentUsedeId(typeRefPayUd.getUsedesId());
        cshMoPayUsdRefFlowIt.setSetOfBooksId(gldAccEntityRefSob.getSetOfBooksId());
        List<CshMoPayUsdRefFlowIt> cshMoPayUsdRefFlowItList = cshMoPayUsdRefFlowItMapper.queryIncludeSetOfBooks(cshMoPayUsdRefFlowIt);
        if (cshMoPayUsdRefFlowItList != null && !cshMoPayUsdRefFlowItList.isEmpty()) {
            for (CshMoPayUsdRefFlowIt it : cshMoPayUsdRefFlowItList) {
                if ("Y".equals(it.getEnabledFlag()) && "Y".equals(it.getDefaultFlag())) {
                    cshMoPayUsdRefFlowIt.setCashFlowItemId(it.getCashFlowItemId());
                }
            }
        }
        // 调整类报销单无需生成计划付款行
        if ("Y".equals(reportType.getAdjustmentFlag())) {
            return null;
        }


        ExpReportPmtSchedule pmtSchedule = new ExpReportPmtSchedule();
        pmtSchedule.setExpReportHeaderId(header.getExpReportHeaderId());

        List<ExpReportPmtSchedule> pmtScheduleList = expReportPmtScheduleMapper.select(pmtSchedule);
        ExpEmployeeAccount employeeAccount = new ExpEmployeeAccount();
        // 如果收款方类型为EMPLOYEE,查询收款方银行账户信息
        if ("EMPLOYEE".equals(header.getPayeeCategory())) {
            employeeAccount.setEmployeeId(header.getPayeeId());
            employeeAccount.setPrimaryFlag("Y");
            employeeAccount.setEnabledFlag("Y");
            employeeAccount = expEmployeeAccountMapper.selectOne(employeeAccount);
        }
        // 前置数据不全(默认付款用途/默认账套/默认现金流量项/收款方账户信息),无法生成计划付款行
        if (typeRefPayUd.getUsedesId() == null || gldAccEntityRefSob.getSetOfBooksId() == null || cshMoPayUsdRefFlowIt.getCashFlowItemId() == null || employeeAccount == null) {
            return null;
        }

        if (pmtScheduleList != null && pmtScheduleList.isEmpty()) {
            reportPmtSchedule.setExpReportHeaderId(header.getExpReportHeaderId());
            reportPmtSchedule.setScheduleLineNumber(Long.valueOf(10));
            reportPmtSchedule.setDescription(header.getDescription());
            reportPmtSchedule.setPaymentCurrencyCode(header.getPaymentCurrencyCode());
            reportPmtSchedule.setPayeeCategory(header.getPayeeCategory());
            reportPmtSchedule.setPayeeId(header.getPayeeId());
            reportPmtSchedule.setScheduleStartDate(DateUtils.getCurrentDate());
            reportPmtSchedule.setScheduleDueDate(DateUtils.getCurrentDate());
            reportPmtSchedule.setCompanyId(header.getCompanyId());
            reportPmtSchedule.setAccEntityId(header.getAccEntityId());
            reportPmtSchedule.setPaymentMethodId(header.getPaymentMethodId());
            reportPmtSchedule.setPaymentUsedeId(typeRefPayUd.getUsedesId());
            reportPmtSchedule.setCashFlowItemId(cshMoPayUsdRefFlowIt.getCashFlowItemId());
            reportPmtSchedule.setDueAmount(totalPayAmount);
            reportPmtSchedule.setFrozenFlag("N");
            reportPmtSchedule.setAccountName(employeeAccount.getAccountName());
            reportPmtSchedule.setAccountNumber(employeeAccount.getAccountNumber());
            reportPmtSchedule.setBankCode(employeeAccount.getBankCode());
            reportPmtSchedule.setBankName(employeeAccount.getBankName());
            reportPmtSchedule.setBankLocationCode(employeeAccount.getBankLocationCode());
            reportPmtSchedule.setBankLocationName(employeeAccount.getBankLocation());
            reportPmtSchedule.setProvinceCode(employeeAccount.getProvinceCode());
            reportPmtSchedule.setProvinceName(employeeAccount.getProvinceName());
            reportPmtSchedule.setCityCode(employeeAccount.getCityCode());
            reportPmtSchedule.setCityName(employeeAccount.getCityName());
            // 当前没有计划付款行,自动创建
            reportPmtSchedule = insertExpRepPmtSchedule(request, reportPmtSchedule, header, reportType);
        } else if (pmtScheduleList.size() == 1) {
            reportPmtSchedule.setPaymentScheduleLineId(pmtScheduleList.get(0).getPaymentScheduleLineId());
            reportPmtSchedule.setPaymentCurrencyCode(header.getPaymentCurrencyCode());
            reportPmtSchedule.setDueAmount(totalPayAmount.compareTo(BigDecimal.ZERO) == 0 ? reportPmtSchedule.getDueAmount() : totalPayAmount);
            reportPmtSchedule.setLastUpdatedBy(request.getUserId());
            reportPmtSchedule.setLastUpdateDate(new Date());
            // 当前有一条计划付款行，自动更新
            reportPmtSchedule = this.updateByPrimaryKeySelective(request, reportPmtSchedule);
        }
        // 重置核销状态
        resetExpRptWriteOffFlag(request, header.getExpReportHeaderId(), lineList);
        return reportPmtSchedule;
    }

    // 重置核销状态
    private void resetExpRptWriteOffFlag(IRequest request, Long reportHeaderId, List<ExpReportLine> lineList) {
        String writeOffFlag = null;
        // 计算支付总金额
        BigDecimal totalPayAmount = new BigDecimal(0);
//        if (lineList != null && !lineList.isEmpty()) {
//            for (ExpReportLine line : lineList) {
//                totalPayAmount = totalPayAmount.add(line.getPaymentAmount());
//            }
//        }
        // 获取报销单总付款金额
        totalPayAmount = expReportLineMapper.getTotalPaymentAmount(reportHeaderId);
        // 获取报销单核销总金额
        BigDecimal reportWriteOffAmount = cshWriteOffMapper.totalDocAmount(reportHeaderId, "EXPENSE_REPORT");
        // 设置核销状态
        if (reportWriteOffAmount.compareTo(BigDecimal.ZERO) == 1) {
            if (totalPayAmount.subtract(reportWriteOffAmount).compareTo(BigDecimal.ZERO) == 1) {
                writeOffFlag = "Y";
            } else if (totalPayAmount.subtract(reportWriteOffAmount).compareTo(BigDecimal.ZERO) == 0 || totalPayAmount.subtract(reportWriteOffAmount).compareTo(BigDecimal.ZERO) == -1) {
                writeOffFlag = "C";
            } else {
                writeOffFlag = "N";
            }
        }
        ExpReportHeader header = new ExpReportHeader();
        header.setExpReportHeaderId(reportHeaderId);
        header.setWriteOffStatus(writeOffFlag);
        // 更新报销单头核销状态字段
        expReportHeaderService.updateByPrimaryKeySelective(request, header);
    }

    /**
     * <p>
     * 新增计划付款行
     * <p/>
     *
     * @param request              请求
     * @param expReportPmtSchedule 需要新增的计划付款行DTO
     * @param header               报销单头信息
     * @param reportType           报销单类型
     * @return 新增后的计划付款行DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 17:16
     */
    public ExpReportPmtSchedule insertExpRepPmtSchedule(IRequest request, ExpReportPmtSchedule expReportPmtSchedule, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException {
        if (expReportPmtSchedule.getPaymentUsedeId() != null && expReportPmtSchedule.getCashFlowItemId() == null) {
            // 获取现金流量项ID(exp_util_pkg.get_cash_flow_item_id)
            Long cashFlowItemId = cshMoPayUsdRefFlowItMapper.getCashFlowItemId(expReportPmtSchedule.getPaymentUsedeId(), expReportPmtSchedule.getAccEntityId());
            expReportPmtSchedule.setCashFlowItemId(cashFlowItemId);
        }
        expReportPmtSchedule.setAccEntityId(header.getAccEntityId());
        expReportPmtSchedule.setCreationDate(new Date());
        expReportPmtSchedule.setCreatedBy(request.getUserId());
        expReportPmtSchedule.setLastUpdateDate(new Date());
        expReportPmtSchedule.setLastUpdatedBy(request.getUserId());
        // 插入计划付款行
        expReportPmtSchedule = self().insertSelective(request, expReportPmtSchedule);

        // 如果新增税种,将数据同步更新到税种行表
        if (expReportPmtSchedule.getTaxTypeId() != null || expReportPmtSchedule.getTaxAmount() != null) {
            ExpReportPmtSchTaxLine reportPmtSchTaxLine = new ExpReportPmtSchTaxLine();
            reportPmtSchTaxLine.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
            reportPmtSchTaxLine.setTaxRate(expReportPmtSchedule.getTaxRate());
            reportPmtSchTaxLine.setTaxTypeId(expReportPmtSchedule.getTaxTypeId());
            reportPmtSchTaxLine.setTaxAmount(reportPmtSchTaxLine.getTaxAmount());
            reportPmtSchTaxLine.setCreationDate(new Date());
            reportPmtSchTaxLine.setCreatedBy(request.getUserId());
            reportPmtSchTaxLine.setLastUpdateDate(new Date());
            reportPmtSchTaxLine.setLastUpdatedBy(request.getUserId());
            expReportPmtSchTaxLineService.insertSelective(request, reportPmtSchTaxLine);
        }
        // 创建单据支付主体记录
        cshDocPayAccEntityService.createPayAccEntity(request, ExpReportHeader.EXP_REPORT, header.getCompanyId(), header.getMoExpReportTypeId(), header.getExpReportHeaderId(), expReportPmtSchedule.getPaymentScheduleLineId(), header.getAccEntityId(), expReportPmtSchedule.getPaymentMethodId(), expReportPmtSchedule.getPayeeCategory(), reportType.getPaymentFlag(), expReportPmtSchedule.getFrozenFlag());
        return expReportPmtSchedule;
    }


    /**
     * <p>
     * 更新计划付款行
     * <p/>
     *
     * @param request              请求
     * @param expReportPmtSchedule 需要更新的计划付款行DTO
     * @param header               报销单头信息
     * @param reportType           报销单类型
     * @return 更新后的计划付款行DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 17:16
     */
    @Override
    public ExpReportPmtSchedule updateExpRepPmtSchedule(IRequest request, ExpReportPmtSchedule expReportPmtSchedule, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException {
        // 获取计划付款行信息
        ExpReportPmtSchedule reportPmtSchedule = new ExpReportPmtSchedule();
        reportPmtSchedule.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
        reportPmtSchedule = self().selectByPrimaryKey(request, reportPmtSchedule);
        if (expReportHeaderService.statusCheck(request, header.getExpReportHeaderId())) {
            // 如果计划付款行发生核销,收款方信息不允许改变
            Long count = self().checkPmtScheduleLnOccurWf(expReportPmtSchedule.getPaymentScheduleLineId());
            if (count > 0) {
                if (!reportPmtSchedule.getPayeeCategory().equals(expReportPmtSchedule.getPayeeCategory()) || !reportPmtSchedule.getPayeeId().equals(expReportPmtSchedule.getPayeeId())) {
                    throw new ExpReportException("EXP",ExpReportException.EXP5110_PMT_SCHEDULE_LN_OCCUR_WF_ERROR,null);
                }
            }
            if (expReportPmtSchedule.getPaymentUsedeId() != null && expReportPmtSchedule.getCashFlowItemId() != null) {
                Long cashFlowItemId = cshMoPayUsdRefFlowItMapper.getCashFlowItemId(expReportPmtSchedule.getPaymentUsedeId(), expReportPmtSchedule.getAccEntityId());
                expReportPmtSchedule.setCashFlowItemId(cashFlowItemId);
            }
            expReportPmtSchedule.setLastUpdateDate(new Date());
            expReportPmtSchedule.setLastUpdatedBy(request.getUserId());
            // 更新计划付款行
            expReportPmtSchedule = self().updateByPrimaryKeySelective(request, expReportPmtSchedule);
            ExpReportPmtSchTaxLine pmtSchTaxLine = new ExpReportPmtSchTaxLine();
            pmtSchTaxLine.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
            List<ExpReportPmtSchTaxLine> pmtSchTaxLineList = expReportPmtSchTaxLineMapper.select(pmtSchTaxLine);
            // 当更多税种只有一行时才更新
            if (pmtSchTaxLineList != null && pmtSchTaxLineList.size() == 1) {
                if (expReportPmtSchedule.getTaxTypeId() == null && expReportPmtSchedule.getTaxAmount() == null) {
                    // 删除税种信息
                    expReportPmtSchTaxLineMapper.deleteByPrimaryKey(pmtSchTaxLineList.get(0).getTaxLineId());
                } else {
                    pmtSchTaxLine.setTaxLineId(pmtSchTaxLineList.get(0).getTaxLineId());
                    pmtSchTaxLine.setTaxTypeId(expReportPmtSchedule.getTaxTypeId());
                    pmtSchTaxLine.setTaxRate(expReportPmtSchedule.getTaxRate());
                    pmtSchTaxLine.setTaxAmount(expReportPmtSchedule.getTaxAmount());
                    pmtSchTaxLine.setLastUpdateDate(new Date());
                    pmtSchTaxLine.setLastUpdatedBy(request.getUserId());
                    // 更新更多税种
                    expReportPmtSchTaxLineMapper.updateByPrimaryKeySelective(pmtSchTaxLine);
                }
            } else if (pmtSchTaxLineList != null && pmtSchTaxLineList.isEmpty()) {
                if (expReportPmtSchedule.getTaxTypeId() != null || expReportPmtSchedule.getTaxAmount() != null) {
                    // 插入更多税种
                    ExpReportPmtSchTaxLine reportPmtSchTaxLine = new ExpReportPmtSchTaxLine();
                    reportPmtSchTaxLine.setPaymentScheduleLineId(expReportPmtSchedule.getPaymentScheduleLineId());
                    reportPmtSchTaxLine.setTaxRate(expReportPmtSchedule.getTaxRate());
                    reportPmtSchTaxLine.setTaxTypeId(expReportPmtSchedule.getTaxTypeId());
                    reportPmtSchTaxLine.setTaxAmount(expReportPmtSchedule.getTaxAmount());
                    reportPmtSchTaxLine.setCreationDate(new Date());
                    reportPmtSchTaxLine.setCreatedBy(request.getUserId());
                    reportPmtSchTaxLine.setLastUpdateDate(new Date());
                    reportPmtSchTaxLine.setLastUpdatedBy(request.getUserId());
                    expReportPmtSchTaxLineService.insertSelective(request, reportPmtSchTaxLine);
                }
            }
            // 删除单据支付主体记录
            cshDocPayAccEntityService.deletePayAccEntity(request, ExpReportHeader.EXP_REPORT, expReportPmtSchedule.getExpReportHeaderId(), expReportPmtSchedule.getPaymentScheduleLineId());
            // 创建单据支付主体记录
            cshDocPayAccEntityService.createPayAccEntity(request, ExpReportHeader.EXP_REPORT, expReportPmtSchedule.getCompanyId(), reportType.getMoExpReportTypeId(), expReportPmtSchedule.getExpReportHeaderId(), expReportPmtSchedule.getPaymentScheduleLineId(), header.getAccEntityId(), expReportPmtSchedule.getPaymentMethodId(), expReportPmtSchedule.getPayeeCategory(), reportType.getPaymentFlag(), expReportPmtSchedule.getFrozenFlag());
        }
        return expReportPmtSchedule;
    }

    /**
     * <p>
     * 检查计划付款行是否发生过核销
     * <p/>
     *
     * @param paymentScheduleLineId 计划付款行ID
     * @return 核销记录个数
     * @author yang.duan 2019/3/14 9:56
     */
    @Override
    public Long checkPmtScheduleLnOccurWf(Long paymentScheduleLineId) {
        return expReportPmtScheduleMapper.checkPmtScheduleLnOccurWf(paymentScheduleLineId);
    }

    /**
     * <p>
     * 删除报销单计划付款行
     * <p/>
     *
     * @param request
     * @param dto     计划付款行dto
     * @return
     * @author yang.duan 2019/3/29 10:47
     */
    @Override
    public int deleteExpRepPmtSchedule(IRequest request, ExpReportPmtSchedule dto) {
        int deleteFlag = -1;
        if (dto != null) {
            if (dto.getPaymentScheduleLineId() == null && dto.getExpReportHeaderId() != null) {
                // 查询所有的计划付款行
                ExpReportPmtSchedule pmtSchedule = new ExpReportPmtSchedule();
                pmtSchedule.setExpReportHeaderId(dto.getExpReportHeaderId());
                List<ExpReportPmtSchedule> pmtScheduleList = self().select(request, pmtSchedule, 1, 0);
                if (pmtScheduleList != null && !pmtScheduleList.isEmpty()) {
                    for (ExpReportPmtSchedule schedule : pmtScheduleList) {
                        deleteExpRepPmtSchedule(request, schedule);
                    }
                }

            } else {
                // 删除核销记录
                CshWriteOff cshWriteOff = new CshWriteOff();
                cshWriteOff.setDocumentSource("EXPENSE_REPORT");
                cshWriteOff.setDocumentHeaderId(dto.getExpReportHeaderId());
                List<CshWriteOff> cshWriteOffList = cshWriteOffMapper.select(cshWriteOff);
                if (cshWriteOffList != null && !cshWriteOffList.isEmpty()) {
                    for (CshWriteOff writeOff : cshWriteOffList) {
                        if (dto.getPaymentScheduleLineId().equals(writeOff.getDocumentLineId())) {
                            cshWriteOffList.remove(writeOff);
                        }
                        if (cshWriteOffList.isEmpty()) {
                            break;
                        }
                    }
                }
                cshWriteOffService.batchDelete(cshWriteOffList);
                // 删除付款主体信息
                cshDocPayAccEntityService.deletePayAccEntity(request, ExpReportHeader.EXP_REPORT, dto.getExpReportHeaderId(), dto.getPaymentScheduleLineId());
                // 删除计划付款行上的多种税种
                ExpReportPmtSchTaxLine schTaxLine = new ExpReportPmtSchTaxLine();
                schTaxLine.setPaymentScheduleLineId(dto.getPaymentScheduleLineId());
                List<ExpReportPmtSchTaxLine> schTaxLineList = expReportPmtSchTaxLineService.select(request, schTaxLine, 1, 0);
                expReportPmtSchTaxLineService.batchDelete(schTaxLineList);

                // 删除计划付款行
                dto = self().selectByPrimaryKey(request, dto);
                deleteFlag = self().deleteByPrimaryKey(dto);
            }
        }
        return deleteFlag;
    }

    @Override
    public List<ExpReportPmtSchedule> queryPmtSchedule(IRequest request, Long expReportHeaderId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ExpReportPmtSchedule> pmtScheduleList = expReportPmtScheduleMapper.queryPmtSchedule(expReportHeaderId);
        if (pmtScheduleList != null && !pmtScheduleList.isEmpty()) {
            for (ExpReportPmtSchedule pmtSchedule : pmtScheduleList) {
                // 设置收款方描述
                setPayeeName(request, pmtSchedule);
                //获取税额相关字段
                setTax(request, pmtSchedule);
            }
        }
        return pmtScheduleList;
    }

    @Override
    public BigDecimal getTotalAmountByLine(Long paymentScheduleLineId) {
        return expReportPmtScheduleMapper.getTotalAmountByLine(paymentScheduleLineId);
    }

    private ExpReportPmtSchedule setPayeeName(IRequest request, ExpReportPmtSchedule pmtSchedule) {
        if (pmtSchedule.getPayeeCategory() != null) {
            if ("EMPLOYEE".equals(pmtSchedule.getPayeeCategory())) {
                ExpEmployee employee = new ExpEmployee();
                employee.setEmployeeId(pmtSchedule.getPayeeId());
                employee = employeeService.selectByPrimaryKey(request, employee);
                if (employee != null) {
                    pmtSchedule.setPayeeName(employee.getName());
                }
            } else if ("VENDER".equals(pmtSchedule.getPayeeCategory())) {
                PurSystemVender systemVender = new PurSystemVender();
                systemVender.setVenderId(pmtSchedule.getPayeeId());
                systemVender = purSystemVenderService.selectByPrimaryKey(request, systemVender);
                if (systemVender != null) {
                    pmtSchedule.setPayeeName(systemVender.getDescription());
                }
            } else {
                OrdSystemCustomer customer = new OrdSystemCustomer();
                customer.setCustomerId(pmtSchedule.getPayeeId());
                customer = ordSystemCustomerService.selectByPrimaryKey(request, customer);
                if (customer != null) {
                    pmtSchedule.setPayeeName(customer.getDescription());
                }
            }
        }
        return pmtSchedule;
    }

    private ExpReportPmtSchedule setTax(IRequest request, ExpReportPmtSchedule pmtSchedule) {
        ExpReportPmtSchTaxLine taxLine = new ExpReportPmtSchTaxLine();
        int count = 0;
        taxLine.setPaymentScheduleLineId(pmtSchedule.getPaymentScheduleLineId());
        count = expReportPmtSchTaxLineMapper.selectCount(taxLine);
        pmtSchedule.setTaxCount(Long.valueOf(count));
        if (count == 1) {
            Criteria criteria = new Criteria(taxLine);
            criteria.where(new WhereField(ExpReportPmtSchTaxLine.FIELD_PAYMENT_SCHEDULE_LINE_ID, Comparison.EQUAL));
            taxLine = expReportPmtSchTaxLineService.selectOptions(request, taxLine, criteria).get(0);
            if (taxLine != null) {
                pmtSchedule.setTaxRate(taxLine.getTaxRate());
                pmtSchedule.setTaxAmount(taxLine.getTaxAmount());
                pmtSchedule.setTaxTypeId(taxLine.getTaxTypeId());
                pmtSchedule.setTaxTypeName(taxLine.getTaxTypeName());
            }
        }
        return pmtSchedule;
    }

    /**
     * <p>报销单核销借款只读页面头查询</p>
     *
     * @param request
     * @param paymentScheduleLineId 报销单计划付款行ID
     * @return List<Map>
     * @author yang.duan 2019/5/27 14:42
     **/
    @Override
    public List<Map> writeOffPrepaymentHeaderQuery(IRequest request, Long paymentScheduleLineId) {
        return expReportPmtScheduleMapper.writeOffPrepaymentHeaderQuery(paymentScheduleLineId);
    }

    /**
     * <p>报销单核销借款只读页面已核销记录查询</p>
     *
     * @param request
     * @param paymentScheduleLineId 计划付款行ID
     * @param pageNum
     * @param pageSize
     * @return the return
     * @author yang.duan 2019/5/27 15:26
     **/
    @Override
    public List<Map> writeOffPrepaymentHistoryQuery(IRequest request, Long paymentScheduleLineId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> list = expReportPmtScheduleMapper.writeOffPrepaymentHistoryQuery(paymentScheduleLineId);
        if (list != null && !list.isEmpty()) {
            for (Map map : list) {
                Long accEntityId = (Long) map.get("accEntityId");
                Date writeOffDate = (Date) map.get("writeOffDate");
                String periodName = gldPeriodService.getPeriodName(request, writeOffDate, accEntityId, null);
                map.put("periodName", periodName);
            }
        }
        return list;
    }
}
