package com.hand.hec.ssc.dto;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "ssc_work_center_company")
public class SscWorkCenterCompany extends BaseDTO {

     public static final String FIELD_RECORD_ID = "recordId";
     public static final String FIELD_SCOPE_ID = "scopeId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long recordId;

    /**
     * 业务范围ID
     */
     @NotNull
     private Long scopeId;

    /**
     * 公司ID
     */
     @NotNull
     private Long companyId;

    /**
     * 启用状态
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @Transient
     private String companyShortName;

     @Transient
     private String companyCode;

     @Transient
     private Long magOrgId;

     }
