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
import com.hand.hec.wfl.dto.WflProcedure;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_workflow_node_proc")
public class SscWorkflowNodeProc extends BaseDTO {

    public static final String FIELD_RECORD_ID = "recordId";
    public static final String FIELD_NODE_ID = "nodeId";
    public static final String FIELD_PROCEDURE_ID = "procedureId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long recordId;

    /**
     * 工作流程节点ID
     */
    @Where
    private Long nodeId;

    /**
     * 过程ID
     */
    @JoinTable(name = "procJoin", joinMultiLanguageTable = true, target = WflProcedure.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflProcedure.FIELD_PROCEDURE_ID)})
    private Long procedureId;

    /**
     * 节点过程
     */
    @JoinColumn(joinName = "procJoin", field = WflProcedure.FIELD_PROCEDURE_NAME)
    @Transient
    private String procedureName;

    /**
     * 过程
     */
    @JoinColumn(joinName = "procJoin", field = WflProcedure.FIELD_PROCEDURE_CONTENT)
    @Transient
    private String procedureContent;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
