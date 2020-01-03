package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndMagOrgRefBgtOrg;
/**
 * <p>
 * IFndMagOrgRefBgtOrgService
 * </p>
 *
 * @author yang.duan 2019/01/10 10:54
 */
public interface IFndMagOrgRefBgtOrgService extends IBaseService<FndMagOrgRefBgtOrg>, ProxySelf<IFndMagOrgRefBgtOrgService>{

    /**
     * 管理组织与预算组织-存储关联关系,根据参数来判断表中是否有，从而新增或更新或删除
     *
     * @param dto
     * @author ngls.luhui 2019-02-14 17:23
     * @return boolean
     */
    boolean save(IRequest requestCtx, List<FndMagOrgRefBgtOrg> dto);
}