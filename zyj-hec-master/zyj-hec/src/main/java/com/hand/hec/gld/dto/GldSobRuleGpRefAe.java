package com.hand.hec.gld.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
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
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_sob_rule_gp_ref_ae")
public class GldSobRuleGpRefAe extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_RULE_GROUP_ID = "ruleGroupId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 帐套级会计规则组
     */
     @NotNull
	 @Where
     private Long ruleGroupId;

    /**
     * 核算主体
     */
	 @JoinTable(name = "accEntityJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
			 type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
	 @NotNull
     private Long accEntityId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

	@Transient
	@Length(max = 500)
	@JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
	private String accEntityName;


	@Transient
	@Length(max = 500)
	@JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
	private String accEntityCode;


	@Transient
	private Long setOfBookId;

     }
