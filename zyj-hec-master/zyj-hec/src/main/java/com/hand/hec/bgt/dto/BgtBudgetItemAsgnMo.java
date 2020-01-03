package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算项目分配管理组织
 * </p>
 * 
 * @author mouse 2019/01/07 16:11
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_item_asgn_mo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetItemAsgnMo extends BaseDTO {

    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_BUDGET_ITEM_CODE = "budgetItemCode";
    public static final String FIELD_BUDGET_ITEM_NAME = "budgetItemName";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";
    public static final String FIELD_MAG_ORG_NAME = "magOrgName";


    @Id
    @GeneratedValue
    private Long assignMoId;

    /**
     * 预算项目ID
     */
    private Long budgetItemId;

    /**
     * 管理组织ID
     */
    private Long magOrgId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String budgetItemCode;

    @Transient
    private String budgetItemName;

    @Transient
    private String magOrgCode;

    @Transient
    private String magOrgName;

}
