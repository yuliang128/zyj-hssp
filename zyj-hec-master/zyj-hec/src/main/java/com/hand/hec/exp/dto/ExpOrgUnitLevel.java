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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * ExpOrgUnitLevel
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_org_unit_level")
@MultiLanguage
public class ExpOrgUnitLevel extends BaseDTO {

    public static final String FIELD_ORG_UNIT_LEVEL_ID = "orgUnitLevelId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_ORG_UNIT_LEVEL_CODE = "orgUnitLevelCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long orgUnitLevelId;

    /**
     * 管理组织ID
     */
    @Where
    @NotNull
    private Long magOrgId;

    /**
     * 部门级别代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String orgUnitLevelCode;

    /**
     * 描述
     */
    @Where
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 启用标志
     */
    @Where
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
