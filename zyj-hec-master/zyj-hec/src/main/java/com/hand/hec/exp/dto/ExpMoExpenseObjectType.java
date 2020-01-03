package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.fnd.dto.FndManagingOrganization;
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
 * @date 2019/2/25 18:50
 * Description: 费用对象类型定义表DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_expense_object_type")
@MultiLanguage
public class ExpMoExpenseObjectType extends BaseDTO {

    public static final String FIELD_MO_EXP_OBJ_TYPE_ID = "moExpObjTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";
    public static final String FIELD_MAG_ORG_NAME = "magOrgName";
    public static final String FIELD_MO_EXP_OBJ_TYPE_CODE = "moExpObjTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SYSTEM_FLAG = "systemFlag";
    public static final String FIELD_SYSTEM_FLAG_NAME = "systemFlagName";
    public static final String FIELD_EXPENSE_OBJECT_METHOD = "expenseObjectMethod";
    public static final String FIELD_EXPENSE_OBJECT_METHOD_NAME = "expenseObjectMethodName";
    public static final String FIELD_SQL_QUERY = "sqlQuery";
    public static final String FIELD_SQL_VALIDATE = "sqlValidate";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long moExpObjTypeId;

    /**
     * 组织架构ID
     */
    @NotNull
    @JoinTable(name = "FndMagOrgJoin", joinMultiLanguageTable = true, target = FndManagingOrganization.class, type = JoinType.LEFT, on = {@JoinOn(joinField = FndManagingOrganization.FIELD_MAG_ORG_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private Long magOrgId;
    /**
     * 组织架构代码
     */
    @Transient
    @JoinColumn(joinName = "FndMagOrgJoin", field = FndManagingOrganization.FIELD_MAG_ORG_CODE)
    private String magOrgCode;
    /**
     * 组织架构名称
     */
    @Transient
    @JoinColumn(joinName = "FndMagOrgJoin", field = FndManagingOrganization.FIELD_DESCRIPTION)
    private String magOrgName;

    /**
     * 管理组织级费用对象类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moExpObjTypeCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 系统级标志
     */
    @NotEmpty
    @Length(max = 1)
    private String systemFlag;

    @Transient
    @JoinCode(code = "EXP_MO_EXPENSE_OBJECT_TYPE", joinKey = FIELD_SYSTEM_FLAG)
    private String systemFlagName;
    /**
     * 费用对象取值类型
     */
    @NotEmpty
    @Length(max = 30)
    private String expenseObjectMethod;

    @Transient
    @JoinCode(code = "EXPENSE_OBJECT_METHOD", joinKey = FIELD_EXPENSE_OBJECT_METHOD)
    private String expenseObjectMethodName;
    /**
     * 查询SQL
     */
    @Length(max = 2000)
    private String sqlQuery;

    /**
     * 验证SQL
     */
    @Length(max = 2000)
    private String sqlValidate;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
