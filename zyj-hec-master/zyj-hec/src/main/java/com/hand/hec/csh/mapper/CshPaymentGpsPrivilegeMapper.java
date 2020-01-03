package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentGpsAsgnAe;
import com.hand.hec.csh.dto.CshPaymentGpsPrivilege;

import java.util.List;

public interface CshPaymentGpsPrivilegeMapper extends Mapper<CshPaymentGpsPrivilege>{

	/**
	 *@param  @param assignAeId
	 * @return CshPaymentGpsAsgnAe
	 * @author bo.zhang05@hand-china.com 2019-02-28 16:43:00
	 */
	List<CshPaymentGpsAsgnAe> selectCshPaymentGpsPrivilege(Long assignAeId);

}