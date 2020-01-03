package com.hand.hec.ssc.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.mapper.SscTaskDispatchAdviceMapper;
import com.hand.hec.ssc.mapper.SscTaskDispatchRecordMapper;
import com.hand.hec.ssc.mapper.SscTaskPoolMapper;
import com.hand.hec.ssc.service.ISscCoreService;
import com.hand.hec.ssc.service.ISscTaskDispatchRecordService;
import com.hand.hec.ssc.service.ISscTaskPoolService;
import com.hand.hec.ssc.service.ISscWorkflowActionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscTaskPoolServiceImpl extends BaseServiceImpl<SscTaskPool> implements ISscTaskPoolService{
    @Autowired
    private SscTaskPoolMapper mapper;

    @Autowired
    private IExpEmployeeService expEmployeeService;

    @Autowired
    private SscTaskDispatchAdviceMapper sscTaskDispatchAdviceMapper;

    @Autowired
    private ExpEmployeeMapper expEmployeeMapper;

    @Autowired
    private SscTaskDispatchRecordMapper sscTaskDispatchRecordMapper;

    @Autowired
    private ISscTaskDispatchRecordService sscTaskDispatchRecordService;

    @Autowired
    private ISscCoreService sscCoreService;

    @Autowired
    private ISscWorkflowActionService sscWorkflowActionService;

    public static final String EXP_REPORT_SERVICE_NAME = "expm/EXP5110/exp_report_view_main.screen.ftl";
    public static final String PAY_REQ_SERVICE_NAME = "csh/CSH5010/csh_payment_requisition_view_main.screen";
    public static final String ACP_REQ_SERVICE_NAME = "acp/ACP3100/acp_requisition_view_maintain_main.screen";
    public static final String EXP_REPORT_PARAM_NAME = "exp_report_header_id";
    public static final String PAY_REQ_PARAM_NAME = "payment_requisition_header_id";
    public static final String ACP_REQ_PARAM_NAME = "requisition_hds_id";

    @Override
    public List<SscTaskPool> gridQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        List<SscTaskPool> sscTaskPools = new ArrayList<>();
        sscTaskPools = mapper.gridQuery(sscTaskPool);
        if(!sscTaskPools.isEmpty() && sscTaskPools != null){
            for(SscTaskPool sscTaskPool1 : sscTaskPools){
                if(sscTaskPool1.getWorkEmployeeName() == null){
                    List<ExpEmployee> expEmployees = new ArrayList<>();
                    expEmployees = expEmployeeService.getEmployeeNameByTaskId(sscTaskPool1.getTaskId());
                    String curAdviceEmpNames = StringUtils.join(expEmployees,",");
                    sscTaskPool1.setCurAdviceEmpNames(curAdviceEmpNames);
                } else {
                    sscTaskPool1.setCurAdviceEmpNames(sscTaskPool1.getWorkEmployeeName());
                }
            }
        }
        return sscTaskPools;
    }

    @Override
    public List<SscTaskDispatchRecord> ReturnQuery(String docCategory, String docNumber, IRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        //注入页面名称
        List<SscTaskDispatchRecord> list = sscTaskDispatchRecordMapper.ReturnQuery(docCategory,docNumber);
        if(null != list & !list.isEmpty()){
            for(SscTaskDispatchRecord sscTaskDispatchRecord:list){
                String pageName = this.getViewServiceName(sscTaskDispatchRecord.getTaskId(),sscTaskDispatchRecord.getDispatchRecordId(),request);
                sscTaskDispatchRecord.setPageName(pageName);
            }
        }
        return list;
    }

    @Override
    public String getViewServiceName(Long taskId, Long dispatchRecordId,IRequest request) {
        String serviceName = null;
        //根据传入的派工依据来找查看页面
        if(dispatchRecordId!=null){
            serviceName = mapper.queryServiceNameBydispatchId(dispatchRecordId);
        }
        //根据传入的任务ID来找查看页面
        if(serviceName == null){
            serviceName = mapper.queryServiceNameBytaskId(taskId);
        }
        //如果页面还为空，则根据单据类别来找对应的查看页面
        if(serviceName == null){
            String docCategory = mapper.queryDocCategoryBytaskId(taskId);
            if("EXP_REPORT".equals(docCategory)){
                serviceName = EXP_REPORT_SERVICE_NAME+"?"+EXP_REPORT_PARAM_NAME;
            }
            if("PAYMENT_REQUISITION".equals(docCategory)){
                serviceName = PAY_REQ_SERVICE_NAME+"?"+PAY_REQ_PARAM_NAME;
            }
            if("ACP_REQUISITION".equals(docCategory)){
                serviceName = ACP_REQ_SERVICE_NAME+"?"+ACP_REQ_PARAM_NAME;
            }
        }
        return serviceName;
    }

    @Override
    public List<SscTaskDispatchRecord> forceQuery(SscTaskPool sscTaskPool,IRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SscTaskDispatchRecord> list = sscTaskDispatchRecordMapper.forceQuery(sscTaskPool);
        if (null != list & !list.isEmpty()) {
            for (SscTaskDispatchRecord ssc : list) {
                String canProcessName = "";

                //注入可分配人员名称
                if (null == ssc.getCurrentEmployeeId()) {
                    List<String> names = sscTaskDispatchAdviceMapper.getAdviceWorkEmps(ssc.getTaskId());
                    StringBuilder builder = new StringBuilder(canProcessName);
                    for (String name : names) {
                        builder.append("," + name);
                    }
                    ssc.setCanProcessName(builder.toString());
                } else{
                    canProcessName = expEmployeeMapper.select(ExpEmployee.builder().employeeId(ssc.getCurrentEmployeeId()).build()).get(0).getName();
                    ssc.setCanProcessName(canProcessName);
                }

                //注入页面名称
                String pageName = this.getViewServiceName(ssc.getTaskId(),ssc.getDispatchRecordId(),request);
                ssc.setPageName(pageName);
            }
        }
        return list;
    }

    /**
     * 我正在处理的任务grid数据查询
     *
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param iRequest 请求上下文
     * @param sscTaskPool 查询条件
     * @param page
     * @param pageSize
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    @Override
    public List<SscTaskPool> doingTaskQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        //查出除 docTypeName lastRejectOpinion lastNodeName lastEmployeeName之外的数据
        List<SscTaskPool> sscTaskPools= mapper.doingTaskQuery(sscTaskPool);
        //获取 docTypeName，lastRejectOpinion，lastNodeName，lastEmployeeName
        for(int i=0;i< sscTaskPools.size();i++){
           long taskId =sscTaskPools.get(i).getTaskId();
           String docCategory=sscTaskPools.get(i).getDocCategory();
           long docId=sscTaskPools.get(i).getDocId();
           //获取lastRejectOpinion并赋值
            sscTaskPools.get(i).setLastRejectOpinion(mapper.getLastRejectOpinion(taskId,docId,docCategory));
            //获取上一节点的nodeId
            long lastNodeId = mapper.getLastNodeId(taskId,docCategory,docId);
            //获取上一节点的nodeName 并赋值
            sscTaskPools.get(i).setLastNodeName(mapper.getLastNodeName(lastNodeId));
            //获取上一个操作者的employeeId
            long lastemployeeId = mapper.getLastemployeeId(taskId,docId,docCategory);
            //获取上一节点的employeeName并赋值
            sscTaskPools.get(i).setLastEmployeeName(mapper.getLastEmployeeName(lastemployeeId));
            //获取单据类型
            long docTypeId =sscTaskPools.get(i).getDocTypeId();
            getDocTypeName(docCategory,docTypeId);  //调某个公共方法返回单据类型   目前没有QAQ

        }
        return sscTaskPools;
    }

    /**
     * 获取单据类型
     *
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param docCategory 单据代码
     * @param docTypeId 单据id
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    public String getDocTypeName(String docCategory,long docTypeId){
        String DocTypeName = "";
        //传参为空 直接返回空值
        if("".equals(docCategory)|| docCategory == null || docTypeId == 0 || true)
        {
            return "";
        }  //先默认返回空 具体方法出来了再修改！
        if("EXP_REPORT".equals(docCategory)){
            //报销单类型
        }else if ("PAYMENT_REQUISITION".equals(docCategory)){
            //借款单类型
        }else if ("EXP_REQUISITION".equals(docCategory)){
            //申请单类型
        }else if ("CON_CONTRACT".equals(docCategory)){
            //合同
        }else if ("CASH_PLAN".equals(docCategory)){
            //资金计划
        }else if ("ACP_REQUISITION".equals(docCategory)){
            //付款申请单
        }else if ("BANK_TREASURER_REQ".equals(docCategory)){
            //资金调拨单
        }else if ("BUDGET_JOURNAL".equals(docCategory)){
            //预算日记账
        }else if ("PUR_ORDER".equals(docCategory)){
            //采购顶单
        }else if ("PUR_REQUISITION".equals(docCategory)){
            //采购申请
        }else if ("EAM_REQUISITION".equals(docCategory)){
            //资金业务申请
        } else if ("PTL_ANNOUNCEMENT".equals(docCategory)){
            //门户
        } else if ("OA_FLOW".equals(docCategory)){
            //OA
        }else if ("WORK_ORDER".equals(docCategory)){
            //核算工单
        }
        return DocTypeName;
    }

    @Override
    public List<SscTaskPool> holdTaskQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SscTaskPool> sscTaskPools= mapper.holdTaskQuery(sscTaskPool);
        for(int i=0;i<sscTaskPools.size();i++){
            //获取上一次暂挂原因
            sscTaskPools.get(i).setLastHoldReason(mapper.getLastHoldReason(sscTaskPools.get(i).getTaskId()));
        }
        return sscTaskPools;
    }

    @Override
    public List<SscTaskPool> returnTaskQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SscTaskPool> sscTaskPools= mapper.returnTaskQuery(sscTaskPool);
        for(int i=0;i<sscTaskPools.size();i++){
            //获取上一次退回原因
            sscTaskPools.get(i).setLastReturnReason(mapper.getLastReturnReason(sscTaskPools.get(i).getTaskId()));
        }
        return sscTaskPools;
    }

    @Override
    public void doBatchProcess(IRequest iRequest, List<SscTaskPool> sscTaskPools) {
        if(!sscTaskPools.isEmpty() && sscTaskPools != null){
            for(SscTaskPool sscTaskPool : sscTaskPools){
                Long actionId = 0L;
                actionId = sscWorkflowActionService.getActionId(sscTaskPool.getDispatchRecordId(),sscTaskPool.getActionType());
                sscCoreService.doProcess(iRequest,sscTaskPool.getDispatchRecordId(),actionId,sscTaskPool.getOpinion());
            }
        }
    }

    @Override
    public List<SscTaskPool> getTaskRecordInfo(IRequest iRequest,Long taskId, Long dispatchRecordId) {
        List<SscTaskPool> sscTaskPools = new ArrayList<>();
        if(taskId != null){
            sscTaskPools = mapper.getTaskRecordInfoByTaskId(taskId);
            if(sscTaskPools.isEmpty() && sscTaskPools == null){
                sscTaskPools = mapper.getTaskRecordInfo(dispatchRecordId);
            }
        } else {
            sscTaskPools = mapper.getTaskRecordInfo(dispatchRecordId);
        }
        return sscTaskPools;
    }

    @Override
    public String queryServiceNameBydispatchId(IRequest iRequest, Long dispatchRecordId) {
        return mapper.queryServiceNameBydispatchId(dispatchRecordId);
    }
}