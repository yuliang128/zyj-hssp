package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVersionProcess;
import com.hand.hec.wfl.mapper.WflVersionProcessMapper;
import com.hand.hec.wfl.service.IWflVersionProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 11:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVersionProcessServiceImpl extends BaseServiceImpl<WflVersionProcess>
                implements IWflVersionProcessService {

    @Autowired
    WflVersionProcessMapper versionProcessMapper;


    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public Long getProcessCurrentVersion(Long processId) {
        return versionProcessMapper.getProcessCurrentVersion(processId);
    }
}
