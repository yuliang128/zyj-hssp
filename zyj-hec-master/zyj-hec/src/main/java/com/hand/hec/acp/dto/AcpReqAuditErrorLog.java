package com.hand.hec.acp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "acp_req_audit_error_log")
public class AcpReqAuditErrorLog {

     public static final String FIELD_SESSION_ID = "sessionId";
     public static final String FIELD_REQUISITION_NUMBER = "requisitionNumber";
     public static final String FIELD_REQUISITION_LNS_ID = "requisitionLnsId";
     public static final String FIELD_MESSAGE = "message";
     public static final String FIELD_LINE_NUMBER  ="lineNumber";


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
     * 付款申请行id
     */
     private Long requisitionLnsId;

     private Long createdBy;

     private Date creationDate;

    /**
     * 日志信息
     */
     @Length(max = 1000)
    private String message;

    /**
     * 行号
     */
    @Transient
    private Long lineNumber;

}
