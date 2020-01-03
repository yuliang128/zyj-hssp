package com.hand.hec.bgt.test;

import java.math.BigDecimal;

import org.junit.After;
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
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.dto.BgtCheckResult;
import com.hand.hec.bgt.mapper.BgtCheckMapper;
import com.hand.hec.bgt.service.IBgtCheckService;

/**
 * description
 *
 * @author 52552 2019/02/21 10:31
 */
@ContextConfiguration(locations = {"classpath:/spring/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class BgtCheckServiceImplTest {

    @Autowired
    BgtCheckMapper checkMapper;

    @Autowired
    IBgtCheckService checkService;

    IRequest request;

    BgtBudgetReserve reserve;

    @Before
    public void setUp() throws Exception {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void check() throws Exception {

        reserve = new BgtBudgetReserve(30084L, 10791L, null, 10741L, 11305L, 14485L, "2019-03", null, "EXP_REQUISITION",
                "R", "N", "N", 15006L, 15129L, "JPY", 11945L, 11146L, "ZYJ_CURRENCY_0020", new BigDecimal("0.05"),
                new BigDecimal("1500"), new BigDecimal("75"), new BigDecimal("1"), null, 10791L, null, 11606L, 3564L,
                2621L, 383L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, 2019L, 1L, 20190003L,null,null);

        String ignoreWarning = "Y";

        BgtCheckResult result = checkService.check(reserve, ignoreWarning, request);
        System.out.println(result);
    }

}