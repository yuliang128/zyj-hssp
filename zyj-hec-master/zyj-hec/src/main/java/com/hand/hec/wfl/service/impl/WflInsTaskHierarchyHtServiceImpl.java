package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTaskHierarchyHt;
import com.hand.hec.wfl.service.IWflInsTaskHierarchyHtService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskHierarchyHtServiceImpl extends BaseServiceImpl<WflInsTaskHierarchyHt> implements IWflInsTaskHierarchyHtService{

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}