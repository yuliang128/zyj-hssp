package com.hand.hec.gld.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldMappingCondGrpLn;
import com.hand.hec.gld.mapper.GldMappingCondGrpLnMapper;
import com.hand.hec.gld.service.IGldMappingCondGrpLnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldMappingCondGrpLnServiceImpl extends BaseServiceImpl<GldMappingCondGrpLn> implements IGldMappingCondGrpLnService{
    @Autowired
    private GldMappingCondGrpLnMapper gldMappingCondGrpLnMapper;

    @Override
    public List<GldMappingCondGrpLn> selectGroupLines(IRequest request,String groupName){
        List<GldMappingCondGrpLn> gldMappingCondGrpLns = gldMappingCondGrpLnMapper.selectGroupLines(groupName);
        return gldMappingCondGrpLns;
    }
}