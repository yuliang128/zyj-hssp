package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBudgetReserveInit;
import org.apache.ibatis.annotations.Param;

public interface BgtBudgetReserveInitMapper extends Mapper<BgtBudgetReserveInit> {

    /**
     * 初始化预算占用数据
     *
     * @param group
     * @author mouse 2019-04-18 10:46
     * @return void
     */
    void initReserveData(@Param("group") BgtBalanceQueryGroup group);

    /**
     * 清除预算占用数据
     *
     *
     * @author mouse 2019-04-18 10:47
     * @return void
     */
    void cleanReserveData();
}
