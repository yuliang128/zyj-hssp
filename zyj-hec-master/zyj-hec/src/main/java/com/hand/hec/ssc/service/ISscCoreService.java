package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;

/**
 * 工作台处理接口类
 *
 * @author zhaohui 2019/03/25 14:04
 */
public interface ISscCoreService extends ProxySelf<ISscCoreService> {
    /**
     * 操作人员点击操作
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param dispatchRecordId 任务分派记录Id
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
    void doProcess(IRequest iRequest, Long dispatchRecordId, Long actionId, String opinion);

    /**
     * 派工
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 19:18
     *@param iRequest
     *@param dispatchRecordId 派工记录Id
     *@param taskId 任务Id
     *@param workTeamId 工作组Id
     *@param employeeId 员工Id
     *@return
     *@Version 1.0
     **/
     void doDispatch(IRequest iRequest, Long dispatchRecordId, Long taskId, Long workTeamId, Long employeeId);

    /**
     * 组长/管理员同意退回
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
    void doAgreeReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, String opinion);

    /**
     * 组长/管理员拒绝退回
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
    void doRejectReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, String opinion);

    /**
     * 组长/管理员强制退回
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
    void doForceReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, String opinion);

    /**
     * 同意
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doApprove(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                          String opinion);

    /**
     * 拒绝
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doReject(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                         String opinion);

    /**
     * 暂挂
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doHold(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId, String opinion);

    /**
     * 取消暂挂
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doCancelHold(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                             String opinion);

    /**
     * 退回
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doReturnBack(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                             String opinion);

    /**
     * 工作人员取消退回给提单人
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doCancelReturnBack(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                                   String opinion);

    /**
     * 申请退回
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/25 14:09
     * @param iRequest 请求上下文
     * @param sscTaskDispatchRecord 任务分派记录
     * @param actionId 流程动作Id
     * @param opinion 操作意见
     * @return
     * @Version 1.0
     **/
     void doApplyReturn(IRequest iRequest, SscTaskDispatchRecord sscTaskDispatchRecord, Long actionId,
                              String opinion);

    /**
     * 自动派工
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 19:18
     *@param iRequest
     *@return
     *@Version 1.0
     **/
    void autoDispatch(IRequest iRequest);

    /**
     * 手动派工
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 19:18
     *@param iRequest
     *@return
     *@Version 1.0
     **/
     void manualDispatch(IRequest iRequest);
}
