package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkCenterDocType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkCenterDocTypeService extends IBaseService<SscWorkCenterDocType>, ProxySelf<ISscWorkCenterDocTypeService>{
	/**
	 *查询共享作业中心业务范围_单据类型范围
	 *Param scopeId magOrgId
	 * @return SscWorkCenterBusiScope
	 * @author bo.zhang 2019-03-15 19:24:36
	 */
	List<SscWorkCenterDocType> querySscWorkCenterDocType(@Param("scopeId") Long scopeId,
														 @Param("magOrgId") Long magOrgId,
														 IRequest iRequest);

}