package com.hand.hec.ssc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Table(name = "ssc_node_assign_rule")
public class SscNodeAssignRule extends BaseDTO {

     public static final String FIELD_RECORD_ID = "recordId";
     public static final String FIELD_WORKER_ASSIGN_ID = "workerAssignId";
     public static final String FIELD_WFL_BUSINESS_RULE_ID = "wflBusinessRuleId";
     public static final String FIELD_START_DATE = "startDate";
     public static final String FIELD_END_DATE = "endDate";


     @Id
     @GeneratedValue
     private Long recordId;

    /**
     * 作业人员分配ID
     */
     @NotNull
     private Long workerAssignId;

    /**
     * 工作流权限规则ID
     */
     @NotNull
     private Long wflBusinessRuleId;

    /**
     * 启用日期
     */
     @JsonFormat(pattern="yyyy-MM-dd")
     private Date startDate;

    /**
     * 失效日期
     */
     @JsonFormat(pattern="yyyy-MM-dd")
     private Date endDate;

    @Transient
    private String businessRuleCode;

    @Transient
    private String businessRuleName;

     }
