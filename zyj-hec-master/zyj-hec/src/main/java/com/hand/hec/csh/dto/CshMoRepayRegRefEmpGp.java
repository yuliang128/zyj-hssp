package com.hand.hec.csh.dto;

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

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/24 20:22
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_mo_repay_reg_ref_emp_gp")
public class CshMoRepayRegRefEmpGp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_REPAYMENT_REG_TYPE_ID = "moRepaymentRegTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级还款申请单类型ID
     */
    @NotNull
    private Long moRepaymentRegTypeId;

    /**
     * 管理组织级员工组ID
     */
    @NotNull
    private Long moEmpGroupId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 员工组代码
     */
    @Transient
    private String moEmpGroupCode;

    /**
     * 员工组描述
     */
    @Transient
    private String description;

}
