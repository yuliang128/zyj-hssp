package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshOfferFormatAsgnAe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICshOfferFormatAsgnAeService extends IBaseService<CshOfferFormatAsgnAe>, ProxySelf<ICshOfferFormatAsgnAeService>{

	/**
	 * 查询已分配的核算主体
	 *
	 * @param formatHdsId
	 * @author bo.zhang 2019-03-04 17:42:26
	 * @return CshOfferFormatAsgnAe
	 */
	List<CshOfferFormatAsgnAe> queryCshOfferFormatAsgnAe(@Param("formatHdsId") Long formatHdsId,IRequest iRequest);

	/**
	 * 查询批量分配核算主体
	 *
	 * @param formatHdsId
	 * @author bo.zhang 2019-03-04 17:42:26
	 * @return CshOfferFormatAsgnAe
	 */
	List<CshOfferFormatAsgnAe> selectCshOfferFormatAsgnAe(@Param("formatHdsId") Long formatHdsId,IRequest iRequest);

}