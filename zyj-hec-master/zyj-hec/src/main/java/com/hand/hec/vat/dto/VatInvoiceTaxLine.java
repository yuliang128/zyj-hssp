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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "vat_invoice_tax_line")
public class VatInvoiceTaxLine extends BaseDTO {

     public static final String FIELD_TAX_LINE_ID = "taxLineId";
     public static final String FIELD_INVOICE_LINE_ID = "invoiceLineId";
     public static final String FIELD_TAX_TYPE_ID = "taxTypeId";
     public static final String FIELD_TAX_RATE = "taxRate";
     public static final String FIELD_TAX_AMOUNT = "taxAmount";


     @Id
     @GeneratedValue
     private Long taxLineId;

    /**
     * 发票行
     */
     @NotNull
     private Long invoiceLineId;

    /**
     * 税率类型
     */
     private Long taxTypeId;

    /**
     * 税率
     */
     private Long taxRate;

    /**
     * 税金
     */
     @NotNull
     private Long taxAmount;

     }
