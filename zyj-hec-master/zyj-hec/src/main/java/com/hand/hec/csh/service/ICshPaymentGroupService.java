package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICshPaymentGroupService extends IBaseService<CshPaymentGroup>, ProxySelf<ICshPaymentGroupService>{
	/**
	 *@param  magOrgId groupCode description
	 * @return CshPaymentGroup
	 * @author bo.zhang05@hand-china.com 2019-03-01 10:43:06
	 */
	List<CshPaymentGroup> selectCshPaymentGroup(@Param("magOrgId") Long magOrgId,
												@Param("groupCode") String groupCode,
												@Param("description") String description,
												IRequest iRequest);
}