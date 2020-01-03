package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_pay_req_asgn_com")
public class CshMoPayReqAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_PAYMENT_REQ_TYPE_ID = "moPaymentReqTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_PAYMENT_REQ_TYPE_LIST = "paymentReqTypeList";

    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 管理组织借款申请单类型ID
     */
    @Where
    private Long moPaymentReqTypeId;

    /**
     * 管理公司ID
     */
    @Where
    private Long companyId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 公司代码
     */
    @Transient
    private String companyCode;

    /**
     * 公司简称
     */
    @Transient
    private String companyShortName;

    /**
     * 借款申请单集合
     */
    @Transient
    private List<CshMoPaymentReqType> paymentReqTypeList;

}
