package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentGpsAsgnAe;
import com.hand.hec.csh.dto.CshPaymentGpsPrivilege;

import java.util.List;

public interface ICshPaymentGpsPrivilegeService extends IBaseService<CshPaymentGpsPrivilege>, ProxySelf<ICshPaymentGpsPrivilegeService>{

	/**
	 *@param  @param assignAeId
	 * @return CshPaymentGpsAsgnAe
	 * @author bo.zhang05@hand-china.com 2019-02-28 16:43:00
	 */
	List<CshPaymentGpsAsgnAe> selectCshPaymentGpsPrivilege(Long assignAeId,IRequest iRequest);

}