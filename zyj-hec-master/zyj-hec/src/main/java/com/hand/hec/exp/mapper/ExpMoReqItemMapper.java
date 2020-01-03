package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReqItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpMoReqItemMapper extends Mapper<ExpMoReqItem>{

    /**
     * <p>根据报销类型获取费用项目</p>
     *
     * @param moExpenseTypeId
     * @return List<ExpMoReqItem>
     * @author yang.duan 2019/4/29 11:30
    **/
    List<ExpMoReqItem> getReqItemByExpenseType(@Param("moExpenseTypeId") Long moExpenseTypeId);
}