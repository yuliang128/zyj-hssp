package com.hand.hec.fnd.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndMoClassSet;
import com.hand.hec.fnd.mapper.FndMoClassSetMapper;
import com.hand.hec.fnd.service.IFndMoClassSetService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndMoClassSetServiceImpl extends BaseServiceImpl<FndMoClassSet> implements IFndMoClassSetService{

    @Autowired
    private FndMoClassSetMapper mapper;

    @Override
    public List<FndMoClassSet> baseSelect(IRequest request, FndMoClassSet dto,int page,int pageSize) {
        PageHelper.startPage(page,pageSize);
        return mapper.baseSelect(dto);
    }
}