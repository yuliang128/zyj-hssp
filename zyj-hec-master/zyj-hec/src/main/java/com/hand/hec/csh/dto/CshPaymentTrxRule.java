package com.hand.hec.csh.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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

@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_trx_rule")
public class CshPaymentTrxRule extends BaseDTO {

    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_RULE_CODE = "ruleCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BUSINESS_TYPE_CODE = "businessTypeCode";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    /**
     * 增加付款事务业务规则的取值方式
     * 
     * @FILTRATE_METHOD_INCLUDE 包含
     * @FILTRATE_METHOD_EXCLUDE 排除
     */
    public static final String FILTRATE_METHOD_INCLUDE = "INCLUDE";
    public static final String FILTRATE_METHOD_EXCLUDE = "EXCLUDE";

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
     * 优先级
     */
    @NotNull
    private Long priority;

    /**
     * 规则代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String ruleCode;

    /**
     * 规则描述
     */
    @Where
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 业务类别（SYSCODE：CSH_PAYMENT_DOCUMENT_CATEGORY）
     */
    @Length(max = 30)
    private String businessTypeCode;

    /**
     * 有效期从
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDateActive;

    /**
     * 有效期到
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDateActive;

}
