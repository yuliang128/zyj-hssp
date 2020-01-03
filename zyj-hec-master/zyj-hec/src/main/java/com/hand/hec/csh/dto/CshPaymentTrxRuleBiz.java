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
@Table(name = "csh_payment_trx_rule_biz")
public class CshPaymentTrxRuleBiz extends BaseDTO {

    public static final String FIELD_RULE_BIZ_ID = "ruleBizId";
    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_RULE_BIZ_CODE = "ruleBizCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TYPE_CODE = "typeCode";
    public static final String FIELD_CUSTOM_FUNCTION = "customFunction";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    /**
     * 增加付款事务规则业务规则类别
     * 
     * @TYPE_CODE_10 参数
     * @TYPE_CODE_20 函数
     */
    public static final String TYPE_CODE_10 = "10";
    public static final String TYPE_CODE_20 = "20";

    @Id
    @GeneratedValue
    private Long ruleBizId;

    /**
     * 付款事务生成规则ID
     */
    @NotNull
    @Where
    private Long ruleId;

    /**
     * 业务范围编码
     */
    @NotEmpty
    @Length(max = 30)
    private String ruleBizCode;

    /**
     * 业务范围描述
     */
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 类型代码（SYSCODE：BGT_STRATEGY_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String typeCode;

    /**
     * 函数名
     */
    @Length(max = 2000)
    private String customFunction;

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
