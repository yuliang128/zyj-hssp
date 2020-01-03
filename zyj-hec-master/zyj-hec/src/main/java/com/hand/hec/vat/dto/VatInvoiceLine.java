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
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "vat_invoice_line")
public class VatInvoiceLine extends BaseDTO {

     public static final String FIELD_INVOICE_LINE_ID = "invoiceLineId";
     public static final String FIELD_INVOICE_ID = "invoiceId";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_GOODS_OR_TAXABLE_SERVICE = "goodsOrTaxableService";
     public static final String FIELD_SPECIFICATIONS = "specifications";
     public static final String FIELD_UNIT = "unit";
     public static final String FIELD_QUANTITY = "quantity";
     public static final String FIELD_UNIT_PRICE = "unitPrice";
     public static final String FIELD_WITHOUT_TAX_AMOUNT = "withoutTaxAmount";
     public static final String FIELD_TAX_RATE = "taxRate";
     public static final String FIELD_TAX_AMOUNT = "taxAmount";
     public static final String FIELD_TAX_TYPE_ID = "taxTypeId";


    /**
     * 主键
     */
     @Id
     @GeneratedValue
     private Long invoiceLineId;

    /**
     * 发票ID
     */
     @NotNull
     private Long invoiceId;

    /**
     * 发票行号
     */
     @NotNull
     private Long lineNumber;

    /**
     * 商品或可计税服务
     */
     @Length(max = 2000)
     private String goodsOrTaxableService;

    /**
     * 规格型号
     */
     @Length(max = 200)
     private String specifications;

    /**
     * 单位
     */
     @Length(max = 200)
     private String unit;

    /**
     * 数量
     */
     private BigDecimal quantity;

    /**
     * 单价
     */
     private BigDecimal unitPrice;

    /**
     * 不含税金额
     */
     private BigDecimal withoutTaxAmount;

    /**
     * 税率
     */
     private BigDecimal taxRate;

    /**
     * 税额
     */
     private BigDecimal taxAmount;

    /**
     * 税率类型
     */
     private Long taxTypeId;

     }
