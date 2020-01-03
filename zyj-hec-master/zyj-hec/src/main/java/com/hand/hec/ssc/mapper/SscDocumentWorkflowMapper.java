package com.hand.hec.ssc.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscDocumentWorkflow;

public interface SscDocumentWorkflowMapper extends Mapper<SscDocumentWorkflow>{
    
    
    /**
     * 共享业务类型分配主页面查询(ssc_document_workflow.bm)
     *
     * @param magOrgId
     * @param docCategory
     * @author ngls.luhui 2019-03-20 15:24
     * @return List<SscDocumentWorkflow>
     */
    List<SscDocumentWorkflow> queryDocument(@Param("magOrgId") Long magOrgId,@Param("docCategory") String docCategory);
}