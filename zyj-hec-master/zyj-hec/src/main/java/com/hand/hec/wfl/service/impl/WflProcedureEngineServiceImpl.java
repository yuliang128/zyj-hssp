package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflProcedure;
import com.hand.hec.wfl.service.IWflProcedureEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/5/2
 * \* Time: 14:31
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflProcedureEngineServiceImpl implements IWflProcedureEngineService {

    @Override
    public Object executeProcedure(IRequest context, WflProcedure procedure) {
        return null;
    }
}