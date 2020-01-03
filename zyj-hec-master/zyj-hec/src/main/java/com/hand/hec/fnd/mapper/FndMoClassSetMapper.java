package com.hand.hec.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndMoClassSet;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/23 19:22
 */
public interface FndMoClassSetMapper extends Mapper<FndMoClassSet>{

    List<FndMoClassSet> baseSelect(FndMoClassSet dto);

}