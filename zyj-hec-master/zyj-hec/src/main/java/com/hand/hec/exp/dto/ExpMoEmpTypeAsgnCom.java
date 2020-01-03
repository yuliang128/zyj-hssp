package com.hand.hec.exp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_emp_type_asgn_com")
public class ExpMoEmpTypeAsgnCom extends BaseDTO {

    public static final String FIELD_EMP_MO_ASGN_ID = "empMoAsgnId";
    public static final String FIELD_EMPLOYEE_TYPE_ID = "employeeTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long empMoAsgnId;

    /**
     * 员工类型ID
     */
    @NotNull
    private Long employeeTypeId;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 管理组织描述
     */
    @Transient
    private String magOrgName;

    /**
     * 管理组织ID
     */
    @Transient
    private Long magOrgId;

    /**
     * 管理公司代码
     */
    @Transient
    private String companyCode;

    /**
     * 管理公司简称
     */
    @Transient
    private String companyShortName;
}
