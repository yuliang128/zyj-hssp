package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentGpsAsgnAe;

import java.util.List;

public interface ICshPaymentGpsAsgnAeService extends IBaseService<CshPaymentGpsAsgnAe>, ProxySelf<ICshPaymentGpsAsgnAeService>{

	/**
	 *
	 *
	 * @param  @param groupId
	 * @return CshPaymentGpsAsgnAe
	 * @author bo.zhang05@hand-china.com 2019-02-28 16:43
	 */
	List<CshPaymentGpsAsgnAe> selectCshPaymentGpsAsgnAe(Long groupId, IRequest iRequest);

}