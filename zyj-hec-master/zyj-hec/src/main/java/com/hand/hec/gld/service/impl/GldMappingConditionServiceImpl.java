package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldMappingCondGrpHd;
import com.hand.hec.gld.dto.GldMappingCondGrpLn;
import com.hand.hec.gld.dto.GldMappingCondition;
import com.hand.hec.gld.mapper.GldMappingCondGrpHdMapper;
import com.hand.hec.gld.mapper.GldMappingCondGrpLnMapper;
import com.hand.hec.gld.mapper.GldMappingConditionMapper;
import com.hand.hec.gld.service.IGldMappingConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldMappingConditionServiceImpl extends BaseServiceImpl<GldMappingCondition>
                implements IGldMappingConditionService {
    @Autowired
    private GldMappingConditionMapper gldMappingConditionMapper;

    @Autowired
    private GldMappingCondGrpHdMapper gldMappingCondGrpHdMapper;

    @Autowired
    private GldMappingCondGrpLnMapper gldMappingCondGrpLnMapper;

    @Override
    public List<GldMappingCondition> accBuilderEmployeeExp(String magOrgId, String companyId, String positionId,
                                                           String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                                                           String currencyCode, String expenseItemId, String expenseTypeId, String periodComparison,
                                                           String employeeTypeId, String orgUnitId, String orgUnitTypeId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("MANAGING_ORG");
        gldMappingCondition.setMappingConditionValue(magOrgId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition1.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition2.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition3.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition4.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EMPLOYEE");
        gldMappingCondition5.setMappingConditionValue(employeeId);
        gldMappingConditions.add(gldMappingCondition5);
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition6.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition6);
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("CURRENCY");
        gldMappingCondition7.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition7);
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("EMPLOYEE_EXPENSE_ITEM");
        gldMappingCondition8.setMappingConditionValue(expenseItemId);
        gldMappingConditions.add(gldMappingCondition8);
        GldMappingCondition gldMappingCondition9 = new GldMappingCondition();
        gldMappingCondition9.setMappingConditionCode("EMPLOYEE_EXPENSE_TYPE");
        gldMappingCondition9.setMappingConditionValue(expenseTypeId);
        gldMappingConditions.add(gldMappingCondition9);
        GldMappingCondition gldMappingCondition10 = new GldMappingCondition();
        gldMappingCondition10.setMappingConditionCode("PERIOD_COMPARISON");
        gldMappingCondition10.setMappingConditionValue(periodComparison);
        gldMappingConditions.add(gldMappingCondition10);
        GldMappingCondition gldMappingCondition11 = new GldMappingCondition();
        gldMappingCondition11.setMappingConditionCode("EMPLOYEE_TYPE");
        gldMappingCondition11.setMappingConditionValue(employeeTypeId);
        gldMappingConditions.add(gldMappingCondition11);
        GldMappingCondition gldMappingCondition12 = new GldMappingCondition();
        gldMappingCondition12.setMappingConditionCode("ORG_UNIT");
        gldMappingCondition12.setMappingConditionValue(orgUnitId);
        gldMappingConditions.add(gldMappingCondition12);
        GldMappingCondition gldMappingCondition13 = new GldMappingCondition();
        gldMappingCondition13.setMappingConditionCode("ORG_UNIT_TYPE");
        gldMappingCondition13.setMappingConditionValue(orgUnitTypeId);
        gldMappingConditions.add(gldMappingCondition13);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderEmployeeExpTax(String companyId, String positionId, String accEntityId,
                                                              String respCenterId, String expenseReportTypeId, String taxTypeId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition1.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition2.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition3.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition4.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("TAX");
        gldMappingCondition5.setMappingConditionValue(taxTypeId);
        gldMappingConditions.add(gldMappingCondition5);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderTaxTransferOut(String invoiceItemId, String invoiceUsedeId,
                                                              String taxTypeId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("INVOICE_ITEM");
        gldMappingCondition.setMappingConditionValue(invoiceItemId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("INVOICE_USEDE");
        gldMappingCondition1.setMappingConditionValue(invoiceUsedeId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("TAX");
        gldMappingCondition2.setMappingConditionValue(taxTypeId);
        gldMappingConditions.add(gldMappingCondition2);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderExpClearing(String magOrgId, String companyId, String positionId,
                                                           String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                                                           String currencyCode, String employeeTypeId, String partnerCategory, String partnerTypeId,
                                                           String partnerId, String usedesId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("MANAGING_ORG");
        gldMappingCondition.setMappingConditionValue(magOrgId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition1.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition2.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition3.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition4.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EMPLOYEE");
        gldMappingCondition5.setMappingConditionValue(employeeId);
        gldMappingConditions.add(gldMappingCondition5);
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("EMPLOYEE_TYPE");
        gldMappingCondition6.setMappingConditionValue(employeeTypeId);
        gldMappingConditions.add(gldMappingCondition6);
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition7.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition7);
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("CURRENCY");
        gldMappingCondition8.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition8);
        GldMappingCondition gldMappingCondition9 = new GldMappingCondition();
        gldMappingCondition9.setMappingConditionCode("PARTNER_CATEGORY");
        gldMappingCondition9.setMappingConditionValue(partnerCategory);
        gldMappingConditions.add(gldMappingCondition9);
        GldMappingCondition gldMappingCondition10 = new GldMappingCondition();
        gldMappingCondition10.setMappingConditionCode("PARTNER_TYPE");
        gldMappingCondition10.setMappingConditionValue(partnerTypeId);
        gldMappingConditions.add(gldMappingCondition10);
        GldMappingCondition gldMappingCondition11 = new GldMappingCondition();
        gldMappingCondition11.setMappingConditionCode("PARTNER");
        gldMappingCondition11.setMappingConditionValue(partnerId);
        gldMappingConditions.add(gldMappingCondition11);
        GldMappingCondition gldMappingCondition12 = new GldMappingCondition();
        gldMappingCondition12.setMappingConditionCode("USEDES");
        gldMappingCondition12.setMappingConditionValue(usedesId);
        gldMappingConditions.add(gldMappingCondition12);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderExpClearTax(String magOrgId, String companyId, String positionId,
                                                           String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                                                           String currencyCode, String employeeTypeId, String partnerCategory, String partnerTypeId,
                                                           String partnerId, String usedesId, String taxTypeId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("MANAGING_ORG");
        gldMappingCondition.setMappingConditionValue(magOrgId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition1.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition2.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition3.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition4.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EMPLOYEE");
        gldMappingCondition5.setMappingConditionValue(employeeId);
        gldMappingConditions.add(gldMappingCondition5);
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("EMPLOYEE_TYPE");
        gldMappingCondition6.setMappingConditionValue(employeeTypeId);
        gldMappingConditions.add(gldMappingCondition6);
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition7.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition7);
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("CURRENCY");
        gldMappingCondition8.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition8);
        GldMappingCondition gldMappingCondition9 = new GldMappingCondition();
        gldMappingCondition9.setMappingConditionCode("PARTNER_CATEGORY");
        gldMappingCondition9.setMappingConditionValue(partnerCategory);
        gldMappingConditions.add(gldMappingCondition9);
        GldMappingCondition gldMappingCondition10 = new GldMappingCondition();
        gldMappingCondition10.setMappingConditionCode("PARTNER_TYPE");
        gldMappingCondition10.setMappingConditionValue(partnerTypeId);
        gldMappingConditions.add(gldMappingCondition10);
        GldMappingCondition gldMappingCondition11 = new GldMappingCondition();
        gldMappingCondition11.setMappingConditionCode("PARTNER");
        gldMappingCondition11.setMappingConditionValue(partnerId);
        gldMappingConditions.add(gldMappingCondition11);
        GldMappingCondition gldMappingCondition12 = new GldMappingCondition();
        gldMappingCondition12.setMappingConditionCode("USEDES");
        gldMappingCondition12.setMappingConditionValue(usedesId);
        gldMappingConditions.add(gldMappingCondition12);
        GldMappingCondition gldMappingCondition13 = new GldMappingCondition();
        gldMappingCondition13.setMappingConditionCode("TAX");
        gldMappingCondition13.setMappingConditionValue(taxTypeId);
        gldMappingConditions.add(gldMappingCondition13);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderEmpCompanyAr(String magOrgId, String companyId, String positionId,
                                                            String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                                                            String currencyCode, String expenseItemId, String expenseTypeId, String oppositeAccEntityId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("MANAGING_ORG");
        gldMappingCondition.setMappingConditionValue(magOrgId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition1.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition2.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition3.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition4.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EMPLOYEE");
        gldMappingCondition5.setMappingConditionValue(employeeId);
        gldMappingConditions.add(gldMappingCondition5);
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition6.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition6);
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("CURRENCY");
        gldMappingCondition7.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition7);
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("EMPLOYEE_EXPENSE_ITEM");
        gldMappingCondition8.setMappingConditionValue(expenseItemId);
        gldMappingConditions.add(gldMappingCondition8);
        GldMappingCondition gldMappingCondition9 = new GldMappingCondition();
        gldMappingCondition9.setMappingConditionCode("EMPLOYEE_EXPENSE_TYPE");
        gldMappingCondition9.setMappingConditionValue(expenseTypeId);
        gldMappingConditions.add(gldMappingCondition9);
        GldMappingCondition gldMappingCondition10 = new GldMappingCondition();
        gldMappingCondition10.setMappingConditionCode("OPPOSITE_ACC_ENTITY");
        gldMappingCondition10.setMappingConditionValue(oppositeAccEntityId);
        gldMappingConditions.add(gldMappingCondition10);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderEmpCompanyAp(String magOrgId, String companyId, String positionId,
                                                            String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                                                            String currencyCode, String expenseItemId, String expenseTypeId, String oppositeAccEntityId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("MANAGING_ORG");
        gldMappingCondition.setMappingConditionValue(magOrgId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition1.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition2.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition3.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition4.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EMPLOYEE");
        gldMappingCondition5.setMappingConditionValue(employeeId);
        gldMappingConditions.add(gldMappingCondition5);
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition6.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition6);
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("CURRENCY");
        gldMappingCondition7.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition7);
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("EMPLOYEE_EXPENSE_ITEM");
        gldMappingCondition8.setMappingConditionValue(expenseItemId);
        gldMappingConditions.add(gldMappingCondition8);
        GldMappingCondition gldMappingCondition9 = new GldMappingCondition();
        gldMappingCondition9.setMappingConditionCode("EMPLOYEE_EXPENSE_TYPE");
        gldMappingCondition9.setMappingConditionValue(expenseTypeId);
        gldMappingConditions.add(gldMappingCondition9);
        GldMappingCondition gldMappingCondition10 = new GldMappingCondition();
        gldMappingCondition10.setMappingConditionCode("OPPOSITE_ACC_ENTITY");
        gldMappingCondition10.setMappingConditionValue(oppositeAccEntityId);
        gldMappingConditions.add(gldMappingCondition10);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuilderInvoiceTax(String companyId, String positionId, String respCenterId,
                                                          String expenseReportTypeId, String taxTypeId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition1.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition2.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("EXPENSE_REPORT_TYPE");
        gldMappingCondition3.setMappingConditionValue(expenseReportTypeId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("TAX");
        gldMappingCondition4.setMappingConditionValue(taxTypeId);
        gldMappingConditions.add(gldMappingCondition4);
        return gldMappingConditions;
    }



    /**
     * 创建汇兑损益科目匹配组
     *
     * @Author Tagin
     * @Date 2019-02-25 14:06
     * @param companyId 管理公司ID
     * @param positionId 岗位ID
     * @param accEntityId 核算主体ID
     * @param respCenterId 成本中心ID
     * @param currencyCode 币种
     * @Return java.util.List<com.hand.hec.gld.dto.GldMappingCondition>
     * @Version 1.0
     **/
    @Override
    public List<GldMappingCondition> accBuilderRevaluation(String companyId, String positionId, String accEntityId,
                                                           String respCenterId, String currencyCode) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        // 管理公司
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        // 岗位
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition1.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition1);
        // 核算主体
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition2.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition2);
        // 成本中心
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition3.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition3);
        // 币种
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("CURRENCY");
        gldMappingCondition4.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition4);

        return gldMappingConditions;
    }

    /**
     * 创建现金内部往来应收科目
     *
     * @author Tagin
     * @date 2019-03-27 13:37
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @param oppositeAccEntityId 对方核算主体ID
     * @param respCenterId 成本中心ID
     * @param currencyCode 币种
     * @param positionId 岗位ID
     * @return java.util.List<com.hand.hec.gld.dto.GldMappingCondition>
     * @version 1.0
     **/
    @Override
    public List<GldMappingCondition> accBuilderCshInCompanyAr(String companyId, String accEntityId,
                                                              String oppositeAccEntityId, String respCenterId, String currencyCode, String positionId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        // 管理公司
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        // 核算主体
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition1.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition1);
        // 对方核算主体
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("OPPOSITE_ACC_ENTITY");
        gldMappingCondition2.setMappingConditionValue(oppositeAccEntityId);
        gldMappingConditions.add(gldMappingCondition2);
        // 成本中心
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition3.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition3);
        // 币种
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("CURRENCY");
        gldMappingCondition4.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition4);
        // 岗位
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition5.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition5);

        return gldMappingConditions;
    }

    /**
     * 创建现金内部往来应付科目
     *
     * @author Tagin
     * @date 2019-03-27 13:37
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @param oppositeAccEntityId 对方核算主体ID
     * @param respCenterId 成本中心ID
     * @param currencyCode 币种
     * @param positionId 岗位ID
     * @return java.util.List<com.hand.hec.gld.dto.GldMappingCondition>
     * @version 1.0
     **/
    @Override
    public List<GldMappingCondition> accBuilderCshInCompanyAp(String companyId, String accEntityId,
                                                              String oppositeAccEntityId, String respCenterId, String currencyCode, String positionId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        // 管理公司
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        // 核算主体
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition1.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition1);
        // 对方核算主体
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("OPPOSITE_ACC_ENTITY");
        gldMappingCondition2.setMappingConditionValue(oppositeAccEntityId);
        gldMappingConditions.add(gldMappingCondition2);
        // 成本中心
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition3.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition3);
        // 币种
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("CURRENCY");
        gldMappingCondition4.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition4);
        // 岗位
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition5.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition5);

        return gldMappingConditions;
    }

    /**
     * 创建预付款科目
     *
     * @author Tagin
     * @date 2019-04-01 15:52
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @param respCenterId 成本中心ID
     * @param currencyCode 币种
     * @param positionId 岗位ID
     * @param employeeId 员工ID
     * @param partnerCategory 交易对象类别
     * @param cshTransactionType 付款交易类型
     * @param employeeTypeId 员工类型ID
     * @param venderTypeId 供应商类型ID
     * @param venderId 供应商ID
     * @param moCshTrxClassId 事务分类ID
     * @param customerTypeId 客户类型ID
     * @param customerId 客户ID
     * @return java.util.List<com.hand.hec.gld.dto.GldMappingCondition>
     * @version 1.0
     **/
    @Override
    public List<GldMappingCondition> accBuilderPrepayment(String companyId, String accEntityId, String respCenterId,
                                                          String currencyCode, String positionId, String employeeId, String partnerCategory,
                                                          String cshTransactionType, String employeeTypeId, String venderTypeId, String venderId,
                                                          String moCshTrxClassId, String customerTypeId, String customerId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        // 管理公司
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        // 核算主体
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition1.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition1);
        // 成本中心
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition2.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition2);
        // 币种
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("CURRENCY");
        gldMappingCondition3.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition3);
        // 岗位
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition4.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition4);
        // 员工编号
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("EMPLOYEE");
        gldMappingCondition5.setMappingConditionValue(employeeId);
        gldMappingConditions.add(gldMappingCondition5);
        // 交易对象类别
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("PARTNER_CATEGORY");
        gldMappingCondition6.setMappingConditionValue(partnerCategory);
        gldMappingConditions.add(gldMappingCondition6);
        // 付款交易类型
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("CSH_TRANSACTION_TYPE");
        gldMappingCondition7.setMappingConditionValue(cshTransactionType);
        gldMappingConditions.add(gldMappingCondition7);
        // 员工类型
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("EMPLOYEE_TYPE");
        gldMappingCondition8.setMappingConditionValue(employeeTypeId);
        gldMappingConditions.add(gldMappingCondition8);
        // 供应商类型
        GldMappingCondition gldMappingCondition9 = new GldMappingCondition();
        gldMappingCondition9.setMappingConditionCode("VENDER_TYPE");
        gldMappingCondition9.setMappingConditionValue(venderTypeId);
        gldMappingConditions.add(gldMappingCondition9);
        // 供应商
        GldMappingCondition gldMappingCondition10 = new GldMappingCondition();
        gldMappingCondition10.setMappingConditionCode("VENDER");
        gldMappingCondition10.setMappingConditionValue(venderId);
        gldMappingConditions.add(gldMappingCondition10);
        // 事务分类
        GldMappingCondition gldMappingCondition11 = new GldMappingCondition();
        gldMappingCondition11.setMappingConditionCode("CSH_TRANSACTION_CLASS");
        gldMappingCondition11.setMappingConditionValue(moCshTrxClassId);
        gldMappingConditions.add(gldMappingCondition11);
        // 客户类型
        GldMappingCondition gldMappingCondition12 = new GldMappingCondition();
        gldMappingCondition12.setMappingConditionCode("CUSTOMER_TYPE");
        gldMappingCondition12.setMappingConditionValue(customerTypeId);
        gldMappingConditions.add(gldMappingCondition12);
        // 客户
        GldMappingCondition gldMappingCondition13 = new GldMappingCondition();
        gldMappingCondition13.setMappingConditionCode("CUSTOMER");
        gldMappingCondition13.setMappingConditionValue(customerId);
        gldMappingConditions.add(gldMappingCondition13);
        return gldMappingConditions;
    }

    /**
     * 创建付款单科目
     *
     * @author Tagin
     * @date 2019-04-30 16:19
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @param respCenterId 成本中心ID
     * @param positionId 岗位ID
     * @param partnerCategory 交易对象类别
     * @param partnerType 交易对象类型
     * @param paymentUsedeId 付款用途
     * @param moCshTrxClassId 事务分类ID
     * @return java.util.List<com.hand.hec.gld.dto.GldMappingCondition>
     * @version 1.0
     **/
    @Override
    public List<GldMappingCondition> accBuilderPayRequisition(String companyId, String accEntityId, String respCenterId,
                                                              String positionId, String partnerCategory, String partnerType, String paymentUsedeId,
                                                              String moCshTrxClassId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        // 管理公司
        gldMappingCondition.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition);
        // 核算主体
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition1.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition1);
        // 成本中心
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition2.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition2);
        // 岗位
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition3.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition3);
        // 交易对象类别
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("PARTNER_CATEGORY");
        gldMappingCondition4.setMappingConditionValue(partnerCategory);
        gldMappingConditions.add(gldMappingCondition4);
        // 交易对象类型
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("PARTNER_TYPE");
        gldMappingCondition5.setMappingConditionValue(partnerType);
        gldMappingConditions.add(gldMappingCondition5);
        // 付款交易类型
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("USEDES");
        gldMappingCondition6.setMappingConditionValue(paymentUsedeId);
        gldMappingConditions.add(gldMappingCondition6);
        // 事务分类
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("CSH_TRANSACTION_CLASS");
        gldMappingCondition7.setMappingConditionValue(moCshTrxClassId);
        gldMappingConditions.add(gldMappingCondition7);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuildPaymentReq(String magOrgId, String companyId, String positionId,
                                                        String accEntityId, String respCenterId, String partnerCategory, String partnerTypeId,
                                                        String partnerId, String cshTransactionClassId) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        gldMappingCondition.setMappingConditionCode("MANAGING_ORG");
        gldMappingCondition.setMappingConditionValue(magOrgId);
        gldMappingConditions.add(gldMappingCondition);
        GldMappingCondition gldMappingCondition1 = new GldMappingCondition();
        gldMappingCondition1.setMappingConditionCode("FND_COMPANY");
        gldMappingCondition1.setMappingConditionValue(companyId);
        gldMappingConditions.add(gldMappingCondition1);
        GldMappingCondition gldMappingCondition2 = new GldMappingCondition();
        gldMappingCondition2.setMappingConditionCode("EXP_POSITION");
        gldMappingCondition2.setMappingConditionValue(positionId);
        gldMappingConditions.add(gldMappingCondition2);
        GldMappingCondition gldMappingCondition3 = new GldMappingCondition();
        gldMappingCondition3.setMappingConditionCode("ACC_ENTITY");
        gldMappingCondition3.setMappingConditionValue(accEntityId);
        gldMappingConditions.add(gldMappingCondition3);
        GldMappingCondition gldMappingCondition4 = new GldMappingCondition();
        gldMappingCondition4.setMappingConditionCode("RESP_CENTER");
        gldMappingCondition4.setMappingConditionValue(respCenterId);
        gldMappingConditions.add(gldMappingCondition4);
        GldMappingCondition gldMappingCondition5 = new GldMappingCondition();
        gldMappingCondition5.setMappingConditionCode("PARTNER_CATEGORY");
        gldMappingCondition5.setMappingConditionValue(partnerCategory);
        gldMappingConditions.add(gldMappingCondition5);
        GldMappingCondition gldMappingCondition6 = new GldMappingCondition();
        gldMappingCondition6.setMappingConditionCode("PARTNER_TYPE");
        gldMappingCondition6.setMappingConditionValue(partnerTypeId);
        gldMappingConditions.add(gldMappingCondition6);
        GldMappingCondition gldMappingCondition7 = new GldMappingCondition();
        gldMappingCondition7.setMappingConditionCode("PARTNER");
        gldMappingCondition7.setMappingConditionValue(partnerId);
        gldMappingConditions.add(gldMappingCondition7);
        GldMappingCondition gldMappingCondition8 = new GldMappingCondition();
        gldMappingCondition8.setMappingConditionCode("CSH_TRANSACTION_CLASS");
        gldMappingCondition8.setMappingConditionValue(cshTransactionClassId);
        gldMappingConditions.add(gldMappingCondition8);
        return gldMappingConditions;
    }

    @Override
    public List<GldMappingCondition> accBuildCshRevaluation(String currencyCode) {
        List<GldMappingCondition> gldMappingConditions = new ArrayList<>();
        GldMappingCondition gldMappingCondition = new GldMappingCondition();
        // 币种
        gldMappingCondition.setMappingConditionCode("CURRENCY");
        gldMappingCondition.setMappingConditionValue(currencyCode);
        gldMappingConditions.add(gldMappingCondition);

        return gldMappingConditions;
    }

    @Override
    public Long getAccount(List<GldMappingCondition> gldMappingConditions, String usageCode, Long magOrgId,
                           Long setOfBooksId) {
        Long accountId = 0L;
        // 通过用途代码获取对应匹配组信息
        List<GldMappingCondGrpHd> gldMappingCondGrpHds = gldMappingCondGrpHdMapper.getMappingCondGrpHds(usageCode);
        if (gldMappingCondGrpHds != null && !gldMappingCondGrpHds.isEmpty()) {
            for (GldMappingCondGrpHd gldMappingCondGrpHd : gldMappingCondGrpHds) {
                int i = 0;
                // 获取科目的sql
                StringBuilder sqlSB = new StringBuilder();
                sqlSB.append("select account_id from ").append(gldMappingCondGrpHd.getTableName());
                // 获取该匹配组下所有的匹配项信息
                List<GldMappingCondGrpLn> gldMappingCondGrpLns =
                                gldMappingCondGrpLnMapper.getMappingCondGrpLns(gldMappingCondGrpHd.getGroupName());
                if (gldMappingCondGrpLns.isEmpty()) {
                    if (gldMappingCondGrpHd.getPriority() == 999) {
                        sqlSB.append(" where mag_org_id = ").append(magOrgId).append(" and set_of_books_id = ")
                                        .append(setOfBooksId).append(" and usage_code = '").append(usageCode)
                                        .append("'");
                    } else {
                        sqlSB.append(" where mag_org_id = ").append(magOrgId).append(" and set_of_books_id = ")
                                        .append(setOfBooksId);
                    }
                } else {
                    for (GldMappingCondGrpLn gldMappingCondGrpLn : gldMappingCondGrpLns) {
                        for (GldMappingCondition gldMappingCondition : gldMappingConditions) {
                            if (i == 0) {
                                sqlSB.append(" where mag_org_id = ").append(magOrgId).append(" and set_of_books_id = ")
                                                .append(setOfBooksId);
                                i += 1;
                            }
                            if (gldMappingCondition.getMappingConditionCode()
                                            .equals(gldMappingCondGrpLn.getMappingConditionCode())) {
                                sqlSB.append(" and ").append(gldMappingCondition.getMappingConditionCode())
                                                .append(" = '").append(gldMappingCondition.getMappingConditionValue())
                                                .append("'");
                            }
                        }
                    }
                }
                accountId = gldMappingConditionMapper.getAccount(sqlSB.toString());
                if(accountId != null) {
                    if(accountId.longValue() != 0){
                        return accountId;
                    }
                }
            }
        }
        return accountId;
    }
}
