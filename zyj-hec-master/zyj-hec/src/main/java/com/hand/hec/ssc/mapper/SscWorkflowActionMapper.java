package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkflowAction;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SscWorkflowActionMapper extends Mapper<SscWorkflowAction>{

    /**
     *根据动作类型和派工记录Id获取ActionId
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 16:52
     *@param dispatchRecordId 派工记录Id
     *@param actionType 动作类型
     *@return
     *@Version 1.0
     **/
    Long getActionId(@Param("dispatchRecordId") Long dispatchRecordId,@Param("actionType") String actionType);

    /**
     *根据派工记录Id获取Action
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 16:52
     *@param dispatchRecordId 派工记录Id
     *@return
     *@Version 1.0
     **/
    List<SscWorkflowAction> getActionByDispatchRecordId(Long dispatchRecordId);
}