package com.hand.hec.ssc.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
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
@ExtensionAttribute(disable = true)
@Table(name = "ssc_task_dispatch_record")
public class SscTaskDispatchRecord extends BaseDTO {

    public static final String FIELD_DISPATCH_RECORD_ID = "dispatchRecordId";
    public static final String FIELD_TASK_ID = "taskId";
    public static final String FIELD_NODE_ID = "nodeId";
    public static final String FIELD_WORK_TEAM_ID = "workTeamId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_ENTER_TIME = "enterTime";
    public static final String FIELD_EXIT_TIME = "exitTime";

    /**
     * 处理状态
     */
    /**
     * 等待派工
     */
    public static final String DISPATCH_STATUS_WAITING = "WAITING";
    /**
     * 派工处理中
     */
    public static final String DISPATCH_STATUS_IN_PROGRESS = "IN_PROGRESS";
    /**
     * 暂挂中
     */
    public static final String DISPATCH_STATUS_HOLDING = "HOLDING";
    /**
     * 退回提单人
     */
    public static final String DISPATCH_STATUS_RETURN_BACK = "RETURN_BACK";
    /**
     * 申请退回中
     */
    public static final String DISPATCH_STATUS_APPLY_RETURN = "APPLYING_RETURN";
    /**
     * 已同意
     */
    public static final String DISPATCH_STATUS_APPROVED = "APPROVED";
    /**
     * 已拒绝
     */
    public static final String DISPATCH_STATUS_REJECTED = "REJECTED";
    /**
     * 取消退回提单人
     */
    public static final String DISPATCH_CANCEL_RETURN_BACK = "CANCEL_RETURN_BACK";


    @Id
    @GeneratedValue
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
     * 当前派工处理状态：SSC_DISPATCH_STATUS
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String status;

    /**
     * 开始时间
     */
    private Date enterTime;

    /**
     * 结束时间
     */
    private Date exitTime;

    /**
     * 方法名称
     */
    @Transient
    private String procName;

    @Transient
    private String opinion;

    @Transient
    private String pageName;

    @Transient
    private String canProcessName;

    @Transient
    private String createDate;

    @Transient
    private Long currentEmployeeId;

    @Transient
    private String processOpinion;

    @Transient
    private String workEmployeeName;

    @Transient
    private String docCategory;

    @Transient
    private Long docId;

    @Transient
    private String docNumber;

    @Transient
    private String nodeName;

    @Transient
    private String accEntityName;

    @Transient
    private String docCategoryName;

    @Transient
    private String dispatchStatusName;

    @Transient
    private String workTeamName;

    @Transient
    private String employeeCode;
}
