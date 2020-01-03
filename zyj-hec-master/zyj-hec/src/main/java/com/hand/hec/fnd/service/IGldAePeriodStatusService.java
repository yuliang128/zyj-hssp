package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldAePeriodStatus;
import com.hand.hec.fnd.exception.GldAePeriodStatusException;

import java.util.List;

/**
 * <p>
 * 核算主体级会计期间控制service接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 17:26
 */
public interface IGldAePeriodStatusService
		extends IBaseService<GldAePeriodStatus>, ProxySelf<IGldAePeriodStatusService> {

	/**
	 * 查询选择核算主体的下拉框数据
	 *
	 * @author LJK 2019-01-28 16:05
	 * @return
	 */
	List<GldAePeriodStatus> accEntityListQuery();

	/**
	 * 查询未打开的会计期间
	 *
	 * @param periodSetId 会计期id
	 * @param accEntityId 核算主体id
	 * @param page 页码
	 * @param pageSize 分页显示数量
	 * @author LJK 2019-01-31 10:43
	 * @return
	 */
	List<GldAePeriodStatus> unOpenedPeriodQuery(Long periodSetId, Long accEntityId, int page, int pageSize);

	/**
	 * 查询已打开的会计期间
	 *
	 * @param periodSetId 会计期id
	 * @param accEntityId 核算主体id
	 * @param page 页码
	 * @param pageSize 分页显示数量
	 * @author LJK 2019-01-31 10:43
	 * @return
	 */
	List<GldAePeriodStatus> OpenedPeriodQuery(Long periodSetId, Long accEntityId, int page, int pageSize);

	/**
	 * 初始化期间
	 *
	 * @param request
	 * @param periodStatus 实体
	 * @author LJK 2019-02-14 17:04
	 * @return
	 */
	void initPeriod(IRequest request, GldAePeriodStatus periodStatus);

	/**
	 * 打开期间
	 *
	 * @param request
	 * @param periodStatus 实体
	 * @author LJK 2019-02-14 17:04
	 * @throws GldAePeriodStatusException exception
	 * @return
	 */
	void openPeriod(IRequest request, GldAePeriodStatus periodStatus,Long periodSetId) throws GldAePeriodStatusException;

	/**
	 * 关闭期间
	 *
	 * @param request
	 * @param periodStatus 实体
	 * @param periodSetId 会计期id
	 * @author LJK 2019-02-14 17:04
	 * @throws GldAePeriodStatusException exception
	 * @return
	 */
	void closePeriod(IRequest request, GldAePeriodStatus periodStatus,Long periodSetId) throws GldAePeriodStatusException;


	/**
	 * 插入期间
	 * @param request
	 * @param periodStatus
	 */
	void openGldPeriodStatus(IRequest request, GldAePeriodStatus periodStatus);


	/**
	 * 重新打开期间
	 * @param request
	 * @param periodStatus
	 */
	void reopenGldPeriodStatus(IRequest request, GldAePeriodStatus periodStatus);


	/**
	 * 关闭期间
	 * @param request
	 * @param periodStatus
	 */
	void closeGldPeriodStatus(IRequest request, GldAePeriodStatus periodStatus);
}