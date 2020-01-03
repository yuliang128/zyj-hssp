package com.hand.hec.fnd.dto;


/**
 *
 * 汇率定义dto
 *
 * @weikun.wang xxx YYYY-mm-dd
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
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
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_exchange_rate")
public class GldExchangeRate extends BaseDTO {

     public static final String FIELD_CONVERSION_RATE = "conversionRate";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_CONVERSION_DATE = "conversionDate";
     public static final String FIELD_EXCHANGE_RATE_ID = "exchangeRateId";
     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_FROM_CURRENCY = "fromCurrency";
     public static final String FIELD_CURRENCY_VOLUME = "currencyVolume";
     public static final String FIELD_TO_CURRENCY = "toCurrency";
     public static final String FIELD_PERIOD_FROM = "periodFrom";
     public static final String FIELD_PERIOD_TO = "periodTo";
     public static final String FIELD_DATE_FROM = "dateFrom";
     public static final String FIELD_DATE_TO = "dateTo";


    /**
     * 兑换货币数量
     */
     private BigDecimal conversionRate;

    /**
     * 期间
     */
     @Length(max = 30)
     @Where
     private String periodName;

    /**
     * 日期
     */
     @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
     private Date conversionDate;

     @Id
     @GeneratedValue
     private Long exchangeRateId;

    /**
     * 汇率类型ID
     */
     @NotNull
     @Where
     private Long typeId;

    /**
     * 原货币
     */
     @NotEmpty
     @Length(max = 10)
     @Where
     @JoinTable(name = "fromCurrencyJoin",joinMultiLanguageTable = false,target = GldCurrency.class,
     type = JoinType.LEFT,on = {@JoinOn(joinField = GldCurrency.FIELD_CURRENCY_CODE)})
     private String fromCurrency;

    @Transient
    @JoinColumn(joinName = "fromCurrencyJoin", field = GldCurrency.FIELD_CURRENCY_NAME)
    private String fromCurrencyName;


    @Transient
    @Where(expression = "period_name >= #{dto.periodFrom}")
    private String periodFrom;

    @Transient
    @Where(expression = "period_name <= #{dto.periodTo}")
    private String periodTo;


    @Transient
    @Where(expression = "conversion_date >= str_to_date(#{dto.dateFrom},'%Y-%m-%d %H') ")
    private String dateFrom;

    @Transient
    @Where(expression = "conversion_date <= str_to_date(#{dto.dateTo},'%Y-%m-%d %H')  ")
    private String dateTo;

    /**
     * 原货币数量
     */
     private BigDecimal currencyVolume;

    /**
     * 兑换货币
     */
     @NotEmpty
     @Length(max = 10)
     @Where
     @JoinTable(name = "toCurrencyJoin",joinMultiLanguageTable = false,target = GldCurrency.class,
             type = JoinType.LEFT,on = {@JoinOn(joinField = GldCurrency.FIELD_CURRENCY_CODE)})
     private String toCurrency;


    @Transient
    @JoinColumn(joinName = "toCurrencyJoin", field = GldCurrency.FIELD_CURRENCY_NAME)
    private String toCurrencyName;

     }
