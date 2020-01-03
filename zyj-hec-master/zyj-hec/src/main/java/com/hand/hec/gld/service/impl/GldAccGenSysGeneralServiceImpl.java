package com.hand.hec.gld.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.hand.hec.gld.mapper.GldAccGenSysGeneralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccGenSysGeneral;
import com.hand.hec.gld.service.IGldAccGenSysGeneralService;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccGenSysGeneralServiceImpl extends BaseServiceImpl<GldAccGenSysGeneral> implements IGldAccGenSysGeneralService{
    @Autowired
    private GldAccGenSysGeneralMapper mapper;
    @Override
    public List<GldAccGenSysGeneral> query(IRequest requestContext, GldAccGenSysGeneral gldAccGenSysGeneral, int page, int pagesize){
        PageHelper.startPage(page,pagesize);
        return mapper.query(gldAccGenSysGeneral);
    }
}