package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkCenterCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkCenterCompanyService extends IBaseService<SscWorkCenterCompany>, ProxySelf<ISscWorkCenterCompanyService>{
	/**
	 *查询共享作业中心业务范围_公司范围
	 *Param scopeId
	 * @return SscWorkCenterCompany
	 * @author bo.zhang 2019-03-18 19:24:36
	 */
	List<SscWorkCenterCompany> selectSscWorkCenterCompany(IRequest iRequest,@Param("scopeId") Long scopeId);
}