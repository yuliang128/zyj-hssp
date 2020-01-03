package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscWorkRecord;
import com.hand.hec.ssc.mapper.SscWorkRecordMapper;
import com.hand.hec.ssc.service.ISscWorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkRecordServiceImpl extends BaseServiceImpl<SscWorkRecord> implements ISscWorkRecordService{
    @Autowired
    private SscWorkRecordMapper sscWorkRecordMapper;

    @Override
    public void startWork(IRequest iRequest){
        SscWorkRecord sscWorkRecord = new SscWorkRecord();
        sscWorkRecord.setEmployeeId(iRequest.getEmployeeId());
        sscWorkRecord.setProcessDate(DateUtils.getCurrentDate());
        sscWorkRecord.setProcessTime(new Date());
        sscWorkRecord.setWorkStatus(SscWorkRecord.WORK_STATUS_WORK);
        self().insertSelective(iRequest,sscWorkRecord);
    }

    @Override
    public void pauseWork(IRequest iRequest){
        SscWorkRecord sscWorkRecord = new SscWorkRecord();
        sscWorkRecord.setEmployeeId(iRequest.getEmployeeId());
        sscWorkRecord.setProcessDate(DateUtils.getCurrentDate());
        sscWorkRecord.setProcessTime(new Date());
        sscWorkRecord.setWorkStatus(SscWorkRecord.WORK_STATUS_PAUSE);
        self().insertSelective(iRequest,sscWorkRecord);
    }

    @Override
    public void stopWork(IRequest iRequest){
        SscWorkRecord sscWorkRecord = new SscWorkRecord();
        sscWorkRecord.setEmployeeId(iRequest.getEmployeeId());
        sscWorkRecord.setProcessDate(DateUtils.getCurrentDate());
        sscWorkRecord.setProcessTime(new Date());
        sscWorkRecord.setWorkStatus(SscWorkRecord.WORK_STATUS_STOP);
        self().insertSelective(iRequest,sscWorkRecord);
    }

    @Override
    public String getWorkStatus(IRequest iRequest) {
        String workStatus = null;
        List<SscWorkRecord> sscWorkRecords = new ArrayList<>();
        sscWorkRecords = sscWorkRecordMapper.getWorkStatus(DateUtils.getCurrentDate());
        if(!sscWorkRecords.isEmpty() && sscWorkRecords != null) {
            workStatus = sscWorkRecordMapper.getWorkStatus(DateUtils.getCurrentDate()).get(0).getWorkStatus();
        }
        return workStatus;
    }
}