package com.hand.hec.csh.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoTransactionClass;

/**
 * <p>
 * 现金事物分类定义mapper
 * </p>
 *
 * @author LJK 2019/02/19 12:05
 */
public interface CshMoTransactionClassMapper extends Mapper<CshMoTransactionClass>{

    /**
     * 还款申请单借款行明细-行借款类型查询
     *
     * @author jialin.xing@hand-china.com 2019-05-29 23:31
     * @return java.util.List<com.hand.hec.csh.dto.CshMoTransactionClass>
     */
    List<CshMoTransactionClass> queryTrxClass();
}