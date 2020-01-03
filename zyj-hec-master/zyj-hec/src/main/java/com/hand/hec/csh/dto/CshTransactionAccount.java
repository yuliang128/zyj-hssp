package com.hand.hec.csh.dto;

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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_transaction_account")

/**
 * <p>
 * 现金事务凭证DTO
 * </p>
 *
 * @author Tagin 2019/01/22 20:04
 */
public class CshTransactionAccount extends BaseDTO {

    public static final String FIELD_TRANSACTION_JE_LINE_ID = "transactionJeLineId";
    public static final String FIELD_TRANSACTION_LINE_ID = "transactionLineId";
    public static final String FIELD_DISTRIBUTION_LINE_ID = "distributionLineId";
    public static final String FIELD_WRITE_OFF_ID = "writeOffId";
    public static final String FIELD_SOURCE_CODE = "sourceCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_ENTERED_AMOUNT_DR = "enteredAmountDr";
    public static final String FIELD_ENTERED_AMOUNT_CR = "enteredAmountCr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_DR = "functionalAmountDr";
    public static final String FIELD_FUNCTIONAL_AMOUNT_CR = "functionalAmountCr";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_JOURNAL_DATE = "journalDate";
    public static final String FIELD_JOURNAL_DATE_TZ = "journalDateTz";
    public static final String FIELD_JOURNAL_DATE_LTZ = "journalDateLtz";
    public static final String FIELD_CASH_CLEARING_FLAG = "cashClearingFlag";
    public static final String FIELD_BANK_ACCOUNT_FLAG = "bankAccountFlag";
    public static final String FIELD_GLD_INTERFACE_FLAG = "gldInterfaceFlag";
    public static final String FIELD_USAGE_CODE = "usageCode";
    public static final String FIELD_BATCH_JE_LINE_ID = "batchJeLineId";
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

    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_ACC_ENTITY_CODE = "accEntityCode";
    public static final String FIELD_RESP_CENTER_CODE = "respCenterCode";
    public static final String FIELD_RESP_CENTER_NAME = "respCenterName";
    public static final String FIELD_ACCOUNT_CODE = "accountCode";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_CURRENCY_NAME = "currencyName";


    /**
     * 来源代码
     */
    public static final String SOURCE_CODE_CSH_PAYMENT = "CSH_PAYMENT";


    public static final String GLD_INTERFACE_FLAG_Y = "Y";
    public static final String GLD_INTERFACE_FLAG_P = "P";
    public static final String GLD_INTERFACE_FLAG_N = "N";

    @Id
    @GeneratedValue
    private Long transactionJeLineId;

    /**
     * 现金事物行ID
     */
    @NotNull
    @Where
    private Long transactionLineId;

    /**
     * 现金事物分配行ID
     */
    private Long distributionLineId;

    /**
     * 核销ID
     */
    private Long writeOffId;

    /**
     * 来源代码
     */
    @NotEmpty
    @Length(max = 30)
    private String sourceCode;

    /**
     * 描述
     */
    @Length(max = 2000)
    private String description;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 公司
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体
     */
    @NotNull
    private Long accEntityId;

    /**
     * 责任中心
     */
    @NotNull
    private Long respCenterId;

    /**
     * 科目
     */
    @NotNull
    private Long accountId;

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
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 交易日期
     */
    private Date journalDate;

    /**
     * 交易日期_当地时区
     */
    private Date journalDateTz;

    /**
     * 交易日期_查询时区
     */
    private Date journalDateLtz;

    /**
     * 现金清算标志
     */
    @Length(max = 1)
    private String cashClearingFlag;

    /**
     * 银行账户标志
     */
    @Length(max = 1)
    private String bankAccountFlag;

    /**
     * 总账标志
     */
    @NotEmpty
    @Length(max = 1)
    private String gldInterfaceFlag;

    /**
     * 用途代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String usageCode;

    /**
     * 付款批凭证ID
     */
    private Long batchJeLineId;

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

    @Transient
    private String accEntityName;

    @Transient
    private String accEntityCode;

    @Transient
    private String respCenterCode;

    @Transient
    private String respCenterName;

    @Transient
    private String accountCode;

    @Transient
    private String accountName;

    @Transient
    private String currencyName;

}
