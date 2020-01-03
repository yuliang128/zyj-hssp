package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算中心
 * </p>
 * 
 * @author mouse 2019/01/07 16:21
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtCenter extends BaseDTO {

    public static final String FIELD_CENTER_ID = "centerId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_CENTER_CODE = "centerCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CENTER_TYPE = "centerType";
    public static final String FIELD_SOURCE_TYPE_CODE = "sourceTypeCode";
    public static final String FIELD_SOURCE_ID = "sourceId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    public static final String CENTER_TYPE_DETAILS = "DETAILS";
    public static final String CENTER_TYPE_SUMMARY = "SUMMARY";
    public static final String SOURCE_TYPE_MANAGEMENT = "MANAGEMENT";
    public static final String SOURCE_TYPE_ACCOUNTING = "ACCOUNTING";

    @Id
    @GeneratedValue
    private Long centerId;

    @NotNull
    private Long bgtOrgId;

    @NotEmpty
    @Length(max = 30)
    @Where
    private String centerCode;

    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    @Length(max = 30)
    private String currencyCode;

    @NotEmpty
    @Length(max = 30)
    private String centerType;

    @NotEmpty
    @Length(max = 30)
    private String sourceTypeCode;

    private Long sourceId;

    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String currencyName;

    @Transient
    private String sourceDetail;

    @Transient
    private String centerTypeName;

    @Transient
    private String sourceTypeName;

    @Transient
    private List<BgtEntity> bgtEntityList;

    /**
     * 有效日期从
     */
    @Transient
    private Date startDateActive;

    /**
     * 有效日期至
     */
    @Transient
    private Date endDateActive;
}
