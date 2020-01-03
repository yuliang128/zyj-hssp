package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSobRuleGpRefAe;

import java.util.List;

public interface GldSobRuleGpRefAeMapper extends Mapper<GldSobRuleGpRefAe>{

	List<GldSobRuleGpRefAe> queryNotRefAE(GldSobRuleGpRefAe condition);
}