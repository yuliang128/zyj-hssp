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
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.wfl.dto.WflPage;
import com.hand.hec.wfl.dto.WflProcedure;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_workflow_node_action")
public class SscWorkflowNodeAction extends BaseDTO {

    public static final String FIELD_RECORD_ID = "recordId";
    public static final String FIELD_NODE_ID = "nodeId";
    public static final String FIELD_ACTION_ID = "actionId";
    public static final String FIELD_BACK_NODE_ID = "backNodeId";
    public static final String FIELD_PROCEDURE_ID = "procedureId";


    @Id
    @GeneratedValue
    private Long recordId;

    /**
     * 节点ID
     */
    @NotNull
    @Where
    private Long nodeId;

    /**
     * 动作ID
     */
    @NotNull
    @JoinTable(name = "actionJoin", joinMultiLanguageTable = true, target = SscWorkflowAction.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = SscWorkflowAction.FIELD_ACTION_ID)})
    private Long actionId;

    /**
     * 动作名称
     */
    @JoinColumn(joinName = "actionJoin", field = SscWorkflowAction.FIELD_DESCRIPTION)
    @Transient
    private String actionName;

    /**
     * 退回节点ID
     */
    @JoinTable(name = "bNodeJoin", joinMultiLanguageTable = true, target = SscWorkflowNode.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = SscWorkflowNode.FIELD_NODE_ID)})
    private Long backNodeId;

    /**
     * 退回节点名称
     */
    @JoinColumn(joinName = "bNodeJoin", field = SscWorkflowNode.FIELD_DESCRIPTION)
    @Transient
    private String backNodeDesc;

    /**
     * 动作触发过程
     */
    @JoinTable(name = "procJoin", joinMultiLanguageTable = true, target = WflProcedure.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflProcedure.FIELD_PROCEDURE_ID)})
    private Long procedureId;

    /**
     * 动作触发过程名称
     */
    @JoinColumn(joinName = "procJoin", field = WflProcedure.FIELD_PROCEDURE_NAME)
    @Transient
    private String procedureName;

}
