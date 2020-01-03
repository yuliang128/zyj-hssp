package com.hand.hec.wfl.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;

/**
 * 归口负责人
 *
 * @author mouse 2019/03/06 13:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverCentralizedEmployeeServiceImpl extends WflReceiverBaseServiceImpl  {

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver, WflInsTaskReceiver insTaskReceiver) {

    }
}
