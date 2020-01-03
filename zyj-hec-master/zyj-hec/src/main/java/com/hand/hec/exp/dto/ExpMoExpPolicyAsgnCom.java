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
 * 政策标准关联管理公司dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_policy_asgn_com")
public class ExpMoExpPolicyAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_EXPENSE_POLICY_ID = "expensePolicyId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long assignId;
    /**
     * 管理组织Id
     */
    @Transient
    private Long magOrgId;

    /**
     * 政策标准ID
     */
    @NotNull
    private Long expensePolicyId;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 管理公司简称
     */
    @Transient
    private String companyShortName;
    /**
     * 管理公司代码
     */
    @Transient
    private String companyCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
