package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;

import java.util.List;

/**
 * <p>
 * IExpOrgUnitRefBgtOrgService
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:20
 */
public interface IExpOrgUnitRefBgtOrgService
                extends IBaseService<ExpOrgUnitRefBgtOrg>, ProxySelf<IExpOrgUnitRefBgtOrgService> {
    /**
     * 
     * 批量提交部门预算实体分配
     * 
     * @param request
     * @param list
     * @author guiyu 2019-01-25 14:55
     * @return
     */
    List<ExpOrgUnitRefBgtOrg> batchSubmit(IRequest request, List<ExpOrgUnitRefBgtOrg> list)
                    throws UnitAccOrBgtDfMultiException;

    /**
     * 查询该部门默认预算主体个数
     *
     * @param refBgtOrg
     * @author guiyu 2019-01-25 14:57
     * @return 该部门默认预算主体的数量
     */
    int checkUnitBgtDefault(ExpOrgUnitRefBgtOrg refBgtOrg);

    /**
     * 根据部门ID和预算实体ID获取默认的部门、预算中心关联
     *
     * @param unitId
     * @param bgtEntityId
     * @author mouse 2019-03-14 16:54
     * @return com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg
     */
    ExpOrgUnitRefBgtOrg getDefaultRef(IRequest request,Long unitId,Long bgtEntityId);
}
