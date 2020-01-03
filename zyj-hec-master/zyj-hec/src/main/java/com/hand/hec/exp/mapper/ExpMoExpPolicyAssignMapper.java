package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpPolicyAssign;

import java.util.List;

public interface ExpMoExpPolicyAssignMapper extends Mapper<ExpMoExpPolicyAssign>{

    @Override
    List<ExpMoExpPolicyAssign> select(ExpMoExpPolicyAssign dto) ;
}