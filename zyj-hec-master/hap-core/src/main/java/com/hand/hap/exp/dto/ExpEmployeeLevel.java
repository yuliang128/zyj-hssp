package com.hand.hap.exp.dto;

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

@Getter
@Setter
@NoArgsConstructor
@ToString
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "exp_employee_level")
public class ExpEmployeeLevel extends BaseDTO {

    public static final String FIELD_EMPLOYEE_LEVELS_ID = "employeeLevelsId";
    public static final String FIELD_LEVEL_SERIES_ID = "levelSeriesId";
    public static final String FIELD_EMPLOYEE_LEVELS_CODE = "employeeLevelsCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_EMPLOYEE_JOB_ID = "employeeJobId";


    @Id
    @GeneratedValue
    private Long employeeLevelsId;

    /**
     * 级别系列ID
     */
    @NotNull
    private Long levelSeriesId;

    /**
     * 级别代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String employeeLevelsCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @MultiLanguageField
    @Where
    @Length(max = 500)
    private String description;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 级别系列描述
     */
    @Transient
    private String levelSeriesCodeDisplay;

    /**
     * 员工职务ID
     */
    @Transient
    private Long employeeJobId;
}
