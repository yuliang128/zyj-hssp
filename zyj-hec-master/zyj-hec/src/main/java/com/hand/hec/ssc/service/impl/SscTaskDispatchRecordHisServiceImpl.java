package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscTaskDispatchRecordHisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscTaskDispatchRecordHis;
import com.hand.hec.ssc.service.ISscTaskDispatchRecordHisService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscTaskDispatchRecordHisServiceImpl extends BaseServiceImpl<SscTaskDispatchRecordHis> implements ISscTaskDispatchRecordHisService{
    @Autowired
    private SscTaskDispatchRecordHisMapper sscTaskDispatchRecordHisMapper;

    @Override
    public List<SscTaskDispatchRecordHis> getAllDispatchRecordHis(Long taskId, String status,String processTypeCode) {
        return sscTaskDispatchRecordHisMapper.getAllDispatchRecordHis(taskId,status,processTypeCode);
    }
}