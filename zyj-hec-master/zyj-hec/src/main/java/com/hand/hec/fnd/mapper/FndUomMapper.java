package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndUom;

import java.util.List;

public interface FndUomMapper extends Mapper<FndUom>{


    @Override
    List<FndUom> select(FndUom dto);
}