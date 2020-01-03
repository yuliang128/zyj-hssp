package com.hand.hec.evt.service;

import com.hand.hap.core.IRequest;

import java.util.Map;

/**
 * <p>
 * 事件处理引擎
 * </p>
 * 
 * @author mouse 2019/04/09 15:20
 */
public interface IEvtEventEngine {

    /**
     * 触发事件
     *
     * @param eventCode 事件代码
     * @param param 参数Map
     * @author mouse 2019-04-09 17:08
     * @return void
     */
    void fireEvent(IRequest request, String eventCode, Map param);
}
