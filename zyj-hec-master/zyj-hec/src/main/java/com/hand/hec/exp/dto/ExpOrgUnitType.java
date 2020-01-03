package com.hand.hec.exp.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
import javax.validation.constraints.NotNull;

/**
 * <p>
 * ExpOrgUnitType
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_org_unit_type")
@MultiLanguage
public class ExpOrgUnitType extends BaseDTO {

    public static final String FIELD_UNIT_TYPE_ID = "unitTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_UNIT_TYPE_CODE = "unitTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long unitTypeId;

    /**
     * 管理组织ID
     */
    @Where
    @NotNull
    private Long magOrgId;

    /**
     * 部门类型代码
     */
    @NotEmpty
    @Length(max = 30)
	@Where
    private String unitTypeCode;

    /**
     * 描述
     */
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
	@Where
    private String description;

    /**
     * 启用标志
     */
    @Where
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
