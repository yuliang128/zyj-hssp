package com.hand.hec.vat.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
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
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "vat_invoice_category")
@MultiLanguage
public class VatInvoiceCategory extends BaseDTO {

     public static final String FIELD_INVOICE_CATEGORY_ID = "invoiceCategoryId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_INVOICE_CATEGORY_CODE = "invoiceCategoryCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_INVOICE_ATTRIBUTES = "invoiceAttributes";
     public static final String FIELD_AUTHENTICATING_FLAG = "authenticatingFlag";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long invoiceCategoryId;

    /**
     * 管理组织
     */
     @NotNull
     @Where
     private Long magOrgId;

    /**
     * 发票种类代码
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String invoiceCategoryCode;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     @Where(comparison = Comparison.LIKE)
     @MultiLanguageField
     private String description;

    /**
     * 发票属性（SYSCODE：VAT_INVOICE_ATTRIBUTES）
     */
     @NotEmpty
     @Length(max = 30)
     private String invoiceAttributes;

    /**
     * 认证标志
     */
     @NotEmpty
     @Length(max = 1)
     private String authenticatingFlag;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
