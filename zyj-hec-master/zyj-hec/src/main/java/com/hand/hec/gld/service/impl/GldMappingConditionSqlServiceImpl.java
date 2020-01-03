package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldMappingConditionSql;
import com.hand.hec.gld.mapper.GldMappingConditionSqlMapper;
import com.hand.hec.gld.service.IGldMappingConditionSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldMappingConditionSqlServiceImpl extends BaseServiceImpl<GldMappingConditionSql> implements IGldMappingConditionSqlService{
    @Autowired
    private GldMappingConditionSqlMapper gldMappingConditionSqlMapper;
    @Override
    public List<GldMappingConditionSql> selectGridData(IRequest requestContext, String usageCode, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return gldMappingConditionSqlMapper.selectGridData(usageCode);
    }
}