package com.hand.hec.bpm.dto;

/**
 * Auto Generated By Code Generator Description:
 */

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

@ExtensionAttribute(disable = true)
@Table(name = "bpm_page_group_entity")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PageGroupEntity extends BaseDTO {

    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_GROUP_ID = "groupId";
    public static final String FIELD_ORDER_NUM = "orderNum";
    public static final String FIELD_ENTITY_LEVEL = "entityLevel";
    public static final String FIELD_PARENT_ENTITY_ID = "parentEntityId";
    public static final String FIELD_ENTITY_CODE = "entityCode";
    public static final String FIELD_ENTITY_NAME = "entityName";
    /**
     * 字段ID，主键
     */
    @Id
    @GeneratedValue
    @Where
    private Long entityId;

    /**
     * 所属页面组
     */
    @Where
    @NotNull
    private Long groupId;

    /**
     * 序号
     */
    @NotNull
    private Long orderNum;

    /**
     * 序号
     */
    private Long entityLevel;

    /**
     * 父实体ID
     */
    @JoinTable(name = "parentEntityJoin", type = JoinType.LEFT, target = PageGroupEntity.class,
                    on = {@JoinOn(joinField = PageGroupEntity.FIELD_ENTITY_ID)})
    @Where
    private Long parentEntityId;

    /**
     * 父实体代码
     */
    @JoinColumn(joinName = "parentEntityJoin", field = PageGroupEntity.FIELD_ENTITY_CODE)
    @Transient
    private String parentEntityCode;

    /**
     * 实体代码
     */
    @NotEmpty
    @Length(max = 200)
    private String entityCode;

    /**
     * 实体名称
     */
    @NotEmpty
    @Length(max = 200)
    private String entityName;


    @Transient
    private String expand = "Y";
}
