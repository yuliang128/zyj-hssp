package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkflowMapper extends Mapper<SscWorkflow>{
	/**
	 *根据流程代码，秒速，单据类别查询
	 *@Param workflowCode,description,docCategory
	 * @return SscWorkflow
	 * @author bo.zhang 2019-03-20 19:24:36
	 */
	List<SscWorkflow> querySscWorkflow(@Param("workflowCode") String workflowCode,
											  @Param("description") String description,
											  @Param("docCategory") String docCategory);
}