package com.hand.hec.ssc.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "ssc_work_record")
public class SscWorkRecord extends BaseDTO {

     public static final String FIELD_RECORD_ID = "recordId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_WORK_STATUS = "workStatus";
     public static final String FIELD_PROCESS_DATE = "processDate";
     public static final String FIELD_PROCESS_TIME = "processTime";

     /**
      * 工作状态
      */
    /**
     * 开始工作
     */
     public static final String WORK_STATUS_WORK = "WORK";
    /**
     * 暂停工作
     */
     public static final String WORK_STATUS_PAUSE = "PAUSE";/**
     * 结束工作
     */
     public static final String WORK_STATUS_STOP = "STOP";

     @Id
     @GeneratedValue
     private Long recordId;

    /**
     * 员工
     */
     @NotNull
     private Long employeeId;

    /**
     * 工作状态
     */
     @NotEmpty
     @Length(max = 30)
     private String workStatus;

    /**
     * 操作日期
     */
     private Date processDate;

    /**
     * 操作时间
     */
     private Date processTime;

     }
