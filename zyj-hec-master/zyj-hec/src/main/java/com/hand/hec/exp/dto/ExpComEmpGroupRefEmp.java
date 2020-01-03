package com.hand.hec.exp.dto;

import javax.persistence.*;

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
 * 员工分配员工组Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_com_emp_group_ref_emp")
public class ExpComEmpGroupRefEmp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_EMP_GROUP_COM_ASSIGN_ID = "empGroupComAssignId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级员工组公司分配ID
     */
    @NotNull
    private Long empGroupComAssignId;
    /**
     * 员工组名称
     */
    @Transient
    private String description;
    /**
     * 员工组代码
     */
    @Transient
    private String moEmpGroupCode;

    /**
     * 员工ID
     */
    @NotNull
    private Long employeeId;
    /**
     * 公司Id
     */
    @Transient
    private Long companyId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
