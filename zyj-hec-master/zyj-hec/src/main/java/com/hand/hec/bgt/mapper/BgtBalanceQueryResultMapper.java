package com.hand.hec.bgt.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBalanceQueryResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BgtBalanceQueryResultMapper extends Mapper<BgtBalanceQueryResult> {

    /**
     * 对结果进行汇总
     *
     * @author mouse 2019-04-24 20:29
     * @return void
     */
    void groupResult(@Param("group") BgtBalanceQueryGroup group, @Param("groupField") String groupField);

    /**
     * 获取最小的期间号
     *
     *
     * @author mouse 2019-04-25 14:22
     * @return java.lang.Long
     */
    Long getMinPeriodNum();

    /**
     * 获取最大的期间号
     *
     *
     * @author mouse 2019-04-25 14:22
     * @return java.lang.Long
     */
    Long getMaxPeriodNum();

    /**
     * 进行期间汇总
     *
     * @param group
     * @param periodDesc
     * @author mouse 2019-04-25 14:10
     * @return void
     */
    void periodSummary(@Param("group") BgtBalanceQueryGroup group, @Param("periodDesc") String periodDesc);

    /**
     * 期间汇总时删除期间描述不为periodDesc
     *
     * @param periodDesc
     * @author guiyuting 2019-05-16 14:10
     * @return 
     */
    void deleteBySessionId(@Param("periodDesc") String periodDesc);

    /**
     * 预算余额查询 - 结果查询
     *
     * @param quantityAmountCode
     * @author guiyuting 2019-05-16 16:52
     * @return
     */
    List<BgtBalanceQueryResult> getBudgetBalanceResult(BgtBalanceQueryResult balanceQueryResult);
}
