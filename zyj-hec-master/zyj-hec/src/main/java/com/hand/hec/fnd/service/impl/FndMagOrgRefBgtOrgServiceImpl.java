package com.hand.hec.fnd.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndMagOrgRefBgtOrg;
import com.hand.hec.fnd.service.IFndMagOrgRefBgtOrgService;

/**
 * <p>
 * FndMagOrgRefBgtOrgServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 10:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndMagOrgRefBgtOrgServiceImpl extends BaseServiceImpl<FndMagOrgRefBgtOrg> implements IFndMagOrgRefBgtOrgService{

    @Override
    public boolean save(IRequest requestCtx, List<FndMagOrgRefBgtOrg> dto) {
        IBaseService<FndMagOrgRefBgtOrg> self = ((IBaseService<FndMagOrgRefBgtOrg>) AopContext.currentProxy());
        for(FndMagOrgRefBgtOrg magBgt:dto){
            Long bgtOrgId = magBgt.getBgtOrgId();
            Long refId = magBgt.getRefId();
            if(bgtOrgId == null&refId != null){
                //删掉默认预算组织
                self.deleteByPrimaryKey(magBgt);
            }
            if(bgtOrgId !=null&refId == null){
                //新增的默认预算组织
                magBgt.setDefaultFlag("Y");
                magBgt.setEnabledFlag("Y");
                self.insertSelective(requestCtx,magBgt);
            }
            if(bgtOrgId !=null&refId != null){
                //更新了默认预算组织
                self.updateByPrimaryKeySelective(requestCtx,magBgt);
            }
        }
        return true;
    }
}