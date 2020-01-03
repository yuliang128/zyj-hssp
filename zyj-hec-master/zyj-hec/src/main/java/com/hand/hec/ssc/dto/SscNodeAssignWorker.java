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
import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "ssc_node_assign_worker")
public class SscNodeAssignWorker extends BaseDTO {

     public static final String FIELD_WORKER_ASSIGN_ID = "workerAssignId";
     public static final String FIELD_NODE_ID = "nodeId";
     public static final String FIELD_WORK_TEAM_ID = "workTeamId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_DISPATCH_BASIS_TYPE = "dispatchBasisType";
     public static final String FIELD_BASIS_VALUE = "basisValue";
     public static final String FIELD_MAX_DISPATCH_COUNT = "maxDispatchCount";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 派工依据值
     */
    public static final String BASIS_DOC_ON_HAND        = "DOC_ON_HAND";
    public static final String BASIS_DOC_ON_HAND_NO_HOLD = "DOC_ON_HAND_NO_HOLD";


     @Id
     @GeneratedValue
     private Long workerAssignId;

    /**
     * 工作流程节点ID
     */
     @NotNull
     private Long nodeId;

    /**
     * 工作组ID
     */
     @NotNull
     private Long workTeamId;

    /**
     * 作业人员员工ID
     */
     private Long employeeId;

    /**
     * 派工依据类型：SSC_DISPATCH_BASIS
     */
     @NotEmpty
     @Length(max = 30)
     private String dispatchBasisType;

    /**
     * 依据值
     */
     @NotNull
     private Long basisValue;

    /**
     * 单次最大取单量
     */
     private BigDecimal maxDispatchCount;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @Transient
     private String workTeamCodeName;

     @Transient
     private String employeeCodeName;

     @Transient
     private String sscDispatchBasisName;

     @Transient
     private Long taskId;

     }
