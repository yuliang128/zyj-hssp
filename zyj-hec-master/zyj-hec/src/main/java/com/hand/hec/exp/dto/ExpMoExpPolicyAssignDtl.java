package com.hand.hec.exp.dto;

import javax.persistence.*;


import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_exp_policy_assign_dtl")
public class ExpMoExpPolicyAssignDtl extends BaseDTO {

    public static final String FIELD_ASSIGN_DTL_ID = "assignDtlId";
    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_EXPENSE_POLICY_ID = "expensePolicyId";
    public static final String FIELD_EXPENSE_POLICY_CODE = "expensePolicyCode";
    public static final String FIELD_EXPENSE_POLICY_NAME = "expensePolicyName";


    @Id
    @GeneratedValue
    private Long assignDtlId;

    /**
     * 管理组织级政策分配ID
     */
    @NotNull
    @Where
    private Long assignId;

    /**
     * 政策标准ID
     */
    @JoinTable(name = "expensePolicyJoin", joinMultiLanguageTable = false, target = com.hand.hec.exp.dto.ExpMoExpensePolicy.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = ExpMoExpensePolicy.FIELD_EXPENSE_POLICY_ID)})
    private Long expensePolicyId;

    /**
     * 政策标准代码
     */
    @Transient
    @Length(max = 30)
    @JoinColumn(joinName = "expensePolicyJoin", field = ExpMoExpensePolicy.FIELD_EXPENSE_POLICY_CODE)
    private String expensePolicyCode;

    /**
     * 政策标准名称
     */
    @Transient
    @Length(max = 500)
    @JoinColumn(joinName = "expensePolicyJoin", field = ExpMoExpensePolicy.FIELD_DESCRIPTION)
    private String expensePolicyName;


}
