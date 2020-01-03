package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;
import com.hand.hec.ssc.dto.SscTaskPool;

import java.util.List;

public interface ISscTaskPoolService extends IBaseService<SscTaskPool>, ProxySelf<ISscTaskPoolService> {

    /**
     * 我已处理的任务grid数据查询
     *
     * @param iRequest    请求上下文
     * @param sscTaskPool 查询条件
     * @param page
     * @param pageSize
     * @return List<SscTaskPool>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/22 14:46
     * @Version 1.0
     **/
    List<SscTaskPool> gridQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize);

    /**
     * 退回申请单据查询
     *
     * @param docCategory 单据类别
     * @param docNumber 单据编号
     * @param request 上下文
     * @param page
     * @param pageSize
     * @return List<SscTaskPool>
     * @author ngls.luhui 2019-03-25 14:51
     */
    List<SscTaskDispatchRecord> ReturnQuery(String docCategory, String docNumber, IRequest request, Integer page, Integer pageSize);

    /**
     * 获取查看页面名称
     *
     * @param taskId 任务ID
     * @param dispatchRecordId 任务分派记录ID
     * @param request 上下文
     * @author ngls.luhui 2019-03-25 17:10
     * @return java.lang.String
     */
    String getViewServiceName(Long taskId,Long dispatchRecordId,IRequest request);

    /**
     * 单据强制收回查询
     *
     * @param sscTaskPool 查询条件
     * @param request 上下文
     * @param page
     * @param pageSize
     * @author ngls.luhui 2019-03-26 16:08
     * @return List<SscTaskPool>
     */
    List<SscTaskDispatchRecord> forceQuery(SscTaskPool sscTaskPool,IRequest request, Integer page, Integer pageSize);


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
    List<SscTaskPool> doingTaskQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize);


    /**
     * 我暂挂的任务grid数据查询
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
    List<SscTaskPool> holdTaskQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize);

    /**
     * 我退回的任务grid数据查询
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
    List<SscTaskPool> returnTaskQuery(IRequest iRequest, SscTaskPool sscTaskPool, int page, int pageSize);

    /**
     *操作人员批量操作
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 16:37
     *@param iRequest
     *@param sscTaskPools
     *@return
     *@Version 1.0
     **/
    void doBatchProcess(IRequest iRequest, List<SscTaskPool> sscTaskPools);

    /**
     *根据派工记录Id获取任务池信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 19:54
     *@param iRequest
     *@param taskId 任务Id
     *@param dispatchRecordId 派工记录Id
     *@return List<SscTaskPool>
     *@Version 1.0
     **/
    List<SscTaskPool> getTaskRecordInfo(IRequest iRequest,Long taskId, Long dispatchRecordId);

    /**
     *根据派工记录Id获取单据页面
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/2 10:05
     *@param iRequest
     *@param dispatchRecordId 派工记录Id
     *@return String
     *@Version 1.0
     **/
    String queryServiceNameBydispatchId(IRequest iRequest, Long dispatchRecordId);
}
