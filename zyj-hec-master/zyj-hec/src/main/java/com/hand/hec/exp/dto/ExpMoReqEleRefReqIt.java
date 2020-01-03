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
 * @date 2019/3/1 17:57
 * Description:申请单页面元素报销类型分配费用项目
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_ele_ref_req_it")
public class ExpMoReqEleRefReqIt extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_EXP_TYPE_REF_ID = "expTypeRefId";
    public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
    public static final String FIELD_MO_REQ_ITEM_CODE = "moReqItemCode";
    public static final String FIELD_MO_REQ_ITEM_NAME = "moReqItemName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级申请单类型与报销类型关联ID
     */
    @NotNull
    @Where
    private Long expTypeRefId;

    /**
     * 申请项目ID
     */
    @NotNull
    @JoinTable(name = "ExpMoReqItemJoin", joinMultiLanguageTable = true, target = ExpMoReqItem.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoReqItem.FIELD_MO_REQ_ITEM_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long moReqItemId;

    /**
     * 申请项目代码
     */
    @Transient
    @JoinColumn(joinName = "ExpMoReqItemJoin", field = ExpMoReqItem.FIELD_MO_REQ_ITEM_CODE)
    private String moReqItemCode;

    /**
     * 申请项目名称
     */
    @Transient
    @JoinColumn(joinName = "ExpMoReqItemJoin", field = ExpMoReqItem.FIELD_DESCRIPTION)
    private String moReqItemName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
