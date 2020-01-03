package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 预算项目层次
 * </p>
 * 
 * @author mouse 2019/01/07 16:16
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_item_hierarchy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBudgetItemHierarchy extends BaseDTO {

    public static final String FIELD_HIERARCHY_ID = "hierarchyId";
    public static final String FIELD_PARENT_BUDGET_ITEM_ID = "parentBudgetItemId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_PARENT_BUDGET_ITEM_CODE = "parentBudgetItemCode";
    public static final String FIELD_PARENT_BUDGET_ITEM_NAME = "parentBudgetItemName";
    public static final String FIELD_BUDGET_ITEM_CODE = "budgetItemCode";
    public static final String FIELD_BUDGET_ITEM_NAME = "budgetItemName";


    @Id
    @GeneratedValue
    private Long hierarchyId;

    /**
     * 父预算项目ID
     */
    private Long parentBudgetItemId;

    @Transient
    private String parentBudgetItemCode;

    @Transient
    private String parentBudgetItemName;

    /**
     * 子预算项目ID
     */
    private Long budgetItemId;

    @Transient
    private String budgetItemCode;

    @Transient
    private String budgetItemName;

}
