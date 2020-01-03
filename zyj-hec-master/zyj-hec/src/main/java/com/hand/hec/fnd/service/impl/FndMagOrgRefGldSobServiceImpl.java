package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.hand.hec.fnd.dto.FndMagOrgRefBgtOrg;
import com.hand.hec.fnd.dto.FndMagOrgRefGldSob;
import com.hand.hec.fnd.service.IFndMagOrgRefGldSobService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * FndMagOrgRefGldSobServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndMagOrgRefGldSobServiceImpl extends BaseServiceImpl<FndMagOrgRefGldSob> implements IFndMagOrgRefGldSobService{

    @Override
    public boolean save(IRequest requestCtx, List<FndMagOrgRefGldSob> dto) {
        IBaseService<FndMagOrgRefGldSob> self = ((IBaseService<FndMagOrgRefGldSob>) AopContext.currentProxy());
            for(FndMagOrgRefGldSob magSob:dto){
            Long setOfBooksId = magSob.getSetOfBooksId();
            Long refId = magSob.getRefId();
            if(setOfBooksId == null&refId != null){
                //删掉默认账套
                self.deleteByPrimaryKey(magSob);
            }
            if(setOfBooksId !=null&refId == null){
                //新增的默认账套
                magSob.setDefaultFlag("Y");
                magSob.setEnabledFlag("Y");
                self.insertSelective(requestCtx,magSob);
            }
            if(setOfBooksId !=null&refId != null){
                //更新了默认账套
                self.updateByPrimaryKeySelective(requestCtx,magSob);
            }
        }
        return true;
    }
}