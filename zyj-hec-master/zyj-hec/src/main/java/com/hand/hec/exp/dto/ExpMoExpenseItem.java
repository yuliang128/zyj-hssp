package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.bgt.dto.BgtBudgetItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 费用项目dto
 * </p>
 *
 * @author YHL 2019/01/23 16:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_expense_item")
public class ExpMoExpenseItem extends BaseDTO {

    public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_EXPENSE_ITEM_CODE = "moExpenseItemCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_STANDARD_PRICE = "standardPrice";
    public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MO_REQ_ITEM_DESC  = "moReqItemDesc";
    public static final String FIELD_MO_REQ_ITEM_CODE = "moReqItemCode";
    public static final String FIELD_BUDGET_ITEM_DESC = "budgetItemDesc";
    public static final String FIELD_BUDGET_ITEM_CODE = "budgetItemCode";

    /**
     * 费用项目ID
     */
    @Id
    @GeneratedValue
    private Long moExpenseItemId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 费用项目代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moExpenseItemCode;

    /**
     * 费用项目描述
     */
    @NotEmpty
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 标准费率
     */
    private Long standardPrice;

    /**
     * 申请项目ID
     */
    @JoinTable(name = "expMoReqItemJoin",joinMultiLanguageTable = true,target = com.hand.hec.exp.dto.ExpMoReqItem.class,
    type = JoinType.LEFT,on = {@JoinOn(joinField = ExpMoReqItem.FIELD_MO_REQ_ITEM_ID),
            @JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long moReqItemId;

    /**
     * 预算项目ID
     */
    @JoinTable(name = "bgtBudgetItemJoin",joinMultiLanguageTable = true,target = com.hand.hec.bgt.dto.BgtBudgetItem.class,
    type = JoinType.LEFT,on = {@JoinOn(joinField = BgtBudgetItem.FIELD_BUDGET_ITEM_ID),
            @JoinOn(joinField = BaseDTO.FIELD_LANG,joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long budgetItemId;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    /**
     *申请项目描述
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "expMoReqItemJoin",field = ExpMoReqItem.FIELD_DESCRIPTION)
    private String moReqItemDesc;

    /**
     * 申请项目代码
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "expMoReqItemJoin",field = ExpMoReqItem.FIELD_MO_REQ_ITEM_CODE)
    private String moReqItemCode;

    /**
     * 预算项目描述
     */
    @Transient
    @Length(max = 500)
    @Where
    @JoinColumn(joinName = "bgtBudgetItemJoin",field = BgtBudgetItem.FIELD_DESCRIPTION)
    private String budgetItemDesc;
    /**
     * 预算项目代码
     */
    @Transient
    @Length(max = 500)
    @Where
    @JoinColumn(joinName = "bgtBudgetItemJoin",field = BgtBudgetItem.FIELD_BUDGET_ITEM_CODE)
    private String budgetItemCode;

    @Transient
    private String tip;
}
