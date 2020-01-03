package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryDetailTemp;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import org.apache.ibatis.annotations.Param;

public interface BgtBalanceQueryDetailTempMapper extends Mapper<BgtBalanceQueryDetailTemp> {

    /**
     *
     * 生成最明细的预算数
     *
     * @param group
     * @param filterField
     * @param periodStrategy
     * @author mouse 2019-04-23 16:02
     * @return void
     */
    void generateBalanceDetail(@Param("group") BgtBalanceQueryGroup group, @Param("filterField") String filterField,
                    @Param("periodStrategy") String periodStrategy);

    /**
     *
     * 生成最明细的占用数
     *
     * @param group
     * @param filterField
     * @param periodStrategy
     * @author mouse 2019-04-23 16:03
     * @return void
     */
    void generateReserveDetail(@Param("group") BgtBalanceQueryGroup group, @Param("filterField") String filterField,
                    @Param("periodStrategy") String periodStrategy);

    /**
     * 清除临时明细数
     *
     *
     * @author mouse 2019-04-25 14:38
     * @return void
     */
    void cleanDetailTemp();
}
