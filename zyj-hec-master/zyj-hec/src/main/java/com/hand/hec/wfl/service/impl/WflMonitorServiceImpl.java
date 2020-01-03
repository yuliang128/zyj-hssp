package com.hand.hec.wfl.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.wfl.dto.WflApproveRecord;
import com.hand.hec.wfl.dto.WflInsTask;
import com.hand.hec.wfl.dto.WflInsTaskRecipient;
import com.hand.hec.wfl.mapper.WflMonitorMapper;
import com.hand.hec.wfl.service.IWflApproveRecordService;
import com.hand.hec.wfl.service.IWflInsTaskRecipientService;
import com.hand.hec.wfl.service.IWflInsTaskService;
import com.hand.hec.wfl.service.IWflMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/03/07 14:29
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflMonitorServiceImpl implements IWflMonitorService {

    @Autowired
    WflMonitorMapper mapper;

    @Autowired
    IWflInsTaskRecipientService recipientService;

    @Autowired
    IWflInsTaskService insTaskService;

    @Autowired
    IWflApproveRecordService approveRecordService;

    @Override
    public List<Map<String, Object>> selectWflInstance(IRequest context, Map param, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Map<String, Object>> resultList = mapper.selectWflInstance(param);

        // 循环当前结果列表，为每个结果设置最后审批人和当前审批人
        for (Map<String, Object> result : resultList) {
            // 设置当前审批人
            String currentApprover = "";
            WflInsTaskRecipient recipient = new WflInsTaskRecipient();
            recipient.setInstanceId((Long) result.get("instanceId"));
            Criteria criteria = new Criteria(recipient);
            List<WflInsTaskRecipient> recipientList =
                            recipientService.selectOptions(context, recipient, criteria, 0, 0);

            if (recipientList.size() != 0) {
                HashSet<Long> insTaskSet = new HashSet<Long>();
                for (WflInsTaskRecipient rcpt : recipientList) {
                    insTaskSet.add(rcpt.getInsTaskId());
                }

                for (Long insTaskId : insTaskSet) {
                    WflInsTask insTask = new WflInsTask();
                    insTask.setInsTaskId(insTaskId);
                    Criteria criteria2 = new Criteria(insTask);
                    List<WflInsTask> insTaskList = insTaskService.selectOptions(context, insTask, criteria2, 0, 0);
                    insTask = insTaskList.get(0);

                    currentApprover += insTask.getTaskName();
                    currentApprover += "(";

                    for (WflInsTaskRecipient rcpt : recipientList) {
                        if (insTask.getInsTaskId().equals(rcpt.getInsTaskId())) {
                            currentApprover += rcpt.getApproverName() + "[" + rcpt.getApproverCode() + "];";
                        }
                    }
                    currentApprover += ")";
                }
            }

            // 设置最后审批人
            String lastApprover = "";
            Long lastApproveRecordId = approveRecordService.getLastApproveRecordId((Long) result.get("instance_id"));
            WflApproveRecord record = new WflApproveRecord();
            record.setRecordId(lastApproveRecordId);
            Criteria criteria3 = new Criteria(record);
            List<WflApproveRecord> recordList = approveRecordService.selectOptions(context, record, criteria3, 0, 0);
            if (recordList.size() != 0) {
                lastApprover = recordList.get(0).getApproverName() + "[" + recordList.get(0).getApproverCode() + "]";
            }

            result.put("approverName", currentApprover);
            result.put("lastApproverName", lastApprover);
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectWflApproveInfo(IRequest context, Long instanceId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return mapper.selectWflApproveInfo(instanceId);
    }
}
