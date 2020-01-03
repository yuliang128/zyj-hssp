package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_requisition_ref")
public class CshPaymentRequisitionRef extends BaseDTO {

     public static final String FIELD_PAYMENT_REQUISITION_REF_ID = "paymentRequisitionRefId";
     public static final String FIELD_PAYMENT_REQUISITION_LINE_ID = "paymentRequisitionLineId";
     public static final String FIELD_CSH_TRANSACTION_LINE_ID = "cshTransactionLineId";
     public static final String FIELD_WRITE_OFF_FLAG = "writeOffFlag";
     public static final String FIELD_WRITE_OFF_ID = "writeOffId";
     public static final String FIELD_AMOUNT = "amount";
     public static final String FIELD_DESCRIPTION = "description";


     @Id
     @GeneratedValue
     private Long paymentRequisitionRefId;

    /**
     * 借款申请单行ID
     */
     @NotNull
     private Long paymentRequisitionLineId;

    /**
     * 现金事务行ID
     */
     @NotNull
     private Long cshTransactionLineId;

    /**
     * 核销标志
     */
     @Length(max = 1)
     private String writeOffFlag;

    /**
     * 核销ID
     */
     private Long writeOffId;

    /**
     * 金额
     */
     private BigDecimal amount;

    /**
     * 描述
     */
     @Length(max = 2000)
     private String description;

     }
