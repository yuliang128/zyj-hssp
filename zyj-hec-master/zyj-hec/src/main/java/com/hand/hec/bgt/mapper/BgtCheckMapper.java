package com.hand.hec.bgt.mapper;

import com.hand.hec.bgt.dto.BgtCheckBalanceResult;
import com.hand.hec.bgt.dto.BgtCheckCondition;
import com.hand.hec.bgt.dto.BgtCheckReserveResult;

import java.math.BigDecimal;

/**
 * description
 *
 * @author mouse 2019/01/22 18:50
 */
public interface BgtCheckMapper {


    /**
     * 获取预算余额金额
     *
     * @param condition 预算检查参数
     * @author mouse 2019-01-22 18:57
     * @return
     */
    BgtCheckBalanceResult getBalanceAmount(BgtCheckCondition condition);

    /**
     * 获取预算占用金额
     *
     * @param condition 预算检查参数
     * @author mouse 2019-01-22 18:57
     * @return
     */
    BgtCheckReserveResult getReserveAmount(BgtCheckCondition condition);
}
