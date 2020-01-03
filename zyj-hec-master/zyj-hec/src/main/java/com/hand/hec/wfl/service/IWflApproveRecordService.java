package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflApproveRecord;

public interface IWflApproveRecordService extends IBaseService<WflApproveRecord>, ProxySelf<IWflApproveRecordService> {

    Long getLastApproveRecordId(Long instanceId);
}
