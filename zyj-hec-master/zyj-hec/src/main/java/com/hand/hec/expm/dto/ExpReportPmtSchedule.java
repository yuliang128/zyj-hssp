package com.hand.hec.expm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
 * ExpReportPmtSchedule
 * </p>
 *
 * @author yang.duan 2019/01/10 15:01
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_pmt_schedule")
public class ExpReportPmtSchedule extends BaseDTO {

    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";
    public static final String FIELD_CONTRACT_HEADER_ID = "contractHeaderId";
    public static final String FIELD_CON_PMT_SCHEDULE_LINE_ID = "conPmtScheduleLineId";
    public static final String FIELD_PAYMENT_SCHEDULE_LINE_ID = "paymentScheduleLineId";
    public static final String FIELD_EXP_REPORT_HEADER_ID = "expReportHeaderId";
    public static final String FIELD_SCHEDULE_LINE_NUMBER = "scheduleLineNumber";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_SCHEDULE_START_DATE = "scheduleStartDate";
    public static final String FIELD_SCHEDULE_DUE_DATE = "scheduleDueDate";
    public static final String FIELD_DUE_AMOUNT = "dueAmount";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_FROZEN_FLAG = "frozenFlag";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_BANK_LOCATION_CODE = "bankLocationCode";
    public static final String FIELD_BANK_LOCATION_NAME = "bankLocationName";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";


    /**
     * 城市代码
     */
    @Length(max = 200)
    private String cityCode;

    /**
     * 城市名称
     */
    @Length(max = 200)
    private String cityName;

    /**
     * 合同申请头ID
     */
    private Long contractHeaderId;

    /**
     * 资金计划行ID
     */
    private Long conPmtScheduleLineId;

    @Id
    @GeneratedValue
    private Long paymentScheduleLineId;

    /**
     * 报销单头ID
     */
    @NotNull
    private Long expReportHeaderId;

    /**
     * 计划付款行号
     */
    @NotNull
    private Long scheduleLineNumber;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 付款币种
     */
    @NotEmpty
    @Length(max = 30)
    private String paymentCurrencyCode;

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
     * 计划起始付款日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date scheduleStartDate;

    /**
     * 计划付款日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date scheduleDueDate;

    /**
     * 计划付款金额
     */
    private BigDecimal dueAmount;

    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 核算实体ID
     */
    @NotNull
    private Long accEntityId;

    /**
     * 付款方式
     */
    private Long paymentMethodId;

    /**
     * 付款用途
     */
    private Long paymentUsedeId;

    /**
     * 现金流量项
     */
    private Long cashFlowItemId;

    /**
     * 冻结标志
     */
    @Length(max = 1)
    private String frozenFlag;

    /**
     * 户名
     */
    @Length(max = 200)
    private String accountName;

    /**
     * 账号
     */
    @Length(max = 200)
    private String accountNumber;

    /**
     * 银行代码
     */
    @Length(max = 200)
    private String bankCode;

    /**
     * 银行名称
     */
    @Length(max = 200)
    private String bankName;

    /**
     * 分行代码
     */
    @Length(max = 200)
    private String bankLocationCode;

    /**
     * 分行名称
     */
    @Length(max = 200)
    private String bankLocationName;

    /**
     * 省份代码
     */
    @Length(max = 200)
    private String provinceCode;

    /**
     * 省份名称
     */
    @Length(max = 200)
    private String provinceName;

    /**
     * 税额
     */
    @Transient
    private BigDecimal taxAmount;

    /**
     * 税率类型ID
     */
    @Transient
    private Long taxTypeId;

    /**
     * 收款方类型ID
     */
    @Transient
    private Long payeeTypeId;


    /*
     * 税率
     */
    @Transient
    private BigDecimal taxRate;

    @Transient
    private String paymentCurrencyName;

    @Transient
    private String companyName;

    @Transient
    private String payeeCategoryName;

    @Transient
    private String payeeName;

    @Transient
    private String accEntityName;

    @Transient
    private String paymentMethodName;

    @Transient
    private String paymentUsedeName;

    @Transient
    private String cashFlowItemName;

    @Transient
    private BigDecimal writeOffAmount;

    @Transient
    private String contractNumber;

    @Transient
    private String conPmtScheduleLineNumber;

    @Transient
    private String taxFlag;

    @Transient
    private String moreTaxFlag;

    @Transient
    private String taxTypeName;

    @Transient
    private Long taxCount;
}
