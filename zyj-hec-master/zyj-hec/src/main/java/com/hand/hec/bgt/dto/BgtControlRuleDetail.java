package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
/**
 * <p>
 * 预算控制规则明细
 * </p>
 * 
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_control_rule_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtControlRuleDetail extends BaseDTO {

    public static final String FIELD_CONTROL_RULE_ID = "controlRuleId";
    public static final String FIELD_CONTROL_RULE_DETAIL_ID = "controlRuleDetailId";
    public static final String FIELD_RULE_PARAMETER_TYPE = "ruleParameterType";
    public static final String FIELD_RULE_PARAMETER = "ruleParameter";
    public static final String FIELD_FILTRATE_METHOD = "filtrateMethod";
    public static final String FIELD_SUMMARY_OR_DETAIL = "summaryOrDetail";
    public static final String FIELD_PARAMETER_LOWER_LIMIT = "parameterLowerLimit";
    public static final String FIELD_PARAMETER_UPPER_LIMIT = "parameterUpperLimit";
    public static final String FIELD_USER_EXIT_PROCEDURE = "userExitProcedure";
    public static final String FIELD_INVALID_DATE = "invalidDate";
    public static final String FIELD_RULE_PARAMETER_DISPLAY = "ruleParameterDisplay";
    public static final String FIELD_RULE_PARAMETER_TYPE_NAME = "ruleParameterTypeName";
    public static final String FIELD_FILTRATE_METHOD_NAME = "filtrateMethodName";
    public static final String FIELD_SUMMARY_OR_DETAIL_NAME = "summaryOrDetailName";


    /**
     * 规则ID
     */
    @NotNull
    @Where
    private Long controlRuleId;

    /**
     * 规则明细ID
     */
    @Id
    @GeneratedValue
    private Long controlRuleDetailId;

    /**
     * 规则参数类型
     */
    @NotEmpty
    @Length(max = 30)
    private String ruleParameterType;

    /**
     * 规则参数
     */
    @NotEmpty
    @Length(max = 30)
    private String ruleParameter;

    /**
     * 取值方式
     */
    @NotEmpty
    @Length(max = 30)
    private String filtrateMethod;

    /**
     * 取值范围
     */
    @NotEmpty
    @Length(max = 30)
    private String summaryOrDetail;

    /**
     * 下限值
     */
    @Length(max = 100)
    private String parameterLowerLimit;

    /**
     * 上限制
     */
    @Length(max = 100)
    private String parameterUpperLimit;

    /**
     * 用户出口程序
     */
    @Length(max = 100)
    private String userExitProcedure;

    /**
     * 失效日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date invalidDate;

    @Transient
    private String ruleParameterDisplay;

    @Transient
    private String ruleParameterTypeName;

    @Transient
    private String filtrateMethodName;

    @Transient
    private String summaryOrDetailName;

}
