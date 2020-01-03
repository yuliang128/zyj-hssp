package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndMagOrgRefGldSob;

/**
 * <p>
 * FndMagOrgRefGldSobMapper
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:07
 */
public interface FndMagOrgRefGldSobMapper extends Mapper<FndMagOrgRefGldSob>{


	/**
	 * 根据公司id查询默认账套id
	 * @param  companyId 公司id
	 * @author rui.shi@hand-china.com 2019-03-08 13:52
	 * @return
	 */
	Long queryDefaultSobIdByCompanyId(Long companyId);
}