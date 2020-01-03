package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpDocumentHistoryMapper extends Mapper<ExpDocumentHistory> {

    /**
     * 根据id查询借款申请单历史
     *
     * @param cshPayHeaderId 借款申请单id
     * @author LJK 2019-03-01 15:05
     * @return
     */
    List<ExpDocumentHistory> queryPayReqHistory(@Param("cshPayHeaderId") Long cshPayHeaderId);

}
