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
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "ssc_task_dispatch_record_his")
public class SscTaskDispatchRecordHis extends BaseDTO {

     public static final String FIELD_DISPATCH_RECORD_ID = "dispatchRecordId";
     public static final String FIELD_TASK_ID = "taskId";
     public static final String FIELD_NODE_ID = "nodeId";
     public static final String FIELD_WORK_TEAM_ID = "workTeamId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_STATUS = "status";
     public static final String FIELD_ENTER_TIME = "enterTime";
     public static final String FIELD_EXIT_TIME = "exitTime";


    /**
     * 主键
     */
     @NotNull
     private Long dispatchRecordId;

    /**
     * 任务ID
     */
     @NotNull
     private Long taskId;

    /**
     * 当前节点ID
     */
     private Long nodeId;

    /**
     * 当前工作组ID
     */
     private Long workTeamId;

    /**
     * 当前操作人员ID
     */
     private Long employeeId;

    /**
     * 当前派工处理状态:SSC_PROCESS_STATUS
     */
     @NotEmpty
     @Length(max = 30)
     private String status;

    /**
     * 开始时间
     */
     private Date enterTime;

    /**
     * 结束时间
     */
     private Date exitTime;

     }
