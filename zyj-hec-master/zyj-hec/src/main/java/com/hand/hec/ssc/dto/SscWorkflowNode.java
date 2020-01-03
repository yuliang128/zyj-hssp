package com.hand.hec.ssc.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.wfl.dto.WflPage;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "ssc_workflow_node")
public class SscWorkflowNode extends BaseDTO {

    public static final String FIELD_NODE_ID = "nodeId";
    public static final String FIELD_WORKFLOW_ID = "workflowId";
    public static final String FIELD_NODE_SEQUENCE = "nodeSequence";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PROCESS_PAGE_ID = "processPageId";
    public static final String FIELD_VIEW_PAGE_ID = "viewPageId";
    public static final String FIELD_TIME_CONTROL_FLAG = "timeControlFlag";
    public static final String FIELD_TIME_LIMIT = "timeLimit";
    public static final String FIELD_AUTO_DISPATCH_FLAG = "autoDispatchFlag";
    public static final String FIELD_CAN_HOLD_FLAG = "canHoldFlag";
    public static final String FIELD_CAN_RETURN_FLAG = "canReturnFlag";
    public static final String FIELD_MANUAL_DISPATCH_FLAG = "manualDispatchFlag";
    public static final String FIELD_FIXED_FLAG = "fixedFlag";
    public static final String FIELD_NODE_TYPE = "nodeType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long nodeId;

    /**
     * 工作流程ID
     */
    @Where
    @NotNull
    private Long workflowId;

    /**
     * 节点顺序
     */
    @NotNull
    private Long nodeSequence;

    /**
     * 描述
     */
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 操作页面ID
     */
    @JoinTable(name = "processJoin", joinMultiLanguageTable = true, target = WflPage.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflPage.FIELD_PAGE_ID)})
    private Long processPageId;

    /**
     * 操作页面名称
     */
    @JoinColumn(joinName = "processJoin", field = WflPage.FIELD_PAGE_NAME)
    @Transient
    private String processPage;

    /**
     * 查看页面ID
     */
    @JoinTable(name = "viewJoin", joinMultiLanguageTable = true, target = WflPage.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflPage.FIELD_PAGE_ID)})
    private Long viewPageId;

    /**
     * 查看页面名称
     */
    @JoinColumn(joinName = "viewJoin", field = WflPage.FIELD_PAGE_NAME)
    @Transient
    private String viewPage;

    /**
     * 是否进行时间限制
     */
    @Length(max = 1)
    private String timeControlFlag;

    /**
     * 时间限制(分钟)
     */
    private Long timeLimit;

    /**
     * 自动派工标志
     */
    @Length(max = 1)
    private String autoDispatchFlag;

    /**
     * 是否可以暂挂
     */
    @Length(max = 1)
    private String canHoldFlag;

    /**
     * 是否可以退回派工池
     */
    @Length(max = 1)
    private String canReturnFlag;

    /**
     * 手动取单标志
     */
    @Length(max = 1)
    private String manualDispatchFlag;

    /**
     * 固定节点标志
     */
    @Length(max = 1)
    private String fixedFlag;

    /**
     * 节点类型
     */
    @NotEmpty
    @Length(max = 30)
    private String nodeType;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 工作流程节点分配工作中心
     */
    @Transient
    private List<SscNodeAsgnWorkCenter> centers;

    /**
     * 共享工作流程节点动作
     */
    @Transient
    private List<SscWorkflowNodeAction> nodeActions;

    /**
     * 共享工作流程节点相关过程
     */
    @Transient
    private List<SscWorkflowNodeProc> nodeProcs;
}
