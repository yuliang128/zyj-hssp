package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkCenterService extends IBaseService<SscWorkCenter>, ProxySelf<ISscWorkCenterService> {
	/**
	 * 查询工作中心
	 *
	 * @return
	 * @author bo.zhang 2019-03-11 16:24:36
	 */
	List<SscWorkCenter> querySscWorkCenter(IRequest iRequest,
										   @Param("workCenterCode") String workCenterCode,
										   @Param("description") String description);

	/**
	 * 任务中心的派工查询
	 *@Param
	 * @return SscWorkCenter
	 * @author bo.zhang 2019-03-27 16:24:36
	 */
	List<SscWorkCenter> selectSscWorkCenter(IRequest iRequest,
											@Param("name") String name,
											@Param("description") String description,
											@Param("workTeamName") String workTeamName);


	/**
	 * 工作中心下拉框(查询条件为工作中心字段通用)
	 *
	 * @param sscWorkCenter
	 * @param request
	 * @author ngls.luhui 2019-03-25 18:51
	 * @return java.util.List<com.hand.hec.ssc.dto.SscWorkCenter>
	 */
	List<SscWorkCenter> combox(SscWorkCenter sscWorkCenter,IRequest request);

}