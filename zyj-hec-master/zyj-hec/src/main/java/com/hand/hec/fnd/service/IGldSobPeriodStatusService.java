package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldSobPeriodStatus;
import com.hand.hec.fnd.exception.GldSobPeriodStatusException;

import java.util.List;

public interface IGldSobPeriodStatusService
		extends IBaseService<GldSobPeriodStatus>, ProxySelf<IGldSobPeriodStatusService>{

	/**
	 * 查询打开的期间
	 * @param requestContext requestContext
	 * @param dto dto
	 * @param page page
	 * @param pageSize pageSize
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return 打开的期间
	 */
	List<GldSobPeriodStatus> queryForStatusOpen(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize);


	/**
	 * 查询关闭的期间
	 * @param requestContext requestContext
	 * @param dto dto
	 * @param page page
	 * @param pageSize pageSize
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return 关闭的期间
	 */
	List<GldSobPeriodStatus> queryForStatusClosed(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize);


	/**
	 * 初始化期间
	 * @param requestContext requestContext
	 * @param dto dto
	 * @param page page
	 * @param pageSize pageSize
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return
	 */
	boolean initPeriod(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize);

	/**
	 * 打开期间
	 * @param requestContext requestContext
	 * @param dto dto
	 * @param page page
	 * @param pageSize pageSize
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return
	 */
	boolean openPeriod(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize) throws
			GldSobPeriodStatusException;


	/**
	 * 关闭期间
	 * @param requestContext requestContext
	 * @param dto dto
	 * @param page page
	 * @param pageSize pageSize
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return
	 */
	boolean closePeriod(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize);

	/**
	 *  关闭期间
	 *   需要将账套级会计期间关闭
	 *   需要将核算主体级会计期间关闭
	 * @param requestContext requestContext
	 * @param dto dto
	 */
	void closeGldPeriodStatus(IRequest requestContext, GldSobPeriodStatus dto);


	/**
	  * 是否能初始化期间
	  * @param   requestContext requestContext
	  * @param   periodSetCode 会计期代码
	  * @param   setOfBooksId 账套id
	  * @author rui.shi@hand-china.com 2019-03-19 10:45
	  * @return 是/否
	  */
	boolean canInitPeriod(IRequest requestContext, String periodSetCode,Long setOfBooksId);


	 /**
	  * 打开期间状态(第一次打開)
	  *  其实就是往数据库插入一条状态为打开的记录
	  * @param   requestContext requestContext
	  * @param   dto 查询条件
	  * @author rui.shi@hand-china.com 2019-03-19 11:16
	  * @return
	  */
	 GldSobPeriodStatus openGldPeriodStatus(IRequest requestContext, GldSobPeriodStatus dto);



	 /**
	  * 重新打开期间状态
	  *
	  * @param   requestContext requestContext
	  * @param   dto 查询条件
	  * @author rui.shi@hand-china.com 2019-03-19 11:16
	  *
	  */
	 void reopenGldPeriodStatus(IRequest requestContext, GldSobPeriodStatus dto) throws GldSobPeriodStatusException;


	/**
	 * 打开以此账套为默认账套的所有核算主体对应的会计期间
	 * @param requestContext requestContext
	 * @param accEntityId  accEntityId
	 * @param periodSetId periodSetId
	 * @param periodName periodName
	 * @param userId userId
	 * @throws GldSobPeriodStatusException
	 *  @author rui.shi@hand-china.com 2019-03-19 11:16
	 */
	 void openPeriodRelatedToAcc(IRequest requestContext, Long accEntityId, Long periodSetId, String periodName, Long userId);


	/**
	 * 关闭以此账套为默认账套的所有核算主体对应的会计期间
	 * @param requestContext requestContext
	 * @param accEntityId  accEntityId
	 * @param periodSetId periodSetId
	 * @param periodName periodName
	 * @param userId userId
	 * @throws GldSobPeriodStatusException
	 *  @author rui.shi@hand-china.com 2019-04-09 11:16
	 */
	void closePeriodRelatedToAcc(IRequest requestContext, Long accEntityId, Long periodSetId, String periodName, Long userId);



}