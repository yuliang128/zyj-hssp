package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflPage;
import com.hand.hec.wfl.service.IWflPageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflPageServiceImpl extends BaseServiceImpl<WflPage> implements IWflPageService {
    @Autowired
    public WflPageMapper wflPageMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public List<WflPage> selectWflPage(WflPage wflPage){

        return wflPageMapper.selectWflPage(wflPage);
    }
}