package com.hand.hec.gld.dto;

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
@ExtensionAttribute(disable=true)
@Table(name = "gld_sob_rule")
public class GldSobRule extends BaseDTO {

     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_RULE_GROUP_ID = "ruleGroupId";
     public static final String FIELD_RULE_CODE = "ruleCode";
     public static final String FIELD_RULE_NAME = "ruleName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long ruleId;

    /**
     * 账套级会计规则组
     */
     @NotNull
	 @Where
     private Long ruleGroupId;

    /**
     * 账套级会计规则代码
     */
     @NotEmpty
     @Length(max = 30)
	 @Where
     private String ruleCode;

    /**
     * 账套级会计规则名称
     */
     @Length(max = 500)
	 @Where
     private String ruleName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
