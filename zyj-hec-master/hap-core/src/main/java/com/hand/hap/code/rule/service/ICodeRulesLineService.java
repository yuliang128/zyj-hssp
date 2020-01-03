package com.hand.hap.code.rule.service;

import com.hand.hap.code.rule.dto.CodeRulesLine;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

public interface ICodeRulesLineService extends IBaseService<CodeRulesLine>, ProxySelf<ICodeRulesLineService> {

    CodeRulesLine updateRecord(CodeRulesLine record);
}