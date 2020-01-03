package com.hand.hec.csh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentMethod;

/**
 * <p>
 * 付款方式 Mapper 接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/19 15:21
 */

public interface CshPaymentMethodMapper extends Mapper<CshPaymentMethod>{

    /**
     * 根据管理组织(公司)获取对应付款方式
     *
     * @param magOrgId
     * @param companyId
     * @author jialin.xing@hand-china.com 2019-04-11 16:29
     * @return java.util.List<com.hand.hec.csh.dto.CshPaymentMethod>
     */
    List<CshPaymentMethod> queryPaymentMethods(@Param("magOrgId")Long magOrgId,
                                               @Param("companyId")Long companyId);

}