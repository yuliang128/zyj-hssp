package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.FndUomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndUom;
import com.hand.hec.fnd.service.IFndUomService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndUomServiceImpl extends BaseServiceImpl<FndUom> implements IFndUomService{

    @Autowired
    FndUomMapper mapper;

    @Override
    public List<FndUom> select(IRequest request, FndUom dto, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return mapper.select(dto);
    }
}