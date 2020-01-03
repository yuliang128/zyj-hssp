package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import java.util.Date;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算期间
 * </p>
 * 
 * @author mouse 2019/01/07 16:40
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_period")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtPeriod extends BaseDTO {

    public static final String FIELD_PERIOD_ID = "periodId";
    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_PERIOD_YEAR = "periodYear";
    public static final String FIELD_PERIOD_NUM = "periodNum";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_INTERNAL_PERIOD_NUM = "internalPeriodNum";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_QUARTER_NUM = "quarterNum";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long periodId;

    /**
     * 预算期ID
     */
    @NotNull
    @Where
    private Long periodSetId;

    /**
     * 年
     */
    @NotNull
    @Where
    private Long periodYear;

    /**
     * 月份
     */
    @NotNull
    @Where
    private Long periodNum;

    /**
     * 期间
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String periodName;

    /**
     * 期间序号
     */
    @NotNull
    @Where
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
    @Where(expression = " exists (select * from bgt_organization o where o.period_set_id = a.period_set_id and o.bgt_org_id = #{dto.bgtOrgId})")
    private Long bgtOrgId;

    @Transient
    private String periodNameFrom;

    @Transient
    private String periodNameTo;

}
