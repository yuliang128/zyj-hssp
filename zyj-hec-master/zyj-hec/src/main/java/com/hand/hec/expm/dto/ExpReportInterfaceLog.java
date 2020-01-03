package com.hand.hec.expm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
/**
 * <p>
 * ExpReportInterfaceLog
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_report_interface_log")
public class ExpReportInterfaceLog extends BaseDTO {

     public static final String FIELD_BATCH_ID = "batchId";
     public static final String FIELD_BATCH_LINE_ID = "batchLineId";
     public static final String FIELD_EXP_REPORT_BATCH_NUMBER = "expReportBatchNumber";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_MESSAGE = "message";


    /**
     * 批次号
     */
     private Long batchId;

    /**
     * 序号
     */
     private Long batchLineId;

    /**
     * 报销单唯一标识
     */
     @Length(max = 200)
     private String expReportBatchNumber;

    /**
     * 行号
     */
     private Long lineNumber;

    /**
     * 错误信息
     */
     @Length(max = 4000)
     private String message;

     }
