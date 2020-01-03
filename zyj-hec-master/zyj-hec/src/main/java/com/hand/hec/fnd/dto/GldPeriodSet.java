package com.hand.hec.fnd.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
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

/**
 * <p>
 * 会计期dto
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:19
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "gld_period_set")
public class GldPeriodSet extends BaseDTO {

	public static final String FIELD_PERIOD_SET_ID = "periodSetId";
	public static final String FIELD_PERIOD_SET_CODE = "periodSetCode";
	public static final String FIELD_PERIOD_SET_NAME = "periodSetName";
	public static final String FIELD_TOTAL_PERIOD_NUM = "totalPeriodNum";
	public static final String FIELD_PERIOD_ADDITIONAL_FLAG = "periodAdditionalFlag";


	@Id
	@GeneratedValue
	private Long periodSetId;

	/**
	 * 会计期代码
	 */
	@NotEmpty
	@Length(max = 30)
	@Where
	private String periodSetCode;

	/**
	 * 会计期名称
	 */
	@MultiLanguageField
	@Length(max = 500)
	@Where
	private String periodSetName;

	/**
	 * 会计期总数
	 */
	@NotNull
	private Long totalPeriodNum;

	/**
	 * 名称附加（SYSCODE：PERIOD_ADDITIONAL_FLAG）
	 */
	@NotEmpty
	@Length(max = 1)
	private String periodAdditionalFlag;

}
