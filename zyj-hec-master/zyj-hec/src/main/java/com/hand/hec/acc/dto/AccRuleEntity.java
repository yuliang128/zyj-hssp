package com.hand.hec.acc.dto;

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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "acc_rule_entity")
public class AccRuleEntity extends BaseDTO {

     public static final String FIELD_ENTITY_ID = "entityId";
     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_ENTITY_CODE = "entityCode";
     public static final String FIELD_ENTITY_NAME = "entityName";
     public static final String FIELD_ENTITY_TYPE = "entityType";
     public static final String FIELD_MOD_ENTITY_ID = "modEntityId";
     public static final String FIELD_LOGIC_SCOPE = "logicScope";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long entityId;

    /**
     * 会计规则ID
     */
     @NotNull
     private Long ruleId;

    /**
     * 实体代码
     */
     @NotEmpty
     @Length(max = 30)
     private String entityCode;

    /**
     * 实体名称
     */
     @NotEmpty
     @Length(max = 200)
     private String entityName;

    /**
     * 实体类型
     */
     @NotEmpty
     @Length(max = 30)
     private String entityType;

    /**
     * 模型实体ID
     */
     @NotNull
     private Long modEntityId;

    /**
     * 逻辑范围
     */
     @Length(max = 30)
     private String logicScope;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
