package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.fnd.dto.FndManagingOrganization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 付款方式分配管理组织dto
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 11:15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_method_asgn_mo")
public class CshPaymentMethodAsgnMo extends BaseDTO {

    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MAG_ORG_NAME = "description";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";

    @Id
    @GeneratedValue
    private Long assignMoId;

    /**
     * 付款方式ID
     */
    @NotNull
    private Long paymentMethodId;

    /**
     * 管理组织ID
     */

    @NotNull
    private Long magOrgId;

    @Length(max = 500)
    @Transient
    private String description;

    @Length(max = 500)
    @Transient
    private String magOrgCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;
}
