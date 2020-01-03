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
 * 预算占用币种扩展表
 * </p>
 * 
 * @author mouse 2019/01/07 16:19
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_reserve_extend")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetReserveExtend extends BaseDTO {

    public static final String FIELD_BUDGET_RESERVE_LINE_ID = "budgetReserveLineId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_EXCHANGE_DATE = "exchangeDate";
    public static final String FIELD_EXCHANGE_PERIOD_NAME = "exchangePeriodName";


    /**
     * 预算占用ID
     */
    @NotNull
    private Long budgetReserveLineId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    @Id
    @GeneratedValue
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
     * 兑换日期
     */
    private Date exchangeDate;

    /**
     * 兑换期间
     */
    @NotEmpty
    @Length(max = 30)
    private String exchangePeriodName;

}
