package com.hand.hec.fnd.dto;

/**
 * Auto Generated By Hap Code Generator
 **/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.core.annotation.MultiLanguage;


@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "fnd_business_rule_parameter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FndBusinessRuleParameter extends BaseDTO {

    public static final String FIELD_PARAMETER_ID = "parameterId";
    public static final String FIELD_PARAMETER_CODE = "parameterCode";
    public static final String FIELD_PARAMETER_NAME = "parameterName";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_SQL_CONTENT = "sqlContent";
    public static final String FIELD_DATA_TYPE = "dataType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String DATA_TYPE_VARCHAR = "VARCHAR";
    public static final String DATA_TYPE_NUMERIC = "NUMERIC";


    /**
     * 权限规则参数ID
     */
    @Id
    @GeneratedValue
    private Long parameterId; 

    /**
     * 权限规则参数代码
     */
    @NotEmpty
    @Length(max = 30)
    private String parameterCode; 

    /**
     * 参数名称
     */
    @Length(max = 240)
    @MultiLanguageField
    private String parameterName; 

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    private String docCategory; 

    /**
     * SQL内容
     */
    @NotEmpty
    @Length(max = 2000)
    private String sqlContent; 

    /**
     * 数据类型
     */
    @NotEmpty
    @Length(max = 30)
    private String dataType; 

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

}
