package com.hand.hec.bgt.dto;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 控制规则
 * </p>
 *
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_control_rule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtControlRule extends BaseDTO {

    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_CONTROL_RULE_ID = "controlRuleId";
    public static final String FIELD_CONTROL_RULE_CODE = "controlRuleCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_BUDGET_STRATEGY_GROUP_ID = "budgetStrategyGroupId";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_BUDGET_STRATEGY_DISPLAY = "budgetStrategyDisplay";


    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 规则ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long controlRuleId;

    /**
     * 规则代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String controlRuleCode;

    /**
     * 描述ID
     */
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    @Where
    private String description;

    /**
     * 优先级
     */
    @OrderBy("ASC")
    private Long priority;

    /**
     * 预算控制策略表
     */
    @NotNull
    @Where
    @JoinTable(name = "strategyJoin", joinMultiLanguageTable = false, target = BgtControlStrategyGroup.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = BgtControlStrategyGroup.FIELD_CONTROL_STRATEGY_GROUP_ID)})
    private Long budgetStrategyGroupId;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    @Where(expression = "start_date <= #{startDate}")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    @Where(expression = "end_date is null or a.end_date >= #{endDate}")
    private Date endDate;

    @Transient
    @JoinColumn(joinName = "strategyJoin",field = BgtControlStrategyGroup.FIELD_CONTROL_STRATEGY_GROUP_DESC)
    private String budgetStrategyDisplay;


}
