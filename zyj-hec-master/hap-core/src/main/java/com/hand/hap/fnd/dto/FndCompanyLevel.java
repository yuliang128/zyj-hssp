package com.hand.hap.fnd.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
 * FndCompanyLevel
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_company_level")
@MultiLanguage
public class FndCompanyLevel extends BaseDTO {

    public static final String FIELD_COMPANY_LEVEL_ID = "companyLevelId";
    public static final String FIELD_COMPANY_LEVEL_CODE = "companyLevelCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 公司级别ID
     */
    @Id
    @GeneratedValue
    private Long companyLevelId;

    /**
     * 公司级别代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String companyLevelCode;

    /**
     * 描述
     */
    @MultiLanguageField
    @Where
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
