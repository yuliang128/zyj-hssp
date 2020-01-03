package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hec.wfl.dto.WflInsTaskHierarchy;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.service.IWflInsTaskHierarchyService;
import com.hand.hec.wfl.service.IWflReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/28 \* Time: 16:43 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public abstract class WflReceiverBaseServiceImpl implements IWflReceiverService {

    @Autowired
    IWflInsTaskHierarchyService wflInsTaskHierarchyService;

    @Autowired
    IUserInfoService userInfoService;

    @Override
    public void insertReceiverHierarchy(IRequest context, WflInstance instance, WflInsTaskReceiver insTaskReceiver,
                    User user) {
        WflInsTaskHierarchy hierarchy = new WflInsTaskHierarchy();
        hierarchy.setApproverId(user.getUserId());
        hierarchy.setDisabledFlag("N");
        hierarchy.setInsReceiverId(insTaskReceiver.getInsReceiverId());
        hierarchy.setInstanceId(instance.getInstanceId());
        hierarchy.setInsTaskId(insTaskReceiver.getInsTaskId());
        hierarchy.setNote(null);
        hierarchy.setParentInsHierarchyId(null);
        hierarchy.setPostedFlag("N");
        hierarchy.setSequence(10L);
        wflInsTaskHierarchyService.insertSelective(context, hierarchy);
    }

    protected void generateHierarchyByEmpAssignList(IRequest context, WflInstance instance,
                    WflInsTaskReceiver insTaskReceiver, List<ExpEmployeeAssign> assignList) {
        for (ExpEmployeeAssign expEmployeeAssign : assignList) {
            User queryUser = new User();
            queryUser.setEmployeeId(expEmployeeAssign.getEmployeeId());
            queryUser.setStartActiveDate(new Date());
            queryUser.setEndActiveDate(new Date());
            List<User> userList = userInfoService.selectUserByEmployee(context, queryUser);

            for (User user : userList) {
                insertReceiverHierarchy(context, instance, insTaskReceiver, user);
            }
        }
    }
}
