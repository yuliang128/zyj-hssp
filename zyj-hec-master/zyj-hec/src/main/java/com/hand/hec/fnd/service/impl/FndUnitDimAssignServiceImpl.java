package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndUnitDimValueAssign;
import com.hand.hec.fnd.mapper.FndUnitDimAssignMapper;
import com.hand.hec.fnd.service.IFndUnitDimValueAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndUnitDimAssign;
import com.hand.hec.fnd.service.IFndUnitDimAssignService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndUnitDimAssignServiceImpl extends BaseServiceImpl<FndUnitDimAssign> implements IFndUnitDimAssignService {

    @Autowired
    private FndUnitDimAssignMapper mapper;

    @Autowired
    private IFndUnitDimValueAssignService unitDimValueAssignService;

    @Override
    public List<FndUnitDimAssign> queryByUnit(IRequest request, FndUnitDimAssign fndUnitDimAssign, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryByUnit(fndUnitDimAssign);
    }

    @Override
    public List<FndUnitDimAssign> batchUpdate(IRequest request, List<FndUnitDimAssign> list) {

        for (FndUnitDimAssign fndUnitDimAssign : list) {
            switch (fndUnitDimAssign.get__status()) {
                case DTOStatus.ADD:
                    this.insertUnitDim(request, fndUnitDimAssign);
                    break;
                case DTOStatus.UPDATE:
                    this.updateUnitDim(request, fndUnitDimAssign);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(fndUnitDimAssign);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    private FndUnitDimAssign insertUnitDim(IRequest request, FndUnitDimAssign fndUnitDimAssign) {
        FndUnitDimAssign unitDimAssign = self().insertSelective(request, fndUnitDimAssign);
        if (fndUnitDimAssign.getDefaultDimensionValueId() != null) {
            this.insertUnitDimValue(request,unitDimAssign);
        }
        return fndUnitDimAssign;
    }

    private FndUnitDimAssign updateUnitDim(IRequest request, FndUnitDimAssign fndUnitDimAssign) {
        boolean flag = false;
        FndUnitDimAssign unitDimAssign = self().selectByPrimaryKey(request, fndUnitDimAssign);
        if (!unitDimAssign.getDefaultDimensionValueId().equals(fndUnitDimAssign.getDefaultDimensionValueId())) {
            List<FndUnitDimValueAssign> unitDimValueAssigns = unitDimValueAssignService.select(request,
                            FndUnitDimValueAssign.builder().dimAssignId(fndUnitDimAssign.getAssignId()).build(), 0, 0);
            for (FndUnitDimValueAssign unitDimValueAssign : unitDimValueAssigns) {
                unitDimValueAssign.setDefaultFlag(BaseConstants.NO);
                if (unitDimValueAssign.getDimensionValueId().equals(fndUnitDimAssign.getDefaultDimensionValueId())) {
                    unitDimValueAssign.setDefaultFlag(BaseConstants.YES);
                    flag =  true;
                }
            }
            unitDimValueAssignService.batchUpdate(request,unitDimValueAssigns);
            self().updateByPrimaryKey(request,fndUnitDimAssign);
            if(!flag){
                this.insertUnitDimValue(request,fndUnitDimAssign);
            }
        }
        return fndUnitDimAssign;
    }

    private FndUnitDimValueAssign insertUnitDimValue(IRequest request,FndUnitDimAssign fndUnitDimAssign){
        FndUnitDimValueAssign newUnitDimValueAssign = new FndUnitDimValueAssign();
        newUnitDimValueAssign.setDimAssignId(fndUnitDimAssign.getAssignId());
        newUnitDimValueAssign.setUnitId(fndUnitDimAssign.getUnitId());
        newUnitDimValueAssign.setDimensionId(fndUnitDimAssign.getDimensionId());
        newUnitDimValueAssign.setDimensionValueId(fndUnitDimAssign.getDefaultDimensionValueId());
        newUnitDimValueAssign.setDefaultFlag(BaseConstants.YES);
        return unitDimValueAssignService.insertSelective(request, newUnitDimValueAssign);
    }
}
