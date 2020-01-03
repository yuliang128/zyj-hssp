package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_rule_parameter")
public class CshPaymentRuleParameter extends BaseDTO {

     public static final String FIELD_RULE_PARAMETER_ID = "ruleParameterId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_PARAMETER_CODE = "parameterCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_SQL_CONTENTS = "sqlContents";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_BUSINESS_TYPE = "businessType";


     @Id
     @Where
     @GeneratedValue
     private Long ruleParameterId;

    /**
     * 管理组织ID
     */
     @NotNull
     @Where
     private Long magOrgId;

    /**
     * 参数代码
     */
     @NotEmpty
     @Where
     @Length(max = 30)
     private String parameterCode;

    /**
     * 参数名称
     */
     @Length(max = 500)
     @Where
     @MultiLanguageField
     private String description;

    /**
     * SQL内容
     */
     @NotEmpty
     @Length(max = 4000)
     private String sqlContents;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @NotEmpty
     @Length(max = 30)
     private String businessType;

     /**
     * 用于校验sql是否正确，正确返回true，否则返回false
     */
     @Transient
     private String resultFlag;
     }
