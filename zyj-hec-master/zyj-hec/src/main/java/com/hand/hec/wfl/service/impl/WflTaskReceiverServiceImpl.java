package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflTaskRcvBizDetail;
import com.hand.hec.wfl.service.IWflTaskRcvBizDetailService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflTaskReceiver;
import com.hand.hec.wfl.service.IWflTaskReceiverService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflTaskReceiverServiceImpl extends BaseServiceImpl<WflTaskReceiver> implements IWflTaskReceiverService {

    ThreadLocal<IRequest> requestLocal = new ThreadLocal<IRequest>();

    @Autowired
    IWflTaskRcvBizDetailService bizDetailService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public int deleteByPrimaryKey(WflTaskReceiver record) {
        // 删除任务接受者，需要先删除对应的权限分配信息
        WflTaskRcvBizDetail detail = new WflTaskRcvBizDetail();
        detail.setReceiverId(record.getReceiverId());
        List<WflTaskRcvBizDetail> detailList = bizDetailService.select(getRequest(), detail, 0, 0);
        if(detailList != null && detailList.size() != 0){
            bizDetailService.batchDelete(detailList);
        }

        return super.deleteByPrimaryKey(record);
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
