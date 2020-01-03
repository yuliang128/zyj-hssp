package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.dto.BgtEntityPeriodStatus;
import com.hand.hec.bgt.mapper.BgtOrgPeriodStatusMapper;
import com.hand.hec.bgt.service.IBgtEntityPeriodStatusService;
import com.hand.hec.bgt.service.IBgtEntityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtOrgPeriodStatus;
import com.hand.hec.bgt.service.IBgtOrgPeriodStatusService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtOrgPeriodStatusServiceImpl extends BaseServiceImpl<BgtOrgPeriodStatus>
                implements IBgtOrgPeriodStatusService {
    @Autowired
    private BgtOrgPeriodStatusMapper orgPeriodStatusMapper;

    @Autowired
    private IBgtEntityService bgtEntityService;

    @Autowired
    private IBgtEntityPeriodStatusService entityPeriodStatusService;

    @Override
    public List<BgtOrgPeriodStatus> queryBgtOrgOpenPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus) {
        return orgPeriodStatusMapper.queryBgtOrgOpenPeriod(bgtOrgPeriodStatus);
    }

    @Override
    public List<BgtOrgPeriodStatus> queryBgtOrgClosedPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus) {
        return orgPeriodStatusMapper.queryBgtOrgClosedPeriod(bgtOrgPeriodStatus);
    }

    @Override
    public void closeOrgPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus) {
        // 关闭预算组织的期间
        int num = this.getOrgPeriodNum(bgtOrgPeriodStatus);
        if (num == 0) {
            bgtOrgPeriodStatus.setBgtPeriodStatusCode(BgtOrgPeriodStatus.PERIOD_CLOSED_STATUS);
            self().insertSelective(request, bgtOrgPeriodStatus);
        } else {
            bgtOrgPeriodStatus.setBgtPeriodStatusCode(BgtOrgPeriodStatus.PERIOD_CLOSED_STATUS);
            self().updateOrgPeriod(request, bgtOrgPeriodStatus);
        }
        // 关闭预算组织下预算组织的期间
        this.updateBgtEntityPeriod(request,bgtOrgPeriodStatus,BgtOrgPeriodStatus.CLOSE_PERIOD);
    }

    @Override
    public void updateOrgPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus) {
        orgPeriodStatusMapper.updateOrgPeriod(bgtOrgPeriodStatus);
    }

    @Override
    public void openOrgPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus) {
        // 打开预算组织的期间
        int num = this.getOrgPeriodNum(bgtOrgPeriodStatus);
        if (num > 0) {
            bgtOrgPeriodStatus.setBgtPeriodStatusCode(BgtOrgPeriodStatus.PERIOD_OPENED_STATUS);
            self().updateOrgPeriod(request, bgtOrgPeriodStatus);
        }
        // 打开预算组织下预算组织的期间
        this.updateBgtEntityPeriod(request,bgtOrgPeriodStatus,BgtOrgPeriodStatus.OPEN_PERIOD);
    }

    /**
     * 获取预算组织下所有的预算实体，对预算实体的预算期间进行操作
     *
     * @param bgtOrgPeriodStatus
     * @param action 开启期间或关闭期间
     * @author guiyuting 2019-03-15 14:46
     * @return 
     */
    private void updateBgtEntityPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus, String action) {
        List<BgtEntity> entityList = bgtEntityService.select(request,
                        BgtEntity.builder().bgtOrgId(bgtOrgPeriodStatus.getBgtOrgId()).build(), 0, 0);
        entityList.forEach(bgtEntity -> {
            BgtEntityPeriodStatus entityPeriodStatus = new BgtEntityPeriodStatus();
            BeanUtils.copyProperties(bgtOrgPeriodStatus,entityPeriodStatus);
            entityPeriodStatus.setBgtEntityId(bgtEntity.getEntityId());
            switch (action) {
                case BgtOrgPeriodStatus.OPEN_PERIOD:
                    entityPeriodStatusService.openEntityPeriod(request, entityPeriodStatus);
                    break;
                case BgtOrgPeriodStatus.CLOSE_PERIOD:
                    entityPeriodStatusService.closeEntityPeriod(request, entityPeriodStatus);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 根据预算组织ID，预算期间代码，预算期间年份，预算期间数查询期间状态书否存在
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-15 14:54
     * @return int 0表示不存在，1表示存在
     */
    private int getOrgPeriodNum(BgtOrgPeriodStatus bgtOrgPeriodStatus){
        BgtOrgPeriodStatus orgPeriodStatus = new BgtOrgPeriodStatus();
        orgPeriodStatus.setBgtOrgId(bgtOrgPeriodStatus.getBgtOrgId());
        orgPeriodStatus.setBgtPeriodSetCode(bgtOrgPeriodStatus.getBgtPeriodSetCode());
        orgPeriodStatus.setBgtPeriodYear(bgtOrgPeriodStatus.getBgtPeriodYear());
        orgPeriodStatus.setBgtPeriodNum(bgtOrgPeriodStatus.getBgtPeriodNum());
        return orgPeriodStatusMapper.selectCount(orgPeriodStatus);
    }
}
