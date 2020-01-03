package com.hand.hec.bgt.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondL;

/**
 * <p>
 * 预算余额查询方案行service
 * </p>
 *
 * @author YHL 2019/03/13 18:22
 */
public interface IBgtBalanceQueryCondLService
        extends IBaseService<BgtBalanceQueryCondL>, ProxySelf<IBgtBalanceQueryCondLService> {

    /**
     * 获取取值范围
     *
     * @param request
     * @author YHL 2019-03-18 14:19
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 取值范围
     */
    List<Map<String, Object>> getControlRuleRange(IRequest request);

    /**
     * 根据预算余额查询方案头ID和预算组织ID查询预算余额查询方案（预算参数）
     *
     * @param request
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-18 16:15
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLBgt(IRequest request, Long balanceQueryCondHId, Long bgtOrgId);

    /**
     * 根据预算余额查询方案头ID、公司ID和管理组织ID获取预算余额查询方案（组织相关）
     *
     * @param request
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-18 18:41
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLOrg(IRequest request, Long balanceQueryCondHId);

    /**
     * 根据预算余额查询方案头ID和公司ID获取预算余额查询方案（维度相关）
     *
     * @param request
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-18 19:04
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLDim(IRequest request, Long balanceQueryCondHId);

    /**
     * 插入预算余额查询方案行
     *
     * @param request
     * @param balanceQueryConditionCode 预算余额查询方案代码
     * @param sessionId 系统Session ID
     * @param parameter 参数类型
     * @param controlRuleRange 取值范围
     * @param parameterCode 参数代码
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-21 14:05
     * @return void
     */
    void insertBalanceQueryCondL(IRequest request, String balanceQueryConditionCode, String sessionId, String parameter,
            String controlRuleRange, String parameterCode, String parameterLowerLimit, String parameterUpperLimit);

    /**
     * 根据预算余额查询方案头ID查询预算余额查询方案行
     *
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-21 18:31
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案行
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLByCondHId(Long balanceQueryCondHId);

}