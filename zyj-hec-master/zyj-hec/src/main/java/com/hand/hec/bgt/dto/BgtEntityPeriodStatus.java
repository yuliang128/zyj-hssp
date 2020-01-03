package com.hand.hec.bgt.dto;

/**
 * <p>
 * 预算实体级预算期间控制表
 * </p>
 * 
 * @author guiyuting 2019/03/14 18:46
 */
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "bgt_entity_period_status")
@Builder
public class BgtEntityPeriodStatus extends BaseDTO {

    public static final String FIELD_PERIOD_STATUS_ID = "periodStatusId";
    public static final String FIELD_BGT_ENTITY_ID = "bgtEntityId";
    public static final String FIELD_BGT_PERIOD_SET_CODE = "bgtPeriodSetCode";
    public static final String FIELD_BGT_PERIOD_YEAR = "bgtPeriodYear";
    public static final String FIELD_BGT_PERIOD_NUM = "bgtPeriodNum";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_BGT_PERIOD_STATUS_CODE = "bgtPeriodStatusCode";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_QUARTERNUM = "quarterNum";
    public static final String FIELD_STARTDATE = "startDate";
    public static final String FIELD_ENDDATE = "endDate";

    public static final String PERIOD_OPENED_STATUS = "O";
    public static final String PERIOD_CLOSED_STATUS = "C";


    @Id
    @GeneratedValue
    private Long periodStatusId;

    /**
     * 预算实体ID
     */
    @NotNull
    private Long bgtEntityId;

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
     * 预算期间状态
     */
    @Length(max = 20)
    private String bgtPeriodStatusCode;

    /**
     * 预算组织ID
     */
    @NotNull
    private Long bgtOrgId;

    @Transient
    private String quarterNum;

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    @Transient
    private Date startDate;

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    @Transient
    private Date endDate;

}
