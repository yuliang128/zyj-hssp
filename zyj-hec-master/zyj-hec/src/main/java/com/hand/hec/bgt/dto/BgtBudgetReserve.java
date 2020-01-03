package com.hand.hec.bgt.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 预算占用
 * </p>
 * 
 * @author mouse 2019/01/07 16:19
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_reserve")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetReserve extends BaseDTO {

    public static final String FIELD_BUDGET_RESERVE_LINE_ID = "budgetReserveLineId";
    public static final String FIELD_RESERVE_COMPANY_ID = "reserveCompanyId";
    public static final String FIELD_RESERVE_OPERATION_UNIT_ID = "reserveOperationUnitId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_RELEASE_ID = "releaseId";
    public static final String FIELD_BUSINESS_TYPE = "businessType";
    public static final String FIELD_RESERVE_FLAG = "reserveFlag";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_MANUAL_FLAG = "manualFlag";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
    public static final String FIELD_CURRENCY = "currency";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_EXCHANGE_RATE_TYPE = "exchangeRateType";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_FUNCTIONAL_AMOUNT = "functionalAmount";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_UOM = "uom";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
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
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
    public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";

    public static final String BUSINESS_TYPE_BGT_JOURNAL_DATA = "BGT_JOURNAL_DATA";
    public static final String BUSINESS_TYPE_BGT_JOURNAL_CHECK = "BGT_JOURNAL_CHECK";


    /**
     * 预算保留表行ID
     */
    @Id
    @GeneratedValue
    private Long budgetReserveLineId;

    /**
     * 预算公司ID
     */
    private Long reserveCompanyId;

    /**
     * 废弃_预算经营单位
     */
    private Long reserveOperationUnitId;

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
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 预算释放ID
     */
    private Long releaseId;

    /**
     * EXP_REQUISITION:费用申请单,EXP_REPORT:费用报销单
     */
    @NotEmpty
    @Length(max = 30)
    private String businessType;

    /**
     * R:申请单冻结,U:报销单占用
     */
    @NotEmpty
    @Length(max = 1)
    private String reserveFlag;

    /**
     * 状态
     */
    @NotEmpty
    @Length(max = 30)
    private String status;

    /**
     * 手工标志
     */
    @NotEmpty
    @Length(max = 1)
    private String manualFlag;

    /**
     * 费用申请单/报销单头ID
     */
    private Long documentId;

    /**
     * 费用申请单/报销单分配行ID
     */
    private Long documentLineId;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currency;

    /**
     * 预算项目ID
     */
    private Long budgetItemId;

    /**
     * 成本中心ID
     */
    private Long responsibilityCenterId;

    /**
     * 汇率类型
     */
    @Length(max = 30)
    private String exchangeRateType;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 本位币金额
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
     * 单据公司ID
     */
    private Long companyId;

    /**
     * 废弃_单据经营单位
     */
    private Long operationUnitId;

    /**
     * 部门ID
     */
    private Long unitId;

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 员工ID
     */
    private Long employeeId;

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
     * 预算释放类型
     */
    @Length(max = 30)
    private String sourceType;

    /**
     * 预算年度
     */
    private Long periodYear;

    /**
     * 预算季度
     */
    private Long periodQuarter;

    /**
     * 期间序号
     */
    @NotNull
    private Long internalPeriodNum;

    /**
     * 占用金额合计
     */
    @Transient
    private BigDecimal amountSum;

    /**
     * 占用数量合计
     */
    @Transient
    private BigDecimal quantitySum;

}
