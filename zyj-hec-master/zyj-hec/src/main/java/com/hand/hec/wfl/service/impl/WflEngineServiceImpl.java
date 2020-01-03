package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.components.ApplicationContextHelper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndBusinessRuleEngineService;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import com.hand.hec.fnd.service.IFndDocEngineService;
import com.hand.hec.wfl.cache.WflVersionProcessCache;
import com.hand.hec.wfl.cache.WflVersionUtil;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/19 \* Time: 10:04 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflEngineServiceImpl implements IWflEngineService {

    @Autowired
    IWflDocProcessEngineService wflDocProcessEngineService;

    @Autowired
    IWflInstanceService wflInstanceService;

    @Autowired
    IFndDocEngineService fndDocEngineService;

    @Autowired
    IWflUtilService wflUtilService;

    @Autowired
    IWflProcessService wflProcessService;

    @Autowired
    IWflInsEventService wflInsEventService;

    @Autowired
    IWflInsTaskService wflInsTaskService;

    @Autowired
    IWflInsGatewayService wflInsGatewayService;

    @Autowired
    IWflInsFlowService wflInsFlowService;

    @Autowired
    IWflTaskReceiverService wflTaskReceiverService;

    @Autowired
    IWflTaskService wflTaskService;

    @Autowired
    IWflTaskRcvBizDetailService wflTaskRcvBizDetailService;

    @Autowired
    IFndBusinessRuleEngineService fndBusinessRuleEngineService;

    @Autowired
    IWflInsTaskReceiverService wflInsTaskReceiverService;

    @Autowired
    IWflTaskEngineService wflTaskEngineService;

    @Autowired
    IWflEventService wflEventService;

    @Autowired
    IWflEventEngineService wflEventEngineService;

    @Autowired
    IWflGatewayEngineService wflGatewayEngineService;

    @Autowired
    IWflFlowEngineService wflFlowEngineService;

    @Autowired
    IWflProcedureEngineService wflPorcedureEngineService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    IFndBusinessRuleService fndBusinessRuleService;

    @Autowired
    IWflVersionProcessService versionProcessService;

    Logger logger = LoggerFactory.getLogger(WflEngineServiceImpl.class);

    /**
     * 根据单据类别和单据ID寻找当前版本的工作流
     *
     * @param context
     * @param docCategory
     * @param docId
     * @author mouse 2019-03-01 11:36
     * @return com.hand.hec.wfl.dto.WflVersionProcess
     */
    public WflVersionProcess getProcess(IRequest context, String docCategory, String docId) {
        WflProcess process = wflDocProcessEngineService.getTargetWflProcess(context, docCategory, docId);
        Long version = versionProcessService.getProcessCurrentVersion(process.getProcessId());
        WflVersionProcess versionProcess =
                        getCache().getValue(WflVersionUtil.getVersion(process.getProcessId(), version));
        return versionProcess;
    }

    /**
     * 根据工作流ID和版本获取工作流
     *
     * @param processId
     * @param version
     * @author mouse 2019-03-04 14:07
     * @return com.hand.hec.wfl.dto.WflVersionProcess
     */
    public WflVersionProcess getProcess(Long processId, Long version) {
        WflVersionProcess versionProcess = getCache().getValue(WflVersionUtil.getVersion(processId, version));
        return versionProcess;
    }

    public WflInstance generateInstance(IRequest context, WflVersionProcess process, String docCategory, String docId) {
        return self().createInstance(context, process, docCategory, docId);
    }

    /**
     * User: MouseZhou Date: 2018/5/28 para:WflInstance 流程实例 Purpose:实例化所有元素 包括事件,任务,网关,流
     */
    public void generateInstanceElement(IRequest context, WflInstance instance, WflVersionProcess process,
                    List<WflInsTask> taskList) {
        logger.debug("生成工作流实例对应的元素");

        Map<String, Long> elementMap = new HashMap<String, Long>();

        // 生成所有的Event实例
        if (process.getEventMap() != null) {
            for (WflVersionEvent event : process.getEventMap().values()) {
                WflInsEvent insEvent = new WflInsEvent();
                insEvent.setArrivalStatus(WflInsEvent.ARRIVAL_STATUS_NOT_ARRIVED);
                insEvent.setEventId(event.getEventId());
                insEvent.setInstanceId(instance.getInstanceId());
                insEvent = wflInsEventService.insertSelective(context, insEvent);
                // 保存事件对应的事件实例ID 生成流实例的时候需要用到
                elementMap.put(WflEvent.ELEMENT_EVENT + "-" + event.getEventId(), insEvent.getInsEventId());
            }
        }

        // 生成所有的Task实例
        if (process.getTaskMap() != null) {
            for (WflVersionTask task : process.getTaskMap().values()) {
                WflInsTask insTask = new WflInsTask();
                insTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
                insTask.setTaskId(task.getTaskId());
                insTask.setInstanceId(instance.getInstanceId());
                insTask = wflInsTaskService.insertSelective(context, insTask);
                taskList.add(insTask);
                elementMap.put(WflTask.ELEMENT_TASK + "-" + task.getTaskId(), insTask.getInsTaskId());
            }
        }

        // 生成所有的Gateway实例
        if (process.getGatewayMap() != null) {
            for (WflVersionGateway gateway : process.getGatewayMap().values()) {
                WflInsGateway insGateway = new WflInsGateway();
                insGateway.setArrivalStatus(WflInsGateway.ARRIVAL_STATUS_NOT_ARRIVED);
                insGateway.setGatewayId(gateway.getGatewayId());
                insGateway.setInstanceId(instance.getInstanceId());
                insGateway = wflInsGatewayService.insertSelective(context, insGateway);
                elementMap.put(WflGateway.ELEMENT_GATEWAY + "-" + gateway.getGatewayId(), insGateway.getInsGatewayId());
            }
        }

        // 生成所有的flow实例
        if (process.getFlowMap() != null) {
            for (WflVersionFlow flow : process.getFlowMap().values()) {
                WflInsFlow insFlow = new WflInsFlow();
                insFlow.setArrivalStatus(WflInsFlow.ARRIVAL_STATUS_NOT_ARRIVED);
                insFlow.setFlowId(flow.getFlowId());
                insFlow.setInstanceId(instance.getInstanceId());
                insFlow.setFromElementType(flow.getFromElementType());
                // 获取元素生成的实例
                insFlow.setFromElementId(elementMap.get(flow.getFromElementType() + "-" + flow.getFromElementId()));
                insFlow.setToElementType(flow.getToElementType());
                // 获取元素生成的实例
                insFlow.setToElementId(elementMap.get(flow.getToElementType() + "-" + flow.getToElementId()));
                insFlow.setSequence(flow.getSequence());
                insFlow = wflInsFlowService.insertSelective(context, insFlow);
            }
        }
    }

    /**
     * User: MouseZhou Date: 2018/5/28 para:WflInstance 流程实例 WflProcess 流程 List<WflTask> 任务数组
     * Purpose:生成任务接收者实例
     */
    @Override
    public void generateInsTaskReceiver(IRequest context, WflInstance instance, WflVersionProcess process,
                    WflInsTask insTask) {
        // 如果当前节点的任务接受者不为空，则生成任务接受者信息
        WflVersionTask verTask = process.getTask(WflVersionUtil.getVersion(insTask.getTaskId(), process.getVersion()));
        if (verTask != null && verTask.getReceiverMap() != null) {
            for (WflVerTaskReceiver taskReceiver : verTask.getReceiverMap().values()) {
                boolean receiverValidateResult = false;
                if (taskReceiver.getRcvBizDetailMap() != null) {
                    for (WflVerTaskRcvBizDetail rcvBizDetail : taskReceiver.getRcvBizDetailMap().values()) {
                        // 实例化权限规则
                        FndBusinessRule businessRule = new FndBusinessRule();
                        businessRule.setBusinessRuleId(rcvBizDetail.getBusinessRuleId());
                        businessRule = fndBusinessRuleService.selectByPrimaryKey(context, businessRule);
                        // 校验业务规则
                        boolean businessRuleResult = fndBusinessRuleEngineService.validateBusinessRule(context,
                                        businessRule, instance.getDocCategory(), String.valueOf(instance.getDocId()));
                        if (!businessRuleResult) {
                            receiverValidateResult = false;
                            break;
                        } else {
                            receiverValidateResult = true;
                        }
                    }

                } else {
                    // 如果没有权限组，则认为接收者验证成功
                    receiverValidateResult = true;
                }

                if (receiverValidateResult) {
                    // 如果接收者验证成功，则生成对应的接收者信息
                    if (taskReceiver.getReceiverCategory() != null) {
                        WflInsTaskReceiver insTaskReceiver = new WflInsTaskReceiver();
                        insTaskReceiver.setInstanceId(instance.getInstanceId());
                        insTaskReceiver.setInsTaskId(insTask.getInsTaskId());
                        insTaskReceiver.setPostValidationFlag("Y");
                        insTaskReceiver.setReceiverCategory(taskReceiver.getReceiverCategory());
                        insTaskReceiver.setReceiverId(taskReceiver.getReceiverId());
                        insTaskReceiver.setReceiverParam1Code(taskReceiver.getReceiverParam1Code());
                        insTaskReceiver.setReceiverParam1Id(taskReceiver.getReceiverParam1Id());
                        insTaskReceiver.setReceiverParam2Code(taskReceiver.getReceiverParam2Code());
                        insTaskReceiver.setReceiverParam2Id(taskReceiver.getReceiverParam2Id());
                        insTaskReceiver.setReceiverParam3Code(taskReceiver.getReceiverParam3Code());
                        insTaskReceiver.setReceiverParam3Id(taskReceiver.getReceiverParam3Id());
                        insTaskReceiver.setReceiverParam4Code(taskReceiver.getReceiverParam4Code());
                        insTaskReceiver.setReceiverParam4Id(taskReceiver.getReceiverParam4Id());
                        insTaskReceiver.setReceiverType(taskReceiver.getReceiverType());
                        insTaskReceiver.setSequence(taskReceiver.getSequence());
                        insTaskReceiver = wflInsTaskReceiverService.insertSelective(context, insTaskReceiver);

                    }
                }
            }
        }
    }

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVersionProcess process,
                    List<WflInsTask> insTaskList) {
        if (insTaskList != null) {
            for (WflInsTask insTask : insTaskList) {
                generateInsTaskReceiver(context, instance, process, insTask);
            }
        }
    }

    /**
     * User: MouseZhou Date: 2018/5/28 para:docCategory 单据类型 docId 单据ID Purpose:初始化工作流事件,任务,网关,流
     */
    @Override
    public WflInstance initInstance(IRequest context, String docCategory, String docId, Long processId) {
        WflVersionProcess process = null;
        if (processId == null) {
            // 寻找匹配优先级最高的流程
            process = getProcess(context, docCategory, docId);
        } else {
            Long version = versionProcessService.getProcessCurrentVersion(processId);
            process = getProcess(processId, version);
        }

        // 生成流程实例
        WflInstance instance = generateInstance(context, process, docCategory, docId);

        // 初始化元素实例(避免因为添加新的节点而导致流程产生变化)
        List<WflInsTask> insTaskList = new ArrayList<WflInsTask>();
        generateInstanceElement(context, instance, process, insTaskList);

        // 初始化接收者
        generateReceiver(context, instance, process, insTaskList);

        return instance;

    }

    /**
     * User: MouseZhou Date: 2018/5/28 para:docCategory 单据类型 docId 单据ID Purpose:初始化工作流事件,任务,网关,流 启动工作流
     */
    @Override
    public WflInstance startupInstance(IRequest context, String docCategory, String docId) {
        // 实例化工作流程
        WflInstance instance = self().initInstance(context, docCategory, docId, null);

        WflInstance resInstance = wflInstanceService.selectByPrimaryKey(context, instance);

        WflVersionProcess process =
                        getCache().getValue(WflVersionUtil.getVersion(instance.getProcessId(), instance.getVersion()));

        self().startupInstance(context, instance, process);

        return instance;
    }

    @Override
    public void startupInstance(IRequest context, WflInstance instance, WflVersionProcess process) {
        // 1、更新当前工作流实例状态为处理中
        instance.setStatus(WflInstance.STATUS_IN_PROGRESS);
        wflInstanceService.updateByPrimaryKey(context, instance);

        // 2、找到启动事件实例，设置启动事件抵达状态为:ARRIVED
        WflInsEvent insEvent = wflEventEngineService.startProcess(context, instance, process);

        // 3、调用nextProcess方法 找到开始事件的flow，开始事件下有且只有一条流
        List<WflInsFlow> insFlowList = wflInsFlowService.getNextFlow(WflEvent.ELEMENT_EVENT, insEvent.getInsEventId());

        if (insFlowList.size() == 1) {
            WflInsFlow insFlow = insFlowList.get(0);
            self().nextProcess(context, instance, insFlow, process);
        } else if (insFlowList.size() == 0) {
            // 如果开始事件后续没有流程，抛出错误
            throw new RuntimeException("当前工作流实例开始事件后没有后续的处理节点，请联系管理员!InstanceId:" + instance.getInstanceId()
                            + ",ProcessId:" + instance.getProcessId() + ",ElementType:" + WflEvent.ELEMENT_EVENT
                            + ",InsElementId:" + insEvent.getInsEventId());
        } else {
            // 如果开始事件后续有多个流程，抛出错误
            throw new RuntimeException("当前工作流实例开始事件后有多个后续的处理节点，请联系管理员!InstanceId:" + instance.getInstanceId()
                            + ",ProcessId:" + instance.getProcessId() + ",ElementType:" + WflEvent.ELEMENT_EVENT
                            + ",InsElementId:" + insEvent.getInsEventId());
        }
    }


    public void stopInstance(IRequest context, WflInstance instance, String status, WflVersionProcess process) {
        // 3.1 找到当前抵达状态的工作流元素，调用停止过程
        List<Map> arrivedElementList = wflInstanceService.getArrivedElement(instance.getInstanceId());
        for (Map arrivedElement : arrivedElementList) {
            if (WflEvent.ELEMENT_EVENT.equals(arrivedElement.get("element_type"))) {
                WflInsEvent insEvent = new WflInsEvent();
                insEvent.setInsEventId(Long.parseLong(arrivedElement.get("ins_element_id").toString()));
                insEvent = wflInsEventService.selectByPrimaryKey(context, insEvent);
                wflEventEngineService.interruptEvent(context, insEvent, process);
            } else if (WflFlow.ELEMENT_FLOW.equals(arrivedElement.get("element_type"))) {
                WflInsFlow insFlow = new WflInsFlow();
                insFlow.setInsFlowId(Long.parseLong(arrivedElement.get("ins_element_id").toString()));
                insFlow = wflInsFlowService.selectByPrimaryKey(context, insFlow);
                wflFlowEngineService.interruptFlow(context, insFlow, process);
            } else if (WflTask.ELEMENT_TASK.equals(arrivedElement.get("element_type"))) {
                WflInsTask insTask = new WflInsTask();
                insTask.setInsTaskId(Long.parseLong(arrivedElement.get("ins_element_id").toString()));
                insTask = wflInsTaskService.selectByPrimaryKey(context, insTask);
                wflTaskEngineService.interruptTask(context, insTask, process);
            } else if (WflGateway.ELEMENT_GATEWAY.equals(arrivedElement.get("element_type"))) {
                WflInsGateway insGateway = new WflInsGateway();
                insGateway.setInsGatewayId(Long.parseLong(arrivedElement.get("ins_element_id").toString()));
                insGateway = wflInsGatewayService.selectByPrimaryKey(context, insGateway);
                wflGatewayEngineService.interruptGateway(context, insGateway, process);
            }
        }


        // 3.3 设置工作流状态为结束
        instance.setStatus(status);
        wflInstanceService.updateByPrimaryKey(context, instance);

        // 3.2 找到结束时过程，执行
        WflProcedure procedure = new WflProcedure();
        procedure.setProcedureId(process.getEndProcedureId());

        wflPorcedureEngineService.executeProcedure(context, procedure);
    }

    @Override
    public void shutdownInstance(IRequest context, WflInstance instance, WflVersionProcess process) {
        stopInstance(context, instance, WflInstance.STATUS_FINISHED, process);
    }

    @Override
    public void interruptInstance(IRequest context, WflInstance instance, WflVersionProcess process) {
        stopInstance(context, instance, WflInstance.STATUS_INTERRUPTED, process);
    }

    /**
     * 流程下一步操作： 1、找到后续的流 2、找到流的后续的元素 a、事件：结束事件，结束当前流程 b、任务：调用任务引擎的enterTask，返回
     * c、网关：调用网关引擎的enterGateway，返回
     *
     * @param context 上下文
     * @param instance 工作流实例
     * @param insFlow 工作流实例
     */
    @Override
    public void nextProcess(IRequest context, WflInstance instance, WflInsFlow insFlow, WflVersionProcess process) {

        // 设置每个flow的抵达状态为ARRIVED
        wflFlowEngineService.arriveFlow(context, insFlow, process);
        // 设置每个flow的抵达状态为PASSED
        // //避免因为下个节点是结束事件导致的当前Flow没有完结的问题
        wflFlowEngineService.passFlow(context, insFlow, process);

        // 找到当前流的下一个节点
        if (WflEvent.ELEMENT_EVENT.equals(insFlow.getToElementType())) {
            // 如果是事件节点
            // 获取事件实例
            WflInsEvent insEvent = new WflInsEvent();
            insEvent.setInsEventId(insFlow.getToElementId());
            insEvent = wflInsEventService.selectByPrimaryKey(context, insEvent);

            WflVersionEvent event =
                            process.getEvent(WflVersionUtil.getVersion(insEvent.getEventId(), instance.getVersion()));

            // 如果当前事件类型是END类型，则执行instance结束的过程
            if (WflEvent.EVENT_TYPE_END.equals(event.getEventType())) {
                wflEventEngineService.stopProcess(context, instance, insEvent, process);
            } else {
                throw new RuntimeException("当前只支持结束类型的事件，请联系管理员!InstanceId:" + instance.getInstanceId() + ",InsEventId:"
                                + insEvent.getInsEventId() + ",eventId:" + event.getEventId());
            }

        } else if (WflTask.ELEMENT_TASK.equals(insFlow.getToElementType())) {
            // 如果是任务节点
            // 获取任务实例
            WflInsTask insTask = new WflInsTask();
            insTask.setInsTaskId(insFlow.getToElementId());
            insTask = wflInsTaskService.selectByPrimaryKey(context, insTask);
            wflTaskEngineService.enterTask(context, instance, insTask, process);
            wflTaskEngineService.nextProcess(context, instance, insTask, process);
        } else if (WflGateway.ELEMENT_GATEWAY.equals(insFlow.getToElementType())) {
            // 如果是网关节点
            WflInsGateway insGateway = new WflInsGateway();
            insGateway.setInsGatewayId(insFlow.getToElementId());
            insGateway = wflInsGatewayService.selectByPrimaryKey(context, insGateway);
            wflGatewayEngineService.nextProcess(context, instance, insGateway, process);
        }

    }

    /**
     * 生成流程实例 docCategory和tableLevelCode来获取唯一fnd_doc_info
     * 其中的docNumber和DocAmount字段对应number_field_name和amount_field_name
     *
     * @param context
     * @param process
     * @param docCategory
     * @param docId
     * @author mouse 2019-03-04 14:22
     * @return com.hand.hec.wfl.dto.WflInstance
     */
    @Override
    public WflInstance createInstance(IRequest context, WflVersionProcess process, String docCategory, String docId) {
        WflInstance instance = new WflInstance();

        FndDocInfo docInfo = fndDocEngineService.getDocHeadInfo(docCategory);
        Map headData = fndDocEngineService.getHeadDocData(docCategory, docId);

        String docNumber = headData.get(docInfo.getNumberFieldName()).toString();
        instance.setDocCategory(docCategory);

        if(headData.get(docInfo.getTypeFieldName()) != null){
            Long docTypeId = Long.parseLong(headData.get(docInfo.getTypeFieldName()).toString());
            instance.setDocTypeId(docTypeId);
        }

        instance.setDocId(Integer.valueOf(docId).longValue());
        instance.setDocNumber(docNumber);

        // 初始化实例描述默认给空
        String instanceDesc = wflUtilService.getInstanceDesc(instance);
        instance.setInstanceDesc(instanceDesc);

        instance.setProcessId(process.getProcessId());
        instance.setStatus(WflInstance.STATUS_INITIAL);
        instance.setVersion(process.getVersion());

        BigDecimal docAmount = fndDocEngineService.getDocAmount(docCategory, docId);
        instance.setDocAmount(docAmount);

        instance = wflInstanceService.insertSelective(context, instance);
        return instance;
    }

    private WflVersionProcessCache getCache() {
        return (WflVersionProcessCache) ApplicationContextHelper.getApplicationContext()
                        .getBean("wflVersionProcessCache");
    }

}
