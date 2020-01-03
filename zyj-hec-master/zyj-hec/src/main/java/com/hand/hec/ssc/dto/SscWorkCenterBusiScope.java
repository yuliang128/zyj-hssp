package com.hand.hec.ssc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
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
@Table(name = "ssc_work_center_busi_scope")
@MultiLanguage
public class SscWorkCenterBusiScope extends BaseDTO {

     public static final String FIELD_SCOPE_ID = "scopeId";
     public static final String FIELD_SCOPE_CODE = "scopeCode";
     public static final String FIELD_WORK_CENTER_ID = "workCenterId";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_START_DATE = "startDate";
     public static final String FIELD_END_DATE = "endDate";


     @Id
     @GeneratedValue
     private Long scopeId;

    /**
     * 业务范围代码
     */
     @NotEmpty
     @Length(max = 30)
     private String scopeCode;

    /**
     * 工作中心ID
     */
     @NotNull
     private Long workCenterId;

    /**
     * 描述ID
     */
     @Length(max = 500)
     @MultiLanguageField
     private String description;

    /**
     * 管理组织ID
     */
     private Long magOrgId;

    /**
     * 启用日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

     @Transient
     private String busiScopeCodeName;

     @Transient
     private String magOrgCodeName;

     @Transient
     private String magOrgName;

     }
