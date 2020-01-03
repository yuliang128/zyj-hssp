package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshPaymentGroupMapper extends Mapper<CshPaymentGroup>{


	/**
	 *@param  @param magOrgId @param groupCode @param description
	 * @return CshPaymentGroup
	 * @author bo.zhang05@hand-china.com 2019-03-01 10:43:06
	 */
	List<CshPaymentGroup> selectCshPaymentGroup(@Param("magOrgId") Long magOrgId,
												@Param("groupCode") String groupCode,
												@Param("description") String description);

}