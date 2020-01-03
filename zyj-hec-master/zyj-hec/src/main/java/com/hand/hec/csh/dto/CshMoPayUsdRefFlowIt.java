package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.gld.dto.GldSetOfBook;
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
 * 付款用途联系现金流量项DTO
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_pay_usd_ref_flow_it")
public class CshMoPayUsdRefFlowIt extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
     public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
     public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_PAYMENT_USEDE_CODE = "paymentUsedeCode";
     public static final String FIELD_CASH_FLOW_ITEM_DESC = "cashFlowItemDesc";
     public static final String FIELD_SET_OF_BOOKS_NAME = "setOfBooksName";
     public static final String  FIELD_SET_OF_BOOKS_ID = "setOfBooksId";

     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 付款用途ID
     */
     @NotNull
     private Long paymentUsedeId;

     @Transient
     private String paymentUsedeCode;

    /**
     * 现金流量项ID
     */
     @NotNull
     private Long cashFlowItemId;

     @Transient
     @MultiLanguageField
     private String cashFlowItemDesc;

     @Transient
     private Long setOfBooksId;

     @Transient
     @MultiLanguageField
     private String setOfBooksName;

    /**
     * 默认标志
     */
     @NotEmpty
     @Length(max = 1)
     private String defaultFlag;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
