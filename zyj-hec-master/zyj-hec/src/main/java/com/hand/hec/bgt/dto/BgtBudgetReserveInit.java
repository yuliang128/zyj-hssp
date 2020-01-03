package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "bgt_budget_reserve_init")
public class BgtBudgetReserveInit extends BaseDTO {

     public static final String FIELD_SESSION_ID = "sessionId";
     public static final String FIELD_BUDGET_RESERVE_LINE_ID = "budgetReserveLineId";
     public static final String FIELD_RESERVE_COMPANY_ID = "reserveCompanyId";
     public static final String FIELD_RESERVE_OPERATION_UNIT_ID = "reserveOperationUnitId";
     public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
     public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
     public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
     public static final String FIELD_PERIOD_YEAR = "periodYear";
     public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
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
     public static final String FIELD_EXCHANGE_RATE_QUOTATION = "exchangeRateQuotation";
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


     private Long sessionId;

    /**
     * 预算保留表行ID
     */
     private Long budgetReserveLineId;

    /**
     * 占用公司id
     */
     private Long reserveCompanyId;

    /**
     * 经营单位id
     */
     private Long reserveOperationUnitId;

    /**
     * 预算组织id
     */
     private Long bgtOrgId;

     private Long bgtEntityId;

     private Long bgtCenterId;

    /**
     * 预算年度
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
     * 预算期间
     */
     private Long bgtPeriodNum;

    /**
     * 释放标志
     */
     private Long releaseId;

    /**
     * 业务类型
     */
     @Length(max = 30)
     private String businessType;

    /**
     * 释放标志
     */
     @Length(max = 1)
     private String reserveFlag;

    /**
     * 状态
     */
     @Length(max = 30)
     private String status;

    /**
     * 预算状态
     */
     @Length(max = 1)
     private String manualFlag;

    /**
     * 单据头id
     */
     private Long documentId;

    /**
     * 表行id
     */
     private Long documentLineId;

    /**
     * 币种
     */
     @Length(max = 30)
     private String currency;

    /**
     * 预算项目id
     */
     private Long budgetItemId;

    /**
     * 责任中心id
     */
     private Long responsibilityCenterId;

    /**
     * 汇率类型
     */
     @Length(max = 30)
     private String exchangeRateType;

    /**
     * 标价方法
     */
     @Length(max = 30)
     private String exchangeRateQuotation;

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
     * 经营单位id
     */
     @Length(max = 30)
     private String uom;

    /**
     * 公司ID
     */
     private Long companyId;

    /**
     * 经营单位id
     */
     private Long operationUnitId;

    /**
     * 部门id
     */
     private Long unitId;

    /**
     * 岗位id
     */
     private Long positionId;

    /**
     * 员工id
     */
     private Long employeeId;

    /**
     * 维值1id
     */
     private Long dimension1Id;

    /**
     * 维值2id
     */
     private Long dimension2Id;

    /**
     * 维值3id
     */
     private Long dimension3Id;

    /**
     * 维值4id
     */
     private Long dimension4Id;

    /**
     * 维值5id
     */
     private Long dimension5Id;

    /**
     * 维值6id
     */
     private Long dimension6Id;

    /**
     * 维值7id
     */
     private Long dimension7Id;

    /**
     * 维值8id
     */
     private Long dimension8Id;

    /**
     * 维值9id
     */
     private Long dimension9Id;

    /**
     * 维值10id
     */
     private Long dimension10Id;

    /**
     * 维值11id
     */
     private Long dimension11Id;

    /**
     * 维值12id
     */
     private Long dimension12Id;

    /**
     * 维值13id
     */
     private Long dimension13Id;

    /**
     * 维值14id
     */
     private Long dimension14Id;

    /**
     * 维值15id
     */
     private Long dimension15Id;

    /**
     * 维值16id
     */
     private Long dimension16Id;

    /**
     * 维值17id
     */
     private Long dimension17Id;

    /**
     * 维值18id
     */
     private Long dimension18Id;

    /**
     * 维值19id
     */
     private Long dimension19Id;

    /**
     * 维值20id
     */
     private Long dimension20Id;

    /**
     * 来源类型
     */
     @Length(max = 30)
     private String sourceType;

     }
