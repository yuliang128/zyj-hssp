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
 * 政策标准明细条件行dto
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_policy_cond_lns")
public class ExpMoExpPolicyCondLns extends BaseDTO {

    public static final String FIELD_CONDITION_LNS_ID = "conditionLnsId";
    public static final String FIELD_CONDITION_HDS_ID = "conditionHdsId";
    public static final String FIELD_CONDITION_VALUE_CODE = "conditionValueCode";


    @Id
    @GeneratedValue
    private Long conditionLnsId;

    /**
     * 管理组织级政策标准定义条件分配头
     */
    private Long conditionHdsId;

    /**
     * 值代码（管理公司、核算主体等）
     */
    @NotEmpty
    @Length(max = 30)
    private String conditionValueCode;
    /**
     * 值代码对应的名称
     */
    @Transient
    private String conditionValueName;
    /**
     * 明细条件ID
     */
    @Transient
    private String conditionId;
    /**
     * 值代码所定义的类型
     */
    @Transient
    private String conditionValueType;
    /**
     * 管理组织id
     */
    @Transient
    private String magOrgId;

}
