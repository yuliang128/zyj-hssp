package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpLevelSeries;

import java.util.List;

public interface ExpLevelSeriesMapper extends Mapper<ExpLevelSeries>{
    /**
     * 级别系列查询
     *
     * @author ZhongYu 20190-02-14 16:57
     * @return 级别系列
     */
    List<ExpLevelSeries> queryExpLevelSeries();

}