package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshOfferFormatAsgnAe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshOfferFormatAsgnAeMapper extends Mapper<CshOfferFormatAsgnAe>{

	/**
	 * 查询已分配的核算主体
	 *
	 * @param formatHdsId
	 * @author bo.zhang 2019-03-04 17:42:26
	 * @return CshOfferFormatAsgnAe
	 */
	List<CshOfferFormatAsgnAe> queryCshOfferFormatAsgnAe(@Param("formatHdsId") Long formatHdsId);

	/**
	 * 查询批量分配核算主体
	 *
	 * @param formatHdsId
	 * @author bo.zhang 2019-03-04 17:42:26
	 * @return CshOfferFormatAsgnAe
	 */
	List<CshOfferFormatAsgnAe> selectCshOfferFormatAsgnAe(@Param("formatHdsId") Long formatHdsId);

}