package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscTaskDispatchRecord;
import com.hand.hec.ssc.dto.SscTaskPool;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscTaskDispatchRecordMapper extends Mapper<SscTaskDispatchRecord>{
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
    List<SscTaskDispatchRecord> getActionProcedure(@Param("dispatchRecordId") Long dispatchRecordId,@Param("actionId") Long actionId);

    /**
     *获取任务结束时需要执行的方法的方法名称
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 18:42
     *@param taskId 任务Id
     *@return List<SscTaskDispatchRecord>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecord> getFinishProcedure(@Param("taskId") Long taskId);

    /**
     * 退回申请单据查询
     *
     * @param docCategory 单据类别
     * @param docNumber   单据编号
     * @return List<SscTaskPool>
     * @author ngls.luhui 2019-03-25 14:51
     */
    List<SscTaskDispatchRecord> ReturnQuery(@Param("docCategory") String docCategory, @Param("docNumber") String docNumber);

    /**
     * 单据强制收回查询
     *
     * @param sscTaskPool 查询条件
     * @return List<SscTaskPool>
     * @author ngls.luhui 2019-03-26 16:08
     */
    List<SscTaskDispatchRecord> forceQuery(SscTaskPool sscTaskPool);

    /**
     * 同意并指派单据的人员查询
     *
     * @return List<SscTaskPool>
     * @author ngls.luhui 2019-03-26 16:08
     */
    List<SscTaskDispatchRecord> conAndAgenQuery();

    /**
     *获取所有待派工的数据
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/25 18:42
     *@param
     *@return List<SscTaskDispatchRecord>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecord> selectWatingData();
}