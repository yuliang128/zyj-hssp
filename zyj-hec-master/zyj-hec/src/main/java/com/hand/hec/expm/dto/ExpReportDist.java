package com.hand.hec.expm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <p>
 * ExpReportDist
 * </p>
 *
 * @author yang.duan 2019/01/10 14:58
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_report_dist")
public class ExpReportDist extends BaseDTO {

     public static final String FIELD_POSITION_ID = "positionId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_RESP_CENTER_ID = "respCenterId";
     public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
     public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
     public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
     public static final String FIELD_PAYEE_ID = "payeeId";
     public static final String FIELD_PAYMENT_FLAG = "paymentFlag";
     public static final String FIELD_REPORT_STATUS = "reportStatus";
     public static final String FIELD_CLOSE_FLAG = "closeFlag";
     public static final String FIELD_CLOSE_DATE = "closeDate";
     public static final String FIELD_CLOSE_DATE_TZ = "closeDateTz";
     public static final String FIELD_CLOSE_DATE_LTZ = "closeDateLtz";
     public static final String FIELD_AUDIT_FLAG = "auditFlag";
     public static final String FIELD_AUDIT_DATE = "auditDate";
     public static final String FIELD_AUDIT_DATE_TZ = "auditDateTz";
     public static final String FIELD_AUDIT_DATE_LTZ = "auditDateLtz";
     public static final String FIELD_WRITE_OFF_STATUS = "writeOffStatus";
     public static final String FIELD_WRITE_OFF_COMLETED_DATE = "writeOffComletedDate";
     public static final String FIELD_WRITE_OFF_COMLETED_DATE_TZ = "writeOffComletedDateTz";
     public static final String FIELD_WRITE_OFF_COMLETED_DATE_LTZ = "writeOffComletedDateLtz";
     public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
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
     public static final String FIELD_EXP_REPORT_DIST_ID = "expReportDistId";
     public static final String FIELD_EXP_REPORT_LINE_ID = "expReportLineId";
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
     public static final String FIELD_PAY2MAG_EXCHANGE_RATE = "pay2magExchangeRate";
     public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
     public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
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
     public static final String FIELD_REPORT_FUNCTIONAL_AMOUNT = "reportFunctionalAmount";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
     public static final String FIELD_UNIT_ID = "unitId";


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
     * 预算实体ID
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
     @Length(max = 1)
     private String paymentFlag;

    /**
     * 废弃_单据状态
     */
     @NotEmpty
     @Length(max = 30)
     private String reportStatus;

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
     * 关闭日期_业务时区
     */
     private Date closeDateTz;

    /**
     * 关闭日期_查询时区
     */
     private Date closeDateLtz;

    /**
     * 废弃_审核标志
     */
     @Length(max = 30)
     private String auditFlag;

    /**
     * 废弃_审核日期
     */
     private Date auditDate;

    /**
     * 废弃_审核日期_业务时区
     */
     private Date auditDateTz;

    /**
     * 废弃_审核日期_查询时区
     */
     private Date auditDateLtz;

    /**
     * 废弃_核销状态
     */
     @Length(max = 30)
     private String writeOffStatus;

    /**
     * 废弃_核销日期
     */
     private Date writeOffComletedDate;

    /**
     * 废弃_核销日期_业务时区
     */
     private Date writeOffComletedDateTz;

    /**
     * 废弃_核销日期_查询时区
     */
     private Date writeOffComletedDateLtz;

    /**
     * 附件数
     */
     private Long attachmentNum;

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
     private Long expReportDistId;

    /**
     * 报销单行ID
     */
     @NotNull
     @Where
     private Long expReportLineId;

    /**
     * 描述
     */
     @Length(max = 4000)
     private String description;

    /**
     * 费用发生日期从
     */
     private Date dateFrom;

    /**
     * 费用发生日期到
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
     * 支付币种->本位率汇率
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
     * 管理组织级费用项目ID
     */
     @NotNull
     private Long moExpenseItemId;

    /**
     * 预算项目ID
     */
     private Long budgetItemId;

    /**
     * 业务币种单价
     */
     private BigDecimal businessPrice;

    /**
     * 支付币种单价
     */
     private BigDecimal paymentPrice;

    /**
     * 管理币种单价
     */
     private BigDecimal managementPrice;

    /**
     * 数量
     */
     private BigDecimal primaryQuantity;

    /**
     * 数量单位
     */
     @Length(max = 30)
     private String primaryUom;

    /**
     * 废弃_次要数量
     */
     private BigDecimal secondaryQuantity;

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
     * 支付币种金额
     */
     private BigDecimal paymentAmount;

    /**
     * 管理币种金额
     */
     private BigDecimal managementAmount;

    /**
     * 本位币金额
     */
     private BigDecimal reportFunctionalAmount;

    /**
     * 管理公司ID
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
     * 行号
     */
     @Transient
     private Long lineNumber;

    /**
     * 拆分税金
     */
    @Transient
    private BigDecimal splittedTaxAmount;
    /**
     * 支付币种拆分税金（拆分税金 * 业务币种->支付币种汇率）
     */
    @Transient
    private BigDecimal paymentSpTaxAmount;

    /**
     * 实际报销税金
     */
    @Transient
    private BigDecimal reportTaxAmount;

    /**
     * 支付币种实际报销税金（实际报销税金 * 业务币种->支付币种汇率）
     */
    @Transient
    private BigDecimal paymentRpTaxAmount;

    /**
     * 税率类型ID
     */
    @Transient
    private Long taxTypeId;

    /**
     * 发票项目ID
     */
    @Transient
    private Long invoiceItemId;

    /**
     * 发票用途ID
     */
    @Transient
    private Long invoiceUsedeId;

    /**
     * 部门类型ID
     */
    @Transient
    private Long unitTypeId;

    /**
     * 账套ID
     */
    @Transient
    private Long setOfBooksId;

    /**
     * 认证状态
     */
    @Transient
    private String authenticationStatus;

    /**
     * 是否需要认证标识
     */
    @Transient
    private String authenticationFlag;

    /**
     * 本位币拆分税金（支付币种拆分税金 * 支付币种->本位币汇率）
     */
    @Transient
    private BigDecimal functionalSpTaxAmount;

    /**
     * 本位币实际报销税金（支付币种实际报销税金 * 支付币种->本位币汇率）
     */
    @Transient
    private BigDecimal functionalRpTaxAmount;
     }
