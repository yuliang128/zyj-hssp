package com.hand.hec.bgt.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondition;
import com.hand.hec.bgt.dto.BgtBudgetItem;
import com.hand.hec.bgt.dto.BgtPeriod;
import com.hand.hec.exp.dto.ExpEmployeeJob;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;

/**
 * <p>
 * 预算余额查询参数Service
 * </p>
 *
 * @author YHL 2019/03/22 14:59
 */
public interface IBgtBalanceQueryParamService {

    /**
     * 检查公司是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-26 14:19
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateCompany(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            Map balanceQueryParamMap);

    /**
     * 检查预算组织是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-22 16:18
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetOrganization(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            Long bgtOrgId, Map balanceQueryParamMap);

    /**
     * 检查预算表是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 9:43
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetStructure(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            Map balanceQueryParamMap);

    /**
     * 检查预算场景是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 9:51
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetScenario(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            Map balanceQueryParamMap);

    /**
     * 检查预算版本是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 9:58
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetVersion(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            Map balanceQueryParamMap);

    /**
     * 检查预算实体是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param entityFlag 预算实体标志
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 11:20
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetEntity(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            String entityFlag, Map balanceQueryParamMap);

    /**
     * 检查币种是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 14:16
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateCurrency(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            Map balanceQueryParamMap);

    /**
     * 检查预算期间是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param periodLower 预算期间始值
     * @param periodUpper 预算期间止值
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 15:07
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetPeriod(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            BgtPeriod periodLower, BgtPeriod periodUpper, Map balanceQueryParamMap);

    /**
     * 检查预算期间季度是否在预算期间范围内
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param periodLower 预算期间始值
     * @param periodUpper 预算期间止值
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 15:53
     * @return java.lang.Boolean 是否在范围内
     */
    Boolean validateBudgetPeriodQuarter(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            BgtPeriod periodLower, BgtPeriod periodUpper, Map balanceQueryParamMap);

    /**
     * 检查预算期间年度是否在预算期间范围内
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param periodLower 预算期间始值
     * @param periodUpper 预算期间止值
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-25 16:30
     * @return java.lang.Boolean 是否在范围内
     */
    Boolean validateBudgetPeriodYear(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            BgtPeriod periodLower, BgtPeriod periodUpper, Map balanceQueryParamMap);

    /**
     * 检查预算中心是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-26 15:44
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetCenter(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            Map balanceQueryParamMap);

    /**
     * 检查预算项目是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-26 18:21
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateBudgetItem(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            Map balanceQueryParamMap);

    /**
     * 检查预算项目类型是否与预算项目匹配
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param bgtOrgId 预算组织ID
     * @param budgetItemList 预算项目
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-27 10:12
     * @return java.lang.Boolean 是否匹配
     */
    Boolean validateBudgetItemType(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition, Long bgtOrgId,
            List<BgtBudgetItem> budgetItemList, Map balanceQueryParamMap);

    /**
     * 检查员工是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-27 14:07
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateEmployee(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            Map balanceQueryParamMap);

    /**
     * 检查员工组是否存在预算余额查询记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param employeeList 员工
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-27 15:12
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateEmpGroup(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            List<ExpEmployee> employeeList, Map balanceQueryParamMap);

    /**
     * 检查岗位是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-27 18:52
     * @return java.lang.Boolean 是否存在
     */
    Boolean validatePosition(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            Map balanceQueryParamMap);

    /**
     * 检查岗位组是否存在预算余额查询记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param positionList 岗位
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-27 19:37
     * @return java.lang.Boolean 是否存在
     */
    Boolean validatePositionGroup(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            List<ExpOrgPosition> positionList, Map balanceQueryParamMap);

    /**
     * 检查员工职务是否与岗位匹配
     *
     * @param request  请求
     * @param bgtBalanceQueryCondition  查询条件
     * @param positionList 岗位
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-27 17:23
     * @return java.lang.Boolean 是否匹配
     */
    Boolean validateEmployeeJob(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            List<ExpOrgPosition> positionList, Map balanceQueryParamMap);

    /**
     * 检查员工级别是否与员工职务匹配
     *
     * @param request  请求
     * @param bgtBalanceQueryCondition  查询条件
     * @param employeeJobList 员工职务
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-28 14:30
     * @return java.lang.Boolean 是否匹配
     */
    Boolean validateEmployeeLevel(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            List<ExpEmployeeJob> employeeJobList, Map balanceQueryParamMap);

    /**
     * 检查部门是否存在预算余额记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-28 18:32
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateOrgUnit(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            Map balanceQueryParamMap);

    /**
     * 检查部门组是否存在预算余额查询记录
     *
     * @param request 请求
     * @param bgtBalanceQueryCondition 查询条件
     * @param unitList 部门
     * @param balanceQueryParamMap 预算余额查询参数Map
     * @author YHL 2019-03-28 19:22
     * @return java.lang.Boolean 是否存在
     */
    Boolean validateUnitGroup(IRequest request, BgtBalanceQueryCondition bgtBalanceQueryCondition,
            List<ExpOrgUnit> unitList, Map balanceQueryParamMap);

}
