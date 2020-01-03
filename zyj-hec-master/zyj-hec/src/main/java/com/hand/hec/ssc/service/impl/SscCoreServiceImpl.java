package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.ssc.dto.*;
import com.hand.hec.ssc.mapper.SscTaskDispatchRecordHisMapper;
import com.hand.hec.ssc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscCoreServiceImpl implements ISscCoreService {
    @Autowired
    private DatabaseLockProvider databaseLockProvider;

    @Autowired
    private ISscTaskDispatchRecordService sscTaskDispatchRecordService;

    @Autowired
    private ISscWorkflowActionService sscWorkflowActionService;

    @Autowired
    private ISscTaskPoolService sscTaskPoolService;

    @Autowired
    private ISscProcessService sscProcessService;

    @Autowired
    private IExpDocumentHistoryService expDocumentHistoryService;

    @Autowired
    private ISscTaskDispatchRecordHisService sscTaskDispatchRecordHisService;

    @Autowired
    private ISscTaskDispatchAdviceService sscTaskDispatchAdviceService;

    @Autowired
    private ISscWorkflowNodeService sscWorkflowNodeService;

    @Autowired
    private ISscTaskPoolHisService sscTaskPoolHisService;

    @Autowired
    private ISscWorkTeamService sscWorkTeamService;

    @Autowired
    private ISscNodeAssignWorkerService sscNodeAssignWorkerService;

    @Autowired
    private ISscNodeAssignRuleService sscNodeAssignRuleService;

    @Autowired
    private ISscWorkerService sscWorkerService;

    @Autowired
    private IExpEmployeeService expEmployeeService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void executeMethod(String beanClass, String methodName, Object[] object) {
        try {
            Class clz = Class.forName(beanClass);
            Method method = clz.getMethod(methodName);
            // 调用方法
            method.invoke(clz.newInstance(), object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入操作记录
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 16:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param sscTaskPool 任务池记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @param processTypeCode 操作类型
     * @return
     * @Version 1.0
     **/
    public void insertSscProcess(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord,SscTaskPool sscTaskPool, Long actionId,
                    String opinion, String processTypeCode) {
        SscProcess sscProcess = new SscProcess();
        sscProcess.setDispatchRecordId(sscTaskDispatchRecord.getDispatchRecordId());
        sscProcess.setActionId(actionId);
        sscProcess.setEmployeeId(sscTaskDispatchRecord.getEmployeeId());
        sscProcess.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscProcess.setProcessTime(new Date());
        sscProcess.setProcessOpinion(opinion);
        sscProcess.setProcessTypeCode(processTypeCode);
        sscProcessService.insertSelective(iRequest,sscProcess);
        String operationCode = null;
        switch (processTypeCode){
            case SscWorkflowAction.PROCESS_TYPE_APPROVE:
                operationCode = ExpDocumentHistory.STATUS_SSC_APPROVE;
                break;
            case SscWorkflowAction.PROCESS_TYPE_REJECT:
                operationCode = ExpDocumentHistory.STATUS_SSC_REJECT;
                break;
            case SscWorkflowAction.PROCESS_TYPE_HOLD:
                operationCode = ExpDocumentHistory.STATUS_SSC_HOLD;
                break;
            case SscWorkflowAction.PROCESS_TYPE_CANCEL_HOLD:
                operationCode = ExpDocumentHistory.STATUS_SSC_CANCEL_HOLD;
                break;
            case SscWorkflowAction.PROCESS_TYPE_RETURN_BACK:
                operationCode = ExpDocumentHistory.STATUS_SSC_RETURN_BACK;
                break;
            case SscWorkflowAction.PROCESS_TYPE_CANCEL_RTN_BACK:
                operationCode = ExpDocumentHistory.STATUS_SSC_CANCEL_RETURN_BACK;
                break;
            case SscWorkflowAction.PROCESS_TYPE_APPLY_RETURN:
                operationCode = ExpDocumentHistory.STATUS_SSC_APPLY_RETURN;
                break;
            case SscWorkflowAction.PROCESS_TYPE_AGREE_RETURN:
                operationCode = ExpDocumentHistory.STATUS_SSC_AGREE_RETURN;
                break;
            case SscWorkflowAction.PROCESS_TYPE_REJECT_RETURN:
                operationCode = ExpDocumentHistory.STATUS_SSC_REJECT_RETURN;
                break;
            case SscWorkflowAction.PROCESS_TYPE_FORCE_RETURN:
                operationCode = ExpDocumentHistory.STATUS_SSC_FORCE_RETURN;
                break;
            default:
                break;
        }
        //插单据历史
        expDocumentHistoryService.insertDocumentHistory(iRequest,sscTaskPool.getDocCategory(),sscTaskPool.getDocId(),operationCode,sscTaskPool.getEmployeeId(),opinion);
    }

    /**
     *执行自定义方法
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 19:01
     * @param dispatchRecordId 任务分派记录Id
     * @param actionId 流程动作Id
     * @param objects 执行方法对应参数
     *@return
     *@Version 1.0
     **/
    public void executeActionProcedure(Long dispatchRecordId,Long actionId,Object[] objects){
        logger.info("execute_action_proc:开始执行动作后过程!");
        //获取定义的需要执行的方法
        List<SscTaskDispatchRecord> sscTaskDispatchRecords = new ArrayList<>();
        sscTaskDispatchRecords = sscTaskDispatchRecordService.getActionProcedure(dispatchRecordId,actionId);
        if(!sscTaskDispatchRecords.isEmpty() && sscTaskDispatchRecords != null){
            for (SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecords){
                logger.info("execute_action_proc:动作后方法名称为：{}!",sscTaskDispatchRecord.getProcName());
                int index = sscTaskDispatchRecord.getProcName().lastIndexOf(".");
                int length = sscTaskDispatchRecord.getProcName().length();
                String className = sscTaskDispatchRecord.getProcName().substring(0,index);
                String methodName = sscTaskDispatchRecord.getProcName().substring(index+1,length);
                executeMethod(className,methodName,objects);
                logger.info("execute_action_proc:结束执行动作后过程!");
            }
        }
    }

    /**
     *执行结束时自定义方法
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 19:01
     * @param taskId 任务Id
     * @param objects 执行方法对应参数
     *@return
     *@Version 1.0
     **/
    public void executeFinishProcedure(Long taskId,Object[] objects){
        logger.info("execute_finished_proc:开始执行结束时过程!");
        //获取定义的需要执行的方法
        List<SscTaskDispatchRecord> sscTaskDispatchRecords = new ArrayList<>();
        sscTaskDispatchRecords = sscTaskDispatchRecordService.getFinishProcedure(taskId);
        if(!sscTaskDispatchRecords.isEmpty() && sscTaskDispatchRecords != null){
            for (SscTaskDispatchRecord sscTaskDispatchRecord:sscTaskDispatchRecords){
                logger.info("execute_finished_proc:动作后方法名称为：{}!",sscTaskDispatchRecord.getProcName());
                //String[] strings = sscTaskDispatchRecord.getProcName().split("(?!.*,.*)");
                int index = sscTaskDispatchRecord.getProcName().lastIndexOf(".");
                int length = sscTaskDispatchRecord.getProcName().length();
                String className = sscTaskDispatchRecord.getProcName().substring(0,index);
                String methodName = sscTaskDispatchRecord.getProcName().substring(index+1,length);
                executeMethod(className,methodName,objects);
                logger.info("execute_finished_proc:结束执行结束时过程!");
            }
        }
    }

    /**
     *删除派工建议
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 19:09
     *@param iRequest 请求上下文
     *@param dispatchRecordId 任务分派记录Id
     *@return
     *@Version 1.0
     **/
     public void cleanAdviceDispatch(IRequest iRequest,Long dispatchRecordId){
         SscTaskDispatchAdvice sscTaskDispatchAdvice = new SscTaskDispatchAdvice();
         sscTaskDispatchAdvice.setDispatchRecordId(dispatchRecordId);
         List<SscTaskDispatchAdvice> sscTaskDispatchAdvices = new ArrayList<>();
         sscTaskDispatchAdvices = sscTaskDispatchAdviceService.select(iRequest,sscTaskDispatchAdvice,0,0);
         if(!sscTaskDispatchAdvices.isEmpty() && sscTaskDispatchAdvice != null){
             sscTaskDispatchAdviceService.batchDelete(sscTaskDispatchAdvices);
         }
     }

     /**
      * 获取下一节点
      *
      * @Author hui.zhao01@hand-china.com
      * @Date 2019/3/25 19:33
      * @param iRequest 请求上下文
      * @param workflowId 工作流Id
      * @param taskId 任务Id
      * @return
      * @Version 1.0
      **/
     public Long getNextNodeId(IRequest iRequest,Long workflowId,SscTaskPool sscTaskPool) {
         Long currentNodeSequence = 0L;
         if(sscTaskPool.getCurrentNodeId() != null){
             //获取当前节点序号
             SscWorkflowNode sscWorkflowNode = new SscWorkflowNode();
             sscWorkflowNode.setNodeId(sscTaskPool.getCurrentNodeId());
             sscWorkflowNode = sscWorkflowNodeService.selectByPrimaryKey(iRequest,sscWorkflowNode);
             currentNodeSequence = sscWorkflowNode.getNodeSequence();
         }
        return sscWorkflowNodeService.getNextNodeId(iRequest,workflowId,sscTaskPool,currentNodeSequence);
     }

    /**
     * 获取上一节点
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 19:33
     * @param iRequest 请求上下文
     * @param workflowId 工作流Id
     * @param taskId 任务Id
     * @return
     * @Version 1.0
     **/
    public Long getPreNodeId(IRequest iRequest, Long workflowId, SscTaskPool sscTaskPool) {
        Long currentNodeSequence = 0L;
        if(sscTaskPool.getCurrentNodeId() != null){
            //获取当前节点序号
            SscWorkflowNode sscWorkflowNode = new SscWorkflowNode();
            sscWorkflowNode.setNodeId(sscTaskPool.getCurrentNodeId());
            sscWorkflowNode = sscWorkflowNodeService.selectByPrimaryKey(iRequest,sscWorkflowNode);
            currentNodeSequence = sscWorkflowNode.getNodeSequence();;
        }
        return sscWorkflowNodeService.getPreNodeId(iRequest,workflowId,sscTaskPool,currentNodeSequence);
    }

     /**
      *任务结束或者中断之后,备份历史数据
      *
      *@Author hui.zhao01@hand-china.com
      *@Date 2019/3/26 16:10
      *@param iRequest 请求上下文
      *@param sscTaskPool 任务数据
      *@return
      *@Version 1.0
      **/
     public void backupTask(IRequest iRequest, SscTaskPool sscTaskPool) {
         logger.info("backup_task:备份共享作业池！");
         SscTaskPoolHis sscTaskPoolHis = new SscTaskPoolHis();
         BeanUtils.copyProperties(sscTaskPoolHis,sscTaskPool);
         sscTaskPoolHisService.insertSelective(iRequest,sscTaskPoolHis);
         sscTaskPoolService.deleteByPrimaryKey(sscTaskPool);
     }

     /**
      *结束任务
      *
      *@Author hui.zhao01@hand-china.com
      *@Date 2019/3/26 16:05
      *@param iRequest 请求上下文
      *@param sscTaskPool 任务数据
      *@param taskStatus 任务状态
      *@return
      *@Version 1.0
      **/
    public void finishTask(IRequest iRequest, SscTaskPool sscTaskPool, String taskStatus) {
        logger.info("finish_task:开始结束任务,task_id:{},task_status:{}", sscTaskPool.getTaskId(), taskStatus);
        //更新任务状态为已结束/已中断
        sscTaskPool.setTaskStatus(taskStatus);
        sscTaskPool.setExitTime(DateUtils.getCurrentDate());
        sscTaskPoolService.updateByPrimaryKeySelective(iRequest,sscTaskPool);
        //执行任务结束过程
        Object[] objects = new Object[]{sscTaskPool.getTaskId(),iRequest.getUserId()};
        executeFinishProcedure(sscTaskPool.getTaskId(),objects);
        logger.info("finish_task:任务结束过程执行成功,task_id:{},task_status:{}", sscTaskPool.getTaskId(), taskStatus);
        //归档任务
        backupTask(iRequest,sscTaskPool);
        logger.info("finish_task:结束任务成功,task_id:{},task_status:{}", sscTaskPool.getTaskId(), taskStatus);
    }

    /**
     *生成建议派工人员
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 16:55
     *@param iRequest
     *@param dispatchRecordId
     *@return
     *@Version 1.0
     **/
    public void adviceDispatch(IRequest iRequest, Long dispatchRecordId,SscTaskPool sscTaskPool) {
        logger.info("advice_dispatch:开始建议任务分派，dispatch_record_id:{}", dispatchRecordId);
        List<SscNodeAssignWorker> sscNodeAssignWorkers = new ArrayList<>();
        sscNodeAssignWorkers = sscNodeAssignWorkerService.querySscNodeAssignWorkerByDispatch(iRequest,dispatchRecordId);
        if(!sscNodeAssignWorkers.isEmpty() && sscNodeAssignWorkers != null){
            for(SscNodeAssignWorker sscNodeAssignWorker : sscNodeAssignWorkers){
                logger.info("advice_dispatch:当前节点派工分配ID为:{}", sscNodeAssignWorker.getWorkerAssignId());
                boolean flag = sscNodeAssignRuleService.validateNodeWorkerRule(iRequest,sscNodeAssignWorker.getWorkerAssignId(),sscTaskPool);
                if(flag){
                    logger.info("advice_dispatch:满足派工规则,work_team_id:{}", sscNodeAssignWorker.getWorkTeamId());
                    if(sscNodeAssignWorker.getEmployeeId() == null){
                        //未分配默认作业人员，则取工作中心下的所有人
                        List<SscWorker> sscWorkers = new ArrayList<>();
                        SscWorker sscWorker = new SscWorker();
                        sscWorker.setWorkTeamId(sscNodeAssignWorker.getWorkTeamId());
                        sscWorkers = sscWorkerService.select(iRequest,sscWorker,0,0);
                        if(!sscWorkers.isEmpty() && sscWorker != null){
                            for(SscWorker sscWorker1:sscWorkers){
                                SscTaskDispatchAdvice sscTaskDispatchAdvice = new SscTaskDispatchAdvice();
                                sscTaskDispatchAdvice.setDispatchRecordId(dispatchRecordId);
                                sscTaskDispatchAdvice.setTaskId(sscTaskPool.getTaskId());
                                sscTaskDispatchAdvice.setWorkTeamId(sscNodeAssignWorker.getWorkTeamId());
                                sscTaskDispatchAdvice.setEmployeeId(sscWorker1.getEmployeeId());
                                sscTaskDispatchAdviceService.insertSelective(iRequest,sscTaskDispatchAdvice);
                            }
                        }
                    } else {
                        SscTaskDispatchAdvice sscTaskDispatchAdvice = new SscTaskDispatchAdvice();
                        sscTaskDispatchAdvice.setDispatchRecordId(dispatchRecordId);
                        sscTaskDispatchAdvice.setTaskId(sscTaskPool.getTaskId());
                        sscTaskDispatchAdvice.setWorkTeamId(sscNodeAssignWorker.getWorkTeamId());
                        sscTaskDispatchAdvice.setEmployeeId(sscNodeAssignWorker.getEmployeeId());
                        sscTaskDispatchAdviceService.insertSelective(iRequest,sscTaskDispatchAdvice);
                    }
                }
            }
        }
    }


    /**
     *自动生成等待派工记录
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 16:46
     *@param iRequest 请求上下文
     *@param sscTaskPool 共享池数据
     *@return Long
     *@Version 1.0
     **/
    public Long generateDispatchRecord(IRequest iRequest, SscTaskPool sscTaskPool) {
        SscTaskDispatchRecord sscTaskDispatchRecord = new SscTaskDispatchRecord();
        sscTaskDispatchRecord.setTaskId(sscTaskPool.getTaskId());
        sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_WAITING);
        sscTaskDispatchRecord.setNodeId(sscTaskPool.getCurrentNodeId());
        sscTaskDispatchRecord = sscTaskDispatchRecordService.insertSelective(iRequest,sscTaskDispatchRecord);
        //生成派工记录后，自动生成派工建议人员
        adviceDispatch(iRequest,sscTaskDispatchRecord.getDispatchRecordId(),sscTaskPool);
        return sscTaskDispatchRecord.getDispatchRecordId();
    }

    /**
     *获取下个节点审批记录
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 18:52
     *@param taskId 任务Id
     *@return
     *@Version 1.0
     **/
    public SscTaskDispatchRecordHis getNodeLastRejectEmployee(Long taskId){
        List<SscTaskDispatchRecordHis> sscTaskDispatchRecordHis = new ArrayList<>();
        sscTaskDispatchRecordHis = sscTaskDispatchRecordHisService.getAllDispatchRecordHis(taskId,SscTaskDispatchRecord.DISPATCH_STATUS_REJECTED, SscWorkflowAction.PROCESS_TYPE_REJECT);
        if(sscTaskDispatchRecordHis.isEmpty() && sscTaskDispatchRecordHis != null) {
            return sscTaskDispatchRecordHis.get(0);
        } else {
            return new SscTaskDispatchRecordHis();
        }
    }

    /**
     *获取上个节点审批记录
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 18:52
     *@param taskId 任务Id
     *@return
     *@Version 1.0
     **/
    public SscTaskDispatchRecordHis getNodeLastApproveEmployee(Long taskId) {
        List<SscTaskDispatchRecordHis> sscTaskDispatchRecordHis = new ArrayList<>();
        sscTaskDispatchRecordHis = sscTaskDispatchRecordHisService.getAllDispatchRecordHis(taskId,SscTaskDispatchRecord.DISPATCH_STATUS_APPROVED, SscWorkflowAction.PROCESS_TYPE_APPROVE);
        if(sscTaskDispatchRecordHis.isEmpty() && sscTaskDispatchRecordHis != null) {
            return sscTaskDispatchRecordHis.get(0);
        } else {
            return new SscTaskDispatchRecordHis();
        }
    }

    @Override
    public void doDispatch(IRequest iRequest, Long dispatchRecordId, Long taskId, Long workTeamId, Long employeeId) {
        SscWorkTeam sscWorkTeam = new SscWorkTeam();
        sscWorkTeam.setWorkTeamId(workTeamId);
        sscWorkTeam = sscWorkTeamService.selectByPrimaryKey(iRequest,sscWorkTeam);
        SscTaskDispatchRecord sscTaskDispatchRecord = new SscTaskDispatchRecord();
        sscTaskDispatchRecord.setDispatchRecordId(dispatchRecordId);
        sscTaskDispatchRecord.setWorkTeamId(workTeamId);
        sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_IN_PROGRESS);
        sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest,sscTaskDispatchRecord);
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(taskId);
        sscTaskPool.setCurrentWorkTeamId(workTeamId);
        sscTaskPool.setCurrentEmployeeId(employeeId);
        sscTaskPool.setWorkCenterId(sscWorkTeam.getWorkCenterId());
        sscTaskPoolService.updateByPrimaryKeySelective(iRequest,sscTaskPool);
        logger.info("do_dispatch:任务分派成功!");
    }

    /**
     * 校验操作人员是否可以进行派工
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/27 16:46
     * @param iRequest
     * @param sscNodeAssignWorker
     * @param employeeId
     * @return
     * @Version 1.0
     * */
    public boolean validateWorkerDispatch(IRequest iRequest, SscNodeAssignWorker sscNodeAssignWorker,Long employeeId){
        SscTaskDispatchRecord sscTaskDispatchRecord = new SscTaskDispatchRecord();
        List<SscTaskDispatchRecord> sscTaskDispatchRecords = new ArrayList<>();
        Long basisValue = sscNodeAssignWorker.getBasisValue() == null ? 30L:sscNodeAssignWorker.getBasisValue();
        int docInHandCount;
        boolean flag = true;
        switch (sscNodeAssignWorker.getDispatchBasisType()){
            case SscNodeAssignWorker.BASIS_DOC_ON_HAND:
                sscTaskDispatchRecord.setEmployeeId(employeeId);
                sscTaskDispatchRecords = sscTaskDispatchRecordService.select(iRequest,sscTaskDispatchRecord,0,0);
                docInHandCount = sscTaskDispatchRecords.size();
                if(basisValue.longValue() > docInHandCount){
                    logger.info("validate_worker_dispatch:单据在手量满足配置，可以取单!");
                    flag = true;
                } else {
                    logger.info("validate_worker_dispatch:单据在手量不满足配置，不可以取单!");
                    flag = false;
                }
                break;
            case SscNodeAssignWorker.BASIS_DOC_ON_HAND_NO_HOLD:
                sscTaskDispatchRecord.setEmployeeId(employeeId);
                sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_HOLDING);
                Criteria criteria = new Criteria(sscTaskDispatchRecord);
                criteria.where(new WhereField(SscTaskDispatchRecord.FIELD_STATUS, Comparison.NOT_EQUAL));
                sscTaskDispatchRecords = sscTaskDispatchRecordService.selectOptions(iRequest,sscTaskDispatchRecord,criteria,0,0);
                docInHandCount = sscTaskDispatchRecords.size();
                if(basisValue.longValue() > docInHandCount){
                    logger.info("validate_worker_dispatch:单据在手量满足配置，可以取单!");
                    flag = true;
                } else {
                    logger.info("validate_worker_dispatch:单据在手量不满足配置，不可以取单!");
                    flag = false;
                }
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     *获取该工作组在手单据量最少的员工
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/27 19:21
     *@param
     *@return
     *@Version 1.0
     **/
     public Long getMostIdleEmpInTeam(Long workTeamId) {
         List<ExpEmployee> expEmployees = new ArrayList<>();
         expEmployees = expEmployeeService.getMostIdleEmpInTeam(workTeamId);
         return expEmployees.get(0).getEmployeeId();
     }

    @Override
    public void autoDispatch(IRequest iRequest) {
        logger.info("auto_dispatch:开始自动派工!");
        /* 自动任务派工逻辑
           从共享任务池中按时间倒序排列
           找到当前节点可以自动派工的单据且状态为等待派工的单据*/
        List<SscTaskDispatchRecord> sscTaskDispatchRecords = new ArrayList<>();
        sscTaskDispatchRecords = sscTaskDispatchRecordService.selectWatingData(iRequest);
        if(!sscTaskDispatchRecords.isEmpty() && sscTaskDispatchRecords != null){
            for(SscTaskDispatchRecord sscTaskDispatchRecord : sscTaskDispatchRecords){
                logger.info("auto_dispatch:当前等待派工记录ID为:{}!",sscTaskDispatchRecord.getDispatchRecordId());
                SscNodeAssignWorker sscNodeAssignWorker = new SscNodeAssignWorker();
                sscNodeAssignWorker.setNodeId(sscTaskDispatchRecord.getNodeId());
                sscNodeAssignWorker.setEnabledFlag("Y");
                List<SscNodeAssignWorker> sscNodeAssignWorkers = new ArrayList<>();
                sscNodeAssignWorkers = sscNodeAssignWorkerService.select(iRequest,sscNodeAssignWorker,0,0);
                if(!sscNodeAssignWorkers.isEmpty() && sscNodeAssignWorkers != null) {
                    for (SscNodeAssignWorker sscNodeAssignWorker1 : sscNodeAssignWorkers) {
                        logger.info("auto_dispatch:当前工作组为:{},员工为:{}!",sscNodeAssignWorker1.getWorkTeamId(),sscNodeAssignWorker1.getEmployeeId());
                        /* *检查当前员工是否对当前任务进行过申请退回操作
                         *  如果申请退回过，那么就不可以进行本次取单
                         * */
                        SscProcess sscProcess = new SscProcess();
                        sscProcess.setEmployeeId(iRequest.getEmployeeId());
                        sscProcess.setTaskId(sscTaskDispatchRecord.getTaskId());
                        sscProcess.setProcessTypeCode(SscWorkflowAction.PROCESS_TYPE_APPLY_RETURN);
                        List<SscProcess> sscProcesses = new ArrayList<>();
                        sscProcesses = sscProcessService.select(iRequest,sscProcess,0,0);
                        if(!sscProcesses.isEmpty() && sscProcesses != null){
                            logger.info("validate_worker_dispatch:当前员工申请退回过这张单据，无法再次取单!");
                            continue;
                        }
                        SscTaskPool sscTaskPool = new SscTaskPool();
                        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
                        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest,sscTaskPool);
                        boolean flag;
                        flag = sscNodeAssignRuleService.validateNodeWorkerRule(iRequest,sscNodeAssignWorker.getWorkerAssignId(),sscTaskPool);
                        if(flag) {
                            Long employeeId = null;
                            int checkFlag = expEmployeeService.checkExpEmployeeValidate(sscNodeAssignWorker1.getEmployeeId());
                            if(checkFlag == 0 && sscNodeAssignWorker1.getEmployeeId() == null){
                                employeeId = getMostIdleEmpInTeam(sscNodeAssignWorker1.getWorkTeamId());
                            }
                            flag = validateWorkerDispatch(iRequest,sscNodeAssignWorker1,employeeId);
                            if(flag){
                                logger.info("auto_dispatch:校验通过,执行派工!");
                                doDispatch(iRequest,sscTaskDispatchRecord.getDispatchRecordId(),sscTaskDispatchRecord.getTaskId(),sscNodeAssignWorker1.getWorkTeamId(),employeeId);
                                logger.info("auto_dispatch:该任务成功分派!");
                            }
                        } else {
                            logger.info("validate_worker_dispatch:手动取单结束!");
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void manualDispatch(IRequest iRequest) {
        logger.info("manual_dispatch:手动取单开始员工ID为:{}!",iRequest.getEmployeeId());
        /*1、循环当前员工所在的工作组
          2、根据工作组和员工找到对应的派工节点
          3、找到当前节点当前处于等待派工状态的单据
          4、判断是否符合权限规则
          5、判断是否符合员工派工依据*/
        List<SscWorker> sscWorkers = new ArrayList<>();
        sscWorkers = sscWorkerService.getAllWorkTeam(iRequest.getEmployeeId());
        if(!sscWorkers.isEmpty() && sscWorkers != null){
            for(SscWorker sscWorker : sscWorkers){
                logger.info("manual_dispatch:当前工作组为:{}!",sscWorker.getWorkTeamId());
                List<SscNodeAssignWorker> sscNodeAssignWorkers = new ArrayList<>();
                sscNodeAssignWorkers = sscNodeAssignWorkerService.getAllNodeAssignWorker(sscWorker.getWorkTeamId(),sscWorker.getEmployeeId());
                if(!sscNodeAssignWorkers.isEmpty() && sscNodeAssignWorkers != null){
                    for (SscNodeAssignWorker sscNodeAssignWorker:sscNodeAssignWorkers){
                        logger.info("manual_dispatch:当前派工节点为:{}!",sscNodeAssignWorker.getNodeId());
                        List<SscTaskDispatchRecord> sscTaskDispatchRecords = new ArrayList<>();
                        SscTaskDispatchRecord sscTaskDispatchRecord = new SscTaskDispatchRecord();
                        sscTaskDispatchRecord.setNodeId(sscNodeAssignWorker.getNodeId());
                        sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_WAITING);
                        sscTaskDispatchRecords = sscTaskDispatchRecordService.select(iRequest,sscTaskDispatchRecord,0,0);
                        if(!sscTaskDispatchRecords.isEmpty() && sscTaskDispatchRecords != null){
                            BigDecimal index = new BigDecimal(1);
                            for(SscTaskDispatchRecord sscTaskDispatchRecord1 : sscTaskDispatchRecords){
                                if(index.compareTo(sscNodeAssignWorker.getMaxDispatchCount()) > 0){
                                    break;
                                } else {
                                    logger.info("manual_dispatch:当前派工记录为:{}!",sscTaskDispatchRecord1.getDispatchRecordId());
                                    index = index.add(new BigDecimal(1));
                                    /* *检查当前员工是否对当前任务进行过申请退回操作
                                     *  如果申请退回过，那么就不可以进行本次取单
                                     * */
                                    SscProcess sscProcess = new SscProcess();
                                    sscProcess.setEmployeeId(iRequest.getEmployeeId());
                                    sscProcess.setTaskId(sscTaskDispatchRecord1.getTaskId());
                                    sscProcess.setProcessTypeCode(SscWorkflowAction.PROCESS_TYPE_APPLY_RETURN);
                                    List<SscProcess> sscProcesses = new ArrayList<>();
                                    sscProcesses = sscProcessService.select(iRequest,sscProcess,0,0);
                                    if(!sscProcesses.isEmpty() && sscProcesses != null){
                                        logger.info("validate_worker_dispatch:当前员工申请退回过这张单据，无法再次取单!");
                                        continue;
                                    }
                                    boolean flag = validateWorkerDispatch(iRequest,sscNodeAssignWorker,iRequest.getEmployeeId());
                                    if(flag){
                                        SscTaskPool sscTaskPool = new SscTaskPool();
                                        sscTaskPool.setTaskId(sscTaskDispatchRecord1.getTaskId());
                                        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest,sscTaskPool);
                                        flag = sscNodeAssignRuleService.validateNodeWorkerRule(iRequest,sscNodeAssignWorker.getWorkerAssignId(),sscTaskPool);
                                        if(flag){
                                            logger.info("manual_dispatch:校验通过,执行派工!");
                                            doDispatch(iRequest,sscTaskDispatchRecord1.getDispatchRecordId(),sscTaskDispatchRecord1.getTaskId(),sscWorker.getWorkTeamId(),sscWorker.getEmployeeId());
                                        }
                                    } else {
                                        logger.info("validate_worker_dispatch:手动取单结束!");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.info("validate_worker_dispatch:手动取单结束!");
    }

    @Override
    public void doApprove(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                    String opinion) {
        logger.info("do_approve:开始同意操作,动作ID：{},意见：{}", actionId, opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*
             * 同意动作操作步骤:
             * 1、插入同意动作
             * 2、更改当前dispatch_record为APPROVED并执行动作后过程
             * 3、归档数据
             * 4、执行节点后校验过程
             * 5、判断当前是否最后一个节点
             *  a、最后一个节点
             *      a1、执行do_finish
             *  b、并非最后一个节点
             *      b1、找到下一个节点,并将任务跳转到上一个节点
             *      b2、判断当前节点上次是否被拒绝,如果被拒绝,直接派单给对应人员
             */
            insertSscProcess(iRequest,sscTaskDispatchRecord,sscTaskPool,actionId,opinion,SscWorkflowAction.PROCESS_TYPE_APPROVE);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_APPROVED);
            sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest,sscTaskDispatchRecord);
            logger.info("do_approve:同意操作执行成功!");
            //执行自定义方法
            Object[] objects = new Object[]{sscTaskDispatchRecord.getDispatchRecordId(),actionId,opinion,iRequest.getUserId()};
            executeActionProcedure(sscTaskDispatchRecord.getDispatchRecordId(),actionId,objects);
            logger.info("do_approve:动作后过程执行成功");
            //备份派工记录
            SscTaskDispatchRecordHis sscTaskDispatchRecordHis = new SscTaskDispatchRecordHis();
            sscTaskDispatchRecord.setExitTime(new Date());
            BeanUtils.copyProperties(sscTaskDispatchRecordHis,sscTaskDispatchRecord);
            sscTaskDispatchRecordHisService.insertSelective(iRequest,sscTaskDispatchRecordHis);
            sscTaskDispatchRecordService.deleteByPrimaryKey(sscTaskDispatchRecord);
            //删除建议派工信息
            cleanAdviceDispatch(iRequest,sscTaskDispatchRecord.getDispatchRecordId());
            logger.info("do_approve:备份派工记录完成");
            //获取下一节点Id
            Long nextNodeId = null;
            nextNodeId = getNextNodeId(iRequest,sscTaskPool.getWorkflowId(),sscTaskPool);
            logger.info("do_approve:获取到下一个节点ID为：{}",nextNodeId);
            if(nextNodeId.longValue() == 0) {
                //没有下一个匹配节点,当前节点为最后的节点
                finishTask(iRequest,sscTaskPool,SscTaskPool.TASK_STATUS_FINISHED);
                logger.info("do_approve:下一个节点不存在,结束任务");
            }else{
                sscTaskPool.setCurrentNodeId(nextNodeId);
                sscTaskPoolService.updateByPrimaryKeySelective(iRequest,sscTaskPool);
                logger.info("do_approve:更新任务到下一个节点");
                Long dispatchRecordId = generateDispatchRecord(iRequest,sscTaskPool);
                logger.info("do_approve:生成新的派工记录,派工记录ID为: {}",sscTaskDispatchRecord.getDispatchRecordId());
                SscTaskDispatchRecordHis sscTaskDispatchRecordHis1 = new SscTaskDispatchRecordHis();
                sscTaskDispatchRecordHis1 = getNodeLastRejectEmployee(sscTaskPool.getTaskId());
                logger.info("do_approve:获取下个节点上次拒绝人员信息为:work_team_id {},employee_id:{}",sscTaskDispatchRecordHis1.getWorkTeamId(),sscTaskDispatchRecordHis1.getEmployeeId());
                if(sscTaskDispatchRecordHis1 != null){
                    doDispatch(iRequest,dispatchRecordId,sscTaskPool.getTaskId(),sscTaskDispatchRecordHis1.getWorkTeamId(),sscTaskDispatchRecordHis1.getEmployeeId());
                    logger.info("do_approve:上次拒绝人存在,成功派工给上次拒绝人!");
                }
            }
        }
        logger.info("do_approve:同意结束");
    }

    @Override
    public void doReject(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                    String opinion) {
        logger.info("do_reject:开始拒绝操作,动作ID:{},意见:{}",actionId,opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*拒绝动作操作步骤:
            1、插入拒绝动作
            2、更改当前dispatch_record为REJECTED并执行动作后过程
            3、归档数据
            4、判断当前是否第一个节点
               a、第一个节点
                  a1、执行do_finish
               b、并非第一个节点
                  b1、找到上一个节点,并将任务跳转到上一个节点
                  b2、判断当前节点上次是否被同意,如果被同意,直接派单给对应人员*/
            insertSscProcess(iRequest,sscTaskDispatchRecord,sscTaskPool,actionId,opinion,SscWorkflowAction.PROCESS_TYPE_REJECT);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_REJECTED);
            sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest,sscTaskDispatchRecord);
            logger.info("do_reject:拒绝操作执行成功!");
            //执行自定义方法
            Object[] objects = new Object[]{sscTaskDispatchRecord.getDispatchRecordId(),actionId,opinion,iRequest.getUserId()};
            executeActionProcedure(sscTaskDispatchRecord.getDispatchRecordId(),actionId,objects);
            logger.info("do_reject:动作后过程执行成功");
            //备份派工记录
            SscTaskDispatchRecordHis sscTaskDispatchRecordHis = new SscTaskDispatchRecordHis();
            sscTaskDispatchRecord.setExitTime(new Date());
            BeanUtils.copyProperties(sscTaskDispatchRecordHis,sscTaskDispatchRecord);
            sscTaskDispatchRecordHisService.insertSelective(iRequest,sscTaskDispatchRecordHis);
            sscTaskDispatchRecordService.deleteByPrimaryKey(sscTaskDispatchRecord);
            //删除建议派工信息
            cleanAdviceDispatch(iRequest,sscTaskDispatchRecord.getDispatchRecordId());
            logger.info("do_reject:备份派工记录完成");
            //获取上一节点Id
            Long preNodeId = null;
            preNodeId = getPreNodeId(iRequest,sscTaskPool.getWorkflowId(),sscTaskPool);
            logger.info("do_reject:获取到上一个节点ID为：{}",preNodeId);
            if(preNodeId.longValue() == 0) {
                //当前节点为第一个节点
                finishTask(iRequest,sscTaskPool,SscTaskPool.TASK_STATUS_FINISHED);
                logger.info("do_reject:上一个节点不存在,结束任务");
            }else{
                sscTaskPool.setCurrentNodeId(preNodeId);
                sscTaskPoolService.updateByPrimaryKeySelective(iRequest,sscTaskPool);
                logger.info("do_reject:更新任务到上一个节点");
                Long dispatchRecordId = generateDispatchRecord(iRequest,sscTaskPool);
                logger.info("do_reject:生成新的派工记录,派工记录ID为: {}",sscTaskDispatchRecord.getDispatchRecordId());
                SscTaskDispatchRecordHis sscTaskDispatchRecordHis1 = new SscTaskDispatchRecordHis();
                sscTaskDispatchRecordHis1 = getNodeLastApproveEmployee(sscTaskPool.getTaskId());
                logger.info("do_reject:获取上个节点上次同意人员信息为:work_team_id {},employee_id:{}",sscTaskDispatchRecordHis1.getWorkTeamId(),sscTaskDispatchRecordHis1.getEmployeeId());
                if(sscTaskDispatchRecordHis1 != null){
                    doDispatch(iRequest,dispatchRecordId,sscTaskPool.getTaskId(),sscTaskDispatchRecordHis1.getWorkTeamId(),sscTaskDispatchRecordHis1.getEmployeeId());
                    logger.info("do_reject:上次同意人存在,成功派工给上次同意人!");
                }
            }
        }
        logger.info("do_reject:拒绝结束");
    }


    @Override
    public void doHold(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId, String opinion) {
        logger.info("do_hold:开始暂挂操作,动作ID:{},意见:{}",actionId,opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            SscWorkflowNode sscWorkflowNode = new SscWorkflowNode();
            sscWorkflowNode.setNodeId(sscTaskDispatchRecord.getNodeId());
            sscWorkflowNode = sscWorkflowNodeService.selectByPrimaryKey(iRequest,sscWorkflowNode);
            if("Y".equals(sscWorkflowNode.getCanHoldFlag())){
                /*暂挂动作操作步骤:
                    1、插入暂挂动作
                    2、更改当前dispatch_record为HOLDING并执行动作后过程*/
                insertSscProcess(iRequest,sscTaskDispatchRecord,sscTaskPool,actionId,opinion,SscWorkflowAction.PROCESS_TYPE_HOLD);
                //更新任务分派记录对应状态
                sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_HOLDING);
                sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest,sscTaskDispatchRecord);
                logger.info("do_hold:暂挂操作执行成功!");
                //执行自定义方法
                Object[] objects = new Object[]{sscTaskDispatchRecord.getDispatchRecordId(),actionId,opinion,iRequest.getUserId()};
                executeActionProcedure(sscTaskDispatchRecord.getDispatchRecordId(),actionId,objects);
                logger.info("do_hold:暂挂动作后过程执行成功");
            } else {
                logger.info("do_hold:当前节点不允许暂挂,节点ID:{}",sscTaskDispatchRecord.getNodeId());
                logger.info("do_hold:当前节点不允许暂挂");
                throw new RuntimeException("SSC_TASK_DIPATCH_RECORD.NODE_CANNOT_HOLD");
            }
        }
    }

    @Override
    public void doCancelHold(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                    String opinion) {
        logger.info("do_cancel_hold:开始暂挂操作,动作ID:{},意见:{}",actionId,opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*取消暂挂动作操作步骤:
              1、插入取消暂挂动作
              2、更改当前dispatch_record为IN_PROGRESS并执行动作后过程*/
            insertSscProcess(iRequest, sscTaskDispatchRecord, sscTaskPool, actionId, opinion, SscWorkflowAction.PROCESS_TYPE_CANCEL_HOLD);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_IN_PROGRESS);
            sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest, sscTaskDispatchRecord);
            logger.info("do_cancel_hold:取消暂挂操作成功!");
        }
    }

    @Override
    public void doReturnBack(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                    String opinion) {
        logger.info("do_return_back:开始退回提单人操作,动作ID:{},意见:{}",actionId,opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*取消暂挂动作操作步骤:
              1、插入取消暂挂动作
              2、更改当前dispatch_record为IN_PROGRESS并执行动作后过程*/
            insertSscProcess(iRequest, sscTaskDispatchRecord, sscTaskPool, actionId, opinion, SscWorkflowAction.PROCESS_TYPE_RETURN_BACK);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_RETURN_BACK);
            sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest, sscTaskDispatchRecord);
            logger.info("do_return_back:退回提单人操作执行成功!");
            //执行自定义方法
            Object[] objects = new Object[]{sscTaskDispatchRecord.getDispatchRecordId(),actionId,opinion,iRequest.getUserId()};
            executeActionProcedure(sscTaskDispatchRecord.getDispatchRecordId(),actionId,objects);
            logger.info("do_return_back:退回提单人动作后过程执行成功!");
        }
    }

    @Override
    public void doCancelReturnBack(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                             String opinion) {
        logger.info("do_cancel_return_back:开始取消退回提单人操作,动作ID:{},意见:{}",actionId,opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*取消退回动作操作步骤:
              1、插入取消退回动作
              2、更改当前dispatch_record为IN_PROGRESS*/
            insertSscProcess(iRequest, sscTaskDispatchRecord, sscTaskPool, actionId, opinion, SscWorkflowAction.PROCESS_TYPE_CANCEL_RTN_BACK);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_IN_PROGRESS);
            sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest, sscTaskDispatchRecord);
            logger.info("do_cancel_return_back:取消退回提单人操作成功!");
        }
    }

    @Override
    public void doApplyReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                    String opinion) {
        logger.info("do_apply_return:开始申请退回操作,动作ID:{},意见:{}",actionId,opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            SscWorkflowNode sscWorkflowNode = new SscWorkflowNode();
            sscWorkflowNode.setNodeId(sscTaskDispatchRecord.getNodeId());
            sscWorkflowNode = sscWorkflowNodeService.selectByPrimaryKey(iRequest,sscWorkflowNode);
            if("Y".equals(sscWorkflowNode.getCanReturnFlag())){
                /*申请退回动作操作步骤:
                    1、插入申请退回动作
                    2、更改当前dispatch_record为APPLYING_RETURN并执行动作后过程*/
                insertSscProcess(iRequest,sscTaskDispatchRecord,sscTaskPool,actionId,opinion,SscWorkflowAction.PROCESS_TYPE_APPLY_RETURN);
                //更新任务分派记录对应状态
                sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_APPLY_RETURN);
                sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest,sscTaskDispatchRecord);
                logger.info("do_apply_return:申请退回操作执行成功!");
                //执行自定义方法
                Object[] objects = new Object[]{sscTaskDispatchRecord.getDispatchRecordId(),actionId,opinion,iRequest.getUserId()};
                executeActionProcedure(sscTaskDispatchRecord.getDispatchRecordId(),actionId,objects);
                logger.info("do_apply_return:申请退回动作后过程执行成功");
            } else {
                logger.info("do_apply_return:当前节点不允许申请退回,节点ID:{}",sscTaskDispatchRecord.getNodeId());
                logger.info("do_apply_return:当前节点不允许申请退回");
                throw new RuntimeException("SSC_TASK_DIPATCH_RECORD.NODE_CANNOT_RETURN");
            }
        }
    }

    @Override
    public void doProcess(IRequest iRequest, Long dispatchRecordId, Long actionId, String opinion) {
        logger.info("do_process:开始执行操作,动作ID: {} ,意见: {}", actionId, opinion);
        try {
            // 锁表
            StringBuilder whereConditionSB = new StringBuilder();
            whereConditionSB.append("dispatch_record_id=").append(dispatchRecordId);
            databaseLockProvider.lock(new SscTaskDispatchRecord(), whereConditionSB.toString(), null);
            SscTaskDispatchRecord sscTaskDispatchRecord = new SscTaskDispatchRecord();
            sscTaskDispatchRecord.setDispatchRecordId(dispatchRecordId);
            sscTaskDispatchRecord = sscTaskDispatchRecordService.selectByPrimaryKey(iRequest, sscTaskDispatchRecord);
            Long taskId = sscTaskDispatchRecord.getTaskId();
            SscWorkflowAction sscWorkflowAction = new SscWorkflowAction();
            sscWorkflowAction.setActionId(actionId);
            sscWorkflowAction.setEnabledFlag("Y");
            List<SscWorkflowAction> sscWorkflowActions = new ArrayList<>();
            sscWorkflowActions = sscWorkflowActionService.select(iRequest, sscWorkflowAction, 0, 0);
            if (!sscWorkflowActions.isEmpty() && sscWorkflowActions != null && sscWorkflowActions.size() == 1) {
                String action = null;
                action = sscWorkflowActions.get(0).getActionType();
                logger.info("do_process:开始执行操作,动作类型为: {},任务ID为：{}", action, taskId);
                switch (action) {
                    case SscWorkflowAction.PROCESS_TYPE_APPROVE:
                        logger.info("do_process:执行同意动作,任务ID为：{}", taskId);
                        doApprove(iRequest, sscTaskDispatchRecord, actionId, opinion);
                        break;
                    case SscWorkflowAction.PROCESS_TYPE_REJECT:
                        logger.info("do_process:执行拒绝动作,任务ID为：{}", taskId);
                        doReject(iRequest, sscTaskDispatchRecord, actionId, opinion);
                        break;
                    case SscWorkflowAction.PROCESS_TYPE_HOLD:
                        logger.info("do_process:执行挂起动作,任务ID为：{}", taskId);
                        doHold(iRequest, sscTaskDispatchRecord, actionId, opinion);
                        break;
                    case SscWorkflowAction.PROCESS_TYPE_CANCEL_HOLD:
                        logger.info("do_process:执行取消暂挂动作,任务ID为：{}", taskId);
                        doCancelHold(iRequest, sscTaskDispatchRecord, actionId, opinion);
                        break;
                    case SscWorkflowAction.PROCESS_TYPE_RETURN_BACK:
                        logger.info("do_process:执行退回提单人动作,任务ID为：{}", taskId);
                        doReturnBack(iRequest, sscTaskDispatchRecord, actionId, opinion);
                        break;
                    case SscWorkflowAction.PROCESS_TYPE_APPLY_RETURN:
                        logger.info("do_process:执行申请退回动作,任务ID为：{}", taskId);
                        doApplyReturn(iRequest, sscTaskDispatchRecord, actionId, opinion);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void doAgreeReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, String opinion) {
        logger.info("do_agree_return:开始同意退回,意见:{}",opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        List<SscTaskPool> sscTaskPools = new ArrayList<>();
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*同意退回动作操作步骤:
              1、插入同意退回动作
              2、更改当前dispatch_record为WAITING,清空当前作业组和作业人员信息*/
            insertSscProcess(iRequest, sscTaskDispatchRecord, sscTaskPool, null, opinion, SscWorkflowAction.PROCESS_TYPE_AGREE_RETURN);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_WAITING);
            sscTaskDispatchRecord.setWorkTeamId(null);
            sscTaskDispatchRecord.setEmployeeId(null);
            sscTaskDispatchRecordService.updateByPrimaryKey(iRequest, sscTaskDispatchRecord);
            sscTaskPool.setCurrentWorkTeamId(null);
            sscTaskPool.setCurrentEmployeeId(null);
            sscTaskPoolService.updateByPrimaryKey(iRequest,sscTaskPool);
            logger.info("do_agree_return:同意退回完成!");
        }
    }

    @Override
    public void doRejectReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, String opinion) {
        logger.info("do_reject_return:开始拒绝退回,意见:{}",opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*拒绝退回动作操作步骤:
              1、插入拒绝退回动作
              2、更改当前dispatch_record为IN_PROGRESS*/
            insertSscProcess(iRequest, sscTaskDispatchRecord, sscTaskPool, null, opinion, SscWorkflowAction.PROCESS_TYPE_REJECT_RETURN);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_IN_PROGRESS);
            sscTaskDispatchRecordService.updateByPrimaryKeySelective(iRequest, sscTaskDispatchRecord);
            logger.info("do_reject_return:拒绝退回完成!");
        }
    }

    @Override
    public void doForceReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, String opinion) {
        logger.info("do_force_return:开始强制退回,意见:{}",opinion);
        // 根据任务ID获取共享池任务数据
        SscTaskPool sscTaskPool = new SscTaskPool();
        sscTaskPool.setTaskId(sscTaskDispatchRecord.getTaskId());
        sscTaskPool = sscTaskPoolService.selectByPrimaryKey(iRequest, sscTaskPool);
        if (sscTaskPool != null) {
            /*强制退回动作操作步骤:
              1、插入强制退回动作
              2、更改当前dispatch_record为WAITING,清空当前工作组和工作人员*/
            insertSscProcess(iRequest, sscTaskDispatchRecord, sscTaskPool, null, opinion, SscWorkflowAction.PROCESS_TYPE_FORCE_RETURN);
            //更新任务分派记录对应状态
            sscTaskDispatchRecord.setStatus(SscTaskDispatchRecord.DISPATCH_STATUS_WAITING);
            sscTaskDispatchRecord.setWorkTeamId(null);
            sscTaskDispatchRecord.setEmployeeId(null);
            sscTaskDispatchRecordService.updateByPrimaryKey(iRequest, sscTaskDispatchRecord);
            sscTaskPool.setCurrentWorkTeamId(null);
            sscTaskPool.setCurrentEmployeeId(null);
            sscTaskPoolService.updateByPrimaryKey(iRequest,sscTaskPool);
            logger.info("do_force_return:强制退回完成!");
        }
    }
}
