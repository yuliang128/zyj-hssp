package com.hand.hec.gld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_acc_entity_hierarchy")
public class GldAccEntityHierarchy extends BaseDTO {

    public static final String FIELD_HIERARCHY_ID = "hierarchyId";
    public static final String FIELD_PARENT_ENTITY_ID = "parentEntityId";
    public static final String FIELD_ENTITY_ID = "entityId";


    @Id
    @GeneratedValue
    private Long hierarchyId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long parentEntityId;

    /**
     * 子核算主体ID
     */
    @NotNull
    private Long entityId;

    /**
     * 核算主体公司类型描述
     */
    @Transient
    private String companyTypeDisplay;

    /**
     * 子核算主体代码
     */
    @Transient
    private String accEntityCode;

    /**
     * 子核算主体名称
     */
    @Transient
    private String accEntityName;

}
