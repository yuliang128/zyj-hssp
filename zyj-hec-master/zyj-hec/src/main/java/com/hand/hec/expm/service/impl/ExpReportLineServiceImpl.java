package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.mapper.SysParameterMapper;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.service.IBgtBudgetItemMappingService;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.service.*;
import com.hand.hec.expm.dto.*;
import com.hand.hec.expm.exception.ExpReportException;
import com.hand.hec.expm.mapper.ExpReportHeaderMapper;
import com.hand.hec.expm.mapper.ExpReportLineMapper;
import com.hand.hec.expm.mapper.ExpReportObjectMapper;
import com.hand.hec.expm.service.*;
import com.hand.hec.fnd.mapper.FndDimensionValueMapper;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * ExpReportLineServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportLineServiceImpl extends BaseServiceImpl<ExpReportLine> implements IExpReportLineService {


    @Autowired
    GldCurrencyMapper gldCurrencyMapper;
    @Autowired
    ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    FndDimensionValueMapper dimensionValueMapper;
    @Autowired
    ExpReportLineMapper reportLineMapper;

    @Autowired
    SysParameterMapper sysParameterMapper;

    @Autowired
    GldPeriodMapper gldPeriodMapper;

    @Autowired
    ExpReportObjectMapper expReportObjectMapper;

    @Autowired
    IGldExchangeRateService gldExchangeRateService;

    @Autowired
    IExpReportObjectService expReportObjectService;

    @Autowired
    FndCompanyMapper fndCompanyMapper;

    @Autowired
    IExpMoExpenseItemDescService expenseItemDescService;

    @Autowired
    ISysParameterService sysParameterService;

    @Autowired
    IExpMoReportTypeService reportTypeService;

    @Autowired
    IExpMoRepEleRefLnDimService eleRefLnDimService;


    @Autowired
    IBgtBudgetItemService bgtBudgetItemService;


    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @Autowired
    IBgtBudgetItemMappingService budgetItemMappingService;

    @Autowired
    IExpMoExpenseItemService expenseItemService;

    @Autowired
    IExpMoReqItemService expMoReqItemService;

    @Autowired
    IExpEmployeeAssignService expEmployeeAssignService;


    @Autowired
    IExpMoExpPolicyDetailService expMoExpPolicyDetailService;

    @Autowired
    IExpReportAccountService expReportAccountService;

    @Autowired
    IBgtBudgetReserveService bgtBudgetReserveService;

    @Autowired
    IExpReportDistService expReportDistService;

    @Autowired
    IExpReportHeaderService expReportHeaderService;

    @Autowired
    IExpReportTravelLineService expReportTravelLineService;


    /**
     * <p>
     * 报销单行新增
     * <p/>
     *
     * @param request
     * @param line       需要插入的报销单行信息
     * @param reportType 报销单类型
     * @return 插入后的报销单行信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/11 9:56
     */
    @Override
    public ExpReportLine insertStandardLine(IRequest request, ExpReportLine line, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException {
        if (expReportHeaderService.statusCheck(request,header.getExpReportHeaderId())) {
            // 非“调整”类型的报销单,金额与数量必须为正
            signCheck(reportType, line.getBusinessPrice());
            signCheck(reportType, line.getPrimaryQuantity());

            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(line.getAccEntityId());
            // 计算金额
            ExpReportLine amountLine = calcAmount(line.getBusinessPrice(), line.getPrimaryQuantity(), line.getBusinessCurrencyCode(), line.getBiz2payExchangeRate(), line.getPaymentCurrencyCode(), line.getPay2magExchangeRate(), line.getManagementCurrencyCode(), line.getPay2funExchangeRate(), functionalCurrencyCode);

            // 根据预算项决定规则获取预算项目ID
            Long budgetItemId = budgetItemMappingService.getPrioRityItemId("EXP_REP", line.getCompanyId(), reportType.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null, line.getUnitId(), line.getPositionId(), line.getEmployeeId(), line.getAccEntityId(), line.getRespCenterId(), header.getPayeeCategory(), header.getPayeeId(), line.getDimension1Id(), line.getDimension2Id(), line.getDimension3Id(), line.getDimension4Id(), line.getDimension5Id(), line.getDimension6Id(), line.getDimension7Id(), line.getDimension8Id(), line.getDimension9Id(), line.getDimension10Id(), line.getDimension11Id(), line.getDimension12Id(), line.getDimension13Id(), line.getDimension14Id(), line.getDimension15Id(), line.getDimension16Id(), line.getDimension17Id(), line.getDimension18Id(), line.getDimension19Id(), line.getDimension20Id());

            if (budgetItemId.intValue() == -1) {
                budgetItemId = bgtBudgetItemService.getExpneseBgtItemId(request, line.getMoExpenseItemId());
            }
            // 校验维值是否存在
            checkDimValue(line);
            // 如果行号为空，则获取最大行号+10
            Long lineNumber = Long.valueOf(0);
            if (line.getLineNumber() == null) {
                lineNumber = reportLineMapper.getMaxLineNUmber(header.getExpReportHeaderId()) + Long.valueOf(10);
            } else {
                lineNumber = line.getLineNumber();
            }

            // 赋值数据
            line.setLineNumber(lineNumber);
            line.setExpReportHeaderId(header.getExpReportHeaderId());
            line.setBudgetItemId(budgetItemId);
            line.setPaymentPrice(amountLine.getPaymentPrice());
            line.setManagementPrice(amountLine.getManagementPrice());
            line.setBusinessAmount(amountLine.getBusinessAmount());
            line.setPaymentAmount(amountLine.getPaymentAmount());
            line.setManagementAmount(amountLine.getManagementAmount());
            line.setReportFunctionalAmount(amountLine.getReportFunctionalAmount());
            line.setPayeeId(header.getPayeeId());
            line.setPayeeCategory(header.getPayeeCategory());
            line.setReportStatus(header.getReportStatus());
            line.setAuditFlag(header.getAuditFlag());
            line.setAuditDate(header.getAuditDate());
            line.setAuditDateTz(header.getAuditDateTz());
            line.setAuditDateLtz(header.getAuditDateLtz());
            line.setWriteOffStatus("N");

            ExpReportLine lineNumberDto = new ExpReportLine();
            lineNumberDto.setExpReportHeaderId(header.getExpReportHeaderId());
            List<ExpReportLine> lineNumberList = reportLineMapper.select(lineNumberDto);
            if (lineNumberList != null && !lineNumberList.isEmpty()) {
                for (ExpReportLine l : lineNumberList) {
                    if (l.getLineNumber().longValue() == line.getLineNumber().longValue()) {
                        throw new ExpReportException("EXP",ExpReportException.EXP5110_EXP_REP_LINE_NUMBER_ERROR,null);
                    }
                }
            }
            line.setCreatedBy(request.getUserId());
            line.setCreationDate(new Date());
            line.setLastUpdatedBy(request.getUserId());
            line.setLastUpdateDate(new Date());
            // 插入数据
            line = self().insertSelective(request, line);

            //新增差旅扩展表信息(未)


            // 插入事件
            // exp_evt_pkg.fire_workflow_event(p_event_name => 'EXP_REPORT_LINES_POST_MODIFY',
            // p_document_id => p_exp_report_header_id,
            // p_document_line_id => p_exp_report_line_id,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_REPORT_LINES_POST_MODIFY',
            // p_user_id => p_user_id);

            // 获取员工分配信息(hec_util_pkg.get_employee_assign_info)
            ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(request, line.getCompanyId(), line.getEmployeeId());

            FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, line.getCompanyId());
            // 获取费用政策定义信息
            List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), line.getCompanyId(), line.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), line.getPlaceId(), line.getPlaceTypeId(), line.getPositionId(), line.getUnitId(), null, "EXP_REP", reportType.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null);
            ExpMoExpPolicyDetail policyDetail = new ExpMoExpPolicyDetail();
            if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
                policyDetail = expPolicyDetails.get(0);
            }
            // 获取费用标准/上下限值
            BigDecimal newExpenseStandard = policyDetail.getExpenseStandard();
            BigDecimal newLowerLimit = policyDetail.getLowerLimit();
            BigDecimal newUpperLimit = policyDetail.getUpperLimit();

            if (line.getPlaceId() != null) {
                // 地点常用表数据插入(未)
            }


            // 判断币种是否相同
            if (policyDetail.getCurrencyCode() != null && !line.getPaymentCurrencyCode().equals(policyDetail.getCurrencyCode())) {
                // 根据系统参数获取默认汇率类型
                String defaultExchangeRate = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, line.getCompanyId(), line.getAccEntityId(), null, null, null);
                // 获取期间
                String periodName = String.valueOf(gldPeriodMapper.getPeriodName(header.getReportDate(), line.getAccEntityId(), null));
                // 获取汇率
                BigDecimal exchangeRate = gldExchangeRateService.getExchangeRate(line.getAccEntityId(), policyDetail.getCurrencyCode(), line.getPaymentCurrencyCode(), defaultExchangeRate, header.getReportDate(), periodName, "Y");
                // 重新设置费用标准/上下限值
                newExpenseStandard = policyDetail.getExpenseStandard().multiply(exchangeRate);
                newLowerLimit = policyDetail.getLowerLimit().multiply(exchangeRate);
                newUpperLimit = policyDetail.getUpperLimit().multiply(exchangeRate);

            }

            // 超上下限
            if ("Y".equals(policyDetail.getCommitFlag()) && line.getManagementPrice() != null && policyDetail.getEventName() != null) {
                newLowerLimit = newLowerLimit == null ? line.getManagementPrice().subtract(BigDecimal.ONE) : newLowerLimit;
                newUpperLimit = newUpperLimit == null ? line.getManagementPrice().add(BigDecimal.ONE) : newUpperLimit;
                if (line.getManagementPrice().compareTo(newLowerLimit) == -1 || line.getManagementPrice().compareTo(newUpperLimit) == 1) {
                    // exp_evt_pkg.fire_workflow_event(p_event_name => v_event_name,
                    // p_document_id => p_exp_report_header_id,
                    // p_document_line_id => p_exp_report_line_id,
                    // p_source_module => c_exp_report,
                    // p_event_type => 'EXP_EXPENSE_POLICY',
                    // p_user_id => p_user_id,
                    // p_param2 => v_expense_standard,
                    // p_param3 => v_upper_limit,
                    // p_param4 => v_lower_limit,
                    // p_param5 => v_event_name,
                    // p_param6 => v_upper_std_event_name,
                    // p_param7 => v_changeable_flag,
                    // p_param8 => null);
                }
            }

            newExpenseStandard = newExpenseStandard == null ? line.getBusinessPrice().subtract(BigDecimal.ONE) : newExpenseStandard;
            // 超标准
            if (line.getManagementPrice().compareTo(newExpenseStandard) == 1 && policyDetail.getUpperStdEventName() != null) {
                // exp_evt_pkg.fire_workflow_event(p_event_name => v_upper_std_event_name,
                // p_document_id => p_exp_report_header_id,
                // p_document_line_id => p_exp_report_line_id,
                // p_source_module => c_exp_report,
                // p_event_type => 'EXP_EXPENSE_STANDARD_POLICY',
                // p_user_id => p_user_id,
                // p_param2 => v_expense_standard,
                // p_param3 => v_upper_limit,
                // p_param4 => v_lower_limit,
                // p_param5 => v_event_name,
                // p_param6 => v_upper_std_event_name,
                // p_param7 => v_changeable_flag,
                // p_param8 => null);
            }

            //库存事务的新增逻辑(未)

            // 分配行赋值
            ExpReportDist reportDist = new ExpReportDist();
            reportDist.setExpReportLineId(line.getExpReportLineId());
            reportDist.setDescription(line.getDescription());
            reportDist.setDateFrom(line.getDateFrom());
            reportDist.setDateTo(line.getDateTo());
            reportDist.setPeriodName(line.getPeriodName());
            reportDist.setBusinessCurrencyCode(line.getBusinessCurrencyCode());
            reportDist.setBiz2payExchangeType(line.getBiz2payExchangeType());
            reportDist.setBiz2payExchangeRate(line.getBiz2payExchangeRate());
            reportDist.setPaymentCurrencyCode(line.getPaymentCurrencyCode());
            reportDist.setPay2funExchangeType(line.getPay2funExchangeType());
            reportDist.setPay2funExchangeRate(line.getPay2funExchangeRate());
            reportDist.setManagementCurrencyCode(line.getManagementCurrencyCode());
            reportDist.setPay2magExchangeType(line.getPay2magExchangeType());
            reportDist.setPay2magExchangeRate(line.getPay2magExchangeRate());
            reportDist.setMoExpenseTypeId(line.getMoExpenseTypeId());
            reportDist.setMoExpenseItemId(line.getMoExpenseItemId());
            reportDist.setBudgetItemId(budgetItemId);
            reportDist.setBusinessPrice(line.getBusinessPrice());
            reportDist.setPrimaryQuantity(line.getPrimaryQuantity());
            reportDist.setCompanyId(line.getCompanyId());
            reportDist.setUnitId(line.getUnitId());
            reportDist.setPositionId(line.getPositionId());
            reportDist.setEmployeeId(line.getEmployeeId());
            reportDist.setAccEntityId(line.getAccEntityId());
            reportDist.setRespCenterId(line.getRespCenterId());
            reportDist.setBgtEntityId(line.getBgtEntityId());
            reportDist.setBgtCenterId(line.getBgtCenterId());
            reportDist.setPayeeCategory(line.getPayeeCategory());
            reportDist.setPayeeId(line.getPayeeId());
            reportDist.setAttachmentNum(line.getAttachmentNum());
            reportDist.setReportStatus(header.getReportStatus());
            reportDist.setAuditFlag(header.getAuditFlag());
            reportDist.setAuditDate(header.getAuditDate());
            reportDist.setAuditDateTz(header.getAuditDate());
            reportDist.setAuditDateLtz(header.getAuditDateLtz());
            reportDist.setDimension1Id(line.getDimension1Id());
            reportDist.setDimension2Id(line.getDimension2Id());
            reportDist.setDimension3Id(line.getDimension3Id());
            reportDist.setDimension4Id(line.getDimension4Id());
            reportDist.setDimension5Id(line.getDimension5Id());
            reportDist.setDimension6Id(line.getDimension6Id());
            reportDist.setDimension7Id(line.getDimension7Id());
            reportDist.setDimension8Id(line.getDimension8Id());
            reportDist.setDimension9Id(line.getDimension9Id());
            reportDist.setDimension10Id(line.getDimension10Id());
            reportDist.setDimension11Id(line.getDimension11Id());
            reportDist.setDimension12Id(line.getDimension12Id());
            reportDist.setDimension13Id(line.getDimension13Id());
            reportDist.setDimension14Id(line.getDimension14Id());
            reportDist.setDimension15Id(line.getDimension15Id());
            reportDist.setDimension16Id(line.getDimension16Id());
            reportDist.setDimension17Id(line.getDimension17Id());
            reportDist.setDimension18Id(line.getDimension18Id());
            reportDist.setDimension19Id(line.getDimension19Id());
            reportDist.setDimension20Id(line.getDimension20Id());

            // 自动插入分配行
            reportDist = expReportDistService.insertDisLine(request, reportDist, reportType, header, line);
        }
        return line;
    }

    /**
     * <p>
     * 报销单行更新
     * <p/>
     *
     * @param request
     * @param line       需要更新的报销单行信息
     * @param header     报销单头信息
     * @param reportType 报销单类型
     * @return 更新后的行信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/11 9:58
     */
    @Override
    public ExpReportLine updateStandardLine(IRequest request, ExpReportLine line, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException {
        if (expReportHeaderService.statusCheck(request,header.getExpReportHeaderId())) {
            // 非“调整”类型的报销单,金额与数量必须为正
            signCheck(reportType, line.getBusinessPrice());
            signCheck(reportType, line.getPrimaryQuantity());

            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(line.getAccEntityId());
            // 计算金额
            ExpReportLine amountLine = calcAmount(line.getBusinessPrice(), line.getPrimaryQuantity(), line.getBusinessCurrencyCode(), line.getBiz2payExchangeRate(), line.getPaymentCurrencyCode(), line.getPay2magExchangeRate(), line.getManagementCurrencyCode(), line.getPay2funExchangeRate(), functionalCurrencyCode);


            // 根据预算项决定规则获取预算项目ID
            Long budgetItemId = budgetItemMappingService.getPrioRityItemId("EXP_REP", line.getCompanyId(), reportType.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null, line.getUnitId(), line.getPositionId(), line.getEmployeeId(), line.getAccEntityId(), line.getRespCenterId(), header.getPayeeCategory(), header.getPayeeId(), line.getDimension1Id(), line.getDimension2Id(), line.getDimension3Id(), line.getDimension4Id(), line.getDimension5Id(), line.getDimension6Id(), line.getDimension7Id(), line.getDimension8Id(), line.getDimension9Id(), line.getDimension10Id(), line.getDimension11Id(), line.getDimension12Id(), line.getDimension13Id(), line.getDimension14Id(), line.getDimension15Id(), line.getDimension16Id(), line.getDimension17Id(), line.getDimension18Id(), line.getDimension19Id(), line.getDimension20Id());

            if (budgetItemId.intValue() == -1) {
                budgetItemId = bgtBudgetItemService.getExpneseBgtItemId(request, line.getMoExpenseItemId());
            }

            line.setBudgetItemId(budgetItemId);
            line.setPaymentPrice(amountLine.getPaymentPrice());
            line.setManagementPrice(amountLine.getManagementPrice());
            line.setBusinessAmount(amountLine.getBusinessAmount());
            line.setPaymentAmount(amountLine.getPaymentAmount());
            line.setManagementAmount(amountLine.getManagementAmount());
            line.setReportFunctionalAmount(amountLine.getReportFunctionalAmount());
            // 重算分配行
            expReportDistService.resetDisLine(request, line, header, reportType);

            // 更新行
            line.setLastUpdatedBy(request.getUserId());
            line.setLastUpdateDate(new Date());
            line = self().updateByPrimaryKeySelective(request, line);

            //更新库存数据逻辑(未)

            //更新差旅扩展表信息(未)

            // 事件触发
            // exp_evt_pkg.fire_workflow_event(p_event_name => 'EXP_REPORT_LINES_POST_MODIFY',
            // p_document_id => p_exp_report_header_id,
            // p_document_line_id => p_exp_report_line_id,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_REPORT_LINES_POST_MODIFY',
            // p_user_id => p_user_id);
            //
            // --0031425: 费用政策定义增加超标准事件定义 2010.08.26 Bobo
            // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_EXPENSE_STANDARD_POLICY',
            // p_user_id => p_user_id,
            // p_document_line_id => p_exp_report_line_id);

            // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_EXPENSE_POLICY',
            // p_user_id => p_user_id,
            // p_document_line_id => p_exp_report_line_id);

            // 获取员工分配信息(hec_util_pkg.get_employee_assign_info)
            ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(request, line.getCompanyId(), line.getEmployeeId());

            if (line.getPlaceId() != null) {
                // 地点常用表数据插入(未)
            }
            FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, line.getCompanyId());
            // 费用申请政策定义增加超标准事件定义
            List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), line.getCompanyId(), line.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), line.getPlaceId(), line.getPlaceTypeId(), line.getPositionId(), line.getUnitId(), null, "EXP_REP", reportType.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null);
            ExpMoExpPolicyDetail policyDetail = new ExpMoExpPolicyDetail();
            if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
                policyDetail = expPolicyDetails.get(0);
            }

            BigDecimal newExpenseStandard = policyDetail.getExpenseStandard();
            BigDecimal newLowerLimit = policyDetail.getLowerLimit();
            BigDecimal newUpperLimit = policyDetail.getUpperLimit();

            // 判断币种是否相同
            if (policyDetail.getCurrencyCode() != null && !line.getPaymentCurrencyCode().equals(policyDetail.getCurrencyCode())) {
                // 根据系统参数获取默认汇率类型
                String defaultExchangeRate = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, line.getCompanyId(), line.getAccEntityId(), null, null, null);
                // 获取期间
                String periodName = String.valueOf(gldPeriodMapper.getPeriodName(header.getReportDate(), line.getAccEntityId(), null));
                // 获取汇率
                BigDecimal exchangeRate = gldExchangeRateService.getExchangeRate(line.getAccEntityId(), policyDetail.getCurrencyCode(), line.getPaymentCurrencyCode(), defaultExchangeRate, header.getReportDate(), periodName, "Y");

                newExpenseStandard = policyDetail.getExpenseStandard().multiply(exchangeRate);
                newLowerLimit = policyDetail.getLowerLimit().multiply(exchangeRate);
                newUpperLimit = policyDetail.getUpperLimit().multiply(exchangeRate);

            }


            // ---先清理事件，然后插入，避免重复 add by duanjian 2017.12.28---------------------------------
            // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_EXPENSE_POLICY', --'EXP_EXPENSE_INVOICE_TYPE_XSFP',
            // p_user_id => p_user_id,
            // p_document_line_id => p_exp_report_line_id);
            // ---先清理事件，然后插入，避免重复 add by duanjian 2017.12.28---------------------------------
            // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
            // p_source_module => c_exp_report,
            // p_event_type => 'EXP_EXPENSE_STANDARD_POLICY', --'EXP_EXPENSE_INVOICE_TYPE_XSFP',
            // p_user_id => p_user_id,
            // p_document_line_id => p_exp_report_line_id);

            // 超上下限
            if ("Y".equals(policyDetail.getCommitFlag()) && line.getManagementPrice() != null && policyDetail.getEventName() != null) {
                newLowerLimit = newLowerLimit == null ? line.getManagementPrice().subtract(BigDecimal.ONE) : newLowerLimit;
                newUpperLimit = newUpperLimit == null ? line.getManagementPrice().add(BigDecimal.ONE) : newUpperLimit;
                if (line.getManagementPrice().compareTo(newLowerLimit) == -1 || line.getManagementPrice().compareTo(newUpperLimit) == 1) {
                    // exp_evt_pkg.fire_workflow_event(p_event_name => v_event_name,
                    // p_document_id => p_exp_report_header_id,
                    // p_document_line_id => p_exp_report_line_id,
                    // p_source_module => c_exp_report,
                    // p_event_type => 'EXP_EXPENSE_POLICY',
                    // p_user_id => p_user_id,
                    // p_param2 => v_expense_standard,
                    // p_param3 => v_upper_limit,
                    // p_param4 => v_lower_limit,
                    // p_param5 => v_event_name,
                    // p_param6 => v_upper_std_event_name,
                    // p_param7 => v_changeable_flag,
                    // p_param8 => null);
                }
            }
            newExpenseStandard = newExpenseStandard == null ? line.getBusinessPrice().subtract(BigDecimal.ONE) : newExpenseStandard;
            // 超标准
            if (line.getManagementPrice().compareTo(newExpenseStandard) == 1 && policyDetail.getUpperStdEventName() != null) {
                // exp_evt_pkg.fire_workflow_event(p_event_name => v_upper_std_event_name,
                // p_document_id => p_exp_report_header_id,
                // p_document_line_id => p_exp_report_line_id,
                // p_source_module => c_exp_report,
                // p_event_type => 'EXP_EXPENSE_STANDARD_POLICY',
                // p_user_id => p_user_id,
                // p_param2 => v_expense_standard,
                // p_param3 => v_upper_limit,
                // p_param4 => v_lower_limit,
                // p_param5 => v_event_name,
                // p_param6 => v_upper_std_event_name,
                // p_param7 => v_changeable_flag,
                // p_param8 => null);
            }
        }
        return line;
    }


    /**
     * <p>
     * 报销单行费用对象保存
     * <p/>
     *
     * @param request 请求
     * @param line    报销单行信息
     * @return 保存后的行费用对象list
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 10:41
     */
    @Override
    public List<ExpReportObject> saveLineObject(IRequest request, ExpReportLine line) throws RuntimeException {
        List<ExpReportObject> expReportObjects = new ArrayList<>();
        List<HashMap<String, Object>> objectList = (List<HashMap<String, Object>>) line.getInnerMap().get("expenseObjectLines");
        if (objectList != null && !objectList.isEmpty()) {
            for (HashMap<String, Object> map : objectList) {
                ExpReportObject reportObject = new ExpReportObject();
                reportObject.setExpReportHeaderId(line.getExpReportHeaderId());
                reportObject.setExpReportLineId(line.getExpReportLineId());
                reportObject.setMoExpObjTypeId(Long.valueOf((String) map.get("moExpObjTypeId")));

                // 查询行费用对象是否存在
                int oldObjectCount = expReportObjectMapper.selectCount(reportObject);

                reportObject.setMoExpenseObjectId(Long.valueOf((String) map.get("moExpenseObjectId")));
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
                expReportObjects.add(reportObject);
            }
        }
        return expReportObjects;
    }

    /**
     * <p>
     * 校验行维值是否存在
     * <p/>
     *
     * @param line 报销单行信息
     * @return void
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/11 10:02
     */
    private void checkDimValue(ExpReportLine line) {
        List<Long> dimensionList = new ArrayList<>();
        dimensionList.add(line.getDimension1Id());
        dimensionList.add(line.getDimension2Id());
        dimensionList.add(line.getDimension3Id());
        dimensionList.add(line.getDimension4Id());
        dimensionList.add(line.getDimension5Id());
        dimensionList.add(line.getDimension6Id());
        dimensionList.add(line.getDimension7Id());
        dimensionList.add(line.getDimension8Id());
        dimensionList.add(line.getDimension9Id());
        dimensionList.add(line.getDimension10Id());
        dimensionList.add(line.getDimension11Id());
        dimensionList.add(line.getDimension12Id());
        dimensionList.add(line.getDimension13Id());
        dimensionList.add(line.getDimension14Id());
        dimensionList.add(line.getDimension15Id());
        dimensionList.add(line.getDimension16Id());
        dimensionList.add(line.getDimension17Id());
        dimensionList.add(line.getDimension18Id());
        dimensionList.add(line.getDimension19Id());
        dimensionList.add(line.getDimension20Id());
        for (int i = 0; i <= 19; i++) {
            int count = 0;
            if (dimensionList.get(i) != null) {
                count = dimensionValueMapper.checkDimensionValue(Long.valueOf(i + 1), dimensionList.get(i));
                if (count == 0) {
                    throw new ExpReportException("EXP",ExpReportException.EXP5110_DIMENSION_VALUE_ERROR,null);
                }
            }
        }
    }

    /**
     * <p>
     * 计算金额
     * <p/>
     *
     * @param businessPrice          业务单价
     * @param primaryQuantity        业务数量
     * @param businessCurrencyCode   业务币种
     * @param biz2payExchangeRate    业务币种->支付币种汇率
     * @param paymentCurrencyCode    支付币种
     * @param pay2magExchangeRate    支付币种->管理币种汇率
     * @param managementCurrencyCode 管理币种
     * @param pay2funExchangeRate    支付币种->本位币汇率
     * @param funCurrencyCode        本位币币种
     * @return 返回带有各个金额属性的报销单行DTO
     * @author yang.duan 2019/3/6 16:53
     */
    @Override
    public ExpReportLine calcAmount(BigDecimal businessPrice, BigDecimal primaryQuantity, String businessCurrencyCode, BigDecimal biz2payExchangeRate, String paymentCurrencyCode, BigDecimal pay2magExchangeRate, String managementCurrencyCode, BigDecimal pay2funExchangeRate, String funCurrencyCode) {
        ExpReportLine line = new ExpReportLine();
        // 计算业务金额
        BigDecimal businessAmount = calcCurrTrxAmount(businessPrice.multiply(primaryQuantity), businessCurrencyCode);

        // 计算支付单价
        BigDecimal paymentPrice = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate), paymentCurrencyCode);

        // 计算支付金额
        BigDecimal paymentAmount = calcCurrGldAmount(businessPrice.multiply(biz2payExchangeRate).multiply(primaryQuantity), paymentCurrencyCode);

        // 计算管理单价
        BigDecimal managementPrice = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate).multiply(pay2magExchangeRate), managementCurrencyCode);

        // 计算管理金额
        BigDecimal managementAmount = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate).multiply(pay2magExchangeRate).multiply(primaryQuantity), managementCurrencyCode);

        // 计算核算金额(本位币对应金额)
        BigDecimal functionalAmount = calcCurrGldAmount(businessPrice.multiply(biz2payExchangeRate).multiply(pay2funExchangeRate).multiply(primaryQuantity), funCurrencyCode);

        line.setBusinessAmount(businessAmount);
        line.setPaymentPrice(paymentPrice);
        line.setPaymentAmount(paymentAmount);
        line.setManagementPrice(managementPrice);
        line.setManagementAmount(managementAmount);
        line.setReportFunctionalAmount(functionalAmount);

        return line;
    }

    /**
     * <p>
     * 根据精度计算金额(业务精度)
     * <p/>
     *
     * @param amount
     * @return 返回计算金额
     * @author yang.duan 2019/3/6 16:54
     */
    private BigDecimal calcCurrTrxAmount(BigDecimal amount, String currencyCode) {
        int precision = gldCurrencyMapper.getTransactionPrecision(currencyCode);
        if (precision == 0) {
            return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            return amount.setScale(precision, BigDecimal.ROUND_HALF_UP);
        }

    }

    /**
     * <p>
     * 根据精度计算金额(财务精度)
     * <p/>
     *
     * @param amount
     * @return 返回计算金额
     * @author yang.duan 2019/3/6 16:54
     */
    private BigDecimal calcCurrGldAmount(BigDecimal amount, String currencyCode) {
        int precision = gldCurrencyMapper.getPrecision(currencyCode);
        if (precision == 0) {
            return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            return amount.setScale(precision, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * <p>
     * 校验非调整类报销单数量和金额
     * <p/>
     *
     * @param reportType 报销单类型DTO
     * @param number     行金额/数量
     * @return void
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/6 16:57
     */
    @Override
    public void signCheck(ExpMoReportType reportType, BigDecimal number) throws RuntimeException {
        if (reportType != null && "N".equals(reportType.getTemplateFlag())) {
            if ("N".equals(reportType.getAdjustmentFlag())) {
                if (number.compareTo(BigDecimal.ZERO) == -1) {
                    throw new ExpReportException("EXP",ExpReportException.EXP5110_EXP_REP_POSITIVE_NUMBER_ERROR,null);
                }
            }
            if (number.compareTo(BigDecimal.ZERO) == 0) {
                throw new ExpReportException("EXP",ExpReportException.EXP5110_EXP_REP_ZERO_CHECK_ERROR,null);
            }
        }
    }


    /**
     * <p>
     * 更新行维度
     * <p/>
     *
     * @param request
     * @param header  头信息
     * @return 更新完后的行list
     * @author yang.duan 2019/3/11 9:54
     */
    @Override
    public List<ExpReportLine> updateLineDim(IRequest request, ExpReportHeader header) {
        ExpReportLine reportLine = new ExpReportLine();
        List<ExpReportLine> expReportLines = new ArrayList<>();
        if (header != null) {
            reportLine.setExpReportHeaderId(header.getExpReportHeaderId());
            expReportLines = reportLineMapper.select(reportLine);
            if (!expReportLines.isEmpty()) {
                for (ExpReportLine line : expReportLines) {
                    Long budgetItemId = Long.valueOf(-1);
                    budgetItemId = budgetItemMappingService.getPrioRityItemId("EXP_REP", line.getCompanyId(), header.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null, line.getUnitId(), line.getPositionId(), line.getEmployeeId(), line.getAccEntityId(), line.getRespCenterId(), header.getPayeeCategory(), header.getPayeeId(), header.getDimension1Id() == null ? line.getDimension1Id() : header.getDimension1Id(), header.getDimension2Id() == null ? line.getDimension2Id() : header.getDimension2Id(), header.getDimension3Id() == null ? line.getDimension3Id() : header.getDimension3Id(), header.getDimension4Id() == null ? line.getDimension4Id() : header.getDimension4Id(), header.getDimension5Id() == null ? line.getDimension5Id() : header.getDimension5Id(), header.getDimension6Id() == null ? line.getDimension6Id() : header.getDimension6Id(), header.getDimension7Id() == null ? line.getDimension7Id() : header.getDimension7Id(), header.getDimension8Id() == null ? line.getDimension8Id() : header.getDimension8Id(), header.getDimension9Id() == null ? line.getDimension9Id() : header.getDimension9Id(), header.getDimension10Id() == null ? line.getDimension10Id() : header.getDimension10Id(), header.getDimension11Id() == null ? line.getDimension11Id() : header.getDimension11Id(), header.getDimension12Id() == null ? line.getDimension12Id() : header.getDimension12Id(), header.getDimension13Id() == null ? line.getDimension13Id() : header.getDimension13Id(), header.getDimension14Id() == null ? line.getDimension14Id() : header.getDimension14Id(), header.getDimension15Id() == null ? line.getDimension15Id() : header.getDimension15Id(), header.getDimension16Id() == null ? line.getDimension16Id() : header.getDimension16Id(), header.getDimension17Id() == null ? line.getDimension17Id() : header.getDimension17Id(), header.getDimension18Id() == null ? line.getDimension18Id() : header.getDimension18Id(), header.getDimension19Id() == null ? line.getDimension19Id() : header.getDimension19Id(), header.getDimension20Id() == null ? line.getDimension20Id() : header.getDimension20Id());
                    if (budgetItemId.intValue() == -1) {
                        budgetItemId = bgtBudgetItemService.getExpneseBgtItemId(request, line.getMoExpenseItemId());
                    }
                    line.setDimension1Id(header.getDimension1Id() == null ? line.getDimension1Id() : header.getDimension1Id());
                    line.setDimension2Id(header.getDimension2Id() == null ? line.getDimension2Id() : header.getDimension2Id());
                    line.setDimension3Id(header.getDimension3Id() == null ? line.getDimension3Id() : header.getDimension3Id());
                    line.setDimension4Id(header.getDimension4Id() == null ? line.getDimension4Id() : header.getDimension4Id());
                    line.setDimension5Id(header.getDimension5Id() == null ? line.getDimension5Id() : header.getDimension5Id());
                    line.setDimension6Id(header.getDimension6Id() == null ? line.getDimension6Id() : header.getDimension6Id());
                    line.setDimension7Id(header.getDimension7Id() == null ? line.getDimension7Id() : header.getDimension7Id());
                    line.setDimension8Id(header.getDimension8Id() == null ? line.getDimension8Id() : header.getDimension8Id());
                    line.setDimension9Id(header.getDimension9Id() == null ? line.getDimension9Id() : header.getDimension9Id());
                    line.setDimension10Id(header.getDimension10Id() == null ? line.getDimension10Id() : header.getDimension10Id());
                    line.setDimension11Id(header.getDimension11Id() == null ? line.getDimension11Id() : header.getDimension11Id());
                    line.setDimension12Id(header.getDimension12Id() == null ? line.getDimension12Id() : header.getDimension12Id());
                    line.setDimension13Id(header.getDimension13Id() == null ? line.getDimension13Id() : header.getDimension13Id());
                    line.setDimension14Id(header.getDimension14Id() == null ? line.getDimension14Id() : header.getDimension14Id());
                    line.setDimension15Id(header.getDimension15Id() == null ? line.getDimension15Id() : header.getDimension15Id());
                    line.setDimension16Id(header.getDimension16Id() == null ? line.getDimension16Id() : header.getDimension16Id());
                    line.setDimension17Id(header.getDimension17Id() == null ? line.getDimension17Id() : header.getDimension17Id());
                    line.setDimension18Id(header.getDimension18Id() == null ? line.getDimension18Id() : header.getDimension18Id());
                    line.setDimension19Id(header.getDimension19Id() == null ? line.getDimension19Id() : header.getDimension19Id());
                    line.setDimension20Id(header.getDimension20Id() == null ? line.getDimension20Id() : header.getDimension20Id());

                    line.setBudgetItemId(budgetItemId);
                    line.set__status(DTOStatus.UPDATE);
                    expReportDistService.updateDisLineDim(request, line);
                }
            }
        }
        return self().batchUpdate(request, expReportLines);
    }


    @Override
    public List<ExpMoExpenseType> queryExpenseTypeDefault(IRequest request, Long moExpReportTypeId, String pageElementCode, Long companyId, Integer page, Integer pageSize) {
        return reportLineMapper.queryExpenseTypeDefault(moExpReportTypeId, pageElementCode, companyId);
    }

    @Override
    public List<ExpMoExpenseType> queryExpenseType(IRequest request, Long moExpReportTypeId, String pageElementCode, Long companyId, Integer page, Integer pageSize) {
        return reportLineMapper.queryExpenseType(moExpReportTypeId, pageElementCode, companyId);
    }

    //    @Override
    //    public List<ExpReportLine> queryTaxTypeCode(IRequest request, ExpReportLine expReportLine, Integer page,
    //                    Integer pageSize) {
    //        return reportLineMapper.queryTaxTypeCode(expReportLine);
    //    }

    @Override
    public List<ExpMoExpenseItem> queryExpenseItem(IRequest request, Long moExpReportTypeId, Long moExpenseTypeId, String pageElementCode, Long companyId) {
        List<ExpMoExpenseItem> itemList = reportLineMapper.queryExpenseItem(moExpReportTypeId, moExpenseTypeId, pageElementCode, companyId);
        if (itemList != null && !itemList.isEmpty()) {
            for (ExpMoExpenseItem item : itemList) {
                FndCompany company = fndCompanyMapper.selectByPrimaryKey(companyId);
                ExpMoExpenseItemDesc itemDesc = new ExpMoExpenseItemDesc();
                itemDesc.setCompanyId(companyId);
                itemDesc.setMoExpenseItemId(item.getMoExpenseItemId());
                itemDesc.setMoExpReportTypeId(moExpReportTypeId);
                itemDesc.setMagOrgId(company.getMagOrgId());
                List<ExpMoExpenseItemDesc> list = expenseItemDescService.select(request, itemDesc, 1, 0);
                if (list != null && !list.isEmpty()) {
                    item.setTip(list.get(0).getDescription());
                }
            }
        }
        return itemList;
    }

    /**
     * <p>
     * 费用报销单行删除
     * <p/>
     *
     * @param request
     * @param dto
     * @return
     * @author yang.duan 2019/3/29 13:38
     */
    @Override
    public int deleteExpReportLine(IRequest request, ExpReportLine dto) {
        int deleteFlag = -1;
        if (dto != null) {
            if (dto.getExpReportHeaderId() != null && dto.getExpReportLineId() == null) {
                List<ExpReportLine> lineList = self().select(request, dto, 1, 0);
                if (lineList != null && !lineList.isEmpty()) {
                    for (ExpReportLine line : lineList) {
                        if (BigDecimal.ZERO.compareTo(line.getPrimaryQuantity()) == 1) {
                            lineList.remove(line);
                        }
                        if (lineList.isEmpty()) {
                            break;
                        }
                    }
                }
                if (!lineList.isEmpty()) {
                    for (ExpReportLine line : lineList) {
                        deleteExpReportLine(request, line);
                    }
                }
            } else {
                // 单行删除
                // 查询头数据
                ExpReportHeader header = expReportHeaderMapper.selectByPrimaryKey(dto.getExpReportHeaderId());
                if (header != null) {
                    if (expReportHeaderService.statusCheck(request,header.getExpReportHeaderId())) {
                        // 删除凭证
                        ExpReportDist reportDist = new ExpReportDist();
                        reportDist.setExpReportLineId(dto.getExpReportLineId());
                        List<ExpReportDist> distList = expReportDistService.select(request, reportDist, 1, 0);
                        if (distList != null && !distList.isEmpty()) {
                            for (ExpReportDist dist : distList) {
                                ExpReportAccount expReportAccount = new ExpReportAccount();
                                expReportAccount.setExpReportDistId(dist.getExpReportDistId());
                                List<ExpReportAccount> accountList = expReportAccountService.select(request, expReportAccount, 1, 0);
                                expReportAccountService.batchDelete(accountList);
                            }
                        }
                        // 删除预算保留行
                        if (distList != null && !distList.isEmpty()) {
                            for (ExpReportDist dist : distList) {
                                BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
                                budgetReserve.setDocumentId(dto.getExpReportHeaderId());
                                budgetReserve.setDocumentLineId(dist.getExpReportDistId());
                                List<BgtBudgetReserve> reserveList = bgtBudgetReserveService.select(request, budgetReserve, 1, 0);
                                bgtBudgetReserveService.batchDelete(reserveList);
                            }
                        }
                        // 删除分配行
                        if (distList != null && !distList.isEmpty()) {
                            for (ExpReportDist dist : distList) {
                                expReportDistService.deleteExpReportDist(request, dist);
                            }
                        }
                        // 库存删除逻辑(未)
                        // 差旅删除逻辑
                        dto = self().selectByPrimaryKey(request,dto);
                        if("TRAVEL_LINES".equals(dto.getReportPageElementCode())
                                || "TRAVEL_STAY_LINES".equals(dto.getReportPageElementCode())
                                || "TRAVEL_SUBSIDY_LINES".equals(dto.getReportPageElementCode())
                                || "TICKET_UNIFIED".equals(dto.getReportPageElementCode())
                                || "TRAVEL_STAY_UNIFIED".equals(dto.getReportPageElementCode())){
                            ExpReportTravelLine travelLine = new ExpReportTravelLine();
                            travelLine.setExpReportLineId(dto.getExpReportLineId());
                            travelLine = expReportTravelLineService.selectByPrimaryKey(request,travelLine);
                            expReportTravelLineService.deleteByPrimaryKey(travelLine);
                        }
                        // 删除费用对象
                        expReportObjectService.deleteExpObject(null, dto.getExpReportLineId());
                        //删除发票关联信息(未)
                        // 删除报销单行
                        deleteFlag = self().deleteByPrimaryKey(dto);
                        // --删除费用政策相关事件
                        // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
                        // p_source_module => c_exp_report,
                        // p_event_type => 'EXP_EXPENSE_STANDARD_POLICY',
                        // p_user_id => p_user_id,
                        // p_document_line_id => p_exp_report_line_id);
                        //
                        // exp_evt_pkg.delete_workflow_event(p_document_id => p_exp_report_header_id,
                        // p_source_module => c_exp_report,
                        // p_event_type => 'EXP_EXPENSE_POLICY',
                        // p_user_id => p_user_id,
                        // p_document_line_id => p_exp_report_line_id);
                    }
                }
            }
        }
        return deleteFlag;
    }

    /**
     * <p>
     * 查询报销单行主数据
     * <p/>
     *
     * @param request
     * @param expReportHeaderId     报销单头ID
     * @param reportPageElementCode 页面元素类型code
     * @param pageNum               页码
     * @param pageSize              分页大小
     * @return 报销单行list
     * @author yang.duan 2019/4/3 13:22
     */
    @Override
    public List<ExpReportLine> reportLineQuery(IRequest request, Long expReportHeaderId, String reportPageElementCode, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExpReportLine> reportLineList = reportLineMapper.reportLineQuery(expReportHeaderId, reportPageElementCode);
        return reportLineList;
    }
}

