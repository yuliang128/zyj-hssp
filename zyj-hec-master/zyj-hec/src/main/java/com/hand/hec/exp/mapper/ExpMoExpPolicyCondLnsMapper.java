package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondLns;

import java.util.List;

/**
 * <p>
 * 政策标准明细条件行Mapper
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */
public interface ExpMoExpPolicyCondLnsMapper extends Mapper<ExpMoExpPolicyCondLns> {
    /**
     * 查询标准明细条件所有的数据
     *
     * @param condition 查询条件
     * @return 返回所有明细条件数据
     * @author xiuxian.wu 2019-02-21 16:26
     */
    List<ExpMoExpPolicyCondLns> queryPolicyCondLns(ExpMoExpPolicyCondLns condition);
}