package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
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
 * ExpMoRepEleRefExpIt
 * </p>
 *
 * @author yang.duan 2019/01/10 14:43
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_ele_ref_exp_it")
public class ExpMoRepEleRefExpIt extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_EXP_TYPE_REF_ID = "expTypeRefId";
    public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    public static final String FIELD_MO_EXPENSE_ITEM_CODE = "moExpenseItemCode";
    public static final String FIELD_MO_EXPENSE_ITEM_NAME = "moExpenseItemName";

    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型与报销类型关联ID
     */
    @NotNull
    @Where
    private Long expTypeRefId;

    /**
     * 费用项目ID
     */
    @NotNull
    @JoinTable(name = "expenseItemJoin", target = ExpMoExpenseItem.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_ID)})
    private Long moExpenseItemId;

    /**
     * 费用项目Code
     */
    @Transient
    @Where
    @JoinColumn(joinName = "expenseItemJoin",field = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_CODE)
    private String moExpenseItemCode;

    /**
     * 费用项目Code
     */
    @Transient
    @Where
    @JoinColumn(joinName = "expenseItemJoin",field = ExpMoExpenseItem.FIELD_DESCRIPTION)
    private String moExpenseItemName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
