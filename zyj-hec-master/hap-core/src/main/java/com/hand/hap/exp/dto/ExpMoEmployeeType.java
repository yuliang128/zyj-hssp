package com.hand.hap.exp.dto;

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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "exp_mo_employee_type")
public class ExpMoEmployeeType extends BaseDTO {

    public static final String FIELD_EMPLOYEE_TYPE_ID = "employeeTypeId";
    public static final String FIELD_EMPLOYEE_TYPE_CODE = "employeeTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long employeeTypeId;

    /**
     * 员工类型代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String employeeTypeCode;

    /**
     * 员工类型描述
     */
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
