package com.hand.hec.ssc.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscProcess;
import com.hand.hec.ssc.service.ISscProcessService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscProcessServiceImpl extends BaseServiceImpl<SscProcess> implements ISscProcessService{
    @Autowired
    private SscProcessMapper sscProcessMapper;

    @Override
    public List<SscProcess> processQuery(IRequest requestContext, Long dispatchRecordId, Long taskId, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return sscProcessMapper.processQuery(dispatchRecordId,taskId);
    }
}