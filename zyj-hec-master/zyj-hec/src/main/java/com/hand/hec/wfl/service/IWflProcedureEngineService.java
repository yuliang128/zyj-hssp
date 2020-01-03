package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflProcedure;
import uncertain.composite.CompositeMap;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/5/2
 * \* Time: 14:30
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public interface IWflProcedureEngineService {

    Object executeProcedure(IRequest context, WflProcedure procedure);
}
