package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVerGtwBizRuleGroup;
import com.hand.hec.wfl.service.IWflVerGtwBizRuleGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 18:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVerGtwBizRuleGroupServiceImpl extends BaseServiceImpl<WflVerGtwBizRuleGroup> implements IWflVerGtwBizRuleGroupService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
