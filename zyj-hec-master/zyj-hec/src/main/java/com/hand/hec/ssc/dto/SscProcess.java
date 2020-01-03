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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "ssc_process")
public class SscProcess extends BaseDTO {

     public static final String FIELD_PROCESS_ID = "processId";
     public static final String FIELD_DISPATCH_RECORD_ID = "dispatchRecordId";
     public static final String FIELD_TASK_ID = "taskId";
     public static final String FIELD_PROCESS_TYPE_CODE = "processTypeCode";
     public static final String FIELD_ACTION_ID = "actionId";
     public static final String FIELD_PROCESS_TIME = "processTime";
     public static final String FIELD_PROCESS_OPINION = "processOpinion";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";


     @Id
     @GeneratedValue
     private Long processId;

    /**
     * 派工记录ID
     */
     @NotNull
     private Long dispatchRecordId;

    /**
     * 任务ID
     */
     @NotNull
     private Long taskId;

    /**
     * 操作类型代码
     */
     @NotEmpty
     @Length(max = 30)
     private String processTypeCode;

    /**
     * 动作ID
     */
     private Long actionId;

    /**
     * 操作时间
     */
     private Date processTime;

    /**
     * 操作意见
     */
     @Length(max = 2000)
     private String processOpinion;

    /**
     * 员工ID
     */
     private Long employeeId;

    /**
     * 员工姓名
     */
    @Transient
    private String employeeName;

    /**
     * 动作名称
     */
    @Transient
    private String actionName;

    /**
     * 工作组名称
     */
    @Transient
    private String workTeamName;

     }
