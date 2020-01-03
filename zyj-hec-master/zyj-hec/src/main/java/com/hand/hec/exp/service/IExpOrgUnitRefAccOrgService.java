package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpOrgUnitRefAccOrg;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;

import java.util.List;

/**
 * <p>
 * IExpOrgUnitRefAccOrgService
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:20
 */
public interface IExpOrgUnitRefAccOrgService
                extends IBaseService<ExpOrgUnitRefAccOrg>, ProxySelf<IExpOrgUnitRefAccOrgService> {
    /**
     *
     * 批量提交部门核算实体分配
     *
     * @param request
     * @param list
     * @author guiyu 2019-01-25 14:55
     * @return
     */
    List<ExpOrgUnitRefAccOrg> batchSubmit(IRequest request, List<ExpOrgUnitRefAccOrg> list)
                    throws UnitAccOrBgtDfMultiException;

    /**
     * 查询该部门默认核算主体个数
     *
     * @param refAccOrg
     * @author guiyu 2019-01-25 14:57
     * @return 该部门默认核算主体的数量
     */
    int checkUnitAccDefault(ExpOrgUnitRefAccOrg refAccOrg);
}
