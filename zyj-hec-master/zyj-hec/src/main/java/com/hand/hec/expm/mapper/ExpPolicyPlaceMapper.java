package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpPolicyPlace;

import java.util.List;

public interface ExpPolicyPlaceMapper extends Mapper<ExpPolicyPlace>{

    /**
     * 页面初始查询
     * @author Zhongyu 2019-2-20 17:00
     * @return
     */
    List<ExpPolicyPlace> queryPolicyPlace(ExpPolicyPlace expPolicyPlace);
}