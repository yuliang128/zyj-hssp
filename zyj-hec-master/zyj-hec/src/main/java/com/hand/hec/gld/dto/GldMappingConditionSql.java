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
@Table(name = "gld_mapping_condition_sql")
@MultiLanguage
public class GldMappingConditionSql extends BaseDTO {

     public static final String FIELD_MAPPING_CONDITION_SQL_ID = "mappingConditionSqlId";
     public static final String FIELD_MAPPING_CONDITION_CODE = "mappingConditionCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_LOV_STATEMENT = "lovStatement";
     public static final String FIELD_REF_TABLE = "refTable";
     public static final String FIELD_REF_FIELD = "refField";
     public static final String FIELD_REF_ID_FIELD = "refIdField";


     @Id
     @GeneratedValue
     private Long mappingConditionSqlId;

    /**
     * 匹配项代码
     */
     @NotEmpty
     @Length(max = 30)
     private String mappingConditionCode;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     @MultiLanguageField
     private String description;

    /**
     * 取值SQL
     */
     @NotEmpty
     @Length(max = 2000)
     private String lovStatement;

    /**
     * 关联表名称
     */
     @NotEmpty
     @Length(max = 30)
     private String refTable;

    /**
     * 关联字段
     */
     @NotEmpty
     @Length(max = 30)
     private String refField;

    /**
     * 关联匹配项
     */
     @Length(max = 30)
     private String refIdField;
     }
