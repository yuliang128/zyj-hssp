package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkflowNodeRule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SscWorkflowNodeRuleMapper extends Mapper<SscWorkflowNodeRule> {

    /**
     *
     * 获取当前节点配置的权限规则
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/26 11:08
     * @param docWflAssignId 业务单据分配工作流程ID
     * @param nodeId 节点Id
     * @param currentDate 当前日期
     * @return
     * @Version 1.0
     **/
    List<SscWorkflowNodeRule> getAllRules(@Param("docWflAssignId") Long docWflAssignId, @Param("nodeId") Long nodeId,
                    @Param("currentDate") Date currentDate);
}
