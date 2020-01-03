package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * <p>
 * 预算余额
 * </p>
 * 
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_balance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalBalance extends BaseDTO {

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
    public static final String FIELD_BALANCE_ID = "balanceId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_VERSION_ID = "versionId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_PERIOD_AMOUNT = "periodAmount";
    public static final String FIELD_PERIOD_FUNCTIONAL_AMOUNT = "periodFunctionalAmount";
    public static final String FIELD_PERIOD_QUANTITY = "periodQuantity";
    public static final String FIELD_QUARTER_AMOUNT = "quarterAmount";
    public static final String FIELD_QUARTER_FUNCTIONAL_AMOUNT = "quarterFunctionalAmount";
    public static final String FIELD_QUARTER_QUANTITY = "quarterQuantity";
    public static final String FIELD_YEAR_AMOUNT = "yearAmount";
    public static final String FIELD_YEAR_FUNCTIONAL_AMOUNT = "yearFunctionalAmount";
    public static final String FIELD_YEAR_QUANTITY = "yearQuantity";
    public static final String FIELD_UOM = "uom";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_UNIT_GROUP_ID = "unitGroupId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_POSITION_GROUP_ID = "positionGroupId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_GROUP_ID = "employeeGroupId";
    public static final String FIELD_DIMENSION1_ID = "dimension1Id";
    public static final String FIELD_DIMENSION2_ID = "dimension2Id";
    public static final String FIELD_DIMENSION3_ID = "dimension3Id";
    public static final String FIELD_DIMENSION4_ID = "dimension4Id";
    public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";


    /**
     * 维度5
     */
    private Long dimension5Id;

    /**
     * 维度6
     */
    private Long dimension6Id;

    /**
     * 维度7
     */
    private Long dimension7Id;

    /**
     * 维度8
     */
    private Long dimension8Id;

    /**
     * 维度9
     */
    private Long dimension9Id;

    /**
     * 维度10
     */
    private Long dimension10Id;

    /**
     * 维度11
     */
    private Long dimension11Id;

    /**
     * 维度12
     */
    private Long dimension12Id;

    /**
     * 维度13
     */
    private Long dimension13Id;

    /**
     * 维度14
     */
    private Long dimension14Id;

    /**
     * 维度15
     */
    private Long dimension15Id;

    /**
     * 维度16
     */
    private Long dimension16Id;

    /**
     * 维度17
     */
    private Long dimension17Id;

    /**
     * 维度18
     */
    private Long dimension18Id;

    /**
     * 维度19
     */
    private Long dimension19Id;

    /**
     * 维度20
     */
    private Long dimension20Id;

    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long balanceId;

    /**
     * 预算组织ID
     */
    @NotNull
    private Long bgtOrgId;

    /**
     * 预算实体ID
     */
    private Long bgtEntityId;

    /**
     * 预算中心ID
     */
    private Long bgtCenterId;

    /**
     * 预算表ID
     */
    @NotNull
    private Long structureId;

    /**
     * 预算场景ID
     */
    @NotNull
    private Long scenarioId;

    /**
     * 预算版本ID
     */
    @NotNull
    private Long versionId;

    /**
     * 预算项目ID
     */
    @NotNull
    private Long budgetItemId;

    /**
     * 期间年份
     */
    private Long periodYear;

    /**
     * 期间季度
     */
    private Long periodQuarter;

    /**
     * 期间名称
     */
    @Length(max = 30)
    private String periodName;

    /**
     * 期间数
     */
    private Long bgtPeriodNum;

    /**
     * 币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 期间金额
     */
    private BigDecimal periodAmount;

    /**
     * 期间本币金额
     */
    private BigDecimal periodFunctionalAmount;

    /**
     * 期间数量
     */
    private BigDecimal periodQuantity;

    /**
     * 季度金额
     */
    private BigDecimal quarterAmount;

    /**
     * 季度本币金额
     */
    private BigDecimal quarterFunctionalAmount;

    /**
     * 季度数量
     */
    private BigDecimal quarterQuantity;

    /**
     * 年度金额
     */
    private BigDecimal yearAmount;

    /**
     * 年度本币金额
     */
    private BigDecimal yearFunctionalAmount;

    /**
     * 年度数量
     */
    private BigDecimal yearQuantity;

    /**
     * 单位
     */
    @Length(max = 30)
    private String uom;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 经营单位ID
     */
    private Long operationUnitId;

    /**
     * 部门ID
     */
    private Long unitId;

    /**
     * 部门组ID
     */
    private Long unitGroupId;

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 岗位组ID
     */
    private Long positionGroupId;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 员工组ID
     */
    private Long employeeGroupId;

    /**
     * 维度1
     */
    private Long dimension1Id;

    /**
     * 维度2
     */
    private Long dimension2Id;

    /**
     * 维度3
     */
    private Long dimension3Id;

    /**
     * 维度4
     */
    private Long dimension4Id;

    /**
     * 期间序号
     */
    @NotNull
    private Long internalPeriodNum;
}
