package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.fnd.dto.FndCompany;

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

import java.util.Date;
/**
 * <p>
 * 付款方式分配公司dto
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 11:15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_method_asgn_com")
public class CshPaymentMethodAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_COM_ID = "assignComId";
    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    @Id
    @GeneratedValue
    private Long assignComId;

    /**
     * 付款方式分配管理组织ID
     */
    @NotNull
    private Long assignMoId;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    @Length(max = 500)
    @Transient
    private String companyCode;

    @Length(max = 500)
    @Transient
    @MultiLanguageField
    private String companyShortName;

    @Transient
    private Date startDateActive;

    @Transient
    private Date endDateActive;

    @Transient
    private String paymentMethodId;



    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
