package com.hand.hec.expm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpPolicyDetail;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.service.IExpMoExpPolicyDetailService;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.dto.ExpReportTravelLine;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.expm.service.IExpReportLineService;
import com.hand.hec.expm.service.IExpReportTravelLineService;
import com.hand.hec.exp.dto.ExpMoReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * ExpReportTravelLineServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportTravelLineServiceImpl extends BaseServiceImpl<ExpReportTravelLine> implements IExpReportTravelLineService {

    @Autowired
    private IExpReportHeaderService expReportHeaderService;

    @Autowired
    private IExpReportLineService expReportLineService;

    @Autowired
    private IExpEmployeeAssignService employeeAssignService;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @Autowired
    private IExpMoExpPolicyDetailService expMoExpPolicyDetailService;


    /**
     * <p>报销单差旅行新增</p>
     *
     * @param request
     * @param header     报销单头
     * @param line       报销单行
     * @param reportType 报销单类型
     * @param travelLine 差旅行
     * @return ExpReportTravelLine
     * @throws RuntimeException
     * @author yang.duan 2019/6/10 10:05
     **/
    @Override
    public ExpReportTravelLine insertTravelLine(IRequest request, ExpReportHeader header, ExpReportLine line, ExpMoReportType reportType, ExpReportTravelLine travelLine) throws RuntimeException {
        travelLine.setExpReportLineId(line.getExpReportLineId());
        travelLine = self().insertSelective(request, travelLine);

        // 获取员工分配信息(hec_util_pkg.get_employee_assign_info)
        ExpEmployeeAssign employeeAssign = employeeAssignService.getEmployeeAssignInfo(request, line.getCompanyId(), line.getEmployeeId());

        //--0031426: 差旅费用报销政策定义增加超标准事件定义,根据交通工具判断 add by duanjian 2017.12.27
        FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, line.getCompanyId());
        // 获取费用政策定义信息
        List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), line.getCompanyId(), line.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), line.getPlaceId(), line.getPlaceTypeId(), line.getPositionId(), line.getUnitId(), null, "EXP_REP", reportType.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null);
        ExpMoExpPolicyDetail policyDetail = new ExpMoExpPolicyDetail();
        if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
            policyDetail = expPolicyDetails.get(0);
        }
        //飞机超标准
        if ("SEATS_PLANE".equals(travelLine.getTransportation())) {
            if ((travelLine.getSeatClass().compareTo(policyDetail.getTransportation()) < 0) && (policyDetail.getUpperStdEventName() != null)) {
                //调用超标准事件
                //                exp_evt_pkg.fire_workflow_event(p_event_name       => v_upper_std_event_name,
                //                        p_document_id      => v_exp_report_header.exp_report_header_id,
                //                        p_document_line_id => p_exp_report_line_id,
                //                        p_source_module    => c_exp_report,
                //                        p_event_type       => 'EXP_EXPENSE_SEATS_PLANE_POLICY',
                //                        p_user_id          => p_user_id,
                //                        p_param2           => v_expense_standard,
                //                        p_param3           => v_upper_limit,
                //                        p_param4           => v_lower_limit,
                //                        p_param5           => v_event_name,
                //                        p_param6           => v_upper_std_event_name,
                //                        p_param7           => v_changeable_flag,
                //                        p_param8           => null);
            }
        }
        //高铁/动车超标准
        if("SEATS_HSR_EMU".equals(travelLine.getTransportation())){
            if ((travelLine.getSeatClass().compareTo(policyDetail.getSeatClass()) < 0) && (policyDetail.getUpperStdEventName() != null)) {
                //调用超标准事件
                //                exp_evt_pkg.fire_workflow_event(p_event_name       => v_upper_std_event_name,
                //                        p_document_id      => v_exp_report_header.exp_report_header_id,
                //                        p_document_line_id => p_exp_report_line_id,
                //                        p_source_module    => c_exp_report,
                //                        p_event_type       => 'EXP_EXPENSE_SEATS_HSR_EMU_POLICY',
                //                        p_user_id          => p_user_id,
                //                        p_param2           => v_expense_standard,
                //                        p_param3           => v_upper_limit,
                //                        p_param4           => v_lower_limit,
                //                        p_param5           => v_event_name,
                //                        p_param6           => v_upper_std_event_name,
                //                        p_param7           => v_changeable_flag,
                //                        p_param8           => null);
            }
        }


        //火车超标准
        if("SEATS_TRAIN".equals(travelLine.getTransportation())){
            if ((travelLine.getSeatClass().compareTo(policyDetail.getSeatClass()) < 0) && (policyDetail.getUpperStdEventName() != null)) {
                //调用超标准事件
                //                exp_evt_pkg.fire_workflow_event(p_event_name       => v_upper_std_event_name,
                //                        p_document_id      => v_exp_report_header.exp_report_header_id,
                //                        p_document_line_id => p_exp_report_line_id,
                //                        p_source_module    => c_exp_report,
                //                        p_event_type       => 'EXP_EXPENSE_SEATS_TRAIN_POLICY',
                //                        p_user_id          => p_user_id,
                //                        p_param2           => v_expense_standard,
                //                        p_param3           => v_upper_limit,
                //                        p_param4           => v_lower_limit,
                //                        p_param5           => v_event_name,
                //                        p_param6           => v_upper_std_event_name,
                //                        p_param7           => v_changeable_flag,
                //                        p_param8           => null);
            }
        }
        return travelLine;
    }

    /**
     * <p>报销单差旅行更新</p>
     *
     * @param request
     * @param header 报销单头
     * @param line 报销单行
     * @param reportType 报销单类型
     * @param travelLine 差旅行
     * @return ExpReportTravelLine
     * @throws RuntimeException
     * @author yang.duan 2019/6/10 10:05
     **/
    @Override
    public ExpReportTravelLine updateTravelLine(IRequest request,
            ExpReportHeader header, ExpReportLine line,
            ExpMoReportType reportType, ExpReportTravelLine travelLine) throws RuntimeException{
        travelLine = self().updateByPrimaryKeySelective(request,travelLine);
        // 获取员工分配信息(hec_util_pkg.get_employee_assign_info)
        ExpEmployeeAssign employeeAssign = employeeAssignService.getEmployeeAssignInfo(request, line.getCompanyId(), line.getEmployeeId());
        FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, line.getCompanyId());
        // 获取费用政策定义信息
        List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), line.getCompanyId(), line.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), line.getPlaceId(), line.getPlaceTypeId(), line.getPositionId(), line.getUnitId(), null, "EXP_REP", reportType.getMoExpReportTypeId(), line.getMoExpenseTypeId(), line.getMoExpenseItemId(), null);
        ExpMoExpPolicyDetail policyDetail = new ExpMoExpPolicyDetail();
        if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
            policyDetail = expPolicyDetails.get(0);
        }
        //        ---先清理事件，然后插入，避免重复 add  by duanjian---------------------------------
        //        exp_evt_pkg.delete_workflow_event(p_document_id      => v_exp_report_header.exp_report_header_id,
        //                p_source_module    => c_exp_report,
        //                p_event_type       => 'EXP_EXPENSE_SEATS_PLANE_POLICY', --'EXP_EXPENSE_INVOICE_TYPE_XSFP',
        //                p_user_id          => p_user_id,
        //                p_document_line_id => p_exp_report_line_id);
        //        ---先清理事件，然后插入，避免重复 add  by duanjian 2017.12.28---------------------------------
        //                exp_evt_pkg.delete_workflow_event(p_document_id      => v_exp_report_header.exp_report_header_id,
        //                p_source_module    => c_exp_report,
        //                p_event_type       => 'EXP_EXPENSE_SEATS_HSR_EMU_POLICY', --'EXP_EXPENSE_INVOICE_TYPE_XSFP',
        //                p_user_id          => p_user_id,
        //                p_document_line_id => p_exp_report_line_id);
        //        ---先清理事件，然后插入，避免重复 add  by duanjian 2017.12.28---------------------------------
        //                exp_evt_pkg.delete_workflow_event(p_document_id      => v_exp_report_header.exp_report_header_id,
        //                p_source_module    => c_exp_report,
        //                p_event_type       => 'EXP_EXPENSE_SEATS_TRAIN_POLICY', --'EXP_EXPENSE_INVOICE_TYPE_XSFP',
        //                p_user_id          => p_user_id,
        //                p_document_line_id => p_exp_report_line_id);
        //飞机超标准
        if ("SEATS_PLANE".equals(travelLine.getTransportation())) {
            if ((travelLine.getSeatClass().compareTo(policyDetail.getTransportation()) < 0) && (policyDetail.getUpperStdEventName() != null)) {
                //调用超标准事件
                //                exp_evt_pkg.fire_workflow_event(p_event_name       => v_upper_std_event_name,
                //                        p_document_id      => v_exp_report_header.exp_report_header_id,
                //                        p_document_line_id => p_exp_report_line_id,
                //                        p_source_module    => c_exp_report,
                //                        p_event_type       => 'EXP_EXPENSE_SEATS_PLANE_POLICY',
                //                        p_user_id          => p_user_id,
                //                        p_param2           => v_expense_standard,
                //                        p_param3           => v_upper_limit,
                //                        p_param4           => v_lower_limit,
                //                        p_param5           => v_event_name,
                //                        p_param6           => v_upper_std_event_name,
                //                        p_param7           => v_changeable_flag,
                //                        p_param8           => null);
            }
        }
        //高铁/动车超标准
        if("SEATS_HSR_EMU".equals(travelLine.getTransportation())){
            if ((travelLine.getSeatClass().compareTo(policyDetail.getSeatClass()) < 0) && (policyDetail.getUpperStdEventName() != null)) {
                //调用超标准事件
                //                exp_evt_pkg.fire_workflow_event(p_event_name       => v_upper_std_event_name,
                //                        p_document_id      => v_exp_report_header.exp_report_header_id,
                //                        p_document_line_id => p_exp_report_line_id,
                //                        p_source_module    => c_exp_report,
                //                        p_event_type       => 'EXP_EXPENSE_SEATS_HSR_EMU_POLICY',
                //                        p_user_id          => p_user_id,
                //                        p_param2           => v_expense_standard,
                //                        p_param3           => v_upper_limit,
                //                        p_param4           => v_lower_limit,
                //                        p_param5           => v_event_name,
                //                        p_param6           => v_upper_std_event_name,
                //                        p_param7           => v_changeable_flag,
                //                        p_param8           => null);
            }
        }


        //火车超标准
        if("SEATS_TRAIN".equals(travelLine.getTransportation())){
            if ((travelLine.getSeatClass().compareTo(policyDetail.getSeatClass()) < 0) && (policyDetail.getUpperStdEventName() != null)) {
                //调用超标准事件
                //                exp_evt_pkg.fire_workflow_event(p_event_name       => v_upper_std_event_name,
                //                        p_document_id      => v_exp_report_header.exp_report_header_id,
                //                        p_document_line_id => p_exp_report_line_id,
                //                        p_source_module    => c_exp_report,
                //                        p_event_type       => 'EXP_EXPENSE_SEATS_TRAIN_POLICY',
                //                        p_user_id          => p_user_id,
                //                        p_param2           => v_expense_standard,
                //                        p_param3           => v_upper_limit,
                //                        p_param4           => v_lower_limit,
                //                        p_param5           => v_event_name,
                //                        p_param6           => v_upper_std_event_name,
                //                        p_param7           => v_changeable_flag,
                //                        p_param8           => null);
            }
        }
        return travelLine;
    }
}