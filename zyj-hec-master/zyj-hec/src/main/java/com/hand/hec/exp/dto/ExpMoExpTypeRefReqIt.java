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
 * 报销类型关联申请项目dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_type_ref_req_it")
public class ExpMoExpTypeRefReqIt extends BaseDTO {

    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";


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
    @JoinTable(name = "moReqItemJoin", type = JoinType.LEFT, target = ExpMoReqItem.class
            , joinMultiLanguageTable = true, on = {@JoinOn(joinField = ExpMoReqItem.FIELD_MO_REQ_ITEM_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long moReqItemId;
    /**
     * 费用项目代码
     */
    @Transient
    @JoinColumn(joinName = "moReqItemJoin", field = ExpMoReqItem.FIELD_MO_REQ_ITEM_CODE)
    private String moReqItemCode;
    /**
     * 费用项目描述
     */
    @Transient
    @JoinColumn(joinName = "moReqItemJoin", field = ExpMoReqItem.FIELD_DESCRIPTION)
    private String description;


    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 38)
    @Where
    private String enabledFlag;

    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

}
