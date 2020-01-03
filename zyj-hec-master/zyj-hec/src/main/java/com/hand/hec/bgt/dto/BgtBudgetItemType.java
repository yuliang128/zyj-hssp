package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算项目类型
 * </p>
 * 
 * @author mouse 2019/01/07 16:18
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_item_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtBudgetItemType extends BaseDTO {

    public static final String FIELD_BUDGET_ITEM_TYPE_ID = "budgetItemTypeId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BUDGET_ITEM_TYPE_CODE = "budgetItemTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long budgetItemTypeId;

    /**
     * 预算组织ID
     */
    @Where
    private Long bgtOrgId;

    /**
     * 预算项目类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String budgetItemTypeCode;

    /**
     * 预算项目类型描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 备注
     */
    @Length(max = 2000)
    private String notes;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

}
