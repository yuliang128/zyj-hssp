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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_exp_policy_cond_hds")
public class ExpMoExpPolicyCondHds extends BaseDTO {

    public static final String FIELD_CONDITION_HDS_ID = "conditionHdsId";
    public static final String FIELD_CONDITION_ID = "conditionId";
    public static final String FIELD_MATCH_ITEM_CODE = "matchItemCode";


    @Id
    @GeneratedValue
    private Long conditionHdsId;

    /**
     * 管理组织级政策标准定义条件分配
     */
    @NotNull
    private Long conditionId;

    /**
     * 匹配项代码
     */
    @NotEmpty
    @Length(max = 30)
    private String matchItemCode;
    /**
     * 明细Id
     */
    @Transient
    private String detailId;
    /**
     * 管理组织Id
     */
    @Transient
    private String magOrgId;


}
