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
 * @date 2019/2/22 15:10
 * Description: 申请单类型定义行分配费用对象dto
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_ele_ref_ln_obj")
public class ExpMoReqEleRefLnObj extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_REQ_PAGE_ELE_REF_ID = "reqPageEleRefId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_ID = "moExpObjTypeId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_CODE = "moExpObjTypeCode";
    public static final String FIELD_MO_EXP_OBJ_TYPE_NAME = "moExpObjTypeName";
    public static final String FIELD_SQL_QUERY = "sqlQuery";
    public static final String FIELD_EXPENSE_OBJECT_METHOD = "expenseObjectMethod";
    public static final String FIELD_REQUIRED_FLAG = "requiredFlag";
    public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
    public static final String FIELD_DEFAULT_MO_OBJECT_ID = "defaultMoObjectId";
    public static final String FIELD_DEFAULT_MO_OBJECT_CODE = "defaultMoObjectCode";
    public static final String FIELD_DEFAULT_MO_OBJECT_NAME = "defaultMoObjectName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型下页面元素关联ID
     */
    @NotNull
    @Where
    private Long reqPageEleRefId;

    /**
     * 管理组织级费用对象类型ID
     */
    @NotNull
    @JoinTable(name = "ExpMoExpenseObjectTypeJoin", joinMultiLanguageTable = true, target = ExpMoExpenseObjectType.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoExpenseObjectType.FIELD_MO_EXP_OBJ_TYPE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private Long moExpObjTypeId;
    /**
     * 管理组织级费用对象类型代码
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseObjectTypeJoin", field = ExpMoExpenseObjectType.FIELD_MO_EXP_OBJ_TYPE_CODE)
    private String moExpObjTypeCode;

    /**
     * 描述
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseObjectTypeJoin", field = ExpMoExpenseObjectType.FIELD_DESCRIPTION)
    private String moExpObjTypeName;
    /**
     * 查询SQL
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseObjectTypeJoin", field = ExpMoExpenseObjectType.FIELD_SQL_QUERY)
    private String sqlQuery;

    /**
     * 费用对象取值类型
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseObjectTypeJoin", field = ExpMoExpenseObjectType.FIELD_EXPENSE_OBJECT_METHOD)
    private String expenseObjectMethod;

    /**
     * 是否必输
     */
    @NotEmpty
    @Length(max = 1)
    private String requiredFlag;

    /**
     * 布局顺序
     */
    @NotNull
    private Long layoutPriority;

    /**
     * 默认管理组织级费用对象ID
     */
    private Long defaultMoObjectId;

    /**
     * 默认费用对象代码
     */
    @Transient
    private String defaultMoObjectCode;
    /**
     * 默认费用对象名称
     */
    @Transient
    private String defaultMoObjectName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
