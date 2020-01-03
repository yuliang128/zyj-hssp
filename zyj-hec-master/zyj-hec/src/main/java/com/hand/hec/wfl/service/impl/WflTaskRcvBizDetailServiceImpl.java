package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflTaskRcvBizDetail;
import com.hand.hec.wfl.service.IWflTaskRcvBizDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflTaskRcvBizDetailServiceImpl extends BaseServiceImpl<WflTaskRcvBizDetail> implements IWflTaskRcvBizDetailService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}