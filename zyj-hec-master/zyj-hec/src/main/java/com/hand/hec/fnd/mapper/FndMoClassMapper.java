package com.hand.hec.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndMoClass;

public interface FndMoClassMapper extends Mapper<FndMoClass>{

    @Override
    List<FndMoClass> select(FndMoClass condition);

}