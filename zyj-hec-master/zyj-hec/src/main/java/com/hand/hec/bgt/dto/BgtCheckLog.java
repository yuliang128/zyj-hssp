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
/**
 * <p>
 * 预算检查日志
 * </p>
 * 
 * @author mouse 2019/01/07 16:22
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_check_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCheckLog extends BaseDTO {

    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_DOCUMENT_COMPANY_ID = "documentCompanyId";
    public static final String FIELD_DOCUMENT_OPERATION_UNIT_ID = "documentOperationUnitId";
    public static final String FIELD_DOCUMENT_TYPE = "documentType";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
    public static final String FIELD_DOCUMENT_DIST_ID = "documentDistId";
    public static final String FIELD_BUDGET_RESERVE_LINE_ID = "budgetReserveLineId";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_CONTROL_STRATEGY_ID = "controlStrategyId";
    public static final String FIELD_BUDGET_CONTROL_RULE_ID = "budgetControlRuleId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_VERSION_ID = "versionId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_BGT_BUDGET_AMOUNT = "bgtBudgetAmount";
    public static final String FIELD_BGT_REVERSE_AMOUNT = "bgtReverseAmount";
    public static final String FIELD_BGT_REVERSE_AMOUNT_R = "bgtReverseAmountR";
    public static final String FIELD_BGT_REVERSE_AMOUNT_U = "bgtReverseAmountU";
    public static final String FIELD_BGT_BUDGET_QUANTITY = "bgtBudgetQuantity";
    public static final String FIELD_BGT_RESERVE_QUANTITY = "bgtReserveQuantity";
    public static final String FIELD_AVAILABLE_AMOUNT = "availableAmount";
    public static final String FIELD_AVAILABLE_QUANTITY = "availableQuantity";
    public static final String FIELD_UOM = "uom";
    public static final String FIELD_BUDGET_FORMULA = "budgetFormula";
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


    /**
     * 预算组织
     */
    @NotNull
    private Long bgtOrgId;

    /**
     * 预算实体
     */
    private Long bgtEntityId;

    /**
     * 预算中心
     */
    private Long bgtCenterId;

    /**
     * 单据公司
     */
    @NotNull
    private Long documentCompanyId;

    /**
     * 单据经营单位
     */
    private Long documentOperationUnitId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String documentType;

    /**
     * 单据头ID
     */
    @NotNull
    private Long documentId;

    /**
     * 单据行ID
     */
    private Long documentLineId;

    /**
     * 单据分配行ID
     */
    private Long documentDistId;

    /**
     * 预算占用行ID
     */
    @NotNull
    private Long budgetReserveLineId;

    /**
     * 期间
     */
    @Length(max = 30)
    private String periodName;

    /**
     * 预算控制策略ID
     */
    @NotNull
    private Long controlStrategyId;

    /**
     * 预算控制规则ID
     */
    @NotNull
    private Long budgetControlRuleId;

    /**
     * 预算表
     */
    @NotNull
    private Long structureId;

    /**
     * 预算场景
     */
    @NotNull
    private Long scenarioId;

    /**
     * 预算版本
     */
    @NotNull
    private Long versionId;

    /**
     * 预算项目
     */
    private Long budgetItemId;

    /**
     * 责任中心
     */
    private Long responsibilityCenterId;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 金额
     */
    private Long amount;

    /**
     * 预算余额金额
     */
    private Long bgtBudgetAmount;

    /**
     * 预算占用金额
     */
    private Long bgtReverseAmount;

    /**
     * 预算占用金额_申请
     */
    private Long bgtReverseAmountR;

    /**
     * 预算占用金额_占用
     */
    private Long bgtReverseAmountU;

    /**
     * 预算余额数量
     */
    private Long bgtBudgetQuantity;

    /**
     * 预算占用数量
     */
    private Long bgtReserveQuantity;

    /**
     * 可用金额
     */
    private Long availableAmount;

    /**
     * 可用数量
     */
    private Long availableQuantity;

    /**
     * 计量单位
     */
    @Length(max = 30)
    private String uom;

    /**
     * 预算计算公式
     */
    @Length(max = 2000)
    private String budgetFormula;

    /**
     * 公司
     */
    private Long companyId;

    /**
     * 经营单位
     */
    private Long operationUnitId;

    /**
     * 部门
     */
    private Long unitId;

    /**
     * 部门组
     */
    private Long unitGroupId;

    /**
     * 岗位
     */
    private Long positionId;

    /**
     * 岗位组
     */
    private Long positionGroupId;

    /**
     * 员工
     */
    private Long employeeId;

    /**
     * 员工组
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

}
