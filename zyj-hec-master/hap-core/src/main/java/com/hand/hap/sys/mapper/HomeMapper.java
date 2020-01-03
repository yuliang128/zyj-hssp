package com.hand.hap.sys.mapper;

import java.math.BigDecimal;

/**
 * @desc:
 * @author: hao.zhou@hand-china.com
 * @date: 2019/6/19
 */
public interface HomeMapper {

    Long getTodoCount();

    Long getReturnCount();

    BigDecimal getUnpaidAmount();

    BigDecimal getUnWriteOffAmount();
}
