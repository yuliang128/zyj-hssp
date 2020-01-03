package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.exception.WflProcessGenerateException;
import com.hand.hec.wfl.mapper.WflProcessMapper;
import com.hand.hec.wfl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflProcessServiceImpl extends BaseServiceImpl<WflProcess> implements IWflProcessService {

    ThreadLocal<IRequest> requestLocal = new ThreadLocal<IRequest>();

    @Autowired
    private WflProcessMapper wflProcessMapper;

    @Autowired
    private IWflEventService eventService;

    @Autowired
    private IWflGatewayService gatewayService;

    @Autowired
    private IWflTaskService taskService;

    @Autowired
    private IWflFlowService flowService;

    @Autowired
    private IWflProcessActionService actionService;

    @Autowired
    private IWflVersionProcessService versionProcessService;

    @Autowired
    private IWflVersionEventService versionEventService;

    @Autowired
    private IWflVersionTaskService versionTaskService;

    @Autowired
    private IWflVersionGatewayService versionGatewayService;

    @Autowired
    private IWflVersionFlowService versionFlowService;

    @Autowired
    private IWflUserTaskService userTaskService;

    @Autowired
    private IWflVersionUserTaskService versionUserTaskService;

    @Autowired
    private IWflSubProcessTaskService subProcessTaskService;

    @Autowired
    private IWflVerSubProcessTaskService verSubProcessTaskService;

    @Autowired
    private IWflTaskReceiverService receiverService;

    @Autowired
    private IWflVerTaskReceiverService verTaskReceiverService;

    @Autowired
    private IWflTaskRcvBizDetailService rcvBizDetailService;

    @Autowired
    private IWflVerTaskRcvBizDetailService verTaskRcvBizDetailService;

    @Autowired
    private IWflGatewayBizRuleGroupService groupService;

    @Autowired
    private IWflGatewayBizRuleDetailService detailService;

    @Autowired
    private IWflVerGtwBizRuleGroupService verGtwBizRuleGroupService;

    @Autowired
    private IWflVerGtwBizRuleDetailService verGtwBizRuleDetailService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public int deleteByPrimaryKey(WflProcess process) {
        //
        // 删除当前工作流下的事件
        // ------------------------------------------------------------------------------
        WflEvent queryEvent = new WflEvent();
        queryEvent.setProcessId(process.getProcessId());
        List<WflEvent> eventList = eventService.select(getRequest(), queryEvent, 0, 0);
        if (eventList != null && eventList.size() != 0) {
            eventService.batchDelete(eventList);
        }

        //
        // 删除当前工作流下的任务
        // ------------------------------------------------------------------------------
        WflTask queryTask = new WflTask();
        queryTask.setProcessId(process.getProcessId());
        List<WflTask> taskList = taskService.select(getRequest(), queryTask, 0, 0);
        if (taskList != null && taskList.size() != 0) {
            taskService.setRequest(getRequest());
            taskService.batchDelete(taskList);
        }

        //
        // 删除当前工作流下的网关
        // ------------------------------------------------------------------------------
        WflGateway queryGateway = new WflGateway();
        queryGateway.setProcessId(process.getProcessId());
        List<WflGateway> gatewayList = gatewayService.select(getRequest(), queryGateway, 0, 0);
        if (gatewayList != null && gatewayList.size() != 0) {
            gatewayService.setRequest(getRequest());
            gatewayService.batchDelete(gatewayList);
        }

        //
        // 删除当前工作流下的流转
        // ------------------------------------------------------------------------------
        WflFlow queryFlow = new WflFlow();
        queryFlow.setProcessId(process.getProcessId());
        List<WflFlow> flowList = flowService.select(getRequest(), queryFlow, 0, 0);
        if (flowList != null && flowList.size() != 0) {
            flowService.batchDelete(flowList);
        }

        //
        // 删除当前工作流下的动作
        // ------------------------------------------------------------------------------
        WflProcessAction queryAction = new WflProcessAction();
        queryAction.setProcessId(process.getProcessId());
        List<WflProcessAction> actionList = actionService.select(getRequest(), queryAction, 0, 0);
        if (actionList != null && actionList.size() != 0) {
            actionService.batchDelete(actionList);
        }

        return super.deleteByPrimaryKey(process);
    }


    @Override
    public List<Map> queryAllElement(IRequest context, Long processId) {
        return wflProcessMapper.queryAllElement(processId);
    }

    @Override
    public List<WflProcess> queryWflProcess(IRequest context, WflProcess wflProcess) {
        return wflProcessMapper.queryWflProcess(wflProcess);
    }

    @Override
    public List<WflProcess> queryProcessNameLov(IRequest context, WflProcess wflProcess) {
        return wflProcessMapper.queryProcessNameLov(wflProcess);
    }

    @Override
    public List<WflProcess> queryProcessCodeAndName(IRequest context, Long processId) {
        return wflProcessMapper.queryProcessCodeAndName(processId);
    }

    /**
     * 自动生成工作流 如果当前工作流后期有手动调整，且强制生成标志位N，则抛出异常
     *
     * @param forceGenerate 是否强制生成
     * @return void
     * @author mouse 2019-02-27 19:10
     */
    @Override
    public void generateProcess(IRequest context, WflProcess wflProcess, String forceGenerate)
                    throws WflProcessGenerateException {
        // 判断当前流程的配置方式，如果为STANDARD配置，且forceGenerate为空或者为N，说明之前手动调过配置，需要二次确认
        if (WflProcess.CONFIG_TYPE_STANDARD.equals(wflProcess.getConfigType())
                        && (forceGenerate == null || "N".equals(forceGenerate))) {
            throw new WflProcessGenerateException("WFL", "wfl_process.generate_process_error", null);
        }

        // 自动生成工作流的流程
        // 1. 删除当前工作流的所有事件、流转、网关
        // 2. 查询出所有的任务
        // 3. 根据任务的顺序将所有的任务插入到Map中
        // 4. 根据任务的顺序判断当前节点
        // ****a. 单任务，顺序流程
        // ****b. 多任务，并行流程
        // 5. 第一个节点，生成开始事件
        // 6. 最后一个节点，生成结束事件

        // Step1 删除历史数据

        //
        // 删除所有事件
        // ------------------------------------------------------------------------------
        WflEvent queryEvent = new WflEvent();
        queryEvent.setProcessId(wflProcess.getProcessId());
        List<WflEvent> eventList = eventService.select(context, queryEvent, 0, 0);
        if (eventList != null && eventList.size() != 0) {
            eventService.batchDelete(eventList);
        }


        //
        // 删除所有网关
        // ------------------------------------------------------------------------------
        WflGateway queryGateway = new WflGateway();
        queryGateway.setProcessId(wflProcess.getProcessId());
        List<WflGateway> gatewayList = gatewayService.select(context, queryGateway, 0, 0);
        if (gatewayList != null && gatewayList.size() != 0) {
            gatewayService.setRequest(context);
            gatewayService.batchDelete(gatewayList);
        }


        //
        // 删除所有流转
        // ------------------------------------------------------------------------------
        WflFlow queryFlow = new WflFlow();
        queryFlow.setProcessId(wflProcess.getProcessId());
        List<WflFlow> flowList = flowService.select(context, queryFlow, 0, 0);
        if (flowList != null && flowList.size() != 0) {
            flowService.batchDelete(flowList);
        }

        // Step2 查询出当前的任务
        WflTask queryTask = new WflTask();
        queryTask.setProcessId(wflProcess.getProcessId());
        List<WflTask> taskList = taskService.select(context, queryTask, 0, 0);

        if (taskList == null && taskList.size() == 0) {
            return;
        }

        // Step3 生成任务的Sequence的TreeSet
        TreeSet<Long> sequenceSet = new TreeSet<Long>();
        HashMap<Long, List<WflTask>> taskMap = new HashMap<Long, List<WflTask>>();
        taskList.forEach(task -> {
            sequenceSet.add(task.getSequence());

            if (taskMap.get(task.getSequence()) == null) {
                taskMap.put(task.getSequence(), new ArrayList<WflTask>());
            }

            taskMap.get(task.getSequence()).add(task);
        });

        // Step4 生成对应的 事件、网关、流转
        WflElement preElement = null;

        WflEvent startEvent = new WflEvent();
        startEvent.setProcessId(wflProcess.getProcessId());
        startEvent.setEventCode("START");
        startEvent.setEventName("开始");
        startEvent.setEventType(WflEvent.EVENT_TYPE_START);
        startEvent = eventService.insertSelective(context, startEvent);

        preElement = convertElement(startEvent);

        Iterator<Long> seqIterator = sequenceSet.iterator();
        while (seqIterator.hasNext()) {
            Long seq = seqIterator.next();
            List<WflTask> curTaskList = taskMap.get(seq);

            // 如果当前是单个任务的节点，说明是串行节点，直接生成Flow与前置节点串接
            if (curTaskList.size() == 1) {
                WflTask singleTask = curTaskList.get(0);
                WflFlow singleFlow = new WflFlow();
                singleFlow.setProcessId(wflProcess.getProcessId());
                singleFlow.setFlowCode(getFlowCode(preElement.getElementType(), preElement.getElementCode(),
                                WflTask.ELEMENT_TASK, singleTask.getTaskCode()));

                singleFlow.setFromElementCode(preElement.getElementCode());
                singleFlow.setFromElementId(preElement.getElementId());
                singleFlow.setFromElementName(preElement.getElementName());
                singleFlow.setFromElementType(preElement.getElementType());

                singleFlow.setToElementCode(singleTask.getTaskCode());
                singleFlow.setToElementId(singleTask.getTaskId());
                singleFlow.setToElementName(singleTask.getTaskName());
                singleFlow.setToElementType(WflTask.ELEMENT_TASK);

                singleFlow = flowService.insertSelective(context, singleFlow);

                preElement = convertElement(singleTask);
            } else {
                // 同一顺序多任务的情况，认为都是并行任务，设置发散和收敛网关
                WflGateway startGateway = new WflGateway();
                startGateway.setGatewayCode("GATEWAY-START-" + seq.intValue());
                startGateway.setGatewayType(WflGateway.GATEWAY_TYPE_PARALLEL);
                startGateway.setGatewayName("节点" + seq.intValue() + "发散网关");
                startGateway.setProcessId(wflProcess.getProcessId());
                startGateway = gatewayService.insertSelective(context, startGateway);

                WflGateway endGateway = new WflGateway();
                endGateway.setGatewayCode("GATEWAY-END-" + seq.intValue());
                endGateway.setGatewayType(WflGateway.GATEWAY_TYPE_PARALLEL);
                endGateway.setGatewayName("节点" + seq.intValue() + "收敛网关");
                endGateway.setProcessId(wflProcess.getProcessId());
                endGateway = gatewayService.insertSelective(context, endGateway);

                // 循环所有任务，分别建立和发散、收敛网关的FLOW
                for (WflTask curTask : curTaskList) {
                    // 从发散网关到当前任务的Flow
                    WflFlow startFlow = new WflFlow();
                    startFlow.setProcessId(wflProcess.getProcessId());
                    startFlow.setFlowCode(getFlowCode(WflGateway.ELEMENT_GATEWAY, startGateway.getGatewayCode(),
                                    WflTask.ELEMENT_TASK, curTask.getTaskCode()));

                    startFlow.setFromElementCode(startGateway.getGatewayCode());
                    startFlow.setFromElementId(startGateway.getGatewayId());
                    startFlow.setFromElementName(startGateway.getGatewayName());
                    startFlow.setFromElementType(WflGateway.ELEMENT_GATEWAY);

                    startFlow.setToElementCode(curTask.getTaskCode());
                    startFlow.setToElementId(curTask.getTaskId());
                    startFlow.setToElementName(curTask.getTaskName());
                    startFlow.setToElementType(WflTask.ELEMENT_TASK);

                    startFlow = flowService.insertSelective(context, startFlow);

                    // 从当前任务到收敛网关的Flow
                    WflFlow endFlow = new WflFlow();
                    endFlow.setProcessId(wflProcess.getProcessId());
                    endFlow.setFlowCode(getFlowCode(WflTask.ELEMENT_TASK, curTask.getTaskCode(),
                                    WflGateway.ELEMENT_GATEWAY, endGateway.getGatewayCode()));

                    endFlow.setFromElementCode(curTask.getTaskCode());
                    endFlow.setFromElementId(curTask.getTaskId());
                    endFlow.setFromElementName(curTask.getTaskName());
                    endFlow.setFromElementType(WflTask.ELEMENT_TASK);

                    endFlow.setToElementCode(endGateway.getGatewayCode());
                    endFlow.setToElementId(endGateway.getGatewayId());
                    endFlow.setToElementName(endGateway.getGatewayName());
                    endFlow.setToElementType(WflGateway.ELEMENT_GATEWAY);

                    endFlow = flowService.insertSelective(context, endFlow);
                }

                // 建立前置元素和发散网关的流转
                WflFlow singleFlow = new WflFlow();
                singleFlow.setProcessId(wflProcess.getProcessId());
                singleFlow.setFlowCode(getFlowCode(preElement.getElementType(), preElement.getElementCode(),
                                WflGateway.ELEMENT_GATEWAY, startGateway.getGatewayCode()));

                singleFlow.setFromElementCode(preElement.getElementCode());
                singleFlow.setFromElementId(preElement.getElementId());
                singleFlow.setFromElementName(preElement.getElementName());
                singleFlow.setFromElementType(preElement.getElementType());

                singleFlow.setToElementCode(startGateway.getGatewayCode());
                singleFlow.setToElementId(startGateway.getGatewayId());
                singleFlow.setToElementName(startGateway.getGatewayName());
                singleFlow.setToElementType(WflGateway.ELEMENT_GATEWAY);

                singleFlow = flowService.insertSelective(context, singleFlow);

                // 设置前置元素为收敛网关
                preElement = convertElement(endGateway);
            }
        }

        // 生成结束事件
        WflEvent endEvent = new WflEvent();
        endEvent.setProcessId(wflProcess.getProcessId());
        endEvent.setEventCode("END");
        endEvent.setEventName("结束");
        endEvent.setEventType(WflEvent.EVENT_TYPE_END);
        endEvent = eventService.insertSelective(context, endEvent);

        // 生成前置元素与结束事件之间的流转
        WflFlow endFlow = new WflFlow();
        endFlow.setProcessId(wflProcess.getProcessId());
        endFlow.setFlowCode(getFlowCode(preElement.getElementType(), preElement.getElementCode(),
                        WflEvent.ELEMENT_EVENT, endEvent.getEventCode()));

        endFlow.setFromElementCode(preElement.getElementCode());
        endFlow.setFromElementId(preElement.getElementId());
        endFlow.setFromElementName(preElement.getElementName());
        endFlow.setFromElementType(preElement.getElementType());

        endFlow.setToElementCode(endEvent.getEventCode());
        endFlow.setToElementId(endEvent.getEventId());
        endFlow.setToElementName(endEvent.getEventName());
        endFlow.setToElementType(WflEvent.ELEMENT_EVENT);

        endFlow = flowService.insertSelective(context, endFlow);
    }

    private WflElement convertElement(WflEvent event) {
        WflElement element = new WflElement();

        element.setElementId(event.getEventId());
        element.setElementType(WflEvent.ELEMENT_EVENT);
        element.setElementCode(event.getEventCode());
        element.setElementName(event.getEventName());

        return element;
    }

    private WflElement convertElement(WflTask task) {
        WflElement element = new WflElement();

        element.setElementId(task.getTaskId());
        element.setElementType(WflTask.ELEMENT_TASK);
        element.setElementCode(task.getTaskCode());
        element.setElementName(task.getTaskName());

        return element;
    }

    private WflElement convertElement(WflGateway gateway) {
        WflElement element = new WflElement();

        element.setElementId(gateway.getGatewayId());
        element.setElementType(WflGateway.ELEMENT_GATEWAY);
        element.setElementCode(gateway.getGatewayCode());
        element.setElementName(gateway.getGatewayName());

        return element;
    }

    private String getFlowCode(String preElementType, String preElementCode, String nextElementType,
                    String nextElementCode) {
        return "FLOW-[" + preElementType + "_" + preElementCode + "]->[" + nextElementType + "_" + nextElementCode
                        + "]";
    }

    /**
     * 调整工作流配置类型
     *
     * @param context
     * @param processId
     * @param configType
     * @return void
     * @author mouse 2019-02-27 20:00
     */
    @Override
    public void changeConfigType(IRequest context, Long processId, String configType) {
        wflProcessMapper.changeConfigType(processId, configType);
    }

    /**
     * 发布工作流
     *
     * @param context
     * @param wflProcess
     * @return void
     * @author mouse 2019-03-01 11:47
     */
    @Override
    public synchronized void publishProcess(IRequest context, WflProcess wflProcess) {
        // 发布工作流顺序：
        // 1. WflProcess
        // 2. WflEvent
        // 3. WflFlow
        // 4. WflTask
        // 5. WflGateway
        // 6. WflUserTask
        // 7. WflSubProcessTask
        // 8. WflTaskReceiver
        // 9. WflTaskRcvBizDetail
        // 10. WflGtwBizRuleGroup
        // 11. WflGtwBizRuleDetail
        wflProcess = selectByPrimaryKey(context, wflProcess);

        // 版本 ++
        Long version = versionProcessService.getProcessCurrentVersion(wflProcess.getProcessId());
        if (version == null) {
            version = 0L;
        }
        version++;

        //
        // 生成工作流的已发布版本信息
        // ------------------------------------------------------------------------------
        WflVersionProcess versionProcess = new WflVersionProcess(wflProcess);
        versionProcess.setVersion(version);
        versionProcess = versionProcessService.insert(context, versionProcess);

        //
        // 生成工作流流转的已发布版本信息
        // ------------------------------------------------------------------------------
        WflEvent queryEvent = new WflEvent();
        queryEvent.setProcessId(wflProcess.getProcessId());
        List<WflEvent> eventList = eventService.select(context, queryEvent, 0, 0);
        for (WflEvent event : eventList) {
            WflVersionEvent versionEvent = new WflVersionEvent(event);
            versionEvent.setVersion(version);
            versionEventService.insert(context, versionEvent);
        }

        //
        // 生成工作流任务的已发布版本信息
        // ------------------------------------------------------------------------------
        WflTask queryTask = new WflTask();
        queryTask.setProcessId(wflProcess.getProcessId());
        List<WflTask> taskList = taskService.select(context, queryTask, 0, 0);
        for (WflTask task : taskList) {
            WflVersionTask versionTask = new WflVersionTask(task);
            versionTask.setVersion(version);
            versionTaskService.insert(context, versionTask);

            //
            // 生成工作流用户任务的已发布版本信息
            // ------------------------------------------------------------------------------
            WflUserTask queryUserTask = new WflUserTask();
            queryUserTask.setTaskId(task.getTaskId());
            List<WflUserTask> userTaskList = userTaskService.select(context, queryUserTask, 0, 0);
            for (WflUserTask userTask : userTaskList) {
                WflVersionUserTask versionUserTask = new WflVersionUserTask(userTask);
                versionUserTask.setVersion(version);
                versionUserTaskService.insert(context, versionUserTask);
            }

            //
            // 生成工作流子流程任务的已发布版本信息
            // ------------------------------------------------------------------------------
            WflSubProcessTask querySubProcessTask = new WflSubProcessTask();
            querySubProcessTask.setTaskId(task.getTaskId());
            List<WflSubProcessTask> subProcessTaskList =
                            subProcessTaskService.select(context, querySubProcessTask, 0, 0);
            for (WflSubProcessTask subProcessTask : subProcessTaskList) {
                WflVerSubProcessTask verSubProcessTask = new WflVerSubProcessTask(subProcessTask);
                verSubProcessTask.setVersion(version);
                verSubProcessTaskService.insert(context, verSubProcessTask);
            }

            //
            // 生成工作流接受者的已发布版本信息
            // ------------------------------------------------------------------------------
            WflTaskReceiver queryReceiver = new WflTaskReceiver();
            queryReceiver.setTaskId(task.getTaskId());
            List<WflTaskReceiver> receiverList = receiverService.select(context, queryReceiver, 0, 0);
            for (WflTaskReceiver receiver : receiverList) {
                WflVerTaskReceiver verTaskReceiver = new WflVerTaskReceiver(receiver);
                verTaskReceiver.setVersion(version);
                verTaskReceiverService.insert(context, verTaskReceiver);

                //
                // 生成工作流接收者的权限规则的已发布版本信息
                // ------------------------------------------------------------------------------
                WflTaskRcvBizDetail queryRcvBizDetail = new WflTaskRcvBizDetail();
                queryRcvBizDetail.setReceiverId(receiver.getReceiverId());
                List<WflTaskRcvBizDetail> detailList = rcvBizDetailService.select(context, queryRcvBizDetail, 0, 0);
                for (WflTaskRcvBizDetail detail : detailList) {
                    WflVerTaskRcvBizDetail verTaskRcvBizDetail = new WflVerTaskRcvBizDetail(detail);
                    verTaskRcvBizDetail.setVersion(version);
                    verTaskRcvBizDetailService.insert(context, verTaskRcvBizDetail);
                }
            }
        }

        //
        // 生成工作流网关的已发布版本信息
        // ------------------------------------------------------------------------------
        WflGateway queryGateway = new WflGateway();
        queryGateway.setProcessId(wflProcess.getProcessId());
        List<WflGateway> gatewayList = gatewayService.select(context, queryGateway, 0, 0);
        for (WflGateway gateway : gatewayList) {
            WflVersionGateway versionGateway = new WflVersionGateway(gateway);
            versionGateway.setVersion(version);
            versionGatewayService.insert(context, versionGateway);

            //
            // 生成工作流网关权限组的已发布版本信息
            // ------------------------------------------------------------------------------
            WflGatewayBizRuleGroup queryGroup = new WflGatewayBizRuleGroup();
            queryGroup.setGatewayId(gateway.getGatewayId());
            List<WflGatewayBizRuleGroup> groupList = groupService.select(context, queryGroup, 0, 0);
            for (WflGatewayBizRuleGroup group : groupList) {
                WflVerGtwBizRuleGroup gtwBizRuleGroup = new WflVerGtwBizRuleGroup(group);
                gtwBizRuleGroup.setVersion(version);
                verGtwBizRuleGroupService.insert(context, gtwBizRuleGroup);

                //
                // 生成网关权限组明细的已发布版本信息
                // ------------------------------------------------------------------------------
                WflGatewayBizRuleDetail queryDetail = new WflGatewayBizRuleDetail();
                queryDetail.setGroupId(group.getGroupId());
                List<WflGatewayBizRuleDetail> detailList = detailService.select(context, queryDetail, 0, 0);
                for (WflGatewayBizRuleDetail detail : detailList) {
                    WflVerGtwBizRuleDetail verGtwBizRuleDetail = new WflVerGtwBizRuleDetail(detail);
                    verGtwBizRuleDetail.setVersion(version);
                    verGtwBizRuleDetailService.insert(context, verGtwBizRuleDetail);
                }
            }
        }

        //
        // 生成工作流流转的已发布版本信息
        // ------------------------------------------------------------------------------
        WflFlow queryFlow = new WflFlow();
        queryFlow.setProcessId(wflProcess.getProcessId());
        List<WflFlow> flowList = flowService.select(context, queryFlow, 0, 0);
        for (WflFlow flow : flowList) {
            WflVersionFlow versionFlow = new WflVersionFlow(flow);
            versionFlow.setVersion(version);
            versionFlowService.insert(context, versionFlow);
        }
    }

    @Override
    public void setRequest(IRequest request) {
        requestLocal.set(request);
    }

    @Override
    public IRequest getRequest() {
        return requestLocal.get();
    }
}
