package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.mapper.GldResponsibilityCenterMapper;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldResponsibilityCenterServiceImpl extends BaseServiceImpl<GldResponsibilityCenter>
                implements IGldResponsibilityCenterService {

    @Autowired
    GldResponsibilityCenterMapper gldResponsibilityCenterMapper;

	@Override
	protected boolean useSelectiveUpdate() {
		return false;
	}

	@Override
    public Boolean checkInTime(Date date, Long responsibilityCenterId) {
        Date startDateActive =
                        gldResponsibilityCenterMapper
                                        .select(GldResponsibilityCenter.builder()
                                                        .responsibilityCenterId(responsibilityCenterId).build())
                                        .get(0).getStartDateActive();
        Date endDateActive =
                        gldResponsibilityCenterMapper
                                        .select(GldResponsibilityCenter.builder()
                                                        .responsibilityCenterId(responsibilityCenterId).build())
                                        .get(0).getEndDateActive();
        if (date.after(startDateActive) && date.before(endDateActive)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取部门默认成本中心
     *
     * @author Tagin
     * @date 2019-04-01 17:04
     * @param unitId 部门ID
     * @param accEntityId 核算主体ID
     * @return java.util.List<com.hand.hec.gld.dto.GldResponsibilityCenter>
     * @version 1.0
     **/
    @Override
    public GldResponsibilityCenter getDefaultRespCenter(Long unitId, Long accEntityId) {
        List<GldResponsibilityCenter> gldResponsibilityCenterList =
                        gldResponsibilityCenterMapper.getDefaultRespCenter(unitId, accEntityId);
        if (CollectionUtils.isNotEmpty(gldResponsibilityCenterList)) {
            return gldResponsibilityCenterList.get(0);
        } else {
            return null;
        }
    }

}
