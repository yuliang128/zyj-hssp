package com.hand.hec.expm.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * ExpReportInterface
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_report_interface")
public class ExpReportInterface extends BaseDTO {

     public static final String FIELD_BATCH_ID = "batchId";
     public static final String FIELD_BATCH_LINE_ID = "batchLineId";
     public static final String FIELD_IMPORT_FLAG = "importFlag";
     public static final String FIELD_EXP_REPORT_BATCH_NUMBER = "expReportBatchNumber";
     public static final String FIELD_EXP_REPORT_TYPE_CODE = "expReportTypeCode";
     public static final String FIELD_HEAD_COMPANY_CODE = "headCompanyCode";
     public static final String FIELD_EXP_REPORT_BARCODE = "expReportBarcode";
     public static final String FIELD_HEAD_EMPLOYEE_CODE = "headEmployeeCode";
     public static final String FIELD_HEAD_POSITION_CODE = "headPositionCode";
     public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
     public static final String FIELD_PAYEE_CODE = "payeeCode";
     public static final String FIELD_PAYMENT_METHOD = "paymentMethod";
     public static final String FIELD_EXPENSE_USER_GROUP_CODE = "expenseUserGroupCode";
     public static final String FIELD_CURRENCY_CODE = "currencyCode";
     public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
     public static final String FIELD_EXCHANGE_RATE_QUOTATION = "exchangeRateQuotation";
     public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
     public static final String FIELD_REPORT_DATE = "reportDate";
     public static final String FIELD_HEAD_PERIOD_NAME = "headPeriodName";
     public static final String FIELD_ATTACHMENT_NUM = "attachmentNum";
     public static final String FIELD_HEAD_DESCRIPTION = "headDescription";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_CITY = "city";
     public static final String FIELD_PLACE_CODE = "placeCode";
     public static final String FIELD_PLACE_TYPE_CODE = "placeTypeCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_DATE_FROM = "dateFrom";
     public static final String FIELD_DATE_TO = "dateTo";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_EXPENSE_TYPE_CODE = "expenseTypeCode";
     public static final String FIELD_EXPENSE_ITEM_CODE = "expenseItemCode";
     public static final String FIELD_BUDGET_ITEM_CODE = "budgetItemCode";
     public static final String FIELD_PRICE = "price";
     public static final String FIELD_PRIMARY_QUANTITY = "primaryQuantity";
     public static final String FIELD_COMPANY_CODE = "companyCode";
     public static final String FIELD_UNIT_CODE = "unitCode";
     public static final String FIELD_POSITION_CODE = "positionCode";
     public static final String FIELD_RESP_CENTER_CODE = "respCenterCode";
     public static final String FIELD_EMPLOYEE_CODE = "employeeCode";
     public static final String FIELD_DIMENSION1_CODE = "dimension1Code";
     public static final String FIELD_DIMENSION2_CODE = "dimension2Code";
     public static final String FIELD_DIMENSION3_CODE = "dimension3Code";
     public static final String FIELD_DIMENSION4_CODE = "dimension4Code";
     public static final String FIELD_DIMENSION5_CODE = "dimension5Code";
     public static final String FIELD_DIMENSION6_CODE = "dimension6Code";
     public static final String FIELD_DIMENSION7_CODE = "dimension7Code";
     public static final String FIELD_DIMENSION8_CODE = "dimension8Code";
     public static final String FIELD_DIMENSION9_CODE = "dimension9Code";
     public static final String FIELD_DIMENSION10_CODE = "dimension10Code";


    /**
     * 批次号
     */
     @NotNull
     private Long batchId;

    /**
     * 批次行id
     */
     @NotNull
     private Long batchLineId;

    /**
     * 导入状态
     */
     @NotEmpty
     @Length(max = 2)
     private String importFlag;

    /**
     * 报销单唯一标识
     */
     @NotEmpty
     @Length(max = 200)
     private String expReportBatchNumber;

    /**
     * 报销单类型代码
     */
     @NotEmpty
     @Length(max = 30)
     private String expReportTypeCode;

