package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import com.hand.hec.wfl.service.IWflReceiverUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 归口负责主管领导
 *
 * @author mouse 2019/03/06 13:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverAppointedJobServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IWflReceiverUtilService receiverUtilService;

    @Autowired
    IExpEmployeeAssignService assignService;

    @Autowired
    IUserService userService;

    @Autowired
    IUserInfoService userInfoService;

    /**
     * 生成接受者
     *
     * @param context
     * @param instance
     * @param receiver
     * @param insTaskReceiver
     * @author mouse 2019-03-06 18:12
     * @return void
     */
    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        Long jobId = receiver.getReceiverParam1Id();
        ExpEmployeeAssign queryAssign = new ExpEmployeeAssign();
        queryAssign.setEmployeeJobId(jobId);
        queryAssign.setEnabledFlag("Y");
        List<ExpEmployeeAssign> assignList = assignService.select(context, queryAssign, 0, 0);

        generateHierarchyByEmpAssignList(context, instance, insTaskReceiver, assignList);
    }
}
