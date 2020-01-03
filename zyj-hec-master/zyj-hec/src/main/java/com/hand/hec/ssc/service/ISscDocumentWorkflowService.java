package com.hand.hec.ssc.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscDocumentWorkflow;

public interface ISscDocumentWorkflowService extends IBaseService<SscDocumentWorkflow>, ProxySelf<ISscDocumentWorkflowService>{

    /**
     * 共享业务类型分配主页面查询
     *
     * @param magOrgId
     * @param page
     * @param pageSize
     * @param request
     * @param docCategory
     * @author ngls.luhui 2019-03-20 15:24
     * @return List<SscDocumentWorkflow>
     */
    List<SscDocumentWorkflow> queryDocument(Long magOrgId, String docCategory, Integer page, Integer pageSize, IRequest request);

}