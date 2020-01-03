package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.fnd.dto.FndDimensionValue;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算日记账头
 * </p>
 * 
 * @author mouse 2019/01/07 16:34
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_header")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalHeader extends BaseDTO {

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
    public static final String FIELD_JOURNAL_HEADER_ID = "journalHeaderId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_BUDGET_JOURNAL_NUMBER = "budgetJournalNumber";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_VERSION_ID = "versionId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_REVERSED_FLAG = "reversedFlag";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_SOURCE_BUDGET_HEADER_ID = "sourceBudgetHeaderId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_APPROVED_DATE = "approvedDate";
    public static final String FIELD_APPROVED_BY = "approvedBy";
    public static final String FIELD_POSTED_DATE = "postedDate";
    public static final String FIELD_POSTED_BY = "postedBy";
    public static final String FIELD_JOURNAL_HEADER_NOTES = "journalHeaderNotes";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_DIMENSION1_ID = "dimension1Id";
    public static final String FIELD_PERIOD_STRATEGY = "periodStrategy";

    /**
     * 新建
     */
    public static final String STATUS_NEW = "NEW";

    /**
     * 提交
     */
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    /**
     * 过账
     */
    public static final String STATUS_POSTED = "POSTED";
    /**
     * 收回
     */
    public static final String STATUS_WITHDRAWAL = "WITHDRAWAL";
    /**
     * 审批
     */
    public static final String STATUS_APPROVED = "APPROVED";
    /**
     * 拒绝
     */
    public static final String STATUS_REJECTED = "REJECTED";
    /**
     * 取消审批
     */
    public static final String STATUS_CANCELAPPROVED = "CANCELAPPROVED";
    /**
     * 反冲
     */
    public static final String STATUS_REVERSED = "REVERSED";

    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long journalHeaderId;

    /**
     * 预算组织ID
     */
    @NotNull
    @JoinTable(name = "orgJoin", target = BgtOrganization.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtOrganization.FIELD_BGT_ORG_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtOrgId;

    /**
     * 预算实体ID
     */
    @JoinTable(name = "entityJoin", target = BgtEntity.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtEntityId;

    /**
     * 预算中心ID
     */
    @JoinTable(name = "centerJoin", target = BgtCenter.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtCenter.FIELD_CENTER_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtCenterId;

    /**
     * 管理公司ID
     */
    @NotNull
    @JoinTable(name = "companyJoin", target = Company.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = Company.FIELD_COMPANY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long companyId;

    /**
     * 预算日记账类型ID
     */
    @NotNull
    @JoinTable(name = "bgtJournalTypeJoin", target = BgtJournalType.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = BgtJournalType.FIELD_BGT_JOURNAL_TYPE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long bgtJournalTypeId;

    /**
     * 预算表ID
     */
    @NotNull
    @JoinTable(name = "structureJoin", target = BgtStructure.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtStructure.FIELD_STRUCTURE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long structureId;

    /**
     * 预算日记账编号
     */
    @Length(max = 30)
    private String budgetJournalNumber;

    /**
     * 年度
     */
    private Long periodYear;

    /**
     * 季度
     */
    private Long periodQuarter;

    /**
     * 期间
     */
    @Length(max = 30)
    private String periodName;

    /**
     * 预算期间数
     */
    private Long bgtPeriodNum;

    /**
     * 预算场景ID
     */
    @NotNull
    @JoinTable(name = "scenarioJoin", target = BgtScenario.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtScenario.FIELD_SCENARIO_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long scenarioId;

    /**
     * 预算版本ID
     */
    @NotNull
    @JoinTable(name = "versionJoin", target = BgtVersion.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = BgtVersion.FIELD_VERSION_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long versionId;

    /**
     * 状态
     */
    @NotEmpty
    @Length(max = 30)
    private String status;

    /**
     * 反冲标志
     */
    @Length(max = 1)
    private String reversedFlag;

    /**
     * 来源类型
     */
    @Length(max = 30)
    private String sourceType;

    /**
     * 来源日记账头ID
     */
    private Long sourceBudgetHeaderId;

    /**
     * 员工ID
     */
    @JoinTable(name = "employeeJoin", target = ExpEmployee.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;

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
     * 审批日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date approvedDate;

    /**
     * 审批人
     */
    @JoinTable(name = "approvedJoin", target = ExpEmployee.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long approvedBy;

    /**
     * 过账日期
     */
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date postedDate;

    /**
     * 过账人
     */
    @JoinTable(name = "postedJoin", target = ExpEmployee.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long postedBy;

    /**
     * 头备注
     */
    @Length(max = 2000)
    private String journalHeaderNotes;

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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
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
                    joinMultiLanguageTable = false,
                    on = {@JoinOn(joinField = FndDimensionValue.FIELD_DIMENSION_VALUE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long dimension20Id;

    @Transient
    @JoinColumn(joinName = "dim20Join", field = FndDimensionValue.FIELD_DESCRIPTION)
    private String dimension20Name;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date approvedDateFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date approvedDateTo;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date creationDateFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date creationDateTo;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date postedDateFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date postedDateTo;

    @Transient
    @JoinColumn(joinName = "approvedJoin", field = ExpEmployee.FIELD_NAME)
    private String approvedByName;

    @Transient
    @JoinColumn(joinName = "postedJoin", field = ExpEmployee.FIELD_NAME)
    private String postedByName;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = Company.FIELD_COMPANY_SHORT_NAME)
    private String companyName;

    @Transient
    @JoinCode(code = "BGT_JOURNAL_REVERSE_FLAG", joinKey = FIELD_REVERSED_FLAG)
    private String reversedFlagName;

    @Transient
    @JoinColumn(joinName = "versionJoin", field = BgtVersion.FIELD_DESCRIPTION)
    private String versionName;

    @Transient
    @JoinColumn(joinName = "scenarioJoin", field = BgtScenario.FIELD_DESCRIPTION)
    private String scenarioName;

    @Transient
    @JoinColumn(joinName = "structureJoin", field = BgtScenario.FIELD_DESCRIPTION)
    private String structureName;

    @Transient
    @JoinColumn(joinName = "centerJoin", field = BgtCenter.FIELD_DESCRIPTION)
    private String bgtCenterName;

    @Transient
    @JoinColumn(joinName = "entityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    private String bgtEntityName;

    @Transient
    @JoinColumn(joinName = "bgtJournalTypeJoin", field = BgtJournalType.FIELD_DESCRIPTION)
    private String bgtJournalTypeName;

    @Transient
    @JoinColumn(joinName = "employeeJoin", field = ExpEmployee.FIELD_NAME)
    private String employeeName;

    @Transient
    private Long budgetItemId;

    @Transient
    private String budgetItemName;

    @Transient
    @JoinColumn(joinName = "orgJoin", field = BgtOrganization.FIELD_DESCRIPTION)
    private String bgtOrgName;

    @Transient
    private String creationDateScope;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date creationDateScopeFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date creationDateScopeTo;

    @Transient
    @JoinCode(code = "BGT_BUDGET_STATUS", joinKey = FIELD_STATUS)
    private String statusName;

    @Transient
    @JoinColumn(joinName = "bgtJournalTypeJoin", field = BgtJournalType.FIELD_AUTHORITY_TYPE)
    private String authorityType;

    @Transient
    @JoinColumn(joinName = "structureJoin", field = BgtStructure.FIELD_PERIOD_STRATEGY)
    private String periodStrategy;

    @Transient
    @JoinCode(code = "BUDGET_PERIOD", joinKey = FIELD_PERIOD_STRATEGY)
    private String periodStrategyName;

    @Transient
    @JoinColumn(joinName = "structureJoin", field = BgtStructure.FIELD_ENTITY_FLAG)
    private String entityFlag;

    @Transient
    @JoinColumn(joinName = "structureJoin", field = BgtStructure.FIELD_CENTER_FLAG)
    private String centerFlag;

    @Transient
    private String currencySymbol;

    @Transient
    private BigDecimal totalAmount;


    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date docDate;

    @Transient
    private List<BgtJournalLine> journalLines;

    // 预算日记账反冲原因
    @Transient
    private String reason;

    @Transient
    private Long progressCount;

    public Long getProgressCount() {
        if(status == null){
            return 0L;
        }
        switch (status) {
            case STATUS_NEW:
            case STATUS_REJECTED:
            case STATUS_WITHDRAWAL:
                return 25L;
            case STATUS_SUBMITTED:
                return 50L;
            case STATUS_APPROVED:
                return 75L;
            case STATUS_POSTED:
                return 100L;
            default:
                return 0L;
        }
    }

}