    /**
     * 报销单头公司代码
     */
     @NotEmpty
     @Length(max = 30)
     private String headCompanyCode;

    /**
     * 条码
     */
     @Length(max = 30)
     private String expReportBarcode;

    /**
     * 申请人
     */
     @Length(max = 30)
     private String headEmployeeCode;

    /**
     * 报销单头岗位
     */
     @Length(max = 30)
     private String headPositionCode;

    /**
     * 收款方类型
     */
     @Length(max = 30)
     private String payeeCategory;

    /**
     * 收款方代码
     */
     @Length(max = 30)
     private String payeeCode;

    /**
     * 付款方式代码
     */
     @Length(max = 30)
     private String paymentMethod;

    /**
     * 费用用户组代码
     */
     @Length(max = 30)
     private String expenseUserGroupCode;

    /**
     * 币种
     */
     @Length(max = 30)
     private String currencyCode;

    /**
     * 汇率类型
     */
     @Length(max = 30)
     private String exchangeRateType;

    /**
     * 汇率标价方式
     */
     @Length(max = 30)
     private String exchangeRateQuotation;

    /**
     * 汇率
     */
     private BigDecimal exchangeRate;

    /**
     * 报销日期
     */
     @Length(max = 30)
     private String reportDate;

    /**
     * 头期间
     */
     @Length(max = 30)
     private String headPeriodName;

    /**
     * 附件数
     */
     private Long attachmentNum;

    /**
     * 头描述
     */
     @Length(max = 2000)
     private String headDescription;

    /**
     * 行号
     */
     @NotNull
     private Long lineNumber;

    /**
     * 城市
     */
     @Length(max = 200)
     private String city;

    /**
     * 地点代码
     */
     @Length(max = 30)
     private String placeCode;

    /**
     * 地点类型代码
     */
     @Length(max = 30)
     private String placeTypeCode;

    /**
     * 行描述
     */
     @Length(max = 200)
     private String description;

    /**
     * 日期从
     */
     @Length(max = 30)
     private String dateFrom;

    /**
     * 日期到
     */
     @Length(max = 30)
     private String dateTo;

    /**
     * 期间
     */
     @NotEmpty
     @Length(max = 30)
     private String periodName;

    /**
     * 费用类型代码
     */
     @NotEmpty
     @Length(max = 30)
     private String expenseTypeCode;

    /**
     * 费用项目代码
     */
     @NotEmpty
     @Length(max = 30)
     private String expenseItemCode;

    /**
     * 预算项目代码
     */
     @Length(max = 30)
     private String budgetItemCode;

    /**
     * 单价
     */
     private BigDecimal price;

    /**
     * 数量
     */
     private BigDecimal primaryQuantity;

    /**
     * 公司
     */
     @NotEmpty
     @Length(max = 30)
     private String companyCode;

    /**
     * 部门代码
     */
     @Length(max = 30)
     private String unitCode;

    /**
     * 岗位代码
     */
     @Length(max = 30)
     private String positionCode;

    /**
     * 责任中心代码
     */
     @NotEmpty
     @Length(max = 30)
     private String respCenterCode;

    /**
     * 员工代码
     */
     @Length(max = 30)
     private String employeeCode;

    /**
     * 维度1代码
     */
     @Length(max = 30)
     private String dimension1Code;

    /**
     * 维度2代码
     */
     @Length(max = 30)
     private String dimension2Code;

    /**
     * 维度3代码
     */
     @Length(max = 30)
     private String dimension3Code;

    /**
     * 维度4代码
     */
     @Length(max = 30)
     private String dimension4Code;

    /**
     * 维度5代码
     */
     @Length(max = 30)
     private String dimension5Code;

    /**
     * 维度6代码
     */
     @Length(max = 30)
     private String dimension6Code;

    /**
     * 维度7代码
     */
     @Length(max = 30)
     private String dimension7Code;

    /**
     * 维度8代码
     */
     @Length(max = 30)
     private String dimension8Code;

    /**
     * 维度9代码
     */
     @Length(max = 30)
     private String dimension9Code;

    /**
     * 维度10代码
     */
     @Length(max = 30)
     private String dimension10Code;

     }
