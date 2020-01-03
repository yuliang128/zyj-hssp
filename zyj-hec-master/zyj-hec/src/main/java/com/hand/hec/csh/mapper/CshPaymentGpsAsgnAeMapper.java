package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentGpsAsgnAe;

import java.util.List;

public interface CshPaymentGpsAsgnAeMapper extends Mapper<CshPaymentGpsAsgnAe>{

	/**
	 *
	 * @param  @param groupId
	 * @return CshPaymentGpsAsgnAe
	 * @author bo.zhang05@hand-china.com 2019-02-28 16:14:22
	 */
	 List<CshPaymentGpsAsgnAe> selectCshPaymentGpsAsgnAe(Long groupId);

}