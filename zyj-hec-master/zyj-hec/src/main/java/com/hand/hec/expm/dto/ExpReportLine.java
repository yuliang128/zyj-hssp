package com.hand.hec.expm.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hand.hec.vat.dto.VatInvoice;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * ExpReportLine
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_line")
public class ExpReportLine extends BaseDTO {

    public static final String FIELD_INVOICE_TYPE = "invoiceType";
    public static final String FIELD_EXP_REPORT_LINE_ID = "expReportLineId";
    public static final String FIELD_EXP_REPORT_HEADER_ID = "expReportHeaderId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_REPORT_PAGE_ELEMENT_CODE = "reportPageElementCode";
    public static final String FIELD_PLACE_TYPE_ID = "placeTypeId";
    public static final String FIELD_PLACE_ID = "placeId";
    public static final String FIELD_CITY = "city";
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
    public static final String FIELD_REPORT_STATUS = "reportStatus";
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


    /**
     * 发票类型
     */
    @Length(max = 30)
    private String invoiceType;

    @Id
    @GeneratedValue
    private Long expReportLineId;

    /**
     * 报销单头ID
     */
    @NotNull
    @Where
    private Long expReportHeaderId;

    /**
     * 行号
     */
    @NotNull
    private Long lineNumber;

    /**
     * 报销单页面元素代码
     */
    @NotEmpty
    @Length(max = 30)
    private String reportPageElementCode;

    /**
     * 地点类型
     */
    private Long placeTypeId;

    /**
     * 地点类型
     */
    private Long placeId;

    /**
     * 城市，手动输入
     */
    @Length(max = 200)
    private String city;

    /**
     * 描述
     */
    @Length(max = 4000)
    private String description;

    /**
     * 费用发生日期从
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT,timezone = "GTM+8")
    private Date dateFrom;

    /**
     * 费用发生日期到
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT,timezone = "GTM+8")
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
     * 支付币种->管理币种汇率类型
     */
    @Length(max = 30)
    private String pay2magExchangeType;

    /**
     * 支付币种->管理币种汇率
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
     * 废弃_分配集ID
     */
    private Long distributionSetId;

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
     * 废弃_审核标志
     */
    @NotEmpty
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
    @NotEmpty
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

    @Transient
    private String placeTypeName;
    @Transient
    private String placeName;
    @Transient
    private String businessCurrencyName;
    @Transient
    private String businessCurrencyPrecision;

    @Transient
    private String biz2payExchangeTypeName;

    @Transient
    private String paymentCurrencyName;

    @Transient
    private String paymentCurrencyPrecision;
    @Transient
    private String pay2funExchangeTypeName;

    @Transient
    private String managementCurrencyName;

    @Transient
    private String managementCurrencyPrecision;

    @Transient
    private String pay2magExchangeTypeName;

    @Transient
    private String moExpenseTypeName;

    @Transient
    private String moExpenseItemName;

    @Transient
    private String budgetItemName;

    @Transient
    private String companyName;

    @Transient
    private String unitName;

    @Transient
    private String positionName;

    @Transient
    private String employeeName;

    @Transient
    private String accEntityName;

    @Transient
    private String respCenterName;

    @Transient
    private String bgtEntityName;

    @Transient
    private String bgtCenterName;

    @Transient
    private String payeeCategoryName;

    @Transient
    private String payeeName;

    @Transient
    private String dimension1Name;
    @Transient
    private String dimension2Name;
    @Transient
    private String dimension3Name;
    @Transient
    private String dimension4Name;
    @Transient
    private String dimension5Name;
    @Transient
    private String dimension6Name;
    @Transient
    private String dimension7Name;
    @Transient
    private String dimension8Name;
    @Transient
    private String dimension9Name;
    @Transient
    private String dimension10Name;
    @Transient
    private String dimension11Name;
    @Transient
    private String dimension12Name;
    @Transient
    private String dimension13Name;
    @Transient
    private String dimension14Name;
    @Transient
    private String dimension15Name;
    @Transient
    private String dimension16Name;
    @Transient
    private String dimension17Name;
    @Transient
    private String dimension18Name;
    @Transient
    private String dimension19Name;
    @Transient
    private String dimension20Name;

    @Transient
    private String dimension1Level;
    @Transient
    private String dimension2Level;
    @Transient
    private String dimension3Level;
    @Transient
    private String dimension4Level;
    @Transient
    private String dimension5Level;
    @Transient
    private String dimension6Level;
    @Transient
    private String dimension7Level;
    @Transient
    private String dimension8Level;
    @Transient
    private String dimension9Level;
    @Transient
    private String dimension10Level;
    @Transient
    private String dimension11Level;
    @Transient
    private String dimension12Level;
    @Transient
    private String dimension13Level;
    @Transient
    private String dimension14Level;
    @Transient
    private String dimension15Level;
    @Transient
    private String dimension16Level;
    @Transient
    private String dimension17Level;
    @Transient
    private String dimension18Level;
    @Transient
    private String dimension19Level;
    @Transient
    private String dimension20Level;

    @Transient
    private List<VatInvoice> invoiceLines;


}
