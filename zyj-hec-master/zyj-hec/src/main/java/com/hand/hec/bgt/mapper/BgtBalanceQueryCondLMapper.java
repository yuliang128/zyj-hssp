package com.hand.hec.bgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondL;

/**
 * <p>
 * 预算余额查询方案行mapper
 * </p>
 *
 * @author YHL 2019/03/13 18:22
 */
public interface BgtBalanceQueryCondLMapper extends Mapper<BgtBalanceQueryCondL> {

    /**
     * 获取取值范围
     *
     * @author YHL 2019-03-18 14:28
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 取值范围
     */
    List<Map<String, Object>> getControlRuleRange();

    /**
     * 根据预算余额查询方案头ID和预算组织ID获取预算余额查询方案（预算参数）
     *
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-18 16:12
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLBgt(@Param("balanceQueryCondHId") Long balanceQueryCondHId,
            @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 根据预算余额查询方案头ID、公司ID和管理组织ID获取预算余额查询方案（组织相关）
     *
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-18 18:39
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLOrg(@Param("balanceQueryCondHId") Long balanceQueryCondHId);

    /**
     * 根据预算余额查询方案头ID和公司ID获取预算余额查询方案（维度相关）
     *
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-18 19:03
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLDim(@Param("balanceQueryCondHId") Long balanceQueryCondHId);

    /**
     * 根据预算余额查询方案头ID查询预算余额查询方案行
     *
     * @param balanceQueryCondHId 预算余额查询方案头ID
     * @author YHL 2019-03-21 18:33
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondL> 预算余额查询方案行
     */
    List<BgtBalanceQueryCondL> getBalanceQueryCondLByCondHId(@Param("balanceQueryCondHId") Long balanceQueryCondHId);

}