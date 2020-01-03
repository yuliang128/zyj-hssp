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
@Table(name = "acc_je_type_field")
public class AccJeTypeField extends BaseDTO {

     public static final String FIELD_FIELD_ID = "fieldId";
     public static final String FIELD_JE_TYPE_ID = "jeTypeId";
     public static final String FIELD_FIELD_CODE = "fieldCode";
     public static final String FIELD_FIELD_NAME = "fieldName";
     public static final String FIELD_FIELD_SEQ = "fieldSeq";
     public static final String FIELD_TABLE_FIELD_NAME = "tableFieldName";
     public static final String FIELD_DTO_FIELD_NAME = "dtoFieldName";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long fieldId;

    /**
     * 分录类型ID
     */
     @NotNull
     private Long jeTypeId;

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
     * 字段顺序
     */
     @NotNull
     private Long fieldSeq;

    /**
     * 表字段名称
     */
     @NotEmpty
     @Length(max = 200)
     private String tableFieldName;

    /**
     * dto字段名称
     */
     @NotEmpty
     @Length(max = 200)
     private String dtoFieldName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
