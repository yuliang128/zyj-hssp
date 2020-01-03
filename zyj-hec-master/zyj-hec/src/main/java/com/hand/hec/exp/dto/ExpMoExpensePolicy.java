package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 政策标准定义dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_expense_policy")
@MultiLanguage
public class ExpMoExpensePolicy extends BaseDTO {

    public static final String FIELD_EXPENSE_POLICY_ID = "expensePolicyId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_EXPENSE_POLICY_CODE = "expensePolicyCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";


    @Id
    @GeneratedValue
    @Where
    private Long expensePolicyId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 政策标准代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String expensePolicyCode;

    /**
     * 政策标准描述ID
     */
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 有效日期从
     */
    private Date startDateActive;

    /**
     * 有效日期至
     */
    private Date endDateActive;

}
