package com.hand.hec.mod.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "mod_entity")
public class ModEntity extends BaseDTO {

    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_MODEL_ID = "modelId";
    public static final String FIELD_PARENT_ENTITY_ID = "parentEntityId";
    public static final String FIELD_RELATION_TYPE = "relationType";
    public static final String FIELD_ENTITY_CODE = "entityCode";
    public static final String FIELD_ENTITY_NAME = "entityName";
    public static final String FIELD_ENTITY_LEVEL = "entityLevel";
    public static final String FIELD_ENTITY_SEQ = "entitySeq";
    public static final String FIELD_REF_TABLE = "refTable";
    public static final String FIELD_REF_DTO = "refDto";
    public static final String FIELD_MULTI_LANGUAGE_FLAG = "multiLanguageFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_EXPAND = "expand";


    @Id
    @GeneratedValue
    @Where
    private Long entityId;

    /**
     * 模型ID
     */
    @NotNull
    @Where
    private Long modelId;

    private Long entityLevel;

    private Long entitySeq;

    /**
     * 父实体ID
     */
    @JoinTable(name = "parentEntityJoin", target = ModEntity.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ModEntity.FIELD_ENTITY_ID)})
    private Long parentEntityId;

    @Transient
    @JoinColumn(joinName = "parentEntityJoin", field = ModEntity.FIELD_ENTITY_CODE)
    private String parentEntityCode;

    /**
     * 关联类型
     */
    private String relationType;

    @Transient
    @JoinCode(joinKey = ModEntity.FIELD_RELATION_TYPE, code = "MOD_RELATION_TYPE")
    private String relationTypeName;

    /**
     * 实体代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String entityCode;

    /**
     * 实体名称
     */
    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String entityName;

    /**
     * 关联表
     */
    @Length(max = 200)
    private String refTable;

    /**
     * 关联DTO
     */
    @Length(max = 500)
    private String refDto;

    /**
     * 多语言标志
     */
    @Length(max = 1)
    private String multiLanguageFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String expand = "Y";
}
