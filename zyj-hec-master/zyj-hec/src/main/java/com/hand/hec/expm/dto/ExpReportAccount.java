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
 * ExpReportAccount
 * </p>
 *
 * @author yang.duan 2019/01/10 14:59
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_report_account")
public class ExpReportAccount extends BaseDTO {

    public static final String FIELD_EXP_REPORT_JE_LINE_ID = "expReportJeLineId";
    public static final String FIELD_EXP_REPORT_HEADER_ID = "expReportHeaderId";
    public static final String FIELD_EXP_REPORT_DIST_ID = "expReportDistId";
    public static final String FIELD_PAYMENT_SCHEDULE_LINE_ID = "paymentScheduleLineId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_JOURNAL_DATE = "journalDate";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_SOURCE_CODE = "sourceCode";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_ENTERED_AMOUNT_DR = "enteredAmountDr";
    public static final String FIELD_ENTERED_AMOUNT_CR = "enteredAmountCr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_DR = "functionalAmountDr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_CR = "functionalAmountCr";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_TRANSFER_FLAG = "transferFlag";
    public static final String FIELD_USAGE_CODE = "usageCode";
    public static final String FIELD_ACCOUNT_SEGMENT1 = "accountSegment1";
    public static final String FIELD_ACCOUNT_SEGMENT2 = "accountSegment2";
    public static final String FIELD_ACCOUNT_SEGMENT3 = "accountSegment3";
    public static final String FIELD_ACCOUNT_SEGMENT4 = "accountSegment4";
    public static final String FIELD_ACCOUNT_SEGMENT5 = "accountSegment5";
    public static final String FIELD_ACCOUNT_SEGMENT6 = "accountSegment6";
    public static final String FIELD_ACCOUNT_SEGMENT7 = "accountSegment7";
    public static final String FIELD_ACCOUNT_SEGMENT8 = "accountSegment8";
    public static final String FIELD_ACCOUNT_SEGMENT9 = "accountSegment9";
    public static final String FIELD_ACCOUNT_SEGMENT10 = "accountSegment10";
    public static final String FIELD_ACCOUNT_SEGMENT11 = "accountSegment11";
    public static final String FIELD_ACCOUNT_SEGMENT12 = "accountSegment12";
    public static final String FIELD_ACCOUNT_SEGMENT13 = "accountSegment13";
    public static final String FIELD_ACCOUNT_SEGMENT14 = "accountSegment14";
    public static final String FIELD_ACCOUNT_SEGMENT15 = "accountSegment15";
    public static final String FIELD_ACCOUNT_SEGMENT16 = "accountSegment16";
    public static final String FIELD_ACCOUNT_SEGMENT17 = "accountSegment17";
    public static final String FIELD_ACCOUNT_SEGMENT18 = "accountSegment18";
    public static final String FIELD_ACCOUNT_SEGMENT19 = "accountSegment19";
    public static final String FIELD_ACCOUNT_SEGMENT20 = "accountSegment20";


    @Id
    @GeneratedValue
    private Long expReportJeLineId;

    /**
     * 报销单头ID
     */
    @NotNull
    private Long expReportHeaderId;

    /**
     * 报销单分配行ID
     */
    private Long expReportDistId;

    /**
     * 计划付款行ID
     */
    @Where
    private Long paymentScheduleLineId;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 凭证日期
     */
    private Date journalDate;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 来源代码
     */
    @NotEmpty
    @Length(max = 30)
    private String sourceCode;

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
     * 科目ID
     */
    @NotNull
    private Long accountId;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 原币借方金额
     */
    @Where
    private BigDecimal enteredAmountDr;

    /**
     * 原币贷方金额
     */
    @Where
    private BigDecimal enteredAmountCr;

    /**
     * 本币借方金额
     */
    @Where
    private BigDecimal functionalAmountDr;

    /**
     * 本币贷方金额
     */
    @Where
    private BigDecimal functionalAmountCr;

    /**
     * 总账标志
     */
    @NotEmpty
    @Length(max = 10)
    private String gldInterfaceFlag;

    /**
     * 进项税转出标志（Y：是/N：否）
     */
    @Length(max = 1)
    private String transferFlag;

    /**
     * 用途代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String usageCode;

    /**
     * 科目段1
     */
    @Length(max = 200)
    private String accountSegment1;

    /**
     * 科目段2
     */
    @Length(max = 200)
    private String accountSegment2;

    /**
     * 科目段3
     */
    @Length(max = 200)
    private String accountSegment3;

    /**
     * 科目段4
     */
    @Length(max = 200)
    private String accountSegment4;

    /**
     * 科目段5
     */
    @Length(max = 200)
    private String accountSegment5;

    /**
     * 科目段6
     */
    @Length(max = 200)
    private String accountSegment6;

    /**
     * 科目段7
     */
    @Length(max = 200)
    private String accountSegment7;

    /**
     * 科目段8
     */
    @Length(max = 200)
    private String accountSegment8;

    /**
     * 科目段9
     */
    @Length(max = 200)
    private String accountSegment9;

    /**
     * 科目段10
     */
    @Length(max = 200)
    private String accountSegment10;

    /**
     * 科目段11
     */
    @Length(max = 200)
    private String accountSegment11;

    /**
     * 科目段12
     */
    @Length(max = 200)
    private String accountSegment12;

    /**
     * 科目段13
     */
    @Length(max = 200)
    private String accountSegment13;

    /**
     * 科目段14
     */
    @Length(max = 200)
    private String accountSegment14;

    /**
     * 科目段15
     */
    @Length(max = 200)
    private String accountSegment15;

    /**
     * 科目段16
     */
    @Length(max = 200)
    private String accountSegment16;

    /**
     * 科目段17
     */
    @Length(max = 200)
    private String accountSegment17;

    /**
     * 科目段18
     */
    @Length(max = 200)
    private String accountSegment18;

    /**
     * 科目段19
     */
    @Length(max = 200)
    private String accountSegment19;

    /**
     * 科目段20
     */
    @Length(max = 200)
    private String accountSegment20;

    /**
     * 报销单行Id
     */
    @Transient
    private Long expLineId;

    /**
     * 原币金额差异
     */
    @Transient
    private BigDecimal enteredAmount;

    /**
     * 本币金额差异
     */
    @Transient
    private BigDecimal functionalAmount;

    /**
     * 成本中心名称
     */
    @Transient
    private String respCenterName;

    /**
     * 科目名称
     */
    @Transient
    private String accountName;

    /**
     * 事物类型
     */
    @Transient
    private String transactionType;

    /**
     * 事物类型描述
     */
    @Transient
    private String transactionTypeDesc;

    /**
     * 核算主体名称
     */
    @Transient
    private String accEntityName;

    /**
     * 币种名称
     */
    @Transient
    private String currencyName;

    /**
     * 科目编码
     */
    @Transient
    private String accountCode;
}
