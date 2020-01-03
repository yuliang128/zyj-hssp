package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;
/**
 * <p>
 * 预算期间规则
 * </p>
 * 
 * @author mouse 2019/01/07 16:40
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_period_rule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtPeriodRule extends BaseDTO {

    public static final String FIELD_DATE_TO = "dateTo";
    public static final String FIELD_QUARTER_NUM = "quarterNum";
    public static final String FIELD_PERIOD_RULE_ID = "periodRuleId";
    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_PERIOD_ADDITIONAL_NAME = "periodAdditionalName";
    public static final String FIELD_PERIOD_NUM = "periodNum";
    public static final String FIELD_MONTH_FROM = "monthFrom";
    public static final String FIELD_MONTH_TO = "monthTo";
    public static final String FIELD_DATE_FROM = "dateFrom";


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
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long periodRuleId;

    /**
     * 预算期ID
     */
    @Where
    @NotNull
    private Long periodSetId;

    /**
     * 期间名附加
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


}
