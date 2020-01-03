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
 * 员工组定义Dto
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "exp_mo_emp_group")
public class ExpMoEmpGroup extends BaseDTO {

    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_EMP_GROUP_CODE = "moEmpGroupCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";


    @Id
    @GeneratedValue
    @Where
    private Long moEmpGroupId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 员工组代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String moEmpGroupCode;

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

    /**
     * 员工ID
     */
    @Transient
    private Long employeeId;

}
