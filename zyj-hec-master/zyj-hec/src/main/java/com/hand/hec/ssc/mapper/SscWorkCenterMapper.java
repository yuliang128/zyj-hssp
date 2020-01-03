package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkCenter;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkCenterMapper extends Mapper<SscWorkCenter> {

	/**
	 * 查询工作中心
	 *
	 * @return
	 * @author bo.zhang 2019-03-11 16:24:36
	 */
	List<SscWorkCenter> querySscWorkCenter(@Param("workCenterCode") String workCenterCode,
												  @Param("description") String description);


	/**
	 * 任务中心的派工查询
	 *@Param
	 * @return SscWorkCenter
	 * @author bo.zhang 2019-03-27 16:24:36
	 */
	List<SscWorkCenter> selectSscWorkCenter(@Param("name") String name,
										    @Param("description") String description,
											@Param("workTeamName") String workTeamName);

    /**
     * 当前员工为工作组负责人的工作组对应的工作中心
     *
     * @param workCenterCode 工作中心代码
     * @param workCenterName 工作中心名称
     * @return java.util.List<com.hand.hec.ssc.dto.SscWorkCenter>
     * @author ngls.luhui 2019-03-25 18:51
     */
    List<SscWorkCenter> queryCenterByUser(@Param("workCenterCode") String workCenterCode, @Param("workCenterName") String workCenterName);
}