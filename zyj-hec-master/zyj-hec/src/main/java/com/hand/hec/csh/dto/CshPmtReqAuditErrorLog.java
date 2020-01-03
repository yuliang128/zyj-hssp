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
@Table(name = "csh_pmt_req_audit_error_log")
public class CshPmtReqAuditErrorLog extends BaseDTO {

     public static final String FIELD_SESSION_ID = "sessionId";
     public static final String FIELD_REQUISITION_NUMBER = "requisitionNumber";
     public static final String FIELD_PAYMENT_REQ_LINE_ID = "paymentReqLineId";
     public static final String FIELD_MESSAGE = "message";


    /**
     * 系统SESSION ID
     */
     @NotNull
     private Long sessionId;

    /**
     * 申请单编码
     */
     @Length(max = 30)
     private String requisitionNumber;

    /**
     * 借款申请行id
     */
     private Long paymentReqLineId;

    /**
     * 日志信息
     */
     @Length(max = 1000)
     private String message;

     }
