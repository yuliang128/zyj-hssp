package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflElement;
import com.hand.hec.wfl.mapper.WflElementMapper;
import com.hand.hec.wfl.service.IWflElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflElementServiceImpl extends BaseServiceImpl<WflElement> implements IWflElementService{
    @Autowired
    WflElementMapper wflElementMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public List<WflElement> processElementQuery(WflElement record){
        return wflElementMapper.processElementQuery(record);

    }
}