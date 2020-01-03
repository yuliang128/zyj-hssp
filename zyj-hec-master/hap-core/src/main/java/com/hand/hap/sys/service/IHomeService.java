package com.hand.hap.sys.service;

import java.math.BigDecimal;

import com.hand.hap.core.IRequest;

/**
 * @desc:
 * @author: hao.zhou@hand-china.com
 * @date: 2019/6/19
 */
public interface IHomeService {

    /**
     * 获取代办事项数量
     *
     * @param request
     * @return java.math.BigDecimal
     * @author mouse 2019-06-19 19:18
     */
    public Long getTodoCount(IRequest request);

    /**
     * 获取退回单据数量
     *
     * @param request
     * @return java.math.BigDecimal
     * @author mouse 2019-06-19 19:18
     */
    public Long getReturnCount(IRequest request);

    /**
     * 获取未支付金额
     *
     * @param request
     * @return java.math.BigDecimal
     * @author mouse 2019-06-19 19:19
     */
    public BigDecimal getUnpaidAmount(IRequest request);

    /**
     * 获取未核销金额
     *
     * @param request
     * @return java.math.BigDecimal
     * @author mouse 2019-06-19 19:19
     */
    public BigDecimal getUnWriteOffAmount(IRequest request);
}
