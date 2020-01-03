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
 * 预算占用币种扩展备份
 * </p>
 *
 * @author mouse 2019/01/07 16:14
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_reserve_extend_bak")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetReserveExtendBak extends BaseDTO {

    public static final String FIELD_BUDGET_RESERVE_LINE_ID = "budgetReserveLineId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_EXCHANGE_DATE = "exchangeDate";
    public static final String FIELD_EXCHANGE_PERIOD_NAME = "exchangePeriodName";


    @NotNull
    private Long budgetReserveLineId;

    private BigDecimal amount;

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
