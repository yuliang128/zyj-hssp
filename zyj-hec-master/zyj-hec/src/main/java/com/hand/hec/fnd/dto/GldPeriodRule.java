package com.hand.hec.fnd.dto;

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
 * 会计期间规则dto
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:18
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_period_rule")
public class GldPeriodRule extends BaseDTO {

	public static final String FIELD_PERIOD_RULE_ID = "periodRuleId";
	public static final String FIELD_PERIOD_SET_ID = "periodSetId";
	public static final String FIELD_PERIOD_ADDITIONAL_NAME = "periodAdditionalName";
	public static final String FIELD_PERIOD_NUM = "periodNum";
	public static final String FIELD_MONTH_FROM = "monthFrom";
	public static final String FIELD_MONTH_TO = "monthTo";
	public static final String FIELD_DATE_FROM = "dateFrom";
	public static final String FIELD_DATE_TO = "dateTo";
	public static final String FIELD_QUARTER_NUM = "quarterNum";
	public static final String FIELD_ADJUSTMENT_FLAG = "adjustmentFlag";


	@Id
	@GeneratedValue
	private Long periodRuleId;

	/**
	 * 会计期ID
	 */
	@Where
	@NotNull
	private Long periodSetId;

	/**
	 * 期间名称附加
	 */
	@Length(max = 30)
	private String periodAdditionalName;

	/**
	 * 月份
	 */
	@NotNull
	private Long periodNum;

	/**
	 * 月份从
	 */
	@NotNull
	private Long monthFrom;

	/**
	 * 月份到
	 */
	@NotNull
	private Long monthTo;

	/**
	 * 日期从
	 */
	@NotNull
	private Long dateFrom;

	/**
	 * 日期到
	 */
	@NotNull
	private Long dateTo;

	/**
	 * 季度
	 */
	@NotNull
	private Long quarterNum;

	/**
	 * 调整标识
	 */
	@NotEmpty
	@Length(max = 1)
	private String adjustmentFlag;

}
