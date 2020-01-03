package com.hand.hec.bgt.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description
 *
 * @author guiyuting 2019/03/29 11:01
 */
@ExtensionAttribute(disable = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetCurrencyTmp {

    public static final String FIELD_FROM_CURRENCY_CODE = "fromCurrencyCode";
    public static final String FIELD_TO_CURRENCY_CODE = "toCurrencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_EXCHANGE_DATE = "exchangeDate";
    public static final String FIELD_EXCHANGE_PERIOD_NAME = "exchangePeriodName";


    private String fromCurrencyCode;

    private String toCurrencyCode;

    private String exchangeRateType;

    private BigDecimal exchangeRate;

    private Date exchangeDate;

    private String exchangePeriodName;

}
