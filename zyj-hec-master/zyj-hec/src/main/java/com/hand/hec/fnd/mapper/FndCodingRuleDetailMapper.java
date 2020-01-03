package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCodingRuleDetail;

import java.util.List;

public interface FndCodingRuleDetailMapper extends Mapper<FndCodingRuleDetail>{


    /**
     * 按照顺序号排序查询结果
     * @param dto
     * @return
     * @author zhongyu 2019-2-25 15:08
     */
    List<FndCodingRuleDetail> queryBySequence(FndCodingRuleDetail dto);
}