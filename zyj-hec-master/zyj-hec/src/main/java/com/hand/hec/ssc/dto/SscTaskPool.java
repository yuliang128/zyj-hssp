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
@Table(name = "ssc_task_pool")
public class SscTaskPool extends BaseDTO {

    public static final String FIELD_TASK_ID = "taskId";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_DOC_TYPE_ID = "docTypeId";
    public static final String FIELD_DOC_ID = "docId";
    public static final String FIELD_DOC_NUMBER = "docNumber";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ENTER_TIME = "enterTime";
    public static final String FIELD_EXIT_TIME = "exitTime";
    public static final String FIELD_WORK_CENTER_ID = "workCenterId";
    public static final String FIELD_WORKFLOW_ID = "workflowId";
    public static final String FIELD_DOC_WFL_ASSIGN_ID = "docWflAssignId";
    public static final String FIELD_CURRENT_NODE_ID = "currentNodeId";
    public static final String FIELD_CURRENT_WORK_TEAM_ID = "currentWorkTeamId";
    public static final String FIELD_CURRENT_EMPLOYEE_ID = "currentEmployeeId";
    public static final String FIELD_TASK_STATUS = "taskStatus";

    /**
     * 任务状态
     */
    /**
     * 任务进行中
     */
    public static final String TASK_STATUS_WAITING = "ONGOING";
    /**
     * 正常结束
     */
    public static final String TASK_STATUS_FINISHED = "FINISHED";
    /**
     * 异常中断
     */
    public static final String TASK_STATUS_INTERRUPTED = "INTERRUPTED";

    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_LAST_REJECT_OPINION = "lastRejectOpinion";
    public static final String FIELD_DOC_CREATION_DATE_STR = "docCreationDateStr";
    public static final String FIELD_LAST_NODE_NAME = "lastNodeName";
    public static final String FIELD_LAST_EMPLOYEE_NAME = "lastEmployeeName";
    public static final String FIELD_DOC_TYPE_NAME = "docTypeName";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";

    public static final String  FIELD_LAST_HOLD_REASON= "lastHoldReason";
    public static final String  FIELD_LAST_RETURN_REASON= "lastReturnReason";
    @Id
    @GeneratedValue
    private Long taskId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String docCategory;

    /**
     * 单据类型ID
     */
    private Long docTypeId;

    /**
     * 单据ID
     */
    @NotNull
    private Long docId;

    /**
     * 单据编号
     */
    @Where
    @Length(max = 200)
    private String docNumber;

    /**
     * 管理组织ID
     */
    private Long magOrgId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 部门ID
     */
    private Long unitId;

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 账套ID
     */
    private Long setOfBooksId;

    /**
     * 核算主体ID
     */
    private Long accEntityId;

    /**
     * 进入本池时间
     */
    private Date enterTime;

    /**
     * 退出本池时间
     */
    private Date exitTime;

    /**
     * 工作中心ID
     */
    @NotNull
    private Long workCenterId;

    /**
     * 工作流程ID
     */
    private Long workflowId;

    /**
     * 业务单据关联工作流程ID
     */
    private Long docWflAssignId;

    /**
     * 当前节点ID
     */
    private Long currentNodeId;

    /**
     * 当前工作组ID
     */
    private Long currentWorkTeamId;

    /**
     * 当前工作人员ID
     */
    private Long currentEmployeeId;

    /**
     * 任务状态,SYSCODE:SSC_TASK_STATUS
     */
    @NotEmpty
    @Length(max = 30)
    private String taskStatus;

    /**
     * 单据类别描述
     */
    @Transient
    private String docCategoryName;

    @Transient
    private Long docEmployeeId;

    @Transient
    private String docEmployeeName;

    @Transient
    private String workflowName;

    @Transient
    private String nodeName;

    @Transient
    private String poolType;

    @Transient
    private String poolTypeName;

    @Transient
    private String taskStatusName;

    @Transient
    private String dispatchStatus;

    @Transient
    private String dispatchStatusName;

    @Transient
    private String workEmployeeId;

    @Transient
    private String workEmployeeName;

    @Transient
    private String magOrgName;

    @Transient
    private String companyName;

    @Transient
    private Long dispatchRecordId;

    @Transient
    private String errorMessage;

    @Transient
    private Date enterTimeFrom;

    @Transient
    private Date enterTimeTo;

    @Transient
    private Date exitTimeFrom;

    @Transient
    private Date exitTimeTo;

    @Transient
    private Long nodeId;

    @Transient
    private String curAdviceEmpNames;

    @Transient
    private String workTeamId;

    @Transient
    private String dateFrom;

    @Transient
    private String dateTo;

    @Transient
    private String accEntityName;

    @Transient
    private String docCreationDateStr;

    @Transient
    private String lastRejectOpinion;

    @Transient
    private String lastNodeName;

    @Transient
    private String lastEmployeeName;

    @Transient
    private String docTypeName;

    @Transient
    private String lastHoldReason;

    @Transient
    private String employeeName;


    @Transient
    private String lastReturnReason;


    @Transient
    private String processOpinion;

    @Transient
    private String pageName;

    @Transient
    private String canProcessName;

    @Transient
    private String createDate;

    /**
     * 描述
     */
    @Transient
    private String opinion;
    /**
     * 操作类型
     */
    @Transient
    private String actionType;
    /**
     * 表名
     */
    @Transient
    private String tableName;
}
