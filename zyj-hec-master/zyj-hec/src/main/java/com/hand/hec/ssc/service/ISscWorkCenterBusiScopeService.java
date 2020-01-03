package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkCenterBusiScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkCenterBusiScopeService extends IBaseService<SscWorkCenterBusiScope>, ProxySelf<ISscWorkCenterBusiScopeService>{
	/**
	 *
	 *Param workCenterId
	 * @return SscWorkCenterBusiScope
	 * @author bo.zhang 2019-03-15 19:24:36
	 */
	List<SscWorkCenterBusiScope> querySscWorkCenterBusiScope(IRequest iRequest,@Param("workCenterId") Long workCenterId);

}