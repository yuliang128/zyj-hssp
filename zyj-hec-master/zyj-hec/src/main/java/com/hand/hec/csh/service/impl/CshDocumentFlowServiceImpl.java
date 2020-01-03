package com.hand.hec.csh.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshDocumentFlow;
import com.hand.hec.csh.service.ICshDocumentFlowService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshDocumentFlowServiceImpl extends BaseServiceImpl<CshDocumentFlow> implements ICshDocumentFlowService {
    @Override
    public void deleteDocumentFlow(IRequest request, Long sourceDocumentId, String documentType, String sourceDocumentType) {
        CshDocumentFlow cshDocumentFlow = new CshDocumentFlow();
        cshDocumentFlow.setSourceDocumentId(sourceDocumentId);
        cshDocumentFlow.setDocumentType(documentType);
        cshDocumentFlow.setSourceDocumentType(sourceDocumentType);
        Criteria criteria = new Criteria(cshDocumentFlow);
        if (!documentType.isEmpty()){
            criteria.where(new WhereField(CshDocumentFlow.FIELD_DOCUMENT_TYPE), Comparison.EQUAL);
        }
        if (!sourceDocumentType.isEmpty()){
            criteria.where(new WhereField(CshDocumentFlow.FIELD_SOURCE_DOCUMENT_TYPE), Comparison.EQUAL);
        }
        criteria.where(new WhereField(CshDocumentFlow.FIELD_SOURCE_DOCUMENT_ID), Comparison.EQUAL);
        List<CshDocumentFlow> flowList = self().selectOptions(request,cshDocumentFlow,criteria);
        for (CshDocumentFlow flow : flowList){
            self().deleteByPrimaryKey(flow);
        }
    }
}