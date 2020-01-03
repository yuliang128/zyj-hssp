package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.FndCodingRuleDetailMapper;
import com.hand.hec.fnd.mapper.FndCodingRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCodingRuleDetail;
import com.hand.hec.fnd.service.IFndCodingRuleDetailService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndCodingRuleDetailServiceImpl extends BaseServiceImpl<FndCodingRuleDetail> implements IFndCodingRuleDetailService{

    @Autowired
    FndCodingRuleDetailMapper mapper;

    @Override
    public List<FndCodingRuleDetail> queryBySequence(IRequest requestContext, FndCodingRuleDetail dto,int page,int pageSize){
        return mapper.queryBySequence(dto);
    }
}