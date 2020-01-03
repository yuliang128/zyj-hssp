package com.hand.hec.gld.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
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
@Table(name = "gld_sob_rule_segment")
public class GldSobRuleSegment extends BaseDTO {

     public static final String FIELD_RULE_SEGMENT_ID = "ruleSegmentId";
     public static final String FIELD_RULE_ID = "ruleId";
     public static final String FIELD_SEGMENT_ID = "segmentId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_REQUIRED_FLAG = "requiredFlag";
     public static final String FIELD_SOURCE_CODE = "sourceCode";
     public static final String FIELD_DEFAULT_VALUE = "defaultValue";
     public static final String DEFAULT_SOURCE_CODE = "DEFAULT";
     public static final String FIELD_ACCOUNT_SEGMENT_CODE = "accountSegmentCode";
     public static final String FIELD_ACCOUNT_SEGMENT_NAME = "accountSegmentName";


     @Id
     @GeneratedValue
     private Long ruleSegmentId;

    /**
     * 账套级会计规则明细
     */
     @NotNull
	 @Where
     private Long ruleId;

    /**
     * 账套级科目段
     */
     @NotNull
	 @JoinTable(name = "gldSobSegmentJoin", joinMultiLanguageTable = false, target = GldSobSegment.class,
			 type = JoinType.LEFT, on = {@JoinOn(joinField = GldSobSegment.FIELD_SEGMENT_ID)})
     private Long segmentId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 必输标志
     */
     @NotEmpty
     @Length(max = 1)
     private String requiredFlag;

    /**
     * 来源（SYSCODE:GLD_SOB_RULE_SEGMENT_SOURCE）
     */
     @NotEmpty
     @Length(max = 30)
     private String sourceCode;

    /**
     * 默认值
     */
     @Length(max = 255)
     private String defaultValue;

	 @Transient
	 @JoinColumn(joinName = "gldSobSegmentJoin", field = GldSobSegment.FIELD_SEGMENT_CODE)
	 @Length(max = 30)
	 private String accountSegmentCode;

	 @Transient
	 @JoinColumn(joinName = "gldSobSegmentJoin", field = GldSobSegment.FIELD_DESCRIPTION)
	 @Length(max = 255)
	 private String accountSegmentName;

	@Transient
	@JoinCode(code = "GLD_SOB_RULE_SEGMENT_SOURCE", joinKey = FIELD_SOURCE_CODE)
	private String sourceCodeDisplay;
     }
