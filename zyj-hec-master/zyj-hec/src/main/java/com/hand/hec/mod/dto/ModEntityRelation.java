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
@ExtensionAttribute(disable = true)
@Table(name = "mod_entity_relation")
public class ModEntityRelation extends BaseDTO {

    public static final String FIELD_RELATION_ID = "relationId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_TARGET_ENTITY_ID = "targetEntityId";
    public static final String FIELD_SOURCE_FIELD_ID = "sourceFieldId";
    public static final String FIELD_TARGET_FIELD_ID = "targetFieldId";
    public static final String FIELD_CONDITION_TYPE = "conditionType";
    public static final String FIELD_CONDITION_VALUE = "conditionValue";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long relationId;

    /**
     * 实体ID
     */
    @NotNull
    private Long entityId;

    /**
     * 目标实体ID
     */
    private Long targetEntityId;

    /**
     * 来源字段ID
     */
    private Long sourceFieldId;

    /**
     * 目标字段ID
     */
    private Long targetFieldId;

    /**
     * 条件类型
     */
    private String conditionType;

    /**
     * 条件值
     */
    private String conditionValue;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
