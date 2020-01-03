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
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_sob_period_status")
public class GldSobPeriodStatus extends BaseDTO {

     public static final String FIELD_PERIOD_STATUS_ID = "periodStatusId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_PERIOD_SET_CODE = "periodSetCode";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";
     public static final String FIELD_PERIOD_STATUS_CODE = "periodStatusCode";

     public static final String FIELD_PERIOD_STATUS_TYPE_O = "O";
     public static final String FIELD_PERIOD_STATUS_TYPE_C = "C";


     @Id
     @GeneratedValue
     private Long periodStatusId;

    /**
     * 账套ID
     */
     @NotNull
     private Long setOfBooksId;

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

    /**
     * 期间状态代码
     */
     @NotEmpty
     @Length(max = 20)
     private String periodStatusCode;


	/**
	 * 年
	 */
	@Transient
     private Long periodYear;

	/**
	 * 序号
	 */
	@Transient
     private Long periodNum;

	/**
	 * 日期从
	 */
	@Transient
	private Date startDateC;

	/**
	 * 日期至
	 */
	@Transient
	private Date endDateC;


	/**
	 * 季度
	 */
	@Transient
	private Long quarterNum;


	/**
	 * 调整期间
	 */
	@Transient
	private String adjustmentFlag;






}
