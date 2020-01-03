package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtEntityPeriodStatus;
import com.hand.hec.bgt.dto.BgtOrgPeriodStatus;
import com.hand.hec.bgt.service.IBgtEntityPeriodStatusService;
import com.hand.hec.bgt.service.IBgtOrgPeriodStatusService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtPeriodStatus;
import com.hand.hec.bgt.service.IBgtPeriodStatusService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算期间状态ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtPeriodStatusServiceImpl extends BaseServiceImpl<BgtPeriodStatus> implements IBgtPeriodStatusService {
    @Autowired
    private IBgtEntityPeriodStatusService entityPeriodStatusService;

    @Autowired
    private IBgtOrgPeriodStatusService orgPeriodStatusService;

    @Override
    public List queryOpenPeriod(IRequest request, BgtPeriodStatus bgtPeriodStatus, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        if (bgtPeriodStatus.getBgtEntityId() != null) {
            BgtEntityPeriodStatus entityPeriodStatus = new BgtEntityPeriodStatus();
            BeanUtils.copyProperties(bgtPeriodStatus, entityPeriodStatus);
            return entityPeriodStatusService.queryBgtEntityOpenPeriod(request, entityPeriodStatus);
        }
        BgtOrgPeriodStatus orgPeriodStatus = new BgtOrgPeriodStatus();
        BeanUtils.copyProperties(bgtPeriodStatus, orgPeriodStatus);
        return orgPeriodStatusService.queryBgtOrgOpenPeriod(request, orgPeriodStatus);
    }

    @Override
    public List queryClosePeriod(IRequest request, BgtPeriodStatus bgtPeriodStatus, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        if (bgtPeriodStatus.getBgtEntityId() != null) {
            BgtEntityPeriodStatus entityPeriodStatus = new BgtEntityPeriodStatus();
            BeanUtils.copyProperties(bgtPeriodStatus, entityPeriodStatus);
            return entityPeriodStatusService.queryBgtEntityClosedPeriod(request, entityPeriodStatus);
        }
        BgtOrgPeriodStatus orgPeriodStatus = new BgtOrgPeriodStatus();
        BeanUtils.copyProperties(bgtPeriodStatus, orgPeriodStatus);
        return orgPeriodStatusService.queryBgtOrgClosedPeriod(request, orgPeriodStatus);
    }

    @Override
    public boolean closePeriod(IRequest request, BgtPeriodStatus bgtPeriodStatus) {
        if (bgtPeriodStatus.getBgtEntityId() != null) {
            BgtEntityPeriodStatus entityPeriodStatus = new BgtEntityPeriodStatus();
            BeanUtils.copyProperties(bgtPeriodStatus, entityPeriodStatus);
            entityPeriodStatusService.closeEntityPeriod(request, entityPeriodStatus);
        } else {
            BgtOrgPeriodStatus orgPeriodStatus = new BgtOrgPeriodStatus();
            BeanUtils.copyProperties(bgtPeriodStatus, orgPeriodStatus);
            orgPeriodStatusService.closeOrgPeriod(request, orgPeriodStatus);
        }
        return true;
    }

    @Override
    public boolean openPeriod(IRequest request, BgtPeriodStatus bgtPeriodStatus) {
        if (bgtPeriodStatus.getBgtEntityId() != null) {
            BgtEntityPeriodStatus entityPeriodStatus = new BgtEntityPeriodStatus();
            BeanUtils.copyProperties(bgtPeriodStatus, entityPeriodStatus);
            entityPeriodStatusService.openEntityPeriod(request, entityPeriodStatus);
        } else {
            BgtOrgPeriodStatus orgPeriodStatus = new BgtOrgPeriodStatus();
            BeanUtils.copyProperties(bgtPeriodStatus, orgPeriodStatus);
            orgPeriodStatusService.openOrgPeriod(request, orgPeriodStatus);
        }
        return true;
    }
}
