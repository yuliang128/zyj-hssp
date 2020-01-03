package com.hand.hap.gld.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 币种dto
 * </p>
 *
 * @author MouseZhou 2019/01/04 18:41
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gld_currency")
@ExtensionAttribute(disable = true)
@MultiLanguage
public class GldCurrency extends BaseDTO {

    public final static String FIELD_CURRENCY_ID = "currencyId";
    public final static String FIELD_CURRENCY_CODE = "currencyCode";
    public final static String FIELD_ENABLED_FLAG = "enabledFlag";
    public final static String FIELD_CURRENCY_NAME = "currencyName";
    public final static String FIELD_COUNTRY_CODE = "countryCode";
    public final static String FIELD_CURRENCY_CODE_FROM = "currencyCodeFrom";
    public final static String FIELD_CURRENCY_CODE_TO = "currencyCodeTo";
    public final static String FIELD_FINANCE_PRECISION = "financePrecision";
    public final static String FIELD_TRANSACTION_PRECISION = "transactionPrecision";
    public final static String FIELD_CURRENCY_SYMBOL = "currencySymbol";

    @Id
    @GeneratedValue
    @Where
    private Long currencyId;

    @NotEmpty
    @Where
    private String currencyCode;

    @NotEmpty
    @Where
    private String enabledFlag;

    @MultiLanguageField
    @Where
    private String currencyName;

    @Where
    private String countryCode;

    @Where
    private Long financePrecision;

    @Where
    private Long transactionPrecision;

    @Where
    private String currencySymbol;

    @Transient
    @Where
    private String currencyCodeFrom;

    @Transient
    @Where
    private String currencyCodeTo;

}
