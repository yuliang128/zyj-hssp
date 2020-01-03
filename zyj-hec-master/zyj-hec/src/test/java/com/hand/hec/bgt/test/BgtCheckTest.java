package com.hand.hec.bgt.test;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.mapper.RoleMapper;
import com.hand.hap.account.mapper.UserMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.core.impl.ServiceRequest;
import com.hand.hec.bgt.dto.BgtCheckCondition;
import com.hand.hec.bgt.dto.BgtCheckParamValue;
import com.hand.hec.bgt.mapper.BgtCheckMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hailor on 16/9/21.
 */
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class BgtCheckTest {

    @Autowired
    BgtCheckMapper checkMapper;

    IRequest request;


    @Before
    public void setUp() {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);

    }

    @Test
    public void testprofile() throws Exception {
        BgtCheckCondition condition = new BgtCheckCondition();
        Boolean detailFlag = false;

        BgtCheckParamValue controlStrategy = new BgtCheckParamValue();
        BgtCheckParamValue period = new BgtCheckParamValue();
        BgtCheckParamValue quarter = new BgtCheckParamValue();
        BgtCheckParamValue year = new BgtCheckParamValue();
        BgtCheckParamValue currency = new BgtCheckParamValue();
        BgtCheckParamValue entity = new BgtCheckParamValue();
        BgtCheckParamValue center = new BgtCheckParamValue();
        BgtCheckParamValue item = new BgtCheckParamValue();
        BgtCheckParamValue itemType = new BgtCheckParamValue();
        BgtCheckParamValue scenario = new BgtCheckParamValue();
        BgtCheckParamValue structure = new BgtCheckParamValue();
        BgtCheckParamValue version = new BgtCheckParamValue();
        BgtCheckParamValue company = new BgtCheckParamValue();
        BgtCheckParamValue employee = new BgtCheckParamValue();
        BgtCheckParamValue employeeGroup = new BgtCheckParamValue();
        BgtCheckParamValue employeeJob = new BgtCheckParamValue();
        BgtCheckParamValue employeeLevel = new BgtCheckParamValue();
        BgtCheckParamValue position = new BgtCheckParamValue();
        BgtCheckParamValue positionGroup = new BgtCheckParamValue();
        BgtCheckParamValue unit = new BgtCheckParamValue();
        BgtCheckParamValue unitGroup = new BgtCheckParamValue();
        BgtCheckParamValue unitLevel = new BgtCheckParamValue();
        BgtCheckParamValue dimension1 = new BgtCheckParamValue();
        BgtCheckParamValue dimension2 = new BgtCheckParamValue();
        BgtCheckParamValue dimension3 = new BgtCheckParamValue();
        BgtCheckParamValue dimension4 = new BgtCheckParamValue();
        BgtCheckParamValue dimension5 = new BgtCheckParamValue();
        BgtCheckParamValue dimension6 = new BgtCheckParamValue();
        BgtCheckParamValue dimension7 = new BgtCheckParamValue();
        BgtCheckParamValue dimension8 = new BgtCheckParamValue();
        BgtCheckParamValue dimension9 = new BgtCheckParamValue();
        BgtCheckParamValue dimension10 = new BgtCheckParamValue();
        BgtCheckParamValue dimension11 = new BgtCheckParamValue();
        BgtCheckParamValue dimension12 = new BgtCheckParamValue();
        BgtCheckParamValue dimension13 = new BgtCheckParamValue();
        BgtCheckParamValue dimension14 = new BgtCheckParamValue();
        BgtCheckParamValue dimension15 = new BgtCheckParamValue();
        BgtCheckParamValue dimension16 = new BgtCheckParamValue();
        BgtCheckParamValue dimension17 = new BgtCheckParamValue();
        BgtCheckParamValue dimension18 = new BgtCheckParamValue();
        BgtCheckParamValue dimension19 = new BgtCheckParamValue();
        BgtCheckParamValue dimension20 = new BgtCheckParamValue();

        condition.getCheckParamValueMap().put("CONTROL_PERIOD_STRATEGY", controlStrategy);
        condition.getCheckParamValueMap().put("BUDGET_PERIOD", period);
        condition.getCheckParamValueMap().put("BUDGET_QUARTER", quarter);
        condition.getCheckParamValueMap().put("BUDGET_YEAR", year);
        condition.getCheckParamValueMap().put("BUDGET_CURRENCY", currency);
        condition.getCheckParamValueMap().put("BUDGET_ENTITY", entity);
        condition.getCheckParamValueMap().put("BUDGET_CENTER", center);
        condition.getCheckParamValueMap().put("BUDGET_ITEM", item);
        condition.getCheckParamValueMap().put("BUDGET_ITEM_TYPE", itemType);
        condition.getCheckParamValueMap().put("BUDGET_SCENARIO", scenario);
        condition.getCheckParamValueMap().put("BUDGET_STRUCTURE", structure);
        condition.getCheckParamValueMap().put("BUDGET_VERSION", version);
        condition.getCheckParamValueMap().put("DIMENSION_1", dimension1);
        condition.getCheckParamValueMap().put("DIMENSION_2", dimension2);
        condition.getCheckParamValueMap().put("DIMENSION_3", dimension3);
        condition.getCheckParamValueMap().put("DIMENSION_4", dimension4);
        condition.getCheckParamValueMap().put("DIMENSION_5", dimension5);
        condition.getCheckParamValueMap().put("DIMENSION_6", dimension6);
        condition.getCheckParamValueMap().put("DIMENSION_7", dimension7);
        condition.getCheckParamValueMap().put("DIMENSION_8", dimension8);
        condition.getCheckParamValueMap().put("DIMENSION_9", dimension9);
        condition.getCheckParamValueMap().put("DIMENSION_10", dimension10);
        condition.getCheckParamValueMap().put("DIMENSION_11", dimension11);
        condition.getCheckParamValueMap().put("DIMENSION_12", dimension12);
        condition.getCheckParamValueMap().put("DIMENSION_13", dimension13);
        condition.getCheckParamValueMap().put("DIMENSION_14", dimension14);
        condition.getCheckParamValueMap().put("DIMENSION_15", dimension15);
        condition.getCheckParamValueMap().put("DIMENSION_16", dimension16);
        condition.getCheckParamValueMap().put("DIMENSION_17", dimension17);
        condition.getCheckParamValueMap().put("DIMENSION_18", dimension18);
        condition.getCheckParamValueMap().put("DIMENSION_19", dimension19);
        condition.getCheckParamValueMap().put("DIMENSION_20", dimension20);
        condition.getCheckParamValueMap().put("COMPANY", company);
        condition.getCheckParamValueMap().put("EMPLOYEE", employee);
        condition.getCheckParamValueMap().put("EMPLOYEE_GROUP", employeeGroup);
        condition.getCheckParamValueMap().put("EMPLOYEE_JOB", employeeJob);
        condition.getCheckParamValueMap().put("EMPLOYEE_LEVEL", employeeLevel);
        condition.getCheckParamValueMap().put("ORG_POSITION", position);
        condition.getCheckParamValueMap().put("ORG_POSITION_GROUP", positionGroup);
        condition.getCheckParamValueMap().put("ORG_UNIT", unit);
        condition.getCheckParamValueMap().put("ORG_UNIT_GROUP", unitGroup);
        condition.getCheckParamValueMap().put("ORG_UNIT_LEVEL", unitLevel);


        if (detailFlag) {
            currency.setParamValueType("DETAIL");
            currency.setParamValueId(1L);
            currency.setParamValueCode("CNY");

            entity.setParamValueType("DETAIL");
            entity.setParamValueId(1L);
            entity.setParamValueCode("001");

            center.setParamValueType("DETAIL");
            center.setParamValueId(1L);
            center.setParamValueCode("001");

            item.setParamValueType("DETAIL");
            item.setParamValueId(1L);
            item.setParamValueCode("001");

            itemType.setParamValueType("DETAIL");
            itemType.setParamValueId(1L);
            itemType.setParamValueCode("001");

            scenario.setParamValueType("DETAIL");
            scenario.setParamValueId(1L);
            scenario.setParamValueCode("001");

            structure.setParamValueType("DETAIL");
            structure.setParamValueId(1L);
            structure.setParamValueCode("001");

            version.setParamValueType("DETAIL");
            version.setParamValueId(1L);
            version.setParamValueCode("001");

            company.setParamValueType("DETAIL");
            company.setParamValueId(1L);
            company.setParamValueCode("001");

            employee.setParamValueType("DETAIL");
            employee.setParamValueId(1L);
            employee.setParamValueCode("001");

            employeeGroup.setParamValueType("DETAIL");
            employeeGroup.setParamValueId(1L);
            employeeGroup.setParamValueCode("001");

            employeeJob.setParamValueType("DETAIL");
            employeeJob.setParamValueId(1L);
            employeeJob.setParamValueCode("001");

            employeeLevel.setParamValueType("DETAIL");
            employeeLevel.setParamValueId(1L);
            employeeLevel.setParamValueCode("001");

            position.setParamValueType("DETAIL");
            position.setParamValueId(1L);
            position.setParamValueCode("001");

            positionGroup.setParamValueType("DETAIL");
            positionGroup.setParamValueId(1L);
            positionGroup.setParamValueCode("001");

            unit.setParamValueType("DETAIL");
            unit.setParamValueId(1L);
            unit.setParamValueCode("001");

            unitGroup.setParamValueType("DETAIL");
            unitGroup.setParamValueId(1L);
            unitGroup.setParamValueCode("001");

            unitLevel.setParamValueType("DETAIL");
            unitLevel.setParamValueId(1L);
            unitLevel.setParamValueCode("001");

            dimension1.setParamValueType("DETAIL");
            dimension1.setParamValueId(1L);
            dimension1.setParamValueCode("001");

            dimension2.setParamValueType("DETAIL");
            dimension2.setParamValueId(1L);
            dimension2.setParamValueCode("001");

            dimension3.setParamValueType("DETAIL");
            dimension3.setParamValueId(1L);
            dimension3.setParamValueCode("001");

            dimension4.setParamValueType("DETAIL");
            dimension4.setParamValueId(1L);
            dimension4.setParamValueCode("001");

            dimension5.setParamValueType("DETAIL");
            dimension5.setParamValueId(1L);
            dimension5.setParamValueCode("001");

            dimension6.setParamValueType("DETAIL");
            dimension6.setParamValueId(1L);
            dimension6.setParamValueCode("001");

            dimension7.setParamValueType("DETAIL");
            dimension7.setParamValueId(1L);
            dimension7.setParamValueCode("001");

            dimension8.setParamValueType("DETAIL");
            dimension8.setParamValueId(1L);
            dimension8.setParamValueCode("001");

            dimension9.setParamValueType("DETAIL");
            dimension9.setParamValueId(1L);
            dimension9.setParamValueCode("001");

            dimension10.setParamValueType("DETAIL");
            dimension10.setParamValueId(1L);
            dimension10.setParamValueCode("001");

            dimension11.setParamValueType("DETAIL");
            dimension11.setParamValueId(1L);
            dimension11.setParamValueCode("001");


            dimension12.setParamValueType("DETAIL");
            dimension12.setParamValueId(1L);
            dimension12.setParamValueCode("001");

            dimension13.setParamValueType("DETAIL");
            dimension13.setParamValueId(1L);
            dimension13.setParamValueCode("001");

            dimension14.setParamValueType("DETAIL");
            dimension14.setParamValueId(1L);
            dimension14.setParamValueCode("001");

            dimension15.setParamValueType("DETAIL");
            dimension15.setParamValueId(1L);
            dimension15.setParamValueCode("001");

            dimension16.setParamValueType("DETAIL");
            dimension16.setParamValueId(1L);
            dimension16.setParamValueCode("001");

            dimension17.setParamValueType("DETAIL");
            dimension17.setParamValueId(1L);
            dimension17.setParamValueCode("001");

            dimension18.setParamValueType("DETAIL");
            dimension18.setParamValueId(1L);
            dimension18.setParamValueCode("001");

            dimension19.setParamValueType("DETAIL");
            dimension19.setParamValueId(1L);
            dimension19.setParamValueCode("001");

            dimension20.setParamValueType("DETAIL");
            dimension20.setParamValueId(1L);
            dimension20.setParamValueCode("001");
        } else {

            currency.setParamValueType("ALL");
            currency.setParamValueId(1L);
            currency.setParamValueCode("CNY");

            entity.setParamValueType("ALL");
            entity.setParamValueId(1L);
            entity.setParamValueCode("001");

            center.setParamValueType("ALL");
            center.setParamValueId(1L);
            center.setParamValueCode("001");

            item.setParamValueType("ALL");
            item.setParamValueId(1L);
            item.setParamValueCode("001");

            itemType.setParamValueType("ALL");
            itemType.setParamValueId(1L);
            itemType.setParamValueCode("001");

            scenario.setParamValueType("ALL");
            scenario.setParamValueId(1L);
            scenario.setParamValueCode("001");

            structure.setParamValueType("ALL");
            structure.setParamValueId(1L);
            structure.setParamValueCode("001");

            version.setParamValueType("ALL");
            version.setParamValueId(1L);
            version.setParamValueCode("001");

            company.setParamValueType("ALL");
            company.setParamValueId(1L);
            company.setParamValueCode("001");

            employee.setParamValueType("ALL");
            employee.setParamValueId(1L);
            employee.setParamValueCode("001");

            employeeGroup.setParamValueType("ALL");
            employeeGroup.setParamValueId(1L);
            employeeGroup.setParamValueCode("001");

            employeeJob.setParamValueType("ALL");
            employeeJob.setParamValueId(1L);
            employeeJob.setParamValueCode("001");

            employeeLevel.setParamValueType("ALL");
            employeeLevel.setParamValueId(1L);
            employeeLevel.setParamValueCode("001");

            position.setParamValueType("ALL");
            position.setParamValueId(1L);
            position.setParamValueCode("001");

            positionGroup.setParamValueType("ALL");
            positionGroup.setParamValueId(1L);
            positionGroup.setParamValueCode("001");

            unit.setParamValueType("ALL");
            unit.setParamValueId(1L);
            unit.setParamValueCode("001");

            unitGroup.setParamValueType("ALL");
            unitGroup.setParamValueId(1L);
            unitGroup.setParamValueCode("001");

            unitLevel.setParamValueType("ALL");
            unitLevel.setParamValueId(1L);
            unitLevel.setParamValueCode("001");

            dimension1.setParamValueType("ALL");
            dimension1.setParamValueId(1L);
            dimension1.setParamValueCode("001");

            dimension2.setParamValueType("ALL");
            dimension2.setParamValueId(1L);
            dimension2.setParamValueCode("001");

            dimension3.setParamValueType("ALL");
            dimension3.setParamValueId(1L);
            dimension3.setParamValueCode("001");

            dimension4.setParamValueType("ALL");
            dimension4.setParamValueId(1L);
            dimension4.setParamValueCode("001");

            dimension5.setParamValueType("ALL");
            dimension5.setParamValueId(1L);
            dimension5.setParamValueCode("001");

            dimension6.setParamValueType("ALL");
            dimension6.setParamValueId(1L);
            dimension6.setParamValueCode("001");

            dimension7.setParamValueType("ALL");
            dimension7.setParamValueId(1L);
            dimension7.setParamValueCode("001");

            dimension8.setParamValueType("ALL");
            dimension8.setParamValueId(1L);
            dimension8.setParamValueCode("001");

            dimension9.setParamValueType("ALL");
            dimension9.setParamValueId(1L);
            dimension9.setParamValueCode("001");

            dimension10.setParamValueType("ALL");
            dimension10.setParamValueId(1L);
            dimension10.setParamValueCode("001");

            dimension11.setParamValueType("ALL");
            dimension11.setParamValueId(1L);
            dimension11.setParamValueCode("001");


            dimension12.setParamValueType("ALL");
            dimension12.setParamValueId(1L);
            dimension12.setParamValueCode("001");

            dimension13.setParamValueType("ALL");
            dimension13.setParamValueId(1L);
            dimension13.setParamValueCode("001");

            dimension14.setParamValueType("ALL");
            dimension14.setParamValueId(1L);
            dimension14.setParamValueCode("001");

            dimension15.setParamValueType("ALL");
            dimension15.setParamValueId(1L);
            dimension15.setParamValueCode("001");

            dimension16.setParamValueType("ALL");
            dimension16.setParamValueId(1L);
            dimension16.setParamValueCode("001");

            dimension17.setParamValueType("ALL");
            dimension17.setParamValueId(1L);
            dimension17.setParamValueCode("001");

            dimension18.setParamValueType("ALL");
            dimension18.setParamValueId(1L);
            dimension18.setParamValueCode("001");

            dimension19.setParamValueType("ALL");
            dimension19.setParamValueId(1L);
            dimension19.setParamValueCode("001");

            dimension20.setParamValueType("ALL");
            dimension20.setParamValueId(1L);
            dimension20.setParamValueCode("001");
        }


        // 期间信息设置
        period.setParamValueId(1L);
        period.setParamValueCode("2018-07");
        period.setLong1(20180007L);

        quarter.setParamValueId(3L);
        quarter.setParamValueCode("3");

        year.setParamValueId(2018L);
        year.setParamValueCode("2018");

        Boolean errorTestFlag = false;
        String controlType = "MONTH";
        // String controlType = "QTD";
        // String controlType = "QUARTER";
        // String controlType = "RQB";
        // String controlType = "YEAR";
        // String controlType = "YTD";
        // String controlType = "YTQ";
        // String controlType = "NO_FIXED";

        if (!errorTestFlag) {
            if (controlType.equals("MONTH")) {
                controlStrategy.setParamValueCode("MONTH");
                structure.setStr1("PERIOD");
            } else if (controlType.equals("QTD")) {
                controlStrategy.setParamValueCode("QTD");
                structure.setStr1("PERIOD");
            } else if (controlType.equals("QUARTER")) {
                controlStrategy.setParamValueCode("QUARTER");
                structure.setStr1("QUARTER");
            } else if (controlType.equals("RQB")) {
                controlStrategy.setParamValueCode("RQB");
                structure.setStr1("PERIOD");
            } else if (controlType.equals("YEAR")) {
                controlStrategy.setParamValueCode("YEAR");
                structure.setStr1("PERIOD");
            } else if (controlType.equals("YTD")) {
                controlStrategy.setParamValueCode("YTD");
                structure.setStr1("PERIOD");
            } else if (controlType.equals("YTQ")) {
                controlStrategy.setParamValueCode("YTQ");
                structure.setStr1("PERIOD");
            } else if (controlType.equals("NO_FIXED")) {
                controlStrategy.setParamValueCode("NO_FIXED");
                structure.setStr1("PERIOD");
            }
        } else {
            if (controlType.equals("MONTH")) {
                controlStrategy.setParamValueCode("MONTH");
                structure.setStr1("QUARTER");
            } else if (controlType.equals("QTD")) {
                controlStrategy.setParamValueCode("QTD");
                structure.setStr1("YEAR");
            } else if (controlType.equals("QUARTER")) {
                controlStrategy.setParamValueCode("QUARTER");
                structure.setStr1("YEAR");
            } else if (controlType.equals("RQB")) {
                controlStrategy.setParamValueCode("RQB");
                structure.setStr1("QUARTER");
            } else if (controlType.equals("YEAR")) {
                controlStrategy.setParamValueCode("YEAR");
                structure.setStr1("QUARTER");
            } else if (controlType.equals("YTD")) {
                controlStrategy.setParamValueCode("YTD");
                structure.setStr1("QUARTER");
            } else if (controlType.equals("YTQ")) {
                controlStrategy.setParamValueCode("YTQ");
                structure.setStr1("YEAR");
            } else if (controlType.equals("NO_FIXED")) {
                controlStrategy.setParamValueCode("NO_FIXED");
                structure.setStr1("QUARTER");
            }
        }
        checkMapper.getBalanceAmount(condition);
        checkMapper.getReserveAmount(condition);
    }

    @After
    public void tearDown() {

    }

    private class DriverManagerDataSource {
    }
}
