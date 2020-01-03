package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 预算余额查询方案行dto
 * </p>
 *
 * @author YHL 2019/03/13 18:17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "bgt_balance_query_cond_l")
public class BgtBalanceQueryCondL extends BaseDTO {

    public static final String FIELD_BALANCE_QUERY_COND_H_ID = "balanceQueryCondHId";
    public static final String FIELD_BALANCE_QUERY_COND_L_ID = "balanceQueryCondLId";
    public static final String FIELD_SESSION_ID = "sessionId";
    public static final String FIELD_PARAMETER = "parameter";
    public static final String FIELD_PARAMETER_ID = "parameterId";
    public static final String FIELD_PARAMETER_CODE = "parameterCode";
    public static final String FIELD_PARAMETER_UPPER_LIMIT = "parameterUpperLimit";
    public static final String FIELD_PARAMETER_LOWER_LIMIT = "parameterLowerLimit";
    public static final String FIELD_CONTROL_RULE_RANGE = "controlRuleRange";
    public static final String FIELD_CREATION_DATE = "creationDate";
    public static final String FIELD_LAST_UPDATE_DATE = "lastUpdateDate";

    public static final String FIELD_PARAMETER_NAME = "parameterName";
    public static final String FIELD_POSITION_CODE_FROM_DESC = "positionCodeFromDesc";
    public static final String FIELD_POSITION_CODE_TO_DESC = "positionCodeToDesc";
    public static final String FIELD_POSITION_CODE = "positionCode";
    public static final String FIELD_POSITION_CODE_FROM = "positionCodeFrom";
    public static final String FIELD_POSITION_CODE_TO = "positionCodeTo";
    public static final String FIELD_DIMENSION_SEQUENCE = "dimensionSequence";
    public static final String FIELD_SYSTEM_LEVEL = "systemLevel";
    public static final String FIELD_COMPANY_LEVEL = "companyLevel";
    public static final String FIELD_BALANCE_QUERY_CONDITION_CODE = "balanceQueryConditionCode";


    /**
     * 预算余额查询方案头ID
     */
    @NotNull
    private Long balanceQueryCondHId;

    @Id
    @GeneratedValue
    private Long balanceQueryCondLId;

    /**
     * 系统SESSION ID
     */
    @NotNull
    private String sessionId;

    /**
     * 参数类型
     */
    @NotEmpty
    @Length(max = 100)
    private String parameter;

    /**
     * 参数ID
     */
    private Long parameterId;

    /**
     * 参数代码
     */
    @Length(max = 100)
    private String parameterCode;

    /**
     * 参数代码止值
     */
    @Length(max = 100)
    private String parameterUpperLimit;

    /**
     * 参数代码始值
     */
    @Length(max = 100)
    private String parameterLowerLimit;

    /**
     * 取值范围
     */
    @Length(max = 100)
    private String controlRuleRange;

    /**
     * 创建日期
     */
    private Date creationDate;

    /**
     * 最后更新日期
     */
    private Date lastUpdateDate;

    /**
     * 参数名称
     */
    @Transient
    @Length(max = 100)
    private String parameterName;

    /**
     * 参数值描述从
     */
    @Transient
    @Length(max = 100)
    private String positionCodeFromDesc;

    /**
     * 参数值描述到
     */
    @Transient
    @Length(max = 100)
    private String positionCodeToDesc;

    /**
     * 参数代码
     */
    @Transient
    @Length(max = 100)
    private String positionCode;

    /**
     * 参数值从
     */
    @Transient
    @Length(max = 100)
    private String positionCodeFrom;

    /**
     * 参数值到
     */
    @Transient
    @Length(max = 100)
    private String positionCodeTo;

    /**
     * 维度序号
     */
    @Transient
    private Long dimensionSequence;

    /**
     * 系统级类型（SYSCODE：SYS_ORGANIZATION_TYPE）
     */
    @Transient
    @Length(max = 30)
    private String systemLevel;

    /**
     * 公司级类型（SYSCODE：SYS_ORGANIZATION_TYPE）
     */
    @Transient
    @Length(max = 30)
    private String companyLevel;

    /**
     * 方案代码
     */
    @Transient
    @Length(max = 30)
    private String balanceQueryConditionCode;

}
