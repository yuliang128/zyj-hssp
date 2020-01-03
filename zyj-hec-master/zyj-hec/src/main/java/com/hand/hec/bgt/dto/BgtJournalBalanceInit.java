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
@Table(name = "bgt_journal_balance_init")
public class BgtJournalBalanceInit extends BaseDTO {

     public static final String FIELD_SESSION_ID = "sessionId";
     public static final String FIELD_BALANCE_ID = "balanceId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
     public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
     public static final String FIELD_BGT_CENTER_ID = "bgtCenterId";
     public static final String FIELD_BALANCE_COMPANY_ID = "balanceCompanyId";
     public static final String FIELD_BALANCE_OPERATION_UNIT_ID = "balanceOperationUnitId";
     public static final String FIELD_STRUCTURE_ID = "structureId";
     public static final String FIELD_SCENARIO_ID = "scenarioId";
     public static final String FIELD_VERSION_ID = "versionId";
     public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
     public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
     public static final String FIELD_PERIOD_YEAR = "periodYear";
     public static final String FIELD_PERIOD_QUARTER = "periodQuarter";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
     public static final String FIELD_CURRENCY = "currency";
     public static final String FIELD_PERIOD_AMOUNT = "periodAmount";
     public static final String FIELD_PERIOD_FUNCTIONAL_AMOUNT = "periodFunctionalAmount";
     public static final String FIELD_PERIOD_QUANTITY = "periodQuantity";
     public static final String FIELD_QUARTER_AMOUNT = "quarterAmount";
     public static final String FIELD_QUARTER_FUNCTIONAL_AMOUNT = "quarterFunctionalAmount";
     public static final String FIELD_QUARTER_QUANTITY = "quarterQuantity";
     public static final String FIELD_UOM = "uom";
     public static final String FIELD_YEAR_AMOUNT = "yearAmount";
     public static final String FIELD_YEAR_FUNCTIONAL_AMOUNT = "yearFunctionalAmount";
     public static final String FIELD_YEAR_QUANTITY = "yearQuantity";
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


     private Long sessionId;

    /**
     * 预算表balance
     */
     private Long balanceId;

    /**
     * 帐套id
     */
     private Long setOfBooksId;

    /**
     * 预算组织id
     */
     private Long bgtOrgId;

     private Long bgtEntityId;

     private Long bgtCenterId;

    /**
     * 预算公司ID
     */
     private Long balanceCompanyId;

    /**
     * 经营单位id
     */
     private Long balanceOperationUnitId;

    /**
     * 预算表id
     */
     private Long structureId;

    /**
     * 预算场景id
     */
     private Long scenarioId;

    /**
     * 版本ID
     */
     private Long versionId;

    /**
     * 责任中心id
     */
     private Long responsibilityCenterId;

    /**
     * 预算项目id
     */
     private Long budgetItemId;

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
     * 币种
     */
     @Length(max = 30)
     private String currency;

    /**
     * 期间总额
     */
     private BigDecimal periodAmount;

    /**
     * 本位币期间金额
     */
     private BigDecimal periodFunctionalAmount;

    /**
     * 期间数量
     */
     private Long periodQuantity;

    /**
     * 季度总额
     */
     private BigDecimal quarterAmount;

    /**
     * 本位币季度金额
     */
     private BigDecimal quarterFunctionalAmount;

    /**
     * 季度数量
     */
     private Long quarterQuantity;

    /**
     * 经营单位id
     */
     @Length(max = 30)
     private String uom;

    /**
     * 年度总额
     */
     private BigDecimal yearAmount;

    /**
     * 本位币年度金额
     */
     private BigDecimal yearFunctionalAmount;

    /**
     * 年度数量
     */
     private BigDecimal yearQuantity;

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
     * 部门组ID
     */
     private Long unitGroupId;

    /**
     * 岗位id
     */
     private Long positionId;

    /**
     * 岗位组ID
     */
     private Long positionGroupId;

    /**
     * 员工id
     */
     private Long employeeId;

    /**
     * 员工组ID
     */
     private Long employeeGroupId;

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

     private Long dimension11Id;

     private Long dimension12Id;

     private Long dimension13Id;

     private Long dimension14Id;

     private Long dimension15Id;

     private Long dimension16Id;

     private Long dimension17Id;

     private Long dimension18Id;

     private Long dimension19Id;

     private Long dimension20Id;

     }
