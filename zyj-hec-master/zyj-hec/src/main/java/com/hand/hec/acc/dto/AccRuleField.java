package com.hand.hec.acc.dto;

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
@Table(name = "acc_rule_field")
public class AccRuleField extends BaseDTO {

     public static final String FIELD_FIELD_ID = "fieldId";
     public static final String FIELD_ENTITY_ID = "entityId";
     public static final String FIELD_MOD_FIELD_ID = "modFieldId";
     public static final String FIELD_FIELD_CODE = "fieldCode";
     public static final String FIELD_FIELD_NAME = "fieldName";
     public static final String FIELD_FIELD_DATA_TYPE = "fieldDataType";
     public static final String FIELD_FIELD_TYPE = "fieldType";
     public static final String FIELD_REF_ENTITY_FIELD_ID = "refEntityFieldId";
     public static final String FIELD_REF_JE_FIELD_ID = "refJeFieldId";
     public static final String FIELD_LOGICAL_TYPE = "logicalType";
     public static final String FIELD_CONSTANT_VALUE = "constantValue";
     public static final String FIELD_FAST_CODE = "fastCode";
     public static final String FIELD_FORMULA = "formula";


     @Id
     @GeneratedValue
     private Long fieldId;

    /**
     * 实体ID
     */
     @NotNull
     private Long entityId;

    /**
     * 模型字段ID
     */
     private Long modFieldId;

    /**
     * 字段代码
     */
     @NotEmpty
     @Length(max = 30)
     private String fieldCode;

    /**
     * 字段名称
     */
     @NotEmpty
     @Length(max = 200)
     private String fieldName;

    /**
     * 字段数据类型
     */
     @NotEmpty
     @Length(max = 30)
     private String fieldDataType;

    /**
     * 字段类型
     */
     @NotEmpty
     @Length(max = 30)
     private String fieldType;

    /**
     * 关联实体字段ID
     */
     private Long refEntityFieldId;

    /**
     * 关联分录字段ID
     */
     private Long refJeFieldId;

    /**
     * 逻辑类型
     */
     @Length(max = 30)
     private String logicalType;

    /**
     * 常量
     */
     @Length(max = 200)
     private String constantValue;

    /**
     * 快码
     */
     @Length(max = 200)
     private String fastCode;

    /**
     * 函数
     */
     @Length(max = 2000)
     private String formula;

     }
