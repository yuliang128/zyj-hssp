package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentRuleDetail;
import org.apache.ibatis.annotations.Param;

public interface CshPaymentRuleDetailMapper extends Mapper<CshPaymentRuleDetail>{

    /**
     *
     * 校验函数
     *
     * 检验其中的左右括号和and or是否匹配，匹配则返回true 不匹配返回false
     */
    public int check(@Param("str")String str);
}