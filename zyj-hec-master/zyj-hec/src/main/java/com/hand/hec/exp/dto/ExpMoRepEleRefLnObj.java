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
 * ExpMoRepEleRefLnObj
 * </p>
 *
 * @author yang.duan 2019/01/10 14:44
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_ele_ref_ln_obj")
public class ExpMoRepEleRefLnObj extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_REP_PAGE_ELE_REF_ID = "repPageEleRefId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_ID = "moExpObjTypeId";
    public static final String FIELD_REQUIRED_FLAG = "requiredFlag";
    public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";
    public static final String FIELD_DEFAULT_MO_OBJECT_ID = "defaultMoObjectId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    public static final String FIELD_MO_EXP_OBJ_TYPE_CODE = "moExpObjTypeCode";
    public static final String FIELD_MO_EXP_OBJ_TYPE_NAME = "moExpObjTypeName";
    public static final String FIELD_DEFAULT_MO_OBJECT_CODE = "defaultMoObjectCode";
    public static final String FIELD_DEFAULT_MO_OBJECT_NAME = "defaultMoObjectName";
    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级报销单类型下页面元素关联ID
     */
    @NotNull
    @Where
    private Long repPageEleRefId;

    /**
     * 管理组织级费用对象类型ID
     */
    @NotNull
    @JoinTable(name = "expObjTypeJoin", target = ExpMoExpenseObjectType.class, type = JoinType.LEFT,
                    joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = ExpMoExpenseObjectType.FIELD_MO_EXP_OBJ_TYPE_ID)})
    private Long moExpObjTypeId;

    /**
     * 管理组织级费用对象类型Code
     */
    @Transient
    @Where
    @JoinColumn(joinName = "expObjTypeJoin", field = ExpMoExpenseObjectType.FIELD_MO_EXP_OBJ_TYPE_CODE)
    private String moExpObjTypeCode;

    /**
     * 管理组织级费用对象类型Name
     */
    @Transient
    @Where
    @JoinColumn(joinName = "expObjTypeJoin", field = ExpMoExpenseObjectType.FIELD_DESCRIPTION)
    private String moExpObjTypeName;

    /*
     * 管理组织级费用对象类型查询sql
     * */
    @Transient
    @Where
    @JoinColumn(joinName = "expObjTypeJoin", field = ExpMoExpenseObjectType.FIELD_SQL_QUERY)
    private String sqlQuery;

    /*
     * 管理组织级费用对象类型方式
     * */
    @Transient
    @Where
    @JoinColumn(joinName = "expObjTypeJoin", field = ExpMoExpenseObjectType.FIELD_EXPENSE_OBJECT_METHOD)
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
     * 默认管理组织级费用对象Code
     */
    @Transient
    private String defaultMoObjectCode;


    /**
     * 默认管理组织级费用对象Name
     */
    @Transient
    private String defaultMoObjectName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String returnField;

    @Transient
    private String displayField;

}
