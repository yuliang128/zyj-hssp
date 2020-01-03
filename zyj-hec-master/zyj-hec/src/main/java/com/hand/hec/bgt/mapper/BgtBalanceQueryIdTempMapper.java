package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryIdTemp;

public interface BgtBalanceQueryIdTempMapper extends Mapper<BgtBalanceQueryIdTemp>{

    /**
     * 清除ID的临时数据
     *
     *
     * @author mouse 2019-04-25 14:40
     * @return void
     */
    void cleanIdTemp();
}