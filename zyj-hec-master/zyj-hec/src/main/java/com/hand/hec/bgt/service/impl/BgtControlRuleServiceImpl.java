package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtControlRule;
import com.hand.hec.bgt.service.IBgtControlRuleService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算控制规则ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtControlRuleServiceImpl extends BaseServiceImpl<BgtControlRule> implements IBgtControlRuleService{

    protected boolean useSelectiveUpdate() {
        return false;
    }

}