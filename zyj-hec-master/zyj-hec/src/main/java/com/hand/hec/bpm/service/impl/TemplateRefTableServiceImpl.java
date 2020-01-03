package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.TemplateRefTable;
import com.hand.hec.bpm.mapper.TemplateRefTableMapper;
import com.hand.hec.bpm.service.ITemplateRefTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TemplateRefTableServiceImpl extends BaseServiceImpl<TemplateRefTable> implements ITemplateRefTableService {
    @Autowired
    TemplateRefTableMapper TemplateRefTableMapper;

    @Override
    public List<TemplateRefTable> queryByTemplateId(IRequest request, TemplateRefTable TemplateRefTable){
        return TemplateRefTableMapper.queryByTemplateId(TemplateRefTable);
    }
}