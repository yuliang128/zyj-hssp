package com.hand.hap.code.rule.mapper;

import com.hand.hap.code.rule.dto.CodeRulesLine;
import com.hand.hap.mybatis.common.Mapper;

public interface CodeRulesLineMapper extends Mapper<CodeRulesLine> {

    int deleteByHeaderId(CodeRulesLine line);
}