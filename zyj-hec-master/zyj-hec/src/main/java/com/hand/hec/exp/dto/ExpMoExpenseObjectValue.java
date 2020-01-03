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
 * @date 2019/2/25 18:58
 * Description:费用对象值类表Dto
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_expense_object_value")
@MultiLanguage
public class ExpMoExpenseObjectValue extends BaseDTO {

    public static final String FIELD_MO_EXPENSE_OBJECT_ID = "moExpenseObjectId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_ID = "moExpObjTypeId";
    public static final String FIELD_MO_EXP_OBJ_TYPE_CODE = "moExpObjTypeCode";
    public static final String FIELD_MO_EXP_OBJ_TYPE_NAME = "moExpObjTypeName";
    public static final String FIELD_MO_EXPENSE_OBJECT_CODE = "moExpenseObjectCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long moExpenseObjectId;

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
     * 管理组织级费用对象类型名称
     */
    @Transient
    @JoinColumn(joinName = "ExpMoExpenseObjectTypeJoin", field = ExpMoExpenseObjectType.FIELD_DESCRIPTION)
    private String moExpObjTypeName;
    /**
     * 管理组织级费用对象代码
     */
    @NotEmpty
    @Length(max = 30)
    private String moExpenseObjectCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
