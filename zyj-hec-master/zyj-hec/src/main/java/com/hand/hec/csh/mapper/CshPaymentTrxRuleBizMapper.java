package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentTrxRuleBiz;

public interface CshPaymentTrxRuleBizMapper extends Mapper<CshPaymentTrxRuleBiz> {

    /**
     * @Description 获取付款事务生成规则的业务范围
     *
     * @Author Tagin
     * @Date 2019-03-13 18:40
     * @param magOrgId 管理组织ID
     * @param documentCategory 单据类别
     * @param accEntityId 核算主体ID
     * @Return List<CshPaymentTrxRuleBiz>
     * @Version 1.0
     **/
    List<CshPaymentTrxRuleBiz> getTrxRuleBiz(@Param("magOrgId") Long magOrgId,
                    @Param("documentCategory") String documentCategory, @Param("accEntityId") Long accEntityId);

}
