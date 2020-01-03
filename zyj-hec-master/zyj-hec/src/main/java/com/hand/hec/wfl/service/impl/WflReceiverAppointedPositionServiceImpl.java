package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/28 \* Time: 16:43 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverAppointedPositionServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IExpEmployeeAssignService expEmployeeAssignsService;

    @Autowired
    IUserInfoService userInfoService;

    /**
     * 生成接收者
     * <p>
     * 指定岗位： 参数1：岗位的代码和ID 参数2：空 参数3：空 参数4：空
     *
     * @param context 运行上下文
     * @param instance 工作流实例
     * @param receiver 接收者
     * @param insTaskReceiver 工作流实例接收者
     */
    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        ExpEmployeeAssign queryAssign = new ExpEmployeeAssign();
        queryAssign.setPositionId(receiver.getReceiverParam1Id());
        queryAssign.setEnabledFlag("Y");
        List<ExpEmployeeAssign> assignList = expEmployeeAssignsService.select(context, queryAssign, 0, 0);

        generateHierarchyByEmpAssignList(context, instance, insTaskReceiver, assignList);
    }
}
