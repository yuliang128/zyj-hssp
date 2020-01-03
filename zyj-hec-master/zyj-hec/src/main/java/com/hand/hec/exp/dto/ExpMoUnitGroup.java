package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * ExpMoUnitGroup
 * </p>
 *
 * @author yang.duan 2019/01/10 11:24
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_unit_group")
@MultiLanguage
public class ExpMoUnitGroup extends BaseDTO {

    public static final String FIELD_MO_UNIT_GROUP_ID = "moUnitGroupId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_UNIT_GROUP_CODE = "moUnitGroupCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_UNIT_ID = "unitId";


    @Where
    @Id
    @GeneratedValue
    private Long moUnitGroupId;

    /**
     * 管理组织ID
     */
    @Where
    @NotNull
    private Long magOrgId;

    /**
     * 部门组代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String moUnitGroupCode;

    /**
     * 描述
     */
    @Where
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 部门ID
     */
    @Transient
    private Long unitId;

}
