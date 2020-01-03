package com.hand.hec.expm.test;

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
import com.hand.hap.core.util.DateUtils;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.gld.mapper.GldAccEntityRefSobMapper;
import com.hand.hec.gld.service.IGldAccEntityRefSobService;

/**
 * description
 *
 * @author yang.duan 2019/03/14 18:00
 */
@ContextConfiguration(locations = {"classpath:/spring/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class ExpReportTest {
    @Autowired
    IExpReportHeaderService reportHeaderService;

    @Autowired
    IGldAccEntityRefSobService gldAccEntityRefSobService;

    @Autowired
    GldAccEntityRefSobMapper gldAccEntityRefSobMapper;

    IRequest request;

    @Before
    public void setUp() throws Exception {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);
    }
    @Test
    public void testReportHeaderSave(){
        System.out.println(DateUtils.getDateScopeFrom("000-090"));
    }
}
