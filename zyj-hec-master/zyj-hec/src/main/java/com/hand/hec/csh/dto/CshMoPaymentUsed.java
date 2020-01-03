package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
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
/**
 * <p>
 * 付款用途DTO
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_payment_used")
@MultiLanguage
public class CshMoPaymentUsed extends BaseDTO {

     public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_PAYMENT_USEDE_CODE = "paymentUsedeCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_USEDES_CODE_NAME = "usedesCodeName";


     @Id
     @GeneratedValue
     private Long paymentUsedeId;

    /**
     * 管理组织ID
     */
     @NotNull
     private Long magOrgId;

    /**
     * 付款用途代码
     */
     @NotEmpty
     @Length(max = 30)
     private String paymentUsedeCode;

     @Transient
     private String usedesCodeName;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     @MultiLanguageField
     private String description;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
