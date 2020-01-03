package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import java.util.Date;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 政策标准明细条件dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/21 10:20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_policy_condition")
@MultiLanguage
public class ExpMoExpPolicyCondition extends BaseDTO {

    public static final String FIELD_CONDITION_ID = "conditionId";
    public static final String FIELD_DETAIL_ID = "detailId";
    public static final String FIELD_CONDITION_CODE = "conditionCode";
    public static final String FIELD_CONDITION_NAME = "conditionName";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";


    @Id
    @GeneratedValue
    private Long conditionId;

    /**
     * 标准明细ID
     */
    @NotNull
    private Long detailId;

    /**
     * 条件代码
     */
    @NotEmpty
    @Length(max = 30)
    private String conditionCode;

    /**
     * 条件描述ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String conditionName;

    /**
     * 匹配项代码
     */
    @Length(max = 30)
    @Transient
    private String value;

    /**
     * 有效日期从
     */
    private Date startDateActive;

    /**
     * 有效日期至
     */
    private Date endDateActive;

}
