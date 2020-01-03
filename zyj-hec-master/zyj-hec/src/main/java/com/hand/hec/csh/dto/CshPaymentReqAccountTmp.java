package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_req_account_tmp")
public class CshPaymentReqAccountTmp extends BaseDTO {

     public static final String FIELD_SESSION_ID = "sessionId";
     public static final String FIELD_PAYMENT_REQ_HEADER_ID = "paymentReqHeaderId";


    /**
     * 系统SESSION ID
     */
     @NotNull
     private Long sessionId;

    /**
     * 借款申请单头id
     */
     @NotNull
     private Long paymentReqHeaderId;

     }
