package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;

import java.util.List;

public interface ISscTaskDispatchRecordService extends IBaseService<SscTaskDispatchRecord>, ProxySelf<ISscTaskDispatchRecordService>{
    /**
     *获取需要执行的方法的方法名称
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 18:42
     *@param dispatchRecordId 派工记录Id
     *@param actionId 动作Id
     *@return List<SscTaskDispatchRecord>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecord> getActionProcedure(Long dispatchRecordId,Long actionId);

    /**
     *获取任务结束时需要执行的方法的方法名称
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 18:42
     *@param taskId 任务Id
     *@return List<SscTaskDispatchRecord>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecord> getFinishProcedure(Long taskId);

    /**

     * 组长/管理员拒绝退回(批量处理)
     *
     * @author ngls.luhui 2019-03-27 16:45
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecordList 任务分派记录(含操作意见)
     * @return
     * @Version 1.0
     **/
    Boolean rejectReturn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList);

    /**
     * 组长/管理员同意退回(批量处理)
     *
     * @author ngls.luhui 2019-03-27 16:45
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecordList 任务分派记录(含操作意见)
     * @return
     * @Version 1.0
     **/
    Boolean agreeReturn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList);

    /**
     * 组长/管理员同意并指派(批量处理)
     *
     * @author ngls.luhui 2019-03-27 16:45
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecordList 任务分派记录(含操作意见)
     * @return
     * @Version 1.0
     **/
    Boolean conAndAsgn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList);

    /**
     * 同意并指派单据的人员查询
     *
     * @return List<SscTaskPool>
     * @param request 请求上下文
     * @param page
     * @param pageSize
     * @author ngls.luhui 2019-03-26 16:08
     */
    List<SscTaskDispatchRecord> conAndAgenQuery(IRequest request,Integer page,Integer pageSize);

    /**
     * 组长/管理员强制退回(批量处理)
     *
     * @author ngls.luhui 2019-03-27 16:45
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecordList 任务分派记录(含操作意见)
     * @return
     * @Version 1.0
     **/
    Boolean forceReturn(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList);

    /**
     * 派工(批量处理)
     *
     * @author ngls.luhui 2019-03-27 16:45
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecordList 任务分派记录(含操作意见,employeeId,workTeamId)
     * @return
     * @Version 1.0
     **/
    Boolean dispatch(IRequest iRequest, List<SscTaskDispatchRecord> sscTaskDispatchRecordList);

    /**
     *获取所有待派工的数据
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 18:42
     *@param taskId 任务Id
     *@return List<SscTaskDispatchRecord>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecord> selectWatingData(IRequest iRequest);

}