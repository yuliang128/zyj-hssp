package com.hand.hec.exp.dto;

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
 * 费用申请项目dto
 * </p>
 *
 * @author YHL 2019/01/23 16:15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_item")
public class ExpMoReqItem extends BaseDTO {

    public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
    public static final String FIELD_MO_REQ_ITEM_CODE = "moReqItemCode";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 申请项目ID
     */
    @Id
    @GeneratedValue
    private Long moReqItemId;

    /**
     * 申请项目代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moReqItemCode;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 申请项目描述
     */
    @NotEmpty
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 预算项目ID
     */
    @JoinTable(name = "bgtItemJoin", joinMultiLanguageTable = true, target = com.hand.hec.bgt.dto.BgtBudgetItem.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = BgtBudgetItem.FIELD_BUDGET_ITEM_ID)})
    private Long budgetItemId;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 预算项目code
     */
    @Transient
    @Length(max=500)
    @JoinColumn(joinName = "bgtItemJoin", field = BgtBudgetItem.FIELD_BUDGET_ITEM_CODE)
    private String budgetItemCode;

    /*
    * 预算项目描述
    * */
    @Transient
    @Length(max=500)
    @JoinColumn(joinName = "bgtItemJoin", field = BgtBudgetItem.FIELD_DESCRIPTION)
    private String budgetItemName;

}
