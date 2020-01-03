package com.hand.hec.fnd.dto;

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

/**
 * <p>
 * 核算主体级会计期间控制dto
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 16:42
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_ae_period_status")
public class GldAePeriodStatus extends BaseDTO {

	public static final String FIELD_PERIOD_STATUS_ID = "periodStatusId";
	public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
	public static final String FIELD_PERIOD_SET_CODE = "periodSetCode";
	public static final String FIELD_PERIOD_NAME = "periodName";
	public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";
	public static final String FIELD_PERIOD_STATUS_CODE = "periodStatusCode";


	@Id
	@GeneratedValue
	private Long periodStatusId;

	/**
	 * 核算主体ID
	 */
	@NotNull
	private Long accEntityId;

	/**
	 * 会计期代码
	 */
	@NotEmpty
	@Length(max = 30)
	private String periodSetCode;

	/**
	 * 会计期间
	 */
	@NotEmpty
	@Length(max = 30)
	private String periodName;

	/**
	 * 期间序号
	 */
	@NotNull
	private Long internalPeriodNum;


	@NotNull
	@Transient
	private Long internalPeriodNumForClose;

	/**
	 * 期间状态代码
	 */
	@NotEmpty
	@Length(max = 20)
	private String periodStatusCode;

	/**
	 * 核算主体代码（combBox）
	 */
	@Transient
	private String code;

	/**
	 * 核算主体名称（combBox）
	 */
	@Transient
	private String name;

	/**
	 * 核算主体显示名（combBox）
	 */
	@Transient
	private String valueName;

	/**
	 * 年（会计期）
	 */
	@Transient
	private Long periodYear;

	/**
	 * 序号（会计期）
	 */
	@Transient
	private Long periodNum;

	/**
	 * 日期从（会计期）
	 */
	@Transient
	private String startDate;

	/**
	 * 日期至（会计期）
	 */
	@Transient
	private String endDate;

	/**
	 * 季度（会计期）
	 */
	@Transient
	private Long quarterNum;

	/**
	 * 调整标识（会计期）
	 */
	@Transient
	private String adjustmentFlag;

	@Transient
	private Long periodSetId;

}
