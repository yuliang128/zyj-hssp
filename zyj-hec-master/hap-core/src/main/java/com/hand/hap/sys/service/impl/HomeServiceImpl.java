package com.hand.hap.sys.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.sys.mapper.HomeMapper;
import com.hand.hap.sys.service.IHomeService;

/**
 * @desc:
 * @author: hao.zhou@hand-china.com
 * @date: 2019/6/19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HomeServiceImpl implements IHomeService {

    @Autowired
    HomeMapper mapper;

    @Override
    public Long getTodoCount(IRequest request) {
        return mapper.getTodoCount();
    }

    @Override
    public Long getReturnCount(IRequest request) {
        return mapper.getReturnCount();
    }

    @Override
    public BigDecimal getUnpaidAmount(IRequest request) {
        return mapper.getUnpaidAmount();
    }

    @Override
    public BigDecimal getUnWriteOffAmount(IRequest request) {
        return mapper.getUnWriteOffAmount();
    }
}
