package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondH;

/**
 * <p>
 * 预算余额查询方案头mapper
 * </p>
 *
 * @author YHL 2019/03/13 18:22
 */
public interface BgtBalanceQueryCondHMapper extends Mapper<BgtBalanceQueryCondH> {

    /**
     * 根据预算组织ID获取预算余额查询方案
     *
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-18 9:39
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryCondH> 预算余额查询方案
     */
    List<BgtBalanceQueryCondH> getBalanceQueryCondH(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 根据预算余额查询方案代码查询对应预算余额查询方案头ID
     *
     * @param balanceQueryConditionCode 预算余额查询方案代码
     * @author YHL 2019-03-20 16:56
     * @return java.lang.Long 预算余额查询方案头ID
     */
    Long getCondHIdByConditionCode(
            @Param("balanceQueryConditionCode") String balanceQueryConditionCode);

}