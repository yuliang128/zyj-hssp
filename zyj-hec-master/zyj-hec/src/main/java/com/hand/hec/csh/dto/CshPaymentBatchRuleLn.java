package com.hand.hec.csh.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.hand.hap.core.BaseConstants;
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
@Table(name = "csh_payment_batch_rule_ln")
public class CshPaymentBatchRuleLn extends BaseDTO {

    public static final String FIELD_RULE_LNS_ID = "ruleLnsId";
    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_RULE_CODE = "ruleCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TYPE_CODE = "typeCode";
    public static final String FIELD_CUSTOM_FUNCTION = "customFunction";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    /**
     * 增加类型ID [查询付款批决定规则时带出付款批类型]
     */
    public static final String FIELD_TYPE_ID = "typeId";

    /**
     * 增加付款批决定规则类型代码
     *
     * @TYPE_CODE_10 参数
     * @TYPE_CODE_20 函数
     */
    public static final String TYPE_CODE_10 = "10";
    public static final String TYPE_CODE_20 = "20";

    @Id
    @GeneratedValue
    private Long ruleLnsId;

    /**
     * 付款批规则定义ID
     */
    @Where
    @NotNull
    private Long ruleId;

    /**
     * 明细代码
     */
    @NotEmpty
    @Length(max = 30)
    private String ruleCode;

    /**
     * 名称ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 类型代码（SYSCODE：BGT_STRATEGY_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String typeCode;

    /**
     * 函数
     */
    @Length(max = 2000)
    private String customFunction;

    /**
     * 有效期从
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date startDateActive;

    /**
     * 有效期到
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date endDateActive;

    /**
     * 付款批类型ID
     */
    @Transient
    private Long typeId;

}
