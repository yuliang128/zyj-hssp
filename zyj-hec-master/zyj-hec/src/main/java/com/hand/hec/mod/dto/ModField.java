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
@Table(name = "mod_field")
public class ModField extends BaseDTO {

    public static final String FIELD_FIELD_ID = "fieldId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_TABLE_FIELD_NAME = "tableFieldName";
    public static final String FIELD_DTO_FIELD_NAME = "dtoFieldName";
    public static final String FIELD_MULTI_LANGUAGE_FLAG = "multiLanguageFlag";
    public static final String FIELD_FIELD_NAME = "fieldName";
    public static final String FIELD_FIELD_SEQ = "fieldSeq";
    public static final String FIELD_FIELD_DATA_TYPE = "fieldDataType";
    public static final String FIELD_FIELD_TYPE = "fieldType";
    public static final String FIELD_LOGICAL_TYPE = "logicalType";
    public static final String FIELD_LOGIC_FIELD_ID = "logicFieldId";
    public static final String FIELD_LAZY_LOAD = "lazyLoad";
    public static final String FIELD_PK_FLAG = "pkFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long fieldId;

    /**
     * 实体ID
     */
    @NotNull
    @Where
    private Long entityId;

    /**
     * 表字段名称
     */
    @Length(max = 200)
    @Where
    private String tableFieldName;

    /**
     * dto字段名称
     */
    @Length(max = 200)
    @Where
    private String dtoFieldName;

    /**
     * 多语言标志
     */
    @NotEmpty
    @Length(max = 1)
    private String multiLanguageFlag;

    /**
     * 字段名称
     */
    @NotEmpty
    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String fieldName;

    private Long fieldSeq;

    /**
     * 字段数据类型
     */
    @NotEmpty
    @Length(max = 30)
    private String fieldDataType;

    @Transient
    @JoinCode(joinKey = ModField.FIELD_FIELD_DATA_TYPE, code = "MOD_FIELD_DATA_TYPE")
    private String fieldDataTypeName;

    /**
     * 字段类型（PHYSICAL/LOGICAL)
     */
    @NotEmpty
    @Length(max = 30)
    private String fieldType;

    @Transient
    @JoinCode(joinKey = ModField.FIELD_FIELD_TYPE, code = "MOD_FIELD_TYPE")
    private String fieldTypeName;

    /**
     * 逻辑类型（Lov/Code/Prompt/GroupChild/Constant/MasterData/Program）
     */
    @Length(max = 30)
    private String logicalType;

    @Transient
    @JoinCode(joinKey = ModField.FIELD_LOGICAL_TYPE, code = "MOD_LOGICAL_TYPE")
    private String logicalTypeName;

    /**
     * 逻辑字段ID
     */
    @JoinTable(name = "logicFieldJoin", target = ModField.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = ModField.FIELD_FIELD_ID)})
    private Long logicFieldId;

    @Transient
    @JoinColumn(joinName = "logicFieldJoin", field = ModField.FIELD_FIELD_NAME)
    private String logicFieldName;

    /**
     * 延迟加载
     */
    @Length(max = 1)
    private String lazyLoad;

    /**
     * 主键字段
     */
    private String pkFlag;

    /**
     * 启用
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
