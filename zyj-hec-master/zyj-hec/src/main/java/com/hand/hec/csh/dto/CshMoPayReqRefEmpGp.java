package com.hand.hec.csh.dto;

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
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_pay_req_ref_emp_gp")
public class CshMoPayReqRefEmpGp extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_PAYMENT_REQ_TYPE_ID = "moPaymentReqTypeId";
    public static final String FIELD_MO_EMP_GROUP_ID = "moEmpGroupId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MO_EMP_GROUP_CODE = "moEmpGroupCode";
    public static final String FIELD_MO_EMP_GROUP_DESC = "moEmpGroupDesc";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级借款申请单类型ID
     */
    @NotNull
    private Long moPaymentReqTypeId;

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
    private String moEmpGroupDesc;

}
