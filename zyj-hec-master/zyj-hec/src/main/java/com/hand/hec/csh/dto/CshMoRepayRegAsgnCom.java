package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/25 09:54
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_mo_repay_reg_asgn_com")
public class CshMoRepayRegAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_REPAYMENT_REG_TYPE_ID = "moRepaymentRegTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 管理组织级还款申请单类型ID
     */
    @NotNull
    private Long moRepaymentRegTypeId;

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
     * 公司简称
     */
    @Transient
    private String companyName;

    /**
     * 公司代码
     */
    @Transient
    private String companyCode;

}
