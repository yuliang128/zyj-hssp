package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import java.util.Date;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "csh_payment_batch_rule")
public class CshPaymentBatchRule extends BaseDTO {

    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_RULE_CODE = "ruleCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TYPE_ID = "typeId";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    /**
     * 增加付款批决定规则的取值方式
     *
     * @FILTRATE_METHOD_INCLUDE 包含
     * @FILTRATE_METHOD_EXCLUDE 排除
     */
    public static final String FILTRATE_METHOD_INCLUDE = "INCLUDE";
    public static final String FILTRATE_METHOD_EXCLUDE = "EXCLUDE";

    @Where
    @Id
    @GeneratedValue
    private Long ruleId;

    /**
     * 管理组织ID
     */
    @Where
    @NotNull
    private Long magOrgId;

    /**
     * 优先级
     */
    @NotNull
    private Long priority;

    /**
     * 规则代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String ruleCode;

    /**
     * 规则名称
     */
    @Where
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 付款批类型ID
     */
    @NotNull
    @JoinTable(name = "payBatchTypeJoin", target = CshPaymentBatchType.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = CshPaymentBatchType.FIELD_TYPE_ID)})
    private Long typeId;

    @Transient
    @JoinColumn(joinName = "payBatchTypeJoin", field = CshPaymentBatchType.FIELD_TYPE_CODE)
    private String typeCode;
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

}
