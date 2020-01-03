package com.hand.hec.bgt.service;

import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.dto.BgtControlRuleDetail;

/**
 * description
 *
 * @author mouse 2019/01/09 10:56
 */
public interface IBgtCheckParamService {

    /**
     * 验证预算实体是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetCurrency(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                                 IRequest request);

    /**
     * 验证预算实体是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetStructure(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                                   IRequest request);

    /**
     * 验证预算实体是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetVersion(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                                    IRequest request);

    /**
     * 验证预算实体是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetScenario(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                                  IRequest request);

    /**
     * 验证预算实体是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetEntity(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证预算中心是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetCenter(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证预算项目是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetItem(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证预算项目类型是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetItemType(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证预算期间是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetPeriod(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证预算季度是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetQuarter(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证预算年度是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateBudgetYear(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证维度是否符合控制规则
     *
     * @param dimensionSequence 维度顺序
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateDimension(Long dimensionSequence, BgtControlRuleDetail detail, BgtBudgetReserve reserve,
                    Map checkParamMap, IRequest request);

    /**
     * 验证员工是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateEmployee(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);


    /**
     * 验证员工组是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateEmployeeGroup(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证员工职务是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateEmployeeJob(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证员工级别是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateEmployeeLevel(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证岗位是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validatePosition(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证岗位组是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validatePositionGroup(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证部门是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateUnit(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap, IRequest request);

    /**
     * 验证部门组是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateUnitGroup(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证部门级别是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateUnitLevel(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap,
                    IRequest request);

    /**
     * 验证公司是否符合控制规则
     *
     * @param detail 控制规则明细
     * @param reserve 预算占用
     * @param checkParamMap 预算检查参数Map
     * @param request 请求信息
     * @author mouse 2019-01-09 11:04
     * @return
     */
    Boolean validateCompany(BgtControlRuleDetail detail, BgtBudgetReserve reserve, Map checkParamMap, IRequest request);

}
