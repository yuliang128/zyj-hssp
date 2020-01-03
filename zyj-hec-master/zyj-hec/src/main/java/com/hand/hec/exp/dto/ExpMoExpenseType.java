package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 报销类型定义dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "exp_mo_expense_type")
public class ExpMoExpenseType extends BaseDTO {

    public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_EXPENSE_TYPE_CODE = "moExpenseTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long moExpenseTypeId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 管理组织级报销类型代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moExpenseTypeCode;

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
    @Where
    private String enabledFlag;

}
