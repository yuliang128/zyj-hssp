package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtScenario;

/**
 * <p>
 * 预算场景Service
 * </p>
 *
 * @author mouse 2019/01/07 17:04
 */
public interface IBgtScenarioService extends IBaseService<BgtScenario>, ProxySelf<IBgtScenarioService> {

    /**
     * 预算检查的预算场景符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param scenarioCodeFrom 控制规则预算场景代码从
     * @param scenarioCodeTo 控制规则预算场景代码到
     * @author YHL 2019-03-05 18:36
     * @return java.util.List<com.hand.hec.bgt.dto.BgtScenario> 符合的预算场景
     */
    List<BgtScenario> checkBgtScenario(String controlType, String filtrateMethod, String scenarioCodeFrom,
            String scenarioCodeTo);

    /**
     * 根据预算组织ID查询默认预算场景
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 15:40
     * @return com.hand.hec.bgt.dto.BgtScenario 该预算组织的默认预算场景
     */
    BgtScenario getDefaultBgtScenarioByBgtOrgId(IRequest request, Long bgtOrgId);

    /**
     * 根据预算组织ID获取预算场景下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 15:48
     * @return java.util.List<com.hand.hec.bgt.dto.BgtScenario> 该预算组织的预算场景
     */
    List<BgtScenario> getBgtScenarioOption(IRequest request, Long bgtOrgId);

}