package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshCashFlowFormulaLn;
import org.apache.ibatis.annotations.Param;

public interface CshCashFlowFormulaLnMapper extends Mapper<CshCashFlowFormulaLn>{
    
    /**
     * 现金流向项行查询
     *
     * @param cashFlowItemId 现金流量项Id
     * @author dingwei.ma@hand-china.com 2019-02-21 17:32
     * @return List<CshCashFlowFormulaLn>
     */
    List<Map> queryByItemId(@Param("cashFlowItemId") Long cashFlowItemId);
}