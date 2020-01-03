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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_employee_job")
@MultiLanguage
public class ExpEmployeeJob extends BaseDTO {

    public static final String FIELD_EMPLOYEE_JOB_ID = "employeeJobId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_EMPLOYEE_JOB_CODE = "employeeJobCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LEVEL_SERIES_ID = "levelSeriesId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_EMPLOYEE_JOB_NAME = "employeeJobName";
    public static final String FIELD_POSITION_ID = "positionId";


    @Id
    @GeneratedValue
    private Long employeeJobId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 职务代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String employeeJobCode;

    /**
     * 职务名称ID
     */
    @NotEmpty
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    @Transient
    private String employeeJobName;

    /**
     * 级别系列ID
     */
    @Where
    private Long levelSeriesId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 岗位ID
     */
    @Transient
    private Long positionId;

}
