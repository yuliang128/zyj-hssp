package com.hand.hec.wfl.service.impl;

import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
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
 * 申请者上级公司主管
 *
 * @author mouse 2019/03/06 13:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverParentCompanyManagerServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IWflReceiverUtilService utilService;

    @Autowired
    IExpEmployeeAssignService assignService;

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        HashMap headData = utilService.getHeadData(instance);

        if (headData.get(FndDocInfo.COLUMN_COMPANY_ID) != null) {
            FndCompany company = new FndCompany();
            company.setCompanyId((Long) headData.get(FndDocInfo.COLUMN_COMPANY_ID));
            company = companyService.selectByPrimaryKey(context, company);

            if (company != null && company.getParentCompanyId() != null) {
                FndCompany parentCompany = new FndCompany();
                parentCompany.setCompanyId(company.getParentCompanyId());
                parentCompany = companyService.selectByPrimaryKey(context, parentCompany);

                if (parentCompany != null && parentCompany.getChiefPositionId() != null) {
                    ExpEmployeeAssign assign = new ExpEmployeeAssign();
                    assign.setPositionId(parentCompany.getChiefPositionId());
                    assign.setEnabledFlag("Y");
                    List<ExpEmployeeAssign> assignList = assignService.select(context, assign, 0, 0);

                    generateHierarchyByEmpAssignList(context, instance, insTaskReceiver, assignList);
                }
            }
        }
    }
}
