package com.hand.hec.ssc.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
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
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "ssc_workflow_action")
public class SscWorkflowAction extends BaseDTO {

    public static final String FIELD_ACTION_ID = "actionId";
    public static final String FIELD_WORKFLOW_ID = "workflowId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ACTION_TYPE = "actionType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 动作类型
     */
    /**
     * 同意
     */
    public static final String PROCESS_TYPE_APPROVE = "APPROVE";
    /**
     * 拒绝
     */
    public static final String PROCESS_TYPE_REJECT = "REJECT";
    /**
     * 暂挂
     */
    public static final String PROCESS_TYPE_HOLD = "HOLD";
    /**
     * 取消暂挂
     */
    public static final String PROCESS_TYPE_CANCEL_HOLD = "CANCEL_HOLD";
    /**
     * 退回
     */
    public static final String PROCESS_TYPE_RETURN_BACK = "RETURN_BACK";
    /**
     * 取消退回
     */
    public static final String PROCESS_TYPE_CANCEL_RTN_BACK = "CANCEL_RETURN_BACK";
    /**
     * 申请退回
     */
    public static final String PROCESS_TYPE_APPLY_RETURN = "APPLY_RETURN";
    /**
     * 同意退回
     */
    public static final String PROCESS_TYPE_AGREE_RETURN = "AGREE_RETURN";
    /**
     * 拒绝退回
     */
    public static final String PROCESS_TYPE_REJECT_RETURN = "REJECT_RETURN";
    /**
     * 强制退回
     */
    public static final String PROCESS_TYPE_FORCE_RETURN = "FORCE_RETURN";

    @Id
    @GeneratedValue
    private Long actionId;

    /**
     * 工作流程ID
     */
    @NotNull
    @Where
    private Long workflowId;

    /**
     * 描述
     */
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 动作类型
     */
    @Length(max = 30)
    private String actionType;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
