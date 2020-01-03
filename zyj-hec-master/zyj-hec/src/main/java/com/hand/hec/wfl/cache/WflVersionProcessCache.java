package com.hand.hec.wfl.cache;

import com.hand.hap.cache.impl.HashStringRedisCache;
import com.hand.hap.system.dto.Lov;
import com.hand.hap.system.dto.LovItem;
import com.hand.hec.wfl.dto.*;
import com.hand.hec.wfl.mapper.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/02/19 15:26
 */
public class WflVersionProcessCache extends HashStringRedisCache<WflVersionProcess> {

    private Logger logger = LoggerFactory.getLogger(WflVersionProcessCache.class);

    private String versionProcessSql = WflVersionProcessMapper.class.getName() + ".selectVersionProcess";
    private String versionEventSql = WflVersionEventMapper.class.getName() + ".selectVersionEvent";
    private String versionFlowSql = WflVersionFlowMapper.class.getName() + ".selectVersionFlow";
    private String versionTaskSql = WflVersionTaskMapper.class.getName() + ".selectVersionTask";
    private String versionGatewaySql = WflVersionGatewayMapper.class.getName() + ".selectVersionGateway";
    private String versionUserTaskSql = WflVersionUserTaskMapper.class.getName() + ".selectVerUserTask";
    private String versionSubProcessTaskSql = WflVerSubProcessTaskMapper.class.getName() + ".selectVerSubProcessTask";
    private String versionElementSql = WflVersionElementMapper.class.getName() + ".selectVersionElement";
    private String versionTaskReceiverSql = WflVerTaskReceiverMapper.class.getName() + ".selectVerTaskReceiver";
    private String versionTaskRcvBizDetailSql =
                    WflVerTaskRcvBizDetailMapper.class.getName() + ".selectVerTaskRcvBizDetail";
    private String verGtwBizRuleGroupSql = WflVerGtwBizRuleGroupMapper.class.getName() + ".selectVerGtwBizRuleGroup";
    private String verGtwBizRuleDetailSql = WflVerGtwBizRuleDetailMapper.class.getName() + ".selectVerGtwBizRuleDetail";

    {
        setLoadOnStartUp(true);
        setType(WflVersionProcess.class);
    }

    @Override
    public WflVersionProcess getValue(String key) {
        return super.getValue(key);
    }


    @Override
    public void setValue(String key, WflVersionProcess process) {
        super.setValue(key, process);
    }

    public void reload(String key) {

    }

    @Override
    public void initLoad() {
        this.load(new WflVersionProcess());
    }

