package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInsTaskHierarchy;
import uncertain.composite.CompositeMap;

import java.util.List;

public interface IWflInsTaskHierarchyService extends IBaseService<WflInsTaskHierarchy>, ProxySelf<IWflInsTaskHierarchyService>{
    List<WflInsTaskHierarchy> selectReachableHierarchy(Long insTaskId);

    void backupHierarchy(IRequest context, WflInsTaskHierarchy hierarchy);
}