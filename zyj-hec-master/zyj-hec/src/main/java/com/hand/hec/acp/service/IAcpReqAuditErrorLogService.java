package com.hand.hec.acp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpReqAuditErrorLog;
import java.util.List;

public interface IAcpReqAuditErrorLogService extends IBaseService<AcpReqAuditErrorLog>, ProxySelf<IAcpReqAuditErrorLogService>{

    /**
     *
     * @param request
     * @author zhongyu 2019-5-9
     * @return
     */
    List<AcpReqAuditErrorLog> queryBySessionId(IRequest request,AcpReqAuditErrorLog dto,int page,int pageSize);

}