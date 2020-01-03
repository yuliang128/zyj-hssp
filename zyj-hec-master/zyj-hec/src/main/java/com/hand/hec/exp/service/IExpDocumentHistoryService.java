package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpDocumentHistory;

import java.util.List;

public interface IExpDocumentHistoryService
                extends IBaseService<ExpDocumentHistory>, ProxySelf<IExpDocumentHistoryService> {
    /**
     * 单据历史删除
     *
     * @param documentType String
     * @param documentId Long
     * @author dingwei.ma@hand-china.com 2019-01-28 10:53
     * @return void
     */
    void delteDocumentHistory(IRequest request, String documentType, Long documentId);

    /**
     * 单据历史新增
     *
     * @param request IRequest
     * @param documentType String
     * @param documentId Long
     * @param operationCode String
     * @param employeeId Long
     * @param description String
     * @author dingwei.ma@hand-china.com 2019-01-28 10:53
     * @return void
     */
    void insertDocumentHistory(IRequest request, String documentType, Long documentId, String operationCode,
                    Long employeeId, String description);

    /**
     * 查询借款申请单历史
     *
     * @param request IRequest
     * @param cshPayHeaderId 借款申请单id
     * @param page 页码
     * @param pageSize 页数
     * @author LJK 2019-03-01 15:05
     * @return
     */
    List<ExpDocumentHistory> queryPayReqHistory(IRequest request, Long cshPayHeaderId, int page, int pageSize);


}
