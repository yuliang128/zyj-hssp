package com.hand.hec.wfl.service.impl;

import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.service.IExpOrgUnitService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.wfl.service.IWflReceiverUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;

import java.util.HashMap;
import java.util.List;

/**
 * 预算使用部门主管
 *
 * @author mouse 2019/03/06 13:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverLineUnitManagerServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IWflReceiverUtilService utilService;

    @Autowired
    IExpOrgUnitService unitService;

    @Autowired
    IExpEmployeeAssignService assignService;

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        // 获取当前所有的LINE级别的DOC INFO
        List<FndDocInfo> lineDocInfoList = utilService.getLineDocInfo(instance);
        for (FndDocInfo lineDocInfo : lineDocInfoList) {
            // 获取当前DOC INFO的数据
            List<HashMap> lineDataList = utilService.getData(instance, lineDocInfo, instance.getDocId().toString());
            for (HashMap lineData : lineDataList) {
                // 如果当前行上的部门ID不为空，则取该部门的主管岗位
                if (lineData.get(FndDocInfo.COLUMN_UNIT_ID) != null) {
                    Long unitId = (Long) lineData.get(FndDocInfo.COLUMN_UNIT_ID);
                    ExpOrgUnit unit = new ExpOrgUnit();
                    unit.setUnitId(unitId);
                    unit.setEnabledFlag("Y");
                    unit = unitService.selectByPrimaryKey(context, unit);

                    if (unit != null && unit.getChiefPositionId() != null) {
                        ExpEmployeeAssign assign = new ExpEmployeeAssign();
                        assign.setPositionId(unit.getChiefPositionId());
                        assign.setEnabledFlag("Y");
                        List<ExpEmployeeAssign> assignList = assignService.select(context, assign, 0, 0);

                        generateHierarchyByEmpAssignList(context, instance, insTaskReceiver, assignList);
                    }
                }
            }
        }
    }
}
