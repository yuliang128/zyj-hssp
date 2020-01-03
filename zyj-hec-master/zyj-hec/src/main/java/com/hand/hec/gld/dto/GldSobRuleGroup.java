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
@Table(name = "gld_sob_rule_group")
public class GldSobRuleGroup extends BaseDTO {

     public static final String FIELD_RULE_GROUP_ID = "ruleGroupId";
     public static final String FIELD_SET_OF_BOOK_ID = "setOfBooksId";
     public static final String FIELD_RULE_GROUP_CODE = "ruleGroupCode";
     public static final String FIELD_RULE_GROUP_NAME = "ruleGroupName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long ruleGroupId;

    /**
     * 账套ID
     */
     @NotNull
	 @Where
     private Long setOfBooksId;

    /**
     * 账套级会计规则组代码
     */
     @NotEmpty
     @Length(max = 30)
	 @Where
     private String ruleGroupCode;

    /**
     * 账套级会计规则组名称
     */
     @Length(max = 500)
	 @Where
     private String ruleGroupName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
