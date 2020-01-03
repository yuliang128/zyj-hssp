package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtCenterRefBgtEntity;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.mapper.BgtCenterRefBgtEntityMapper;
import com.hand.hec.bgt.service.IBgtCenterRefBgtEntityService;
import com.hand.hec.exp.dto.ExpMoEmpTypeAsgnCom;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算中心关联预算实体ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCenterRefBgtEntityServiceImpl extends BaseServiceImpl<BgtCenterRefBgtEntity> implements IBgtCenterRefBgtEntityService{

    @Autowired
    private BgtCenterRefBgtEntityMapper bgtCenterRefBgtEntityMapper;

    @Override
    public List<BgtCenterRefBgtEntity> queryMain(Long centerId, IRequest request,Integer page,Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return bgtCenterRefBgtEntityMapper.queryMain(centerId);
    }

    @Override
    public List<BgtCenterRefBgtEntity> queryEntityCanAsgn(Long centerId, Long bgtOrgId, IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return bgtCenterRefBgtEntityMapper.queryEntityCanAsgn(centerId,bgtOrgId);
    }

    @Override
    public Boolean batchSubmit(List<BgtCenter> bgtCenterList, IRequest request) {
        IBaseService<BgtCenterRefBgtEntity> self = ((IBaseService<BgtCenterRefBgtEntity>) AopContext.currentProxy());
        for(BgtCenter bgtCenter:bgtCenterList){
            List<BgtEntity> bgtEntityList = bgtCenter.getBgtEntityList();
            Long centerId = bgtCenter.getCenterId();
            for(BgtEntity bgtEntity:bgtEntityList){
                Long entityId = bgtEntity.getEntityId();
                BgtCenterRefBgtEntity bgtCenterRefBgtEntity = BgtCenterRefBgtEntity.builder().centerId(centerId).entityId(entityId).build();
                List<BgtCenterRefBgtEntity> list = mapper.select(bgtCenterRefBgtEntity);
                if(list.size() == 0){
                    //未分配过
                    bgtCenterRefBgtEntity.setEnabledFlag("Y");
                    self.insertSelective(request,bgtCenterRefBgtEntity);
                }
            }
        }
        return true;
    }
}