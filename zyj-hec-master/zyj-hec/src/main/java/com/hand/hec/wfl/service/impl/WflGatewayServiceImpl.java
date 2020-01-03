package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflGatewayBizRuleGroup;
import com.hand.hec.wfl.mapper.WflGatewayMapper;
import com.hand.hec.wfl.service.IWflGatewayBizRuleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflGateway;
import com.hand.hec.wfl.service.IWflGatewayService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflGatewayServiceImpl extends BaseServiceImpl<WflGateway> implements IWflGatewayService {

    ThreadLocal<IRequest> requestLocal = new ThreadLocal<IRequest>();

    @Autowired
    WflGatewayMapper wflGatewayMapper;

    @Autowired
    IWflGatewayBizRuleGroupService groupService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public List<WflGateway> processGatewayQuery(Long processId, IRequest request) {
        return wflGatewayMapper.processGatewayQuery(processId, request);
    }

    @Override
    public int deleteByPrimaryKey(WflGateway gateway) {
        //
        // 删除网关之前先删除网关权限组
        // ------------------------------------------------------------------------------
        WflGatewayBizRuleGroup queryGroup = new WflGatewayBizRuleGroup();
        queryGroup.setGatewayId(gateway.getGatewayId());
        List<WflGatewayBizRuleGroup> groupList = groupService.select(getRequest(), queryGroup, 0, 0);
        if (groupList != null && groupList.size() != 0) {
            // 执行删除之前先设置request
            groupService.setRequest(getRequest());
            groupService.batchDelete(groupList);
        }

        return super.deleteByPrimaryKey(gateway);
    }

    @Override
    public void setRequest(IRequest request) {
        requestLocal.set(request);
    }

    @Override
    public IRequest getRequest() {
        return requestLocal.get();
    }
}
