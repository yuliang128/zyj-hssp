package com.hand.hec.bgt.dto;

import javax.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算保留临时表
 * </p>
 * 
 * @author guiyuting 2019/05/22 15:38
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Builder
@AllArgsConstructor
@Table(name = "bgt_balance_detail_init")
public class BgtBalanceDetailInit {

    public static final String FIELD_SESSION_ID = "sessionId";
    public static final String FIELD_BUDGET_RESERVE_LINE_ID = "budgetReserveLineId";

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


    /**
     * 系统SESSION ID
     */
    @NotNull
    private Long sessionId;

    /**
     * 预算保留表行ID
     */
    private Long budgetReserveLineId;

    @Transient
    private String reserveFlag;

    /**
     * 预算组织ID
     */
    @Transient
    private Long bgtOrgId;

    /**
     * 预算实体ID
     */
    @Transient
    private Long bgtEntityId;

    /**
     * 预算中心ID
     */
    @Transient
    private Long bgtCenterId;

    /**
     * 期间
     */
    @Length(max = 30)
    private String periodName;

    /**
     * 预算释放ID
     */
    @Transient
    private Long releaseId;

    /**
     * EXP_REQUISITION:费用申请单,EXP_REPORT:费用报销单
     */
    @Transient
    private String businessType;


    /**
     * 状态
     */
    @Transient
    private String status;

    /**
     * 手工标志
     */

    @Transient
    private String manualFlag;

    /**
     * 费用申请单/报销单头ID
     */
    @Transient
    private Long documentId;

    /**
     * 费用申请单/报销单分配行ID
     */
    @Transient
    private Long documentLineId;

    /**
     * 币种
     */
    @Transient
    private String currencyCode;

    @Transient
    private String currency;

    /**
     * 预算项目ID
     */
    @Transient
    private Long budgetItemId;

    /**
     * 成本中心ID
     */
    @Transient
    private Long responsibilityCenterId;

    /**
     * 汇率类型
     */
    @Transient
    private String exchangeRateType;

    /**
     * 汇率
     */
    @Transient
    private BigDecimal exchangeRate;

    /**
     * 金额
     */
    @Transient
    private BigDecimal amount;

    /**
     * 本位币金额
     */
    @Transient
    private BigDecimal functionalAmount;

    /**
     * 数量
     */
    @Transient
    private BigDecimal quantity;

    /**
     * 单位
     */
    @Transient
    private String uom;

    /**
     * 单据公司ID
     */
    @Transient
    private Long companyId;


    /**
     * 部门ID
     */
    @Transient
    private Long unitId;

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 员工ID
     */
    @Transient
    private Long employeeId;

    /**
     * 维度1
     */
    @Transient
    private Long dimension1Id;

    /**
     * 维度2
     */
    @Transient
    private Long dimension2Id;

    /**
     * 维度3
     */
    @Transient
    private Long dimension3Id;

    /**
     * 维度4
     */
    @Transient
    private Long dimension4Id;

    /**
     * 维度5
     */
    @Transient
    private Long dimension5Id;

    /**
     * 维度6
     */
    @Transient
    private Long dimension6Id;

    /**
     * 维度7
     */
    @Transient
    private Long dimension7Id;

    /**
     * 维度8
     */
    @Transient
    private Long dimension8Id;

    /**
     * 维度9
     */
    @Transient
    private Long dimension9Id;

    /**
     * 维度10
     */
    @Transient
    private Long dimension10Id;

    /**
     * 维度11
     */
    @Transient
    private Long dimension11Id;

    /**
     * 维度12
     */
    @Transient
    private Long dimension12Id;

    /**
     * 维度13
     */
    @Transient
    private Long dimension13Id;

    /**
     * 维度14
     */
    @Transient
    private Long dimension14Id;

    /**
     * 维度15
     */
    @Transient
    private Long dimension15Id;

    /**
     * 维度16
     */
    @Transient
    private Long dimension16Id;

    /**
     * 维度17
     */
    @Transient
    private Long dimension17Id;

    /**
     * 维度18
     */
    @Transient
    private Long dimension18Id;

    /**
     * 维度19
     */
    @Transient
    private Long dimension19Id;

    /**
     * 维度20
     */
    @Transient
    private Long dimension20Id;

    @Transient
    private String docType;

    @Transient
    private String description;

    @Transient
    private String budgetItemDesc;

    @Transient
    private String docNumber;

    @Transient
    private BigDecimal docAmount;

    @Transient
    private Date docDate;

    @Transient
    private BigDecimal lineNumber;

    @Transient
    private String companyDesc;

    @Transient
    private String unitDesc;

    @Transient
    private String employeeDesc;

    @Transient
    private BigDecimal typeId;

    @Transient
    private String typeDesc;

    @Transient
    private Long periodYear;

    @Transient
    private Long periodQuarter;

    @Transient
    private List<Long> dimension1Ids;

    @Transient
    private List<Long> dimension2Ids;

    @Transient
    private List<Long> dimension3Ids;

    @Transient
    private List<Long> dimension4Ids;

    @Transient
    private List<Long> dimension5Ids;

    @Transient
    private List<Long> dimension6Ids;

    @Transient
    private List<Long> dimension7Ids;

    @Transient
    private List<Long> dimension8Ids;

    @Transient
    private List<Long> dimension9Ids;

    @Transient
    private List<Long> dimension10Ids;

    @Transient
    private List<Long> dimension11Ids;

    @Transient
    private List<Long> dimension12Ids;

    @Transient
    private List<Long> dimension13Ids;

    @Transient
    private List<Long> dimension14Ids;

    @Transient
    private List<Long> dimension15Ids;

    @Transient
    private List<Long> dimension16Ids;

    @Transient
    private List<Long> dimension17Ids;

    @Transient
    private List<Long> dimension18Ids;

    @Transient
    private List<Long> dimension19Ids;

    @Transient
    private List<Long> dimension20Ids;

    @Transient
    private Long budgetItemTypeId;

    @Transient
    private Long unitGroupId;

    @Transient
    private Long orgUnitLevelId;

    @Transient
    private Long positionGroupId;

    @Transient
    private Long expenseUserGroupId;

    @Transient
    private Long employeeJobId;

    @Transient
    private Long employeeLevelsId;


}
