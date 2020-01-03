package com.hand.hec.wfl.dto;

/** Auto Generated By Hap Code Generator **/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.mybatis.common.query.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import lombok.*;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_ver_sub_process_task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflVerSubProcessTask extends BaseDTO {

    public static final String FIELD_VERSION_TASK_ID = "versionTaskId";
    public static final String FIELD_SUB_PROCESS_FIELD_ID = "subProcessTaskId";
    public static final String FIELD_TASK_ID = "taskId";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_TASK_AUTO_PASS_FLAG = "taskAutoPassFlag";

    /**
     * ID
     */
    @Id
    @GeneratedValue
    @Where
    private long versionTaskId;

    @Where
    private Long subProcessTaskId;

    @Where
    private Long taskId;

    /**
     * 工作流版本
     */
    @Where
    private Long version;

    /**
     * 流程ID
     */
    @Where
    private Long processId;

    /**
     * 节点自动通过标志
     */
    @NotEmpty
    @Length(max = 1)
    private String taskAutoPassFlag;


    public WflVerSubProcessTask(WflSubProcessTask subProcessTask) {
        this.subProcessTaskId = subProcessTask.getSubProcessTaskId();
        this.taskId = subProcessTask.getTaskId();
        this.processId = subProcessTask.getProcessId();
        this.taskAutoPassFlag = subProcessTask.getTaskAutoPassFlag();
    }

}
