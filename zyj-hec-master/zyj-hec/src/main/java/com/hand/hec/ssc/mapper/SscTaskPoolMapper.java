package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscTaskPool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscTaskPoolMapper extends Mapper<SscTaskPool> {
    /**
     * 我已处理的任务grid数据查询
     *
     * @param sscTaskPool 查询条件
     * @return List<SscTaskPool>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/22 14:37
     * @Version 1.0
     **/
    List<SscTaskPool> gridQuery(SscTaskPool sscTaskPool);




    /**
     * 根据任务分派记录ID查询页面名称
     *
     * @param dispatchRecordId 任务分派记录ID
     * @return viewServiceName 页面名称
     * @author ngls.luhui 2019-03-25 17:26
     */
    String queryServiceNameBydispatchId(@Param("dispatchRecordId") Long dispatchRecordId);

    /**
     * 根据任务ID查询页面名称
     *
     * @param taskId 任务ID
     * @return viewServiceName 页面名称
     * @author ngls.luhui 2019-03-25 17:26
     */
    String queryServiceNameBytaskId(@Param("taskId") Long taskId);

    /**
     * 根据任务ID查询单据类型
     *
     * @param taskId 任务ID
     * @return docCategory 页面名称
     * @author ngls.luhui 2019-03-25 17:26
     */
    String queryDocCategoryBytaskId(@Param("taskId") Long taskId);

    /**
     * 我正在处理的任务grid数据查询
     *
     * @param sscTaskPool 查询条件
     * @return List<SscTaskPool>
     * @Author weikun.wang
     * @Date 2019/3/25 14:37
     * @Version 1.0
     **/
    List<SscTaskPool> doingTaskQuery(@Param("sscTaskPool") SscTaskPool sscTaskPool);


    /**
     * 我正在处理的任务grid数据中LastRejectOpinion字段查询
     *
     * @param taskId , docId 查询条件
     * @return String
     * @Author weikun.wang
     * @Date 2019/3/25 14:37
     * @Version 1.0
     **/
    String getLastRejectOpinion(@Param("taskId") long taskId, @Param("docId") long docId, @Param("docCategory") String docCategory);

    long getLastNodeId(@Param("taskId") long taskId, @Param("docCategory") String docCategory, @Param("docId") long docId);

    /**
     * 我正在处理的任务grid数据中LastNodeName字段查询
     *
     * @param lastNodeId 查询条件
     * @return String
     * @Author weikun.wang
     * @Date 2019/3/25 14:37
     * @Version 1.0
     **/
    String getLastNodeName(@Param("lastNodeId") long lastNodeId);

    Long getLastemployeeId(@Param("taskId") long taskId, @Param("docId") long docId, @Param("docCategory") String docCategory);

    String getLastEmployeeName(@Param("lastEmployeeId") long lastEmployeeId);


    /**
     *我暂挂的任务grid数据查询
     *
     *@Author weikun.wang
     *@Date 2019/3/25 14:37
     *@return List<SscTaskPool>
     *@Version 1.0
     **/
    List<SscTaskPool> holdTaskQuery(@Param("sscTaskPool") SscTaskPool sscTaskPool);

    //获取上次暂挂的原因
    String getLastHoldReason(@Param("taskId") long taskId);


    /**
     *我退回的任务grid数据查询
     *
     *@Author weikun.wang
     *@Date 2019/3/25 14:37
     *@return List<SscTaskPool>
     *@Version 1.0
     **/
    List<SscTaskPool> returnTaskQuery(@Param("sscTaskPool") SscTaskPool sscTaskPool);

    //获取上次退回的原因
    String getLastReturnReason(@Param("taskId") long taskId);

    /**
     *根据派工记录Id获取任务池信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 19:54
     *@param dispatchRecordId 派工记录Id
     *@return
     *@Version 1.0
     **/
    List<SscTaskPool> getTaskRecordInfo(Long dispatchRecordId);

    /**
     *根据派工记录Id获取任务池信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 19:54
     *@param taskId 任务Id
     *@return
     *@Version 1.0
     **/
    List<SscTaskPool> getTaskRecordInfoByTaskId(Long taskId);
}