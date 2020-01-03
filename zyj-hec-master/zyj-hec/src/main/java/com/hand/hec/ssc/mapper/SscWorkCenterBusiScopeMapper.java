package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkCenterBusiScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkCenterBusiScopeMapper extends Mapper<SscWorkCenterBusiScope>{
	/**
	*
	*Param workCenterId
	* @return SscWorkCenterBusiScope
	* @author bo.zhang 2019-03-15 19:24:36
	*/
	List<SscWorkCenterBusiScope> querySscWorkCenterBusiScope(@Param("workCenterId") Long workCenterId);
}