package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoTrxClsAsgnCom;

import java.util.List;

/**
 * <p>
 * 现金事物分类分配公司service接口
 * </p>
 * 
 * @author LJK 2019/02/19 12:10
 */
public interface ICshMoTrxClsAsgnComService
		extends IBaseService<CshMoTrxClsAsgnCom>, ProxySelf<ICshMoTrxClsAsgnComService> {

	/**
	 * 现金事务分类定义查询已分配公司
	 * @param request 请求
	 * @param magOrgId 管理组织id
	 * @param moCshTrxClassId 现金事务分类定义id
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author LJK 2019-02-21 12:32
	 * @return
	 */
	List<CshMoTrxClsAsgnCom> queryByTrxClassId(IRequest request, Long magOrgId, Long moCshTrxClassId, int pageNum,
			int pageSize);

	/**
	 * 现金事务分类定义查询未分配公司
	 * @param request 请求
	 * @param magOrgId 管理组织id
	 * @param moCshTrxClassId 现金事务分类定义id
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author LJK 2019-02-21 12:32
	 * @return
	 */
	List<CshMoTrxClsAsgnCom> queryComByTrxClassId(IRequest request, Long magOrgId, Long moCshTrxClassId, int pageNum,
			int pageSize);

}