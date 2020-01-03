package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算日记账币种扩展备份
 * </p>
 *
 * @author mouse 2019/01/07 16:26
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_balance_extend_bak")
public class BgtJournalBalanceExtendBak extends BaseDTO {

    public static final String FIELD_BALANCE_ID = "balanceId";
    public static final String FIELD_PERIOD_AMOUNT = "periodAmount";
    public static final String FIELD_QUARTER_AMOUNT = "quarterAmount";
    public static final String FIELD_YEAR_AMOUNT = "yearAmount";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_EXCHANGE_DATE = "exchangeDate";
    public static final String FIELD_EXCHANGE_PERIOD_NAME = "exchangePeriodName";

    @Id
    @GeneratedValue
    private Long balanceExtendBakId;

    @NotNull
    private Long balanceId;

    private BigDecimal periodAmount;

    private BigDecimal quarterAmount;

    private BigDecimal yearAmount;

    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    @Length(max = 30)
    private String exchangeRateType;

    private BigDecimal exchangeRate;

    private Date exchangeDate;

    @NotEmpty
    @Length(max = 30)
    private String exchangePeriodName;

}
