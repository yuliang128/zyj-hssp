package com.hand.hec.cont.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "con_cash_trx_pmt_schedule_ln")
public class ConCashTrxPmtScheduleLn extends BaseDTO {

     public static final String FIELD_TRX_PMT_SCHEDULE_LINE_ID = "trxPmtScheduleLineId";
     public static final String FIELD_CSH_TRANSACTION_LINE_ID = "cshTransactionLineId";
     public static final String FIELD_WRITE_OFF_ID = "writeOffId";
     public static final String FIELD_CONTRACT_HEADER_ID = "contractHeaderId";
     public static final String FIELD_PAYMENT_SCHEDULE_LINE_ID = "paymentScheduleLineId";
     public static final String FIELD_AMOUNT = "amount";


     @Id
     @GeneratedValue
     private Long trxPmtScheduleLineId;

    /**
     * 现金事务行ID
     */
     @NotNull
     private Long cshTransactionLineId;

    /**
     * 核销ID
     */
     private Long writeOffId;

    /**
     * 合同头ID
     */
     @NotNull
     private Long contractHeaderId;

    /**
     * 支付计划行ID
     */
     private Long paymentScheduleLineId;

    /**
     * 金额
     */
     private BigDecimal amount;

     }
