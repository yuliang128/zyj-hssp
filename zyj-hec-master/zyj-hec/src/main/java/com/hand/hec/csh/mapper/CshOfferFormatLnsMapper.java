package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshOfferFormatLns;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshOfferFormatLnsMapper extends Mapper<CshOfferFormatLns>{

	/**
	 * 根据头id查出报盘文件导出格式定义行表
	 *
	 * @param
	 * @author bo.zhang 2019-03-08 17:42:26
	 * @return
	 */
	List<CshOfferFormatLns> queryCshOfferFormatLns(@Param("formatHdsId") Long formatHdsId);

	/**
	 * 根据头id查出报盘文件导出格式定义行表
	 *fixed后面数字的最大值
	 * @author bo.zhang05@hand-china.com 2019-03-08 15:22
	 * @return int
	 */
	Long querycolumnNameMax();

	/**
	 * 根据行id更新ColumnFormat的值
	 *@author bo.zhang05@hand-china.com 2019-03-08 15:22
	 */
	boolean updateColumnFormat(@Param("formatLnsId") Long formatLnsId);
}