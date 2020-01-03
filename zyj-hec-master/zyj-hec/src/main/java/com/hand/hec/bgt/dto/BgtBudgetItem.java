package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@ExtensionAttribute(disable = true)
@Table(name = "bgt_budget_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
/**
 * <p>
 * 预算项目DTO
 * </p>
 *
 * @author mouse 2019/01/07 13:57
 */
public class BgtBudgetItem extends BaseDTO {

    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BUDGET_ITEM_CODE = "budgetItemCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BUDGET_ITEM_TYPE_ID = "budgetItemTypeId";
    public static final String FIELD_VARIATION_ATTRIBUTE = "variationAttribute";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_SUMMARY_FLAG = "summaryFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_BUDGET_ITEM_TYPE_NAME = "budgetItemTypeName";
    public static final String FIELD_PARENT_BUDGET_ITEMID = "parentBudgetItemId";


    /**
     * 主键，供其他表外键使用
     */
    @Where
    @Id
    @GeneratedValue
    private Long budgetItemId;

    /**
     * 预算组织ID
     */
    @Where
    private Long bgtOrgId;

    /**
     * 预算项目代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String budgetItemCode;

    /**
     * 预算项目描述ID
     */
    @Where
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 预算项目类型ID
     */
    @Where
    @JoinTable(name = "typeJoin", joinMultiLanguageTable = true, target = BgtBudgetItemType.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtBudgetItemType.FIELD_BUDGET_ITEM_TYPE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long budgetItemTypeId;

    @Transient
    @JoinColumn(joinName = "typeJoin", field = BgtBudgetItemType.FIELD_DESCRIPTION)
    private String budgetItemTypeName;

    /**
     * 变动属性
     */
    @Length(max = 30)
    private String variationAttribute;

    @Transient
    @JoinCode(code = "BGT_VARIATION_ATTRIBUTE", joinKey = BgtBudgetItem.FIELD_VARIATION_ATTRIBUTE)
    private String variationAttributeName;

    /**
     * 备注
     */
    @Length(max = 2000)
    private String notes;

    /**
     * 汇总标志
     */
    @NotEmpty
    @Length(max = 1)
    private String summaryFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private Long parentBudgetItemId;

    @Transient
    private Long bgtJournalTypeId;

    @Transient
    private String budgetItemCodeFrom;

    @Transient
    private String budgetItemCodeTo;

    @Transient
    List<BgtBudgetItemAsgnMo> magOrgList;

}
