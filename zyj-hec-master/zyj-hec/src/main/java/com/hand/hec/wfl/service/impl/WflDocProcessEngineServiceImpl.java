package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.service.IFndBusinessRuleEngineService;
import com.hand.hec.fnd.service.IFndDocEngineService;
import com.hand.hec.wfl.dto.WflDocProcessAssign;
import com.hand.hec.wfl.mapper.WflDocBizRuleAssignMapper;
import com.hand.hec.wfl.mapper.WflDocProcessAssignMapper;
import com.hand.hec.wfl.dto.WflDocBizRuleAssign;
import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.service.IWflDocProcessEngineService;
import com.hand.hec.wfl.service.IWflProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/21 \* Time: 16:43 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflDocProcessEngineServiceImpl extends BaseServiceImpl<WflProcess> implements IWflDocProcessEngineService {

    @Autowired
    IFndDocEngineService docEngine;

    @Autowired
    WflDocProcessAssignMapper docProcessMapper;

    @Autowired
    WflDocBizRuleAssignMapper docBizRuleMapper;

    @Autowired
    IWflProcessService iWflProcessService;

    @Autowired
    IFndBusinessRuleEngineService ruleEngineService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    /**
     * docCategory找到fnd_doc_info中的type_field_name docCategory和type_field_name找到wfl_doc_process_assign中的值
     * 规则判断还没实现 默认给true
     *
     * @param context
     * @param docCategory
     * @param docId
     * @author mouse 2019-03-05 14:02
     * @return com.hand.hec.wfl.dto.WflProcess
     */
    @Override
    public WflProcess getTargetWflProcess(IRequest context, String docCategory, String docId) {
        Long docTypeId = docEngine.getDocTypeId(docCategory, docId);
        List<WflDocProcessAssign> docProcessList = docProcessMapper.queryAssignByDocInfo(docCategory, docTypeId);
        Long processId = null;
        if (docProcessList.size() == 0) {
            throw new RuntimeException("未找到匹配的工作流定义，请联系管理员！");
        }

        for (WflDocProcessAssign docProcess : docProcessList) {
            List<WflDocBizRuleAssign> docBizRule = docBizRuleMapper.queryDocBizRuleAssign(docProcess);
            for (WflDocBizRuleAssign ruleAssign : docBizRule) {
                if (ruleEngineService.validateBusinessRule(context, ruleAssign.getBusinessRuleId(), docCategory, docId)) {
                    processId = docProcess.getProcessId();
                    break;
                }
            }
        }

        if (processId != null) {
            WflProcess queryProcess = new WflProcess();
            queryProcess.setProcessId(processId);
            WflProcess process = iWflProcessService.selectByPrimaryKey(context, queryProcess);
            System.out.println(process.toString());
            if (process == null) {
                throw new RuntimeException("未找到匹配的工作流定义，请联系管理员！");
            } else {
                return process;
            }
        } else {
            throw new RuntimeException("未找到匹配的工作流定义，请联系管理员！");
        }
    }
}
