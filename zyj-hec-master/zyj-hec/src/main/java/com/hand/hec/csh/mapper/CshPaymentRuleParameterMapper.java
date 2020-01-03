package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentRuleParameter;

public interface CshPaymentRuleParameterMapper extends Mapper<CshPaymentRuleParameter>{

    /**
     * 支付权限规则参数代码定义-核对sql是否正确
     *
     * @param sqlContents sql语句
     * @author ngls.luhui 2019-02-27 12:10
     * @return 正确返回true
     */
    String checkSql(@Param("sqlContents") String sqlContents);

}