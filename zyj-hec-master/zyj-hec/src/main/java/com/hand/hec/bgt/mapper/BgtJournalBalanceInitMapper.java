package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtJournalBalanceInit;
import org.apache.ibatis.annotations.Param;

public interface BgtJournalBalanceInitMapper extends Mapper<BgtJournalBalanceInit> {
    /**
     * 初始化预算余额数据
     *
     * @param group
     * @author mouse 2019-04-18 11:08
     * @return void
     */
    void initBalanceData(@Param("group") BgtBalanceQueryGroup group);

    /**
     * 清理预算余额数据
     *
     *
     * @author mouse 2019-04-18 11:08
     * @return void
     */
    void clearBalanceData();
}
