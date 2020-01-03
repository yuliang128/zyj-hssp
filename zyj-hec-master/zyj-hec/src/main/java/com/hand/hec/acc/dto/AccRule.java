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
@Table(name = "acc_rule")
public class AccRule extends BaseDTO {

     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_BATCH_ID = "batchId";
     public static final String FIELD_RULE_CODE = "ruleCode";
     public static final String FIELD_RULE_NAME = "ruleName";
     public static final String FIELD_MODEL_ID = "modelId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long ruleId;

    /**
     * 凭证批ID
     */
     @NotNull
     private Long batchId;

    /**
     * 规则代码
     */
     @NotEmpty
     @Length(max = 30)
     private String ruleCode;

    /**
     * 规则名称
     */
     @NotEmpty
     @Length(max = 200)
     private String ruleName;

    /**
     * 模型ID
     */
     @NotNull
     private Long modelId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
