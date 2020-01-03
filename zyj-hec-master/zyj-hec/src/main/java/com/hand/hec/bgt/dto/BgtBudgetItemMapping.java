package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算项决定规则
 * </p>
 * 
 * @author mouse 2019/01/07 16:16
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_item_mapping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetItemMapping extends BaseDTO {

    public static final String FIELD_MAPPING_ID = "mappingId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BUSINESS_CATEGORY = "businessCategory";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_DOCUMENT_TYPE_ID = "documentTypeId";
    public static final String FIELD_BUSINESS_TYPE_ID = "businessTypeId";
    public static final String FIELD_PARAM_VALUE1_ID = "paramValue1Id";
    public static final String FIELD_PARAM_VALUE2_ID = "paramValue2Id";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESP_CENTER_ID = "respCenterId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
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
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_BUDGET_ITEM_NAME = "budgetItemName";
    public static final String FIELD_MAG_ORG_NAME = "magOrgName";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_POSITION_NAME = "positionName";
    public static final String FIELD_EMPLOYEE_NAME  = "employeeName";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_RESP_CNTER_NAME  = "respCnterName";


    @Id
    @GeneratedValue
    private Long mappingId;

    /**
     * 预算组织ID
     */
    @Where
    private Long bgtOrgId;

    /**
     * 业务类别（SYSCODE：BGT_RELATED_BUSINESS_TYPE）
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String businessCategory;

    /**
     * 优先级
     */
    private Long priority;

    /**
     * 预算项目ID
     */
    @Where
    @JoinTable(name = "bgtBudgetItemJoin",joinMultiLanguageTable = false,target = com.hand.hec.bgt.dto.BgtBudgetItem.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = BgtBudgetItem.FIELD_BUDGET_ITEM_ID)})
    private Long budgetItemId;

    /**
     * 预算项目描述
     */
    @Transient
    @Length(max = 500)
    @Where
    @JoinColumn(joinName = "bgtBudgetItemJoin",field = BgtBudgetItem.FIELD_DESCRIPTION)
    private String budgetItemName;

    /**
     * 管理组织ID
     */
    @JoinTable(name = "magOrgJoin",joinMultiLanguageTable = false,target = FndManagingOrganization.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = FndManagingOrganization.FIELD_MAG_ORG_ID)})
    private Long magOrgId;

    /**
     * 管理组织描述
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "magOrgJoin" , field = FndManagingOrganization.FIELD_DESCRIPTION)
    private String magOrgName;

    /**
     * 管理公司ID
     */
    @JoinTable(name = "companyJoin",joinMultiLanguageTable = false,target = FndCompany.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
    private Long companyId;

    /**
     * 管理公司名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "companyJoin" , field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyShortName;

    /**
     * 单据类型ID
     */
    private Long documentTypeId;

    /**
     * 业务类型（业务类别为费用申请/报销则为报销类型ID；业务类别为采购申请/订单则为采购类型ID）
     */
    private Long businessTypeId;

    /**
     * 参数1（业务类别为费用申请/报销则为报销类型下费用项目；业务类别为采购申请/订单则为采购类型下采购对象类型）
     */
    private Long paramValue1Id;

    /**
     * 参数2（业务类别为费用申请/报销则为空；业务类别为采购申请/订单则为参数1下的明细值如：物料、资产子分类、费用项目）
     */
    private Long paramValue2Id;

    /**
     * 部门ID
     */
    @JoinTable(name = "unitJoin",joinMultiLanguageTable = false,target = com.hand.hec.exp.dto.ExpOrgUnit.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = ExpOrgUnit.FIELD_UNIT_ID)})
    private Long unitId;

    /**
     * 部门名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "unitJoin" , field = ExpOrgUnit.FIELD_DESCRIPTION)
    private String unitName;

    /**
     * 岗位ID
     */
    @JoinTable(name = "positionJoin",joinMultiLanguageTable = false,target = com.hand.hec.exp.dto.ExpOrgPosition.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = ExpOrgPosition.FIELD_POSITION_ID)})
    private Long positionId;

    /**
     * 岗位名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "positionJoin" , field = ExpOrgPosition.FIELD_DESCRIPTION)
    private String positionName;

    /**
     * 员工ID
     */
    @JoinTable(name = "employeeJoin",joinMultiLanguageTable = false,target = ExpEmployee.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;

    /**
     * 岗位名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "employeeJoin" , field = ExpEmployee.FIELD_NAME)
    private String employeeName;

    /**
     * 核算主体ID
     */
    @JoinTable(name = "accEntityJoin",joinMultiLanguageTable = false,target = com.hand.hec.gld.dto.GldAccountingEntity.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
    private Long accEntityId;

    /**
     * 核算主体名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "accEntityJoin" , field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    /**
     * 成本中心ID
     */
    @JoinTable(name = "respCenterJoin",joinMultiLanguageTable = false,target = com.hand.hec.gld.dto.GldResponsibilityCenter.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_ID)})
    private Long respCenterId;

    /**
     * 核算主体名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "respCenterJoin" , field = GldResponsibilityCenter.FIELD_RESPONSIBILITY_CENTER_NAME)
    private String respCnterName;

    /**
     * 收款对象类型
     */
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 收款方
     */
    private Long payeeId;

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
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
