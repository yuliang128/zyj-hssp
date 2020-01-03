package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldSobPeriodStatus;

import java.util.List;

public interface GldSobPeriodStatusMapper extends Mapper<GldSobPeriodStatus>{

	/**
	 * 查询打开的期间
	 * @param dto dto
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return 打开的期间
	 */
	List<GldSobPeriodStatus> queryForStatusOpen(GldSobPeriodStatus dto);

	/**
	 * 查询关闭的期间
	 * @param dto dto
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return 关闭的期间
	 */
	List<GldSobPeriodStatus> queryForStatusClosed(GldSobPeriodStatus dto);


	/**
	 * 查询符合条件的期间
	 *  条件均为等号，请勿更改，要用使用like,请自己copy下单独写个接口
	 * @param dto dto
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return 符合条件的期间
	 */
	List<GldSobPeriodStatus> queryGldSobPeriodStatus(GldSobPeriodStatus dto);




	/**
	 *  重新打开会计期间
	 *
	 * @param dto dto
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 *
	 */
	void reopenGldPeriodStatus(GldSobPeriodStatus dto);


	/**
	 *  清空期间关闭异常表
	 *
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 *
	 */
	void deleteFndSobClosePeriodErr();

	/**
	 *  查询是否是第一次打开期间
	 *
	 * @param dto dto
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 * @return
	 */
	Long checkIsFirstOpen(GldSobPeriodStatus dto);

	/**
	 * 关闭账套级会计期间
	 *  即 将期间状态改为C
	 * @param dto dto
	 * @author rui.shi@hand-china.com 2019-03-08 14:58
	 *
	 */
	void closeGldSobPeriodStatus(GldSobPeriodStatus dto);

}