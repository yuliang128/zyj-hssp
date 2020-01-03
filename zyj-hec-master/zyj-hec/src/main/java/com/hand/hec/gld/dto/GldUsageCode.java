package com.hand.hec.gld.dto;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_usage_code")
@MultiLanguage
public class GldUsageCode extends BaseDTO {

     public static final String FIELD_USAGE_CODE_ID = "usageCodeId";
     public static final String FIELD_USAGE_CODE = "usageCode";
     public static final String FIELD_DESCRIPTION = "description";


     @Id
     @GeneratedValue
     private Long usageCodeId;

    /**
     * 用途代码编码
     */
     @NotEmpty
     @Length(max = 30)
     private String usageCode;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     @MultiLanguageField
     private String description;

     }
