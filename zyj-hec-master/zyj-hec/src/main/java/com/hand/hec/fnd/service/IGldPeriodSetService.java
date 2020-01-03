package com.hand.hec.fnd.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldPeriodSet;
import com.hand.hec.fnd.exception.GldPeriodSetException;

import java.util.List;


/**
 * <p>
 * 会计期Service接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:47
 */
public interface IGldPeriodSetService extends IBaseService<GldPeriodSet>, ProxySelf<IGldPeriodSetService> {

	/**
	 * 会计期删除校验
	 *
	 * @param dto 会计期实体
	 * @author LJK 2019-01-15 18:35
	 * @throws GldPeriodSetException exception
	 * @return
	 */
	void periodSetDeleteCheck(List<GldPeriodSet> dto) throws GldPeriodSetException;


	 /**
	  * 根据 期间代码 获取期间
	  * @param   periodSetCode periodSetCode
	  * @author rui.shi@hand-china.com 2019-03-22 17:07
	  * @return
	  */
	GldPeriodSet queryByPeriodSetCode(String periodSetCode);

}