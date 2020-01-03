package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtEntityPeriodStatusMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtEntityPeriodStatus;
import com.hand.hec.bgt.service.IBgtEntityPeriodStatusService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtEntityPeriodStatusServiceImpl extends BaseServiceImpl<BgtEntityPeriodStatus>
                implements IBgtEntityPeriodStatusService {
    @Autowired
    private BgtEntityPeriodStatusMapper entityPeriodStatusMapper;

    @Override
    public List<BgtEntityPeriodStatus> queryBgtEntityOpenPeriod(IRequest request,
                    BgtEntityPeriodStatus bgtEntityPeriodStatus) {
        return entityPeriodStatusMapper.queryBgtEntityOpenPeriod(bgtEntityPeriodStatus);
    }

    @Override
    public List<BgtEntityPeriodStatus> queryBgtEntityClosedPeriod(IRequest request,
                    BgtEntityPeriodStatus bgtEntityPeriodStatus) {
        return entityPeriodStatusMapper.queryBgtEntityClosedPeriod(bgtEntityPeriodStatus);
    }

    @Override
    public void closeEntityPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus) {
        int num = entityPeriodStatusMapper.selectCount(bgtEntityPeriodStatus);
        if(num > 0){
            bgtEntityPeriodStatus.setBgtPeriodStatusCode(BgtEntityPeriodStatus.PERIOD_CLOSED_STATUS);
            self().updateEntityPeriod(request,bgtEntityPeriodStatus);
        }

    }

    @Override
    public void updateEntityPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus) {
        entityPeriodStatusMapper.updateEntityPeriod(bgtEntityPeriodStatus);
    }

    @Override
    public void openEntityPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus) {
        int num = entityPeriodStatusMapper.selectCount(bgtEntityPeriodStatus);
        if(num >0){
            bgtEntityPeriodStatus.setBgtPeriodStatusCode(BgtEntityPeriodStatus.PERIOD_OPENED_STATUS);
            self().updateEntityPeriod(request,bgtEntityPeriodStatus);
        }else{
            bgtEntityPeriodStatus.setBgtPeriodStatusCode(BgtEntityPeriodStatus.PERIOD_OPENED_STATUS);
            self().insertSelective(request,bgtEntityPeriodStatus);
        }
    }
}
