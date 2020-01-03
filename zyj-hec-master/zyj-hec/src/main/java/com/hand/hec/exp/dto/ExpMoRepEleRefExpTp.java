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
 * ExpMoRepEleRefExpTp
 * </p>
 *
 * @author yang.duan 2019/01/10 14:43
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_ele_ref_exp_tp")
public class ExpMoRepEleRefExpTp extends BaseDTO {

    public static final String FIELD_EXP_TYPE_REF_ID = "expTypeRefId";
    public static final String FIELD_REP_PAGE_ELE_REF_ID = "repPageEleRefId";
    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_APPONIT_ITEM_FLAG = "apponitItemFlag";


    public static final String FIELD_MO_EXPENSE_TYPE_CODE = "moExpenseTypeCode";
    public static final String FIELD_MO_EXPENSE_TYPE_NAME = "moExpenseTypeName";


    @Id
    @GeneratedValue
    private Long expTypeRefId;

    /**
     * 管理组织级报销单类型下页面元素关联ID
     */
    @NotNull
    @Where
    private Long repPageEleRefId;

    /**
     * 管理组织级报销类型ID
     */
    @NotNull
    @JoinTable(name = "moExpenseTypeJoin", target = ExpMoExpenseType.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = ExpMoExpenseType.FIELD_MO_EXPENSE_TYPE_ID)})
    private Long moExpenseTypeId;

    /**
     * 管理组织级报销类型Code
     */
    @Transient
    @JoinColumn(joinName = "moExpenseTypeJoin",field = ExpMoExpenseType.FIELD_MO_EXPENSE_TYPE_CODE)
    private String moExpenseTypeCode;

    /**
     * 管理组织级报销类型Name
     */
    @Transient
    @JoinColumn(joinName = "moExpenseTypeJoin",field = ExpMoExpenseType.FIELD_DESCRIPTION)
    private String moExpenseTypeName;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 指定明细项目标志
     */
    @NotEmpty
    @Length(max = 1)
    private String apponitItemFlag;

}
