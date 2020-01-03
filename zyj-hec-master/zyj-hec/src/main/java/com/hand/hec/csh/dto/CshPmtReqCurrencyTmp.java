package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_pmt_req_currency_tmp")
public class CshPmtReqCurrencyTmp extends BaseDTO {

     public static final String FIELD_SESSION_ID = "sessionId";
     public static final String FIELD_CURRENCY_CODE = "currencyCode";
     public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
     public static final String FIELD_EXCHANGE_RATE = "exchangeRate";


    /**
     * 系统SESSION ID
     */
     @NotNull
     private Long sessionId;

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
     private Long exchangeRate;

     }
