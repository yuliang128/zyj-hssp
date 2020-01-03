package com.hand.hec.fnd.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 会计期间dto
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:13
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_period")
public class GldPeriod extends BaseDTO {

    public static final String FIELD_PERIOD_ID = "periodId";
    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_NUM = "periodNum";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_ADJUSTMENT_FLAG = "adjustmentFlag";
    public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_QUARTER_NUM = "quarterNum";

    /**
     * 期间状态
     * 
     * @STATUS_CODE_O 打开
     * @STATUS_CODE_C 关闭
     */
    public static final String STATUS_CODE_O = "O";
    public static final String STATUS_CODE_C = "C";

    @Id
    @GeneratedValue
    private Long periodId;

    /**
     * 会计期ID
     */
    @NotNull
    private Long periodSetId;

    /**
     * 年
     */
    @NotNull
    private Long periodYear;

    /**
     * 月份
     */
    @NotNull
    private Long periodNum;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    private String periodName;

    /**
     * 调整
     */
    @NotEmpty
    @Length(max = 1)
    private String adjustmentFlag;

    /**
     * 期间序号
     */
    @NotNull
    private Long internalPeriodNum;

    /**
     * 日期从
     */
    private Date startDate;

    /**
     * 日期到
     */
    private Date endDate;

    /**
     * 季度
     */
    @NotNull
    private Long quarterNum;

    @Transient
    private Long yearFrom;

    @Transient
    private Long yearTo;

    @Transient
    private Integer count;

    @Transient
    private String periodSetCode;

}
