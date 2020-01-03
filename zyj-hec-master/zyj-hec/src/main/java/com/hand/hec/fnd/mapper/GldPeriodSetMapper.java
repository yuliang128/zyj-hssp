package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldPeriodSet;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 会计期Mapper接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:42
 */
public interface GldPeriodSetMapper extends Mapper<GldPeriodSet> {

	/**
	 * 查询会计期总数
	 *
	 * @param periodSetId
	 * @author LJK 2019-01-15 18:04
	 * @return totalPeriodNum
	 */
	Long totalPeriodNumQuery(@Param("periodSetId") Long periodSetId);

	/**
	 * 查询名称附加
	 *
	 * @param periodSetId
	 * @author LJK 2019-01-15 18:04
	 * @return periodAdditionalFlag
	 */
	String periodAdditionalFlagQuery(@Param("periodSetId") Long periodSetId);

	/**
	 * 根据 期间代码 获取期间
	 * @param   periodSetCode periodSetCode
	 * @author rui.shi@hand-china.com 2019-03-22 17:07
	 * @return
	 */
	GldPeriodSet queryByPeriodSetCode(String periodSetCode);
}