package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Comparison;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_dimension")
@MultiLanguage
public class FndDimension extends BaseDTO {

    public static final String FIELD_DIMENSION_ID = "dimensionId";
    public static final String FIELD_DIMENSION_SEQUENCE = "dimensionSequence";
    public static final String FIELD_DIMENSION_CODE = "dimensionCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SYSTEM_FLAG = "systemFlag";
    public static final String FIELD_SYSTEM_LEVEL = "systemLevel";
    public static final String FIELD_COMPANY_LEVEL = "companyLevel";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_DIMENSION_CODE_DISPLAY = "dimensionCodeDisplay";


    @Id
    @GeneratedValue
    @Where
    private Long dimensionId;

    /**
     * 序号
     */
    @NotNull
    @Where
    private Long dimensionSequence;

    /**
     * 维度代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String dimensionCode;

    /**
     * 维度描述
     */
    @Length(max = 500)
    @Where(comparison = Comparison.LIKE)
    @MultiLanguageField
    private String description;

    /**
     * 系统标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String systemFlag;

    /**
     * 系统级类型（SYSCODE：SYS_ORGANIZATION_TYPE）
     */
    @Length(max = 30)
    private String systemLevel;

    /**
     * 公司级类型（SYSCODE：SYS_ORGANIZATION_TYPE）
     */
    @Length(max = 30)
    private String companyLevel;

    /**
     * 启用不标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    @Transient
    private String dimensionCodeDisplay;

    @Transient
    private Long valueExistFlag;

}
