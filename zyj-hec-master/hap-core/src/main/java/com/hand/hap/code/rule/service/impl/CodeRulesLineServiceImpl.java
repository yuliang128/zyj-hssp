package com.hand.hap.code.rule.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.code.rule.dto.CodeRulesLine;
import com.hand.hap.code.rule.service.ICodeRulesLineService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class CodeRulesLineServiceImpl extends BaseServiceImpl<CodeRulesLine> implements ICodeRulesLineService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public CodeRulesLine updateRecord(CodeRulesLine record) {
        mapper.updateByPrimaryKey(record);
        return record;
    }
}