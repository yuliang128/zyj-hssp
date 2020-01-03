package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.mapper.ExpDocumentHistoryMapper;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpDocumentHistoryServiceImpl extends BaseServiceImpl<ExpDocumentHistory>
                implements IExpDocumentHistoryService {

    @Autowired
    ExpDocumentHistoryMapper mapper;

    @Override
    public void delteDocumentHistory(IRequest request, String documentType, Long documentId) {
        ExpDocumentHistory expDocumentHistory = new ExpDocumentHistory();
        expDocumentHistory.setDocumentType(documentType);
        expDocumentHistory.setDocumentId(documentId);
        Criteria criteria = new Criteria(expDocumentHistory);
        criteria.where(new WhereField(ExpDocumentHistory.FIELD_DOCUMENT_TYPE, Comparison.EQUAL),
                        new WhereField(ExpDocumentHistory.FIELD_DOCUMENT_ID, Comparison.EQUAL));
        List<ExpDocumentHistory> historyList = self().selectOptions(request, expDocumentHistory, criteria);
        for (ExpDocumentHistory history : historyList) {
            self().deleteByPrimaryKey(history);
        }

    }

    @Override
    public void insertDocumentHistory(IRequest request, String documentType, Long documentId, String operationCode,
                    Long employeeId, String description) {
        ExpDocumentHistory expDocumentHistory = new ExpDocumentHistory();
        expDocumentHistory.setUserId(request.getUserId());
        expDocumentHistory.setDocumentType(documentType);
        expDocumentHistory.setDocumentId(documentId);
        expDocumentHistory.setOperationCode(operationCode);
        expDocumentHistory.setEmployeeId(employeeId);
        expDocumentHistory.setDescription(description);
        expDocumentHistory.setOperationTime(new Date());
        expDocumentHistory.setOperationTimeTz(new Timestamp(System.currentTimeMillis()));
        expDocumentHistory.setOperationTimeLtz(new Timestamp(System.currentTimeMillis()));

        self().insertSelective(request, expDocumentHistory);
    }

    @Override
    public List<ExpDocumentHistory> queryPayReqHistory(IRequest request, Long cshPayHeaderId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryPayReqHistory(cshPayHeaderId);
    }

}
