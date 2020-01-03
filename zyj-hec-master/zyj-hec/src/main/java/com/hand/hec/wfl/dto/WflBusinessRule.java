package com.hand.hec.wfl.dto;

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
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "wfl_business_rule")
public class WflBusinessRule extends BaseDTO {

     public static final String FIELD_BUSINESS_RULE_ID = "businessRuleId";
     public static final String FIELD_BUSINESS_RULE_CODE = "businessRuleCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_WORKFLOW_CATEGORY = "workflowCategory";
     public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
     public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
     public static final String FIELD_NODE_FLAG = "nodeFlag";
     public static final String FIELD_WORKFLOW_FLAG = "workflowFlag";
     public static final String FIELD_AUTHORIZATION_FLAG = "authorizationFlag";
     public static final String FIELD_SSC_FIXED_NODE_FLAG = "sscFixedNodeFlag";
     public static final String FIELD_SSC_AUTHORIZATION_FLAG = "sscAuthorizationFlag";


     @Id
     @GeneratedValue
     private BigDecimal businessRuleId;

    /**
     * 权限规则编码
     */
     @NotEmpty
     @Length(max = 30)
     private String businessRuleCode;

    /**
     * 权限规则名称
     */
     @Length(max = 500)
     private String description;

    /**
     * 工作流类型
     */
     @Length(max = 30)
     private String workflowCategory;

    /**
     * 有效日期从
     */
     private Date startDateActive;

    /**
     * 有效日期到
     */
     private Date endDateActive;

    /**
     * 工作流节点规则
     */
     @NotEmpty
     @Length(max = 1)
     private String nodeFlag;

    /**
     * 工作流指定
     */
     @NotEmpty
     @Length(max = 1)
     private String workflowFlag;

    /**
     * 审批授权
     */
     @NotEmpty
     @Length(max = 1)
     private String authorizationFlag;

    /**
     * 工作台节点规则
     */
     @NotEmpty
     @Length(max = 1)
     private String sscFixedNodeFlag;

    /**
     * 派工规则
     */
     @NotEmpty
     @Length(max = 1)
     private String sscAuthorizationFlag;

     }
