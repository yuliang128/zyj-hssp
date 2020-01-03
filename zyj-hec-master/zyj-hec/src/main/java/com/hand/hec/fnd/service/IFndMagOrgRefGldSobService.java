package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndMagOrgRefBgtOrg;
import com.hand.hec.fnd.dto.FndMagOrgRefGldSob;
/**
 * <p>
 * IFndMagOrgRefGldSobService
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:10
 */
public interface IFndMagOrgRefGldSobService extends IBaseService<FndMagOrgRefGldSob>, ProxySelf<IFndMagOrgRefGldSobService>{

    /**
     * 管理组织与账套-存储关联关系,根据参数来判断表中是否有，从而新增或更新或删除
     *
     * @param dto
     * @author ngls.luhui 2019-02-14 17:23
     * @return boolean
     */
     boolean save(IRequest requestCtx, List<FndMagOrgRefGldSob> dto);

}