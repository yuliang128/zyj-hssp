package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.components.ApplicationContextHelper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.wfl.cache.WflVersionProcessCache;
import com.hand.hec.wfl.cache.WflVersionUtil;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/29 \* Time: 16:39 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflTaskEngineServiceImpl implements IWflTaskEngineService {

    @Autowired
    IWflInsTaskService wflInsTaskService;

    @Autowired
    IWflTaskService wflTaskService;

    @Autowired
    IWflEngineService wflEngineService;

    @Autowired
    IWflSubProcessTaskService wflSubProcessTaskService;

    @Autowired
    IWflUserTaskService wflUserTaskService;

    @Autowired
    IWflInsFlowService wflInsFlowService;

    @Autowired
    IWflInsTaskReceiverService wflInsTaskReceiverService;

    @Autowired
    IWflInsTaskHierarchyService wflInsTaskHierarchyService;

    @Autowired
    IWflInsTaskRecipientService wflInsTaskRecipientService;

    @Autowired
    IWflInsTaskReceiverHtService wflInsTaskReceiverHtService;

    @Autowired
    IWflInsTaskHierarchyHtService wflInsTaskHierarchyHtService;

    @Autowired
    IWflInsTaskRecipientHtService wflInsTaskRecipientHtService;

    @Autowired
    IWflTaskReceiverService wflTaskReceiverService;

    @Autowired
    IWflDeliverService wflDeliverService;

    @Autowired
    IWflApproveRecordService wflApproveRecordService;

    @Autowired
    IWflProcessActionService wflProcessActionService;

    @Autowired
    IWflFlowService wflFlowService;

    @Autowired
    IWflFlowEngineService wflFlowEngineService;

    @Autowired
    IWflGatewayEngineService wflGatewayEngineService;

    @Autowired
    IWflInstanceService wflInstanceService;

    @Autowired
    IWflInsGatewayService wflInsGatewayService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    IWflUtilService utilService;


    @Override
    public WflInsTask arriveTask(IRequest context, WflInsTask insTask, WflVersionProcess process) {
        insTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_ARRIVED);
        insTask.setArrivalDate(new Date());
        insTask = wflInsTaskService.updateByPrimaryKey(context, insTask);
        return insTask;
    }

    @Override
    public WflInsTask passTask(IRequest context, WflInsTask insTask, WflVersionProcess process) {
        insTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_PASSED);
        insTask = wflInsTaskService.updateByPrimaryKey(context, insTask);
        return insTask;
    }

    /**
     * User: MouseZhou Date: 2018/5/28 para:WflInstance 流程实例 WflInsTask 任务实例 Purpose:生成审批层次 默认为10
     */
    @Override
    public void generateHierarchy(IRequest context, WflInstance instance, WflInsTask insTask,
                    WflVersionProcess process) {
        int hierachyCount = 0;
        // 实例化任务
        WflVersionTask task = process.getTask(WflVersionUtil.getVersion(insTask.getTaskId(), instance.getVersion()));

        WflVersionUserTask userTask = null;
        // 用户任务需要实例化任务明细作为判断条件
        if (WflTask.TASK_TYPE_USER.equals(task.getTaskType())) {
            userTask = task.getUserTask();
        }

        // 实例化任务接收者实例
        WflInsTaskReceiver queryInsTaskReceiver = new WflInsTaskReceiver();
        queryInsTaskReceiver.setInsTaskId(insTask.getInsTaskId());
        List<WflInsTaskReceiver> insTaskReceiverList = wflInsTaskReceiverService.selectOptions(context,
                        queryInsTaskReceiver, new Criteria(queryInsTaskReceiver));


        for (WflInsTaskReceiver insTaskReceiver : insTaskReceiverList) {
            // 实例化任务接收者
            WflVerTaskReceiver taskReceiver = task.getReceiver(
                            WflVersionUtil.getVersion(insTaskReceiver.getReceiverId(), instance.getVersion()));

            IWflReceiverService wflReceiverService =
                            (IWflReceiverService) applicationContext.getBean(insTaskReceiver.getReceiverCategory());
            wflReceiverService.generateReceiver(context, instance, taskReceiver, insTaskReceiver);

            // 实例化审批层次
            WflInsTaskHierarchy insTaskHierarchy = new WflInsTaskHierarchy();
            insTaskHierarchy.setInsTaskId(insTask.getInsTaskId());
            List<WflInsTaskHierarchy> hierarchyList = wflInsTaskHierarchyService.selectOptions(context,
                            insTaskHierarchy, new Criteria(insTaskHierarchy));

            hierachyCount += hierarchyList.size();
        }


        if (userTask != null && "N".equals(userTask.getCanNoApprover()) && hierachyCount == 0) {
            // 如果是用户任务，且当前节点不允许没有接受者，抛出异常
            throw new RuntimeException("当前节点不允许无审批者,请联系管理员!InstanceId:" + instance.getInstanceId() + ",InsTaskId:"
                            + insTask.getInsTaskId() + ",TaskId:" + insTask.getTaskId());
        } else if (userTask != null && "Y".equals(userTask.getCanNoApprover()) && hierachyCount == 0) {
            // 如果当前节点的
        }
    }

    @Override
    public void generateRecipient(IRequest context, WflInstance instance, WflInsTask insTask,
                    WflVersionProcess process) {
        // 实例化任务
        WflVersionTask task = process.getTask(WflVersionUtil.getVersion(insTask.getTaskId(), instance.getVersion()));

        WflVersionUserTask userTask = null;
        // 用户任务需要实例化任务明细作为判断条件
        if (WflTask.TASK_TYPE_USER.equals(task.getTaskType())) {
            // modify@mdw 2018-05-31因为表wfl_user_task中的task_id不是主键 所以不能用selectByPrimaryKey方法
            userTask = task.getUserTask();
        }
        // 寻找当前优先级最高的待审批实例
        List<WflInsTaskHierarchy> insTaskHierarchyList =
                        wflInsTaskHierarchyService.selectReachableHierarchy(insTask.getInsTaskId());
        List<WflInsTaskRecipient> insTaskRecipientList = new ArrayList<WflInsTaskRecipient>();
        List<WflInsTaskRecipient> savedInsTaskRecipientList = new ArrayList<WflInsTaskRecipient>();
        HashMap<Long, WflInsTaskHierarchy> hierarchyMap = new HashMap<Long, WflInsTaskHierarchy>();

        for (WflInsTaskHierarchy insTaskHierarchy : insTaskHierarchyList) {
            // 检查当前审批人是否有转交配置
            List<WflDeliver> deliverList = wflDeliverService.getMatchedDeliver(context, instance, insTaskHierarchy);
            if (deliverList != null && deliverList.size() != 0) {
                // 有转交设置
                // 1、设置来源转交人的hierarchy无效，并备份
                // 2、新建目标转交人的hierarchy
                // 3、从新建转交人的hierarchy生成recipient
                // 4、更新hierarchy的状态
                insTaskHierarchy.setDisabledFlag("Y");
                insTaskHierarchy.setPostedFlag("N");
                wflInsTaskHierarchyService.updateByPrimaryKey(context, insTaskHierarchy);
                wflInsTaskHierarchyService.backupHierarchy(context, insTaskHierarchy);

                // 新增转交的审批记录
                WflApproveRecord record = new WflApproveRecord();
                record.setActionId(null);
                record.setActionType(WflProcessAction.ACTION_TYPE_DELIVER);
                record.setApproveDate(new Date());
                record.setCommentText("转交规则配置转交");
                record.setInsHierarchyId(insTaskHierarchy.getInsHierarchyId());
                record.setInsRecipientId(null);
                record.setInstanceId(instance.getInstanceId());
                record.setApproverId(insTaskHierarchy.getApproverId());
                record.setRecordId(null);
                record.setInsTaskId(insTask.getInsTaskId());
                wflApproveRecordService.insertSelective(context, record);

                for (WflDeliver deliver : deliverList) {
                    WflInsTaskHierarchy deliverInsTaskHierarchy = new WflInsTaskHierarchy();
                    deliverInsTaskHierarchy.setPostedFlag("N");
                    deliverInsTaskHierarchy.setDisabledFlag("N");
                    deliverInsTaskHierarchy.setInsTaskId(insTask.getInsTaskId());
                    deliverInsTaskHierarchy.setParentInsHierarchyId(null);
                    deliverInsTaskHierarchy.setNote("");
                    deliverInsTaskHierarchy.setInstanceId(instance.getInstanceId());
                    deliverInsTaskHierarchy.setInsReceiverId(insTaskHierarchy.getInsReceiverId());
                    deliverInsTaskHierarchy.setApproverId(deliver.getToUserId());
                    deliverInsTaskHierarchy.setInsHierarchyId(null);
                    deliverInsTaskHierarchy.setSequence(insTaskHierarchy.getSequence());

                    deliverInsTaskHierarchy =
                                    wflInsTaskHierarchyService.insertSelective(context, deliverInsTaskHierarchy);

                    hierarchyMap.put(deliverInsTaskHierarchy.getInsHierarchyId(), deliverInsTaskHierarchy);

                    WflInsTaskRecipient recipient = new WflInsTaskRecipient();
                    recipient.setSequence(deliverInsTaskHierarchy.getSequence());
                    if (userTask != null && "Y".equals(userTask.getTimeLimitedFlag())
                                    && userTask.getLimitedHours() != null) {
                        int LimitedMinutes = userTask.getLimitedHours().multiply(new BigDecimal(60)).intValue();
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MINUTE, LimitedMinutes);
                        recipient.setLimitedDate(calendar.getTime());
                    }
                    recipient.setInstanceId(instance.getInstanceId());
                    recipient.setInsRecipientId(null);
                    recipient.setInsHierarchyId(deliverInsTaskHierarchy.getInsHierarchyId());
                    recipient.setCommissionDate(new Date());
                    recipient.setCommissionBy(insTaskHierarchy.getApproverId());
                    recipient.setApproverId(deliverInsTaskHierarchy.getApproverId());
                    recipient.setInsTaskId(insTask.getInsTaskId());

                    insTaskRecipientList.add(recipient);
                }
            } else {
                // 无转交设置
                // 新建当前审批人的recipient
                // 更新hierarchy的状态
                hierarchyMap.put(insTaskHierarchy.getInsHierarchyId(), insTaskHierarchy);

                WflInsTaskRecipient recipient = new WflInsTaskRecipient();
                recipient.setSequence(insTaskHierarchy.getSequence());
                if (userTask != null && "Y".equals(userTask.getTimeLimitedFlag())
                                && userTask.getLimitedHours() != null) {
                    int LimitedMinutes = userTask.getLimitedHours().multiply(new BigDecimal(60)).intValue();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE, LimitedMinutes);
                    recipient.setLimitedDate(calendar.getTime());
                }
                recipient.setInstanceId(instance.getInstanceId());
                recipient.setInsRecipientId(null);
                recipient.setInsHierarchyId(insTaskHierarchy.getInsHierarchyId());
                recipient.setCommissionDate(new Date());
                recipient.setCommissionBy(insTaskHierarchy.getApproverId());
                recipient.setApproverId(insTaskHierarchy.getApproverId());
                recipient.setInsTaskId(insTask.getInsTaskId());

                insTaskRecipientList.add(recipient);
            }
        }


        // 插入所有的接受者
        for (WflInsTaskRecipient recipient : insTaskRecipientList) {
            recipient = wflInsTaskRecipientService.insertSelective(context, recipient);
            savedInsTaskRecipientList.add(recipient);

            // 修改hierarchy的POST状态
            WflInsTaskHierarchy hierarchy = hierarchyMap.get(recipient.getInsHierarchyId());
            hierarchy.setPostedFlag("Y");
            wflInsTaskHierarchyService.updateByPrimaryKey(context, hierarchy);
        }


        // 判断当前工作流是否允许重复审批
        if (WflTask.TASK_TYPE_USER.equals(task.getTaskType()) && userTask != null
                        && "N".equals(userTask.getCanRepeatedApprove())) {
            // 当前工作流不允许重复审批
            // 循环所有接受者，检查是否存在某个接受者已经审批过
            for (WflInsTaskRecipient recipient : savedInsTaskRecipientList) {
                if (wflInsTaskRecipientService.isRepeatedApprove(context, recipient)) {
                    // 自动审批
                    autoApprove(context, instance, recipient, "无需重复审批", process);
                    savedInsTaskRecipientList.remove(recipient);
                }
            }
        }

        // 判断当前工作流是否需要提交人复核
        if (WflTask.TASK_TYPE_USER.equals(task.getTaskType()) && userTask != null
                        && "N".equals(userTask.getCanSubmitterRecheck())) {
            // 当前工作流不需要提交人复核
            // 循环所有接受者，检查是否存在是否某个接受者是提交者
            for (WflInsTaskRecipient recipient : savedInsTaskRecipientList) {
                if (recipient.getApproverId().equals(instance.getCreatedBy())) {
                    // 自动审批
                    autoApprove(context, instance, recipient, "提交人无需审批", process);
                    savedInsTaskRecipientList.remove(recipient);
                }
            }
        }
    }

    @Override
    public void enterTask(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process) {
        generateHierarchy(context, instance, insTask, process);
        generateRecipient(context, instance, insTask, process);
    }

    @Override
    public void nextProcess(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process) {
        /**
         * 1、检查当前任务状态，如果未NOT_ARRIVED，修改为ARRIVED，同时检查按当前任务类型，如果是子流程，启动子流程
         * 2、检查当前节点是否可以通过，如果可以通过，备份审批人信息，修改当前任务状态为ARRIVED并找到下一个FLOW调用instance的nextProcess，
         * 3、当前节点不可以通过，生成待办事项
         */
        if (WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED.equals(insTask.getArrivalStatus())) {
            arriveTask(context, insTask, process);
        }
        // 实例化任务
        WflVersionTask task = process.getTask(WflVersionUtil.getVersion(insTask.getTaskId(), instance.getVersion()));
        // 子流程项目
        if (WflTask.TASK_TYPE_SUB_PROCESS.equals(task.getTaskType())) {
            WflVerSubProcessTask subProcessTask = task.getSubProcessTask();
        }

        String taskResult = self().getTaskResult(context, instance, insTask, process);
        if (taskResult == null) {
            // 如果当前任务结果为空，说明还未审批完成，结束当前nextProcess
            return;
        } else if (WflInsTask.TASK_RESULT_AGREE.equals(taskResult)) {
            // 如果当前任务为同意，找到下个流继续执行,通过当前节点
            self().backupTaskReceiver(context, instance, insTask, process);
            insTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_PASSED);
            wflInsTaskService.updateByPrimaryKey(context, insTask);

            List<WflInsFlow> nextInsFlowList =
                            wflInsFlowService.getNextFlow(WflTask.ELEMENT_TASK, insTask.getInsTaskId());
            if (nextInsFlowList.size() != 1) {
                throw new RuntimeException("当前任务后续没有流接入或者有多个流接入，请联系管理员!InstanceId:" + instance.getInstanceId()
                                + ",InsTaskId:" + insTask.getInsTaskId() + ",TaskId:" + insTask.getTaskId());
            }
            WflInsFlow nextInsFlow = nextInsFlowList.get(0);
            wflEngineService.nextProcess(context, instance, nextInsFlow, process);
        } else if (WflInsTask.TASK_RESULT_REJECT.equals(taskResult)) {
            self().backupTaskReceiver(context, instance, insTask, process);
            insTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_PASSED);
            wflInsTaskService.updateByPrimaryKey(context, insTask);

            List<WflInsFlow> nextInsFlowList =
                            wflInsFlowService.getNextFlow(WflTask.ELEMENT_TASK, insTask.getInsTaskId());
            if (nextInsFlowList.size() != 1) {
                throw new RuntimeException("当前任务后续没有流接入或者有多个流接入，请联系管理员!InstanceId:" + instance.getInstanceId()
                                + ",InsTaskId:" + insTask.getInsTaskId() + ",TaskId:" + insTask.getTaskId());
            }
            WflInsFlow nextInsFlow = nextInsFlowList.get(0);
            // 如果当前任务为拒绝
            // 判断当前是否在并行网关路径中
            // a、并行网关：设置当前任务的下个Flow为UNREACHABLE，并判断当前是否有处于处理中状态的节点，
            // 如果存在处理中节点为任务，则不执行任何操作，直接返回
            // 如果只存在处理中节点为网关，则执行网关的nextProcess
            // 如果当前没有处理中的节点，则执行结束过程
            // b、非并行网关路径:
            // 1、下个节点也是任务或者事件，自动执行instance的stopProcess
            // 2、下个节点是网关，找到下个流继续执行
            Map element = new HashMap();
            element.put("element_type", WflTask.ELEMENT_TASK);
            element.put("ins_element_id", insTask.getInsTaskId());
            WflInsGateway parallelInsGateway =
                            wflGatewayEngineService.getParallelGatewayInPath(context, instance, element, process);
            if (parallelInsGateway != null) {
                // 当前Task的前置路径存在并行网关，
                // 设置任务的下个Flow为UNREACHABLE
                wflFlowEngineService.unreachFlow(context, nextInsFlow, process);
                // 判断当前是否有处于处理中状态的节点，
                List<Map> arrivedElementList = wflInstanceService.getArrivedElement(instance.getInstanceId());
                // //如果当前没有处理中的节点，则执行工作流中止过程
                if (arrivedElementList == null || arrivedElementList.size() == 0) {
                    wflEngineService.interruptInstance(context, instance, process);
                } else {
                    // 如果存在处理中节点为任务，则不执行任何操作，直接返回
                    for (Map arrivedElement : arrivedElementList) {
                        if (WflTask.ELEMENT_TASK.equals(arrivedElement.get("element_type"))) {
                            return;
                        }
                    }
                    // 如果只存在处理中节点为网关，则执行网关的nextProcess
                    for (Map arrivedElement : arrivedElementList) {
                        if (WflGateway.ELEMENT_GATEWAY.equals(arrivedElement.get("element_type"))) {
                            WflInsGateway arrivedInsGateway = new WflInsGateway();
                            arrivedInsGateway.setInsGatewayId(
                                            Long.parseLong(arrivedElement.get("ins_element_id").toString()));
                            arrivedInsGateway = wflInsGatewayService.selectByPrimaryKey(context, arrivedInsGateway);
                            wflGatewayEngineService.nextProcess(context, instance, arrivedInsGateway, process);
                        }
                    }
                }
            } else {
                if (WflTask.ELEMENT_TASK.equals(nextInsFlow.getToElementType())
                                || WflEvent.ELEMENT_EVENT.equals(nextInsFlow.getToElementType())) {
                    wflEngineService.shutdownInstance(context, instance, process);
                } else if (WflGateway.ELEMENT_GATEWAY.equals(nextInsFlow.getToElementType())) {
                    wflEngineService.nextProcess(context, instance, nextInsFlow, process);
                }
            }
        }
    }

    @Override
    public String getTaskResult(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process) {
        // 判断当前节点是否可以结束
        // 如果当前是子流程，且子流程的设置为无需等待，直接通过
        // 如果当前是用户任务，判断当前任务是否满足通过条件
        // 1. 当前节点没有审批人
        // 2. 当前节点的审批人通过符合规则

        // 实例化任务
        WflVersionTask task = process.getTask(WflVersionUtil.getVersion(insTask.getTaskId(), instance.getVersion()));
        if (WflTask.TASK_TYPE_SUB_PROCESS.equals(task.getTaskType())) {
            WflVerSubProcessTask subProcessTask = task.getSubProcessTask();

            if ("Y".equals(subProcessTask.getTaskAutoPassFlag())) {
                return "AGREE";
            } else {
                return null;
            }
        } else if (WflTask.TASK_TYPE_USER.equals(task.getTaskType())) {
            WflVersionUserTask userTask = new WflVersionUserTask();

            // 如果当前节点没有审批人，则返回审批通过
            WflInsTaskRecipient rcpt = new WflInsTaskRecipient();
            rcpt.setInsTaskId(insTask.getInsTaskId());
            List<WflInsTaskRecipient> rcptList = wflInsTaskRecipientService.select(context, rcpt, 0, 0);
            if (rcptList.size() == 0) {
                return WflInsTask.TASK_RESULT_AGREE;
            }


            String approveType = userTask.getApproveType();

            Long peopleAgreeCount = null;
            Long ruleAgreeCount = null;
            Long peopleRejectCount = null;
            Long ruleRejectCount = null;
            Long lineAgreeCount = null;
            Long lineRejectCount = null;
            Long allPeopleCount = null;
            Long allRuleCount = null;

            // 任一人审批、全部同意、比例同意、一票通过拒绝，需要计算已同意人数
            if (WflUserTask.APPROVE_TYPE_ANY_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ALL_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_RATIO_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ONE_AGREE_REJECT.equals(approveType)) {
                peopleAgreeCount = wflInsTaskService.getPeopleAgreeCount(context, insTask.getInsTaskId());
            }

            // 任一人审批、全部同意、比例同意、一票通过拒绝，需要统计已拒绝人数
            if (WflUserTask.APPROVE_TYPE_ANY_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ALL_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_RATIO_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ONE_AGREE_REJECT.equals(approveType)) {
                peopleRejectCount = wflInsTaskService.getPeopleRejectCount(context, insTask.getInsTaskId());
            }

            // 一定比例的规则同意、任一规则同意拒绝，需要计算已同意的规则数
            if (WflUserTask.APPROVE_TYPE_RATIO_RULE_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ONE_RULE_AGREE_REJECT.equals(approveType)) {
                ruleAgreeCount = wflInsTaskService.getRuleAgreeCount(context, insTask.getInsTaskId());
            }

            // 一定比例的规则同意、任一规则同意拒绝，需要计算已拒绝的规则数
            if (WflUserTask.APPROVE_TYPE_RATIO_RULE_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ONE_RULE_AGREE_REJECT.equals(approveType)) {
                ruleRejectCount = wflInsTaskService.getRuleRejectCount(context, insTask.getInsTaskId());
            }

            // 任一条线同意拒绝，需要计算已同意的条线数
            if (WflUserTask.APPROVE_TYPE_ONE_LINE_AGREE_REJECT.equals(approveType)) {
                lineAgreeCount = wflInsTaskService.getLineAgreeCount(context, insTask.getInsTaskId());
            }

            // 任一条线同意拒绝，需要计算已拒绝的条线数
            if (WflUserTask.APPROVE_TYPE_ONE_LINE_AGREE_REJECT.equals(approveType)) {
                lineRejectCount = wflInsTaskService.getLineRejectCount(context, insTask.getInsTaskId());
            }
            // 任一人审批、全部审批、比例同意、一票通过拒绝，需要计算所有人数量
            if (WflUserTask.APPROVE_TYPE_ANY_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ALL_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_RATIO_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ONE_AGREE_REJECT.equals(approveType)) {
                allPeopleCount = wflInsTaskService.getAllPeopleCount(context, insTask.getInsTaskId());
            }
            // 一定比例的规则同意、任一规则同意拒绝，需要计算所有的规则数
            if (WflUserTask.APPROVE_TYPE_RATIO_RULE_AGREE.equals(approveType)
                            || WflUserTask.APPROVE_TYPE_ONE_RULE_AGREE_REJECT.equals(approveType)) {
                allRuleCount = wflInsTaskService.getAllRuleCount(context, insTask.getInsTaskId());
            }

            // 任一人审批，一个人同意或者全部拒绝
            if (WflUserTask.APPROVE_TYPE_ANY_AGREE.equals(approveType)) {
                if (peopleAgreeCount != null && peopleAgreeCount > 0) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (peopleRejectCount != null && allPeopleCount != null && peopleRejectCount == allPeopleCount) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            } else if (WflUserTask.APPROVE_TYPE_ONE_AGREE_REJECT.equals(approveType)) {
                if (peopleAgreeCount != null && peopleAgreeCount > 0) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (peopleRejectCount != null && peopleRejectCount > 0) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            } else if (WflUserTask.APPROVE_TYPE_ALL_AGREE.equals(approveType)) {
                if (peopleAgreeCount != null && allPeopleCount != null && peopleAgreeCount == allPeopleCount) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (peopleRejectCount != null && peopleRejectCount > 0) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            } else if (WflUserTask.APPROVE_TYPE_RATIO_AGREE.equals(approveType)) {
                // 同意比例算法
                // 同意人数的比例超过设定值
                // 或者拒绝人数的比例超过 1 - 设定值

                String param = userTask.getApproveTypeParam();
                Double ratio = null;

                try {
                    ratio = Double.parseDouble(param);
                } catch (Exception e) {
                    throw new RuntimeException("比例同意值无法转换成数值，请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                                    + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId() + ",Param:" + param);
                }

                if (ratio == null && ratio < 0 && ratio > 1) {
                    throw new RuntimeException("比例同意值不符合要求，值必须在 0~1之间，请联系管理员!InstanceId:" + instance.getInstanceId()
                                    + ",TaskId:" + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId()
                                    + ",ratio:" + ratio);
                }

                if (peopleAgreeCount != null && allPeopleCount != null && peopleAgreeCount / allPeopleCount >= ratio) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (peopleAgreeCount != null && allPeopleCount != null
                                && peopleRejectCount / allPeopleCount > 1 - ratio) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            } else if (WflUserTask.APPROVE_TYPE_RATIO_RULE_AGREE.equals(approveType)) {
                // 同意比 例算法
                // 同意规则的比例超过设定值
                // 或者拒绝规则的比例超过 1 - 设定值

                String param = userTask.getApproveTypeParam();
                Double ratio = null;

                try {
                    ratio = Double.parseDouble(param);
                } catch (Exception e) {
                    throw new RuntimeException("比例同意值无法转换成数值，请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                                    + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId() + ",Param:" + param);
                }

                if (ratio == null && ratio < 0 && ratio > 1) {
                    throw new RuntimeException("比例同意值不符合要求，值必须在 0~1之间，请联系管理员!InstanceId:" + instance.getInstanceId()
                                    + ",TaskId:" + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId()
                                    + ",ratio:" + ratio);
                }

                if (ruleAgreeCount != null && allRuleCount != null && ruleAgreeCount / allRuleCount >= ratio) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (ruleAgreeCount != null && allRuleCount != null
                                && ruleAgreeCount / allRuleCount > 1 - ratio) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            } else if (WflUserTask.APPROVE_TYPE_ONE_RULE_AGREE_REJECT.equals(approveType)) {
                if (ruleAgreeCount != null && ruleAgreeCount > 0) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (ruleRejectCount != null && ruleRejectCount > 0) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            } else if (WflUserTask.APPROVE_TYPE_ONE_LINE_AGREE_REJECT.equals(approveType)) {
                if (lineAgreeCount != null && lineAgreeCount > 0) {
                    return WflInsTask.TASK_RESULT_AGREE;
                } else if (lineRejectCount != null && lineRejectCount > 0) {
                    return WflInsTask.TASK_RESULT_REJECT;
                }

                return null;
            }
        }
        return null;
    }

    @Override
    public void approve(IRequest context, WflInstance instance, WflInsTaskRecipient recipient, WflTaskAction action,
                    String note) {
        WflVersionProcess process = utilService.getVersionProcess(instance);

        WflProcessAction processAction = new WflProcessAction();
        processAction.setActionId(action.getActionId());
        processAction = wflProcessActionService.selectByPrimaryKey(context, processAction);

        WflApproveRecord record = new WflApproveRecord();
        record.setActionId(action.getActionId());
        record.setActionType(processAction.getActionType());
        record.setApproveDate(new Date());
        record.setCommentText(note);
        record.setInsHierarchyId(recipient.getInsHierarchyId());
        record.setInsRecipientId(recipient.getInsRecipientId());
        record.setInstanceId(instance.getInstanceId());
        record.setApproverId(recipient.getApproverId());
        record.setRecordId(null);
        record.setInsTaskId(recipient.getInsTaskId());
        wflApproveRecordService.insertSelective(context, record);

        // 备份接受者
        wflInsTaskRecipientService.backupRecipient(context, recipient);

        WflInsTask insTask = new WflInsTask();
        insTask.setInsTaskId(recipient.getInsTaskId());
        insTask = wflInsTaskService.selectByPrimaryKey(context, insTask);

        // 如果动作类型是SEND_TO\SEND_BACK，新增对应的ELEMENT并结束当前TASK的审批信息，跳转到下个节点
        // 如果动作类型是AGREE\REJECT，重新生成接受者，并执行节点的NextProcess
        if (WflProcessAction.ACTION_TYPE_SEND_TO.equals(processAction.getActionType())) {
            WflVersionTask task = process.getTask(
                            WflVersionUtil.getVersion(processAction.getSendTargetTaskId(), instance.getVersion()));

            self().sendTo(context, instance, insTask, task, process);
        } else if (WflProcessAction.ACTION_TYPE_SEND_BACK.equals(processAction.getActionType())) {
            self().sendBack(context, instance, insTask, process);
        } else if (WflProcessAction.ACTION_TYPE_ADD_TASK.equals(processAction.getActionType())) {
            WflVersionTask task = process.getTask(
                            WflVersionUtil.getVersion(processAction.getSendTargetTaskId(), instance.getVersion()));

            self().addTask(context, instance, insTask, task, process);
        } else {
            self().generateRecipient(context, instance, insTask, process);
            self().nextProcess(context, instance, insTask, process);
        }
    }

    @Override
    public void deliver(IRequest context, WflInstance instance, WflInsTaskRecipient recipient, Long targetUserId,
                    String note) {

        // 转交：
        // 1、备份当前recipient数据
        // 2、设置原hierarchy的disabled_flag为Y
        // 3、新建转交的hierarchy
        // 4、重算recipient
        // 5、调用当前任务的nextProcess

        WflVersionProcess process = utilService.getVersionProcess(instance);

        wflInsTaskRecipientService.backupRecipient(context, recipient);

        WflInsTaskHierarchy originInsTaskHierarchy = new WflInsTaskHierarchy();
        originInsTaskHierarchy.setInsHierarchyId(recipient.getInsHierarchyId());
        originInsTaskHierarchy = wflInsTaskHierarchyService.selectByPrimaryKey(context, originInsTaskHierarchy);
        originInsTaskHierarchy.setDisabledFlag("Y");
        wflInsTaskHierarchyService.updateByPrimaryKey(context, originInsTaskHierarchy);

        WflInsTaskHierarchy insTaskHierarchy = new WflInsTaskHierarchy();
        insTaskHierarchy.setInsHierarchyId(null);
        insTaskHierarchy.setNote(note);
        insTaskHierarchy.setPostedFlag("N");
        insTaskHierarchy.setDisabledFlag("N");
        insTaskHierarchy.setSequence(originInsTaskHierarchy.getSequence());
        insTaskHierarchy.setApproverId(targetUserId);
        insTaskHierarchy.setInsReceiverId(originInsTaskHierarchy.getInsReceiverId());
        insTaskHierarchy.setInstanceId(originInsTaskHierarchy.getInstanceId());
        insTaskHierarchy.setParentInsHierarchyId(null);
        insTaskHierarchy.setInsTaskId(recipient.getInsTaskId());
        insTaskHierarchy = wflInsTaskHierarchyService.insertSelective(context, insTaskHierarchy);

        WflInsTask insTask = new WflInsTask();
        insTask.setInsTaskId(recipient.getInsTaskId());
        insTask = wflInsTaskService.selectByPrimaryKey(context, insTask);

        self().generateRecipient(context, instance, insTask, process);
        self().nextProcess(context, instance, insTask, process);
    }

    @Override
    public void addApprover(IRequest context, WflInstance instance, WflInsTaskRecipient recipient,
                    List<WflAddApprover> approverList, String note) {

        // 添加审批人：
        // 1、判断当前添加审批人列表里是否有顺序为:之前,如果存在之前，备份当前接受者，并重置hierarchy的POSTED_FLAG为N，备份接受者信息
        // 2、如果当前hierarchy 的的parent_ins_hierarchy_id为空，设置当前hierarchy 的parent_ins_hierarchy_id =
        // ins_hierarchy_id
        // 3、循环添加审批人信息，按照以下处理
        // 4、 之前：parent_ins_hierarchy_id:当前hierarchy.parent_ins_hierarchy_id，sequence：当前hierarchy的sequence -
        // 10
        // 5、 平行：parent_ins_hierarchy_id:空，sequence：当前hierarchy的sequence
        // 6、 之后：parent_ins_hierarchy_id:当前hierarchy.parent_ins_hierarchy_id，sequence：当前hierarchy的sequence +
        // 10
        // 7、generateRecipient
        // 8、task的nextProcess

        WflVersionProcess process = utilService.getVersionProcess(instance);

        WflInsTask insTask = new WflInsTask();
        insTask.setInsTaskId(recipient.getInsTaskId());
        insTask = wflInsTaskService.selectByPrimaryKey(context, insTask);

        WflInsTaskHierarchy insTaskHierarchy = new WflInsTaskHierarchy();
        insTaskHierarchy.setInsHierarchyId(recipient.getInsHierarchyId());
        insTaskHierarchy = wflInsTaskHierarchyService.selectByPrimaryKey(context, insTaskHierarchy);
        insTaskHierarchy.setParentInsHierarchyId(
                        insTaskHierarchy.getParentInsHierarchyId() == null ? insTaskHierarchy.getInsHierarchyId()
                                        : insTaskHierarchy.getParentInsHierarchyId());
        insTaskHierarchy.setSequence(insTaskHierarchy.getSequence() == null ? 10 : insTaskHierarchy.getSequence());
        insTaskHierarchy = wflInsTaskHierarchyService.updateByPrimaryKey(context, insTaskHierarchy);

        for (WflAddApprover addApprover : approverList) {
            if (WflAddApprover.ADD_ORDER_BEFORE.equals(addApprover.getOrder())) {
                insTaskHierarchy.setPostedFlag("N");
                insTaskHierarchy = wflInsTaskHierarchyService.updateByPrimaryKey(context, insTaskHierarchy);
                wflInsTaskRecipientService.backupRecipient(context, recipient);
                break;
            }
        }

        for (WflAddApprover addApprover : approverList) {
            if (WflAddApprover.ADD_ORDER_BEFORE.equals(addApprover.getOrder())) {
                WflInsTaskHierarchy addInsTaskHierarchy = new WflInsTaskHierarchy();
                addInsTaskHierarchy.setParentInsHierarchyId(insTaskHierarchy.getParentInsHierarchyId());
                addInsTaskHierarchy.setPostedFlag("N");
                addInsTaskHierarchy.setInsHierarchyId(null);
                addInsTaskHierarchy.setDisabledFlag("N");
                addInsTaskHierarchy.setInsTaskId(insTaskHierarchy.getInsTaskId());
                addInsTaskHierarchy.setInstanceId(insTaskHierarchy.getInstanceId());
                addInsTaskHierarchy.setInsReceiverId(insTaskHierarchy.getInsReceiverId());
                addInsTaskHierarchy.setApproverId(addApprover.getUserId());
                addInsTaskHierarchy.setSequence(insTaskHierarchy.getSequence() - 10);
                addInsTaskHierarchy.setNote(note);
                wflInsTaskHierarchyService.insertSelective(context, addInsTaskHierarchy);
            } else if (WflAddApprover.ADD_ORDER_PARALLEL.equals(addApprover.getOrder())) {
                WflInsTaskHierarchy addInsTaskHierarchy = new WflInsTaskHierarchy();
                addInsTaskHierarchy.setParentInsHierarchyId(null);
                addInsTaskHierarchy.setPostedFlag("N");
                addInsTaskHierarchy.setInsHierarchyId(null);
                addInsTaskHierarchy.setDisabledFlag("N");
                addInsTaskHierarchy.setInsTaskId(insTaskHierarchy.getInsTaskId());
                addInsTaskHierarchy.setInstanceId(insTaskHierarchy.getInstanceId());
                addInsTaskHierarchy.setInsReceiverId(insTaskHierarchy.getInsReceiverId());
                addInsTaskHierarchy.setApproverId(addApprover.getUserId());
                addInsTaskHierarchy.setSequence(insTaskHierarchy.getSequence());
                addInsTaskHierarchy.setNote(note);
                wflInsTaskHierarchyService.insertSelective(context, addInsTaskHierarchy);
            } else if (WflAddApprover.ADD_ORDER_AFTER.equals(addApprover.getOrder())) {
                WflInsTaskHierarchy addInsTaskHierarchy = new WflInsTaskHierarchy();
                addInsTaskHierarchy.setParentInsHierarchyId(insTaskHierarchy.getParentInsHierarchyId());
                addInsTaskHierarchy.setPostedFlag("N");
                addInsTaskHierarchy.setInsHierarchyId(null);
                addInsTaskHierarchy.setDisabledFlag("N");
                addInsTaskHierarchy.setInsTaskId(insTaskHierarchy.getInsTaskId());
                addInsTaskHierarchy.setInstanceId(insTaskHierarchy.getInstanceId());
                addInsTaskHierarchy.setInsReceiverId(insTaskHierarchy.getInsReceiverId());
                addInsTaskHierarchy.setApproverId(addApprover.getUserId());
                addInsTaskHierarchy.setSequence(insTaskHierarchy.getSequence() + 10);
                addInsTaskHierarchy.setNote(note);
                wflInsTaskHierarchyService.insertSelective(context, addInsTaskHierarchy);
            }
        }


        // modify@mdw 2018-05-31 需要删除当前待审批者 重新生成审批人
        // wflInsTaskRecipientService.deleteByPrimaryKey(recipient);

        self().generateRecipient(context, instance, insTask, process);
        self().nextProcess(context, instance, insTask, process);
    }

    @Override
    public void autoApprove(IRequest context, WflInstance instance, WflInsTaskRecipient recipient, String note,
                    WflVersionProcess process) {
        WflApproveRecord record = new WflApproveRecord();
        record.setActionId(null);
        record.setActionType(WflProcessAction.ACTION_TYPE_AGREE);
        record.setApproveDate(new Date());
        record.setCommentText("自动审批:" + note);
        record.setInsHierarchyId(recipient.getInsHierarchyId());
        record.setInsRecipientId(null);
        record.setInstanceId(instance.getInstanceId());
        record.setApproverId(recipient.getApproverId());
        record.setRecordId(null);
        record.setInsTaskId(recipient.getInsTaskId());
        wflApproveRecordService.insertSelective(context, record);

        // 备份接受者
        wflInsTaskRecipientService.backupRecipient(context, recipient);
    }

    @Override
    public void sendBack(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionProcess process) {
        // 跳转到上一个节点
        // 找到当前Task，通过Flow回溯上一个Task节点
        // 如果上一个节点不是TASK类型，抛出异常
        WflFlow flow = new WflFlow();
        flow.setToElementType(WflTask.ELEMENT_TASK);
        flow.setToElementId(insTask.getTaskId());
        flow.setProcessId(instance.getProcessId());
        List<WflFlow> flowList = wflFlowService.selectOptions(context, flow, new Criteria(flow));

        if (flowList.size() == 0) {
            throw new RuntimeException("未找到当前任务的前置任务,请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                            + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId());
        } else if (flowList.size() > 1) {
            throw new RuntimeException("当前任务有多个前置流,请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                            + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId());
        } else if (!WflTask.ELEMENT_TASK.equals(flowList.get(0).getFromElementType())) {
            throw new RuntimeException("当前任务前置节点非任务类型,无法退回上一任务节点,请联系管理员!InstanceId:" + instance.getInstanceId()
                            + ",TaskId:" + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId());
        }

        // 生成前置任务
        WflVersionTask preTask = process
                        .getTask(WflVersionUtil.getVersion(flowList.get(0).getFromElementId(), instance.getVersion()));

        WflInsTask preInsTask = new WflInsTask();
        preInsTask.setInsTaskId(null);
        preInsTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        preInsTask.setArrivalDate(null);
        preInsTask.setInstanceId(instance.getInstanceId());
        preInsTask.setTaskId(preTask.getTaskId());
        preInsTask.setDepartureDate(null);
        preInsTask.setResult(null);
        preInsTask = wflInsTaskService.insertSelective(context, preInsTask);

        // 生成当前任务的copy
        WflInsTask curInsTaskCopy = new WflInsTask();
        curInsTaskCopy.setInsTaskId(null);
        curInsTaskCopy.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        curInsTaskCopy.setArrivalDate(null);
        curInsTaskCopy.setInstanceId(instance.getInstanceId());
        curInsTaskCopy.setTaskId(insTask.getTaskId());
        curInsTaskCopy.setDepartureDate(null);
        curInsTaskCopy.setResult(null);
        curInsTaskCopy = wflInsTaskService.insertSelective(context, curInsTaskCopy);

        // 找到原Flow，修改原Flow的TO
        WflInsFlow originInsFlow = new WflInsFlow();
        originInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
        originInsFlow.setFromElementId(insTask.getInsTaskId());
        originInsFlow.setInstanceId(insTask.getInstanceId());
        List<WflInsFlow> originFlowList =
                        wflInsFlowService.selectOptions(context, originInsFlow, new Criteria(originInsFlow));
        if (originFlowList.size() != 1) {
            throw new RuntimeException("未找到当前任务的后续流数量不等于1,请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                            + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId());
        }
        originInsFlow = originFlowList.get(0);
        String originToElementType = originInsFlow.getToElementType();
        Long originToElementId = originInsFlow.getToElementId();

        // 修改原流程的下个节点为新增的insTask
        originInsFlow.setToElementType(WflTask.ELEMENT_TASK);
        originInsFlow.setToElementId(preInsTask.getInsTaskId());
        originInsFlow = wflInsFlowService.updateByPrimaryKey(context, originInsFlow);

        // 设置nextFlow的来源节点为新增task，目标节点为原Task的copy
        WflInsFlow nextInsFlow = new WflInsFlow();
        nextInsFlow.setInstanceId(instance.getInstanceId());
        nextInsFlow.setToElementType(WflTask.ELEMENT_TASK);
        nextInsFlow.setToElementId(curInsTaskCopy.getInsTaskId());
        nextInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
        nextInsFlow.setFromElementId(preInsTask.getInsTaskId());
        nextInsFlow.setSequence(null);
        nextInsFlow.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        nextInsFlow.setArrivalDate(null);
        nextInsFlow.setFlowId(null);
        nextInsFlow.setDepartureDate(null);
        nextInsFlow.setInsFlowId(null);
        nextInsFlow.setResult(null);
        nextInsFlow = wflInsFlowService.insertSelective(context, nextInsFlow);

        // 原Flow的copy的来源节点为原Task的copy，目标为原flow的to
        WflInsFlow curInsFlowCopy = new WflInsFlow();
        curInsFlowCopy.setInstanceId(instance.getInstanceId());
        curInsFlowCopy.setToElementType(originToElementType);
        curInsFlowCopy.setToElementId(originToElementId);
        curInsFlowCopy.setFromElementType(WflTask.ELEMENT_TASK);
        curInsFlowCopy.setFromElementId(curInsTaskCopy.getInsTaskId());
        curInsFlowCopy.setSequence(null);
        curInsFlowCopy.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        curInsFlowCopy.setArrivalDate(null);
        curInsFlowCopy.setFlowId(null);
        curInsFlowCopy.setDepartureDate(null);
        curInsFlowCopy.setInsFlowId(null);
        curInsFlowCopy.setResult(null);
        curInsFlowCopy = wflInsFlowService.insertSelective(context, curInsFlowCopy);

        // 通过当前节点并备份当前节点接受者信息
        self().passTask(context, insTask, process);
        backupTaskReceiver(context, instance, insTask, process);

        // 生成前置任务的接收者和当前任务拷贝的接收者
        wflEngineService.generateInsTaskReceiver(context, instance, process, preInsTask);
        wflEngineService.generateInsTaskReceiver(context, instance, process, curInsTaskCopy);

        // 执行工作流的nextProcess
        wflEngineService.nextProcess(context, instance, originInsFlow, process);
    }

    @Override
    public void sendTo(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionTask targetTask,
                    WflVersionProcess process) {
        // 跳转到指定节点
        // 判断目标任务与当前任务的可达性
        // 1、如果这两个任务之间存在非TASK类型的节点，认为这两个任务之间不可达
        // 2、如果这两个任务之间不可以直达，认为这两个任务之间不可达
        // 3、如果这两个任务之间可达，进行如下处理
        // 3.1 如果目标任务在当前任务之后，跳过当前任务的处理，并设置当前任务
        // 3.2 如果目标任务在当前任务之前，新建之前任务到当前任务之间所有任务的实例，并生成接受者信息
        WflTask task = new WflTask();
        task.setTaskId(insTask.getTaskId());
        WflTask target = new WflTask();
        target.setTaskId(targetTask.getTaskId());
        String taskRelation = wflTaskService.getTaskRelation(context, task, target);
        if (WflTask.TASK_RELATION_UNREACHABLE.equals(taskRelation)) {
            throw new RuntimeException("目标任务不可达，无法进行跳转!ProcessId:" + instance.getProcessId() + ",CurrentTaskId:"
                            + insTask.getTaskId() + ",TargetTaskId:" + targetTask.getTaskId());
        }

        // 找到当前任务的后续流
        WflInsFlow originInsFlow = new WflInsFlow();
        originInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
        originInsFlow.setFromElementId(insTask.getInsTaskId());
        originInsFlow.setInstanceId(insTask.getInstanceId());
        List<WflInsFlow> originFlowList =
                        wflInsFlowService.selectOptions(context, originInsFlow, new Criteria(originInsFlow));
        if (originFlowList.size() != 1) {
            throw new RuntimeException("未找到当前任务的后续流数量不等于1,请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                            + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId());
        }
        originInsFlow = originFlowList.get(0);


        if (WflTask.TASK_RELATION_AFTER.equals(taskRelation)) {
            // 设置当前任务的后续流状态为UNREACHABLE
            wflFlowEngineService.unreachFlow(context, originInsFlow, process);

            // 设置当前任务状态为PASSED，并备份接收者
            self().passTask(context, insTask, process);
            self().backupTaskReceiver(context, instance, insTask, process);

            // 跳转到目标任务，并执行nextProcess
            // 直接跳转后续任务的特殊处理：
            // 跳转到后续任务不是从flow引入，所以需要手动在代码内触发arriveTask，enterTask，nextProcess操作
            WflInsTask queryInsTask = new WflInsTask();
            queryInsTask.setInstanceId(insTask.getInstanceId());
            queryInsTask.setTaskId(targetTask.getTaskId());
            queryInsTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
            List<WflInsTask> targetinsTaskList =
                            wflInsTaskService.selectOptions(context, queryInsTask, new Criteria(queryInsTask));
            if (targetinsTaskList.size() != 1) {
                throw new RuntimeException("跳转目标节点的实例数量不为1，请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                                + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId() + ",TargetTaskId:"
                                + targetTask.getTaskId());
            }
            WflInsTask targetInsTask = targetinsTaskList.get(0);
            self().arriveTask(context, targetInsTask, process);
            self().enterTask(context, instance, targetInsTask, process);
            self().nextProcess(context, instance, targetInsTask, process);
        } else if (WflTask.TASK_RELATION_BEFORE.equals(taskRelation)) {
            List<WflTask> linkedTaskList = wflTaskService.getLinedTask(context, target);
            WflInsTask firstInsTask = null;
            WflInsTask preInsTask = null;
            String originToElementType = originInsFlow.getToElementType();
            Long originToElementId = originInsFlow.getToElementId();
            for (WflTask linkedTask : linkedTaskList) {
                WflInsTask addedInsTask = new WflInsTask();
                addedInsTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
                addedInsTask.setTaskId(linkedTask.getTaskId());
                addedInsTask.setInstanceId(instance.getInstanceId());
                addedInsTask.setDepartureDate(null);
                addedInsTask.setInsTaskId(null);
                addedInsTask.setResult(null);
                addedInsTask.setArrivalDate(null);
                addedInsTask = wflInsTaskService.insertSelective(context, addedInsTask);

                // 如果是第一个节点，设置原Flow的to信息为当前新增任务
                if (firstInsTask == null) {
                    firstInsTask = addedInsTask;
                    originInsFlow.setToElementType(WflTask.ELEMENT_TASK);
                    originInsFlow.setToElementId(firstInsTask.getInsTaskId());
                } else {
                    // 非第一个节点，新增Flow，从上一个任务到当前任务
                    WflInsFlow addedInsFlow = new WflInsFlow();
                    addedInsFlow.setArrivalStatus(WflInsFlow.ARRIVAL_STATUS_NOT_ARRIVED);
                    addedInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
                    addedInsFlow.setFromElementId(preInsTask.getInsTaskId());
                    addedInsFlow.setToElementType(WflTask.ELEMENT_TASK);
                    addedInsFlow.setToElementId(addedInsTask.getInsTaskId());
                    addedInsFlow.setInsFlowId(null);
                    addedInsFlow.setDepartureDate(null);
                    addedInsFlow.setFlowId(null);
                    addedInsFlow.setArrivalDate(null);
                    addedInsFlow.setSequence(null);
                    addedInsFlow.setInstanceId(instance.getInstanceId());
                    addedInsFlow.setResult(null);
                    addedInsFlow = wflInsFlowService.insertSelective(context, addedInsFlow);
                }

                preInsTask = addedInsTask;

                // 生成任务接受者
                wflEngineService.generateInsTaskReceiver(context, instance, process, addedInsTask);

                if (linkedTask.getTaskId().equals(insTask.getTaskId())) {
                    break;
                }
            }

            // 最后一个节点补充一个Flow，从当前Task 到原Flow 的to
            WflInsFlow addedInsFlow = new WflInsFlow();
            addedInsFlow.setArrivalStatus(WflInsFlow.ARRIVAL_STATUS_NOT_ARRIVED);
            addedInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
            addedInsFlow.setFromElementId(preInsTask.getInsTaskId());
            addedInsFlow.setToElementType(originToElementType);
            addedInsFlow.setToElementId(originToElementId);
            addedInsFlow.setInsFlowId(null);
            addedInsFlow.setDepartureDate(null);
            addedInsFlow.setFlowId(null);
            addedInsFlow.setArrivalDate(null);
            addedInsFlow.setSequence(null);
            addedInsFlow.setInstanceId(instance.getInstanceId());
            addedInsFlow.setResult(null);
            addedInsFlow = wflInsFlowService.insertSelective(context, addedInsFlow);

            // 设置当前节点状态为通过，并备份接收者
            self().passTask(context, insTask, process);
            self().backupTaskReceiver(context, instance, insTask, process);
            // 执行WflEngine的nextProcess
            wflEngineService.nextProcess(context, instance, originInsFlow, process);
        } else {
            throw new RuntimeException("跳转节点与当前节点之间不存在关系，无法跳转!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                            + insTask.getTaskId() + ",TargetTaskId:" + targetTask.getTaskId());
        }
    }

    @Override
    public void addTask(IRequest context, WflInstance instance, WflInsTask insTask, WflVersionTask targetTask,
                    WflVersionProcess process) {
        // 加签指定节点

        WflInsTask targetInsTask = new WflInsTask();
        targetInsTask.setInsTaskId(null);
        targetInsTask.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        targetInsTask.setArrivalDate(null);
        targetInsTask.setInstanceId(instance.getInstanceId());
        targetInsTask.setTaskId(targetTask.getTaskId());
        targetInsTask.setDepartureDate(null);
        targetInsTask.setResult(null);
        targetInsTask = wflInsTaskService.insertSelective(context, targetInsTask);

        // 生成当前任务的copy
        WflInsTask curInsTaskCopy = new WflInsTask();
        curInsTaskCopy.setInsTaskId(null);
        curInsTaskCopy.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        curInsTaskCopy.setArrivalDate(null);
        curInsTaskCopy.setInstanceId(instance.getInstanceId());
        curInsTaskCopy.setTaskId(insTask.getTaskId());
        curInsTaskCopy.setDepartureDate(null);
        curInsTaskCopy.setResult(null);
        curInsTaskCopy = wflInsTaskService.insertSelective(context, curInsTaskCopy);

        // 找到原Flow，修改原Flow的TO
        WflInsFlow originInsFlow = new WflInsFlow();
        originInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
        originInsFlow.setFromElementId(insTask.getInsTaskId());
        originInsFlow.setInstanceId(insTask.getInstanceId());
        List<WflInsFlow> originFlowList =
                        wflInsFlowService.selectOptions(context, originInsFlow, new Criteria(originInsFlow));
        if (originFlowList.size() != 1) {
            throw new RuntimeException("未找到当前任务的后续流数量不等于1,请联系管理员!InstanceId:" + instance.getInstanceId() + ",TaskId:"
                            + insTask.getTaskId() + ",InsTaskId:" + insTask.getInsTaskId());
        }
        originInsFlow = originFlowList.get(0);
        String originToElementType = originInsFlow.getToElementType();
        Long originToElementId = originInsFlow.getToElementId();

        // 修改原流程的下个节点为新增的insTask
        originInsFlow.setToElementType(WflTask.ELEMENT_TASK);
        originInsFlow.setToElementId(targetInsTask.getInsTaskId());
        originInsFlow = wflInsFlowService.updateByPrimaryKey(context, originInsFlow);

        // 设置nextFlow的来源节点为新增task，目标节点为原Task的copy
        WflInsFlow nextInsFlow = new WflInsFlow();
        nextInsFlow.setInstanceId(instance.getInstanceId());
        nextInsFlow.setToElementType(WflTask.ELEMENT_TASK);
        nextInsFlow.setToElementId(curInsTaskCopy.getInsTaskId());
        nextInsFlow.setFromElementType(WflTask.ELEMENT_TASK);
        nextInsFlow.setFromElementId(targetInsTask.getInsTaskId());
        nextInsFlow.setSequence(null);
        nextInsFlow.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        nextInsFlow.setArrivalDate(null);
        nextInsFlow.setFlowId(null);
        nextInsFlow.setDepartureDate(null);
        nextInsFlow.setInsFlowId(null);
        nextInsFlow.setResult(null);
        nextInsFlow = wflInsFlowService.insertSelective(context, nextInsFlow);

        // 原Flow的copy的来源节点为原Task的copy，目标为原flow的to
        WflInsFlow curInsFlowCopy = new WflInsFlow();
        curInsFlowCopy.setInstanceId(instance.getInstanceId());
        curInsFlowCopy.setToElementType(originToElementType);
        curInsFlowCopy.setToElementId(originToElementId);
        curInsFlowCopy.setFromElementType(WflTask.ELEMENT_TASK);
        curInsFlowCopy.setFromElementId(curInsTaskCopy.getInsTaskId());
        curInsFlowCopy.setSequence(null);
        curInsFlowCopy.setArrivalStatus(WflInsTask.ARRIVAL_STATUS_NOT_ARRIVED);
        curInsFlowCopy.setArrivalDate(null);
        curInsFlowCopy.setFlowId(null);
        curInsFlowCopy.setDepartureDate(null);
        curInsFlowCopy.setInsFlowId(null);
        curInsFlowCopy.setResult(null);
        curInsFlowCopy = wflInsFlowService.insertSelective(context, curInsFlowCopy);

        // 通过当前节点并备份当前节点接受者信息
        self().passTask(context, insTask, process);
        self().backupTaskReceiver(context, instance, insTask, process);

        // 生成前置任务的接收者和当前任务拷贝的接收者
        wflEngineService.generateInsTaskReceiver(context, instance, process, targetInsTask);
        wflEngineService.generateInsTaskReceiver(context, instance, process, curInsTaskCopy);

        // 执行工作流的nextProcess
        wflEngineService.nextProcess(context, instance, originInsFlow, process);
    }


    @Override
    public void backupTaskReceiver(IRequest context, WflInstance instance, WflInsTask insTask,
                    WflVersionProcess process) {
        // 备份receiver
        WflInsTaskReceiver insTaskReceiver = new WflInsTaskReceiver();
        insTaskReceiver.setInsTaskId(insTask.getInsTaskId());
        List<WflInsTaskReceiver> receiverList = wflInsTaskReceiverService.selectOptions(context, insTaskReceiver,
                        new Criteria(insTaskReceiver));
        for (WflInsTaskReceiver receiver : receiverList) {
            wflInsTaskReceiverService.backupReceiver(context, receiver);
        }

        // 备份hierarchy
        WflInsTaskHierarchy insTaskHierarchy = new WflInsTaskHierarchy();
        insTaskHierarchy.setInsTaskId(insTask.getInsTaskId());
        List<WflInsTaskHierarchy> hierarchyList = wflInsTaskHierarchyService.selectOptions(context, insTaskHierarchy,
                        new Criteria(insTaskHierarchy));
        for (WflInsTaskHierarchy hierarchy : hierarchyList) {
            wflInsTaskHierarchyService.backupHierarchy(context, hierarchy);
        }

        // 备份recipient
        WflInsTaskRecipient insTaskRecipient = new WflInsTaskRecipient();
        insTaskRecipient.setInsTaskId(insTask.getInsTaskId());
        List<WflInsTaskRecipient> recipientList = wflInsTaskRecipientService.selectOptions(context, insTaskRecipient,
                        new Criteria(insTaskRecipient));
        for (WflInsTaskRecipient recipient : recipientList) {
            wflInsTaskRecipientService.backupRecipient(context, recipient);
        }
    }


    @Override
    public void interruptTask(IRequest context, WflInsTask insTask, WflVersionProcess process) {

    }

    private WflVersionProcessCache getCache() {
        return (WflVersionProcessCache) ApplicationContextHelper.getApplicationContext()
                        .getBean("wflVersionProcessCache");
    }
}
