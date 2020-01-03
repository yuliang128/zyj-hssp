package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscTaskDispatchRecordMapper;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;
import com.hand.hec.ssc.service.ISscCoreService;
import com.hand.hec.ssc.service.ISscTaskDispatchRecordService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscTaskDispatchRecordServiceImpl extends BaseServiceImpl<SscTaskDispatchRecord> implements ISscTaskDispatchRecordService{
    @Autowired
    private SscTaskDispatchRecordMapper sscTaskDispatchRecordMapper;

    @Autowired
    private ISscCoreService sscCoreService;

    @Override
    public List<SscTaskDispatchRecord> getActionProcedure(Long dispatchRecordId, Long actionId) {
        return sscTaskDispatchRecordMapper.getActionProcedure(dispatchRecordId,actionId);
    }

    @Override
    public List<SscTaskDispatchRecord> getFinishProcedure(Long taskId) {
        return sscTaskDispatchRecordMapper.getFinishProcedure(taskId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rejectReturn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList) {
        for(SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecordList){
            sscCoreService.doRejectReturn(iRequest,sscTaskDispatchRecord,sscTaskDispatchRecord.getOpinion());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean agreeReturn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList) {
        for(SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecordList){
            sscCoreService.doAgreeReturn(iRequest,sscTaskDispatchRecord,sscTaskDispatchRecord.getOpinion());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean conAndAsgn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList) {
        for(SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecordList){
            sscCoreService.doAgreeReturn(iRequest,sscTaskDispatchRecord,sscTaskDispatchRecord.getOpinion());
        }
        for(SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecordList){
            sscCoreService.doDispatch(iRequest,sscTaskDispatchRecord.getDispatchRecordId(),sscTaskDispatchRecord.getTaskId(),
                    sscTaskDispatchRecord.getWorkTeamId(),sscTaskDispatchRecord.getEmployeeId());
        }
        return true;
    }

    @Override
    public List<SscTaskDispatchRecord> conAndAgenQuery(IRequest request,Integer page,Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return sscTaskDispatchRecordMapper.conAndAgenQuery();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean forceReturn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList) {
        for(SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecordList){
            sscCoreService.doForceReturn(iRequest,sscTaskDispatchRecord,sscTaskDispatchRecord.getOpinion());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean dispatch(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList) {
        for (SscTaskDispatchRecord sscTaskDispatchRecord : sscTaskDispatchRecordList) {
            sscCoreService.doDispatch(iRequest, sscTaskDispatchRecord.getDispatchRecordId(), sscTaskDispatchRecord.getTaskId(),
                    sscTaskDispatchRecord.getWorkTeamId(), sscTaskDispatchRecord.getEmployeeId());
        }
        return true;
    }

    public List<SscTaskDispatchRecord> selectWatingData(IRequest iRequest) {
        return sscTaskDispatchRecordMapper.selectWatingData();
    }
}