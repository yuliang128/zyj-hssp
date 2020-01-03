package com.hand.hec.exp.dto;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_requisition_dist")
public class ExpRequisitionDist extends BaseDTO {

     public static final String FIELD_PAY2MAG_EXCHANGE_RATE = "pay2magExchangeRate";
     public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
     public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
     public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
     public static final String FIELD_BUSINESS_PRICE = "businessPrice";
     public static final String FIELD_PAYMENT_PRICE = "paymentPrice";
     public static final String FIELD_MANAGEMENT_PRICE = "managementPrice";
     public static final String FIELD_PRIMARY_QUANTITY = "primaryQuantity";
     public static final String FIELD_PRIMARY_UOM = "primaryUom";
     public static final String FIELD_SECONDARY_QUANTITY = "secondaryQuantity";
     public static final String FIELD_SECONDARY_UOM = "secondaryUom";
     public static final String FIELD_BUSINESS_AMOUNT = "businessAmount";
     public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
     public static final String FIELD_MANAGEMENT_AMOUNT = "managementAmount";
     public static final String FIELD_REQUISITION_FUNCTIONAL_AMOUNT = "requisitionFunctionalAmount";
     public static final String FIELD_DISTRIBUTION_SET_ID = "distributionSetId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
     public static final String FIELD_UNIT_ID = "unitId";
     public static final String FIELD_POSITION_ID = "positionId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_RESP_CENTER_ID = "respCenterId";
     public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
     public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
     public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
     public static final String FIELD_PAYEE_ID = "payeeId";
     public static final String FIELD_PAYMENT_FLAG = "paymentFlag";
     public static final String FIELD_CLOSE_FLAG = "closeFlag";
     public static final String FIELD_CLOSE_DATE = "closeDate";
     public static final String FIELD_REQUISITION_STATUS = "requisitionStatus";
     public static final String FIELD_AUDIT_FLAG = "auditFlag";
     public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
     public static final String FIELD_EXCEED_BUDGET_STRATEGY = "exceedBudgetStrategy";
     public static final String FIELD_RELEASED_AMOUNT = "releasedAmount";
     public static final String FIELD_RELEASED_QUANTITY = "releasedQuantity";
     public static final String FIELD_RELEASED_STATUS = "releasedStatus";
     public static final String FIELD_DIMENSION1_ID = "dimension1Id";
     public static final String FIELD_DIMENSION2_ID = "dimension2Id";
     public static final String FIELD_DIMENSION3_ID = "dimension3Id";
     public static final String FIELD_DIMENSION4_ID = "dimension4Id";
     public static final String FIELD_DIMENSION5_ID = "dimension5Id";
     public static final String FIELD_DIMENSION6_ID = "dimension6Id";
     public static final String FIELD_DIMENSION7_ID = "dimension7Id";
     public static final String FIELD_DIMENSION8_ID = "dimension8Id";
     public static final String FIELD_DIMENSION9_ID = "dimension9Id";
     public static final String FIELD_DIMENSION10_ID = "dimension10Id";
     public static final String FIELD_DIMENSION11_ID = "dimension11Id";
     public static final String FIELD_DIMENSION12_ID = "dimension12Id";
     public static final String FIELD_DIMENSION13_ID = "dimension13Id";
     public static final String FIELD_DIMENSION14_ID = "dimension14Id";
     public static final String FIELD_DIMENSION15_ID = "dimension15Id";
     public static final String FIELD_DIMENSION16_ID = "dimension16Id";
     public static final String FIELD_DIMENSION17_ID = "dimension17Id";
     public static final String FIELD_DIMENSION18_ID = "dimension18Id";
     public static final String FIELD_DIMENSION19_ID = "dimension19Id";
     public static final String FIELD_DIMENSION20_ID = "dimension20Id";
     public static final String FIELD_EXP_REQUISITION_DIST_ID = "expRequisitionDistId";
     public static final String FIELD_EXP_REQUISITION_LINE_ID = "expRequisitionLineId";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_DATE_FROM = "dateFrom";
     public static final String FIELD_DATE_TO = "dateTo";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_BUSINESS_CURRENCY_CODE = "businessCurrencyCode";
     public static final String FIELD_BIZ2PAY_EXCHANGE_TYPE = "biz2payExchangeType";
     public static final String FIELD_BIZ2PAY_EXCHANGE_RATE = "biz2payExchangeRate";
     public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
     public static final String FIELD_PAY2FUN_EXCHANGE_TYPE = "pay2funExchangeType";
     public static final String FIELD_PAY2FUN_EXCHANGE_RATE = "pay2funExchangeRate";
     public static final String FIELD_MANAGEMENT_CURRENCY_CODE = "managementCurrencyCode";
     public static final String FIELD_PAY2MAG_EXCHANGE_TYPE = "pay2magExchangeType";


    /**
     * 业务币种->管理币种汇率
     */
     private BigDecimal pay2magExchangeRate;

    /**
     * 管理组织级报销类型ID
     */
     @NotNull
     private Long moExpenseTypeId;

    /**
     * 管理组织级费用申请项目ID
     */
     @NotNull
     private Long moReqItemId;

    /**
     * 预算项目ID
     */
     private Long budgetItemId;

    /**
     * 业务币种单价
     */
     private BigDecimal businessPrice;

    /**
     * 付款币种单价
     */
     private BigDecimal paymentPrice;

