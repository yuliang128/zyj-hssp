package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.fnd.dto.GldExchangerateType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 预算日记账行
 * </p>
 * 
 * @author mouse 2019/01/07 16:34
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_line")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalLine extends BaseDTO {

    public static final String FIELD_JOURNAL_LINE_ID = "journalLineId";
    public static final String FIELD_JOURNAL_HEADER_ID = "journalHeaderId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_LINE_PERIOD_NAME = "linePeriodName";
    public static final String FIELD_LINE_PERIOD_QUARTER = "linePeriodQuarter";
    public static final String FIELD_LINE_PERIOD_YEAR = "linePeriodYear";
    public static final String FIELD_LINE_BGT_PERIOD_NUM = "lineBgtPeriodNum";
    public static final String FIELD_CURRENCY = "currency";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_FUNCTIONAL_AMOUNT = "functionalAmount";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_UOM = "uom";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_JOURNAL_LINES_NOTES = "journalLinesNotes";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_DIMENSION1_ID = "dimension1Id";
    public static final String FIELD_DIMENSION2_ID = "dimension2Id";
    public static final String FIELD_DIMENSION3_ID = "dimension3Id";
    public static final String FIELD_DIMENSION4_ID = "dimension4Id";
    public static final String FIELD_DIMENSION5_ID = "dimension5Id";
    public static final String FIELD_DIMENSION6_ID = "dimension6Id";
    public static final String FIELD_DIMENSION7_ID = "dimension7Id";
    public static final String FIELD_DIMENSION8_ID = "dimension8Id";
    public static final String FIELD_DIMENSION9_ID = "dimension9Id";
    public static final String FIELD_DIMENSION10_ID = "dimension10Id";
    public static final String FIELD_DIMENSION11_ID = "dimension11Id";
    public static final String FIELD_DIMENSION12_ID = "dimension12Id";
    public static final String FIELD_DIMENSION13_ID = "dimension13Id";
    public static final String FIELD_DIMENSION14_ID = "dimension14Id";
    public static final String FIELD_DIMENSION15_ID = "dimension15Id";
    public static final String FIELD_DIMENSION16_ID = "dimension16Id";
    public static final String FIELD_DIMENSION17_ID = "dimension17Id";
    public static final String FIELD_DIMENSION18_ID = "dimension18Id";
    public static final String FIELD_DIMENSION19_ID = "dimension19Id";
    public static final String FIELD_DIMENSION20_ID = "dimension20Id";

    public static final String FIELD_BALANCE_COMPANY_ID = "balanceCompanyId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_BUDGET_JOURNAL_NUMBER = "budgetJournalNumber";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
    public static final String FIELD_JOURNAL_HEADER_NOTES = "journalHeaderNotes";
    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_VERSION_ID = "versionId";



    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long journalLineId;

    /**
     * 预算日记账头ID
     */
    @NotNull
    @Where
    private Long journalHeaderId;

    /**
     * 预算实体ID
     */
    @JoinTable(name = "entityJoin", target = BgtEntity.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtEntityId;

    @Transient
    @JoinColumn(joinName = "entityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    private String bgtEntityName;

    /**
     * 预算中心ID
     */
    @JoinTable(name = "centerJoin", target = BgtCenter.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtCenter.FIELD_CENTER_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtCenterId;

    @Transient
    @JoinColumn(joinName = "centerJoin", field = BgtCenter.FIELD_DESCRIPTION)
    private String bgtCenterName;

    /**
     * 预算项目ID
     */
    @NotNull
    @JoinTable(name = "itemJoin", target = BgtBudgetItem.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtBudgetItem.FIELD_BUDGET_ITEM_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long budgetItemId;

    @Transient
    @JoinColumn(joinName = "itemJoin", field = BgtBudgetItem.FIELD_DESCRIPTION)
    private String budgetItemName;

    /**
     * 期间
     */
    @Length(max = 30)
    private String linePeriodName;

    /**
     * 季度
     */
    private Long linePeriodQuarter;

    /**
     * 年度
     */
    private Long linePeriodYear;

    /**
     * 预算期间数
     */
    private Long lineBgtPeriodNum;

    /**
     * 币种
     */
    @Length(max = 30)
    @JoinTable(name = "currencyJoin", target = GldCurrency.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = GldCurrency.FIELD_CURRENCY_CODE),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private String currency;

    @Transient
    @JoinColumn(joinName = "currencyJoin", field = GldCurrency.FIELD_CURRENCY_NAME)
    private String currencyName;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    @JoinTable(name = "exchangeRateJoin", target = GldExchangerateType.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = GldExchangerateType.FIELD_TYPE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private String exchangeRateType;

    @Transient
    @JoinColumn(joinName = "exchangeRateJoin", field = GldExchangerateType.FIELD_DESCRIPTION)
    private String exchangeRateTypeName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 本币金额
     */
    private BigDecimal functionalAmount;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */
    @Length(max = 30)
    private String uom;

    /**
     * 管理公司ID
     */
    @NotNull
    @JoinTable(name = "companyJoin", target = Company.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = Company.FIELD_COMPANY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long companyId;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = Company.FIELD_COMPANY_SHORT_NAME)
    private String companyName;

    /**
     * 部门ID
     */
    @JoinTable(name = "unitJoin", target = ExpOrgUnit.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = ExpOrgUnit.FIELD_UNIT_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long unitId;

    @Transient
    @JoinColumn(joinName = "unitJoin", field = ExpOrgUnit.FIELD_DESCRIPTION)
    private String unitName;

    /**
     * 岗位ID
     */
    @JoinTable(name = "positionJoin", target = ExpOrgPosition.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = ExpOrgPosition.FIELD_POSITION_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long positionId;

    @Transient
    @JoinColumn(joinName = "positionJoin", field = ExpOrgPosition.FIELD_DESCRIPTION)
    private String positionName;

    /**
     * 员工ID
     */
    @JoinTable(name = "employeeJoin", target = ExpEmployee.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;

    @Transient
    @JoinColumn(joinName = "employeeJoin", field = ExpEmployee.FIELD_NAME)
    private String employeeName;

    /**
     * 行备注
     */
    @Length(max = 2000)
    private String journalLinesNotes;

    /**
     * 经营单位ID（废弃）
     */
    private Long operationUnitId;

    /**
     * 维度1
     */
    @JoinTable(name = "dim1Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension1Id;


    @Transient
    @JoinColumn(joinName = "dim1Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension1Name;

    /**
     * 维度2
     */
    @JoinTable(name = "dim2Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension2Id;


    @Transient
    @JoinColumn(joinName = "dim2Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension2Name;

    /**
     * 维度3
     */
    @JoinTable(name = "dim3Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension3Id;

    @Transient
    @JoinColumn(joinName = "dim3Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension3Name;

    /**
     * 维度4
     */
    @JoinTable(name = "dim4Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension4Id;


    @Transient
    @JoinColumn(joinName = "dim4Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension4Name;

    /**
     * 维度5
     */
    @JoinTable(name = "dim5Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension5Id;


    @Transient
    @JoinColumn(joinName = "dim5Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension5Name;

    /**
     * 维度6
     */
    @JoinTable(name = "dim6Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension6Id;

    @Transient
    @JoinColumn(joinName = "dim6Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension6Name;

    /**
     * 维度7
     */
    @JoinTable(name = "dim7Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension7Id;

    @Transient
    @JoinColumn(joinName = "dim7Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension7Name;

    /**
     * 维度8
     */
    @JoinTable(name = "dim8Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension8Id;

    @Transient
    @JoinColumn(joinName = "dim8Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension8Name;

    /**
     * 维度9
     */
    @JoinTable(name = "dim9Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension9Id;

    @Transient
    @JoinColumn(joinName = "dim9Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension9Name;

    /**
     * 维度10
     */
    @JoinTable(name = "dim10Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension10Id;

    @Transient
    @JoinColumn(joinName = "dim10Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension10Name;

    /**
     * 维度11
     */
    @JoinTable(name = "dim11Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension11Id;

    @Transient
    @JoinColumn(joinName = "dim11Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension11Name;

    /**
     * 维度12
     */
    @JoinTable(name = "dim12Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension12Id;

    @Transient
    @JoinColumn(joinName = "dim12Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension12Name;

    /**
     * 维度13
     */
    @JoinTable(name = "dim13Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension13Id;

    @Transient
    @JoinColumn(joinName = "dim13Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension13Name;

    /**
     * 维度14
     */
    @JoinTable(name = "dim14Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension14Id;

    @Transient
    @JoinColumn(joinName = "dim14Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension14Name;

    /**
     * 维度15
     */
    @JoinTable(name = "dim15Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension15Id;

    @Transient
    @JoinColumn(joinName = "dim15Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension15Name;

    /**
     * 维度16
     */
    @JoinTable(name = "dim16Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension16Id;

    @Transient
    @JoinColumn(joinName = "dim16Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension16Name;

    /**
     * 维度17
     */
    @JoinTable(name = "dim17Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension17Id;

    @Transient
    @JoinColumn(joinName = "dim17Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension17Name;

    /**
     * 维度18
     */
    @JoinTable(name = "dim18Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension18Id;

    @Transient
    @JoinColumn(joinName = "dim18Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension18Name;

    /**
     * 维度19
     */
    @JoinTable(name = "dim19Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension19Id;

    @Transient
    @JoinColumn(joinName = "dim19Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension19Name;

    /**
     * 维度20
     */
    @JoinTable(name = "dim20Join", target = FndDimensionValue.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension20Id;

    @Transient
    @JoinColumn(joinName = "dim20Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension20Name;

    @Transient
    private Long balanceCompanyId;

    @Transient
    private Long bgtOrgId;

    @Transient
    private Long structureId;

    @Transient
    private String budgetJournalNumber;

    @Transient
    private String periodName;

    @Transient
    private Long periodQuarter;

    @Transient
    private Long periodYear;

    @Transient
    private Long bgtPeriodNum;

    @Transient
    private String journalHeaderNotes;

    @Transient
    private Long scenarioId;

    @Transient
    private Long versionId;

    @Transient
    private Long internalPeriodNum;

    @Transient
    private Long positionGroupId;

    @Transient
    private Long unitGroupId;

    @Transient
    private Long employeeGroupId;

}
