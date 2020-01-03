package com.hand.hec.ssc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "ssc_task_pool_prepare")
public class SscTaskPoolPrepare extends BaseDTO {

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
     public static final String FIELD_PROCESS_STATUS = "processStatus";
     public static final String FIELD_ERROR_MESSAGE = "errorMessage";


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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterTimeTo;

    /**
     * 退出本池时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exitTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exitTimeTo;

    /**
     * 处理状态，N/Y/E/I，未处理，已处理，处理异常，处理中
     */
     @Length(max = 30)
     private String processStatus;

    /**
     * 错误消息，由预处理池进入派工池时的异常消息
     */
     @Length(max = 2000)
     private String errorMessage;

     @Transient
     private String curAdviceEmpName;

    @Transient
    private String docCategoryName;

    @Transient
    private Long docEmployeeId;

    @Transient
    private String docEmployeeName;

    @Transient
    private Long workflowId;
    @Transient
    private String workflowName;
    @Transient
    private Long nodeId;
    @Transient
    private String nodeName;
    @Transient
    private String poolType;
    @Transient
    private String poolTypeName;
    @Transient
    private String magOrgName;
    @Transient
    private Long currentNodeId;
    @Transient
    private String currentNodeName;

    @Transient
    private String companyName;
    @Transient
    private Long dispatchRecordId;
    @Transient
    private String taskStatus;
    @Transient
    private String taskStatusName;

    @Transient
    private String dispatchStatus;
    @Transient
    private String dispatchStatusName;
    @Transient
    private Long workEmployeeId;
    @Transient
    private String workEmployeeName;
    @Transient
    private String companyCode;
    @Transient
    private String enterTime2;
    @Transient
    private String exitTime2;
}
