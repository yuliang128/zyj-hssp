package com.hand.hec.csh.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.hand.hap.core.BaseConstants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CshDocPayment {

    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_PAYMENT_DATE = "paymentDate";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYMENT_METHOD_CODE = "paymentMethodCode";
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * 支付核算主体
     */
    private Long accEntityId;

    /**
     * 付款日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date paymentDate;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 汇率类型
     */
    private String exchangeRateType;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 付款银行账户ID
     */
    private Long bankAccountId;

    /**
     * 现金流量项
     */
    private Long cashFlowItemId;

    /**
     * 付款方式
     */
    private Long paymentMethodId;

    /**
     * 付款方式代码
     */
    private String paymentMethodCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 支付数组
     */
    private List<CshDocPayAccEntity> data;

}
