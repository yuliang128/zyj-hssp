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
@Table(name = "ssc_task_pool_his")
public class SscTaskPoolHis extends BaseDTO {

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
     * 工作重心
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

     }
