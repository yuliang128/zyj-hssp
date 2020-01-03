package com.hand.hec.csh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MultiLanguage
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_rule")
public class CshPaymentRule extends BaseDTO {

     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_RULE_CODE = "ruleCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_START_DATE = "startDate";
     public static final String FIELD_END_DATE = "endDate";
     public static final String FIELD_BUSINESS_TYPE = "businessType";
     public static final String FIELD_RULE_CODE_TO = "ruleCodeTo";
     public static final String FIELD_RULE_CODE_FROM = "ruleCodeFrom";


    /**
     * 主键
     */
     @Id
     @GeneratedValue
     private Long ruleId;

    /**
     * 管理组织ID
     */
     @NotNull
     @Where
     private Long magOrgId;

    /**
     * 权限规则代码
     */
     @NotEmpty
     @Length(max = 30)
     private String ruleCode;

    /**
     * 权限规则代码名称
     */
     @MultiLanguageField
     @Length(max = 500)
     private String description;

    /**
     * 有效日期从
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;

    /**
     * 有效日期到
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;

    /**
     * 业务类别(SYSCODE:CSH_PAYMENT_DOCUMENT_CATEGORY)
     */
    @NotEmpty
    @Length(max = 30)
    private String businessType;

    @Where(expression ="rule_code >= #{ruleCodeFrom}" )
    @Transient
    private String ruleCodeFrom;

    @Where(expression ="rule_code <= #{ruleCodeTo}" )
    @Transient
    private String ruleCodeTo;

     }
