package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算中心关联预算实体
 * </p>
 * 
 * @author mouse 2019/01/07 16:22
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_center_ref_bgt_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCenterRefBgtEntity extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_CENTER_ID = "centerId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * pk
     */
    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 预算中心ID
     */
    @NotNull
    private Long centerId;

    /**
     * 预算主体ID
     */
    @NotNull
    private Long entityId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 预算实体code
     */
    @Transient
    private String entityCode;

    /**
     * 预算实体描述
     */
    @Transient
    private String description;

    /**
     * 预算组织id
     */
    @Transient
    private Long bgtOrgId;

}
