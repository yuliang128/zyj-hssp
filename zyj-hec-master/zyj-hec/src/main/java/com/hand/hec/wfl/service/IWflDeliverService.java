package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflDeliver;
import com.hand.hec.wfl.dto.WflInsTaskHierarchy;
import com.hand.hec.wfl.dto.WflInstance;
import uncertain.composite.CompositeMap;

import java.util.List;

public interface IWflDeliverService extends IBaseService<WflDeliver>, ProxySelf<IWflDeliverService>{
    List<WflDeliver> selectWflDeliver(IRequest context, WflDeliver wflDeliver);

    List<WflDeliver> selectWflDeliverLov(IRequest context, WflDeliver wflDeliver);

    List<WflDeliver> getMatchedDeliver(IRequest context, WflInstance instance, WflInsTaskHierarchy insTaskHierarchy);
}