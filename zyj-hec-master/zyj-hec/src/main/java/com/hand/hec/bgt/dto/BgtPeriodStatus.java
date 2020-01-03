package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * <p>
 * 预算期间状态
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
@ExtensionAttribute(disable = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtPeriodStatus extends BaseDTO {

    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_PERIOD_SET_CODE = "bgtPeriodSetCode";
    public static final String FIELD_BGT_PERIOD_YEAR = "bgtPeriodYear";
    public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_PERIOD_STATUS_CODE = "bgtPeriodStatusCode";
    public static final String FIELD_QUARTERNUM = "quarterNum";
    public static final String FIELD_STARTDATE = "startDate";
    public static final String FIELD_ENDDATE = "endDate";


    /**
     * 预算组织ID
     */
    @NotNull
    private Long bgtOrgId;

    /**
     * 预算期间
     */
    @NotEmpty
    @Length(max = 30)
    private String bgtPeriodSetCode;

    /**
     * 年度
     */
    @NotNull
    private Long bgtPeriodYear;

    /**
     * 预算期间数
     */
    @NotNull
    private Long bgtPeriodNum;

    /**
     * 期间
     */
    @Length(max = 30)
    private String periodName;

    /**
     * 公司ID
     */
    private Long bgtEntityId;

    /**
     * 预算期间状态
     */
    @Length(max = 20)
    private String bgtPeriodStatusCode;

    private String quarterNum;

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date startDate;

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date endDate;


}
