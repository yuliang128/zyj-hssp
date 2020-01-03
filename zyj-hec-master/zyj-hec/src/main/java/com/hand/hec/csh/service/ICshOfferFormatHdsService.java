package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshOfferFormatHds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICshOfferFormatHdsService extends IBaseService<CshOfferFormatHds>, ProxySelf<ICshOfferFormatHdsService>{

	/**
	 * 查询出分隔符
	 *
	 * @param
	 * @author bo.zhang 2019-03-04 17:42:26
	 * @return
	 */
	List<CshOfferFormatHds> querySysCode(IRequest iRequest);

	/**
	 * 查出报盘文件导出格式定义头表
	 *
	 * @param
	 * @author bo.zhang 2019-03-07 17:42:26
	 * @return
	 */
	List<CshOfferFormatHds> queryCshOfferFormatHds(@Param("formatCode") String formatCode,
												   @Param("description") String description,IRequest iRequest);
}