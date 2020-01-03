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
@Table(name = "gld_mapping_condition")
public class GldMappingCondition extends BaseDTO {

     public static final String FIELD_MAPPING_CONDITION_ID = "mappingConditionId";
     public static final String FIELD_PROCESS_ID = "processId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_MAPPING_CONDITION_CODE = "mappingConditionCode";
     public static final String FIELD_MAPPING_CONDITION_VALUE = "mappingConditionValue";


     @Id
     @GeneratedValue
     private Long mappingConditionId;

    /**
     * 处理
     */
     @NotNull
     private Long processId;

    /**
     * 管理组织
     */
     @NotNull
     private Long magOrgId;

    /**
     * 账套
     */
     @NotNull
     private Long setOfBooksId;

    /**
     * 匹配条件代码
     */
     @NotEmpty
     @Length(max = 30)
     private String mappingConditionCode;

    /**
     * 匹配条件值
     */
     @Length(max = 30)
     private String mappingConditionValue;

     }
