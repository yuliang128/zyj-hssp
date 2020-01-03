package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondition;

import java.util.List;

/**
 * <p>
 * 政策标准明细条件Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/21 10:20
 */

public interface ExpMoExpPolicyConditionMapper extends Mapper<ExpMoExpPolicyCondition> {
    /**
     * 获取所有匹配条件代码
     *
     * @return 返回所有匹配条件代码
     * @author xiuxian.wu 2019/02/21 10:31
     */
    List<CodeValue> queryAllMatchingCondition();
}