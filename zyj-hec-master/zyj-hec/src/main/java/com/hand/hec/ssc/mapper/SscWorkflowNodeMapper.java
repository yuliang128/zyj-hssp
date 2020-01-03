package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkflowNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkflowNodeMapper extends Mapper<SscWorkflowNode>{
	/**
	 *根据workflowId，nodeId 查询出共享工作流程节点
	 *@Param workflowId,nodeId
	 * @return SscWorkflowNode
	 * @author bo.zhang 2019-03-20 19:24:36
	 */
	List<SscWorkflowNode> querySscWorkflowNode(@Param("workflowId") Long workflowId,
											  			@Param("nodeId") Long nodeId);

	/**
	 *获取当前节点之后的所有节点
	 *
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/26 10:17
	 *@param workflowId 工作流Id
	 *@param currentNodeSequence 当前节点序号
	 *@return  List<SscWorkflowNode>
	 *@Version 1.0
	 **/
    List<SscWorkflowNode> getAllNextNode(@Param("workflowId") Long workflowId,@Param("currentNodeSequence") Long currentNodeSequence);

    /**
	 *获取当前节点之后的所有节点
	 *
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/26 10:17
	 *@param workflowId 工作流Id
	 *@param currentNodeSequence 当前节点序号
	 *@return  List<SscWorkflowNode>
	 *@Version 1.0
	 **/
    List<SscWorkflowNode> getAllPreNode(@Param("workflowId") Long workflowId,@Param("currentNodeSequence") Long currentNodeSequence);
}