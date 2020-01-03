package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpEmployeeJob;
import com.hand.hec.exp.dto.ExpLevelSeries;
import com.hand.hap.fnd.dto.FndManagingOrganization;

public interface IExpEmployeeJobService extends IBaseService<ExpEmployeeJob>, ProxySelf<IExpEmployeeJobService> {

    /**
     * 预算检查的员工职务符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param employeeId 当前占用行员工ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlEmpJobCodeFrom 控制规则员工职务代码从
     * @param controlEmpJobCodeTo 控制规则员工职务代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的员工职务
     */
    List<ExpEmployeeJob> checkExpEmpJob(String controlType, Long employeeId, String filtrateMethod,
            String controlEmpJobCodeFrom, String controlEmpJobCodeTo);

    /**
     * <p>
     * 用于combobox展示数据
     * <p/>
     *
     * @param request
     * @param employeeJob
     * @return List<ExpEmployeeJob>
     */
    List<ExpEmployeeJob> queryForCb(IRequest request, ExpEmployeeJob employeeJob);

    /**
     * 查询级别系列
     * @param request
     * @param expLevelSeries
     * @return 级别系列
     * @author Zhongyu 2019-2-18 17:48
     */
    List<ExpLevelSeries> queryForLevelService(IRequest request, ExpLevelSeries expLevelSeries);

    /**
     * 查询管理组织
     * @param request
     * @param fndManagingOrganization
     * @param roleId
     * @param userId
     * @param magOrgId
     * @author Zhongyu 2019-2-19 14:06
     */
    List<FndManagingOrganization> queryForManagingOrganization(IRequest request,
            FndManagingOrganization fndManagingOrganization, Long roleId, Long userId, Long magOrgId);

    /**
     * 员工职务信息查询
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 员工职务信息
     * @author Zhongyu 2019-2-25 14:03
     */
    List<ExpEmployeeJob> queryEmpJobs(IRequest request, ExpEmployeeJob dto, int page, int pageSize);
}
