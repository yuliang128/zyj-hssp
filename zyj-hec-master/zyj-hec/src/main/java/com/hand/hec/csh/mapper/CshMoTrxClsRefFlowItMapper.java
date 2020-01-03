package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoTrxClsRefFlowIt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 现金事物关联现金流量项mapper
 * </p>
 *
 * @author LJK 2019/02/19 12:06
 */
public interface CshMoTrxClsRefFlowItMapper extends Mapper<CshMoTrxClsRefFlowIt>{

	/**
	 * 根据现金事务分类定义id查询现金事物关联现金流量项
	 *
	 * @param moCshTrxClassId 现金事务分类定义id
	 * @author LJK 2019-02-21 17:49
	 * @return
	 */
	List<CshMoTrxClsRefFlowIt> queryByTrxClassId(@Param("moCshTrxClassId")Long moCshTrxClassId);

	/**
	 * 校验同一账套下只能有一个默认现金流量项
	 *
	 * @param moCshTrxClassId 现金事务分类定义id
	 * @param cashFlowItemId 现金流量项id
	 * @param refId 主键
	 * @author LJK 2019-04-03 16:04
	 * @return Integer
	 */
	Integer checkDftFlowItem(@Param("moCshTrxClassId") Long moCshTrxClassId,
			@Param("cashFlowItemId") Long cashFlowItemId, @Param("refId") Long refId);
}