package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscDocumentWorkflow;
import com.hand.hec.ssc.mapper.SscDocumentWorkflowMapper;
import com.hand.hec.ssc.service.ISscDocumentWorkflowService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscDocumentWorkflowServiceImpl extends BaseServiceImpl<SscDocumentWorkflow> implements ISscDocumentWorkflowService{

    @Autowired
    private SscDocumentWorkflowMapper sscDocumentWorkflowMapper;

    @Override
    public List<SscDocumentWorkflow> queryDocument(Long magOrgId, String docCategory, Integer page, Integer pageSize, IRequest request) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return sscDocumentWorkflowMapper.queryDocument(magOrgId,docCategory);
    }
}