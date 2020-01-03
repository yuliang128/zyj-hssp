package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentBatchTpRule;

import java.util.List;

public interface CshPaymentBatchTpRuleMapper extends Mapper<CshPaymentBatchTpRule>{
    /**
     *付款批合并规则ds查询
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/4 19:02
     *@param cshPaymentBatchTpRule 查询条件
     *@return List<CshPaymentBatchTpRule>
     *@Version 1.0
     **/
    List<CshPaymentBatchTpRule> query(CshPaymentBatchTpRule cshPaymentBatchTpRule);
}