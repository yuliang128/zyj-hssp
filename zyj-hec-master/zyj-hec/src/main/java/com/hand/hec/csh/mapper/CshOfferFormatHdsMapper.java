package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshOfferFormatHds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshOfferFormatHdsMapper extends Mapper<CshOfferFormatHds>{


	/**
	 * 查询出分隔符
	 *
	 * @param
	 * @author bo.zhang 2019-03-04 17:42:26
	 * @return
	 */
	List<CshOfferFormatHds> querySysCode();

	/**
	 * 查出报盘文件导出格式定义头表
	 *
	 * @param
	 * @author bo.zhang 2019-03-07 17:42:26
	 * @return
	 */
	List<CshOfferFormatHds> queryCshOfferFormatHds(@Param("formatCode") String formatCode,
												   @Param("description") String description);

}