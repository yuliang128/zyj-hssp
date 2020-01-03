package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflInsTaskHierarchyHt;
import com.hand.hec.wfl.mapper.WflInsTaskHierarchyMapper;
import com.hand.hec.wfl.service.IWflInsTaskHierarchyHtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsTaskHierarchy;
import com.hand.hec.wfl.service.IWflInsTaskHierarchyService;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsTaskHierarchyServiceImpl extends BaseServiceImpl<WflInsTaskHierarchy> implements IWflInsTaskHierarchyService{
    @Autowired
    WflInsTaskHierarchyMapper wflInsTaskHierarchyMapper;

    @Autowired
    IWflInsTaskHierarchyHtService wflInsTaskHierarchyHtService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<WflInsTaskHierarchy> selectReachableHierarchy(Long insTaskId) {
        return wflInsTaskHierarchyMapper.selectReachableHierarchy(insTaskId);
    }

    @Override
    public void backupHierarchy(IRequest context, WflInsTaskHierarchy hierarchy) {
        WflInsTaskHierarchyHt insTaskHierarchyHt = new WflInsTaskHierarchyHt(hierarchy);
        wflInsTaskHierarchyHtService.insertSelective(context, insTaskHierarchyHt);
        deleteByPrimaryKey(hierarchy);
    }
}