    /**
     * 加载已发布的工作流数据
     *
     * @param queryProcess
     * @author mouse 2019-02-21 13:46
     * @return void
     */
    public void load(WflVersionProcess queryProcess) {
        Map<String, WflVersionProcess> tempMap = new HashMap<String, WflVersionProcess>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            // 查询所有发布的工作流
            sqlSession.select(versionProcessSql, queryProcess, (resultContext) -> {
                WflVersionProcess process = (WflVersionProcess) resultContext.getResultObject();
                tempMap.put(WflVersionUtil.getVersion(process.getProcessId(), process.getVersion()), process);
            });

            // 查询所有发布的事件
            sqlSession.select(versionEventSql, (resultContext) -> {
                WflVersionEvent event = (WflVersionEvent) resultContext.getResultObject();
                WflVersionProcess process =
                                tempMap.get(WflVersionUtil.getVersion(event.getProcessId(), event.getVersion()));
                if (process != null) {
                    process.addEvent(WflVersionUtil.getVersion(event.getEventId(), event.getVersion()), event);
                }
            });

            // 查询所有发布的任务
            sqlSession.select(versionTaskSql, (resultContext) -> {
                WflVersionTask task = (WflVersionTask) resultContext.getResultObject();
                WflVersionProcess process =
                                tempMap.get(WflVersionUtil.getVersion(task.getProcessId(), task.getVersion()));
                if (process != null) {
                    process.addTask(WflVersionUtil.getVersion(task.getTaskId(), task.getVersion()), task);

                    try (SqlSession taskSqlSession = getSqlSessionFactory().openSession()) {
                        // 查询当前版本任务的用户任务和子流程任务
                        WflVersionUserTask queryUserTask = new WflVersionUserTask();
                        queryUserTask.setTaskId(task.getTaskId());
                        queryUserTask.setVersion(task.getVersion());
                        taskSqlSession.select(versionUserTaskSql, queryUserTask, (userTaskContext) -> {
                            WflVersionUserTask userTask = (WflVersionUserTask) userTaskContext.getResultObject();
                            task.setUserTask(userTask);
                            process.addUserTask(WflVersionUtil.getVersion(userTask.getTaskId(), userTask.getVersion()),
                                            userTask);
                        });

                        WflVerSubProcessTask querySubProcessTask = new WflVerSubProcessTask();
                        querySubProcessTask.setTaskId(task.getTaskId());
                        querySubProcessTask.setVersion(task.getVersion());
                        taskSqlSession.select(versionSubProcessTaskSql, querySubProcessTask, (subTaskContext) -> {
                            WflVerSubProcessTask subProcessTask =
                                            (WflVerSubProcessTask) subTaskContext.getResultObject();
                            task.setSubProcessTask(subProcessTask);
                            process.addSubProcessTask(WflVersionUtil.getVersion(subProcessTask.getTaskId(),
                                            subProcessTask.getVersion()), subProcessTask);
                        });

                        // 查询当前版本任务的接收者
                        WflVerTaskReceiver queryReceiver = new WflVerTaskReceiver();
                        queryReceiver.setTaskId(task.getTaskId());
                        queryReceiver.setVersion(task.getVersion());
                        taskSqlSession.select(versionTaskReceiverSql, queryReceiver, (receiverContext) -> {
                            WflVerTaskReceiver receiver = (WflVerTaskReceiver) receiverContext.getResultObject();
                            task.addReceiver(WflVersionUtil.getVersion(receiver.getReceiverId(), receiver.getVersion()),
                                            receiver);
                            process.addTaskReceiver(
                                            WflVersionUtil.getVersion(receiver.getReceiverId(), receiver.getVersion()),
                                            receiver);


                            try (SqlSession receiverSqlSession = getSqlSessionFactory().openSession()) {
                                // 查询当前接受者下的所有权限规则明细
                                receiverSqlSession.select(versionTaskRcvBizDetailSql, (rcvBizDetailContext) -> {
                                    WflVerTaskRcvBizDetail detail =
                                                    (WflVerTaskRcvBizDetail) rcvBizDetailContext.getResultObject();
                                    receiver.addTaskRcvBizDetail(WflVersionUtil.getVersion(detail.getDetailId(),
                                                    detail.getVersion()), detail);
                                    process.addTaskRcvBizDetail(WflVersionUtil.getVersion(detail.getDetailId(),
                                                    detail.getVersion()), detail);
                                });
                            }
                        });
                    }
                }
            });

            // 查询所有发布的网关
            sqlSession.select(versionGatewaySql, (resultContext) -> {
                WflVersionGateway gateway = (WflVersionGateway) resultContext.getResultObject();
                WflVersionProcess process =
                                tempMap.get(WflVersionUtil.getVersion(gateway.getProcessId(), gateway.getVersion()));
                if (process != null) {
                    process.addGateway(WflVersionUtil.getVersion(gateway.getGatewayId(), gateway.getVersion()),
                                    gateway);

                    // 查询当前网关下的网关权限组
                    WflVerGtwBizRuleGroup queryGroup = new WflVerGtwBizRuleGroup();
                    queryGroup.setGatewayId(gateway.getGatewayId());
                    queryGroup.setVersion(gateway.getVersion());

                    try (SqlSession gatewaySqlSession = getSqlSessionFactory().openSession()) {
                        gatewaySqlSession.select(verGtwBizRuleGroupSql, queryGroup, groupContext -> {
                            WflVerGtwBizRuleGroup group = (WflVerGtwBizRuleGroup) groupContext.getResultObject();
                            gateway.addGtwBizRuleGroup(
                                            WflVersionUtil.getVersion(group.getGroupId(), group.getVersion()), group);
                            process.addGtwBizRuleGroup(
                                            WflVersionUtil.getVersion(group.getGroupId(), group.getVersion()), group);

                            try (SqlSession groupSession = getSqlSessionFactory().openSession()) {
                                // 查询当前网关权限组下的权限明细
                                WflVerGtwBizRuleDetail queryDetail = new WflVerGtwBizRuleDetail();
                                queryDetail.setGroupId(group.getGroupId());
                                queryDetail.setVersion(group.getVersion());

                                groupSession.select(verGtwBizRuleDetailSql, queryDetail, (detailContext) -> {
                                    WflVerGtwBizRuleDetail detail =
                                                    (WflVerGtwBizRuleDetail) detailContext.getResultObject();
                                    group.addGtwBizRuleDetail(WflVersionUtil.getVersion(detail.getDetailId(),
                                                    detail.getVersion()), detail);
                                    process.addGtwBizRuleDetail(WflVersionUtil.getVersion(detail.getDetailId(),
                                                    detail.getVersion()), detail);
                                });
                            }
                        });
                    }
                }
            });

            // 查询所有发布的流向
            sqlSession.select(versionFlowSql, (resultContext) -> {
                WflVersionFlow flow = (WflVersionFlow) resultContext.getResultObject();
                WflVersionProcess process =
                                tempMap.get(WflVersionUtil.getVersion(flow.getProcessId(), flow.getVersion()));
                if (process != null) {
                    process.addFlow(WflVersionUtil.getVersion(flow.getFlowId(), flow.getVersion()), flow);
                }
            });

            // 查询所有发布的元素
            sqlSession.select(versionElementSql, (resultContext) -> {
                WflVersionElement element = (WflVersionElement) resultContext.getResultObject();
                WflVersionProcess process =
                                tempMap.get(WflVersionUtil.getVersion(element.getProcessId(), element.getVersion()));
                if (process != null) {
                    process.addElement(WflVersionUtil.getVersion(element.getElementType(), element.getElementId(),
                                    element.getVersion()), element);
                }
            });
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("init lov cache error:", e);
            }
        }

        for(WflVersionProcess process : tempMap.values()){
            // 设置当前的工作流进入缓存
            setValue(WflVersionUtil.getVersion(process.getProcessId(), process.getVersion()), process);
        }
    }
}
