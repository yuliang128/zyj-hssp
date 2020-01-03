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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "vat_invoice_rel_tax_ln")
public class VatInvoiceRelTaxLn extends BaseDTO {

     public static final String FIELD_RELATION_TAX_LINE_ID = "relationTaxLineId";
     public static final String FIELD_RELATION_ID = "relationId";
     public static final String FIELD_TAX_LINE_ID = "taxLineId";
     public static final String FIELD_TAX_TYPE_ID = "taxTypeId";
     public static final String FIELD_SPLITTED_TAX_AMOUNT = "splittedTaxAmount";
     public static final String FIELD_PAYMENT_SP_TAX_AMOUNT = "paymentSpTaxAmount";
     public static final String FIELD_FUNCTIONAL_SP_TAX_AMOUNT = "functionalSpTaxAmount";
     public static final String FIELD_REPORT_TAX_AMOUNT = "reportTaxAmount";
     public static final String FIELD_PAYMENT_RP_TAX_AMOUNT = "paymentRpTaxAmount";
     public static final String FIELD_FUNCTIONAL_RP_TAX_AMOUNT = "functionalRpTaxAmount";
     public static final String FIELD_INVOICE_ITEM_ID = "invoiceItemId";
     public static final String FIELD_INVOICE_USEDE_ID = "invoiceUsedeId";
     public static final String FIELD_MANUAL_SPLITTED_FLAG = "manualSplittedFlag";


     @Id
     @GeneratedValue
     private Long relationTaxLineId;

    /**
     * 发票关联
     */
     @NotNull
     private Long relationId;

    /**
     * 发票税金行
     */
     private Long taxLineId;

    /**
     * 税率类型
     */
     private Long taxTypeId;

    /**
     * 拆分税金
     */
     @NotNull
     private Long splittedTaxAmount;

    /**
     * 支付币种拆分税金（拆分税金 * 业务币种->支付币种汇率）
     */
     @NotNull
     private Long paymentSpTaxAmount;

    /**
     * 本位币拆分税金（支付币种拆分税金 * 支付币种->本位币汇率）
     */
     @NotNull
     private Long functionalSpTaxAmount;

    /**
     * 实际报销税金
     */
     @NotNull
     private Long reportTaxAmount;

    /**
     * 支付币种实际报销税金（实际报销税金 * 业务币种->支付币种汇率）
     */
     @NotNull
     private Long paymentRpTaxAmount;

    /**
     * 本位币实际报销税金（支付币种实际报销税金 * 支付币种->本位币汇率）
     */
     @NotNull
     private Long functionalRpTaxAmount;

    /**
     * 发票项目
     */
     private Long invoiceItemId;

    /**
     * 发票用途
     */
     private Long invoiceUsedeId;

    /**
     * 手动拆分标志
     */
     @NotEmpty
     @Length(max = 1)
     private String manualSplittedFlag;

     }
