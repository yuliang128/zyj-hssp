package com.hand.hec.vat.dto;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "vat_invoice_relation_ln")
public class VatInvoiceRelationLn extends BaseDTO {

    public static final String FIELD_RELATION_LNS_ID = "relationLnsId";
    public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_INVOICE_LINE_ID = "invoiceLineId";
    public static final String FIELD_BUSINESS_AMOUNT = "businessAmount";
    public static final String FIELD_BIZ2PAY_EXCHANGE_RATE = "biz2payExchangeRate";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_PAY2FUN_EXCHANGE_RATE = "pay2funExchangeRate";
    public static final String FIELD_FUNCTIONAL_AMOUNT = "functionalAmount";


    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long relationLnsId;

    /**
     * 发票ID
     */
    @NotEmpty
    @Length(max = 30)
    private String documentCategory;

    /**
     * 单据ID
     */
    @NotNull
    private Long documentId;

    /**
     * 单据行ID
     */
    @NotNull
    private Long documentLineId;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 发票行ID
     */
    private Long invoiceLineId;

    /**
     * 实际报销业务金额
     */
    private BigDecimal businessAmount;

    /**
     * 业务币种->支付币种汇率
     */
    private BigDecimal biz2payExchangeRate;

    /**
     * 实际报销支付币种金额
     */
    private BigDecimal paymentAmount;

    /**
     * 支付币种->本位币汇率
     */
    private BigDecimal pay2funExchangeRate;

    /**
     * 实际报销本位币金额
     */
    private BigDecimal functionalAmount;

    @Transient
    private Long taxTypeId;

    @Transient
    private BigDecimal taxAmount;

}
