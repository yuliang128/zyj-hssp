package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.account.dto.User;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflTaskReceiver;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import uncertain.composite.CompositeMap;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/3/28
 * \* Time: 16:38
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public interface IWflReceiverService {

    void insertReceiverHierarchy(IRequest context, WflInstance instance, WflInsTaskReceiver insTaskReceiver, User user);

    void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver, WflInsTaskReceiver insTaskReceiver);
}
