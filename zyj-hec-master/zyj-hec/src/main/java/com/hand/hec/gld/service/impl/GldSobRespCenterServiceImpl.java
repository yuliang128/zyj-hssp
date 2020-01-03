package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.mapper.GldResponsibilityCenterMapper;
import com.hand.hec.gld.mapper.GldSobRespCenterMapper;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSobRespCenter;
import com.hand.hec.gld.service.IGldSobRespCenterService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 账套级成本中心定义ServiceImpl
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRespCenterServiceImpl extends BaseServiceImpl<GldSobRespCenter> implements IGldSobRespCenterService {
    @Autowired
    private GldSobRespCenterMapper gldSobRespCenterMapper;
    @Autowired
    private IGldResponsibilityCenterService responsibilityCenterService;
    @Autowired
    GldResponsibilityCenterMapper gldResponsibilityCenterMapper;
    @Override
    public List<GldResponsibilityCenter> selectAccountingEntity(IRequest request, GldResponsibilityCenter condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return gldSobRespCenterMapper.selectAccountingEntity(condition);
    }

    @Override
    public List<GldAccountingEntity> queryAccountingEntity(IRequest request, GldSobRespCenter center, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return gldSobRespCenterMapper.queryAccountingEntity(center);
    }

    @Override
    public List<GldResponsibilityCenter> submitResponsibilityCenter(IRequest request, List<GldResponsibilityCenter> dto) {
        if (dto == null ||dto.isEmpty()) {
            return dto;
        }
        GldSobRespCenter sobRespCenter = new GldSobRespCenter();
        sobRespCenter.setResponsibilityCenterId(dto.get(0).getResponsibilityCenterId());
        sobRespCenter = gldSobRespCenterMapper.selectByPrimaryKey(sobRespCenter);
        List<GldResponsibilityCenter> centers = new ArrayList<>();
        for (GldResponsibilityCenter r : dto) {
            if(DTOStatus.ADD.equals(r.get__status())) {
                GldResponsibilityCenter center = new GldResponsibilityCenter(r.getAccEntityId(), sobRespCenter.getResponsibilityCenterCode(), sobRespCenter.getResponsibilityCenterName(), sobRespCenter.getRespCenterTypeCode(), r.getStartDateActive(), r.getEndDateActive(), sobRespCenter.getSummaryFlag(), r.getResponsibilityCenterId());
                center.set__id(r.get__id());
                center.set__status(r.get__status());
                centers.add(center);
            }else if(DTOStatus.UPDATE.equals(r.get__status())){
                gldResponsibilityCenterMapper.updateByAccEntityIdAndParentRespCenterId(r);
            }
        }
        return responsibilityCenterService.batchUpdate(request,centers);
    }

    @Override
    protected boolean useSelectiveUpdate(){
        return false;
    }
}