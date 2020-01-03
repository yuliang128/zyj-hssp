package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBalanceQuerySummaryTemp;
import org.apache.ibatis.annotations.Param;

public interface BgtBalanceQuerySummaryTempMapper extends Mapper<BgtBalanceQuerySummaryTemp> {

    void generateSummary(@Param("group") BgtBalanceQueryGroup group, @Param("filterField") String filterField);

    /**
     * 清除汇总临时数据
     *
     *
     * @author mouse 2019-04-25 14:49
     * @return void
     */
    void cleanSummaryTemp();
}
