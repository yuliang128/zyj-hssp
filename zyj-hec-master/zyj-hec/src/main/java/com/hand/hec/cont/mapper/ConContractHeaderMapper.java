package com.hand.hec.cont.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.cont.dto.ConContractHeader;

public interface ConContractHeaderMapper extends Mapper<ConContractHeader> {

    /**
     * 根据现金事务币种获取合同头信息
     *
     * @author Tagin
     * @date 2019-04-03 17:06
     * @param transactionLineId 现金事务行ID
     * @return List<ConContractHeader>
     * @version 1.0
     **/
    List<ConContractHeader> queryContractHeaderByTrx(@Param("transactionLineId") Long transactionLineId);

}
