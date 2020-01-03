package com.hand.hec.csh.mapper;

import com.hand.hec.csh.dto.CshTransactionHeader;

import java.math.BigDecimal;

/**
 * 现金事物处理Mapper
 *
 * @author mouse 2019/05/07 10:05
 */
public interface CshTransactionMapper {

    /**
     * 获取某个现金事物的已退款金额合计
     *
     * @param header
     * @author mouse 2019-05-07 10:08
     * @return java.math.BigDecimal
     */
    BigDecimal getReturnedAmount(CshTransactionHeader header);
}
