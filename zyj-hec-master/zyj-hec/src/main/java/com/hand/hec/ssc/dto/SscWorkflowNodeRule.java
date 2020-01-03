package com.hand.hec.ssc.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.hand.hec.wfl.dto.WflBusinessRule;

import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_workflow_node_rule")
public class SscWorkflowNodeRule extends BaseDTO {

    public static final String FIELD_RECORD_ID = "recordId";
    public static final String FIELD_DOC_WFL_ASSIGN_ID = "docWflAssignId";
    public static final String FIELD_NODE_ID = "nodeId";
    public static final String FIELD_WFL_BUSINESS_RULE_ID = "wflBusinessRuleId";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";


    @Id
    @GeneratedValue
    private Long recordId;

    /**
     * 业务单据分配工作流程ID
     */
    @NotNull
    @Where
    private Long docWflAssignId;

    /**
     * 工作流程节点ID
     */
    @NotNull
    @Where
    private Long nodeId;

    /**
     * 工作权限规则ID
     */
    @NotNull
    @JoinTable(name = "businessJoin", joinMultiLanguageTable = false, target = WflBusinessRule.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = WflBusinessRule.FIELD_BUSINESS_RULE_ID)})
    private Long wflBusinessRuleId;

    /**
     * 工作权限规则代码
     */
    @JoinColumn(joinName = "businessJoin", field = WflBusinessRule.FIELD_BUSINESS_RULE_CODE)
    @Transient
    private String wflBusinessRuleCode;

    /**
     * 工作权限规则名称
     */
    @JoinColumn(joinName = "businessJoin", field = WflBusinessRule.FIELD_DESCRIPTION)
    @Transient
    private String wflBusinessRuleName;

    /**
     * 启用日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /**
     * 失效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

}
