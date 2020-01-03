package com.hand.hec.bgt.test;

import java.util.ArrayList;
import java.util.List;

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
import com.hand.hec.bgt.dto.BgtBalanceQueryCondition;
import com.hand.hec.bgt.dto.BgtBalanceQueryData;
import com.hand.hec.bgt.service.IBgtBalanceQueryService;

/**
 * <p>
 * 预算余额查询功能测试
 * </p>
 *
 * @author YHL 2019/03/25 19:06
 */
@ContextConfiguration(locations = {"classpath:/spring/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class BgtBalanceQueryServiceImplTest {

    @Autowired
    private IBgtBalanceQueryService service;

    private List<BgtBalanceQueryCondition> bgtBalanceQueryConditionList = new ArrayList<>();

    private IRequest request;

    @Before
    public void setUp() throws Exception {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        request.setMagOrgId(271L);
        request.setCompanyId(10791L);
        RequestHelper.setCurrentRequest(request);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void bgtBalanceQuery() throws Exception {
/*
        BgtBalanceQueryCondition condition1 = new BgtBalanceQueryCondition();
        condition1.setParameter("COMPANY");
        condition1.setControlRuleRange("ALL");
        condition1.setParameterCode("ZYJ_001");
        condition1.setParameterLowerLimit("ZYJ_001");
        condition1.setParameterUpperLimit("ZYJ_001");

        BgtBalanceQueryCondition condition2 = new BgtBalanceQueryCondition();
        condition2.setParameter("BGT_ORG");
        condition2.setControlRuleRange("ALL");
        condition2.setParameterCode("ZYJ_001");
        condition2.setParameterLowerLimit("ZYJ_001");
        condition2.setParameterUpperLimit("ZYJ_001");

        BgtBalanceQueryCondition condition3 = new BgtBalanceQueryCondition();
        condition3.setParameter("BUDGET_STRUCTURE");
        condition3.setControlRuleRange("ALL");
        condition3.setParameterCode("ZYJ_BO_S001");
        condition3.setParameterLowerLimit("ZYJ_BO_S001");
        condition3.setParameterUpperLimit("ZYJ_BO_S001");

        BgtBalanceQueryCondition condition4 = new BgtBalanceQueryCondition();
        condition4.setParameter("BUDGET_SCENARIO");
        condition4.setControlRuleRange("ALL");
        condition4.setParameterCode("ZYJ_001");
        condition4.setParameterLowerLimit("ZYJ_001");
        condition4.setParameterUpperLimit("ZYJ_001");

        BgtBalanceQueryCondition condition5 = new BgtBalanceQueryCondition();
        condition5.setParameter("BUDGET_VERSION");
        condition5.setControlRuleRange("ALL");
        condition5.setParameterCode("ZYJ_001");
        condition5.setParameterLowerLimit("ZYJ_001");
        condition5.setParameterUpperLimit("ZYJ_001");

        BgtBalanceQueryCondition condition6 = new BgtBalanceQueryCondition();
        condition6.setParameter("BGT_ENTITY");
        condition6.setControlRuleRange("ALL");
        condition6.setParameterCode("ZYJ_001");
        condition6.setParameterLowerLimit("ZYJ_001");
        condition6.setParameterUpperLimit("ZYJ_001");

        BgtBalanceQueryCondition condition7 = new BgtBalanceQueryCondition();
        condition7.setParameter("BUDGET_CURRENCY");
        condition7.setControlRuleRange("ALL");
        condition7.setParameterCode("CNY");
        condition7.setParameterLowerLimit("CNY");
        condition7.setParameterUpperLimit("CNY");

        BgtBalanceQueryCondition condition8 = new BgtBalanceQueryCondition();
        condition8.setParameter("PERIOD");
        condition8.setControlRuleRange("ALL");
        condition8.setParameterCode(null);
        condition8.setParameterLowerLimit("2019-01");
        condition8.setParameterUpperLimit("2019-03");

        BgtBalanceQueryCondition condition9 = new BgtBalanceQueryCondition();
        condition9.setParameter("PERIOD_QUARTER");
        condition9.setControlRuleRange("ALL");
        condition9.setParameterCode(null);
        condition9.setParameterLowerLimit("1");
        condition9.setParameterUpperLimit("1");

        BgtBalanceQueryCondition condition10 = new BgtBalanceQueryCondition();
        condition10.setParameter("PERIOD_YEAR");
        condition10.setControlRuleRange("ALL");
        condition10.setParameterCode(null);
        condition10.setParameterLowerLimit("2019");
        condition10.setParameterUpperLimit("2019");

        BgtBalanceQueryCondition condition11 = new BgtBalanceQueryCondition();
        condition11.setParameter("BGT_CENTER");
        condition11.setControlRuleRange("SUMMARY");
        condition11.setParameterCode(null);
        condition11.setParameterLowerLimit("ZYJ_001");
        condition11.setParameterUpperLimit("ZYJ_002");

        BgtBalanceQueryCondition condition12 = new BgtBalanceQueryCondition();
        condition12.setParameter("BUDGET_ITEM");
        condition12.setControlRuleRange("ALL");
        condition12.setParameterCode(null);
        condition12.setParameterLowerLimit("ZYJ_001");
        condition12.setParameterUpperLimit("ZYJ_003");

        BgtBalanceQueryCondition condition13 = new BgtBalanceQueryCondition();
        condition13.setParameter("BUDGET_ITEM_TYPE");
        condition13.setControlRuleRange("ALL");
        condition13.setParameterCode(null);
        condition13.setParameterLowerLimit("ZYJ_BO_666");
        condition13.setParameterUpperLimit("ZYJ_BO_999");

        //以下条件需要先确认数据中是否有对应数据
        BgtBalanceQueryCondition condition14 = new BgtBalanceQueryCondition();
        condition14.setParameter("EMPLOYEE");
        condition14.setControlRuleRange("ALL");
        condition14.setParameterCode(null);
        condition14.setParameterLowerLimit("XXX");
        condition14.setParameterUpperLimit("ZYJYSCS");

        BgtBalanceQueryCondition condition15 = new BgtBalanceQueryCondition();
        condition15.setParameter("EMPLOYEE_GROUP");
        condition15.setControlRuleRange("ALL");
        condition15.setParameterCode(null);
        condition15.setParameterLowerLimit("TXL_001");
        condition15.setParameterUpperLimit("ZYJ_001");

        BgtBalanceQueryCondition condition16 = new BgtBalanceQueryCondition();
        condition16.setParameter("ORG_POSITION");
        condition16.setControlRuleRange("ALL");
        condition16.setParameterCode(null);
        condition16.setParameterLowerLimit("ZYJ_001");
        condition16.setParameterUpperLimit("ZYJ_003");

        BgtBalanceQueryCondition condition17 = new BgtBalanceQueryCondition();
        condition17.setParameter("ORG_POSITION_GROUP");
        condition17.setControlRuleRange("ALL");
        condition17.setParameterCode("ZYJ_001");
        condition17.setParameterLowerLimit(null);
        condition17.setParameterUpperLimit(null);

        BgtBalanceQueryCondition condition18 = new BgtBalanceQueryCondition();
        condition18.setParameter("EMPLOYEE_JOB");
        condition18.setControlRuleRange("ALL");
        condition18.setParameterCode(null);
        condition18.setParameterLowerLimit("ZYJ_001");
        condition18.setParameterUpperLimit("ZYJ_003");

        BgtBalanceQueryCondition condition19 = new BgtBalanceQueryCondition();
        condition19.setParameter("EMPLOYEE_LEVEL");
        condition19.setControlRuleRange("ALL");
        condition19.setParameterCode(null);
        condition19.setParameterLowerLimit("10");
        condition19.setParameterUpperLimit("30");

        BgtBalanceQueryCondition condition20 = new BgtBalanceQueryCondition();
        condition20.setParameter("ORG_UNIT");
        condition20.setControlRuleRange("ALL");
        condition20.setParameterCode(null);
        condition20.setParameterLowerLimit("ZYJ_001");
        condition20.setParameterUpperLimit("ZYJ_003");

        BgtBalanceQueryCondition condition21 = new BgtBalanceQueryCondition();
        condition21.setParameter("ORG_UNIT_GROUP");
        condition21.setControlRuleRange("ALL");
        condition21.setParameterCode("ZYJ_001");
        condition21.setParameterLowerLimit(null);
        condition21.setParameterUpperLimit(null);

        bgtBalanceQueryConditionList.add(condition1);
        bgtBalanceQueryConditionList.add(condition2);
        bgtBalanceQueryConditionList.add(condition3);
        bgtBalanceQueryConditionList.add(condition4);
        bgtBalanceQueryConditionList.add(condition5);
        bgtBalanceQueryConditionList.add(condition6);
        bgtBalanceQueryConditionList.add(condition7);
        bgtBalanceQueryConditionList.add(condition8);
        bgtBalanceQueryConditionList.add(condition9);
        bgtBalanceQueryConditionList.add(condition10);
        bgtBalanceQueryConditionList.add(condition11);
        bgtBalanceQueryConditionList.add(condition12);
        bgtBalanceQueryConditionList.add(condition13);

        bgtBalanceQueryConditionList.add(condition14);
        bgtBalanceQueryConditionList.add(condition15);
        bgtBalanceQueryConditionList.add(condition16);
        bgtBalanceQueryConditionList.add(condition17);
        bgtBalanceQueryConditionList.add(condition18);
        bgtBalanceQueryConditionList.add(condition19);
        bgtBalanceQueryConditionList.add(condition20);
        bgtBalanceQueryConditionList.add(condition21);

        service.bgtBalanceQuery(request, bgtBalanceQueryConditionList);*/

    }
}