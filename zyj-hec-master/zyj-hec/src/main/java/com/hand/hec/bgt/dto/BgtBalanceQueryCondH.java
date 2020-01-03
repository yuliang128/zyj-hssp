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

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 预算余额查询方案头dto
 * </p>
 *
 * @author YHL 2019/03/13 18:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "bgt_balance_query_cond_h")
@MultiLanguage
public class BgtBalanceQueryCondH extends BaseDTO {

    public static final String FIELD_BALANCE_QUERY_COND_H_ID = "balanceQueryCondHId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_BALANCE_QUERY_CONDITION_CODE = "balanceQueryConditionCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BUDGET_STRC_CODE = "budgetStrcCode";
    public static final String FIELD_SCENARIO_CODE = "scenarioCode";
    public static final String FIELD_VERSION_CODE = "versionCode";
    public static final String FIELD_AMOUNT_FLAG = "amountFlag";
    public static final String FIELD_QUANTITY_FLAG = "quantityFlag";
    public static final String FIELD_PERIOD_SUMMARY_FLAG = "periodSummaryFlag";
    public static final String FIELD_LAST_UPDATE_DATE = "lastUpdateDate";

    public static final String FIELD_CONDITION_CODE = "conditionCode";
    public static final String FIELD_CONDITION_DESC = "conditionDesc";
    public static final String FIELD_PERIOD_QUARTER_FROM = "periodQuarterFrom";
    public static final String FIELD_PERIOD_QUARTER_TO = "periodQuarterTo";
    public static final String FIELD_PERIOD_YEAR_FROM = "periodYearFrom";
    public static final String FIELD_PERIOD_YEAR_TO = "periodYearTo";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_BUDGET_VERSION = "budgetVersion";
    public static final String FIELD_BUDGET_STRUCTURE = "budgetStructure";
    public static final String FIELD_BUDGET_STRUCTURE_DESC = "budgetStructureDesc";
    public static final String FIELD_BUDGET_SCENARIO = "budgetScenario";
    public static final String FIELD_BUDGET_SCENARIO_DESC = "budgetScenarioDesc";
    public static final String FIELD_PERIOD_FROM = "periodFrom";
    public static final String FIELD_PERIOD_TO = "periodTo";
    public static final String FIELD_COMPANYLID = "companylid";
    public static final String FIELD_BUDGETVERSIONLID = "budgetversionlid";
    public static final String FIELD_BUDGETSCENARIOLID = "budgetscenariolid";
    public static final String FIELD_BUDGETSTRUCTURELID = "budgetstructurelid";
    public static final String FIELD_PERIODLID = "periodlid";
    public static final String FIELD_PERIODQUARTERLID = "periodquarterlid";
    public static final String FIELD_PERIODYEARLID = "periodyearlid";
    public static final String FIELD_PERIOD_STRATEGY = "periodStrategy";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_STRUCTURE_CODE = "structureCode";
    public static final String FIELD_STRUCTURE_NAME = "structureName";


    @Id
    @GeneratedValue
    private Long balanceQueryCondHId;

    /**
     * 公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 方案代码
     */
    @NotEmpty
    @Length(max = 30)
    private String balanceQueryConditionCode;

    /**
     * 描述
     */
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 预算表代码
     */
    @Length(max = 30)
    private String budgetStrcCode;

    /**
     * 预算场景代码
     */
    @Length(max = 30)
    private String scenarioCode;

    /**
     * 预算版本代码
     */
    @Length(max = 30)
    private String versionCode;

    /**
     * 金额标志
     */
    @Length(max = 1)
    private String amountFlag;

    /**
     * 数量标志
     */
    @Length(max = 1)
    private String quantityFlag;

    /**
     * 期间汇总标志
     */
    @Length(max = 1)
    private String periodSummaryFlag;

    /**
     * 最后更新日期
     */
    private Date lastUpdateDate;

    /**
     * 方案代码
     */
    @Transient
    @Length(max = 30)
    private String conditionCode;

    /**
     * 描述
     */
    @Transient
    @Length(max = 500)
    private String conditionDesc;

    /**
     * 季度从
     */
    @Transient
    @Length(max = 100)
    private String periodQuarterFrom;

    /**
     * 季度到
     */
    @Transient
    @Length(max = 100)
    private String periodQuarterTo;

    /**
     * 年度从
     */
    @Transient
    @Length(max = 100)
    private String periodYearFrom;

    /**
     * 年度到
     */
    @Transient
    @Length(max = 100)
    private String periodYearTo;

    /**
     * 公司代码
     */
    @Transient
    @Length(max = 100)
    private String companyCode;

    /**
     * 预算版本描述
     */
    @Transient
    @Length(max = 500)
    private String budgetVersion;

    /**
     * 预算表代码
     */
    @Transient
    @Length(max = 100)
    private String budgetStructure;

    /**
     * 预算表描述
     */
    @Transient
    @Length(max = 500)
    private String budgetStructureDesc;

    /**
     * 预算场景代码
     */
    @Transient
    @Length(max = 100)
    private String budgetScenario;

    /**
     * 预算场景描述
     */
    @Transient
    @Length(max = 500)
    private String budgetScenarioDesc;

    /**
     * 期间从
     */
    @Transient
    @Length(max = 100)
    private String periodFrom;

    /**
     * 期间到
     */
    @Transient
    @Length(max = 100)
    private String periodTo;

    /**
     * 公司ID（方案行）
     */
    @Transient
    private Long companylid;

    /**
     * 预算版本ID（方案行）
     */
    @Transient
    private Long budgetversionlid;

    /**
     * 预算场景ID（方案行）
     */
    @Transient
    private Long budgetscenariolid;

    /**
     * 预算表ID（方案行）
     */
    @Transient
    private Long budgetstructurelid;

    /**
     * 预算期间ID（方案行）
     */
    @Transient
    private Long periodlid;

    /**
     * 季度ID（方案行）
     */
    @Transient
    private Long periodquarterlid;

    /**
     * 年度ID（方案行）
     */
    @Transient
    private Long periodyearlid;

    /**
     * 编制期段（SYSCODE：BUDGET_PERIOD）
     */
    @Transient
    private String periodStrategy;

    /**
     * 预算表ID
     */
    @Transient
    private Long structureId;

    /**
     * 预算表代码
     */
    @Transient
    private String structureCode;

    /**
     * 预算表描述
     */
    @Transient
    private String structureName;

}
