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
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "mod_entity_reference")
public class ModEntityReference extends BaseDTO {

     public static final String FIELD_REFERENCE_ID = "referenceId";
     public static final String FIELD_ENTITY_ID = "entityId";
     public static final String FIELD_REF_MODEL_ID = "refModelId";
     public static final String FIELD_REF_ENTITY_ID = "refEntityId";
     public static final String FIELD_REF_TYPE = "refType";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long referenceId;

    /**
     * 实体ID
     */
     @NotNull
     private Long entityId;

    /**
     * 关联模型ID
     */
     @NotNull
     private Long refModelId;

    /**
     * 关联实体ID
     */
     @NotNull
     private Long refEntityId;

    /**
     * 关联类型
     */
     @NotEmpty
     @Length(max = 30)
     private String refType;

    /**
     * 启用
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
