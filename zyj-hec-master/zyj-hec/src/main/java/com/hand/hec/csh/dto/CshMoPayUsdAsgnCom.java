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
/**
 * <p>
 * 付款用途分配公司DTO
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_pay_usd_asgn_com")
public class CshMoPayUsdAsgnCom extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_COMPANY_CODE = "companyCode";
     public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";

     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 付款用途ID
     */
     @NotNull
     private Long paymentUsedeId;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

     @Transient
     private String companyCode;

     @Transient
     @MultiLanguageField
     private String companyShortName;

     @Transient
     private Long magOrgId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
