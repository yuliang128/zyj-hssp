package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshCashFlowFormulaHd;
import org.apache.ibatis.annotations.Param;

public interface CshCashFlowFormulaHdMapper extends Mapper<CshCashFlowFormulaHd>{
    /**
     * 公式查询
     *
     * @param cashFlowItemId  现金流量表Id
     * @author dingwei.ma@hand-china.com 2019-02-21 16:56
     * @return CshCashFlowFormulaHd
     */
    CshCashFlowFormulaHd queryFormulaByItemId(@Param("cashFlowItemId") Long cashFlowItemId);

    /**
     * 查询现金流量表行号
     *
     * @param cashFlowItemId  现金流量表Id
     * @param setOfBooksId  账套Id
     * @author dingwei.ma@hand-china.com 2019-02-21 16:56
     * @return CshCashFlowFormulaHd
     */
    List<Map> queryCashFlowLineNumber(@Param("cashFlowItemId") Long cashFlowItemId, @Param("setOfBooksId") Long setOfBooksId);


}