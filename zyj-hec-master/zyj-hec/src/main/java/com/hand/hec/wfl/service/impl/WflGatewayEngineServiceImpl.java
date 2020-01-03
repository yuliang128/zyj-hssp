package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.service.IFndBusinessRuleEngineService;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import com.hand.hec.wfl.cache.WflVersionUtil;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/4/19 \* Time: 11:21 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflGatewayEngineServiceImpl implements IWflGatewayEngineService {

    @Autowired
    IWflInsGatewayService wflInsGatewayService;

    @Autowired
    IWflInsFlowService wflInsFlowService;

    @Autowired
    IWflEngineService wflEngineService;

    @Autowired
    IWflGatewayBizRuleGroupService wflGatewayBizRuleGroupService;

    @Autowired
    IWflGatewayBizRuleDetailService wflGatewayBizRuleDetailService;

    @Autowired
    IFndBusinessRuleEngineService fndBusinessRuleEngineService;

    @Autowired
    IFndBusinessRuleService fndBusinessRuleService;

    @Autowired
    IWflFlowEngineService wflFlowEngineService;

    @Autowired
    IWflInstanceService wflInstanceService;

    @Autowired
    IWflGatewayService wflGatewayService;

    private Logger logger = LoggerFactory.getLogger(WflGatewayEngineServiceImpl.class);

    private Map<String, Map> insElementMap;
    private Set<String> insElementKeySet;
    private Stack<WflInsGateway> insGatewayStack;

    @Override
    public WflInsGateway arriveGateway(IRequest context, WflInsGateway insGateway, WflVersionProcess process) {
        insGateway.setArrivalStatus(WflInsGateway.ARRIVAL_STATUS_ARRIVED);
        insGateway.setArrivalDate(new Date());
        insGateway = wflInsGatewayService.updateByPrimaryKey(context, insGateway);
        return insGateway;
    }

    @Override
    public WflInsGateway passGateway(IRequest context, WflInsGateway insGateway, WflVersionProcess process) {
        insGateway.setArrivalStatus(WflInsGateway.ARRIVAL_STATUS_PASSED);
        insGateway = wflInsGatewayService.updateByPrimaryKey(context, insGateway);
        return insGateway;
    }

    /**
     * 检查当前收敛网关是否可以通过，如果当前收敛网关上的Flow都处于PASSED或者路径上存在UNREACHABLE的Flow状态说明当前网关已经收敛完成
     * 迭代计算当前所有flow的状态，如果当前为NOT_ARRIVED和ARRIVED 则在头部加上 false && ，如果为PASSED则在头部加上 true &&
     * ，如果为UNREACHABLE则在尾部加上 ||true
     *
     * @param sourceInsGateway 来源发散网关
     * @param flowElement 当前流
     * @return 检查结果
     */
    private boolean canGatewayPass(WflInsGateway sourceInsGateway, Map flowElement, String scriptStr,
                    WflVersionProcess process) {
        boolean validateResult = false;

        // 如果当前流的来源元素不是来源发散网关，则拼接scriptStr，并迭代
        if (WflInsFlow.ARRIVAL_STATUS_ARRIVED.equals(flowElement.get("arrival_status"))
                        || WflInsFlow.ARRIVAL_STATUS_NOT_ARRIVED.equals(flowElement.get("arrival_status"))) {
            // 如果当前存在已抵达或者未抵达状态，script前序拼接false
            scriptStr = "false && " + scriptStr;
        } else if (WflInsFlow.ARRIVAL_STATUS_PASSED.equals(flowElement.get("arrival_status"))) {
            // 如果当前存在已通过，script前序拼接true
            scriptStr = "true && " + scriptStr;
        } else if (WflInsFlow.ARRIVAL_STATUS_UNREACHABLE.equals(flowElement.get("arrival_status"))) {
            // 如果当前存在不可达状态，script后续||拼接true
            scriptStr = scriptStr + " || true";
        }

        // 如果当前流的来源元素是来源发散网关，进行scriptStr的检查
        if (WflGateway.ELEMENT_GATEWAY.equals(flowElement.get("from_element_type")) && sourceInsGateway
                        .getInsGatewayId().equals(Long.parseLong(flowElement.get("from_element_id").toString()))) {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            try {
                logger.debug(new StringBuffer("ScriptStr:").append(scriptStr).toString());
                Object resultObj = engine.eval(scriptStr.toString());
                if (resultObj != null) {
                    validateResult = ((Boolean) resultObj).booleanValue();
                }
            } catch (ScriptException se) {
                throw new RuntimeException(se);
            }
        } else {
            // 循环Flow，找到前置Flow进行迭代
            for (String key : insElementKeySet) {
                if (key.contains("TO[" + flowElement.get("from_element_type").toString() + ":"
                                + flowElement.get("from_element_id").toString() + "]")) {
                    validateResult = canGatewayPass(sourceInsGateway, insElementMap.get(key), scriptStr, process);
                    // 如果验证结果为false，则直接返回，终止本次网关的收敛完成
                    if (!validateResult) {
                        return validateResult;
                    }
                }
            }
        }
        return validateResult;
    }

    /**
     * 获取当前路径上的发散网关
     *
     * @return 检查结果
     */
    private WflInsGateway getSourceInsGatewayInPath(WflInsGateway sourceInsGateway, Map element,
                    WflVersionProcess process) {
        // 获取当前元素的全部来源Flow
        // 如果来源Flow的为sourceInsGateway，返回sourceInsGateway
        // 如果当前Flow追踪到最初节点仍未找到对应的sourceInsGateway，认为当前路径上不存在sourceInsGateway，返回null
        WflInsGateway resultInsGateway = null;
        for (String key : insElementKeySet) {
            if (key.contains("TO[" + element.get("element_type") + ":" + element.get("ins_element_id") + "]")) {
                Map flowMap = insElementMap.get(key);
                String fromElementKey = flowMap.get("from_element_type") + ":" + flowMap.get("from_element_id");
                Map fromElementMap = insElementMap.get(fromElementKey);
                // 如果当前element是Gateway，进行判断是否为sourceInsGateway
                // 如果是，则返回sourceInsGateway
                // 如果不是，则继续迭代执行getSourceInsGatewayInPath
                if (WflGateway.ELEMENT_GATEWAY.equals(fromElementMap.get("element_type"))
                                && sourceInsGateway.getInsGatewayId().equals(
                                                Long.parseLong(fromElementMap.get("ins_element_id").toString()))) {
                    return sourceInsGateway;
                } else {
                    resultInsGateway = getSourceInsGatewayInPath(sourceInsGateway, fromElementMap, process);
                    if (resultInsGateway == null) {
                        return resultInsGateway;
                    }
                }
            }
        }

        return resultInsGateway;
    }

    /**
     * 检查收敛网关的路径是否都来自于发散网关
     *
     * @return 检查结果
     */
    private boolean checkSourceTargetRelation(WflInsGateway sourceInsGateway, WflInsGateway targetInsGateway,
                    WflVersionProcess process) {
        WflInsGateway resultInsGateway = getSourceInsGatewayInPath(sourceInsGateway,
                        insElementMap.get(WflGateway.ELEMENT_GATEWAY + ":" + targetInsGateway.getInsGatewayId()),
                        process);
        if (resultInsGateway == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取前一个发散网关
     *
     * @param element 当前元素
     * @return 来源发散网关
     */
    private WflInsGateway getPreInsGateway(Map element, WflVersionProcess process) {
        WflInsGateway sourceInsGateway = null;
        for (String key : insElementKeySet) {
            if (key.contains("TO[" + element.get("element_type") + ":" + element.get("ins_element_id") + "]")) {
                // 如果当前key包含TO当前元素，说明是Flow，
                // 找到上一个元素，判断是否Gateway，
                // 如果不是Gateway，以当前节点继续往前找
                // 如果是Gateway，判断是否并行类型
                // 1.1 如果不是，继续往前找
                // 1.2 如果是，判断是否发散网关
                // a. 如果是的话，从stack中pop一个元素，如果pop完之后stack为空，说明这个并行类型的发散网关就是来源发散网关，如果不为空，继续往前找
                // b. 如果不是的话，将该网关push进stack，继续往前找
                Map flowMap = insElementMap.get(key);
                String fromElementKey = flowMap.get("from_element_type") + ":" + flowMap.get("from_element_id");
                Map fromElementMap = insElementMap.get(fromElementKey);
                if (WflGateway.ELEMENT_GATEWAY.equals(flowMap.get("from_element_type"))) {
                    // 根据网关实例主键来实例化
                    WflInsGateway insGateway = wflInsGatewayService
                                    .selectInsGatewayById(Long.parseLong(flowMap.get("from_element_id").toString()));
                    if (WflGateway.GATEWAY_TYPE_PARALLEL.equals(insGateway.getGatewayType())) {
                        if (insGateway.getToFlowCount() > 1) {
                            insGatewayStack.pop();
                            if (insGatewayStack.size() == 0) {
                                return insGateway;
                            } else {
                                sourceInsGateway = getPreInsGateway(fromElementMap, process);
                                if (sourceInsGateway != null) {
                                    return sourceInsGateway;
                                }
                            }
                        } else {
                            // 将收敛网关push进栈
                            insGatewayStack.push(insGateway);
                            sourceInsGateway = getPreInsGateway(fromElementMap, process);
                            if (sourceInsGateway != null) {
                                return sourceInsGateway;
                            }
                        }
                    } else {
                        sourceInsGateway = getPreInsGateway(fromElementMap, process);
                        if (sourceInsGateway != null) {
                            return sourceInsGateway;
                        }
                    }
                } else {
                    sourceInsGateway = getPreInsGateway(fromElementMap, process);
                    if (sourceInsGateway != null) {
                        return sourceInsGateway;
                    }
                }
            }
        }
        return sourceInsGateway;
    }

    @Override
    public void initElementMap(IRequest context, WflInstance instance, WflVersionProcess process) {
        // 找到当前instance的所有元素
        // 构造一个Map存放所有元素
        // 获取Map的keySet
        insElementMap = new HashMap<String, Map>();
        insGatewayStack = new Stack<WflInsGateway>();
        List<Map> allElementList = wflInstanceService.getAllElement(instance.getInstanceId());
        // Key的规则
        // Gateway,Task,Event：TASK:insElementId
        // Flow:FLOW:insElementId_FROM[TASK:insElementId]_TO[GATEWAY:insElementId]
        for (Map elementMap : allElementList) {
            String elementType = elementMap.get("element_type").toString();
            Long insElementId = Long.parseLong(elementMap.get("ins_element_id").toString());
            String key = null;
            if (WflEvent.ELEMENT_EVENT.equals(elementType) || WflTask.ELEMENT_TASK.equals(elementType)
                            || WflGateway.ELEMENT_GATEWAY.equals(elementType)) {
                key = new StringBuffer(elementType).append(":").append(insElementId).toString();

            } else if (WflFlow.ELEMENT_FLOW.equals(elementType)) {
                String fromElementType = elementMap.get("from_element_type").toString();
                String fromElementId = elementMap.get("from_element_id").toString();
                String toElementType = elementMap.get("to_element_type").toString();
                String toElementId = elementMap.get("to_element_id").toString();
                key = new StringBuffer(elementType).append(":").append(insElementId).append("_FROM[")
                                .append(fromElementType).append(":").append(fromElementId).append("]_TO[")
                                .append(toElementType).append(":").append(toElementId).append("]").toString();
            }
            insElementMap.put(key, elementMap);
        }

        insElementKeySet = insElementMap.keySet();
    }

    @Override
    public WflInsGateway getParallelGatewayInPath(IRequest context, WflInstance instance, Map element,
                    WflVersionProcess process) {

        // 构造所有元素的map 和 keyset
        self().initElementMap(context, instance, process);

        // 构造一个空的insGateway
        WflInsGateway insGateway = new WflInsGateway();
        // 构造一个stack存储insGateway
        // 存储当前的gateway
        // 调用getPreInsGateway
        insGatewayStack.push(insGateway);

        // 返回getPreInsGateway的结果
        return getPreInsGateway(element, process);
    }

    @Override
    public WflInsGateway getSourceInsGateway(IRequest context, WflInstance instance, WflInsGateway targetInsGateway,
                    WflVersionProcess process) {

        // 构造所有元素的map 和 keyset
        self().initElementMap(context, instance, process);

        // 构造一个stack存储insGateway
        // 存储当前的gateway
        // 调用getPreInsGateway
        insGatewayStack.push(targetInsGateway);

        WflInsGateway sourceInsGateway = getPreInsGateway(
                        insElementMap.get(WflGateway.ELEMENT_GATEWAY + ":" + targetInsGateway.getInsGatewayId()),
                        process);

        if (sourceInsGateway == null) {
            throw new RuntimeException("当前收敛网关未找到对应的发散网关，请联系管理员!InstanceId:" + instance.getInstanceId()
                            + ",insGatewayId:" + targetInsGateway.getInsGatewayId() + ",gatewayId:"
                            + targetInsGateway.getGatewayId());
        }

        // 检查当前收敛网关的所有路径是否可以指向到该发散网关
        if (!checkSourceTargetRelation(sourceInsGateway, targetInsGateway, process)) {
            throw new RuntimeException("当前收敛网关的流来源不能完全指向发散网关，请联系管理员!InstanceId:" + instance.getInstanceId()
                            + ",insGatewayId:" + targetInsGateway.getInsGatewayId() + ",gatewayId:"
                            + targetInsGateway.getGatewayId());
        }

        return sourceInsGateway;
    }


    @Override
    public void nextProcess(IRequest context, WflInstance instance, WflInsGateway insGateway,
                    WflVersionProcess process) {
        if (WflInsGateway.ARRIVAL_STATUS_NOT_ARRIVED.equals(insGateway.getArrivalStatus())) {
            arriveGateway(context, insGateway, process);
        }

        // 网关流程
        // 1、判断当前网关是发散类型网关还是收敛类型网关(看来源流数量，等于一个说明是发散类型，大于一个说明是收敛类型)
        // a、发散类型网关
        // a.1 判断当前网关类型
        // a.2 排他类型网关，找到第一个满足条件的分支，继续nextProcess
        // a.3 并行类型网关，找到所有满足条件的分支，继续nextProcess
        // b、收敛类型网关
        // b.1 找到所有可能经过的来源分支
        // b.2 判断所有来源分支是否都结束
        // b.3 全部结束，当前收敛结束，找到下个flow继续nextProcess
        // b.4 未完全结束，不进行任何操作

        // 寻找前置网关
        List<WflInsFlow> fromInsFlowList =
                        wflInsFlowService.getPreFlow(WflGateway.ELEMENT_GATEWAY, insGateway.getInsGatewayId());
        if (fromInsFlowList.size() > 1) {
            // 网关来源数量大于1，收敛型网关
            // 找到所有来源分支，判断分支是否可以结束
            WflInsGateway sourceInsGateway = getSourceInsGateway(context, instance, insGateway, process);

            // 判断当前收敛网关是否收敛完成
            for (String key : insElementKeySet) {
                if (key.contains("TO[" + WflGateway.ELEMENT_GATEWAY + ":" + insGateway.getInsGatewayId() + "]")) {
                    Map flowMap = insElementMap.get(key);
                    boolean validateResult = canGatewayPass(sourceInsGateway, flowMap, " true", process);
                    // 如果验证为false，return
                    if (!validateResult) {
                        return;
                    }
                }
            }

            // 没有return说明可以收敛，设置当前网关状态为PASSED，找到下一个Flow并执行instance的nextProcess
            self().passGateway(context, insGateway, process);

            List<WflInsFlow> nextFlowList =
                            wflInsFlowService.getNextFlow(WflGateway.ELEMENT_GATEWAY, insGateway.getInsGatewayId());
            if (nextFlowList.size() == 0) {
                throw new RuntimeException("当前收敛网关不存在后续流程，请联系管理员!InstanceId:" + instance.getInstanceId()
                                + ",insGatewayId:" + insGateway.getInsGatewayId() + ",gatewayId:"
                                + insGateway.getGatewayId());
            }
            if (nextFlowList.size() > 1) {
                throw new RuntimeException("当前收敛网关存在多余一个后续流程，请联系管理员!InstanceId:" + instance.getInstanceId()
                                + ",insGatewayId:" + insGateway.getInsGatewayId() + ",gatewayId:"
                                + insGateway.getGatewayId());
            }

            wflEngineService.nextProcess(context, instance, nextFlowList.get(0), process);

        } else {
            // 发散网关，直接通过当前网关
            self().passGateway(context, insGateway, process);
            // 网关来源数量等于1，发散型网关
            // 对流进行权限规则的过滤
            // 找到后续所有的流，并执行nextProcess

            // 找到对应的网关，如果是排他网关，仅走第一条路径，后续路径都设置为UNREACHABLE
            WflVersionGateway gateway = process
                            .getGateway(WflVersionUtil.getVersion(insGateway.getGatewayId(), instance.getVersion()));

            Boolean firstPassFlag = true;

            Collection<WflVerGtwBizRuleGroup> bizRuleGroupList =
                            gateway.getGtwBizRuleGroupMap() == null ? null : gateway.getGtwBizRuleGroupMap().values();

            List<WflInsFlow> toInsFlowList =
                            wflInsFlowService.getNextFlow(WflGateway.ELEMENT_GATEWAY, insGateway.getInsGatewayId());

            for (WflInsFlow toInsFlow : toInsFlowList) {
                int bizRuleGroupCount = 0;
                Boolean bizRuleGroupValidateFlag = null;
                if (bizRuleGroupList != null) {
                    // 循环所有的权限规则组，找到当前流对应的权限规则组
                    for (WflVerGtwBizRuleGroup bizRuleGroup : bizRuleGroupList) {
                        bizRuleGroupCount++;
                        if (toInsFlow.getFlowId().equals(bizRuleGroup.getFlowId())) {
                            int bizRuleDetailCount = 0;
                            // 循环当前权限规则组下的所有的权限规则明细
                            if (bizRuleGroup.getGtwBizRuleDetailMap() != null) {
                                Collection<WflVerGtwBizRuleDetail> bizRuleDetailList =
                                                bizRuleGroup.getGtwBizRuleDetailMap().values();
                                for (WflVerGtwBizRuleDetail bizRuleDetail : bizRuleDetailList) {
                                    bizRuleDetailCount++;
                                    FndBusinessRule rule = new FndBusinessRule();
                                    rule.setBusinessRuleId(bizRuleDetail.getBusinessRuleId());
                                    rule = fndBusinessRuleService.selectByPrimaryKey(context, rule);

                                    boolean validateResult = fndBusinessRuleEngineService.validateBusinessRule(context,
                                                    rule, instance.getDocCategory(),
                                                    String.valueOf(instance.getDocId()));

                                    if (validateResult && WflGatewayBizRuleGroup.ADJUST_TYPE_ONE
                                                    .equals(bizRuleGroup.getAdjustType())) {
                                        // 如果当前权限组的判断类型为：任一条，如果存在验证通过的权限规则则通过当前权限组
                                        bizRuleGroupValidateFlag = true;
                                        break;
                                    } else if (!validateResult && WflGatewayBizRuleGroup.ADJUST_TYPE_ALL
                                                    .equals(bizRuleGroup.getAdjustType())) {
                                        // 如果当前权限组的判断类型为：全部，如果存在验证不通过的权限规则则不通过当前权限组
                                        bizRuleGroupValidateFlag = false;
                                        break;
                                    }
                                }
                            }
                            if (bizRuleDetailCount == 0) {
                                // 如果没有规则明细，则认为通过当前权限组的验证
                                bizRuleGroupValidateFlag = true;
                                break;
                            }

                            // 如果权限组的验证结果为空，则认为没有通过当前权限组的验证
                            if (bizRuleGroupValidateFlag != null) {
                                break;
                            }
                        }
                    }
                }

                // 如果当前没有匹配的权限组或者权限组下的权限规则满足，则进入nextProcess
                // 并且，当前网关是合并网关，或者是排他网关的第一条Flow
                if ((bizRuleGroupList == null || (bizRuleGroupValidateFlag != null && bizRuleGroupValidateFlag)
                                && (WflGateway.GATEWAY_TYPE_PARALLEL.equals(gateway.getGatewayType())
                                                || firstPassFlag))) {
                    wflFlowEngineService.arriveFlow(context, toInsFlow, process);
                    wflEngineService.nextProcess(context, instance, toInsFlow, process);
                    wflFlowEngineService.passFlow(context, toInsFlow, process);
                    firstPassFlag = false;

                } else {
                    wflFlowEngineService.unreachFlow(context, toInsFlow, process);
                }
            }
        }


        if (!WflInstance.STATUS_IN_PROGRESS.equals(instance.getStatus())) {
            // 如果工作流状态不为处理中，直接return不继续执行
            return;
        }

        // 网关执行完成后，查找是否存在处于ARRIVED状态的元素，如果不存在的话说明整个流程无法继续执行，中止流程的运行
        List<Map> arrivedElementList = wflInstanceService.getArrivedElement(instance.getInstanceId());
        if (arrivedElementList.size() == 0) {
            // throw new RuntimeException("当前工作流实例没有正在运行中的节点，流程无法继续流转，请联系管理员!InstanceId:" +
            // instance.getInstanceId() + ",GatewayId:" + insGateway.getGatewayId());
            // 如果当前流程没有运行中的节点，认为工作流被拒绝，进入工作流结束流程
            wflEngineService.interruptInstance(context, instance, process);
        }

        // 每个网关执行完成之后，找到当前处于ARRIVED状态的网关，执行nextProcess，避免由于本次网关未能分派流导致流程无法执行
        for (Map elementMap : arrivedElementList) {
            if (WflGateway.ELEMENT_GATEWAY.equals(elementMap.get("element_type"))) {
                WflInsGateway curInsGateway = new WflInsGateway();
                curInsGateway.setInsGatewayId(Long.parseLong(elementMap.get("ins_element_id").toString()));
                curInsGateway = wflInsGatewayService.selectByPrimaryKey(context, curInsGateway);
                self().nextProcess(context, instance, curInsGateway, process);
            }
        }
    }

    @Override
    public void interruptGateway(IRequest context, WflInsGateway insGateway, WflVersionProcess process) {

    }
}
