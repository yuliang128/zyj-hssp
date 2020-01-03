package com.hand.hec.csh.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
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
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_rule_detail")
public class CshPaymentRuleDetail extends BaseDTO {

     public static final String FIELD_RULE_DETAIL_ID = "ruleDetailId";
     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_SEQUENCE = "sequence";
     public static final String FIELD_AND_OR = "andOr";
     public static final String FIELD_LEFT_PARENTHESIS = "leftParenthesis";
     public static final String FIELD_RIGHT_PARENTHESIS = "rightParenthesis";
     public static final String FIELD_RULE_PARAMETER_ID = "ruleParameterId";
     public static final String FIELD_CONDITION_TYPE = "conditionType";
     public static final String FIELD_CONDITION_VALUE = "conditionValue";
     public static final String FIELD_SQL_PARAM_1 = "sqlParam_1";
     public static final String FIELD_SQL_PARAM_2 = "sqlParam_2";
     public static final String FIELD_SQL_PARAM_3 = "sqlParam_3";
     public static final String FIELD_SQL_PARAM_4 = "sqlParam_4";


     @Id
     @GeneratedValue
     private Long ruleDetailId;

    /**
     * 支付规则ID
     */
     @Where
     @NotNull
     private Long ruleId;

    /**
     * 序号
     */
     @NotNull
     private Long sequence;

    /**
     * AND/OR
     */
     @NotEmpty
     @Length(max = 30)
     private String andOr;

    /**
     * 左括号
     */
     @Length(max = 30)
     private String leftParenthesis;

    /**
     * 右括号
     */
     @Length(max = 30)
     private String rightParenthesis;

    /**
     * 权限规则参数ID
     */
     @NotNull
     @JoinTable(name = "ruleParameterJoin",joinMultiLanguageTable = false,target = com.hand.hec.csh.dto.CshPaymentRuleParameter.class,
     type = JoinType.LEFT,on={@JoinOn(joinField = CshPaymentRuleParameter.FIELD_RULE_PARAMETER_ID)})
     private Long ruleParameterId;

    /**
     * 权限规则参数代码
     */
     @Transient
     @JoinColumn(joinName ="ruleParameterJoin",field = CshPaymentRuleParameter.FIELD_PARAMETER_CODE )
     private String ruleParameterCode;

    /**
     * 权限规则参数名称
     */
     @Transient
     @JoinColumn(joinName ="ruleParameterJoin",field = CshPaymentRuleParameter.FIELD_DESCRIPTION )
     private String ruleParameterName;

    /**
     * 条件类型
     */
     @NotEmpty
     @Length(max = 30)
     private String conditionType;

    /**
     * 条件值
     */
     @NotEmpty
     @Length(max = 100)
     private String conditionValue;

    /**
     * 参数1
     */
     @Length(max = 100)
     private String sqlParam_1;

    /**
     * 参数2
     */
     @Length(max = 100)
     private String sqlParam_2;

    /**
     * 参数3
     */
     @Length(max = 100)
     private String sqlParam_3;

    /**
     * 参数4
     */
     @Length(max = 100)
     private String sqlParam_4;




     }
