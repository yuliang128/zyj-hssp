package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflGatewayBizRuleDetail;
import com.hand.hec.wfl.service.IWflGatewayBizRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflGatewayBizRuleGroup;
import com.hand.hec.wfl.service.IWflGatewayBizRuleGroupService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflGatewayBizRuleGroupServiceImpl extends BaseServiceImpl<WflGatewayBizRuleGroup>
        implements IWflGatewayBizRuleGroupService {

    ThreadLocal<IRequest> requestLocal = new ThreadLocal<IRequest>();

    @Autowired
    IWflGatewayBizRuleDetailService detailService;

    @Override
    public void setRequest(IRequest request) {
        requestLocal.set(request);
    }

    @Override
    public IRequest getRequest() {
        return requestLocal.get();
    }


    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public int deleteByPrimaryKey(WflGatewayBizRuleGroup group) {
        //
        // 删除网关权限组之前先删除下属的权限规则明细
        // ------------------------------------------------------------------------------
        WflGatewayBizRuleDetail queryDetail = new WflGatewayBizRuleDetail();
        queryDetail.setGroupId(group.getGroupId());
        List<WflGatewayBizRuleDetail> detailList = detailService.select(getRequest(), queryDetail, 0, 0);
        if (detailList != null && detailList.size() != 0) {
            detailService.batchDelete(detailList);
        }


        return super.deleteByPrimaryKey(group);
    }
}
