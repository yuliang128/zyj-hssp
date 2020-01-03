package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndUnitDimAssign;
import com.hand.hec.fnd.exception.FndUnitValueAssignException;
import com.hand.hec.fnd.mapper.FndUnitDimValueAssignMapper;
import com.hand.hec.fnd.service.IFndUnitDimAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndUnitDimValueAssign;
import com.hand.hec.fnd.service.IFndUnitDimValueAssignService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndUnitDimValueAssignServiceImpl extends BaseServiceImpl<FndUnitDimValueAssign>
                implements IFndUnitDimValueAssignService {

    @Autowired
    private FndUnitDimValueAssignMapper unitDimValueAssignMapper;

    @Autowired
    private IFndUnitDimAssignService unitDimAssignService;

    @Override
    public List<FndUnitDimValueAssign> queryByDimAssignId(IRequest request,
                    FndUnitDimValueAssign fndUnitDimValueAssign) {
        return unitDimValueAssignMapper.queryByDimAssignId(fndUnitDimValueAssign);
    }

    @Override
    public List<FndUnitDimValueAssign> batchSubmit(IRequest request, List<FndUnitDimValueAssign> list)
                    throws FndUnitValueAssignException {
        for (FndUnitDimValueAssign fndUnitDimValueAssign : list) {
            switch (fndUnitDimValueAssign.get__status()) {
                case DTOStatus.ADD:
                    this.insertUnitDimValueAssign(request, fndUnitDimValueAssign);
                    break;
                case DTOStatus.UPDATE:
                    this.updateUnitDimValueAssign(request, fndUnitDimValueAssign);
                    break;
                case DTOStatus.DELETE:
                    this.deleteUnitDimValueAssign(fndUnitDimValueAssign);
                    break;
                default:
                    break;

            }

        }
        return list;
    }

    private FndUnitDimValueAssign insertUnitDimValueAssign(IRequest request,
                    FndUnitDimValueAssign fndUnitDimValueAssign) throws FndUnitValueAssignException {
        int num = unitDimValueAssignMapper
                        .selectCount(FndUnitDimValueAssign.builder().dimAssignId(fndUnitDimValueAssign.getDimAssignId())
                                        .dimensionValueId(fndUnitDimValueAssign.getDimensionValueId()).build());
        if (num > 0) {
            throw new FndUnitValueAssignException(FndUnitValueAssignException.INSERT_FND_UNIT_DIM_VALUE_ASSIGN_ERROR,
                            null);
        }
        self().insertSelective(request, fndUnitDimValueAssign);
        return fndUnitDimValueAssign;
    }

    private FndUnitDimValueAssign updateUnitDimValueAssign(IRequest request,
                    FndUnitDimValueAssign fndUnitDimValueAssign) throws FndUnitValueAssignException {
        List<FndUnitDimValueAssign> valueAssigns = unitDimValueAssignMapper
                        .select(FndUnitDimValueAssign.builder().dimAssignId(fndUnitDimValueAssign.getDimAssignId())
                                        .dimensionValueId(fndUnitDimValueAssign.getDimensionValueId()).build());
        for (FndUnitDimValueAssign valueAssign : valueAssigns) {
            if (!valueAssign.getAssignId().equals(fndUnitDimValueAssign.getAssignId())) {
                throw new FndUnitValueAssignException(
                                FndUnitValueAssignException.INSERT_FND_UNIT_DIM_VALUE_ASSIGN_ERROR, null);
            }
        }
        if (BaseConstants.YES.equals(fndUnitDimValueAssign.getDefaultFlag())) {
            unitDimAssignService.updateByPrimaryKeySelective(request,
                            FndUnitDimAssign.builder().assignId(fndUnitDimValueAssign.getDimAssignId())
                                            .defaultDimensionValueId(fndUnitDimValueAssign.getDimensionValueId())
                                            .build());
        }
        self().updateByPrimaryKey(request, fndUnitDimValueAssign);

        return fndUnitDimValueAssign;
    }

    private FndUnitDimValueAssign deleteUnitDimValueAssign(FndUnitDimValueAssign fndUnitDimValueAssign)
                    throws FndUnitValueAssignException {
        if (BaseConstants.YES.equals(fndUnitDimValueAssign.getDefaultFlag())) {
            throw new FndUnitValueAssignException(FndUnitValueAssignException.DELETE_FND_UNIT_DIM_VALUE_ASSIGN_ERROR,
                            null);
        }
        self().deleteByPrimaryKey(fndUnitDimValueAssign);
        return fndUnitDimValueAssign;
    }
}
