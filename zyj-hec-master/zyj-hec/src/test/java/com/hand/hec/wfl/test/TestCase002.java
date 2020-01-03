package com.hand.hec.wfl.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.core.impl.ServiceRequest;
import com.hand.hec.wfl.service.IWflEngineService;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/5/2 \* Time: 15:09 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */
@ContextConfiguration(locations = {"classpath:/spring/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class TestCase002 {
    @Autowired
    IWflEngineService service;

    IRequest request;

    @Before
    public void setup() {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        request.setUserId(10001L);
        RequestHelper.setCurrentRequest(request);
    }

    @Test
    @Rollback(false)
    public void testMain() {
        service.startupInstance(request, "BUDGET_JOURNAL", "12056");
    }
}
