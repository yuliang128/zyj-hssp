package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_pay_req_ref_trx_cls")
public class CshMoPayReqRefTrxCls extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_PAYMENT_REQ_TYPE_ID = "moPaymentReqTypeId";
    public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MO_CSH_TRX_CLASS_CODE = "moCshTrxClassCode";
    public static final String FIELD_MO_CSH_TRX_CLASS_DESC = "moCshTrxClassDesc";
    public static final String FIELD_MO_PAYMENT_REQ_TYPE_CODE = "moPaymentReqTypeCode";
    public static final String FIELD_MO_PAYMENT_REQ_TYPE_DESC = "moPaymentReqTypeDesc";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级借款申请单类型ID
     */
    @NotNull
    private Long moPaymentReqTypeId;

    /**
     * 现金事物分类ID
     */
    @NotNull
    private Long moCshTrxClassId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 现金事务代码
     */
    @Transient
    private String moCshTrxClassCode;
    /**
     * 现金事务描述
     */
    @Transient
    private String moCshTrxClassDesc;

    /**
     * 借款申请单类型代码
     */
    @Transient
    private String moPaymentReqTypeCode;

    /**
     * 借款申请单类型描述
     */
    @Transient
    private String moPaymentReqTypeDesc;

}
