package com.hand.hec.mod.dto;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "mod_field_master_data")
public class ModFieldMasterData extends BaseDTO {

     public static final String FIELD_FIELD_ID = "fieldId";
     public static final String FIELD_TARGET_ENTITY_ID = "targetEntityId";
     public static final String FIELD_TARGET_FIELD_ID = "targetFieldId";
     public static final String FIELD_SOURCE_FIELD_2_ID = "sourceField2Id";
     public static final String FIELD_TARGET_FIELD_2_ID = "targetField2Id";
     public static final String FIELD_SOURCE_FIELD_3_ID = "sourceField3Id";
     public static final String FIELD_TARGET_FIELD_3_ID = "targetField3Id";
     public static final String FIELD_TARGET_VALUE_FIELD_ID = "targetValueFieldId";


     @Id
     @GeneratedValue
     private Long fieldId;

    /**
     * 目标实体ID
     */
     @NotNull
     private Long targetEntityId;

    /**
     * 目标字段ID
     */
     private Long targetFieldId;

    /**
     * 来源字段2ID
     */
     private Long sourceField2Id;

    /**
     * 目标字段2ID
     */
     private Long targetField2Id;

    /**
     * 来源字段3ID
     */
     private Long sourceField3Id;

    /**
     * 目标字段3ID
     */
     private Long targetField3Id;

    /**
     * 目标值字段ID
     */
     @NotNull
     private Long targetValueFieldId;

     }
