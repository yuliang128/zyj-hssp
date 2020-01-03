package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import com.hand.hec.wfl.exception.WflReceiverException;
import com.hand.hec.wfl.service.IWflReceiverUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 申请者公司主管
 *
 * @author mouse 2019/03/06 13:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverCompanyManagerServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IWflReceiverUtilService utilService;

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IExpEmployeeAssignService employeeAssignService;

    @Autowired
    IUserInfoService userInfoService;

    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        List<HashMap> sumDataList = utilService.getSumData(instance);
        for (HashMap sumData : sumDataList) {
            Object companyIdObj = sumData.get(FndDocInfo.COLUMN_COMPANY_ID);
            if (companyIdObj == null) {
                throw new WflReceiverException("WFL", "wfl_receiver.company_not_exists", null);
            }

            Long companyId = (Long) companyIdObj;

            FndCompany company = new FndCompany();
            company.setCompanyId(companyId);
            company = companyService.selectByPrimaryKey(context, company);

            if (company.getChiefPositionId() != null) {
                ExpEmployeeAssign queryAssign = new ExpEmployeeAssign();
                queryAssign.setPositionId(company.getChiefPositionId());
                queryAssign.setEnabledFlag("Y");

                List<ExpEmployeeAssign> assignList = employeeAssignService.select(context, queryAssign, 0, 0);

                generateHierarchyByEmpAssignList(context, instance, insTaskReceiver, assignList);
            }
        }
    }
}