    /**
     * 管理币种单价
     */
     private BigDecimal managementPrice;

    /**
     * 数量
     */
     @NotNull
     private Long primaryQuantity;

    /**
     * 数量单位
     */
     @Length(max = 30)
     private String primaryUom;

    /**
     * 废弃_次要数量
     */
     private Long secondaryQuantity;

    /**
     * 废弃_次要数量单位
     */
     @Length(max = 30)
     private String secondaryUom;

    /**
     * 业务币种金额
     */
     private BigDecimal businessAmount;

    /**
     * 付款币种金额
     */
     private BigDecimal paymentAmount;

    /**
     * 管理币种金额
     */
     private BigDecimal managementAmount;

    /**
     * 本位币金额
     */
     private BigDecimal requisitionFunctionalAmount;

    /**
     * 废弃_分配集
     */
     private Long distributionSetId;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

    /**
     * 废弃_经营单位ID
     */
     private Long operationUnitId;

    /**
     * 部门ID
     */
     @NotNull
     private Long unitId;

    /**
     * 岗位ID
     */
     @NotNull
     private Long positionId;

    /**
     * 员工ID
     */
     @NotNull
     private Long employeeId;

    /**
     * 核算主体ID
     */
     @NotNull
     private Long accEntityId;

    /**
     * 责任中心ID
     */
     @NotNull
     private Long respCenterId;

    /**
     * 预算主体ID
     */
     @NotNull
     private Long bgtEntityId;

    /**
     * 预算中心ID
     */
     @NotNull
     private Long bgtCenterId;

    /**
     * 收款方类别
     */
     @Length(max = 30)
     private String payeeCategory;

    /**
     * 收款方ID
     */
     private Long payeeId;

    /**
     * 废弃_付款标志
     */
     @NotEmpty
     @Length(max = 1)
     private String paymentFlag;

    /**
     * 关闭标志
     */
     @NotEmpty
     @Length(max = 1)
     private String closeFlag;

    /**
     * 关闭日期
     */
     private Date closeDate;

    /**
     * 废弃_申请状态
     */
     @NotEmpty
     @Length(max = 30)
     private String requisitionStatus;

    /**
     * 废弃_审核标志
     */
     @Length(max = 1)
     private String auditFlag;

    /**
     * 附件数量
     */
     private Long attachmentNum;

    /**
     * 控制策略
     */
     @Length(max = 30)
     private String exceedBudgetStrategy;

    /**
     * 下达金额
     */
     private BigDecimal releasedAmount;

    /**
     * 下达数量
     */
     private Long releasedQuantity;

    /**
     * 下达状态
     */
     @Length(max = 30)
     private String releasedStatus;

    /**
     * 维度1
     */
     private Long dimension1Id;

    /**
     * 维度2
     */
     private Long dimension2Id;

    /**
     * 维度3
     */
     private Long dimension3Id;

    /**
     * 维度4
     */
     private Long dimension4Id;

    /**
     * 维度5
     */
     private Long dimension5Id;

    /**
     * 维度6
     */
     private Long dimension6Id;

    /**
     * 维度7
     */
     private Long dimension7Id;

    /**
     * 维度8
     */
     private Long dimension8Id;

    /**
     * 维度9
     */
     private Long dimension9Id;

    /**
     * 维度10
     */
     private Long dimension10Id;

    /**
     * 维度11
     */
     private Long dimension11Id;

    /**
     * 维度12
     */
     private Long dimension12Id;

    /**
     * 维度13
     */
     private Long dimension13Id;

    /**
     * 维度14
     */
     private Long dimension14Id;

    /**
     * 维度15
     */
     private Long dimension15Id;

    /**
     * 维度16
     */
     private Long dimension16Id;

    /**
     * 维度17
     */
     private Long dimension17Id;

    /**
     * 维度18
     */
     private Long dimension18Id;

    /**
     * 维度19
     */
     private Long dimension19Id;

    /**
     * 维度20
     */
     private Long dimension20Id;

     @Id
     @GeneratedValue
     private Long expRequisitionDistId;

    /**
     * 申请单头ID
     */
     @NotNull
     private Long expRequisitionLineId;

    /**
     * 描述
     */
     @Length(max = 4000)
     private String description;

    /**
     * 日期从
     */
     private Date dateFrom;

    /**
     * 日期到
     */
     private Date dateTo;

    /**
     * 期间
     */
     @NotEmpty
     @Length(max = 30)
     private String periodName;

    /**
     * 业务币种
     */
     @NotEmpty
     @Length(max = 30)
     private String businessCurrencyCode;

    /**
     * 业务币种->支付币种汇率类型
     */
     @Length(max = 30)
     private String biz2payExchangeType;

    /**
     * 业务币种->支付币种汇率
     */
     private BigDecimal biz2payExchangeRate;

    /**
     * 支付币种
     */
     @NotEmpty
     @Length(max = 30)
     private String paymentCurrencyCode;

    /**
     * 支付币种->本位币汇率类型
     */
     @Length(max = 30)
     private String pay2funExchangeType;

    /**
     * 支付币种->本位币汇率
     */
     private BigDecimal pay2funExchangeRate;

    /**
     * 管理币种
     */
     @NotEmpty
     @Length(max = 30)
     private String managementCurrencyCode;

    /**
     * 业务币种->管理币种汇率类型
     */
     @Length(max = 30)
     private String pay2magExchangeType;

     @Transient
     private String oneOffFlag;
     }
