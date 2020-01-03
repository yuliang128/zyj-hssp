package com.hand.hec.gld.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_sob_rule_account")
public class GldSobRuleAccount extends BaseDTO {

     public static final String FIELD_RULE_ACCOUNT_ID = "ruleAccountId";
     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_ACCOUNT_ID = "accountId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
	public static final String FIELD_ACCOUNT_CODE = "accountCode";
	public static final String FIELD_ACCOUNT_NAME = "accountName";
	public static final String FIELD_ACCOUNT_CODE_FROM = "accountCodeFrom";
	public static final String FIELD_ACCOUNT_CODE_TO = "accountCodeTo";

     @Id
     @GeneratedValue
     private Long ruleAccountId;

    /**
     * 帐套级会计规则明细
     */
     @NotNull
     private Long ruleId;

    /**
     * 科目ID
     */
     @NotNull
     private Long accountId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;



     @Transient
	 private String accountCode;


     @Transient
	 private String accountName;

	@Transient
	private String accountType;

     @Transient
	 private String accountCodeFrom;

	 @Transient
	 private String accountCodeTo;


	 @Transient
	 private Long ruleGroupId;

	 @Transient
	 private Long setOfBookId;


     }
