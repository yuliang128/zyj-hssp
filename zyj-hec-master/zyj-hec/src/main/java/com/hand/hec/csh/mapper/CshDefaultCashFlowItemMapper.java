package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshDefaultCashFlowItem;
import org.apache.ibatis.annotations.Param;

public interface CshDefaultCashFlowItemMapper extends Mapper<CshDefaultCashFlowItem>{
    /**
     * 现金流量项分配默认科目查询
     *
     * @param record    现金流量项分配默认科目实体类
     * @author dingwei.ma@hand-china.com 2019-02-22 14:51
     * @return List<Map>
     */

    List<Map> queryByItemId(CshDefaultCashFlowItem record);

    /**
     * 科目查询
     *
     * @param setOfBooksId   账套id
     * @param accountCodeFrom   科目代码从
     * @param accountCodeTo  科目代码到
     * @author dingwei.ma@hand-china.com 2019-02-22 14:51
     * @return List<Map>
     */
    List<Map> queryByAccount(@Param("setOfBooksId") Long setOfBooksId,@Param("accountCodeFrom") String accountCodeFrom,@Param("accountCodeTo") String accountCodeTo);
}