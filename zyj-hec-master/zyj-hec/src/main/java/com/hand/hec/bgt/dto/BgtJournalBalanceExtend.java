package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算余额币种扩展
 * </p>
 * 
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_balance_extend")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalBalanceExtend extends BaseDTO {

    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_EXCHANGE_DATE = "exchangeDate";
    public static final String FIELD_EXCHANGE_PERIOD_NAME = "exchangePeriodName";
    public static final String FIELD_BALANCE_ID = "balanceId";
    public static final String FIELD_PERIOD_AMOUNT = "periodAmount";
    public static final String FIELD_QUARTER_AMOUNT = "quarterAmount";
    public static final String FIELD_YEAR_AMOUNT = "yearAmount";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue
    private Long balanceExtendId;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 汇兑日期
     */
    private Date exchangeDate;

    /**
     * 汇兑期间
     */
    @NotEmpty
    @Length(max = 30)
    private String exchangePeriodName;

    /**
     * 预算余额ID
     */
    @NotNull
    private Long balanceId;

    /**
     * 期间金额
     */
    private BigDecimal periodAmount;

    /**
     * 季度金额
     */
    private BigDecimal quarterAmount;

    /**
     * 年度金额
     */
    private BigDecimal yearAmount;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

}
