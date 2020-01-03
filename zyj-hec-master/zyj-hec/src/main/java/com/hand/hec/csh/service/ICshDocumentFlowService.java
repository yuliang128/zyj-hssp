package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshDocumentFlow;

public interface ICshDocumentFlowService extends IBaseService<CshDocumentFlow>, ProxySelf<ICshDocumentFlowService>{
    /**
     * 删除合同关联表
     *
     * @param request IRequest
     * @param documentType String
     * @param sourceDocumentType String
     * @param sourceDocumentId Long
     * @author dingwei.ma@hand-china.com 2019-01-28 10:35
     * @return void
     */
    void deleteDocumentFlow(IRequest request, Long sourceDocumentId, String documentType, String sourceDocumentType);
}