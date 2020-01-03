package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.hr.dto.Position;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.wfl.exception.WflReceiverException;
import com.hand.hec.wfl.service.IWflReceiverUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 申请者上级的上级
 *
 * @author mouse 2019/03/06 13:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverGrandManagerServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IWflReceiverUtilService utilService;

    @Autowired
    IExpOrgPositionService positionService;

    @Autowired
    IExpEmployeeAssignService employeeAssignService;

    @Autowired
    IUserInfoService userInfoService;

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {

        List<HashMap> sumDataList = utilService.getSumData(instance);
        for (HashMap sumData : sumDataList) {
            Object positionIdObj = sumData.get(FndDocInfo.COLUMN_POSITION_ID);

            if (positionIdObj == null) {
                throw new WflReceiverException("WFL", "wfl_receiver.position_not_exists", null);
            }

            Long positionId = (Long) positionIdObj;

            // 获取当前岗位的上级岗位
            ExpOrgPosition position = new ExpOrgPosition();
            position.setPositionId(positionId);
            position.setEnabledFlag("Y");
            position = positionService.selectByPrimaryKey(context, position);

            // 如果当前岗位不为空且上级岗位不为空
            if (position != null && position.getParentPositionId() != null) {
                ExpOrgPosition parentPosition = new ExpOrgPosition();
                parentPosition.setPositionId(position.getParentPositionId());
                parentPosition.setEnabledFlag("Y");
                parentPosition = positionService.selectByPrimaryKey(context, parentPosition);

                // 如果上级岗位不为空且上级岗位的上级岗位不为空，则去找对应的assign
                if (parentPosition != null && parentPosition.getParentPositionId() != null) {
                    ExpEmployeeAssign queryAssign = new ExpEmployeeAssign();
                    queryAssign.setPositionId(parentPosition.getParentPositionId());
                    queryAssign.setEnabledFlag("Y");

                    List<ExpEmployeeAssign> assignList = employeeAssignService.select(context, queryAssign, 0, 0);

                    generateHierarchyByEmpAssignList(context, instance, insTaskReceiver, assignList);
                }
            }
        }
    }
}
