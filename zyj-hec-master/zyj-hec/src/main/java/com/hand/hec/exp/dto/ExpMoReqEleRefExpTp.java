package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
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
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/1 17:18
 * Description:申请单页面元素分配报销类型
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_ele_ref_exp_tp")
public class ExpMoReqEleRefExpTp extends BaseDTO {

    public static final String FIELD_EXP_TYPE_REF_ID = "expTypeRefId";
    public static final String FIELD_REQ_PAGE_ELE_REF_ID = "reqPageEleRefId";
    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_MO_EXPENSE_TYPE_CODE = "moExpenseTypeCode";
    public static final String FIELD_MO_EXPENSE_TYPE_NAME = "moExpenseTypeName";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_APPOINT_ITEM_FLAG = "appointItemFlag";


    @Id
    @GeneratedValue
    private Long expTypeRefId;

    /**
     * 管理组织级申请单类型与报销类型关联ID
     */
    @NotNull
    @Where
    private Long reqPageEleRefId;

    /**
     * 管理组织级报销类型ID
     */
    @NotNull
    @JoinTable(name = "ExpMoExpenseTypeJoin", joinMultiLanguageTable = true, target = ExpMoExpenseType.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoExpenseType.FIELD_MO_EXPENSE_TYPE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long moExpenseTypeId;

    /**
     * 管理组织级报销类型代码
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseTypeJoin", field = ExpMoExpenseType.FIELD_MO_EXPENSE_TYPE_CODE)
    private String moExpenseTypeCode;

    /**
     * 管理组织级报销类型名称
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseTypeJoin", field = ExpMoExpenseType.FIELD_DESCRIPTION)
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
    private String appointItemFlag;

}
