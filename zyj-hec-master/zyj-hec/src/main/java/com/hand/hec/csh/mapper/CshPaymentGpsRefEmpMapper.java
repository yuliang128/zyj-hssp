package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentGpsRefEmp;

import java.util.List;

public interface CshPaymentGpsRefEmpMapper extends Mapper<CshPaymentGpsRefEmp>{

	/**
	 *@param  @param groupId
	 * @return CshPaymentGroup
	 * @author bo.zhang05@hand-china.com 2019-02-28 19:43:00
	 */
	List<CshPaymentGpsRefEmp> selectCshPaymentGpsRefEmp(Long magOrgId);


}