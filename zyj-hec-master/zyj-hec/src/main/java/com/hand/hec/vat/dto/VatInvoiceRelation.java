package com.hand.hec.vat.dto;

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

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "vat_invoice_relation")
public class VatInvoiceRelation extends BaseDTO {

    public static final String FIELD_RELATION_ID = "relationId";
    public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
    public static final String FIELD_DOCUMENT_DIST_ID = "documentDistId";
    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_INVOICE_LINE_ID = "invoiceLineId";
    public static final String FIELD_BUSINESS_AMOUNT = "businessAmount";
    public static final String FIELD_BIZ2PAY_EXCHANGE_RATE = "biz2payExchangeRate";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_PAY2FUN_EXCHANGE_RATE = "pay2funExchangeRate";
    public static final String FIELD_FUNCTIONAL_AMOUNT = "functionalAmount";


    @Id
    @GeneratedValue
    private Long relationId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String documentCategory;

    /**
     * 单据
     */
    @NotNull
    private Long documentId;

    /**
     * 单据行
     */
    @NotNull
    private Long documentLineId;

    /**
     * 单据分配行
     */
    private Long documentDistId;

    /**
     * 发票
     */
    private Long invoiceId;

    /**
     * 发票行
     */
    private Long invoiceLineId;

    /**
     * 实际报销业务金额
     */
    private Long businessAmount;

    /**
     * 业务币种->支付币种汇率
     */
    private Long biz2payExchangeRate;

    /**
     * 实际报销支付币种金额
     */
    private Long paymentAmount;

    /**
     * 支付币种->本位币汇率
     */
    private Long pay2funExchangeRate;

    /**
     * 实际报销本位币金额
     */
    private Long functionalAmount;


    @Transient
    private String invoiceCode;

    @Transient
    private String invoiceNumber;

    @Transient
    private Date invoiceDate;

    @Transient
    private String invoiceType;

    @Transient
    private String invoiceSource;

    @Transient
    private Long invoiceCategoryId;

    @Transient
    private Long invoiceUsedeId;

    @Transient
    private BigDecimal amount;

    @Transient
    private BigDecimal taxAmount;

    @Transient
    private Long taxTypeId;

    @Transient
    private Long vatCount;

    @Transient
    private Long taxCount;

    @Transient
    private Long vatGCount;

}
