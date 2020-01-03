package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算实体层次
 * </p>
 * 
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_entity_hierarchy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtEntityHierarchy extends BaseDTO {

    public static final String FIELD_HIERARCHY_ID = "hierarchyId";
    public static final String FIELD_PARENT_ENTITY_ID = "parentEntityId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long hierarchyId;

    /**
     * 预算实体ID
     */
    @NotNull
    private Long parentEntityId;

    /**
     * 子预算实体ID
     */
    @NotNull
    @JoinTable(name = "entityJoin", joinMultiLanguageTable = true, target = BgtEntity.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "entityJoin2", joinMultiLanguageTable = false, target = BgtEntity.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID)})
    private Long entityId;

    @Transient
    @JoinColumn(joinName = "entityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    private String entityName;


    @Transient
    @JoinColumn(joinName = "entityJoin2", field = BgtEntity.FIELD_DESCRIPTION)
    private String entityCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
