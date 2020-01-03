package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldPeriodRule;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 会计期间规则Mapper接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:42
 */
public interface GldPeriodRuleMapper extends Mapper<GldPeriodRule> {

	/**
	 * 根据会计期id + 期间名称数查询数量
	 *
	 * @param periodSetId 会计期id
	 * @param periodAdditionalName 期间名称附加
	 * @author LJK 2019-01-15 18:04
	 * @return count
	 */
	Long periodAdditionalNameQuery(@Param("periodSetId") Long periodSetId,
			@Param("periodAdditionalName") String periodAdditionalName);
}