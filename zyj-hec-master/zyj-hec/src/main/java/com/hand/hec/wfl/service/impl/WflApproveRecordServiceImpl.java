package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflApproveRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflApproveRecord;
import com.hand.hec.wfl.service.IWflApproveRecordService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflApproveRecordServiceImpl extends BaseServiceImpl<WflApproveRecord> implements IWflApproveRecordService {

    @Autowired
    WflApproveRecordMapper mapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public Long getLastApproveRecordId(Long instanceId) {
        return mapper.getLastApproveRecordId(instanceId);
    }
}
