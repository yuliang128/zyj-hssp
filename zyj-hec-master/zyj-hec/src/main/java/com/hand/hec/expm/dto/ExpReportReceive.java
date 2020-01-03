package com.hand.hec.expm.dto;

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
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * ExpReportReceive
 * </p>
 *
 * @author yang.duan 2019/01/10 15:01
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_report_receive")
public class ExpReportReceive extends BaseDTO {

     public static final String FIELD_RECEIVE_ID = "receiveId";
     public static final String FIELD_SEQUENCE_NUM = "sequenceNum";
     public static final String FIELD_RECEIVE_NUMBER = "receiveNumber";
     public static final String FIELD_RECEIVE_DATE = "receiveDate";
     public static final String FIELD_DOCUMENT_TYPE = "documentType";
     public static final String FIELD_DOCUMENT_ID = "documentId";
     public static final String FIELD_DOCUMENT_NUMBER = "documentNumber";
     public static final String FIELD_STATUS = "status";


     @Id
     @GeneratedValue
     private Long receiveId;

    /**
     * 序号
     */
     @NotNull
     private Long sequenceNum;

    /**
     * 接收单号
     */
     @NotEmpty
     @Length(max = 30)
     private String receiveNumber;

    /**
     * 接收日期
     */
     private Date receiveDate;

    /**
     * 单据类型
     */
     @NotEmpty
     @Length(max = 30)
     private String documentType;

    /**
     * 单据头ID
     */
     @NotNull
     private Long documentId;

    /**
     * 单据编号
     */
     @NotEmpty
     @Length(max = 30)
     private String documentNumber;

    /**
     * 状态（SYSCODE：EXP_REPORT_RECEIVE_STATUS）
     */
     @NotEmpty
     @Length(max = 30)
     private String status;

     }
