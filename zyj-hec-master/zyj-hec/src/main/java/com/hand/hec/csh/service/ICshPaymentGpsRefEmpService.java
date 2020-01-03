package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentGpsRefEmp;

import java.util.List;

public interface ICshPaymentGpsRefEmpService extends IBaseService<CshPaymentGpsRefEmp>, ProxySelf<ICshPaymentGpsRefEmpService>{

	/**
	 *@param  @param groupId
	 * @return CshPaymentGpsRefEmp
	 * @author bo.zhang05@hand-china.com 2019-02-28 19:43:00
	 */
	List<CshPaymentGpsRefEmp> selectCshPaymentGpsRefEmp(Long groupId,IRequest iRequest);
}