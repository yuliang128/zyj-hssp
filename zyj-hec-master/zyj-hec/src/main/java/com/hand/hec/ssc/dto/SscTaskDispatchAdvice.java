package com.hand.hec.ssc.dto;

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
@Table(name = "ssc_task_dispatch_advice")
public class SscTaskDispatchAdvice extends BaseDTO {

     public static final String FIELD_ADVICE_ID = "adviceId";
     public static final String FIELD_DISPATCH_RECORD_ID = "dispatchRecordId";
     public static final String FIELD_TASK_ID = "taskId";
     public static final String FIELD_WORK_TEAM_ID = "workTeamId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";


     @Id
     @GeneratedValue
     private Long adviceId;

    /**
     * 任务派工ID
     */
     @NotNull
     private Long dispatchRecordId;

    /**
     * 任务ID
     */
     @NotNull
     private Long taskId;

    /**
     * 工作组ID
     */
     @NotNull
     private Long workTeamId;

     private Long employeeId;

     }
