package com.hand.hec.csh.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.dto.CshTransactionLine;

/**
 * <p>
 * 现金事务行Mapper
 * </p>
 * 
 * @author Tagin 2019/01/22 19:59
 */
public interface CshTransactionLineMapper extends Mapper<CshTransactionLine> {

    /**
     * 根据预付款现金事务头获取已还款金额【Tips：此处查询未被反冲的已还金额】
     * 
     * @Author Tagin
     * @Date 2019/1/29 18:47
     * @Param transactionHeaderId 预付款现金事务头ID
     * @Version 1.0
     **/
    BigDecimal totalRepayAmount(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * 根据现金事务头ID获取现金事务行
     *
     * @author Tagin
     * @date 2019-03-21 19:32
     * @param transactionHeaderId 现金事务头ID
     * @return List<CshTransactionLine>
     * @version 1.0
     **/
    List<CshTransactionLine> queryTrxLine(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * 获取现金事务预付款现金事务核销记录
     *
     * @author Tagin
     * @date 2019-05-28 11:07
     * @param transactionHeaderId 现金事务头 ID
     * @return java.lang.Long
     * @version 1.0
     **/
    Long totalPreCount(@Param("transactionHeaderId") Long transactionHeaderId);


    List<CshTransactionHeader> queryByReqHdsId(@Param("requisitionHdsId") Long requisitionHdsId);

}
