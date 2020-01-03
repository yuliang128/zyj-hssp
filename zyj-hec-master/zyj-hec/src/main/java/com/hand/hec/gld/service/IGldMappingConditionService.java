package com.hand.hec.gld.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldMappingCondition;

public interface IGldMappingConditionService
                extends IBaseService<GldMappingCondition>, ProxySelf<IGldMappingConditionService> {
    /**
     * 创建借方科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/15 15:29
     * @param magOrgId 管理组织Id
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param employeeId 员工Id
     * @param expenseReportTypeId 费用报销单类型
     * @param currencyCode 币种编码
     * @param expenseItemId 费用项目Id
     * @param expenseTypeId 费用类型Id
     * @param periodComparison 期间
     * @param employeeTypeId 员工类型Id
     * @param orgUnitId 部门Id
     * @param orgUnitTypeId 部门类型Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderEmployeeExp(String magOrgId, String companyId, String positionId,
                    String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                    String currencyCode, String expenseItemId, String expenseTypeId, String periodComparison,
                    String employeeTypeId, String orgUnitId, String orgUnitTypeId);

    /**
     * 创建借方税金科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/15 15:29
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param employeeId 员工Id
     * @param expenseReportTypeId 费用报销单类型
     * @param taxTypeId 税率类型
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderEmployeeExpTax(String companyId, String positionId, String accEntityId,
                    String respCenterId, String expenseReportTypeId, String taxTypeId);

    /**
     * 创建借方税金科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/15 15:29
     * @param invoiceItemId 发票项目Id
     * @param invoiceUsedeId 发票用途Id
     * @param taxTypeId 税率类型
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderTaxTransferOut(String invoiceItemId, String invoiceUsedeId, String taxTypeId);

    /**
     * 创建贷方科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 16:44
     * @param magOrgId 管理组织Id
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param employeeId 员工Id
     * @param expenseReportTypeId 费用报销单类型
     * @param currencyCode 币种编码
     * @param employeeTypeId 员工类型Id
     * @param partnerCategory 收款对象
     * @param partnerTypeId 收款方类型Id
     * @param partnerId 收款方Id
     * @param usedesId 付款用途Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderExpClearing(String magOrgId, String companyId, String positionId,
                    String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                    String currencyCode, String employeeTypeId, String partnerCategory, String partnerTypeId,
                    String partnerId, String usedesId);

    /**
     * 创建贷方税务科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 16:44
     * @param magOrgId 管理组织Id
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param employeeId 员工Id
     * @param expenseReportTypeId 费用报销单类型
     * @param currencyCode 币种编码
     * @param employeeTypeId 员工类型Id
     * @param partnerCategory 收款对象
     * @param partnerTypeId 收款方类型Id
     * @param partnerId 收款方Id
     * @param usedesId 付款用途Id
     * @param taxTypeId 税率类型Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderExpClearTax(String magOrgId, String companyId, String positionId,
                    String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                    String currencyCode, String employeeTypeId, String partnerCategory, String partnerTypeId,
                    String partnerId, String usedesId, String taxTypeId);

    /**
     * 往来应收科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 19:08
     * @param magOrgId 管理组织Id
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param employeeId 员工Id
     * @param expenseReportTypeId 费用报销单类型
     * @param currencyCode 币种编码
     * @param expenseItemId 费用项目Id
     * @param expenseTypeId 费用类型Id
     * @param oppositeAccEntityId 对方核算主体Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderEmpCompanyAr(String magOrgId, String companyId, String positionId,
                    String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                    String currencyCode, String expenseItemId, String expenseTypeId, String oppositeAccEntityId);

    /**
     * 往来应付科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 19:08
     * @param magOrgId 管理组织Id
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param employeeId 员工Id
     * @param expenseReportTypeId 费用报销单类型
     * @param currencyCode 币种编码
     * @param expenseItemId 费用项目Id
     * @param expenseTypeId 费用类型Id
     * @param oppositeAccEntityId 对方核算主体Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderEmpCompanyAp(String magOrgId, String companyId, String positionId,
                    String accEntityId, String respCenterId, String employeeId, String expenseReportTypeId,
                    String currencyCode, String expenseItemId, String expenseTypeId, String oppositeAccEntityId);

    /**
     * 发票认证通过获取科目预置匹配组
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/25 14:45
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param respCenterId 责任中心Id
     * @param expenseReportTypeId 费用报销单类型
     * @param taxTypeId 税率类型Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuilderInvoiceTax(String companyId, String positionId, String respCenterId,
                    String expenseReportTypeId, String taxTypeId);

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
    List<GldMappingCondition> accBuilderRevaluation(String companyId, String positionId, String accEntityId,
                    String respCenterId, String currencyCode);

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
    List<GldMappingCondition> accBuilderCshInCompanyAr(String companyId, String accEntityId, String oppositeAccEntityId,
                    String respCenterId, String currencyCode, String positionId);

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
    List<GldMappingCondition> accBuilderCshInCompanyAp(String companyId, String accEntityId, String oppositeAccEntityId,
                    String respCenterId, String currencyCode, String positionId);

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
    List<GldMappingCondition> accBuilderPrepayment(String companyId, String accEntityId, String respCenterId,
                    String currencyCode, String positionId, String employeeId, String partnerCategory,
                    String cshTransactionType, String employeeTypeId, String venderTypeId, String venderId,
                    String moCshTrxClassId, String customerTypeId, String customerId);


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
    List<GldMappingCondition> accBuilderPayRequisition(String companyId, String accEntityId, String respCenterId,
                    String positionId, String partnerCategory, String partnerType, String paymentUsedeId,
                    String moCshTrxClassId);

    /**
     *
     * 借款单科目预置匹配组
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/26 15:09
     * @param magOrgId 管理组织Id
     * @param companyId 公司Id
     * @param positionId 岗位Id
     * @param accEntityId 核算主体Id
     * @param respCenterId 责任中心Id
     * @param partnerCategory 收款对象
     * @param partnerTypeId 收款方类型Id
     * @param partnerId 收款方Id
     * @param cshTransactionClassId 现金事物分类Id
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuildPaymentReq(String magOrgId, String companyId, String positionId,
                    String accEntityId, String respCenterId, String partnerCategory, String partnerTypeId,
                    String partnerId, String cshTransactionClassId);

    /**
     * 创建汇率差预置匹配项
     *
     * @Author Tagin
     * @Date 2019-02-25 14:06
     * @param currencyCode 币种
     * @return List<GldMappingCondition>
     * @Version 1.0
     **/
    List<GldMappingCondition> accBuildCshRevaluation(String currencyCode);

    /**
     * 获取用途代码匹配的科目
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/15 11:13
     * @param gldMappingConditions 预置的匹配项数据
     * @param usageCode 用途代码
     * @param magOrgId 管理组织ID
     * @param setOfBooksId 账套ID
     * @return Long
     * @Version 1.0
     **/
    Long getAccount(List<GldMappingCondition> gldMappingConditions, String usageCode, Long magOrgId, Long setOfBooksId);

}
