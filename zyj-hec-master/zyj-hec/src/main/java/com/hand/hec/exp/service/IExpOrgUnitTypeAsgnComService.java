package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpOrgUnitTypeAsgnCom;

import java.util.List;

/**
 * <p>
 * IExpOrgUnitTypeAsgnComService
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:20
 */
public interface IExpOrgUnitTypeAsgnComService extends IBaseService<ExpOrgUnitTypeAsgnCom>, ProxySelf<IExpOrgUnitTypeAsgnComService>{

	 /**
	  * 批量分配管理组织下的所有公司
	  * @param  list list
	  * @param  request request
	  * @author rui.shi@hand-china.com 2019-03-18 15:49
	  * @return List<ExpOrgUnitTypeAsgnCom>
	  */
	List<ExpOrgUnitTypeAsgnCom> batchAssignComAll(IRequest request, List<ExpOrgUnitTypeAsgnCom> list);

}