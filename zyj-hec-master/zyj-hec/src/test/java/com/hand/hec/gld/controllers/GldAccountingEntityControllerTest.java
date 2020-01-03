package com.hand.hec.gld.controllers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hec.fnd.exception.ExchangerateTypeException;
import com.hand.hec.fnd.service.IGldExchangerateTypeService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;

/**
 * description
 *
 * @author xingjialin 2019/02/22 09:51
 */
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
@WebAppConfiguration
public class GldAccountingEntityControllerTest {

    @Autowired
    private IGldExchangerateTypeService service;

    @Test
    public void queryByTypeId() throws ExchangerateTypeException {
        int s = service.check();
        System.out.println(s);
    }
}