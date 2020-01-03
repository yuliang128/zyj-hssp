package com.hand.hec.wfl.dto;

/** Auto Generated By Hap Code Generator **/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_ins_task_hierarchy_ht")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflInsTaskHierarchyHt extends BaseDTO {

    public static final String FIELD_INS_HIERARCHY_ID = "insHierarchyId";
    public static final String FIELD_INSTANCE_ID = "instanceId";
    public static final String FIELD_INS_TASK_ID = "insTaskId";
    public static final String FIELD_INS_RECEIVER_ID = "insReceiverId";
    public static final String FIELD_SEQUENCE = "sequence";
    public static final String FIELD_APPROVER_ID = "approverId";
    public static final String FIELD_POSTED_FLAG = "postedFlag";
    public static final String FIELD_DISABLED_FLAG = "disabledFlag";
    public static final String FIELD_PARENT_INS_HIERARCHY_ID = "parentInsHierarchyId";
    public static final String FIELD_NOTE = "note";


    /**
     * ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long insHierarchyId;

    /**
     * 实例ID
     */
    @Where
    private Long instanceId;

    /**
     * 实例任务ID
     */
    @Where
    private Long insTaskId;

    /**
     * 实例接受者ID
     */
    @Where
    private Long insReceiverId;

    /**
     * 序号
     */
    private Long sequence;

    /**
     * 审批者ID
     */
    private Long approverId;

    /**
     * 已审批标志
     */
    @Length(max = 1)
    private String postedFlag;

    /**
     * 失效标志
     */
    @Length(max = 1)
    private String disabledFlag;

    /**
     * 父接受者层次ID
     */
    private Long parentInsHierarchyId;

    /**
     * 备注
     */
    @Length(max = 2000)
    private String note;


    public WflInsTaskHierarchyHt(WflInsTaskHierarchy insTaskHierarchy) {
        this.setApproverId(insTaskHierarchy.getApproverId());
        this.setDisabledFlag(insTaskHierarchy.getDisabledFlag());
        this.setInsHierarchyId(insTaskHierarchy.getInsHierarchyId());
        this.setInsReceiverId(insTaskHierarchy.getInsReceiverId());
        this.setInstanceId(insTaskHierarchy.getInstanceId());
        this.setInsTaskId(insTaskHierarchy.getInsTaskId());
        this.setNote(insTaskHierarchy.getNote());
        this.setParentInsHierarchyId(insTaskHierarchy.getParentInsHierarchyId());
        this.setPostedFlag(insTaskHierarchy.getPostedFlag());
        this.setSequence(insTaskHierarchy.getSequence());

        this.setCreatedBy(insTaskHierarchy.getCreatedBy());
        this.setCreationDate(insTaskHierarchy.getCreationDate());
        this.setLastUpdatedBy(insTaskHierarchy.getLastUpdatedBy());
        this.setLastUpdateDate(insTaskHierarchy.getLastUpdateDate());
        this.setObjectVersionNumber(insTaskHierarchy.getObjectVersionNumber());
    }

}
