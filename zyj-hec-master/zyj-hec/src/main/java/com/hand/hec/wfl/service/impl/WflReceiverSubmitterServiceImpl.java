package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflTaskReceiver;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import org.owasp.esapi.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/28 \* Time: 16:43 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverSubmitterServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IUserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        User user = new User();
        user.setUserId(instance.getCreatedBy());
        user = userService.selectByPrimaryKey(context, user);
        if (user != null) {
            insertReceiverHierarchy(context, instance, insTaskReceiver, user);
        }
    }
}
