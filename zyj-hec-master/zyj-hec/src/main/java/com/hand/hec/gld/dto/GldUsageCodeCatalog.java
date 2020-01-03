package com.hand.hec.gld.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_usage_code_catalog")
public class GldUsageCodeCatalog extends BaseDTO {

     public static final String FIELD_USAGE_CODE = "usageCode";
     public static final String FIELD_MAPPING_CONDITION_CODE = "mappingConditionCode";
     public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";


    /**
     * 用途代码
     */
     @Id
     @GeneratedValue
     private String usageCode;

    /**
     * 目录编码
     */
     @NotEmpty
     @Length(max = 30)
     private String mappingConditionCode;

    /**
     * 布局位置
     */
     @NotNull
     private Long layoutPriority;

     }
