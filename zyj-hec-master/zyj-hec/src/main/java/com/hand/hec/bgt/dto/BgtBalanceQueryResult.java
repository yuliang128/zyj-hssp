package com.hand.hec.bgt.dto;

import java.math.BigDecimal;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * 预算余额查询结果
 * </p>
 *
 * @author YHL 2019/03/13 18:32
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ExtensionAttribute(disable = true)
@Table(name = "bgt_balance_query_result")
public class BgtBalanceQueryResult extends BaseDTO {

    public static final String FIELD_SESSION_ID = "sessionId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_VERSION_ID = "versionId";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_BUDGET_ITEM_TYPE_ID = "budgetItemTypeId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_BGT_AMOUNT = "bgtAmount";
    public static final String FIELD_BGT_FUN_AMOUNT = "bgtFunAmount";
    public static final String FIELD_BGT_QUANTITY = "bgtQuantity";
    public static final String FIELD_EXP_RESERVE_AMOUNT = "expReserveAmount";
    public static final String FIELD_EXP_RESERVE_FUN_AMOUNT = "expReserveFunAmount";
    public static final String FIELD_EXP_RESERVE_QUANTITY = "expReserveQuantity";
    public static final String FIELD_EXP_USED_AMOUNT = "expUsedAmount";
    public static final String FIELD_EXP_USED_FUN_AMOUNT = "expUsedFunAmount";
    public static final String FIELD_EXP_USED_QUANTITY = "expUsedQuantity";
    public static final String FIELD_EXP_AVAILABLE_AMOUNT = "expAvailableAmount";
    public static final String FIELD_EXP_AVAILABLE_FUN_AMOUNT = "expAvailableFunAmount";
    public static final String FIELD_EXP_AVAILABLE_QUANTITY = "expAvailableQuantity";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_UNIT_GROUP_ID = "unitGroupId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_POSITION_GROUP_ID = "positionGroupId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_GROUP_ID = "employeeGroupId";
    public static final String FIELD_EMPLOYEE_LEVEL_ID = "employeeLevelId";
    public static final String FIELD_EMPLOYEE_JOB_ID = "employeeJobId";
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
    public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";


    /**
     * 系统SESSION ID
     */
    private String sessionId;

    /**
     * 预算组织ID
     */
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
    private Long structureId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 预算版本ID
     */
    private Long versionId;

    /**
     * 经营单位ID
     */
    private Long operationUnitId;

    /**
     * 预算场景ID
     */
    private Long scenarioId;

    /**
     * 预算项目类型ID
     */
    private Long budgetItemTypeId;

    /**
     * 预算项目ID
     */
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
    private String periodName;

    /**
     * 期间数
     */
    private Long bgtPeriodNum;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 预算金额
     */
    private BigDecimal bgtAmount;

    /**
     * 预算本位币金额
     */
    private BigDecimal bgtFunAmount;

    /**
     * 预算数量
     */
    private BigDecimal bgtQuantity;

    /**
     * 预算保留金额
     */
    private BigDecimal expReserveAmount;

    /**
     * 预算保留本位币金额
     */
    private BigDecimal expReserveFunAmount;

    /**
     * 预算保留数量
     */
    private BigDecimal expReserveQuantity;

    /**
     * 预算发生金额
     */
    private BigDecimal expUsedAmount;

    /**
     * 预算发生本位币金额
     */
    private BigDecimal expUsedFunAmount;

    /**
     * 预算发生数量
     */
    private BigDecimal expUsedQuantity;

    /**
     * 预算可用金额
     */
    private BigDecimal expAvailableAmount;

    /**
     * 预算可用本位币金额
     */
    private BigDecimal expAvailableFunAmount;

    /**
     * 预算可用数量
     */
    private BigDecimal expAvailableQuantity;

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
     * 员工级别ID
     */
    private Long employeeLevelId;

    /**
     * 员工职务ID
     */
    private Long employeeJobId;

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

    /**
     * 期间序号
     */
    private Long internalPeriodNum;

    @Transient
    private String quantityAmountCode;

    @Transient
    private String quantityAmount;

    @Transient
    private BigDecimal reserve;

    @Transient
    private BigDecimal used;

    @Transient
    private BigDecimal available;

    @Transient
    private BigDecimal bgt;

    @Transient
    private String bgtOrgName;

    @Transient
    private String bgtEntityName;

    @Transient
    private String budgetItemType;

    @Transient
    private String budgetItem;

    @Transient
    private String company;

    @Transient
    private String unit;

    @Transient
    private String unitGroup;

    @Transient
    private String position;

    @Transient
    private String positionGroup;

    @Transient
    private String employee;

    @Transient
    private String employeeGroup;

    @Transient
    private String employeeJob;

    @Transient
    private String employeeLevel;

    @Transient
    private String budgetStructure;

    @Transient
    private String currency;


}
