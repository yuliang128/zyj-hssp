package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVerGtwBizRuleDetail;
import com.hand.hec.wfl.service.IWflVerGtwBizRuleDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 19:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVerGtwBizRuleDetailServiceImpl extends BaseServiceImpl<WflVerGtwBizRuleDetail>
                implements IWflVerGtwBizRuleDetailService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
