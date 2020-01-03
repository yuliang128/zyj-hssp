package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVerTaskRcvBizDetail;
import com.hand.hec.wfl.service.IWflVerTaskRcvBizDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 18:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVerTaskRcvBizDetailServiceImpl extends BaseServiceImpl<WflVerTaskRcvBizDetail>
                implements IWflVerTaskRcvBizDetailService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
