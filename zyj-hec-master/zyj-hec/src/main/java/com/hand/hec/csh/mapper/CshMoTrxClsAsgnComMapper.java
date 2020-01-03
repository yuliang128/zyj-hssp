package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoTrxClsAsgnCom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 现金事物分类分配公司mapper
 * </p>
 *
 * @author LJK 2019/02/19 12:06
 */
public interface CshMoTrxClsAsgnComMapper extends Mapper<CshMoTrxClsAsgnCom>{

	/**
	 * 根据现金事务分类定义Id查询已分配公司
	 *
	 * @param magOrgId 管理组织id
	 * @param moCshTrxClassId 现金事务分类定义Id
	 * @author LJK 2019-02-21 12:08
	 * @return
	 */
	List<CshMoTrxClsAsgnCom> queryByTrxClassId(@Param("magOrgId") Long magOrgId,@Param("moCshTrxClassId")Long moCshTrxClassId);

	/**
	 * 根据现金事务分类定义Id查询未分配公司
	 *
	 * @param magOrgId 管理组织id
	 * @param moCshTrxClassId 现金事务分类定义Id
	 * @author LJK 2019-02-21 12:08
	 * @return
	 */
	List<CshMoTrxClsAsgnCom> queryComByTrxClassId(@Param("magOrgId") Long magOrgId,@Param("moCshTrxClassId")Long moCshTrxClassId);
}