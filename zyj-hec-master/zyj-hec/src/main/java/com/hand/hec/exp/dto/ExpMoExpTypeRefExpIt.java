package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 报销类型关联费用项目Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_type_ref_exp_it")
public class ExpMoExpTypeRefExpIt extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long refId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 管理组织级报销类型ID
     */
    @NotNull
    @Where
    private Long moExpenseTypeId;

    /**
     * 费用项目ID
     */
    @NotNull
    @Where
    @JoinTable(name = "moExpenseTypeJoin", target = ExpMoExpenseItem.class, type = JoinType.LEFT
            , joinMultiLanguageTable = true, on = {@JoinOn(joinField = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long moExpenseItemId;

    /**
     * 费用项目代码
     */
    @Transient
    @JoinColumn(joinName = "moExpenseTypeJoin", field = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_CODE)
    private String moExpenseItemCode;
    /**
     * 费用代码描述
     */
    @Transient
    @JoinColumn(joinName = "moExpenseTypeJoin", field = ExpMoExpenseItem.FIELD_DESCRIPTION)
    private String description;
    /**
     * 启用标志
     */
    @NotEmpty
    @Where
    @Length(max = 1)
    private String enabledFlag;

}
