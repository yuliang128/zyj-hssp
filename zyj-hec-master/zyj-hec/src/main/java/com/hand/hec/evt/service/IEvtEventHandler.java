package com.hand.hec.evt.service;

        import java.util.Map;

/**
 * <p>
 * 事件处理器接口，所有的处理器需要实现该接口
 * </p>
 *
 * @author mouse 2019/04/09 15:18
 */
public interface IEvtEventHandler {

    String handle(Map paramMap);
}
