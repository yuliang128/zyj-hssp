package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflProcedure;
import com.hand.hec.wfl.service.IWflProcedureService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflProcedureServiceImpl extends BaseServiceImpl<WflProcedure> implements IWflProcedureService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}