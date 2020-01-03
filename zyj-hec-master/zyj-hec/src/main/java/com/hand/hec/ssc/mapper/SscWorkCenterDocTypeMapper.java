package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkCenterDocType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkCenterDocTypeMapper extends Mapper<SscWorkCenterDocType>{
	/**
	 *查询共享作业中心业务范围_单据类型范围
	 *Param scopeId magOrgId
	 * @return SscWorkCenterBusiScope
	 * @author bo.zhang 2019-03-15 19:24:36
	 */
	List<SscWorkCenterDocType> querySscWorkCenterDocType(@Param("scopeId") Long scopeId,
														 @Param("magOrgId") Long magOrgId);

}